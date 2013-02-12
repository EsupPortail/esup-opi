/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;


/**
 * @author cleprous
 *
 */
public class TypeConvocation {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4488846342380474281L;

	
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
	
	/**
	 * The short label.
	 */
	private String shortLabel;
	

	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public TypeConvocation() {
		super();
	}

	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TypeConvocation#" + hashCode() + "[code = " + code 
				+ "], [label = " + label + "], [shortLabel = " + shortLabel + "]]";

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
		if (getClass() != obj.getClass()) {	return false; }
		TypeConvocation other = (TypeConvocation) obj;
		if (code == null) {
			if (other.code != null) { return false; }
		} else if (!code.equals(other.code)) { return false; }
		if (label == null) {
			if (other.label != null) { return false; }
		} else if (!label.equals(other.label)) { return false; }
		return true;
	}

	
	/*
	 ******************* METHODS ********************** */

	/**
	 * @return true if the type de convocation est modifiable.
	 * une fois l'avis validé
	 */
	public boolean isModifiable() {
		return true;
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



	/**
	 * @return the shortLabel
	 */
	public String getShortLabel() {
		return shortLabel;
	}



	/**
	 * @param shortLabel the shortLabel to set
	 */
	public void setShortLabel(final String shortLabel) {
		this.shortLabel = shortLabel;
	}



	
	

	
		
}
