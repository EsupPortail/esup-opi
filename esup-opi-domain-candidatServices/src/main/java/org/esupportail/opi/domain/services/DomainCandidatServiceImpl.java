package org.esupportail.opi.domain.services;


import fj.Effect;
import fj.F;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.dao.DaoService;
import org.esupportail.opi.dao.IndividuDaoService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.references.NombreVoeuCge;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.domain.dto.*;
import org.esupportail.opi.utils.Constantes;

import java.util.*;

import static fj.data.Array.iterableArray;
import static fj.data.Option.fromNull;
import static java.lang.String.format;
import static org.esupportail.opi.domain.dto.CandidatDTO.*;

import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;

public class DomainCandidatServiceImpl implements DomainCandidatService {

    private final DaoService daoService;

    private final IndividuDaoService individuDaoSrv;

    private DomainApoService apoService;

    private final DomainService domainService;

    private final ParameterService parameterService;

    private final Logger log = new LoggerImpl(getClass());

    private DomainCandidatServiceImpl(DaoService daoService, IndividuDaoService individuDaoSrv, DomainApoService apoService, DomainService domainService, ParameterService parameterService) {
        this.daoService = daoService;
        this.individuDaoSrv = individuDaoSrv;
        this.apoService = apoService;
        this.domainService = domainService;
        this.parameterService = parameterService;
    }

    public static DomainCandidatService domainCandidatServiceImpl(DaoService daoService, IndividuDaoService individuDaoSrv, DomainApoService apoService, DomainService domainService, ParameterService parameterService) {
        return new DomainCandidatServiceImpl(daoService, individuDaoSrv, apoService, domainService, parameterService);
    }

    public void deleteCandidatVoeu(Individu individu, CandidatVoeuDTO candidatVoeuDto) {
        IndVoeu voeu = dtoToIndVoeu.f(candidatVoeuDto);
        domainService.deleteIndVoeu(voeu);
    }

    /**
     * @see org.esupportail.opi.domain.DomainService#addIndVoeu(
     * org.esupportail.opi.domain.beans.user.candidature.IndVoeu)
     */
    public void addCandidatVoeu(final CandidatVoeuDTO candidatVoeuDto) {
        IndVoeu voeu = dtoToIndVoeu.f(candidatVoeuDto);
        voeu.setCodUserToCreate(voeu.getIndividu().getNumDossierOpi());
        voeu.setDateCreaEnr(new Date());
        if (log.isDebugEnabled()) {
            log.debug("entering addVoeu( " + voeu + " )");
        }
        domainService.addIndVoeu(voeu);
    }

    @Override
    public Option<CandidatDTO> fetchIndById(String id, Option<Boolean> onlyValidWishes) {
        Option<Individu> individu = individuDaoSrv.fetchIndAndAvisById(id, onlyValidWishes);
        return individu.map(individuToCandidatDTO);
    }

    public F<Individu, CandidatDTO> individuToCandidatDTO = new F<Individu, CandidatDTO>() {
        public CandidatDTO f(final Individu i) {
            final CandidatDTO candidatDTO = CandidatDTO.empty();
            candidatDTO.setNumDossierOpi(i.getNumDossierOpi());
            candidatDTO.setEtatCandidat(i.getState());

            // Etat civil
            final EtatCivilDTO etatCivil = EtatCivilDTO.empty()
                    .withCivilite(i.getSexe())
                    .withDateNaissance(i.getDateNaissance())
                    .withDeptNaissance(i.getCodDepPaysNaissance())
                    .withNationalite(i.getCodPayNationalite())
                    .withNne(format("%s%s", i.getCodeNNE(), i.getCodeClefNNE()))
                    .withNomPatronymique(i.getNomPatronymique())
                    .withNomUsuel(i.getNomUsuel())
                    .withPaysNaissance(i.getCodPayNaissance())
                    .withPrenom(i.getPrenom())
                    .withPrenomAutre(i.getPrenom2())
                    .withVilleNaissance(i.getVilleNaissance());
            candidatDTO.setEtatCivil(etatCivil);

            // Adresse fixe
            fromNull(i.getAdresses().get(Constantes.ADR_FIX))
                    .foreach(new Effect<Adresse>() {
                        public void e(Adresse a) {
                            final AdresseFixeDTO adresseFixe = AdresseFixeDTO.empty()
                                    .withAdresse(a.getAdr1())
                                    .withAdresseCmp1(a.getAdr2())
                                    .withAdresseCmp2(a.getAdr3())
                                    .withCodePostal(a.getCodBdi())
                                    .withEmail(i.getAdressMail())
                                    .withPays(a.getCodPays())
                                    .withTelFixe(a.getPhoneNumber())
                                    .withTelPortable(i.getNumeroTelPortable())
                                    .withVille(a.getCodCommune())
                                    .withVilleEtr(a.getLibComEtr());
                            candidatDTO.setAdresseFixe(adresseFixe);
                        }
                    });

            // Baccalaureat
            final IndBac indBac = iterableArray(i.getIndBac()).get(0);
            fromNull(indBac).foreach(new Effect<IndBac>() {
                public void e(IndBac indBac) {
                    final BaccalaureatDTO baccalaureat = BaccalaureatDTO.empty()
                            .withBac(indBac.getCodBac())
                            .withDateObtention(indBac.getDateObtention())
                            .withDepartement(indBac.getCodDep())
                            .withEtablissement(indBac.getCodEtb())
                            .withMention(indBac.getCodMnb())
                            .withPays(indBac.getCodPay())
                            .withVille(indBac.getCodCom());
                    candidatDTO.setBaccalaureat(baccalaureat);
                }
            });

            final Array<CursusScolDTO> cursusScols =  iterableArray(i.getCursusScol())
                    .map(cursusScolToDTO);
            candidatDTO.setCursusScols(cursusScols.toCollection());

            final Array<CandidatVoeuDTO> candidatVoeux =  iterableArray(i.getVoeux())
                    .map(indVoeuToDTO);
            candidatDTO.setVoeux(candidatVoeux.toCollection());

            final Array<CampagneDTO> candidatCampagnes =  iterableArray(i.getCampagnes())
                    .map(indCampagneToDTO);
            candidatDTO.setCampagnes(candidatCampagnes.toCollection());

            return candidatDTO;
        }
    };

    public static F<IndCursusScol, CursusScolDTO> cursusScolToDTO = new F<IndCursusScol, CursusScolDTO>() {
        public CursusScolDTO f(final IndCursusScol i) {
            final CursusScolDTO cursusScolDTO = CursusScolDTO.empty()
                    .withId(i.getId())
                    .withType(i.getType())
                    .withAnnee(i.getAnnee())
                    .withMention(i.getLibMention())
                    .withObtention(i.getResultat())
                    .withFromApogee(i.getTemoinFromApogee());

            final CursusScolDTO.Formation formation = cursusScolDTO.getFormation();
            final CursusScolDTO.Etablissement etablissement = cursusScolDTO.getEtablissement();

            return cursusScolDTO;
        }
    };

    public static F<IndVoeu, CandidatVoeuDTO> indVoeuToDTO = new F<IndVoeu, CandidatVoeuDTO>() {
        @Override
        public CandidatVoeuDTO f(final IndVoeu indVoeu) {
            Array<AvisDTO> avisDTOs = iterableArray(indVoeu.getAvis()).map(avisToDTO);
            final CandidatVoeuDTO candidatVoeuDTO = CandidatVoeuDTO.empty()
                    .withId(indVoeu.getId())
                    .withIndividu(indVoeu.getIndividu())
                    .withAvis(new ArrayList<>(avisDTOs.toCollection()))
                    .withCodTypeTrait(indVoeu.getCodTypeTrait())
                    .withHaveBeTraited(indVoeu.isHaveBeTraited())
                    .withLinkTrtCmiCamp(indVoeu.getLinkTrtCmiCamp())
                    .withProp(indVoeu.getIsProp())
                    .withState(indVoeu.getState());
            return candidatVoeuDTO;
        }
    };

    public static F<CandidatVoeuDTO, IndVoeu> dtoToIndVoeu = new F<CandidatVoeuDTO, IndVoeu>() {
        @Override
        public IndVoeu f(final CandidatVoeuDTO candidatVoeuDTO) {
            final IndVoeu indVoeu = new IndVoeu();
            indVoeu.setId(candidatVoeuDTO.getId());
            indVoeu.setIndividu(candidatVoeuDTO.getIndividu());
            indVoeu.setCodTypeTrait(candidatVoeuDTO.getCodTypeTrait());
            indVoeu.setState(candidatVoeuDTO.getState());
            indVoeu.setIsProp(candidatVoeuDTO.isProp());
            indVoeu.setHaveBeTraited(candidatVoeuDTO.isHaveBeTraited());
            indVoeu.setLinkTrtCmiCamp(candidatVoeuDTO.getLinkTrtCmiCamp());
            indVoeu.setAvis(new HashSet<Avis>());
            if(candidatVoeuDTO.getAvis() != null && !candidatVoeuDTO.getAvis().isEmpty()) {
                Array<Avis> avis = iterableArray(candidatVoeuDTO.getAvis()).map(dtoToAvis);
                indVoeu.getAvis().addAll(avis.toCollection());
            }
            return indVoeu;
        }
    };

    public static F<LinkTrtCmiCampDTO, LinkTrtCmiCamp> dtoToLinkTrtCmiCamp = new F<LinkTrtCmiCampDTO, LinkTrtCmiCamp>() {
        @Override
        public LinkTrtCmiCamp f(final LinkTrtCmiCampDTO linkTrtCmiCampDTO) {
            final LinkTrtCmiCamp linkTrtCmiCamp = new LinkTrtCmiCamp();
            linkTrtCmiCamp.setCampagne(linkTrtCmiCampDTO.getCampagneDto());
            linkTrtCmiCamp.setTraitementCmi(linkTrtCmiCampDTO.getTraitementCmi());
            return linkTrtCmiCamp;
        }
    };

    public static F<AvisDTO, Avis> dtoToAvis = new F<AvisDTO, Avis>() {
        @Override
        public Avis f(AvisDTO avisDto) {
            final Avis avis = new Avis();
                avis.setAppel(avisDto.getAppel());
                avis.setCommentaire(avisDto.getCommentaire());
                avis.setRang(avisDto.getRang());
                avis.setValidation(avisDto.getValidation());
                avis.setTemoinEnService(avisDto.getTemoinEnService());
                avis.setResult(avisDto.getResult());
            if(avisDto.getMotivationAvis() != null) {
                avis.setMotivationAvis(dtoToMotivationAvis.f(avisDto.getMotivationAvis()));
            }
            return avis;
        }
    };

    public static F<AvisDTO.MotivationAvisDTO, MotivationAvis> dtoToMotivationAvis = new F<AvisDTO.MotivationAvisDTO, MotivationAvis>() {
        @Override
        public MotivationAvis f(AvisDTO.MotivationAvisDTO motivationAvisDTO) {
            final MotivationAvis motivationAvis = new MotivationAvis();
                motivationAvis.setCode(motivationAvisDTO.getCode());
                motivationAvis.setLibelle(motivationAvisDTO.getLibelle());
            return motivationAvis;
        }
    };

    public static F<Campagne, CampagneDTO> indCampagneToDTO = new F<Campagne, CampagneDTO>() {
        @Override
        public CampagneDTO f(final Campagne campagne) {
            Array<LinkTrtCmiCampDTO> linkTrtCmiToDTOs = iterableArray(campagne.getLinkTrtCmiCamp()).map(linkTrtCmiToDTO);
            final CampagneDTO campagneDTO = CampagneDTO.empty()
                    .withId(campagne.getId())
                    .withTemoinEnService(campagne.getTemoinEnService())
                    .withCodAnu(campagne.getCodAnu())
                    .withCode(campagne.getCode())
                    .withCodeRI(campagne.getCodeRI())
                    .withDateDebCamp(campagne.getDateDebCamp())
                    .withDateFinCamp(campagne.getDateFinCamp())
                    .withIsArchived(campagne.getIsArchived())
                    .withLinkTrtCmiCamp(new ArrayList<>(linkTrtCmiToDTOs.toCollection()));
            return campagneDTO;
        }
    };

    public static F<Avis, AvisDTO> avisToDTO = new F<Avis, AvisDTO>() {
        @Override
        public AvisDTO f(Avis avis) {
            final AvisDTO avisDTO = AvisDTO.empty()
                    .withAppel(avis.getAppel())
                    .withCommentaire(avis.getCommentaire())
                    .withRang(avis.getRang())
                    .withValidation(avis.getValidation())
                    .withTemoinEnService(avis.getTemoinEnService())
                    .withResult(avis.getResult());
            if(avis.getMotivationAvis() != null) {
                avisDTO.withMotivationAvis(motivationAvisToDTO.f(avis.getMotivationAvis()));
            }
            return avisDTO;
        }
    };

    public static F<LinkTrtCmiCamp, LinkTrtCmiCampDTO> linkTrtCmiToDTO = new F<LinkTrtCmiCamp, LinkTrtCmiCampDTO>() {
        @Override
        public LinkTrtCmiCampDTO f(LinkTrtCmiCamp linkTrtCmi) {
            final LinkTrtCmiCampDTO linkTrtCmiDTO = LinkTrtCmiCampDTO.empty()
                    .withTraitementCmi(linkTrtCmi.getTraitementCmi())
                    .withCampagneDto(linkTrtCmi.getCampagne());
            return linkTrtCmiDTO;
        }
    };

    public static F<MotivationAvis, AvisDTO.MotivationAvisDTO> motivationAvisToDTO = new F<MotivationAvis, AvisDTO.MotivationAvisDTO>() {
        @Override
        public AvisDTO.MotivationAvisDTO f(MotivationAvis motivationAvis) {
            final AvisDTO.MotivationAvisDTO motivationAvisDTO = AvisDTO.MotivationAvisDTO.empty()
                    .withCode(motivationAvis.getCode())
                    .withLibelle(motivationAvis.getLibelle());
            return motivationAvisDTO;
        }
    };

    public Map<VersionEtpOpi, FormulaireCmi> getFormulaireCmi(Integer codeRi) {
        Map<VersionEtpOpi, FormulaireCmi> map = parameterService.getFormulairesCmi(null, codeRi);
        return map;
    }

    /**
     * @see org.esupportail.opi.domain.DomainService#getIndividu(java.lang.String, java.util.Date)
     */
    public Individu getIndividu(final String numDosOpi, final Date dateNaissance) {
        if (log.isDebugEnabled()) {
            log.debug("entering getIndividu( " + numDosOpi + ", " + dateNaissance + " )");
        }
        return this.daoService.getIndividu(numDosOpi, dateNaissance);
    }

    public Campagne getCampagneEnServ(Integer codeRi) {
        Campagne camp = parameterService.getCampagneEnServ(codeRi);
        return camp;
    }

    public Set<Commission> getCommissions(Boolean b) {
        Set<Commission> cmi = parameterService.getCommissions(true);
        return cmi;
    }

    public VersionEtapeDTO getVersionEtape(TraitementCmi trtCmi) {
        VersionEtapeDTO vet = apoService.getVersionEtape(
                trtCmi.getVersionEtpOpi().getCodEtp(),
                trtCmi.getVersionEtpOpi().getCodVrsVet());
        return vet;
    }

    @Override
    public List<GrpTypDip> getGrpTypDip(Campagne camp) {
        List<GrpTypDip> group = apoService.getGrpTypDip(camp);
        return group;
    }

    @Override
    public Map<Domaine2AnnuForm, List<Cles2AnnuForm>> getDomaine2AnnuForm(GrpTypDip grpTypDip, String locale) {
        Map<Domaine2AnnuForm, List<Cles2AnnuForm>> domains = apoService.getDomaine2AnnuForm(grpTypDip, locale.toUpperCase());
        return domains;
    }

    @Override
    public List<VersionDiplomeDTO> getVersionDiplomes(String codeKeyWord, GrpTypDip grpTpd, String codAnu) {
        List<VersionDiplomeDTO> versionDiplomeDTOs = apoService.getVersionDiplomes(codeKeyWord, grpTpd, codAnu);
        return versionDiplomeDTOs;
    }

    @Override
    public List<VersionEtapeDTO> getVersionEtapes(VersionDiplomeDTO vrsDip, String codAnu) {
        List<VersionEtapeDTO> versionEtapeDTOs = apoService.getVersionEtapes(vrsDip, codAnu);
        return versionEtapeDTOs;
    }

    @Override
    public TraitementCmi getTraitementCmi(
            final VersionEtpOpi versionEtpOpi,
            final Boolean initSelection) {
        return parameterService.getTraitementCmi(versionEtpOpi, initSelection);
    }

    @Override
    public TraitementCmi getTraitementCmi(final Integer id) {
        if (log.isDebugEnabled()) {
            log.debug("entering getTraitementCmi( " + id + " )");
        }
        return parameterService.getTraitementCmi(id);
    }

    @Override
    public LinkTrtCmiCampDTO getLinkTrtCmiCampDto(final TraitementCmi traitementCmi,
                                            final Campagne campagne) {
        if (log.isDebugEnabled()) {
            log.debug("entering getLinkTrtCmiCamp ( " + traitementCmi
                    + ", " + campagne + " )");
        }
        return linkTrtCmiToDTO.f(parameterService.getLinkTrtCmiCamp(traitementCmi, campagne));
    }

    @Override
    public LinkTrtCmiCamp getLinkTrtCmiCamp(final TraitementCmi traitementCmi,
                                               final Campagne campagne) {
        if (log.isDebugEnabled()) {
            log.debug("entering getLinkTrtCmiCamp ( " + traitementCmi
                    + ", " + campagne + " )");
        }
        return parameterService.getLinkTrtCmiCamp(traitementCmi, campagne);
    }

    // ////////////////////////////////////////////////////////////
    // NombreVoeuCge
    // ////////////////////////////////////////////////////////////

    @Override
    public List<NombreVoeuCge> getAllNombreDeVoeuByCge() {
        return parameterService.getAllNombreDeVoeuByCge();
    }

    //////////////////////////////////////////////////////////////
    // CalendarRDV
    //////////////////////////////////////////////////////////////

    @Override
    public List<CalendarRDV> getCalendarRdv() {
        return parameterService.getCalendarRdv();
    }

}
