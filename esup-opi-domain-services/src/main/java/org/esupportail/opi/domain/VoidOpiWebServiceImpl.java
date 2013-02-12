package org.esupportail.opi.domain;

import java.util.List;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;

/**
 * Universite de bretagne - Candisup - 2007
 * Classe : OpiWebServiceImpl.java
 * Description : Met e jour la base APOGEE via l'appel du WEBSERVICE OPI.
 * Copyright : Copyright (c) 2007
 * @author cleprous
 * @version 1.0
 */
public class VoidOpiWebServiceImpl implements OpiWebService {


	/*
	 *************************** PROPERTIES ******************************** */

	/**
	 * A log.
	 */
	private final Logger log = new LoggerImpl(VoidOpiWebServiceImpl.class);




	/*
	 *************************** INIT ************************************** */


	/**
	 * Constructor.
	 */
	public VoidOpiWebServiceImpl() {
		super();
	}


	/*
	 *************************** METHODS *********************************** */


	/**
	 * @see org.esupportail.opi.domain.OpiWebService#launchWebService(
	 * org.esupportail.opi.domain.beans.user.Individu, java.util.List)
	 */
	public Boolean launchWebService(final Individu individu, final List<IndVoeu> voeux) {
		log.info("in " + getClass().getCanonicalName() + "no launchWebService");
		return false;

	}


	


}
