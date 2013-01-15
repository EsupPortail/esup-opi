/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author cleprous
 *
 */
public class PieceJustiVet extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 223745739244657883L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Version Etape. 
	 */
	private VersionEtpOpi versionEtpOpi;
	
	/*
	 ******************* INIT ************************* */
	

	/**
	 * Constructors.
	 */
	public PieceJustiVet() {
		super();
	}

	/**
	 * 
	 * Constructors.
	 * @param vetDTO
	 */
	public PieceJustiVet(final VersionEtapeDTO vetDTO) {
		super();
		this.versionEtpOpi = new VersionEtpOpi(vetDTO);
	}
	
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PieceJustiVet#" + hashCode() + "[versionEtpOpi=[" + versionEtpOpi 
		+ "],  [" + super.toString() + "]]";
	}
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result;
		if (versionEtpOpi != null) { result += versionEtpOpi.hashCode(); }
		result = prime * result;
		return result;
	}
	

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof PieceJustiVet)) { return false; }
		PieceJustiVet other = (PieceJustiVet) obj;
		if (versionEtpOpi == null) {
			if (other.getVersionEtpOpi() != null) {	return false; }
		} else if (!versionEtpOpi.equals(other.getVersionEtpOpi())) { return false; }
		return true;
	}




		
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the versionEtpOpi
	 */
	public VersionEtpOpi getVersionEtpOpi() {
		return versionEtpOpi;
	}

	/**
	 * @param versionEtpOpi the versionEtpOpi to set
	 */
	public void setVersionEtpOpi(final VersionEtpOpi versionEtpOpi) {
		this.versionEtpOpi = versionEtpOpi;
	}
	


	

}
