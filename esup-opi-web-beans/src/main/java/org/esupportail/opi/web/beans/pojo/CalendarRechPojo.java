package org.esupportail.opi.web.beans.pojo;


/**
 * @author ylecuyer
 *
 */
public class CalendarRechPojo {
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * code of calendar searched.
	 */
	private String code;
	
	/**
	 * libelle of calendar searched.
	 */
	private String libelle;
	
	/**
	 * type of calendar.
	 */
	private String type;
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructor.
	 */
	public CalendarRechPojo() {
		super();
	}

	/*
	 ******************* METHODS ********************** */


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendarRechPojo#" + hashCode() + "code=[" + code 
		+ "], libelle=[" + libelle 
		+ "],    ["
		+ super.toString() + "]]";
	}



	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(final String code) {
		this.code = code;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(final String type) {
		this.type = type;
	}

}
