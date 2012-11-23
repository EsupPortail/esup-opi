/**
 * 
 */
package org.esupportail.opi.services.cache;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.quartz.JobExecutionContext;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author cleprous
 *
 */
public class FlushCacheThread extends QuartzJobBean {

	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * THe key of argument for cacheModel.
	 */
	public static final String KEY_CACHE_NAME	= "cacheModel";
	
	/**
	 * The client of webService to flush cache.
	 */
	private UseFlushCache useFlushCache;


	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public FlushCacheThread() {
		super();
	}
	

	


	/*
	 ******************* METHODS ********************** */

	/** 
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(final JobExecutionContext arg0) {
		
		String cacheModel = (String) arg0.getJobDetail().
					getJobDataMap().get(KEY_CACHE_NAME);
		if (log.isDebugEnabled()) {
			log.debug("Lancement du thread pour vider le cache : " + cacheModel);
		}
		useFlushCache.flushCache(cacheModel);
		
	}

	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @param useFlushCache the useFlushCache to set
	 */
	public void setUseFlushCache(final UseFlushCache useFlushCache) {
		this.useFlushCache = useFlushCache;
	}

}
