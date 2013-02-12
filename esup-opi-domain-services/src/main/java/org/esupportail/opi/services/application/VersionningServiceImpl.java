/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.services.application; 

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.Version;
import org.esupportail.commons.services.application.VersionException;
import org.esupportail.commons.services.application.VersionningService;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.mails.MailContent;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.Domain;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
import org.esupportail.opi.services.mails.MailContentService;

/**
 * A bean for versionning management.
 */
public class VersionningServiceImpl implements VersionningService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8260436813100182531L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());
	
	/**
	 * The id of the first administrator.
	 */
	private String firstAdministratorId;
	
	/**
	 * The minimum access for init application.
	 */
	private List<AccessRight> accessAdmin;
	
	/**
	 * The profils.
	 */
	private List<Profile> profils;
	
	/**
	 * The admin of application.
	 */
	private Profile admin;
	
	/**
	 * The domain service
	 */
	private DomainService domainService;
	
	/**
	 * The parameter service
	 */
	private ParameterService parameterService;
	
	/**
	 * The Application Service
	 */
	private ApplicationService applicationService;

	/**
	 * Bean constructor.
	 */
	public VersionningServiceImpl() {
		super();
	}
	
	/**
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	//@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.applicationService, 
				"property applicationService of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.domainService, 
				"property domainService of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.parameterService, 
				"property parameterService of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.firstAdministratorId, 
				"property firstAdministratorId of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.admin, 
				"property admin of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.profils, 
				"property profils of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notEmpty(this.profils, 
				"property profils of class " + this.getClass().getName() 
				+ " can not be empty");
		Assert.notNull(this.accessAdmin, 
				"property accessAdmin of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notEmpty(this.accessAdmin, 
				"property accessAdmin of class " + this.getClass().getName() 
				+ " can not be empty");
		
	}

	/**
	 * print the last version available.
	 */
	@SuppressWarnings("unused")
	private void printLastVersion() {
		Version latestVersion = getApplicationService().getLatestVersion();
		if (latestVersion != null) {
			logger.info("Latest version available: " + latestVersion);
		}
	}
	
	/**
	 * Set the database version.
	 * @param version 
	 * @param silent true to omit info messages
	 */
	public void setDatabaseVersion(
			final String version, 
			final boolean silent) {
		getDomainService().updateDatabaseVersion(version);
		if (!silent) {
			logger.info("database version set to " + version + ".");
		}
	}

	/**
	 * @return the database version.
	 */
	public Version getDatabaseVersion() {
		return getDomainService().getDatabaseVersion();
	}

	/**
	 * @see org.esupportail.commons.services.application.VersionningService#initDatabase()
	 */
	public void initDatabase() {
		logger.info("creating the first user of the application thanks to " 
				+ getClass().getName() + ".firstAdministratorId...");
		//ADD traitments
		initTreatment();
		
		//ADD MailContent
		initMailContent();
		
		//ADD ADMIN
		for (Profile p : profils) {
			getParameterService().addProfile(
					(Profile) getDomainService().add(p, "INIT_DATABASE"));
		}
		
		//ADD ACCESS
		for (AccessRight a : accessAdmin) {
			getParameterService().addAccessRight(a);
		}
		
		getDomainService().addFirstAdmin(firstAdministratorId, admin);
		
		logger.info("the database has been created.");
		setDatabaseVersion("0.0.0", true);
	}

	/**
	 * @see org.esupportail.commons.services.application.VersionningService#checkVersion(boolean, boolean)
	 */
	@Override
	public void checkVersion(
			final boolean throwException,
			final boolean printLatestVersion) throws VersionException {
		Version databaseVersion = getDomainService().getDatabaseVersion();
		Version applicationVersion = getApplicationService().getVersion();
		if (databaseVersion == null) {
			String msg = "Your database is not initialized, please run 'ant init-data'.";
			if (throwException) {
				throw new VersionException(msg);
			}
			logger.error(msg);
			return;
		}
		if (applicationVersion.equals(databaseVersion)) {
			String msg = "The database is up to date.";
			if (throwException) {
				if (logger.isDebugEnabled()) {
					logger.debug(msg);
				}
			} else {
				logger.info(msg);
			}
			return;
		}
		if (applicationVersion.isSameMajorAndMinor(databaseVersion)) {
			logger.info("Database version is " + databaseVersion + ", upgrading...");
			upgradeDatabase();
			return;
		}
		if (databaseVersion.isOlderThan(applicationVersion)) {
			String msg = "The database is too old (" + databaseVersion + "), please run 'ant upgrade'.";
			if (throwException) {
				throw new VersionException(msg);
			}
			logger.error(msg);
			return;
		}
		String msg = "The application is too old (" + databaseVersion + "), please upgrade.";
		if (throwException) {
			throw new VersionException(msg);
		}
		logger.error(msg);
	}
	
	/**
	 * Print a message saying that the database version is older than ...
	 * @param version the new version
	 */	
	protected void printOlderThanMessage(final String version) {
		logger.info(new StringBuffer("database version (")
				.append(getDomainService().getDatabaseVersion())
				.append(") is older than ")
				.append(version)
				.append(", upgrading..."));
	}
	
	/**
	 * Upgrade the database to version 0.1.0.
	 */
	public void upgrade0d1d0() {
		// nothing to do yet
	}

	/**
	 * Upgrade the database to a given version, if needed.
	 * @param version 
	 */
	private void upgradeDatabaseIfNeeded(final String version) {
		if (!getDatabaseVersion().isOlderThan(version)) {
			return;
		}
		printOlderThanMessage(version);
		String methodName = "upgrade" + version.replace('.', 'd');
		Class< ? > [] methodArgs = new Class [] {};
		Method method;
		try {
			method = getClass().getMethod(methodName, methodArgs);
		} catch (SecurityException e) {
			throw new ConfigException(
					"access to the information of class " + getClass() + " was denied", e);
		} catch (NoSuchMethodException e) {
			throw new ConfigException(
					"could no find method " + getClass() + "." + methodName + "()", e);
		}
		Exception invocationException = null;
		try {
			method.invoke(this, new Object[] {});
			setDatabaseVersion(version, false);
			return;
		} catch (IllegalArgumentException e) {
			invocationException = e;
		} catch (IllegalAccessException e) {
			invocationException = e;
		} catch (InvocationTargetException e) {
			if (e.getCause() == null) {
				invocationException = e;
			} else if (e.getCause() instanceof Exception) {
				invocationException = (Exception) e.getCause();
			} else {
				invocationException = e;
			}
		}
		throw new ConfigException(
				"could no invoke method " + getClass() + "." + methodName + "()", 
				invocationException);
	}

	/**
	 * @see org.esupportail.commons.services.application.VersionningService#upgradeDatabase()
	 */
	public boolean upgradeDatabase() {
		if (getDatabaseVersion().equals(getApplicationService().getVersion())) {
			logger.info("The database is up to date, no need to upgrade.");
			return false;
		}
		upgradeDatabaseIfNeeded("0.1.0");
		if (!getDatabaseVersion().equals(getApplicationService().getVersion())) {
			setDatabaseVersion(getApplicationService().getVersion().toString(), false);
		}
		
		return false;
	}

	
	/**
	 * Init in dataBase all treatment of this application.
	 */
	private void initTreatment() {
		//ADD traitments
		List<Domain> domains = prepareTreatmentDomains();
		List<Fonction> functions = prepareTreatmentFonctions();
		for (Domain t : domains) {
			getParameterService().addTraitement(
					(Traitement) getDomainService().add(t, "INIT_DATABASE"));
		}
		for (Fonction t : functions) {
			getParameterService().addTraitement(
					(Traitement) getDomainService().add(t, "INIT_DATABASE"));
		}
	}
	
	/**
	 * Init in dataBase all treatment of this application.
	 */
	@SuppressWarnings("unused")
	private void updateTreatment() {
		//ADD traitments
		List<Domain> domains = prepareTreatmentDomains();
		List<Fonction> functions = prepareTreatmentFonctions();
		for (Domain t : domains) {
			getParameterService().addTraitement(
					(Traitement) getDomainService().add(t, "UPDATE_DATABASE"));
		}
		for (Fonction t : functions) {
			getParameterService().addTraitement(
					(Traitement) getDomainService().add(t, "UPDATE_DATABASE"));
		}
		
	}
	
	/**
	 * Prepare all treatment domains of this application.
	 */
	private List<Domain> prepareTreatmentDomains() {
		//ADD traitments
		Map<String, Traitement> treatments = ApplicationContextHolder.getContext().getBeansOfType(Traitement.class);
		List<Domain> domains = new ArrayList<Domain>();
		for (Entry<String, Traitement> treatment : treatments.entrySet()) {
			if (logger.isDebugEnabled()) {
				logger.debug("get to treatments bean [" + treatment.getKey() + "]...");
			}
			Object bean = treatment.getValue();
			if (bean == null) {
				throw new ConfigException("bean [" + treatment.getKey() 
						+ "] is null, " 
						+ "application doesn't init treatment in dataBase.");
			}
			if (!(bean instanceof Traitement)) {
				throw new ConfigException("bean [" + treatment.getKey() 
						+ "] does not extends Traitement, " 
						+ "application doesn't init treatment in dataBase.");
			}
			if (bean instanceof Domain) { domains.add((Domain) bean); }
		}
		return domains;
	}
	
	/**
	 * Prepare all treatment functions of this application.
	 */
	private List<Fonction> prepareTreatmentFonctions() {
		//ADD traitments
		Map<String, Traitement> treatments = ApplicationContextHolder.getContext().getBeansOfType(Traitement.class);
		List<Fonction> functions = new ArrayList<Fonction>();
		for (Entry<String, Traitement> treatment : treatments.entrySet()) {
			if (logger.isDebugEnabled()) {
				logger.debug("get to treatments bean [" + treatment.getKey() + "]...");
			}
			Object bean = treatment.getValue();
			if (bean == null) {
				throw new ConfigException("bean [" + treatment.getKey() 
						+ "] is null, " 
						+ "application doesn't init treatment in dataBase.");
			}
			if (!(bean instanceof Traitement)) {
				throw new ConfigException("bean [" + treatment.getKey() 
						+ "] does not extends Traitement, " 
						+ "application doesn't init treatment in dataBase.");
			}
			if (bean instanceof Fonction) { functions.add((Fonction) bean); }
		}
		return functions;
	}
	

	/**
	 * Init in dataBase all mailcontent of this application.
	 */
	private void initMailContent() {
		//ADD traitments
		Set<MailContentService> mails = prepareMailContent();
		for (MailContentService service : mails) {
			if (logger.isDebugEnabled()) {
				logger.debug("get to mailContentService bean [" + service.getCode() + "]...");
			}
			MailContent mail = new MailContent();
			mail.setCode(service.getCode());
			mail.setLibelle("INIT_DATABASE libellÃÂ© ÃÂ  modifier");
			getParameterService().addMailContent(
					(MailContent) getDomainService().add(mail, "INIT_DATABASE"));
			
		}
	}
	
	/**
	 * Update in dataBase all mailcontent of this application.
	 */
	private void updateMailContent() {
		//ADD traitments
		Set<MailContentService> mails = prepareMailContent();
		for (MailContentService service : mails) {
			if (logger.isDebugEnabled()) {
				logger.debug("get to mailContentService bean [" + service.getCode() + "]...");
			}
			if (getParameterService().getMailContent(service.getCode()) == null) {
				MailContent mail = new MailContent();
				mail.setCode(service.getCode());
				mail.setLibelle("UPDATE_DATABASE libellÃÂ© ÃÂ  modifier");
				getParameterService().addMailContent(
						(MailContent) getDomainService().add(mail, "UPDATE_DATABASE"));
			}
		}
	}
	
	/**
	 * Prepare all mailcontent of this application.
	 */
	private Set<MailContentService> prepareMailContent() {
		//ADD traitments
		Map<String, MailContentService> mailContentServices = ApplicationContextHolder.getContext().getBeansOfType(MailContentService.class);
		Set<MailContentService> mails = new HashSet<MailContentService>();
		for (Entry<String, MailContentService> mail : mailContentServices.entrySet()) {
			if (logger.isDebugEnabled()) {
				logger.debug("get to treatments bean [" + mail.getKey() + "]...");
			}
			Object bean = mail.getValue();
			if (bean == null) {
				throw new ConfigException("bean [" + mail.getKey() 
						+ "] is null, " 
						+ "application contains not mail.");
			}
			if (!(bean instanceof MailContentService)) {
				throw new ConfigException("bean [" + mail.getKey() 
						+ "] does not implement MailContentService, " 
						+ "application contains not mail.");
			}
			MailContentService service = (MailContentService) bean;
			mails.add(service);
		}
		return mails;
	}
	

	
	/**
	 * @return the firstAdministratorId
	 */
	public String getFirstAdministratorId() {
		return firstAdministratorId;
	}

	/**
	 * @param firstAdministratorId the firstAdministratorId to set
	 */
	public void setFirstAdministratorId(final String firstAdministratorId) {
		this.firstAdministratorId = firstAdministratorId;
	}

	
	/**
	 * @param profils the profils to set
	 */
	public void setProfils(final List<Profile> profils) {
		this.profils = profils;
	}


	/**
	 * @param accessAdmin the accessAdmin to set
	 */
	public void setAccessAdmin(final List<AccessRight> accessAdmin) {
		this.accessAdmin = accessAdmin;
	}

	/**
	 * @param admin the admin to set
	 */
	public void setAdmin(final Profile admin) {
		this.admin = admin;
	}


    public DomainService getDomainService() {
		return domainService;
	}



	public void setDomainService(DomainService domainService) {
		this.domainService = domainService;
	}



	public ParameterService getParameterService() {
		return parameterService;
	}

	public void setParameterService(ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	public ApplicationService getApplicationService() {
		return applicationService;
	}

	public void setApplicationService(ApplicationService applicationService) {
		this.applicationService = applicationService;
	}

	@Override
    public void checkVersion() throws VersionException {
        // TODO Auto-generated method stub
        
    }

}
