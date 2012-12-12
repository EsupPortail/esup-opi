/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;


import org.esupportail.opi.domain.beans.parameters.PieceJustificative;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursus;
import org.esupportail.opi.domain.beans.user.situation.IndSituation;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Set;




/**
 * @author cleprous
 * This class to generate the ducoment PDF for individu.
 */
public class IndDocument implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5393847446586713640L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The individu.
	 */
	private IndividuPojo individuPojo;
	
	/**
	 * The individu's adress.
	 */
	private AdressePojo adressePojo;
	
	/**
	 * The individu's bac.
	 */
	private List<IndBacPojo> indBacPojo;
	
	/**
	 * The individu's situation.
	 * null if not FC individu
	 */
	private IndSituation indSituation;
	
	/**
	 * The emplyeur's adresse.
	 * null if the situation is type Salarie
	 */
	private AdressePojo adresseEmployeurPojo;
	
	/**
	 * The individu's vows with the cmi.
	 */
	private Map<CommissionPojo, Set<VersionEtapeDTO>> cmiAndVowsInd;
	
	/**
	 * List des annees universitaire du candidat.
	 */
	private List<IndCursusScolPojo> indCursusScolPojos;
	
	/**
	 * List du cursus professionnel.
	 */
	private List<IndCursus> indCursusPro;
	
	/**
	 * List des formations professionnalisante.
	 */
	private List<IndCursus> qualifPro;
	
	/**
	 * All piece for this document.
	 */
	private List<PieceJustificative> pieces;
	
	/**
	 * Annee universitaire suivante.
	 * Exemple : du 09/2008 au 08/2009 l'annee universitaire en cours est 2008-2009
	 * L'annee suivante est donc 2009-2010.
	 * 
	 */
	private String anneeUniversitaire;
	
	/**
	 * Annee universitaire d'Apogée de la campagne en cours.
	 * Exemple : pour 2009-2010 l'annee universitaire dans Apogée est 2009.
	 */
	private String anneeUniversitaireApogee;
	
	
	/**
	 * list de VET + calendrier admissiblite et resultat definitif
	 */
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public IndDocument() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndDocument#" + hashCode() + "anneeUniversitaire=[" + anneeUniversitaire 
		+ "], anneeUniversitaireApogee=[" + anneeUniversitaireApogee 
		+ "], pieces=[" + pieces + "], indCursusPro=[" + indCursusPro 
		+ "], indCursusScolPojos=[" + indCursusScolPojos + "], cmiAndVowsInd=[" + cmiAndVowsInd 
		+ "], adressePojo=[" + adressePojo + "],    [" + super.toString() + "]]";
	}
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the individuPojo
	 */
	public IndividuPojo getIndividuPojo() {
		return individuPojo;
	}
	
	/**
	 * @param individuPojo the individuPojo to set
	 */
	public void setIndividuPojo(final IndividuPojo individuPojo) {
		this.individuPojo = individuPojo;
	}
	
	/**
	 * @return the adressePojo
	 */
	public AdressePojo getAdressePojo() {
		return adressePojo;
	}
	
	/**
	 * @param adressePojo the adressePojo to set
	 */
	public void setAdressePojo(final AdressePojo adressePojo) {
		this.adressePojo = adressePojo;
	}
	
	/**
	 * @return the indBacPojo
	 */
	public List<IndBacPojo> getIndBacPojo() {
		return indBacPojo;
	}
	
	/**
	 * @param indBacPojo the indBacPojo to set
	 */
	public void setIndBacPojo(final List<IndBacPojo> indBacPojo) {
		this.indBacPojo = indBacPojo;
	}
	
	/**
	 * @return the indSituation
	 */
	public IndSituation getIndSituation() {
		return indSituation;
	}

	/**
	 * @param indSituation the indSituation to set
	 */
	public void setIndSituation(final IndSituation indSituation) {
		this.indSituation = indSituation;
	}

	/**
	 * @return the adresseEmployeurPojo
	 */
	public AdressePojo getAdresseEmployeurPojo() {
		return adresseEmployeurPojo;
	}

	/**
	 * @param adresseEmployeurPojo the adresseEmployeurPojo to set
	 */
	public void setAdresseEmployeurPojo(final AdressePojo adresseEmployeurPojo) {
		this.adresseEmployeurPojo = adresseEmployeurPojo;
	}

	/**
	 * @return the cmiAndVowsInd
	 */
	public Map<CommissionPojo, Set<VersionEtapeDTO>> getCmiAndVowsInd() {
		return cmiAndVowsInd;
	}
	
	/**
	 * @param cmiAndVowsInd the cmiAndVowsInd to set
	 */
	public void setCmiAndVowsInd(final Map<CommissionPojo, Set<VersionEtapeDTO>> cmiAndVowsInd) {
		this.cmiAndVowsInd = cmiAndVowsInd;
	}
	
	/**
	 * @return indCursusScolPojos
	 */
	public List<IndCursusScolPojo> getIndCursusScolPojos() {
		return indCursusScolPojos;
	}
	
	/**
	 * @param indCursusScolPojos
	 */
	public void setIndCursusScolPojos(final List<IndCursusScolPojo> indCursusScolPojos) {
		this.indCursusScolPojos = indCursusScolPojos;
	}
	
	/**
	 * @return indCursusPro
	 */
	public List<IndCursus> getIndCursusPro() {
		return indCursusPro;
	}
	
	/**
	 * @param indCursusPro
	 */
	public void setIndCursusPro(final List<IndCursus> indCursusPro) {
		this.indCursusPro = indCursusPro;
	}
	
	/**
	 * @return pieces
	 */
	public List<PieceJustificative> getPieces() {
		return pieces;
	}
	
	/**
	 * @param pieces
	 */
	public void setPieces(final List<PieceJustificative> pieces) {
		this.pieces = pieces;
	}
	
	/**
	 * @return the anneeUniversitaire
	 */
	public String getAnneeUniversitaire() {
		return anneeUniversitaire;
	}
	
	/**
	 * @param anneeUniversitaire the anneeUniversitaire to set
	 */
	public void setAnneeUniversitaire(final String anneeUniversitaire) {
		this.anneeUniversitaire = anneeUniversitaire;
	}
	
	/**
	 * @return anneeUniversitaireApogee
	 */
	public String getAnneeUniversitaireApogee() {
		return anneeUniversitaireApogee;
	}
	
	/**
	 * @param anneeUniversitaireApogee
	 */
	public void setAnneeUniversitaireApogee(final String anneeUniversitaireApogee) {
		this.anneeUniversitaireApogee = anneeUniversitaireApogee;
	}
	
	/**
	 * @return the qualifPro
	 */
	public List<IndCursus> getQualifPro() {
		return qualifPro;
	}
	
	/**
	 * @param qualifPro the qualifPro to set
	 */
	public void setQualifPro(final List<IndCursus> qualifPro) {
		this.qualifPro = qualifPro;
	}
}
