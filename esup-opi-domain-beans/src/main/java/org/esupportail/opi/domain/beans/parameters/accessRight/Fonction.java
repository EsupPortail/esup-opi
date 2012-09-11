/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters.accessRight;


/**
 * @author cleprous
 *
 */
public class Fonction extends Traitement {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -3052741157935247851L;

	/*
	 ******************* PROPERTIES ******************* */

	
	/**
	 * The domain.
	 */
	private Domain domain;
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public Fonction() {
		super();
	}

	
	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Fonction clone() {
		Fonction f = new Fonction();
		f = (Fonction) super.clone(f);
		f.setDomain(domain);
		
		return f; 
	}
	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Fonction#" + hashCode() + "[" + super.toString() + "]]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	


	/**
	 * @return the domain
	 */
	public Domain getDomain() {
		return domain;
	}


	/**
	 * @param domain the domain to set
	 */
	public void setDomain(Domain domain) {
		this.domain = domain;
	}


}
