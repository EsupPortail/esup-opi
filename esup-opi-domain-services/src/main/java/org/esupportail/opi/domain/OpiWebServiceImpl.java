package org.esupportail.opi.domain;

import static fj.data.Option.fromString;
import static fj.data.Stream.iterableStream;
import fj.F;
import geographiemetier_06062007_impl.servicesmetiers.commun.apogee.education.gouv.GeographieMetierServiceInterface;
import gouv.education.apogee.commun.transverse.dto.opi.donneesopidto2.DonneesOpiDTO2;
import gouv.education.apogee.commun.transverse.dto.opi.donneesopidto2.TableauVoeu;
import gouv.education.apogee.commun.transverse.dto.opi.majconvocationdto.MAJConvocationDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majdernierdiplobtdto.MAJDernierDiplObtDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majdernieretbfrequentedto.MAJDernierEtbFrequenteDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majdonneesnaissancedto.MAJDonneesNaissanceDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majdonneespersonnellesdto2.MAJDonneesPersonnellesDTO2;
import gouv.education.apogee.commun.transverse.dto.opi.majetatcivildto.MAJEtatCivilDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majinscriptionparalleledto.MAJInscriptionParalleleDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majopiadressedto.MAJOpiAdresseDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majopibacdto.MAJOpiBacDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majopidacdto.MAJOpiDacDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majopiinddto2.MAJOpiIndDTO2;
import gouv.education.apogee.commun.transverse.dto.opi.majopivoeudto.MAJOpiVoeuDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majpremiereinscriptiondto.MAJPremiereInscriptionDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majprgechangedto.MAJPrgEchangeDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majsituationannpredto.MAJSituationAnnPreDTO;
import gouv.education.apogee.commun.transverse.dto.opi.majtitreaccesexternedto.MAJTitreAccesExterneDTO;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import opimetier_24022011_impl.servicesmetiers.commun.apogee.education.gouv.OpiMetierServiceInterface;
//import opimetier_24022011_impl.servicesmetiers.commun.apogee.education.gouv.OpiMetierServiceInterfaceService;

import org.esupportail.commons.annotations.cache.RequestCache;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.parameters.Transfert;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.indcursus.CursusExt;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.utils.Constantes;
import org.springframework.util.StringUtils;

/**
 * Universite de Rennes 1 - 2007
 * Classe : OpiWebServiceImpl.java
 * Description : Met e jour la base APOGEE via l'appel du WEBSERVICE OPI.
 * Copyright : Copyright (c) 2007
 * @author cleprous
 * @version 1.0
 */
public class OpiWebServiceImpl implements OpiWebService {


	/*
	 *************************** PROPERTIES ******************************** */

	/**
	 * A log.
	 */
	private final Logger log = new LoggerImpl(OpiWebServiceImpl.class);

	/**
	 * see {@link DomainApoService}.
	 */
//	private DomainApoService domainApoService;
	/**
	 * Can read the table of GeographiqueMetier in Apogee.
	 */
	private OpiMetierServiceInterface remoteApoRenOpiMetier;
	
	
	/**
	 * see {@link Transfert}.
	 */
	private Transfert transfert;

	/**
	 * Code type etab correspondant aux universitÃÂ©s.
	 */
	private String typeEtabUniv;
	
	/**
	 * Code de l'etablissement.
	 */
	private String codEtabUniv;


	/*
	 *************************** INIT ************************************** */


	/**
	 * Constructor.
	 */
	public OpiWebServiceImpl() {
		super();
	}


	/*
	 *************************** METHODS *********************************** */


	public synchronized Boolean launchWebService(final Individu individu, final List<IndVoeu> voeux) {
		if (log.isDebugEnabled()) {
			log.debug("entering launchWebService( " 
					+ individu 
					+ ", " + voeux + " )");
		}
		//INIT
		if (individu != null && voeux != null) {
//			OpiMetierServiceInterface serviceOpi = new OpiMetierServiceInterfaceProxy();
//			OpiMetierServiceInterface serviceOpi = (OpiMetierSoapBindingStub) WSUtils
//						.getService(WSUtils.OPI_SERVICE_NAME, null, null);

//		    OpiMetierServiceInterface serviceOpi =
//		        new OpiMetierServiceInterfaceService().getOpiMetier();
			
//			DonneesOpiDTO2 donneesOPI = initDonneesOpi(serviceOpi, individu, voeux);
//			DonneesOpiDTO donneesOPI = initDonneesOpiTest();
			DonneesOpiDTO2 donneesOPI = initDonneesOpi(individu, voeux);
			log.info("derversement dans Apo du candidat (numero dossier = " + individu.getNumDossierOpi());

			
			try {
				log.info("execute serviceOpi.mettreajourDonneesOpi");
				remoteApoRenOpiMetier.mettreajourDonneesOpiV2(donneesOPI);
//				serviceOpi.mettreajourDonneesOpiV2(donneesOPI);

				log.info("after execute serviceOpi.mettreajourDonneesOpi");
				return true;
				// Traitement des exceptions
				//TODO: Fix this !!
//			} catch (WebBaseException _ex) {
//				log.error("Web Exception levee de type " + _ex);
//				log.error(_ex.getLastErrorMsg());
				//_ex.printStackTrace();
			} catch (Exception _ex) {
				log.error("Java Exception levee de type " + _ex);
				_ex.printStackTrace();
			}
		} else {
			log.warn("Traitement annule l'individu et/ou le voeu choisis est null");
		}
		return false;

	}

	@SuppressWarnings("unused")
	private DonneesOpiDTO2 initDonneesOpiTest() {
		DonneesOpiDTO2 donneesOPI = new DonneesOpiDTO2();
		MAJOpiIndDTO2 individu = new MAJOpiIndDTO2();
		MAJEtatCivilDTO etatCivil = new MAJEtatCivilDTO();
		MAJDonneesNaissanceDTO donneesNaissance = new MAJDonneesNaissanceDTO();
		MAJPremiereInscriptionDTO premiereInscription = new MAJPremiereInscriptionDTO();
		MAJDonneesPersonnellesDTO2 donneesPersonnelles = new MAJDonneesPersonnellesDTO2();
		MAJPrgEchangeDTO prgEchange = new MAJPrgEchangeDTO();
		MAJDernierEtbFrequenteDTO dernierEtbFrequente = new MAJDernierEtbFrequenteDTO();
		MAJSituationAnnPreDTO situationAnnPre = new MAJSituationAnnPreDTO();
		MAJDernierDiplObtDTO dernierDiplObt = new MAJDernierDiplObtDTO();
		MAJInscriptionParalleleDTO inscriptionParallele = new MAJInscriptionParalleleDTO();
		MAJOpiAdresseDTO adresseFixe = new MAJOpiAdresseDTO();
		MAJOpiAdresseDTO adresseAnnuelle = new MAJOpiAdresseDTO();
		MAJOpiBacDTO bac = new MAJOpiBacDTO();
		MAJOpiDacDTO dac = new MAJOpiDacDTO();
		MAJOpiVoeuDTO[] voeux = new MAJOpiVoeuDTO[1];
		voeux[0] = new MAJOpiVoeuDTO();
		MAJTitreAccesExterneDTO titreAccesExterne1 = new MAJTitreAccesExterneDTO();
		MAJConvocationDTO convocation1 = new MAJConvocationDTO();

		// /////////////////////////
		// / DEBUT TESTS 1A & 1B ///
		// /////////////////////////
		// Donnees de l'individu
		individu.setCodOpiIntEpo("BSGLW15H");
		individu.setCodEtuOpi(null);
		// Etat Civil
		etatCivil.setLibNomPatIndOpi("TESTYL");
		etatCivil.setLibPr1IndOpi("TESTYL");
		etatCivil.setCodSexEtuOpi("M");
		// Donnees Naissance
		donneesNaissance.setDateNaiIndOpi("25121984");
		donneesNaissance.setTemDateNaiRelOpi("N");
		donneesNaissance.setCodPayNat("100");
		donneesNaissance.setCodTypDepPayNai("D");
		donneesNaissance.setCodDepPayNai("035");
		donneesNaissance.setLibVilNaiEtuOpi("RENNES");
		// premier inscription
		premiereInscription.setDaaEnsSupOpi("2003");
		premiereInscription.setDaaEntEtbOpi("2003");
		premiereInscription.setDaaEtbOpi("2003");
		premiereInscription.setCodEtb("0351842M");
		// donnees personnelles
		donneesPersonnelles.setAdrMailOpi("yoann.lecuyer@univ-rennes1.fr");
		// dernier etb frequente
		dernierEtbFrequente.setCodEtbAntIaa("0352291A");
		dernierEtbFrequente.setCodTpeAntIaa("14");
		dernierEtbFrequente.setDaaEtbAntIaaOpi("2005");
		dernierEtbFrequente.setCodTypDepPayAntIaaOpi("P");
		dernierEtbFrequente.setCodDepPayAntIaaOpi("100");
		// ADRESSE FIXE
		adresseFixe.setCodBdi("35000");
		adresseFixe.setCodCom("35238");
		adresseFixe.setLib1("rue test");
		adresseFixe.setLib2("test complÃÂ©ment 1");
		adresseFixe.setLib3("test complÃÂ©ment 2");
		adresseFixe.setLibAde("");
		adresseFixe.setNumTel("0626801729");
		adresseFixe.setCodPay("100");
		// BAC
		bac.setCodBac("S");
		bac.setCodTpe("LY");
		bac.setCodEtb("0350793X");
		bac.setDaaObtBacOba("2001");
		bac.setCodDep("035");
		// DAC
		// VOEUX
		// Premier Voeu
		//voeux[0].setNumCls(new Integer(1));
		//voeux[0].setCodCmp("907");
		voeux[0].setCodCge("SR");
		voeux[0].setCodDip("SM301");
		voeux[0].setCodVrsVdi(Integer.valueOf(281));
		voeux[0].setCodEtp("SM3014");
		voeux[0].setCodVrsVet(Integer.valueOf(281));
		//voeux[0].setCodSpe1Opi("0026");
		//voeux[0].setCodSpe2Opi("0068");
		//voeux[0].setCodSpe3Opi(null);
		//voeux[0].setCodTyd("VA");
		//voeux[0].setCodAttDec("C");
		voeux[0].setCodDecVeu("F");
		voeux[0].setCodDemDos("T");
		//voeux[0].setCodMfo("HA");
		voeux[0].setTemValPsd("O");
		voeux[0].setNumCls(new Integer(1));
		//voeux[0].setLibCmtJur("juste une remarque");
		// Titre acces externe voeu 1
		titreAccesExterne1.setCodDacOpi("410");
		titreAccesExterne1.setCodEtbDacOpi("0352291A");
		titreAccesExterne1.setCodTpeDacOpi("14");
		//titreAccesExterne1.setCodDepPayDacOpi("035");
		//titreAccesExterne1.setCodTypDepPayDacOpi("D");
		titreAccesExterne1.setDaaDacOpi("2005");
		// Convocation voeu 1
		//convocation1.setDatCvc("01092003");
		//convocation1.setDhhCvc(new Integer(10));
		//convocation1.setDmnCvc(new Integer(30));
		individu.setEtatCivil(etatCivil);
		individu.setDonneesNaissance(donneesNaissance);
		individu.setPremiereInscription(premiereInscription);
		individu.setDonneesPersonnelles(donneesPersonnelles);
		individu.setPrgEchange(prgEchange);
		individu.setDernierEtbFrequente(dernierEtbFrequente);
		individu.setDernierDiplObt(dernierDiplObt);
		individu.setSituationAnnPre(situationAnnPre);
		individu.setInscriptionParallele(inscriptionParallele);
		donneesOPI.setIndividu(individu);

		donneesOPI.setAdresseFixe(adresseFixe);
		donneesOPI.setAdresseAnnuelle(adresseAnnuelle);
		donneesOPI.setBac(bac);
		donneesOPI.setDac(dac);

		if (voeux[0] != null) {
			voeux[0].setTitreAccesExterne(titreAccesExterne1);
			voeux[0].setConvocation(convocation1);
		}
		donneesOPI.setVoeux(
		    new TableauVoeu().withItem(Arrays.asList(voeux)));
		return donneesOPI;
	}
	
	/**
	 * charge des donnees OPI.
	 * @param individu 
	 * @param wishes 
	 * @return DonneesOpiDTO
	 */
	private DonneesOpiDTO2 initDonneesOpi(
			final Individu individu,
			final List<IndVoeu> wishes) {

		/* le voeu retourne est definitif car auparavant le candidat a fait le choix la formation */
		//VoeuxDTO voeu = findVoeuFromIndividuForFormation(individu, formation);

		//***************INIT ************************** 
		DonneesOpiDTO2 donneesOPI = new DonneesOpiDTO2(); 
		MAJOpiIndDTO2 individuOpi = new MAJOpiIndDTO2();
		MAJEtatCivilDTO etatCivil = new MAJEtatCivilDTO();
		MAJDonneesNaissanceDTO donneesNaissance = new MAJDonneesNaissanceDTO();
		MAJPremiereInscriptionDTO premiereInscription = new MAJPremiereInscriptionDTO();
		MAJDonneesPersonnellesDTO2 donneesPersonnelles = new MAJDonneesPersonnellesDTO2();
		MAJDernierEtbFrequenteDTO lastEtab = new MAJDernierEtbFrequenteDTO();

		// Donnees de l'individu
		individuOpi.setCodOpiIntEpo(individu.getNumDossierOpi());
		if (StringUtils.hasText(individu.getCodeEtu())) {
			individuOpi.setCodEtuOpi(Integer.parseInt(individu.getCodeEtu()));
		}

		// Etat Civil
		etatCivil.setLibNomPatIndOpi(individu.getNomPatronymique());
		etatCivil.setLibNomUsuIndOpi(individu.getNomUsuel());
		etatCivil.setLibPr1IndOpi(individu.getPrenom());
		etatCivil.setLibPr2IndOpi(individu.getPrenom2());
		etatCivil.setCodSexEtuOpi(individu.getSexe());
		etatCivil.setCodNneIndOpi(individu.getCodeNNE());
		etatCivil.setCodCleNneIndOpi(individu.getCodeClefNNE());


		// Premiere Inscription
		IndCursusScol firstCursus = null;
		String oldYearStr = null;
		IndCursusScol firstCursusUni = null;
		String oldYearUniStr = null;
		IndCursusScol firstCursusEtab = null;
		String oldYearEtabStr = null;
		for (IndCursusScol c : individu.getCursusScol()) {
			// premiÃÂ¨re inscription dans l'enseignement supÃÂ©rieur
			if (oldYearStr == null ) {
				oldYearStr = c.getAnnee();
				firstCursus =  c;
			} else {
				SimpleDateFormat s = new SimpleDateFormat(Constantes.YEAR_FORMAT);
				try {
					Date oldYear = s.parse(oldYearStr);
					Date year = s.parse(c.getAnnee());

					if (oldYear.after(year))  {
						oldYearStr = c.getAnnee();
						firstCursus = c;
					}

				} catch (ParseException e) {
					log.error("error parse date annee cursus " + e);
				}
			}
			// premiÃÂ¨re inscription dans une universitÃÂ©
			if (c.getCodTypeEtab() != null && c.getCodTypeEtab().equals(typeEtabUniv)) {
				if (oldYearUniStr == null ) {
					oldYearUniStr = c.getAnnee();
					firstCursusUni =  c;
				} else {
					SimpleDateFormat s = new SimpleDateFormat(Constantes.YEAR_FORMAT);
					try {
						Date oldYear = s.parse(oldYearUniStr);
						Date year = s.parse(c.getAnnee());
	
						if (oldYear.after(year))  {
							oldYearUniStr = c.getAnnee();
							firstCursusUni = c;
						}
	
					} catch (ParseException e) {
						log.error("error parse date annee cursus " + e);
					}
				}
			}
			// premiÃÂ¨re inscription dans l'ÃÂ©tablissement
			if (c.getCodEtablissement() != null && c.getCodEtablissement().equals(codEtabUniv)) {
				if (oldYearEtabStr == null ) {
					oldYearEtabStr = c.getAnnee();
					firstCursusEtab =  c;
				} else {
					SimpleDateFormat s = new SimpleDateFormat(Constantes.YEAR_FORMAT);
					try {
						Date oldYear = s.parse(oldYearEtabStr);
						Date year = s.parse(c.getAnnee());
	
						if (oldYear.after(year))  {
							oldYearEtabStr = c.getAnnee();
							firstCursusEtab = c;
						}
	
					} catch (ParseException e) {
						log.error("error parse date annee cursus " + e);
					}
				}
			}
		}

		// premiÃÂ¨re inscription dans l'enseignement supÃÂ©rieur
		if (firstCursus != null) {
			premiereInscription.setDaaEnsSupOpi(firstCursus.getAnnee());
			premiereInscription.setDaaEtrSup(null);

		}
		// premiÃÂ¨re inscription dans une universitÃÂ©
		if (firstCursusUni != null) {
			premiereInscription.setDaaEtbOpi(firstCursusUni.getAnnee());
			premiereInscription.setCodEtb(firstCursusUni.getCodEtablissement());

		}
		// premiÃÂ¨re inscription dans l'ÃÂ©tablissement
		if (firstCursusEtab != null) {
			premiereInscription.setDaaEntEtbOpi(firstCursusEtab.getAnnee());

		}
		
		//dernier etablissement 
		IndCursusScol cur = getLastCursus(individu);
		if (cur != null) {
			if (StringUtils.hasText(cur.getCodEtablissement())) {
				//etablissement francais
				lastEtab.setCodTypDepPayAntIaaOpi("P");
				lastEtab.setCodDepPayAntIaaOpi(Constantes.CODEFRANCE);
			}
			//modification suite au nullpointer etablissement cursus postbac
            try {
				lastEtab.setCodEtbAntIaa(cur.getCodEtablissement());
				lastEtab.setCodTpeAntIaa(cur.getCodTypeEtab());
				lastEtab.setDaaEtbAntIaaOpi(cur.getAnnee());
            } catch (Exception e) {
                log.error("error etablissement cursus postbac " + e);
            }
            //fin modification
		}

		// Donnees Naissance
		//TODO: Fix this !!
//		String dateNai =  Utilitaires.convertDateToString(
//				individu.getDateNaissance(), Constantes.DATE_SHORT_FORMAT);
		String dateNai = "";
		donneesNaissance.setDateNaiIndOpi(dateNai);
		donneesNaissance.setTemDateNaiRelOpi("N");
		donneesNaissance.setCodPayNat(individu.getCodPayNaissance());

		String dep = "";
		String temDP = "";
		if (Constantes.CODEFRANCE.equals(individu.getCodPayNaissance())) {
			/* France */
			dep = individu.getCodDepPaysNaissance();
			temDP = "D";
		} else {
			/* etranger */
			dep = individu.getCodPayNaissance();
			temDP = "P";
		}
		donneesNaissance.setCodTypDepPayNai(temDP);
		donneesNaissance.setCodDepPayNai(dep);
		donneesNaissance.setLibVilNaiEtuOpi(individu.getVilleNaissance());


		// donnees personnelles
		donneesPersonnelles.setAdrMailOpi(individu.getAdressMail());
		donneesPersonnelles.setNumTelPorOpi(individu.getNumeroTelPortable());

		// ADRESSE.
		MAJOpiAdresseDTO adresseFixe = mettreajourOpiAdresse(individu);

		// BAC
		MAJOpiBacDTO bac = mettreajourOpiBac(individu);

		// DAC
		//TODO ÃÂ  voir si on continue d'ajouter les DAC
		// manque le niveau de formation pour ajouter correctement
//		MAJOpiDacDTO dac = mettreajourOpiDac(individu);
		MAJOpiDacDTO dac = new MAJOpiDacDTO();

		// VOEUX
		//Premier Voeu
		MAJOpiVoeuDTO[] voeux = mettreajourOpiVoeux(individu, wishes);


		individuOpi.setEtatCivil(etatCivil);
		individuOpi.setDonneesNaissance(donneesNaissance);
		individuOpi.setPremiereInscription(premiereInscription);
		individuOpi.setDernierEtbFrequente(lastEtab);
		individuOpi.setDonneesPersonnelles(donneesPersonnelles);
		donneesOPI.setIndividu(individuOpi);

		donneesOPI.setAdresseFixe(adresseFixe);
		donneesOPI.setBac(bac);
		donneesOPI.setDac(dac);
		donneesOPI.setVoeux(
		    new TableauVoeu().withItem(Arrays.asList(voeux)));

		return donneesOPI;
	}

	/**
	 * Mise a jour donnee OpiBac().
	 * @param individu IndividuDTO
	 * @param voeu VoeuxDTO
	 * @return MAJOpiBacDTO
	 */
	private MAJOpiBacDTO mettreajourOpiBac(final Individu individu) {

		IndBac b = null;
		Date daaObt = null;
		SimpleDateFormat s = new SimpleDateFormat(Constantes.YEAR_FORMAT);
		/* >> Donnees opi_bac */
		//on recupere les dernier bac
		try {
			for (IndBac bac : individu.getIndBac()) {
				if (daaObt == null) {
					daaObt = s.parse(bac.getDateObtention());
					b = bac;
				} else if (daaObt.before(s.parse(bac.getDateObtention()))) {
					daaObt = s.parse(bac.getDateObtention());
					b = bac;
				}
			}
		} catch (Exception e) {
			log.error(e + "probleme parse DateObtention");
		}

		MAJOpiBacDTO bacOpi = new MAJOpiBacDTO();
		bacOpi.setCodBac(b.getCodBac());
		bacOpi.setCodEtb(b.getCodEtb());
		bacOpi.setCodDep(b.getCodDep());
		bacOpi.setCodMention(b.getCodMnb());
		bacOpi.setCodTpe(b.getCodTpe());
		bacOpi.setDaaObtBacOba(b.getDateObtention());
		return bacOpi;
	}


	/**
	 * Mise e jour donnee OpiBac().
	 * @param individu IndividuDTO
	 * @param voeu VoeuxDTO
	 * @return MAJOpiBacDTO
	 */
	@SuppressWarnings("unused")
	private MAJOpiDacDTO mettreajourOpiDac(final Individu individu) {
		MAJOpiDacDTO dacOpi = new MAJOpiDacDTO();

		for (IndCursusScol curScol : individu.getCursusScol()) {
			if (curScol instanceof CursusExt) {
				CursusExt cursE = (CursusExt) curScol;
				dacOpi.setCodDac(cursE.getCodDac());
				dacOpi.setCodTpe(cursE.getCodTypeEtab());
				dacOpi.setCodEtb(cursE.getCodEtablissement());
				dacOpi.setLibCmtDac(cursE.getLibelle());
				dacOpi.setDaaUniDac(cursE.getAnnee());
			}

		}


		return dacOpi;
	}


	/**
	 * Chargement des donnees OPI_Adresse pour un individu.
	 * @param individu
	 * @return MAJOpiAdresseDTO
	 */
	private static MAJOpiAdresseDTO mettreajourOpiAdresse(final Individu individu) {
		MAJOpiAdresseDTO adresse = new MAJOpiAdresseDTO();
		Adresse aFix = individu.getAdresses().get(Constantes.ADR_FIX);
		/* >> Donnees adresse_opi */
		/* adresse */
		adresse.setCodBdi(aFix.getCodBdi());
		adresse.setCodCom(aFix.getCodCommune());
		adresse.setLib1(aFix.getAdr1());
		adresse.setLib2(aFix.getAdr2());
		adresse.setLib3(aFix.getAdr3());
		adresse.setLibAde(
		    fromString(aFix.getLibComEtr()).orSome(""));
		adresse.setNumTel(aFix.getPhoneNumber());
		adresse.setCodPay(aFix.getCodPays());


		return adresse;

	}


	/**
	 * Mise e jour donnee OpiVoeux.
	 * @param individu IndividuDTO
	 * @param voeu VoeuxDTO
	 * @param formation FormationDTO
	 * @return MAJOpiVoeuDTO
	 */
	private MAJOpiVoeuDTO[] mettreajourOpiVoeux(
			final Individu individu,
			final List<IndVoeu> wishes) {
		MAJOpiVoeuDTO[] voeux = new MAJOpiVoeuDTO[wishes.size()];

		for (int i = 0; i < wishes.size(); ++i) {

//			MAJOpiVoeuDTO v = new MAJOpiVoeuDTO();
			voeux[i] = new MAJOpiVoeuDTO();
			//TODO On peut le retrouver avec le traitmentCMI de la commission
			voeux[i].setCodCge(wishes.get(i).getLinkTrtCmiCamp().getTraitementCmi()
					.getVersionEtpOpi().getCodCge());
			voeux[i].setNumCls(new Integer(i + 1));
			
			//Logiquement toujours egale a F 
			voeux[i].setCodDecVeu(
			    iterableStream(wishes.get(i).getAvis()).find(
			        new F<Avis, Boolean>() {
			            public Boolean f(Avis a) {
			                return a.getTemoinEnService();}}).orSome(
			                    new Avis()).getResult().getCodeApogee());
			
			//c pour acces libre et T pour acces selectif
			if (wishes.get(i).getCodTypeTrait().equals(transfert.getCode())) {
				voeux[i].setCodDemDos("C");
			} else {
				voeux[i].setCodDemDos("T");
			}
			//tous les voeux dans OPI passe par une validation
			voeux[i].setTemValPsd("O");

			//TODO inclure codDip et codvrsVdi dans trtcmi (FAIT le 28/09/2009 ÃÂ  tester)	    	
			String codDip = wishes.get(i).getLinkTrtCmiCamp().getTraitementCmi().getCodDip();
			Integer codVrsVdi = wishes.get(i).getLinkTrtCmiCamp().getTraitementCmi().getCodVrsDip();
			if (StringUtils.hasText(codDip)) {
				voeux[i].setCodDip(codDip);
				voeux[i].setCodVrsVdi(codVrsVdi);
			}
			voeux[i].setCodEtp(wishes.get(i).getLinkTrtCmiCamp().getTraitementCmi()
					.getVersionEtpOpi().getCodEtp());
			voeux[i].setCodVrsVet(wishes.get(i).getLinkTrtCmiCamp().getTraitementCmi()
					.getVersionEtpOpi().getCodVrsVet());


			MAJConvocationDTO convocation = new MAJConvocationDTO();
			MAJTitreAccesExterneDTO titre = mettreTitreAccesExterne(individu);
//			if (titre != null) {
				voeux[i].setTitreAccesExterne(titre);
//			}
			voeux[i].setConvocation(convocation);
			
//			voeux[i] = v;
		}




		return voeux;
	}


	/**
	 * Mise a jour donnae MAJTitreAccesExterneDTO.
	 * @param individu IndividuDTO
	 * @return MAJTitreAccesExterneDTO
	 */
	private MAJTitreAccesExterneDTO mettreTitreAccesExterne(
			final Individu individu) {

//		MAJTitreAccesExterneDTO titre = null;
		MAJTitreAccesExterneDTO titre = new MAJTitreAccesExterneDTO();

		//on prend la derniere annee du cursus
		CursusExt cExt = null;
		IndCursusScol cur = getLastCursus(individu);
		if (cur != null && cur instanceof CursusExt) {
			cExt = (CursusExt) cur;
		}
		
		if (cExt != null) {
//			titre = new MAJTitreAccesExterneDTO();
			titre.setCodDacOpi(cExt.getCodDac());
			titre.setCodEtbDacOpi(cExt.getCodEtablissement());
			titre.setCodTpeDacOpi(cExt.getCodTypeEtab());
			titre.setDaaDacOpi(cExt.getAnnee());
		}

		return titre;
	}


	/**
	 * @param ind
	 * @return IndCursusScol the last cursus scol.
	 */
	@RequestCache
	private IndCursusScol getLastCursus(final Individu ind) {
		IndCursusScol cur = null;
		String oldYearStr = null;
		for (IndCursusScol c : ind.getCursusScol()) {
			if (oldYearStr == null ) {
				oldYearStr = c.getAnnee();
				cur =  c;
			} else {
				SimpleDateFormat s = new SimpleDateFormat(Constantes.YEAR_FORMAT);
				try {
					Date oldYear = s.parse(oldYearStr);
					Date year = s.parse(c.getAnnee());

					if (oldYear.before(year))  {
						oldYearStr = c.getAnnee();
						cur = c;
					}

				} catch (ParseException e) {
					log.error("error parse date annee cursus " + e);
				}
			}
		}
		return cur;
	}

	/*
	 *************************** ACCESSORS ********************************* */

	/**
	 * @param domainService the domainService to set
	 */
//	public void setDomainApoService(final DomainApoService domainApoService) {
//		this.domainApoService = domainApoService;
//	}


	/**
	 * @param transfert the transfert to set
	 */
	public void setTransfert(final Transfert transfert) {
		this.transfert = transfert;
	}


	/**
	 * @param typeEtabUniv
	 */
	public void setTypeEtabUniv(final String typeEtabUniv) {
		this.typeEtabUniv = typeEtabUniv;
	}


	/**
	 * @param codEtabUniv
	 */
	public void setCodEtabUniv(final String codEtabUniv) {
		this.codEtabUniv = codEtabUniv;
	}





}
