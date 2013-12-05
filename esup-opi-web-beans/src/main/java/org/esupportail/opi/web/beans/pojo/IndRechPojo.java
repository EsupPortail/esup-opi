package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;

import java.util.*;

import static java.util.Arrays.asList;

public class IndRechPojo {
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
	 * Decision types for the student search.
	 */
	private List<TypeDecision> typesDec = new ArrayList<>();

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
	private Set<RegimeInscription> listeRI = new HashSet<RegimeInscription>(asList(new FormationInitiale()));

    private Collection<TypeTraitement> typeTraitements = Collections.emptyList();

	/**
	 * La commision recherchee.
	 */
	private Integer idCmi;
	
	/**
	 * La date de creation des voeux recherchee.
	 */
	private Date dateCreationVoeuRecherchee;

    /**
     * Filtre-t-on les individus sans voeux ?
     */
    private Boolean useVoeuFilter = true;

    /**
     * Filtre-t-on les individus selon un {@link TypeTraitement}
     */
    private Boolean useTypeTrtFilter = false;

    /**
     * Filtre-t-on selon les commissions du gestionnaire courant ?
     */
    private Boolean useGestCommsFilter = false;

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

	public String getNumDossierOpiRecherche() {
		if (numDossierOpiRecherche != null) {
			return numDossierOpiRecherche.toUpperCase();
		}
		return numDossierOpiRecherche;
	}

	public void setNumDossierOpiRecherche(final String numDossierOpiRecherche) {
		this.numDossierOpiRecherche = numDossierOpiRecherche;
	}

	public String getNomRecherche() {
		if (nomRecherche != null) {
			return nomRecherche.toUpperCase();
		}
		return nomRecherche;
	}

	public void setNomRecherche(final String nomRecherche) {
		this.nomRecherche = nomRecherche;
	}

	public String getPrenomRecherche() {
		if (prenomRecherche != null) {
			return prenomRecherche.toUpperCase();
		}
		return prenomRecherche;
	}

	public void setPrenomRecherche(final String prenomRecherche) {
		this.prenomRecherche = prenomRecherche;
	}

	public String getCodeEtapeRecherchee() {
		return codeEtapeRecherchee;
	}

	public void setCodeEtapeRecherchee(final String codeEtapeRecherchee) {
		this.codeEtapeRecherchee = codeEtapeRecherchee;
	}

	public Integer getCodeTrtCmiRecherchee() {
		return codeTrtCmiRecherchee;
	}

	public void setCodeTrtCmiRecherchee(final Integer codeTrtCmiRecherchee) {
		this.codeTrtCmiRecherchee = codeTrtCmiRecherchee;
	}

    public TypeDecision getTypeDec() {
        // yeah, ugly, but jsf likes it
        return typesDec.isEmpty() ? null : typesDec.get(0);
    }

    public void setTypeDec(TypeDecision typeDec) {
        typesDec =  (typeDec != null) ?
                new ArrayList<>(Collections.singleton(typeDec)) :
                new ArrayList<TypeDecision>();
    }

	public List<TypeDecision> getTypesDec() {
		return typesDec;
	}

	public void setTypesDec(final List<TypeDecision> typesDec) {
		this.typesDec = typesDec;
	}

	public Integer getIdCmi() {
		return idCmi;
	}

	public void setIdCmi(final Integer codeCommissionRecherchee) {
		this.idCmi = codeCommissionRecherchee;
	}

	public Set<RegimeInscription> getListeRI() {
		return listeRI;
	}

	public void setListeRI(final Set<RegimeInscription> listeRI) {
		this.listeRI = listeRI;
	}

	public Date getDateCreationVoeuRecherchee() {
		return dateCreationVoeuRecherchee;
	}

	public void setDateCreationVoeuRecherchee(final Date dateCreationVoeuRecherchee) {
		this.dateCreationVoeuRecherchee = dateCreationVoeuRecherchee;
	}

    public Boolean isUseVoeuFilter() {
        return useVoeuFilter;
    }

    public void setUseVoeuFilter(Boolean useVoeuFilter) {
        this.useVoeuFilter = useVoeuFilter;
    }

    public Boolean isUseTypeTrtFilter() {
        return useTypeTrtFilter;
    }

    public void setUseTypeTrtFilter(Boolean useTypeTrtFilter) {
        this.useTypeTrtFilter = useTypeTrtFilter;
    }

    public Boolean isUseGestCommsFilter() {
        return useGestCommsFilter;
    }

    public void setUseGestCommsFilter(Boolean useGestCommsFilter) {
        this.useGestCommsFilter = useGestCommsFilter;
    }

	public Boolean getExcludeWishProcessed() {
		return excludeWishProcessed;
	}

	public void setExcludeWishProcessed(final Boolean excludeWishProcessed) {
		this.excludeWishProcessed = excludeWishProcessed;
	}

	public Boolean getCanModifyRISearch() {
		return canModifyRISearch;
	}

	public void setCanModifyRISearch(final Boolean canModifyRISearch) {
		this.canModifyRISearch = canModifyRISearch;
	}

    public Boolean getSelectValid() {
        return selectValid;
    }

    public void setSelectValid(final Boolean selectValid) {
        this.selectValid = selectValid;
    }

    public Collection<TypeTraitement> getTypeTraitements() {
        return typeTraitements;
    }

    public void setTypeTraitements(Collection<TypeTraitement> typeTraitements) {
        this.typeTraitements = typeTraitements;
    }
}
