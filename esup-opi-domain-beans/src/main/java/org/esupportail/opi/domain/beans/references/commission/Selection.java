/**
 * 
 */
package org.esupportail.opi.domain.beans.references.commission;

import org.esupportail.opi.domain.beans.NormeSI;




/**
 * @author cleprous
 * Selection : information to the test selection. 
 */
public class Selection extends NormeSI {

	
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8427253093980927710L;

	/*
	 ******************* PROPERTIES ******************* */


	
	/**
	 * Place. 
	 */
	private String place;
	
	/**
	 * Periode admissibilite.
	 */
	private String periodeAdmissibilite;
	
	/**
	 * result period selection.
	 */
	private String resultSelection;
	
	/**
	 * The comment.
	 */
	private String comment;
	
	
	/*
	 ******************* INIT ************************* */

	
	
	/**
	 * Constructors.
	 */
	public Selection() {
		super();
	}
	

	
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((place == null) 
				? 0 : place.hashCode());
		result = prime * result + ((periodeAdmissibilite == null) ? 0 : periodeAdmissibilite.hashCode());
		result = prime * result + ((resultSelection == null) ? 0 : resultSelection.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof Selection)) { return false; }
		Selection other = (Selection) obj;
		if (place == null) {
			if (other.getPlace() != null) {	return false; }
		} else if (!place.equals(other.getPlace())) { return false; }
		if (periodeAdmissibilite == null) {
			if (other.getPeriodeAdmissibilite() != null) {	return false; }
		} else if (!periodeAdmissibilite.equals(other.getPeriodeAdmissibilite())) { return false; }
		if (resultSelection == null) {
			if (other.getResultSelection() != null) {	return false; }
		} else if (!resultSelection.equals(other.getResultSelection())) { return false; }
		if (comment == null) {
			if (other.getComment() != null) {	return false; }
		} else if (!comment.equals(other.getComment())) { return false; }
		return true;
	}
	
	
	
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "Selection#" + hashCode() 
		+ "[resultSelection=[" + resultSelection + "[periodeAdmissibilite=[" + periodeAdmissibilite
		+ "],[place=[" + place + "],[comment=[" + comment + "]";
		
		s += ",  [" + super.toString() + "]]";
		return s;
	}
	
	
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	
	


	/**
	 * @return the periodeAdmissibilite
	 */
	public String getPeriodeAdmissibilite() {
		return periodeAdmissibilite;
	}

	/**
	 * @param periodeAdmissibilite the periodeAdmissibilite to set
	 */
	public void setPeriodeAdmissibilite(final String periodeAdmissibilite) {
		this.periodeAdmissibilite = periodeAdmissibilite;
	}

	/**
	 * @return the resultSelection
	 */
	public String getResultSelection() {
		return resultSelection;
	}

	/**
	 * @param resultSelection the resultSelection to set
	 */
	public void setResultSelection(final String resultSelection) {
		this.resultSelection = resultSelection;
	}





	/**
	 * @return the place
	 */
	public String getPlace() {
		return place;
	}





	/**
	 * @param place the place to set
	 */
	public void setPlace(final String place) {
		this.place = place;
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




	
	
}
