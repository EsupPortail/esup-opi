package org.esupportail.opi.services.application;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.mails.MailContent;
import org.esupportail.opi.services.mails.MailContentService;

import java.util.Collection;

final class MailsContents {
    final Logger logger = new LoggerImpl(getClass());
    final Collection<MailContentService> mailServices;
    final ParameterService parameterService;
    final DomainService domainService;

    private MailsContents(Collection<MailContentService> mailServices,
                          ParameterService parameterService,
                          DomainService domainService) {
        this.mailServices = mailServices;
        this.parameterService = parameterService;
        this.domainService = domainService;
    }

    static MailsContents mailsContents(Collection<MailContentService> mailServices,
                                       ParameterService parameterService,
                                       DomainService domainService) {
        return new MailsContents(mailServices, parameterService, domainService);
    }

    void initMailsContents() {
        for (MailContentService service : mailServices) {
            if (logger.isDebugEnabled())
                logger.debug("got mailContentService bean [" + service.getCode() + "]...");
            MailContent mail = new MailContent();
            mail.setCode(service.getCode());
            mail.setLibelle("INIT_DATABASE libellé à modifier");
            parameterService.addMailContent(domainService.add(mail, "INIT_DATABASE"));
        }
    }

    void updateMailsContents() {
        logger.info("Updating mail contents...");
        for (MailContentService service : mailServices) {
            if (logger.isDebugEnabled())
                logger.debug("got mailContentService bean [" + service.getCode() + "]...");
            for (MailContent mail : parameterService.getMailContents()) {
                mail.setBody(mail.getBody()
                            .replaceAll("commissionPojo", "commissionDTO")
                            .replaceAll("adressePojo", "adresseDTO"));
                parameterService.updateMailContent(domainService.update(mail, "UPDATE_DATABASE"));
            }
        }
    }


}
