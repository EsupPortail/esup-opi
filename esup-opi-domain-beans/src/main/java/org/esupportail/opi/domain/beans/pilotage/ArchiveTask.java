package org.esupportail.opi.domain.beans.pilotage;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.parameters.Campagne;

import java.util.Date;

/**
 * @author ylecuyer
 *
 */
public class ArchiveTask extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -237009527079908717L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The campagne to archive.
	 */
	private Campagne campagneToArchive;
	
	/**
	 * The campagne to active.
	 */
	private Campagne campagneToActive;
	
	/**
	 * The date to launch the archive.
	 */
	private Date dateArchive;
	
	/**
	 * true if the archive has been launched.
	 */
	private Boolean isExecuted;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public ArchiveTask() {
		super();
		isExecuted = false;
	}
	
	/**
	 * @return String
	 */
	@Override
	public String toString() {
		return "[ArchiveTask# [campagneToArchive=[" + campagneToArchive + "], "
		+ "campagneToActive=[" + campagneToActive + "], "
		+ "dateArchive=[" + dateArchive + "], "
		+ "isExecuted=[" + isExecuted + "]]";
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the campagneToArchive
	 */
	public Campagne getCampagneToArchive() {
		return campagneToArchive;
	}

	/**
	 * @param campagneToArchive the campagneToArchive to set
	 */
	public void setCampagneToArchive(final Campagne campagneToArchive) {
		this.campagneToArchive = campagneToArchive;
	}

	/**
	 * @return the campagneToActive
	 */
	public Campagne getCampagneToActive() {
		return campagneToActive;
	}

	/**
	 * @param campagneToActive the campagneToActive to set
	 */
	public void setCampagneToActive(final Campagne campagneToActive) {
		this.campagneToActive = campagneToActive;
	}

	/**
	 * @return the dateArchive
	 */
	public Date getDateArchive() {
		return dateArchive;
	}

	/**
	 * @param dateArchive the dateArchive to set
	 */
	public void setDateArchive(final Date dateArchive) {
		this.dateArchive = dateArchive;
	}

	/**
	 * @return the isExecuted
	 */
	public Boolean getIsExecuted() {
		return isExecuted;
	}

	/**
	 * @param isExecuted the isExecuted to set
	 */
	public void setIsExecuted(final Boolean isExecuted) {
		this.isExecuted = isExecuted;
	}
	

}
