/**
 * 
 */
package org.esupportail.opi.services.mails;

import java.io.Serializable;
import java.util.Map;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;

import org.esupportail.opi.domain.ParameterService;

/**
 * @author cleprous
 *
 */
public abstract class AbstractMailContentImpl 
			implements MailContentService, Serializable, InitializingBean {


	/*
	 ******************* PROPERTIES ******************* */

	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -321813007559244805L;

	
	/**
	 * A logger.
	 */
//	private final Logger log = new LoggerImpl(getClass());	
	
	/**
	 * code to MailContent.
	 */
	private String code;
	
	/**
	 * if true send the mail to the intercept address. 
	 */
	private Boolean interceptAll;
	
	/**
	 * the key is the attributes and the value is a key bundles.
	 */
	private Map<String, String> definitions;
	
	/**
	 * see {@link ParameterService}.
	 */
	private ParameterService parameterService;
	
	/**
	 * {@link SmtpService}.
	 */
	private SmtpService smtpService;
	
	/**
	 * see {@link I18nService}.
	 */
	private I18nService i18Service;
	
	/*
	 ******************* INIT ************************* */
	


	/**
	 * Constructors.
	 */
	public AbstractMailContentImpl() {
		super();
	}

	/**
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.smtpService, 
				"property smtpService of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(this.parameterService, 
				"property parameterService of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(this.i18Service, 
				"property i18Service of class " + this.getClass().getName() + " can not be null");
		
		
	}
	
	/*
	 ******************* METHODS ********************** */
	
	
	
	
	
	/** 
	 * @see org.esupportail.opi.services.mails.MailContentService#getCode()
	 */
	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public Map<String, String> getDefinitions() {
		return definitions;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @param smtpService the smtpService to set
	 */
	public void setSmtpService(final SmtpService smtpService) {
		this.smtpService = smtpService;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * @param parameterService the parameterService to set
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	/**
	 * @return the parameterService
	 */
	protected ParameterService getParameterService() {
		return parameterService;
	}

	/**
	 * @return the smtpService
	 */
	protected SmtpService getSmtpService() {
		return smtpService;
	}

	/**
	 * @param definitions the definitions to set
	 */
	public void setDefinitions(final Map<String, String> definitions) {
		this.definitions = definitions;
	}


	/**
	 * @return the interceptAll
	 */
	public Boolean getInterceptAll() {
		return interceptAll;
	}

	/**
	 * @param interceptAll the interceptAll to set
	 */
	public void setInterceptAll(final Boolean interceptAll) {
		this.interceptAll = interceptAll;
	}

	/**
	 * @return the i18Service
	 */
	protected I18nService getI18Service() {
		return i18Service;
	}

	/**
	 * @param service the i18Service to set
	 */
	public void setI18Service(final I18nService service) {
		i18Service = service;
	}

	

	

	

	
	



}
