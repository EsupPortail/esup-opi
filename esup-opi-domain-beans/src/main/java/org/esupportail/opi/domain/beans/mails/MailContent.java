/**
 * 
 */
package org.esupportail.opi.domain.beans.mails;

import org.esupportail.opi.domain.beans.NormeSI;

/**
 * @author cleprous
 *
 */
public class MailContent extends NormeSI {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4526096665415742767L;

	/**
	 * the code of the mailContent.
	 */
	private String code;
	
	/**
	 * The body of the mail.
	 */
	private String body;
	
	
	/**
	 * The subjet of the mail.
	 */
	private String subject;
	
	
	/*
	 ******************* INIT ************************* */
	

	/**
	 * Constructors.
	 */
	public MailContent() {
		super();
		subject = " ";
		body = " ";
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}






	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		MailContent other = (MailContent) obj;
		if (code == null) {
			if (other.getCode() != null) { return false; }
		} else if (!code.equals(other.getCode())) { return false; }
		return true;
	}
	

	

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return String
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * @return String
	 */
	public String getBody() {
		return body;
	}

	/**
	 * @param body
	 */
	public void setBody(final String body) {
		this.body = body;
	}

	/**
	 * @return String
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 */
	public void setSubject(final String subject) {
		this.subject = subject;
	}



	

}
