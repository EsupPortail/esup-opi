package org.esupportail.opi.services.mails;

import java.io.File;
import java.util.List;

import javax.mail.internet.InternetAddress;


/**
 * An abstract implementation of SmtpService that does not support tests.
 */
@SuppressWarnings("serial")
public abstract class AbstractSmtpService implements SmtpServiceOpi {

	/**
	 * @see org.esupportail.commons.services.smtp.SmtpService#supportsTest()
	 */
	public boolean supportsTest() {
		return false;
	}

	/**
	 * @see org.esupportail.commons.services.smtp.SmtpService#test()
	 */
	public void test() {
		throw new UnsupportedOperationException();
	}

	/**
	 * @see org.esupportail.commons.services.smtp.SmtpService#send(javax.mail.internet.InternetAddress,
	 *  java.lang.String, java.lang.String, java.lang.String)
	 */
	public void send(
			final InternetAddress to, 
			final String subject, 
			final String htmlBody, 
			final String textBody) {
		send(to, subject, htmlBody, textBody, (String) null);
	}
	
	/**
	 * @see org.esupportail.commons.services.smtp.SmtpService#send(javax.mail.internet.InternetAddress,
	 *  java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void send(
			final InternetAddress to, 
			final String subject, 
			final String htmlBody, 
			final String textBody, 
			final List<File> files) {
		send(to, subject, htmlBody, textBody, files, null);
	}
	
	/**
	 * @see org.esupportail.commons.services.smtp.SmtpService#sendtocc(javax.mail.internet.InternetAddress[],
	 *  javax.mail.internet.InternetAddress[], javax.mail.internet.InternetAddress[], java.lang.String,
	 *   java.lang.String, java.lang.String, java.util.List)
	 */
	public void sendtocc(
			final InternetAddress [] tos, 
			final InternetAddress [] ccs, 
			final InternetAddress [] bccs,
			final String subject, 
			final String htmlBody, 
			final String textBody, 
			final List<File> files) {
		this.sendtocc(tos, ccs, bccs, subject, htmlBody, textBody, files, null);
	}
	
	/**
	 * @see org.esupportail.commons.services.smtp.SmtpService#sendDoNotIntercept(
	 * javax.mail.internet.InternetAddress, java.lang.String, java.lang.String, java.lang.String)
	 */
	public void sendDoNotIntercept(
			final InternetAddress to, 
			final String subject, 
			final String htmlBody, 
			final String textBody) {
		sendDoNotIntercept(to, subject, htmlBody, textBody, (String) null);
	}
	
	/**
	 * @see org.esupportail.commons.services.smtp.SmtpService#sendDoNotIntercept(
	 * javax.mail.internet.InternetAddress, java.lang.String, java.lang.String, java.lang.String, java.util.List)
	 */
	public void sendDoNotIntercept(
			final InternetAddress to, 
			final String subject, 
			final String htmlBody, 
			final String textBody, 
			final List<File> files) {
		sendDoNotIntercept(to, subject, htmlBody, textBody, files, null);
	}
}

