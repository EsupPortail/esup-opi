package org.esupportail.opi.web.candidat.beans;

import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.dto.CandidatVoeuDTO;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.springframework.util.StringUtils;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.esupportail.opi.domain.beans.etat.EtatVoeu.*;


public class CandidatVoeuPojo implements Serializable {

    /**
     * The IndVoeu.
     */
    @NotNull
    private CandidatVoeuDTO cdtoVoeu;

    /**
     * The VersionEtape.
     */
    @NotNull
    private VersionEtapeDTO vrsEtape;

    private Collection<AvisPojo> avis;

    /**
     * The vows state.
     */
    private EtatVoeu etat;


    public CandidatVoeuPojo(final CandidatVoeuDTO cdtoVoeu,
                       final VersionEtapeDTO vrsEtp, Collection<AvisPojo> avis) {
        this.cdtoVoeu = cdtoVoeu;
        this.etat = EtatVoeu.fromString(cdtoVoeu.getState());
        this.vrsEtape = vrsEtp;
        this.avis = avis;
    }

    public CandidatVoeuPojo(final CandidatVoeuDTO cdtoVoeu) {
        this.cdtoVoeu = cdtoVoeu;
    }

    public CandidatVoeuDTO getCandidatVoeuDTO() {
        return cdtoVoeu;
    }

    public void setCandidatVoeuDTO(CandidatVoeuDTO cdtoVoeu) {
        this.cdtoVoeu = cdtoVoeu;
    }

    public CandidatVoeuPojo withCandidatVoeuDTO(CandidatVoeuDTO cdtoVoeu) {
        setCandidatVoeuDTO(cdtoVoeu);
        return this;
    }

    public VersionEtapeDTO getVrsEtape() {
        return vrsEtape;
    }

    public void setVrsEtape(VersionEtapeDTO vrsEtape) {
        this.vrsEtape = vrsEtape;
    }

    public EtatVoeu getEtat() {
        return etat;
    }

    public void setEtat(EtatVoeu etat) {
        this.etat = etat;
    }

    public Collection<AvisPojo> getAvis() {
        return avis;
    }

    public void setAvis(List<AvisPojo> avis) {
        this.avis = avis;
    }
}
