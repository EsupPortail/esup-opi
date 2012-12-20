/**
 * 
 */
package org.esupportail.opi.domain;

import java.util.List;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.utils.CacheModelConst;
import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;
import org.esupportail.wssi.services.remote.Etablissement;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;

import com.googlecode.ehcache.annotations.Cacheable;

/**
 * @author cleprous
 *
 */
public class BusinessCacheServiceImpl 
		 implements BusinessCacheService {

	
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
	public void afterPropertiesSet() {
		Assert.notNull(this.domainApoService, "property domainApoService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.cacheManager, "property cacheManager of class " 
				+ this.getClass().getName() + " can not be null");
		// initialisation du cache vet au dÃÂ©marrage de l'appli
		if (!cacheManager.cacheExists(VET_CACHE_NAME)) {
			cacheManager.addCache(VET_CACHE_NAME);
		}

		// initialisation du cache etablissement au dÃÂ©marrage de l'appli
		if (!cacheManager.cacheExists(ETB_CACHE_NAME)) {
			cacheManager.addCache(ETB_CACHE_NAME);
		}
		cacheEtb = cacheManager.getCache(ETB_CACHE_NAME);
		
		initCacheEtablissement();
		
		// initialisation du cache etablissement au dÃÂ©marrage de l'appli
		if (!cacheManager.cacheExists(BAC_CACHE_NAME)) {
			cacheManager.addCache(BAC_CACHE_NAME);
		}
		
		// initialisation du cache etablissement au dÃÂ©marrage de l'appli
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
	 * - on garde en cache sous forme de map les mÃÂ©thodes appelant l'intÃÂ©gralitÃÂ© d'un rÃÂ©fÃÂ©rentiel
	 * - les appels par code vont lire les maps
	 */	
	
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
		
		//  mise en cache des ÃÂ©lÃÂ©ments
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
			// dans le cas ou le cache ne contient pas l'ÃÂ©tablissement ou si le cache a ÃÂ©tÃÂ© flushÃÂ©
			// on rÃÂ©initialise le cache
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
		
		//  mise en cache des ÃÂ©lÃÂ©ments
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
		
		// dans le cas ou le cache ne contient pas l'ÃÂ©tape ou si le cache a ÃÂ©tÃÂ© flushÃÂ©
		// on rÃÂ©initialise le cache
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
