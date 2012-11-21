package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.references.rendezvous.VetCalendar;

/**
 * @author cgomez
 *
 */
public class VetCalendarPojo {
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * vetCalendar.
	 */
	private VetCalendar vetCalendar;
	
	/**
	 * libWebVet.
	 */
	private String libWebVet;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public VetCalendarPojo() {
		super();
	}
	
	/**
	 * Constructors.
	 * @param vetCalendar
	 * @param libWebVet
	 */
	public VetCalendarPojo(final VetCalendar vetCalendar, final String libWebVet) {
		super();
		this.vetCalendar = vetCalendar;
		this.libWebVet = libWebVet;
	}

	

	/*
	 ******************* METHODS ********************** */

	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return vetCalendar
	 */
	public VetCalendar getVetCalendar() {
		return vetCalendar;
	}
	
	/**
	 * 
	 * @param vetCalendar
	 */
	public void setVetCalendar(VetCalendar vetCalendar) {
		this.vetCalendar = vetCalendar;
	}
	
	/**
	 * 
	 * @return libWebVet
	 */
	public String getLibWebVet() {
		return libWebVet;
	}
	
	/**
	 * 
	 * @param libWebVet
	 */
	public void setLibWebVet(String libWebVet) {
		this.libWebVet = libWebVet;
	}
}
