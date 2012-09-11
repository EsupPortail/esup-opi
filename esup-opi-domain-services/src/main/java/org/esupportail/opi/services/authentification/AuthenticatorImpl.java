/**
 * ESUP-Portail Helpdesk - Copyright (c) 2004-2008 ESUP-Portail consortium.
 */
package org.esupportail.opi.services.authentification; 


import static fj.P.p;

import java.io.Serializable;
import java.util.Date;

import org.esupportail.commons.services.authentication.AuthUtils;
import org.esupportail.commons.services.authentication.AuthenticationService;
import org.esupportail.commons.services.authentication.info.AuthInfo;
import org.esupportail.commons.services.authentication.info.AuthInfoImpl;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.utils.ContextUtils;
import org.esupportail.commons.web.controllers.Resettable;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.User;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import fj.P3;


/**
 * A basic authenticator implementation.
 */
public class AuthenticatorImpl implements Serializable, InitializingBean, Authenticator, Resettable {

	/**
	 * The session attribute to store the auth info.
	 */
	protected static final String AUTH_INFO_ATTRIBUTE = AuthenticatorImpl.class.getName() + ".authInfo";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 7145270459954623121L;

	/**
	 * The session attribute to store the user.
	 */
	private static final String USER_ATTRIBUTE = AuthenticatorImpl.class.getName() + ".user";
	
	
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());
	
	/**
	 * The external authenticator.
	 */
	private AuthenticationService authenticationService;
	
	/**
	 * The domain authenticator.
	 */
	private DomainService domainService;
	
	/**
	 * The dossier number.
	 */
	private String numDossier;


	/**
	 * The birth date.
	 */
	private Date dateNaissance;
	
	/**
	 * The manager connect in student space.
	 */
	private Gestionnaire manager;
	
	/**
	 * Bean constructor.
	 */
	public AuthenticatorImpl() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(authenticationService, 
				"property authenticationService of class " + this.getClass().getName() 
				+ " can not be null");
		reset();
	}
	
	@Override
	public void reset() {
		numDossier = null;
		dateNaissance = null;
		manager = null;
		
	}
	

	/**
	 * @see org.esupportail.opi.services.authentification#getUser()
	 */
	public User getUser() {
		AuthInfo authInfo = (AuthInfo) ContextUtils.getRequestAttribute(AUTH_INFO_ATTRIBUTE);
		if (authInfo != null) {
			User user = (User) ContextUtils.getRequestAttribute(USER_ATTRIBUTE);
			if (logger.isDebugEnabled()) {
				logger.debug("found auth info in request: " + user);
			}
			return user;
		}
		if (manager != null) {
			return authClassic();
		}
		if (logger.isDebugEnabled()) {
			logger.debug("no auth info found in request");
		}
		authInfo = authenticationService.getAuthInfo();
		if (authInfo == null) {
			return authClassic();
		}
		if (AuthUtils.CAS.equals(authInfo.getType())) {
		    P3<String, String, String> t3 = p(
		        authInfo.getId(),
		        domainService.getCodStudentRegex(),
		        domainService.getCodStudentPattern());
		    User user = domainService.getUser(
			    (t3._1().matches(t3._2())) ? 
			        t3._1().replaceAll(t3._2(), t3._3()) :
			            t3._1());
			storeToRequest(authInfo, user);
			return user;
		} 
		return null;
	}
	
	/**
	 * Use when the authentication is not cas.
	 * @return User
	 */
	private User authClassic() {
		if (StringUtils.hasText(numDossier)
				&& dateNaissance != null) {
			User user = domainService.getIndividu(numDossier, dateNaissance);
			if (user != null) {
				AuthInfo authInfo = new AuthInfoImpl(numDossier, null, null);
				storeToRequest(authInfo, user);
				return user;
			}
		}
		return null;
	}
	
	/** 
	 * @see org.esupportail.opi.services.authentification.Authenticator#getAuthId()
	 */
	@Override
	public String getAuthId() {
		AuthInfo authInfo = (AuthInfo) ContextUtils.getRequestAttribute(AUTH_INFO_ATTRIBUTE);
		if (authInfo == null) {
			authInfo = authenticationService.getAuthInfo();
		}
		
		if (authInfo != null) {
			return authInfo.getId();
		}
		return null;
	}

	/**
	 * Store the authentication information to the session.
	 * @param authInfo
	 * @param user
	 */
	protected void storeToRequest(
			final AuthInfo authInfo,
			final User user) {
		if (logger.isDebugEnabled()) {
			logger.debug("storing to request: " + authInfo);
		}
		ContextUtils.setRequestAttribute(AUTH_INFO_ATTRIBUTE, authInfo);
		ContextUtils.setRequestAttribute(USER_ATTRIBUTE, user);
	}

	
	/** 
	 * @see org.esupportail.opi.services.authentification.Authenticator#
	 * getManager()
	 */
	@Override
	public Gestionnaire getManager() {
		return manager;
	}
	
	
	
	/** 
	 * @see org.esupportail.opi.services.authentification.Authenticator#storeManager(
	 * org.esupportail.opi.domain.beans.user.Gestionnaire, java.lang.String, java.util.Date)
	 */
	@Override
	public void storeManager(final Gestionnaire gest, final String numeroDossier,
			final Date dateDeNaissance) {
		if (logger.isDebugEnabled()) {
			logger.debug("entering storeManager numeroDossier = " + numeroDossier 
						+ " dateDeNaissance = " + dateDeNaissance
						+ " manager = " + gest);
		}
		manager = gest;
		this.numDossier = numeroDossier;
		this.dateNaissance = dateDeNaissance;
		authClassic();
	}
	
	
	
	
	/**
	 * @param authenticationService the authenticationService to set
	 */
	public void setAuthenticationService(
			final AuthenticationService authenticationService) {
		this.authenticationService = authenticationService;
	}

	/**
	 * @return the authenticationService
	 */
	protected AuthenticationService getAuthenticationService() {
		return authenticationService;
	}

	/**
	 * @return the domainService
	 */
	protected DomainService getDomainService() {
		return domainService;
	}

	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @return the numDossier
	 */
	public String getNumDossier() {
		return numDossier;
	}

	/**
	 * @param numDossier the numDossier to set
	 */
	public void setNumDossier(final String numDossier) {
		this.numDossier = numDossier;
	}

	/**
	 * @return the dateNaissance
	 */
	public Date getDateNaissance() {
		return dateNaissance;
	}

	/**
	 * @param dateNaissance the dateNaissance to set
	 */
	public void setDateNaissance(final Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}
}
