package org.esupportail.opi.web.candidat.config;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.web.candidat.controllers.CandidaturesController;
import org.esupportail.opi.web.candidat.controllers.CoordonneesController;
import org.esupportail.opi.web.candidat.controllers.CursusController;
import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;

import static org.esupportail.opi.web.candidat.services.security.CandidatService.LoggedUser;


@Lazy
@Configuration
@Import({Security.class})
public class ControllerConfig {

    @Inject
    private DomainService domainService;

    @Inject
    private DomainApoService apoService;

    @Inject
    private I18nService i18nService;

    @Bean
    @Scope(value = "session")
    public LoggedUser loggedUser() throws AuthenticationException {
        final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            return (LoggedUser) authentication.getPrincipal();
        }
        return null;
    }

    @Bean
    @Scope("view")
    public CoordonneesController coordonneesController() {
        return CoordonneesController.coordonneesController(domainService, apoService, i18nService, loggedUser());
    }

    @Bean
    @Scope("view")
    public CursusController cursusController() {
        return CursusController.cursusController(domainService, apoService, i18nService, loggedUser());
    }

    @Bean
    @Scope("view")
    public CandidaturesController candidaturesController() {
        return CandidaturesController.candidaturesController(domainService, apoService, i18nService, loggedUser());
    }

}
