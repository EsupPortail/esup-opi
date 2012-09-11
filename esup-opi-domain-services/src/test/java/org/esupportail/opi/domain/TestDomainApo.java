/**
 * 
 */
package org.esupportail.opi.domain;

import java.util.List;

import junit.framework.TestCase;

import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

/**
 * @author cleprous
 *
 */
public class TestDomainApo extends TestCase {

	

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The configuration file where Spring beans are defined.
	 */
	private static final String SPRING_CONFIG_FILE = "/properties/applicationContext.xml"; 
	
	/**
	 * The name of the client bean.
	 */
	private static final String BEAN_NAME = "domainApoService";

	
	/**
	 * {@link DomainApoService}.
	 */
	private DomainApoService domainApoService;
	
	

	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public TestDomainApo() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * Constructors.
	 * @param name
	 */
	public TestDomainApo(final String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the remote service.
	 */
	private DomainApoService getDomain() {
		if (domainApoService == null) {
			ClassPathResource res = new ClassPathResource(SPRING_CONFIG_FILE);
			BeanFactory beanFactory = new XmlBeanFactory(res);
			domainApoService = (DomainApoService) beanFactory.getBean(BEAN_NAME);
		}
		
		 assertNotNull(domainApoService);
		
		return domainApoService;
	}
	
	

	/*
	 ******************* METHODS ********************** */
	
	
	/**
	 * Search the individu with cod etu = 29005106.
	 */
	public void testIndividuFromApogee() {
		
		Individu i = getDomain().getIndividuFromApogee(null, null, "29005106", null, null, null);
		List<IndCursusScol> cur =  getDomain().getIndCursusScolFromApogee(i);
		
		assertNotNull(cur);
	}
	

	/*
	 ******************* ACCESSORS ******************** */

}
