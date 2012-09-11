package org.esupportail.opi.domain.beans.user.situation;

/**
 * @author ylecuyer
 * Cas d'une situation de type autre
 */
public class SituationAutre extends IndSituation {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8636050964944718844L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Situation actuelle.
	 */
	private String actualSituation;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public SituationAutre() {
		super();
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the actualSituation
	 */
	public String getActualSituation() {
		return actualSituation;
	}

	/**
	 * @param actualSituation the actualSituation to set
	 */
	public void setActualSituation(final String actualSituation) {
		this.actualSituation = actualSituation;
	}

}
