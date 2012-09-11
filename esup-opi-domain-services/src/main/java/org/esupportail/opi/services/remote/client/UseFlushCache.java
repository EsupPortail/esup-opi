package org.esupportail.opi.services.remote.client;

import java.io.Serializable;

/**
 * @author cleprous
 *
 */
public interface UseFlushCache extends Serializable {
	
	/**
	 * flush the Cash with this cacheName.
	 * @param cacheName
	 */
	void flushCache(String cacheName);
}
