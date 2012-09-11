/**
 * 
 */
package org.esupportail.opi.domain.beans.user.indcursus;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.Individu;


/**
 * @author cleprous
 *
 */
public class IndBac extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1875894363144150335L;
	
	

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The code Bac.
	 */
	private String codBac;
	
	/**
	 * The code mention.
	 */
	private String codMnb;
	
	/**
	 * The code departement.
	 */
	private String codDep;
	
	/**
	 * The code etablissement.
	 */
	private String codEtb;
	
	/**
	 * The code commune.
	 */
	private String codCom;
	
	/**
	 * The code Pays.
	 */
	private String codPay;
	
	/**
	 * The code type etablissement.
	 */
	private String codTpe;
	
	/**
	 * Date d'obtention.
	 */
	private String dateObtention;
	
	/**
	 * Temoin pour savoir si les infos viennent d'Apogee.
	 * Default value = false.
	 */
	private Boolean temoinFromApogee;
	
	/**
	 * The Individu.
	 */
	private Individu individu;
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public IndBac() {
		super();
		temoinFromApogee = false;
		codDep = "";
		codMnb = "";
		codBac = "";
	}

	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndBac#" + hashCode() + "[codBac=[" + codBac 
			+ "],[codMnb=[" + codMnb 
			+ "],[codDep=[" + codDep 
			+ "],[codEtb=[" + codEtb 
			+ "],[codTpe=[" + codTpe 
			+ "],[temoinFromApogee=[" + temoinFromApogee 
			+ "]  [" + super.toString() + "]]";
	}
	

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((codBac == null) ? 0 : codBac.hashCode());
		result = prime * result + ((codDep == null) ? 0 : codDep.hashCode());
		result = prime * result + ((codEtb == null) ? 0 : codEtb.hashCode());
		result = prime * result + ((codMnb == null) ? 0 : codMnb.hashCode());
		result = prime * result + ((codTpe == null) ? 0 : codTpe.hashCode());
		result = prime * result + ((dateObtention == null) ? 0 : dateObtention.hashCode());
		result = prime * result + ((codCom == null) ? 0 : codCom.hashCode());
		result = prime * result + ((codPay == null) ? 0 : codPay.hashCode());
		return result;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof IndBac)) {	return false; }
		IndBac other = (IndBac) obj;
		if (codBac == null) {
			if (other.getCodBac() != null) {	return false; }
		} else if (!codBac.equals(other.getCodBac())) { return false; }
		if (codDep == null) {
			if (other.getCodDep() != null) { return false; }
		} else if (!codDep.equals(other.getCodDep())) { return false; }
		if (codEtb == null) {
			if (other.getCodEtb() != null) { return false; }
		} else if (!codEtb.equals(other.getCodEtb())) { return false; }
		if (codMnb == null) {
			if (other.getCodMnb() != null) {	return false; }
		} else if (!codMnb.equals(other.getCodMnb())) { return false; }
		if (codTpe == null) {
			if (other.getCodTpe() != null) {	return false; }
		} else if (!codTpe.equals(other.getCodTpe())) { return false; }
		if (dateObtention == null) {
			if (other.getDateObtention() != null) {	return false; }
		} else if (!dateObtention.equals(other.getDateObtention())) { return false; }
		if (codCom == null) {
			if (other.getCodCom() != null) {	return false; }
		} else if (!codCom.equals(other.getCodCom())) { return false; }
		if (codPay == null) {
			if (other.getCodPay() != null) {	return false; }
		} else if (!codPay.equals(other.getCodPay())) { return false; }
		return true;
	}

	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	



	/**
	 * @return the codBac
	 */
	public String getCodBac() {
		return codBac;
	}


	/**
	 * @param codBac the codBac to set
	 */
	public void setCodBac(final String codBac) {
		this.codBac = codBac;
	}


	/**
	 * @return the codMnb
	 */
	public String getCodMnb() {
		return codMnb;
	}


	/**
	 * @param codMnb the codMnb to set
	 */
	public void setCodMnb(final String codMnb) {
		this.codMnb = codMnb;
	}


	/**
	 * @return the codDep
	 */
	public String getCodDep() {
		return codDep;
	}


	/**
	 * @param codDep the codDep to set
	 */
	public void setCodDep(final String codDep) {
		this.codDep = codDep;
	}


	/**
	 * @return the codEtb
	 */
	public String getCodEtb() {
		return codEtb;
	}


	/**
	 * @param codEtb the codEtb to set
	 */
	public void setCodEtb(final String codEtb) {
		this.codEtb = codEtb;
	}


	/**
	 * @return the codTpe
	 */
	public String getCodTpe() {
		return codTpe;
	}


	/**
	 * @param codTpe the codTpe to set
	 */
	public void setCodTpe(final String codTpe) {
		this.codTpe = codTpe;
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


	/**
	 * @return the dateObtention
	 */
	public String getDateObtention() {
		return dateObtention;
	}


	/**
	 * @param dateObtention the dateObtention to set
	 */
	public void setDateObtention(final String dateObtention) {
		this.dateObtention = dateObtention;
	}


	/**
	 * @return the codCom
	 */
	public String getCodCom() {
		return codCom;
	}


	/**
	 * @param codCom the codCom to set
	 */
	public void setCodCom(final String codCom) {
		this.codCom = codCom;
	}


	/**
	 * @return the codPay
	 */
	public String getCodPay() {
		return codPay;
	}


	/**
	 * @param codPay the codPay to set
	 */
	public void setCodPay(final String codPay) {
		this.codPay = codPay;
	}


	/**
	 * @return the temoinFromApogee
	 */
	public Boolean getTemoinFromApogee() {
		return temoinFromApogee;
	}


	/**
	 * @param temoinFromApogee the temoinFromApogee to set
	 */
	public void setTemoinFromApogee(final Boolean temoinFromApogee) {
		this.temoinFromApogee = temoinFromApogee;
	}
	

}
