package org.esupportail.opi.services.mails;

import org.esupportail.commons.services.smtp.AsynchronousSmtpServiceImpl;
import org.esupportail.commons.services.smtp.SmtpService;

/**
 * @author root
 *
 */
public class SmtpServiceFactory {
	
	/**
	 * true if acknowledge is needed
	 */
	protected boolean acknowledgment; 
	
	/**
	 * to avoid acknowledgment if the "to" address equals to recipientEmail
	 */
	protected String recipientEmail;
	
	/**
	 * to specify an acknowledgement Email
	 */
	protected String acknowledgementEmail;


	/**
	 * @return the configured in config.xml smtp service with acknowledge or not
	 */
	@SuppressWarnings("unused")
	private SmtpService createInstance(){
		if (acknowledgment)
			return new AcknowledgmentSmtpServiceImpl(recipientEmail, acknowledgementEmail);
		
		return new AsynchronousSmtpServiceImpl();
			
	}
	
	

	
	public boolean isAcknowledgment() {
		return acknowledgment;
	}


	public void setAcknowledgment(boolean acknowledgment) {
		this.acknowledgment = acknowledgment;
	}
	


	public String getRecipientEmail() {
		return recipientEmail;
	}


	public void setRecipientEmail(String recipientEmail) {
		this.recipientEmail = recipientEmail;
	}


	public String getAcknowledgementEmail() {
		return acknowledgementEmail;
	}


	public void setAcknowledgementEmail(String acknowledgementEmail) {
		this.acknowledgementEmail = acknowledgementEmail;
	}



}
