/**
 * 
 */
package org.esupportail.opi.domain.beans.user.indcursus;


/**
 * @author cleprous
 *
 */
public class CursusPro extends IndCursus {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 2269713438375938190L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The quotite.
	 */
	private String quotite;
	
	/**
	 * the function during the stage. 
	 */
	private String function;
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public CursusPro() {
		super();
	}

	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CursusExt#" + hashCode() + "[quotite=[" + quotite 
			+ "],[function=[" + function 
			+ "]  [" + super.toString() + "]]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the quotite
	 */
	public String getQuotite() {
		return quotite;
	}

	/**
	 * @param quotite the quotite to set
	 */
	public void setQuotite(final String quotite) {
		this.quotite = quotite;
	}


	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}


	/**
	 * @param function the function to set
	 */
	public void setFunction(final String function) {
		this.function = function;
	}
	

}
