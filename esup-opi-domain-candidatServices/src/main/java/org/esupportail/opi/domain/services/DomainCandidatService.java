package org.esupportail.opi.domain.services;

import fj.data.Option;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.NombreVoeuCge;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.dto.CandidatDTO;
import org.esupportail.opi.domain.dto.CandidatVoeuDTO;

import org.esupportail.opi.domain.dto.LinkTrtCmiCampDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * The candidat domain service interface.
 */
public interface DomainCandidatService extends Serializable {

    /**
     * <b>Eagerly</b> (in hibernate sense) fetch an {@link org.esupportail.opi.domain.beans.user.Individu} from the DB by its id
     * @param id The id (i.e 'numDossierOpi') of the {@link org.esupportail.opi.domain.beans.user.Individu}
     * @param onlyValidWishes wether the {@link org.esupportail.opi.domain.beans.user.candidature.IndVoeu}s of the {@link org.esupportail.opi.domain.beans.user.Individu} should be filtered
     *                        with regard to the validity of their Avis(cf. {@link org.esupportail.opi.domain.beans.user.candidature.Avis#validation}).
     *                        A {@link fj.data.Option#none()} value means no filtering.
     * @return The {@link org.esupportail.opi.domain.beans.user.Individu} of 'numDossierOpi' {@code id}
     */
    Option<CandidatDTO> fetchIndById(String id, Option<Boolean> onlyValidWishes);

    /**
     * Return the individu that have the given numDosOpi.
     * @param numDosOpi
     * @param dateNaissance
     * @return the individu
     */
    Individu getIndividu(String numDosOpi, Date dateNaissance);

    void deleteCandidatVoeu(Individu individu, CandidatVoeuDTO candidatVoeuDto);

    void addCandidatVoeu(CandidatVoeuDTO candidatVoeuDto);

    Map<VersionEtpOpi, FormulaireCmi> getFormulaireCmi(Integer codeRi);

    Campagne getCampagneEnServ(Integer codeRi);

    Set<Commission> getCommissions(Boolean b);

    VersionEtapeDTO getVersionEtape(TraitementCmi trtCmi);

    List<GrpTypDip> getGrpTypDip(Campagne camp);

    Map<Domaine2AnnuForm, List<Cles2AnnuForm>> getDomaine2AnnuForm(GrpTypDip grpTypDip, String locale);

    List<VersionDiplomeDTO> getVersionDiplomes(String codeKeyWord, GrpTypDip grpTpd, String codAnu);

    List<VersionEtapeDTO> getVersionEtapes(VersionDiplomeDTO vrsDip, String codAnu);

    //////////////////////////////////////////////////////////////
    // TraitementCmi
    //////////////////////////////////////////////////////////////
    /**
     * @param versionEtpOpi
     * @param initSelection if true init the proxy selection.
     * @return TraitementCmi
     */
    TraitementCmi getTraitementCmi(VersionEtpOpi versionEtpOpi, Boolean initSelection);

    /**
     * @param id
     * @return TraitementCmi
     */
    TraitementCmi getTraitementCmi(Integer id);

    /**
     * @param traitementCmi
     * @param campagne
     * @return LinkTrtCmiCampDTO
     */
    LinkTrtCmiCampDTO getLinkTrtCmiCampDto(TraitementCmi traitementCmi, Campagne campagne);

    /**
     * @param traitementCmi
     * @param campagne
     * @return LinkTrtCmiCampDTO
     */
    LinkTrtCmiCamp getLinkTrtCmiCamp(TraitementCmi traitementCmi, Campagne campagne);

    //////////////////////////////////////////////////////////////
    // NombreVoeuCge
    //////////////////////////////////////////////////////////////

    /**
     *
     * @return liste de nombre de voeux par CGE
     */
    List<NombreVoeuCge> getAllNombreDeVoeuByCge();


    //////////////////////////////////////////////////////////////
    // CalendarRDV
    //////////////////////////////////////////////////////////////

    /**
     *
     * @return liste des calendriers de rendez-vous
     */
    List<CalendarRDV> getCalendarRdv();

}
