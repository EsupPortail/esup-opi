package org.esupportail.opi.web.beans.pojo;


/**
 * @author tducreux
 *
 */
public class CommisionRechPojo {
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * code of commission searched.
	 */
	private String code;
	
	/**
	 * libelle of commission searched.
	 */
	private String libelle;
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructor.
	 */
	public CommisionRechPojo() {
		super();
	}

	/*
	 ******************* METHODS ********************** */


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndRechPojo#" + hashCode() + "code=[" + code 
		+ "], libelle=[" + libelle 
		+ "],    ["
		+ super.toString() + "]]";
	}



	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}



}
