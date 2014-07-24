package org.esupportail.opi.web.candidat.beans;


import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;

import java.util.Set;

public class LinkTrtCmiCampPojo {

    /**
     * The traitement traitementCmi.
     */
    private TraitementCmi traitementCmi;

    private CampagnePojo campagnePojo;

    /**
     * The list of voeux for the campagne and the traitementCmi.
     */
    private Set<IndVoeu> voeux;

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

    /**
     * @return the voeux
     */
    public Set<IndVoeu> getVoeux() {
        return voeux;
    }

    /**
     * @param voeux the voeux to set
     */
    public void setVoeux(final Set<IndVoeu> voeux) {
        this.voeux = voeux;
    }

}
