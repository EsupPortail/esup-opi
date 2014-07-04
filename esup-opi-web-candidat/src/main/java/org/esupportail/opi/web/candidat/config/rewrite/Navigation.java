package org.esupportail.opi.web.candidat.config.rewrite;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.config.Direction;
import org.ocpsoft.rewrite.context.EvaluationContext;
import org.ocpsoft.rewrite.servlet.config.*;
import org.ocpsoft.rewrite.servlet.config.rule.Join;
import org.ocpsoft.rewrite.servlet.http.event.HttpServletRewrite;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import static java.lang.String.format;
import static org.esupportail.opi.web.candidat.services.security.CandidatService.LoggedUser;

public class Navigation extends HttpConfigurationProvider {

    public static final String WELCOME = "/stylesheets/welcome.xhtml";
    public static final String COORDONNEES = "/stylesheets/coordonnees/coordonnees.xhtml";
    public static final String CURSUS = "/stylesheets/cursus/cursus.xhtml";
    public static final String CANDIDATURES = "/stylesheets/candidatures/candidatures.xhtml";

    @Value("${cas.service}")
    private String casUrl;

    @Value("${serviceName}")
    private String serviceUrl;

    @Override
    public int priority() { return 10; }

    @Override
    public Configuration getConfiguration(final ServletContext context) {
        return ConfigurationBuilder.begin()

            .addRule(Join.path("/")
                    .to(WELCOME).withInboundCorrection())
//
//            .addRule()
//            .when(Direction.isInbound()
//                    .and(Path.matches("/logout")))
//            .perform(Redirect.temporary("/j_spring_cas_security_logout"))

            .addRule()
            .when(Direction.isInbound()
                    .and(Path.matches("/cas/login")))
            .perform(new HttpOperation() {
                public void performHttp(HttpServletRewrite httpServletRewrite, EvaluationContext evaluationContext) {
                    final Authentication auth = SecurityContextHolder.getContext().getAuthentication();
                    if (auth != null) {
                        final LoggedUser user = (LoggedUser) auth.getPrincipal();
                        if (user.isCandidat()) {
                            Redirect.permanent(format("/candidats/%s", user.getUsername()))
                                    .performHttp(httpServletRewrite, evaluationContext);
                            return;
                        }
                    }
                    SendStatus.code(403).performHttp(httpServletRewrite, evaluationContext);
                }
            })

            .addRule().when(Path.matches("/candidats/{dossier}"))
                .perform(Redirect.permanent("/candidats/{dossier}/coordonnees"))

            .addRule(Join.path("/candidats/{dossier}/coordonnees")
                    .to(COORDONNEES).withInboundCorrection())

            .addRule(Join.path("/candidats/{dossier}/cursus")
                    .to(CURSUS).withInboundCorrection())

            .addRule(Join.path("/candidats/{dossier}/candidatures")
                    .to(CANDIDATURES).withInboundCorrection())

            .addRule()
            .when(DispatchType.isRequest()
                    .andNot(Path.matches("/logout"))
                    .andNot(Path.matches("{*}javax.faces.resource{*}"))
                    .andNot(Path.matches("{*}.{png|jpg|gif}")))
            .perform(SendStatus.code(404))
;
    }
}
