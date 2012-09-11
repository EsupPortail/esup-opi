package org.esupportail.opi.domain.beans.references.commission;

import java.util.HashSet;
import java.util.Set;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;

/**
 * @author ylecuyer
 *
 */
public class LinkTrtCmiCamp extends NormeSI {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1921896031702339488L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The campagne.
	 */
	private Campagne campagne;
	
	/**
	 * The traitement cmi.
	 */
	private TraitementCmi traitementCmi;
	
	/**
	 * The list of voeux for the campagne and the traitementCmi.
	 */
	private Set<IndVoeu> voeux;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public LinkTrtCmiCamp() {
		super();
		voeux = new HashSet<IndVoeu>();
	}
	
	public LinkTrtCmiCamp(final Campagne campagne, final TraitementCmi traitementCmi) {
		super();
		this.campagne = campagne;
		this.traitementCmi = traitementCmi;
		voeux = new HashSet<IndVoeu>();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LinkTrtCmiCamp#" + hashCode() 
		+ "[Campagne=[" + campagne + "], [TraitementCmi=[" + traitementCmi + "]"
		+ ",  [" + super.toString() + "]]";
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the campagne
	 */
	public Campagne getCampagne() {
		return campagne;
	}

	/**
	 * @param campagne the campagne to set
	 */
	public void setCampagne(final Campagne campagne) {
		this.campagne = campagne;
	}

	/**
	 * @return the traitementCmi
	 */
	public TraitementCmi getTraitementCmi() {
		return traitementCmi;
	}

	/**
	 * @param traitementCmi the traitementCmi to set
	 */
	public void setTraitementCmi(final TraitementCmi traitementCmi) {
		this.traitementCmi = traitementCmi;
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
	
}
