/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import java.io.Serializable;

import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.utils.Utilitaires;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author cleprous
 *
 */
public class VersionEtapePojo implements Serializable {

	/*
	 ******************* PROPERTIES ******************* */

	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 7178293034517330339L;
	
	/**
	 * The versionEtape.
	 */
	private VersionEtapeDTO versionEtape;
	
	/**
	 * if the calendar is open = true.
	 * Default value = false;
	 */
	private Boolean calendarIsOpen;
	
	/**
	 * intervalle candidature.
	 */
	private String calPeriod;
	
	/**
	 * The candidate has already choice this versionEtape.
	 * Default value = false;
	 */
	private Boolean isAlReadyChoice;
	
	/**
	 * Is the current User connected a manager ?
	 * or the regime can always add vows
	 */
	private Boolean hasRightsToAddVows;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public VersionEtapePojo() {
		super();
		calendarIsOpen = false;
		isAlReadyChoice = false;
	}

	
	
	/**
	 * Constructors.
	 * @param versionEtape
	 * @param calIsOpen
	 * @param calPeriod
	 */
	public VersionEtapePojo(final VersionEtapeDTO versionEtape, final Boolean calIsOpen, final String calPeriod) {
		super();
		this.versionEtape = versionEtape;
		calendarIsOpen = calIsOpen;
		isAlReadyChoice = false;
		this.calPeriod = calPeriod;
		hasRightsToAddVows = false;
	}

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((versionEtape == null) ? 0 : versionEtape.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		VersionEtapePojo other = (VersionEtapePojo) obj;
		if (versionEtape == null) {
			if (other.versionEtape != null) { return false; }
		} else if (!versionEtape.equals(other.versionEtape)) { return false; }
		return true;
	}
	


	/*
	 ******************* METHODS ********************** */

	
	/**
	 * @return Boolean true if individual can choice this vet.
	 */
	public Boolean getCanChoiceVet() {
		if (hasRightsToAddVows) { return !isAlReadyChoice; }
		return !isAlReadyChoice && calendarIsOpen;
	}
	
	
	/**
	 * @return the calPeriod
	 */
	public String getShortCalPeriod() {
		String comment = calPeriod;
		return Utilitaires.limitStrLength(comment, Constantes.STR_LENGTH_LIMIT);
	}

	/**
	 * @return true if calPeriod
	 */
	public Boolean getIsShortCalPeriod() {
		String comment = calPeriod;
		if (comment != null && (comment.length() > Constantes.STR_LENGTH_LIMIT)) {
			return true;
		}
		return false;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

	
	/**
	 * @return the versionEtape
	 */
	public VersionEtapeDTO getVersionEtape() {
		return versionEtape;
	}

	/**
	 * @param versionEtape the versionEtape to set
	 */
	public void setVersionEtape(final VersionEtapeDTO versionEtape) {
		this.versionEtape = versionEtape;
	}

	/**
	 * @return the isAlReadyChoice
	 */
	public Boolean getIsAlReadyChoice() {
		return isAlReadyChoice;
	}

	/**
	 * @param isAlReadyChoice the isAlReadyChoice to set
	 */
	public void setIsAlReadyChoice(final Boolean isAlReadyChoice) {
		this.isAlReadyChoice = isAlReadyChoice;
	}



	/**
	 * @return the calendarIsOpen
	 */
	public Boolean getCalendarIsOpen() {
		return calendarIsOpen;
	}



	/**
	 * @param calendarIsOpen the calendarIsOpen to set
	 */
	public void setCalendarIsOpen(final Boolean calendarIsOpen) {
		this.calendarIsOpen = calendarIsOpen;
	}



	/**
	 * @param hasRightsToAddVows the hasRightsToAddVows to set
	 */
	public void setHasRightsToAddVows(final Boolean hasRightsToAddVows) {
		this.hasRightsToAddVows = hasRightsToAddVows;
	}



	/**
	 * @return the calPeriod
	 */
	public String getCalPeriod() {
		return calPeriod;
	}



	/**
	 * @param calPeriod the calPeriod to set
	 */
	public void setCalPeriod(final String calPeriod) {
		this.calPeriod = calPeriod;
	}



	
	

}
