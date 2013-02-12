package org.esupportail.opi.domain.beans.user;

/**
 * @author ylecuyer
 *
 */
public class AdresseEmployeur extends Adresse {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3540798295716346044L;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public AdresseEmployeur() {
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
	public AdresseEmployeur(final String adr1, final String adr2, final String adr3, 
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
		return "AdresseEmployeur#" + hashCode() 
			+ "],  [" + super.toString() + "]]";
	}
	

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AdresseEmployeur clone() {
		AdresseEmployeur a = new AdresseEmployeur();
		a = (AdresseEmployeur) super.clone(a);
		return a; 
	}




	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

}
