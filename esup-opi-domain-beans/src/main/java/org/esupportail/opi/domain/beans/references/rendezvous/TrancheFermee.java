package org.esupportail.opi.domain.beans.references.rendezvous;


import org.esupportail.opi.domain.beans.NormeSI;

import java.io.Serializable;
import java.util.Date;



/**
 * @author Gomez
 *
 */
public class TrancheFermee extends NormeSI implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 2226106991090248152L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * 
	 */
	private Date dateFermeture;
	/**
	 * 
	 */
	private boolean matin;
	/**
	 * 
	 */
	private boolean aprem;
	/**
	 * 
	 */
	private CalendarRDV calendrierRdv;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructeur vide pour hibernate.
	 */
	public TrancheFermee() {
		// Constructeur vide pour hibernate
	}
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * 
	 * @return true si le jour est ferme
	 */
	public boolean isJourFerme() {
		return matin && aprem;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the dateFermeture
	 */
	public Date getDateFermeture() {
		return dateFermeture;
	}
	/**
	 * @param dateFermeture the dateFermeture to set
	 */
	public void setDateFermeture(final Date dateFermeture) {
		this.dateFermeture = dateFermeture;
	}
	/**
	 * @return the matin
	 */
	public boolean isMatin() {
		return matin;
	}
	/**
	 * @param matin the matin to set
	 */
	public void setMatin(final boolean matin) {
		this.matin = matin;
	}
	/**
	 * @return the aprem
	 */
	public boolean isAprem() {
		return aprem;
	}
	/**
	 * @param aprem the aprem to set
	 */
	public void setAprem(final boolean aprem) {
		this.aprem = aprem;
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
 
