package org.esupportail.opi.domain.beans.user;

/**
 * @author ylecuyer
 *
 */
public class AdresseCommission extends Adresse {

	/*
	 ******************* PROPERTIES ******************* */

	/*
	 ******************* INIT ************************* */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6719121841817693461L;


	/**
	 * Constructors.
	 */
	public AdresseCommission() {
		super();
	}

	/**
	 * Constructors.
	 * @param adr1
	 * @param adr2
	 * @param adr3
	 * @param codCommune
	 * @param codBdi 
	 * @param codPays
	 */
	public AdresseCommission(final String adr1, final String adr2, final String adr3, 
			final String codCommune, final String codBdi, final String codPays) {
		super();
		setAdr1(adr1);
		setAdr2(adr2);
		setAdr3(adr3);
		setCodCommune(codCommune);
		setCodBdi(codBdi);
		setCodPays(codPays);
	}


	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdresseCommission#" + hashCode() 
			+ "],  [" + super.toString() + "]]";
	}
	

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AdresseCommission clone() {
		AdresseCommission a = new AdresseCommission();
		a = (AdresseCommission) super.clone(a);
		return a; 
	}




	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

}
