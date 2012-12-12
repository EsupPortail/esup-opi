package org.esupportail.opi.domain.beans.formation;
// default package
// Generated 20 avr. 2009 15:06:34 by Hibernate Tools 3.2.2.GA


import java.util.Date;

/**
 * @author gmartel
 *
 */
public class CalendEpr  implements java.io.Serializable {


     private String codCln;
     private Date datDebAff;
     private Date datFinAff;
     private Date datMaj;


	/**
	 * 
	 * Constructor.
	 */
	public CalendEpr() {
		super();
		// TODO Auto-generated constructor stub
	}

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codCln == null) ? 0 : codCln.hashCode());
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalendEpr other = (CalendEpr) obj;
		if (codCln == null) {
			if (other.codCln != null)
				return false;
		} else if (!codCln.equals(other.codCln))
			return false;
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendEpr#" + hashCode() + "[codCln=[" + codCln + "], datDebAff=[" + datDebAff
		+ "], datFinAff=[" + datFinAff +"]]";
	}
	

	
	/**
	 * @return the codCln
	 */
	public String getCodCln() {
		return codCln;
	}

	/**
	 * @param codCln the codCln to set
	 */
	public void setCodCln(String codCln) {
		this.codCln = codCln;
	}

	/**
	 * @return the datDebAff
	 */
	public Date getDatDebAff() {
		return datDebAff;
	}

	/**
	 * @param datDebAff the datDebAff to set
	 */
	public void setDatDebAff(Date datDebAff) {
		this.datDebAff = datDebAff;
	}

	/**
	 * @return the datFinAff
	 */
	public Date getDatFinAff() {
		return datFinAff;
	}

	/**
	 * @param datFinAff the datFinAff to set
	 */
	public void setDatFinAff(Date datFinAff) {
		this.datFinAff = datFinAff;
	}

	/**
	 * @return the datMaj
	 */
	public Date getDatMaj() {
		return datMaj;
	}

	/**
	 * @param datMaj the datMaj to set
	 */
	public void setDatMaj(Date datMaj) {
		this.datMaj = datMaj;
	}

	

}


