/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

/**
 * @author cleprous
 *
 */
public class Refused extends TypeConvocation {

	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public Refused() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Refuse#" + hashCode() 
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
		return false;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

}
