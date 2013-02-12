package org.esupportail.opi.domain;

import java.util.List;

import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;

/**
 * 
 * @author cleprous
 * @version 1.0
 */
public interface OpiWebService {
	
	/**
	 * Charge les donneesOpi puis appel le webService pour mettre e jour APOGEE.
	 * M.A.J d'apogee synhrone.
	 * @param individu
	 * @param voeu
	 * return true if deversement is ok
	 */
	Boolean launchWebService(Individu individu, List<IndVoeu> voeu);
	

}
