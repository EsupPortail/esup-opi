/**
* CRI - Université de Rennes1 - 57SI-OPIR1 - 2008
* https://subversion.univ-rennes1.fr/repos/57SI-apo-cri-webservice
* Version de la norme de développement : 0.9.0
*/
package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;
import java.sql.Date;


/**
 * @author gmartel
 */
public class CalendRentree implements Serializable {


	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5916544183819625115L;

	
	/**
	 * Code Centre de Gestion (CGE).
	 */
	private String codCge;

	/**
	 * Code Etape. 
	 */
	private String codEtp;
	
	/**
	 * Code Version Etape. 
	 */
	private Integer codVrsVet;
	
	/**
	 * Date de rentrée.
	 */
	private Date date;
	
	/**
	 * Heure de rentrée.
	 */
	private Integer heure;
	
	/**
	 * Minute de Rentrée.
	 */
	private Integer minute;
	
	/**
	 * Lieu de rentrée.
	 */
	private String lieu;
	
	/**
	 * Témoin affichage du calendrier (O/N).
	 */
	private String temoinAffichage;
	
	/**
	 * Commentaire.
	 */
	private String commentaire;
	
	
	/*
	 ******************* INIT ******************* */
	
	/**
	 * Constructor.
	 */
	public CalendRentree() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendRentree#" + hashCode() + "[codCge=[" + codCge + "], codEtp=[" + codEtp 
		+ "], codVrsVet=[" + codVrsVet.toString() + "], date=[" + date.toString() + "], heure=["
		+ heure.toString() + ":" + minute.toString() + "], lieu=" + lieu + "], commentaire=["
		+ commentaire +"]]";
	}
	
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = 1;
		result = PRIME * result + ((codCge == null) ? 0 : codCge.hashCode());
		result = PRIME * result + ((codEtp == null) ? 0 : codEtp.hashCode());
		result = PRIME * result + ((codVrsVet == null) ? 0 : codVrsVet.hashCode());
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
		final CalendRentree other = (CalendRentree) obj;
		if (codCge == null) {
			if (other.codCge != null)
				return false;
		} else if (!codCge.equals(other.codCge))
			return false;
		if (codEtp == null) {
			if (other.codEtp != null)
				return false;
		} else if (!codEtp.equals(other.codEtp))
			return false;
		if (codVrsVet == null) {
			if (other.codVrsVet != null)
				return false;
		} else if (!codVrsVet.equals(other.codVrsVet))
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
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(String commentaire) {
		this.commentaire = commentaire;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/**
	 * @return the heure
	 */
	public Integer getHeure() {
		return heure;
	}

	/**
	 * @param heure the heure to set
	 */
	public void setHeure(Integer heure) {
		this.heure = heure;
	}

	/**
	 * @return the lieu
	 */
	public String getLieu() {
		return lieu;
	}

	/**
	 * @param lieu the lieu to set
	 */
	public void setLieu(String lieu) {
		this.lieu = lieu;
	}

	/**
	 * @return the minute
	 */
	public Integer getMinute() {
		return minute;
	}

	/**
	 * @param minute the minute to set
	 */
	public void setMinute(Integer minute) {
		this.minute = minute;
	}

	/**
	 * @return the temoinAffichage
	 */
	public String getTemoinAffichage() {
		return temoinAffichage;
	}

	/**
	 * @param temoinAffichage the temoinAffichage to set
	 */
	public void setTemoinAffichage(String temoinAffichage) {
		this.temoinAffichage = temoinAffichage;
	}

	/**
	 * @return the codEtp
	 */
	public String getCodEtp() {
		return codEtp;
	}

	/**
	 * @param codEtp the codEtp to set
	 */
	public void setCodEtp(final String codEtp) {
		this.codEtp = codEtp;
	}

	/**
	 * @return the codVrsVet
	 */
	public Integer getCodVrsVet() {
		return codVrsVet;
	}

	/**
	 * @param codVrsVet the codVrsVet to set
	 */
	public void setCodVrsVet(final Integer codVrsVet) {
		this.codVrsVet = codVrsVet;
	}
	

}
