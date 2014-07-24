package org.esupportail.opi.web.candidat.utils;


import fj.F;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.dto.AvisDTO;
import org.esupportail.opi.domain.dto.CandidatVoeuDTO;
import org.esupportail.opi.domain.dto.LinkTrtCmiCampDTO;
import org.esupportail.opi.web.candidat.beans.AvisPojo;
import org.esupportail.opi.web.candidat.beans.CampagnePojo;
import org.esupportail.opi.web.candidat.beans.CandidatVoeuPojo;
import org.esupportail.opi.web.candidat.beans.LinkTrtCmiCampPojo;

import java.util.Collection;

import static fj.data.Array.iterableArray;

public class TransPojoToDto {

    public static F<CandidatVoeuPojo, CandidatVoeuDTO> candidatVoeuPojoToCandidatVoeuDto = new F<CandidatVoeuPojo, CandidatVoeuDTO>() {

        @Override
        public CandidatVoeuDTO f(CandidatVoeuPojo candidatVoeuPojo) {
/*            final Collection<AvisDTO> avisDto =  iterableArray(candidatVoeuPojo.getAvis())
                    .map(avisPojoToDto).toCollection();*/
            final CandidatVoeuDTO candidatVoeuDto = CandidatVoeuDTO.empty()
                    .withId(candidatVoeuPojo.getId())
                    .withIndividu(candidatVoeuPojo.getIndividu())
                    .withCodTypeTrait(candidatVoeuPojo.getCodTypeTrait())
                    .withHaveBeTraited(candidatVoeuPojo.isHaveBeTraited())
                    .withProp(candidatVoeuPojo.isProp())
                    .withState(candidatVoeuPojo.getEtatVoeu().getCodeLabel())
                    .withLinkTrtCmiCamp(candidatVoeuPojo.getLinkTrtCmiCamp());
            return candidatVoeuDto;
        };
    };

    public static F<AvisPojo, AvisDTO> avisPojoToDto = new F<AvisPojo, AvisDTO>() {
        @Override
        public AvisDTO f(AvisPojo avisPojo) {
            final AvisDTO avisDto = AvisDTO.empty()
                    .withId(avisPojo.getId())
                    .withTemoinEnService(avisPojo.getTemoinEnService())
                    .withAppel(avisPojo.getAppel())
                    .withCommentaire(avisPojo.getCommentaire())
                    .withRang(avisPojo.getRang())
                    .withValidation(avisPojo.getValidation())
                    .withTemoinEnService(avisPojo.getTemoinEnService())
                    .withResult(avisPojo.getResult());
            if(avisPojo.getMotivationAvis() != null) {
                avisDto.withMotivationAvis(motivationAvisPojoToDto.f(avisPojo.getMotivationAvis()));
            }
            return avisDto;
        }
    };

    public static F<LinkTrtCmiCampPojo, LinkTrtCmiCampDTO> linkTrtCmiCampPojoToDto = new F<LinkTrtCmiCampPojo, LinkTrtCmiCampDTO>() {
        @Override
        public LinkTrtCmiCampDTO f(LinkTrtCmiCampPojo linkTrtCmiCampPojo) {
            final LinkTrtCmiCampDTO linkTrtCmiCampDTO = LinkTrtCmiCampDTO.empty()
                    .withTraitementCmi(linkTrtCmiCampPojo.getTraitementCmi())
                    .withCampagneDto(campagneToPojo.f(linkTrtCmiCampPojo.getCampagnePojo()));
            linkTrtCmiCampDTO.setVoeux(linkTrtCmiCampPojo.getVoeux());
            return linkTrtCmiCampDTO;
        }
    };

    public static final F<CampagnePojo, Campagne> campagneToPojo = new F<CampagnePojo, Campagne>() {
        public Campagne f(CampagnePojo campagnePojo) {
            final Campagne campagne = new Campagne();
            campagne.setCodeRI(campagnePojo.getCodeRI());
            campagne.setCode(campagnePojo.getCode());
            campagne.setCodAnu(campagnePojo.getCodAnu());
            campagne.setTemoinEnService(campagnePojo.getTemoinEnService());
            campagne.setId(campagnePojo.getId());
            return campagne;
        }
    };

    public static F<AvisPojo.MotivationAvisPojo, AvisDTO.MotivationAvisDTO> motivationAvisPojoToDto = new F<AvisPojo.MotivationAvisPojo, AvisDTO.MotivationAvisDTO>() {

        @Override
        public AvisDTO.MotivationAvisDTO f(AvisPojo.MotivationAvisPojo motivationAvisPojo) {
            final AvisDTO.MotivationAvisDTO motivationAvisDto = AvisDTO.MotivationAvisDTO.empty()
                    .withCode(motivationAvisPojo.getCode())
                    .withLibelle(motivationAvisPojo.getLibelle());
            return motivationAvisDto;
        }
    };
}
