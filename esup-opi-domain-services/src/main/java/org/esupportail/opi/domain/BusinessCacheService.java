/**
 * 
 */
package org.esupportail.opi.domain;

import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;

import java.io.Serializable;

import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.Etablissement;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author cleprous
 * BusinessService : Parcours de listes mises en cache pour renvoyer un element
 */
public interface BusinessCacheService  extends Serializable {

	//////////////////////////////////////////////////////////////
	// Departement
	//////////////////////////////////////////////////////////////
	
	/**
	 * Not write in domainApo because not use the cache. 
	 * Return the departement according to the 'codeDep'.
	 * @param codeDep
	 * @return Departement
	 */
	Departement getDepartement(final String codeDep);
	
	//////////////////////////////////////////////////////////////
	// Pays
	//////////////////////////////////////////////////////////////
	
	/**
	 * Not write in domainApo because not use the cache. 
	 * Returns the country that has the codePay code.
	 * @param codePays
	 * @return Pays
	 */
	Pays getPays(final String codePays);
	
	//////////////////////////////////////////////////////////////
	// CommuneDTO
	//////////////////////////////////////////////////////////////
	
	/**
	 * Not write in domainApo because not use the cache. 
	 * Returns the CommuneDTO that has the codeCom in the list with this codBdi.
	 * @param codCom
	 * @param codBdi
	 * @return
	 */
	CommuneDTO getCommune(final String codCom, final String codBdi);
	
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
	
	/**
	 * Returns the versionEtape of the codes.
	 * @param codEtp
	 * @param codVrsVet
	 * @return
	 */
	VersionEtapeDTO getVersionEtape(String codEtp, Integer codVrsVet);
	
	//////////////////////////////////////////////////////////////
	// Etablissement
	//////////////////////////////////////////////////////////////

	/**
	 * Returns the Etablissement of the code.
	 * @param codEtb
	 * @return
	 */
	Etablissement getEtablissement(String codEtb);

	//////////////////////////////////////////////////////////////
	//BacOuxEqu
	//////////////////////////////////////////////////////////////

	/**
	 * Returns the BacOuxEqu of the code.
	 * @param daaObt
	 * @param codBac
	 * @return
	 */
	BacOuxEqu getBacOuxEqu(String daaObt, String codBac);
	
	/////////////////////////////////////////////////////////
	// SignataireDTO
	//////////////////////////////////////////////////////////////

	public SignataireDTO getSignataire(String codSig);

}
