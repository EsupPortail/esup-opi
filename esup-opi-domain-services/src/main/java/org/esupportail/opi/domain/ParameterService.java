/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.opi.domain.beans.BeanProfile;
import org.esupportail.opi.domain.beans.mails.MailContent;
import org.esupportail.opi.domain.beans.parameters.AutoListPrincipale;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.Nomenclature;
import org.esupportail.opi.domain.beans.parameters.PieceJustiVet;
import org.esupportail.opi.domain.beans.parameters.PieceJustificative;
import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.Domain;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
import org.esupportail.opi.domain.beans.parameters.situation.TypeContrat;
import org.esupportail.opi.domain.beans.parameters.situation.TypeOrganisme;
import org.esupportail.opi.domain.beans.parameters.situation.TypeSituation;
import org.esupportail.opi.domain.beans.parameters.situation.TypeStatut;
import org.esupportail.opi.domain.beans.references.NombreVoeuCge;
import org.esupportail.opi.domain.beans.references.calendar.Calendar;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.calendar.ReunionCmi;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.ContactCommission;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.Member;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;





/**
 * The domain service interface.
 */
public interface ParameterService extends Serializable {

	
	//////////////////////////////////////////////////////////////
	// Profile
	//////////////////////////////////////////////////////////////

	
	/**
	 * Add a profile.
	 * @param profile
	 */
	void addProfile(Profile profile);
	
	/**
	 * Update a profile.
	 * @param profile
	 */
	void updateProfile(Profile profile);
	
	/**
	 * Delete a profile.
	 * @param profile
	 */
	void deleteProfile(Profile profile);
	
	/**
	 * @param id may be null
	 * @param code may be null
	 * @return Profile 
	 */
	Profile getProfile(Integer id, String code);
	
	/**
	 * Return all Profiles if temEnSve = null.
	 * @param temEnSve 
	 * @return Set< BeanProfile> 
	 */
	Set<BeanProfile> getProfiles(Boolean temEnSve);
	
	/**
	 * Test if profile code is unique.
	 * @param profile
	 * @return Boolean : true if code is unique
	 */
	Boolean profileCodeIsUnique(Profile profile);
	
	//////////////////////////////////////////////////////////////
	// Traitement
	//////////////////////////////////////////////////////////////

	
	/**
	 * Add a Traitement.
	 * @param traitement
	 */
	void addTraitement(Traitement traitement);
	
	/**
	 * Update a Traitement.
	 * @param traitement
	 */
	void updateTraitement(Traitement traitement);
	
	/**
	 * Delete a Traitement.
	 * @param traitement
	 */
	void deleteTraitement(Traitement traitement);
	
	/**
	 * According to the rights profile.
	 * @param p
	 * @param typeTraitement
	 * @param domain must be null if typeTraitment = type domain
	 * @return Set< Traitement>
	 */
	Set<Traitement> getTraitements(Profile p, String typeTraitement, Domain domain);
	
	/**
	 * Return traitement with the initialize le accessRight.
	 * @param id 
	 * @return Traitement 
	 */
	Traitement getTraitement(Integer id);
	
	
	/**
	 * @param id 
	 * @return Domain 
	 */
	Domain getDomain(Integer id);
	
	/**
	 * Return all functions if temEnSve = null.
	 * @param temEnSve 
	 * @param initAccess if true initialize le accessRights 
	 * @return Set< Fonction> 
	 */
	Set<Fonction> getFonctions(Boolean temEnSve, Boolean initAccess);
	
	/**
	 * Return all functions if temEnSve = null.
	 * if initAccess == null then initialize nothing.
	 * @param temEnSve 
	 * @param initAccess if true initialize the accessRights else initialize the functions 
	 * @return List< Fonction> 
	 */
	Set<Domain> getDomains(Boolean temEnSve, Boolean initAccess);
	
	/**
	 * Test if Traitement code is unique.
	 * @param traitement
	 * @return Boolean : true if rang is unique
	 */
	Boolean traitementCodeIsUnique(Traitement traitement);
	
	/**
	 * Test if function rang is unique for the same domain.
	 * @param function
	 * @param idDomain 
	 * @return Boolean : true if rang is unique
	 */
	Boolean functionRangIsUnique(Fonction function, Integer idDomain);
	
	/**
	 * Test if domain rang is unique.
	 * @param domain
	 * @return Boolean : true if rang is unique
	 */
	Boolean domainRangIsUnique(Domain domain);
	
	
	//////////////////////////////////////////////////////////////
	// AccessRight
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add the AccessRight. 
	 * @param accessRight 
	 */
	void addAccessRight(AccessRight accessRight);
	
	/**
	 * Update a accessRight.
	 * @param accessRight
	 */
	void updateAccessRight(AccessRight accessRight);
	
	
	//////////////////////////////////////////////////////////////
	// Nomenclature
	//////////////////////////////////////////////////////////////

	
	/**
	 * Add a Nomenclature.
	 * @param nomenclature
	 */
	void addNomenclature(Nomenclature nomenclature);
	
	/**
	 * Update a Nomenclature.
	 * @param nomenclature
	 */
	void updateNomenclature(Nomenclature nomenclature);
	
	/**
	 * Return all TypeConvocation if temEnSve = null.
	 * @param temEnSve 
	 * @return List< TypeDecision> 
	 */
	Set<TypeDecision> getTypeDecisions(Boolean temEnSve);
	
	/**
	 * Return all PJ if temEnSve = null.
	 * @param temEnSve 
	 * @return Set< PieceJustificative> 
	 */
	Set<PieceJustificative> getPJs(Boolean temEnSve);
	
	/**
	 * Return all PJ in use for the vet.
	 * @param vet the versionEtapes 
	 * @param codeRI
	 * @return List< PieceJustificative> 
	 */
	List<PieceJustificative> getPiecesJ(Set<VersionEtpOpi> vet, String codeRI);
	

	/**
	 * Return all PJ in use for ONE vet.
	 * @param vetOpi
	 * @param codeRI
	 * @return List< PieceJustificative> 
	 */
	List<PieceJustificative> getPiecesJ(VersionEtpOpi vetOpi, String codeRI);
	
	/**
	 * Return all MotivationAvis.
	 * @param temEnSve 
	 * @return List< MotivationAvis> 
	 */
	Set<MotivationAvis> getMotivationsAvis(Boolean temEnSve);
	
	/**
	 * Return all Campagne.
	 * @param temEnSve 
	 * @param codeRI (can be null)
	 * @return List< Campagne>
	 */
	Set<Campagne> getCampagnes(Boolean temEnSve, String codeRI);
	
	/**
	 * Return the campagne en service en fonction du codeRI. 
	 * @param codeRI
	 * @return Campagne
	 */
	Campagne getCampagneEnServ(int codeRI);
	
	/**
	 * Return true si il ya une campagne FC et une campagne FI en service, false sinon.
	 */
	Boolean isCampagnesFIAndFCEnServ();
	
	/**
	 * Return all campagnes by codAnu.
	 * @param codAnu
	 * @return Set< Campagne>
	 */
	Set<Campagne> getCampagnesAnu(String codAnu);

	/**
	 * @param id 
	 * @return Nomenclature 
	 */
	Nomenclature getNomenclature(Integer id);
	
	/**
	 * Delete a Nomenclature.
	 * @param nomenclature
	 */
	void deleteNomenclature(Nomenclature nomenclature);
	
	/**
	 * Test if nomenclature code is unique.
	 * @param nomenclature
	 * @return Boolean : true if code is unique for a nomenclature type
	 */
	Boolean nomenclatureCodeIsUnique(Nomenclature nomenclature);
	
	/**
	 * return true if can always delete.
	 * @param nom
	 * @return boolean true if can delete.
	 */
	boolean canDeleteNomclature(Nomenclature nom);
	
	
	//////////////////////////////////////////////////////////////
	// Commission
	//////////////////////////////////////////////////////////////

	
	/**
	 * Add a Commission.
	 * @param commission
	 */
	void addCommission(Commission commission);
	
	/**
	 * Update a Commission.
	 * @param commission
	 */
	void updateCommission(Commission commission);
	
	/**
	 * Return all Commissions if temEnSve = null.
	 * @param temEnSve 
	 * @return Set< Commission> 
	 */
	Set<Commission> getCommissions(Boolean temEnSve);
	
	/**
	 * @param id can be null
	 * @param code can be null 
	 * @return Commission 
	 */
	Commission getCommission(Integer id, String code);
	
	/**
	 * Delete a Commission.
	 * @param commission
	 */
	void deleteCommission(Commission commission);
	
	/**
	 * Test if commission code is unique.
	 * @param commission
	 * @return Boolean : true if code is unique
	 */
	Boolean commissionCodeIsUnique(Commission commission);
	
	//////////////////////////////////////////////////////////////
	// ContactCommission
	//////////////////////////////////////////////////////////////

	
	/**
	 * Add a ContactCommission.
	 * @param contactCommission
	 */
	void addContactCommission(ContactCommission contactCommission);
	
	/**
	 * Update a Commission.
	 * @param contactCommission
	 */
	void updateContactCommission(ContactCommission contactCommission);
	

	//////////////////////////////////////////////////////////////
	// Member
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a Member.
	 * @param member
	 */
	void addMember(Member member);
	
	/**
	 * Update a Member.
	 * @param member
	 */
	void updateMember(Member member);
	
	/**
	 * Delete a Member.
	 * @param member
	 */
	void deleteMember(List<Member> member);
	
	//////////////////////////////////////////////////////////////
	// TraitementCmi
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a TraitementCmi.
	 * @param traitementCmi
	 */
	void addTraitementCmi(TraitementCmi traitementCmi);
	
	/**
	 * Update a TraitementCmi.
	 * @param traitementCmi
	 */
	void updateTraitementCmi(TraitementCmi traitementCmi);
	
	/**
	 * Delete a TraitementCmi.
	 * @param traitementCmi
	 */
	void deleteTraitementCmi(List<TraitementCmi> traitementCmi);
	
	/**
	 * @param versionEtpOpi
	 * @param initSelection if true init the proxy selection.
	 * @return TraitementCmi
	 */
	TraitementCmi getTraitementCmi(VersionEtpOpi versionEtpOpi, Boolean initSelection);
	
	/**
	 * @param id
	 * @return TraitementCmi
	 */
	TraitementCmi getTraitementCmi(Integer id);
	
	/**
	 * Test if t connect to commission.
	 * @param t
	 * @return Boolean true if is connect
	 */
	Boolean isConnectCmi(TraitementCmi t);
	
	
	//////////////////////////////////////////////////////////////
	// Calendar
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a Calendar.
	 * @param calendar
	 */
	void addCalendar(Calendar calendar);
	
	/**
	 * Update a Calendar.
	 * @param calendar
	 */
	void updateCalendar(Calendar calendar);
	
	/**
	 * Test if calendar code is unique by calendar type.
	 * @param calendar
	 * @return Boolean : true if code is unique
	 */
	Boolean calendarCodeIsUnique(Calendar calendar);
	
	/**
	 * Return all Calendars if temEnSve = null.
	 * If typCal not null return only the specified type calendar.
	 * @param temEnSve 
	 * @param typeCal 
	 * @return List< Calendar> 
	 */
	List<Calendar> getCalendars(Boolean temEnSve, String typeCal);
	
	
	/**
	 * Return all Calendars in use for the Version Etape.
	 * @param versionEtpOpi 
	 * @return List< CalendarIns> 
	 */
	Set<CalendarIns> getCalendars(VersionEtpOpi versionEtpOpi);
	
	/**
	 * Delete a Calendar.
	 * @param calendar
	 */
	void deleteCalendar(Calendar calendar);

	/**
	 * Return all CalendarIns for the commission.
	 * @param commission
	 * @return List< CalendarIns>
	 */
	List<CalendarIns> getCalendarIns(Commission commission);

	//////////////////////////////////////////////////////////////
	// Formulaire
	//////////////////////////////////////////////////////////////

	/**
	 * Add a Form.
	 * @param form
	 */
	void addFormulaireCmi(FormulaireCmi form);

	/**
	 * @param form
	 */
	void deleteFormulaireCmi(FormulaireCmi form);

	/**
	 * @param traitements
	 * @param codeRI 
	 * @return a corresponding map of cmi formulaire
	 */
	Map<VersionEtpOpi, FormulaireCmi> getFormulairesCmi(Set<TraitementCmi> traitements, Integer codeRI);

	/**
	 * @param formNorme
	 */
	void addIndFormulaire(IndFormulaire formNorme);

	/**
	 * @param form
	 * @param numDoss
	 * @param sLabelRI : shortLabel du RegimeInscription
	 */
	void deleteIndFormulaire(IndFormulaire form, String numDoss, String sLabelRI);

	
	/**
	 * @param indSelected
	 * @return a corresponding map of ind forms.
	 */
	Map<VersionEtpOpi, IndFormulaire> getIndFormulaires(Individu indSelected);
	
	/**
	 * @param indSelected
	 * @param voeu
	 * @return boolean
	 */
	boolean isExitFormulaireInd(Individu indSelected, IndVoeu voeu);
	
	/**
	 * @param vet
	 * @param codeRI: code du RegimeInscription
	 * @return boolean
	 */
	boolean isExitFormulaireEtp(VersionEtpOpi vet, String codeRI);
	
	/**
	 * Method used to know if all forms were created by the Individu.
	 * 
	 * @param indSelected
	 * @param codeRI : code du RegimeInscription
	 * @return boolean
	 */
	boolean isAllFormulairesCreated(Individu indSelected, String codeRI);
	
	/**
	 * Method used to know if all forms were created by the Individu for the Set
	 * of TraitementCMi.
	 * 
	 * @param indSelected
	 * @param codeRI : code du RegimeInscription
	 * @param traitementsCmi
	 * @return boolean
	 */
	boolean isAllFormulairesCreatedByTraitementsCmi(Individu indSelected,
			Integer codeRI, Set <TraitementCmi> traitementsCmi);

	/**
	 * @param camp
	 * @param sLabelRI : shortLabel du RegimeInscription
	 * @return the map of the form names with the numDoss for key
	 */
	Map<String, List<String>> getAllFormNamesMap(Campagne camp, String sLabelRI);

	/**
	 * Purge la table IND_FORMULAIRE.
	 * @param camp
	 */
	void purgeIndFormulaireCamp(Campagne camp);

	//////////////////////////////////////////////////////////////
	// ReunionCmi
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a ReunionCmi.
	 * @param reunionCmi
	 */
	void addReunionCmi(ReunionCmi reunionCmi);
	
	
	/**
	 * Delete a ReunionCmi.
	 * @param reunionCmi
	 */
	void deleteReunionCmi(List<ReunionCmi> reunionCmi);
	
	/**
	 * Update a ReunionCmi.
	 * @param reunionCmi
	 */
	void updateReunionCmi(ReunionCmi reunionCmi);
	
	//////////////////////////////////////////////////////////////
	// PieceJustiVet
	//////////////////////////////////////////////////////////////
	/**
	 * Delete a ReunionCmi.
	 * @param pieceJustiVet
	 */
	void deletePieceJustiVet(PieceJustiVet pieceJustiVet);
	
	/**
	 * Delete a ReunionCmi.
	 * @param pieceJustiVet
	 */
	void deletePieceJustiVetWithFlush(PieceJustiVet pieceJustiVet);
	
	//////////////////////////////////////////////////////////////
	// NombreVoeuCge
	//////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @return liste de nombre de voeux par CGE
	 */
	List<NombreVoeuCge> getAllNombreDeVoeuByCge();
	

	/**
	 * Add a NombreVoeuCge.
	 * @param nombreVoeuCge
	 */
	void addNombreVoeuCge(NombreVoeuCge nombreVoeuCge);
	
	
	/**
	 * Delete a NombreVoeuCge.
	 * @param nombreVoeuCge
	 */
	void deleteNombreVoeuCge(NombreVoeuCge nombreVoeuCge);
	
	/**
	 * Update a NombreVoeuCge.
	 * @param nombreVoeuCge
	 */
	void updateNombreVoeuCge(NombreVoeuCge nombreVoeuCge);
	
	
	//////////////////////////////////////////////////////////////
	// MailContent
	//////////////////////////////////////////////////////////////
	
	
	/**
	 * @param code
	 * @return MailContent
	 */
	MailContent getMailContent(String code);
	
	/**
	 * @return all mailContent
	 */
	List<MailContent> getMailContents();
	
	/**
	 * Update a MailContent.
	 * @param mailContent
	 */
	void updateMailContent(MailContent mailContent);
	
	/**
	 * Add a MailContent.
	 * @param mailContent
	 */
	void addMailContent(MailContent mailContent);
	
	
	//////////////////////////////////////////////////////////////
	// CalendarRDV
	//////////////////////////////////////////////////////////////
	
	/**
	 * 
	 * @return liste des calendriers de rendez-vous
	 */
	List<CalendarRDV> getCalendarRdv();
	
	/**
	 * Add CalendarRDV.
	 * @param cal
	 */
	void addCalendarRdv(CalendarRDV cal);
	
	/**
	 * Update CalendarRDV.
	 * @param cal
	 */
	void updateCalendarRdv(CalendarRDV cal);
	
	/**
	 * delete CalendarRDV.
	 * @param cal
	 */
	void deleteCalendarRdv(CalendarRDV cal);
	
	/**
	 * @param idCalendarRdv
	 * @param month 
	 * @param dateDebut
	 * @param dateFin
	 * @return List of ListEtudiants in CGE where rdv is between dateDebut & dateFin
	 */
	int getListEtudiantsParCalendarRdvParPeriode(int idCalendarRdv, int month, Date dateDebut, Date dateFin);
	
	/**
	 * @param idCalendarRdv
	 * @param dateDuJour 
	 * @param dateDebut
	 * @param dateFin
	 * @return List of ListEtudiants in CGE where rdv is between dateDebut & dateFin
	 */
	int getListEtudiantsParCalendarRdvParPeriode(int idCalendarRdv, Date dateDuJour, Date dateDebut, Date dateFin);
	
	/**
	 * @param idCalendarRdv
	 * @param month
	 * @param date
	 * @param dateDebut
	 * @param dateFin
	 * @return List of ListEtudiants in CGE where rdv is on half day
	 */
	int getListEtudiantsParCalendarRdvParDemiJournee(
			int idCalendarRdv, int month, 
			Date date, Date dateDebut, Date dateFin);
	
	//////////////////////////////////////////////////////////////
	// TypeTraitement
	//////////////////////////////////////////////////////////////
	/**
	 * Return all TypeTraitement.
	 * @return List< TypeTraitement>
	 */
	List<TypeTraitement> getTypeTraitements();
	
	
	//////////////////////////////////////////////////////////////
	// TypeConvocation
	//////////////////////////////////////////////////////////////
	
	/**
	 * Return all TypeConvocation.
	 * @return List< TypeConvocation>
	 */
	List<TypeConvocation> getTypeConvocations();
	
	
	//////////////////////////////////////////////////////////////
	// TypeContrat
	//////////////////////////////////////////////////////////////
	
	/**
	 * Return all TypeContrat.
	 * @return List< TypeContrat>
	 */
	List<TypeContrat> getTypeContrats();
	
	
	//////////////////////////////////////////////////////////////
	// TypeStatut
	//////////////////////////////////////////////////////////////
	
	/**
	 * Return all TypeStatut.
	 * @return List< TypeStatut>
	 */
	List<TypeStatut> getTypeStatuts();
	
	//////////////////////////////////////////////////////////////
	// TypeOrganisme
	//////////////////////////////////////////////////////////////
	
	/**
	 * Return all TypeOrganisme.
	 * @return List< TypeOrganisme>
	 */
	List<TypeOrganisme> getTypeOrganismes();
	
	//////////////////////////////////////////////////////////////
	// TypeSituation
	//////////////////////////////////////////////////////////////
	
	/**
	 * Return all TypeSituation.
	 * @return List< TypeSituation>
	 */
	List<TypeSituation> getTypeSituations();
	
	//////////////////////////////////////////////////////////////
	// LinkTrtCmiCamp
	//////////////////////////////////////////////////////////////

	
	/**
	 * Add a LinkTrtCmiCamp.
	 * @param linkTrtCmiCamp
	 */
	void addLinkTrtCmiCamp(LinkTrtCmiCamp linkTrtCmiCamp);
	
	/**
	 * Update a LinkTrtCmiCamp.
	 * @param linkTrtCmiCamp
	 */
	void updateLinkTrtCmiCamp(LinkTrtCmiCamp linkTrtCmiCamp);
	
	/**
	 * @param traitementCmi
	 * @param campagne
	 * @return LinkTrtCmiCamp
	 */
	LinkTrtCmiCamp getLinkTrtCmiCamp(TraitementCmi traitementCmi, Campagne campagne);
	
	/**
	 * Delete a LinkTrtCmiCamp.
	 * @param linkTrtCmiCamp
	 */
	void deleteLinkTrtCmiCamp(LinkTrtCmiCamp linkTrtCmiCamp);
	
	/**
	 * Put the temEnService of the links of the campagne.
	 * @param camp
	 * @param temEnServ
	 */
	void updateLinkTrtCmiCampTemEnServ(Campagne camp, boolean temEnServ);
	
	//////////////////////////////////////////////////////////////
	// AutoListPrincipale
	//////////////////////////////////////////////////////////////
	
	/**
	 * @return The list of AutoListPrincipale
	 */
	List<AutoListPrincipale> getAutoListPrincipale();
	
	/**
	 * @param indVoeu
	 * @return AutoListPrincipale
	 */
	AutoListPrincipale getAutoListPrincipale(final IndVoeu indVoeu);
	
	//////////////////////////////////////////////////////////////
	// misc
	//////////////////////////////////////////////////////////////

	/**
	 * @return String
	 */
	String getDateBackDefault();
	
	/**
	 * @return String
	 */
	String getPrefixCodCalCmi();
	
	/**
	 * @return String
	 */
	String getPrefixLibCalCmi();
}
