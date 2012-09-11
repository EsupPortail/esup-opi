/**
 * 
 */
package org.esupportail.opi.domain.beans.references.calendar;

import org.esupportail.opi.domain.beans.NormeSI;

import java.util.Date;



/**
 * @author cleprous
 * ReunionCmi : the meeting of commission.
 */
public class ReunionCmi extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 5292718805437166293L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * date of meeting.
	 */
	private Date date;
	
	/**
	 * Hour of meeting.
	 */
	private Date heure;

	/**
	 * Place of meeting.
	 */
	private String lieu;
	
	/**
	 * the CalendarCmi.
	 */
	private CalendarCmi calendar;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public ReunionCmi() {
		super();
	}

	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((heure == null) ? 0 : heure.hashCode());
		result = prime * result + ((lieu == null) ? 0 : lieu.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof ReunionCmi)) {	return false; }
		ReunionCmi other = (ReunionCmi) obj;
		if (date == null) {
			if (other.getDate() != null) { return false; }
		} else if (!date.equals(other.getDate())) { return false; }
		if (heure == null) {
			if (other.getHeure() != null) {	return false; }
		} else if (!heure.equals(other.getHeure())) { return false; }
		if (lieu == null) {
			if (other.getLieu() != null) { return false; }
		} else if (!lieu.equals(other.getLieu())) { return false; }
		return true;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReunionCmi#" + hashCode() + "[date=[" + date
				+ "],[heure=[" + heure
				+ "],[lieu=[" + lieu + "],  [" + super.toString() + "]]";
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */




	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(final Date date) {
		this.date = date;
	}

	/**
	 * @return the heure
	 */
	public Date getHeure() {
		return heure;
	}

	/**
	 * @param heure the heure to set
	 */
	public void setHeure(final Date heure) {
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
	public void setLieu(final String lieu) {
		this.lieu = lieu;
	}


	/**
	 * @return the calendar
	 */
	public CalendarCmi getCalendar() {
		return calendar;
	}


	/**
	 * @param calendar the calendar to set
	 */
	public void setCalendar(final CalendarCmi calendar) {
		this.calendar = calendar;
	}
	


}
