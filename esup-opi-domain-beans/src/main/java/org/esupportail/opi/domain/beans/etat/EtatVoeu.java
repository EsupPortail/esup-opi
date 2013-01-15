/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;



/**
 * @author cleprous
 *  TODO : shouldn't extend Etat which is a Factory
 */
public abstract class EtatVoeu extends Etat {


	/**
	 * the serialization id.
	 */
	private static final long serialVersionUID = 3974987677907095406L;



	

	/*
	 ******************* PROPERTIES ******************* */

	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public EtatVoeu() {
		super();
	}
	
	

	/*
	 ******************* METHODS ********************** */

	/**
	 * Display the missing piece if etat is EtatArriveIncomplet.
	 * @return Boolean
	 */
	public Boolean getDisplayPJ() {
		if (this instanceof EtatArriveIncomplet) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * @return Boolean
	 */
	public Boolean getDisplayForms() {
		return true;
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	
}
