package org.esupportail.opi.domain.beans.references;

import org.esupportail.opi.domain.beans.NormeSI;

/**
 * @author cleprous
 *
 */
public class NombreVoeuCge extends NormeSI {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 6378899077231281047L;

	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * code CGE.
	 */
	private String codeCge;
	/**
	 * nombre de voeux par CGE.
	 */
	private int nbVoeu;
	
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Contructeur.
	 */
	public NombreVoeuCge() {
		super();
	}
	
	
	/*
	 ******************* METHODS ********************** */
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return code CGE
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
	 * @return nombre de voeux par CGE
	 */
	public int getNbVoeu() {
		return nbVoeu;
	}
	/**
	 * 
	 * @param nbVoeu
	 */
	public void setNbVoeu(final int nbVoeu) {
		this.nbVoeu = nbVoeu;
	}
}
