/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

/**
 * @author cleprous
 *
 */
public class ValidationAcquis extends TypeTraitement {


	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7675331482821300739L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ****************** INIT ************************* */


	

	/**
	 * Constructors.
	 */
	public ValidationAcquis() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ValidationAcquis#" + hashCode() + "[" + super.toString() + "]]";

	}

	
	
	/*
	 ******************* METHODS ********************** */

	/** 
	 * @see org.esupportail.opi.domain.beans.parameters.web.beans.parameters.TypeTraitement#getDownloadDocument()
	 */
	@Override
	public Boolean getDownloadDocument() {
		return true;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

}
