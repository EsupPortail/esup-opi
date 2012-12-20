package org.esupportail.opi.services.mails;

import java.io.File;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SimpleSmtpServiceImpl;

/**
 * @author root
 *
 */
public class AcknowledgmentSmtpServiceImpl extends SimpleSmtpServiceImpl{
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6912061623396146368L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(AcknowledgmentSmtpServiceImpl.class);
	
	/**
	 * Indique l'adresse ÃÂ  laquelle sont envoyÃÂ©es les exceptions
	 */
	private String recipientEmail;
	
	/**
	 * Indique l'adresse ÃÂ  laquelle sont envoyÃÂ©es les accusÃÂ©s de rÃÂ©ception
	 */
	private String acknowledgementEmail;

	/**
	 * Bean constructor.
	 */
	public AcknowledgmentSmtpServiceImpl() {
		super();
	}
	
	/**
	 * @param recipientEmail
	 * @param acknowledgementEmail
	 */
	public AcknowledgmentSmtpServiceImpl(String recipientEmail, String acknowledgementEmail) {
		super();
		this.recipientEmail=recipientEmail;
		this.acknowledgementEmail=acknowledgementEmail;
	}

	/**
	 * @see org.esupportail.commons.services.smtp.SimpleSmtpServiceImpl#send(
	 * javax.mail.internet.InternetAddress, java.lang.String, java.lang.String, 
	 * java.lang.String, java.util.List, boolean, java.lang.String)
	 */
	@Override
	protected void send(
			final InternetAddress to, 
			final String subject, 
			final String htmlBody, 
			final String textBody, 
			final List<File> files,
			final boolean intercept,
			final String messageId) {
		InternetAddress recipient = getRealRecipient(to, intercept);
		if (logger.isDebugEnabled()) {
			logger.debug("launching a new thread for '" + recipient + "'...");
		}
		try{
		
		// start a new thread that will do the job asynchronously
		new MailWithAckSenderThread(
				getServers(), getFromAddress(), recipient, 
				subject, htmlBody, textBody, files, getCharset(), messageId, recipientEmail, acknowledgementEmail).start();
		logger.debug("thread launched.");
		}catch(Exception e){
		e.printStackTrace();	
		}
	}	
	
}
