/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.web.beans;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.exceptions.WebFlowException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.web.controllers.Resettable;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessType;
import org.esupportail.opi.domain.beans.parameters.accessRight.Domain;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.User;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorInteger;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.tag.NavigationMenuItem;
import org.springframework.beans.factory.InitializingBean;



/**
 * A bean to memorize the treatement of the application.
 */
public class ManagedAccess implements Resettable, InitializingBean, Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -2380329125800067454L;


	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * 	Treatment to display of the rights of the current user.
	 */
	private List<NavigationMenuItem> traitementDisplay;

	/**
	 * The current treatement.
	 */
	private Traitement currentTraitement;

	/**
	 * see {@link ParameterService}.
	 */
	private ParameterService parameterService;

	/**
	 * The SessionController.
	 */
	private SessionController sessionController;

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	/*
	 ******************* INIT ************************* */


	/**
	 * Constructor.
	 */
	public ManagedAccess() {
		super();
	}


	/** 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.parameterService, 
				"property parameterService of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(this.sessionController, "property sessionController of class " 
				+ this.getClass().getName() + " can not be null");
		reset();
	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		traitementDisplay = new ArrayList<NavigationMenuItem>();
	}

	/*
	 ******************* CALLBACK ********************** */

	/**
	 * Callback use by all domain with function.
	 * @return String 
	 */
	public String goDisplayFunction() {
		if (currentTraitement instanceof Fonction) {
			//cela signifie que l'on se trouve dans le mecasnisme 
			//de retour vers la liste des fonctionnalites
			Fonction f = (Fonction) currentTraitement;
			currentTraitement = parameterService.getDomain(f.getDomain().getId());

		}

		makeFunction();	
		return NavigationRulesConst.DISPLAY_FONCTION;
	}


	/*
	 ******************* METHODS ********************** */

	/**
	 * The list of domain for the welcome manager.
	 * @return List< NavigationMenuItem>
	 */
	public List<NavigationMenuItem> getDomainAccueilGest() throws UserNotFoundException {
		reset();
		User u = sessionController.getCurrentUser();
		if (u != null) { 
			if (u instanceof Gestionnaire) {
				Gestionnaire g = (Gestionnaire) u;
				Set<Traitement> l = new TreeSet<Traitement>(new ComparatorInteger(Traitement.class));
				l.addAll(parameterService.getTraitements(
						g.getProfile(), Traitement.TYPE_DOMAIN, null)); 
				for (Traitement t : l) {
					traitementDisplay.add(new NavigationMenuItem(t.getLibelle(), t.getAction(), t));
				}
			} else {
				log.warn("the user is not a manager : " + u);
			}
		}
		return traitementDisplay;

	}

	/**
	 * Make the function to display of the rights of currentUser.
	 */
	private void makeFunction() {
		traitementDisplay = new ArrayList<NavigationMenuItem>();
		Gestionnaire g = (Gestionnaire) sessionController.getCurrentUser();
		Set<Traitement> l = new TreeSet<Traitement>(new ComparatorInteger(Traitement.class));
		l.addAll(parameterService.getTraitements(g.getProfile(),
				Traitement.TYPE_FUNCTION, (Domain) currentTraitement)); 
		for (Traitement t : l) {
			traitementDisplay.add(new NavigationMenuItem(t.getLibelle(), t.getAction(), t));
		}


	}

	/** 
	 * Permet de definir si un user a le droit sur la fonctionnalite courante.
	 * @param codAccess String
	 * @param gest The userCurrent
	 * @return boolean
	 */
	private boolean ctrlAccess(final String codAccess, 
			final Gestionnaire gest) {
		try {
			if (gest != null) {
				Set<AccessRight> accessRights  = gest.getProfile().getAccessRight();
				for (Iterator<AccessRight> iA = accessRights.iterator(); iA.hasNext();) {
					AccessRight a = iA.next();
					if (a.getTraitement().getId().equals(getCurrentTraitement().getId())) {
						if (codAccess.equals(a.getCodeAccessType())) {
							return true;
						}
					}
				}
			} else {
				log.error("Le profil du Current User est null donc pas de droit ");
				throw new WebFlowException("L'utilisateur n'est pas autorise");
			}
		} catch (Exception e) {
			throw new WebFlowException("L'utilisateur n'est pas autorise", e);
		}
		return false;
	}


	/*####### AUTHORISATION #######*/

	/**
	 * 
	 * @return Boolean true if user has the read right.
	 */
	public Boolean getReadAuthorized() {
		User u = sessionController.getCurrentUser();
		if (u != null && u instanceof Gestionnaire) {
			Gestionnaire g = (Gestionnaire) u;
			return ctrlAccess(AccessType.COD_READ, g);
		}
		return false;
	}

	/**
	 * 
	 * @return Boolean true if user has the add right.
	 */
	public Boolean getAddAuthorized() {
		User u = sessionController.getCurrentUser();
		if (u != null && u instanceof Gestionnaire) {
			Gestionnaire g = (Gestionnaire) u;
			return ctrlAccess(AccessType.COD_ADD, g);
		}
		return false;
	}

	/**
	 * 
	 * @return Boolean true if user has the update right.
	 */
	public Boolean getUpdateAuthorized() {
		User u = sessionController.getCurrentUser();
		if (u != null && u instanceof Gestionnaire) {
			Gestionnaire g = (Gestionnaire) u;
			return ctrlAccess(AccessType.COD_UPDATE, g);
		}
		return false;
	}

	/**
	 * 
	 * @return Boolean true if user has the delete right.
	 */
	public Boolean getDeleteAuthorized() {
		User u = sessionController.getCurrentUser();
		if (u != null && u instanceof Gestionnaire) {
			Gestionnaire g = (Gestionnaire) u;
			return ctrlAccess(AccessType.COD_DELETE, g);
		}
		return false;
	}

	/*
	 ******************* ACCESSORS ******************** */



	/**
	 * @return the traitementDisplay
	 */
	public List<NavigationMenuItem> getTraitementDisplay() {
		return traitementDisplay;
	}

	/**
	 * @param traitementDisplay the traitementDisplay to set
	 */
	public void setTraitementDisplay(final List<NavigationMenuItem> traitementDisplay) {
		this.traitementDisplay = traitementDisplay;
	}

	/**
	 * @return the currentTraitement
	 */
	public Traitement getCurrentTraitement() {
		return currentTraitement;
	}

	/**
	 * @param currentTraitement the currentTraitement to set
	 */
	public void setCurrentTraitement(final Traitement currentTraitement) {
		this.currentTraitement = currentTraitement;
	}


	/**
	 * @param parameterService the parameterService to set
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	/**
	 * @param sessionController the sessionController to set
	 */
	public void setSessionController(final SessionController sessionController) {
		this.sessionController = sessionController;
	}



}
