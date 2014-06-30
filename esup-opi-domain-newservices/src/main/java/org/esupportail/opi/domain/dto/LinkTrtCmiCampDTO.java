package org.esupportail.opi.domain.dto;


import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;

import java.io.Serializable;
import java.util.Date;

public class LinkTrtCmiCampDTO {

    /**
     * The traitement traitementCmiDto.
     */
    private TraitementCmi traitementCmiDto;

    private Campagne campagneDto;

    public static LinkTrtCmiCampDTO empty() {
        return new LinkTrtCmiCampDTO();
    }

    public TraitementCmi getTraitementCmiDto() {
        return traitementCmiDto;
    }

    public void setTraitementCmiDto(TraitementCmi traitementCmiDto) {
        this.traitementCmiDto = traitementCmiDto;
    }

    public LinkTrtCmiCampDTO withTraitementCmiDto(TraitementCmi traitementCmiDto) {
        setTraitementCmiDto(traitementCmiDto);
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
    //    public static final class TraitementCmiDTO implements Serializable {
//
//        public String libelle;
//
//        public String shortLabel;
//
//        public Date dateCreaEnr;
//
//
//        public Boolean temoinEnService;
//
//        public String codUserToUpdate;
//
//        public String codUserToCreate;
//
//        public VersionEtpOpi versionEtpOpi;
//
//        public String codDip;
//
//        public Integer codVrsDip;
//
//        public String codTypeTrait;
//
//        public static TraitementCmiDTO empty() {
//            return new TraitementCmiDTO();
//        }
//
//        public String getLibelle() {
//            return libelle;
//        }
//
//        public void setLibelle(String libelle) {
//            this.libelle = libelle;
//        }
//
//        public String getShortLabel() {
//            return shortLabel;
//        }
//
//        public void setShortLabel(String shortLabel) {
//            this.shortLabel = shortLabel;
//        }
//
//        public Date getDateCreaEnr() {
//            return dateCreaEnr;
//        }
//
//        public void setDateCreaEnr(Date dateCreaEnr) {
//            this.dateCreaEnr = dateCreaEnr;
//        }
//
//        public Boolean getTemoinEnService() {
//            return temoinEnService;
//        }
//
//        public void setTemoinEnService(Boolean temoinEnService) {
//            this.temoinEnService = temoinEnService;
//        }
//
//        public String getCodUserToUpdate() {
//            return codUserToUpdate;
//        }
//
//        public void setCodUserToUpdate(String codUserToUpdate) {
//            this.codUserToUpdate = codUserToUpdate;
//        }
//
//        public String getCodUserToCreate() {
//            return codUserToCreate;
//        }
//
//        public void setCodUserToCreate(String codUserToCreate) {
//            this.codUserToCreate = codUserToCreate;
//        }
//
//        public VersionEtpOpi getVersionEtpOpi() {
//            return versionEtpOpi;
//        }
//
//        public void setVersionEtpOpi(VersionEtpOpi versionEtpOpi) {
//            this.versionEtpOpi = versionEtpOpi;
//        }
//
//        public String getCodDip() {
//            return codDip;
//        }
//
//        public void setCodDip(String codDip) {
//            this.codDip = codDip;
//        }
//
//        public Integer getCodVrsDip() {
//            return codVrsDip;
//        }
//
//        public void setCodVrsDip(Integer codVrsDip) {
//            this.codVrsDip = codVrsDip;
//        }
//
//        public String getCodTypeTrait() {
//            return codTypeTrait;
//        }
//
//        public void setCodTypeTrait(String codTypeTrait) {
//            this.codTypeTrait = codTypeTrait;
//        }
//    }
//
//    public static final class CampagneDTO implements Serializable {
//
//        /**
//         *  The type of Nomenclature.
//         */
//        private String type;
//
//        /**
//         * The Nomenclature code.
//         */
//        private String code;
//
//        /**
//         * Code de l'année universitaire de la campagne.
//         */
//        private String codAnu;
//
//        /**
//         * Code Regime d'inscription.
//         * Default value 0 --> formation Initiale.
//         * 1 --> Formation Continue
//         */
//        private int codeRI;
//
//        /**
//         * true if this campagne has been archived.
//         */
//        private Boolean isArchived;
//
//        /**
//         * Date de début de la campagne.
//         */
//        private Date dateDebCamp;
//
//        /**
//         * Date de fin de la campagne.
//         */
//        private Date dateFinCamp;
//
//        public static CampagneDTO empty() {
//            return new CampagneDTO();
//        }
//
//        public String getType() {
//            return type;
//        }
//
//        public void setType(String type) {
//            this.type = type;
//        }
//
//        public String getCode() {
//            return code;
//        }
//
//        public void setCode(String code) {
//            this.code = code;
//        }
//
//        public String getCodAnu() {
//            return codAnu;
//        }
//
//        public void setCodAnu(String codAnu) {
//            this.codAnu = codAnu;
//        }
//
//        public int getCodeRI() {
//            return codeRI;
//        }
//
//        public void setCodeRI(int codeRI) {
//            this.codeRI = codeRI;
//        }
//
//        public Boolean getIsArchived() {
//            return isArchived;
//        }
//
//        public void setIsArchived(Boolean isArchived) {
//            this.isArchived = isArchived;
//        }
//
//        public Date getDateDebCamp() {
//            return dateDebCamp;
//        }
//
//        public void setDateDebCamp(Date dateDebCamp) {
//            this.dateDebCamp = dateDebCamp;
//        }
//
//        public Date getDateFinCamp() {
//            return dateFinCamp;
//        }
//
//        public void setDateFinCamp(Date dateFinCamp) {
//            this.dateFinCamp = dateFinCamp;
//        }
//    }


}
