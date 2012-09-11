/**
 * 
 */
package org.esupportail.opi.domain.beans.user.indcursus;


/**
 * @author cleprous
 * Cursus au sein de l'etablissement Rennes1.
 */
public class CursusR1 extends IndCursusScol {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -6180529189080747627L;

	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The codDiplome. 
	 */
	private String codDiplome;
	
	/**
	 * The codVersionDiplome. 
	 */
	private String codVersionDiplome;
	
	/**
	 * The codEtape. 
	 */
	private String codEtape;
	
	/**
	 * The codVersionEtape. 
	 */
	private String codVersionEtape;
	
	/**
	 * The libEtape.
	 */
	private String libEtape;
	
	
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public CursusR1() {
		super();
	}

	
	/**
	 * Constructors.
	 * @param annee 
	 * @param codEtb 
	 * @param codDiplome 
	 * @param codEtape 
	 * @param codVersionDiplome 
	 * @param codVersionEtape 
	 * @param libEtape 
	 */
	public CursusR1(final String annee, final String codEtb,
			final String codDiplome, final String codEtape,
			final String codVersionDiplome, final String codVersionEtape,
			final String libEtape) {
		super(annee, codEtb, null);
		this.codDiplome = codDiplome;
		this.codEtape = codEtape;
		this.codVersionDiplome = codVersionDiplome;
		this.codVersionEtape = codVersionEtape;
		this.libEtape = libEtape;
	}
	
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CursusR1#" + hashCode() + "[codDiplome=[" + codDiplome 
			+ "],[codVersionDiplome=[" + codVersionDiplome 
			+ "],[codEtape=[" + codEtape + "],[codVersionEtape=[" + codVersionEtape 
			+ "],[libEtape=[" + libEtape + "],  [" + super.toString() + "]]";
	}
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((codDiplome == null) ? 0 : codDiplome.hashCode());
		result = prime * result
				+ ((codEtape == null) ? 0 : codEtape.hashCode());
		result = prime
				* result
				+ ((codVersionDiplome == null) ? 0 : codVersionDiplome
						.hashCode());
		result = prime * result
				+ ((codVersionEtape == null) ? 0 : codVersionEtape.hashCode());
		result = prime * result
				+ ((libEtape == null) ? 0 : libEtape.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		CursusR1 other = (CursusR1) obj;
		if (codDiplome == null) {
			if (other.getCodDiplome() != null) { return false; }
		} else if (!codDiplome.equals(other.getCodDiplome())) { return false; }
		if (codEtape == null) {
			if (other.getCodEtape() != null) { return false; }
		} else if (!codEtape.equals(other.getCodEtape())) { return false; }
		if (codVersionDiplome == null) {
			if (other.getCodVersionDiplome() != null) { return false; }
		} else if (!codVersionDiplome.equals(other.getCodVersionDiplome())) { return false; }
		if (codVersionEtape == null) {
			if (other.getCodVersionEtape() != null) { return false; }
		} else if (!codVersionEtape.equals(other.getCodVersionEtape())) { return false; }
		if (libEtape == null) {
			if (other.getLibEtape() != null) { return false; }
		} else if (!libEtape.equals(other.getLibEtape())) { return false; }
		return true;
	}
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */


	/**
	 * @return the codDiplome
	 */
	public String getCodDiplome() {
		return codDiplome;
	}


	/**
	 * @param codDiplome the codDiplome to set
	 */
	public void setCodDiplome(final String codDiplome) {
		this.codDiplome = codDiplome;
	}



	/**
	 * @return the codVersionDiplome
	 */
	public String getCodVersionDiplome() {
		return codVersionDiplome;
	}


	/**
	 * @param codVersionDiplome the codVersionDiplome to set
	 */
	public void setCodVersionDiplome(final String codVersionDiplome) {
		this.codVersionDiplome = codVersionDiplome;
	}


	/**
	 * @return the codEtape
	 */
	public String getCodEtape() {
		return codEtape;
	}


	/**
	 * @param codEtape the codEtape to set
	 */
	public void setCodEtape(final String codEtape) {
		this.codEtape = codEtape;
	}


	/**
	 * @return the codVersionEtape
	 */
	public String getCodVersionEtape() {
		return codVersionEtape;
	}


	/**
	 * @param codVersionEtape the codVersionEtape to set
	 */
	public void setCodVersionEtape(final String codVersionEtape) {
		this.codVersionEtape = codVersionEtape;
	}


	/**
	 * @return the libEtape
	 */
	public String getLibEtape() {
		return libEtape;
	}



	/**
	 * @param libEtape the libEtape to set
	 */
	public void setLibEtape(final String libEtape) {
		this.libEtape = libEtape;
	}


	
	
	
}
