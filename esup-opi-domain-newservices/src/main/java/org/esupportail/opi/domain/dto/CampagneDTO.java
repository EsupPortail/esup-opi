package org.esupportail.opi.domain.dto;


import org.aspectj.apache.bcel.classfile.Constant;
import org.esupportail.opi.domain.beans.NormeSI;

import java.util.Date;
import java.util.List;
import java.util.Set;

public class CampagneDTO {

   /*
	******************* PROPERTIES INHERIT FROM NOMENCLATURE ******************* */

    /**
     *  The type of Nomenclature.
     */
    public static final String type = "CAMPAGNE";

    private Integer id;

    /**
     * The Nomenclature code.
     */
    private String code;

    /**
     * Default value = true.
     */
    private Boolean temoinEnService;

	/*
	 ******************* PROPERTIES ******************* */

    /**
     * Code de l'année universitaire de la campagne.
     */
    private String codAnu;

    /**
     * Code Regime d'inscription.
     * Default value 0 --> formation Initiale.
     * 1 --> Formation Continue
     */
    private int codeRI;

    /**
     * true if this campagne has been archived.
     */
    private Boolean isArchived;

    /**
     * Date de début de la campagne.
     */
    private Date dateDebCamp;

    /**
     * Date de fin de la campagne.
     */
    private Date dateFinCamp;

    /**
     * The list of link with the campagne and the voeux.
     */
    private List<LinkTrtCmiCampDTO> linkTrtCmiCamp;

    public static CampagneDTO empty() {
        return new CampagneDTO();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CampagneDTO withId(Integer id) {
        setId(id);
        return this;
    }

    public Boolean getTemoinEnService() {
        return temoinEnService;
    }

    public void setTemoinEnService(Boolean temoinEnService) {
        this.temoinEnService = temoinEnService;
    }

    public CampagneDTO withTemoinEnService(Boolean temoinEnService) {
        setTemoinEnService(temoinEnService);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CampagneDTO withCode(String code) {
        setCode(code);
        return this;
    }

    public String getCodAnu() {
        return codAnu;
    }

    public void setCodAnu(String codAnu) {
        this.codAnu = codAnu;
    }

    public CampagneDTO withCodAnu(String codAnu) {
        setCodAnu(codAnu);
        return this;
    }

    public int getCodeRI() {
        return codeRI;
    }

    public void setCodeRI(int codeRI) {
        this.codeRI = codeRI;
    }

    public CampagneDTO withCodeRI(int codeRI) {
        setCodeRI(codeRI);
        return this;
    }

    public Boolean getIsArchived() {
        return isArchived;
    }

    public void setIsArchived(Boolean isArchived) {
        this.isArchived = isArchived;
    }

    public CampagneDTO withIsArchived(Boolean isArchived) {
        setIsArchived(isArchived);
        return this;
    }

    public Date getDateDebCamp() {
        return dateDebCamp;
    }

    public void setDateDebCamp(Date dateDebCamp) {
        this.dateDebCamp = dateDebCamp;
    }

    public CampagneDTO withDateDebCamp(Date dateDebCamp) {
        setDateDebCamp(dateDebCamp);
        return this;
    }

    public Date getDateFinCamp() {
        return dateFinCamp;
    }

    public void setDateFinCamp(Date dateFinCamp) {
        this.dateFinCamp = dateFinCamp;
    }

    public CampagneDTO withDateFinCamp(Date dateFinCamp) {
        setDateFinCamp(dateFinCamp);
        return this;
    }

    public List<LinkTrtCmiCampDTO> getLinkTrtCmiCamp() {
        return linkTrtCmiCamp;
    }

    public void setLinkTrtCmiCamp(List<LinkTrtCmiCampDTO> linkTrtCmiCamp) {
        this.linkTrtCmiCamp = linkTrtCmiCamp;
    }

    public CampagneDTO withLinkTrtCmiCamp(List<LinkTrtCmiCampDTO> linkTrtCmiCamp) {
        setLinkTrtCmiCamp(linkTrtCmiCamp);
        return this;
    }
}
