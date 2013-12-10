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

/** AJOUT METHODES OVERRIDE **/
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codLang == null) ? 0 : codLang.hashCode());
		result = prime * result + ((codCles == null) ? 0 : codCles.hashCode());
		return result;
	}




	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cles2AnnuFormId other = (Cles2AnnuFormId) obj;
		if (codLang == null) {
			if (other.codLang != null)
				return false;
		} else if (!codLang.equals(other.codLang))
			return false;
		if (codCles == null) {
			if (other.codCles != null)
				return false;
		} else if (!codCles.equals(other.codCles))
			return false;
		return true;
	}
	/*
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
