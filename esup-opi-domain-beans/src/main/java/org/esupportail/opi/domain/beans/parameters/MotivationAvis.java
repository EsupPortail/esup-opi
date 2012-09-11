/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;


/**
 * @author ylecuyer
 *
 */
public class MotivationAvis extends Nomenclature {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -1614306247537238849L;

	
	/*
	 ******************* PROPERTIES ******************* */


	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public MotivationAvis() {
		super();
	}

	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MotivationAvis#" + hashCode() 
			+ "],  [" + super.toString() + "]]";
	}
	

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public MotivationAvis clone() {
		MotivationAvis t = new MotivationAvis();
		t = (MotivationAvis) super.clone(t);
		return t; 
	}

	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

	
		
}
