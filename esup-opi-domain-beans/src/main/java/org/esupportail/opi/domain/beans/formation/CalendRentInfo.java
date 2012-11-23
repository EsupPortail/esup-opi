package org.esupportail.opi.domain.beans.formation;
// default package
// Generated 20 avr. 2009 15:06:34 by Hibernate Tools 3.2.2.GA



/**
 * @author gmartel
 *
 */
public class CalendRentInfo  implements java.io.Serializable {


     private String codCge;
     private String info;

    
	/**
	 * 
	 * Constructor.
	 */
	public CalendRentInfo() {
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
		result = prime * result + ((codCge == null) ? 0 : codCge.hashCode());
		result = prime * result + ((info == null) ? 0 : info.hashCode());
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
		CalendRentInfo other = (CalendRentInfo) obj;
		if (codCge == null) {
			if (other.codCge != null)
				return false;
		} else if (!codCge.equals(other.codCge))
			return false;
		if (info == null) {
			if (other.info != null)
				return false;
		} else if (!info.equals(other.info))
			return false;
		return true;
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendRentInfo#" + hashCode() + "[codCge=[" + codCge + "], info=["
		+ info +"]]";
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
	public void setCodCge(String codCge) {
		this.codCge = codCge;
	}

	/**
	 * @return the info
	 */
	public String getInfo() {
		return info;
	}

	/**
	 * @param info the info to set
	 */
	public void setInfo(String info) {
		this.info = info;
	}

	




}


