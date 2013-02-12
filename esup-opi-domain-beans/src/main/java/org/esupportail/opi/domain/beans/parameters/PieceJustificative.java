/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

import java.util.Set;


/**
 * @author cleprous
 *
 */
public class PieceJustificative extends Nomenclature {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4488846342380474281L;

	
	/*
	 ******************* PROPERTIES ******************* */

	
	/**
	 * Liste des codes etape et code version etape rattache e cette piece justificative.
	 */
	private Set<PieceJustiVet> versionEtapes;
	
	/**
	 * Code Regime d'inscription.
	 * Default value 0 --> formation Initiale.
	 * 1 --> Formation Continue
	 */
	private int codeRI;
	
	/**
	 * Commun e toutes les versions etapes.
	 */
	private Boolean isForAllVet;
	
	
	/**
	 * Retourne une String correspondant au nom du document lié à la piece justificative
	 */
	private String nomDocument;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public PieceJustificative() {
		super();
	}

	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PieceJustificative#" + hashCode() + "[codeRI=[" + codeRI 
		+ "],[isForAllVet=[" + isForAllVet 
		+ "],[versionEtapes=[" + versionEtapes 
		+ "],  [" + super.toString() + "]]";
	}
	

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PieceJustificative clone() {
		PieceJustificative t = new PieceJustificative();
		t = (PieceJustificative) super.clone(t);
		t.setVersionEtapes(versionEtapes);
		t.setIsForAllVet(isForAllVet);
		t.setCodeRI(codeRI);
		t.setNomDocument(nomDocument);
		return t; 
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the versionEtapes
	 */
	public Set<PieceJustiVet> getVersionEtapes() {
		return versionEtapes;
	}



	/**
	 * @param versionEtapes the versionEtapes to set
	 */
	public void setVersionEtapes(final Set<PieceJustiVet> versionEtapes) {
		this.versionEtapes = versionEtapes;
	}



	/**
	 * @return the codeRI
	 */
	public int getCodeRI() {
		return codeRI;
	}



	/**
	 * @param codeRI the codeRI to set
	 */
	public void setCodeRI(final int codeRI) {
		this.codeRI = codeRI;
	}



	/**
	 * @return the isForAllVet
	 */
	public Boolean getIsForAllVet() {
		return isForAllVet;
	}



	/**
	 * @param isForAllVet the isForAllVet to set
	 */
	public void setIsForAllVet(final Boolean isForAllVet) {
		this.isForAllVet = isForAllVet;
	}

	
	/**
	 * @return String
	 */
	public String getNomDocument() {
		return this.nomDocument;
	}



	/**
	 * @param nomDocument
	 */
	public void setNomDocument(String nomDocument) {
		this.nomDocument = nomDocument;
	}



	
	

		
}
