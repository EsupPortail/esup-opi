/**
 * 
 */
package org.esupportail.opi.domain.beans.user.indcursus;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.Individu;

/**
 * @author cleprous
 * Cursus scolaire et universitaire.
 */
public abstract class IndCursusScol extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -2579322225111848199L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 *  The type of Cursus.
	 */
	private String type;
	
	/**
	 * The year.
	 */
	private String annee;
	
	/**
	 * The codEtablissement. 
	 */
	private String codEtablissement;
	
	/**
	 * The type de l'etablissement. 
	 */
	private String codTypeEtab;
	
	/**
	 * Temoin pour savoir si les infos viennent d'Apogee.
	 * Default value = false.
	 */
	private Boolean temoinFromApogee;
	
	/**
	 * The result value (O or N). 
	 */
	private String resultat;
	
	/**
	 * The libMention. 
	 */
	private String libMention;
	
	/**
	 * The Individu.
	 */
	private Individu individu;
	
	
	/*
	 ******************* INIT ************************* */

	
	

	/**
	 * Constructors.
	 */
	public IndCursusScol() {
		super();
		temoinFromApogee = false;
	}

	
	
	
	/**
	 * Constructors.
	 * @param annee
	 * @param codEtablissement
	 * @param codTypeEtab
	 */
	public IndCursusScol(final String annee, final String codEtablissement,
			final String codTypeEtab) {
		super();
		this.annee = annee;
		this.codEtablissement = codEtablissement;
		this.codTypeEtab = codTypeEtab;
		temoinFromApogee = false;
	}






	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndCursusScol#" + hashCode() + "[type=[" + type + "],[annee=[" + annee 
			+ "],[codEtablissement=[" + codEtablissement + "],[codTypeEtab=[" + codTypeEtab 
			+ "],[resultat=[" + resultat + "],[libMention=[" + libMention 
			+ "],  [" + super.toString() + "]]";
	}
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((annee == null) ? 0 : annee.hashCode());
		result = prime
				* result
				+ ((codEtablissement == null) ? 0 : codEtablissement.hashCode());
		result = prime
				* result
				+ ((temoinFromApogee == null) ? 0 : temoinFromApogee.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		IndCursusScol other = (IndCursusScol) obj;
		if (annee == null) {
			if (other.getAnnee() != null) { return false; }
		} else if (!annee.equals(other.getAnnee())) { return false; }
		if (codEtablissement == null) {
			if (other.getCodEtablissement() != null) { return false; }
		} else if (!codEtablissement.equals(other.getCodEtablissement())) { return false; }
		if (temoinFromApogee == null) {
			if (other.getTemoinFromApogee() != null) { return false; }
		} else if (!temoinFromApogee.equals(other.getTemoinFromApogee())) { return false; }
		if (type == null) {
			if (other.getType() != null) { return false; }
		} else if (!type.equals(other.getType())) { return false; }
		return true;
	}


	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	


	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

	/**
	 * @return the annee
	 */
	public String getAnnee() {
		return annee;
	}

	/**
	 * @param annee the annee to set
	 */
	public void setAnnee(final String annee) {
		this.annee = annee;
	}
	
	/**
	 * @return the codEtablissement
	 */
	public String getCodEtablissement() {
		return codEtablissement;
	}


	/**
	 * @param codEtablissement the codEtablissement to set
	 */
	public void setCodEtablissement(final String codEtablissement) {
		this.codEtablissement = codEtablissement;
	}

	/**
	 * @return the codTypeEtab
	 */
	public String getCodTypeEtab() {
		return codTypeEtab;
	}

	/**
	 * @param codTypeEtab the codTypeEtab to set
	 */
	public void setCodTypeEtab(final String codTypeEtab) {
		this.codTypeEtab = codTypeEtab;
	}




	/**
	 * @return the temoinFromApogee
	 */
	public Boolean getTemoinFromApogee() {
		return temoinFromApogee;
	}




	/**
	 * @param temoinFromApogee the temoinFromApogee to set
	 */
	public void setTemoinFromApogee(final Boolean temoinFromApogee) {
		this.temoinFromApogee = temoinFromApogee;
	}
	
	/**
	 * @return the resultat
	 */
	public String getResultat() {
		return resultat;
	}


	/**
	 * @param resultat the resultat to set
	 */
	public void setResultat(final String resultat) {
		this.resultat = resultat;
	}

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
	 * @return the libMention
	 */
	public String getLibMention() {
		return libMention;
	}


	/**
	 * @param libMention the libMention to set
	 */
	public void setLibMention(final String libMention) {
		this.libMention = libMention;
	}

}
