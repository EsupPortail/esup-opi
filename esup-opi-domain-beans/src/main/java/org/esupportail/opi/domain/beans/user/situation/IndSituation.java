package org.esupportail.opi.domain.beans.user.situation;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.Individu;

/**
 * @author ylecuyer
 * Classe abstraite de la gestion des situations
 * d'un candidat
 */
public abstract class IndSituation extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5108759135836978992L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 *  The type of Situation.
	 */
	private String type;

	/**
	 * true if il s'agit d'un travailleur est handicapé.
	 */
	private Boolean handicapTravail;
	
	/**
	 * true if il s'agit d'un adulte handicapé.
	 */
	private Boolean handicapAdulte;
	
	/**
	 * The individu.
	 */
	private Individu individu;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public IndSituation() {
		super();
		handicapTravail = false;
		handicapAdulte = false;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndSituation#" + hashCode() + "[type=[" + type + "],[handicapTravail=[" + handicapTravail 
			+ "],[handicapAdulte=[" + handicapAdulte + "]]";
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the handicapTravail
	 */
	public Boolean getHandicapTravail() {
		return handicapTravail;
	}

	/**
	 * @param handicapTravail the handicapTravail to set
	 */
	public void setHandicapTravail(final Boolean handicapTravail) {
		this.handicapTravail = handicapTravail;
	}

	/**
	 * @return the handicapAdulte
	 */
	public Boolean getHandicapAdulte() {
		return handicapAdulte;
	}

	/**
	 * @param handicapAdulte the handicapAdulte to set
	 */
	public void setHandicapAdulte(final Boolean handicapAdulte) {
		this.handicapAdulte = handicapAdulte;
	}

	/**
	 * @return the individu
	 */
	public Individu getIndividu() {
		return individu;
	}

	/**
	 * @param individu the individu to set
	 */
	public void setIndividu(final Individu individu) {
		this.individu = individu;
	}
	
}
