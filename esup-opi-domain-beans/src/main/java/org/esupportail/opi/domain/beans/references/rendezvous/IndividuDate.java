package org.esupportail.opi.domain.beans.references.rendezvous;



import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;

import java.io.Serializable;
import java.util.Date;



/**
 * @author Gomez
 *
 */
public class IndividuDate extends NormeSI implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4633204974185147303L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * 
	 */
	private Individu candidat;
	/**
	 * 
	 */
	private Date dateRdv;
	/**
	 * 
	 */
	private CalendarRDV calendrierRdv;
	/**
	 * 
	 */
	private Date dateCreation;
	/**
	 * 
	 */
	private Date dateModification;
	/**
	 * 
	 */
	private IndVoeu voeu;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructeur vide pour hibernate.
	 */
	public IndividuDate() {
		//Constructeur vide pour hibernate.
	}
	/**
	 * Constructors.
	 * @param candidat
	 * @param dateRdv
	 * @param calendrierRdv
	 * @param voeu
	 */
	public IndividuDate(final Individu candidat, final Date dateRdv, 
			final CalendarRDV calendrierRdv, final IndVoeu voeu) {
		this.candidat = candidat;
		this.dateRdv = dateRdv;
		this.calendrierRdv = calendrierRdv;
		dateCreation = new Date();
		dateModification = new Date();
		this.voeu = voeu;
	}
	
	
	/*
	 ******************* METHODS ********************** */
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return etudiant
	 */
	public Individu getCandidat() {
		return candidat;
	}
	/**
	 * 
	 * @param candidat
	 */
	public void setCandidat(final Individu candidat) {
		this.candidat = candidat;
	}
	/**
	 * 
	 * @return dateRdv
	 */
	public Date getDateRdv() {
		return dateRdv;
	}
	/**
	 * 
	 * @param dateRdv
	 */
	public void setDateRdv(final Date dateRdv) {
		this.dateRdv = dateRdv;
	}
	/**
	 * 
	 * @return dateCreation
	 */
	public Date getDateCreation() {
		return dateCreation;
	}
	/**
	 * 
	 * @param dateCreation
	 */
	public void setDateCreation(final Date dateCreation) {
		this.dateCreation = dateCreation;
	}
	/**
	 * 
	 * @return dateModification
	 */
	public Date getDateModification() {
		return dateModification;
	}
	/**
	 * 
	 * @param dateModification
	 */
	public void setDateModification(final Date dateModification) {
		this.dateModification = dateModification;
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
	 * @return voeu
	 */
	public IndVoeu getVoeu() {
		return voeu;
	}
	/**
	 * 
	 * @param voeu
	 */
	public void setVoeu(final IndVoeu voeu) {
		this.voeu = voeu;
	}
}
