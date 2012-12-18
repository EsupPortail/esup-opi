/**
 * 
 */
package org.esupportail.opi.domain;


import java.io.Serializable;

import org.esupportail.wssi.services.remote.Etablissement;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author cleprous
 * BusinessService : Parcours de listes mises en cache pour renvoyer un element
 */
public interface BusinessCacheService  extends Serializable {

	//////////////////////////////////////////////////////////////
	// VersionEtapeDTO
	//////////////////////////////////////////////////////////////	
	/**
	 * Not write in domainApo because not use the cache. 
	 * Returns the versionEtape that has the codes.
	 * @param codEtp
	 * @param codVrsVet
	 * @param codAnu
	 * @return VersionEtapeDTO
	 */
	VersionEtapeDTO getVetDTO(String codEtp, Integer codVrsVet, String codAnu);

	
	//////////////////////////////////////////////////////////////
	// Etablissement
	//////////////////////////////////////////////////////////////

	/**
	 * Returns the Etablissement of the code.
	 * @param codEtb
	 * @return
	 */
	Etablissement getEtablissement(String codEtb);

	
	/////////////////////////////////////////////////////////
	// SignataireDTO
	//////////////////////////////////////////////////////////////

	public SignataireDTO getSignataire(String codSig);

}
