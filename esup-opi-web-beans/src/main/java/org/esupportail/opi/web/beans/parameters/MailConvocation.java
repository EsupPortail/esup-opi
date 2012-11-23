/**
 * 
 */
package org.esupportail.opi.web.beans.parameters;

import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.services.mails.MailContentService;

/**
 * @author ylecuyer
 *
 */
public class MailConvocation {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4526096665415742767L;

	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * The type of convocation.
	 */
	private TypeConvocation typeConvocation;
	
	/**
	 * true if the mail is for appel.
	 * false if not
	 * null if the type is not concerned
	 */
	private Boolean isAppel;
	
	/**
	 * The mail content service.
	 */
	private MailContentService mailContentService;

	/**
	 * Constructors.
	 */
	public MailConvocation() {
		super();
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the typeConvocation
	 */
	public TypeConvocation getTypeConvocation() {
		return typeConvocation;
	}

	/**
	 * @param typeConvocation the typeConvocation to set
	 */
	public void setTypeConvocation(final TypeConvocation typeConvocation) {
		this.typeConvocation = typeConvocation;
	}

	/**
	 * @return the isAppel
	 */
	public Boolean getIsAppel() {
		return isAppel;
	}

	/**
	 * @param isAppel the isAppel to set
	 */
	public void setIsAppel(final Boolean isAppel) {
		this.isAppel = isAppel;
	}

	/**
	 * @return the mailContentService
	 */
	public MailContentService getMailContentService() {
		return mailContentService;
	}

	/**
	 * @param mailContentService the mailContentService to set
	 */
	public void setMailContentService(final MailContentService mailContentService) {
		this.mailContentService = mailContentService;
	}


	
}