/**
 * 
 */
package org.esupportail.opi.domain.beans.user.indcursus;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.Individu;

/**
 * @author cleprous
 *
 */
public class IndCursus extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 2269713438375938190L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 *  The type of Cursus.
	 */
	private String type;

	/**
	 * The year.
	 */
	private String annee;
	
	/**
	 * The comment.
	 */
	private String comment;
	
	/**
	 * The organisme.
	 */
	private String organisme;
	
	
	/**
	 * The duree.
	 */
	private String duree;
	
	/**
	 * The Individu.
	 */
	private Individu individu;
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public IndCursus() {
		super();
	}

	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Cursus#" + hashCode() + "[annee=[" + annee + "],[comment=[" + comment 
			+ "],[type=[" + type + "],[organisme=[" + organisme 
			+ "],[duree=[" + duree 
			+ "],  [" + super.toString() + "]]";
	}
	
	
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((annee == null) ? 0 : annee.hashCode());
		result = prime * result
				+ ((organisme == null) ? 0 : organisme.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		IndCursus other = (IndCursus) obj;
		if (annee == null) {
			if (other.getAnnee() != null) { return false; }
		} else if (!annee.equals(other.getAnnee())) { return false; }
		if (organisme == null) {
			if (other.getOrganisme() != null) { return false; }
		} else if (!organisme.equals(other.getOrganisme())) { return false; }
		if (type == null) {
			if (other.getType() != null) { return false; }
		} else if (!type.equals(other.getType())) { return false; }
		return true;
	}
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the annee
	 */
	public String getAnnee() {
		return annee;
	}

	/**
	 * @param annee the annee to set
	 */
	public void setAnnee(final String annee) {
		this.annee = annee;
	}

	

	/**
	 * @return the organisme
	 */
	public String getOrganisme() {
		return organisme;
	}

	/**
	 * @param organisme the organisme to set
	 */
	public void setOrganisme(final String organisme) {
		this.organisme = organisme;
	}
	
	/**
	 * @return the individu
	 */
	public Individu getIndividu() {
		return individu;
	}

	/**
	 * @param individu the individu to set
	 */
	public void setIndividu(final Individu individu) {
		this.individu = individu;
	}
	
	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}


	/**
	 * @param comment the comment to set
	 */
	public void setComment(final String comment) {
		this.comment = comment;
	}
	
	/**
	 * @return the duree
	 */
	public String getDuree() {
		return duree;
	}

	/**
	 * @param duree the duree to set
	 */
	public void setDuree(final String duree) {
		this.duree = duree;
	}
	

}
