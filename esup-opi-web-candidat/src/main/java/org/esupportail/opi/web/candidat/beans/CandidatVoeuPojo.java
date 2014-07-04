package org.esupportail.opi.web.candidat.beans;

import org.esupportail.opi.domain.beans.etat.Etat;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Collection;

import static org.esupportail.opi.domain.beans.etat.EtatVoeu.*;


public class CandidatVoeuPojo implements Serializable {

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
    private LinkTrtCmiCampPojo linkTrtCmiCampPojo;

    /**
     * The VersionEtape.
     */
    @NotNull
    private VersionEtapeDTO vrsEtape;

    private Collection<AvisPojo> avis;

    private CandidatVoeuPojo() {}

    public static CandidatVoeuPojo empty() {
        return new CandidatVoeuPojo();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CandidatVoeuPojo withId(Integer id) {
        setId(id);
        return this;
    }

    public Individu getIndividu() {
        return individu;
    }

    public void setIndividu(Individu individu) {
        this.individu = individu;
    }

    public CandidatVoeuPojo withIndividu(Individu individu) {
        setIndividu(individu);
        return this;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public CandidatVoeuPojo withState(String state) {
        setState(state);
        return this;
    }

    public String getCodTypeTrait() {
        return codTypeTrait;
    }

    public void setCodTypeTrait(String codTypeTrait) {
        this.codTypeTrait = codTypeTrait;
    }

    public CandidatVoeuPojo withCodTypeTrait(String codTypeTrait) {
        setCodTypeTrait(codTypeTrait);
        return this;
    }

    public boolean isProp() {
        return isProp;
    }

    public void setProp(boolean isProp) {
        this.isProp = isProp;
    }

    public CandidatVoeuPojo withProp(boolean isProp) {
        setProp(isProp);
        return this;
    }

    public boolean isHaveBeTraited() {
        return haveBeTraited;
    }

    public void setHaveBeTraited(boolean haveBeTraited) {
        this.haveBeTraited = haveBeTraited;
    }

    public CandidatVoeuPojo withHaveBeTraited(boolean haveBeTraited) {
        setHaveBeTraited(haveBeTraited);
        return this;
    }

    public LinkTrtCmiCampPojo getLinkTrtCmiCampPojo() {
        return linkTrtCmiCampPojo;
    }

    public void setLinkTrtCmiCampPojo(LinkTrtCmiCampPojo linkTrtCmiCamp) {
        this.linkTrtCmiCampPojo = linkTrtCmiCamp;
    }

    public CandidatVoeuPojo withLinkTrtCmiCamp(LinkTrtCmiCampPojo linkTrtCmiCamp) {
        setLinkTrtCmiCampPojo(linkTrtCmiCamp);
        return this;
    }

    public VersionEtapeDTO getVrsEtape() {
        return vrsEtape;
    }

    public void setVrsEtape(VersionEtapeDTO vrsEtape) {
        this.vrsEtape = vrsEtape;
    }

    public CandidatVoeuPojo withVrsEtape(VersionEtapeDTO vrsEtape) {
        setVrsEtape(vrsEtape);
        return this;
    }

    public Collection<AvisPojo> getAvis() {
        return avis;
    }

    public void setAvis(Collection<AvisPojo> avis) {
        this.avis = avis;
    }

    public CandidatVoeuPojo withAvis(Collection<AvisPojo> avis) {
        setAvis(avis);
        return this;
    }


}
