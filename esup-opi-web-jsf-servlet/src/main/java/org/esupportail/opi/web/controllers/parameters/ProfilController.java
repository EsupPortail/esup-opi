/**
 * 
 */
package org.esupportail.opi.web.controllers.parameters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.BeanProfile;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessType;
import org.esupportail.opi.domain.beans.parameters.accessRight.Domain;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.BeanAccess;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.springframework.util.StringUtils;



/**
 * @author cleprous
 *
 */
public class ProfilController extends AbstractContextAwareController {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 594487224624313963L;

	/**
	 * Default value to select all domains.
	 */
	private static final int SELECT_ALL_DOMAIN = 0;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The profil.
	 */
	private Profile profil;
	
	/**
	 * the domain id selected.
	 */
	private Integer idDomainSelected;

	/**
	 * The access function for this profil.
	 */
	private List<BeanAccess> accessFct;

	/**
	 * The access Domain for this profil.
	 */
	private List<BeanAccess> accessDomain;

	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;

	/**
	 * see {@link AccessRightController}.
	 */
	private AccessRightController accessRController;

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());

	/*
	 ******************* INIT ************************* */



	/**
	 * Constructors.
	 */
	public ProfilController() {
		super();
	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		profil = new Profile();
		accessFct = new ArrayList<BeanAccess>();
		accessDomain = new ArrayList<BeanAccess>();
		actionEnum = new ActionEnum();
		idDomainSelected = null;

	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
		Assert.notNull(this.accessRController, "property accessRController of class " 
				+ this.getClass().getName() + " can not be null");
	}

	/*
	 ******************* CALLBACK ********************** */

	/**
	 * Callback to add profil.
	 * @return String 
	 */
	public String goEnterProfil() {
		return NavigationRulesConst.ENTER_PROFIL;
	}

	/**
	 * Callback to profil list.
	 * @return String 
	 */
	public String goSeeAllProfil() {
		reset();
		return NavigationRulesConst.MANAGED_PROFILS;
	}

	/**
	 * Callback to see a profil.
	 * @return String 
	 */
	public String goSeeProfil() {
		return NavigationRulesConst.SEE_PROFIL;
	}

	/*
	 ******************* METHODS ********************** */



	/**
	 * Add a profil to the dataBase.
	 * @return String
	 */
	public String add() {
		if (log.isDebugEnabled()) {
			log.debug("enterind add with profil = " + profil);
		}
		if (ctrlEnter(profil)) { 
			profil = (Profile) getDomainService().add(profil, getCurrentGest().getLogin());
			getParameterService().addProfile(profil);

			//add the rights
			getAccessRController().add(getAccessDomain(), getAccessFct(), getProfil());

			reset();
			getAccessRController().reset();
			
			if (log.isDebugEnabled()) {
				log.debug("leaving add");
			}

			return NavigationRulesConst.MANAGED_PROFILS;
		}
		return null;

	}


	/**
	 * Update a profil to the dataBase.
	 * @return String
	 */
	public String update() {
		if (log.isDebugEnabled()) {
			log.debug("enterind update with profil = " + profil);
		}
		if (ctrlEnter(profil)) { 
			profil = (Profile) getDomainService().update(profil, getCurrentGest().getLogin());
			getParameterService().updateProfile(profil);

			//add the rights
			getAccessRController().update(getAccessDomain(), getAccessFct(), getProfil());

			reset();
			
			if (log.isDebugEnabled()) {
				log.debug("leaving update");
			}
			return NavigationRulesConst.MANAGED_PROFILS;
		}

		return null;


	}

	/**
	 * Delete a Profile to the dataBase.
	 */
	public void delete() {
		if (log.isDebugEnabled()) {
			log.debug("enterind delete with profil = " + profil);
		}

		getParameterService().deleteProfile(profil);
		reset();

		
		addInfoMessage(null, "INFO.DELETE.SUCCESS");

		if (log.isDebugEnabled()) {
			log.debug("leaving delete");
		}
	}


	/**
	 * The selected domain.
	 * @param event
	 */
	public void selectDomain(final ValueChangeEvent event) {

		Integer value = (Integer) event.getNewValue();
		idDomainSelected = value;
		selectDomain();
		FacesContext.getCurrentInstance().renderResponse();

	}

	/**
	 * The selected domain.
	 */
	public void selectDomain() {
		if (idDomainSelected != null) {
			if (idDomainSelected.equals(0)) {
				//get all access function
				Set<Fonction> fonctions = getParameterService().getFonctions(true, true);
				setAccessFct(makeAccess(new HashSet<Traitement>(fonctions)));
				////get all access domain
				setAccessDomain(makeAccess(
						new HashSet<Traitement>(getParameterService().getDomains(true, true))));
			} else {
				Domain d = getParameterService().getDomain(idDomainSelected);
				Set<Fonction> fonctions = d.getFonctions();
				setAccessFct(makeAccess(new HashSet<Traitement>(fonctions)));
				Set<Traitement> trait =  new HashSet<Traitement>();
				trait.add(d);
				setAccessDomain(makeAccess(trait));
			}
		} else {
			setAccessFct(new ArrayList<BeanAccess>());
		}
	}

	/**
	 * Build the list contains all access for all functions.
	 * @param traitements
	 * @return List< BeanAccess>
	 */
	private List<BeanAccess> makeAccess(final Set<Traitement> traitements) {
		List<BeanAccess> theAccess = new ArrayList<BeanAccess>();

		for (Iterator<Traitement> ite = traitements.iterator(); ite.hasNext();) {
			Traitement t = ite.next();
			//init proxy Hib
			getDomainService().initOneProxyHib(t, t.getAccessRight(), Set.class);
			BeanAccess beanAccess = new BeanAccess();
			beanAccess.setTraitement(t);
			Map<AccessType, Boolean> droits = new HashMap<AccessType, Boolean>();
			Domain d = null;
			if (t instanceof Domain) {
				d = (Domain) t;

			} 
			//commente par CL le 28/01/2009
			//le fait de ne pas bloquer le droit des domain slt e lecture permet
			//de possibilite dans la realisation d'interface. (Par afficher les fonctionnalite + module etudiant)
//			if (d != null && d.getFonctions() != null && !d.getFonctions().isEmpty()) {
//				if (actionEnum.getWhatAction().equals(ActionEnum.ADD_ACTION) 
//						|| actionEnum.getWhatAction().equals(ActionEnum.UPDATE_ACTION)) {
//					//si le domain a des fonctions le seule droit autorise sur ce domain est lecture
//					droits.put(
//							getAccessRController().getAccessTypes()
//								.get(AccessType.COD_READ), true);
//				} else {
//					droits.put(
//							getAccessRController().getAccessTypes()
//								.get(AccessType.COD_READ), ctrlDom(beanAccess));
//				}
//				beanAccess.setTheDroits(droits);
//				theAccess.add(beanAccess);
//
//			} else {

				for (String codeAc : getAccessRController().getAccessTypes().keySet()) {
					AccessType ac =  getAccessRController().getAccessTypes().get(codeAc);
					boolean hasTheDroit = false;
					for (AccessRight a : t.getAccessRight()) {
						//s'il y a deja des droit en base on regarde le droit du profil
						if (a.getProfile().getId().equals(getProfil().getId()) 
								&& a.getTraitement().getId().equals(t.getId())) {
							if (a.getCodeAccessType() != null) {
								if (a.getCodeAccessType().equals(ac.getCode())) {
									hasTheDroit = true;
									break;
								}
							}
						}
					}
					if (d != null && ac.getCode().equals(AccessType.COD_READ)) {
						//droit par defaut lecture
						droits.put(ac, true);
					} else {
						droits.put(ac, hasTheDroit);
					}
				}
				beanAccess.setTheDroits(droits);
				theAccess.add(beanAccess);
//			}
		}

		//trier
		Collections.sort(theAccess, new ComparatorString(BeanAccess.class));
		return theAccess;
	}



	/* ### ALL CONTROL ####*/

	/**
	 * Control Profile attributes for the adding and updating.
	 * @param pro
	 * @return Boolean
	 */
	private Boolean ctrlEnter(final Profile pro) {
		Boolean ctrlOk = true;
		if (!StringUtils.hasText(pro.getCode())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.CODE"));
			ctrlOk = false;
		} else {
			if (!getParameterService().profileCodeIsUnique(pro)) {
				ctrlOk = false;
				addErrorMessage(null, "ERROR.FIELD.EXIST",
						getString("PROFIL"),
						getString("FIELD_LABEL.CODE"));
			}
		}
		if (!StringUtils.hasText(pro.getLibelle())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.LIBELLE"));
			ctrlOk = false;
		}

		if (log.isDebugEnabled()) {
			log.debug("leaving ctrlEnter return = " + ctrlOk);
		}
		return ctrlOk;
	}

	/**
	 * Control if the functions of domain has rights. If not rights return false.
	 * @param beanAccessDom
	 * @return Boolean
	 */
	public Boolean ctrlDom(final BeanAccess beanAccessDom) {
		Domain dom = (Domain) beanAccessDom.getTraitement();	
		//a true si au moins une fonction du domain a des droits 
		boolean oneFunctionHaveRight = false;
		Set<Traitement> functions = new HashSet<Traitement>();
		functions.addAll(dom.getFonctions());
		List<BeanAccess> beanAccessFct = makeAccess(functions);
		for (BeanAccess aFct : beanAccessFct) {
			//on controle si elle a des droits
			for (AccessType aType : aFct.getTheDroits().keySet()) {
				if (aFct.getTheDroits().get(aType)) {
					//on ne modifie pas le domain
					oneFunctionHaveRight = true;
					break;
				}
			}
		}

		return oneFunctionHaveRight;
	}

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return List< BeanAccess>
	 */
	public List<BeanAccess> getAllAccess() {
		Set<Traitement> allTraitement = new HashSet<Traitement>(getParameterService().getFonctions(true, true));
		Set<Traitement> tDom = new HashSet<Traitement>(getParameterService().getDomains(true, true));
		allTraitement.addAll(tDom);
		return makeAccess(allTraitement);
	}

	/**
	 * 
	 * @return Set< BeanProfile>
	 */
	public Set<BeanProfile> getBeanProfile() {
		return getParameterService().getProfiles(null);
	}

	/**
	 * 
	 * @return Set< BeanProfile>
	 */
	public Set<BeanProfile> getBeanProfileInUse() {
		if (getCurrentGest().getProfile().getSuperProfile()) {
			return getParameterService().getProfiles(true);
		}
		//Not Diplay the superProfiles if the user has not a super profile.
		Set<BeanProfile> beanP = new HashSet<BeanProfile>();
		for (BeanProfile b : getParameterService().getProfiles(true)) {
			if (!b.getProfile().getSuperProfile()) {
				beanP.add(b);
			}
		}
		
		return beanP;
	}

	/**
	 * @return List< BeanAccess>
	 */
	public List<BeanAccess> getAccessFct() {
		return accessFct;
	}



	/**
	 * @param access the access to set
	 */
	public void setAccessFct(final List<BeanAccess> access) {
		this.accessFct = access;
	}

	/**
	 * @return the profil
	 */
	public Profile getProfil() {
		return profil;
	}

	/**
	 * @param profil the profil to set
	 */
	public void setProfil(final Profile profil) {
		//Clone est utilise afin que l'utilisateur puisse modifier l'objet sans toucher au CACHE (par reference)
		//Probleme rencontre lors du modification annulee(par exemple), le cache etait tout de meme modifier
		this.profil = profil.clone();
	}

	/**
	 * @return the accessRController
	 */
	public AccessRightController getAccessRController() {
		return accessRController;
	}

	/**
	 * @param accessRController the accessRController to set
	 */
	public void setAccessRController(final AccessRightController accessRController) {
		this.accessRController = accessRController;
	}

	/**
	 * @return the SELECT_ALL_DOMAIN
	 */
	public int getSelectAllDomain() {
		return SELECT_ALL_DOMAIN;
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
	 * @return the accessDomain
	 */
	public List<BeanAccess> getAccessDomain() {
		return accessDomain;
	}

	/**
	 * @param accessDomain the accessDomain to set
	 */
	public void setAccessDomain(final List<BeanAccess> accessDomain) {
		this.accessDomain = accessDomain;
	}

	/**
	 * @return the idDomainSelected
	 */
	public Integer getIdDomainSelected() {
		return idDomainSelected;
	}

	/**
	 * @param idDomainSelected the idDomainSelected to set
	 */
	public void setIdDomainSelected(final Integer idDomainSelected) {
		this.idDomainSelected = idDomainSelected;
	}

}
