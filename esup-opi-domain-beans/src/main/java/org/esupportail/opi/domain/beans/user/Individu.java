/**
 * 
 */
package org.esupportail.opi.domain.beans.user;

import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursus;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;




/**
 * @author cleprous
 *
 */
public class Individu extends User {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5436967664706943494L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The individu  OPI number.
	 */
	private String numDossierOpi;
	
	/**
	 * The code Etudiant.
	 */
	private String codeEtu;
	
	/**
	 * The code Individu in Apogee.
	 */
	private Integer codInd;
	
	/**
	 * The code national.
	 */
	private String codeNNE;
	
	/**
	 * The codeNNE key.
	 */
	private String codeClefNNE;
	
	/**
	 * The campagnes.
	 */
	private Set<Campagne> campagnes;
	
	/**
	 * 
	 */
	private String codDepPaysNaissance;
	
	/**
	 * 
	 */
	private String codPayNaissance;
	
	/**
	 * The date of birth.
	 */
	private Date dateNaissance;
	
	/**
	 *  The city of birth.
	 */
	private String villeNaissance;
	
	/**
	 * The cell phone number.
	 */
	private String numeroTelPortable;
	
	/**
	 * 
	 */
	private String codPayNationalite;
	
    /**
	 * The mail adress from LDAP.
	 */
	private String emailAnnuaire;
	
    /**
     * The sex.
     */
    private String sexe;
    
	/**
	 * The professionnal years. 
	 */
	private Set<IndCursus> cursus;
	
	/**
	 * The university years. 
	 */
	private Set<IndCursusScol> cursusScol;
	
	/**
	 * The key is the adress type, and the value is the adress. 
	 */
	private Map<String, Adresse> adresses;
	
	/**
	 * The bac of individu.
	 */
	private Set<IndBac> indBac;
	
	/**
	 * The vows of individu.
	 * Default value : empty
	 * where="temoinEnService = y"
	 */
	private Set<IndVoeu> voeux;
	
	/**
	 * The vows of individu.
	 * Default value : empty
	 * where="temoinEnService = n"
	 */
	private Set<IndVoeu> archVoeux;
	
	/**
	 * The state of account individu.
	 */
	private String state;
	
	/**
	 * Code Regime d'inscription.
	 * Default value 0 --> formation Initiale.
	 * 1 --> Formation Continue
	 */
//	private int codeRI;
	
	/**
	 * the missing piece list.
	 */
	private Set<MissingPiece> missingPieces;
	
	
	/*
	 ******************* INIT ************************* */
	
	

	/**
	 * Constructor.
	 */
	public Individu() {
		super();
		voeux = new HashSet<IndVoeu>();
		archVoeux = new HashSet<IndVoeu>();
//		codeRI = 0;
		campagnes = new HashSet<Campagne>();
	}
	

	/**
	 * @param codeClefNNE
	 * @param codeEtu
	 * @param codeNNE
	 */
	public Individu(final String codeNNE, final String codeClefNNE, final String codeEtu) {
		super();
		this.codeNNE = codeNNE;
		this.codeClefNNE = codeClefNNE;
		this.codeEtu = codeEtu;
		voeux = new HashSet<IndVoeu>();
		archVoeux = new HashSet<IndVoeu>();
//		codeRI = 0;
		campagnes = new HashSet<Campagne>();
	}


	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((numDossierOpi == null) ? 0 : numDossierOpi.hashCode());
		return result;
	}




	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof Individu)) { return false; }
		Individu other = (Individu) obj;
		if (numDossierOpi == null) {
			if (other.numDossierOpi != null) { return false; }
		} else if (!numDossierOpi.equals(other.numDossierOpi)) { return false; }
		return true;
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Individu#" + hashCode() + "[numDossierOpi=[" 
					+ numDossierOpi + "],  [" + super.toString() + "]]";
	}
	
	
	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Individu clone() {
		Individu i = new Individu();
		i = (Individu) super.clone(i);
		i.setCodDepPaysNaissance(codDepPaysNaissance);
		i.setCodeClefNNE(codeClefNNE);
		i.setCodeEtu(codeEtu);
		i.setCodInd(codInd);
		i.setCodeNNE(codeNNE);
		i.setCodPayNaissance(codPayNaissance);
		i.setCodPayNationalite(codPayNationalite);
		i.setAdresses(new HashMap<String, Adresse>(adresses));
		i.setCursus(new HashSet<IndCursus>(cursus));
		i.setCursusScol(new HashSet<IndCursusScol>(cursusScol));
		i.setDateNaissance(dateNaissance);
		i.setEmailAnnuaire(emailAnnuaire);
		i.setIndBac(new HashSet<IndBac>(indBac));
		i.setNumDossierOpi(numDossierOpi);
		i.setNumeroTelPortable(numeroTelPortable);
		i.setSexe(sexe);
		i.setState(state);
		i.setVilleNaissance(villeNaissance);
		i.setVoeux(voeux);
		i.setArchVoeux(archVoeux);
//		i.setCodeRI(codeRI);
		i.setCampagnes(campagnes);
		return i; 
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
	 * @return the codeNNE
	 */
	public String getCodeNNE() {
		return codeNNE;
	}

	/**
	 * @param codeNNE the codeNNE to set
	 */
	public void setCodeNNE(final String codeNNE) {
		this.codeNNE = codeNNE;
	}

	/**
	 * @return the codeClefNNE
	 */
	public String getCodeClefNNE() {
		return codeClefNNE;
	}

	/**
	 * @param codeClefNNE the codeClefNNE to set
	 */
	public void setCodeClefNNE(final String codeClefNNE) {
		this.codeClefNNE = codeClefNNE;
	}

	/**
	 * @return the campagnes
	 */
	public Set<Campagne> getCampagnes() {
		return campagnes;
	}


	/**
	 * @param campagnes the campagnes to set
	 */
	public void setCampagnes(final Set<Campagne> campagnes) {
		this.campagnes = campagnes;
	}


	/**
	 * @return the codDepPaysNaissance
	 */
	public String getCodDepPaysNaissance() {
		return codDepPaysNaissance;
	}

	/**
	 * @param codDepPaysNaissance the codDepPaysNaissance to set
	 */
	public void setCodDepPaysNaissance(final String codDepPaysNaissance) {
		this.codDepPaysNaissance = codDepPaysNaissance;
	}

	/**
	 * @return the codPayNaissance
	 */
	public String getCodPayNaissance() {
		return codPayNaissance;
	}

	/**
	 * @param codPayNaissance the codPayNaissance to set
	 */
	public void setCodPayNaissance(final String codPayNaissance) {
		this.codPayNaissance = codPayNaissance;
	}

	/**
	 * @return the dateNaissance
	 */
	public Date getDateNaissance() {
		return dateNaissance;
	}

	/**
	 * @param dateNaissance the dateNaissance to set
	 */
	public void setDateNaissance(final Date dateNaissance) {
		this.dateNaissance = dateNaissance;
	}

	/**
	 * @return the villeNaissance
	 */
	public String getVilleNaissance() {
		return villeNaissance;
	}

	/**
	 * @param villeNaissance the villeNaissance to set
	 */
	public void setVilleNaissance(final String villeNaissance) {
		this.villeNaissance = villeNaissance;
	}

	/**
	 * @return the numeroTelPortable
	 */
	public String getNumeroTelPortable() {
		return numeroTelPortable;
	}

	/**
	 * @param numeroTelPortable the numeroTelPortable to set
	 */
	public void setNumeroTelPortable(final String numeroTelPortable) {
		this.numeroTelPortable = numeroTelPortable;
	}

	/**
	 * @return the codPayNationalite
	 */
	public String getCodPayNationalite() {
		return codPayNationalite;
	}

	/**
	 * @param codPayNationalite the codPayNationalite to set
	 */
	public void setCodPayNationalite(final String codPayNationalite) {
		this.codPayNationalite = codPayNationalite;
	}

	/**
	 * @return the emailAnnuaire
	 */
	public String getEmailAnnuaire() {
		return emailAnnuaire;
	}


	/**
	 * @param emailAnnuaire the emailAnnuaire to set
	 */
	public void setEmailAnnuaire(final String emailAnnuaire) {
		this.emailAnnuaire = emailAnnuaire;
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
	 * @return the cursus
	 */
	public Set<IndCursus> getCursus() {
		return cursus;
	}

	/**
	 * @param cursus the cursus to set
	 */
	public void setCursus(final Set<IndCursus> cursus) {
		this.cursus = cursus;
	}

	/**
	 * @return the adresses
	 */
	public Map<String, Adresse> getAdresses() {
		return adresses;
	}

	/**
	 * @param adresses the adresses to set
	 */
	public void setAdresses(final Map<String, Adresse> adresses) {
		this.adresses = adresses;
	}

	/**
	 * @return the indBac
	 */
	public Set<IndBac> getIndBac() {
		return indBac;
	}

	/**
	 * @param indBac the indBac to set
	 */
	public void setIndBac(final Set<IndBac> indBac) {
		this.indBac = indBac;
	}

	/**
	 * @return the cursusScol
	 */
	public Set<IndCursusScol> getCursusScol() {
		return cursusScol;
	}

	/**
	 * @param cursusScol the cursusScol to set
	 */
	public void setCursusScol(final Set<IndCursusScol> cursusScol) {
		this.cursusScol = cursusScol;
	}


	/**
	 * @return the voeux
	 */
	public Set<IndVoeu> getVoeux() {
		return voeux;
	}


	/**
	 * @param voeux the voeux to set
	 */
	public void setVoeux(final Set<IndVoeu> voeux) {
		this.voeux = voeux;
	}


	/**
	 * @return the archVoeux
	 */
	public Set<IndVoeu> getArchVoeux() {
		return archVoeux;
	}


	/**
	 * @param archVoeux the archVoeux to set
	 */
	public void setArchVoeux(final Set<IndVoeu> archVoeux) {
		this.archVoeux = archVoeux;
	}


	/**
	 * @return the state
	 */
	public String getState() {
		return state;
	}


	/**
	 * @param state the state to set
	 */
	public void setState(final String state) {
		this.state = state;
	}




	/**
	 * @return the missingPieces
	 */
	public Set<MissingPiece> getMissingPieces() {
		return missingPieces;
	}


	/**
	 * @param missingPieces the missingPieces to set
	 */
	public void setMissingPieces(final Set<MissingPiece> missingPieces) {
		this.missingPieces = missingPieces;
	}


	/**
	 * @return the codInd
	 */
	public Integer getCodInd() {
		return codInd;
	}


	/**
	 * @param codInd the codInd to set
	 */
	public void setCodInd(final Integer codInd) {
		this.codInd = codInd;
	}


//	public int getCodeRI() {
//		return codeRI;
//	}


//	public void setCodeRI(final int codeRI) {
//		this.codeRI = codeRI;
//	}

}
