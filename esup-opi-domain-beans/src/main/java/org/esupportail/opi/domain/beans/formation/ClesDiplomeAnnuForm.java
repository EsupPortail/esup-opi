package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;


public class ClesDiplomeAnnuForm implements Serializable {


	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serializable id. 
	 */
	private static final long serialVersionUID = 3020016458749222536L;
	
	/**
	 * Code mot clef.
	 */
	private String codCles;
	
	/**
	 * code Diplome.
	 */
	private String codDip;
	

	/*
	 ******************* INIT ******************* */
	
	/**
	 * 
	 * Constructor.
	 */
	public ClesDiplomeAnnuForm() {
		super();
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClesDiplomeAnnuForm#" + hashCode() + "[codCles=[" + codCles 
		+ "], codDip=[" + codDip + "]]";
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codCles == null) ? 0 : codCles.hashCode());
		result = prime * result + ((codDip == null) ? 0 : codDip.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof ClesDiplomeAnnuForm)) { return false; }
		ClesDiplomeAnnuForm other = (ClesDiplomeAnnuForm) obj;
		if (codCles == null) {
			if (other.codCles != null) { return false; }
		} else if (!codCles.equals(other.codCles)) { return false; }
		if (codDip == null) {
			if (other.codDip != null) {	return false; }
		} else if (!codDip.equals(other.codDip)) { return false; }
		return true;
	}
	
	
	
	
	/*
	 ******************* ACCESSORS ******************* */
	
	


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
	/**
	 * @return the codDip
	 */
	public String getCodDip() {
		return codDip;
	}
	/**
	 * @param codDip the codDip to set
	 */
	public void setCodDip(final String codDip) {
		this.codDip = codDip;
	}


}
