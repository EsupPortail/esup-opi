/**
 * 
 */
package org.esupportail.opi.domain.beans.user.indcursus;


/**
 * @author cleprous
 * Cursus scolaire exterieure e l'universite Rennes1.
 */
public class CursusExt extends IndCursusScol {

	

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4707176432142811822L;
	
	
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Code diplome autre cursus.
	 */
	private String codDac;
	
	/**
	 * Libelle diplome autre cursus.
	 */
	private String libDac;
	
	/**
	 * Libelle de l'etablissement e l'etranger.
	 */
	private String libEtbEtr;
	
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public CursusExt() {
		super();
	}

	
	
	/**
	 * Constructors.
	 * @param annee 
	 */
	public CursusExt(final String annee) {
		super(annee, null, null);
	}







	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CursusExt#" + hashCode() + "[codDac=[" + codDac 
			+ "],[libDac=[" + libDac 
			+ "],  [" + super.toString() + "]]";
	}
	
	
	
	
	


	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codDac == null) ? 0 : codDac.hashCode());
		result = prime * result + ((libDac == null) ? 0 : libDac.hashCode());
		result = prime * result + ((libEtbEtr == null) ? 0 : libEtbEtr.hashCode());
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
		CursusExt other = (CursusExt) obj;
		if (codDac == null) {
			if (other.getCodDac() != null) {	return false; }
		} else if (!codDac.equals(other.getCodDac())) { return false; }
		if (libDac == null) {
			if (other.getLibDac() != null) { return false; }
		} else if (!libDac.equals(other.getLibDac())) { return false; }
		if (libEtbEtr == null) {
			if (other.getLibEtbEtr() != null) { return false; }
		} else if (!libEtbEtr.equals(other.getLibEtbEtr())) { return false; }
		return true;
	}


	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the codDac
	 */
	public String getCodDac() {
		return codDac;
	}


	/**
	 * @param codDac the codDac to set
	 */
	public void setCodDac(final String codDac) {
		this.codDac = codDac;
	}


	/**
	 * @return the libDac
	 */
	public String getLibDac() {
		return libDac;
	}


	/**
	 * @param libDac the libDac to set
	 */
	public void setLibDac(final String libDac) {
		this.libDac = libDac;
	}



	/**
	 * @return the libEtbEtr
	 */
	public String getLibEtbEtr() {
		return libEtbEtr;
	}



	/**
	 * @param libEtbEtr the libEtbEtr to set
	 */
	public void setLibEtbEtr(final String libEtbEtr) {
		this.libEtbEtr = libEtbEtr;
	}
	

}
