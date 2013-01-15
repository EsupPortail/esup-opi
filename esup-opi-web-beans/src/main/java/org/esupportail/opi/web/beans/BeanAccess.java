/**
*CRI - Universite de Rennes1 - 57SI-CIES - 2007
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans;

import org.esupportail.opi.domain.beans.parameters.accessRight.AccessType;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;

import java.util.Map;



/**
 * @author leproust cedric
 *
 */
public class BeanAccess {

	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The traitement.
	 */
	private Traitement traitement;
	
	/**
	 * The rights has been choices by user.
	 */
	private Map<AccessType, Boolean> theDroits;
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public BeanAccess() {
		super();
	}
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (traitement != null) {
			result += traitement.hashCode();
		}
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof BeanAccess)) { return false; }
		final BeanAccess other = (BeanAccess) obj;
		if (traitement == null) {
			if (other.traitement != null) { return false; }
		} else if (!traitement.equals(other.traitement)) { return false; }
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BeanAccess#" + hashCode() + "[theDroits" + theDroits + "],[traitement" + traitement + "]]";
	}
	
	
	/*
	 ******************* METHODS ********************** */

	/**
	 * Dans le cas oe un domain e des fonctionnalites, le seul droit autorise sur ce domain est READ.
	 * Ce droit est attribue automatiquement.
	 * @return Boolean true if theDroits contains only the AccessType READ.
	 */
	public Boolean getOnlyReadDomain() {
		if (theDroits.size() > 1) { return false; }
		for (AccessType a : theDroits.keySet()) {
			if (!a.getCode().equals(AccessType.COD_READ)) {
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * @return true if traitement is a function.
	 */
	public Boolean getIsFunction() {
		return traitement instanceof Fonction;
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	
	
	/**
	 * @return the theDroits
	 */
	public Map<AccessType, Boolean> getTheDroits() {
		return theDroits;
	}

	/**
	 * @param theDroits the theDroits to set
	 */
	public void setTheDroits(final Map<AccessType, Boolean> theDroits) {
		this.theDroits = theDroits;
	}

	/**
	 * @return the traitement
	 */
	public Traitement getTraitement() {
		return traitement;
	}

	/**
	 * @param traitement the traitement to set
	 */
	public void setTraitement(final Traitement traitement) {
		this.traitement = traitement;
	}

	
	

	
}
