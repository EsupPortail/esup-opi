/**
 *
 */
package org.esupportail.opi.web.controllers.candidatures;

import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.utils.StringUtils;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.opi.web.controllers.opinions.PrintOpinionController;
import org.esupportail.opi.web.controllers.references.CommissionController;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import javax.faces.event.ValueChangeEvent;
import java.util.*;

/**
 * @author cleprous
 */
public class MonitorCandidaturesController extends AbstractAccessController {


    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -9220116138870526021L;

	/*
     ******************* PROPERTIES ******************* */
    /**
     * the id of treatment commission.
     * Default value = 0.
     */
    private Integer idTrtCmi;

    /**
     * The list of individuals.
     */
    private List<IndividuPojo> individus;

    /**
     * The state of student's wishes.
     */
    private String stateSelected;

    /**
     * see {@link CommissionController}.
     */
    private CommissionController commissionController;

    /**
     * see {@link PrintOpinionController}.
     */
    private PrintOpinionController printOpinionController;
	
	/*
	 ******************* INIT ************************* */


    /**
     * Constructors.
     */
    public MonitorCandidaturesController() {
        super();
    }


    /**
     * @see org.esupportail.opi.web.controllers.AbstractAccessController#afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        Assert.notNull(this.commissionController, "property commissionController of class "
                + this.getClass().getName() + " can not be null");
        Assert.notNull(this.printOpinionController, "property printOpinionController of class "
                + this.getClass().getName() + " can not be null");
        super.afterPropertiesSetInternal();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        commissionController.reset();
        printOpinionController.reset();
        idTrtCmi = 0;
        individus = new ArrayList<IndividuPojo>();
//		stateSelected = null;
        stateSelected = "STATE.DESIST";
        super.reset();
    }


    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return super.toString();
    }
	
	/*
	 ******************* CALLBACK ********************** */


    /**
     * Callback to monitor candidatures page.
     */
    public String goMonitorCand() {
        reset();
        return NavigationRulesConst.MONITOR_CANDIDATURES;
    }
	
	/*
	 ******************* METHODS ********************** */


    /**
     * @param event
     */
    public void changeCommission(final ValueChangeEvent event) {
        commissionController.reset();
        commissionController.getCommission().setId((Integer) event.getNewValue());
        changeCommission();
    }

    /**
     * Intialize the list BeanTrtCmi of the cmi selected.
     */
    public void changeCommission() {
        commissionController.selectCommissionForLists();
        if (commissionController.getCommission() != null
                && commissionController.getCommission().getId() != null
                && commissionController.getCommission().getId() != 0) {
            //charge la liste des etapes
            commissionController.initAllTraitementCmi(
                    commissionController.getCommission());
        }

    }


    /**
     * @param event
     */
    public void changeTrtCmi(final ValueChangeEvent event) {
        idTrtCmi = (Integer) event.getNewValue();
        changeCommission();
    }

    /**
     * Make the list student for the idTrtCmi selected.
     */
    public void makeListStudent() {
        if (idTrtCmi != 0) {
            TraitementCmi t = getParameterService().getTraitementCmi(idTrtCmi);
            //listes des individus avec un etat confirme ou desiste donc forcement valide
            List<Individu> l = getDomainService().getIndividusTrtCmiState(t, stateSelected);
            // on filtre la listeInd selon le choix dans listeRI
            List<Individu> filteredListeInd = new ArrayList<Individu>();
            for (Individu ind : l) {
                if (commissionController.getListeRI().contains(getRegimeIns()
                        .get(Utilitaires.getCodeRIIndividu(ind,
                                getDomainService())))) {
                    filteredListeInd.add(ind);
                }
            }

            individus.clear();
            Set<Campagne> camps = new HashSet<Campagne>();
            for (RegimeInscription reg : commissionController.getListeRI()) {
                camps.addAll(getParameterService().getCampagnes(true,
                        String.valueOf(reg.getCode())));
            }
            Set<VersionEtapeDTO> v = new HashSet<VersionEtapeDTO>();
            for (Campagne camp : camps) {
                v.add(getBusinessCacheService().getVetDTO(
                        t.getVersionEtpOpi().getCodEtp(),
                        t.getVersionEtpOpi().getCodVrsVet(),
                        camp.getCodAnu()));
            }
            individus = Utilitaires.convertIndInIndPojo(filteredListeInd, getParameterService(),
                    getI18nService(), getDomainApoService(), null, null,
                    getParameterService().getTypeTraitements(),
                    getParameterService().getCalendarRdv(), v, false);
            Collections.sort(individus, new ComparatorString(IndividuPojo.class));
        }
    }

    /**
     * The list individus size.
     *
     * @return int
     */
    public int getSizeIndividus() {
        return individus.size();
    }

    public void exportList() {
        String state = getString(stateSelected);
        printOpinionController.csvGeneration(individus, "export_"
                + StringUtils.sansAccent(state) + "_ForCmi_"
                + commissionController.getCommission().getCode() + ".csv");
        reset();
    }
	
	/*
	 ******************* ACCESSORS ******************** */

    /**
     * @param commissionController the commissionController to set
     */
    public void setCommissionController(final CommissionController commissionController) {
        this.commissionController = commissionController;
    }


    /**
     * @return the idtrtCmi
     */
    public Integer getIdTrtCmi() {
        return idTrtCmi;
    }


    /**
     * @param idtrtCmi the idtrtCmi to set
     */
    public void setIdTrtCmi(final Integer idtrtCmi) {
        this.idTrtCmi = idtrtCmi;
    }


    /**
     * @param printOpinionController the printOpinionController to set
     */
    public void setPrintOpinionController(
            final PrintOpinionController printOpinionController) {
        this.printOpinionController = printOpinionController;
    }


    /**
     * @return the individus
     */
    public List<IndividuPojo> getIndividus() {
        return individus;
    }


    /**
     * @param individus the individus to set
     */
    public void setIndividus(final List<IndividuPojo> individus) {
        this.individus = individus;
    }


    /**
     * @return the stateSelected
     */
    public String getStateSelected() {
        return stateSelected;
    }


    /**
     * @param stateSelected the stateSelected to set
     */
    public void setStateSelected(final String stateSelected) {
        this.stateSelected = stateSelected;
    }


}
