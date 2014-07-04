package org.esupportail.opi.domain.services;


import fj.Effect;
import fj.F;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.opi.dao.DaoService;
import org.esupportail.opi.dao.IndividuDaoService;
//import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.domain.dto.*;
import org.esupportail.opi.utils.Constantes;

import java.util.*;

import static fj.data.Array.iterableArray;
import static fj.data.Option.fromNull;
import static fj.data.Option.fromString;
import static fj.data.Option.some;
import static java.lang.String.format;
import static org.esupportail.opi.domain.dto.CandidatDTO.*;

public class DomainCandidatServiceImpl implements DomainCandidatService {

    private final DaoService daoService;

    private final IndividuDaoService individuDaoSrv;

    private final DomainApoService apoService;

    private final DomainService domainService;

    private DomainCandidatServiceImpl(DaoService daoService, IndividuDaoService individuDaoSrv, DomainApoService apoService, DomainService domainService) {
        this.daoService = daoService;
        this.individuDaoSrv = individuDaoSrv;
        this.apoService = apoService;
        this.domainService = domainService;
    }

    public static DomainCandidatService domainCandidatServiceImpl(DaoService daoService, IndividuDaoService individuDaoSrv, DomainApoService apoService, DomainService domainService) {
        return new DomainCandidatServiceImpl(daoService, individuDaoSrv, apoService, domainService);
    }

    public void deleteCandidatVoeu(Individu individu, CandidatVoeuDTO candidatVoeuDto) {
        IndVoeu voeu = dtoToIndVoeu.f(candidatVoeuDto);
        domainService.deleteIndVoeu(voeu);
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
                    .withLinkTrtCmiCamp(linkTrtCmiToDTO.f(indVoeu.getLinkTrtCmiCamp()))
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
            indVoeu.setLinkTrtCmiCamp(dtoToLinkTrtCmiCamp.f(candidatVoeuDTO.getLinkTrtCmiCamp()));
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
}
