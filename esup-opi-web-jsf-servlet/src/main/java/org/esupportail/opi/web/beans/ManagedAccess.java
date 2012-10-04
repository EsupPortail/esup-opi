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

import javax.el.ExpressionFactory;
import javax.el.MethodExpression;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.faces.event.FacesListener;

import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.exceptions.WebFlowException;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.i18n.I18nUtils;
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
import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.component.submenu.Submenu;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;
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
	
	private MenuModel menuModel;

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

				FacesContext ctx = FacesContext.getCurrentInstance();
				for (Traitement t : l) {
					MethodExpression me = ctx.getApplication().getExpressionFactory().createMethodExpression(
							ctx.getELContext(), t.getAction(), String.class, new Class[]{});
					String action  = (String) me.invoke(ctx.getELContext(), null);
					traitementDisplay.add(new NavigationMenuItem(t.getLibelle(), action, t));
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
	
//	/**
//	 * Use by deeplinking cf. deepLinking.xml
//	 * @param codeTrt
//	 * @return String callback to redirect
//	 */
//	public String initCurrentTreatement(final String codeTrt) {
//
//		currentTraitement = parameterService.getTraitement(Integer.valueOf(codeTrt));
//
//		String elAction = TagUtils.makeELExpression(currentTraitement.getAction());
//		FacesContext context = FacesContext.getCurrentInstance();
//		MethodExpression method = context.getApplication()
//		.getExpressionFactory().createMethodExpression(
//				context.getELContext(), elAction, String.class, new Class[]{});
//		//execute the method define to action attributes.
//		Object navRules = method.invoke(context.getELContext(), null);
//		return (String) navRules;
//
//
//	}
//	
//	/**
//	 * Generate the URL for to the all treatment in this application.
//	 * @param t 
//	 * @return String
//	 */
//	private final String treatmentUrl(final Traitement t) {
//		Map<String, String> params = new HashMap<String, String>();
//		params.put("treatment", String.valueOf(t.getId()));
//		String url = urlGenerator.casUrl(params);
//		return url;
//	}
	/**
	 * Builds the manager's menu.
	 * @return a menu.
	 */
	public MenuModel getMenuGestionnaire() {
		menuModel = new DefaultMenuModel();
		I18nService i18nService = I18nUtils.createI18nService();
		final FacesContext fc = FacesContext.getCurrentInstance();
		final ExpressionFactory factory = fc.getApplication().getExpressionFactory();
		MenuItem accueil = new MenuItem();
		accueil.setValue(i18nService.getString("NAVIGATION.TEXT.WELCOME"));
		accueil.setActionExpression(factory.createMethodExpression(fc.getELContext(), "#{welcomeController.goWelcomeManager}", String.class, new Class[]{}));
		menuModel.addMenuItem(accueil);
		User u = sessionController.getCurrentUser();
		if (u != null) { 
			if (u instanceof Gestionnaire) {	
				Gestionnaire g = (Gestionnaire) u;
				Set<Traitement> domains = new TreeSet<Traitement>(new ComparatorInteger(Traitement.class));
				domains.addAll(parameterService.getTraitements(
						g.getProfile(), Traitement.TYPE_DOMAIN, null));
				for (Traitement d : domains) {
					Submenu sub = new Submenu();
					sub.setLabel(d.getLibelle());
					Set<Traitement> functions = new TreeSet<Traitement>(new ComparatorInteger(Traitement.class));
					functions.addAll(parameterService.getTraitements(
							g.getProfile(), Traitement.TYPE_FUNCTION, (Domain) d));
					for (final Traitement f : functions) {
						final MethodExpression me = factory.createMethodExpression(fc.getELContext(), f.getAction(), String.class, new Class[]{});
						MenuItem item = new MenuItem();
						item.setValue(f.getLibelle());
//						item.setActionExpression(me);
						item.setActionExpression(callFunction(f));
						sub.getChildren().add(item);
					}					
					menuModel.addSubmenu(sub);
				}
			}
		}
		MenuItem logout = new MenuItem();
		logout.setRendered(sessionController.getIsServlet());
		logout.setValue(i18nService.getString("NAVIGATION.TEXT.LOGOUT"));
		logout.setActionExpression(factory.createMethodExpression(fc.getELContext(), "#{sessionController.logoutGest}", String.class, new Class[]{}));
		menuModel.addMenuItem(logout);
		
		return menuModel;
	}
	
	public void setMenuGestionnaire(final MenuModel menuModel) {
		this.menuModel = menuModel;  
	}
	
	/**
	 * 
	 * @param f
	 * @return
	 */
	private MethodExpression callFunction(final Traitement f) {
		setCurrentTraitement(f);
		final FacesContext fc = FacesContext.getCurrentInstance();
		final ExpressionFactory factory = fc.getApplication().getExpressionFactory();
		final MethodExpression me = factory.createMethodExpression(fc.getELContext(), f.getAction(), String.class, new Class[]{});
		
		return me;
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
