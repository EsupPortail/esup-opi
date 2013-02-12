/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters.accessRight;

import org.esupportail.opi.domain.beans.NormeSI;

/**
 * @author cleprous
 *
 */
public class AccessRight extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 5887583483500086136L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The Traitement.
	 */
	private Traitement traitement;
	
	/**
	 * The Profile.
	 */
	private Profile profile;
	
	/**
	 * The AccessType code.
	 */
	private String codeAccessType;
	
	/*
	 ******************* INIT ************************* */
	
	

	/**
	 * Constructors.
	 */
	public AccessRight() {
		super();
	}


	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((codeAccessType == null) ? 0 : codeAccessType.hashCode());
		result = prime * result
				+ ((traitement == null) ? 0 : traitement.hashCode());
		result = prime * result + ((profile == null) ? 0 : profile.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof AccessRight)) { return false; }
		AccessRight other = (AccessRight) obj;
		if (codeAccessType == null) {
			if (other.getCodeAccessType() != null) { return false; }
		} else if (!codeAccessType.equals(other.getCodeAccessType())) { return false; }
		if (traitement == null) {
			if (other.getTraitement() != null) { return false; }
		} else if (!traitement.equals(other.getTraitement())) { return false; }
		if (profile == null) {
			if (other.getProfile() != null) { return false; }
		} else if (!profile.equals(other.getProfile())) { return false; }
		return true;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccessRight#" + hashCode() + "[codeAccessType=[" + codeAccessType + "],profile.id=[" + profile.getId() 
				+ "]traitement.id=[" + traitement.getId() + "],  [" + super.toString() + "]  ]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	



	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * @return the traitement
	 */
	public Traitement getTraitement() {
		return traitement;
	}


	/**
	 * @param traitement the traitement to set
	 */
	public void setTraitement(Traitement traitement) {
		this.traitement = traitement;
	}


	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}


	/**
	 * @return the codeAccessType
	 */
	public String getCodeAccessType() {
		return codeAccessType;
	}


	/**
	 * @param codeAccessType the codeAccessType to set
	 */
	public void setCodeAccessType(String codeAccessType) {
		this.codeAccessType = codeAccessType;
	}


	


}
