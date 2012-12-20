/**
 *
 */
package org.esupportail.opi.web.controllers.opinions;


import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.PilotageService;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.utils.ExportUtils;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.PDFUtils;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.wssi.services.remote.Departement;

import javax.faces.context.FacesContext;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipOutputStream;


/**
 * @author cgomez
 */
public class ExportFormOrbeonController extends AbstractContextAwareController {
    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -3376489489251017643L;

	
	/*
	 ******************* PROPERTIES ******************* */
    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());
    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum = new ActionEnum();
    /**
     * list des départements.
     */
    private List<Departement> listDep;
    /**
     * liste des champs selectionnes.
     */
    private List<String> champsChoisis = new ArrayList<String>();
    /**
     * see {@link PilotageService}.
     */
    private PilotageService pilotageService;
	
	/*
	 ******************* INIT ************************* */

    /**
     * Constructors.
     */
    public ExportFormOrbeonController() {
        super();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        if (log.isDebugEnabled()) {
            log.debug("ExportFormOrbeonController reset()");
        }
        listDep = getDomainApoService().getDepartements();
        actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
        champsChoisis.clear();
        champsChoisis.addAll(PilotageService.LIB_BASE.keySet());
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        Assert.notNull(this.pilotageService,
                "property pilotageService of class " + this.getClass().getName()
                        + " can not be null");
        reset();
    }
	
	
	/*
	 ******************* CALLBACK ********************** */

    /**
     * Callback for the print of the lists form.
     *
     * @return String
     */
    public String goPrintListsForm() {
        reset();
        return NavigationRulesConst.DISPLAY_PRINT_LISTS_FORM;
    }
	
	/*
	 ******************* METHODS PRIVATE ********************** */
	
	
	
	/*
	 ******************* METHODS ********************** */

    /**
     * @return the champsDispo
     */
    public List<String> getChampsDispos() {
        return new ArrayList<String>(PilotageService.LIB_BASE.keySet());
    }

    public void makeCsvFormulaire(final Commission commission, final List<RegimeInscription> listeRI) {
        ByteArrayOutputStream zipByteArray = new ByteArrayOutputStream();
        ZipOutputStream zipStream = new ZipOutputStream(zipByteArray);
        // generate the csv if exist
        if (commission != null) {
            List<LinkTrtCmiCamp> listLink = new ArrayList<LinkTrtCmiCamp>();
            Set<TraitementCmi> setTaitementmCmi = commission.getTraitementCmi();
            for (TraitementCmi trtCmi : setTaitementmCmi) {
                Set<LinkTrtCmiCamp> setLinksTrtCmiCamp = trtCmi.getLinkTrtCmiCamp();
                for (LinkTrtCmiCamp link : setLinksTrtCmiCamp) {
                    if (link.getTemoinEnService()) {
                        for (RegimeInscription ri : listeRI) {
                            if (link.getCampagne().getCodeRI() == ri.getCode()) {
                                listLink.add(link);
                                break;
                            }
                        }
                    }
                }
            }

            for (LinkTrtCmiCamp link : listLink) {
                VersionEtpOpi versionEtape = link.getTraitementCmi().getVersionEtpOpi();
                String fileNameCsv = "exportFormulaire_" + versionEtape.getCodEtp()
                        + "-" + versionEtape.getCodVrsVet() + ".csv";
                Map<Integer, List<String>> map = pilotageService.prepareCsvFormulaire(
                        versionEtape,
                        getRegimeIns().get(
                                link.getCampagne().getCodeRI()).getShortLabel(),
                        champsChoisis);

                if (map == null) {
                    break;
                }

                if (listLink.size() > 1) {
                    // zip file
                    ExportUtils.prepareCSVinZip(
                            zipStream,
                            fileNameCsv,
                            map);
                } else {
                    // one csv
                    ExportUtils.csvGenerate(map, fileNameCsv);
                }
            }
            if (listLink.size() > 1) {
                try {
                    zipStream.close();
                } catch (IOException e) {
                    log.error("probleme lors du zipStream.close()");
                }
                PDFUtils.setDownLoadAndSend(
                        zipByteArray.toByteArray(),
                        FacesContext.getCurrentInstance(),
                        Constantes.HTTP_TYPE_ZIP, "exportFormulaire_" + commission.getCode() + ".zip");
            }
        }
    }
	
	
	/*
	 ******************* ACCESSORS ******************** */

    /**
     * @return actionEnum
     */
    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    /**
     * @param actionEnum
     */
    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    /**
     * @return Map
     */
    public Map<String, String> getLibBase() {
        return PilotageService.LIB_BASE;
    }

    /**
     * @return listDep
     */
    public List<Departement> getListDep() {
        return listDep;
    }

    /**
     * @param listDep
     */
    public void setListDep(final List<Departement> listDep) {
        this.listDep = listDep;
    }

    /**
     * @return the champsChoisis
     */
    public List<String> getChampsChoisis() {
        return champsChoisis;
    }

    /**
     * @param champsChoisis the champsChoisis to set
     */
    public void setChampsChoisis(final List<String> champsChoisis) {
        this.champsChoisis = champsChoisis;
    }

    /**
     * @param pilotageService the pilotageService to set
     */
    public void setPilotageService(final PilotageService pilotageService) {
        this.pilotageService = pilotageService;
    }
}
