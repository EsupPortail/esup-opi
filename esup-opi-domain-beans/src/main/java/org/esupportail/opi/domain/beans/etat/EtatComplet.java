/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public class EtatComplet extends EtatIndividu {

	/**
	 * Key I18n for the state label.
	 */
	public static final String I18N_STATE_COMPLET = "STATE.COMPLET";
	
	
	
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 5035210414656866561L;
	
	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public EtatComplet(final I18nService i18Service) {
		super();
		setLabel(i18Service.getString(I18N_STATE_COMPLET));
		
	}
	
	/*
	 ******************* METHODS ********************** */

	/**
	 * @see org.esupportail.opi.domain.beans.etat.EtatIndividu#getCanDoChoiceEtape()
	 */
	@Override
	public Boolean getCanDoChoiceEtape() {
		return true;
	}
	
	/**
	 * @see org.esupportail.opi.domain.beans.etat.EtatVoeu#getCodeLabel()
	 */
	@Override
	public String getCodeLabel() {
		return EtatComplet.I18N_STATE_COMPLET;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */

}
