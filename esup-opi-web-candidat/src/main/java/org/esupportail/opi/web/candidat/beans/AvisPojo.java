package org.esupportail.opi.web.candidat.beans;


import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AvisPojo {

    private Integer id;

    /**
     * Le booléen indiquant si l'avis est issu d'un appel.
     * default value : false
     */
    private Boolean appel;

    /**
     * Le rang sur la liste complementaire de l'avis.
     */
    private Integer rang;

    /**
     * Commentaire de l'avis.
     */
    private String commentaire;

    /**
     * Motivation de l'avis.
     */
    private MotivationAvisPojo motivationAvis;

    /**
     * Le booléen  indiquant si l'avis a ete valide.
     * default value : false
     */
    private Boolean validation;

    /**
     * Default value = true.
     */
    private Boolean temoinEnService;

    /**
     * The result de l'avis.
     */
    private TypeDecision result;


    public static AvisPojo empty() {
        return new AvisPojo();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AvisPojo withId(Integer id) {
        setId(id);
        return this;
    }

    public Boolean getAppel() {
        return appel;
    }

    public void setAppel(Boolean appel) {
        this.appel = appel;
    }

    public AvisPojo withAppel(Boolean appel) {
        setAppel(appel);
        return this;
    }

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }

    public AvisPojo withRang(Integer rang) {
        setRang(rang);
        return this;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public AvisPojo withCommentaire(String commentaire) {
        setCommentaire(commentaire);
        return this;
    }

    public MotivationAvisPojo getMotivationAvis() {
        return motivationAvis;
    }

    public void setMotivationAvis(MotivationAvisPojo motivationAvis) {
        this.motivationAvis = motivationAvis;
    }

    public AvisPojo withMotivationAvis(MotivationAvisPojo motivationAvis) {
        setMotivationAvis(motivationAvis);
        return this;
    }

    public Boolean getValidation() {
        return validation;
    }

    public void setValidation(Boolean validation) {
        this.validation = validation;
    }

    public AvisPojo withValidation(Boolean validation) {
        setValidation(validation);
        return this;
    }

    public Boolean getTemoinEnService() {
        return temoinEnService;
    }

    public void setTemoinEnService(Boolean temoinEnService) {
        this.temoinEnService = temoinEnService;
    }

    public AvisPojo withTemoinEnService(Boolean temoinEnService) {
        setTemoinEnService(temoinEnService);
        return this;
    }

    public TypeDecision getResult() {
        return result;
    }

    public void setResult(TypeDecision result) {
        this.result = result;
    }

    public AvisPojo withResult(TypeDecision result) {
        setResult(result);
        return this;
    }

    public static final class MotivationAvisPojo implements Serializable {

        @NotNull
        private String code;

        private String libelle;

        public String getCode() {
            return code;
        }

        public static MotivationAvisPojo empty() {
            return new MotivationAvisPojo();
        }

        public void setCode(String code) {
            this.code = code;
        }

        public MotivationAvisPojo withCode(String code) {
            setCode(code);
            return this;
        }

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public MotivationAvisPojo withLibelle(String libelle) {
            setLibelle(libelle);
            return this;
        }
    }

    	/*
	 ******************* METHODS ********************** */

    /**
     * @return soit la motivation de l'avis, soit le commentaire
     */
    public String getCommentaireMotiv() {
        String comm = null;
        if (motivationAvis != null
                && !motivationAvis.getLibelle().equals("")) {
            comm = motivationAvis.getLibelle();
        }
        if (commentaire != null
                && !commentaire.equals("")) {
            comm = commentaire;
        }
        return comm;
    }
}
