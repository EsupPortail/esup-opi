/**
 *
 */
package org.esupportail.opi.web.controllers.parameters;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


/**
 * @author cleprous
 */
public class FonctionController extends AbstractContextAwareController {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -4422568824457007050L;

	
	/*
	 ******************* PROPERTIES ******************* */


    /**
     * The fonction.
     */
    private Fonction fonction;

    /**
     * The selected domain id.
     */
    private Integer selectedDomId;

    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum;

    /**
     * The first element of the list is the function to add.
     * The others elements are all function in dataBase.
     */
    private List<Fonction> addFonctions;


    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());
	
	/*
	 ******************* INIT ************************* */


    /**
     * Constructors.
     */
    public FonctionController() {
        super();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        fonction = new Fonction();
        actionEnum = new ActionEnum();
        addFonctions = new ArrayList<Fonction>();

    }

    /**
     * Callback to domain list.
     *
     * @return String
     */
    public String goSeeAllFonction() {
        reset();
        return NavigationRulesConst.MANAGED_FONCTION;
    }

    /**
     * Callback to the form to add a function.
     *
     * @return String
     */
    public String goAddFonction() {
        reset();
        return NavigationRulesConst.ADD_FONCTION;
    }

    /**
     * Callback to the form to add a function.
     *
     * @return String
     */
    public String goUpdateFonction() {
        setSelectedDomId(fonction.getDomain().getId());
        return null;
    }
	
	
	/*
	 ******************* METHODS ********************** */


    /**
     * Add a fonction to the dataBase.
     *
     * @return String
     */
    public String add() {
        if (log.isDebugEnabled()) {
            log.debug("enterind add with id domain = " + getSelectedDomId() + " fonction = " + fonction);
        }
        //Get the first element of addFonctionns
        fonction = addFonctions.get(0);
        if (ctrlEnter(fonction)) {
            fonction = (Fonction) getDomainService().add(fonction, getCurrentGest().getLogin());
            fonction.setDomain(getParameterService().getDomain(getSelectedDomId()));
            getParameterService().addTraitement(fonction);

            reset();
            return NavigationRulesConst.MANAGED_FONCTION;
        }

        fonction = new Fonction();
        return null;


    }

    /**
     * Update a fonction to the dataBase.
     */
    public void update() {
        if (log.isDebugEnabled()) {
            log.debug("enterind update with fonction = " + fonction);
        }
        if (ctrlEnter(fonction)) {
            fonction = (Fonction) getDomainService().update(fonction, getCurrentGest().getLogin());
            fonction.setDomain(getParameterService().getDomain(getSelectedDomId()));
            getParameterService().updateTraitement(fonction);
            reset();
        }

        if (log.isDebugEnabled()) {
            log.debug("leaving update");
        }

    }

    /**
     * Delete a Fonction to the dataBase.
     */
    public void delete() {
        if (log.isDebugEnabled()) {
            log.debug("enterind delete with fonction = " + fonction);
        }

        getParameterService().deleteTraitement(fonction);
        reset();

        addInfoMessage(null, "INFO.DELETE.SUCCESS");

        if (log.isDebugEnabled()) {
            log.debug("leaving delete");
        }
    }
	
	
	/* ### ALL CONTROL ####*/

    /**
     * Control fonction attributes for the adding and updating.
     *
     * @param f
     * @return Boolean
     */
    private Boolean ctrlEnter(final Fonction f) {
        Boolean ctrlOk = true;
        if (!StringUtils.hasText(f.getCode())) {
            addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.CODE"));
            ctrlOk = false;
        } else {
            if (!getParameterService().traitementCodeIsUnique(f)) {
                ctrlOk = false;
                addErrorMessage(null, "ERROR.FIELD.NOT_UNIQUE", getString("FIELD_LABEL.CODE"));
            }
        }
        if (!StringUtils.hasText(f.getLibelle())) {
            addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.LIBELLE"));
            ctrlOk = false;
        }
        if (!StringUtils.hasText(f.getAction())) {
            addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.ACTION"));
            ctrlOk = false;
        }
        if (f.getRang() == null || f.getRang() == 0) {
            addErrorMessage(null, "ERROR.INT_FIELD.EMPTY", getString("FIELD_LABEL.RANG"));
            ctrlOk = false;
        } else {
            if (!getParameterService().functionRangIsUnique(f, getSelectedDomId())) {
                ctrlOk = false;
                addErrorMessage(null, "ERROR.RANG_FCT.NOT_UNIQUE",
                        getParameterService().getDomain(getSelectedDomId()).getLibelle());
            }
        }


        if (log.isDebugEnabled()) {
            log.debug("leaving ctrlAdd return = " + ctrlOk);
        }
        return ctrlOk;
    }
	
	/*
	 ******************* ACCESSORS ******************** */

    /**
     * @return Set< Fonction>
     */
    public Set<Fonction> getFonctions() {
        Set<Fonction> f = new TreeSet<Fonction>(new ComparatorString(Fonction.class));
        f.addAll(getParameterService().getFonctions(true, false));
        return f;
    }

    /**
     * List of Fonction in use.
     *
     * @return
     */
    public List<Fonction> getFonctionsItems() {
        List<Fonction> f = new ArrayList<Fonction>();
        f.addAll(getFonctions());
        return f;
    }

    /**
     * @return the addFonctions
     */
    public List<Fonction> getAddFonctions() {
        if (addFonctions.isEmpty()) {
            addFonctions.add(new Fonction());
            addFonctions.addAll(getFonctions());
        }
        return addFonctions;
    }


    /**
     * @return the fonction
     */
    public Fonction getFonction() {
        return fonction;
    }

    /**
     * @param fonction the fonction to set
     */
    public void setFonction(final Fonction fonction) {
        //Clone est utilise afin que l'utilisateur puisse modifier l'objet sans toucher au CACHE (par reference)
        //Probleme rencontre lors du modification annulee(par exemple), le cache etait tout de meme modifier
        this.fonction = fonction.clone();
    }

    /**
     * @return the selectedDomId
     */
    public Integer getSelectedDomId() {
        return selectedDomId;
    }

    /**
     * @param selectedDomId the selectedDomId to set
     */
    public void setSelectedDomId(final Integer selectedDomId) {
        this.selectedDomId = selectedDomId;
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
     * @param addFonctions the addFonctions to set
     */
    public void setAddFonctions(final List<Fonction> addFonctions) {
        this.addFonctions = addFonctions;
    }

}
