package org.esupportail.opi.web.beans.pojo;

import java.util.Set;
import java.util.TreeSet;

import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;

/**
 * @author ylecuyer
 * Pojo servant e stocker les informations d'un individu pour
 * l'edition de la liste preparatoire des commission
 * Information recuperee e partir de IndividuPojo
 * Version "allegee" de IndividuPojo (principalement sur la partie Individu
 */
public class IndListePrepaPojo {
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The code de la commission.
	 * from Commission.code
	 */
	private String codeCmi;

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
	 * The bac de l'individu.
	 * from IndividuPojo.individu.indBac
	 */
	private String bac;

	/**
	 * The titre fondant la demande.
	 * from IndividuPojo.derniereAnneeEtudeCursus.libCur
	 */
	private String titreAccesDemande;
	
	/**
	 * The dernier cursus de l'individu.
	 * from IndividuPojo.derniereAnneeEtudeCursus.cursus
	 */
	private IndCursusScol dernierIndCursusScol;

	/**
	 * The liste des voeux de l'individu.
	 * from IndividuPojo.indVoeuxPojo
	 */
	private Set<IndVoeuPojo> indVoeuxPojo;
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public IndListePrepaPojo() {
		super();
		indVoeuxPojo = new TreeSet<IndVoeuPojo>(new ComparatorString(IndVoeuPojo.class));
	}


	public IndListePrepaPojo(final String bac, final String codeCmi,
			final IndCursusScol dernierIndCursusScol, final Set<IndVoeuPojo> indVoeuxPojo,
			final String nom, final String numDossierOpi, final String prenom,
			final String titreAccesDemande) {
		super();
		this.bac = bac;
		this.codeCmi = codeCmi;
		this.dernierIndCursusScol = dernierIndCursusScol;
		this.indVoeuxPojo = indVoeuxPojo;
		this.nom = nom;
		this.numDossierOpi = numDossierOpi;
		this.prenom = prenom;
		this.titreAccesDemande = titreAccesDemande;
	}


	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the codeCmi
	 */
	public String getCodeCmi() {
		return codeCmi;
	}

	/**
	 * @param codeCmi the codeCmi to set
	 */
	public void setCodeCmi(final String codeCmi) {
		this.codeCmi = codeCmi;
	}

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
	public void setCodeEtu(String codeEtu) {
		this.codeEtu = codeEtu;
	}


	/**
	 * @return the bac
	 */
	public String getBac() {
		return bac;
	}

	/**
	 * @param bac the bac to set
	 */
	public void setBac(final String bac) {
		this.bac = bac;
	}

	/**
	 * @return the titreAccesDemande
	 */
	public String getTitreAccesDemande() {
		return titreAccesDemande;
	}

	/**
	 * @param titreAccesDemande the titreAccesDemande to set
	 */
	public void setTitreAccesDemande(final String titreAccesDemande) {
		this.titreAccesDemande = titreAccesDemande;
	}

	/**
	 * @return the dernierIndCursusScol
	 */
	public IndCursusScol getDernierIndCursusScol() {
		return dernierIndCursusScol;
	}


	/**
	 * @param dernierIndCursusScol the dernierIndCursusScol to set
	 */
	public void setDernierIndCursusScol(final IndCursusScol dernierIndCursusScol) {
		this.dernierIndCursusScol = dernierIndCursusScol;
	}


	/**
	 * @return the indVoeuxPojo
	 */
	public Set<IndVoeuPojo> getIndVoeuxPojo() {
		return indVoeuxPojo;
	}

	/**
	 * @param indVoeuxPojo the indVoeuxPojo to set
	 */
	public void setIndVoeuxPojo(final Set<IndVoeuPojo> indVoeuxPojo) {
		this.indVoeuxPojo = indVoeuxPojo;
	}

}
