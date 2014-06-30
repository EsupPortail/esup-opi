package org.esupportail.opi.web.candidat.controllers;

import fj.Effect;
import fj.F;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.dto.AvisDTO;
import org.esupportail.opi.domain.dto.CandidatVoeuDTO;
import org.esupportail.opi.domain.services.DomainCandidatService;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.dto.CandidatDTO;
import org.esupportail.opi.services.i18n.I18NUtilsService;
import org.esupportail.opi.web.candidat.beans.AvisPojo;
import org.esupportail.opi.web.candidat.beans.CandidatVoeuPojo;

import org.esupportail.opi.web.candidat.utils.TransDtoToPojo;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static fj.data.Array.iterableArray;
import static fj.data.Array.single;
import static fj.data.Option.*;
import static java.util.Arrays.asList;

public class CandidaturesController extends CandidatController {

    protected final ParameterService parameterService;

    protected final DomainCandidatService domainCandidatService;

    protected Array<CandidatVoeuPojo> candidatVoeuxPojo = Array.empty();

    private I18NUtilsService i18NUtils;

    private CandidaturesController(final DomainService domainService,
                                   final DomainCandidatService domainCandidatService,
                                   final DomainApoService apoService,
                                   final I18nService i18nService,
                                   final ParameterService parameterService) {
        super(domainService, apoService, i18nService);
        this.domainCandidatService = domainCandidatService;
        this.parameterService = parameterService;
    }

    public static CandidaturesController candidaturesController(final DomainService domainService,
                                                                final DomainCandidatService domainCandidatService,
                                                                final DomainApoService apoService,
                                                                final I18nService i18nService,
                                                                final ParameterService parameterService) {
        return new CandidaturesController(domainService, domainCandidatService, apoService, i18nService, parameterService);
    }

    @Override
    public void initView() {
        super.initView();
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ExternalContext externalContext = facesContext.getExternalContext();
        final Option<String> dossier = fromString(externalContext.getRequestParameterMap().get("dossier"));
        final Option<Individu> maybeIndividu = join(dossier.map(new F<String, Option<Individu>>() {
            public Option<Individu> f(String d) {
                return fromNull(domainService.getIndividu(d, null));
            }
        }));

        Option<CandidatDTO> candidatdto = domainCandidatService.fetchIndById(dossier.some(), Option.<Boolean>none());
        if (candidatdto.isSome()) {
            candidatdto.foreach(new Effect<CandidatDTO>() {
                public void e(CandidatDTO cdto) {
                    Collection<CandidatVoeuDTO> cdtoVoeux = cdto.getVoeux();
                    for (final CandidatVoeuDTO cdtoVoeu : cdtoVoeux) {
                        final TraitementCmi trtCmi = cdtoVoeu.getLinkTrtCmiCamp().getTraitementCmiDto();
                        final VersionEtapeDTO vet = apoService.getVersionEtape(
                                trtCmi.getVersionEtpOpi().getCodEtp(),
                                trtCmi.getVersionEtpOpi().getCodVrsVet());
                        final Collection<AvisPojo> avisPojo =  iterableArray(cdtoVoeu.getAvis())
                                .map(TransDtoToPojo.avisDtoToPojo)
                                .toCollection();
                        candidatVoeuxPojo = candidatVoeuxPojo
                                .append(single(new CandidatVoeuPojo(cdtoVoeu, vet, avisPojo)))
                                .reverse();
                    }
                }
            });
        }
    }

    public List<CandidatVoeuPojo> getCandidatVoeuxPojoAsList() {
        return asList(candidatVoeuxPojo.array(CandidatVoeuPojo[].class));
    }


//    public static F<IndVoeu, CandidatVoeuPojo> indVoeuToCandVoeuPojo = new F<IndVoeu, CandidatVoeuPojo>() {
//        public CandidatVoeuPojo f(final IndVoeu i) {
//            final CandidatVoeuPojo candidatVoeuPojo = new CandidatVoeuPojo(i);
//            return candidatVoeuPojo;
//        }
//    };
}
