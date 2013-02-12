/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public class EtatDesiste extends EtatVoeu {

	/**
	 * The state label.
	 */
	public static final String I18N_STATE = "STATE.DESIST";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -6176762107957933376L;
	
	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 * @param i18Service 
	 */
	public EtatDesiste(final I18nService i18Service) {
		super();
		setLabel(i18Service.getString(I18N_STATE));
		
	}



	/** 
	 * @see org.esupportail.opi.domain.beans.etat.EtatVoeu#getDisplayForms()
	 */
	@Override
	public Boolean getDisplayForms() {
		return false;
	}
	
	/*
	 ******************* METHODS ********************** */

	
	/**
	 * @see org.esupportail.opi.domain.beans.etat.EtatVoeu#getCodeLabel()
	 */
	@Override
	public String getCodeLabel() {
		return EtatDesiste.I18N_STATE;
	}
	/*
	 ******************* ACCESSORS ******************** */

}
