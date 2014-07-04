package org.esupportail.opi.web.candidat.controllers;

import fj.Effect;
import fj.F;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.dto.AvisDTO;
import org.esupportail.opi.domain.dto.CandidatVoeuDTO;
import org.esupportail.opi.domain.services.DomainCandidatService;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.dto.CandidatDTO;
import org.esupportail.opi.services.i18n.I18NUtilsService;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.candidat.beans.AvisPojo;
import org.esupportail.opi.web.candidat.beans.CampagnePojo;
import org.esupportail.opi.web.candidat.beans.CandidatVoeuPojo;

import org.esupportail.opi.web.candidat.utils.TransDtoToPojo;
import org.esupportail.opi.web.candidat.utils.TransPojoToDto;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.hibernate.LazyInitializationException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.*;

import static fj.data.Array.iterableArray;
import static fj.data.Array.single;
import static fj.data.Option.*;
import static java.util.Arrays.asList;

public class CandidaturesController extends CandidatController {

    protected final ParameterService parameterService;

    protected final DomainCandidatService domainCandidatService;

    protected Array<CandidatVoeuPojo> candidatVoeuxPojo = Array.empty();

    private I18NUtilsService i18NUtils;

    private int wizardLevel = 1;

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
        initCandidatVoeuPojo(dossier);
    }

    public void initCandidatVoeuPojo(Option<String> dossier) {
        Option<CandidatDTO> candidatdto = domainCandidatService.fetchIndById(dossier.some(), Option.<Boolean>none());
        if (candidatdto.isSome()) {
            candidatVoeuxPojo = Array.empty();
            candidatdto.foreach(new Effect<CandidatDTO>() {
                public void e(CandidatDTO cdto) {
                    Collection<CandidatVoeuDTO> cdtoVoeux = cdto.getVoeux();
                    for (final CandidatVoeuDTO cdtoVoeu : cdtoVoeux) {
                        final TraitementCmi trtCmi = cdtoVoeu.getLinkTrtCmiCamp().getTraitementCmi();
                        final VersionEtapeDTO vet = apoService.getVersionEtape(
                                trtCmi.getVersionEtpOpi().getCodEtp(),
                                trtCmi.getVersionEtpOpi().getCodVrsVet());
                        final Collection<AvisPojo> avisPojo =  iterableArray(cdtoVoeu.getAvis())
                                .map(TransDtoToPojo.avisDtoToPojo)
                                .toCollection();
                        candidatVoeuxPojo = candidatVoeuxPojo
                                .append(single(CandidatVoeuPojo.empty()
                                        .withId(cdtoVoeu.getId())
                                        .withIndividu(cdtoVoeu.getIndividu())
                                        .withAvis(avisPojo)
                                        .withCodTypeTrait(cdtoVoeu.getCodTypeTrait())
                                        .withState(cdtoVoeu.getState())
                                        .withHaveBeTraited(cdtoVoeu.isHaveBeTraited())
                                        .withProp(cdtoVoeu.isProp())
                                        .withVrsEtape(vet)
                                        .withLinkTrtCmiCamp(TransDtoToPojo.linkTrtCmiCampDtoToPojo.f(cdtoVoeu.getLinkTrtCmiCamp()))))
                                .reverse();
                    }
                }
            });
        }
    }

    public List<CandidatVoeuPojo> getCandidatVoeuxPojoAsList() {
        return asList(candidatVoeuxPojo.array(CandidatVoeuPojo[].class));
    }

    public void deleteCandidatVoeu(CandidatVoeuPojo candidatVoeuPojo) {
        final Individu individu = domainService.getIndividu(candidat.getDossier(), null);
        final CandidatVoeuDTO candidatVoeuDto = TransPojoToDto.candidatVoeuPojoToCandidatVoeuDto.f(candidatVoeuPojo);
        domainCandidatService.deleteCandidatVoeu(individu, candidatVoeuDto);
        initCandidatVoeuPojo(fromString(candidat.getDossier())) ;
    }
    /**
     * @return true if the current ind have to fill forms
     */
    public Map<VersionEtpOpi, FormulaireCmi> getIndSelectedForms() {
        Map<VersionEtpOpi, FormulaireCmi> retour = new HashMap<VersionEtpOpi, FormulaireCmi>();

        // On recupere tout les formulaires Cmi
        Map<VersionEtpOpi, FormulaireCmi> map = parameterService.getFormulairesCmi(null, getCodeRIIndividu());
        for (CandidatVoeuPojo candidatVoeuPojo : candidatVoeuxPojo) {
            TraitementCmi trtCmi = candidatVoeuPojo.getLinkTrtCmiCampPojo().getTraitementCmi();
            if (map.containsKey(trtCmi.getVersionEtpOpi())) {
                retour.put(trtCmi.getVersionEtpOpi(), map.get(trtCmi.getVersionEtpOpi()));
            }
        }
        return retour;
    }

    /**
     * @return the codeRI of the campagne en service.
     */
    public Integer getCodeRIIndividu() {
        Integer codeRI = null;
        List<CampagnePojo> listCampPojo = new ArrayList<>();
        if (candidat.getCampagnes() != null && !candidat.getCampagnes().isEmpty()) {
            for (CampagnePojo camp : candidat.getCampagnes()) {
                if (camp.getTemoinEnService()) {
                    codeRI = camp.getCodeRI();
                }
            }
        }
        if(codeRI == null)  {
            List<Campagne> listCamp = new ArrayList<>();
            listCamp.addAll(domainService.getIndividu(candidat.getDossier(), candidat.getEtatCivil().getDateNaissance())
                    .getCampagnes());
            for (Campagne camp : listCamp) {
                if (camp.getTemoinEnService()) {
                    return camp.getCodeRI();
                }
            }
        }
        return codeRI;
    }

    /**
     * @return true si au moins un voeu est favorable
     */
    public Boolean getCanConfirmVoeux() {
        for (CandidatVoeuPojo candidatVoeuPojo : candidatVoeuxPojo) {
            for (AvisPojo a : candidatVoeuPojo.getAvis()) {
                // Sort the type of avis
                if (a != null && a.getValidation() && a.getResult().getIsFinal()
                        && a.getResult().getCodeTypeConvocation().equals("IA")) {
                    return true;
                }
            }
        }
        return false;
    }

    public void upWizardLevel() {
        if (wizardLevel < 5) wizardLevel ++;
    }

    public int getWizardLevel() {
        return wizardLevel;
    }

    public void setWizardLevel(int wizardLevel) {
        this.wizardLevel = wizardLevel;
    }
}
