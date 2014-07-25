package org.esupportail.opi.web.candidat.controllers;

import fj.Effect;
import fj.F;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.commons.annotations.cache.RequestCache;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.NombreVoeuCge;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.dto.CandidatVoeuDTO;
import org.esupportail.opi.domain.services.DomainCandidatService;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.dto.CandidatDTO;
import org.esupportail.opi.services.i18n.I18NUtilsService;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.SearchFormationPojo;
import org.esupportail.opi.web.beans.pojo.VersionEtapePojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorSelectItem;
import org.esupportail.opi.web.candidat.beans.*;

import org.esupportail.opi.web.candidat.services.beans.ManagedCandidatCalendar;
import org.esupportail.opi.web.candidat.utils.TransDtoToPojo;
import org.esupportail.opi.web.candidat.utils.TransPojoToDto;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;
import org.primefaces.event.FlowEvent;
import org.springframework.util.StringUtils;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;

import java.util.*;

import static fj.data.Array.iterableArray;
import static fj.data.Array.single;
import static fj.data.Option.*;
import static java.util.Arrays.asList;
import static org.esupportail.opi.domain.beans.etat.EtatVoeu.EtatNonArrive;
import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.AccesSelectif;
import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.ValidationAcquis;
import static org.esupportail.opi.web.candidat.services.security.CandidatService.LoggedUser;

public class CandidaturesController extends CandidatController {

    protected final DomainCandidatService domainCandidatService;

    private ManagedCandidatCalendar managedCandidatCalendar;

    protected Array<CandidatVoeuPojo> candidatVoeuxPojo = Array.empty();

    /**
     * The SearchFormationPojo.
     */
    private SearchFormationPojo searchFormationPojo = new SearchFormationPojo();

    private I18NUtilsService i18NUtils;

    private int currentLevel = 1;

    private int codeRICandidat;

    private Campagne campagneEnServ;

    private Set<Commission> initCmi;

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

    /**
     * The list of bean regimeInscription.
     */
    private static List<RegimeInscription> regimeInscriptions = asList((RegimeInscription) new FormationInitiale());
    private static Map<Integer, RegimeInscription> codeRIMap = new HashMap<>();

    private final Logger log = new LoggerImpl(getClass());
    /*
     ******************* CONSTRUCTORS ********************** */


    private CandidaturesController(final DomainService domainService,
                                   final DomainCandidatService domainCandidatService,
                                   final DomainApoService apoService,
                                   final I18nService i18nService,
                                   final ParameterService parameterService,
                                   final LoggedUser loggedUser,
                                   final ManagedCandidatCalendar managedCandidatCalendar) {
        super(domainService, apoService, i18nService, loggedUser);
        this.domainCandidatService = domainCandidatService;
        this.managedCandidatCalendar = managedCandidatCalendar;
    }

    public static CandidaturesController candidaturesController(final DomainService domainService,
                                                                final DomainCandidatService domainCandidatService,
                                                                final DomainApoService apoService,
                                                                final I18nService i18nService,
                                                                final ParameterService parameterService,
                                                                final LoggedUser loggedUser,
                                                                final ManagedCandidatCalendar managedCandidatCalendar) {
        return new CandidaturesController(domainService,
                                            domainCandidatService,
                                            apoService,
                                            i18nService,
                                            parameterService,
                                            loggedUser,
                                            managedCandidatCalendar);
    }


    /*
    ******************* INIT ******************** */


    @Override
    public void initView() {
        super.initView();
        initCandidatVoeuPojo(candidat.getDossier());
        initCodeRICandidat();
    }


    public void initCandidatVoeuPojo(String dossier) {
        Option<CandidatDTO> candidatdto = domainCandidatService.fetchIndById(dossier, Option.<Boolean>none());
        if (candidatdto.isSome()) {
            candidat.setEtatCandidat(fromNull(candidatdto.some().getEtatCandidat())
                    .map(new F<String, EtatIndividu>() {
                        public EtatIndividu f(String s) {
                            return EtatIndividu.fromString(s);
                        }
                    }).toNull());
            RegimeInscription ri = getRegimeInsCandidat().get(codeRICandidat);
            candidat.setRegimeInscription(ri);
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
                        final EtatVoeu etatVoeuPojo = fromString(cdtoVoeu.getState())
                                .map(new F<String, EtatVoeu>() {
                                    public EtatVoeu f(String s) {
                                        return EtatVoeu.fromString(s);
                                    }
                                }).toNull();
                        candidatVoeuxPojo = candidatVoeuxPojo
                                .append(single(CandidatVoeuPojo.empty()
                                        .withId(cdtoVoeu.getId())
                                        .withIndividu(cdtoVoeu.getIndividu())
                                        .withAvis(avisPojo)
                                        .withCodTypeTrait(cdtoVoeu.getCodTypeTrait())
                                        .withEtatVoeu(etatVoeuPojo)
                                        .withHaveBeTraited(cdtoVoeu.isHaveBeTraited())
                                        .withProp(cdtoVoeu.isProp())
                                        .withVrsEtape(vet)
//                                        .withLinkTrtCmiCamp(TransDtoToPojo.linkTrtCmiCampDtoToPojo.f(cdtoVoeu.getLinkTrtCmiCamp()))
                                        .withLinkTrtCmiCamp(cdtoVoeu.getLinkTrtCmiCamp())
                                ))
                                .reverse();
                    }
                }
            });
        }
    }

    /**
     * @return the codeRI of the campagne en service.
     */
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


    @RequestCache
    public Set<Commission> getCmi() {
        Set<Commission> cmi = domainCandidatService.getCommissions(true);
        return cmi;
    }

    /*
    ******************* METHODS ********************** */


    public void deleteCandidatVoeu(CandidatVoeuPojo candidatVoeuPojo) {
        final Individu individu = domainService.getIndividu(candidat.getDossier(), null);
        final CandidatVoeuDTO candidatVoeuDto = TransPojoToDto.candidatVoeuPojoToCandidatVoeuDto.f(candidatVoeuPojo);
        domainCandidatService.deleteCandidatVoeu(individu, candidatVoeuDto);
        initCandidatVoeuPojo(candidat.getDossier()) ;
    }


    /**
     * Add a vows in etapeChoice to the database.
     *
     * @return String
     */
    public void add() {

        Set<CandidatVoeuPojo> candidatVoeuAdd = new HashSet<CandidatVoeuPojo>();

        // on récupère le régime
        RegimeInscription regime = candidat.getRegimeInscription();

        // récupération de la campagne
        Campagne campagne = campagneEnServ;

        final Individu individu = domainCandidatService.getIndividu(candidat.getDossier(), candidat.getEtatCivil().getDateNaissance());

        for (VersionEtapePojo vrsVet : searchFormationPojo.getVrsEtpSelected()) {

            CandidatVoeuPojo cvp = null;
            VersionEtpOpi vEOpi = new VersionEtpOpi(vrsVet.getVersionEtape());
            // cherche un traitementCMI de type vrsVet
            TraitementCmi traitementCmi = domainCandidatService.getTraitementCmi(vEOpi, true);
            TypeTraitement typTrt = null;

            // recupere type traitement par defaut
            Boolean authorizedAddWish = null;

            if (traitementCmi != null) {
                // on récupère le linkTrtCmiCamp
                typTrt = TypeTraitement.fromCode(traitementCmi.getCodTypeTrait());
                String codCge = traitementCmi.getVersionEtpOpi().getCodCge();

                //control VET par CGE.
                authorizedAddWish = controlNbVowByCge(codCge);
            }
            if (authorizedAddWish != null && authorizedAddWish) {

                // création du voeu
                cvp = CandidatVoeuPojo.empty()
                        .withId(0)
                        .withIndividu(individu)
                        .withLinkTrtCmiCamp(domainCandidatService.getLinkTrtCmiCamp(traitementCmi, campagne))
                        .withEtatVoeu(EtatVoeu.EtatNonArrive)
                        .withCodTypeTrait(typTrt.getCode())
                        .withProp(false);
                if (typTrt == AccesSelectif) {
                    cvp.setHaveBeTraited(true);
                } else {
                    cvp.setHaveBeTraited(false);
                }

                domainCandidatService.addCandidatVoeu(TransPojoToDto.candidatVoeuPojoToCandidatVoeuDto.f(cvp));
                VersionEtapeDTO vet = apoService.getVersionEtape(vEOpi.getCodEtp(), vEOpi.getCodVrsVet());
                cvp.setVrsEtape(vet);
                candidatVoeuxPojo = candidatVoeuxPojo.append(Array.single(cvp));
                searchFormationPojo.resetSearch();
            } else {
                    log.error("entering addVoeu( " + cvp + " )");
            }
        }


//        //sendMail
//        sendMailIfAddWishes(indVoeuAdd);
    }

    /**
     * Add VET in objectToadd to vrsEtpSelected.
     * @return callback to recap formation.
     */
    public void addEtapeChoice() {
        if (searchFormationPojo.getObjectToAdd().length > 0) {
            for (Object o : searchFormationPojo.getObjectToAdd()) {
                searchFormationPojo.getVrsEtpSelected().add((VersionEtapePojo) o);
            }
        } else if (searchFormationPojo.getAllChecked()) {
            for (VersionEtapePojo v : searchFormationPojo.getVersionEtapes()) {
                if (v.getCanChoiceVet()) {
                    searchFormationPojo.getVrsEtpSelected().add(v);
                }
            }
        }
    }
    /**
     * control if the candidate does not exceed
     * the number authorized by the wish management center.
     *
     * @param codCge
     * @return Boolean false if exceed the number authorized.
     */
    private Boolean controlNbVowByCge(final String codCge) {
        Integer cpt = 0;
        for (CandidatVoeuPojo cvp : candidatVoeuxPojo) {
            TraitementCmi trtCmi = domainCandidatService.getTraitementCmi(cvp.getLinkTrtCmiCamp().getTraitementCmi().getId());
            if (trtCmi.getVersionEtpOpi().getCodCge().equals(codCge)) {
                cpt++;
            }
            if (cpt >= nbVoeu(codCge)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param codCge
     * @return NB_VOEU_BY_CGE
     */
    private int nbVoeu(final String codCge) {
        List<NombreVoeuCge> listNbVoeuByCGE = domainCandidatService.getAllNombreDeVoeuByCge();
        for (NombreVoeuCge nbVoeuByCGE : listNbVoeuByCGE) {
            if (codCge.equals(nbVoeuByCGE.getCodeCge())) {
                return nbVoeuByCGE.getNbVoeu();
            }
        }
        return Constantes.DEFAULT_NB_VOEU_BY_CGE;
    }


	/*
	 ******************* ACCESSORS ******************** */


    public SearchFormationPojo getSearchFormationPojo() {
        return searchFormationPojo;
    }


    public void setSearchFormationPojo(final SearchFormationPojo searchFormationPojo) {
        this.searchFormationPojo = searchFormationPojo;
    }


    /**
     * @return true if the current ind have to fill forms
     */
    public Map<VersionEtpOpi, FormulaireCmi> getIndSelectedForms() {
        Map<VersionEtpOpi, FormulaireCmi> retour = new HashMap<VersionEtpOpi, FormulaireCmi>();
        // On recupere tout les formulaires Cmi
        Map<VersionEtpOpi, FormulaireCmi> map = domainCandidatService.getFormulaireCmi(codeRICandidat);
        for (CandidatVoeuPojo candidatVoeuPojo : candidatVoeuxPojo) {
            TraitementCmi trtCmi = candidatVoeuPojo.getLinkTrtCmiCamp().getTraitementCmi();
            if (map.containsKey(trtCmi.getVersionEtpOpi())) {
                retour.put(trtCmi.getVersionEtpOpi(), map.get(trtCmi.getVersionEtpOpi()));
            }
        }
        return retour;
    }


    /**
     * @return true si au moins un voeu est favorable
     */
    public Boolean getCanConfirmVoeux() {
        for (CandidatVoeuPojo candidatVoeuPojo : candidatVoeuxPojo) {
            if(candidatVoeuPojo.getAvis() != null && !candidatVoeuPojo.getAvis().isEmpty()) {
                for (AvisPojo a : candidatVoeuPojo.getAvis()) {
                    // Sort the type of avis
                    if (a != null && a.getValidation() && a.getResult().getIsFinal()
                            && a.getResult().getCodeTypeConvocation().equals("IA")) {
                        return true;
                    }
                }
            }
        }
        return false;
    }


    /**
     * The all Ren1GrpTypDip in use.
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
        return gtdList;
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


    public String getVrsDipSelected() {
        if(searchFormationPojo.getVrsDipSelected() != null) {
            return searchFormationPojo.getVrsDipSelected().getCodDip();
        }
        return "";
    }


    public List<CandidatVoeuPojo> getCandidatVoeuxPojoAsList() {
        return asList(candidatVoeuxPojo.array(CandidatVoeuPojo[].class));
    }


    public List<VersionEtapePojo> getVersionEtapesAsList() {
        List<VersionEtapePojo> vrsEtpListToDisplay = new ArrayList<VersionEtapePojo>();
        List<VersionEtapePojo> vrsEtpList = searchFormationPojo.getVersionEtapesAsList();
        for(VersionEtapePojo vep : vrsEtpList) {
            if(vep.getCanChoiceVet()) {
                vrsEtpListToDisplay.add(vep);
            }
        }
        return vrsEtpListToDisplay;
    }

    /**
     * @return Map all RegimeInscription by code.
     */
    public static Map<Integer, RegimeInscription> getRegimeInsCandidat() {
        if (codeRIMap.isEmpty())
            for (RegimeInscription ri : regimeInscriptions)
                codeRIMap.put(ri.getCode(), ri);
        return codeRIMap;
    }


    public String getTypeSelected() {
        if(searchFormationPojo.getGroupTypSelected() != null) {
            return searchFormationPojo.getGroupTypSelected().getCodGrpTpd();
        }
        return "";
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
            List<VersionDiplomeDTO> vdi = new ArrayList<VersionDiplomeDTO>();
            //on retire le diplome qui n'ont pas de VET rattachees e des commissions.
            for (VersionDiplomeDTO v : searchFormationPojo.getVersionDiplomes()) {
                List<VersionEtapeDTO> list = domainCandidatService.getVersionEtapes( v, camp.getCodAnu());

                Map<Commission, Set<VersionEtapeDTO>> mapCmi =
                        Utilitaires.getCmiForVetDTO(getCmi(), new HashSet<VersionEtapeDTO>(list), camp);

                if (!mapCmi.isEmpty()) {
                    vdi.add(v);
                }
            }
            searchFormationPojo.setVersionDiplomes(vdi);
        }
    }

    public void setVrsDipSelected(final String codeType) {
        List<VersionDiplomeDTO> vdis = searchFormationPojo.getVersionDiplomes();
        for (VersionDiplomeDTO vdi : vdis) {
            if(vdi.getCodDip().equals(codeType)) {
                searchFormationPojo.setVrsDipSelected(vdi);
                break;
            }
        }
        selectEtape();
    }


    /**
     * Recupere les versions etapes en fonction de la version Diplome selectionnee.
     */
    public void selectEtape() {
        Campagne camp = domainCandidatService.getCampagneEnServ(codeRICandidat);
        List<VersionEtapeDTO> list = domainCandidatService.getVersionEtapes(
                searchFormationPojo.getVrsDipSelected(), camp.getCodAnu());

        Map<Commission, Set<VersionEtapeDTO>> mapCmi = Utilitaires.getCmiForVetDTO(getCmi(), new HashSet<VersionEtapeDTO>(list), camp);

        searchFormationPojo.setVersionEtapes(
                managedCandidatCalendar.getVrsEtpPojo(mapCmi, candidat.getRegimeInscription(), candidatVoeuxPojo));
    }


    public boolean isCalInsIsOpen() {
        return managedCandidatCalendar.getCalInsIsOpen(candidat.getRegimeInscription());
    }
}
