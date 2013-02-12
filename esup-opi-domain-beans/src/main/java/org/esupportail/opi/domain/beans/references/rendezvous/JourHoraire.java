package org.esupportail.opi.domain.beans.references.rendezvous;


import org.esupportail.opi.domain.beans.NormeSI;

import java.io.Serializable;
import java.util.Date;



/**
 * @author Gomez
 *
 */
public class JourHoraire extends NormeSI implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 138565355277948903L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * 
	 */
	private Date dateDuJour;
	/**
	 * 
	 */
	private Date dateDebutMatin;
	/**
	 * 
	 */
	private Date dateDebutAmidi;
	/**
	 * 
	 */
	private Date dateFinMatin;
	/**
	 * 
	 */
	private Date dateFinAmidi;
	/**
	 * 
	 */
	private CalendarRDV calendrierRdv;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructeur vide pour hibernate.
	 */
	public JourHoraire() {
		// Constructeur vide pour hibernate
	}
	/**
	 * Constructor.
	 * @param dateDuJour
	 * @param calendrierRdv
	 */
	public JourHoraire(final Date dateDuJour, final CalendarRDV calendrierRdv) {
		this.dateDuJour = dateDuJour;
		this.calendrierRdv = calendrierRdv;
	}
	
	
	/*
	 ******************* METHODS ********************** */
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return dateDuJour
	 */
	public Date getDateDuJour() {
		return dateDuJour;
	}
	/**
	 * 
	 * @param dateDuJour
	 */
	public void setDateDuJour(final Date dateDuJour) {
		this.dateDuJour = dateDuJour;
	}
	/**
	 * @return the dateDebutMatin
	 */
	public Date getDateDebutMatin() {
		return dateDebutMatin;
	}
	/**
	 * @param dateDebutMatin the dateDebutMatin to set
	 */
	public void setDateDebutMatin(final Date dateDebutMatin) {
		this.dateDebutMatin = dateDebutMatin;
	}
	/**
	 * @return the dateDebutAmidi
	 */
	public Date getDateDebutAmidi() {
		return dateDebutAmidi;
	}
	/**
	 * @param dateDebutAmidi the dateDebutAmidi to set
	 */
	public void setDateDebutAmidi(final Date dateDebutAmidi) {
		this.dateDebutAmidi = dateDebutAmidi;
	}
	/**
	 * @return the dateFinMatin
	 */
	public Date getDateFinMatin() {
		return dateFinMatin;
	}
	/**
	 * @param dateFinMatin the dateFinMatin to set
	 */
	public void setDateFinMatin(final Date dateFinMatin) {
		this.dateFinMatin = dateFinMatin;
	}
	/**
	 * @return the dateFinAmidi
	 */
	public Date getDateFinAmidi() {
		return dateFinAmidi;
	}
	/**
	 * @param dateFinAmidi the dateFinAmidi to set
	 */
	public void setDateFinAmidi(final Date dateFinAmidi) {
		this.dateFinAmidi = dateFinAmidi;
	}
	/**
	 * @return the calendrierRdv
	 */
	public CalendarRDV getCalendrierRdv() {
		return calendrierRdv;
	}
	/**
	 * @param calendrierRdv
	 */
	public void setCalendrierRdv(final CalendarRDV calendrierRdv) {
		this.calendrierRdv = calendrierRdv;
	}
}
 
