/**
 * 
 */
package org.esupportail.opi.domain;

import java.io.Serializable;
import java.util.Date;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.services.cache.FlushCacheThread;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleTrigger;
import org.springframework.beans.factory.InitializingBean;



/**
 * @author cleprous
 *
 */
public abstract class AbstractDomainService implements Serializable, InitializingBean {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serializable id.
	 */
	private static final long serialVersionUID = -67496917282879128L;
	
	
	/**
	 *  schedulerFlushCache permet de lancer un trigger. 	
	 */
	private Scheduler schedulerFlushCache; 	

	/**
	 * jobDetail contient le job e lancer par le trigger (cf MajAllUtilThread). 
	 */
	private JobDetail jobDetail;
	

	
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(this.getClass());

	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public AbstractDomainService() {
		// TODO Auto-generated constructor stub
	}

	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		Assert.notNull(this.schedulerFlushCache, 
				"property schedulerFlushCache of class " 
					+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.jobDetail, 
				"property jobDetail of class " + this.getClass().getName() + " can not be null");

	}

	
	/*
	 ******************* METHODS ********************** */

	/**
	 * Lance le thread qui va vider le cache des autres instance OPI. (definis dans config.properties)
	 * @param cacheModel
	 */
	public void executeFlushCache(final String cacheModel, final String threadName) {
		try {
			Date d = new Date();
			String uniqName = "threadFlushCache_" + threadName + "_" + d.getTime();
			SimpleTrigger trigger = new SimpleTrigger(uniqName, Scheduler.DEFAULT_GROUP);
			jobDetail.getJobDataMap().put(FlushCacheThread.KEY_CACHE_NAME, cacheModel);     
			jobDetail.setName(uniqName);
			schedulerFlushCache.scheduleJob(jobDetail, trigger);
		} catch (SchedulerException e) {
			logger.error("Probleme au lancement du thread FlushCacheThread" + e);
		} 
	}
	
	
	

	
	
	/*
	 ******************* ACCESSORS ******************** */
	


	/**
	 * @param schedulerFlushCache the schedulerFlushCache to set
	 */
	public void setSchedulerFlushCache(final Scheduler schedulerFlushCache) {
		this.schedulerFlushCache = schedulerFlushCache;
	}



	/**
	 * @param jobDetail the jobDetail to set
	 */
	public void setJobDetail(final JobDetail jobDetail) {
		this.jobDetail = jobDetail;
	}


	
	

}
