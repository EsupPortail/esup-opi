/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

/**
 * @author cleprous
 *
 */
public class Preselection extends TypeConvocation {

	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public Preselection() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Preselection#" + hashCode() 
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
