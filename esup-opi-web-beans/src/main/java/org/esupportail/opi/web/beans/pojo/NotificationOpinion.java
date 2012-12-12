package org.esupportail.opi.web.beans.pojo;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.utils.Utilitaires;
import org.esupportail.wssi.services.remote.SignataireDTO;


/**
 * @author cleprous
 *
 */
public class NotificationOpinion {
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The code du dossier de l'individu.
	 * from IndividuPojo.individu.numDossierOpi
	 */
	private String numDossierOpi;

	/**
	 * The nom de l'individu.
	 * from IndividuPojo.individu.nomPatronymique
	 */
	private String nom;

	/**
	 * The prenom de l'individu.
	 * from IndividuPojo.individu.prenom
	 */
	private String prenom;
	
	/**
	 * The code etudiant de l'individu.
	 * from IndividuPojo.individu.codeEtu
	 */
	private String codeEtu;
	
	/**
	 * The sex. 
	 * from IndividuPojo.individu.sexe
	 */
	private String sexe;
	
	/**
	 * Adresse Etudiant.
	 */
	private AdressePojo adresseEtu;
	
	/**
	 * Voeux valides Favorables.
	 */
	private Set <IndVoeuPojo> voeuxFavorable;

	/**
	 * Voeux valides Defavorables.
	 */
	private Set <IndVoeuPojo> voeuxDefavorable;

	/**
	 * Voeux valides Favorables en appel.
	 */
	private Set <IndVoeuPojo> voeuxFavorableAppel;

	/**
	 * Voeux valides Defavorables en appel.
	 */
	private Set <IndVoeuPojo> voeuxDefavorableAppel;

	/**
	 * Coordonnees du contact.
	 */
	private AdressePojo coordonneesContact;
	
	/**
	 * Date du jour.
	 */
	private String dateDuJour;
	
	/**
	 * Periode Scoalire. 
	 */
	private String periodeScolaire;
	
	/**
	 * Nom commission.
	 */
	private String nomCommission;
	
	/**
	 * Date de cloture (Avis Favorable).
	 */
	private String dateCloture;
	
	/**
	 * 
	 */
	private SignataireDTO signataire;
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public NotificationOpinion() {
		super();
		this.voeuxFavorable = new HashSet<IndVoeuPojo>();
		this.voeuxDefavorable = new HashSet<IndVoeuPojo>();
		this.dateDuJour = Utilitaires.convertDateToString(new Date(),
				Constantes.DATE_FORMAT);
	}


	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the numDossierOpi
	 */
	public String getNumDossierOpi() {
		return numDossierOpi;
	}

	/**
	 * @param numDossierOpi the numDossierOpi to set
	 */
	public void setNumDossierOpi(final String numDossierOpi) {
		this.numDossierOpi = numDossierOpi;
	}

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom the nom to set
	 */
	public void setNom(final String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(final String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the codeEtu
	 */
	public String getCodeEtu() {
		return codeEtu;
	}


	/**
	 * @param codeEtu the codeEtu to set
	 */
	public void setCodeEtu(final String codeEtu) {
		this.codeEtu = codeEtu;
	}


	/**
	 * @return the voeuxFavorable
	 */
	public Set<IndVoeuPojo> getVoeuxFavorable() {
		return voeuxFavorable;
	}


	/**
	 * @param voeuxFavorable the voeuxFavorable to set
	 */
	public void setVoeuxFavorable(final Set<IndVoeuPojo> voeuxFavorable) {
		this.voeuxFavorable = voeuxFavorable;
	}


	/**
	 * @return the voeuxDefavorable
	 */
	public Set<IndVoeuPojo> getVoeuxDefavorable() {
		return voeuxDefavorable;
	}


	/**
	 * @param voeuxDefavorable the voeuxDefavorable to set
	 */
	public void setVoeuxDefavorable(final Set<IndVoeuPojo> voeuxDefavorable) {
		this.voeuxDefavorable = voeuxDefavorable;
	}


	/**
	 * @return the voeuxFavorableAppel
	 */
	public Set<IndVoeuPojo> getVoeuxFavorableAppel() {
		return voeuxFavorableAppel;
	}


	/**
	 * @param voeuxFavorableAppel the voeuxFavorableAppel to set
	 */
	public void setVoeuxFavorableAppel(final Set<IndVoeuPojo> voeuxFavorableAppel) {
		this.voeuxFavorableAppel = voeuxFavorableAppel;
	}


	/**
	 * @return the voeuxDefavorableAppel
	 */
	public Set<IndVoeuPojo> getVoeuxDefavorableAppel() {
		return voeuxDefavorableAppel;
	}


	/**
	 * @param voeuxDefavorableAppel the voeuxDefavorableAppel to set
	 */
	public void setVoeuxDefavorableAppel(final Set<IndVoeuPojo> voeuxDefavorableAppel) {
		this.voeuxDefavorableAppel = voeuxDefavorableAppel;
	}


	/**
	 * @return the sexe
	 */
	public String getSexe() {
		return sexe;
	}


	/**
	 * @param sexe the sexe to set
	 */
	public void setSexe(final String sexe) {
		this.sexe = sexe;
	}


	
	/**
	 * @return the adresseEtu
	 */
	public AdressePojo getAdresseEtu() {
		return adresseEtu;
	}


	/**
	 * @param adresseEtu the adresseEtu to set
	 */
	public void setAdresseEtu(final AdressePojo adresseEtu) {
		this.adresseEtu = adresseEtu;
	}


	/**
	 * @return the coordonneesContact
	 */
	public AdressePojo getCoordonneesContact() {
		return coordonneesContact;
	}


	/**
	 * @param coordonneesContact the coordonneesContact to set
	 */
	public void setCoordonneesContact(
			final AdressePojo coordonneesContact) {
		this.coordonneesContact = coordonneesContact;
	}


	/**
	 * @return the dateDuJour
	 */
	public String getDateDuJour() {
		return dateDuJour;
	}


	/**
	 * @param dateDuJour the dateDuJour to set
	 */
	public void setDateDuJour(final String dateDuJour) {
		this.dateDuJour = dateDuJour;
	}


	/**
	 * @return the periodeScolaire
	 */
	public String getPeriodeScolaire() {
		return periodeScolaire;
	}


	/**
	 * @param periodeScolaire the periodeScolaire to set
	 */
	public void setPeriodeScolaire(final String periodeScolaire) {
		this.periodeScolaire = periodeScolaire;
	}


	/**
	 * @return the nomCommission
	 */
	public String getNomCommission() {
		return nomCommission;
	}


	/**
	 * @param nomCommission the nomCommission to set
	 */
	public void setNomCommission(final String nomCommission) {
		this.nomCommission = nomCommission;
	}


	/**
	 * @return the dateCloture
	 */
	public String getDateCloture() {
		return dateCloture;
	}


	/**
	 * @param dateCloture the dateCloture to set
	 */
	public void setDateCloture(final String dateCloture) {
		this.dateCloture = dateCloture;
	}


	/**
	 * @return the signataire
	 */
	public SignataireDTO getSignataire() {
		return signataire;
	}


	/**
	 * @param signataire the signataire to set
	 */
	public void setSignataire(final SignataireDTO signataire) {
		this.signataire = signataire;
	}








}
