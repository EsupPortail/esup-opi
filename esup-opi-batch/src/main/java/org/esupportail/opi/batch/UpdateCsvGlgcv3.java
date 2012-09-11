package org.esupportail.opi.batch;



import java.io.File;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.esupportail.apogee.domain.beans.referentiel.BacOuxEqu;
import org.esupportail.apogee.domain.beans.referentiel.etablissement.Etablissement;
import org.esupportail.apogee.domain.beans.referentiel.geographie.Departement;
import org.esupportail.apogee.domain.dto.referentiel.geographie.CommuneDTO;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;
import org.springframework.util.StringUtils;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.modules.XPathQueryService;

import org.esupportail.opi.domain.BusinessCacheService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.CursusExt;
import org.esupportail.opi.domain.beans.user.indcursus.CursusR1;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.web.beans.pojo.etat.EtatArriveComplet;
import org.esupportail.opi.web.beans.pojo.etat.EtatArriveIncomplet;
import org.esupportail.opi.web.beans.pojo.etat.EtatNonArrive;
import org.esupportail.opi.web.utils.Constantes;



/**
 * @author cgomez
 * 
 */
public class UpdateCsvGlgcv3  {
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(UpdateCsvGlgcv3.class);
	
	/**
	 * 
	 */
	private static final File FILE = new File("/data/appli/esup-opi/infosGlgcv3.csv");
	
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
	private static final String COD_CGE = "GG1";
	/**
	 * 
	 */
	private static final String COD_ETP = "GLGCV3";
	/**
	 * 
	 */
	private static final int COD_VRS_VET = 80;

	/**
	 * 
	 */
	private static final int UN = 1;
	/**
	 * 
	 */
//	private static final int DEUX = 2;
	/**
	 * 
	 */
//	private static final int TROIS = 3;
	/**
	 * 
	 */
	private static final int HUIT = 8;
	
	/**
	 * 
	 */
	private static String chaineInfos;
	
	
	/**
	 * Bean constructor.
	 */
	private UpdateCsvGlgcv3() {
		throw new UnsupportedOperationException();
	}
	
	
	/**
	 * 
	 */
	private static void initChaineInfos() {
		chaineInfos = "N° de dossier" + S
		+ "Etat du dossier" + S
		+ "Sexe" + S
		+ "Nom" + S
		+ "Prénom" + S
		+ "Nom_marital" + S
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
		+ "Lettre de motivation et curriculum vitae" + S
		+ "Dans la lettre de motivation, vous devez OBLIATOIREMENT préciser si vous postuler également à la Licence 3 Commerce Vente par apprentissage" + S
		+ "Dans la lettre de motivation, vous devez OBLIATOIREMENT préciser si vous postuler également à une autre Licence 3 de l'ISEM (MSG, CF, HT, licence professionnelle)" + S
		+ "Dans la lettre de motivation, vous devez OBLIATOIREMENT préciser si vous postuler également à une Licence 2 de l'ISEM" + S
		+ "Mentionner si vous souhaitez poursuivre en Master à l'ISEM" + S
		+ "Cursus post-Baccalauréat : moyennes obtenues au semestre 1" + S
		+ "Cursus post-Baccalauréat : moyennes obtenues au semestre 2" + S
		+ "Cursus post-Baccalauréat : moyennes obtenues au semestre 3" + S
		+ "Cours d'Anglais : note du Bac" + S
		+ "Cours d'Anglais : note semestre 1" + S
		+ "Cours d'Anglais : note semestre 2" + S
		+ "Cours d'Anglais : note semestre 3" + S
		+ "Cours d'Allemand : note du Bac" + S
		+ "Cours d'Allemand : note semestre 1" + S
		+ "Cours d'Allemand : note semestre 2" + S
		+ "Cours d'Allemand : note semestre 3" + S
		+ "Cours d'Espagnol : note du Bac" + S
		+ "Cours d'Espagnol : note semestre 1" + S
		+ "Cours d'Espagnol : note semestre 2" + S
		+ "Cours d'Espagnol : note semestre 3" + S
		+ "Année(s) d'études à l'étranger : période(s)" + S
		+ "Année(s) d'études à l'étranger : diplôme obtenu" + S
		+ "Séjour(s) à l'étranger supérieur(s) à un mois : période(s)" + S
		+ "Séjour(s) à l'étranger supérieur(s) à un mois : motif(s)" + S
		+ "Séjour(s) à l'étranger supérieur(s) à un mois : pays" + S
		+ "TOEIC" + S
		+ "TOEFL" + S
		+ "Niveau d'informatique" + F;
	}
	
	
	/**
	 * 
	 */
	private static void scriptOrtho() {
		DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		DomainApoService domainApoService = (DomainApoService) BeanUtils.getBean("domainApoService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		BusinessCacheService businessCacheService = (BusinessCacheService) BeanUtils.getBean("businessCacheService");
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
			List<Individu> individus = new ArrayList<Individu>();
			individus.addAll(domainService.getIndividusTrtCmiState(trtCmi, EtatArriveComplet.I18N_STATE));
			individus.addAll(domainService.getIndividusTrtCmiState(trtCmi, EtatArriveIncomplet.I18N_STATE));
			individus.addAll(domainService.getIndividusTrtCmiState(trtCmi, EtatNonArrive.I18N_STATE));
			
			String driver = "org.exist.xmldb.DatabaseImpl";
			Class< ? > cl = Class.forName(driver);
			Database database = (Database) cl.newInstance();
			DatabaseManager.registerDatabase(database);
			
			String uri = "xmldb:exist://jerry.univ-montp1.fr/esup-opiR1/exist/xmlrpc";
			
			Collection root = DatabaseManager.getCollection(uri + "/db", "admin", "");
			XPathQueryService service = (XPathQueryService) root.getService("XPathQueryService", "1.0");
			service.setProperty("indent", "yes");
			
			
			
			////////////////////////////////////////////////////////////////
			/////////////////////////   TEST  //////////////////////////////
			
//			String numDossier = "21RHHKTM";
//			String requeteTest = ""
//				+ "for $x in doc('/db/OPI/" + COD_ETP + "-" + COD_VRS_VET + "-FI/data/"
//						+ numDossier + "/data.xml') "
//				+ "return concat("
//						+ "replace($x/form/section-1/control-2, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-1/control-29, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-1/control-30, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-1/control-31, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-1/control-28, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-4, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-22, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-23, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-5, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-24, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-25, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-26, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-6, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-19, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-20, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-21, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-8, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-16, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-17, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-18, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-9, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-15, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-10, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-35, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-14, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-11, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-12, '\"', \"'\"),'" + S + "',"
//						+ "replace($x/form/section-2/control-13, '\"', \"'\"))";
//
//			ResourceSet resultTest = service.query(requeteTest);
//			ResourceIterator iteratorTest = resultTest.getIterator();
//			
//			String resultChaineTest = "";
//			while (iteratorTest.hasMoreResources()) {
//				resultChaineTest = (String) iteratorTest.nextResource().getContent();
//				LOG.info("numDossier : " + numDossier + " --> Resultat DB XML : " + resultChaineTest);
//			}
			
			///////////////////////   TEST  //////////////////////////////
			//////////////////////////////////////////////////////////////
			
			
			
			
			//Parcours des individus 
			initChaineInfos();
			for (int i = 0; i < individus.size(); i++) {
				Individu ind = individus.get(i);
				
				String etatDossier = "En attente";
				for (IndVoeu v : ind.getVoeux()) {
					if (v.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi().
							getCodEtp().equals(COD_ETP)
							&& v.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi().
							getCodVrsVet() == COD_VRS_VET) {
						if (v.getState().equals(EtatArriveComplet.I18N_STATE)) {
							etatDossier = "Arrivé";
						} else if (v.getState().equals(EtatArriveIncomplet.I18N_STATE)) {
							etatDossier = "Arrivé mais incomplet";
						}
					}
				}
				
				//Qualite = 1 -> Mr
				String sexe = "Mr";
				if (ind.getSexe().equals("F")) {
					//Qualite = 3 -> Mle
					sexe = "Mle";
					if (ind.getNomUsuel() != null && !ind.getNomUsuel().isEmpty()
							&& !ind.getNomUsuel().equals(ind.getNomPatronymique())) {
						//Qualite = 2 -> Mme
						sexe = "Mme";
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
				//int jour = cal.get(Calendar.DAY_OF_MONTH);
				int mois = cal.get(Calendar.MONTH) + UN;
				int annee = cal.get(Calendar.YEAR);
				
				
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
				String titre = "Bac en cours d'obtention";
				IndBac bac = ind.getIndBac().iterator().next();
				int dateObtention = -1;
				if (bac.getDateObtention() != null && !bac.getDateObtention().isEmpty()) {
					dateObtention = Integer.parseInt(bac.getDateObtention());
				}
				if (!bac.getCodBac().equals("0000") && (
						annee > dateObtention
						|| (annee == dateObtention && mois >= HUIT))) {
					//Bac obtenu
					titre = "Bac obtenu";
					boolean testCodBacAT = bac.getCodBac().equals("0031")
						|| bac.getCodBac().equals("0032") || bac.getCodBac().equals("0035");
					if (testCodBacAT || bac.getCodBac().equals("0036")
							|| bac.getCodBac().equals("0037")) {
						//Autres
						titre = "Autres";
					} else if (bac.getCodBac().equals("0033") || bac.getCodBac().equals("0034")) {
						//E.S.E.U.
						titre = "E.S.E.U.";
					} else if (bac.getCodBac().equals("DAEA") || bac.getCodBac().equals("DAEB")) {
						//D.A.E.U.
						titre = "D.A.E.U.";
					}
				}
				
				//Bac série et département
				String bacSerie = "";
				if (bac.getDateObtention() != null && !bac.getDateObtention().isEmpty()
						&& bac.getCodBac() != null && !bac.getCodBac().isEmpty()) {
					BacOuxEqu bacOuxEqu = businessCacheService.getBacOuxEqu(
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
				+ "for $x in doc('/db/OPI/" + COD_ETP + "-" + COD_VRS_VET + "-FI/data/"
						+ ind.getNumDossierOpi() + "/data.xml') "
				+ "return concat("
				+ "replace(replace($x/form/section-1/control-2, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-1/control-29, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-1/control-30, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-1/control-31, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-1/control-28, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-4, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-22, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-23, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-5, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-24, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-25, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-26, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-6, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-19, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-20, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-21, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-8, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-16, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-17, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-18, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-9, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-15, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-10, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-35, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-14, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-11, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-12, '\"', \"'\"), '\n', ' '),'" + S + "',"
				+ "replace(replace($x/form/section-2/control-13, '\"', \"'\"), '\n', ' '))";
				
				ResourceSet result = service.query(requete);
				ResourceIterator iterator = result.getIterator();
				
				String resultOrbeon = "";
				while (iterator.hasMoreResources()) {
					resultOrbeon = (String) iterator.nextResource().getContent();
				}
				resultOrbeon = resultOrbeon.replaceAll("[\r\n]+", "");
				
				//
				if (!resultOrbeon.isEmpty()) {
					chaineInfos = chaineInfos
						+ ind.getNumDossierOpi() + S
						+ etatDossier + S
						+ sexe + S
						+ ind.getNomPatronymique() + S
						+ prenom + S 
						+ ind.getNomUsuel() + S
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
					LOG.info("resultOrbeon num dos " + ind.getNumDossierOpi() + " : " + resultOrbeon);
				} else {
					LOG.info("Dossiser sans formulaire complémentaire:" + ind.getNumDossierOpi());
					chaineInfos = chaineInfos
					+ ind.getNumDossierOpi() + S
					+ etatDossier + S
					+ sexe + S
					+ ind.getNomPatronymique() + S
					+ prenom + S
					+ ind.getNomUsuel() + S
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
				"syntax: " + UpdateCsvGlgcv3.class.getSimpleName() + " <options>"
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
