/**
 *
 */
package org.esupportail.opi.web.controllers.references;

import fj.data.Option;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.BeanTrtCmi;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;

import javax.faces.event.ValueChangeEvent;
import java.util.*;

import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.EnAttente;

public class TrtCmiController extends AbstractAccessController {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -385061645426193790L;

    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

    /**
     * The action.
     */
    private ActionEnum actionEnum;

    /**
     * The code to the research.
     */
    private String codeCmi;

    /**
     * The id to the research.
     */
    private Integer idCmi;

    /**
     * The selected commission.
     */
    private Commission commission;

    /**
     * Select all the type treatment for the TrtCmi.
     */
    private String codeTypeTrtselected;

    /**
     * The TraitementCmi to add.
     */
    private BeanTrtCmi beanTrtCmi;

    /**
     * Link to delete.
     */
    private LinkTrtCmiCamp linkToDel;

    /**
     * The selected campagne.
     */
    private Campagne campagneSelected;

    /**
     * Liste des campagnes pour le régime.
     */
    private List<Campagne> listeCampagne;

    /**
     * The current regime.
     */
    private RegimeInscription currentRegime;

    /**
     * Liste des traitementsCmi en service de la commission .
     */
    private List<BeanTrtCmi> allTraitementCmi;

    /**
     * Liste des traitementsCmi hors service de la commission .
     */
    private List<BeanTrtCmi> treatmentsCmiOff;

    /**
     * Liste des VET à transférer sur la commission.
     */
    private List<VersionEtapeDTO> listVETToTransfert;

    /**
     * VET to transfert.
     */
    private VersionEtapeDTO vetToTransfert;

    /**
     * true if the gestionnaire valide the transfert.
     * false otherwise
     */
    private boolean transfertVet;

    /**
     * see {@link EtapeController}.
     */
    private EtapeController etapeController;
    private BeanTrtCmi beanTrtCmi1;

    public TrtCmiController() {
        super();
    }

    @Override
    public void reset() {
        super.reset();
        etapeController.reset();
        idCmi = null;
        codeCmi = null;
        allTraitementCmi = new ArrayList<BeanTrtCmi>();
        treatmentsCmiOff = new ArrayList<BeanTrtCmi>();
        listVETToTransfert = new ArrayList<VersionEtapeDTO>();
        codeTypeTrtselected = null;
        commission = null;
        beanTrtCmi = new BeanTrtCmi();
        currentRegime = null;
        this.actionEnum = new ActionEnum();
    }

    @Override
    public void afterPropertiesSetInternal() {
        Assert.notNull(this.etapeController,
                "property etapeController of class " + this.getClass().getName()
                        + " can not be null");
        reset();
    }

    /**
     * @return String
     */
    public String goMangedTrtCmi() {
        if (log.isDebugEnabled()) {
            log.debug("entering goMangedTrtCmi");
        }
        reset();
        return NavigationRulesConst.MANAGED_TRT_CMI;
    }

    /**
     * @return String
     */
    public String goBackManagedTrtCmi() {
        if (log.isDebugEnabled()) {
            log.debug("entering goBackManagedTrtCmi");
        }
        actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
        commission = getParameterService().getCommission(commission.getId(), null);
        initAllTraitementCmi(commission, null);
        return NavigationRulesConst.MANAGED_TRT_CMI;
    }

    /**
     * @return String
     */
    public String managedCamp() {
        actionEnum.setWhatAction(ActionEnum.READ_ACTION);
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        String codeRI = String.valueOf(gest.getProfile().getCodeRI());
        listeCampagne = new ArrayList<Campagne>(getParameterService().getCampagnes(true, codeRI));
        Collections.sort(listeCampagne, new ComparatorString(Campagne.class));
        return NavigationRulesConst.MANAGED_CAMP_TO_VET;
    }

    /**
     * method to look for commission.
     */
    public void lookForCmi() {
        Commission c = null;
        if (idCmi != null) {
            c = getParameterService().getCommission(idCmi, codeCmi);
        }

        if (c == null) {
            addErrorMessage(null, "COMMISSION.NOT_FOUND");
            reset();
        } else {
            commission = c;
            initAllTraitementCmi(c, null);
        }
    }

    /**
     * Add the members in objetToAdd in membersToDisplay.
     *
     * @return String
     */
    public String addEtapes() {

        if (etapeController.getObjectToAdd().length > 0) {
            for (Object o : etapeController.getObjectToAdd()) {
                VersionEtapeDTO v = (VersionEtapeDTO) o;
                TraitementCmi t = getParameterService().getTraitementCmi(new VersionEtpOpi(v), false);
                if (t == null || !getParameterService().isConnectCmi(t)) {
                    TraitementCmi trtCmi = new TraitementCmi(etapeController.getCodCge(), v);
                    trtCmi.getLinkTrtCmiCamp().add(new LinkTrtCmiCamp(
                            getParameterService().getCampagneEnServ(
                                    getCurrentGest().getProfile().getCodeRI())
                            , trtCmi));
//                    BeanTrtCmi b = new BeanTrtCmi(trtCmi, TypeTraitement.fromCode(trtCmi.getCodTypeTrait()));
                    BeanTrtCmi b = new BeanTrtCmi(trtCmi, Option.<TypeTraitement>none());
                    b = prepareTrtCmi(b);
//                    b = beanTrtCmi1;
                    allTraitementCmi.add(b);
                } else {
                    listVETToTransfert.add(v);
                }
            }
        } else if (etapeController.getAllChecked()) {
            for (VersionEtapeDTO v : etapeController.getEtapes()) {
                TraitementCmi t = getParameterService().getTraitementCmi(new VersionEtpOpi(v), false);
                if (t == null || !getParameterService().isConnectCmi(t)) {
                    TraitementCmi trtCmi = new TraitementCmi(etapeController.getCodCge(), v);
                    trtCmi.getLinkTrtCmiCamp().add(new LinkTrtCmiCamp(
                            getParameterService().getCampagneEnServ(
                                    getCurrentGest().getProfile().getCodeRI())
                            , trtCmi));
//                    BeanTrtCmi b = new BeanTrtCmi(trtCmi, TypeTraitement.fromCode(trtCmi.getCodTypeTrait()));
                    BeanTrtCmi b = new BeanTrtCmi(trtCmi, Option.<TypeTraitement>none());
                    b = prepareTrtCmi(b);

                    allTraitementCmi.add(b);
                } else {
                    listVETToTransfert.add(v);
                }
            }
        }

        return NavigationRulesConst.MANAGED_TRT_CMI;
    }

    /**
     * Transfert the VET to the commission.
     */
    public void transfertVetToComm() {
        // si on transfert, on change la commission de la vet
        if (transfertVet) {
            TraitementCmi t = getParameterService().
                    getTraitementCmi(new VersionEtpOpi(vetToTransfert), false);
            t.setCommission(commission);
            getParameterService().updateTraitementCmi(t);
            commission.getTraitementCmi().add(t);
            initAllTraitementCmi(commission, null);
        }
        listVETToTransfert.remove(vetToTransfert);
    }

    /**
     * Initialize the allTraitmentCmi(the treatment of commission) attribute.
     */
    public void initAllTraitementCmi(final Commission c, final Boolean inUse) {
        if (log.isDebugEnabled()) {
            log.debug("entering initAllTraitementCmi( " + c + " )");
        }
        allTraitementCmi = new ArrayList<>();
        treatmentsCmiOff = new ArrayList<>();
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        int codeRI = gest.getProfile().getCodeRI();
        // maj du régime d'inscription
        currentRegime = getRegimeIns().get(codeRI);
        // Avoid a hibernate LazyInitException
        Commission com = getParameterService().getCommission(c.getId(), c.getCode());

        if (com != null) {
            for (TraitementCmi t : com.getTraitementCmi()) {
                if ((inUse == null || !inUse) && Utilitaires.isTraitementCmiOff(t, codeRI)) {
                    BeanTrtCmi b = new BeanTrtCmi(t,  Option.some(TypeTraitement.fromCode(t.getCodTypeTrait())));
                    treatmentsCmiOff.add(prepareTrtCmi(b));
                }
                if ((inUse == null || inUse) && !Utilitaires.isTraitementCmiOff(t, codeRI)) {
                    BeanTrtCmi b = new BeanTrtCmi(t, Option.some(TypeTraitement.fromCode(t.getCodTypeTrait())));
                    allTraitementCmi.add(prepareTrtCmi(b));
                }
            }
        }
    }


    /**
     * Prepare le Bean trtCmi : Charge le centre de gestion, l'etape, etc.
     *
     * @param bt
     * @return BeanTrtCmi
     */
    private BeanTrtCmi prepareTrtCmi(final BeanTrtCmi bt) {
        bt.setEtape(
                getDomainApoService().getVersionEtape(
                        bt.getTraitementCmi().getVersionEtpOpi().getCodEtp(),
                        bt.getTraitementCmi().getVersionEtpOpi().getCodVrsVet()));
        return bt;
    }

    /**
     * Remove traitementCmi in allTraitementCmi.
     */
    public void removeTrtCmi() {
        if (log.isDebugEnabled()) {
            log.debug("entering removeTrtCmi with beanTrtCmi = " + beanTrtCmi);
        }
        allTraitementCmi.remove(beanTrtCmi);
    }

    public void removeLinkTrtCmiCamp() {
        if (log.isDebugEnabled()) {
            log.debug("entering removeLinkTrtCmiCamp with linkTrtCmiCamp = " + linkToDel);
        }

        //init proxy hib
        getDomainService().initOneProxyHib(linkToDel, linkToDel.getVoeux(), Set.class);
        if (linkToDel.getVoeux().isEmpty()) {
            getParameterService().deleteLinkTrtCmiCamp(linkToDel);
        } else {
            linkToDel.setTemoinEnService(false);
            getParameterService().updateLinkTrtCmiCamp(linkToDel);
        }
        // si la liste des link est vide, on supprime le traitement Cmi
        List<TraitementCmi> trtToDel = new ArrayList<TraitementCmi>();
        for (BeanTrtCmi b : allTraitementCmi) {
            TraitementCmi trt = getParameterService().getTraitementCmi(b.getTraitementCmi().getId());
            if (trt.getLinkTrtCmiCamp().contains(linkToDel)) {
                trt.getLinkTrtCmiCamp().remove(linkToDel);
            }
            if (trt.getLinkTrtCmiCamp().isEmpty()) {
                trtToDel.add(trt);
            }
        }
        getParameterService().deleteTraitementCmi(trtToDel);
        commission.getTraitementCmi().removeAll(trtToDel);
        initAllTraitementCmi(commission, null);
    }

    /**
     * The selected type treatment.
     *
     * @param event
     */
    public void selectTypTrt(final ValueChangeEvent event) {
        codeTypeTrtselected = (String) event.getNewValue();
        selectAllTypeTrt();
    }


    /**
     * Select all the type treatment for beanTrtCmi.
     */
    public void selectAllTypeTrt() {
        for (BeanTrtCmi b : allTraitementCmi)
            b.getTraitementCmi().setCodTypeTrait(codeTypeTrtselected);
    }

    /**
     * Update a Commission to the dataBase and managed the trtCmi.
     *
     * @return String
     */
    public String update() {
        if (log.isDebugEnabled()) {
            log.debug("enterind update");
        }

        commission = getParameterService().getCommission(commission.getId(), null);
        if (ctrlEnter(true)) {
            List<TraitementCmi> trtcmiToDelete = new ArrayList<TraitementCmi>();

            for (TraitementCmi t : commission.getTraitementCmi()) {
                BeanTrtCmi b = new BeanTrtCmi(t, Option.some(TypeTraitement.fromCode(t.getCodTypeTrait())));
                if (!allTraitementCmi.contains(b) && !treatmentsCmiOff.contains(b)) {
                    trtcmiToDelete.add(t);
                }
            }

            // récupération des campagnes en cours
            Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
            int codeRI = gest.getProfile().getCodeRI();
            Set<Campagne> campagnes = new HashSet<Campagne>();
            if (StringUtils.hasText(etapeController.getCodAnu()))
                campagnes.addAll(getParameterService().getCampagnesAnu(etapeController.getCodAnu()));
            // on utilise que les campagnes auquels à le droit le gestionnaire
            Set<Campagne> campagnesAdd = new HashSet<Campagne>();
            for (Campagne camp : campagnes) {
                if (camp.getCodeRI() == codeRI) {
                    campagnesAdd.add(camp);
                }
            }
            
            List<TraitementCmi> listTrtCmi = new ArrayList<TraitementCmi>();
            for (BeanTrtCmi b : allTraitementCmi)
                listTrtCmi.add(b.getTraitementCmi());
            listTrtCmi.removeAll(trtcmiToDelete);

            //update or add trtCmi
            for (TraitementCmi b : listTrtCmi) {
            	TraitementCmi trtcmi = getParameterService().getTraitementCmi(b.getId());
            	if (trtcmi == null) { //n'existe pas donc à ajouter
            		trtcmi = new TraitementCmi(b);
            		trtcmi.setLinkTrtCmiCamp(null);
					Campagne campFI = getParameterService().getCampagneEnServ(FormationInitiale.CODE);
					VersionDiplomeDTO vdiDTO = getDomainApoService().getVersionDiplomes(trtcmi.getVersionEtpOpi(), campFI);
					trtcmi.setCodDip(vdiDTO.getCodDip());
					trtcmi.setCodVrsDip(vdiDTO.getCodVrsVdi());
					trtcmi.setCommission(commission);
					trtcmi = getDomainService().add(trtcmi, getCurrentGest().getLogin());
					
					getParameterService().addTraitementCmi(trtcmi);

					// création du linkTrtCmiCamp pour chaque campagne
					for (Campagne camp : campagnesAdd) {
					    LinkTrtCmiCamp linkTrtCmiCamp = new LinkTrtCmiCamp();
					    linkTrtCmiCamp.setTraitementCmi(trtcmi);
					    linkTrtCmiCamp.setCampagne(camp);
					    linkTrtCmiCamp.setTemoinEnService(camp.getTemoinEnService());
					    // sauvegarde en base
					    getParameterService().addLinkTrtCmiCamp(linkTrtCmiCamp);
					}
            	} else { //existe donc à mettre à jour
					trtcmi.setCommission(commission);
					trtcmi = getDomainService().update(trtcmi, getCurrentGest().getLogin());
					getParameterService().updateTraitementCmi(trtcmi);
            	}
            }

            // on boucle sur la liste des trtCmi à supprimer
            // on teste sur chaque linkTrtCmiCamp :
            // - si il y a au moins un voeu, on le met HS
            // - sinon, on supprime le link
            // Si tous les link du trtCmi ont été supprimé, on supprime le trtCmi de la base.
            List<TraitementCmi> trtcmiToDeleteFinal = new ArrayList<TraitementCmi>();
            for (TraitementCmi trtCmi : trtcmiToDelete) {
                boolean deleteTrt = true;
                Set<LinkTrtCmiCamp> list = new HashSet<LinkTrtCmiCamp>(trtCmi.getLinkTrtCmiCamp());
                for (LinkTrtCmiCamp link : list) {
                    //init proxy hib
                    getDomainService().initOneProxyHib(link, link.getVoeux(), Set.class);
                    if (link.getVoeux().isEmpty()) {
                        trtCmi.getLinkTrtCmiCamp().remove(link);
                        getParameterService().deleteLinkTrtCmiCamp(link);
                        getParameterService().updateTraitementCmi(trtCmi);
                    } else {
                        link.setTemoinEnService(false);
                        getParameterService().updateLinkTrtCmiCamp(link);
                        deleteTrt = false;
                    }
                }
                if (deleteTrt) {
                	trtCmi.setLinkTrtCmiCamp(null);
                    trtcmiToDeleteFinal.add(trtCmi);
                } else {
                    VersionEtapeDTO v = getDomainApoService().getVersionEtape(
                            trtCmi.getVersionEtpOpi().getCodEtp(),
                            trtCmi.getVersionEtpOpi().getCodVrsVet());
                    addErrorMessage(null, "ERROR.TRT.CMI.EXIST_VOEU",
                            v.getCodEtp() + "_" + v.getCodVrsVet() + ":" + v.getLicEtp());
                    BeanTrtCmi b = new BeanTrtCmi(trtCmi, Option.some(TypeTraitement.fromCode(trtCmi.getCodTypeTrait())));
                    b.setEtape(v);
                    treatmentsCmiOff.add(b);
                }
            }
            

            try {
            //delete trtCmi
            	commission.getTraitementCmi().removeAll(trtcmiToDeleteFinal);
            	getParameterService().updateCommission(commission);
            	getParameterService().deleteTraitementCmi(trtcmiToDeleteFinal);
            } catch(Exception e) {
            	ExceptionUtils.catchException(e);
            }

            //reset();
            addInfoMessage(null, "INFO.ENTER.SUCCESS");
//			return NavigationRulesConst.SEE_CMI;
        }

        if (log.isDebugEnabled()) {
            log.debug("leaving update");
        }

        return null;

    }


    /**
     * Create a linkCampToVEt and pass the trtOff to trtInUse.
     *
     * @return String
     */
    public String addCampToVet() {
        if (log.isDebugEnabled()) {
            log.debug("addCampToVet");
        }
        if (etapeController.getObjectToAdd().length > 0) {
            for (Object o : etapeController.getObjectToAdd()) {
                BeanTrtCmi bt = (BeanTrtCmi) o;
                addCampToVet(bt);
            }

        } else if (etapeController.getAllChecked()) {
            for (BeanTrtCmi bt : treatmentsCmiOff) {
                addCampToVet(bt);
            }
        } else {
            addErrorMessage(null, "ERROR.TRT.CMI.NOT_SELECT");
            return null;
        }
        commission = getParameterService().getCommission(commission.getId(), null);
        initAllTraitementCmi(commission, null);
        actionEnum.setWhatAction(ActionEnum.READ_ACTION);
        return null;
    }

    /**
     * @param bt
     */
    private void addCampToVet(final BeanTrtCmi bt) {
        TraitementCmi t = bt.getTraitementCmi();
        Boolean isConnectCamp = false;
        for (LinkTrtCmiCamp link : t.getLinkTrtCmiCamp()) {
            if (link.getCampagne().equals(campagneSelected)) {
                isConnectCamp = true;
                link.setTemoinEnService(true);
                getParameterService().updateLinkTrtCmiCamp(link);
                break;
            }
        }

        if (!isConnectCamp) {
            List<VersionEtapeDTO> etapes = getDomainApoService().
                    getVersionEtapes(t.getVersionEtpOpi().getCodEtp(), null,
                            t.getVersionEtpOpi().getCodCge(), campagneSelected.getCodAnu());
            // on crée le link uniquement si l'étape est ouverte pour la campagne sélectionnée
            boolean linkCreated = false;
            for (VersionEtapeDTO etp : etapes) {
                if (etp.getCodVrsVet().compareTo(t.getVersionEtpOpi().getCodVrsVet()) == 0) {
                    //on cree un nouveau linkTrtCmiCamp
                    LinkTrtCmiCamp l = new LinkTrtCmiCamp();
                    l.setCampagne(campagneSelected);
                    l.setTraitementCmi(t);
                    LinkTrtCmiCamp lAdd = getDomainService().add(l, getCurrentGest().getLogin());
                    getParameterService().addLinkTrtCmiCamp(lAdd);
                    linkCreated = true;
                    break;
                }
            }
            if (!linkCreated) {
                addErrorMessage(null, "ERROR.TRT.CMI.NO_CAMPAGNE");
            }
        }
    }

    /**
     * Control Commission treatment to update.
     *
     * @param displayMessage
     * @return Boolean
     */
    private Boolean ctrlEnter(final Boolean displayMessage) {
        Boolean ctrlOk = true;
        for (BeanTrtCmi t : allTraitementCmi) {
            if (!StringUtils.hasText(t.getTraitementCmi().getCodTypeTrait())) {
                if (displayMessage) {
                    addErrorMessage(null, Constantes.I18N_EMPTY, getString("TYP_TRT"));
                }
                return false;
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("leaving ctrlAdd return = " + ctrlOk);
        }
        return ctrlOk;
    }

    public List<BeanTrtCmi> getAllTraitementCmi() {
        Collections.sort(allTraitementCmi, new ComparatorString(BeanTrtCmi.class));
        return allTraitementCmi;
    }

    public List<BeanTrtCmi> getTreatmentsCmiOff() {
        Collections.sort(treatmentsCmiOff, new ComparatorString(BeanTrtCmi.class));
        return treatmentsCmiOff;
    }

    public void setAllTraitementCmi(final List<BeanTrtCmi> allTraitementCmi) {
        this.allTraitementCmi = allTraitementCmi;
    }

    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    public void setEtapeController(final EtapeController etapeController) {
        this.etapeController = etapeController;
    }

    public String getCodeCmi() {
        return codeCmi;
    }

    public void setCodeCmi(final String codeCmi) {
        this.codeCmi = codeCmi;
    }

    public Integer getIdCmi() {
        if (idCmi == null) {
            return 0;
        }
        return idCmi;
    }

    public void setIdCmi(final Integer idCmi) {
        this.idCmi = idCmi;
    }

    public String getCodeTypeTrtselected() {
        return codeTypeTrtselected;
    }

    public void setCodeTypeTrtselected(final String codeTypeTrtselected) {
        this.codeTypeTrtselected = codeTypeTrtselected;
    }

    public BeanTrtCmi getbeanTrtCmi() {
        return beanTrtCmi;
    }

    public void setbeanTrtCmi(final BeanTrtCmi beanTrtCmi) {
        this.beanTrtCmi = beanTrtCmi;
    }

    public Commission getCommission() {
        return commission;
    }

    public void setCommission(final Commission commission) {
        this.commission = commission;
    }

    public EtapeController getEtapeController() {
        return etapeController;
    }

    public void setTreatmentsCmiOff(final List<BeanTrtCmi> treatmentCmiOff) {
        this.treatmentsCmiOff = treatmentCmiOff;
    }

    public Campagne getCampagneSelected() {
        return campagneSelected;
    }

    public void setCampagneSelected(final Campagne campagneSelected) {
        this.campagneSelected = campagneSelected;
    }

    public LinkTrtCmiCamp getLinkToDel() {
        return linkToDel;
    }

    public void setLinkToDel(final LinkTrtCmiCamp linkToDel) {
        this.linkToDel = linkToDel;
    }

    public List<Campagne> getListeCampagne() {
        return listeCampagne;
    }

    public void setListeCampagne(final List<Campagne> listeCampagne) {
        this.listeCampagne = listeCampagne;
    }

    public RegimeInscription getCurrentRegime() {
        return currentRegime;
    }

    public void setCurrentRegime(final RegimeInscription currentRegime) {
        this.currentRegime = currentRegime;
    }

    public List<VersionEtapeDTO> getListVETToTransfert() {
        return listVETToTransfert;
    }

    public void setListVETToTransfert(final List<VersionEtapeDTO> listVETToTransfert) {
        this.listVETToTransfert = listVETToTransfert;
    }

    public VersionEtapeDTO getVetToTransfert() {
        return vetToTransfert;
    }

    public void setVetToTransfert(final VersionEtapeDTO vetToTransfert) {
        this.vetToTransfert = vetToTransfert;
    }

    public boolean isTransfertVet() {
        return transfertVet;
    }

    public void setTransfertVet(final boolean transfertVet) {
        this.transfertVet = transfertVet;
    }


}

