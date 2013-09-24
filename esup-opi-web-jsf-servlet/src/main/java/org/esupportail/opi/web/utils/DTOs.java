package org.esupportail.opi.web.utils;

import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.ContactCommission;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.dto.AdresseDTO;
import org.esupportail.opi.domain.dto.CommissionDTO;
import org.esupportail.opi.web.beans.pojo.AdressePojo;
import org.esupportail.opi.web.beans.pojo.CommissionPojo;
import org.esupportail.wssi.services.remote.Pays;

import static org.esupportail.opi.domain.dto.AdresseDTO.adrDTO;
import static org.esupportail.opi.domain.dto.CommissionDTO.comDTO;

public class DTOs {
    private DTOs() { throw new UnsupportedOperationException(); }

    public static CommissionDTO commissionPojoToDTO(CommissionPojo cmiPojo) {
        return comDTO(cmiPojo.getCommission(),
                adressePojoToDTO(cmiPojo.getAdressePojo()),
                cmiPojo.getContactCommission());
    }

    public static AdresseDTO adressePojoToDTO(AdressePojo adressePojo) {
        final Adresse adresse = adressePojo.getAdresse();
        return adrDTO(adresse,
                adressePojo.getPays(),
                adressePojo.getCommune(),
                adresse.getCedex() != null);
    }

    public static CommissionDTO buildCommissionDTO(DomainApoService domainApoService, int codeRI, Commission cmi) {
        final ContactCommission contactCmi = cmi.getContactsCommission().get(codeRI);
        final Adresse adresse = contactCmi.getAdresse();
        final Pays pays = domainApoService.getPays(adresse.getCodPays());
        final CommuneDTO commune =
                domainApoService.getCommune(adresse.getCodCommune(), adresse.getCodBdi());
        return comDTO(cmi, adrDTO(adresse, pays, commune, adresse.getCedex() != null), contactCmi);
    }
}
