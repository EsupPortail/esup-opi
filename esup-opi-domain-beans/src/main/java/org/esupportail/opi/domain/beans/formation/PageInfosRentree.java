/**
* CRI - Université de Rennes1 - 57SI-OPIR1 - 2008
* https://subversion.univ-rennes1.fr/repos/57SI-apo-cri-webservice
* Version de la norme de développement : 0.9.0
*/
package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;


/**
 * @author gmartel
 */
public class PageInfosRentree implements Serializable {


	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2883851027082894781L;

	/**
	 * Code Centre de Gestion (CGE). 
	 */
	private String codCge;
	
	/**
	 * Texte de la page d'infos
	 */
	private String texte;
	
	
	/*
	 ******************* INIT ******************* */
	
	/**
	 * Constructor.
	 */
	public PageInfosRentree() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PageInfosRentree#" + hashCode() + "[codCge=[" + codCge 
		+ "], texte=[" + texte.substring(0,50) + "...]]";
	}
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codCge == null) ? 0 : codCge.hashCode());
		result = PRIME * result + ((texte == null) ? 0 : texte.hashCode());
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
		final PageInfosRentree other = (PageInfosRentree) obj;
		if (codCge == null) {
			if (other.codCge != null)
				return false;
		} else if (!codCge.equals(other.codCge))
			return false;
		if (texte == null) {
			if (other.texte != null)
				return false;
		} else if (!texte.equals(other.texte))
			return false;
		return true;
	}


	
	/*
	 ******************* ACCESSORS ******************* */
	

	/**
	 * @return the codCge
	 */
	public String getCodCge() {
		return codCge;
	}

	/**
	 * @param codCge the codCge to set
	 */
	public void setCodCge(String codCge) {
		this.codCge = codCge;
	}

	/**
	 * @return the texte
	 */
	public String getTexte() {
		return texte;
	}

	/**
	 * @param texte the texte to set
	 */
	public void setTexte(String texte) {
		this.texte = texte;
	}
	

}
