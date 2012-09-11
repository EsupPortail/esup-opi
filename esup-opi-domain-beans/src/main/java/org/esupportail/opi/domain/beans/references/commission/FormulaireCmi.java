/**
 * 
 */
package org.esupportail.opi.domain.beans.references.commission;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;



/**
 * @author cleprous
 * Contient Les etapes gerees ppar une commission et leur type traitement associe. 
 */
public class FormulaireCmi extends NormeSI {



	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -9011249966465780314L;

	/**
	 * The app name.
	 */
	private VersionEtpOpi versionEtpOpi;
	
	/**
	 * The code RI.
	 */
	private Integer codeRI;

	/*
	 ******************* INIT ************************* */



	/**
	 * Constructors.
	 */
	public FormulaireCmi() {
		super();
	}

	/**
	 * Constructors.
	 * @param versionEtpOpi
	 */
	public FormulaireCmi(final VersionEtpOpi versionEtpOpi, final Integer codeRI) {
		super();
		this.versionEtpOpi = versionEtpOpi;
		this.codeRI = codeRI;
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the serialVersionUID
	 */
	public static long getSerialVersionUID() {
		return serialVersionUID;
	}

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
	 * @return the codeRI
	 */
	public Integer getCodeRI() {
		return codeRI;
	}

	/**
	 * @param codeRI the codeRI to set
	 */
	public void setCodeRI(final Integer codeRI) {
		this.codeRI = codeRI;
	}

}
