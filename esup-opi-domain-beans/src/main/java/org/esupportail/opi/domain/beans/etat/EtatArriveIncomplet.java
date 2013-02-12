/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public class EtatArriveIncomplet extends EtatVoeu {

	/**
	 * The state label.
	 */
	public static final String I18N_STATE = "STATE.ARRIVE_INCOMPLET";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 477449838177845351L;
	
	
	


	
	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public EtatArriveIncomplet(final I18nService i18Service) {
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
		return EtatArriveIncomplet.I18N_STATE;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */

}
