/**
 * 
 */
package org.esupportail.opi.domain.beans.user.candidature;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;


/**
 * @author cleprous
 *
 */
public class IndFormulaire extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5430782752427621342L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Version Etape. 
	 */
	private VersionEtpOpi versionEtpOpi;
	
	/**
	 * The Individu.
	 */
	private Individu individu;

	/*
	 ******************* INIT ************************* */
	
	

	/**
	 * Constructor.
	 */
	public IndFormulaire() {
		super();
	}

	
	
	
	/**
	 * Constructors.
	 * @param codEtp
	 * @param codVrsVet
	 * @param individu
	 */
	public IndFormulaire(final VersionEtapeDTO vetDTO, final Individu individu) {
		super();
		versionEtpOpi = new VersionEtpOpi(vetDTO);
		this.individu = individu;
	}

	/**
	 * Constructors.
	 * @param versionEtpOpi
	 */
	public IndFormulaire(final VersionEtpOpi versionEtpOpi, final Individu individu) {
		this.versionEtpOpi = versionEtpOpi;
		this.individu = individu;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Voeu#" + hashCode() 
			+ "[versionEtpOpi=[" + versionEtpOpi  
			+ "],[" + super.toString() + "]]";
	}

	
	

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((versionEtpOpi == null) ? 0 : versionEtpOpi.hashCode());
		result = prime * result
				+ ((individu == null) ? 0 : individu.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof IndFormulaire)) { return false; }
		IndFormulaire other = (IndFormulaire) obj;
		if (versionEtpOpi == null) {
			if (other.getVersionEtpOpi() != null) {	return false; }
		} else if (!versionEtpOpi.equals(other.getVersionEtpOpi())) { return false; }
		if (individu == null) {
			if (other.getIndividu() != null) { return false; }
		} else if (!individu.equals(other.getIndividu())) { return false; }
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


}
