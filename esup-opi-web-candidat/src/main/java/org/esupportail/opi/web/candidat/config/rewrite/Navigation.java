package org.esupportail.opi.web.candidat.config.rewrite;

import org.ocpsoft.rewrite.config.Configuration;
import org.ocpsoft.rewrite.config.ConfigurationBuilder;
import org.ocpsoft.rewrite.servlet.config.*;
import org.ocpsoft.rewrite.servlet.config.rule.Join;

import javax.servlet.ServletContext;

public class Navigation extends HttpConfigurationProvider {

    @Override
    public int priority() { return 10; }

    @Override
    public Configuration getConfiguration(final ServletContext context) {
        return ConfigurationBuilder.begin()

            .addRule(Join.path("/")
                    .to("/stylesheets/welcome.xhtml").withInboundCorrection())

            .addRule().when(Path.matches("/candidats/{dossier}"))
                .perform(Redirect.permanent("/candidats/{dossier}/coordonnees"))

            .addRule(Join.path("/candidats/{dossier}/coordonnees")
                    .to("/stylesheets/coordonnees/coordonnees.xhtml").withInboundCorrection())

            .addRule(Join.path("/candidats/{dossier}/cursus")
                    .to("/stylesheets/cursus/cursus.xhtml").withInboundCorrection())

            .addRule(Join.path("/candidats/{dossier}/candidatures")
                    .to("/stylesheets/candidatures/candidatures.xhtml").withInboundCorrection())

            .addRule()
            .when(DispatchType.isRequest()
                    .andNot(Path.matches("{*}javax.faces.resource{*}"))
                    .andNot(Path.matches("{*}.{png|jpg|gif}")))
            .perform(SendStatus.code(404));
    }
}
