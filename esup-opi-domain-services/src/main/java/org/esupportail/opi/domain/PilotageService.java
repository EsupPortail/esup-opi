/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;


import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.utils.CoordonneStatistique;



/**
 * The pilotage service interface.
 */
public interface PilotageService extends Serializable {
	
	/**
	 * 
	 */
	 String NUM_DOS = "numDos";
	/**
	 * 
	 */
	Map<String, String> LIB_BASE = 
		new LinkedHashMap<String, String>() { 

			private static final long serialVersionUID = 4451087010675988608L;
	
			{ put("commission", "Commission"); put(NUM_DOS, "Num_Dos_OPI"); put("sexe", "Sexe");
			  put("nom", "Nom_Patrony"); put("prenom", "Prenom"); put("dateNaiss", "Date_Naiss");
			  put("mail", "Mail"); put("titre", "Titre"); put("bac", "Bac");
			  put("insAnnee", "Inscrit_univ_annee"); put("univAnnee", "Univ_insc_annee");
			  put("etudAnnee", "Etudes_insc_annee"); put("insAvant", "Inscrit_univ_avant");
			  put("univAvant", "Univ_insc_avant"); put("etudAvant", "Etudes_insc_avant");
			  put("resultAvant", "Resultat_univ_avant"); put("dipAvant", "Diplome_univ_avant"); }
		};
	
	/**
	 * @param ordonne 
	 * @param abscisse 
	 * @param campagne 
	 * @return String
	 */
	String getRecupValCoordonne(CoordonneStatistique ordonne, CoordonneStatistique abscisse,
			Campagne campagne, boolean isNotDoublonIndividu);
	
	/**
	 * @param typeClass
	 * @param methode 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	List getAllCoordonne(Class< ? > typeClass, String methode);
	
	/**
	 * 
	 * @param versionEtape
	 * @param sLabelRI : shortLabel of a RegimeInscription
	 * @param champsChoisis
	 * @return
	 */
	Map<Integer, List<String>> prepareCsvFormulaire(final VersionEtpOpi versionEtape,
			final String sLabelRI, final List<String> champsChoisis);
	
	
}
