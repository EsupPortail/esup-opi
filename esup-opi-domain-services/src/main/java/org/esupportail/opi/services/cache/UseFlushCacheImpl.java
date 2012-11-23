/**
 * 
 */
package org.esupportail.opi.services.cache;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;



/**
 * @author cleprous
 *
 */
public class UseFlushCacheImpl implements UseFlushCache, InitializingBean {

	/*
	 ******************* PROPERTIES ******************* */


	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -6573644287349916771L;

	/**
	 * Separated the name server in serversString.
	 */
	private static final String SEPARATED_SERVER = ","; 

	/**
	 * Begin the url for wsdlDocument.
	 */
	private static final String BEGIN_URL_WSDL	= "http://";

	/**
	 * End the url for wsdlDocument.
	 */
	private static final String END_URL_WSDL	= "/xfire/IFlushCache?WSDL";

	/**
	 * The servers name string. (separated by ',')
	 */
	private String serversString; 

	/**
	 * The list of serversName.
	 */
	private List<String> serversName;


	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public UseFlushCacheImpl() {
		super();
	}

	/** 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		if (StringUtils.hasText(serversString)) {
			String[] s = serversString.split(Pattern.quote(SEPARATED_SERVER));
			serversName = new ArrayList<String>();
			for (int i = 0; i < s.length; ++i) {
				serversName.add(s[i]);
			}

			log.info("the servers is Defined : " + serversName);
		} else {
			serversString = null;
			log.info("no servers names defined");
		}
	}

	/*
	 ******************* METHODS ********************** */


	/** 
	 * @see org.esupportail.opi.services.cache.UseFlushCache#flushCache(java.lang.String)
	 */
	@Override
	public void flushCache(final String cacheName) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering flushCache( " + cacheName + " )");
//		}
//		if (serversName != null) {
//			for (String server : serversName) {
//				try {
//					XFireClientFactoryBean f = new XFireClientFactoryBean();
//					f.setServiceClass(IFlushCache.class);
//					f.setWsdlDocumentUrl(BEGIN_URL_WSDL + server + END_URL_WSDL);
//					IFlushCache flush = null;
//					//init the ClientFactoryBean
//					f.afterPropertiesSet();
//					flush = (IFlushCache) f.getObject();
//					flush.flushCash(cacheName);
//				} catch (Exception e) {
//					log.error("The creation clientXFire (IFlushCache) is faild because :" + e);
//				}
//			}
//
//		} 
	}



	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @param serversString the serversString to set
	 */
	public void setServersString(final String serversString) {
		this.serversString = serversString;
	}


}
