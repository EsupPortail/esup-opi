package org.esupportail.opi.domain.dto;

import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.wssi.services.remote.Pays;

public class AdresseDTO {
    private final Adresse adresse;
    private final Pays pays;
    private final CommuneDTO commune;
    private final Boolean isCedex;

    private AdresseDTO(Adresse adresse, Pays pays, CommuneDTO commune, Boolean cedex) {
        this.adresse = adresse;
        this.pays = pays;
        this.commune = commune;
        isCedex = cedex;
    }

    public static AdresseDTO adrDTO(Adresse adresse, Pays pays, CommuneDTO commune, Boolean cedex) {
        return new AdresseDTO(adresse, pays, commune, cedex);
    }

    public Adresse getAdresse() { return adresse; }

    public Pays getPays() { return pays; }

    public CommuneDTO getCommune() { return commune; }

    public Boolean getCedex() { return isCedex; }
}
