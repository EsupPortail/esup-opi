/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public class EtatNonRenseigne extends Etat {

	/**
	 * The state label.
	 */
	public static final String I18N_STATE_NON_RENSEIGNE = "STATE.NON_RENSEIGNE";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -3474809761593264147L;
	
	
	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public EtatNonRenseigne(final I18nService i18Service) {
		super();
		setLabel(i18Service.getString(I18N_STATE_NON_RENSEIGNE));
		
	}
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * @see org.esupportail.opi.domain.beans.etat.EtatVoeu#getCodeLabel()
	 */
	@Override
	public String getCodeLabel() {
		return EtatNonRenseigne.I18N_STATE_NON_RENSEIGNE;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

}
