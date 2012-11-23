package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;


/**
 * GrpTypDipCorresp : Liaison entre groupe Type diplome et type diplome.
 */
public class GrpTypDipCorresp implements Serializable {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * the serializable id. 
	 */
	private static final long serialVersionUID = -4466930751347123777L;
	
	/**
	 * Id.
	 */
	private Long id;
	
	/**
	 * Code groupe Type diplome.
	 */
	private GrpTypDip grpTpd;
	
	/**
	 * Code Type diplome.
	 */
	private String codTpdEtb;

	/*
	 ******************* INIT ******************* */
	
	/**
	 * 
	 * Constructor.
	 */
	public GrpTypDipCorresp() {
		this.grpTpd = new GrpTypDip();
	}
	
	public GrpTypDipCorresp(String codGrpTpd, String codTpdEtb) {
		this();
		this.grpTpd.setCodGrpTpd(codGrpTpd);
		this.codTpdEtb = codTpdEtb;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GrpTypDipCorresp#" + hashCode() + "[codGrpTpd=[" + grpTpd 
		+ "], codTpdEtb=[" + codTpdEtb + "]]";
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((codTpdEtb == null) ? 0 : codTpdEtb.hashCode());
		result = prime * result + ((grpTpd == null) ? 0 : grpTpd.hashCode());
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
		GrpTypDipCorresp other = (GrpTypDipCorresp) obj;
		if (codTpdEtb == null) {
			if (other.codTpdEtb != null) {
				return false;
			}
		} else if (!codTpdEtb.equals(other.codTpdEtb)) {
			return false;
		}
		if (grpTpd == null) {
			if (other.grpTpd != null) {
				return false;
			}
		} else if (!grpTpd.equals(other.grpTpd)) {
			return false;
		}
		return true;
	}

	

	/*
	 ******************* ACCESSORS ******************* */

	/**
	 * @return the grpTpd
	 */
	public GrpTypDip getGrpTpd() {
		return grpTpd;
	}

	/**
	 * @param grpTpd the grpTpd to set
	 */
	public void setGrpTpd(final GrpTypDip grpTpd) {
		this.grpTpd = grpTpd;
	}

	/**
	 * @return the codTpdEtb
	 */
	public String getCodTpdEtb() {
		return codTpdEtb;
	}

	/**
	 * @param codTpdEtb the codTpdEtb to set
	 */
	public void setCodTpdEtb(final String codTpdEtb) {
		this.codTpdEtb = codTpdEtb;
	}

	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

}
