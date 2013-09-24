package org.esupportail.opi.domain.dto;

import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.ContactCommission;

public class CommissionDTO {
    private final Commission commission;
    private final AdresseDTO adresseDTO;
    private final ContactCommission contactCommission;

    private CommissionDTO(Commission commission, AdresseDTO adresseDTO, ContactCommission contactCommission) {
        this.commission = commission;
        this.adresseDTO = adresseDTO;
        this.contactCommission = contactCommission;
    }

    public static CommissionDTO comDTO(Commission commission, AdresseDTO adresseDTO, ContactCommission contactCommission) {
        return new CommissionDTO(commission, adresseDTO, contactCommission);
    }

    public Commission getCommission() { return commission; }

    public AdresseDTO getAdresseDTO() { return adresseDTO; }

    public ContactCommission getContactCommission() { return contactCommission; }
}
