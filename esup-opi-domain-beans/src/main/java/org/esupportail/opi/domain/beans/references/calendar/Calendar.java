/**
 * 
 */
package org.esupportail.opi.domain.beans.references.calendar;

import org.esupportail.opi.domain.beans.NormeSI;

/**
 * @author cleprous
 *
 */
public abstract class Calendar extends NormeSI {

	
	/**
	 * The type for inscription calendar.
	 */
	public static final String TYPE_CAL_INSCRIPTION = "CAL_INS";
	
	/**
	 * The type for inscription calendar.
	 */
	public static final String TYPE_CAL_COMMISSION = "CAL_CMI";

	/**
	 * 
	 */
	private static final long serialVersionUID = 5855317636164655755L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The code of calendar.
	 */
	private String code;
	
	/**
	 * The type of calendar.
	 */
	private String type;
	
	/*
	 ******************* INIT ************************* */


	/**
	 * Constructors.
	 */
	public Calendar() {
		super();
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Calendar#" + hashCode() + "[code=[" + code
				+ "],[type=[" + type
				+ "],  [" + super.toString() + "]]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/**
	 * Return t with attributes of this.
	 * @param c
	 * @return Calendar
	 */
	protected Calendar clone(final Calendar c) {
		Calendar cal = c;
		cal = (Calendar) super.clone(cal);
		cal.setCode(getCode());
		cal.setType(type);
		
		return cal; 
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

}
