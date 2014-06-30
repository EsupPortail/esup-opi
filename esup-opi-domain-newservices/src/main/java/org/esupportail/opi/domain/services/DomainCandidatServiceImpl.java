package org.esupportail.opi.domain.services;


import fj.Effect;
import fj.F;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.opi.dao.DaoService;
import org.esupportail.opi.dao.IndividuDaoService;
//import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainApoService;
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

import java.util.ArrayList;

import static fj.data.Array.iterableArray;
import static fj.data.Option.fromNull;
import static fj.data.Option.fromString;
import static java.lang.String.format;
import static org.esupportail.opi.domain.dto.CandidatDTO.*;

public class DomainCandidatServiceImpl implements DomainCandidatService {

    private final DaoService daoService;

    private final IndividuDaoService individuDaoSrv;

    private final DomainApoService apoService;

    private DomainCandidatServiceImpl(DaoService daoService, IndividuDaoService individuDaoSrv, DomainApoService apoService) {
        this.daoService = daoService;
        this.individuDaoSrv = individuDaoSrv;
        this.apoService = apoService;
    }

    public static DomainCandidatService domainCandidatServiceImpl(DaoService daoService, IndividuDaoService individuDaoSrv, DomainApoService apoService) {
        return new DomainCandidatServiceImpl(daoService, individuDaoSrv, apoService);
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
                    .withAvis(new ArrayList<>(avisDTOs.toCollection()))
                    .withCodTypeTrait(indVoeu.getCodTypeTrait())
                    .withHaveBeTraited(indVoeu.isHaveBeTraited())
                    .withLinkTrtCmiCamp(linkTrtCmiToDTO.f(indVoeu.getLinkTrtCmiCamp()))
                    .withProp(indVoeu.getIsProp())
                    .withState(indVoeu.getState());
            return candidatVoeuDTO;
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
                    .withTemoinEnService(avis.getTemoinEnService());
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
                    .withTraitementCmiDto(linkTrtCmi.getTraitementCmi())
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
