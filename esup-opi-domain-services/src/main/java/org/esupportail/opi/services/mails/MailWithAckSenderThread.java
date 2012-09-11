package org.esupportail.opi.services.mails;

/**
 * ESUP-Portail Commons - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-commons
 */

import java.io.File;
import java.util.List;

import javax.mail.internet.InternetAddress;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.MailSenderThread;
import org.esupportail.commons.services.smtp.SmtpException;
import org.esupportail.commons.services.smtp.SmtpServer;


/**
 * Send a mail to an InternetAddress throught an asynchroneous thread.
 */
public class MailWithAckSenderThread extends Thread {

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(MailSenderThread.class);
	
	/**
	 * The SMTP servers to use.
	 */
	private List<SmtpServer> smtpServers;
	/**
	 * The sender.
	 */
	private InternetAddress from;
	/**
	 * The recipient.
	 */
	private InternetAddress to;
	/**
	 * The subject.
	 */
	private String subject;
	/**
	 * The HTML body.
	 */
	private String htmlBody;
	/**
	 * The plain text body.
	 */
	private String textBody;
	
	/**
	 * The list attach file.
	 */
	private List<File> files;
	
	/**
	 * The charset used to encode the headers.
	 */
	private String charset;
	
	/**
	 * Message-ID set to sending email.
	 */
	private String messageId;
	
	/**
	 * address where the exceptions are sended
	 */
	private String recipientMessage;
	
	/**
	 * address where the acknowledgements are sended
	 */
	private String acknowledgementMessage;
	
	/**
	 * Constructor.
	 * @param smtpServers
	 * @param from
	 * @param to
	 * @param subject
	 * @param htmlBody
	 * @param textBody
	 * @param files 
	 * @param charset 
	 * @param messageId
	 * @throws SmtpException 
	 */
	public MailWithAckSenderThread(
			final List<SmtpServer> smtpServers,
			final InternetAddress from, 
			final InternetAddress to, 
			final String subject,
			final String htmlBody,
			final String textBody,
			final List<File> files,
			final String charset,
			final String messageId,
			final String recipientMessage,
			final String acknowledgementMessage) throws SmtpException {
		super();
		if (logger.isDebugEnabled()) {
			logger.debug("starting a new thread for '" + to + "'...");
		}
		this.smtpServers = smtpServers;
		this.from = from;
		this.to = to;
		this.subject = subject;
		this.htmlBody = htmlBody;
		this.textBody = textBody;
		this.files = files;
		this.charset = charset;
		this.setPriority(Thread.MIN_PRIORITY);
		this.messageId = messageId;
		this.recipientMessage = recipientMessage;
		this.acknowledgementMessage = acknowledgementMessage;
		logger.debug("thread started.");
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public final void run() {
		SmtpUtils.sendEmailWithAck(smtpServers, from, to, subject, htmlBody, textBody, files, charset, messageId, recipientMessage, acknowledgementMessage);
	}

}

