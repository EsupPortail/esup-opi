package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;


public class Cles2AnnuForm implements Serializable {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serializable id. 
	 */
	private static final long serialVersionUID = -2522776742728884950L;
	
	
	/**
	 * Composite id.
	 */
	private Cles2AnnuFormId id;
	
	/**
	 * Liaison en Domain et mot clef.
	 */
	private ClesAnnuForm clesAnnuForm;
	
	/**
	 * Lib Mot clef.
	 */
	private String libCles;
	

	/*
	 ******************* INIT ******************* */

	/**
	 * Constructor.
	 */
	public Cles2AnnuForm() {
		super();
	}

	public Cles2AnnuForm(Cles2AnnuFormId id, ClesAnnuForm clesAnnuForm,
			String libCles) {
		this.id = id;
		this.clesAnnuForm = clesAnnuForm;
		this.libCles = libCles;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cles2AnnuForm#" + hashCode() + "[id=[" + id 
		+ "], ClesAnnuForm=[" + clesAnnuForm 
		+ "], libCles=[" + libCles + "]]";
	}

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((libCles == null) ? 0 : libCles.hashCode());
		result = prime
				* result
				+ ((clesAnnuForm == null) ? 0 : clesAnnuForm.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof Cles2AnnuForm)) { return false; }
		Cles2AnnuForm other = (Cles2AnnuForm) obj;
		if (id == null) {
			if (other.id != null) {	return false; }
		} else if (!id.equals(other.id)) { return false; }
		if (libCles == null) {
			if (other.libCles != null) { return false; }
		} else if (!libCles.equals(other.libCles)) { return false; }
		if (clesAnnuForm == null) {
			if (other.clesAnnuForm != null) { return false; }
		} else if (!clesAnnuForm.equals(other.clesAnnuForm)) { return false; }
		return true;
	}

	/*
	 ******************* ACCESSORS ******************* */

	

	/**
	 * @return the id
	 */
	public Cles2AnnuFormId getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(final Cles2AnnuFormId id) {
		this.id = id;
	}


	/**
	 * @return the ClesAnnuForm
	 */
	public ClesAnnuForm getClesAnnuForm() {
		return clesAnnuForm;
	}


	/**
	 * @param ClesAnnuForm the ClesAnnuForm to set
	 */
	public void setClesAnnuForm(final ClesAnnuForm ClesAnnuForm) {
		this.clesAnnuForm = ClesAnnuForm;
	}


	/**
	 * @return the libCles
	 */
	public String getLibCles() {
		return libCles;
	}


	/**
	 * @param libCles the libCles to set
	 */
	public void setLibCles(final String libCles) {
		this.libCles = libCles;
	}
	

	

}
