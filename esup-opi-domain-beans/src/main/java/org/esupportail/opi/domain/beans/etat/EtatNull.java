/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 * EtatNull : for wish without state.
 */
public class EtatNull extends EtatVoeu {

	/**
	 * The state label.
	 */
	public static final String I18N_STATE = "STATE.NULL";
	
	/**
	 * The serialization id.
	 */	private static final long serialVersionUID = 5341299911602108603L;
	
	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public EtatNull(final I18nService i18Service) {
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
		return EtatNull.I18N_STATE;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

}
