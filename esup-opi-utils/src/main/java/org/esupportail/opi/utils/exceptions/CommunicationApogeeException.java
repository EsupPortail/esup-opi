/**
 * ESUP-Portail Commons - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-commons
 */
package org.esupportail.opi.utils.exceptions; 

import org.esupportail.commons.exceptions.EsupException;

/**
 * A class for configuration exceptions.
 */
public class CommunicationApogeeException extends EsupException {

	/**
	 * The id for serialization.
	 */
	private static final long serialVersionUID = -6697943874257515161L;

	/**
	 * Message par defaut.
	 */
	private static final String MESS_DEFAULT = 
		"Probleme de communication avec Apogee. Verifiez l'etat d'apogee ou l'etat des webServices.";

	/**
	 * @param message
	 */
	public CommunicationApogeeException(final String message) {
		super(message);
	}

	/**
	 * @param cause
	 */
	public CommunicationApogeeException(final Throwable cause) {
		super(MESS_DEFAULT, cause);
	}

	/**
	 * @param message
	 * @param cause
	 */
	public CommunicationApogeeException(final String message, final Throwable cause) {
		super(message, cause);
	}

}
