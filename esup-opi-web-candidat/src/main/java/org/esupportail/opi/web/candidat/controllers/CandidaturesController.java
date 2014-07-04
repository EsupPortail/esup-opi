package org.esupportail.opi.web.candidat.controllers;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.web.candidat.services.security.CandidatService;

import static org.esupportail.opi.web.candidat.services.security.CandidatService.LoggedUser;

public class CandidaturesController extends CandidatController {

    private CandidaturesController(final DomainService domainService,
                                   final DomainApoService apoService,
                                   final I18nService i18nService,
                                   final LoggedUser loggedUser) {
        super(domainService, apoService, i18nService, loggedUser);
    }

    public static CandidaturesController candidaturesController(final DomainService domainService,
                                                                final DomainApoService apoService,
                                                                final I18nService i18nService,
                                                                final LoggedUser loggedUser) {
        return new CandidaturesController(domainService, apoService, i18nService, loggedUser);
    }
}
