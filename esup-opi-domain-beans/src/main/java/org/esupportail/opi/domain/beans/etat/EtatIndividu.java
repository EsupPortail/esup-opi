/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;



/**
 * @author cleprous
 *
 */
public abstract class EtatIndividu extends Etat {


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
	public EtatIndividu() {
		super();
	}
	
	

	/*
	 ******************* METHODS ********************** */

	/**
	 * if incomplet can not make a vows.
	 * @return Boolean true if this is EtatComplet
	 */
	public abstract Boolean getCanDoChoiceEtape(); 
	
	/*
	 ******************* ACCESSORS ******************** */
	
}
