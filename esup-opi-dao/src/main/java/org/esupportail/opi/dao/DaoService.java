/**
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.dao;



import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fj.P3;
import fj.data.Seq;
import org.esupportail.opi.domain.beans.VersionManager;
import org.esupportail.opi.domain.beans.parameters.AutoListPrincipale;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.PieceJustificative;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.VetAutoLp;
import org.esupportail.opi.domain.beans.pilotage.ArchiveTask;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.references.rendezvous.Horaire;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire;
import org.esupportail.opi.domain.beans.references.rendezvous.TrancheFermee;
import org.esupportail.opi.domain.beans.references.rendezvous.VetCalendar;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.User;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursus;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.domain.beans.user.situation.IndSituation;
import org.esupportail.opi.utils.primefaces.PFFilters;
import org.primefaces.model.SortOrder;

import fj.P2;
import fj.data.Option;
import fj.data.Stream;



/**
 * The DAO service interface.
 */
public interface DaoService extends Serializable {

	
	//////////////////////////////////////////////////////////////
	// OBJECT
	//////////////////////////////////////////////////////////////
	
	/**
	 * Permet de rendre persistant un proxy contenu dans args.
	 * @param args
	 * @param proxy
	 * @param proxyClass
	 */
	void initOneProxyHib(Object args, Object proxy, Class< ? > proxyClass);
	
	
	//////////////////////////////////////////////////////////////
	// User
	//////////////////////////////////////////////////////////////
	
	/**
	 * @param id (codeEtu for Individu and login for Manager)
	 * @param clazz 
	 * @return the User instance that corresponds to an id.
	 */
	User getUser(String id, Class< ? > clazz);
	
	/**
	 * @param id
	 * @return the User instance that corresponds to an id.
	 */
	User getUser(Integer id);
	
	/**
	 * Return the individu that have the given numDosOpi.
	 * @param numDosOpi
	 * @param dateNaissance 
	 * @return the individu
	 */
	Individu getIndividu(String numDosOpi, Date dateNaissance);
	
	/**
	 * Return the individual that have the given codEtu.
	 * @param codEtu
	 * @return
	 */
	Individu getIndividuCodEtu(String codEtu);
	
	/**
	 * Return the individual for the given INE.
	 * @param codeNNE
	 * @param cleNNE
	 * @return
	 */
	Individu getIndividuINE(String codeNNE, String cleNNE);
	
	/**
	 * Return the individu that have the given mail.
	 * @param mail 
	 * @return Individu
	 */
	Individu getIndividu(String mail);
	
	/**
	 * Return the individual with the wishes on this vet.
	 * @param trt
	 * @param state state of wish. (can be null). 
	 * @return Individu
	 */
	List<Individu> getIndividus(TraitementCmi trt, String state);
	
	/**
	 * Return the individus fot the traitementCmi.
	 * @param trt
	 * @return Individu
	 */
	List<Individu> getIndividus(TraitementCmi trt);
	
	/**
	 * Return the individuals managed by commission.
	 * @param commission 
	 * @param validate
	 * @param listeRI
	 * @return List< Individu>
	 */
	List<Individu> getIndividus(Commission commission, Boolean validate, List<String> listeRICodes);

	/**
	 * withWishes if null return allIndividus in use.
	 * @param withWishes if false Return the individuals without vows.
	 * @param codeTypeTrt if withWishes true or null return the individuals with vows for the treatment type.
	 * @return List< Individu>
	 */
	List<Individu> getIndividus(Boolean withWishes, String codeTypeTrt);
	
	/**
	 * Return the individuals with the search module.
	 * @param nomPatronymique
	 * @param prenom
	 * @param dateNaissance
	 * @param codPayNaissance
	 * @param codDepPaysNaissance
	 * @return
	 */
	List<Individu> getIndividuSearch(String nomPatronymique, String prenom,
			Date dateNaissance, String codPayNaissance, String codDepPaysNaissance);

	/**
	 * Return all individus with a codeEtu.
	 * @return List< Individu>
	 */
	List<Individu> getAllIndividusEtu();
	
	/**
	 * Return all individus of the campagne.
	 * @param campagne
	 * @return List< Individu>
	 */
	List<Individu> getIndividusByCampagne(Campagne campagne, Boolean temSve);
	
	/**
	 * Retrieve a slice of {@link Individu}
	 * 
	 *
     * @param pfFilters
     * @param wishCreation
     * @return
	 */
	P2<Long, Stream<Individu>> sliceOfInd(PFFilters pfFilters,
                                          Set<TypeDecision> typesDec,
                                          Option<Boolean> validWish,
                                          Option<Boolean> treatedWish,
                                          Option<Date> wishCreation,
                                          Option<String> codeTypeTrtmt,
                                          Set<TraitementCmi> trtCmis,
                                          Set<Integer> listCodesRI);
	
	/**
	 * Login gest = uid@domain.
	 * @param uid
	 * @return the Gestionnaire instance that corresponds to a login.
	 */
	Gestionnaire getManager(String uid);

	/**
	 * Return managers if they ara not expiry.  
	 * @param date if null return all managers
	 * @return the list of all the managers.
	 */
	List<Gestionnaire> getManagers(Date date);

	/**
	 * Return not expired managers
	 * @return the list of all the managers.
	 */
	List<Gestionnaire> getManagers();
	
	/**
	 * @param commission
	 * @return the list of all the managers for the commission.
	 */
	List<Gestionnaire> getManagersByCmi(Commission commission);

	/**
	 * Add a user.
	 * @param user
	 */
	void addUser(User user);

	/**
	 * Delete a user.
	 * @param user
	 */
	void deleteUser(User user);
	
	/**
	 * Delete the list of users.
	 * @param users
	 */
	void deleteUserList(List<User> users);

	/**
	 * Update a user.
	 * @param user
	 */
	void updateUser(User user);

	//////////////////////////////////////////////////////////////
	// IndCursus
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a indCursus.
	 * @param indCursus
	 */
	void addIndCursus(IndCursus indCursus);

	/**
	 * Update a indCursus.
	 * @param indCursus
	 */
	void updateIndCursus(IndCursus indCursus);

	/**
	 * Delete a indCursus.
	 * @param indCursus
	 */
	void deleteIndCursus(IndCursus indCursus);
	
	//////////////////////////////////////////////////////////////
	// IndCursusScol
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a indCursusScol.
	 * @param indCursusScol
	 */
	void addIndCursusScol(IndCursusScol indCursusScol);

	/**
	 * Update a indCursusScol.
	 * @param indCursusScol
	 */
	void updateIndCursusScol(IndCursusScol indCursusScol);
	
	/**
	 * Delete a indCursusScol.
	 * @param indCursusScol
	 */
	void deleteIndCursusScol(IndCursusScol indCursusScol);

	//////////////////////////////////////////////////////////////
	// Adresse
	//////////////////////////////////////////////////////////////
	

	/**
	 * Update a adresse.
	 * @param adresse
	 */
	void updateAdresse(Adresse adresse);
	
	/**
	 * Add a adresse.
	 * @param adresse
	 */
	void addAdresse(Adresse adresse);


	//////////////////////////////////////////////////////////////
	// IndBac
	//////////////////////////////////////////////////////////////
	
	/**

	 * Add a indBac.
	 * @param indBac
	 */
	void addIndBac(IndBac indBac);

	/**
	 * Update a indBac.
	 * @param indBac
	 */
	void updateIndBac(IndBac indBac);
	
	/**
	 * Delete a indBac.
	 * @param indBac
	 */
	void deleteIndBac(IndBac indBac);

	
	//////////////////////////////////////////////////////////////
	// Voeu
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a Voeu.
	 * @param voeu
	 */
	void addIndVoeu(IndVoeu voeu);
	
	/**
	 * Delete a Voeu.
	 * @param voeu
	 */
	void deleteIndVoeu(IndVoeu voeu);
	

	/**
	 * Update a Voeu.
	 * @param voeu
	 */
	void updateIndVoeu(IndVoeu voeu);


	/**
	 * @return the list of accepted vows.
	 */
	List<IndVoeu> getVoeuxAcceptes();
	
	/**
	 * Update the list of IndVoeu by setting the temEnServ.
	 * @param link
	 * @param temEnServ
	 */
	void updateIndVoeuTemEnServ(LinkTrtCmiCamp link, boolean temEnServ);
	
	//////////////////////////////////////////////////////////////
	// VersionManager
	//////////////////////////////////////////////////////////////
	
	/**
	 * @return the VersionManager of the database.
	 */
	VersionManager getVersionManager();

	/**
	 * Update a VersionManager.
	 * @param versionManager
	 */
	void updateVersionManager(VersionManager versionManager);
	
	
	//////////////////////////////////////////////////////////////
	// MissingPiece
	//////////////////////////////////////////////////////////////

	/**
	 * All missingPiece for this individual.
	 * @param individu (can be null)
	 * @param cmi (can be null)
	 * @return la liste des pieces manquantes
	 */
	List<MissingPiece> getMissingPiece(Individu individu, Commission cmi);
	
	/**
	 * Add a missingPiece.
	 * @param missingPiece
	 */
	void addMissingPiece(MissingPiece missingPiece);

	/**
	 * delete the missing piece list.
	 * if all attributes are null, this method do nothing.
	 * @param listMP can be null
	 * @param piece can be null
	 */
	void deleteMissingPiece(List<MissingPiece> listMP, PieceJustificative piece);

	/**
	 * Update a missingPiece.
	 * @param missingPiece
	 */
	void updateMissingPiece(MissingPiece missingPiece);
	
	/**
	 * Purge la table MISSING_PIECE pour la campagne.
	 * @param camp
	 */
	void purgeMissingPieceCamp(Campagne camp);

	
	//////////////////////////////////////////////////////////////
	// Opinion
	//////////////////////////////////////////////////////////////

	/**
	 * All the opinions pour un voeu.
	 * @param indVoeu
	 * @return la liste d'avis
	 */
	List<Avis> getAvis(IndVoeu indVoeu);

	/**
	 * All the opinions pour une etape.
	 * @param codEtp
	 * @param codVrsVet
	 * @return la liste de Avis
	 */
	List<Avis> getAvisByEtp(String codEtp, Integer codVrsVet);
	
	/**
	 * Add an avis.
	 * @param avis
	 */
	void addAvis(Avis avis);
	
	/**
	 * Update an avis.
	 * @param avis
	 */
	void updateAvis(Avis avis);

	//////////////////////////////////////////////////////////////
	// RENDEZ VOUS
	//////////////////////////////////////////////////////////////

	/**
	 * @param indDate
	 */
	void addIndividuDate(IndividuDate indDate);

	/**
	 * @param indDate
	 */
	void updateIndividuDate(IndividuDate indDate);

	/**
	 * @param indDate
	 */
	void deleteIndividuDate(IndividuDate indDate);
	
	/**
	 * @param calRdv
	 * @return the list of students  
	 */
	List<IndividuDate> getIndividusDate(CalendarRDV calRdv);

	/**
	 * @param indVoeu
	 * @return the lstudent  
	 */
	IndividuDate getIndividuDate(IndVoeu indVoeu);

	/**
	 * Delete the list of individuDate corresponding to the individual.
	 * @param ind
	 */
	void deleteAllIndividuDate(Individu ind);
	
	/**
	 * Purge la table RDV_INDIVIDU_DATE.
	 * @param camp
	 */
	void purgeIndividuDateCamp(Campagne camp);
	
	/**
	 * @param ho
	 */
	void addHoraire(Horaire ho);

	/**
	 * @param ho
	 */
	void updateHoraire(Horaire ho);

	/**
	 * @param ho
	 */
	void deleteHoraire(Horaire ho);

	/**
	 * @param trFermee
	 */
	void addTrancheFermee(TrancheFermee trFermee);

	/**
	 * @param trFermee
	 */
	void updateTrancheFermee(TrancheFermee trFermee);

	/**
	 * @param trFermee
	 */
	void deleteTrancheFermee(TrancheFermee trFermee);
	
	/**
	 * @param vetCalendar
	 */
	void addVetCalendar(VetCalendar vetCalendar);

	/**
	 * @param vetCalendar
	 */
	void updateVetCalendar(VetCalendar vetCalendar);

	/**
	 * @param vetCalendar
	 */
	void deleteVetCalendar(VetCalendar vetCalendar);

	/**
	 * @param jourHoraire
	 */
	void addJourHoraire(JourHoraire jourHoraire);

	/**
	 * @param jourHoraire
	 */
	void updateJourHoraire(JourHoraire jourHoraire);

	/**
	 * @param jourHoraire
	 */
	void deleteJourHoraire(JourHoraire jourHoraire);

	/**
	 * @param jourHoraire
	 */
	void refreshJourHoraire(JourHoraire jourHoraire);
	
	/**
	 * @param calendarRdv
	 */
	void refreshCalendarRdv(CalendarRDV calendarRdv);

	//////////////////////////////////////////////////////////////
	// ARCHIVE TASK
	//////////////////////////////////////////////////////////////

	/**
	 * Add a archive task.
	 * @param archiveTask
	 */
	void addArchiveTask(ArchiveTask archiveTask);
	
	/**
	 * Delete a archive task.
	 * @param archiveTask
	 */
	void deleteArchiveTask(ArchiveTask archiveTask);
	
	/**
	 * Update a archive task.
	 * @param archiveTask
	 */
	void updateArchiveTask(ArchiveTask archiveTask);
	
	/**
	 * Get all archive tasks.
	 * @return List< ArchiveTask>
	 */
	List<ArchiveTask> getArchiveTasks();
	
	/**
	 * Get all archive tasks to execute.
	 * @return List< ArchiveTask>
	 */
	List<ArchiveTask> getArchiveTasksToExecute();
	

	//////////////////////////////////////////////////////////////
	// AUTOMATION SUPPLEMENTARY LISTS
	//////////////////////////////////////////////////////////////
	
	/**
	 * @param autoLp
	 */
	void addAutoListPrincipale(AutoListPrincipale autoLp);

	/**
	 * @param autoLp
	 */
	void updateAutoListPrincipale(AutoListPrincipale autoLp);

	/**
	 * @param autoLp
	 */
	void deleteAutoListPrincipale(AutoListPrincipale autoLp);
	
	/**
	 * @param vet
	 */
	void addVetAutoLp(VetAutoLp vet);

	/**
	 * @param vet
	 */
	void updateVetAutoLp(VetAutoLp vet);

	/**
	 * @param vet
	 */
	void deleteVetAutoLp(VetAutoLp vet);

	/**
	 * @param typeDec
	 * @param versionEtpOpi
	 * @return The list of IndVoeu
	 */
	List<IndVoeu> getRecupListIndVoeuLc(TypeDecision typeDec, VersionEtpOpi versionEtpOpi);


	//////////////////////////////////////////////////////////////
	// IndSituation
	//////////////////////////////////////////////////////////////
	
	/**
	 * Add a indSituation.
	 * @param indSituation
	 */
	void addIndSituation(IndSituation indSituation);

	/**
	 * Update a indSituation.
	 * @param indSituation
	 */
	void updateIndSituation(IndSituation indSituation);
	
	/**
	 * Delete a indSituation.
	 * @param indSituation
	 */
	void deleteIndSituation(IndSituation indSituation);
	
	/**
	 * Get the indSituation for the ind.
	 * @param ind
	 */
	IndSituation getIndSituation(Individu ind);

}
