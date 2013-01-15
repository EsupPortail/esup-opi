package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * @author tducreux
 *
 */
public class IndRechPojo {
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * Number of dossier searched.
	 */
	private String numDossierOpiRecherche;
	
	/**
	 * name of the student searched.
	 */
	private String nomRecherche;

	/**
	 * prenom of the student searched.
	 */
	private String prenomRecherche;

	/**
	 * etape for the student search.
	 */
	private TypeDecision typeDecRecherchee;

	/**
	 * etape for the student search.
	 */
	private String codeEtapeRecherchee;

	/**
	 * traiment cmi for the student search.
	 */
	private Integer codeTrtCmiRecherchee;
	
	/**
	 * Liste des régimes d'inscription pour la recherche.
	 */
	private Set<RegimeInscription> listeRI = new HashSet<RegimeInscription>();

	/**
	 * La commision recherchee.
	 */
	private Integer idCmi;
	
	/**
	 * La date de creation des voeux recherchee.
	 */
	private Date dateCreationVoeuRecherchee;
	
	/**
	 * Exclude the wishes processed.
	 */
	private Boolean excludeWishProcessed;

    /**
     * Validité des voeux
     */
    private Boolean selectValid;
	
	/**
	 * true si le gestionnaire peut modifier.
	 * le filtre sur le régime d'inscription, false sinon
	 */
	private Boolean canModifyRISearch;
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructor.
	 */
	public IndRechPojo() {
		//this.excludeWishProcessed = true;
		//this.listeRI = null;
	}

	/*
	 ******************* METHODS ********************** */


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndRechPojo#" + hashCode() + "numDossierOpiRecherche=[" + numDossierOpiRecherche 
		+ "], nomRecherche=[" + nomRecherche 
		+ "], prenomRecherche=[" + prenomRecherche + "], codeEtapeRecherchee=[" + codeEtapeRecherchee 
		+ "], idCmi=[" + idCmi
		+ "], dateCreationVoeuRecherchee=[" + dateCreationVoeuRecherchee 
		+ "],    ["
		+ super.toString() + "]]";
	}

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the numDossierOpiRecherche
	 */
	public String getNumDossierOpiRecherche() {
		if (numDossierOpiRecherche != null) {
			return numDossierOpiRecherche.toUpperCase();
		}
		return numDossierOpiRecherche;
	}

	/**
	 * @param numDossierOpiRecherche the numDossierOpiRecherche to set
	 */
	public void setNumDossierOpiRecherche(final String numDossierOpiRecherche) {
		this.numDossierOpiRecherche = numDossierOpiRecherche;
	}



	/**
	 * @return the nomRecherche
	 */
	public String getNomRecherche() {
		if (nomRecherche != null) {
			return nomRecherche.toUpperCase();
		}
		return nomRecherche;
	}

	/**
	 * @param nomRecherche the nomRecherche to set
	 */
	public void setNomRecherche(final String nomRecherche) {
		this.nomRecherche = nomRecherche;
	}

	/**
	 * @return the prenomRecherche
	 */
	public String getPrenomRecherche() {
		if (prenomRecherche != null) {
			return prenomRecherche.toUpperCase();
		}
		return prenomRecherche;
	}

	/**
	 * @param prenomRecherche the prenomRecherche to set
	 */
	public void setPrenomRecherche(final String prenomRecherche) {
		this.prenomRecherche = prenomRecherche;
	}

	/**
	 * @return the codeEtapeRecherchee
	 */
	public String getCodeEtapeRecherchee() {
		return codeEtapeRecherchee;
	}

	/**
	 * @param codeEtapeRecherchee the codeEtapeRecherchee to set
	 */
	public void setCodeEtapeRecherchee(final String codeEtapeRecherchee) {
		this.codeEtapeRecherchee = codeEtapeRecherchee;
	}

	/**
	 * @return the codeTrtCmiRecherchee
	 */
	public Integer getCodeTrtCmiRecherchee() {
		return codeTrtCmiRecherchee;
	}

	/**
	 * @param codeTrtCmiRecherchee the codeTrtCmiRecherchee to set
	 */
	public void setCodeTrtCmiRecherchee(final Integer codeTrtCmiRecherchee) {
		this.codeTrtCmiRecherchee = codeTrtCmiRecherchee;
	}

	/**
	 * @return the typeDecRecherchee
	 */
	public TypeDecision getTypeDecRecherchee() {
		return typeDecRecherchee;
	}

	/**
	 * @param typeDecRecherchee the typeDecRecherchee to set
	 */
	public void setTypeDecRecherchee(final TypeDecision typeDecRecherchee) {
		this.typeDecRecherchee = typeDecRecherchee;
	}

	/**
	 * @return the codeCommissionRecherchee
	 */
	public Integer getIdCmi() {
		return idCmi;
	}

	/**
	 * @param codeCommissionRecherchee the codeCommissionRecherchee to set
	 */
	public void setIdCmi(final Integer codeCommissionRecherchee) {
		this.idCmi = codeCommissionRecherchee;
	}

	/**
	 * @return the listeRI
	 */
	public Set<RegimeInscription> getListeRI() {
		return listeRI;
	}

	/**
	 * @param listeRI the listeRI to set
	 */
	public void setListeRI(final Set<RegimeInscription> listeRI) {
		this.listeRI = listeRI;
	}

	/**
	 * @return the dateCreationVoeuRecherchee
	 */
	public Date getDateCreationVoeuRecherchee() {
		return dateCreationVoeuRecherchee;
	}

	/**
	 * @param dateCreationVoeuRecherchee the dateCreationVoeuRecherchee to set
	 */
	public void setDateCreationVoeuRecherchee(final Date dateCreationVoeuRecherchee) {
		this.dateCreationVoeuRecherchee = dateCreationVoeuRecherchee;
	}


	/**
	 * @return the excludeWishProcessed
	 */
	public Boolean getExcludeWishProcessed() {
		return excludeWishProcessed;
	}

	/**
	 * @param excludeWishProcessed the excludeWishProcessed to set
	 */
	public void setExcludeWishProcessed(final Boolean excludeWishProcessed) {
		this.excludeWishProcessed = excludeWishProcessed;
	}

	/**
	 * @return the canModifyRISearch
	 */
	public Boolean getCanModifyRISearch() {
		return canModifyRISearch;
	}

	/**
	 * @param canModifyRISearch the canModifyRISearch to set
	 */
	public void setCanModifyRISearch(final Boolean canModifyRISearch) {
		this.canModifyRISearch = canModifyRISearch;
	}

    /**
     * @return the selectValid
     */
    public Boolean getSelectValid() {
        return selectValid;
    }

    /**
     * @param selectValid the selectValid to set
     */
    public void setSelectValid(final Boolean selectValid) {
        this.selectValid = selectValid;
    }

}
