package org.esupportail.opi.batch;



import java.io.File;
import java.io.RandomAccessFile;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.BusinessCacheService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatArriveComplet;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.CursusExt;
import org.esupportail.opi.domain.beans.user.indcursus.CursusR1;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.CommuneDTO;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.Etablissement;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XPathQueryService;



/**
 * @author cgomez
 * 
 */
public class UpdateOrthoAccesScript  {
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(UpdateOrthoAccesScript.class);
	
	/**
	 * 
	 */
	private static final File FILE = new File("/data/appli/esup-opi/infosOrtho.csv");
	
	/**
	 * 
	 */
	private static final String S = "|";
	/**
	 * 
	 */
	private static final String F = "\n";

	/**
	 * 
	 */
	private static final String COD_CGE = "FG1";
	/**
	 * 
	 */
	private static final String COD_ETP = "MCORT1";
	/**
	 * 
	 */
	private static final int COD_VRS_VET = 40;
	/**
	 * Path to the config.properties file.
	 */
	private static final String RESOURCE_CONFIG = "classpath:properties/config.properties";
	/**
	 * Name ot the orbeon uri property in the config.propeties file.
	 */
	private static final String PROPERTY_ORBEON_URI = "init.orbeon.uri";
	/**
	 * 
	 */
	private static final int UN = 1;
	/**
	 * 
	 */
	private static final int DEUX = 2;
	/**
	 * 
	 */
	private static final int TROIS = 3;
	/**
	 * 
	 */
	private static final int HUIT = 8;
	
	/**
	 * 
	 */
	private static String chaineInfos;
	
	private static final SimpleDateFormat sdfDateExcel = new SimpleDateFormat("yyyy/MM/dd");
	
	
	/**
	 * Bean constructor.
	 */
	private UpdateOrthoAccesScript() {
		throw new UnsupportedOperationException();
	}
	
	
	/**
	 * 
	 */
	private static void initChaineInfos() {
		chaineInfos = "No_liaison" + S
		+ "Faculté_origine" + S
		+ "Qualité" + S
		+ "Nom" + S
		+ "Prénom" + S
		+ "Nom_marital" + S
		+ "Email" + S
		+ "Date_naissance" + S
		+ "Chèque_date" + S
		+ "Chèque_type" + S
		+ "Adresse_ligne1" + S
		+ "Adresse_ligne2" + S
		+ "Adresse_ligne3" + S
		+ "Titre" + S
		+ "Bac_annee" + S
		+ "Bac_serie" + S
		+ "Bac_departement" + S
		+ "Inscrit_univ_annee" + S
		+ "Univ_insc_annee" + S
		+ "Etudes_insc_annee" + S
		+ "Inscrit_univ_avant" + S
		+ "Univ_insc_avant" + S
		+ "Etudes_insc_avant" + S
		+ "Resultat_univ_avant" + S
		+ "Diplome_univ_avant" + S
		//Orbeon
		+ "Chèque_montant" + S
		+ "Chèque_domiciliation" + S
		+ "Chèque_emetteur" + S
		+ "Chèque_compte" + S
		+ "Activite_salariee_annee" + S
		+ "Activite_exercee_annee" + S
		+ "Date_activite_annee" + S
		+ "Activite_salariee_avant" + S
		+ "Activite_exercee_avant" + S
		+ "Mois_activité_avant" + S
		+ "Concours_montp" + S
		+ "Nb_inscriptions" + S
		+ "Profession_pere" + S
		+ "Handicap" + S
		+ "HandicapON" + F;
	}
	
	
	/**
	 * 
	 */
	private static void scriptOrtho() {
		DomainService domainService = (DomainService) ApplicationContextHolder.getContext().getBean("domainService");
		DomainApoService domainApoService = (DomainApoService) ApplicationContextHolder.getContext().getBean("domainApoService");
		ParameterService parameterService = (ParameterService) ApplicationContextHolder.getContext().getBean("parameterService");
		BusinessCacheService businessCacheService = (BusinessCacheService) ApplicationContextHolder.getContext().getBean("businessCacheService");
		//
		List<Departement> listDep = domainApoService.getDepartements();
		
		try {
			DatabaseUtils.open();
			DatabaseUtils.begin();
			if (FILE.exists()) {
				FILE.delete();
			}
			FILE.createNewFile();
			RandomAccessFile infosOrtho = new RandomAccessFile(FILE, "rw");
			
			LOG.info("début de la procédure");
			
			// récupération de tous les individus
			VersionEtpOpi versionEtpOpi = new VersionEtpOpi();
			versionEtpOpi.setCodCge(COD_CGE);
			versionEtpOpi.setCodEtp(COD_ETP);
			versionEtpOpi.setCodVrsVet(COD_VRS_VET);
			TraitementCmi trtCmi = parameterService.getTraitementCmi(versionEtpOpi, null);
			List<Individu> individus = domainService.getIndividusTrtCmiState(trtCmi, EtatArriveComplet.I18N_STATE);
			
			String driver = "org.exist.xmldb.DatabaseImpl";
			Class< ? > cl = Class.forName(driver);
			Database database = (Database) cl.newInstance();
			DatabaseManager.registerDatabase(database);
						
			DefaultResourceLoader drl = new DefaultResourceLoader();
			Resource res = drl.getResource(RESOURCE_CONFIG);
			Properties properties = new Properties();
			properties.load(res.getInputStream());
			String uri = properties.getProperty(PROPERTY_ORBEON_URI);
			LOG.info("URI : " + uri);
			
			Collection root = DatabaseManager.getCollection(uri + "/db", "admin", "");
			XPathQueryService service = (XPathQueryService) root.getService("XPathQueryService", "1.0");
			service.setProperty("indent", "yes");
			
			//Parcours des individus 
			initChaineInfos();
			for (int i = 0; i < individus.size(); i++) {
				Individu ind = individus.get(i);
				
				//Qualite = 1 -> Mr
				int qualite = 1;
				if (ind.getSexe().equals("F")) {
					//Qualite = 3 -> Mle
					qualite = TROIS;
					if (ind.getNomUsuel() != null && !ind.getNomUsuel().isEmpty()
							&& !ind.getNomUsuel().equals(ind.getNomPatronymique())) {
						//Qualite = 2 -> Mme
						qualite = DEUX;
					}
				}
				
				//Prénom
				String prenom;
				if (ind.getPrenom() != null && ind.getPrenom2() != null
						&& !ind.getPrenom().isEmpty() && !ind.getPrenom2().isEmpty()) {
					prenom = ind.getPrenom() + ", " + ind.getPrenom2();
				} else if (ind.getPrenom2() != null && !ind.getPrenom2().isEmpty()) {
					prenom = ind.getPrenom2();
				} else {
					prenom = ind.getPrenom();
				}
				
				//Date du jour
				Calendar cal = new GregorianCalendar();
//				int jour = cal.get(Calendar.DAY_OF_MONTH);
				int mois = cal.get(Calendar.MONTH) + UN;
				int annee = cal.get(Calendar.YEAR);
				
				Date dateDuJour = new Date();
				String laDateDuJour = sdfDateExcel.format(dateDuJour);
				
				
				//Adresse
				Adresse adresse = ind.getAdresses().get(Constantes.ADR_FIX);
				String adr1 = "" + adresse.getAdr1();
				String adr2 = adresse.getAdr2() + " " + adresse.getAdr3();
				String adr3 = "" + adresse.getCodBdi();
				if (adresse.getCodCommune() != null && !adresse.getCodCommune().isEmpty()) {
					CommuneDTO commune = domainApoService.getCommune(adresse.getCodCommune());
					if (commune != null) {
						adr3 = adr3 + " " + domainApoService.getCommune(
								adresse.getCodCommune()).getLibCom();
					}
				}
				
				//Titre du Bac
				//Bac en cours d'obtention (par default)
				String titre = "BE";
				IndBac bac = ind.getIndBac().iterator().next();
				int dateObtention = -1;
				if (bac.getDateObtention() != null && !bac.getDateObtention().isEmpty()) {
					dateObtention = Integer.parseInt(bac.getDateObtention());
				}
				if (!bac.getCodBac().equals("0000") && (
						annee > dateObtention
						|| (annee == dateObtention && mois >= HUIT))) {
					//Bac obtenu
					titre = "BO";
					boolean testCodBacAT = bac.getCodBac().equals("0031")
						|| bac.getCodBac().equals("0032") || bac.getCodBac().equals("0035");
					if (testCodBacAT || bac.getCodBac().equals("0036")
							|| bac.getCodBac().equals("0037")) {
						//Autres
						titre = "AT";
					} else if (bac.getCodBac().equals("0033") || bac.getCodBac().equals("0034")) {
						//E.S.E.U.
						titre = "E1";
					} else if (bac.getCodBac().equals("DAEA") || bac.getCodBac().equals("DAEB")) {
						//D.A.E.U.
						titre = "E2";
					}
				}
				
				
				//Bac série et département
				String bacSerie = "";
				if (bac.getDateObtention() != null && !bac.getDateObtention().isEmpty()
						&& bac.getCodBac() != null && !bac.getCodBac().isEmpty()) {
					BacOuxEqu bacOuxEqu = domainApoService.getBacOuxEqu(
							bac.getDateObtention(), bac.getCodBac());
					if (bacOuxEqu != null) {
						bacSerie = bacOuxEqu.getLibBac();
					}
				}
				String bacDepartement = bac.getCodDep();
				for (Departement dep : listDep) {
					if (dep.getCodDep().equals(bac.getCodDep())) { 
						bacDepartement = dep.getLibDep();
						break;
					}
				}
				
				//Inscription universitaire
				IndCursusScol cursusAnnee = null;
				IndCursusScol cursusAvant = null;
				for (IndCursusScol cursus : ind.getCursusScol()) {
					if (String.valueOf(annee + 1).equals(cursus.getAnnee())) {
						cursusAnnee = cursus;
					} else if (cursusAvant == null
							|| Integer.parseInt(cursusAvant.getAnnee())
								< Integer.parseInt(cursus.getAnnee())) {
						cursusAvant = cursus;
					}
				}
				//Cursus année
				String inscritUnivAnnee = "false";
				String univInscAnnee = "";
				String etudesInscAnnee = "";
				if (cursusAnnee != null) {
					inscritUnivAnnee = "true";
					univInscAnnee = cursusAnnee.getAnnee();
					if (cursusAnnee.getCodEtablissement() != null
							&& !cursusAnnee.getCodEtablissement().isEmpty()) {
						Etablissement etablissement = businessCacheService.
								getEtablissement(cursusAnnee.getCodEtablissement());
						if (etablissement != null) {
							etudesInscAnnee = etablissement.getLibEtb();
						}
					}
				}
				//Cursus avant
				String inscritUnivAvant = "false";
				String univInscAvant = "";
				String etudesInscAvant = "";
				String resultatUnivAvant = "";
				String diplomeUnivAvant = "";
				if (cursusAvant != null) {
					inscritUnivAvant = "true";
					univInscAvant = cursusAvant.getAnnee();
					if (cursusAvant.getCodEtablissement() != null
							&& !cursusAvant.getCodEtablissement().isEmpty()) {
						Etablissement etablissement = businessCacheService.
								getEtablissement(cursusAvant.getCodEtablissement());
						if (etablissement != null) {
							etudesInscAvant = etablissement.getLibEtb();
						}
					}
					//Resultat
					if ("N".equals(cursusAvant.getResultat())) {
						resultatUnivAvant = "Ajourné";
					}
					if ("O".equals(cursusAvant.getResultat())) {
						resultatUnivAvant = "Obtenu";
					}
					//Diplome
					if (cursusAvant instanceof CursusR1) {
						diplomeUnivAvant = ((CursusR1) cursusAvant).getLibEtape();
					} else {
						CursusExt curE = (CursusExt) cursusAvant;
						if (StringUtils.hasText(curE.getLibelle())) {
							diplomeUnivAvant = curE.getLibelle();
						} else {
							diplomeUnivAvant = curE.getLibDac();
						}
					}
				}
				
				//Orbeon
				String requete = ""
				+ "declare function local:ouiNonReplace($text as xs:string) \n "
				+ "as xs:string { \n "
				+ "if(fn:compare($text, 'Oui') = 0) \n "
				+ "then fn:string('true') \n "
				+ "else fn:string('false') \n "
				+ "}; \n "
				
				+ "declare function local:ouiNonHandicap($text as xs:string) \n "
				+ "as xs:string { \n "
				+ "if(fn:compare($text, '') = 0) \n "
				+ "then fn:string('false') \n "
				+ "else fn:string('true') \n "
				+ "}; \n "
				
				+ "declare function local:zeroUnHandicap($text as xs:string) \n "
				+ "as xs:string { \n "
				+ "if(fn:compare($text, '') = 0) \n "
				+ "then fn:string('0') \n "
				+ "else fn:string('1') \n "
				+ "}; \n "
				
				+ "for $x in doc('/db/OPI/MCORT1-40-FI/data/" + ind.getNumDossierOpi() + "/data.xml') "
				+ "return concat("
						+ "'80','" + S + "',"
						+ "replace($x/form/section-1/control-2, '\"', \"'\"),'" + S + "',"
						+ "replace($x/form/section-1/control-3, '\"', \"'\"),'" + S + "',"
						+ "replace($x/form/section-1/control-4, '\"', \"'\"),'" + S + "',"
						+ "local:ouiNonReplace($x/form/section-3/control-7),'" + S + "',"
						+ "replace($x/form/section-3/control-8, '\"', \"'\"),'" + S + "',"
						+ "replace($x/form/section-3/control-9, '-', '/'),'" + S + "',"
						+ "local:ouiNonReplace($x/form/section-3/control-10),'" + S + "',"
						+ "replace($x/form/section-3/control-11, '\"', \"'\"),'" + S + "',"
						+ "replace($x/form/section-3/control-12, '\"', \"'\"),'" + S + "',"
						+ "local:ouiNonReplace($x/form/section-3/control-13),'" + S + "',"
						+ "replace($x/form/section-3/control-14, '\"', \"'\"),'" + S + "',"
						+ "replace($x/form/section-3/control-15, '\"', \"'\"),'" + S + "',"
						//Handicap
						+ "local:ouiNonHandicap($x/form/section-4/control-18),'" + S + "',"
						+ "local:zeroUnHandicap($x/form/section-4/control-18))";
				
				ResourceSet result = service.query(requete);
				ResourceIterator iterator = result.getIterator();
				
				String resultOrbeon = "";
				while (iterator.hasMoreResources()) {
					resultOrbeon = (String) iterator.nextResource().getContent();
				}
				
				//
				if (!resultOrbeon.isEmpty()) {
					chaineInfos = chaineInfos
						+ ind.getNumDossierOpi() + S
						+ "1" + S
						+ qualite + S
						+ ind.getNomPatronymique() + S
						+ prenom + S 
						+ ind.getNomUsuel() + S
						+ ind.getAdressMail() + S
						+ sdfDateExcel.format(ind.getDateNaissance()) + S
						+ laDateDuJour + S
						+ "BAN" + S
						+ adr1 + S
						+ adr2 + S
						+ adr3 + S
						+ titre + S
						+ dateObtention + S
						+ bacSerie + S
						+ bacDepartement + S
						+ inscritUnivAnnee + S
						+ univInscAnnee + S
						+ etudesInscAnnee + S
						+ inscritUnivAvant + S
						+ univInscAvant + S
						+ etudesInscAvant + S
						+ resultatUnivAvant + S
						+ diplomeUnivAvant + S
						//Orbeon
						+ resultOrbeon + F;
				} else {
					LOG.info("Dossiser sans formulaire complémentaire:" + ind.getNumDossierOpi());
					chaineInfos = chaineInfos
					+ ind.getNumDossierOpi() + S
					+ "1" + S
					+ qualite + S
					+ ind.getNomPatronymique() + S
					+ prenom + S
					+ ind.getNomUsuel() + S
					+ laDateDuJour + S
					+ "BAN" + S
					+ adr1 + S
					+ adr2 + S
					+ adr3 + S
					+ titre + S
					+ dateObtention + S
					+ bacSerie + S
					+ bacDepartement + S
					+ inscritUnivAnnee + S
					+ univInscAnnee + S
					+ etudesInscAnnee + S
					+ inscritUnivAvant + S
					+ univInscAvant + S
					+ etudesInscAvant + S
					+ resultatUnivAvant + S
					+ diplomeUnivAvant + S
					//Orbeon
					+ "80" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + S
					+ "" + F;
				}
			}
			
			//Sauvegarde dans le fichier
			infosOrtho.writeBytes(chaineInfos);
			infosOrtho.close();
			
			LOG.info("procédure terminée");
			DatabaseUtils.commit();

		} catch (Exception e) {
			DatabaseUtils.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtils.close();
		}
	}
	
	
	/**
	 * Print the syntax and exit.
	 */
	private static void syntax() {
		throw new IllegalArgumentException(
				"syntax: " + UpdateOrthoAccesScript.class.getSimpleName() + " <options>"
				+ "\nwhere option can be:"
				+ "\n- test-beans: test the required beans");
	}
	
	
	/**
	 * Dispatch dependaing on the arguments.
	 * @param args
	 */
	protected static void dispatch(final String[] args) {
		switch (args.length) {
		case 0:
			scriptOrtho();
			break;
		default:
			syntax();
		break;
		}
	}
	
	
	/**
	 * The main method, called by ant.
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			ApplicationService applicationService = ApplicationUtils.createApplicationService();
			LOG.info(applicationService.getName() + " v" + applicationService.getVersion());
			dispatch(args);
		} catch (Throwable t) {
			ExceptionUtils.catchException(t);
		}
	}
}
