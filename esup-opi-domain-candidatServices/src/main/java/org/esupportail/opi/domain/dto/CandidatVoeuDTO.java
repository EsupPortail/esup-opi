package org.esupportail.opi.domain.dto;

import org.esupportail.opi.domain.beans.etat.Etat;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.user.Individu;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class CandidatVoeuDTO {

    /**
     * The Individu.
     */
    private Individu individu;

    private Integer id;
    /**
     * The vow state.
     */
    private String state;

    /**
     * The code TypeTraitement.
     */
    private String codTypeTrait;

    /**
     * The avis du voeu.
     */
    private List<AvisDTO> avis;

    /**
     * true if the voeu is a proposition from the commission.
     */
    private boolean isProp;

    /**
     * Have the Voeu be traited by the manager ?
     * Default value false.
     */
    private boolean haveBeTraited;

    /**
     * The link between the traitement cmi and the campagne.
     */
//    private LinkTrtCmiCampDTO linkTrtCmiCamp;
    private LinkTrtCmiCamp linkTrtCmiCamp;


    private CandidatVoeuDTO() {}

    public static CandidatVoeuDTO empty() {
        return new CandidatVoeuDTO();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CandidatVoeuDTO withId(Integer id) {
        setId(id);
        return this;
    }

    public Individu getIndividu() {
        return individu;
    }

    public void setIndividu(Individu individu) {
        this.individu = individu;
    }

    public CandidatVoeuDTO withIndividu(Individu individu) {
        setIndividu(individu);
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CandidatVoeuDTO withState(String state) {
        setState(state);
        return this;
    }

    public String getCodTypeTrait() {
        return codTypeTrait;
    }

    public void setCodTypeTrait(String codTypeTrait) {
        this.codTypeTrait = codTypeTrait;
    }

    public CandidatVoeuDTO withCodTypeTrait(String codTypeTrait) {
        setCodTypeTrait(codTypeTrait);
        return this;
    }

    public List<AvisDTO> getAvis() {
        return avis;
    }

    public void setAvis(List<AvisDTO> avis) {
        this.avis = avis;
    }

    public CandidatVoeuDTO withAvis(List<AvisDTO> avisDTOs) {
        setAvis(avisDTOs);
        return this;
    }

    public boolean isProp() {
        return isProp;
    }

    public void setProp(boolean isProp) {
        this.isProp = isProp;
    }

    public CandidatVoeuDTO withProp(boolean isProp) {
        setProp(isProp);
        return this;
    }

    public boolean isHaveBeTraited() {
        return haveBeTraited;
    }

    public void setHaveBeTraited(boolean haveBeTraited) {
        this.haveBeTraited = haveBeTraited;
    }

    public CandidatVoeuDTO withHaveBeTraited(boolean haveBeTraited) {
        setHaveBeTraited(haveBeTraited);
        return this;
    }

//    public LinkTrtCmiCampDTO getLinkTrtCmiCamp() {
//        return linkTrtCmiCamp;
//    }
//
//    public void setLinkTrtCmiCamp(LinkTrtCmiCampDTO linkTrtCmiCamp) {
//        this.linkTrtCmiCamp = linkTrtCmiCamp;
//    }
//
//    public CandidatVoeuDTO withLinkTrtCmiCamp(LinkTrtCmiCampDTO linkTrtCmiCamp) {
//        setLinkTrtCmiCamp(linkTrtCmiCamp);
//        return this;
//    }

    public LinkTrtCmiCamp getLinkTrtCmiCamp() {
        return linkTrtCmiCamp;
    }

    public void setLinkTrtCmiCamp(LinkTrtCmiCamp linkTrtCmiCamp) {
        this.linkTrtCmiCamp = linkTrtCmiCamp;
    }

    public CandidatVoeuDTO withLinkTrtCmiCamp(LinkTrtCmiCamp linkTrtCmiCamp) {
        setLinkTrtCmiCamp(linkTrtCmiCamp);
        return this;
    }
}
