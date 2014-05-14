/**
 *
 */
package org.esupportail.opi.web.controllers.references;


import fj.data.Option;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.web.beans.BeanTrtCmi;
import org.esupportail.opi.web.beans.beanEnum.WayfEnum;
import org.esupportail.opi.web.beans.paginator.VETPaginator;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.opi.web.controllers.parameters.NomenclatureController;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.jsf.FacesContextUtils;

import javax.faces.context.FacesContext;
import java.util.*;

public class EtapeController extends AbstractContextAwareController {
    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -385061645426193790L;

    /**
     * The etape code.
     */
    private String codEtp;

    /**
     * The centre gestion code.
     */
    private String codCge;

    /**
     * The code de l'année universitaire.
     */
    private String codAnu;

    /**
     * The version Etape label.
     */
    private String libWebVet;

    /**
     * The version etape.
     */
    private List<VersionEtapeDTO> etapes;

    /**
     * The manager of the versionEtape to add to the cmi.
     */
    private Object[] objectToAdd;

    /**
     * Liste des campagnes en service.
     */
    private List<Campagne> campagnes;

    /**
     * Has true if all VersionEtape in etapes is selected.
     * Default value = false
     */
    private Boolean allChecked;

    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

    /**
     * where you are from.
     */
    private WayfEnum wayfEnum;

    private VETPaginator paginator;

    public EtapeController() {
        super();
    }

    @Override
    public void reset() {
        super.reset();
        codEtp = null;
        libWebVet = null;
        codCge = null;
        etapes = new ArrayList<VersionEtapeDTO>();
        allChecked = false;
        codAnu = null;
        campagnes = new ArrayList<Campagne>();
        objectToAdd = new Object[0];
        this.wayfEnum = new WayfEnum();
    }

    @Override
    public void afterPropertiesSetInternal() {
        reset();
    }
	
    public void forcereload(){
    	paginator.forceReload();
    }

    /**
     * Callback to search version etape.
     *
     * @return String
     */
    public String goSearchEtpForCmi() {
        reset();
        codCge = getCurrentGest().getCodeCge();
        // on initialise la liste de campagne
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        int codeRI = gest.getProfile().getCodeRI();
        campagnes.addAll(getParameterService().getCampagnes(null,
                String.valueOf(codeRI)));
        codAnu = getParameterService().getCampagneEnServ(codeRI).getCodAnu();
        // define from where we go to search Vet
        this.wayfEnum.setWhereAreYouFrom(this.wayfEnum.getMemberCmiValue());
        return NavigationRulesConst.SEARCH_VET;
    }

    /**
     * Callback to search version etape to add PJ.
     *
     * @return String
     */
    public String goSearchVetForGestPJ() {
        reset();
        WebApplicationContext wac = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        NomenclatureController nc = (NomenclatureController) wac.getBean("nomenclatureController");
        nc.reset();
        codCge = getCurrentGest().getCodeCge();
        // on initialise la liste de campagne
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        paginator.getRvd().setCodCge(getCurrentGest().getCodeCge());
        // on initialise la liste de campagne
        int codeRI = gest.getProfile().getCodeRI();
        if (paginator.getRvd().getCampagnes() != null)
            paginator.getRvd().getCampagnes().addAll(getParameterService().getCampagnes(null,
                    String.valueOf(codeRI)));
        paginator.getRvd().setCodAnu(getParameterService().getCampagneEnServ(codeRI).getCodAnu());
        paginator.getRvd().setIdCmi(null);
        paginator.getRvd().setCodeVet("");
        paginator.getRvd().setLibWebVet("");

        return NavigationRulesConst.SEARCH_VET_GEST_PJ;
    }

    /**
     * @return the list of PJs for the VET
     */
    public String goBackVET() {
        reset();
        WebApplicationContext wac = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        NomenclatureController nc = (NomenclatureController) wac.getBean("nomenclatureController");
        nc.resetSpecial();
        nc.getPiecesJToNomenclaturePojo();
        return NavigationRulesConst.ENTER_VET;
    }

    /**
     * the list of PJs for the VET with a VET
     *
     * @return a String
     */
    public String goEnterVET() {
        reset();
        WebApplicationContext wac = FacesContextUtils.getWebApplicationContext(FacesContext.getCurrentInstance());
        NomenclatureController nc = (NomenclatureController) wac.getBean("nomenclatureController");
        nc.getPiecesJToNomenclaturePojo();
        return NavigationRulesConst.ENTER_VET;
    }


    /**
     * Go back to form add PJ.
     *
     * @return String
     */
    public String goBackSearchEtape() {
        String callback = null;
        if (wayfEnum.getWhereAreYouFrom().equals(WayfEnum.PJ_VALUE)) {
            callback = NavigationRulesConst.ENTER_PJ;
        } else if (wayfEnum.getWhereAreYouFrom().equals(WayfEnum.AFFECT_PJ_VALUE)) {
            callback = NavigationRulesConst.AFFECT_PJ;
        } else if (wayfEnum.getWhereAreYouFrom().equals(WayfEnum.MEMBER_CMI_VALUE)) {
            callback = NavigationRulesConst.MANAGED_TRT_CMI;
        } else if (wayfEnum.getWhereAreYouFrom().equals(WayfEnum.EMPTY_VALUE)) {
            callback = NavigationRulesConst.DISPLAY_FONCTION;
        }
        reset();
        return callback;
    }

    public void searchEtape() {
        if (StringUtils.hasText(codCge) && StringUtils.hasLength(codAnu)) {
            etapes = getDomainApoService().getVersionEtapes(codEtp, libWebVet, codCge, codAnu);
            Collections.sort(etapes, new ComparatorString(VersionEtapeDTO.class));
        } else {
            if (!StringUtils.hasText(codCge)) {
                addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.CGE"));
            }
            if (!StringUtils.hasText(codAnu)) {
                addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.CAMPAGNES"));
            }
        }

    }

    public Set<Commission> getCommissionsItemsByRight() {
        Set<Commission> cmi = new TreeSet<Commission>(new ComparatorString(NormeSI.class));

        cmi.addAll(getDomainApoService().getListCommissionsByRight(
                getCurrentGest(),
                true));
        return cmi;

    }

    public List<CentreGestion> getCGEItemsByRight() {
        List<CentreGestion> cge = new ArrayList<CentreGestion>();

        cge.addAll(Utilitaires.getListCgeByRight(getCurrentGest(), getDomainApoService()));
        Collections.sort(cge, new ComparatorString(CentreGestion.class));

        return cge;
    }

    public List<BeanTrtCmi> searchEtapeInCmi() {
        Set<Commission> c = getDomainApoService().getListCommissionsByRight(
                getCurrentGest(), true);
        // on récupère les campagnes en service
        Set<Campagne> camps = getParameterService().getCampagnes(true,
                String.valueOf(getCurrentGest().getProfile().getCodeRI()));
        //on recupere la vetDTO
        etapes.clear();
        for (Campagne camp : camps) {
            etapes.addAll(getDomainApoService().getVersionEtapes(codEtp, libWebVet, null,
                    camp.getCodAnu()));
        }
        List<BeanTrtCmi> listTrt = new ArrayList<BeanTrtCmi>();
        for (Commission cmi : c) {
            for (TraitementCmi t : cmi.getTraitementCmi()) {
                for (VersionEtapeDTO vDTO : etapes) {
                    if (t.getVersionEtpOpi().getCodEtp().equals(vDTO.getCodEtp())
                            && t.getVersionEtpOpi().getCodVrsVet()
                            .equals(vDTO.getCodVrsVet())) {
                        //init proxy hib
                        t.setCommission(cmi);
                        BeanTrtCmi b = new BeanTrtCmi(t, Option.some(TypeTraitement.fromCode(t.getCodTypeTrait())));
                        b.setEtape(vDTO);
                        listTrt.add(b);
                        break;
                    }
                }

            }
        }
        reset();
        if (log.isDebugEnabled()) {
            log.debug("leaving searchEtapeInCmi with listTrt = " + listTrt);
        }
        return listTrt;

    }
    
    public void lookForVets() {
    	paginator.lookForVets();
        etapes = paginator.getData();
    }

    private boolean isVuPjEtp() {
        return wayfEnum.getWhereAreYouFrom().equals(wayfEnum.getPJValue())
                || wayfEnum.getWhereAreYouFrom().equals(wayfEnum.getAffectPJValue());
    }

    public boolean isRightOnCge() {
        return isVuPjEtp()
                && StringUtils.hasText(getCurrentGest().getCodeCge())
                && !getSessionController().isAllViewPJ();
    }

    public boolean isRightOneEtp() {
        return isVuPjEtp()
                && getCurrentGest().getRightOnCmi() != null
                && !getCurrentGest().getRightOnCmi().isEmpty()
                && !getSessionController().isAllViewPJ();
    }

    public List<CentreGestion> getCentreGestion() {
        if (isRightOnCge()) {
            List<CentreGestion> listCge = new ArrayList<CentreGestion>();
            for (CentreGestion cge : getDomainApoService().getCentreGestion()) {
                if (cge.getCodCge().equals(getCurrentGest().getCodeCge())) {
                    listCge.add(cge);
                    return listCge;
                }
            }
        }
        return getDomainApoService().getCentreGestion();
    }

    public int getCodeRI() {
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        return gest.getProfile().getCodeRI();

    }

    public String getCodEtp() {
        return codEtp;
    }

    public void setCodEtp(final String codEtp) {
        this.codEtp = codEtp;
    }

    public String getCodCge() {
        return codCge;
    }

    public void setCodCge(final String codCge) {
        this.codCge = codCge;
    }

    public List<VersionEtapeDTO> getEtapes() {
        return etapes;
    }

    public void setEtapes(final List<VersionEtapeDTO> etapes) {
        this.etapes = etapes;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(final Boolean allChecked) {
        this.allChecked = allChecked;
    }	

    public WayfEnum getWayfEnum() {
        return wayfEnum;
    }

    public void setWayfEnum(final WayfEnum wayfEnum) {
        this.wayfEnum = wayfEnum;
    }

    public String getLibWebVet() {
        return libWebVet;
    }

    public void setLibWebVet(final String libWebVet) {
        this.libWebVet = libWebVet;
    }

    public Object[] getObjectToAdd() {
        return objectToAdd;
    }

    public void setObjectToAdd(final Object[] objectToAdd) {
        this.objectToAdd = objectToAdd;
    }

    public String getCodAnu() {
        return codAnu;
    }

    public void setCodAnu(final String codAnu) {
        this.codAnu = codAnu;
    }

    public List<Campagne> getCampagnes() {
        return campagnes;
    }

    public void setCampagnes(final List<Campagne> campagnes) {
        this.campagnes = campagnes;
    }

    public VETPaginator getPaginator() {
        return paginator;
    }

    public void setPaginator(VETPaginator paginator) {
        this.paginator = paginator;
    }

	public List<VersionEtapeDTO> getAllEtapesItems() {
		return etapes;
	}

	public void setAllEtapes(final List<VersionEtapeDTO> etapes) {
		this.etapes = etapes;
	}
	
}
