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
public class FormationInitiale extends RegimeInscription {

	/**
	 * The code for this beans.
	 */
	public static final int CODE = 0;
	
	/**
	 * The regime label.
	 */
	protected static final String I18N_FORMATION_INITIALE_LAB = "REGIME.LABEL.FI";
	
	/**
	 * The regime label.
	 */
	protected static final String I18N_FORMATION_INITIALE_SHORT_LAB = "REGIME.SHORT_LABEL.FI";

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5824297664288394001L;

	
	
	/*
	 *************************** PROPERTIES ******************************** */

	/*
	 *************************** INIT ************************************** */
	
	/**
	 * 
	 */
	public FormationInitiale() {
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
		Assert.notNull(this.getManagedCalendar(), "property managedCalendar of class " 
				+ this.getClass().getName() + " can not be null");
		setLabel(getI18nService().getString(I18N_FORMATION_INITIALE_LAB));
		setShortLabel(getI18nService().getString(I18N_FORMATION_INITIALE_SHORT_LAB));
		
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "FormationInitiale#" + hashCode() + "[" + super.toString() + "]";

	}
	

	/*
	 *************************** METHODS *********************************** */

	/** 
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#canModifyRISearch()
	 */
	@Override
	public boolean canModifyRISearch() {
		return true;
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#getDisplayInfoBac()
	 */
	@Override
	public boolean getDisplayInfoBac() {
		return true;
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#displayInfoSituation()
	 */
	@Override
	public boolean getDisplayInfoSituation() {
		return false;
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#getCanAlwaysAddVows()
	 */
	@Override
	public boolean getCanAlwaysAddVows() {
		return false;
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#getCalInsIsOpen()
	 */
	@Override
	public Boolean getCalInsIsOpen() {
		return getManagedCalendar().getCalInsIsOpen();
	}

	/**
	 * @see org.esupportail.opi.web.beans.parameters.web.beans.parameters.RegimeInscription#getDisplayInfoFC()
	 */
	@Override
	public boolean getDisplayInfoFC() {
		return false;
	}

	/*
	 *************************** ACCESSORS ********************************* */


}
