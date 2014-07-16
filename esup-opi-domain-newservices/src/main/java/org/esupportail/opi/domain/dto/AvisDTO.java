package org.esupportail.opi.domain.dto;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;

import java.io.Serializable;


public class AvisDTO {

    private Integer id;

    /**
     * Default value = true.
     */
    private Boolean temoinEnService;

    /**
     * The boolean indiquant si l'avis a ete valide.
     * default value : false
     */
    private Boolean validation;

    /**
     * The boolean indiquant si l'avis est issu d'un appel.
     * default value : false
     */
    private Boolean appel;

    /**
     * The rang sur la liste complementaire de l'avis.
     */
    private Integer rang;

    /**
     * The commentaire de l'avis.
     */
    private String commentaire;

    /**
     * The motivation de l'avis.
     */
    private MotivationAvisDTO motivationAvis;

    /**
     * The result de l'avis.
     */
    private TypeDecision result;

    public static AvisDTO empty() {
        return new AvisDTO();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AvisDTO withId(Integer id) {
        setId(id);
        return this;
    }

    public Boolean getTemoinEnService() {
        return temoinEnService;
    }

    public void setTemoinEnService(Boolean temoinEnService) {
        this.temoinEnService = temoinEnService;
    }

    public AvisDTO withTemoinEnService(Boolean temoinEnService) {
        setTemoinEnService(temoinEnService);
        return this;
    }

    public Boolean getValidation() {
        return validation;
    }

    public void setValidation(Boolean validation) {
        this.validation = validation;
    }

    public AvisDTO withValidation(Boolean validation) {
        setValidation(validation);
        return this;
    }

    public Boolean getAppel() {
        return appel;
    }

    public void setAppel(Boolean appel) {
        this.appel = appel;
    }

    public AvisDTO withAppel(Boolean appel) {
        setAppel(appel);
        return this;
    }

    public Integer getRang() {
        return rang;
    }

    public void setRang(Integer rang) {
        this.rang = rang;
    }

    public AvisDTO withRang(Integer rang) {
        setRang(rang);
        return this;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

    public AvisDTO withCommentaire(String commentaire) {
        setCommentaire(commentaire);
        return this;
    }

    public MotivationAvisDTO getMotivationAvis() {
        return motivationAvis;
    }

    public void setMotivationAvis(MotivationAvisDTO motivationAvis) {
        this.motivationAvis = motivationAvis;
    }

    public AvisDTO withMotivationAvis(MotivationAvisDTO motivationAvis) {
        setMotivationAvis(motivationAvis);
        return this;
    }

    public TypeDecision getResult() {
        return result;
    }

    public void setResult(TypeDecision result) {
        this.result = result;
    }

    public AvisDTO withResult(TypeDecision result) {
        setResult(result);
        return this;
    }

    public static final class MotivationAvisDTO implements Serializable {

        private String code;

        private String libelle;

        public String getCode() {
            return code;
        }

        public static MotivationAvisDTO empty() {
            return new MotivationAvisDTO();
        }

        public void setCode(String code) {
            this.code = code;
        }

        public MotivationAvisDTO withCode(String code) {
            setCode(code);
            return this;
        }

        public String getLibelle() {
            return libelle;
        }

        public void setLibelle(String libelle) {
            this.libelle = libelle;
        }

        public MotivationAvisDTO withLibelle(String libelle) {
            setLibelle(libelle);
            return this;
        }
    }

}
