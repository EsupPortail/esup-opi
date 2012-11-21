/**
 * 
 */
package org.esupportail.opi.web.beans;

import org.esupportail.opi.domain.beans.references.calendar.Calendar;
import org.esupportail.opi.domain.beans.references.calendar.CalendarCmi;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;

/**
 * @author cleprous
 *
 */
public class BeanCalendar {

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The calendar.
	 */
	private Calendar calendar;
	
	/**
	 * The type of calendar selected.
	 */
	private String typeSelected;
	
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public BeanCalendar() {
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BeanCalendar#" + hashCode() + "[calendar=[" + calendar
				+ "],[typeSelected=[" + typeSelected + "]]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the calendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}

	/**
	 * @param calendar the calendar to set
	 */
	public void setCalendar(final Calendar calendar) {
		//Clone est utilise afin que l'utilisateur puisse modifier l'objet sans toucher au CACHE (par reference)
		//Probleme rencontre lors du modification annulee(par exemple), le cache etait tout de meme modifier
		if (calendar instanceof CalendarCmi) {
			CalendarCmi c = (CalendarCmi) calendar;
			this.calendar = c.clone();
		} else if (calendar instanceof CalendarIns) {
			CalendarIns c = (CalendarIns) calendar;
			this.calendar = c.clone();
		} 
		
	}

	/**
	 * @return the typeSelected
	 */
	public String getTypeSelected() {
		return typeSelected;
	}

	/**
	 * @param typeSelected the typeSelected to set
	 */
	public void setTypeSelected(final String typeSelected) {
		this.typeSelected = typeSelected;
	}
	
	

}
