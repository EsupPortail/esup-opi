package org.esupportail.opi.domain.beans.references.rendezvous;



import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.references.commission.Commission;

import java.io.Serializable;



/**
 * 
 * @author Gomez
 *
 */
public class VetCalendar extends NormeSI implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8042444084174876385L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * codEtp.
	 */
	private String codEtp;
	
	/**
	 * The Version Etape code.
	 */
	private Integer codVrsVet;
	
	/**
	 * The rdv calendar.
	 */
	private CalendarRDV calendrierRdv;
	
	/**
	 * commission.
	 */
	private Commission commission;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructeur vide pour hibernate.
	 */
	public VetCalendar() {
		//Constructeur vide pour hibernate.
	}
	
	/**
	 *
	 * @param codEtp 
	 * @param codVrsVet 
	 * @param calendrierRdv 
	 * @param commission 
	 */
	public VetCalendar(
			final String codEtp,
			final Integer codVrsVet,
			final CalendarRDV calendrierRdv,
			final Commission commission) {
		this.codEtp = codEtp;
		this.codVrsVet = codVrsVet;
		this.calendrierRdv = calendrierRdv;
		this.commission = commission;
	}
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final VetCalendar other = (VetCalendar) obj;
		if (!this.getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return codEtp
	 */
	public String getCodEtp() {
		return codEtp;
	}
	/**
	 * 
	 * @param codEtp
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
	
	/**
	 * 
	 * @return calendrierRdv
	 */
	public CalendarRDV getCalendrierRdv() {
		return calendrierRdv;
	}
	
	/**
	 * 
	 * @param calendrierRdv
	 */
	public void setCalendrierRdv(final CalendarRDV calendrierRdv) {
		this.calendrierRdv = calendrierRdv;
	}
	
	/**
	 * 
	 * @return commission
	 */
	public Commission getCommission() {
		return commission;
	}
	
	/**
	 * 
	 * @param commission
	 */
	public void setCommission(final Commission commission) {
		this.commission = commission;
	}
}
