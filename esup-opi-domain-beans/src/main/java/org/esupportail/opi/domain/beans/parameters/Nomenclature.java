/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

import org.esupportail.opi.domain.beans.NormeSI;

/**
 * @author cleprous
 *
 */
public abstract class Nomenclature extends NormeSI {

	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7501505227052330671L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 *  The type of Nomenclature.
	 */
	private String type;
	
	/**
	 * The Nomenclature code.
	 */
	private String code;
	
	
	/*
	 ******************* INIT ************************* */

	
	
	/**
	 * Constructors.
	 */
	public Nomenclature() {
		super();
	}


	/**
	 * Return n with attributes of this.
	 * @param n
	 * @return Nomenclature
	 */
	protected Nomenclature clone(final Nomenclature n) {
		Nomenclature nomen = n;
		nomen = (Nomenclature) super.clone(nomen);
		nomen.setCode(code);
		nomen.setType(type);
		
		return nomen; 
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof Nomenclature)) { return false; }
		Nomenclature other = (Nomenclature) obj; 
		if (code == null) {
			if (other.getCode() != null) { return false; }
		} else if (!code.equals(other.getCode())) { return false; }
		if (type == null) {
			if (other.getType() != null) { return false; }
		} else if (!type.equals(other.getType())) { return false; }
		return true;
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Nomenclature#" + hashCode() 
			+ "[code=[" + code 
			+ "],[type=[" + type 
			+ "],  [" + super.toString() + "]]";
	}
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}


	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}


	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}


	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}


	
	

}
