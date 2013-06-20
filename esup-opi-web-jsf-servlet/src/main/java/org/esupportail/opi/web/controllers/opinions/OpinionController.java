package org.esupportail.opi.web.controllers.opinions;

import fj.*;
import fj.data.Option;
import fj.data.Stream;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.etat.EtatArriveComplet;
import org.esupportail.opi.domain.beans.parameters.*;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.User;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.BeanTrtCmi;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.beanEnum.WayfEnum;
import org.esupportail.opi.web.beans.paginator.IndividuPaginator;
import org.esupportail.opi.web.beans.paginator.IndividuPojoPaginator;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.*;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorDate;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.controllers.parameters.NomenclatureController;
import org.esupportail.opi.web.controllers.references.CommissionController;
import org.esupportail.opi.web.controllers.user.IndividuController;
import org.esupportail.opi.web.utils.fj.Conversions;
import org.esupportail.opi.web.utils.paginator.LazyDataModel;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.primefaces.model.SortOrder;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.*;

import static fj.data.Option.*;
import static fj.data.Option.fromString;
import static fj.data.Stream.*;
import static fj.data.Stream.join;
import static org.esupportail.opi.web.utils.fj.Conversions.individuToPojo;
import static org.esupportail.opi.web.utils.paginator.LazyDataModel.lazyModel;


/**
 * @author ylecuyer
 */
public class OpinionController
        extends AbstractAccessController {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -5349678716095172353L;
    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

	/*
     ******************* PROPERTIES ******************* */

    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum;

    /**
     * The wayfEnum.
     */
    private WayfEnum wayfEnum;

    /**
     * The indVoeuxPojo.
     */
    private IndVoeuPojo indVoeuxPojo;

    /**
     * The list of Avis.
     */
    private List<AvisPojo> listAvisPojo;

    /**
     * The avis.
     */
    private Avis avis;

    /**
     * The id of the selected type of decision.
     */
    private TypeDecision selectedTypeDec;

    /**
     * The id of the selected motivation of opinion.
     */
    private Integer idSelectedMotiv;

    /**
     * The motivation selected in the list.
     */
    private NomenclaturePojo selectedMotivation;

    /**
     * The commission selected in the list.
     */
    private Integer idSelectedComm;

    /**
     * Has true if all Individu  is selected.
     * Default value = false
     */
    private Boolean allChecked;

    /**
     * The all wishes selected.
     */
    private Map<Individu, List<IndVoeuPojo>> wishSelected;

    /**
     * see {@link Refused}.
     */
    private Refused refused;

    /**
     * see {@link Preselection}.
     */
    private Preselection preSelection;

    /**
     * see {@link ListeComplementaire}.
     */
    private ListeComplementaire listeComplementaire;

    /**
     * see {@link InscriptionAdm}.
     */
    private InscriptionAdm inscriptionAdm;

    /**
     * true if the type of decision is LC.
     * Default value : false.
     */
    private Boolean isUsingLC;
    /**
     * true if the type of decision is DEF.
     * Default value : false.
     */
    private Boolean isUsingDEF;
    /**
     * true if the type of decision is Preselection.
     * Default value : false.
     */
    private Boolean isUsingPreselect;

    private NomenclatureController nomenclatureController;

    private CommissionController commissionController;

    private IndividuController individuController;

    private Map<VersionEtpOpi, List<Integer>> mapTestRang = new HashMap<VersionEtpOpi, List<Integer>>();

    private LazyDataModel<IndividuPojo> indPojoLDM;

    private boolean renderTable;

    public OpinionController() {
        super();
    }

    @Override
    public void reset() {
        actionEnum = new ActionEnum();
        wayfEnum = new WayfEnum();
        listAvisPojo = new ArrayList<AvisPojo>();
        avis = new Avis();
        allChecked = false;
        wishSelected = new HashMap<Individu, List<IndVoeuPojo>>();
        selectedTypeDec = null;
        idSelectedMotiv = null;
        selectedMotivation = null;
        mapTestRang.clear();
        renderTable = false;
        isUsingLC = false;
        isUsingDEF = false;
        isUsingPreselect = false;
    }

    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();
        String canNotBeNull = " can not be null";
        Assert.notNull(this.nomenclatureController,
                "property nomenclatureController of class " + this.getClass().getName()
                        + canNotBeNull);
        Assert.notNull(this.refused,
                "property refused of class " + this.getClass().getName()
                        + canNotBeNull);
        Assert.notNull(this.preSelection,
                "property preSelection of class " + this.getClass().getName()
                        + canNotBeNull);
        Assert.notNull(this.listeComplementaire,
                "property listeComplementaire of class " + this.getClass().getName()
                        + canNotBeNull);
        Assert.notNull(this.inscriptionAdm,
                "property inscriptionAdm of class " + this.getClass().getName()
                        + canNotBeNull);

        indPojoLDM = individuController.getIndLDM().map(individuToPojo(
                getDomainApoService(), getParameterService(), getI18nService()).andThen(setIndPojoAttrs));

        reset();
    }

    private final F<IndividuPojo, IndividuPojo> setIndPojoAttrs = new F<IndividuPojo, IndividuPojo>() {
        public IndividuPojo f(IndividuPojo iPojo) {
            iPojo.setIsUsingLC(isUsingLC);
            iPojo.setIsUsingDEF(isUsingDEF);
            for (IndVoeuPojo voeuPojo : iPojo.getIndVoeuxPojo()) {
                Avis a = new Avis();
                voeuPojo.setNewAvis(a);
                voeuPojo.setIsUsingLC(isUsingLC);
                voeuPojo.setIsUsingDEF(isUsingDEF);
                if (isUsingPreselect) {
                    // cas de la selection de la preselection
                    //charge le commentaire
                    TraitementCmi t = voeuPojo.getIndVoeu().getLinkTrtCmiCamp().getTraitementCmi();
                    if (t.getSelection() != null)
                        voeuPojo.getNewAvis().setCommentaire(t.getSelection().getComment());
                }
            }
            return iPojo;
        }
    };

    /**
     * Callback to list of student for the gestion of the opinions.
     *
     * @return String
     */
    public String goEnterAllStudentsOpinions() {
        reset();
        return NavigationRulesConst.ENTER_ALL_STUDENTS_OPINIONS;
    }

    /**
     * Callback to see l'avis d'un voeu.
     *
     * @return String
     */
    public String goSeeOpinionVoeu() {
        for (Avis a : indVoeuxPojo.getIndVoeu().getAvis()) {
            if (a.getResult().getCodeTypeConvocation()
                    .equals(listeComplementaire.getCode())) {
                indVoeuxPojo.setIsUsingLC(true);
            }
        }
        isUsingDEF = false;
        isUsingLC = false;
        actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
        return NavigationRulesConst.DISPLAY_OPINIONS;
    }

    /**
     * Callback to return from OpinionVoeu to lists.
     *
     * @return String
     */
    public String goBackOpinions() {
	    return goEnterAllStudentsOpinions();
    }

    /**
     * Callback to add un nouvel avis.
     */
    public void goAddAvis() {
        avis = new Avis();
        selectedTypeDec = null;
        actionEnum.setWhatAction(ActionEnum.ADD_ACTION);
    }

    /**
     * Callback to add un nouvel avis avec appel.
     */
    public void goAddAvisAppel() {
        avis = new Avis();
        avis.setAppel(true);
        selectedTypeDec = null;
        actionEnum.setWhatAction(ActionEnum.ADD_ACTION);
    }


    /**
     * Callback to update l'avis.
     */
    public void goUpdateAvis() {
        isUsingLC = false;
        isUsingDEF = false;
        // mise e jour des identifiants pour les listes deroulantes
        selectedTypeDec = avis.getResult();
        if (avis.getMotivationAvis() != null) {
            idSelectedMotiv = avis.getMotivationAvis().getId();
            // on memorise la motivation correspondante
            setSelectedMotivation();
        }
        if (selectedTypeDec != null
                && selectedTypeDec.getCodeTypeConvocation()
                .equals(listeComplementaire.getCode()))
            isUsingLC = true;
        if (selectedTypeDec != null
                && selectedTypeDec.getCodeTypeConvocation()
                .equals(refused.getCode()))
            isUsingDEF = true;
        actionEnum.setWhatAction(ActionEnum.UPDATE_ACTION);
    }

	/*
	 ******************* METHODS ********************** */

    /**
     * Set the motivation after select one in the list.
     */
    public void setSelectedMotivation() {
        selectedMotivation = null;
        for (NomenclaturePojo nomP : nomenclatureController.getMotivationsAvis()) {
            if (nomP.getNomenclature().getId().equals(idSelectedMotiv)) {
                selectedMotivation = nomP;
            }
        }
    }


    /**
     * Save pour tous les individus coches le type de decision selectionne.
     */
    public void saveAll() {
        if (selectedTypeDec == null)
            addInfoMessage(null, "AVIS.INFO.TYP_DEC.NOT_SELECT");
        else {
            //Stockage des indVoeuPojo en erreur pour les reediter dans le formulaire
            Set<IndVoeuPojo> voeuxInError = new HashSet<IndVoeuPojo>();
            mapTestRang.clear();
            Boolean isRefused = selectedTypeDec.getIsFinal()
                    && selectedTypeDec.getCodeTypeConvocation().equals(refused.getCode());
//TODO: Boucle "if" est tout le temp � false.
//			if (allChecked && false) {
//				for (IndividuPojo ind : individuPaginator.getIndPojosWithWishForOneCmi()) {
//					for (IndVoeuPojo iPojo : ind.getIndVoeuxPojo()) {
//						iPojo.getNewAvis().setResult(selectedTypeDec);
//						Boolean goodAdd = add(iPojo.getIndVoeu(), iPojo.getNewAvis());
//						if (!goodAdd && isRefused) {
//							voeuxInError.add(iPojo);
//						}
//					}
//				}
//			} else {
            Map<Integer, IndVoeuPojo> mapIndVoeuPojoNewAvis = new HashMap<Integer, IndVoeuPojo>();
            //R�cup�ration de tous les nouveaus avis
            for (List<IndVoeuPojo> li : wishSelected.values()) {
                for (IndVoeuPojo iPojo : li)
                    mapIndVoeuPojoNewAvis.put(iPojo.getIndVoeu().getId(), iPojo);
            }
            //Ajout des nouveaux avis
//			for (Integer idIndVoeu : mapIndVoeuPojoNewAvis.keySet()) {
            for (Map.Entry<Integer, IndVoeuPojo> idIndVoeu : mapIndVoeuPojoNewAvis.entrySet()) {
                IndVoeuPojo iVoeuPojo = idIndVoeu.getValue();
                iVoeuPojo.getNewAvis().setResult(selectedTypeDec);
                Boolean goodAdd = add(iVoeuPojo.getIndVoeu(),
                        iVoeuPojo.getNewAvis(), mapIndVoeuPojoNewAvis);
                if (!goodAdd && isRefused) {
                    voeuxInError.add(iVoeuPojo);
                }
            }
//				for (Object o : wishSelected) {
//					IndVoeuPojo iPojo = (IndVoeuPojo) o;
//					iPojo.getNewAvis().setResult(selectedTypeDec);
//					Boolean goodAdd = add(iPojo.getIndVoeu(), iPojo.getNewAvis(), mapNewAvis);
//					if (!goodAdd && isRefused) {
//						voeuxInError.add(iPojo);
//					}
//				}
//			}

            // on memorise selectedTypeDec
            TypeDecision saveTypeDec = selectedTypeDec;
            reset();
            if (isRefused) {
                // rechargement des indVoeuPojo en erreur
                // pour les cases e cocher
                for (IndVoeuPojo iPojo : voeuxInError) {
                    Individu ind = iPojo.getIndVoeu().getIndividu();
                    List<IndVoeuPojo> li = wishSelected.get(ind);
                    if (li == null) li = new ArrayList<IndVoeuPojo>();
                    li.add(iPojo);
                    wishSelected.put(ind, li);
                }
                // recharge des voeux en erreur pour nouvelle saisie
                setVoeuxInErrorInPaginator(voeuxInError);
            }

            // apres le reset, on recupere selectedTypeDec
            selectedTypeDec = saveTypeDec;
            selectTypeDecision();
            if (log.isDebugEnabled()) {
                log.debug("leaving saveAll");
            }
            //7468 Pbs saisie résultat résultat défavorable (oubli de la motivation)
            wishSelected = new HashMap<Individu, List<IndVoeuPojo>>();
        }
    }

    public void checkAll() {
        if (allChecked) {
            for (IndividuPojo ind : indPojoLDM.getData()) {
                wishSelected.put(ind.getIndividu(), new ArrayList<IndVoeuPojo>(ind.getIndVoeuxPojo()));
            }
        } else {
            wishSelected.clear();
        }
    }

    private void setVoeuxInErrorInPaginator(final Set<IndVoeuPojo> voeuxInError) {
        for (IndividuPojo ind : indPojoLDM.getData())
            for (IndVoeuPojo iPojo : ind.getIndVoeuxPojo())
                for (IndVoeuPojo iPojoError : voeuxInError)
                    if (iPojoError.equals(iPojo))
                        iPojo.setNewAvis(iPojoError.getNewAvis());
    }

    /**
     * Methode d'ajout utilisee sur la page d'historique.
     */
    public void add() {
        if (log.isDebugEnabled()) {
            log.debug("entering add");
        }
        mapTestRang.clear();
        avis.setResult(selectedTypeDec);
        if (selectedMotivation != null) {
            avis.setMotivationAvis((MotivationAvis) selectedMotivation.getNomenclature());
        }
        if (add(indVoeuxPojo.getIndVoeu(), avis, null)) {
            //reinit

            actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
        }
    }

    /**
     * Update one wish : add an opinion.
     */
    private Boolean add(final IndVoeu indVoeu, final Avis a,
                        final Map<Integer, IndVoeuPojo> mapIndVoeuPojoNewAvis) {
        if (log.isDebugEnabled()) {
            log.debug("entering add with indVoeu = " + indVoeu + " and avis = " + a);
        }

        if (ctrlAdd(a.getResult(), a, indVoeu, mapIndVoeuPojoNewAvis)) {

            // mise hors service des autres avis
            Set<Avis> listAvis = indVoeu.getAvis();
            for (Avis av : listAvis) {
                av.setTemoinEnService(false);
                Avis avi = getDomainService().update(
                        av, getCurrentGest().getLogin());
                getDomainService().updateAvis(avi);
            }
            // recuperation du voeu
            a.setIndVoeu(indVoeu);
            // recuperation du type de decision

            // mise en service de l'avis
            a.setTemoinEnService(true);
            Avis av = getDomainService().add(
                    a, getCurrentGest().getLogin());

            getDomainService().addAvis(av);
            //update state indVoeu
            if (!indVoeu.getState().equals(EtatArriveComplet.I18N_STATE)) {
                if (av.getResult().getCodeTypeConvocation().equals(inscriptionAdm.getCode())) {
                    indVoeu.setState(EtatArriveComplet.I18N_STATE);
                }
                indVoeu.getAvis().add(av);
                getDomainService().updateIndVoeu(indVoeu);
            }

            addInfoMessage(null, "INFO.ENTER.SUCCESS");
            return true;
        }
        return false;
    }

    /**
     * Update avis.
     */
    public void update() {
        mapTestRang.clear();
        getDomainService().initOneProxyHib(avis, avis.getIndVoeu(), IndVoeu.class);
        if (ctrlUpdate(avis.getResult(), avis, avis.getIndVoeu(), null)) {
            if (selectedMotivation != null) {
                avis.setMotivationAvis((MotivationAvis) selectedMotivation.getNomenclature());
            }
            // devalidation automatique de l'avis
            avis.setValidation(false);
            Avis a = getDomainService().update(
                    avis, getCurrentGest().getLogin());
            getDomainService().updateAvis(a);
            addInfoMessage(null, "INFO.ENTER.SUCCESS");
            //reinit
            actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
        }
    }

    /**
     * Method to the choice the proposition.
     */
    public void addProposition() {
        final IndividuPaginator individuPaginator = individuController.getIndividuPaginator();
        if (individuPaginator.getIndRechPojo().getIdCmi() != null) {
            Commission c = getParameterService().getCommission(
                    individuPaginator.getIndRechPojo().getIdCmi(), null);
            commissionController.initAllTraitementCmi(c);
        }
        actionEnum.setWhatAction(ActionEnum.PROPOSITION_ACTION);
    }

    /**
     * Save the selected propositions.
     * Create the voeux for each proposition with opinion Fav.
     */
    public void savePropositions() {

        TypeDecision fav = null;
        for (TypeDecision t : getParameterService().getTypeDecisions(true)) {
            if (t.getIsFinal() && t.getCodeTypeConvocation().equals(inscriptionAdm.getCode())) {
                fav = t;
                break;
            }
        }
        if (fav == null) {
            throw new ConfigException("l'avis favorable n'existe pas "
                    + " : ajout proposition annnulee in class " + getClass().getName());
        }

        // on récupère le régime FI
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        int codeRI = gest.getProfile().getCodeRI();
        RegimeInscription regime = getSessionController().getRegimeIns().get(codeRI);

        // récupération de la campagne
        Campagne campagne = getParameterService().getCampagneEnServ(regime.getCode());

        for (Object o : commissionController.getObjectToAdd()) {
            BeanTrtCmi b = (BeanTrtCmi) o;
            // on récupère le linkTrtCmiCamp
            LinkTrtCmiCamp linkTrtCmiCamp = getParameterService().
                    getLinkTrtCmiCamp(b.getTraitementCmi(), campagne);
            // création du voeu
            IndVoeu indV = new IndVoeu(linkTrtCmiCamp, indVoeuxPojo.getIndVoeu().getIndividu());
//			indV.setIndividu(indVoeuxPojo.getIndVoeu().getIndividu());
//			indV.setVersionEtpOpi(b.getTraitementCmi().getVersionEtpOpi());
            //TODO a faire avec le groupe
            indV.setCodTypeTrait(b.getTraitementCmi().getCodTypeTrait());
            indV.setState(EtatArriveComplet.I18N_STATE);
            indV.setHaveBeTraited(true);
            indV.setIsProp(true);

            IndVoeu i = getDomainService().add(indV, getCurrentGest().getLogin());
            getDomainService().addIndVoeu(i);

            Avis a = new Avis();
            a.setCommentaire("proposition de la commission");
            a.setIndVoeu(indV);
            a.setResult(fav);

            Avis av = getDomainService().add(a, getCurrentGest().getLogin());
            getDomainService().addAvis(av);

            indVoeuxPojo.setIndVoeu(indV);
        }

        commissionController.setObjectToAdd(new Object[0]);

        //updateIndVoeuPojo

        addInfoMessage(null, "AVIS.INFO.ADD_PROPOSITION");
        actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
    }

    /**
     * make the list etape for the commission selected.
     */
    public void selectCmiForProposition() {
        commissionController.initAllTraitementCmi(
                getParameterService().getCommission(
                        commissionController.getCommission().getId(), null));
    }

    /**
     * The selected domain.
     *
     * @param event
     */
    public void selectTypeDecision(final ValueChangeEvent event) {
        selectedTypeDec = (TypeDecision) event.getNewValue();
        selectTypeDecision();
        FacesContext.getCurrentInstance().renderResponse();
    }

    /**
     * Methode appelee lors de la selection d'un type de decision.
     * pour un individu
     */
    public void selectTypeDecisionIndividu() {
        for (IndividuPojo iPojo : indPojoLDM.getData())
            for (IndVoeuPojo ivPojo : iPojo.getIndVoeuxPojo()) {
                TypeDecision newDec = ivPojo.getNewAvis().getResult();
                if (newDec != null
                        && newDec.getCodeTypeConvocation()
                        .equals(preSelection.getCode())) {
                    //charge le commentaire
                    TraitementCmi t = ivPojo.getIndVoeu()
                            .getLinkTrtCmiCamp().getTraitementCmi();
                    if (t.getSelection() != null)
                        ivPojo.getNewAvis()
                                .setCommentaire(t.getSelection().getComment());
                    isUsingPreselect = true;
                } else if (newDec != null
                        && newDec.getCodeTypeConvocation()
                        .equals(listeComplementaire.getCode())) {
                    ivPojo.setIsUsingLC(true);
                    ivPojo.setIsUsingDEF(false);
                    isUsingLC = true;
                } else if (newDec != null
                        && newDec.getCodeTypeConvocation()
                        .equals(refused.getCode()) && newDec.getIsFinal()) {
                    ivPojo.setIsUsingDEF(true);
                    ivPojo.setIsUsingLC(false);
                    isUsingDEF = true;
                }

            }
    }

    /**
     * Methode appelee lors de la selection d'un type de decision.
     * pour un avis
     */
    public void selectTypeDecision() {
        isUsingPreselect = selectedTypeDec != null &&
                selectedTypeDec.getCodeTypeConvocation().equals(preSelection.getCode());
        if (isUsingPreselect && indVoeuxPojo != null) {
            //charge le commentaire
            TraitementCmi t = indVoeuxPojo.getIndVoeu().getLinkTrtCmiCamp().getTraitementCmi();
            if (t.getSelection() != null)
                avis.setCommentaire(t.getSelection().getComment());
        }

        isUsingLC = selectedTypeDec != null &&
                selectedTypeDec.getCodeTypeConvocation().equals(listeComplementaire.getCode());

        isUsingDEF = selectedTypeDec != null &&
                selectedTypeDec.getCodeTypeConvocation().equals(refused.getCode()) &&
                selectedTypeDec.getIsFinal();
    }


    /**
     * Search method to list the students with the filter.
     * <p/>
     * TODO : Rename that method
     */
    public void searchStudents() {
        final IndividuPaginator individuPaginator = individuController.getIndividuPaginator();
        List<TypeDecision> types = individuPaginator.getIndRechPojo().getTypesDec();
        Integer codeCommRech = individuPaginator.getIndRechPojo().getIdCmi();
        Integer codeTrtCmiRech = individuPaginator.getIndRechPojo().getCodeTrtCmiRecherchee();
        if (!types.isEmpty() && types.get(0).getCode().equals(listeComplementaire.getCode())
                && (codeCommRech == null || codeTrtCmiRech == null)) {
            addInfoMessage(null, "AVIS.INFO.LISTE_COMP");
        }
    }

    /**
     * Search method to list the students with the filter by type of decision.
     * <p/>
     * TODO : Rename that method
     */
    public void searchStudentsByTypeDec() {
        final IndividuPaginator individuPaginator = individuController.getIndividuPaginator();
        individuPaginator.getIndRechPojo().setCodeTrtCmiRecherchee(null);
        searchStudents();
        List<TypeDecision> types = individuPaginator.getIndRechPojo().getTypesDec();
        Integer codeCommRech = individuPaginator.getIndRechPojo().getIdCmi();
        if (!types.isEmpty() && types.get(0).getCode().equals(listeComplementaire.getCode())
                && codeCommRech != null) {
            commissionController.initAllTraitementCmi(
                    getParameterService().getCommission(codeCommRech, null));
        }
    }

    /**
     * Search method to list the students with the filter by commission.
     * <p/>
     * TODO : Rename that method
     */
    public void searchStudentsByComm() {
        final IndividuPaginator individuPaginator = individuController.getIndividuPaginator();
        individuPaginator.getIndRechPojo().setCodeTrtCmiRecherchee(null);
        searchStudents();
        List<TypeDecision> types = individuPaginator.getIndRechPojo().getTypesDec();
        Integer codeCommRech = individuPaginator.getIndRechPojo().getIdCmi();
        if (!types.isEmpty() && types.get(0).getCode().equals(listeComplementaire.getCode())
                && codeCommRech != null) {
            commissionController.initAllTraitementCmi(
                    getParameterService().getCommission(codeCommRech, null));
        }
    }

    /**
     * Search method to list the students with the filter by etape.
     * <p/>
     * TODO : get rid of that method
     */
    public void searchStudentsByEtp() {
        searchStudents();
    }

    /**
     * Control the add of an opinion.
     *
     * @param tdec
     * @param a
     * @return Boolean
     */
    private Boolean ctrlAdd(final TypeDecision tdec, final Avis a,
                            final IndVoeu voeu, final Map<Integer, IndVoeuPojo> mapIndVoeuPojoNewAvis) {
        Boolean ctrlOk = true;
        if (tdec == null) {
            addErrorMessage(null, getString("AVIS.ERROR.IN_SERVICE"));
            ctrlOk = false;
        }
        if (tdec != null && tdec.getIsFinal() && tdec.getCodeTypeConvocation().equals(refused.getCode())) {
            //the motivation is obligatory
            if (a.getMotivationAvis() == null) {
                addErrorMessage(null, "ERROR.FIELD.EMPTY.MOTIVATION", tdec.getLibelle());
                ctrlOk = false;
            }
        }

        if (tdec != null && tdec.getCodeTypeConvocation().equals(listeComplementaire.getCode())) {
            //the rang is obligatory
            //TODO le rang doit etre unique pour une VET.
            if (a.getRang() == null) {
                addErrorMessage(null, "ERROR.FIELD.EMPTY.RANG", tdec.getLibelle());
                ctrlOk = false;
            } else if (isExistRangForCodEtp(
                    voeu.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi(),
                    a.getRang(), a)) {
                //Le rang exite d�j�
                if (mapIndVoeuPojoNewAvis != null) {
                    addErrorMessage(null, "ERROR.FIELD.EXIST.RANG",
                            a.getRang(),
                            mapIndVoeuPojoNewAvis.get(voeu.getId()).getVrsEtape().getLibWebVet());
                } else {
                    VersionEtpOpi vet = voeu.getLinkTrtCmiCamp()
                            .getTraitementCmi().getVersionEtpOpi();
                    addErrorMessage(null, "ERROR.FIELD.EXIST.RANG",
                            a.getRang(),
                            getDomainApoService().getVersionEtape(
                                    vet.getCodEtp(), vet.getCodVrsVet()).getLibWebVet());
                }
                ctrlOk = false;
            } else if (mapIndVoeuPojoNewAvis != null) {
//				for (Integer idVoeuPojo : mapIndVoeuPojoNewAvis.keySet()) {
                for (Map.Entry<Integer, IndVoeuPojo> idIndVoeu : mapIndVoeuPojoNewAvis.entrySet()) {
                    IndVoeuPojo voeuPojo = idIndVoeu.getValue();
                    VersionEtpOpi etpVoeuNewAvis = voeu.getLinkTrtCmiCamp().
                            getTraitementCmi().getVersionEtpOpi();
                    boolean voeuDif = voeuPojo.getIndVoeu().getId().intValue()
                            != voeu.getId().intValue();
                    boolean identicEtp = voeuPojo.getVrsEtape().getCodEtp().equals(
                            etpVoeuNewAvis.getCodEtp())
                            && voeuPojo.getVrsEtape().getCodVrsVet().intValue()
                            == etpVoeuNewAvis.getCodVrsVet().intValue();
                    if (voeuPojo.getNewAvis().getRang().intValue() == a.getRang().intValue()
                            && voeuDif && identicEtp) {
                        //Deux rang sont identique dans les nouveaux avis
                        addErrorMessage(null, "ERROR.FIELD.IDENTIC.RANG",
                                a.getRang(),
                                mapIndVoeuPojoNewAvis.get(voeu.getId())
                                        .getVrsEtape().getLibWebVet());
                        ctrlOk = false;
                    }
                }
            }
        }
        //		if (selectMotiv.equals(MOTIV) && selectedMotivation == null) {
        //			addErrorMessage(null, Constantes.I18N_EMPTY, getString("AVIS.MOTIVATION"));
        //			ctrlOk = false;
        //		} else {
        //			if (selectMotiv.equals(COMMENTAIRE) && !StringUtils.hasText(avis.getCommentaire())) {
        //				addErrorMessage(null, Constantes.I18N_EMPTY, getString("AVIS.COMMENTAIRE"));
        //				ctrlOk = false;
        //			}
        //		}
        return ctrlOk;
    }

    /**
     * Control the update of an opinion.
     *
     * @return Boolean
     */
    private Boolean ctrlUpdate(final TypeDecision tdec, final Avis a,
                               final IndVoeu voeu, final Map<Integer, IndVoeuPojo> mapIndVoeuPojoNewAvis) {
        Boolean ctrlOk = ctrlAdd(tdec, a, voeu, mapIndVoeuPojoNewAvis);
        if (!avis.getTemoinEnService()) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("AVIS.MOTIVATION"));
            ctrlOk = false;
        }
        return ctrlOk;
    }

    public List<Integer> getListRangForCodEtp(final VersionEtpOpi vet, final Avis avisUpdate) {
        if (!mapTestRang.containsKey(vet)) {
            List<Integer> listRangAvis = new ArrayList<Integer>();
            for (Avis avis : getDomainService().getAvisByEtp(vet.getCodEtp(), vet.getCodVrsVet())) {
                boolean avisNotValid = avis.getResult().getCodeTypeConvocation().equals(
                        listeComplementaire.getCode())
                        && (avisUpdate.getId().intValue() != avis.getId().intValue());
                if (avis.getTemoinEnService() && avis.getIndVoeu().getTemoinEnService()
                        && avis.getRang() != null && avisNotValid) {
                    listRangAvis.add(avis.getRang());
                }
            }
            mapTestRang.put(vet, listRangAvis);

            return listRangAvis;
        }

        return mapTestRang.get(vet);
    }

    public boolean isExistRangForCodEtp(final VersionEtpOpi vet, final int rang, final Avis avisUpdate) {
        for (int r : getListRangForCodEtp(vet, avisUpdate)) {
            if (r == rang) {
                return true;
            }
        }

        return false;
    }

    /**
     * @return true si on a choisi de filtrer sur les LC et
     *         si on a selectionne une commission
     *         false sinon
     */
    public Boolean getIsFilterLCAndCommissionOK() {
        final IndividuPaginator individuPaginator = individuController.getIndividuPaginator();
        final List<TypeDecision> types = individuPaginator.getIndRechPojo().getTypesDec();
        final Integer codeCommRech = individuPaginator.getIndRechPojo().getIdCmi();
        return !types.isEmpty() &&
                types.get(0).getCode().equals(listeComplementaire.getCode()) &&
                codeCommRech != null;
    }

    /**
     * @return la liste des etapes de la commission
     *         si on a choisi de filtrer sur les LC et si on a selectionne une commission
     *         null sinon
     */
    public Set<VersionEtapeDTO> getVersionsEtapeForLCAndCommission() {
        Set<VersionEtapeDTO> vetComm = new TreeSet<VersionEtapeDTO>(
                new ComparatorString(VersionEtapeDTO.class));
        final IndividuPaginator individuPaginator = individuController.getIndividuPaginator();
        final List<TypeDecision> types = individuPaginator.getIndRechPojo().getTypesDec();
        final Integer codeCommRech = individuPaginator.getIndRechPojo().getIdCmi();
        if (!types.isEmpty() && types.get(0).getCode().equals(listeComplementaire.getCode())
                && codeCommRech != null) {
            Commission comm = getParameterService().getCommission(codeCommRech, null);
            for (TraitementCmi trait : comm.getTraitementCmi()) {
                VersionEtpOpi vetOPI = trait.getVersionEtpOpi();
                VersionEtapeDTO vetDTO = getDomainApoService().getVersionEtape(
                        vetOPI.getCodEtp(), vetOPI.getCodVrsVet());
                vetComm.add(vetDTO);
            }
            return vetComm;
        }
        return null;
    }

    /**
     * @return the listAvisPojo
     */
    public List<AvisPojo> getListAvisPojo() {
        List<Avis> listAvis = getDomainService().getAvis(indVoeuxPojo.getIndVoeu());
        listAvisPojo = new ArrayList<AvisPojo>();
        for (Avis a : listAvis) {
            listAvisPojo.add(new AvisPojo(a));
            if (a.getResult().getCodeTypeConvocation()
                    .equals(listeComplementaire.getCode())) {
                indVoeuxPojo.setIsUsingLC(true);
            }
        }
        Collections.sort(listAvisPojo, new ComparatorDate(AvisPojo.class));
        return listAvisPojo;
    }


    /**
     * @return the actionEnum
     */
    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    /**
     * @param actionEnum the actionEnum to set
     */
    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }


    /**
     * @return the wayfEnum
     */
    public WayfEnum getWayfEnum() {
        return wayfEnum;
    }

    /**
     * @param wayfEnum the wayfEnum to set
     */
    public void setWayfEnum(final WayfEnum wayfEnum) {
        this.wayfEnum = wayfEnum;
    }

    /**
     * @return the indVoeuxPojo
     */
    public IndVoeuPojo getIndVoeuxPojo() {
        return indVoeuxPojo;
    }

    /**
     * @param indVoeuxPojo the indVoeuxPojo to set
     */
    public void setIndVoeuxPojo(final IndVoeuPojo indVoeuxPojo) {
        this.indVoeuxPojo = indVoeuxPojo;
    }

    /**
     * @return the avis
     */
    public Avis getAvis() {
        return avis;
    }

    /**
     * @param avis the avis to set
     */
    public void setAvis(final Avis avis) {
        this.avis = avis;
    }

    /**
     * @return the selectedTypeDec
     */
    public TypeDecision getSelectedTypeDec() {
        return selectedTypeDec;
    }

    /**
     * @param selectedTypeDec the selectedTypeDec to set
     */
    public void setSelectedTypeDec(final TypeDecision selectedTypeDec) {
        this.selectedTypeDec = selectedTypeDec;
    }

    /**
     * @return the idSelectedMotiv
     */
    public Integer getIdSelectedMotiv() {
        return idSelectedMotiv;
    }

    /**
     * @param idSelectedMotiv the idSelectedMotiv to set
     */
    public void setIdSelectedMotiv(final Integer idSelectedMotiv) {
        this.idSelectedMotiv = idSelectedMotiv;
    }

    /**
     * @return the selectedMotivation
     */
    public NomenclaturePojo getSelectedMotivation() {
        return selectedMotivation;
    }

    /**
     * @return the idSelectedComm
     */
    public Integer getIdSelectedComm() {
        return idSelectedComm;
    }

    /**
     * @param idSelectedComm the idSelectedComm to set
     */
    public void setIdSelectedComm(final Integer idSelectedComm) {
        this.idSelectedComm = idSelectedComm;
    }

    /**
     * @return the nomenclatureController
     */
    public NomenclatureController getNomenclatureController() {
        return nomenclatureController;
    }

    /**
     * @param nomenclature the nomenclatureController to set
     */
    public void setNomenclatureController(final NomenclatureController nomenclature) {
        this.nomenclatureController = nomenclature;
    }

    /**
     * @return the commissionController
     */
    public CommissionController getCommissionController() {
        return commissionController;
    }

    /**
     * @param commissionController the commissionController to set
     */
    public void setCommissionController(final CommissionController commissionController) {
        this.commissionController = commissionController;
    }

    public IndividuController getIndividuController() {
        return individuController;
    }

    public void setIndividuController(IndividuController individuController) {
        this.individuController = individuController;
    }

    /**
     * FIXME : Or not ? A hack to comply with jsf
     *
     * @return the wishSelected
     */
    public Set<Integer> getWishSelected() {
        Set<Integer> svp = new HashSet<Integer>();
        for (List<IndVoeuPojo> li : wishSelected.values())
            for (IndVoeuPojo ivp : li)
                svp.add(ivp.getIndVoeu().getId());
        return svp;
    }

    /**
     * FIXME: Or not ? A hack to comply with jsf
     */
    public void setWishSelected(final Set<Integer> wishesIds) {
        for (IndividuPojo indP : indPojoLDM.getData())
            for (IndVoeuPojo ivp : indP.getIndVoeuxPojo())
                for (Integer id : wishesIds)
                    if (id.equals(ivp.getIndVoeu().getId())) {
                        Individu ind = ivp.getIndVoeu().getIndividu();
                        List<IndVoeuPojo> vals = wishSelected.get(ind);
                        if (vals == null) vals = new ArrayList<IndVoeuPojo>();
                        vals.add(ivp);
                        wishSelected.put(ind, vals);
                    }
    }

    /**
     * @return the allChecked
     */
    public Boolean getAllChecked() {
        return allChecked;
    }

    /**
     * @param allChecked the allChecked to set
     */
    public void setAllChecked(final Boolean allChecked) {
        this.allChecked = allChecked;
    }

    /**
     * @param refused the refused to set
     */
    public void setRefused(final Refused refused) {
        this.refused = refused;
    }

    /**
     * @param preSelection the preSelection to set
     */
    public void setPreSelection(final Preselection preSelection) {
        this.preSelection = preSelection;
    }

    /**
     * @param listeComplementaire the listeComplementaire to set
     */
    public void setListeComplementaire(final ListeComplementaire listeComplementaire) {
        this.listeComplementaire = listeComplementaire;
    }

    /**
     * @param inscriptionAdm the inscriptionAdm to set
     */
    public void setInscriptionAdm(final InscriptionAdm inscriptionAdm) {
        this.inscriptionAdm = inscriptionAdm;
    }

    public LazyDataModel<IndividuPojo> getIndPojoLDM() {
        return indPojoLDM;
    }

    public boolean isRenderTable() {
        return renderTable;
    }

    public void doRenderTable() {
        this.renderTable = true;
    }

    /**
     * @return the isUsingLC
     */
    public Boolean getIsUsingLC() {
        return isUsingLC;
    }

    /**
     * @param isUsingLC the isUsingLC to set
     */
    public void setIsUsingLC(final Boolean isUsingLC) {
        this.isUsingLC = isUsingLC;
    }

    /**
     * @return the isUsingDEF
     */
    public Boolean getIsUsingDEF() {
        return isUsingDEF;
    }

    /**
     * @param isUsingDEF the isUsingDEF to set
     */
    public void setIsUsingDEF(final Boolean isUsingDEF) {
        this.isUsingDEF = isUsingDEF;
    }

    /**
     * @return the isUsingPreselect
     */
    public Boolean getIsUsingPreselect() {
        return isUsingPreselect;
    }

    /**
     * @param isUsingPreselect the isUsingPreselect to set
     */
    public void setIsUsingPreselect(final Boolean isUsingPreselect) {
        this.isUsingPreselect = isUsingPreselect;
    }

}

