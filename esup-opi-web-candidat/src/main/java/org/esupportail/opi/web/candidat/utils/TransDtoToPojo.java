package org.esupportail.opi.web.candidat.utils;

import fj.F;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.dto.AvisDTO;
import org.esupportail.opi.domain.dto.LinkTrtCmiCampDTO;
import org.esupportail.opi.web.candidat.beans.AvisPojo;
import org.esupportail.opi.web.candidat.beans.LinkTrtCmiCampPojo;


public final class TransDtoToPojo {

    public static F<AvisDTO, AvisPojo> avisDtoToPojo = new F<AvisDTO, AvisPojo>() {
        @Override
        public AvisPojo f(AvisDTO avisDto) {
            final AvisPojo avisPojo = AvisPojo.empty()
                    .withId(avisDto.getId())
                    .withTemoinEnService(avisDto.getTemoinEnService())
                    .withAppel(avisDto.getAppel())
                    .withCommentaire(avisDto.getCommentaire())
                    .withRang(avisDto.getRang())
                    .withValidation(avisDto.getValidation())
                    .withTemoinEnService(avisDto.getTemoinEnService())
                    .withResult(avisDto.getResult());
            if(avisDto.getMotivationAvis() != null) {
                avisPojo.withMotivationAvis(motivationAvisDtoToPojo.f(avisDto.getMotivationAvis()));
            }
            return avisPojo;
        }
    };

    public static F<AvisDTO.MotivationAvisDTO, AvisPojo.MotivationAvisPojo> motivationAvisDtoToPojo = new F<AvisDTO.MotivationAvisDTO, AvisPojo.MotivationAvisPojo>() {

        @Override
        public AvisPojo.MotivationAvisPojo f(AvisDTO.MotivationAvisDTO motivationAvisDTO) {
            final AvisPojo.MotivationAvisPojo motivationAvisPojo = AvisPojo.MotivationAvisPojo.empty()
                    .withCode(motivationAvisDTO.getCode())
                    .withLibelle(motivationAvisDTO.getLibelle());
            return motivationAvisPojo;
        }
    };

    public static F<LinkTrtCmiCampDTO, LinkTrtCmiCampPojo> linkTrtCmiCampDtoToPojo = new F<LinkTrtCmiCampDTO, LinkTrtCmiCampPojo>() {
        @Override
        public LinkTrtCmiCampPojo f(LinkTrtCmiCampDTO linkTrtCmiCampDTO) {
            final LinkTrtCmiCampPojo linkTrtCmiCampPojo = LinkTrtCmiCampPojo.empty()
                    .withTraitementCmi(linkTrtCmiCampDTO.getTraitementCmi())
                    .withCampagnePojo(Transform.campagneToPojo.f(linkTrtCmiCampDTO.getCampagneDto()));
            return linkTrtCmiCampPojo;
        }
    };
}
