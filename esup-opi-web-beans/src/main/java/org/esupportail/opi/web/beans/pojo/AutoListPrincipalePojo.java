package org.esupportail.opi.web.beans.pojo;


import org.esupportail.opi.domain.beans.parameters.AutoListPrincipale;

import java.util.List;


/**
 * @author cgomez
 *
 */
public class AutoListPrincipalePojo {
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * AutoListPrincipale.
	 */
	private AutoListPrincipale autoLp;

	/**
	 * libellÃ© du centre de gestion.
	 */
	private String libelleCge;

	/**
	 * droit d'affichage.
	 */
	private boolean isRight;
	
	/**
	 * List of commissionPojo corresponding to the list.
	 * of commissions of autoLp
	 */
	private List<CommissionPojo> listCommissionPojo;
	
	/**
	 * listVetAutoLpPojo.
	 */
	private List<VetAutoLpPojo> listVetAutoLpPojo;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public AutoListPrincipalePojo() {
		super();
	}
	
	/**
	 * Constructors.
	 * @param autoLp 
	 * @param listVetAutoLpPojo
	 */
	public AutoListPrincipalePojo(final AutoListPrincipale autoLp,
			final List<VetAutoLpPojo> listVetAutoLpPojo,
			final List<CommissionPojo> listCommissionPojo) {
		super();
		this.autoLp = autoLp;
		this.listVetAutoLpPojo = listVetAutoLpPojo;
		this.listCommissionPojo = listCommissionPojo;
	}

	/*
	 ******************* METHODS ********************** */

	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return autoLp
	 */
	public AutoListPrincipale getAutoLp() {
		return autoLp;
	}
	
	/**
	 * 
	 * @param autoLp
	 */
	public void setAutoLp(final AutoListPrincipale autoLp) {
		this.autoLp = autoLp;
	}
	
	/**
	 * 
	 * @return libelleCge
	 */
	public String getLibelleCge() {
		return libelleCge;
	}
	
	/**
	 * 
	 * @param libelleCge
	 */
	public void setLibelleCge(final String libelleCge) {
		this.libelleCge = libelleCge;
	}
	
	/**
	 * 
	 * @return
	 */
	public boolean isRight() {
		return isRight;
	}
	
	/**
	 * 
	 * @param isRight
	 */
	public void setRight(final boolean isRight) {
		this.isRight = isRight;
	}
	
	/**
	 * 
	 * @return listVetAutoLpPojo
	 */
	public List<VetAutoLpPojo> getListVetAutoLpPojo() {
		return listVetAutoLpPojo;
	}
	
	/**
	 * 
	 * @param listVetAutoLpPojo
	 */
	public void setListVetAutoLpPojo(final List<VetAutoLpPojo> listVetAutoLpPojo) {
		this.listVetAutoLpPojo = listVetAutoLpPojo;
	}

	/**
	 * @return the listCommissionPojo
	 */
	public List<CommissionPojo> getListCommissionPojo() {
		return listCommissionPojo;
	}

	/**
	 * @param listCommissionPojo the listCommissionPojo to set
	 */
	public void setListCommissionPojo(final List<CommissionPojo> listCommissionPojo) {
		this.listCommissionPojo = listCommissionPojo;
	}
	
}
