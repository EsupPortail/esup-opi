/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public class EtatArrive extends EtatVoeu {

	/**
	 * The state label.
	 */
	public static final String I18N_STATE = "STATE.ARRIVE";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 8382865851707418479L;
	

	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 * @param i18Service 
	 */
	public EtatArrive(final I18nService i18Service) {
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
		return EtatArrive.I18N_STATE;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

}
