/**
* CRI - Universite de Rennes1 - 57SI-CIES - 2007
* http://sourcesup.cru.fr/projects/57si-cies/
*/
package org.esupportail.opi.domain.beans;

import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.user.Gestionnaire;

import java.io.Serializable;
import java.util.Set;

public class BeanProfile implements Serializable {
	private static final long serialVersionUID = 3670656713361699391L;
	
	private final Profile profile;

    private final int nbGest;

	public BeanProfile(final Profile p) {
		super();
		profile = p;
        Set<Gestionnaire> gest = p.getGestionnaires();
        nbGest = gest != null ? gest.size() : 0;
	}

	public int getNbGest() {
//		if (profile.getGestionnaires() != null) {
//			return profile.getGestionnaires().size();
//		}
//		return 0;
        return nbGest;
	}

	public Profile getProfile() {
		return profile;
	}

    @Override
    public String toString() {
        return "BeanProfil#" + hashCode() + "[ " + profile.toString() + " ]";
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((profile == null) ? 0 : profile.hashCode());
        return result;
    }

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
}
