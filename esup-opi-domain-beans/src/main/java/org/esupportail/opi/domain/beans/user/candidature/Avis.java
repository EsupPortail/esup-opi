package org.esupportail.opi.domain.beans.user.candidature;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;

/**
 * @author ylecuyer
 *
 */
public class Avis extends NormeSI {
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3849830407111350956L;

	/**
	 * The result de l'avis.
	 */
	private TypeDecision result;
	
	/**
	 * The voeu auquel l'avis est attache.
	 */
	private IndVoeu indVoeu;
	
	/**
	 * The boolean indiquant si l'avis a ete valide.
	 * default value : false
	 */
	private Boolean validation;
	
	/**
	 * The boolean indiquant si l'avis est issu d'un appel.
	 * default value : false
	 */
	private Boolean appel;
	
	/**
	 * The rang sur la liste complementaire de l'avis.
	 */
	private Integer rang;
	
	/**
	 * The commentaire de l'avis.
	 */
	private String commentaire;
	
	/**
	 * The motivation de l'avis.
	 */
	private MotivationAvis motivationAvis;
	


	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public Avis() {
		super();
		validation = false;
		appel = false;
		commentaire = null;
		motivationAvis = null;
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result += prime * result
				+ ((this.result == null) ? 0 : this.result.hashCode());
		result += prime * result
				+ ((validation == null) ? 0 : validation.hashCode());
		result += prime * result
		+ ((appel == null) ? 0 : appel.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		Avis other = (Avis) obj;
		if (result == null) {
			if (other.getResult() != null) { return false; }
		} else if (!result.equals(other.getResult())) { return false; }
		if (validation == null) {
			if (other.getValidation() != null) { return false; }
		} else if (!validation.equals(other.getValidation())) { return false; }
		if (appel == null) {
			if (other.getAppel() != null) { return false; }
		} else if (!appel.equals(other.getAppel())) { return false; }
		return true;
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Avis#" + hashCode() 
			+ "[result=[" + result  
			+ "],[validation=[" + validation 
			+ "],[appel=[" + appel 
			+ "],  [" + super.toString() + "]]";
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	



	/**
	 * @return the result
	 */
	public TypeDecision getResult() {
		return result;
	}

	/**
	 * @param result the result to set
	 */
	public void setResult(final TypeDecision result) {
		this.result = result;
	}

	/**
	 * @return the indVoeu
	 */
	public IndVoeu getIndVoeu() {
		return indVoeu;
	}

	/**
	 * @param indVoeu the indVoeu to set
	 */
	public void setIndVoeu(final IndVoeu indVoeu) {
		this.indVoeu = indVoeu;
	}

	/**
	 * @return the validation
	 */
	public Boolean getValidation() {
		return validation;
	}

	/**
	 * @param validation the validation to set
	 */
	public void setValidation(final Boolean validation) {
		this.validation = validation;
	}

	/**
	 * @return the appel
	 */
	public Boolean getAppel() {
		return appel;
	}

	/**
	 * @param appel the appel to set
	 */
	public void setAppel(final Boolean appel) {
		this.appel = appel;
	}

	/**
	 * @return the rang
	 */
	public Integer getRang() {
		return rang;
	}

	/**
	 * @param rang the rang to set
	 */
	public void setRang(final Integer rang) {
		this.rang = rang;
	}

	/**
	 * @return the commentaire
	 */
	public String getCommentaire() {
		return commentaire;
	}

	/**
	 * @param commentaire the commentaire to set
	 */
	public void setCommentaire(final String commentaire) {
		this.commentaire = commentaire;
	}

	/**
	 * @return the motivationAvis
	 */
	public MotivationAvis getMotivationAvis() {
		return motivationAvis;
	}

	/**
	 * @param motivationAvis the motivationAvis to set
	 */
	public void setMotivationAvis(final MotivationAvis motivationAvis) {
		this.motivationAvis = motivationAvis;
	}

	
}
