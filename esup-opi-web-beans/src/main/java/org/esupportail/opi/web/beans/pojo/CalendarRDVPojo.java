package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;

import java.util.List;


/**
 * @author ylecuyer
 *
 */
public class CalendarRDVPojo {
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * calendarRDV.
	 */
	private CalendarRDV calendarRDV;

	/**
	 * List of vetCalendarPojo.
	 */
	private List<VetCalendarPojo> listVetCalendarPojo;
	
	/**
	 * List of commissionPojo corresponding to the list.
	 * of commissions of autoLp
	 */
	private List<CommissionPojo> listCommissionPojo;
	

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public CalendarRDVPojo() {
		super();
	}



	/**
	 * Constructors.
	 * @param calendarRDV
	 * @param listVetCalendarPojo
	 * @param listCommissionPojo
	 */
	public CalendarRDVPojo(final CalendarRDV calendarRDV,
			final List<VetCalendarPojo> listVetCalendarPojo,
			final List<CommissionPojo> listCommissionPojo) {
		super();
		this.calendarRDV = calendarRDV;
		this.listVetCalendarPojo = listVetCalendarPojo;
		this.listCommissionPojo = listCommissionPojo;
	}



	/*
	 ******************* METHODS ********************** */

	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the calendarRDV
	 */
	public CalendarRDV getCalendarRDV() {
		return calendarRDV;
	}
	/**
	 * @param calendarRDV the calendarRDV to set
	 */
	public void setCalendarRDV(final CalendarRDV calendarRDV) {
		this.calendarRDV = calendarRDV;
	}
	
	/**
	 * @return the listVetCalendarPojo
	 */
	public List<VetCalendarPojo> getListVetCalendarPojo() {
		return listVetCalendarPojo;
	}
	/**
	 * @param listVetCalendarPojo the listVetCalendarPojo to set
	 */
	public void setListVetCalendarPojoo(final List<VetCalendarPojo> listVetCalendarPojo) {
		this.listVetCalendarPojo = listVetCalendarPojo;
	}
	
	/**
	 * @return the listCommissionPojo
	 */
	public List<CommissionPojo> getListCommissionPojo() {
		return listCommissionPojo;
	}
	/**
	 * @param listCommissionPojo the listCommissionPojo to set
	 */
	public void setListCommissionPojo(final List<CommissionPojo> listCommissionPojo) {
		this.listCommissionPojo = listCommissionPojo;
	}
	
	
}
