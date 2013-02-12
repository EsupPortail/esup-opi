/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public class EtatConfirme extends EtatVoeu {
	
	/**
	 * The state label.
	 */
	public static final String I18N_STATE = "STATE.CONFIRM";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -2843029448143247147L;
	
	
	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 * @param i18Service 
	 */
	public EtatConfirme(final I18nService i18Service) {
		super();
		setLabel(i18Service.getString(I18N_STATE));
	}
	
	/*
	 ******************* METHODS ********************** */

	
	/** 
	 * @see org.esupportail.opi.domain.beans.etat.EtatVoeu#getDisplayForms()
	 */
	@Override
	public Boolean getDisplayForms() {
		return false;
	}
	
	
	/**
	 * @see org.esupportail.opi.domain.beans.etat.EtatVoeu#getCodeLabel()
	 */
	@Override
	public String getCodeLabel() {
		return EtatConfirme.I18N_STATE;
	}
	/*
	 ******************* ACCESSORS ******************** */

}
