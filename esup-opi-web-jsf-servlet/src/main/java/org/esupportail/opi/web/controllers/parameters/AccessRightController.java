/**
 * CRI - Universite de Rennes1 - 57SI-CIES - 2007
 * http://sourcesup.cru.fr/projects/57si-cies/
 */
package org.esupportail.opi.web.controllers.parameters;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.BeanProfile;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessType;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
import org.esupportail.opi.web.beans.BeanAccess;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;




/**
 * Gere les modfication des droits pour chaque profil
 * et aussi controlle les droits pour chaque actions au sein des pages.
 * @author cleprous
 *
 */
public class AccessRightController extends AbstractContextAwareController {


	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1222587868921680324L;


	/*
	 ******************* PROPERTIES ******************* */


	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	
	/**
	 * All access type.
	 */
	private Map<String, AccessType> accessTypes;

	/*
	 ******************* INIT ************************* */

	/**
	 *Construtor.
	 */
	public AccessRightController() {
		super();
	}



	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
	}
	
	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
		Assert.notNull(this.accessTypes, "property accessTypes of class " 
				+ this.getClass().getName() + " can not be null");
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 * Save the rights for p.
	 * @param beanAccessDom 
	 * @param beanAccessFct List< BeanAccess>
	 * @param p Profil
	 */
	public void add(final List<BeanAccess> beanAccessDom,
					final List<BeanAccess> beanAccessFct, final Profile p) {
		addRights(beanAccessDom, p);
		addRights(beanAccessFct, p);
		reset();
	}

	
	/**
	 * Add the right in th dataBase.
	 * @param beanAccess
	 * @param p
	 */
	private void addRights(final List<BeanAccess> beanAccess, final Profile p) {
		for (int i = 0; i < beanAccess.size(); ++i) {
			Traitement traitmt = beanAccess.get(i).getTraitement();
			for (AccessType typeA : beanAccess.get(i).getTheDroits().keySet()) {
				//on insere tous les droits
				AccessRight a = new AccessRight();
				a.setProfile(p);
				a.setTraitement(traitmt);
				if (beanAccess.get(i).getTheDroits().get(typeA)) {
					a.setCodeAccessType(typeA.getCode());
				}
				getParameterService().addAccessRight(a);
			}
		}
	}

	/**
	 * Update ses droits acces.
	 * @param beanAccessDom 
	 * @param beanAccessFct List< BeanAccess>
	 * @param profile Profil
	 */
	public void update(final List<BeanAccess> beanAccessDom,
			    	final List<BeanAccess> beanAccessFct, final Profile profile) {
		if (log.isDebugEnabled()) {
			log.debug("entering update");
		}
		
		//plus utilise un domain peut avoir des droits si ces fct n'en ont pas.
		//COMMENT 05/03/2009
		//ctrlDom(beanAccessDom, beanAccessFct);
		updateRights(beanAccessDom, profile);
		updateRights(beanAccessFct, profile);
		
		reset();
		if (log.isDebugEnabled()) {
			log.debug("leaving update");
		}

	}

	
	/**
	 * Controle si les fonctionnalites d'un domain ont des droits.
	 *  Si elles n'en n'ont pas supprime les droits du domain.
	 * @param beanAccessDom
	 * @param beanAccessFct
	 * @return List< BeanAccess>
	 */
	@SuppressWarnings("unused")
	private List<BeanAccess> ctrlDom(final List<BeanAccess> beanAccessDom, final List<BeanAccess> beanAccessFct) {
		for (BeanAccess aDom : beanAccessDom) {
			//a true si au moins une fonction du domain a des droits 
			boolean oneFunctionHaveRight = false;
			//a true si le domain a des fonctions.
			boolean hasFunction = false;
			for (BeanAccess aFct : beanAccessFct) {
				Fonction f = (Fonction) aFct.getTraitement();
				//si c'est une fonctionnalite du domain
				if (f.getDomain().getId().equals(aDom.getTraitement().getId())) {
					hasFunction = true;
					//on controle si elle a des droits
					for (AccessType aType : aFct.getTheDroits().keySet()) {
						if (aFct.getTheDroits().get(aType)) {
							//on ne modifie pas le domain
							oneFunctionHaveRight = true;
							break;
						}
					}
				}
				if (oneFunctionHaveRight) { break; }
			}
			if (hasFunction && !oneFunctionHaveRight) {
				//on place a false tous les droits
				aDom.getTheDroits().put(getAccessTypes().get(AccessType.COD_ADD), false);
				aDom.getTheDroits().put(getAccessTypes().get(AccessType.COD_READ), false);
				aDom.getTheDroits().put(getAccessTypes().get(AccessType.COD_DELETE), false);
				aDom.getTheDroits().put(getAccessTypes().get(AccessType.COD_UPDATE), false);
			}
		}
		
		
		
		
		return null;
	}
	
	
	/**
	 * Add the right in th dataBase.
	 * @param beanAccess
	 * @param profile
	 */
	private void updateRights(final List<BeanAccess> beanAccess, final Profile profile) {
		for (int i = 0; i < beanAccess.size(); ++i) {
			Traitement traitmt = beanAccess.get(i).getTraitement();
			for (AccessType typeA : beanAccess.get(i).getTheDroits().keySet()) {
				boolean estPresent = false;
				AccessRight aUpdate = new AccessRight();
				//traitmt = getParameterService().getTraitement(traitmt.getId());
				//INIT THE ACCESS RIGHT
				getDomainService().initOneProxyHib(traitmt, traitmt.getAccessRight(), Set.class);
				
				for (Iterator<AccessRight> iA = traitmt.getAccessRight().iterator(); iA.hasNext();) {
					//si le droit existe on regarde s'il a ete modifie
					AccessRight a = iA.next();
					if (a.getCodeAccessType() != null) {
						if (a.getTraitement().getId().equals(traitmt.getId()) 
								&& a.getProfile().getId().equals(profile.getId())) {
							if (a.getCodeAccessType().equals(typeA.getCode())) {
								estPresent = true;
								aUpdate = a;
								break;
							}
						}
					} else {
						if (a.getTraitement().getId().equals(traitmt.getId()) 
								&& a.getProfile().getId().equals(profile.getId())) {
							//le droit existe en base mais est e null
							aUpdate = a;
							//on continue le for car le type
							//peut exister p our un autre droit
						}
					}
				}
				if (estPresent) {
					//s'il n'est plus coche on enleve ce droit
					if (!beanAccess.get(i).getTheDroits().get(typeA)) {
						aUpdate.setCodeAccessType(null);
						getParameterService().updateAccessRight(aUpdate);
					}
				} else {
					//s'il est coche on doit lui creer le droit
					if (beanAccess.get(i).getTheDroits().get(typeA)) {
						if (!aUpdate.getId().equals(0)) {
							aUpdate.setCodeAccessType(typeA.getCode());
							getParameterService().updateAccessRight(aUpdate);
						} else {
							//initialisation
							aUpdate.setProfile(profile);
							aUpdate.setCodeAccessType(typeA.getCode());
							aUpdate.setTraitement(traitmt);
							getParameterService().addAccessRight(aUpdate);
						}
					}
				}
				
				
			}
		}
	}


	
	/* ************FIN GESTION DES DROITS ************/

	
	/***
	 * Return all key of AccessType.
	 * @return Set< String>
	 */
	public Set<String> getCodeAccess() {
		return getAccessTypes().keySet();
	}

	/**
	 * List of BeanProfile in use.
	 * @return
	 */
	public List<String> getCodeAccessItems() {
		List<String> s = new ArrayList<String>();
		s.addAll(getCodeAccess());
		return s;		
	}

	/**
	 * @return the accessTypes
	 */
	public Map<String, AccessType> getAccessTypes() {
		return accessTypes;
	}

	


	/**
	 * @param accessTypes the accessTypes to set
	 */
	public void setAccessTypes(final Map<String, AccessType> accessTypes) {
		this.accessTypes = accessTypes;
	}



}
