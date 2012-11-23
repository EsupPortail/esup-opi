/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;

import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.services.remote.client.IApogee;
import org.esupportail.wssi.services.remote.AnneeUniDTO;
import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.DipAutCur;
import org.esupportail.wssi.services.remote.Diplome;
import org.esupportail.wssi.services.remote.Etablissement;
import org.esupportail.wssi.services.remote.MentionNivBac;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.TypDiplome;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * The domain service interface.
 */
public interface DomainApoService extends Serializable {

	//////////////////////////////////////////////////////////////
	// CentreGestion
	//////////////////////////////////////////////////////////////


	/**
	 * Return the list of all the centreGestion in use.
	 * @return List< CentreGestion>.
	 */
	List<CentreGestion> getCentreGestion();
	
	
	/**
	 * @param codCge
	 * @return CentreGestion.
	 */
	CentreGestion getCentreGestion(String codCge);

	//////////////////////////////////////////////////////////////
	// Version Etape
	//////////////////////////////////////////////////////////////

	/**
	 * 
	 * @param codEtp 
	 * @param libWebVet 
	 * @param codCge 
	 * @param codAnu
	 * @return List< VersionEtapeDTO>.
	 */
	List<VersionEtapeDTO> getVersionEtapes(String codEtp, String libWebVet, String codCge, String codAnu);
	
	
	/**
	 * 
	 * @param vrsDip VersionDiplome
	 * @return List< VersionEtapeDTO>.
	 */
	List<VersionEtapeDTO> getVersionEtapes(VersionDiplomeDTO vrsDip, String codAnu);
	
	
	/**
	 * @param codEtp
	 * @param codVrsVet
	 * @return VersionEtapeDTO
	 */
	VersionEtapeDTO getVersionEtape(String codEtp, Integer codVrsVet);
	
	    /**
    	 * List of the commisions managed by the gestionnaire.
    	 * @param gest 
    	 * @param domainApoService 
    	 * @param parameterService 
    	 * @return Set<Commission>
    	 */
	Set<Commission> getListCommissionsByRight(final Gestionnaire gest, final Boolean temEnSve);

	
	//////////////////////////////////////////////////////////////
	// GeographieApogee
	//////////////////////////////////////////////////////////////
	
	/**
	 * Returns the countries managed by the WS of GeographieApogee.
	 * @return List< Pays>.
	 */
	List<Pays> getPays();

	/**
	 * Returns one pays from the list getPays().
	 *@param codePays 
	 * @return Pays.
	 */
	Pays getPays(String codePays);
	
	/**
	 * Returns the departements managed by the WS of GeographieApogee.
	 * @return List< Departement>.
	 */
	List<Departement> getDepartements();

	/**
	 * Returns one departement from the list getDepartements().
	 *@param codeDep 
	 * @return Departement.
	 */
	Departement getDepartement(String codeDep);

	/**
	 * Returns the cities managed by the WS of GeographieApogee.
	 * @param codeBdi 
	 * @return List< CommuneDTO>.
	 */
	List<CommuneDTO> getCommunesDTO(String codeBdi);

	/**
	 * Returns one pays from the list getCommunesDTO().
	 *@param codCom 
	 *@param codBdi 
	 * @return CommuneDTO.
	 */
	CommuneDTO getCommune(String codCom, String codBdi);
	
	/**
	 * Returns all communes in use.
	 * @param codDep Can be null.
	 * @param onlyLycee can be null
	 * @param withEtab can be null.
	 * @return List< CommuneDTO >
	 * 				The CommuneDTO is coming from the apo-cri WebService
	 */
	List<org.esupportail.wssi.services.remote.CommuneDTO> 
		getCommunes(String codDep, Boolean onlyLycee, Boolean withEtab);
	
	/**
	 * Return the commune according to the 'codCom'.
	 * @param codCom 
	 * @return CommuneDTO.
	 */
	org.esupportail.wssi.services.remote.CommuneDTO getCommune(String codCom);

	
	
	//////////////////////////////////////////////////////////////
	// MentionNivBac
	//////////////////////////////////////////////////////////////

	/**
	 * Returns all mentions in use.
	 * @return List< MentionNivBac>.
	 */
	List<MentionNivBac> getMentionNivBacs();
	
	/**
	 * Return the mention according to the given 'codMnb'.
	 * @param codMnb 
	 * @return MentionNivBac.
	 */
	MentionNivBac getMentionNivBac(String codMnb);
	
	
	//////////////////////////////////////////////////////////////
	//Etablissement
	//////////////////////////////////////////////////////////////
	
	/**
	 * Return the Lycees.
	 * All the attributes can be null
	 * @param codCom 
	 * @param codDep 
	 * @return List< Etablissement>.
	 */
	List<Etablissement> getLycees(String codCom, String codDep);
	
	/**
	 * Return all the Etablissements.
	 * All the attributes can be null
	 * @param codCom 
	 * @param codDep 
	 * @return List< Etablissement>.
	 */
	List<Etablissement> getEtablissements(String codCom, String codDep);
	
	/**
	 * Return the Etablissement according to the codEtb code.
	 * All the attributes can be null
	 * @param codEtb 
	 * @return Etablissement.
	 */
	Etablissement getEtablissement(String codEtb);
	
	//////////////////////////////////////////////////////////////
	//BacOuxEqu
	//////////////////////////////////////////////////////////////
	
	/**
	 * Return all the baccalaureats ou equivalences in use.
	 * 
	 * @param daaObt Date d'obtention (can be null)
	 * @return List< BacOuxEqu>.
	 */
	List<BacOuxEqu> getBacOuxEqus(String daaObt);

	/**
	 * Return the bac according to the given 'codBac'.
	 * 
	 * @param daaObt Date d'obtention (can be null)
	 * @param codBac code du bac
	 * @return BacOuxEqu.
	 */
	/**
	 * TODO : à supprimer (16/01/2012)
	 */
	@Deprecated
	BacOuxEqu getBacOuxEqu(String daaObt, String codBac);

	
	//////////////////////////////////////////////////////////////
	//Diplome
	//////////////////////////////////////////////////////////////
	
	/**
	 * @see org.esupportail.opi.domain.DomainApoService#getAllDiplomes()
	 * @return list of the diplome
	 */
	List<Diplome> getAllDiplomes();
	
	/**
	 * Returns the diplomes managed by the WS of GeographieApogee.
	 * @param codSds Code discipline
	 * @return List< Diplome>.
	 */
	@Deprecated
	List<Diplome> getDiplomes(String codSds);
	
	/**
	 * Returns the diplome managed by the WS of GeographieApogee.
	 * @param codDip Code diplome
	 * @param codSds Code discipline
	 * @return Diplome.
	 */
	@Deprecated
	Diplome getDiplome(String codDip, String codSds);
	
	/**
	 * Returns the DipAutCur managed by the WS of GeographieApogee.
	 * @return List< DipAutCur>.
	 */
	List<DipAutCur> getDipAutCurs();
		
	/**
	 * Returns the DipAutCur according to the given codDac.
	 * @param codDac : Code diplome autre cursus
	 * @return DipAutCur.
	 */
	DipAutCur getDipAutCur(String codDac);
		
	
	//////////////////////////////////////////////////////////////
	// AnneeUni
	//////////////////////////////////////////////////////////////
	
	/**
	 * @param codAnu 
	 * @return AnneeUni.
	 */
	AnneeUniDTO getAnneeUni(String codAnu);

	//////////////////////////////////////////////////////////////
	//VersionDiplome
	//////////////////////////////////////////////////////////////
	
	
	/**
	 * @param codeKeyWord 
	 * @param grpTpd groupe Type Diplome
	 * @return List< VersionDiplome>
	 */
	List<VersionDiplomeDTO> getVersionDiplomes(String codeKeyWord, GrpTypDip grpTpd, String codAnu);
	
	/**
	 * @param annee 
	 * @param codeKeyWord
	 * @return List< VersionDiplomeDTO>
	 */
	List<VersionDiplomeDTO> getVersionDiplomes(String codeKeyWord, String annee);
	
	/**
	 * Return the VersionDiplome for this VET recruitment open. 
	 * @param vetOpi
	 * @param campagne
	 * @return VersionDiplome
	 */
	VersionDiplomeDTO getVersionDiplomes(VersionEtpOpi vetOpi, Campagne campagne);
	
	//////////////////////////////////////////////////////////////
	// EtudiantMetier Apogee
	//////////////////////////////////////////////////////////////
	/**
	 * Returns the student managed by the WS of EtudiantMetier Apogee.
	 * @param codeNne (can be null)
	 * @param clefNne (can be null)
	 * @param codEtu (can be null)
	 * @param nom (can be null)
	 * @param prenom (can be null)
	 * @param dateNaiss (can be null)
	 * @return Individu.
	 */
	Individu getIndividuFromApogee(String codeNne, String clefNne, String codEtu, 
			String nom, String prenom, String dateNaiss);
		
	/**
	 * Returns the student managed by the WS of EtudiantMetier Apogee.
	 * @param codeNne 
	 * @param clefNne 
	 * @param codEtu (can be null)
	 * @return List< IndBac>.
	 */
	List<IndBac> getIndBacFromApogee(String codeNne, String clefNne, String codEtu);
	
	/**
	 * Returns the cursus scolaire by the WS of AdministratifMetier Apogee.
	 * @param individu 
	 * @return List< IndCursusScol>.
	 */
	List<IndCursusScol> getIndCursusScolFromApogee(Individu individu);
	
	/**
	 * Returns the annees d'IA by the WS of AdministratifMetier Apogee.
	 * @param individu 
	 * @return List< IndCursusScol>.
	 */
	String[] getAnneesIa(Individu individu);
	
	
	///////////////////////////////////////////////////////////////
	// GrpTypDip
	///////////////////////////////////////////////////////////////

	/**
	 * Return the list of group type Diplome in use.
	 * @return List< GrpTypDip>.
	 */
	List<GrpTypDip> getGrpTypDip(Campagne camp);
		
	// ////////////////////////////////////////////////////////////
	// ClesAnnuFormPojo
	// ////////////////////////////////////////////////////////////
	/**
	 * @return List
	 */
	//TODO : fix this !
	//List<ClesAnnuFormPojo> getClesAnnuForm();
	
	
	///////////////////////////////////////////////////////////
	// SignataireDTO
	//////////////////////////////////////////////////////////////
	
	/**
	 * list In Use.
	 * @return List< SignataireDTO>
	 */
	List<SignataireDTO> getSignataires();
	
	
	///////////////////////////////////////////////////////////
	// TelemLaisserPasserDTO
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add all LaisserPasser. 
	 * @param wishes
	 * @param isReins true if it is reinscription in University
	 */
	void addTelemLaisserPasser(List<IndVoeu> wishes, Boolean isReins);
	
	
	/**
	 * Delete all LaisserPasser. 
	 * @param wishes
	 * @param isReins true if it is reinscription in University
	 */
	void deleteTelemLaisserPasser(List<IndVoeu> wishes, Boolean isReins);
	
	//////////////////////////////////////////////////////////////
	// Domaine2AnnuForm
	//////////////////////////////////////////////////////////////
	
	/**
	 * Retourne les domaines en fonction d'un groupe de type diplome.
	 *
	 * @return Map<Domaine2AnnuForm, List<Cles2AnnuForm>>
	 * 
	 */
	Map<Domaine2AnnuForm, List<Cles2AnnuForm>> getDomaine2AnnuForm(GrpTypDip grpTypDip, String locale);
	
	/**
	 * 
	 * @return list of the typDiplome
	 */
	List<TypDiplome> getAllTypeDiplomes();

	/**
	 * 
	 * @return the intern code of the diploma for foreigns students.
	 */
	String getCodDacEtr();
	
	/**
	 * 
	 * @return ReadRennes1
	 */
	IApogee getRemoteApo();
	
	
	//////////////////////////////////////////////////////////////
	// DateWeb
	//////////////////////////////////////////////////////////////

	/**
	 * @return the date d'ouverture des IA Web Primo non bacheliers
	 */
	String getDateDebWebPrimoNb();

	/**
	 * @return the date d'ouverture des IA Web Primo non neo bacheliers
	 */
	String getDateDebWebPrimoNnb();

	/**
	 * @return the date d'ouverture des IA Web de reinscription
	 */
	String getDateDebWeb();

	/**
	 * @param var : nom de la variable
	 * @return la valeur de la variable
	 */
	String getVariableAppli(String var);

}
