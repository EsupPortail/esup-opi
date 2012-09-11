/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;


import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.references.commission.Commission;

import java.util.List;
import java.util.Set;


/**
 * @author Gomez
 *
 */
public class AutoListPrincipale extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7734100473028282841L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * 
	 */
	private String mail;
	
	/**
	 * 
	 */
	private String codeCge;
	
	/**
	 * The list of commission.
	 */
	private Set<Commission> listCommission;
	
	/**
	 * The list of vet.
	 */
	private Set<VetAutoLp> listVet;
	
	/**
	 * The type of decision to the main list.
	 */
	private TypeDecision typeDecisionDeLP;
	
	/**
	 * The list type of decision.
	 */
	private List<TypeDecision> listTypeOfDecision;
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public AutoListPrincipale() {
		//Constructors.
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AutoListPrincipale#" + hashCode() + "[Mail=[" + mail 
		+ "],[codeCge=[" + codeCge + "],  [" + super.toString() + "]]";
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * 
	 * @return mail
	 */
	public String getMail() {
		return mail;
	}
	
	/**
	 * 
	 * @param mail
	 */
	public void setMail(final String mail) {
		this.mail = mail;
	}
	
	/**
	 * 
	 * @return codeCge
	 */
	public String getCodeCge() {
		return codeCge;
	}
	
	/**
	 * 
	 * @param codeCge
	 */
	public void setCodeCge(final String codeCge) {
		this.codeCge = codeCge;
	}
	
	/**
	 * 
	 * @return listCommission
	 */
	public Set<Commission> getListCommission() {
		return listCommission;
	}
	
	/**
	 * 
	 * @param listCommission
	 */
	public void setListCommission(final Set<Commission> listCommission) {
		this.listCommission = listCommission;
	}
	
	/**
	 * 
	 * @return listVet
	 */
	public Set<VetAutoLp> getListVet() {
		return listVet;
	}
	
	/**
	 * 
	 * @param listVet
	 */
	public void setListVet(final Set<VetAutoLp> listVet) {
		this.listVet = listVet;
	}

	/**
	 * 
	 * @return typeDecisionDeLP
	 */
	public TypeDecision getTypeDecisionDeLP() {
		return typeDecisionDeLP;
	}
	
	/**
	 * 
	 * @param typeDecisionDeLP
	 */
	public void setTypeDecisionDeLP(final TypeDecision typeDecisionDeLP) {
		this.typeDecisionDeLP = typeDecisionDeLP;
	}
	
	/**
	 * 
	 * @return listTypeOfDecision
	 */
	public List<TypeDecision> getListTypeOfDecision() {
		return listTypeOfDecision;
	}
	
	/**
	 * 
	 * @param listTypeOfDecision
	 */
	public void setListTypeOfDecision(final List<TypeDecision> listTypeOfDecision) {
		this.listTypeOfDecision = listTypeOfDecision;
	}
}
