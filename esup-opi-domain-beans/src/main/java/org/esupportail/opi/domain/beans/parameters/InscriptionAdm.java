/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

/**
 * @author cleprous
 *
 */
public class InscriptionAdm extends TypeConvocation {

	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public InscriptionAdm() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "InscriptionAdm#" + hashCode() 
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
