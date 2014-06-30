package org.esupportail.opi.web.candidat.utils;

import fj.F;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.dto.AvisDTO;
import org.esupportail.opi.web.candidat.beans.AvisPojo;


public final class TransDtoToPojo {

    public static F<AvisDTO, AvisPojo> avisDtoToPojo = new F<AvisDTO, AvisPojo>() {
        @Override
        public AvisPojo f(AvisDTO avisDto) {
            final AvisPojo avisPojo = AvisPojo.empty()
                    .withAppel(avisDto.getAppel())
                    .withCommentaire(avisDto.getCommentaire())
                    .withRang(avisDto.getRang())
                    .withValidation(avisDto.getValidation())
                    .withTemoinEnService(avisDto.getTemoinEnService());
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
}
