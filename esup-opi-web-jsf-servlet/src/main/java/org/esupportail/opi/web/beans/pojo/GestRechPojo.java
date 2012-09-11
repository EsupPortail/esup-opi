package org.esupportail.opi.web.beans.pojo;


/**
 * @author tducreux
 *
 */
public class GestRechPojo {
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * name of gestionnaire searched.
	 */
	private String nom;
	
	/**
	 * prenom of gestionnaire searched.
	 */
	private String prenom;
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructor.
	 */
	public GestRechPojo() {
		super();
	}

	/*
	 ******************* METHODS ********************** */


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndRechPojo#" + hashCode() + "nom=[" + nom 
		+ "], prenom=[" + prenom 
		+ "],    ["
		+ super.toString() + "]]";
	}


	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(final String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(final String prenom) {
		this.prenom = prenom;
	}



}
