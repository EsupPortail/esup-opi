/**
* CRI - Universitï¿½ de Rennes1 - 57SI-OPIR1 - 2008
* https://subversion.univ-rennes1.fr/repos/57SI-apo-cri-webservice
* Version de la norme de dï¿½veloppement : 0.9.0
*/
package org.esupportail.opi.domain.beans.formation;


import java.io.Serializable;
import java.util.Set;

/**
 * @author cleprous
 * GrpTypDip : Groupe de type Diplome.
 */
public class GrpTypDip implements Serializable {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serializable id.
	 */
	private static final long serialVersionUID = -2772521560014439795L;
	
	/**
	 * Code groupe Type diplome.
	 */
	private String codGrpTpd;
	
	/**
	 * Libelle groupe Type diplome.
	 */
	private String libGrpTpd;
	
	/**
	 * Temoin en service groupe Type diplome.
	 */
	private String temEnSveGrpTpd;
	
	/**
	 * Laision entre le groupe et le type diplome. 
	 */
	private Set<GrpTypDipCorresp> GrpTypDipCorresps;
	
	/*
	 ******************* INIT ******************* */
	
	/**
	 * Constructor.
	 */
	public GrpTypDip() {
		super();
	}
	
	public GrpTypDip(String codGrpTpd, String libGrpTpd, String temEnSveGrpTpd,
			Set<GrpTypDipCorresp> GrpTypDipCorresps) {
		this.codGrpTpd = codGrpTpd;
		this.libGrpTpd = libGrpTpd;
		this.temEnSveGrpTpd = temEnSveGrpTpd;
		this.GrpTypDipCorresps = GrpTypDipCorresps;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GrpTypDip#" + hashCode() + "[codGrpTpd=[" + codGrpTpd 
		+ "], libGrpTpd=[" + libGrpTpd + "], temEnSveGrpTpd=[" + temEnSveGrpTpd + "]]";
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codGrpTpd == null) ? 0 : codGrpTpd.hashCode());
		result = prime * result
				+ ((libGrpTpd == null) ? 0 : libGrpTpd.hashCode());
		result = prime
				* result
				+ ((GrpTypDipCorresps == null) ? 0 : GrpTypDipCorresps
						.hashCode());
		result = prime * result
				+ ((temEnSveGrpTpd == null) ? 0 : temEnSveGrpTpd.hashCode());
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof GrpTypDip)) { return false; }
		GrpTypDip other = (GrpTypDip) obj;
		if (codGrpTpd == null) {
			if (other.codGrpTpd != null) { return false; }
		} else if (!codGrpTpd.equals(other.codGrpTpd)) { return false; }
		if (libGrpTpd == null) {
			if (other.libGrpTpd != null) { return false; }
		} else if (!libGrpTpd.equals(other.libGrpTpd)) { return false; }
		if (GrpTypDipCorresps == null) {
			if (other.GrpTypDipCorresps != null) { return false; }
		} else if (!GrpTypDipCorresps.equals(other.GrpTypDipCorresps)) { return false; }
		if (temEnSveGrpTpd == null) {
			if (other.temEnSveGrpTpd != null) {	return false; }
		} else if (!temEnSveGrpTpd.equals(other.temEnSveGrpTpd)) { return false; }
		return true;
	}

	/*
	 ******************* ACCESSORS ******************* */
	
	/**
	 * @return the codGrpTpd
	 */
	public String getCodGrpTpd() {
		return codGrpTpd;
	}
	/**
	 * @param codGrpTpd the codGrpTpd to set
	 */
	public void setCodGrpTpd(final String codGrpTpd) {
		this.codGrpTpd = codGrpTpd;
	}
	/**
	 * @return the libGrpTpd
	 */
	public String getLibGrpTpd() {
		return libGrpTpd;
	}
	/**
	 * @param libGrpTpd the libGrpTpd to set
	 */
	public void setLibGrpTpd(final String libGrpTpd) {
		this.libGrpTpd = libGrpTpd;
	}
	/**
	 * @return the temEnSveGrpTpd
	 */
	public String getTemEnSveGrpTpd() {
		return temEnSveGrpTpd;
	}
	/**
	 * @param temEnSveGrpTpd the temEnSveGrpTpd to set
	 */
	public void setTemEnSveGrpTpd(final String temEnSveGrpTpd) {
		this.temEnSveGrpTpd = temEnSveGrpTpd;
	}
	/**
	 * @return the GrpTypDipCorresps
	 */
	public Set<GrpTypDipCorresp> getGrpTypDipCorresps() {
		return GrpTypDipCorresps;
	}
	/**
	 * @param GrpTypDipCorresps the GrpTypDipCorresps to set
	 */
	public void setGrpTypDipCorresps(
			final Set<GrpTypDipCorresp> GrpTypDipCorresps) {
		this.GrpTypDipCorresps = GrpTypDipCorresps;
	}

	
}
