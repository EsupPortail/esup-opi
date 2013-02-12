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
public class PageInfosExam implements Serializable {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * 
	 */
	private static final long serialVersionUID = 203369147976109662L;


	/**
	 * Code Centre d'incompatibilité. 
	 */
	private String codCin;
	
	/**
	 * Texte de la page d'infos.
	 */
	private String texte;
	
	
	/*
	 ******************* INIT ******************* */
	
	/**
	 * Constructor.
	 */
	public PageInfosExam() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PageInfosExam#" + hashCode() + "[codCin=[" + codCin + "], texte=[" + texte.substring(0,50) + "...]]";
	}
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codCin == null) ? 0 : codCin.hashCode());
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
		final PageInfosExam other = (PageInfosExam) obj;
		if (codCin == null) {
			if (other.codCin != null)
				return false;
		} else if (!codCin.equals(other.codCin))
			return false;
		return true;
	}

	
	
	/*
	 ******************* ACCESSORS ******************* */


	/**
	 * @return the codCin
	 */
	public String getCodCin() {
		return codCin;
	}

	/**
	 * @param codCin the codCin to set
	 */
	public void setCodCin(String codCin) {
		this.codCin = codCin;
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
