/**
 * ESUP-Portail Blank Application - Copyright (c) 2010 ESUP-Portail consortium.
 */
package org.esupportail.opi.domain;

import java.io.Serializable;

import org.esupportail.opi.domain.beans.User;

/**
 * @author Yves Deschamps (Universite de Lille 1) - 2010
 * 
 */
public interface DomainService extends Serializable {
	
	/**
	 * @param uid
	 * @return a user.
	 */
	User getUser(String uid);

}
