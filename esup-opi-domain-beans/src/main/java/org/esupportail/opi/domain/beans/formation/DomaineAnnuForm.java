package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;


public class DomaineAnnuForm implements Serializable {


	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serializable id. 
	 */
	private static final long serialVersionUID = 1537657389170538363L;

	/**
	 * Code Domaine. 
	 */
	private String codDom;
	
	/**
	 * Temoin en Service des domaines.
	 */
	private String temSveDom;
	
	/*
	 ******************* INIT ******************* */
	
	/**
	 * Constructor.
	 */
	public DomaineAnnuForm() {
		super();
	}
	
	public DomaineAnnuForm(String codDom, String temSveDom) {
		this.codDom = codDom;
		this.temSveDom = temSveDom;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DomaineAnnuForm#" + hashCode() + "[codDom=[" + codDom 
		+ "], temSveDom=[" + temSveDom + "]]";
	}
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codDom == null) ? 0 : codDom.hashCode());
		result = prime * result
				+ ((temSveDom == null) ? 0 : temSveDom.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof DomaineAnnuForm)) { return false; }
		DomaineAnnuForm other = (DomaineAnnuForm) obj;
		if (codDom == null) {
			if (other.getCodDom() != null) {	return false; }
		} else if (!codDom.equals(other.getCodDom())) {	return false; }
		if (temSveDom == null) {
			if (other.getTemSveDom() != null) { return false; }
		} else if (!temSveDom.equals(other.getTemSveDom())) { return false; }
		return true;
	}
	
	/*
	 ******************* ACCESSORS ******************* */
	
	


	/**
	 * @return the temSveDom
	 */
	public String getTemSveDom() {
		return temSveDom;
	}

	/**
	 * @param temSveDom the temSveDom to set
	 */
	public void setTemSveDom(final String temSveDom) {
		this.temSveDom = temSveDom;
	}

	/**
	 * @return the codDom
	 */
	public String getCodDom() {
		return codDom;
	}

	/**
	 * @param codDom the codDom to set
	 */
	public void setCodDom(final String codDom) {
		this.codDom = codDom;
	}

	

}
