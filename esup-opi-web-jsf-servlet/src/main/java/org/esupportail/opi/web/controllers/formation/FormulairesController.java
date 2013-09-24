/**
 *
 */
package org.esupportail.opi.web.controllers.formation;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.OrbeonService;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.User;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.utils.PDFUtils;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.opi.web.controllers.references.TrtCmiController;
import org.xml.sax.SAXException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author cleprous
 */
public class FormulairesController extends AbstractAccessController {

    /**
     *
     */
    private static final long serialVersionUID = -4581392769340545286L;

    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

    /**
     * The TrtCmiController.
     */
    private TrtCmiController trtCmiController;

    /**
     *
     */
    private OrbeonService orbeonService;

	/*
     ******************* PROPERTIES ******************* */

    /**
     * The traitementCmi of the form.
     */
    private TraitementCmi traitementCmiSelected;

    /**
     * The ind selected.
     */
    private Individu indSelected;
    /**
     * The ind vow.
     */
    private IndVoeu indVoeuSelected;

	/*
	 ******************* INIT ************************* */

    /**
     * The selected commission.
     */
    private Commission cmiSelected;

    /**
     * The map to get a cmi form depending on the versionEtpOPi.
     */
    private Map<VersionEtpOpi, FormulaireCmi> mapFormulairesVet;

    /**
     * The map to get an ind form depending on the individu.
     */
    private Map<VersionEtpOpi, IndFormulaire> mapIndFormulaires;

    /**
     * la string indiquant le chemin et nom du document
     */
    private String nomDocument;

    /**
     *
     */
    private String cheminFichier;


    /**
     * Constructors.
     */
    public FormulairesController() {
        super();
    }

    /**
     *
     */
    public void download() {

        try {
            //Récupérer le type du fichier
            String suffix = FilenameUtils.getExtension(nomDocument);

            //on lance la lecture puis le téléchargement des fichiers .odt, .txt et .doc dans le premier if
            if (suffix.equalsIgnoreCase("txt") || suffix.equalsIgnoreCase("doc") || suffix.equalsIgnoreCase("odt")) {

                InputStream inputStream = new FileInputStream(cheminFichier + nomDocument);
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                int data;
                while ((data = inputStream.read()) >= 0) {
                    outputStream.write(data);
                }

                PDFUtils.setDownLoadAndSend(outputStream.toByteArray(), FacesContext.getCurrentInstance(),
                        Constantes.HTTP_TYPE_TEXT, nomDocument);
            } else {
                //On effectue la lecture et le téléchargement du fichier pdf si suffix = .pdf
                if (suffix.equalsIgnoreCase("pdf")) {

                    InputStream inputStream = new FileInputStream(cheminFichier + nomDocument);
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

                    int data;
                    while ((data = inputStream.read()) >= 0) {
                        outputStream.write(data);
                    }


                    PDFUtils.setDownLoadAndSend(outputStream.toByteArray(), FacesContext.getCurrentInstance(),
                            Constantes.HTTP_TYPE_PDF, nomDocument);
                } else {
                    //On effectue la lecture et le téléchargement du fichier excel si suffix = .xls
                    if (suffix.equalsIgnoreCase("xls")) {
                        POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(cheminFichier + nomDocument));
                        HSSFWorkbook theWorkBook = new HSSFWorkbook(fs);

                        PDFUtils.setDownLoadAndSend(theWorkBook.getBytes(), FacesContext.getCurrentInstance(),
                                Constantes.HTTP_TYPE_EXCEL, nomDocument);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Erreur ouverture fichier");
        }
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        indSelected = null;
        cmiSelected = null;
        mapFormulairesVet = new HashMap<>();
        mapIndFormulaires = new HashMap<>();
    }

    /**
     * @return the session id
     */
    public String getSessionId() {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        ExternalContext externalContext = facesContext.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        return request.getSession().getId();
    }

    /**
     * @return the redirect string
     */
    public String createFormInBuilder() {
        if (log.isDebugEnabled()) {
            log.debug("--- Creating form in builder ---");
        }
        // récupération du régime d'inscription  du gestionnaire
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        RegimeInscription regime = getRegimeIns().get(gest.getProfile().getCodeRI());

        String formName = traitementCmiSelected.getVersionEtpOpi().getCodEtp() + "-"
                + traitementCmiSelected.getVersionEtpOpi().getCodVrsVet() + "-" + regime.getShortLabel();

        try {
            if (orbeonService.createForm(formName)) {
                FormulaireCmi form = new FormulaireCmi(
                        traitementCmiSelected.getVersionEtpOpi(), regime.getCode());

                mapFormulairesVet.put(traitementCmiSelected.getVersionEtpOpi(), form);

                FormulaireCmi formNorme = trtCmiController.getDomainService().add(
                        form, getCurrentGest().getLogin());
                trtCmiController.getParameterService().addFormulaireCmi(formNorme);

                addInfoMessage(null, "FORMULAIRE.NEW.SUCCESS");
                return "";
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addErrorMessage(null, "FORMULAIRE.NEW.ERROR");
        return "";
    }

    /**
     * @return the redirect string
     */
    public String deleteFormInBuilder() {
        if (log.isDebugEnabled()) {
            log.debug("--- Deleting form in builder ---");
        }
        // récupération du régime d'inscription  du gestionnaire
        Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
        RegimeInscription regime = getRegimeIns().get(gest.getProfile().getCodeRI());

        String code = traitementCmiSelected.getVersionEtpOpi().getCodEtp() + "-"
                + traitementCmiSelected.getVersionEtpOpi().getCodVrsVet() + "-" + regime.getShortLabel();

        try {
            if (orbeonService.deleteForm(code)) {
                FormulaireCmi form = mapFormulairesVet.get(traitementCmiSelected.getVersionEtpOpi());
                trtCmiController.getParameterService().deleteFormulaireCmi(form);
                mapFormulairesVet.remove(traitementCmiSelected.getVersionEtpOpi());

                addInfoMessage(null, "FORMULAIRE.DELETE.SUCCESS");
                return null;
            }
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addErrorMessage(null, "FORMULAIRE.DELETE.ERROR");
        return null;
    }

    /**
     * @return the redirect string
     */
    public String createResponseInRunner() {
        log.info("--- Creating response in runner ---");

        TraitementCmi trtCmi = indVoeuSelected.getLinkTrtCmiCamp().getTraitementCmi();

        RegimeInscription regime = getRegimeIns().get(
                Utilitaires.getCodeRIIndividu(indVoeuSelected.getIndividu(),
                        getDomainService()));

        String formName = trtCmi.getVersionEtpOpi().getCodEtp() + "-"
                + trtCmi.getVersionEtpOpi().getCodVrsVet() + "-" + regime.getShortLabel();

        try {
            if (orbeonService.createResponse(formName, indVoeuSelected.getIndividu().getNumDossierOpi())) {
                // On sauvegarde le form
                IndFormulaire form = new IndFormulaire(
                        trtCmi.getVersionEtpOpi(), indVoeuSelected.getIndividu());

                IndFormulaire formNorme = trtCmiController.getDomainService().add(
                        form, indVoeuSelected.getIndividu().getNumDossierOpi());
                trtCmiController.getParameterService().addIndFormulaire(formNorme);

                mapIndFormulaires.put(trtCmi.getVersionEtpOpi(), formNorme);

                addInfoMessage(null, "FORMULAIRE.NEW.SUCCESS");
                return null;
            }
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        addErrorMessage(null, "FORMULAIRE.NEW.ERROR");
        return null;
    }

    /**
     * @return the map
     */
    public Map<VersionEtpOpi, FormulaireCmi> getFormulairesCmi() {
        if (cmiSelected == null || !cmiSelected.equals(trtCmiController.getCommission())) {
            Integer codeRI = null;
            User u = getSessionController().getCurrentUser();
            if (u instanceof Gestionnaire) {
                Gestionnaire gest = (Gestionnaire) u;
                codeRI = gest.getProfile().getCodeRI();
            } else {
                codeRI = Utilitaires.getCodeRIIndividu(getCurrentInd().getIndividu(),
                        getDomainService());
            }
            if (trtCmiController.getCommission() == null) {
                cmiSelected = new Commission();
            } else {
                cmiSelected = trtCmiController.getCommission();
            }
            mapFormulairesVet = getParameterService().getFormulairesCmi(cmiSelected.getTraitementCmi(),
                    codeRI);
        }
        return mapFormulairesVet;
    }

    /**
     * @return true if the current ind have to fill forms
     */
    public Map<VersionEtpOpi, FormulaireCmi> getIndSelectedForms() {
        Map<VersionEtpOpi, FormulaireCmi> retour = new HashMap<VersionEtpOpi, FormulaireCmi>();
        //can be null when it's the first connect for an individu (in ent)
        if (getCurrentInd() != null) {
            // On recupere tout les formulaires Cmi
            Map<VersionEtpOpi, FormulaireCmi> map = getParameterService().getFormulairesCmi(null,
                    Utilitaires.getCodeRIIndividu(getCurrentInd().getIndividu(),
                            getDomainService()));
            for (IndVoeuPojo indVoeuPojo : getCurrentInd().getIndVoeuxPojo()) {
                TraitementCmi trtCmi = indVoeuPojo.getIndVoeu().getLinkTrtCmiCamp().getTraitementCmi();
                if (map.containsKey(trtCmi.getVersionEtpOpi())) {
                    retour.put(trtCmi.getVersionEtpOpi(), map.get(trtCmi.getVersionEtpOpi()));
                }
            }
        }

        if (log.isDebugEnabled()) {
            log.debug("leaving getInsSelectedForms return  " + retour);
        }

        return retour;
    }

    /**
     * @return the map
     */
    public Map<VersionEtpOpi, IndFormulaire> getIndFormulaires() {
        if (indSelected == null) {
            indSelected = this.getCurrentInd().getIndividu();

            mapIndFormulaires = getParameterService().getIndFormulaires(indSelected);
        }
        return mapIndFormulaires;
    }


    /**
     * @return true if all the forms have been filled
     */
    public boolean isAllFormsFilled() {
        for (FormulaireCmi form : getIndSelectedForms().values()) {
            if (getIndFormulaires().get(form.getVersionEtpOpi()) == null) {
                return false;
            }
        }
        return true;
    }

    /**
     * The form is modifiable only if the calendarIns is closed.
     *
     * @return Boolean true if cIns is open.
     */
    public boolean getModifiable() {
        if (log.isDebugEnabled()) {
            log.debug("entering getModifiable");
        }

        List<CalendarIns> calIns = getParameterService().getCalendarIns(trtCmiController.getCommission());
        if (calIns != null && !trtCmiController.getCurrentRegime().getDisplayInfoFC()) {
            for (CalendarIns cal : calIns) {
                // On permet la modification du formulaire que si
                // le calendrier d'inscription est ferme ou s'il n'est pas encore positionne.
                if (Utilitaires.calIsOpen(cal)) {
                    return false;
                }
            }
        }
        //cas de l'ajout ou cas pas de calendrier
        return true;
    }


    /**
     * @param indFormulaire
     * @param regime
     * @return the tab of bytes corresponding to the pdf
     * @throws IOException
     */
    public byte[] getPdf(final IndFormulaire indFormulaire, final RegimeInscription regime) throws IOException {
        return orbeonService
                .getPdf(getParameterService().findIndFormulaireById(indFormulaire.getId()), regime.getShortLabel());
    }

    /**
     * @return the public URL for OPI
     */
    public String getOrbeonOpiUrl() {
        return orbeonService.getOrbeonOpiUrl();
    }

    /**
     * @return the public URL for OPI
     */
    public String getOrbeonBuilderUrl() {
        return orbeonService.getOrbeonBuilderUrl();
    }


    /**
     * @return the trtCmiController
     */
    public TrtCmiController getTrtCmiController() {
        return trtCmiController;
    }

    /**
     * @param trtCmiController the trtCmiController to set
     */
    public void setTrtCmiController(final TrtCmiController trtCmiController) {
        this.trtCmiController = trtCmiController;
    }

    /**
     * @return the traitementCmiSelected
     */
    public TraitementCmi getTraitementCmiSelected() {
        return traitementCmiSelected;
    }

    /**
     * @param traitementCmiSelected the traitementCmiSelected to set
     */
    public void setTraitementCmiSelected(final TraitementCmi traitementCmiSelected) {
        this.traitementCmiSelected = traitementCmiSelected;
    }

    /**
     * @return the indSelected
     */
    public Individu getIndSelected() {
        return indSelected;
    }

    /**
     * @param indSelected the indSelected to set
     */
    public void setIndSelected(final Individu indSelected) {
        this.indSelected = indSelected;
    }

    /**
     * @return the indVoeuSelected
     */
    public IndVoeu getIndVoeuSelected() {
        return indVoeuSelected;
    }

    /**
     * @param indVoeuSelected the indVoeuSelected to set
     */
    public void setIndVoeuSelected(final IndVoeu indVoeuSelected) {
        this.indVoeuSelected = indVoeuSelected;
    }

    /**
     * @return the orbeonService
     */
    public OrbeonService getOrbeonService() {
        return orbeonService;
    }

    /**
     * @param orbeonService the orbeonService to set
     */
    public void setOrbeonService(final OrbeonService orbeonService) {
        this.orbeonService = orbeonService;
    }

    /**
     * @return String
     */
    public String getNomDocument() {
        return nomDocument;
    }

    /**
     * @param nomDocument
     */
    public void setNomDocument(String nomDocument) {
        this.nomDocument = nomDocument;
    }

    /**
     * @return le chemin ou l'on doit récupérer les fichiers attachés à une pièce justificative
     */
    public String getCheminFichier() {
        return cheminFichier;
    }

    /**
     * @param cheminFichier
     */
    public void setCheminFichier(String cheminFichier) {
        this.cheminFichier = cheminFichier;
    }


}
