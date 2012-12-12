/**
 * 
 */
package org.esupportail.opi.web.controllers.parameters;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.faces.webapp.UIComponentTag;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.parameters.accessRight.Domain;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.springframework.util.StringUtils;

/**
 * @author cleprous
 *
 */
public class DomainController extends AbstractContextAwareController {

	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5769900637456057431L;
	
	
	/*
	 ******************* PROPERTIES ******************* */


	/**
	 * The domain.
	 */
	private Domain domain;
	
	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;
	
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	/*
	 ******************* INIT ************************* */
	
	

	/**
	 * Constructors.
	 */
	public DomainController() {
		super();
	}
	
	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		domain = new Domain();
		actionEnum = new ActionEnum();
		
	}

	/*
	 ******************* CALLBACK ********************** */
	
	/**
	 * Callback to domain list.
	 * @return String 
	 */
	public String goSeeAllDomain() {
		return NavigationRulesConst.MANAGED_DOMAIN;
	}
	
	/**
	 * Callback to add domain.
	 * @return String 
	 */
	public String goAddDomain() {
		reset();
		actionEnum.setWhatAction(ActionEnum.ADD_ACTION);
		return null;
	}
	
	/*
	 ******************* METHODS ********************** */

	
	/**
	 * Add a Domain to the dataBase.
	 */
	public void add() {
		if (log.isDebugEnabled()) {
			log.debug("enterind add with domain = " + domain);
		}
		if (ctrlEnter(domain)) {
			domain = (Domain) getDomainService().add(domain, getCurrentGest().getLogin());
			getParameterService().addTraitement(domain);
			
			reset();
			addInfoMessage(null, "INFO.ENTER.SUCCESS");
		}
		if (log.isDebugEnabled()) {
			log.debug("leaving add");
		}
		
	}
	
	/**
	 * Update a Domain to the dataBase.
	 */
	public void update() {
		if (log.isDebugEnabled()) {
			log.debug("enterind update with domain = " + domain);
		}
		if (ctrlEnter(domain)) {
			domain = (Domain) getDomainService().update(domain, getCurrentGest().getLogin());
			getParameterService().updateTraitement(domain);
			
			reset();
			addInfoMessage(null, "INFO.ENTER.SUCCESS");
		}
		
		if (log.isDebugEnabled()) {
			log.debug("leaving update");
		}
		
	}
	
	
	/**
	 * Delete a Domain to the dataBase.
	 */
	public void delete() {
		if (log.isDebugEnabled()) {
			log.debug("enterind delete with domain = " + domain);
		}

		getParameterService().deleteTraitement(domain);
		reset();
		
		addInfoMessage(null, "INFO.DELETE.SUCCESS");
		
		if (log.isDebugEnabled()) {
			log.debug("leaving delete");
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	/* ### ALL CONTROL ####*/
	
	/**
	 * Control domain attributes for the adding and updating.
	 * @param d
	 * @return Boolean
	 */
	private Boolean ctrlEnter(final Domain d) {
		Boolean ctrlOk = true;
		if (!StringUtils.hasText(d.getCode())) {
			addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.CODE"));
			ctrlOk = false;
		} else {
			if (!getParameterService().traitementCodeIsUnique(d)) {
				ctrlOk = false;
				addErrorMessage(null, "ERROR.FIELD.NOT_UNIQUE", getString("FIELD_LABEL.CODE"));
			}
		}
		if (!StringUtils.hasText(d.getLibelle())) {
			addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.LONG_LIB"));
			ctrlOk = false;
		}
		if (!StringUtils.hasText(d.getShortLabel())) {
			addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.SHORT_LIB"));
			ctrlOk = false;
		}
		if (!StringUtils.hasText(d.getAction())) {
			addErrorMessage(null, "ERROR.FIELD.EMPTY", getString("FIELD_LABEL.ACTION"));
			ctrlOk = false;
		}
		if (d.getRang() == null || d.getRang() == 0) {
			addErrorMessage(null, "ERROR.INT_FIELD.EMPTY", getString("FIELD_LABEL.RANG"));
			ctrlOk = false;
		} else {
			if (!getParameterService().domainRangIsUnique(d)) {
				ctrlOk = false;
				addErrorMessage(null, "ERROR.FIELD.NOT_UNIQUE", getString("FIELD_LABEL.RANG"));
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
	 * @return Set< Domain>
	 */
	public Set<Domain> getDomains() {
		return getParameterService().getDomains(true, false);
	}

	/**
	 * List of Domain in use.
	 * @return
	 */
	public List<Domain> getDomainsItems() {
		List<Domain> d = new ArrayList<Domain>();
		d.addAll(getDomains());
		return d;		
	}

	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}

	/**
	 * @param domain the domain to set
	 */
	public void setDomain(final Domain domain) {
		//Clone est utilise afin que l'utilisateur puisse modifier l'objet sans toucher au CACHE (par reference)
		//Probleme rencontre lors du modification annulee(par exemple), le cache etait tout de meme modifier
		
		this.domain = domain.clone();
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
	public void setActionEnum(ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}

}
