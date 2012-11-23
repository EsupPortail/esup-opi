/**
* CRI - UniversitÃ© de Rennes1 - 57SI-OPIR1 - 2008
* https://subversion.univ-rennes1.fr/repos/57SI-apo-cri-webservice
* Version de la norme de dÃ©veloppement : 0.9.0
*/
package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;


/**
 * @author cleprous
 * Domaine2AnnuForm : A Domain is a group of key word. 
 */
public class Domaine2AnnuForm implements Serializable {


	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serializable id. 
	 */
	private static final long serialVersionUID = 3557205409418501633L;
	
	/**
	 * Code Lang.
	 */
	private String codLang;
	
	/**
	 * Code Domaine. 
	 */
	private String codDom;
	
	/**
	 * Libelle domaine.
	 */
	private String libDom;
	
	/**
	 * Temoin en Service des domaines.
	 */
	private DomaineAnnuForm DomaineAnnuForm;
	
	
	/*
	 ******************* INIT ******************* */
	
	/**
	 * Constructor.
	 */
	public Domaine2AnnuForm() {
		super();
	}
	
	public Domaine2AnnuForm(String codLang, String codDom, String libDom,
			DomaineAnnuForm DomaineAnnuForm) {
		this.codLang = codLang;
		this.codDom = codDom;
		this.libDom = libDom;
		this.DomaineAnnuForm = DomaineAnnuForm;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "DomaineAnnuForm#" + hashCode() + "[codDom=[" + codDom 
		+ "], codLang=[" + codLang 
		+ "], libDom=[" + libDom + "]]";
	}
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((libDom == null) ? 0 : libDom.hashCode());
		result = prime * result + ((codDom == null) ? 0 : codDom.hashCode());
		result = prime * result + ((codLang == null) ? 0 : codLang.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof Domaine2AnnuForm)) { return false; }
		Domaine2AnnuForm other = (Domaine2AnnuForm) obj;
		if (libDom == null) {
			if (other.libDom != null) {	return false; }
		} else if (!libDom.equals(other.libDom)) {	return false; }
		if (codDom == null) {
			if (other.codDom != null) {	return false; }
		} else if (!codDom.equals(other.codDom)) {	return false; }
		if (codLang == null) {
			if (other.codLang != null) {	return false; }
		} else if (!codLang.equals(other.codLang)) {	return false; }
		if (DomaineAnnuForm == null) {
			if (other.DomaineAnnuForm != null) { return false; }
		} else if (!DomaineAnnuForm.equals(other.DomaineAnnuForm)) { return false; }
		return true;
	}
	
	/*
	 ******************* ACCESSORS ******************* */
	
	

	/**
	 * @return the libDom
	 */
	public String getLibDom() {
		return libDom;
	}
	/**
	 * @param libDom the libDom to set
	 */
	public void setLibDom(final String libDom) {
		this.libDom = libDom;
	}

	

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

	/**
	 * @return the DomaineAnnuForm
	 */
	public DomaineAnnuForm getDomaineAnnuForm() {
		return DomaineAnnuForm;
	}

	/**
	 * @param DomaineAnnuForm the DomaineAnnuForm to set
	 */
	public void setDomaineAnnuForm(final DomaineAnnuForm DomaineAnnuForm) {
		this.DomaineAnnuForm = DomaineAnnuForm;
	}

	

}
