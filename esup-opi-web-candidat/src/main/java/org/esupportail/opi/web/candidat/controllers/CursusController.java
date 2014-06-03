package org.esupportail.opi.web.candidat.controllers;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;

public class CursusController extends CandidatController {

    private CursusController(final DomainService domainService,
                             final DomainApoService apoService,
                             final I18nService i18nService) {
        super(domainService, apoService, i18nService);
    }

    public static CursusController cursusController(final DomainService domainService,
                                                                final DomainApoService apoService,
                                                                final I18nService i18nService) {
        return new CursusController(domainService, apoService, i18nService);
    }
}
