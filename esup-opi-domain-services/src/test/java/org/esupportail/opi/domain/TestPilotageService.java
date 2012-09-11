/**
 * 
 */
package org.esupportail.opi.domain;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

import junit.framework.TestCase;

import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.utils.BeanUtils;
import org.esupportail.opi.dao.PilotageDaoService;
import org.esupportail.opi.domain.beans.etat.EtatArriveComplet;
import org.esupportail.opi.domain.beans.etat.EtatComplet;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.InscriptionAdm;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.DipAutCur;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;


/**
 * @author cleprous
 *
 */
public class TestPilotageService extends TestCase {
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * 
	 */
	//SM3012
//	private static final String CODE_ETP = "MCORT1";
	private static final String CODE_ETP = "DM2NOT";
	/**
	 * 
	 */
	//281
//	private static final int VRS_ETP = 40;
	private static final int VRS_ETP = 70;
	/**
	 * 
	 */
//	private static final String CODE_CMI = "ortho1";
	private static final String CODE_CMI = "DM2NOT";
	/**
	 * 
	 */
//	private static final int ID_CMI = 96;
	private static final int ID_CMI = 52;
	/**
	 * 
	 */
	private static final String COD_PAYS = "100";
	/**
	 * 
	 */
	private static final int COD_TYPE_DEC = 67;
	/**
	 * {@link PilotageService}.
	 */
	private PilotageDaoService pilotageDaoService;
	/**
	 * {@link ParameterService}.
	 */
	private ParameterService parameterService;
	
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public TestPilotageService() {
		//Constructors
	}
	/**
	 * Constructors.
	 * @param name
	 */
	public TestPilotageService(final String name) {
		super(name);
		//Constructors
	}
	
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * Search the individu with cod etu = 29005106.
	 */
	public void testgetResult() {
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();
			DomainApoService domainApo = (DomainApoService) BeanUtils.getBean("domainApoService");
			I18nService i18nService =  (I18nService) BeanUtils.getBean("i18nService");
			BusinessCacheService businessCacheService = (BusinessCacheService) BeanUtils.getBean("businessCacheService");
			
			VersionEtapeDTO versionEtpDto = businessCacheService.getVersionEtape(CODE_ETP, VRS_ETP);
//			VersionEtpOpi versionEtpOpi = new VersionEtpOpi(versionEtpDto);
			
			Campagne camp = getCampagne();
			
			DipAutCur dip = null;
			for (DipAutCur diplome : domainApo.getDipAutCurs()) {
				if (diplome.getCodDac().equals("011")) {
					dip = diplome;
				}
			}
			
			EtatIndividu etatIndComplet = new EtatComplet(i18nService);
			
			EtatVoeu etatVoeuArriveComplet = new EtatArriveComplet(i18nService);
			
			MotivationAvis motivation = getParameter().getMotivationsAvis(true).iterator().next();
			
			Pays pays = null;
			for (Pays p : domainApo.getPays()) {
				if (p.getCodPay().equals(COD_PAYS)) {
					pays = p;
				}
			}
			
			TypeConvocation typeConvoc = (InscriptionAdm) BeanUtils.getBean("inscriptionAdm");
			
			TypeDecision typeDec = (TypeDecision) getParameter().getNomenclature(COD_TYPE_DEC);
			
			Commission commission =  getParameter().getCommission(ID_CMI, CODE_CMI);
			
			// TODO: Fix this !!
			//RegimeInscription ri =  (FormationInitiale) BeanUtils.getBean("formationInitiale");
			
//			TraitementCmi trtCmi = getParameter().getTraitementCmi(versionEtpOpi, null);
			
			//Commission
			System.out.println("compteur Commission/dip : "
				+ getPilotage().statCommissionDiplomeCriteria(commission, dip, camp));
			System.out.println("compteur Commission/etatInd : "
				+ getPilotage().statCommissionEtatIndividuCriteria(commission, etatIndComplet, camp));
			System.out.println("compteur Commission/etatV : "
				+ getPilotage().statCommissionEtatVoeuCriteria(commission, etatVoeuArriveComplet, camp));
			System.out.println("compteur Commission/motivation : "
				+ getPilotage().statCommissionMotivationCriteria(commission, motivation, camp));
			System.out.println("compteur Commission/pays : "
				+ getPilotage().statCommissionPaysCriteria(commission, pays, camp));
			System.out.println("compteur Commission/typeConvoc : "
				+ getPilotage().statCommissionTypeConvocationCriteria(commission, typeConvoc, camp));
			System.out.println("compteur Commission/typeDec : "
				+ getPilotage().statCommissionTypeDecisionCriteria(commission, typeDec, camp));
			System.out.println("compteur Commission : "
				+ getPilotage().allIndividuCommission(commission, camp));
			
			//Composante
			for (CentreGestion composante : domainApo.getCentreGestion()) {
				if (composante.getCodCge().equals("FG1")) {
					System.out.println("compteur Composante/dip : "
						+ getPilotage().statComposanteDiplomeCriteria(composante, dip, camp));
					System.out.println("compteur Composante/etatInd : "
						+ getPilotage().statComposanteEtatIndividuCriteria(composante, etatIndComplet, camp));
					System.out.println("compteur Composante/etatV : "
						+ getPilotage().statComposanteEtatVoeuCriteria(composante, etatVoeuArriveComplet, camp));
					System.out.println("compteur Composante/motivation : "
						+ getPilotage().statComposanteMotivationCriteria(composante, motivation, camp));
					System.out.println("compteur Composante/pays : "
						+ getPilotage().statComposantePaysCriteria(composante, pays, camp));
					System.out.println("compteur Composante/typeConvoc : "
						+ getPilotage().statComposanteTypeConvocationCriteria(composante, typeConvoc, camp));
					System.out.println("compteur Composante/typeDec : "
						+ getPilotage().statComposanteTypeDecisionCriteria(composante, typeDec, camp));
					System.out.println("compteur Composante : "
						+ getPilotage().allIndividuComposante(composante, camp));
				}
			}
			
			//TODO : fix this !!
			//Mot clés
//			List<Ren1ClesAnnuFormPojo> listCles =  
//					domainApo.getDomainServiceRennes1().getRen1ClesAnnuForm();
//			Set<VersionEtapeDTO> listEtp = new HashSet<VersionEtapeDTO>();
			
			
//			for (Ren1ClesAnnuFormPojo motCle : listCles) {
//				if (motCle.getCodCles().equals("DROIT1")) {
//					for (VersionDiplomeDTO vrsDip : domainApo.getVersionDiplomes(
//							motCle.getRen1ClesAnnuForm().getCodCles(), camp.getCodAnu())) {
//						listEtp.addAll(domainApo.getVersionEtapes(vrsDip, camp.getCodAnu()));
//					}
//					Set<VersionEtpOpi> vetEtpOpi = Utilitaires.convertVetInVetOpi(listEtp);
//					
//					System.out.println("compteur Mot_clés/dip : "
//						+ getPilotage().statMotCleDiplomeCriteria(vetEtpOpi, dip, camp));
//					System.out.println("compteur Mot_clés/etatInd : "
//						+ getPilotage().statMotCleEtatIndividuCriteria(
//								vetEtpOpi, etatIndComplet, camp));
//					System.out.println("compteur Mot_clés/etatV : "
//						+ getPilotage().statMotCleEtatVoeuCriteria(
//								vetEtpOpi, etatVoeuArriveComplet, camp));
//					System.out.println("compteur Mot_clés/motivation : "
//						+ getPilotage().statMotCleMotivationCriteria(
//								vetEtpOpi, motivation, camp));
//					System.out.println("compteur Mot_clés/pays : "
//						+ getPilotage().statMotClePaysCriteria(vetEtpOpi, pays, camp));
//					System.out.println("compteur Mot_clés/typeConvoc : "
//						+ getPilotage().statMotCleTypeConvocationCriteria(
//								vetEtpOpi, typeConvoc, camp));
//					System.out.println("compteur Mot_clés/typeDec : "
//						+ getPilotage().statMotCleTypeDecisionCriteria(
//								vetEtpOpi, typeDec, camp));
//					System.out.println("compteur Mot_clés : "
//						+ getPilotage().allIndividuMotCle(vetEtpOpi, camp));
//				}
//			}
			//TODO : fix this !!
			//Régime d'inscription
//			System.out.println("compteur Régime_d'inscription/dip : "
//				+ getPilotage().statRegimeInscriptionDiplomeCriteria(ri, dip, camp));
//			System.out.println("compteur Régime_d'inscription/etatInd : "
//				+ getPilotage().statRegimeInscriptionEtatIndividuCriteria(ri, etatIndComplet, camp));
//			System.out.println("compteur Régime_d'inscription/etatV : "
//				+ getPilotage().statRegimeInscriptionEtatVoeuCriteria(ri, etatVoeuArriveComplet, camp));
//			System.out.println("compteur Régime_d'inscription/motivation : "
//				+ getPilotage().statRegimeInscriptionMotivationCriteria(ri, motivation, camp));
//			System.out.println("compteur Régime_d'inscription/pays : "
//				+ getPilotage().statRegimeInscriptionPaysCriteria(ri, pays, camp));
//			System.out.println("compteur Régime_d'inscription/typeConvoc : "
//				+ getPilotage().statRegimeInscriptionTypeConvocationCriteria(ri, typeConvoc, camp));
//			System.out.println("compteur Régime_d'inscription/typeDec : "
//				+ getPilotage().statRegimeInscriptionTypeDecisionCriteria(ri, typeDec, camp));
//			System.out.println("compteur Régime_d'inscription : "
//					+ getPilotage().allIndividuRegimeInscription(ri, camp));
			
			//VersionEtpDto
			System.out.println("compteur VersionEtpDto/dip : "
				+ getPilotage().statVetDiplomeCriteria(versionEtpDto, dip, camp));
			System.out.println("compteur VersionEtpDto/etatInd : "
				+ getPilotage().statVetEtatIndividuCriteria(versionEtpDto, etatIndComplet, camp));
			System.out.println("compteur VersionEtpDto/etatV : "
				+ getPilotage().statVetEtatVoeuCriteria(versionEtpDto, etatVoeuArriveComplet, camp));
			System.out.println("compteur VersionEtpDto/motivation : "
				+ getPilotage().statVetMotivationCriteria(versionEtpDto, motivation, camp));
			System.out.println("compteur VersionEtpDto/pays : "
				+ getPilotage().statVetPaysCriteria(versionEtpDto, pays, camp));
			System.out.println("compteur VersionEtpDto/typeConvoc : "
				+ getPilotage().statVetTypeConvocationCriteria(versionEtpDto, typeConvoc, camp));
			System.out.println("compteur VersionEtpDto/typeDec : "
				+ getPilotage().statVetTypeDecisionCriteria(versionEtpDto, typeDec, getCampagne()));
			System.out.println("compteur VersionEtpDto : "
				+ getPilotage().allIndividuVet(versionEtpDto, camp));
			
			
		
		} catch (NullPointerException e) {
			DatabaseUtils.rollback();
		} finally {
			DatabaseUtils.close();
		}
	}
	/**
	 * @return Campagne
	 */
	private Campagne getCampagne() {
		//on renvoit le premier élement pour le test.
		Campagne c = getParameter().getCampagnes(null, null).iterator().next();
		for (Campagne camp : getParameter().getCampagnes(null, null)) {
			if (camp.getCodAnu().equals("2009")) {
				c = camp;
			}
		}
		System.out.println("campagne = " + c);
		return c;
	}
	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the remote service.
	 */
	private PilotageDaoService getPilotage() {
		if (pilotageDaoService == null) {
			pilotageDaoService = (PilotageDaoService) BeanUtils.getBean("pilotageDaoService");
		}
		
		 assertNotNull(pilotageDaoService);
		
		return pilotageDaoService;
	}
	/**
	 * @return the remote service.
	 */
	private ParameterService getParameter() {
		if (parameterService == null) {
			parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		}
		
		 assertNotNull(parameterService);
		
		return parameterService;
	}
}
