package org.esupportail.opi.domain.beans.user;

/**
 * @author ylecuyer
 *
 */
public class AdresseFix extends Adresse {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 4734878670592726560L;
	
	/**
	 * The Individu.
	 */
	private Individu individu;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public AdresseFix() {
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
	public AdresseFix(final String adr1, final String adr2, final String adr3, 
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
		return "AdresseFix#" + hashCode() 
			+ "],  [" + super.toString() + "]]";
	}
	

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AdresseFix clone() {
		AdresseFix a = new AdresseFix();
		a = (AdresseFix) super.clone(a);
		return a; 
	}



	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

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

	

}
