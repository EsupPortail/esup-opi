/**
 * 
 */
package org.esupportail.opi.domain.beans.references.calendar;

import org.esupportail.opi.domain.beans.references.commission.Commission;


import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * @author cleprous
 *
 */
public class CalendarIns extends Calendar {


	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 8595199822776399766L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The start date.
	 */
	private Date startDate;
	
	/**
	 * The end date.
	 */
	private Date endDate;
	
	/**
	 * The commissions.
	 */
	private Set<Commission> commissions;
	
	/*
	 ******************* INIT ************************* */


	/**
	 * Constructors.
	 */
	public CalendarIns() {
		super();
		commissions = new HashSet<Commission>();
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendarIns#" + hashCode() + "[startDate=[" + startDate
				+ "],[endDate=[" + endDate
				+ "],  [" + super.toString() + "]]";
	}
	
	
	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CalendarIns clone() {
		CalendarIns c = new CalendarIns();
		c = (CalendarIns) super.clone(c);
		c.setStartDate(startDate);
		c.setEndDate(endDate);
		c.setCommissions(commissions);
		
		return c; 
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}


	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}


	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}


	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}


	/**
	 * @return the commissions
	 */
	public Set<Commission> getCommissions() {
		return commissions;
	}


	/**
	 * @return the commissions
	 */
	public List<Commission> getCommissionsAsList() {
		return new ArrayList<Commission>(commissions);
	}


	/**
	 * @param commissions the commissions to set
	 */
	public void setCommissions(final Set<Commission> commissions) {
		this.commissions = commissions;
	}

}
