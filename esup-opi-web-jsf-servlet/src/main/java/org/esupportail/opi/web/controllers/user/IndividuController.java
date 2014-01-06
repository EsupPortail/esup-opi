/**
 *
 */
package org.esupportail.opi.web.controllers.user;


import fj.P1;
import org.apache.commons.lang.StringUtils;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.domain.beans.user.situation.IndSituation;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.utils.GenNumDosOPI;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.parameters.FormationContinue;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndRechPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.controllers.formation.FormulairesController;
import org.esupportail.opi.web.utils.paginator.LazyDataModel;
import org.esupportail.opi.web.utils.paginator.PaginationFunctions;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.DateTimeConverter;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static java.util.Arrays.asList;
import static org.esupportail.opi.domain.beans.etat.EtatIndividu.EtatComplet;
import static org.esupportail.opi.domain.beans.etat.EtatIndividu.EtatIncomplet;
import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.AccesSelectif;
import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.ValidationAcquis;
import static org.esupportail.opi.web.utils.paginator.LazyDataModel.lazyModel;

public class IndividuController extends AbstractAccessController {

    private static final long serialVersionUID = 1739075202274589002L;

    /**
     * The number of character for the NumDosOPI code.
     */
    private static final int NB_CHAR_CODE = 8;
    /**
     *
     */
    private static final int NB_YEAR_FIRST = 10;
    /**
     *
     */
    private static final int NB_YEAR_LAST = 100;
    /**
    *
    */    
    public static final SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy");
    
    /**
     * Current Year Used By default for birth dates().
     */
    private static final int DEFAULT_CURRENT_YEAR = 2010;

    /**
     * can no be null.
     */
    private static final String CAN_NO_BE_NULL = "can not be null";

    /**
     * The state when create a dossier.
     */
    private static final String DOSSIER_CREATE = "DOSSIER_CREATE";

    /**
     * The state when update a dossier.
     */
    private static final String DOSSIER_UPDATE = "DOSSIER_UPDATE";

    /**
     * Static list used to store the years which can be used for the birthdates.
     */
    private static List<SelectItem> listeAnneeNaissance = new ArrayList<>();


    /**
     * At true if call in ENT.
     * Default value : false.
     */
    private boolean isRecupCursus;

    /**
     * At true if call in ENT.
     * Default value : false.
     */
    private boolean isRecupBac;

    /**
     * At true if call in ENT.
     * Default value : false.
     */
    private boolean isRecupInfos;

    /**
     * access to the ldap Service
     */
    LdapUserService ldapUserService;

    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

    /**
     * The Individu.
     */
    private IndividuPojo pojoIndividu;

    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum;

    /**
     * see {@link AdressController}.
     */
    private AdressController adressController;

    /**
     * see {@link CursusController}.
     */
    private CursusController cursusController;

    /**
     * see {@link AdressController}.
     */
    private IndBacController indBacController;

    /**
     * see {@link SituationController}.
     */
    private SituationController situationController;

    /**
     * see {@link FormulairesController}.
     */
    private FormulairesController formulairesController;

    /**
     * State of the dossier (create or update).
     */
    private String etatDossier;

    /**
     * Variable to check the mail address.
     */
    private String checkEmail;

    /**
     * Used to know if the list cmi has to be filtered by right
     */
    private boolean listCmiByRight;

    private boolean renderTable = false;

    private IndRechPojo indRechPojo;

    private final LazyDataModel<Individu> indLDM = lazyModel(
            PaginationFunctions.getData(
                    new P1<SessionController>() {
                        public SessionController _1() { return getSessionController(); }
                    },
                    new P1<DomainService>() {
                        public DomainService _1() { return getDomainService(); }
                    },
                    new P1<DomainApoService>() {
                        public DomainApoService _1() { return getDomainApoService(); }
                    },
                    new P1<ParameterService>() {
                        public ParameterService _1() { return getParameterService(); }
                    },
                    new P1<IndRechPojo>() {
                        public IndRechPojo _1() { return indRechPojo; }
                    }),
            PaginationFunctions.findByRowKey);

    @Override
    public void reset() {
        actionEnum = new ActionEnum();

        pojoIndividu = new IndividuPojo();
        pojoIndividu.getIndividu().setCodPayNaissance(Constantes.CODEFRANCE);
        pojoIndividu.getIndividu().setCodPayNationalite(Constantes.CODEFRANCE);

        checkEmail = "";

        adressController.reset();
        cursusController.reset();
        indBacController.reset();
        situationController.reset();
    }

    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();

        Assert.notNull(this.adressController,
                "property adressController of class " + this.getClass().getName() + CAN_NO_BE_NULL);
        Assert.notNull(this.cursusController,
                "property cursusController of class " + this.getClass().getName() + CAN_NO_BE_NULL);
        Assert.notNull(this.indBacController,
                "property indBacController of class " + this.getClass().getName() + CAN_NO_BE_NULL);
        Assert.notNull(this.situationController,
                "property situationController of class " + this.getClass().getName() + CAN_NO_BE_NULL);
        Assert.notNull(this.formulairesController,
                "property formulairesController of class " + this.getClass().getName() + CAN_NO_BE_NULL);

        indRechPojo = new IndRechPojo() {{
            setUseGestCommsFilter(listCmiByRight);
        }};
    }

    @Override
    public String toString() {
        return "IndividuController#" + hashCode() + "[pojoIndividu =" + pojoIndividu + "]";
    }

    /**
     * Callback to search a Rennes1 student with his NNE code.
     *
     * @return String
     */
    public String goSearchEtuR1() {
        reset();
        resetRoadMap();
        pojoIndividu.getIndividu().setCodPayNaissance("");
        pojoIndividu.getIndividu().setCodPayNationalite("");
        if (!getManagedCalendar().getCalInsIsOpen()) {
            //les calendriers sont fermes
            return NavigationRulesConst.INSCRIPTION_CLOSE;
        }

        getSessionController().setRegimeInsUser(
                getSessionController().getRegimeIns().get(FormationInitiale.CODE));

        addTheCurrentRoad(NavigationRulesConst.SEARCH_ETU_R1,
                getString("INDIVIDU.SEARCH"), getString("INDIVIDU.SEARCH.DESC"));
        return NavigationRulesConst.SEARCH_ETU_R1;
    }

    /**
     * Callback to find a Rennes1 student in Apogee with his NNE code.
     *
     * @return String
     */
    public String goFindEtuR1() {
        if (log.isDebugEnabled()) {
            log.debug("entering goFindEtuR1()");
        }
        etatDossier = DOSSIER_CREATE;
        /**
         * Règles d'accès à la création d'un dossier :
         * - soit l'étudiant vient de l'ENT auquel cas on l'identifie avec son codEtu depuis Apogée
         * - soit il vient de la page de recherche :
         *   - si il a saisit son code INE, on le recherche dans Apogée
         *   - si il a cliqué sur "Création d'un dossier", on remet à zero le pojoIndividu
         *   - si il a fait une recherche, on lui affiche son dossier prérempli avec ses coordonnées
         *   éventuellement bloquées si les données existent déjà dans Apogée
         */

        //isCodEtu signifie qu'1 etudiant de Rennes1 se connecte pour la  premiere fois sur OPI
        Boolean isCodEtu = StringUtils.isNotBlank(getSessionController().getCodEtu());
        if (isCodEtu || (!pojoIndividu.getDoNotHaveCodeNne() && !pojoIndividu.getIsUsingSearch())) {

            Individu ind = pojoIndividu.getIndividu();
            Individu individuOPI;
            Individu individuApogee;
            if (!isCodEtu) {
                // Check NNE fields
                if (!StringUtils.isNotBlank(ind.getCodeNNE())
                        || !StringUtils.isNotBlank(ind.getCodeClefNNE())) {
                    addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.NUM_NNE"));
                    return null;
                }
                // Get the student data from Base
                individuOPI = getDomainService().getIndividuINE(ind.getCodeNNE(), ind.getCodeClefNNE());
                // Get the student data from Apogee
                individuApogee = getDomainApoService().getIndividuFromApogee(
                        ind.getCodeNNE(), ind.getCodeClefNNE().toUpperCase(), null, null, null, null);
            } else {
                // Get the student data from Base
                individuOPI = getDomainService().getIndividuCodEtu(getSessionController().getCodEtu());
                // Get the student data from Apogee
                individuApogee = getDomainApoService().getIndividuFromApogee(
                        null, null, getSessionController().getCodEtu(), null, null, null);
                // cas d'un individu déjà enregistré sans codEtu mais présent dans OPI
                // récup grace au num ine dans apogée
                if (individuOPI == null && individuApogee != null) {
                    List<Individu> indResSearch = getDomainService().getIndividuSearch(
                            individuApogee.getNomPatronymique(), individuApogee.getPrenom(),
                            individuApogee.getDateNaissance(), individuApogee.getCodPayNaissance(),
                            individuApogee.getCodDepPaysNaissance());
                    // si on a une liste de plus d'un individu, on informe le candidat
                    // et on arrête sa candidature
                    if (indResSearch.size() > 1) {
                        // si on a plusieurs résultats, cas d'une homonymie en base,
                        // dans ce cas, on informe le candidat de contacter la scol
                        addErrorMessage(null, "ERROR.SEARCH_IND.HOMONYME");
                        return null;
                    } else if (!indResSearch.isEmpty()) {
                        individuOPI = indResSearch.get(0);
                    }
                }
            }

            if (individuApogee == null && individuOPI == null) {
                addErrorMessage(null, "ERROR.FIELD.NNE_KO");
            } else {
                initIndividuFromApogee(individuOPI, individuApogee);
            }
        } else if (pojoIndividu.getIsUsingSearch()) {
            // cas où l'utilisateur fait une recherche
            // on recherche l'individu
            if (ctrlSearch()) {
                Individu searchInd = toUpperCaseAnyAttributs(pojoIndividu.getIndividu());
                List<Individu> indResSearch = getDomainService().getIndividuSearch(
                        searchInd.getNomPatronymique(), searchInd.getPrenom(),
                        searchInd.getDateNaissance(), searchInd.getCodPayNaissance(),
                        searchInd.getCodDepPaysNaissance());
                // on réinitialise le controller dans tous les cas
                reset();
                if (indResSearch.isEmpty()) {
                    // si le résultat est vide, on informe le candidat
                    addErrorMessage(null, "ERROR.SEARCH_IND.EMPTY");
                    return null;
                } else if (indResSearch.size() > 1) {
                    // si on a plusieurs résultats, cas d'une homonymie en base,
                    // dans ce cas, on informe le candidat de contacter la scol
                    addErrorMessage(null, "ERROR.SEARCH_IND.HOMONYME");
                    return null;
                } else {
                    // on a récupéré le dossier puis on récupère ses infos depuis Apogée
                    Individu individuOPI = indResSearch.get(0);
                    SimpleDateFormat dateFormat = new SimpleDateFormat(Constantes.DATE_FORMAT);
                    Individu individuApogee = getDomainApoService().getIndividuFromApogee(
                            null, null, null, individuOPI.getNomPatronymique(),
                            individuOPI.getPrenom(),
                            dateFormat.format(individuOPI.getDateNaissance()));
                    initIndividuFromApogee(individuOPI, individuApogee);
                }
            } else {
                return null;
            }
        } else {
            reset();
        }

        return goAddAccount();

    }

    /**
     * Callback to see account details for the current connected user.
     *
     * @return String
     */
    public String goSeeAccount() {
        pojoIndividu = getCurrentInd();
        actionEnum.setWhatAction(ActionEnum.READ_ACTION);
        //init individu
        initIndividuPojo();
        checkEmail = pojoIndividu.getIndividu().getAdressMail();
        // init adress
        if (pojoIndividu.getIndividu().getAdresses().get(Constantes.ADR_FIX) != null) {
            adressController.init(
                    pojoIndividu.getIndividu()
                            .getAdresses().get(Constantes.ADR_FIX), false);
        }
        return NavigationRulesConst.SEE_ACCOUNT;
    }

    /**
     * Callback to see cursus details for the current connected user.
     *
     * @return String
     */
    public String goSeeCursus() {
        pojoIndividu = getCurrentInd();
        indBacController.initIndBac(new ArrayList<>(pojoIndividu.getIndividu().getIndBac()), false);
        return NavigationRulesConst.SEE_CURSUS;
    }

    /**
     * Callback to add a account.
     *
     * @return String
     */
    public String goAddAccount() {
        // on mémorise le codeRI présent en session
        pojoIndividu.setRegimeInscription(getSessionController().getRegimeInsUser());
        addTheCurrentRoad(NavigationRulesConst.ADD_ACCOUNT,
                getString("INDIVIDU.COORD"), getString("INDIVIDU.COORD"));
        return NavigationRulesConst.ADD_ACCOUNT;
    }


    /**
     * Callback to add ind bac or a situation.
     *
     * @return String
     */
    public String goAddIndBacOrSituation() {
        if (ctrlEnter() && ctrlUnicite()) {
            if (pojoIndividu.getRegimeInscription() instanceof FormationContinue) {
                situationController.getActionEnum().setWhatAction(ActionEnum.ADD_ACTION);
                situationController.setIndividuPojo(pojoIndividu);
                situationController.cancelSituation();
                addTheCurrentRoad(NavigationRulesConst.ADD_SITUATION,
                        getString("INDIVIDU.SITUATION"),
                        getString("INDIVIDU.SITUATION"));
                return NavigationRulesConst.ADD_SITUATION;
            }
            addTheCurrentRoad(NavigationRulesConst.ADD_IND_BAC,
                    getString("INDIVIDU.BAC"), getString("INDIVIDU.BAC"));
            return NavigationRulesConst.ADD_IND_BAC;
        }
        return null;
    }

    /**
     * Callback to accueilCandidat for a manager.
     *
     * @return String
     */
    public String goSeeOneIndividu() {
        //put true boolean isManager attribute in indPojo
        //put the boolean canUpdateStudent atttribute in indPojo
        final Set<Commission> rightOnCmi =
                new HashSet<>(getDomainApoService().getListCommissionsByRight(getCurrentGest(), true));
        final Individu unsafeInd = pojoIndividu.getIndividu();
        final Individu ind = getDomainService().getIndividu(unsafeInd.getNumDossierOpi(), unsafeInd.getDateNaissance());

        pojoIndividu.setIndividu(ind);

        getSessionController().initCurrentInd(
                ind.getNumDossierOpi(),
                ind.getDateNaissance(),
                true,
                getDomainService().hasGestionnaireRightsOnStudent(ind.getVoeux(), rightOnCmi));

        final IndividuPojo curInd = getCurrentInd();

        if (curInd.getEtat() == EtatIncomplet) {
            //on informe l'individu qu'il doit completer sur dossier avant de deposer de voeux
            if (!curInd.getRegimeInscription().getDisplayInfoFC())
                addInfoMessage(null, "INFO.CANDIDAT.ETAT_INCOMPLET.1");
            else
                addInfoMessage(null, "INFO.CANDIDAT.ETAT_INCOMPLET.1.FC");
            addInfoMessage(null, "INFO.CANDIDAT.ETAT_INCOMPLET.2");
        }
        // initialisation de la situation de l'individu si FC
        if (curInd.getRegimeInscription() instanceof FormationContinue)
            getSituationController().setIndSituation(
                    getDomainService().getIndSituation(curInd.getIndividu()));

        formulairesController.reset();
        return NavigationRulesConst.ACCUEIL_CANDIDAT;
    }

    /**
     * Go to generate numero Dossier.
     *
     * @return String
     */
    public String goSaveDossier() {
        addTheCurrentRoad(NavigationRulesConst.SAVE_DOSSIER,
                getString("INDIVIDU.INIT.CANDI"), getString("INDIVIDU.INIT.CANDI"));

        if (etatDossier != null && etatDossier.equals(DOSSIER_CREATE)) {
            // Get a random alphanumeric string for NumDosOPI
            GenNumDosOPI genNumDosOPI = new GenNumDosOPI(NB_CHAR_CODE);
            String newNumDosOPI = genNumDosOPI.generate();
            while (getDomainService().getIndividu(newNumDosOPI, null) != null) {
                newNumDosOPI = genNumDosOPI.generate();
            }
            this.pojoIndividu.getIndividu().setNumDossierOpi(newNumDosOPI);
        }
        return NavigationRulesConst.SAVE_DOSSIER;
    }

    /**
     * Go numero Dossier.
     *
     * @return String
     */
    public String goGetNumDossier() {
        return NavigationRulesConst.GET_NUM_DOSSIER;
    }

    /**
     * Callback to see all sutdents.
     *
     * @return String
     */
    public String goSeeAllEtudiants() {
        return NavigationRulesConst.DISPLAY_STUDENT;
    }


    /**
     * Callback to add a student.
     *
     * @return String
     */

    public String goAddEtudiant() {
        reset();
        // on initialise le régime d'inscription du candidat selon
        // le régime du gestionnaire
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        int codeRI = gest.getProfile().getCodeRI();
        pojoIndividu.setRegimeInscription(getRegimeIns().get(codeRI));
        return NavigationRulesConst.ADD_STUDENT;
    }


    /**
     * Callback to list of a students (Gestionnaire).
     *
     * @return String
     */
    public String goSendMailStudent() {
        pojoIndividu.getRegimeInscription().sendCreateDos(pojoIndividu.getIndividu());
        addInfoMessage(null, "INFO.CANDIDAT.MAIL_OK");
        return NavigationRulesConst.DISPLAY_STUDENT;
    }


    /**
     * add a student (by a gestionnaire manager).
     */
    public String addIndGestionnaire() {
        if (log.isDebugEnabled()) {
            log.debug("entering goAddIndGestionnaire() ");
        }
        if (ctrlEnterMinimum()) {
            // Get a random alphanumeric string for NumDosOPI
            GenNumDosOPI genNumDosOPI = new GenNumDosOPI(NB_CHAR_CODE);
            String newNumDosOPI = genNumDosOPI.generate();
            while (getDomainService().getIndividu(newNumDosOPI, null) != null) {
                newNumDosOPI = genNumDosOPI.generate();
            }
            this.pojoIndividu.getIndividu().setNumDossierOpi(newNumDosOPI);
            //pojoIndividu.setRegimeInscription(getRegimeIns()
            //	.get(Utilitaires.getCodeRIIndividu(pojoIndividu.getIndividu())));
            // put add action
            this.actionEnum.setWhatAction(this.actionEnum.getAddAction());
        }

        return null;
    }

    /**
     * @return a List de user ldap si ils sont enregistrés dans LDAP
     */
    public List<LdapUser> isInLdap(String attributName, String attribut) {
        //make filter
        String filter = "(" + attributName + "=" + attribut + ")";
        List<LdapUser> ldapManagers = ldapUserService.getLdapUsersFromFilter(filter);
        return ldapManagers;
    }


    /**
     * Initialisation de l'individu à partir de l'individu Apogée.
     * Si on a un individu dans Opi
     */
    public void initIndividuFromApogee(final Individu individuOPI, final Individu individuApogee) {
        if (log.isDebugEnabled()) {
            log.debug("entering initIndividuFromApogee(individuOPI "
                    + individuOPI + ", individuApogee " + individuApogee + " ) ");
        }
        if (individuOPI != null) {
            // si individuOPI != null, on est dans un cas de mise à jour de dossier
            etatDossier = DOSSIER_UPDATE;
            if (individuApogee != null && isRecupInfos) {

                String id = individuApogee.getCodeNNE();
                id += individuApogee.getCodeClefNNE();
                List<LdapUser> ldapManagers = isInLdap("supannCodeINE", id);

                // on met à jour individuOPI avec les données d'Apogée
                individuOPI.setCodeEtu(individuApogee.getCodeEtu());
                individuOPI.setCodeNNE(individuApogee.getCodeNNE());
                individuOPI.setCodeClefNNE(individuApogee.getCodeClefNNE());
                if (StringUtils.isNotBlank(individuApogee.getAdressMail())) {
                    individuOPI.setAdressMail(individuApogee.getAdressMail());
                }

                //On ne remplie pas l'email institutionnel (xxx@etu.fr) si étudiant pas dans ldap car il ne sera plus valide et retournera une erreur au moment de l'envoi bug 7693
                if (StringUtils.isNotBlank(individuApogee.getEmailAnnuaire()) && !ldapManagers.isEmpty()) {
                    individuOPI.setEmailAnnuaire(individuApogee.getEmailAnnuaire());
                } else {
                    individuOPI.setEmailAnnuaire(null);
                }

                if (StringUtils.isNotBlank(individuApogee.getNumeroTelPortable())) {
                    individuOPI.setNumeroTelPortable(individuApogee.getNumeroTelPortable());
                }
                // mise à jour de l'adresse
                Adresse adresseOPI = individuOPI.getAdresses().get(Constantes.ADR_FIX);
                Adresse adresseApo = individuApogee.getAdresses().get(Constantes.ADR_FIX);
                adresseOPI.setAdr1(adresseApo.getAdr1());
                adresseOPI.setAdr2(adresseApo.getAdr2());
                adresseOPI.setAdr3(adresseApo.getAdr3());
                adresseOPI.setCodPays(adresseApo.getCodPays());
                adresseOPI.setCodCommune(adresseApo.getCodCommune());
                adresseOPI.setLibComEtr(adresseApo.getLibComEtr());
                adresseOPI.setCodBdi(adresseApo.getCodBdi());
            }
            pojoIndividu.setIndividu(individuOPI);
            //TODO faire de mm si on n'est pas en maj de dossier ?
            //Better use Option<IndividuPojo> here or even better a NullObject patten on IndividuPojo to avoid returning null all the time
            if (getSessionController().getCurrentInd() == null){
                getSessionController().initCurrentInd(individuOPI.getNumDossierOpi(), individuApogee.getDateNaissance(), false, false);
            }
        } else {
            addInfoMessage(null, "INFO.CANDIDAT.FIND.ETU_R1");

            if (isRecupInfos) {
                String id = individuApogee.getCodeNNE();
                id += individuApogee.getCodeClefNNE();
                List<LdapUser> ldapManagers = isInLdap("supannCodeINE", id);

                //On vide l'email institutionnel (xxx@etu.fr) si étudiant pas dans ldap car il ne sera plus valide et retournera une erreur au moment de l'envoi bug 7693
                if (StringUtils.isNotBlank(individuApogee.getEmailAnnuaire()) && ldapManagers.isEmpty()) {
                    individuApogee.setEmailAnnuaire(null);
                }

                pojoIndividu.setIndividu(individuApogee);
            } else {
                reset();
                return;
            }
        }

        Individu individu = pojoIndividu.getIndividu();

        adressController.getFixAdrPojo().setAdresse(
                individu.getAdresses().get(Constantes.ADR_FIX));
        adressController.selectCpFix();

        //afin de pouvoir enregistrer l'individu separement des adresses
        //pojoIndividu.getIndividu().setAdresses(null);

        //init POJO
        initIndividuPojo();

        if (isRecupInfos && isRecupBac) {
            //bug 7428 Correction du bug empechant la recherche dossier côté candidat
            String codeClefNNE = individu.getCodeClefNNE();
            if (codeClefNNE != null)
                codeClefNNE = codeClefNNE.toUpperCase();
            // Get the IndBac data from Apogee
            List<IndBac> indBacs = getDomainApoService().getIndBacFromApogee(
                    individu.getCodeNNE(), codeClefNNE,
                    getSessionController().getCodEtu());
            if (indBacs != null) {
                indBacController.initIndBac(indBacs, true);
            } else if (individuOPI != null) {
                List<IndBac> indBacsOPI = new ArrayList<IndBac>();
                indBacsOPI.addAll(individuOPI.getIndBac());
                indBacController.initIndBac(indBacsOPI, false);
            }
        } else if (individuOPI != null) {
            List<IndBac> indBacsOPI = new ArrayList<IndBac>();
            indBacsOPI.addAll(individuOPI.getIndBac());
            indBacController.initIndBac(indBacsOPI, false);
        }

        if (isRecupInfos && isRecupCursus) {
            if (individuOPI == null) {
                List<IndCursusScol> indCurScol = getDomainApoService()
                        .getIndCursusScolFromApogee(individu);
                if (indCurScol != null) {
                    cursusController.initCursusList(indCurScol);
                }
            } else {
                if (individuOPI.getCursusScol() != null) {
                    cursusController.initCursusListFromApogee(individuOPI);
                }
            }
        } else if (individuOPI != null) {
            List<IndCursusScol> indCurScol = new ArrayList<>();
            indCurScol.addAll(individuOPI.getCursusScol());
            cursusController.initCursusList(indCurScol);
        }
    }

    public void useVoeuFilter(Boolean bool) {
        indRechPojo.setUseVoeuFilter(bool);
    }   
    
    public void useGestCommsFilter(Boolean bool) {
        indRechPojo.setUseGestCommsFilter(bool);
    }

    public void excludeWishProcessed(Boolean bool) {
        indRechPojo.setExcludeWishProcessed(bool);
    }
    
    /**
     * Charge les attributes des individus Pojo.
     */
    public void initIndividuPojo() {
        pojoIndividu.setDepartement(getDomainApoService().getDepartement(
                pojoIndividu.getIndividu().getCodDepPaysNaissance()));
        pojoIndividu.setPays(getDomainApoService().getPays(
                pojoIndividu.getIndividu().getCodPayNaissance()));
        pojoIndividu.setNationalite(getDomainApoService().getPays(
                pojoIndividu.getIndividu().getCodPayNationalite()));
    }

    /**
     * Charge les attribut des Pojo de pojoIndividu, d'adresse et de cursus et indBac.
     */
    public void initAllPojo() {
        //init individu
        initIndividuPojo();
        // init adress
        Adresse a = pojoIndividu.getIndividu()
                .getAdresses().get(Constantes.ADR_FIX);
        if (a != null) {
            adressController.init(a, false);
        }
        //init cursus Pro
        cursusController.initCursus(pojoIndividu.getIndividu().getCursus());
        cursusController.initCursusList(
                new ArrayList<>(
                        pojoIndividu.getIndividu().getCursusScol()));
        //init indBac
        indBacController.initIndBac(new ArrayList<>(pojoIndividu.getIndividu().getIndBac()), false);
    }
    
    /**
     * @return the dateDeNaissance in String format
     */
    public String getStringDateDeNaissance() {
    	if (getPojoIndividu().getIndividu().getDateNaissance() == null) {
            return "";
    	}
        return dateFormat.format(getPojoIndividu().getIndividu().getDateNaissance());
    }

    /**
     * Changes the regime d'inscription du candidat.
     */
    public void modifyRegimeInscription() {
        pojoIndividu = getCurrentInd();
        int codeRI = pojoIndividu.getRegimeInscription().getCode();
        // on retire la campagne en cours pour l'ancien régime
        Campagne camp = pojoIndividu.getCampagneEnServ(getDomainService());
        pojoIndividu.getIndividu().getCampagnes().remove(camp);
        // on ajoute celle du nouveau régime
        pojoIndividu.getIndividu().getCampagnes().add(
                getParameterService().getCampagneEnServ(codeRI));

        // on supprime les formulaires
        Map<VersionEtpOpi, IndFormulaire> indF =
                getParameterService().getIndFormulaires(pojoIndividu.getIndividu());

        if (indF != null && !indF.isEmpty()) {
            for (Map.Entry<VersionEtpOpi, IndFormulaire> vOpi : indF.entrySet()) {
                getParameterService().deleteIndFormulaire(vOpi.getValue(),
                        pojoIndividu.getIndividu().getNumDossierOpi(),
                        pojoIndividu.getRegimeInscription().getShortLabel());
            }
        }

        getDomainService().updateUser(pojoIndividu.getIndividu());

        // on supprime les voeux qu'il avait fait sur l'ancien régime
        for (IndVoeu voeu : pojoIndividu.getIndividu().getVoeux()) {
            getDomainService().deleteIndVoeu(voeu);
        }

    }

    /**
     * Save the individu only.
     */
    public void add() {
        if (log.isDebugEnabled()) {
            log.debug("entering add with individu = " + pojoIndividu.getIndividu());
        }
        pojoIndividu.setIndividu(
                getDomainService().add(pojoIndividu.getIndividu(), pojoIndividu.getIndividu().getNumDossierOpi()));
        int codeRI = pojoIndividu.getRegimeInscription().getCode();
        if (!StringUtils.isNotBlank(pojoIndividu.getIndividu().getState())) {
            //slt si l'etat est vide
            if (pojoIndividu.getRegimeInscription()
                    .getControlField().control(pojoIndividu.getIndividu())) {
                pojoIndividu.getIndividu().setState(EtatComplet.getCodeLabel());
            } else {
                pojoIndividu.getIndividu().setState(EtatIncomplet.getCodeLabel());
            }
        }

        // affiliation à la campagne en cours
        pojoIndividu.getIndividu().getCampagnes().add(getParameterService().getCampagneEnServ(codeRI));

        pojoIndividu.setIndividu(toUpperCaseAnyAttributs(pojoIndividu.getIndividu()));

        getDomainService().addUser(pojoIndividu.getIndividu());

        log.info("save to database the individu with the num dossier = " + pojoIndividu.getIndividu().getNumDossierOpi());
    }


    /**
     * Save the individu, his adress and cursus.
     *
     * @return to accueil
     */
    public String addFullInd() {
        if (log.isDebugEnabled())
            log.debug("entering addFullInd with individu = " + pojoIndividu.getIndividu());
        //On teste si le dossier n'a pas déja été créé.
        if (getDomainService().getIndividu(
                pojoIndividu.getIndividu().getNumDossierOpi(),
                pojoIndividu.getIndividu().getDateNaissance()) != null) {
            addErrorMessage(null, "ERROR.ACTION.DOSSIER.EXIST", pojoIndividu
                    .getIndividu().getNumDossierOpi());
            return NavigationRulesConst.GET_NUM_DOSSIER;
        }
        pojoIndividu.getIndividu().setState(EtatComplet.getCodeLabel());

        if (etatDossier != null && etatDossier.equals(DOSSIER_CREATE)) {
            add();
            adressController.addAdrFix(pojoIndividu.getIndividu());
            indBacController.add(pojoIndividu.getIndividu());
        } else {
            getActionEnum().setWhatAction(ActionEnum.UPDATE_ACTION);
            adressController.getActionEnum().setWhatAction(ActionEnum.UPDATE_ACTION);
            update();
            adressController.update(adressController.getFixAdrPojo());
            if (isRecupInfos && isRecupCursus) {
                cursusController.deleteCursusR1();
            }
        }
        cursusController.add(pojoIndividu.getIndividu());
        situationController.add(pojoIndividu.getIndividu());

        //Send email
        pojoIndividu.getRegimeInscription().sendCreateDos(pojoIndividu.getIndividu());

        getSessionController().initCurrentInd(
                pojoIndividu.getIndividu().getNumDossierOpi(),
                pojoIndividu.getIndividu().getDateNaissance(),
                false, false);

        addInfoMessage(null, "INFO.CANDIDAT.SAVE_OK");
        addInfoMessage(null, "INFO.CANDIDAT.SEND_MAIL.NUM_DOS");

        if (log.isDebugEnabled())
            log.debug("leaving add");
        return NavigationRulesConst.GET_NUM_DOSSIER;
    }

    /**
     * Initialisation de l'adresse.
     */
    public void initAdresse() {
        if (pojoIndividu.getIndividu().getAdresses().get(Constantes.ADR_FIX) != null)
            adressController.init(pojoIndividu.getIndividu().getAdresses().get(Constantes.ADR_FIX), false);
    }

    /**
     * Update the individu.
     */
    public void update() {
        if (getActionEnum().getWhatAction().equals(ActionEnum.UPDATE_ACTION) &&  ctrlEnterMail()) {
            final boolean haveToInit =
                    StringUtils.isNotBlank(pojoIndividu.getIndividu().getCodDepPaysNaissance()) ||
                            StringUtils.isNotBlank(pojoIndividu.getIndividu().getCodPayNaissance());

            pojoIndividu.setIndividu(toUpperCaseAnyAttributs(pojoIndividu.getIndividu()));

            // on ajoute éventuellement la nouvelle campagne correspondant au régime d'inscription
            if (pojoIndividu.getRegimeInscription() != null) {
                int codeRI = pojoIndividu.getRegimeInscription().getCode();
                Campagne campEnCours = getParameterService().getCampagneEnServ(codeRI);
                Campagne campDel = null;

                for (Campagne camp : pojoIndividu.getIndividu().getCampagnes())
                    if (camp.getCodAnu().equals(campEnCours.getCodAnu()))
                        campDel = camp;

                if (campDel == null) {
                    pojoIndividu.getIndividu().getCampagnes().add(campEnCours);
                } else {
                    if (campDel.equals(campEnCours)) {
                        // on retire la campagne en cours pour l'ancien régime
                        pojoIndividu.getIndividu().getCampagnes().remove(campDel);
                        // on ajoute celle du nouveau régime
                        pojoIndividu.getIndividu().getCampagnes().add(campEnCours);
                    }
                }
            }
            // on le met en service dans tous les cas
            pojoIndividu.getIndividu().setTemoinEnService(true);
            getDomainService().updateUser(pojoIndividu.getIndividu());

            // on remet à jour l'authenticator dans le cas où la date de naissance est modifié
            // si modification de la date de naissance, perte du currentUser
            getSessionController().getAuthenticator().storeManager(
                    getSessionController().getAuthenticator().getManager(),
                    pojoIndividu.getIndividu().getNumDossierOpi(),
                    pojoIndividu.getIndividu().getDateNaissance());


            if (haveToInit) {
                initIndividuPojo();
            }
            actionEnum.setWhatAction(ActionEnum.READ_ACTION);
        }

        // Update current Adress si champs ok
        if (adressController.getActionEnum()
                .getWhatAction().equals(ActionEnum.UPDATE_ACTION)
                && adressController.ctrlEnter(adressController
                .getFixAdrPojo().getAdresse(), true) && ctrlEnterMail()) {
            // maj de l'individu pour enregistrer son adresse mail
            getDomainService().updateUser(pojoIndividu.getIndividu());
            if (pojoIndividu.getIndividu()
                    .getAdresses().get(Constantes.ADR_FIX) == null) {
                adressController.addAdrFix(pojoIndividu.getIndividu());
            } else {
                adressController.update(adressController.getFixAdrPojo());
            }
        }
        if (StringUtils.equals(pojoIndividu.getIndividu().getState(), EtatIncomplet.getCodeLabel())) {
            //si l'etat est incomplet dans le cas d'un dossier candidat créé par un gestionnaire
            if (pojoIndividu.getRegimeInscription()
                    .getControlField().control(pojoIndividu.getIndividu())) {
                pojoIndividu.getIndividu().setState(EtatComplet.getCodeLabel());
                pojoIndividu.setEtat(EtatComplet);
                // maj de l'individu pour enregistrer l'état complet
                getDomainService().updateUser(pojoIndividu.getIndividu());
            }
        } 
    }

    /**
     * Delete the individu.
     */
    public void delete() {
        if (log.isDebugEnabled()) {
            log.debug("entering delete with individu = " + pojoIndividu.getIndividu());
        }
        // on supprime les formulaires
        Map<VersionEtpOpi, IndFormulaire> indF =
                getParameterService().getIndFormulaires(pojoIndividu.getIndividu());

        RegimeInscription regime = getRegimeIns().get(
                Utilitaires.getCodeRIIndividu(pojoIndividu.getIndividu(),
                        getDomainService()));

        if (indF != null && !indF.isEmpty()) {
            for (Map.Entry<VersionEtpOpi, IndFormulaire> vOpi : indF.entrySet()) {
                getParameterService().deleteIndFormulaire(vOpi.getValue(),
                        pojoIndividu.getIndividu().getNumDossierOpi(),
                        regime.getShortLabel());
            }
        }

        // on supprime les rendez vous s'il y en a
        //init hib proxy adresse
        getDomainService().initOneProxyHib(pojoIndividu.getIndividu(),
                pojoIndividu.getIndividu().getVoeux(), IndVoeu.class);
        for (IndVoeu indVoeu : pojoIndividu.getIndividu().getVoeux()) {
            IndividuDate individuDate = getDomainService().getIndividuDate(indVoeu);
            if (individuDate != null) {
                getDomainService().deleteIndividuDate(individuDate);
            }
        }
        // de même pour les voeux archivés
        for (IndVoeu indVoeu : pojoIndividu.getIndividu().getArchVoeux()) {
            IndividuDate individuDate = getDomainService().getIndividuDate(indVoeu);
            if (individuDate != null) {
                getDomainService().deleteIndividuDate(individuDate);
            }
        }

        // on supprime la situation de l'individu si FC
        IndSituation indSituation = getDomainService().getIndSituation(pojoIndividu.getIndividu());
        if (indSituation != null) {
            getDomainService().deleteIndSituation(indSituation);
        }

        getDomainService().deleteUser(pojoIndividu.getIndividu());

        reset();

        addInfoMessage(null, "INFO.DELETE.SUCCESS");

        if (log.isDebugEnabled()) {
            log.debug("leaving delete");
        }
    }

    /**
     * Return the civility items.
     *
     * @return List<SelectItem>
     */
    public List<SelectItem> getCiviliteItems() {
        List<SelectItem> s = new ArrayList<>();
        s.add(new SelectItem("", ""));
        s.add(new SelectItem(Constantes.COD_SEXE_FEMININ, getString(Constantes.I18N_CIV_MM)));
        s.add(new SelectItem(Constantes.COD_SEXE_MASCULIN, getString(Constantes.I18N_CIV_MR)));

        return s;
    }


    /**
     * The selected pays.
     */
    public void selectPay() {
        String codePay = pojoIndividu.getIndividu().getCodPayNaissance();
        //SI Pays != france  on remet à null le département.
        if (!StringUtils.equals(codePay, adressController.getCodeFrance())) {
            pojoIndividu.getIndividu().setCodDepPaysNaissance(null);
        }
        //modification de la nationalite
        pojoIndividu.getIndividu().setCodPayNationalite(codePay);
    }


	/* ### ALL CONTROL ####*/

    /**
     * Control the pojoIndividu attributes.
     * @deprecated for form validation should migrate to JSR-303 and JSF2.x mechanism
     * cf:
     * http://fr.slideshare.net/martyhall/jsf-2-tutorial-validating-user-input-form-field-validation
     * http://www.javacodegeeks.com/2012/12/easier-multi-field-validation-with-jsf-2-0.html
     * http://www.mkyong.com/jsf2/multi-components-validator-in-jsf-2-0/}
     *
     *
     * @return Boolean
     */
    private Boolean ctrlEnterMinimum() {
        Boolean ctrlOk = true;

        // Check individu's fields
        Individu ind = pojoIndividu.getIndividu();

        if (ind.getSexe() == null || ind.getSexe().isEmpty()) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.CIVILITE"));
            ctrlOk = false;
        }
        if (!StringUtils.isNotBlank(ind.getNomPatronymique())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.NOM"));
            ctrlOk = false;
        }
        if (!StringUtils.isNotBlank(ind.getPrenom())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.PRENOM"));
            ctrlOk = false;
        }

        // check individu mail fields
        if (!ctrlEnterMail()) {
            ctrlOk = false;
        }

        return ctrlOk;
    }

    /**
     * Control the pojoIndividu attributes.
     * @deprecated for form validation should migrate to JSR-303 and JSF2.x mechanism
     * cf:
     * http://fr.slideshare.net/martyhall/jsf-2-tutorial-validating-user-input-form-field-validation
     * http://www.javacodegeeks.com/2012/12/easier-multi-field-validation-with-jsf-2-0.html
     * http://www.mkyong.com/jsf2/multi-components-validator-in-jsf-2-0/}
     *
     *
     *
     * @return Boolean
     */
    private Boolean ctrlEnterMail() {
        Boolean ctrlOk = true;
        // Check individu's fields
        Individu ind = pojoIndividu.getIndividu();

        if (!StringUtils.isNotBlank(ind.getAdressMail())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.MAIL"));
            ctrlOk = false;
        } else if (!Utilitaires.isFormatEmailValid(ind.getAdressMail())) {
            addErrorMessage(null, "ERROR.FIELD.EMAIL");
            ctrlOk = false;
        } else if (!getDomainService().emailIsUnique(ind)) {
            //adress mail doit etre unique
            addErrorMessage(null, "ERROR.FIELD.EMAIL.EXIST");
            ctrlOk = false;
        }
        // controle sur la double saisie du mail
        if (!StringUtils.isNotBlank(checkEmail)) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.MAIL_CHECK"));
            ctrlOk = false;
        } else if (ind.getAdressMail().compareTo(checkEmail) != 0) {
            addErrorMessage(null, "ERROR.FIELD.EMAIL.BAD_CHECK");
            ctrlOk = false;
        }
        return ctrlOk;
    }


    /**
     * @deprecated for form validation should migrate to JSR-303 and JSF2.x mechanism
     * cf:
     * http://fr.slideshare.net/martyhall/jsf-2-tutorial-validating-user-input-form-field-validation
     * http://www.javacodegeeks.com/2012/12/easier-multi-field-validation-with-jsf-2-0.html
     * http://www.mkyong.com/jsf2/multi-components-validator-in-jsf-2-0/}
     *
     *
     * @return Boolean
     */
    private Boolean ctrlEnter() {
        Boolean ctrlOk = ctrlEnterMinimum();

        // Check individu's other fields
        Individu ind = pojoIndividu.getIndividu();
        if (!StringUtils.isNotBlank(ind.getCodPayNaissance())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.PAY_NAI"));
            ctrlOk = false;
        }
        if (ind.getCodPayNaissance().equals(Constantes.CODEFRANCE)
                && !StringUtils.isNotBlank(ind.getCodDepPaysNaissance())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.DEP_NAI"));
            ctrlOk = false;
        }
        if (!StringUtils.isNotBlank(ind.getVilleNaissance())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.VIL_NAI"));
            ctrlOk = false;
        }
        if (!StringUtils.isNotBlank(ind.getCodPayNationalite())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.NAT"));
            ctrlOk = false;
        }
        // Check fix adress fields
        if (!adressController.ctrlEnter(adressController.getFixAdrPojo().getAdresse(), true)) {
            ctrlOk = false;
        }
        return ctrlOk;
    }


    /**
     * Control if the added user is unique.
     * @deprecated for form validation should migrate to JSR-303 and JSF2.x mechanism
     * cf:
     * http://fr.slideshare.net/martyhall/jsf-2-tutorial-validating-user-input-form-field-validation
     * http://www.javacodegeeks.com/2012/12/easier-multi-field-validation-with-jsf-2-0.html
     * http://www.mkyong.com/jsf2/multi-components-validator-in-jsf-2-0/}
     *
     *
     *
     */
    private Boolean ctrlUnicite() {
        Boolean ctrlOk = true;
        // Check individu's other fields
        if (etatDossier != null && etatDossier.equals(DOSSIER_CREATE)) {
            Individu individu = pojoIndividu.getIndividu();
            if (!getDomainService().canInsertIndividu(individu)) {
                addErrorMessage(null, "ERROR.INDIVIDU_NOT_UNIQUE");
                ctrlOk = false;
            }
        }
        return ctrlOk;
    }

    /**
     * Control the pojoIndividu attributes.
     * @deprecated for form validation should migrate to JSR-303 and JSF2.x mechanism
     * cf:
     * http://fr.slideshare.net/martyhall/jsf-2-tutorial-validating-user-input-form-field-validation
     * http://www.javacodegeeks.com/2012/12/easier-multi-field-validation-with-jsf-2-0.html
     * http://www.mkyong.com/jsf2/multi-components-validator-in-jsf-2-0/}
     *
     *
     * @return Boolean
     */
    private Boolean ctrlSearch() {
        Boolean ctrlOk = true;
        // Check individu's fields
        Individu ind = pojoIndividu.getIndividu();

        if (StringUtils.isBlank(ind.getNomPatronymique())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.NOM"));
            ctrlOk = false;
        }
        if (StringUtils.isBlank(ind.getPrenom())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.PRENOM"));
            ctrlOk = false;
        }
        if (ind.getDateNaissance() == null) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.DATE_NAI"));
            ctrlOk = false;
        }
        if (StringUtils.isBlank(ind.getCodPayNaissance())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.PAY_NAI"));
            ctrlOk = false;
        }else {
            if (ind.getCodPayNaissance().equals(Constantes.CODEFRANCE)
                && StringUtils.isBlank(ind.getCodDepPaysNaissance())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.DEP_NAI"));
            ctrlOk = false;
            }
        }
        return ctrlOk;
    }
    
    /**
     * Upper any attributs in Individu.
     *
     * @return Individu
     */
    private Individu toUpperCaseAnyAttributs(final Individu i) {
        //UPPER CASE any attributs
        if (StringUtils.isNotBlank(i.getNomPatronymique())) {
            i.setNomPatronymique(i.getNomPatronymique().toUpperCase());
        }
        if (StringUtils.isNotBlank(i.getNomUsuel())) {
            i.setNomUsuel(i.getNomUsuel().toUpperCase());
        }
        i.setPrenom(i.getPrenom().toUpperCase());
        if (StringUtils.isNotBlank(i.getPrenom2())) {
            i.setPrenom2(i.getPrenom2().toUpperCase());
        }
        if (StringUtils.isNotBlank(i.getVilleNaissance())) {
            i.setVilleNaissance(
                    i.getVilleNaissance().toUpperCase());
        }
        if (StringUtils.isNotBlank(i.getCodeClefNNE())) {
            i.setCodeClefNNE(
                    i.getCodeClefNNE().toUpperCase());
        }
        return i;
    }

    public IndividuPojo getPojoIndividu() {
        return pojoIndividu;
    }

    public void setPojoIndividu(final IndividuPojo pojoIndividu) {
        this.pojoIndividu = pojoIndividu;
    }

    public void setAdressController(final AdressController adressController) {
        this.adressController = adressController;
    }

    public void setCursusController(final CursusController cursusController) {
        this.cursusController = cursusController;
    }

    public void setIndBacController(final IndBacController indBacController) {
        this.indBacController = indBacController;
    }

    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    public AdressController getAdressController() {
        return adressController;
    }

    public CursusController getCursusController() {
        return cursusController;
    }

    public IndBacController getIndBacController() {
        return indBacController;
    }

    public SituationController getSituationController() {
        return situationController;
    }

    public void setSituationController(final SituationController situationController) {
        this.situationController = situationController;
    }

    public void setFormulairesController(final FormulairesController formulairesController) {
        this.formulairesController = formulairesController;
    }

    public String getEtatDossier() {
        return etatDossier;
    }

    public void setEtatDossier(final String etatDossier) {
        this.etatDossier = etatDossier;
    }

    public boolean getIsRecupInfos() {
        return isRecupInfos;
    }

    public void setIsRecupInfos(final boolean isRecupInfos) {
        this.isRecupInfos = isRecupInfos;
    }

    public boolean getIsRecupCursus() {
        return isRecupCursus;
    }

    public void setIsRecupCursus(final boolean isRecupCursus) {
        this.isRecupCursus = isRecupCursus;
    }

    public boolean getIsRecupBac() {
        return isRecupBac;
    }

    public void setIsRecupBac(final boolean isRecupBac) {
        this.isRecupBac = isRecupBac;
    }

    public Date getToday() {
        return new Date();
    }
    
    public List<SelectItem> getListeAnneeNaissance() {
        return listeAnneeNaissance;
    }

    public String getCheckEmail() {
        return checkEmail;
    }

    public void setCheckEmail(final String checkEmail) {
        this.checkEmail = checkEmail;
    }

    public LdapUserService getLdapUserService() {
        return ldapUserService;
    }

    public void setLdapUserService(LdapUserService ldapUserService) {
        this.ldapUserService = ldapUserService;
    }

    public boolean isRenderTable() {
        return renderTable;
    }

    public void setRenderTable(boolean renderTable) {
        this.renderTable = renderTable;
    }

    public void doRenderTable() {
        renderTable = true;
    }

    public void doNotRenderTable() {
        if (!FacesContext.getCurrentInstance().isPostback())
            this.renderTable = false;
    }

    public IndRechPojo getIndRechPojo() { return indRechPojo; }

    public LazyDataModel<Individu> getIndLDM() { return indLDM; }

    public void setListCmiByRight(boolean listCmiByRight) {
        this.listCmiByRight = listCmiByRight;
    }

    public boolean getListCmiByRight() {
        return listCmiByRight;
    }
}
