package org.esupportail.opi.domain.beans.parameters.accessRight;



import java.util.Set;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.Gestionnaire;



/**
 * @author cleprous
 *
 */
public class Profile  extends NormeSI {
  
	
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 4347597698211551620L;

	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * Code du profil.
	 */
	private String code;
	
	/**
	 * The access of this profil.
	 */
	private Set<AccessRight> accessRight;
	
	/**
	 * The Gestionnaire.
	 */
	private Set<Gestionnaire> gestionnaires;
	
	/**
	 * At true if the strongest Profile.
	 * Default value false.
	 */
	private Boolean superProfile;
	
	/**
	 * Code Regime d'inscription.
	 * Default value 0 --> formation Initiale.
	 * 1 --> Formation Continue
	 */
	private int codeRI;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public Profile() {
		super();
		superProfile = false;
		codeRI = 0;
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result;
		if (code != null) { result += code.hashCode(); }
		return result;
	}

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Profile clone() {
		Profile p = new Profile();
		p = (Profile) this.clone(p);
		p.setCode(code);
		p.setAccessRight(accessRight);
		p.setGestionnaires(gestionnaires);
		p.setSuperProfile(superProfile);
		p.setCodeRI(codeRI);
		return p; 
	}
	
	
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof Profile)) { return false; }
		final Profile other = (Profile) obj;
		if (code == null) {
			if (other.getCode() != null) { return false; }
		} else if (!code.equals(other.getCode())) { return false; }
		return true;
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Profile#" + hashCode() + "[code=[" + code + "],  ["+ super.toString() + "]]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	

	/**
	 * @return the codeProfil
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * @return the accessRight
	 */
	public Set<AccessRight> getAccessRight() {
		return accessRight;
	}

	/**
	 * @param accessRight the accessRight to set
	 */
	public void setAccessRight(final Set<AccessRight> accessRight) {
		this.accessRight = accessRight;
	}

	/**
	 * @return the gestionnaires
	 */
	public Set<Gestionnaire> getGestionnaires() {
		return gestionnaires;
	}

	/**
	 * @param gestionnaires the gestionnaires to set
	 */
	public void setGestionnaires(final Set<Gestionnaire> gestionnaires) {
		this.gestionnaires = gestionnaires;
	}

	/**
	 * @return the superProfile
	 */
	public Boolean getSuperProfile() {
		return superProfile;
	}

	/**
	 * @param superProfile the superProfile to set
	 */
	public void setSuperProfile(final Boolean superProfile) {
		this.superProfile = superProfile;
	}

	/**
	 * @return the codeRI
	 */
	public int getCodeRI() {
		return codeRI;
	}

	/**
	 * @param codeRI the codeRI to set
	 */
	public void setCodeRI(int codeRI) {
		this.codeRI = codeRI;
	}

	

		
}

