/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.esupportail.opi.domain.beans.mails.MailContent;
import org.esupportail.opi.domain.beans.parameters.AutoListPrincipale;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.Nomenclature;
import org.esupportail.opi.domain.beans.parameters.PieceJustiVet;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.Domain;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
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
 * The DAO service interface.
 */
public interface ParameterDaoService extends Serializable {

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
	 * Return all Profiles if temEnSve = null.
	 * @param temEnSve 
	 * @return List< Profile> 
	 */
	List<Profile> getProfiles(Boolean temEnSve);
	
	/**
	 * @param id may be null
	 * @param code may be null
	 * @return Profile 
	 */
	Profile getProfile(Integer id, String code);
	
	/**
	 * Delete a profile.
	 * @param profile
	 */
	void deleteProfile(Profile profile);
	
	
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
	 * @return List< Traitement>
	 */
	List<Traitement> getTraitements(Profile p, String typeTraitement, Domain domain);
	
	/**
	 * Return all functions if temEnSve = null.
	 * if initAccess == null then initialize nothing.
	 * @param temEnSve 
	 * @param initAccess if true initialize the accessRights else initialize the functions 
	 * @return List< Fonction> 
	 */
	List<Fonction> getFonctions(Boolean temEnSve, Boolean initAccess);
	
	/**
	 * Return all domains if temEnSve = null.
	 * @param temEnSve 
	 * @param initAccess if true initialize le accessRights 
	 * @return Set< Domain> 
	 */
	List<Domain> getDomains(Boolean temEnSve, Boolean initAccess);
	
	
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
	
	
	
	//////////////////////////////////////////////////////////////
	// AccessRight
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add the AccessRight. 
	 * @param accessRight 
	 */
	void addAccessRight(AccessRight accessRight);

	/**
	 * Delete a accessRight.
	 * @param accessRight
	 */
	void deleteAccessRight(AccessRight accessRight);
	
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
	 * Return all Nomenclatures if temEnSve = null.
	 * @param temEnSve 
	 * @param typNomenclature 
	 * @return List< Nomenclature> 
	 */
	List<Nomenclature> getNomenclatures(Boolean temEnSve, Class< ? > typNomenclature);
	
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
	 * 
	 * @param typeD
	 * @return boolean true if can delete.
	 */
	boolean canDeleteTypeDecision(TypeDecision typeD);
	
	/**
	 * 
	 * @param motiv
	 * @return boolean true if can delete.
	 */
	boolean canDeleteMotivation(final MotivationAvis motiv);
	
	
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
	 * @return List< Commission> 
	 */
	List<Commission> getCommissions(Boolean temEnSve);
	
	/**
	 * @param id
	 * @param code 
	 * @return Commission 
	 */
	Commission getCommission(Integer id, String code);
	
	/**
	 * Delete a Commission.
	 * @param commission
	 */
	void deleteCommission(Commission commission);

	
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
	void deleteMember(Member member);
	
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
	void deleteTraitementCmi(TraitementCmi traitementCmi);
	
	/**
	 * @param versionEtpOpi (can be null)
	 * @param codCge (can be null)
	 * @param initSelection if true init the proxy selection.
	 * @return TraitementCmi
	 */
	TraitementCmi getTraitementCmi(VersionEtpOpi versionEtpOpi, String codCge, Boolean initSelection);
	
	/**
	 * @param id
	 * @return TraitementCmi
	 */
	TraitementCmi getTraitementCmi(Integer id);
	
	//////////////////////////////////////////////////////////////
	// Calendar
	//////////////////////////////////////////////////////////////

	
	/**
	 * Add a Calendar.
	 * @param calendar
	 */
	void addCalendar(Calendar calendar);
	
	/**
	 * Return a Calendar.
	 * @param id
	 * @return Calendar
	 */
	Calendar getCalendar(Integer id);
	
	/**
	 * Delete a Calendar.
	 * @param calendar
	 */
	void deleteCalendar(Calendar calendar);
	
	/**
	 * Update a Calendar.
	 * @param calendar
	 */
	void updateCalendar(Calendar calendar);
	
	/**
	 * Return all Calendars if temEnSve = null.
	 * If typCal not null return only the specified type calendar.
	 * @param temEnSve 
	 * @param typeCal 
	 * @return List< Calendar> 
	 */
	List<Calendar> getCalendars(Boolean temEnSve, String typeCal);
	
	/**
	 * Return all CalendarIns for the commission.
	 * @param commission
	 * @return List< CalendarIns>
	 */
	List<CalendarIns> getCalendarIns(Commission commission);

	//////////////////////////////////////////////////////////////
	// ReunionCmi
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a Form.
	 * @param form
	 */
	void addFormulaire(FormulaireCmi form);

	/**
	 * @param form
	 */
	void deleteFormulaire(FormulaireCmi form);

	/**
	 * @param versionsEtpOpi
	 * @param codeRI  : code du RegimeInscription
	 * @return the list of cmi forms
	 */
	List<FormulaireCmi> getFormulairesCmi(
			Set<VersionEtpOpi> versionsEtpOpi, Integer codeRI);

	/**
	 * @param formNorme
	 */
	void addIndFormulaire(IndFormulaire formNorme);

	/**
	 * @param formNorme
	 */
	void deleteIndFormulaire(IndFormulaire formNorme);

	/**
	 * @param indSelected
	 * @return the list of ind forms
	 */
	List<IndFormulaire> getIndFormulaires(Individu indSelected);
	
	/**
	 * @param indSelected
	 * @return the list of ind forms
	 */
	List<IndFormulaire> getIndFormulaires(Individu indSelected, boolean allForm);

	/**
	 * @param camp
	 * @return the list of ind forms
	 */
	List<IndFormulaire> getIndFormulaires(Campagne camp);

	/**
	 * Delete the indFormulaire of indSelected.
	 * @param indSelected
	 */
	void deleteIndFormulaires(Individu indSelected);
	
	/**
	 * Purge la table IND_FORMULAIRE.
	 * @param camp
	 */
	void purgeIndFormulaireCamp(Campagne camp);

	/**
	 * @param indSelected
	 * @param voeu
	 * @return boolean
	 */
	boolean isExitFormulaireInd(Individu indSelected, VersionEtpOpi vet);
	
	/**
	 * @param vet
	 * @param codeRI : code du RegimeInscription
	 * @return boolean
	 */
	boolean isExitFormulaireEtp(VersionEtpOpi vet, String codeRI);
	
	/**
	 * Method used to know how many forms the Individu has to create depending
	 * on his Voeux.
	 * 
	 * @param indVoeux
	 * @param ri : code du RegimeInscription
	 * @return the numbre of forms the Individu has to create.
	 */
	Integer nbFormulairesToCreateForIndividu(Set<IndVoeu> indVoeux, String codeRI);

	/**
	 * Method used to know how many forms the Individu created.
	 * 
	 * @param indSelected
	 * @param indVoeux
	 * @return the numbre of forms the Individu has to create.
	 */
	Integer nbFormulairesCreatedByIndividu(final Individu indSelected,
			final Set<IndVoeu> indVoeux);

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
	void deleteReunionCmi(ReunionCmi reunionCmi);
	
	/**
	 * Update a ReunionCmi.
	 * @param reunionCmi
	 */
	void updateReunionCmi(ReunionCmi reunionCmi);
	
	//////////////////////////////////////////////////////////////
	// PieceJustiVet
	//////////////////////////////////////////////////////////////
	/**
	 * Delete a pieceJustiVet.
	 * @param pieceJustiVet
	 */
	void deletePieceJustiVet(PieceJustiVet pieceJustiVet);
	
	/**
	 * Delete a pieceJustiVet.
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
	 * @return List of ListEtudiants in CalendarRdv where rdv is between dateDebut & dateFin
	 */
	int getListEtudiantsParCalendarRdvParPeriode(int idCalendarRdv, int month, Date dateDebut, Date dateFin);

	/**
	 * @param idCalendarRdv
	 * @param dateDuJour 
	 * @param dateDebut
	 * @param dateFin
	 * @return List of ListEtudiants in CalendarRdv where rdv is between dateDebut & dateFin
	 */
	int getListEtudiantsParCalendarRdvParPeriode(int idCalendarRdv, Date dateDuJour, Date dateDebut, Date dateFin);
	
	/**
	 * @param idCalendarRdv
	 * @param month
	 * @param date 
	 * @param dateDebut
	 * @param dateFin
	 * @return List of ListEtudiants in CalendarRdv where rdv is on half day
	 */
	int getListEtudiantsParCalendarRdvParDemiJournee(
			int idCalendarRdv, int month, 
			Date date, Date dateDebut, Date dateFin);
	
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
}
