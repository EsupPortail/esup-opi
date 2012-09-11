/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

/**
 * @author ylecuyer
 * Intermediary :
 */
public class Intermediary extends TypeConvocation {

	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public Intermediary() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Intermediary#" + hashCode() 
			+ "[" + super.toString() + "]]";
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 * @see org.esupportail.opi.domain.beans.parameters.web.beans.parameters.TypeConvocation#
	 * isModifiable()
	 */
	@Override
	public boolean isModifiable() {
		return true;
	}
	
	
	
	/*
	 ******************* ACCESSORS ******************** */

}
