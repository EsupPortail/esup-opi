package org.esupportail.opi.domain.beans.user.situation;

import java.util.Date;

import org.esupportail.opi.domain.beans.user.AdresseEmployeur;

/**
 * @author ylecuyer
 * Cas d'une situation de type salarié
 */
public class SituationSalarie extends IndSituation {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6492942538560903281L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The code of the type of contract.
	 */
	private String codeTypeContrat;
	
	/**
	 * The code of the type of statut.
	 */
	private String codeTypeStatut;
	
	/**
	 * The end date of the CDD.
	 * (null if contrat type != CDD) 
	 */
	private Date dateFinCDD;
	
	/**
	 * The precision of the type of contract.
	 * (null if contrat type != Autre)
	 */
	private String autreTypeContrat;
	
	/**
	 * Grade du candidat.
	 */
	private String grade;
	
	/*
	 ********* DONNEES SUR L'EMPLOYEUR *********/
	
	/**
	 * Raison sociale de l'employeur.
	 */
	private String raisonSociale;
	
	/**
	 * Code du type d'organisme de l'employeur.
	 */
	private String codeTypeOrga;
	
	/**
	 * Secteur d'activité de l'employeur.
	 */
	private String secteurActivity;
	
	/**
	 * Adresse de l'employeur.
	 */
	private AdresseEmployeur adresseEmployeur;
	
	

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public SituationSalarie() {
		super();
		adresseEmployeur = new AdresseEmployeur();
	}



	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the codeTypeContrat
	 */
	public String getCodeTypeContrat() {
		return codeTypeContrat;
	}

	/**
	 * @param codeTypeContrat the codeTypeContrat to set
	 */
	public void setCodeTypeContrat(final String codeTypeContrat) {
		this.codeTypeContrat = codeTypeContrat;
	}

	/**
	 * @return the codeTypeStatut
	 */
	public String getCodeTypeStatut() {
		return codeTypeStatut;
	}

	/**
	 * @param codeTypeStatut the codeTypeStatut to set
	 */
	public void setCodeTypeStatut(final String codeTypeStatut) {
		this.codeTypeStatut = codeTypeStatut;
	}

	/**
	 * @return the dateFinCDD
	 */
	public Date getDateFinCDD() {
		return dateFinCDD;
	}

	/**
	 * @param dateFinCDD the dateFinCDD to set
	 */
	public void setDateFinCDD(final Date dateFinCDD) {
		this.dateFinCDD = dateFinCDD;
	}

	/**
	 * @return the autreTypeContrat
	 */
	public String getAutreTypeContrat() {
		return autreTypeContrat;
	}

	/**
	 * @param autreTypeContrat the autreTypeContrat to set
	 */
	public void setAutreTypeContrat(final String autreTypeContrat) {
		this.autreTypeContrat = autreTypeContrat;
	}

	/**
	 * @return the grade
	 */
	public String getGrade() {
		return grade;
	}

	/**
	 * @param grade the grade to set
	 */
	public void setGrade(final String grade) {
		this.grade = grade;
	}

	/**
	 * @return the raisonSociale
	 */
	public String getRaisonSociale() {
		return raisonSociale;
	}

	/**
	 * @param raisonSociale the raisonSociale to set
	 */
	public void setRaisonSociale(final String raisonSociale) {
		this.raisonSociale = raisonSociale;
	}

	/**
	 * @return the codeTypeOrga
	 */
	public String getCodeTypeOrga() {
		return codeTypeOrga;
	}

	/**
	 * @param codeTypeOrga the codeTypeOrga to set
	 */
	public void setCodeTypeOrga(final String codeTypeOrga) {
		this.codeTypeOrga = codeTypeOrga;
	}

	/**
	 * @return the secteurActivity
	 */
	public String getSecteurActivity() {
		return secteurActivity;
	}

	/**
	 * @param secteurActivity the secteurActivity to set
	 */
	public void setSecteurActivity(final String secteurActivity) {
		this.secteurActivity = secteurActivity;
	}

	/**
	 * @return the adresseEmployeur
	 */
	public AdresseEmployeur getAdresseEmployeur() {
		return adresseEmployeur;
	}

	/**
	 * @param adresseEmployeur the adresseEmployeur to set
	 */
	public void setAdresseEmployeur(final AdresseEmployeur adresseEmployeur) {
		this.adresseEmployeur = adresseEmployeur;
	}
	

}
