package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;


public class Cles2AnnuFormId implements Serializable {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serializable id. 
	 */
	private static final long serialVersionUID = 7307731758282942956L;

	/**
	 * The cod language Mot clef.
	 */
	private String codLang;
	
	/**
	 * The code Mot clef.
	 */
	private String codCles;
	
	/*
	 ******************* INIT ******************* */
	
	/**
	 * Constructor.
	 */
	public Cles2AnnuFormId() {
		super();
	}
	
	
	public Cles2AnnuFormId(String codLang, String codCles) {
		this.codLang = codLang;
		this.codCles = codCles;
	}

	/*
	 ******************* ACCESSORS ******************* */

	/**
	 * @return the codLang
	 */
	public String getCodLang() {
		return codLang;
	}
	/**
	 * @param codLang the codLang to set
	 */
	public void setCodLang(final String codLang) {
		this.codLang = codLang;
	}
	/**
	 * @return the codCles
	 */
	public String getCodCles() {
		return codCles;
	}
	/**
	 * @param codCles the codCles to set
	 */
	public void setCodCles(final String codCles) {
		this.codCles = codCles;
	}


}
