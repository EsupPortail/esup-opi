package org.esupportail.opi.web.candidat.controllers;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;

public class CandidaturesController extends CandidatController {

    private CandidaturesController(final DomainService domainService,
                                   final DomainApoService apoService,
                                   final I18nService i18nService) {
        super(domainService, apoService, i18nService);
    }

    public static CandidaturesController candidaturesController(final DomainService domainService,
                                                                final DomainApoService apoService,
                                                                final I18nService i18nService) {
        return new CandidaturesController(domainService, apoService, i18nService);
    }
}
