package org.esupportail.opi.web.beans.pojo;

import java.util.Date;

/**
 * @author ylecuyer
 *
 */
public class IndividuDatePojo {
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Date du rdv.
	 */
	private Date dateRdv;
	
	/**
	 * Message du rdv.
	 */
	private String messageRdv;
	
	/**
	 * Voeu du rdv.
	 */
	private IndVoeuPojo voeuRdv;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 * @param dateRdv
	 * @param messageRdv
	 * @param voeuRdv
	 */
	public IndividuDatePojo(final Date dateRdv, final String messageRdv, 
			final IndVoeuPojo voeuRdv) {
		super();
		this.dateRdv = dateRdv;
		this.messageRdv = messageRdv;
		this.voeuRdv = voeuRdv;
	}

	/**
	 * Constructors.
	 */
	public IndividuDatePojo() {
		super();
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the dateRdv
	 */
	public Date getDateRdv() {
		return dateRdv;
	}

	/**
	 * @param dateRdv the dateRdv to set
	 */
	public void setDateRdv(Date dateRdv) {
		this.dateRdv = dateRdv;
	}

	/**
	 * @return the messageRdv
	 */
	public String getMessageRdv() {
		return messageRdv;
	}

	/**
	 * @param messageRdv the messageRdv to set
	 */
	public void setMessageRdv(String messageRdv) {
		this.messageRdv = messageRdv;
	}

	/**
	 * @return the voeuRdv
	 */
	public IndVoeuPojo getVoeuRdv() {
		return voeuRdv;
	}

	/**
	 * @param voeuRdv the voeuRdv to set
	 */
	public void setVoeuRdv(IndVoeuPojo voeuRdv) {
		this.voeuRdv = voeuRdv;
	}
	
}
