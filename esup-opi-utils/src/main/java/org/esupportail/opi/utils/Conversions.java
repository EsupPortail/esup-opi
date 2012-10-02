package org.esupportail.opi.utils;

import java.util.HashSet;
import java.util.Set;

import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

public class Conversions {

    /**
     * Convert a versionEtape list in versionEtpOpi list.
     * @param vet
     * @return Set< VersionEtpOpi>
     */
    public static Set<VersionEtpOpi> convertVetInVetOpi(final Set<VersionEtapeDTO> vet) {
    	Set<VersionEtpOpi> vetOpi = new HashSet<VersionEtpOpi>();
    	for (VersionEtapeDTO v : vet) {
    		VersionEtpOpi vOpi = new VersionEtpOpi(v);
    		vetOpi.add(vOpi);
    	}
    	return vetOpi;
    
    }

}
