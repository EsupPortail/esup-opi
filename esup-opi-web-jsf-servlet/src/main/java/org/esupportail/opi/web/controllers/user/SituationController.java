/**
 *
 */
package org.esupportail.opi.web.controllers.user;

import org.esupportail.opi.domain.beans.parameters.situation.TypeContrat;
import org.esupportail.opi.domain.beans.parameters.situation.TypeOrganisme;
import org.esupportail.opi.domain.beans.parameters.situation.TypeSituation;
import org.esupportail.opi.domain.beans.parameters.situation.TypeStatut;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.AdresseEmployeur;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.situation.*;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.springframework.util.StringUtils;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.List;

import static org.esupportail.opi.domain.beans.etat.EtatIndividu.EtatComplet;
import static org.esupportail.opi.domain.beans.etat.EtatNonRenseigne.EtatNonRenseigne;


/**
 * @author ylecuyer
 */
public class SituationController extends AbstractAccessController {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = 8039202959295823479L;

    /**
     * A logger.
     */
//	private final Logger log = new LoggerImpl(getClass());

	/*
	 ******************* PROPERTIES ******************* */

    /**
     * The indSituation.
     */
    private IndSituation indSituation;

    /**
     * The current user.
     */
    private IndividuPojo individuPojo;

    /**
     * The codeTypeSituation.
     */
    private String codeTypeSituation;

    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum;

    /**
     * see {@link AdressController}.
     */
    private AdressController adressController;
	
	/*
	 ******************* INIT ************************* */


    /**
     * Constructors.
     */
    public SituationController() {
        super();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        actionEnum = new ActionEnum();
        indSituation = null;
        codeTypeSituation = null;
        adressController.reset();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();

    }

	/*
	 ******************* CALLBACK ********************** */

    /**
     * Callback to add ind bac.
     *
     * @return String
     */
    public String goAddIndBac() {
        if (ctrlEnter()) {
            addTheCurrentRoad(NavigationRulesConst.ADD_IND_BAC,
                    getString("INDIVIDU.BAC"), getString("INDIVIDU.BAC"));
            return NavigationRulesConst.ADD_IND_BAC;
        }
        return null;
    }

    /**
     * Callback to see situation details for the current connected user.
     *
     * @return String
     */
    public String goSeeSituation() {
        reset();
        individuPojo = getCurrentInd();
        cancelSituation();
        return NavigationRulesConst.SEE_SITUATION;
    }

    /**
     * Callback to update situation details for the current connected user.
     *
     * @return String
     */
    public String goUpdateSituation() {
        actionEnum.setWhatAction(ActionEnum.UPDATE_ACTION);
        return null;
    }

    /**
     * Cancel the situation.
     */
    public void cancelSituation() {
        indSituation = getDomainService().getIndSituation(individuPojo.getIndividu());
        if (indSituation != null) {
            codeTypeSituation = indSituation.getType();
            if (codeTypeSituation.equals(getTypeSal())) {
                adressController.init(((SituationSalarie) indSituation).getAdresseEmployeur(), true);
            }
            actionEnum.setWhatAction(ActionEnum.READ_ACTION);
        }
    }
	
	/*
	 ******************* METHODS ********************** */

    /**
     * @return false if it is not a new ind when create a dossier
     *         true otherwise
     */
    public boolean getDisplayCreateDossier() {
        IndividuPojo indPojo = getCurrentInd();
        if (indPojo == null) {
            return false;
        }
        return true;
    }

    /**
     * State of IndBac.
     *
     * @return String
     */
    public String getEtatSituation() {
        if (indSituation != null) {
            return getI18nService().getString(EtatComplet.getCodeLabel());
        }
        return getI18nService().getString(EtatNonRenseigne.getCodeLabel());
    }

    /**
     * Return the result items.
     *
     * @return List< SelectItem>
     */
    public List<SelectItem> getResultItems() {
        List<SelectItem> s = new ArrayList<SelectItem>();
        s.add(new SelectItem(Constantes.FLAG_YES, getString("_.BUTTON.YES")));
        s.add(new SelectItem(Constantes.FLAG_NO, getString("_.BUTTON.NO")));
        s.add(new SelectItem(getString("FIELD_LABEL.IN_PROGRESS"), getString("FIELD_LABEL.IN_PROGRESS")));
        return s;
    }

    /**
     * Method called when valid the choice of the situation.
     * Init the type of indSituation
     */
    public void selectTypeSituation() {
        if (codeTypeSituation != null) {
            if (codeTypeSituation.equals(getTypeSal())) {
                indSituation = new SituationSalarie();
                adressController.init(new AdresseEmployeur(), false);
            } else if (codeTypeSituation.equals(getTypeProLib())) {
                indSituation = new SituationProLib();
            } else if (codeTypeSituation.equals(getTypeDemEmp())) {
                indSituation = new SituationDE();
            } else if (codeTypeSituation.equals(getTypeEtu())) {
                indSituation = new SituationEtu();
            } else if (codeTypeSituation.equals(getTypeOther())) {
                indSituation = new SituationAutre();
            }
            actionEnum.setWhatAction(ActionEnum.ADD_ACTION);
        }
    }

    /**
     * Save the situation.
     */
    public void add() {
        // on sauvegarde la situation de l'individu
        if (ctrlEnter()) {
            add(getCurrentInd().getIndividu());
            addInfoMessage(null, "INFO.ENTER.SUCCESS");
            actionEnum.setWhatAction(ActionEnum.READ_ACTION);
        }
    }

    public void add(final Individu individu) {
        // si le candidat avait une situation, on la supprime
        if (indSituation != null) {
            IndSituation indSitToDel = getDomainService()
                    .getIndSituation(individu);
            if (indSitToDel != null) {
                getDomainService().deleteIndSituation(indSitToDel);
            }
            // sauvegarde de l'adresse si type salarié
            if (indSituation instanceof SituationSalarie) {
                adressController.addAdrEmployeur();
                ((SituationSalarie) indSituation).setAdresseEmployeur(
                        (AdresseEmployeur) adressController.getEmplAdrPojo().getAdresse());
                adressController.init(((SituationSalarie) indSituation).getAdresseEmployeur(), true);
            }
            // ajout du lien avec l'individu
            indSituation.setIndividu(individu);
            indSituation = (IndSituation) getDomainService().add(indSituation,
                    Utilitaires.codUserThatIsAction(getCurrentGest(), getCurrentInd()));
            getDomainService().addIndSituation(indSituation);
        }
    }

    /**
     * Update the situation.
     */
    public void update() {
        // on sauvegarde la situation de l'individu
        if (ctrlEnter()) {
            // sauvegarde de l'adresse si type salarié
            if (indSituation instanceof SituationSalarie) {
                adressController.update(adressController.getEmplAdrPojo());
                adressController.init(((SituationSalarie) indSituation).getAdresseEmployeur(), true);
            }
            // ajout du lien avec l'individu
            indSituation = (IndSituation) getDomainService()
                    .update(indSituation, getCurrentGest().getLogin());
            getDomainService().updateIndSituation(indSituation);

            addInfoMessage(null, "INFO.ENTER.SUCCESS");
            actionEnum.setWhatAction(ActionEnum.READ_ACTION);
        }
    }


	/* ### ALL CONTROL ####*/

    /**
     * Control the pojoIndividu attributes.
     *
     * @return Boolean
     */
    private Boolean ctrlEnter() {
        Boolean ctrlOk = true;

        if (indSituation == null) {
            addErrorMessage(null, "SITUATION.NOT_NULL");
            ctrlOk = false;
        } else if (indSituation instanceof SituationSalarie) {
            SituationSalarie indSal = (SituationSalarie) indSituation;
            Adresse adresse = adressController.getEmplAdrPojo().getAdresse();
            if (!StringUtils.hasText(indSal.getCodeTypeContrat())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.SALARIE.TYPE_CONTRAT"));
                ctrlOk = false;
            }
            if (!StringUtils.hasText(indSal.getCodeTypeStatut())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.SALARIE.TYPE_STATUT"));
                ctrlOk = false;
            }
            if (!StringUtils.hasText(indSal.getRaisonSociale())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.SALARIE.EMPLOYEUR.RAISON_SOC"));
                ctrlOk = false;
            }
            if (!StringUtils.hasText(indSal.getCodeTypeOrga())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.SALARIE.EMPLOYEUR.TYPE_ORGA"));
                ctrlOk = false;
            }
            if (!StringUtils.hasText(indSal.getSecteurActivity())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.SALARIE.EMPLOYEUR.SECTEUR_ACT"));
                ctrlOk = false;
            }
            if (!StringUtils.hasText(adresse.getCodPays())) {
                addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.PAY"));
                ctrlOk = false;
            }
            if (!StringUtils.hasText(adresse.getAdr1())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.SALARIE.EMPLOYEUR.ADRESSE"));
                ctrlOk = false;
            }
            if (ctrlOk && adresse.getCodPays().equals(Constantes.CODEFRANCE)) {
                if (!StringUtils.hasText(adresse.getCodBdi())) {
                    addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.COD_POST"));
                    ctrlOk = false;
                }
                if (!StringUtils.hasText(adresse.getCodCommune())) {
                    addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.VIL"));
                    ctrlOk = false;
                }
            } else if (ctrlOk && !adresse.getCodPays().equals(Constantes.CODEFRANCE)) {
                if (!StringUtils.hasText(adresse.getLibComEtr())) {
                    addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.VIL.ETR"));
                    ctrlOk = false;
                }
            }
            if (StringUtils.hasText(adresse.getMail())
                    && !Utilitaires.isFormatEmailValid(adresse.getMail())) {
                addErrorMessage(null, "ERROR.FIELD.EMAIL");
                ctrlOk = false;
            }
        } else if (indSituation instanceof SituationProLib) {
            SituationProLib indSal = (SituationProLib) indSituation;
            if (!StringUtils.hasText(indSal.getExactStatut())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.PRO_LIB.EXACT_STATUT"));
                ctrlOk = false;
            }
            if (!StringUtils.hasText(indSal.getActivity())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.PRO_LIB.ACTIVITY"));
                ctrlOk = false;
            }
        } else if (indSituation instanceof SituationAutre) {
            SituationAutre indSal = (SituationAutre) indSituation;
            if (!StringUtils.hasText(indSal.getActualSituation())) {
                addErrorMessage(null, Constantes.I18N_EMPTY,
                        getString("SITUATION.OTHER.ACTUAL_SIT"));
                ctrlOk = false;
            }
        }
        return ctrlOk;
    }

	/*
	 ******************* ACCESSORS ******************** */

    /**
     * Return all TypeContrat.
     *
     * @return List< TypeContrat>
     */
    public List<TypeContrat> getTypeContrats() {
        return getParameterService().getTypeContrats();
    }

    /**
     * Return all TypeStatut.
     *
     * @return List< TypeStatut>
     */
    public List<TypeStatut> getTypeStatuts() {
        return getParameterService().getTypeStatuts();
    }

    /**
     * Return all TypeOrganisme.
     *
     * @return List< TypeOrganisme>
     */
    public List<TypeOrganisme> getTypeOrganismes() {
        return getParameterService().getTypeOrganismes();
    }

    /**
     * Return all TypeSituation.
     *
     * @return List< TypeSituation>
     */
    public List<TypeSituation> getTypeSituations() {
        return getParameterService().getTypeSituations();
    }

    /**
     * @return the indSituation
     */
    public IndSituation getIndSituation() {
        return indSituation;
    }

    /**
     * @param indSituation the indSituation to set
     */
    public void setIndSituation(final IndSituation indSituation) {
        this.indSituation = indSituation;
    }

    /**
     * @return the individuPojo
     */
    public IndividuPojo getIndividuPojo() {
        return individuPojo;
    }

    /**
     * @param individuPojo the individuPojo to set
     */
    public void setIndividuPojo(final IndividuPojo individuPojo) {
        this.individuPojo = individuPojo;
    }

    /**
     * @return the codeTypeSituation
     */
    public String getCodeTypeSituation() {
        return codeTypeSituation;
    }

    /**
     * @param codeTypeSituation the codeTypeSituation to set
     */
    public void setCodeTypeSituation(final String codeTypeSituation) {
        this.codeTypeSituation = codeTypeSituation;
    }

    /**
     * @return the actionEnum
     */
    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    /**
     * @param actionEnum the actionEnum to set
     */
    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    /**
     * @return the adressController
     */
    public AdressController getAdressController() {
        return adressController;
    }

    /**
     * @param adressController the adressController to set
     */
    public void setAdressController(final AdressController adressController) {
        this.adressController = adressController;
    }

	/*----------------------------------------
	 *  GETTERS POUR JSF
	 */

    /**
     * @return the COD_CDD
     */
    public String getCodCDD() {
        return TypeContrat.COD_CDD;
    }

    /**
     * @return the COD_CDI
     */
    public String getCodCDI() {
        return TypeContrat.COD_CDI;
    }

    /**
     * @return the COD_AUTRE
     */
    public String getCodAutre() {
        return TypeContrat.COD_AUTRE;
    }

    /**
     * @return the tYPE_SAL
     */
    public String getTypeSal() {
        return TypeSituation.TYPE_SAL;
    }

    /**
     * @return the tYPE_PRO_LIB
     */
    public String getTypeProLib() {
        return TypeSituation.TYPE_PRO_LIB;
    }

    /**
     * @return the tYPE_DEM_EMP
     */
    public String getTypeDemEmp() {
        return TypeSituation.TYPE_DEM_EMP;
    }

    /**
     * @return the tYPE_ETU
     */
    public String getTypeEtu() {
        return TypeSituation.TYPE_ETU;
    }

    /**
     * @return the tYPE_OTHER
     */
    public String getTypeOther() {
        return TypeSituation.TYPE_OTHER;
    }

    /**
     * Return the France country code.
     *
     * @return String
     */
    public String getCodeFrance() {
        return Constantes.CODEFRANCE;
    }

}
