package org.esupportail.opi.web.candidat.beans;


public class CampagnePojo {

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

    public static CampagnePojo empty() {
        return new CampagnePojo()
                .withId(0)
                .withTemoinEnService(false);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CampagnePojo withId(Integer id) {
        setId(id);
        return this;
    }

    public Boolean getTemoinEnService() {
        return temoinEnService;
    }

    public void setTemoinEnService(Boolean temoinEnService) {
        this.temoinEnService = temoinEnService;
    }

    public CampagnePojo withTemoinEnService(Boolean temoinEnService) {
        setTemoinEnService(temoinEnService);
        return this;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CampagnePojo withCode(String code) {
        setCode(code);
        return this;
    }

    public String getCodAnu() {
        return codAnu;
    }

    public void setCodAnu(String codAnu) {
        this.codAnu = codAnu;
    }

    public CampagnePojo withCodAnu(String codAnu) {
        setCodAnu(codAnu);
        return this;
    }

    public int getCodeRI() {
        return codeRI;
    }

    public void setCodeRI(int codeRI) {
        this.codeRI = codeRI;
    }

    public CampagnePojo withCodeRI(int codeRI) {
        setCodeRI(codeRI);
        return this;
    }
}
