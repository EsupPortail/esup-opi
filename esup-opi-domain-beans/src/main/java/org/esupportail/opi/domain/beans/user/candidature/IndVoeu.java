/**
 * 
 */
package org.esupportail.opi.domain.beans.user.candidature;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.user.Individu;


/**
 * @author cleprous
 *
 */
public class IndVoeu extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5436967664706943494L;

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The Individu.
	 */
	private Individu individu;
	
	/**
	 * The vow state.
	 */
	private String state;
	
	
	/**
	 * The code TypeTraitement.
	 */
	private String codTypeTrait;
	
	/**
	 * The avis du voeu.
	 */
	private Set<Avis> avis;
	
	/**
	 * true if the voeu is a proposition from the commission.
	 */
	private boolean isProp;
	
	/**
	 * Have the Voeu be traited by the manager ?
	 * Default value false.
	 */
	private boolean haveBeTraited;
	
	/**
	 * The link between the traitement cmi and the campagne.
	 */
	private LinkTrtCmiCamp linkTrtCmiCamp;
	
	/*
	 ******************* INIT ************************* */
	
	

	/**
	 * Constructor.
	 */
	public IndVoeu() {
		super();
		haveBeTraited = false;
		isProp = false;
	}


	/**
	 * Constructors.
	 * @param linkTrtCmiCamp
	 * @param individu
	 */
	public IndVoeu(final LinkTrtCmiCamp linkTrtCmiCamp, final Individu individu) {
		super();
		this.linkTrtCmiCamp = linkTrtCmiCamp;
		this.individu = individu;
		haveBeTraited = false;
		isProp = false;
	}




	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Voeu#" + hashCode() 
			+ "],[state=[" + state 
			+ "],[codTypeTrait=[" + codTypeTrait 
			+ "],  [" + super.toString() + "]]";
	}

	
	

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codTypeTrait == null) ? 0 : codTypeTrait.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof IndVoeu)) { return false; }
		IndVoeu other = (IndVoeu) obj;
		if (codTypeTrait == null) {
			if (other.getCodTypeTrait() != null) { return false; }
		} else if (!codTypeTrait.equals(other.getCodTypeTrait())) { return false; }
		return true;
	}


	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */


	/**
	 * @return the individu
	 */
	public Individu getIndividu() {
		return individu;
	}


	/**
	 * @param individu the individu to set
	 */
	public void setIndividu(final Individu individu) {
		this.individu = individu;
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
	 * @return the avis
	 */
	public Set<Avis> getAvis() {
		return avis;
	}
	
	/**
	 * @return the avis
	 */
	@SuppressWarnings({"JpaAttributeTypeInspection", "JpaAttributeMemberSignatureInspection"})
    public List<Avis> getAvisAsList() {
		List<Avis> result = new ArrayList<>();
		if (avis != null) result.addAll(avis);
		return result;
	}

	/**
	 * @param avis the avis to set
	 */
	public void setAvis(final Set<Avis> avis) {
		this.avis = avis;
	}

	/**
	 * @return the codTypeTrait
	 */
	public String getCodTypeTrait() {
		return codTypeTrait;
	}

	/**
	 * @param codTypeTrait the codTypeTrait to set
	 */
	public void setCodTypeTrait(final String codTypeTrait) {
		this.codTypeTrait = codTypeTrait;
	}

	/**
	 * @return the isProp
	 */
	public boolean getIsProp() {
		return isProp;
	}

	/**
	 * @param isProp the isProp to set
	 */
	public void setIsProp(final boolean isProp) {
		this.isProp = isProp;
	}

	/**
	 * @return the haveBeTraited
	 */
	public boolean isHaveBeTraited() {
		return haveBeTraited;
	}

	/**
	 * @param haveBeTraited the haveBeTraited to set
	 */
	public void setHaveBeTraited(final boolean haveBeTraited) {
		this.haveBeTraited = haveBeTraited;
	}

	/**
	 * @return the linkTrtCmiCamp
	 */
	public LinkTrtCmiCamp getLinkTrtCmiCamp() {
		return linkTrtCmiCamp;
	}

	/**
	 * @param linkTrtCmiCamp the linkTrtCmiCamp to set
	 */
	public void setLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
		this.linkTrtCmiCamp = linkTrtCmiCamp;
	}
	
}
