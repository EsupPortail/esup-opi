/**
 * 
 */
package org.esupportail.opi.domain;

import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.utils.CacheModelConst;
import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;
import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.Etablissement;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;

import com.googlecode.ehcache.annotations.Cacheable;

/**
 * @author cleprous
 *
 */
public class BusinessCacheServiceImpl 
		extends AbstractDomainService implements BusinessCacheService {

	
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * the serialization id.
	 */
	private static final long serialVersionUID = -900695769464258201L;

	private final Logger log = new LoggerImpl(BusinessCacheServiceImpl.class);	
	
	/**
	 * The  cache session attribute to store the map of vet.
	 */
	public static final String VET_CACHE_NAME = "org.esupportail.apogee.domain.dto.enseignement.VersionEtapeDTO";
	
	/**
	 * The  cache session attribute to store the map of etablissement.
	 */
	public static final String ETB_CACHE_NAME = "org.esupportail.apogee.domain.beans.referentiel.etablissement.Etablissement";
	
	/**
	 * The  cache session attribute to store the map of etablissement.
	 */
	public static final String BAC_CACHE_NAME = "org.esupportail.apogee.domain.beans.referentiel.BacOuxEqu";
	
	/**
	 * The  cache session attribute to store the map of signataires.
	 */
	public static final String SIGN_CACHE_NAME = "org.esupportail.apogee.domain.dto.referentiel.SignataireDTO";

	/**
	 * see {@link DomainApoService}.
	 */
	private DomainApoService domainApoService;
	
	/**
	 * see {@link CacheManager}
	 */
	private CacheManager cacheManager;
	
	/**
	 * The cache object for the Etablissement.
	 */
	private Cache cacheEtb;
	
	/**
	 * The cache object for the BacOuxEqu.
	 */
	private Cache cacheBac;
	
	/**
	 * The cache object for the SignataireDTO.
	 */
	private Cache cacheSign;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public BusinessCacheServiceImpl() {
		super();
	}
	
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.notNull(this.domainApoService, "property domainApoService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.cacheManager, "property cacheManager of class " 
				+ this.getClass().getName() + " can not be null");
		// initialisation du cache vet au démarrage de l'appli
		if (!cacheManager.cacheExists(VET_CACHE_NAME)) {
			cacheManager.addCache(VET_CACHE_NAME);
		}

		// initialisation du cache etablissement au démarrage de l'appli
		if (!cacheManager.cacheExists(ETB_CACHE_NAME)) {
			cacheManager.addCache(ETB_CACHE_NAME);
		}
		cacheEtb = cacheManager.getCache(ETB_CACHE_NAME);
		
		initCacheEtablissement();
		
		// initialisation du cache etablissement au démarrage de l'appli
		if (!cacheManager.cacheExists(BAC_CACHE_NAME)) {
			cacheManager.addCache(BAC_CACHE_NAME);
		}
		cacheBac = cacheManager.getCache(BAC_CACHE_NAME);
		
		// initialisation du cache etablissement au démarrage de l'appli
		if (!cacheManager.cacheExists(SIGN_CACHE_NAME)) {
			cacheManager.addCache(SIGN_CACHE_NAME);
		}
		cacheSign = cacheManager.getCache(SIGN_CACHE_NAME);
		initCacheSignataire();
	}
	
	/*
	 ******************* METHODS ********************** */

	/**
	 * Gestion du cache
	 * - on garde en cache sous forme de map les méthodes appelant l'intégralité d'un référentiel
	 * - les appels par code vont lire les maps
	 */
	
	//////////////////////////////////////////////////////////////
	////// GEOGRAPHIE
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	// Departement
	//////////////////////////////////////////////////////////////
//	
//	/** 
//	 * @see org.esupportail.opi.domain.BusinessCacheService#
//	 * getDepartement(java.lang.String)
//	 */
//	@Override
//	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
//	public Departement getDepartement(final String codeDep) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering getDepartement( " + codeDep + " )");
//		}
//		if (StringUtils.hasText(codeDep)) {
//			List<Departement> departements = domainApoService.getDepartements();
//			for (Departement d : departements) {
//				if (codeDep.equals(d.getCodDep())) {
//					return d;
//				}
//			}
//		}
//		return null;
//	}

	
//	//////////////////////////////////////////////////////////////
//	// Pays
//	//////////////////////////////////////////////////////////////
//	
//	/** 
//	 * @see org.esupportail.opi.domain.BusinessCacheService#
//	 * getPays(java.lang.String)
//	 */
//	@Override
//	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
//	public Pays getPays(final String codePays) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering getPays( " + codePays + " )");
//		}
//		if (StringUtils.hasText(codePays)) {
//			List<Pays> pays = domainApoService.getPays();
//			for (Pays p : pays) {
//				if (codePays.equals(p.getCodPay())) {
//					return p;
//				}
//			}
//		}
//		return null;
//	}

//	//////////////////////////////////////////////////////////////
//	// CommuneDTO
//	//////////////////////////////////////////////////////////////
//	
//	/** 
//	 * @see org.esupportail.opi.domain.BusinessCacheService#
//	 * getCommune(java.lang.String, java.lang.String)
//	 */
//	@Override
//	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
//	public CommuneDTO getCommune(final String codCom, final String codBdi) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering getCommune(" + codCom + ", " 
//					+ codBdi + "  )");
//		}
//		if (StringUtils.hasText(codCom) 
//				&& StringUtils.hasText(codBdi)) {
//			//parcours la liste des VET ouvertes au recrutement
//			List<CommuneDTO> co = domainApoService.getCommunesDTO(codBdi);
//			for (CommuneDTO c : co) {
//				if (c.getCodeCommune().equals(codCom)) {
//					return c;
//				}
//			}
//		}
//		return null;
//	}

	//////////////////////////////////////////////////////////////
	////// ENSEIGNEMENT
	//////////////////////////////////////////////////////////////
	
	//////////////////////////////////////////////////////////////
	// VersionEtapeDTO
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.BusinessCacheService#getVetDTO(
	 * java.lang.String, java.lang.Integer)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.ENS_APOGEE_MODEL)
	public VersionEtapeDTO getVetDTO(final String codEtp, final Integer codVrsVet, final String codAnu) {
		if (log.isDebugEnabled()) {
			log.debug("entering getVetDTO(" + codEtp + ", " 
					+ codVrsVet + "  )");
		}
		VersionEtapeDTO result = null;
		if (StringUtils.hasText(codEtp) && codVrsVet != 0) {
			//parcours la liste des VET ouvertes au recrutement
			try {
				List<VersionEtapeDTO> v = domainApoService.getVersionEtapes(codEtp, null, null, codAnu);
				for (VersionEtapeDTO vet : v) {
					if (codEtp.equals(vet.getCodEtp()) && codVrsVet.equals(vet.getCodVrsVet())) {
						result = vet;
						break;
					}
				}
			} catch (CommunicationApogeeException e) {
				log.error(e);
				result = new VersionEtapeDTO().withCodEtp(codEtp)
						.withLibWebVet("DOES NOT EXIST")
						.withLicEtp("DOES NOT EXIST");
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("leanving getVetDTO with vet =" + result);
		}
		
		return result;
	}
	
	/**
	 * Initialisation du cache VET.
	 */
//	public void initCacheVet() {
//		if (log.isDebugEnabled()) {
//			log.debug("entering initCacheVet( )");
//		}
//		
//		cacheVet.flush();
//		
//		// création de la map avec sous liste de VET
//		Map<String, List<VersionEtapeDTO>> mapOfVet = new HashMap<String, List<VersionEtapeDTO>>();
//		List<VersionEtapeDTO> v = domainApoService.getVersionEtapes(null, null, null, null);
//		for (VersionEtapeDTO vet : v) {
//			if (!mapOfVet.containsKey(vet.getCodEtp())) {
//				mapOfVet.put(vet.getCodEtp(), new ArrayList<VersionEtapeDTO>());
//			}
//			mapOfVet.get(vet.getCodEtp()).add(vet);
//		}
//		
//		// mise en cache des éléments de la map
//		for (Map.Entry<String, List<VersionEtapeDTO>> vetEntry : mapOfVet.entrySet()) {
//			cacheVet.put(new Element(vetEntry.getKey(), vetEntry.getValue()));
//		}
//		
//		if (log.isDebugEnabled()) {
//			log.debug("leanving initCacheVet");
//		}
//	}

	/**
	 * @see org.esupportail.opi.domain.BusinessCacheService#
	 * getVersionEtape(java.lang.String, java.lang.Integer)
	 */
	@Override
	public VersionEtapeDTO getVersionEtape(final String codEtp, final Integer codVrsVet) {
		if (log.isDebugEnabled()) {
			log.debug("entering getVersionEtape(" + codEtp + ", " 
					+ codVrsVet + "  )");
		}
		VersionEtapeDTO result = null;
		List<VersionEtapeDTO> v = domainApoService.getVersionEtapes(null, null, null, null);
		for (VersionEtapeDTO vet : v) {
		    if (codVrsVet.equals(vet.getCodVrsVet())) {
		        return vet;
		    }
		}
		if (log.isDebugEnabled()) {
		    log.debug("leanving getVersionEtape with vet =" + result);
		}
		return result;
	}
	
	//////////////////////////////////////////////////////////////
	// Etablissement
	//////////////////////////////////////////////////////////////

	/**
	 * Initialisation du cache Etab.
	 */
	public void initCacheEtablissement() {
		if (log.isDebugEnabled()) {
			log.debug("entering initCacheEtablissement( )");
		}
		
		cacheEtb.flush();
		
		//  mise en cache des éléments
		List<Etablissement> etabs = domainApoService.getEtablissements(null, null);
		for (Etablissement etb : etabs) {
			cacheEtb.put(new Element(etb.getCodEtb(), etb));
		}
		
		if (log.isDebugEnabled()) {
			log.debug("leanving initCacheEtablissement");
		}
	}
	/**
	 * @see org.esupportail.opi.domain.BusinessCacheService#
	 * getVersionEtape(java.lang.String, java.lang.Integer)
	 */
	@Override
	public Etablissement getEtablissement(final String codEtb) {
		if (log.isDebugEnabled()) {
			log.debug("entering getEtablissement(" + codEtb + "  )");
		}
		
		if (codEtb != null) {
			// dans le cas ou le cache ne contient pas l'établissement ou si le cache a été flushé
			// on réinitialise le cache
			if (cacheEtb.get(codEtb) == null) {
				initCacheEtablissement();	
			}
			Etablissement result = null;
			if (cacheEtb.get(codEtb) != null) {
				result = (Etablissement) cacheEtb.get(codEtb).getObjectValue();
			}
			
			if (log.isDebugEnabled()) {
				log.debug("leanving getEtablissement with vet =" + result);
			}
			return result;
		} 
		return null;
	}
	
	//////////////////////////////////////////////////////////////
	//BacOuxEqu
	//////////////////////////////////////////////////////////////

	/**
	 * Initialisation du cache Bac.
	 * @param daaObt
	 */
	public void initCacheBacOuxEqu(final String daaObt) {
		if (log.isDebugEnabled()) {
			log.debug("entering initCacheBacOuxEqu( )");
		}
		
		//  mise en cache des éléments
		List<BacOuxEqu> bacs = domainApoService.getBacOuxEqus(daaObt);
		for (BacOuxEqu bac : bacs) {
			// test pour s'assurer de ne pas avoir de doublon
			if (cacheBac.get(bac.getCodBac()) == null) {
				cacheBac.put(new Element(bac.getCodBac(), bac));
			}
		}
		
		if (log.isDebugEnabled()) {
			log.debug("leanving initCacheBacOuxEqu");
		}
		
	}
	
	/**
	 * @see org.esupportail.opi.domain.BusinessCacheService#getBacOuxEqu(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public BacOuxEqu getBacOuxEqu(final String daaObt, final String codBac) {
		if (log.isDebugEnabled()) {
			log.debug("entering getBacOuxEqu(" + daaObt + ", " + codBac +" )");
		}
		
		// dans le cas ou le cache ne contient pas le bac ou si le cache a été flushé
		// on réinitialise le cache
		if (cacheBac.get(codBac) == null) {
			initCacheBacOuxEqu(daaObt);	
		}
		
		BacOuxEqu result = (BacOuxEqu) cacheBac.get(codBac).getObjectValue();
		
		if (log.isDebugEnabled()) {
			log.debug("leanving getBacOuxEqu with vet =" + result);
		}
		return result;
	}
	
	/////////////////////////////////////////////////////////
	// SignataireDTO
	//////////////////////////////////////////////////////////////

	/**
	 * Initialisation du cache Signataire.
	 */
	public void initCacheSignataire() {
		if (log.isDebugEnabled()) {
			log.debug("entering initCacheSignataire( )");
		}
		
		cacheSign.flush();
		
		//  mise en cache des éléments
		List<SignataireDTO> signs = domainApoService.getSignataires();
		for (SignataireDTO sign : signs) {
			cacheSign.put(new Element(sign.getCodSig(), sign));
		}
		
		if (log.isDebugEnabled()) {
			log.debug("leanving initCacheSignataire");
		}
	}
	
	/**
	 * @see org.esupportail.opi.domain.BusinessCacheService#
	 * getSignataire(java.lang.String)
	 */
	@Override
	public SignataireDTO getSignataire(final String codSig) {
		if (log.isDebugEnabled()) {
			log.debug("entering getSignataire(" + codSig +" )");
		}
		
		// dans le cas ou le cache ne contient pas l'étape ou si le cache a été flushé
		// on réinitialise le cache
		if (cacheSign.get(codSig) == null) {
			initCacheSignataire();
		}
		
		SignataireDTO result = (SignataireDTO) cacheSign.get(codSig).getObjectValue();
		
		if (log.isDebugEnabled()) {
			log.debug("leanving getSignataire with vet =" + result);
		}
		return result;
	}

	/*
	 ******************* ACCESSORS ******************** */

	
	/**
	 * @param domainApoService the domainApoService to set
	 */
	public void setDomainApoService(final DomainApoService domainApoService) {
		this.domainApoService = domainApoService;
	}

	/**
	 * @param cacheManager
	 */
	public void setCacheManager(final CacheManager cacheManager) {
		this.cacheManager = cacheManager;
	}
	
}
