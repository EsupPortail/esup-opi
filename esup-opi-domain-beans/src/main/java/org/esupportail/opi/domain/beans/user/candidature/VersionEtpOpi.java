/**
 * 
 */
package org.esupportail.opi.domain.beans.user.candidature;

import java.io.Serializable;

import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author cleprous
 *
 */
public class VersionEtpOpi implements Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7359717393677216583L;


	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The Etape code.
	 */
	private String codEtp;
	

	/**
	 * The Version Etape code.
	 */
	private Integer codVrsVet;
	
	/**
	 * Code Centre de gestion.
	 */
	private String codCge;
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public VersionEtpOpi() {
		super();
	}

	
	
	/**
	 * Constructors.
	 * @param vetDTO
	 */
	public VersionEtpOpi(final VersionEtapeDTO vetDTO) {
		super();
		this.codEtp = vetDTO.getCodEtp();
		this.codVrsVet = vetDTO.getCodVrsVet();
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "VersionEtpOpi#" + hashCode() 
			+ "[codEtp=[" + codEtp  
			+ "],[codVrsVet=[" + codVrsVet 
			+ "],[codCge=[" + codCge 
			+ "]]";
	}

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codEtp == null) ? 0 : codEtp.hashCode());
		result = prime * result
				+ ((codVrsVet == null) ? 0 : codVrsVet.hashCode());
		return result;
	}




	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		VersionEtpOpi other = (VersionEtpOpi) obj;
		if (codEtp == null) {
			if (other.getCodEtp() != null) { return false; }
		} else if (!codEtp.equals(other.getCodEtp())) { return false; }
		if (codVrsVet == null) {
			if (other.getCodVrsVet() != null) { return false; }
		} else if (!codVrsVet.equals(other.getCodVrsVet())) { return false; }
		return true;
	}

	

	/*
	 ******************* METHODS ********************** */
	
	
	
	/*
	 ******************* ACCESSORS ******************** */

	



	/**
	 * @return the codEtp
	 */
	public String getCodEtp() {
		return codEtp;
	}

	/**
	 * @param codEtp the codEtp to set
	 */
	public void setCodEtp(final String codEtp) {
		this.codEtp = codEtp;
	}

	/**
	 * @return the codVrsVet
	 */
	public Integer getCodVrsVet() {
		return codVrsVet;
	}

	/**
	 * @param codVrsVet the codVrsVet to set
	 */
	public void setCodVrsVet(final Integer codVrsVet) {
		this.codVrsVet = codVrsVet;
	}



	/**
	 * @return the codCge
	 */
	public String getCodCge() {
		return codCge;
	}



	/**
	 * @param codCge the codCge to set
	 */
	public void setCodCge(final String codCge) {
		this.codCge = codCge;
	}


	
}
