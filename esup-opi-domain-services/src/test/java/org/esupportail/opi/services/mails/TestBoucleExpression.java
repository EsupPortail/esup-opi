package org.esupportail.opi.services.mails;

import java.util.ArrayList;
import java.util.List;

import junit.framework.TestCase;

import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;
import org.esupportail.opi.dao.DaoService;

/**
 * @author cleprous
 *
 */
public class TestBoucleExpression extends TestCase {
	/*
	 ******************* PROPERTIES ******************* */


	/**
	 * The configuration file where Spring beans are defined.
	 */
//	private static final String SPRING_CONFIG_FILE = "/properties/applicationContext.xml"; 

	/**
	 * The name of the client bean.
	 */
	private static final String BEAN_NAME = "addCursusScolFI";
	
	/**
	 * To the log.
	 */
	private static Logger LOG = new LoggerImpl(TestBoucleExpression.class);

	/**
	 * ReadRennes1.
	 */
	private MailContentService mailContentService;





	/**
	 * @return the service.
	 */
	private MailContentService getService() {
		if (mailContentService == null) {
			
			mailContentService = (MailContentService) BeanUtils.getBean(BEAN_NAME);
		}

		assertNotNull(mailContentService);

		return mailContentService;
	}



	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 * @param name
	 */
	public TestBoucleExpression(final String name) {
		super(name);
	}

	/**
	 * Constructors.
	 */
	public TestBoucleExpression() {
		super();
	}



	/*
	 ******************* METHODS ********************** */

	//TODO : fix this !!
	/**
	 * Test mail cursus with individu == null et list commission is empty.
	 */
//	public void testBoucleWithListVide() {
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("entering testBoucleWithListVide");
//		}
//		try {
//			DatabaseUtils.open();
//			DatabaseUtils.begin();
//			List<Object> list = new ArrayList<Object>();
//			list.add(null);
//			list.add(new ArrayList<CommissionPojo>());
//			MailContentService m = getService();
//			m.send("cedric.leproust@univ-rennes1.fr", list);
//			assertTrue(true);
//		} catch (Exception e) {
//			LOG.error(e);
//			assertTrue(false);
//		} finally {
//			DatabaseUtils.close();
//		}
//
//	}
	//TODO : fix this !!
	/**
	 * Test mail de modification du crusus avec un individu sans voeu.
	 */
//	public void testBoucleWithIndivWithoutWish() {
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("entering testBoucleWithIndivWithoutWish");
//		}
//		try {
//			DatabaseUtils.open();
//			DatabaseUtils.begin();
//			DaoService dao = (DaoService) BeanUtils.getBean("daoService");
//			List<Object> list = new ArrayList<Object>();
//			list.add(dao.getIndividu("3DUXUH6N", null));
//			list.add(new ArrayList<CommissionPojo>());
//			MailContentService m = getService();
//			m.send("cedric.leproust@univ-rennes1.fr", list);
//			assertTrue(true);
//		} catch (Exception e) {
//			LOG.error(e);
//			assertTrue(false);
//		} finally {
//			DatabaseUtils.close();
//		}
//
//	}





}
