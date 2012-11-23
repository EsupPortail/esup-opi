package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.parameters.VetAutoLp;

/**
 * @author cgomez
 *
 */
public class VetAutoLpPojo {
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * vetAutoLp.
	 */
	private VetAutoLp vetAutoLp;
	
	/**
	 * libWebVet.
	 */
	private String libWebVet;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public VetAutoLpPojo() {
		super();
	}
	
	/**
	 * Constructors.
	 * @param vetAutoLp
	 * @param libWebVet
	 */
	public VetAutoLpPojo(final VetAutoLp vetAutoLp, final String libWebVet) {
		super();
		this.vetAutoLp = vetAutoLp;
		this.libWebVet = libWebVet;
	}

	

	/*
	 ******************* METHODS ********************** */

	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return vetAutoLp
	 */
	public VetAutoLp getVetAutoLp() {
		return vetAutoLp;
	}
	
	/**
	 * 
	 * @param vetAutoLp
	 */
	public void setVetAutoLp(VetAutoLp vetAutoLp) {
		this.vetAutoLp = vetAutoLp;
	}
	
	/**
	 * 
	 * @return libWebVet
	 */
	public String getLibWebVet() {
		return libWebVet;
	}
	
	/**
	 * 
	 * @param libWebVet
	 */
	public void setLibWebVet(String libWebVet) {
		this.libWebVet = libWebVet;
	}
}
