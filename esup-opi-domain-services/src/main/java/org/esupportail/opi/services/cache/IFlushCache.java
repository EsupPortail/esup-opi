/**
 * 
 */
package org.esupportail.opi.services.cache;

import java.io.Serializable;

/**
 * @author cleprous
 * IFlushCash : Can flush Cash in all OPI-R1 instances.
 */
public interface IFlushCache extends Serializable {

	/**
	 * flush the Cash with this cacheModel.
	 * @param cacheModel
	 */
	void flushCash(String cacheModel);
	
}
