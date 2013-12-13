package org.esupportail.opi.web.controllers.pilotage;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.pilotage.ArchiveTask;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.services.archives.ArchiveService;
import org.esupportail.opi.web.beans.BeanTrtCmi;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.parameters.FormationContinue;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorSelectItem;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.opi.web.controllers.references.TrtCmiController;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import java.util.*;

/**
 * @author ylecuyer
 */
public class ArchiveTaskController extends AbstractContextAwareController {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = 3652722084180609045L;

    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

	/*
	 ******************* PROPERTIES ******************* */

    /**
     * The archiveTask task.
     */
    private ArchiveTask archiveTask;

    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum;

    /**
     * List of the campagnes in use.
     */
    private Set<Campagne> campagnesInUse;

    /**
     * The campagne to archive.
     */
    private Campagne campToArch;

    /**
     * List of the campagnes to active.
     * can contain the campagnes already archive
     */
    private Set<Campagne> campagnesToActive;

    /**
     * The campagne to active.
     */
    private Campagne campToActive;

    /**
     * true if we show the archived campagnes.
     * in the selection of campagnes to active
     */
    private Boolean affArchivedCamps;

    /**
     * The date for the archive.
     */
    private Date dateArch;

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
     * Liste des traitementsCmi en service de la commission .
     */
    private List<BeanTrtCmi> allTraitementCmi;

    /**
     * Liste des traitementsCmi hors service de la commission .
     */
    private List<BeanTrtCmi> treatmentsCmiOff;

    /**
     * List of BeanTrtCmi to archive.
     */
    private Object[] objectToArch;

    /**
     * Has true if all traitement cmi on service are selected.
     * Default value = false
     */
    private Boolean allCheckedOn;

    /**
     * Has true if all traitement cmi off are selected.
     * Default value = false
     */
    private Boolean allCheckedOff;

    private ArchiveService archiveService;

    private TrtCmiController trtCmiController;

	/*
	 ******************* INIT ************************* */

    /**
     * Constructors.
     */
    public ArchiveTaskController() {
        super();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        trtCmiController.reset();
        archiveTask = null;
        actionEnum = new ActionEnum();
        campagnesInUse = new HashSet<Campagne>();
        campToArch = null;
        campagnesToActive = new HashSet<Campagne>();
        campToActive = null;
        affArchivedCamps = false;
        dateArch = null;
        idCmi = 0;
        codeCmi = null;
        commission = null;
        allTraitementCmi = new ArrayList<BeanTrtCmi>();
        treatmentsCmiOff = new ArrayList<BeanTrtCmi>();
        objectToArch = new Object[]{};
        allCheckedOn = false;
        allCheckedOff = false;
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();
        Assert.notNull(this.archiveService,
                "property archiveService of class " + this.getClass().getName()
                        + " can not be null");
        Assert.notNull(this.trtCmiController,
                "property trtCmiController of class " + this.getClass().getName()
                        + " can not be null");
        reset();
    }

	/*
	 ******************* CALLBACK ********************** */

    /**
     * Callback to archiveTask tasks list.
     *
     * @return String
     */
    public String goSeeAllArchiveTasks() {
        reset();
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        int codeRI = gest.getProfile().getCodeRI();
        RegimeInscription regimeIns = getSessionController().getRegimeIns().get(codeRI);
        if (regimeIns instanceof FormationInitiale) {
            return NavigationRulesConst.MANAGED_ARCHIVE_TASKS;
        }
        return NavigationRulesConst.MANAGED_ARCHIVE_SFC;
    }

    /**
     * Callback to add a archiveTask task.
     */
    public void goAddArchiveTask() {
        archiveTask = new ArchiveTask();
        // set campToArch
        campToArch = getParameterService().getCampagneEnServ(FormationInitiale.CODE);

        refreshCampToAct();

        actionEnum.setWhatAction(ActionEnum.ADD_ACTION);
    }

    /**
     * Callback to add a archiveTask task.
     */
    public void goUpdateArchiveTask() {
        // set campToArch
        campToArch = archiveTask.getCampagneToArchive();
        // set campToActive
        campToActive = archiveTask.getCampagneToActive();
        // set dateArch
        dateArch = archiveTask.getDateArchive();

        refreshCampToAct();

        actionEnum.setWhatAction(ActionEnum.UPDATE_ACTION);
    }
	
	/*
	 ******************* METHODS FOR FC ********************** */

    /**
     * method to look for commission.
     */
    public void lookForCmi() {
        Commission c = getParameterService().getCommission(
                idCmi,
                codeCmi);
        if (c == null) {
            addErrorMessage(null, "COMMISSION.NOT_FOUND");
            reset();
        } else {
            commission = c;
            // on initialise la liste de campagne
            campagnesInUse.addAll(getParameterService().getCampagnes(
                    true, String.valueOf(FormationContinue.CODE)));
        }
    }

    /**
     * List the trtCmi for the campagne that are not archived.
     */
    public void selectCampToArch() {
        allTraitementCmi.clear();
        treatmentsCmiOff.clear();
        objectToArch = new Object[]{};
        if (campToArch != null) {
            for (TraitementCmi t : commission.getTraitementCmi()) {
                //init proxy hib
                // passage du link en lazy false
                //getDomainService().initOneProxyHib(t, t.getLinkTrtCmiCamp(), Set.class);
                // si le traitement est actif dans la campagne
                // et non hors service
                if (Utilitaires.isTraitementCmiInCamp(t, campToArch)
                        && !Utilitaires.isTraitementCmiOff(t, campToArch.getCodeRI())) {
                    BeanTrtCmi b = new BeanTrtCmi(t, TypeTraitement.fromCode(t.getCodTypeTrait()));
                    b.setEtape(getDomainApoService().getVersionEtape(
                            b.getTraitementCmi().getVersionEtpOpi().getCodEtp(),
                            b.getTraitementCmi().getVersionEtpOpi().getCodVrsVet()));

                    allTraitementCmi.add(b);
                }
                if (Utilitaires.isTraitementCmiOff(t, campToArch.getCodeRI())
                        && Utilitaires.getLinkTrtCmiCamp(t, campToArch) != null) {
                    BeanTrtCmi b = new BeanTrtCmi(t, TypeTraitement.fromCode(t.getCodTypeTrait()));
                    b.setEtape(getDomainApoService().getVersionEtape(
                            b.getTraitementCmi().getVersionEtpOpi().getCodEtp(),
                            b.getTraitementCmi().getVersionEtpOpi().getCodVrsVet()));

                    treatmentsCmiOff.add(b);
                }
            }
        }
        refreshCampToActFC();
    }

    /**
     * Refresh the list campagnesToActive.
     */
    public void refreshCampToActFC() {
        campagnesToActive = new HashSet<Campagne>();
        if (campToArch != null) {
            int codeRI = campToArch.getCodeRI();
            // on récupère les campagnes
            Set<Campagne> listCamps = getParameterService().getCampagnes(null, null);
            for (Campagne camp : listCamps) {
                // on sélectionne les campagnes non archivées étant FC
                // on peut prendre les campagnes déjà archivés si on a coché la case
                if (camp.getCodeRI() == codeRI && (!camp.getIsArchived() || affArchivedCamps)) {
                    campagnesToActive.add(camp);
                }
            }
        }
    }

    /**
     * @param value
     */
    public void checkAllOn(final ValueChangeEvent value) {
        List<Object> list = Arrays.asList(objectToArch);
        if (!allCheckedOn) {
            list.addAll(allTraitementCmi);
        } else {
            list.removeAll(allTraitementCmi);
        }
        objectToArch = list.toArray();
    }

    /**
     * @param value
     */
    public void checkAllOff(final ValueChangeEvent value) {
        List<Object> list = Arrays.asList(objectToArch);
        if (!allCheckedOff) {
            list.addAll(treatmentsCmiOff);
        } else {
            list.removeAll(treatmentsCmiOff);
        }
        objectToArch = list.toArray();
    }

    /**
     * Archive the selected traitementCmi.
     */
    public void archiveTrtCmi() {
        if (campToActive != null) {
            // si la campagne de transfert n'est pas activée, on l'active
            // et on la désarchive au cas où elle était aussi archivée
            if (!campToActive.getTemoinEnService()) {
                campToActive.setTemoinEnService(true);
                campToActive.setIsArchived(false);
                getParameterService().updateNomenclature(
                        (Campagne) getDomainService().update(
                                campToActive, getCurrentGest().getLogin()));
            }
            Set<Individu> listIndArch = new HashSet<Individu>();
            // on boucle sur les traitementsCmi à archiver
            for (Object o : objectToArch) {
                BeanTrtCmi b = (BeanTrtCmi) o;
                TraitementCmi trtCmi = b.getTraitementCmi();
                // on récupère le linkTrtCmiCamp correspondant à la campagne à archiver
                //init proxy hib
                // passage du link en lazy false
                //getDomainService().initOneProxyHib(trtCmi, trtCmi.getLinkTrtCmiCamp(), Set.class);
                LinkTrtCmiCamp linkArch = Utilitaires.getLinkTrtCmiCamp(trtCmi, campToArch);
                if (linkArch != null) {
                    //init proxy hib
                    getDomainService().initOneProxyHib(linkArch, linkArch.getVoeux(), Set.class);
                    // on boucle sur les voeux du link
                    for (IndVoeu voeu : linkArch.getVoeux()) {
                        // on enregistre l'individu lié au voeu pour les traiter à la fin
                        listIndArch.add(voeu.getIndividu());
                        // on met HS le voeu
                        voeu.setTemoinEnService(false);
                        getDomainService().updateIndVoeu(voeu);
                        // on supprime le formulaire si il existe
                        IndFormulaire indF = getParameterService()
                                .getIndFormulaires(voeu.getIndividu())
                                .get(trtCmi.getVersionEtpOpi());
                        RegimeInscription regime = getRegimeIns().get(
                                Utilitaires.getCodeRIIndividu(voeu.getIndividu(),
                                        getDomainService()));
                        if (indF != null) {
                            getParameterService().deleteIndFormulaire(
                                    indF, voeu.getIndividu().getNumDossierOpi(),
                                    regime.getShortLabel());
                        }
                        // on supprime le rendez-vous du voeu s'il existe
                        if (getDomainService().getIndividuDate(voeu) != null) {
                            getDomainService().deleteIndividuDate(
                                    getDomainService().getIndividuDate(voeu));
                        }
                    }
                    // on met HS le link
                    linkArch.setTemoinEnService(false);
                    getParameterService().updateLinkTrtCmiCamp(linkArch);

                }
                // si le link pour la nouvelle campagne n'existe pas
                // on ajoute (si possible) le traitementCmi pour la nouvelle campagne
                LinkTrtCmiCamp linkAct = Utilitaires.getLinkTrtCmiCamp(trtCmi, campToActive);
                if (linkAct == null) {
                    archiveService.addTraitementCmiToNewCamp(trtCmi, campToActive);
                } else {
                    linkAct.setTemoinEnService(true);
                    getParameterService().updateLinkTrtCmiCamp(linkAct);
                }
            }
            // pour chaque individu de listIndArch, on teste si TOUS les voeux sont HS
            // si oui, on met HS l'individu
            for (Individu ind : listIndArch) {
                boolean archInd = true;
                for (IndVoeu voeu : ind.getVoeux()) {
                    if (voeu.getTemoinEnService()) {
                        archInd = false;
                    }
                }
                if (archInd) {
                    archiveService.archiveIndividu(ind);
                }
            }
            // pour la campagne à archiver, on teste si TOUS les links sont HS
            // si oui, on met HS la campagne
            boolean archCamp = true;
            //init proxy hib
            getDomainService().initOneProxyHib(campToArch, campToArch.getLinkTrtCmiCamp(), Set.class);
            for (LinkTrtCmiCamp linkCamp : campToArch.getLinkTrtCmiCamp()) {
                if (linkCamp.getTemoinEnService()) {
                    archCamp = false;
                }
            }
            if (archCamp) {
                campToArch.setIsArchived(true);
                campToArch.setTemoinEnService(false);
                getParameterService().updateNomenclature(campToArch);
            }
        } else {
            addErrorMessage(null, "ERROR.ARCHIVAGE.CAMP_ACT");
        }
        // on rafraichit les listes de traitementCmi
        lookForCmi();
        selectCampToArch();
    }
	
	/*
	 ******************* METHODS ********************** */

    /**
     * List of campagnes in use.
     *
     * @return
     */
    public List<SelectItem> getCampagnesInUseItems() {
        List<SelectItem> s = new ArrayList<SelectItem>();
        s.add(new SelectItem("", ""));
        for (Campagne c : getCampagnesInUse()) {
            s.add(new SelectItem(c, c.getLibelle()));
        }
        Collections.sort(s, new ComparatorSelectItem());
        return s;
    }

    /**
     * List of campagnes to active.
     *
     * @return
     */
    public List<SelectItem> getCampagnesToActiveItems() {
        List<SelectItem> s = new ArrayList<SelectItem>();
        s.add(new SelectItem("", ""));
        for (Campagne c : getCampagnesToActive()) {
            s.add(new SelectItem(c, c.getLibelle()));
        }
        Collections.sort(s, new ComparatorSelectItem());
        return s;

    }
	
	/*
	 ******************* METHODS FOR FI ********************** */

    /**
     * Refresh the list campagnesToActive.
     */
    public void refreshCampToAct() {
        campagnesToActive = new HashSet<Campagne>();
        if (campToArch != null) {
            int codeRI = campToArch.getCodeRI();
            // on récupère les campagnes non en service
            Set<Campagne> campsNotEnServ = getParameterService().getCampagnes(false, null);
            for (Campagne camp : campsNotEnServ) {
                // on sélectionne les campagnes non archivées étant FC ou non selon le choix
                // de campagne à archiver
                // on peut prendre les campagnes déjà archivés si on a coché la case
                if (camp.getCodeRI() == codeRI && (!camp.getIsArchived() || affArchivedCamps)) {
                    campagnesToActive.add(camp);
                }
            }
            // on modifie la dateArch
            dateArch = campToArch.getDateFinCamp();
        }
    }

    /**
     * Add the archiveTask to the dataBase.
     */
    public void add() {
        if (log.isDebugEnabled()) {
            log.debug("entering add with archiveTask = " + archiveTask);
        }

        if (ctrlEnter()) {
            // on set les campagnes sélectionnés
            archiveTask.setCampagneToArchive(campToArch);
            archiveTask.setCampagneToActive(campToActive);
            archiveTask.setDateArchive(dateArch);

            archiveTask = (ArchiveTask) getDomainService().add(archiveTask, getCurrentGest().getLogin());

            getDomainService().addArchiveTask(archiveTask);

            addInfoMessage(null, "INFO.ENTER.SUCCESS");
            reset();
        }

        if (log.isDebugEnabled()) {
            log.debug("leaving add");
        }
    }

    /**
     * Update the archiveTask to the dataBase.
     */
    public void update() {
        if (log.isDebugEnabled()) {
            log.debug("entering update with archiveTask = " + archiveTask);
        }

        if (ctrlEnter()) {
            // on set les campagnes sélectionnés
            archiveTask.setCampagneToArchive(campToArch);
            archiveTask.setCampagneToActive(campToActive);
            archiveTask.setDateArchive(dateArch);

            archiveTask = (ArchiveTask) getDomainService().update(archiveTask, getCurrentGest().getLogin());

            getDomainService().updateArchiveTask(archiveTask);

            addInfoMessage(null, "INFO.ENTER.SUCCESS");
            reset();
        }

        if (log.isDebugEnabled()) {
            log.debug("leaving update");
        }
    }

    /**
     * Delete the archiveTask to the dataBase.
     */
    public void delete() {
        if (log.isDebugEnabled()) {
            log.debug("entering delete with archiveTask = " + archiveTask);
        }

        getDomainService().deleteArchiveTask(archiveTask);

        reset();
        addInfoMessage(null, "INFO.DELETE.SUCCESS");

        if (log.isDebugEnabled()) {
            log.debug("leaving delete");
        }
    }

    /**
     * Control archiveTask attributes for the adding and updating.
     *
     * @return Boolean
     */
    public Boolean ctrlEnter() {
        boolean ctrlOK = true;
        if (campToArch == null) {
            ctrlOK = false;
            addErrorMessage(null, "ERROR.ARCHIVAGE.CAMP_ARCH");
        }
        if (campToActive == null) {
            ctrlOK = false;
            addErrorMessage(null, "ERROR.ARCHIVAGE.CAMP_ACT");
        }
        if (dateArch == null) {
            ctrlOK = false;
            addErrorMessage(null, "ERROR.ARCHIVAGE.DATE_MISS");
        }
        if (dateArch != null && !dateArch.after(new Date())) {
            ctrlOK = false;
            addErrorMessage(null, "ERROR.ARCHIVAGE.DATE_INF");
        }
        return ctrlOK;
    }
	
	/*
	 ******************* ACCESSORS ******************** */

    /**
     * @return the list of all the archive tasks
     */
    public List<ArchiveTask> getArchiveTasks() {
        return getDomainService().getArchiveTasks();
    }

    /**
     * @return the archiveTask
     */
    public ArchiveTask getArchiveTask() {
        return archiveTask;
    }

    /**
     * @param archiveTask the archiveTask to set
     */
    public void setArchiveTask(final ArchiveTask archiveTask) {
        this.archiveTask = archiveTask;
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
     * @return the campagnesInUse
     */
    public Set<Campagne> getCampagnesInUse() {
        return campagnesInUse;
    }

    /**
     * @param campagnesInUse the campagnesInUse to set
     */
    public void setCampagnesInUse(final Set<Campagne> campagnesInUse) {
        this.campagnesInUse = campagnesInUse;
    }

    /**
     * @return the campToArch
     */
    public Campagne getCampToArch() {
        return campToArch;
    }

    /**
     * @param campToArch the campToArch to set
     */
    public void setCampToArch(final Campagne campToArch) {
        this.campToArch = campToArch;
    }

    /**
     * @return the campagnesToActive
     */
    public Set<Campagne> getCampagnesToActive() {
        return campagnesToActive;
    }

    /**
     * @param campagnesToActive the campagnesToActive to set
     */
    public void setCampagnesToActive(final Set<Campagne> campagnesToActive) {
        this.campagnesToActive = campagnesToActive;
    }

    /**
     * @return the campToActive
     */
    public Campagne getCampToActive() {
        return campToActive;
    }

    /**
     * @param campToActive the campToActive to set
     */
    public void setCampToActive(final Campagne campToActive) {
        this.campToActive = campToActive;
    }

    /**
     * @return the affArchivedCamps
     */
    public Boolean getAffArchivedCamps() {
        return affArchivedCamps;
    }

    /**
     * @param affArchivedCamps the affArchivedCamps to set
     */
    public void setAffArchivedCamps(final Boolean affArchivedCamps) {
        this.affArchivedCamps = affArchivedCamps;
    }

    /**
     * @return the dateArch
     */
    public Date getDateArch() {
        return dateArch;
    }

    /**
     * @param dateArch the dateArch to set
     */
    public void setDateArch(final Date dateArch) {
        this.dateArch = dateArch;
    }

    /**
     * @return the codeCmi
     */
    public String getCodeCmi() {
        return codeCmi;
    }

    /**
     * @param codeCmi the codeCmi to set
     */
    public void setCodeCmi(final String codeCmi) {
        this.codeCmi = codeCmi;
    }

    /**
     * @return the idCmi
     */
    public Integer getIdCmi() {
        return idCmi;
    }

    /**
     * @param idCmi the idCmi to set
     */
    public void setIdCmi(final Integer idCmi) {
        this.idCmi = idCmi;
    }

    /**
     * @return the commission
     */
    public Commission getCommission() {
        return commission;
    }

    /**
     * @param commission the commission to set
     */
    public void setCommission(final Commission commission) {
        this.commission = commission;
    }

    /**
     * @return the archiveService
     */
    public ArchiveService getArchiveService() {
        return archiveService;
    }

    /**
     * @param archiveService the archiveService to set
     */
    public void setArchiveService(final ArchiveService archiveService) {
        this.archiveService = archiveService;
    }

    /**
     * @return the trtCmiController
     */
    public TrtCmiController getTrtCmiController() {
        return trtCmiController;
    }

    /**
     * @param trtCmiController the trtCmiController to set
     */
    public void setTrtCmiController(final TrtCmiController trtCmiController) {
        this.trtCmiController = trtCmiController;
    }

    /**
     * @return the allTraitementCmi
     */
    public List<BeanTrtCmi> getAllTraitementCmi() {
        return allTraitementCmi;
    }

    /**
     * @param allTraitementCmi the allTraitementCmi to set
     */
    public void setAllTraitementCmi(final List<BeanTrtCmi> allTraitementCmi) {
        this.allTraitementCmi = allTraitementCmi;
    }

    /**
     * @return the treatmentsCmiOff
     */
    public List<BeanTrtCmi> getTreatmentsCmiOff() {
        return treatmentsCmiOff;
    }

    /**
     * @param treatmentsCmiOff the treatmentsCmiOff to set
     */
    public void setTreatmentsCmiOff(final List<BeanTrtCmi> treatmentsCmiOff) {
        this.treatmentsCmiOff = treatmentsCmiOff;
    }

    /**
     * @return the objectToArch
     */
    public Object[] getObjectToArch() {
        return objectToArch;
    }

    /**
     * @param objectToArch the objectToArch to set
     */
    public void setObjectToArch(final Object[] objectToArch) {
        this.objectToArch = objectToArch;
    }

    /**
     * @return the allCheckedOn
     */
    public Boolean getAllCheckedOn() {
        return allCheckedOn;
    }

    /**
     * @param allCheckedOn the allCheckedOn to set
     */
    public void setAllCheckedOn(final Boolean allCheckedOn) {
        this.allCheckedOn = allCheckedOn;
    }

    /**
     * @return the allCheckedOff
     */
    public Boolean getAllCheckedOff() {
        return allCheckedOff;
    }

    /**
     * @param allCheckedOff the allCheckedOff to set
     */
    public void setAllCheckedOff(final Boolean allCheckedOff) {
        this.allCheckedOff = allCheckedOff;
    }

}
