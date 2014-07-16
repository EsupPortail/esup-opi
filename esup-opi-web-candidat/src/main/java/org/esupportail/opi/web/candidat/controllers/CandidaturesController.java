package org.esupportail.opi.web.candidat.controllers;

import fj.Effect;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.dto.CandidatVoeuDTO;
import org.esupportail.opi.domain.services.DomainCandidatService;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.dto.CandidatDTO;
import org.esupportail.opi.services.i18n.I18NUtilsService;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.pojo.SearchFormationPojo;
import org.esupportail.opi.web.beans.pojo.VersionEtapePojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorSelectItem;
import org.esupportail.opi.web.candidat.beans.AvisPojo;
import org.esupportail.opi.web.candidat.beans.CampagnePojo;
import org.esupportail.opi.web.candidat.beans.CandidatPojo;
import org.esupportail.opi.web.candidat.beans.CandidatVoeuPojo;

import org.esupportail.opi.web.candidat.utils.TransDtoToPojo;
import org.esupportail.opi.web.candidat.utils.TransPojoToDto;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;
import org.springframework.util.StringUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import java.util.*;

import static fj.data.Array.iterableArray;
import static fj.data.Array.single;
import static fj.data.Option.*;
import static java.util.Arrays.asList;

public class CandidaturesController extends CandidatController {

//    protected final ParameterService parameterService;

    protected final DomainCandidatService domainCandidatService;

//    protected final DomainApoService apoService;

    protected Array<CandidatVoeuPojo> candidatVoeuxPojo = Array.empty();

    /**
     * The SearchFormationPojo.
     */
    private SearchFormationPojo searchFormationPojo = new SearchFormationPojo();

    private I18NUtilsService i18NUtils;

    private int currentLevel = 1;

    private int codeRICandidat;

    private Campagne campagneEnServ;

    /**
     * Code groupe Type diplome pour les licences.
     */
    private static final String COD_LICENCE = "LICENCE";

    /**
     * Code groupe Type diplome pour les Masters.
     */
    private static final String COD_MASTER = "MASTER";

    /**
     * Code groupe Type diplome pour les Doctorats.
     */
    private static final String COD_DOCTORAT = "DOCTORAT";

    private CandidaturesController(final DomainService domainService,
                                   final DomainApoService apoService,
                                   final I18nService i18nService,
                                   final ParameterService parameterService,
                                   final DomainCandidatService domainCandidatService) {
        super(domainService, apoService, i18nService);
        this.domainCandidatService = domainCandidatService;
//        this.apoService = apoService;
//        this.parameterService = parameterService;
    }

    public static CandidaturesController candidaturesController(final DomainService domainService,
                                                                final DomainApoService apoService,
                                                                final I18nService i18nService,
                                                                final ParameterService parameterService,
                                                                final DomainCandidatService domainCandidatService) {
        return new CandidaturesController(domainService, apoService, i18nService, parameterService, domainCandidatService);
    }

    @Override
    public void initView() {
        super.initView();
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ExternalContext externalContext = facesContext.getExternalContext();
        final Option<String> dossier = fromString(externalContext.getRequestParameterMap().get("dossier"));
        initCandidatVoeuPojo(dossier);
        initCodeRICandidat();
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
//        Map<VersionEtpOpi, FormulaireCmi> map = parameterService.getFormulairesCmi(null, getCodeRICandidat());getFormulaireCmi
        Map<VersionEtpOpi, FormulaireCmi> map = domainCandidatService.getFormulaireCmi(codeRICandidat);
        for (CandidatVoeuPojo candidatVoeuPojo : candidatVoeuxPojo) {
            TraitementCmi trtCmi = candidatVoeuPojo.getLinkTrtCmiCampPojo().getTraitementCmi();
            if (map.containsKey(trtCmi.getVersionEtpOpi())) {
                retour.put(trtCmi.getVersionEtpOpi(), map.get(trtCmi.getVersionEtpOpi()));
            }
        }
        return retour;
    }

    public void initCodeRICandidat() {
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
                    codeRI = camp.getCodeRI();
                    break;
                }
            }
        }
        this.codeRICandidat = codeRI;
        this.campagneEnServ = domainCandidatService.getCampagneEnServ(codeRI);
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

    /**
     * The all Ren1GrpTypDip in use.
     *            if (!r.getCodGrpTpd().equals(COD_LICENCE)
     && !r.getCodGrpTpd().equals(COD_MASTER)
     && !r.getCodGrpTpd().equals(COD_DOCTORAT)) {
     l.add(r);
     }
     * @return List< Ren1GrpTypDip>
     */
    public List<GrpTypDip> getGroupTypeDip() {
        Campagne camp = domainCandidatService.getCampagneEnServ(codeRICandidat);
        List<GrpTypDip> group = domainCandidatService.getGrpTypDip(camp);
        ArrayList gtdList = new ArrayList();
        for (GrpTypDip r : group) {
            if (r.getCodGrpTpd().equals(COD_LICENCE)) {
                gtdList.add(r);
                break;
            }
        }
        for (GrpTypDip r : group) {
            if (r.getCodGrpTpd().equals(COD_MASTER)) {
                gtdList.add(r);
                break;
            }
        }
        for (GrpTypDip r : group) {
            if (r.getCodGrpTpd().equals(COD_DOCTORAT)) {
                gtdList.add(r);
                break;
            }
        }
        for (GrpTypDip r : group) {
            if (!r.getCodGrpTpd().equals(COD_LICENCE)
                    && !r.getCodGrpTpd().equals(COD_MASTER)
                    && !r.getCodGrpTpd().equals(COD_DOCTORAT)) {
                gtdList.add(r);
            }
        }
//        Set<GrpTypDip> orderedGtdList = new HashSet<>()
        return gtdList;
    }

    public void setTypeSelected(final String codeRI) {
        Campagne camp = domainCandidatService.getCampagneEnServ(codeRICandidat);
        List<GrpTypDip> group = domainCandidatService.getGrpTypDip(camp);
        for (GrpTypDip r : group) {
            if(r.getCodGrpTpd().equals(codeRI)) {
                searchFormationPojo.setGroupTypSelected(r);
                break;
            }
        }
    }

    public String getTypeSelected() {
        if(searchFormationPojo.getGroupTypSelected() != null) {
            return searchFormationPojo.getGroupTypSelected().getCodGrpTpd();
        }
        return "";
    }

    /**
     * The item for the key words.
     *
     * @return List< SelectItem>
     */
    public List<SelectItem> getKeyWordItems() {
        List<SelectItem> l = new ArrayList<SelectItem>();
        if (searchFormationPojo.getGroupTypSelected() != null) {
            String locale = "FR";
            Map<Domaine2AnnuForm, List<Cles2AnnuForm>> domains =
                    domainCandidatService.getDomaine2AnnuForm(
                            searchFormationPojo.getGroupTypSelected(), locale.toUpperCase());
            List<SelectItem> listGroup = new ArrayList<SelectItem>();
            SelectItemGroup group = null;
            l.add(new SelectItem("", ""));
            for (Map.Entry<Domaine2AnnuForm, List<Cles2AnnuForm>> entry : domains.entrySet()) {
                Set<SelectItem> si = new TreeSet<SelectItem>(new Comparator<SelectItem>() {
                    @Override
                    public int compare(SelectItem s1, SelectItem s2) {
                        String cmp1 = s1.getLabel() + s1.getValue();
                        String cmp2 = s2.getLabel() + s2.getValue();
                        return cmp1.compareToIgnoreCase(cmp2);
                    }
                });
                for (Cles2AnnuForm cles : entry.getValue()) {
                    SelectItem s = new SelectItem(cles.getClesAnnuForm().getCodCles(), cles.getLibCles());
                    si.add(s);
                }
                if (si.size() != 0) {
                    group = new SelectItemGroup(entry.getKey().getLibDom(), null, false, si.toArray(new SelectItem[si.size()]));
                    listGroup.add(group);
                }
            }
            Collections.sort(listGroup, new ComparatorSelectItem());
            l.addAll(listGroup);
        }
        return l;
    }

    /**
     * Make the Version diplome list for the codKeyWordSelected.
     */
    public void selectKeyWord() {
        Campagne camp = domainCandidatService.getCampagneEnServ(codeRICandidat);
        searchFormationPojo.setVersionEtapes(null);
        if (StringUtils.hasText(searchFormationPojo.getCodKeyWordSelected())) {
            searchFormationPojo.setVersionDiplomes(domainCandidatService
                    .getVersionDiplomes(searchFormationPojo.getCodKeyWordSelected(),
                            searchFormationPojo.getGroupTypSelected(),
                            camp.getCodAnu()));
            Set<Commission> cmi = domainCandidatService.getCommissions(true);
            List<VersionDiplomeDTO> vdi = new ArrayList<VersionDiplomeDTO>();
            //on retire le diplome qui n'ont pas de VET rattachees e des commissions.
            for (VersionDiplomeDTO v : searchFormationPojo.getVersionDiplomes()) {
                List<VersionEtapeDTO> list = domainCandidatService.getVersionEtapes(
                        v, camp.getCodAnu());

                Map<Commission, Set<VersionEtapeDTO>> mapCmi =
                        Utilitaires.getCmiForVetDTO(cmi, new HashSet<VersionEtapeDTO>(list), camp);

                if (!mapCmi.isEmpty()) {
                    vdi.add(v);
                }
            }
            searchFormationPojo.setVersionDiplomes(vdi);
        }
    }

    public SearchFormationPojo getSearchFormationPojo() {
        return searchFormationPojo;
    }

    public void setSearchFormationPojo(final SearchFormationPojo searchFormationPojo) {
        this.searchFormationPojo = searchFormationPojo;
    }

    public void setVrsDipSelected(final String codeType) {
        List<VersionDiplomeDTO> vdis = searchFormationPojo.getVersionDiplomes();
        for (VersionDiplomeDTO vdi : vdis) {
            if(vdi.getCodDip().equals(codeType)) {
                searchFormationPojo.setVrsDipSelected(vdi);
                break;
            }
        }
    }

    public String getVrsDipSelected() {
        if(searchFormationPojo.getVrsDipSelected() != null) {
            return searchFormationPojo.getVrsDipSelected().getCodDip();
        }
        return "";
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

}
