package org.esupportail.opi.domain.beans.formation;
// default package
// Generated 20 avr. 2009 15:06:34 by Hibernate Tools 3.2.2.GA



/**
 * @author gmartel
 *
 */
public class CalendEprInfo  implements java.io.Serializable {


     private String codCin;
     private String info;

    public CalendEprInfo() {
    }

    
    
    
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codCin == null) ? 0 : codCin.hashCode());
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
		CalendEprInfo other = (CalendEprInfo) obj;
		if (codCin == null) {
			if (other.codCin != null)
				return false;
		} else if (!codCin.equals(other.codCin))
			return false;
		return true;
	}




	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendRentInfo#" + hashCode() + "[codCin=[" + codCin + "], info=["
		+ info +"]]";
	}

	/**
	 * @return the codCin
	 */
	public String getCodCin() {
		return codCin;
	}

	/**
	 * @param codCin the codCin to set
	 */
	public void setCodCin(String codCin) {
		this.codCin = codCin;
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


