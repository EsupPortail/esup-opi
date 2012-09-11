/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

/**
 * @author cleprous
 *
 */
public class AccesSelectif extends TypeTraitement {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -1515713231267794196L;


	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ****************** INIT ************************* */

	/**
	 * Constructors.
	 */
	public AccesSelectif() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccesSelectif#" + hashCode() + "[" + super.toString() + "]]";

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
