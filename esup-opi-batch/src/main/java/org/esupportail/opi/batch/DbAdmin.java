/**
 * 
 */
package org.esupportail.opi.batch;

import java.util.Properties;

import org.esupportail.commons.services.application.VersionningService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author bourges
 *
 */
public class DbAdmin {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(DbAdmin.class);

	/**
	 * Print the syntax and exit.
	 */
	private static void syntax() {
		System.out.print(
				"syntax: " + DbAdmin.class.getSimpleName() + " <options>\n" +
				"where option can be:\n" +
				"- init-db\n" +
				"- upgrade-db\n");
	}

	/**
	 * Dispatch dependaing on the arguments.
	 * @param args
	 */
	protected static void dispatch(final String[] args) {
		switch (args.length) {
		case 0:
			syntax();
			break;
		case 1:
			if ("init-db".equals(args[0])) {
				LOG.info("Initialise DataBase...");
				initDb();
			} else if ("upgrade-db".equals(args[0])) {
				LOG.info("Upgrade DataBase...");
				upgradeDb();
			} else {
				syntax();
			}
			break;
		default:
			syntax();
			break;
		}
	}

	/**
	 * get the versionning service by spring initialization 
	 * System environment variable generateDdl is set to true in order to force hibernate to generate DDL 
	 * @return versionning service
	 */
	private static VersionningService getVersionningService() {
		ApplicationContext context =
			    new ClassPathXmlApplicationContext("properties/applicationContext.xml");
		VersionningService versionningService =  (VersionningService) context.getBean("versionningService");
		return versionningService;
	}

	/**
	 * Upgrade the database
	 */
	private static void upgradeDb() {
		Properties properties = System.getProperties();
		properties.put("hbm2ddl", "update");
		System.setProperties(properties);
		VersionningService versionningService = getVersionningService();
		versionningService.upgradeDatabase();
	}

	/**
	 * Initialize the database 
	 */
	private static void initDb() {
		Properties properties = System.getProperties();
		properties.put("hbm2ddl", "create");
		System.setProperties(properties);
		VersionningService versionningService = getVersionningService();
		versionningService.initDatabase();
		versionningService.upgradeDatabase();
	}

	/**
	 * The main method, called by ant.
	 * @param args
	 */
	public static void main(final String[] args) {
		dispatch(args);
	}


}

