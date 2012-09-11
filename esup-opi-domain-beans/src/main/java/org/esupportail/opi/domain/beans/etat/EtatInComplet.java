/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public class EtatInComplet extends EtatIndividu {

	/**
	 * The state label.
	 */
	public static final String I18N_STATE_INCOMPLET = "STATE.INCOMPLET";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -3432041279806183638L;
	

	
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public EtatInComplet(final I18nService i18Service) {
		super();
		setLabel(i18Service.getString(I18N_STATE_INCOMPLET));
		
	}




	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * @see org.esupportail.opi.domain.beans.etat.EtatIndividu#getCanDoChoiceEtape()
	 */
	@Override
	public Boolean getCanDoChoiceEtape() {
		return false;
	}
	
	/**
	 * @see org.esupportail.opi.domain.beans.etat.EtatVoeu#getCodeLabel()
	 */
	@Override
	public String getCodeLabel() {
		return EtatInComplet.I18N_STATE_INCOMPLET;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */

}
