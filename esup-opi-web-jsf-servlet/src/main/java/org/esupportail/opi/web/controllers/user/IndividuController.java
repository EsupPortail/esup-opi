/**
 * 
 */
package org.esupportail.opi.web.controllers.user;



import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;

import fj.F;
import fj.F2;
import fj.F5;
import fj.P2;
import fj.data.Option;
import fj.data.Stream;
import org.apache.commons.lang.StringUtils;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.etat.EtatComplet;
import org.esupportail.opi.domain.beans.etat.EtatInComplet;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.User;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.domain.beans.user.situation.IndSituation;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.utils.GenNumDosOPI;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.paginator.IndividuPaginator;
import org.esupportail.opi.web.beans.parameters.FormationContinue;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndRechPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.utils.NavigationRulesConst;
import org.esupportail.opi.web.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.opi.web.controllers.formation.FormulairesController;
import org.esupportail.opi.web.utils.fj.Conversions;
import org.esupportail.opi.web.utils.paginator.LazyDataModel;
import org.primefaces.model.SortOrder;

import static fj.data.Option.fromNull;
import static fj.data.Option.fromString;
import static fj.data.Option.none;
import static fj.data.Stream.iterableStream;
import static fj.data.Stream.join;
import static fj.data.Stream.single;
import static org.esupportail.opi.web.utils.paginator.LazyDataModel.lazyModel;


/**
 * @author cleprous
 *
 */
public class IndividuController extends AbstractAccessController {

	/**
	 * The serialization id.
	 */
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
	private static final  String DOSSIER_CREATE = "DOSSIER_CREATE";
	
	/**
	 * The state when update a dossier.
	 */
	private static final String DOSSIER_UPDATE = "DOSSIER_UPDATE";
	
	/**
	 * Static list used to store the years which can be used for the birthdates.
	 */
	private static List<SelectItem> listeAnneeNaissance = new ArrayList<SelectItem>();
	
	
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



	/*
	 ******************* PROPERTIES ******************* */

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
	 * see {@link IndividuPaginator}.
	 */
	private IndividuPaginator individuPaginator;

	/**
	 * see {@link FormulairesController}.
	 */
	private FormulairesController formulairesController;

	/**
	 * State of the dossier (create or update).
	 */
	private String etatDossier;
	
	/**
	 * Used to Store the birth day.
	 */
	private String jourNaissance;
	/**
	 * Used to store the birth month.
	 */
	private String moisNaissance;
	/**
	 * Use to store the birth Year.
	 */
	private String anneeNaissance;
	/**
	 * Variable to check the mail address.
	 */
	private String checkEmail;

    private final LazyDataModel<Individu> indLDM = lazyModel(
            new F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Stream<Individu>>>() {
                public P2<Long, Stream<Individu>> f(
                        Integer first, Integer pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
                    // le gestionnaire courant
                    final SessionController sessionCont = getSessionController();
                    final User user = sessionCont.getCurrentUser();
                    final Gestionnaire gest = !(user instanceof Gestionnaire) ?
                            sessionCont.getManager() : (Gestionnaire) user;

                    // les filtres :
                    IndRechPojo indRechPojo = individuPaginator.getIndRechPojo();
                    // 1. les numdossier, nom, prenom
                    filters.put("numDossierOpi", fromString(indRechPojo.getNumDossierOpiRecherche()).orSome(""));
                    filters.put("nomPatronymique", fromString(indRechPojo.getNomRecherche()).orSome(""));
                    filters.put("prenom", fromString(indRechPojo.getPrenomRecherche()).orSome(""));

                    // 3. les étapes (TraitementCmi) de la commission
                    final Integer idCmi = indRechPojo.getIdCmi();
                    final Stream<Commission> cmis = (idCmi != null) ?
                            single(getParameterService().getCommission(idCmi, null)) :
                            iterableStream(getDomainApoService().getListCommissionsByRight(gest, true));

                    final Set<TraitementCmi> trtCmis = new HashSet<TraitementCmi>(
                            cmis.bind(new F<Commission, Stream<TraitementCmi>>() {
                                public Stream<TraitementCmi> f(Commission com) {
                                    return join(fromNull(com.getTraitementCmi()).toStream().map(
                                            Conversions.<TraitementCmi>setToStream_()));
                                }
                            }).toCollection());

                    // 4. les régimes d'inscription
                    Set<Integer> listCodesRI = new HashSet<Integer>(
                            iterableStream(fromNull(indRechPojo.getListeRI()).orSome(
                                    new HashSet<RegimeInscription>())).map(new F<RegimeInscription, Integer>() {
                                public Integer f(RegimeInscription ri) {
                                    return ri.getCode();
                                }}).toCollection());

                    Option<Boolean> boolNone = none();

                    return getDomainService().sliceOfInd(
                            new Long(first), new Long(pageSize), sortField, sortOrder, filters,
                            new HashSet<TypeDecision>(), boolNone, boolNone, Option.<String>none(), trtCmis, listCodesRI);
                }
            },
            new F2<String, Individu, Boolean>() {
                public Boolean f(String rowKey, Individu individu) {
                    return individu.getId().equals(rowKey);
                }
            }
    );

	/*
	 ******************* INIT ************************* */



	/**
	 * Constructors.
	 */
	public IndividuController() {
		super();

	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		actionEnum = new ActionEnum();

		pojoIndividu = new IndividuPojo(getI18nService());
		pojoIndividu.getIndividu().setCodPayNaissance(Constantes.CODEFRANCE);
		pojoIndividu.getIndividu().setCodPayNationalite(Constantes.CODEFRANCE);
		
		checkEmail = "";
		
		jourNaissance = "";
		moisNaissance = "";
		anneeNaissance = "";
		
		adressController.reset();
		cursusController.reset();
		indBacController.reset();
		situationController.reset();
	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
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
		Assert.notNull(this.individuPaginator, 
			"property individuPaginator of class " + this.getClass().getName() + CAN_NO_BE_NULL);
		Assert.notNull(this.formulairesController, 
			"property formulairesController of class " + this.getClass().getName() + CAN_NO_BE_NULL);
		
		reset();
		
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndividuController#" + hashCode() + "[pojoIndividu =" + pojoIndividu + "]";
	}

	/*
	 ******************* CALLBACK ********************** */


	/**
	 * Callback to search a Rennes1 student with his NNE code.
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
			Individu individuOPI = null;
			Individu individuApogee = null;
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

			if (individuApogee == null && individuOPI ==  null) {
				addErrorMessage(null, "ERROR.FIELD.NNE_KO");
			} else {
				initIndividuFromApogee(individuOPI, individuApogee);
			}
		} else if (pojoIndividu.getIsUsingSearch()) {
			// cas où l'utilisateur fait une recherche
			// on recherche l'individu
			if (ctrlSearch()) {
				Individu searchInd  = toUpperCaseAnyAttributs(pojoIndividu.getIndividu());
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
	 * @return String 
	 */
	public String goSeeAccount() {
		pojoIndividu = getCurrentInd();
		actionEnum.setWhatAction(ActionEnum.READ_ACTION);
		//init individu
		initIndividuPojo();
		checkEmail = pojoIndividu.getIndividu().getAdressMail();
		initChampsSelectAnneeNaissance();
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
	 * @return String 
	 */
	public String goSeeCursus() {
		pojoIndividu = getCurrentInd();
		indBacController.initIndBac(
				new ArrayList<IndBac>(
						pojoIndividu.getIndividu().getIndBac()), false);

		return NavigationRulesConst.SEE_CURSUS;
	}

	/**
	 * Callback to add a account.
	 * @return String 
	 */
	public String goAddAccount() {
		// on mémorise le codeRI présent en session
		pojoIndividu.setRegimeInscription(getSessionController().getRegimeInsUser());
		addTheCurrentRoad(NavigationRulesConst.ADD_ACCOUNT, 
				getString("INDIVIDU.COORD"), getString("INDIVIDU.COORD"));	
		initChampsSelectAnneeNaissance();
		
		return NavigationRulesConst.ADD_ACCOUNT;
	}
	
	
	

	
	/**
	 * Callback to add ind bac or a situation.
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
	 * @return String
	 */
	public String goSeeOneIndividu() {
		//put true boolean isManager attribute in indPojo 
		//put the boolean canUpdateStudent atttribute in indPojo 
		Set<Commission> rightOnCmi = new HashSet<Commission>(getDomainApoService().getListCommissionsByRight(
				getCurrentGest(), true));
		getSessionController().initCurrentInd(
				pojoIndividu.getIndividu().getNumDossierOpi(),
				pojoIndividu.getIndividu().getDateNaissance(),
				true,
				// TODO : change asGestionnaireRightsOnStudent to take a List ?
				getDomainService().asGestionnaireRightsOnStudent(
						pojoIndividu.getIndividu().getVoeux(),
						rightOnCmi)
		);
		if (getCurrentInd().getEtat() instanceof EtatInComplet) {
			//on informe l'individu qu'il doit completer sur dossier avant de deposer de voeux
			if (!getSessionController().getCurrentInd()
					.getRegimeInscription().getDisplayInfoFC()) {
				addInfoMessage(null, "INFO.CANDIDAT.ETAT_INCOMPLET.1");
			} else {
				addInfoMessage(null, "INFO.CANDIDAT.ETAT_INCOMPLET.1.FC");
			}
			addInfoMessage(null, "INFO.CANDIDAT.ETAT_INCOMPLET.2");

		}
		// initialisation de la situation de l'individu si FC
		if (getCurrentInd().getRegimeInscription() instanceof FormationContinue) {
			getSituationController().setIndSituation(
					getDomainService().getIndSituation(getCurrentInd().getIndividu()));
		}

		formulairesController.reset();
		return NavigationRulesConst.ACCUEIL_CANDIDAT;
	}

	/**
	 * Go to generate numero Dossier.
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
	 * @return String
	 */
	public String goGetNumDossier() {
		return NavigationRulesConst.GET_NUM_DOSSIER;
	}

	/**
	 * CallBack to the candidat.
	 * @return String
	 */
	public String goSeeCandidats() {
		//init the filtre
		individuPaginator.filtreRechercheEtudiants();
		individuPaginator.forceReload();
		//comment the 01/04/2009
		//individuPaginator.reset();
		return NavigationRulesConst.DISPLAY_FOUND_STUDENT;
	}



	/**
	 * Callback to see all sutdents.
	 * @return String
	 */
	public String goSeeAllEtudiants() {
		individuPaginator.allStudentsFilter();
		individuPaginator.forceReload();
		return NavigationRulesConst.DISPLAY_STUDENT;
	}


	/**
	 * Callback to add a student.
	 * @return String
	 */

	public String goAddEtudiant() {
		reset();
		// on initialise le régime d'inscription du candidat selon
		// le régime du gestionnaire
		Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
		int codeRI = gest.getProfile().getCodeRI();
		pojoIndividu.setRegimeInscription(getRegimeIns().get(codeRI));
		initChampsSelectAnneeNaissance();
		return NavigationRulesConst.ADD_STUDENT;
	}


	/**
	 * Callback to list of a students (Gestionnaire).
	 * @return String
	 */
	public String goSendMailStudent() {
		//send the mail


		pojoIndividu.getRegimeInscription().sendCreateDos(pojoIndividu.getIndividu());
		//COMMENT the 21/10/2009 to DELELTE
		//		String civilite = Utilitaires.getCivilite(
		//				getI18nService(), pojoIndividu.getIndividu().getSexe());
		//		//Send email
		//		String htmlBody = getString("MAIL.CREATE.DOS_IND.HTMLTEXT_BODY", 
		//				civilite, 
		//				pojoIndividu.getIndividu().getNumDossierOpi());
		//		Utilitaires.sendEmailIndividu(
		//				getString("MAIL.CREATE.DOS_IND.SUBJECT"), 
		//				htmlBody, null, pojoIndividu.getIndividu(),
		//				smtpService, getI18nService());


		addInfoMessage(null, "INFO.CANDIDAT.MAIL_OK");
		return NavigationRulesConst.DISPLAY_STUDENT;
	}
	
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * add a student (by a gestionnaire manager).
	 * @return String
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
	 * @param attributName
	 * @param attribut
	 * @return a List de user ldap si ils sont enregistrés dans LDAP
	 */
	public List<LdapUser> isInLdap(String attributName, String attribut){
		//make filter
		String filter = "("+attributName+"=" + attribut + ")";				
		List<LdapUser> ldapManagers = ldapUserService.getLdapUsersFromFilter(filter);
		return ldapManagers;
	}


	/**
	 * Initialisation de l'individu à partir de l'individu Apogée.
	 * Si on a un individu dans Opi 
	 * @param individuOPI
	 * @param individuApogee
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
				}else{
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
			if (codeClefNNE!=null)
				codeClefNNE=codeClefNNE.toUpperCase();
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
			List<IndCursusScol> indCurScol = new ArrayList<IndCursusScol>();
			indCurScol.addAll(individuOPI.getCursusScol());
			cursusController.initCursusList(indCurScol);
		}
	}

	
	
	/**
	 * Charge les attributes des individus Pojo.
	 */
	public void initIndividuPojo() {
//		pojoIndividu.setDepartement(
//				getBusinessCacheService().getDepartement(
//						pojoIndividu.getIndividu().getCodDepPaysNaissance()));
		pojoIndividu.setDepartement(getDomainApoService().getDepartement(pojoIndividu.getIndividu().getCodDepPaysNaissance()));
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
				new ArrayList<IndCursusScol>(
						pojoIndividu.getIndividu().getCursusScol()));

		//init indBac
		indBacController.initIndBac(new ArrayList<IndBac>(pojoIndividu.getIndividu().getIndBac()), false);


	}
	
	/**
	 * Méthode utilisée pour initialiser la liste des années de naissance.
	 */
	private void initListeAnneeNaissance() {
		Campagne campEnCours = null;
		int anneeEnCours = DEFAULT_CURRENT_YEAR;
		if (pojoIndividu.getRegimeInscription() != null) {
			int codeRI = pojoIndividu.getRegimeInscription().getCode();
			campEnCours = getParameterService().getCampagneEnServ(codeRI);
			anneeEnCours = Integer.parseInt(campEnCours.getCodAnu());
		}
		listeAnneeNaissance.add(new SelectItem("", ""));
		for (int i = anneeEnCours - NB_YEAR_FIRST; i > anneeEnCours
				- (NB_YEAR_FIRST + NB_YEAR_LAST); i--) {
			listeAnneeNaissance.add(new SelectItem(String.valueOf(i), String
					.valueOf(i)));
		}
	}
	
	/**
	 * Méthode permettant d'initialiser les champs Select utilisés pour la
	 * saisie de la date de naissance d' un individu.
	 */
	private void initChampsSelectAnneeNaissance() {
		//On initialise la liste des années de naissance si elle est vide.
		if (listeAnneeNaissance.isEmpty()) {
			initListeAnneeNaissance();
		}
		// Si l'individu a déja une date de naissance on initialise les champs
		// select avec celle-ci.
		if (getPojoIndividu().getIndividu().getDateNaissance() != null) {
			 Calendar calendar = Calendar.getInstance();
		     calendar.setTime(pojoIndividu
						.getIndividu().getDateNaissance());
			jourNaissance = StringUtils.leftPad(String.valueOf(calendar
					.get(Calendar.DAY_OF_MONTH)), 2, '0');
			moisNaissance = StringUtils.leftPad(String.valueOf(calendar
					.get(Calendar.MONTH) + 1), 2, '0');
			anneeNaissance = String.valueOf(calendar.get(Calendar.YEAR));
		}

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
				(Individu) getDomainService().add(
						pojoIndividu.getIndividu(), 
						pojoIndividu.getIndividu().getNumDossierOpi()));
		int codeRI = pojoIndividu.getRegimeInscription().getCode();
		if (!StringUtils.isNotBlank(pojoIndividu.getIndividu().getState())) {
			//slt si l'etat est vide
			if (pojoIndividu.getRegimeInscription()
					.getControlField().control(pojoIndividu.getIndividu())) {
				pojoIndividu.getIndividu().setState(EtatComplet.I18N_STATE_COMPLET);
			} else {
				pojoIndividu.getIndividu().setState(EtatInComplet.I18N_STATE_INCOMPLET);
			}
		}

		// affiliation à la campagne en cours

		pojoIndividu.getIndividu().getCampagnes().add(
				getParameterService().getCampagneEnServ(codeRI));

		pojoIndividu.setIndividu(toUpperCaseAnyAttributs(pojoIndividu.getIndividu()));


		getDomainService().addUser(pojoIndividu.getIndividu());

		log.info("save to database the individu with the num dossier = " 
				+ pojoIndividu.getIndividu().getNumDossierOpi());
	}


	/**
	 * Save the individu, his adress and cursus. 
	 * @return to accueil
	 */
	public String addFullInd() {
		if (log.isDebugEnabled()) {
			log.debug("entering addFullInd with individu = " + pojoIndividu.getIndividu());
		}
		
//		getDomainService().initOneProxyHib(pojoIndividu.getIndividu(), 
//				pojoIndividu.getIndividu().getCampagnes(), Campagne.class);

		//l'etat est forcement complet
		
		
		//On teste si le dossier a pas déja été créé.
		if (getDomainService().getIndividu(
				pojoIndividu.getIndividu().getNumDossierOpi(),
				pojoIndividu.getIndividu().getDateNaissance()) != null) {
			addErrorMessage(null, "ERROR.ACTION.DOSSIER.EXIST", pojoIndividu
					.getIndividu().getNumDossierOpi());
			return NavigationRulesConst.GET_NUM_DOSSIER;
		}
		
		pojoIndividu.getIndividu().setState(EtatComplet.I18N_STATE_COMPLET);

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

		pojoIndividu.getRegimeInscription()
				.sendCreateDos(pojoIndividu.getIndividu());

		getSessionController().initCurrentInd(
				pojoIndividu.getIndividu().getNumDossierOpi(),
				pojoIndividu.getIndividu().getDateNaissance(),
				false, false);

		addInfoMessage(null, "INFO.CANDIDAT.SAVE_OK");
		addInfoMessage(null, "INFO.CANDIDAT.SEND_MAIL.NUM_DOS");

		if (log.isDebugEnabled()) {
			log.debug("leaving add");
		}
		return NavigationRulesConst.GET_NUM_DOSSIER;
	}
	
	/**
	 * Initialisation de l'adresse. 
	 */
	public void initAdresse() {
		if (pojoIndividu.getIndividu().getAdresses().get(Constantes.ADR_FIX) != null) {
			adressController.init(
					pojoIndividu.getIndividu()
					.getAdresses().get(Constantes.ADR_FIX), false);
		}
	}
	
	/**
	 * Update the individu. 
	 */
	public void update() {
		if (getActionEnum().getWhatAction().equals(ActionEnum.UPDATE_ACTION)
				&& ctrlEnterMinimum()) {
			boolean haveToInit = false;
			if ((StringUtils.isNotBlank(pojoIndividu.getIndividu()
					.getCodDepPaysNaissance()))
					|| StringUtils.isNotBlank(pojoIndividu.getIndividu()
							.getCodPayNaissance())) {
				haveToInit = true;
			}

			pojoIndividu.setIndividu(toUpperCaseAnyAttributs(pojoIndividu.getIndividu()));

			// on ajoute éventuellement la nouvelle campagne correspondant au régime d'inscription
			if (pojoIndividu.getRegimeInscription() != null) {
				int codeRI = pojoIndividu.getRegimeInscription().getCode();
				Campagne campEnCours = getParameterService().getCampagneEnServ(codeRI);
				Campagne campDel = null;

				for (Campagne camp : pojoIndividu.getIndividu().getCampagnes()) {
					if (camp.getCodAnu().equals(campEnCours.getCodAnu())) {
						campDel = camp;
					}
				}
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

			

			if (haveToInit) { initIndividuPojo(); }
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
	 * @return List< SelectItem>
	 */
	public List<SelectItem> getCiviliteItems() {
		List<SelectItem> s = new ArrayList<SelectItem>();
		s.add(new SelectItem("", ""));
		s.add(new SelectItem(Constantes.COD_SEXE_FEMININ, getString(Constantes.I18N_CIV_MM)));
		s.add(new SelectItem(Constantes.COD_SEXE_MASCULIN, getString(Constantes.I18N_CIV_MR)));

		return s;
	}


	/**
	 * The selected pays.
	 * @param event
	 */
	public void selectPay(final ValueChangeEvent event) {
		String codePay = (String) event.getNewValue();
		pojoIndividu.getIndividu().setCodPayNaissance(codePay);
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
		if (!(StringUtils.isNotBlank(jourNaissance)
				&& StringUtils.isNotBlank(moisNaissance) && StringUtils
				.isNotBlank(anneeNaissance))) {
			addErrorMessage(null, Constantes.I18N_EMPTY,
					getString("INDIVIDU.DATE_NAI_COURT"));
			ctrlOk = false;
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat(Constantes.DATE_SHORT_FORMAT);
			sdf.setLenient(false);
			try {
				ind.setDateNaissance(sdf.parse(jourNaissance + moisNaissance
						+ anneeNaissance));
			} catch (ParseException e) {
				addErrorMessage(null, "ERROR.FIELD.DAT_NAISS.INEXIST");
				ctrlOk = false;
			}
		}
		
		// check individu mail fields
		if (!ctrlEnterMail()) {
			ctrlOk = false;
		}
				
		return ctrlOk;
	}
	
	/**
	 * Control the pojoIndividu attributes.
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
	 * @return Boolean
	 */
	private Boolean ctrlEnter() {
		Boolean ctrlOk = true;

		// check individu commun's fields
		ctrlOk = ctrlEnterMinimum();

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

		// Check current adress fields
		//not use at the moment (11/09/2008) cf ticket : 36313
		//		if (!adressController.ctrlEnter(adressController.getCurrentAdrPojo().getAdresse())) {
		//			ctrlOk = false;
		//		}
		// Check fix adress fields
		if (!adressController.ctrlEnter(adressController.getFixAdrPojo().getAdresse(), true)) {
			ctrlOk = false;
		}


		return ctrlOk;
	}


	/**
	 * Control if the added user is unique.
	 * @return
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
	 * @return Boolean
	 */
	private Boolean ctrlSearch() {
		Boolean ctrlOk = true;
		// Check individu's fields
		Individu ind = pojoIndividu.getIndividu();

		if (!StringUtils.isNotBlank(ind.getNomPatronymique())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.NOM"));
			ctrlOk = false;
		}
		if (!StringUtils.isNotBlank(ind.getPrenom())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.PRENOM"));
			ctrlOk = false;
		}
		if (ind.getDateNaissance() == null) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.DATE_NAI"));
			ctrlOk = false;
		}
		if (!StringUtils.isNotBlank(ind.getCodPayNaissance())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.PAY_NAI"));
			ctrlOk = false;
		}
		if (ind.getCodPayNaissance().equals(Constantes.CODEFRANCE) 
				&& !StringUtils.isNotBlank(ind.getCodDepPaysNaissance())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("INDIVIDU.DEP_NAI"));
			ctrlOk = false;
		}
		return ctrlOk;
	}

	/**
	 * Upper any attributs in Individu.
	 * @param i
	 * @return Individu
	 */
	private Individu toUpperCaseAnyAttributs(final Individu i) {
		//UPPER CASE any attributs
		Individu in = i;
		if (StringUtils.isNotBlank(i.getNomPatronymique())) {
			in.setNomPatronymique(i.getNomPatronymique().toUpperCase());
		}
		if (StringUtils.isNotBlank(i.getNomUsuel())) {
			in.setNomUsuel(i.getNomUsuel().toUpperCase());
		}
		in.setPrenom(i.getPrenom().toUpperCase());
		if (StringUtils.isNotBlank(i.getPrenom2())) {
			in.setPrenom2(i.getPrenom2().toUpperCase());
		}
		if (StringUtils.isNotBlank(i.getVilleNaissance())) {
			in.setVilleNaissance(
					i.getVilleNaissance().toUpperCase());
		}
		if (StringUtils.isNotBlank(i.getCodeClefNNE())) {
			in.setCodeClefNNE(
					i.getCodeClefNNE().toUpperCase());
		}

		return in;
	}

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the pojoIndividu
	 */
	public IndividuPojo getPojoIndividu() {
		return pojoIndividu;
	}

	/**
	 * @param pojoIndividu the pojoIndividu to set
	 */
	public void setPojoIndividu(final IndividuPojo pojoIndividu) {
		this.pojoIndividu = pojoIndividu;
	}

	/**
	 * @param adressController the adressController to set
	 */
	public void setAdressController(final AdressController adressController) {
		this.adressController = adressController;
	}

	/**
	 * @param cursusController the cursusController to set
	 */
	public void setCursusController(final CursusController cursusController) {
		this.cursusController = cursusController;
	}

	/**
	 * @param indBacController the indBacController to set
	 */
	public void setIndBacController(final IndBacController indBacController) {
		this.indBacController = indBacController;
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
	 * @return the individuPaginator
	 */
	public IndividuPaginator getIndividuPaginator() {
		return individuPaginator;
	}
	/**
	 * @param individuPaginator the individuPaginator to set
	 */
	public void setIndividuPaginator(final IndividuPaginator individuPaginator) {
		this.individuPaginator = individuPaginator;
	}

	/**
	 * @return the adressController
	 */
	public AdressController getAdressController() {
		return adressController;
	}

	/**
	 * @return the cursusController
	 */
	public CursusController getCursusController() {
		return cursusController;
	}

	/**
	 * @return the indBacController
	 */
	public IndBacController getIndBacController() {
		return indBacController;
	}


	/**
	 * @return the situationController
	 */
	public SituationController getSituationController() {
		return situationController;
	}

	/**
	 * @param situationController the situationController to set
	 */
	public void setSituationController(final SituationController situationController) {
		this.situationController = situationController;
	}

	/**
	 * @param formulairesController the formulairesController to set
	 */
	public void setFormulairesController(final FormulairesController formulairesController) {
		this.formulairesController = formulairesController;
	}

	/**
	 * @return the etatDossier
	 */
	public String getEtatDossier() {
		return etatDossier;
	}

	/**
	 * @param etatDossier the etatDossier to set
	 */
	public void setEtatDossier(final String etatDossier) {
		this.etatDossier = etatDossier;
	}
	
	/**
	 * @return isRecupInfos
	 */
	public boolean getIsRecupInfos() {
		return isRecupInfos;
	}
	
	/**
	 * @param isRecupInfos
	 */
	public void setIsRecupInfos(final boolean isRecupInfos) {
		this.isRecupInfos = isRecupInfos;
	}
	
	/**
	 * @return isRecupCursus
	 */
	public boolean getIsRecupCursus() {
		return isRecupCursus;
	}
	
	/**
	 * @param isRecupCursus
	 */
	public void setIsRecupCursus(final boolean isRecupCursus) {
		this.isRecupCursus = isRecupCursus;
	}
	
	/**
	 * @return isRecupBac
	 */
	public boolean getIsRecupBac() {
		return isRecupBac;
	}
	
	/**
	 * @param isRecupBac
	 */
	public void setIsRecupBac(final boolean isRecupBac) {
		this.isRecupBac = isRecupBac;
	}

	/**
	 * @return the jourNaissance
	 */
	public String getJourNaissance() {
		return jourNaissance;
	}

	/**
	 * @param jourNaissance the jourNaissance to set
	 */
	public void setJourNaissance(final String jourNaissance) {
		this.jourNaissance = jourNaissance;
	}

	/**
	 * @return the moisNaissance
	 */
	public String getMoisNaissance() {
		return moisNaissance;
	}

	/**
	 * @param moisNaissance the moisNaissance to set
	 */
	public void setMoisNaissance(final String moisNaissance) {
		this.moisNaissance = moisNaissance;
	}

	/**
	 * @return the anneeNaissance
	 */
	public String getAnneeNaissance() {
		return anneeNaissance;
	}

	/**
	 * @param anneeNaissance the anneeNaissance to set
	 */
	public void setAnneeNaissance(final String anneeNaissance) {
		this.anneeNaissance = anneeNaissance;
	}

	/**
	 * @return the listeAnneeNaissance
	 */
	public List<SelectItem> getListeAnneeNaissance() {
		return listeAnneeNaissance;
	}

	/**
	 * @return the checkEmail
	 */
	public String getCheckEmail() {
		return checkEmail;
	}

	/**
	 * @param checkEmail the checkEmail to set
	 */
	public void setCheckEmail(final String checkEmail) {
		this.checkEmail = checkEmail;
	}
	
	/**
	 * @return ldapUserService
	 */
	public LdapUserService getLdapUserService() {
		return ldapUserService;
	}

	/**
	 * @param ldapUserService
	 */
	public void setLdapUserService(LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

    public LazyDataModel<Individu> getIndLDM() {
        return indLDM;
    }
}
