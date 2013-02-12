/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.io.Serializable;



/**
 * @author cleprous
 *
 */
public class TraitementCmiPojo implements Serializable {

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
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public TraitementCmiPojo() {
		super();
	}

	
	
	/**
	 * Constructors.
	 * @param versionEtape
	 */
	public TraitementCmiPojo(final VersionEtapeDTO versionEtape) {
		super();
		this.versionEtape = versionEtape;
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
		TraitementCmiPojo other = (TraitementCmiPojo) obj;
		if (versionEtape == null) {
			if (other.versionEtape != null) { return false; }
		} else if (!versionEtape.equals(other.versionEtape)) { return false; }
		return true;
	}
	


	/*
	 ******************* METHODS ********************** */

	
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

}
