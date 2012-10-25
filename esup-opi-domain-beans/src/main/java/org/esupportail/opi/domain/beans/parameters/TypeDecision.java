/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;


/**
 * @author cleprous
 *
 */
public class TypeDecision extends Nomenclature {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4488846342380474281L;

	
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * Code Apogee.
	 */
	private String codeApogee;
	
	/**
	 * code Type de convocation.
	 */
	private String  codeTypeConvocation;
	
	/**
	 * Flag to know if this is final decision.
	 * default value = false
	 */
	private Boolean isFinal;
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public TypeDecision() {
		super();
		isFinal = false;
	}

	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TypeDecision#" + hashCode() + "[codeApogee=[" + codeApogee 
		 + "[isFinal=[" + isFinal + "],[codeTypeConvocation=[" + codeTypeConvocation
		 + "],  [" + super.toString() + "]]";
	}
	

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TypeDecision clone() {
		TypeDecision t = new TypeDecision();
		t = (TypeDecision) super.clone(t);
		t.setCodeApogee(codeApogee);
		t.setCodeTypeConvocation(codeTypeConvocation);
		t.setIsFinal(isFinal);
		
		return t; 
	}

	

	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	



	/**
	 * @return the codeApogee
	 */
	public String getCodeApogee() {
		return codeApogee;
	}

	/**
	 * @param codeApogee the codeApogee to set
	 */
	public void setCodeApogee(final String codeApogee) {
		this.codeApogee = codeApogee;
	}


	/**
	 * @return the isFinal
	 */
	public Boolean getIsFinal() {
		return isFinal;
	}


	/**
	 * @param isFinal the isFinal to set
	 */
	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}



	/**
	 * @return the codeTypeConvocation
	 */
	public String getCodeTypeConvocation() {
		return codeTypeConvocation;
	}



	/**
	 * @param codeTypeConvocation the codeTypeConvocation to set
	 */
	public void setCodeTypeConvocation(final String codeTypeConvocation) {
		this.codeTypeConvocation = codeTypeConvocation;
	}



		
}
