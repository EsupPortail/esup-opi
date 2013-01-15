/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters.accessRight;

import java.util.Set;

/**
 * @author cleprous
 *
 */
public class Domain extends Traitement {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 8891235007735480905L;

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The fonctions.
	 */
	private Set<Fonction> fonctions;
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public Domain() {
		super();
	}

	

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Domain clone() {
		Domain d = new Domain();
		d = (Domain) super.clone(d);
		d.setFonctions(fonctions);
		
		return d; 
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Domain#" + hashCode() + "["+ super.toString() + "]]";
	}
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the fonctions
	 */
	public Set<Fonction> getFonctions() {
		return fonctions;
	}


	/**
	 * @param fonctions the fonctions to set
	 */
	public void setFonctions(Set<Fonction> fonctions) {
		this.fonctions = fonctions;
	}


}
