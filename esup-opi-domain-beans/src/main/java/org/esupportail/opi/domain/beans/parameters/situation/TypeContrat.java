/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters.situation;

import java.io.Serializable;


/**
 * @author ylecuyer
 *
 */
public class TypeContrat  implements Serializable {

	/**
	 * Code du contrat de type CDD.
	 */
	public static final String COD_CDD = "CDD";
	
	/**
	 * Code du contrat de type CDI.
	 */
	public static final String COD_CDI = "CDI";
	
	/**
	 * Code du contrat de type Autre.
	 */
	public static final String COD_AUTRE = "autre";

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 5813002694113685185L;

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
	public TypeContrat() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TypeContrat#" + hashCode() + "[code = " + code + "], [label = " + label + "]]";

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
		TypeContrat other = (TypeContrat) obj;
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
