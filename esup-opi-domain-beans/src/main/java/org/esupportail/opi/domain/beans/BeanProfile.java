/**
* CRI - Universite de Rennes1 - 57SI-CIES - 2007
* http://sourcesup.cru.fr/projects/57si-cies/
*/
package org.esupportail.opi.domain.beans;

import java.io.Serializable;

import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;

/**
 * @author invite
 *
 */
public class BeanProfile implements Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3670656713361699391L;
	
	/*
	 ******************* PROPERTIES ********************** */
	
	/**
	 * The profil.
	 */
	private Profile profile;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public BeanProfile() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param p 
	 */
	public BeanProfile(final Profile p) {
		super();
		profile = p;
	}
	

	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		return result;
	}




	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof BeanProfile)) { return false; }
		BeanProfile other = (BeanProfile) obj;
		if (profile == null) {
			if (other.profile != null) { return false; }
		} else if (!profile.equals(other.profile)) { return false; }
		return true;
	}




	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BeanProfil#" + hashCode() + "[ " + profile.toString() + " ]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/**
	 * Return all gestionnaire who has this profil.
	 * @return int
	 */
	public int getNbGest() {
		if (profile.getGestionnaires() != null) {
			return profile.getGestionnaires().size();
		}
		return 0;
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return Profil
	 */
	public Profile getProfile() {
		return profile;
	}


	/**
	 * @param profil
	 */
	public void setProfile(final Profile profil) {
		this.profile = profil;
	}


}
