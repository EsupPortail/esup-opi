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
	 * Code groupe Type diplome.
	 */
	private String codGrpTpd;
	
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
		super();
	}
	
	public GrpTypDipCorresp(String codGrpTpd, String codTpdEtb) {
		this.codGrpTpd = codGrpTpd;
		this.codTpdEtb = codTpdEtb;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "GrpTypDipCorresp#" + hashCode() + "[codGrpTpd=[" + codGrpTpd 
		+ "], codTpdEtb=[" + codTpdEtb + "]]";
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
				+ ((codTpdEtb == null) ? 0 : codTpdEtb.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof GrpTypDipCorresp)) { return false; }
		GrpTypDipCorresp other = (GrpTypDipCorresp) obj;
		if (codGrpTpd == null) {
			if (other.codGrpTpd != null) { return false; }
		} else if (!codGrpTpd.equals(other.codGrpTpd)) { return false; }
		if (codTpdEtb == null) {
			if (other.codTpdEtb != null) { return false; }
		} else if (!codTpdEtb.equals(other.codTpdEtb)) { return false; }
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

}
