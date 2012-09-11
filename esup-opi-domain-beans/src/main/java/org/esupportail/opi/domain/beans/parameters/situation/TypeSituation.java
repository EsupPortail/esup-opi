/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters.situation;

import java.io.Serializable;


/**
 * @author ylecuyer
 *
 */
public class TypeSituation  implements Serializable {

	/**
	 * Code de la situation de type salarié.
	 */
	public static final String TYPE_SAL = "sal";
	
	/**
	 * Code de la situation de type salarié.
	 */
	public static final String TYPE_PRO_LIB = "pro_lib";

	/**
	 * Code de la situation de type salarié.
	 */
	public static final String TYPE_DEM_EMP = "dem_emp";

	/**
	 * Code de la situation de type salarié.
	 */
	public static final String TYPE_ETU = "etu";

	/**
	 * Code de la situation de type salarié.
	 */
	public static final String TYPE_OTHER = "other";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 7969966213026907852L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The code.
	 */
	private String code;
	
	/**
	 * The label.
	 */
	private String label;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public TypeSituation() {
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TypeSituation#" + hashCode() + "[code = " + code + "], [label = " + label + "]]";

	}



	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		TypeSituation other = (TypeSituation) obj;
		if (code == null) {
			if (other.code != null) {  return false; }
		} else if (!code.equals(other.code)) { return false; }
		if (label == null) {
			if (other.label != null) { return false; }
		} else if (!label.equals(other.label)) { return false; }
		return true;
	}

	

	

	
	/*
	 ******************* METHODS ********************** */

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
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @param label the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
	}


	
}
