package org.esupportail.opi.domain.beans.user.situation;

import java.util.Date;

/**
 * @author ylecuyer
 * Cas d'une situation de type demandeur d'emploi
 */
public class SituationDE extends IndSituation {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6575152988786026149L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Date d'inscription au pôle emploi.
	 */
	private Date dateInscriptionPE;
	
	/**
	 * true si le candidat est indemnisé par le PE.
	 */
	private Boolean compensated;
	
	/**
	 * Date de début de l'indemnisation.
	 */
	private Date dateStartCompensation;
	
	/**
	 * Date de fin de l'indemnisation.
	 */
	private Date dateEndCompensation;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public SituationDE() {
		super();
		compensated = false;
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the dateInscriptionPE
	 */
	public Date getDateInscriptionPE() {
		return dateInscriptionPE;
	}

	/**
	 * @param dateInscriptionPE the dateInscriptionPE to set
	 */
	public void setDateInscriptionPE(final Date dateInscriptionPE) {
		this.dateInscriptionPE = dateInscriptionPE;
	}

	/**
	 * @return the compensated
	 */
	public Boolean getCompensated() {
		return compensated;
	}

	/**
	 * @param compensated the compensated to set
	 */
	public void setCompensated(final Boolean compensated) {
		this.compensated = compensated;
	}

	/**
	 * @return the dateStartCompensation
	 */
	public Date getDateStartCompensation() {
		return dateStartCompensation;
	}

	/**
	 * @param dateStartCompensation the dateStartCompensation to set
	 */
	public void setDateStartCompensation(final Date dateStartCompensation) {
		this.dateStartCompensation = dateStartCompensation;
	}

	/**
	 * @return the dateEndCompensation
	 */
	public Date getDateEndCompensation() {
		return dateEndCompensation;
	}

	/**
	 * @param dateEndCompensation the dateEndCompensation to set
	 */
	public void setDateEndCompensation(final Date dateEndCompensation) {
		this.dateEndCompensation = dateEndCompensation;
	}

}
