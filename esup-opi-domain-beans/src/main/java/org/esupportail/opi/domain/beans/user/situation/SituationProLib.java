package org.esupportail.opi.domain.beans.user.situation;

import java.util.Date;

/**
 * @author ylecuyer
 * Cas d'une situation de type professionnel libre
 */
public class SituationProLib extends IndSituation {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 5679126930723440186L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Statut précis du candidat.
	 */
	private String exactStatut;
	
	/**
	 * Activité exercée par le candidat. 
	 */
	private String activity;
	
	/**
	 * Date du début de l'activité.
	 */
	private Date dateStartActivity;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public SituationProLib() {
		super();
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the exactStatut
	 */
	public String getExactStatut() {
		return exactStatut;
	}

	/**
	 * @param exactStatut the exactStatut to set
	 */
	public void setExactStatut(final String exactStatut) {
		this.exactStatut = exactStatut;
	}

	/**
	 * @return the activity
	 */
	public String getActivity() {
		return activity;
	}

	/**
	 * @param activity the activity to set
	 */
	public void setActivity(final String activity) {
		this.activity = activity;
	}

	/**
	 * @return the dateStartActivity
	 */
	public Date getDateStartActivity() {
		return dateStartActivity;
	}

	/**
	 * @param dateStartActivity the dateStartActivity to set
	 */
	public void setDateStartActivity(final Date dateStartActivity) {
		this.dateStartActivity = dateStartActivity;
	}

}
