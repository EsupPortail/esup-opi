/**
 * 
 */
package org.esupportail.opi.services.cache;

import java.util.Map;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.beans.factory.InitializingBean;


/**
 * @author cleprous
 *
 */
public class FlushCacheImpl implements IFlushCache, InitializingBean {

	/*
	 ******************* PROPERTIES ******************* */
	
	
	
	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = -6725958244378493207L;
	
//	/**
//	 * {@link EhCacheFacade}.
//	 */
//	private EhCacheFacade cacheProvider;
	
	/**
	* Map of <code>{@link FlushingModel}</code>s that specify how to flush the
	* cache. Each cache model is stored under a unique id (a String).
	*/
	private Map<Object, Object> flushingModels;
	
	
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	

	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public FlushCacheImpl() {
		super();
	}
	
	/** 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
//		Assert.notNull(this.cacheProvider, "property cacheProvider of class "
//				+ this.getClass().getName() + " can not be null");
//		
//		
//		if (flushingModels == null || flushingModels.isEmpty()) {
//		      return;
//		    }
//
//		    CacheModelValidator validator = cacheProvider.modelValidator();
//
//		    if (flushingModels instanceof Properties) {
//		      PropertyEditor editor = cacheProvider.getFlushingModelEditor();
//		      Properties properties = (Properties) flushingModels;
//		      Map<Object, Object> newFlushingModels = new HashMap<Object, Object>();
//
//		      String id = null;
//
//		      try {
//		        for (Iterator<Object> i = properties.keySet().iterator(); i.hasNext();) {
//		          id = (String) i.next();
//
//		          String property = properties.getProperty(id);
//		          editor.setAsText(property);
//		          Object flushingModel = editor.getValue();
//		          validator.validateFlushingModel(flushingModel);
//
//		          newFlushingModels.put(id, flushingModel);
//		        }
//		      } catch (Exception exception) {
//		        throw new FatalCacheException(
//		            "Unable to create the flushing model with id "
//		                + StringUtils.quote(id), exception);
//		      }
//
//		      setFlushingModels(newFlushingModels);
//
//		    } else {
//		      String id = null;
//
//		      try {
//		        for (Map.Entry<Object, Object> flushing : flushingModels.entrySet()) {
//		          Object flushingModel = flushing.getValue();
//		          validator.validateFlushingModel(flushingModel);
//		        }
//		      } catch (Exception exception) {
//		        throw new FatalCacheException(
//		            "Unable to validate flushing model with id "
//		                + StringUtils.quote(id), exception);
//		      }
//		    }
//		
//		
	}


	/*
	 ******************* METHODS ********************** */

	

	/** 
	 * @see org.esupportail.opi.services.cache.IFlushCache#flushCash(java.lang.String)
	 */
	@Override
	public void flushCash(final String cacheModel) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering flushCash( " + cacheModel + " )");
//		}
//		
//		cacheProvider.flushCache((FlushingModel) flushingModels.get(cacheModel));
		
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @param cacheProvider the cacheProvider to set
	 */
//	public void setCacheProvider(final EhCacheFacade cacheProvider) {
//		this.cacheProvider = cacheProvider;
//	}


	/**
	 * @param flushingModels the flushingModels to set
	 */
	public void setFlushingModels(final Map<Object, Object> flushingModels) {
		this.flushingModels = flushingModels;
	}


	

}
