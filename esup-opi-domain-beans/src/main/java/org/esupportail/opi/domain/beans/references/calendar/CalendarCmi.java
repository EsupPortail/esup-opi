/**
 * 
 */
package org.esupportail.opi.domain.beans.references.calendar;

import org.esupportail.opi.domain.beans.references.commission.Commission;

import java.util.Date;
import java.util.Set;




/**
 * @author cleprous
 *
 */
public class CalendarCmi extends Calendar {


	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 8595199822776399766L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The End date of confirmation of results.
	 */
	private Date endDatConfRes;
	
	/**
	 * date limite de retour des dossiers.
	 */
	private Date datEndBackDossier;
	
	/**
	 * Commentaire ajouté à la date de retour des dossiers. 
	 */
	private String commDatEndBack;
		
	/**
	 * The meeting od cmi.
	 */
	private Set<ReunionCmi> reunions;
	
	/**
	 * The commission.
	 */
	private Commission commission;
	
	/*
	 ******************* INIT ************************* */


	/**
	 * Constructors.
	 */
	public CalendarCmi() {
		super();
	}
	
	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CalendarCmi clone() {
		CalendarCmi c = new CalendarCmi();
		c = (CalendarCmi) super.clone(c);
		c.setEndDatConfRes(endDatConfRes);
		c.setDatEndBackDossier(datEndBackDossier);
		c.setCommDatEndBack(commDatEndBack);
		c.setReunions(reunions);
		c.setCommission(commission);
		
		return c; 
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the endDatConfRes
	 */
	public Date getEndDatConfRes() {
		return endDatConfRes;
	}


	/**
	 * @param endDatConfRes the endDatConfRes to set
	 */
	public void setEndDatConfRes(final Date endDatConfRes) {
		this.endDatConfRes = endDatConfRes;
	}


	/**
	 * @return the datEndBackDossier
	 */
	public Date getDatEndBackDossier() {
		return datEndBackDossier;
	}

	/**
	 * @param datEndBackDossier the datEndBackDossier to set
	 */
	public void setDatEndBackDossier(final Date datEndBackDossier) {
		this.datEndBackDossier = datEndBackDossier;
	}

	/**
	 * @return the commDatEndBack
	 */
	public String getCommDatEndBack() {
		return commDatEndBack;
	}

	/**
	 * @param commDatEndBack the commDatEndBack to set
	 */
	public void setCommDatEndBack(final String commDatEndBack) {
		this.commDatEndBack = commDatEndBack;
	}

	/**
	 * @return the reunions
	 */
	public Set<ReunionCmi> getReunions() {
		return reunions;
	}


	/**
	 * @param reunions the reunions to set
	 */
	public void setReunions(final Set<ReunionCmi> reunions) {
		this.reunions = reunions;
	}


	/**
	 * @return the commission
	 */
	public Commission getCommission() {
		return commission;
	}


	/**
	 * @param commission the commission to set
	 */
	public void setCommission(final Commission commission) {
		this.commission = commission;
	}

}
