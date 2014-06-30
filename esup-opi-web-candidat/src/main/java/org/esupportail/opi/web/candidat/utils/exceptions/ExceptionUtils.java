package org.esupportail.opi.web.candidat.utils.exceptions;

import org.esupportail.commons.mail.SmtpService;
import org.esupportail.commons.mail.exceptions.SmtpException;
import org.esupportail.commons.mail.model.MessageTemplate;

import javax.inject.Inject;
import javax.inject.Named;
import javax.mail.MessagingException;
import javax.mail.internet.InternetAddress;

public enum ExceptionUtils {
	exceptionUtils;
	
	@Inject
    @Named("smtpServiceNew")
	private SmtpService smtpService;

	@Inject
    @Named("exceptionAddress")
	private InternetAddress exceptionAddress;

	public void send(final Throwable t) {
        try {
            smtpService.send(MessageTemplate.create(t, exceptionAddress));
        } catch (MessagingException e) {
            throw new SmtpException(e.getMessage());
        }
    }
}
