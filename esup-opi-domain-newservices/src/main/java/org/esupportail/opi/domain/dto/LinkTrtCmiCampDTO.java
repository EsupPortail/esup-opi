package org.esupportail.opi.domain.dto;


import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;

import java.io.Serializable;
import java.util.Date;

public class LinkTrtCmiCampDTO {

    /**
     * The traitement traitementCmiDto.
     */
    private TraitementCmi traitementCmi;

    private Campagne campagneDto;

    public static LinkTrtCmiCampDTO empty() {
        return new LinkTrtCmiCampDTO();
    }

    public TraitementCmi getTraitementCmi() {
        return traitementCmi;
    }

    public void setTraitementCmi(TraitementCmi traitementCmiDto) {
        this.traitementCmi = traitementCmiDto;
    }

    public LinkTrtCmiCampDTO withTraitementCmi(TraitementCmi traitementCmiDto) {
        setTraitementCmi(traitementCmiDto);
        return this;
    }

    public Campagne getCampagneDto() {
        return campagneDto;
    }

    public void setCampagneDto(Campagne campagneDto) {
        this.campagneDto = campagneDto;
    }

    public LinkTrtCmiCampDTO withCampagneDto(Campagne campagneDto) {
        setCampagneDto(campagneDto);
        return this;
    }

}
