package org.esupportail.opi.services.authentification;

import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.User;

import java.util.Date;




/**
 * The interface of authenticators.
 */
public interface Authenticator {

	/**
	 * @return the authenticated user.
	 */
	User getUser();
	
	/**
	 * The id for the authentication.
	 * @return String
	 */
	String getAuthId();
	
	
	/**
	 * Store the manager and info for student when he connect in student space.
	 * @param manager
	 * @param numeroDossier
	 * @param dateDeNaissance
	 */
	void storeManager(Gestionnaire manager, String numeroDossier, Date dateDeNaissance);
	
	/**
	 * the manager connect in student space.
	 * @return Gestionnaire
	 */
	Gestionnaire getManager();

}