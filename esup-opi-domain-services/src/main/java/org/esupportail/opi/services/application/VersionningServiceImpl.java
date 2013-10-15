/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.services.application;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.Version;
import org.esupportail.commons.services.application.VersionException;
import org.esupportail.commons.services.application.VersionningService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
import org.esupportail.opi.services.mails.MailContentService;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

import static java.lang.String.format;
import static org.esupportail.opi.services.application.MailsContents.mailsContents;
import static org.esupportail.opi.services.application.Treatments.treatments;

public class VersionningServiceImpl implements VersionningService {

	private final Logger logger = new LoggerImpl(getClass());

    private final DomainService domainService;

    private final ParameterService parameterService;

    private final ApplicationService applicationService;

	private final String firstAdministratorId;
	
	/**
	 * The minimum access for init application.
	 */
	private final List<AccessRight> accessAdmin;
	
	private final List<Profile> profils;

	private final Profile admin;


    private VersionningServiceImpl(DomainService domainService,
                                   ParameterService parameterService,
                                   ApplicationService applicationService,
                                   String firstAdministratorId,
                                   List<AccessRight> accessAdmin,
                                   List<Profile> profils,
                                   Profile admin) {
        this.domainService = domainService;
        this.parameterService = parameterService;
        this.applicationService = applicationService;
        this.firstAdministratorId = firstAdministratorId;
        this.accessAdmin = accessAdmin;
        this.profils = profils;
        this.admin = admin;
    }

    public static VersionningServiceImpl versionningService(DomainService domainService,
                                                                      ParameterService parameterService,
                                                                      ApplicationService applicationService,
                                                                      String firstAdministratorId,
                                                                      List<AccessRight> accessAdmin,
                                                                      List<Profile> profils,
                                                                      Profile admin) {
        return new VersionningServiceImpl(domainService, parameterService, applicationService,
                firstAdministratorId, accessAdmin, profils, admin);
    }

    /**
	 * Set the database version.
	 * @param version database version
	 * @param silent true to omit info messages
	 */
	public void setDatabaseVersion(final String version, final boolean silent) {
		domainService.updateDatabaseVersion(version);
		if (!silent)
            logger.info("database version set to " + version + ".");
	}

	/**
	 * @return the database version.
	 */
	public Version getDatabaseVersion() {
		return domainService.getDatabaseVersion();
	}

	public void initDatabase() {
		logger.info("creating the first user of the application thanks to " 
				+ getClass().getName() + ".firstAdministratorId...");
		//ADD traitments
		treatments(getBeans(Traitement.class), parameterService, domainService).initTreatments();
		
		//ADD MailContent
		mailsContents(getBeans(MailContentService.class), parameterService, domainService).initMailsContents();
		
		//ADD ADMIN
		for (Profile p : profils)
            parameterService.addProfile(domainService.add(p, "INIT_DATABASE"));
		
		//ADD ACCESS
		for (AccessRight a : accessAdmin) parameterService.addAccessRight(a);
		
		domainService.addFirstAdmin(firstAdministratorId, admin);
		
		logger.info("the database has been created.");
        setDatabaseVersion(applicationService.getVersion().toString(), true);
	}

    @Override
    public boolean upgradeDatabase() {
        if (getDatabaseVersion().equals(applicationService.getVersion())) {
            logger.info("The database is up to date, no need to upgrade.");
            return false;
        }

        upgradeDatabaseIfNeeded("2.0.0");

        if (!getDatabaseVersion().equals(applicationService.getVersion()))
            setDatabaseVersion(applicationService.getVersion().toString(), false);
        return false;
    }

    @Override
    public void checkVersion() throws VersionException {
        checkVersion(true, true);
    }

    @Override
	public void checkVersion(boolean throwException, boolean printLatestVersion) throws VersionException {
		Version databaseVersion = domainService.getDatabaseVersion();
        Version applicationVersion = applicationService.getVersion();
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

    public void upgrade2d0d0() {
        logger.info("Upgrading the database to version 2.0.0");
        mailsContents(getBeans(MailContentService.class), parameterService, domainService).updateMailsContents();
    }

    /**
     * Upgrade the database to a given version, if needed.
     */
    private void upgradeDatabaseIfNeeded(final String version) {
        if (!getDatabaseVersion().isOlderThan(version)) return;
        printOlderThanMessage(version);
        String methodName = "upgrade" + version.replace('.', 'd');
        Class<?>[] methodArgs = new Class[]{};
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
        Throwable invocationException = null;
        try {
            method.invoke(this);
            setDatabaseVersion(version, false);
            return;
        } catch (IllegalArgumentException | IllegalAccessException | InvocationTargetException e) {
            invocationException = (e.getCause() != null &&
                    e instanceof InvocationTargetException) ? e.getCause() : e;
        }
        throw new ConfigException(
                "could no invoke method " + getClass() + "." + methodName + "()",
                invocationException);
    }

    private void printOlderThanMessage(final String version) {
        logger.info(new StringBuffer("database version (")
                .append(domainService.getDatabaseVersion())
                .append(") is older than ")
                .append(version)
                .append(", upgrading..."));
    }

    private void printLastVersion() {
        Version latestVersion = applicationService.getLatestVersion();
        if (latestVersion != null)
            logger.info("Latest version available: " + latestVersion);
    }

    private <T> Collection<T> getBeans(Class<T> clazz) {
        return ApplicationContextHolder.getContext().getBeansOfType(clazz).values();
    }
}
