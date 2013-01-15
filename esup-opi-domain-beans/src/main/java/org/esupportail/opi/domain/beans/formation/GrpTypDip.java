/**
* CRI - Universitï¿½ de Rennes1 - 57SI-OPIR1 - 2008
* https://subversion.univ-rennes1.fr/repos/57SI-apo-cri-webservice
* Version de la norme de dï¿½veloppement : 0.9.0
*/
package org.esupportail.opi.domain.beans.formation;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codGrpTpd == null) ? 0 : codGrpTpd.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GrpTypDip other = (GrpTypDip) obj;
		if (codGrpTpd == null) {
			if (other.codGrpTpd != null) {
				return false;
			}
		} else if (!codGrpTpd.equals(other.codGrpTpd)) {
			return false;
		}
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
	
	public List<GrpTypDipCorresp> getListGrpTypDipCorresps() {
		return new ArrayList<GrpTypDipCorresp>(GrpTypDipCorresps);
	}

	
}
