/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public class EtatNonArrive extends EtatVoeu {

	/**
	 * The state label.
	 */
	public static final String I18N_STATE = "STATE.NON_ARRIVE";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 838811362883051097L;
	
	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public EtatNonArrive(final I18nService i18Service) {
		super();
		setLabel(i18Service.getString(I18N_STATE));
		
	}



	/*
	 ******************* METHODS ********************** */

	/**
	 * @see org.esupportail.opi.domain.beans.etat.EtatVoeu#getCodeLabel()
	 */
	@Override
	public String getCodeLabel() {
		return EtatNonArrive.I18N_STATE;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

}
