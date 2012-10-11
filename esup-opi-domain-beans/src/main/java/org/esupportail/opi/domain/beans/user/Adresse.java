/**
 * 
 */
package org.esupportail.opi.domain.beans.user;

import org.esupportail.opi.domain.beans.NormeSI;


/**
 * @author cleprous
 *
 */
public abstract class Adresse extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5436967664706943494L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The adress1.
	 */
	private String adr1;
	
	/**
	 * The adress2.
	 */
	private String adr2;
	
	/**
	 * The adress3.
	 */
	private String adr3;
	
	/**
	 * The contry code.
	 */
	private String codPays;
	
	/**
	 * The city code.
	 */
	private String codCommune;
	
	/**
	 * The foreign city label.
	 */
	private String libComEtr;
	
	/**
	 * The BDI code.
	 */
	private String codBdi;
	
	/**
	 * Le CEDEX d'une adresse.
	 * Peut etre null, vide ou avoir un chiffre ex.:CEDEX 9
	 */
	private String cedex;
	
	/**
	 * The phone number of corresponding.
	 */
	private String phoneNumber;
	
	/**
	 * the mail of corresponding.
	 */
	private String mail;
	
	/**
	 * The fax number of corresponding.
	 */
	private String faxNumber;
	
	

	/*
	 ******************* INIT ************************* */
	
	

	/**
	 * Constructor.
	 */
	public Adresse() {
		super();
	}

	
	public Adresse(final Adresse a) {
		super();
		this.adr1 = a.adr1;
		this.adr2 = a.adr2;
		this.adr3 = a.adr3;
		this.codPays = a.codPays;
		this.codCommune = a.codCommune;
		this.libComEtr = a.libComEtr;
		this.codBdi = a.codBdi;
		this.cedex = a.cedex;
		this.phoneNumber = a.phoneNumber;
		this.mail = a.mail;
		this.faxNumber = a.faxNumber;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof Adresse)) { return false; }
		return true;
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Adresse#" + hashCode() + "[phoneNumber=[" + phoneNumber + "], [mail=[" + mail 
						+ "], [faxNumber=[" + faxNumber 
						+ "], [cedex=[" + cedex 
						+ "] [codPays=[" + codPays + "], [adr1=[" + adr1 
						+ "],  [" + super.toString() + "]]";
	}

	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	
	/**
	 * @return the adr1
	 */
	public String getAdr1() {
		return adr1;
	}

	/**
	 * @param adr1 the adr1 to set
	 */
	public void setAdr1(final String adr1) {
		this.adr1 = adr1;
	}

	/**
	 * @return the adr2
	 */
	public String getAdr2() {
		return adr2;
	}

	/**
	 * @param adr2 the adr2 to set
	 */
	public void setAdr2(final String adr2) {
		this.adr2 = adr2;
	}

	/**
	 * @return the adr3
	 */
	public String getAdr3() {
		return adr3;
	}

	/**
	 * @param adr3 the adr3 to set
	 */
	public void setAdr3(final String adr3) {
		this.adr3 = adr3;
	}

	/**
	 * @return the codPays
	 */
	public String getCodPays() {
		return codPays;
	}

	/**
	 * @param codPays the codPays to set
	 */
	public void setCodPays(final String codPays) {
		this.codPays = codPays;
	}

	/**
	 * @return the codCommune
	 */
	public String getCodCommune() {
		return codCommune;
	}

	/**
	 * @param codCommune the codCommune to set
	 */
	public void setCodCommune(final String codCommune) {
		this.codCommune = codCommune;
	}

	/**
	 * @return the codBdi
	 */
	public String getCodBdi() {
		return codBdi;
	}

	/**
	 * @param codBdi the codBdi to set
	 */
	public void setCodBdi(final String codBdi) {
		this.codBdi = codBdi;
	}	
	

	/**
	 * @return the cedex
	 */
	public String getCedex() {
		return cedex;
	}


	/**
	 * @param cedex the cedex to set
	 */
	public void setCedex(final String cedex) {
		this.cedex = cedex;
	}


	/**
	 * @return the libComEtr
	 */
	public String getLibComEtr() {
		return libComEtr;
	}


	/**
	 * @param libComEtr the libComEtr to set
	 */
	public void setLibComEtr(final String libComEtr) {
		this.libComEtr = libComEtr;
	}


	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}


	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}


	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}


	/**
	 * @param mail the mail to set
	 */
	public void setMail(final String mail) {
		this.mail = mail;
	}


	/**
	 * @return the faxNumber
	 */
	public String getFaxNumber() {
		return faxNumber;
	}


	/**
	 * @param faxNumber the faxNumber to set
	 */
	public void setFaxNumber(final String faxNumber) {
		this.faxNumber = faxNumber;
	}


	@Override
	public Adresse clone() {
		return (Adresse) super.clone(this);
	}
}
