/**
* ESUP-Portail - candidatures - 2009
* http://subversion.cru.fr/57si-OPI
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.parameters;

import org.esupportail.commons.utils.Assert;

/**
 * @author cleprous
 *
 */
public class FormationContinue extends RegimeInscription {

	/**
	 * The code for this beans.
	 */
	public static final int CODE = 1;
	
	/**
	 * The regime label.
	 */
	protected static final String I18N_FORMATION_CONTINUE_LAB = "REGIME.LABEL.FC";

	/**
	 * The regime label.
	 */
	protected static final String I18N_FORMATION_CONTINUE_SHORT_LAB = "REGIME.SHORT_LABEL.FC";

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -3889976826758556733L;
	


	/*
	 *************************** PROPERTIES ******************************** */

	/*
	 *************************** INIT ************************************** */
	
	

	/**
	 * Constructors.
	 */
	public FormationContinue() {
		super();
		setCode(CODE);
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.getI18nService(), "property i18nService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.getControlField(), "property controlField of class " 
				+ this.getClass().getName() + " can not be null");
		setLabel(getI18nService().getString(I18N_FORMATION_CONTINUE_LAB));
		setShortLabel(getI18nService().getString(I18N_FORMATION_CONTINUE_SHORT_LAB));
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormationContinue#" + hashCode() + "[" + super.toString() + "]";

	}
	

	/*
	 *************************** METHODS *********************************** */

	/** 
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#canModifyRISearch()
	 */
	@Override
	public boolean canModifyRISearch() {
		return false;
	}
	
	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#getDisplayInfoBac()
	 */
	@Override
	public boolean getDisplayInfoBac() {
		return false;
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#displayInfoSituation()
	 */
	@Override
	public boolean getDisplayInfoSituation() {
		return true;
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#getCanAlwaysAddVows()
	 */
	@Override
	public boolean getCanAlwaysAddVows() {
		return true;
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#getCalInsIsOpen()
	 */
	@Override
	public Boolean getCalInsIsOpen() {
		if (getManagedCalendar() == null) {
			return true;
		}
		return getManagedCalendar().getCalInsIsOpen();
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#getDisplayInfoFC()
	 */
	@Override
	public boolean getDisplayInfoFC() {
		return true;
	}

	/*
	 *************************** ACCESSORS ********************************* */

}
