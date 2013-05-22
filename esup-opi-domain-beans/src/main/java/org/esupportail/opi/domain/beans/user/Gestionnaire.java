/**
 * 
 */
package org.esupportail.opi.domain.beans.user;

import java.util.Date;
import java.util.Set;

import org.esupportail.commons.utils.strings.StringUtils;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.Member;

/**
 * @author cleprous
 *
 */
public class Gestionnaire extends User {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -2515757173762383178L;

	/*
	 ******************* PROPERTIES ******************* */

	 /**
	 * Display Name of the user.
	 */
    private String displayName;
	
	/**
	 * The login.
	 */
	private String login;
	
	/**
	 * The date of entry manager.
	 */
	private Date dateDbtValidite;
	

	/**
	 * The expiry date manager.
	 */
	private Date dateFinValidite;
	
	/**
	 * Code du centre de gestion.
	 */
	private String codeCge;
	
	/**
	 * The user profile.
	 */
	private Profile profile;
	
	/**
	 * The members of commissions.
	 */
	private Set<Member> members;
	
	/**
	 * Le droit sur le commissions.
	 */
	private Set<Commission> rightOnCmi;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public Gestionnaire() {
		super();
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((login == null) ? 0 : login.hashCode());
		return result;
	}




	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof Gestionnaire)) { return false; }
		Gestionnaire other = (Gestionnaire) obj;
		if (login == null) {
			if (other.getLogin() != null) { return false; }
		} else if (!login.equals(other.getLogin())) { return false; }

		return true;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "Gestionnaire#" + hashCode() + "[login=[" + login + "], dateDbtValidite=[" + dateDbtValidite 
		+ "], dateFinValididte=[" + dateFinValidite + "],  displayName=[" + displayName
		+ "],  codeCge=[" + codeCge;
		
		if (profile != null) {
			s += "], profile.code=[" + profile.getCode() + "],  [" + super.toString() + "]]";
		} else {
			s += "],  [" + super.toString() + "]]";
		}
		
		return s; 
	}
	
	
	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Gestionnaire clone() {
		Gestionnaire g = new Gestionnaire();
		g = (Gestionnaire) super.clone(g);
		g.setLogin(login);
		g.setDateDbtValidite(dateDbtValidite);
		g.setDateFinValidite(dateFinValidite);
		g.setDisplayName(displayName);
		g.setProfile(profile);
		g.setCodeCge(codeCge);
		g.setMembers(members);
		g.setRightOnCmi(rightOnCmi);
		
		return g; 
	}


	/*
	 ******************* METHODS ********************** */

	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	
	  /**
	 * @return  Returns the displayName.
	 */
    public String getDisplayName() {
        return this.displayName;
    }

    /**
	 * @param displayName  The displayName to set.
	 */
    public void setDisplayName(final String displayName) {
        this.displayName = StringUtils.nullIfEmpty(displayName);
    }

	/**
	 * @return the login
	 */
	public String getLogin() {
		return login;
	}

	/**
	 * @param login the login to set
	 */
	public void setLogin(String login) {
		this.login = login;
	}

	/**
	 * @return the dateDbtValidite
	 */
	public Date getDateDbtValidite() {
		return dateDbtValidite;
	}

	/**
	 * @param dateDbtValidite the dateDbtValidite to set
	 */
	public void setDateDbtValidite(Date dateDbtValidite) {
		this.dateDbtValidite = dateDbtValidite;
	}

	/**
	 * @return the dateFinValididte
	 */
	public Date getDateFinValidite() {
		return dateFinValidite;
	}

	/**
	 * @param dateFinValididte the dateFinValididte to set
	 */
	public void setDateFinValidite(Date dateFinValididte) {
		this.dateFinValidite = dateFinValididte;
	}

	/**
	 * @return the profile
	 */
	public Profile getProfile() {
		return profile;
	}

	/**
	 * @param profile the profile to set
	 */
	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	/**
	 * @return the codeCge
	 */
	public String getCodeCge() {
		return codeCge;
	}

	/**
	 * @param codeCge the codeCge to set
	 */
	public void setCodeCge(String codeCge) {
		this.codeCge = codeCge;
	}

	/**
	 * @return the members
	 */
	public Set<Member> getMembers() {
		return members;
	}

	/**
	 * @param members the members to set
	 */
	public void setMembers(Set<Member> members) {
		this.members = members;
	}

	/**
	 * @return the rightOnCmi
	 */
	public Set<Commission> getRightOnCmi() {
		return rightOnCmi;
	}

	/**
	 * @param rightOnCmi the rightOnCmi to set
	 */
	public void setRightOnCmi(Set<Commission> rightOnCmi) {
		this.rightOnCmi = rightOnCmi;
	}
	

}
