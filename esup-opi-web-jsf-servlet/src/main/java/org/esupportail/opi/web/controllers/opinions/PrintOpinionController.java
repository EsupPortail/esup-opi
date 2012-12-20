package org.esupportail.opi.web.controllers.opinions;

import static fj.data.IterableW.wrap;
import static fj.data.Array.*;
import static fj.Equal.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.zip.ZipOutputStream;

import javax.faces.context.FacesContext;

import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.parameters.InscriptionAdm;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.Refused;
import org.esupportail.opi.domain.beans.parameters.Transfert;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.calendar.CalendarCmi;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.services.export.CastorService;
import org.esupportail.opi.services.export.ISerializationService;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.AdressePojo;
import org.esupportail.opi.web.beans.pojo.IndCursusScolPojo;
import org.esupportail.opi.web.beans.pojo.IndListePrepaPojo;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.pojo.NotificationOpinion;
import org.esupportail.opi.web.beans.utils.ExportUtils;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.PDFUtils;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.opi.web.controllers.references.CommissionController;
import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;

import fj.F;
import fj.data.Array;
import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;



/**
 * @author tducreux
 * PrintOpinionController : 
 */
public class PrintOpinionController  extends AbstractContextAwareController  {


	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * the serialization id.
	 */
	private static final long serialVersionUID = 7174653291470562703L;

	/**
	 * 
	 */
	private static final List<String> HEADER_CVS = 
		new ArrayList<String>() { 

		private static final long serialVersionUID = 4451087010675988608L;

		{ add("Commission"); add("Num_Dos_OPI"); add("Nom_Patrony");
		add("Prenom"); add("Date_Naiss"); add("Code/clef INE");
		add("Adresse_1"); add("Adresse_2"); add("Adresse_3");
		add("Cedex"); add("Code_Postale"); add("Lib_Commune");
		add("Lib_Pays"); add("Telephone fixe");
		add("Mail"); add("Bac"); add("Dernier_Etab_Cur");
		add("Dernier_Etab_Etb"); add("Dernier_Etab_Result_Ext"); 
		add("Date_depot_voeu"); add("Type_Traitement"); add("Voeu_Lib_Vet"); 
		add("Etat_Voeu"); add("Avis_Result_Lib"); add("Rang");
		add("Avis_Motivation/Commentaire"); add("Avis_Result_Code");
		add("Avis_Result_Code_Apogee"); add("Avis_temoin_validation"); add("Avis_date_validation"); } };



		/**
		 * A logger.
		 */
		private final Logger log = new LoggerImpl(getClass());

		/**
		 * id commission selectd.
		 */
		private Integer idCommissionSelected;

		/**
		 * Champs dispo pour l'export.
		 */
		private List<String> champsDispos;
		/**
		 * liste des champs selectionnes.
		 */
		private String[] champsChoisis;

		/**
		 * default value : false.
		 */
		private Boolean selectValid;

		/**
		 * The result (of type Final or not final) 
		 * *selectionned or not by the gestionnaire.
		 */
		private Object[] resultSelected;

		/**
		 * List of commissions selected. 
		 */
		private Object[] commissionsSelected;
		/**
		 * Has true if all Commission are selected.
		 * Default value = false
		 */
		private Boolean allChecked; 

		/**
		 * List of individus for a commission and avis positionned by gestionnaire.
		 */
		private List <IndividuPojo> lesIndividus;

		/**
		 * Data for pdf generation.
		 */
		private Map <Commission , List <NotificationOpinion>> pdfData;

		/**
		 * see {@link CommissionController}.
		 */
		private CommissionController commissionController;
		
		/**
		 * see {@link ExportFormOrbeonController}.
		 */
		private ExportFormOrbeonController exportFormOrbeonController;

		/**
		 * The actionEnum.
		 */
		private ActionEnum actionEnum;

		/**
		 * see {@link InscriptionAdm}.
		 */
		private InscriptionAdm inscriptionAdm;

		/**
		 * Print only notification fot Defavorable.
		 */
		private Boolean printOnlyDef;
		
		/**
		 * see {@link Refused}.
		 */
		private Refused refused;

		/**
		 * see {@link Transfert}.
		 */
		private Transfert transfert;
		
		/**
		 * Service to generate Xml.
		 */
		private ISerializationService castorService;

		/**
		 * individuPojoSelected.
		 */
		private IndividuPojo individuPojoSelected;

		/*
		 ******************* INIT ************************* */

		/**
		 * Constructors.
		 */
		public PrintOpinionController() {
			super();
			
		}

		/** 
		 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
		 */
		@Override
		public void reset() {
			super.reset();
			commissionController.reset();
			this.selectValid = false;
			this.resultSelected = new Object[0];
			this.commissionsSelected = new Object[0];
			this.lesIndividus = new ArrayList<IndividuPojo>();
			champsDispos = new ArrayList<String>();
			List<String> lChampschoisis = new ArrayList<String>();
			for (String champs : HEADER_CVS) {
				champsDispos.add(champs);
				lChampschoisis.add(champs);
			}
			champsChoisis = lChampschoisis.toArray(new String[0]);
			this.allChecked = false;
			this.pdfData = new HashMap<Commission, List<NotificationOpinion>>();
			this.actionEnum = new ActionEnum();

		}

		/** 
		 * @see org.esupportail.opi.web.controllers.
		 * AbstractContextAwareController#afterPropertiesSetInternal()
		 */
		@Override
		public void afterPropertiesSetInternal() {
			super.afterPropertiesSetInternal();
			Assert.notNull(this.castorService, 
					"property castorService of class " + this.getClass().getName() 
					+ " can not be null");
			Assert.notNull(this.commissionController, 
					"property commissionController of class " + this.getClass().getName() 
					+ " can not be null");
			Assert.notNull(this.exportFormOrbeonController, 
					"property exportFormOrbeonController of class " + this.getClass().getName() 
					+ " can not be null");
			reset();
		}

		/*
		 ******************* CALLBACK ********************** */

		/**
		 * Callback for the print of opinions.
		 * @return String
		 */
		public String goPrintOpinions() {
			reset();
			setListTypeOpinions();
			return NavigationRulesConst.DISPLAY_PRINT_OPINIONS;
		}
		
		/**
		 * Callback for the print of TR opinions.
		 * @return String
		 */
		public String goPrintTROpinions() {
			reset();
			return NavigationRulesConst.DISPLAY_PRINT_TR_OPINIONS;
		}
		/*
		 ******************* METHODS ********************** */

		/**
		 * Find student.
		 * call in printOpinions.jsp
		 */
		public void seeCandidats() {
			makeAllIndividus(selectValid, false, true);
		}

		/**
		 * Print pdf after set the list of students.
		 * call in printOpinions.jsp
		 */
		public void printPDFValidation() {
			makeAllIndividus(selectValid, true, true);
			makePDFValidation();
			this.lesIndividus = new ArrayList<IndividuPojo>();
			seeCandidats();
		}

		/**
		 * Make pdf after set the list of students.
		 * call in printOpinions.jsp
		 */
		public void makeCsvValidation() {
			makeAllIndividus(selectValid, true, true);
			csvGeneration(lesIndividus,
					"exportAvis_" + commissionController.getCommission().getCode() + ".csv");
			this.lesIndividus = new ArrayList<IndividuPojo>();
			seeCandidats();
		}

		/**
		 * Make pdf after set the list of students.
		 * call in printOpinions.jsp
		 */
		public void makeCsvFormulaire() {
			if (this.idCommissionSelected != null) {
				commissionController.setCommission(getParameterService().
					getCommission(this.idCommissionSelected, null));
			} else {
				addErrorMessage(null, "ERROR.PRINT.COMMISSION_NULL");
				return;
			}
			
			if (commissionController.getListeRI() == null
					|| commissionController.getListeRI().isEmpty()) {
				addErrorMessage(null, "ERROR.PRINT.LIST_RI_NULL");
				return;
			}
			
			exportFormOrbeonController.makeCsvFormulaire(
					commissionController.getCommission(),
					commissionController.getListeRI());
		}
		
		/**
		 * Make csv after see the list of students.
		 * call in seeNotEtudiant.jsp
		 */
		public void makeCsvInSeeEtuVal() {
			csvGeneration(lesIndividus,
					"exportAvis_" + commissionController.getCommission().getCode() + ".csv");
			this.lesIndividus = new ArrayList<IndividuPojo>();
		}

		/**
		 * print pdf of notification for the student selected.
		 * call in printOpinions.jsp
		 */
		public void printOneNotification() {
			this.pdfData.clear();

			List<IndividuPojo> individus = new ArrayList<IndividuPojo>();
			individus.add(individuPojoSelected);
			
			makePdfData(individus, commissionController.getCommission());

			if (!this.pdfData.isEmpty()) {
				printOnlyDef = false;
				notificationPdfGeneration();
			} else {
				addInfoMessage(null, "INFO.PRINT.NO_NOTIFICATION");
			}
		}

		/**
		 * print pdf of notifications for the student of the commission selected.
		 * call in printOpinions.jsp
		 */
		public void printPDFAllNotifications() {
			this.pdfData.clear();

			makeAllIndividus(selectValid, false, true);
			makePdfData(lesIndividus, commissionController.getCommission());

			if (!this.pdfData.isEmpty()) {
				printOnlyDef = false;
				notificationPdfGeneration();
			} else {
				addInfoMessage(null, "INFO.PRINT.NO_NOTIFICATION");
			}
			this.lesIndividus = new ArrayList<IndividuPojo>();
			seeCandidats();
		}

		/**
		 * Generate the CVS de la liste preparatoire de la commission.
		 * call in printListsPrepa.jsp
		 */
		public void generateCSVListesPreparatoire() {
			Commission com = commissionController.getCommission(); 
			// hibernate session reattachment
			com = getParameterService().getCommission(com.getId(), com.getCode());
			
			List<Individu> listeInd = getDomainService().getIndividusCommission(
					com, null,
					wrap(commissionController.getListeRI()).map(
					    new F<RegimeInscription, String>() {
                            public String f(RegimeInscription ri) {
                                return String.valueOf(ri.getCode());
                            }}).toStandardList());
			log.debug("after list individus");

			Set<Commission> listComm = new HashSet<Commission>();
			listComm.add(com);

			List<IndividuPojo> listeIndPojo = 
				Utilitaires.convertIndInIndPojo(listeInd, 
						getParameterService(), getI18nService(), 
						getDomainApoService(), listComm, null, 
						getParameterService().getTypeTraitements(),
						getParameterService().getCalendarRdv(), null, false);

			for (IndividuPojo iPojo : listeIndPojo) {
				iPojo.initIndCursusScolPojo(getDomainApoService(), getI18nService());
				
				// on enlève les voeux en transfert
				Set<IndVoeuPojo> voeuxToRemove = new HashSet<IndVoeuPojo>();
				for (IndVoeuPojo iVoeuP : iPojo.getIndVoeuxPojo()) {
					if (iVoeuP.getTypeTraitement().equals(transfert)) {
						voeuxToRemove.add(iVoeuP);
					}
				}
				iPojo.getIndVoeuxPojo().removeAll(voeuxToRemove);
				
				// enleve les etudiants sans voeux restant
				if (!iPojo.getIndVoeuxPojo().isEmpty()) {
					this.lesIndividus.add(iPojo);
				}

			}
			
			csvGeneration(lesIndividus, 
					"listePrepa_" + commissionController.getCommission().getCode() + ".csv");
			this.lesIndividus = new ArrayList<IndividuPojo>();
		}
		
		
		/**
		 * Generate the CVS de la liste preparatoire de la commission.
		 * call in printListsPrepa.jsp
		 */
		public void generateCSVListesTransfert() {
			/**
			 * recuperation de la liste des individus ayant fait un voeu dans la commission
			 */
			//String includeTypeTrt = "'" + transfert.getCode() + "'";

			List<Individu> listeInd = getDomainService().getIndividusCommission(
					commissionController.getCommission(), null, null);
			log.debug("after list individus");
			Set<Commission> listComm = new HashSet<Commission>();
			Commission c = getParameterService().getCommission(commissionController.getCommission().getId(), null);
			listComm.add(c);

			List<IndividuPojo> listeIndPojo = 
				Utilitaires.convertIndInIndPojo(listeInd, 
						getParameterService(), getI18nService(), 
						getDomainApoService(), listComm, null, 
						getParameterService().getTypeTraitements(), 
						getParameterService().getCalendarRdv(), null, false);

			// on filtre les voeux 
			
			for (IndividuPojo iPojo : listeIndPojo) {
				iPojo.initIndCursusScolPojo(getDomainApoService(), getI18nService());
				
				// on enlève les voeux non en transfert
				Set<IndVoeuPojo> voeuxToRemove = new HashSet<IndVoeuPojo>();
				for (IndVoeuPojo iVoeuP : iPojo.getIndVoeuxPojo()) {
					if (!iVoeuP.getTypeTraitement().equals(transfert)) {
						voeuxToRemove.add(iVoeuP);
					}
				}
				iPojo.getIndVoeuxPojo().removeAll(voeuxToRemove);
				
				// enleve les etudiants sans voeux restant
				if (!iPojo.getIndVoeuxPojo().isEmpty()) {
					this.lesIndividus.add(iPojo);
				}

			}
			csvGeneration(lesIndividus, 
					"listePrepa_" + commissionController.getCommission().getCode() + ".csv");
			this.lesIndividus = new ArrayList<IndividuPojo>();
		}
		
	
		/**
		 * Generate a CSV of the list of student. 
		 * @param individus 
		 * @param fileName 
		 * @return String
		 */
		public String csvGeneration(final List<IndividuPojo> individus, final String fileName) {
			//key ligne value value list
			Map<Integer, List<String>> mapCsv = new HashMap<Integer, List<String>>(); 
			Integer counter = 0;
			Integer colonne = 0;
			Array<String> tabChampschoisis = array(champsChoisis);
			if (champsChoisis == null || tabChampschoisis.isEmpty()) {
				champsChoisis = HEADER_CVS.toArray(new String[0]);
			}

			log.info("Champs choisis : " + champsChoisis);

			mapCsv.put(counter, Arrays.asList(champsChoisis));
			Collections.sort(individus, new ComparatorString(IndividuPojo.class));
			for (IndividuPojo ind : individus) {
				Pays p = null;
				CommuneDTO c = null;
				
				//init hib proxy adresse
//				getDomainService().initOneProxyHib(ind.getIndividu(), 
//						ind.getIndividu().getAdresses(), Adresse.class);
				// récupération de l'adresse
				Adresse adresse =  ind.getIndividu().getAdresses().get(Constantes.ADR_FIX);
				if (adresse != null) {
					if (c == null || !c.getCodeCommune().equals(adresse.getCodCommune())) {
						c = getDomainApoService().getCommune(
								adresse.getCodCommune(), adresse.getCodBdi());
					}
					if (p == null || !p.getCodPay().equals(adresse.getCodPays())) {
						p = getDomainApoService().getPays(
								adresse.getCodPays());
					}
				}
				
				for (IndVoeuPojo v : ind.getIndVoeuxPojo()) {
					List<String> ligne = new ArrayList<String>();
					++counter;
					colonne = 0;
					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(colonne)))) {
						ligne.add(this.commissionController.getCommission().getLibelle());
					}
					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						ligne.add(ind.getIndividu().getNumDossierOpi());
					}

					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {	
						ligne.add(ind.getIndividu().getNomPatronymique());
					}

					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						ligne.add(ind.getIndividu().getPrenom());
					}

					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						ligne.add("" + ind.getIndividu().getDateNaissance());
					}
					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						String ine = ExportUtils.isNotNull(ind.getIndividu().getCodeNNE()) 
							+ ExportUtils.isNotNull(ind.getIndividu().getCodeClefNNE());
						ligne.add(ExportUtils.isNotNull(ine));
					}
				
					if (adresse != null) {
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(adresse.getAdr1()));
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(adresse.getAdr2()));
						}

						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(adresse.getAdr3()));
						}

						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(adresse.getCedex()));
						}

						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(adresse.getCodBdi()));
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							if (c != null) {
								ligne.add(c.getLibCommune());
							} else { 
								ligne.add(ExportUtils.isNotNull(
									adresse.getLibComEtr())); 
							}
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							if (p != null) {
								ligne.add(p.getLibPay());
							} else {
								ligne.add("");
							}
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(adresse.getPhoneNumber()));
						}
					} else {
						for (int i = 0; i < 8; i++) {
							if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
								ligne.addAll(ExportUtils.addBlankList(1));
							}
						}
						//ExportUtils.addBlankList(8);
					}
					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						ligne.add(ExportUtils.isNotNull(ind.getIndividu().getAdressMail()));
					}

					// bac
					boolean hasCodeBac = false;
					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						for (IndBac iB : ind.getIndividu().getIndBac()) {
							BacOuxEqu b = getDomainApoService().getBacOuxEqu(
									iB.getDateObtention(),
									ExportUtils.isNotNull(iB.getCodBac()));
							if (b != null) {
								ligne.add(b.getLibBac());
							} else {
								ligne.add(iB.getCodBac());
							}
							hasCodeBac = true;
							break;
						}
						
						if (!hasCodeBac) { ligne.add(""); }
					}
					// dernier cursus
					IndCursusScolPojo d = ind.getDerniereAnneeEtudeCursus();
					if (d != null) {
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(d.getLibCur()));
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(d.getLibEtb()));
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(d.getResultatExt()));
						}
					} else {
						for (int i = 0; i < 3; i++) {
							if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
								ligne.addAll(ExportUtils.addBlankList(1));
							}
						}
					}

					// Voeux
					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						DateFormat sdf = new SimpleDateFormat(Constantes.DATE_HOUR_FORMAT); 
						ligne.add(sdf.format(v.getIndVoeu().getDateCreaEnr()));
					}

					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						ligne.add(ExportUtils.isNotNull(v.getTypeTraitement().getCode()));
					}

					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						ligne.add(ExportUtils.isNotNull(v.getVrsEtape().getLibWebVet()));
					}
					if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
						ligne.add(ExportUtils.isNotNull(v.getEtat().getLabel()));
					}
					if (v.getAvisEnService() != null) {
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(v.getAvisEnService().
									getResult().getLibelle()));
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							if (v.getAvisEnService().getRang() != null) {
								ligne.add(v.getAvisEnService().getRang().toString());
							} else {
								ligne.add("");
							}
						}						
						String comm = null;
						if (v.getAvisEnService().getMotivationAvis() != null) {
							comm = v.getAvisEnService().getMotivationAvis().getLibelle();
						}
						if (comm != null && StringUtils.hasText(v.getAvisEnService().
								getCommentaire())) {
							comm += "/" + v.getAvisEnService().getCommentaire();
						} else { 
							comm += ExportUtils.isNotNull(
									v.getAvisEnService().getCommentaire()); 
						}

						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(comm));
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(v.getAvisEnService().
									getResult().getCode()));
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull(v.getAvisEnService().
									getResult().getCodeApogee()));
						}
						if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
							ligne.add(ExportUtils.isNotNull("" + v.getAvisEnService().
									getValidation()));
						}
						if (v.getAvisEnService().getValidation()) {
							if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
								ligne.add(ExportUtils.isNotNull(
										Utilitaires.convertDateToString(
											v.getAvisEnService().
											getDateModifEnr(), 
											Constantes.DATE_FORMAT)));
							}
						} else {
							if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
								ligne.add(""); 
							}
						}
					} else {
						for (int i = 0; i < 7; i++) {
							if (tabChampschoisis.exists(stringEqual.eq(HEADER_CVS.get(++colonne)))) {
								ligne.addAll(ExportUtils.addBlankList(1));
							}
						}

					}
					if (ligne.size() != tabChampschoisis.length()) {
						throw new ConfigException("Construction du csv avis : " 
								+ "le nombre de colonne par ligne ("
								+ ligne.size() + ")est different " 
								+ "que celui du header("
								+ tabChampschoisis.length() + ")(method csvGeneration in " 
								+ getClass().getName() + " )");
					}

					mapCsv.put(counter, ligne);
				}


			}

			ExportUtils.csvGenerate(mapCsv, fileName);
			return null;
		}
		
		/**
		 * clear and found the list of IndividuPojo and IndVoeuPjo
		 * filtred by commission and typeDecision selected by the gestionnaire.
		 * @param laCommission
		 * @param onlyValidate
		 * @param initCursusPojo if true init cursus pojo
		 * @param excludeTR 
		 */
		public void lookForIndividusPojo(final Commission laCommission,
				final Boolean onlyValidate, final Boolean initCursusPojo, final Boolean excludeTR) {
			if (log.isDebugEnabled()) {
				log.debug("entering foundLesIndividusPojo() " + laCommission.toString());
			}
			this.lesIndividus.clear();
			
//			String excludeTypeTrt = "";
//			if (excludeTR) {
//				excludeTypeTrt = "'" + transfert.getCode() + "'";
//			}
			// on récupère la liste des individus de la commission en filtrant
			// - sur les avis validés ou non (onlyValidate)
			// - sur les voeux issus d'un transfert ou non (excludeTypeTrt)
			List <Individu> l = getDomainService().getIndividusCommission(
			    laCommission, onlyValidate,
			    wrap(this.commissionController.getListeRI()).map(
			        new F<RegimeInscription, String>() {
                        public String f(RegimeInscription ri) {
                            return String.valueOf(ri.getCode());
                        }}).toStandardList());
			// param Set <Commission>
			Set <Commission> lesCommissions = new HashSet<Commission>();
			lesCommissions.add(laCommission);
			// param Set <TypeDecisions>
			Set <TypeDecision> lesTypeDecisions = new HashSet<TypeDecision>();
			for (Object o : this.resultSelected) {
				lesTypeDecisions.add((TypeDecision) o);
			}
			List<TypeTraitement> lesTypeTrait = getParameterService().getTypeTraitements();
			
			List<CalendarRDV> listCalendrierParam = getParameterService().getCalendarRdv();
			// convert to IndividuPojo and IndVoeuPojo
			for (Individu i : l) {
				
				// TODO : à supprimer 24/01/2012, cas traité dans getIndividusCommission
				// on filtre la liste des individus selon les régimes d'inscription
//				if (this.commissionController.getListeRI()
//						.contains(getRegimeIns().get(Utilitaires.getCodeRIIndividu(i,
//								getDomainService())))) {
					
					// converti en indPojo en filtrant sur la liste des type de décisions cochés
					IndividuPojo iPojo = new IndividuPojo(i,
							getDomainApoService(), getI18nService(), 
							getParameterService(), lesCommissions, lesTypeDecisions, lesTypeTrait, listCalendrierParam, null);
					if (initCursusPojo) {
						iPojo.initIndCursusScolPojo(getDomainApoService(), getI18nService());
					}
					
					// on enlève les voeux en transfert
					Set<IndVoeuPojo> voeuxToRemove = new HashSet<IndVoeuPojo>();
					for (IndVoeuPojo iVoeuP : iPojo.getIndVoeuxPojo()) {
						if (iVoeuP.getTypeTraitement().equals(transfert)) {
							voeuxToRemove.add(iVoeuP);
						}
					}
					iPojo.getIndVoeuxPojo().removeAll(voeuxToRemove);
					
					// si onlyValidate = true, on enlève les voeux non validés
					// et inversement
					if (onlyValidate != null) {
						voeuxToRemove.clear();
						for (IndVoeuPojo iVoeuP : iPojo.getIndVoeuxPojo()) {
							if (!iVoeuP.getAvisEnService().getValidation()
									.equals(onlyValidate)) {
								voeuxToRemove.add(iVoeuP);
							}
						}
					}
					iPojo.getIndVoeuxPojo().removeAll(voeuxToRemove);
					
					// enleve les etudiants sans voeux restant
					if (!iPojo.getIndVoeuxPojo().isEmpty()) {
						this.lesIndividus.add(iPojo);
					}
//				}
			}
			// trie
			Collections.sort(this.lesIndividus, new ComparatorString(IndividuPojo.class));
		}


		/**
		 * Int the commission and make the individuals list.
		 * @param onlyValidate
		 * @param initCursusPojo if true init cursus pojo
		 * @param excludeTR if true, exclude TR
		 */
		private void makeAllIndividus(final Boolean onlyValidate, 
				final Boolean initCursusPojo, final Boolean excludeTR) {
			// list of indivius from the commission selected 
			// with an opinion not validate
			if (this.idCommissionSelected != null) {
				this.commissionController.setCommission(getParameterService().
						getCommission(this.idCommissionSelected, null));
				lookForIndividusPojo(
						this.commissionController.getCommission(),
						onlyValidate, initCursusPojo, excludeTR);
			}
		}


		/**
		 * Labels of the Results selected by the gestionnaire.
		 * @return String
		 */
		public String getLabelResultSelected() {
			StringBuffer r = new StringBuffer();
			Boolean first = true;
			for (Object o : this.resultSelected) {
				TypeDecision result = (TypeDecision) o;
				if (first) { 
					first = false;
				} else { 
					r.append(", "); 
				}
				r.append(result.getLibelle());
			}
			return r.toString();
		}

		/**
		 * Return the list of Type Opinion of the type selected by the user. 
		 * @return  String
		 */
		public String setListTypeOpinions() {
			this.resultSelected = new Object[0];
			return NavigationRulesConst.DISPLAY_VALID_OPINIONS;
		}

		/**
		 * Genere le PDF de validation des avis.
		 */
		public void makePDFValidation() {
			if (log.isDebugEnabled()) {
				log.debug("entering makePDFValidation()");
			}

			// Map repartissant les IndListePrepaPojo par etape puis par avis
			Map<VersionEtapeDTO, Map<TypeDecision, List<IndListePrepaPojo>>> mapIndListByEtapeAndAvis =
				new TreeMap<VersionEtapeDTO, Map<TypeDecision, List<IndListePrepaPojo>>>(
						new ComparatorString(VersionEtapeDTO.class));

			// on boucle sur la liste des individus de la commission avec les avis selectionnes
			for (IndividuPojo iP : this.lesIndividus) {
				// hibernate session reattachment
				Individu ind = iP.getIndividu(); 
				iP.setIndividu(getDomainService().getIndividu(
						ind.getNumDossierOpi(), ind.getDateNaissance()));
				
				// initialisation des cursus scolaires
				iP.initIndCursusScolPojo(getDomainApoService(), getI18nService());
				// on boucle sur les listes des avis de chaque individu
				for (IndVoeuPojo indVoeuPojo : iP.getIndVoeuxPojo()) {
					Avis unAvis = indVoeuPojo.getAvisEnService();
					TraitementCmi trtCmi = 
						unAvis.getIndVoeu().getLinkTrtCmiCamp().getTraitementCmi();
					// on recupere l'etape de l'avis
					VersionEtapeDTO vDTO = getDomainApoService().getVersionEtape(
							trtCmi.getVersionEtpOpi().getCodEtp(), 
							trtCmi.getVersionEtpOpi().getCodVrsVet());
					// on cree l'entree de l'etape si elle n'existe pas
					if (!mapIndListByEtapeAndAvis.containsKey(vDTO)) {
						mapIndListByEtapeAndAvis.put(vDTO, new TreeMap<TypeDecision, 
								List<IndListePrepaPojo>>(new ComparatorString(
										TypeDecision.class)));
					}

					// on recupere le type de decision de l'avis
					TypeDecision typeDec = unAvis.getResult();
					// on cree l'entree du type de decision si elle n'existe pas
					if (!mapIndListByEtapeAndAvis.get(vDTO).containsKey(typeDec)) {
						mapIndListByEtapeAndAvis.get(vDTO).put(typeDec, 
								new ArrayList<IndListePrepaPojo>());
					}
					// on cree l'IndListePrepaPojo correspondant pour l'ajouter dans la map
					IndListePrepaPojo unIndPrepa = new IndListePrepaPojo();
					// code de la commission from Commission.code
					unIndPrepa.setCodeCmi(commissionController.getCommission().getCode());
					// code du dossier de l'individu from IndividuPojo.individu.numDossierOpi
					unIndPrepa.setNumDossierOpi(iP.getIndividu().getNumDossierOpi());
					// nom de l'individu from IndividuPojo.individu.nomPatronymique
					unIndPrepa.setNom(iP.getIndividu().getNomPatronymique());
					// prenom de l'individu from IndividuPojo.individu.prenom
					unIndPrepa.setPrenom(iP.getIndividu().getPrenom());
					// codeEtu de l'individu from IndividuPojo.individu.codeEtu
					unIndPrepa.setCodeEtu(iP.getIndividu().getCodeEtu());
					// bac de l'individu from IndividuPojo.individu.indBac 
					// (premier element de la liste)
					IndBac iB = iP.getIndividu().getIndBac().iterator().next();
					BacOuxEqu b = getDomainApoService().getBacOuxEqu(
							iB.getDateObtention(),
							ExportUtils.isNotNull(iB.getCodBac()));
					if (b != null) {
						unIndPrepa.setBac(b.getLibBac());
					} else {
						unIndPrepa.setBac(iB.getCodBac());
					}
					if (iP.getDerniereAnneeEtudeCursus() != null) {
						// titre fondant la demande from 
						// IndividuPojo.derniereAnneeEtudeCursus.libCur
						unIndPrepa.setTitreAccesDemande(
								iP.getDerniereAnneeEtudeCursus().getLibCur());
						// dernier cursus from  IndividuPojo.derniereAnneeEtudeCursus.cursus
						unIndPrepa.setDernierIndCursusScol(iP.getDerniereAnneeEtudeCursus()
								.getCursus());
					}
					// creation d'un indVoeuPojo
					IndVoeuPojo indPojo = new IndVoeuPojo();
					indPojo.setIndVoeu(unAvis.getIndVoeu());
					indPojo.setVrsEtape(vDTO);

					indPojo.setAvisEnService(unAvis);
					// on ajoute indPojo
					unIndPrepa.setIndVoeuxPojo(new HashSet<IndVoeuPojo>());
					unIndPrepa.getIndVoeuxPojo().add(indPojo);

					// on ajoute l'indPrepa dans la map
					mapIndListByEtapeAndAvis.get(vDTO).get(typeDec).add(unIndPrepa);
				}
			}

			// tri de chaque sous liste par ordre alphabetique
			for (Map.Entry<VersionEtapeDTO, Map<TypeDecision, List<IndListePrepaPojo>>> 
			indListForOneEtape : mapIndListByEtapeAndAvis.entrySet()) {
				for (Map.Entry<TypeDecision, List<IndListePrepaPojo>> 
				indListForOneAvis : indListForOneEtape.getValue().entrySet()) {
					Collections.sort(indListForOneAvis.getValue(), 
							new ComparatorString(IndListePrepaPojo.class));
				}
			}

			// on recupere le xsl correspondant e l'edition
			// par ordre alphabetique
			String fileNameXsl = Constantes.LISTE_VALIDATION_AVIS_XSL;
			String fileNamePdf = "listeValidationAvis_" 
				+ commissionController.getCommission().getCode() + ".pdf";
			String fileNameXml = String.valueOf(System.currentTimeMillis()) 
			+ "_" + commissionController.getCommission().getCode() + ".xml";

			// on genere le pdf
			commissionController.generatePDFListePreparatoire(fileNameXsl, fileNameXml, 
					fileNamePdf, mapIndListByEtapeAndAvis);
		}
		
		/**
		 * Genere le pdf des notifications.
		 * @return String
		 */
		public String notificationPdfGeneration() {
			ByteArrayOutputStream zipByteArray = new ByteArrayOutputStream();
			ZipOutputStream zipStream = new ZipOutputStream(zipByteArray);
			// generate the pdf if exist
			if (!this.pdfData.isEmpty()) {
				Set <Commission> lesCommissions = this.pdfData.keySet();
				for (Commission laCommission : lesCommissions) {
					String fileNameXml = String.valueOf(System.currentTimeMillis()) 
					+ "_" + laCommission.getCode() + ".xml";
					String fileNamePdf = "commission_" + laCommission.getCode() + ".pdf";
					List <NotificationOpinion> lesNotifs = this.pdfData.get(laCommission);

					for (NotificationOpinion n : lesNotifs) {
						if (printOnlyDef) {
							n.setVoeuxFavorable(new HashSet<IndVoeuPojo>());
							n.setVoeuxFavorableAppel(new HashSet<IndVoeuPojo>());
						}
					}
					castorService.objectToFileXml(lesNotifs, fileNameXml);
					CastorService cs = (CastorService) castorService;
					if (lesCommissions.size() > 1) {
						// zip file
						PDFUtils.preparePDFinZip(
								fileNameXml, zipStream,
								cs.getXslXmlPath(),
								fileNamePdf, Constantes.NOTIFICATION_IND_XSL);

					} else {
						// one pdf
						PDFUtils.exportPDF(fileNameXml, FacesContext.getCurrentInstance(), 
								cs.getXslXmlPath(),
								fileNamePdf, Constantes.NOTIFICATION_IND_XSL);
					}
				}
				if (lesCommissions.size() > 1) {
					try {
						zipStream.close();
					} catch (IOException e) {
						log.error("probleme lors du zipStream.close() "
								+ " les notification des commissions " 
								+ "n ont pas ete telechargee");
					}
					PDFUtils.setDownLoadAndSend(
							zipByteArray.toByteArray(), 
							FacesContext.getCurrentInstance(), 
							Constantes.HTTP_TYPE_ZIP, "NotifsCommissions.zip");
				}
			}
			actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
			return NavigationRulesConst.DISPLAY_VALID_OPINIONS;
		}

		/**
		 * Make the Map Map < Commission , List < NotificationOpinion>> pdfData.
		 * @param individus 
		 * @param laCommission 
		 */
		public void makePdfData(final List<IndividuPojo> individus, final Commission laCommission) {
			// hibernate session reattachment
			Commission com = getParameterService().getCommission(
					laCommission.getId(), laCommission.getCode());
			
			List <NotificationOpinion> dataPDF = new ArrayList<NotificationOpinion>();
			for (IndividuPojo i : individus) {
				Set <IndVoeuPojo> indVoeuPojoFav = new HashSet<IndVoeuPojo>();
				Set <IndVoeuPojo> indVoeuPojoDef = new HashSet<IndVoeuPojo>();
				Set <IndVoeuPojo> indVoeuPojoFavAppel = new HashSet<IndVoeuPojo>();
				Set <IndVoeuPojo> indVoeuPojoDefAppel = new HashSet<IndVoeuPojo>();
				for (IndVoeuPojo indVPojo : i.getIndVoeuxPojo()) {
					Avis a = indVPojo.getAvisEnService();
					if (a.getResult().getIsFinal() 
							&& a.getResult().getCodeTypeConvocation()
							.equals(inscriptionAdm.getCode())) {
						if (!a.getAppel()) {
							indVoeuPojoFav.add(indVPojo);
						} else {
							indVoeuPojoFavAppel.add(indVPojo);
						}
					} else {
						if (a.getResult().getIsFinal() 
								&& a.getResult().getCodeTypeConvocation()
								.equals(refused.getCode())) {
							if (!a.getAppel()) {
								indVoeuPojoDef.add(indVPojo);
							} else {
								indVoeuPojoDefAppel.add(indVPojo);
							}
						}
					}
				}
				// data for pdf if necessery
				if (!indVoeuPojoFav.isEmpty() || !indVoeuPojoDef.isEmpty()
						|| !indVoeuPojoFavAppel.isEmpty() || !indVoeuPojoDefAppel.isEmpty()) {
					NotificationOpinion notificationOpinion = 
						initNotificationOpinion(
								i,
								com,
								indVoeuPojoFav, 
								indVoeuPojoDef,
								indVoeuPojoFavAppel, 
								indVoeuPojoDefAppel);
					dataPDF.add(notificationOpinion);
				}
			}

			// add data to pdfData
			if (!dataPDF.isEmpty()) {
				this.pdfData.put(laCommission, dataPDF);
			}
			
			
		}

		/**
		 * Initialisation pojo.
		 * @param i 
		 * @param laCommission 
		 * @param indVoeuPojoFav
		 * @param indVoeuPojoDef
		 * @param indVoeuPojoFavAppel 
		 * @param indVoeuPojoDefAppel 
		 * @return NotificationOpinion
		 */
		private NotificationOpinion initNotificationOpinion(
				final IndividuPojo i, 
				final Commission laCommission,
				final Set <IndVoeuPojo> indVoeuPojoFav, 
				final Set <IndVoeuPojo> indVoeuPojoDef,
				final Set <IndVoeuPojo> indVoeuPojoFavAppel, 
				final Set <IndVoeuPojo> indVoeuPojoDefAppel) {

			NotificationOpinion notificationOpinion = new NotificationOpinion();

			notificationOpinion.setVoeuxFavorable(indVoeuPojoFav);
			notificationOpinion.setVoeuxDefavorable(indVoeuPojoDef);
			notificationOpinion.setVoeuxFavorableAppel(indVoeuPojoFavAppel);
			notificationOpinion.setVoeuxDefavorableAppel(indVoeuPojoDefAppel);
			notificationOpinion.setCodeEtu(i.getIndividu().getCodeEtu());
			notificationOpinion.setNom(i.getIndividu().getNomPatronymique());
			notificationOpinion.setNumDossierOpi(i.getIndividu().getNumDossierOpi());
			notificationOpinion.setPrenom(i.getIndividu().getPrenom());
			notificationOpinion.setSexe(i.getIndividu().getSexe());
			notificationOpinion.setPeriodeScolaire(i.getCampagneEnServ(getDomainService()).getCode());
			AdressePojo aPojo = new AdressePojo(laCommission.getContactsCommission()
					.get(Utilitaires.getCodeRIIndividu(i.getIndividu(),
							getDomainService())).getAdresse(), 
							getDomainApoService());
			notificationOpinion.setCoordonneesContact(aPojo);
			aPojo = null;
			//init hib proxy adresse
			getDomainService().initOneProxyHib(i.getIndividu(), 
					i.getIndividu().getAdresses(), Adresse.class);
			if (i.getIndividu().getAdresses() != null)	{
				if (i.getIndividu().getAdresses().get(Constantes.ADR_FIX) != null) { 
					aPojo = new AdressePojo(i.getIndividu().getAdresses().
							get(Constantes.ADR_FIX), getDomainApoService());
				}
			}
			if (laCommission.getCalendarCmi() != null) {
				getDomainService().initOneProxyHib(
						laCommission, 
						laCommission.getCalendarCmi(),
						CalendarCmi.class);
				if (laCommission.getCalendarCmi().getEndDatConfRes() != null) {
					notificationOpinion.setDateCloture(
							Utilitaires.convertDateToString(laCommission.
									getCalendarCmi().getEndDatConfRes(),
									Constantes.DATE_FORMAT));
				}
			}
			
			SignataireDTO s = null;
			Integer codeRI = i.getCampagneEnServ(getDomainService()).getCodeRI();
			if (StringUtils.hasText(laCommission.getContactsCommission()
					.get(codeRI).getCodSig())) {
				s = getBusinessCacheService().getSignataire(laCommission.getContactsCommission()
						.get(codeRI).getCodSig());
			}
			notificationOpinion.setSignataire(s);
			notificationOpinion.setNomCommission(laCommission.getContactsCommission()
					.get(codeRI).getCorresponding());
			notificationOpinion.setAdresseEtu(aPojo);

			return notificationOpinion;
		}


		/*
		 ******************* ACCESSORS ******************** */
		/**
		 * @return the idCommissionSelected
		 */
		public Integer getIdCommissionSelected() {
			return idCommissionSelected;
		}

		/**
		 * @param idCommissionSelected the idCommissionSelected to set
		 */
		public void setIdCommissionSelected(final Integer idCommissionSelected) {
			this.idCommissionSelected = idCommissionSelected;
		}

		/**
		 * @return the selectValid
		 */
		public Boolean getSelectValid() {
			return selectValid;
		}

		/**
		 * @param selectValid the selectValid to set
		 */
		public void setSelectValid(final Boolean selectValid) {
			this.selectValid = selectValid;
		}

		/**
		 * @return the commissionsSelected
		 */
		public Object[] getCommissionsSelected() {
			return commissionsSelected;
		}

		/**
		 * @param commissionsSelected the commissionsSelected to set
		 */
		public void setCommissionsSelected(final Object[] commissionsSelected) {
			this.commissionsSelected = commissionsSelected;
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
		 * @return the champsDispo
		 */
		public List<String> getChampsDispos() {
			return champsDispos;
		}

		/**
		 * @return the champsChoisis
		 */
		public String[] getChampsChoisis() {
			return champsChoisis;
		}

		/**
		 * @param champsChoisis the champsChoisis to set
		 */
		public void setChampsChoisis(final String[] champsChoisis) {
			this.champsChoisis = champsChoisis;
		}

		/**
		 * @return the lesIndividus
		 */
		public List<IndividuPojo> getLesIndividus() {
			return lesIndividus;
		}

		/**
		 * @param lesIndividus the lesIndividus to set
		 */
		public void setLesIndividus(final List<IndividuPojo> lesIndividus) {
			this.lesIndividus = lesIndividus;
		}

		/**
		 * @return the resultSelected
		 */
		public Object[] getResultSelected() {
			return resultSelected;
		}

		/**
		 * @param resultSelected the resultSelected to set
		 */
		public void setResultSelected(final Object[] resultSelected) {
			this.resultSelected = resultSelected;
		}


		/**
		 * @return the pdfData
		 */
		public Map<Commission, List<NotificationOpinion>> getPdfData() {
			return pdfData;
		}

		/**
		 * @param pdfData the pdfData to set
		 */
		public void setPdfData(final Map<Commission, List<NotificationOpinion>> pdfData) {
			this.pdfData = pdfData;
		}

		/**
		 * @param commissionController the commissionController to set
		 */
		public void setCommissionController(final CommissionController commissionController) {
			this.commissionController = commissionController;
		}

		/**
		 * @param castorService the castorService to set
		 */
		public void setCastorService(final ISerializationService castorService) {
			this.castorService = castorService;
		}

		/**
		 * @return the transfert
		 */
		public Transfert getTransfert() {
			return transfert;
		}

		/**
		 * @param transfert the transfert to set
		 */
		public void setTransfert(final Transfert transfert) {
			this.transfert = transfert;
		}

		/**
		 * @return the inscriptionAdm
		 */
		public InscriptionAdm getInscriptionAdm() {
			return inscriptionAdm;
		}

		/**
		 * @param inscriptionAdm the inscriptionAdm to set
		 */
		public void setInscriptionAdm(final InscriptionAdm inscriptionAdm) {
			this.inscriptionAdm = inscriptionAdm;
		}

		/**
		 * @return the refused
		 */
		public Refused getRefused() {
			return refused;
		}

		/**
		 * @param refused the refused to set
		 */
		public void setRefused(final Refused refused) {
			this.refused = refused;
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
		 * @return the printOnlyDef
		 */
		public Boolean getPrintOnlyDef() {
			return printOnlyDef;
		}

		/**
		 * @param printOnlyDef the printOnlyDef to set
		 */
		public void setPrintOnlyDef(final Boolean printOnlyDef) {
			this.printOnlyDef = printOnlyDef;
		}

		/**
		 * @return the castorService
		 */
		public ISerializationService getCastorService() {
			return castorService;
		}

		/**
		 * @return the individuPojoSelected
		 */
		public IndividuPojo getIndividuPojoSelected() {
			return individuPojoSelected;
		}
		
		/**
		 * @param individuPojoSelected the individuPojoSelected to set
		 */
		public void setIndividuPojoSelected(final IndividuPojo individuPojoSelected) {
			this.individuPojoSelected = individuPojoSelected;
		}
		
		
		/**
		 * @param exportFormOrbeonController
		 */
		public void setExportFormOrbeonController(
				final ExportFormOrbeonController exportFormOrbeonController) {
			this.exportFormOrbeonController = exportFormOrbeonController;
		}
		
		
		
		
}
