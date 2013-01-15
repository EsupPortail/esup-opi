/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

/**
 * @author cleprous
 *
 */
public class ListeComplementaire extends TypeConvocation {

	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public ListeComplementaire() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ListeComplementaire#" + hashCode() 
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
