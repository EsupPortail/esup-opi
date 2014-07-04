package org.esupportail.opi.web.candidat.beans;


import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;

public class LinkTrtCmiCampPojo {

    /**
     * The traitement traitementCmi.
     */
    private TraitementCmi traitementCmi;

    private CampagnePojo campagnePojo;

    public static LinkTrtCmiCampPojo empty() {
        return new LinkTrtCmiCampPojo();
    }

    public TraitementCmi getTraitementCmi() {
        return traitementCmi;
    }

    public void setTraitementCmi(TraitementCmi traitementCmi) {
        this.traitementCmi = traitementCmi;
    }

    public LinkTrtCmiCampPojo withTraitementCmi(TraitementCmi traitementCmiDto) {
        setTraitementCmi(traitementCmiDto);
        return this;
    }

    public CampagnePojo getCampagnePojo() {
        return campagnePojo;
    }

    public void setCampagnePojo(CampagnePojo campagnePojo) {
        this.campagnePojo = campagnePojo;
    }

    public LinkTrtCmiCampPojo withCampagnePojo(CampagnePojo campagnePojo) {
        setCampagnePojo(campagnePojo);
        return this;
    }
}
