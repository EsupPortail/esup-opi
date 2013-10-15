package org.esupportail.opi.services.application;

import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;

import java.util.Collection;

final class Treatments {
    final Collection<Traitement> traitements;
    final ParameterService parameter;
    final DomainService domain;

    private Treatments(Collection<Traitement> traitements, ParameterService parameter, DomainService domain) {
        this.traitements = traitements;
        this.parameter = parameter;
        this.domain = domain;
    }

    static Treatments treatments(Collection<Traitement> traitements, ParameterService parameter, DomainService domain) {
        return new Treatments(traitements, parameter, domain);
    }

    void initTreatments() {
        for (Traitement traitement: traitements)
            parameter.addTraitement(domain.add(traitement, "INIT_DATABASE"));
    }

    void updateTreatments() {
        for (Traitement traitement: traitements)
            parameter.updateTraitement(domain.update(traitement, "UPDATE_DATABASE"));
    }
}
