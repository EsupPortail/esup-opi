/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters.accessRight;

import java.util.Set;

import org.esupportail.opi.domain.beans.NormeSI;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

/**
 * @author cleprous
 *
 */
public abstract class Traitement extends NormeSI {

	
	/**
	 * The type for domain treatment.
	 */
	public static final String TYPE_DOMAIN = "DOM";
	
	/**
	 * The type for function treatment.
	 */
	public static final String TYPE_FUNCTION = "FCT";
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -7867226722831677556L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 *  The type of traitement.
	 */
	private String type;
	
	/**
	 * The Traitement code.
	 */
	private String code;
	
	/**
	 * The rang.
	 */
	private Integer rang;
	
	/**
	 * The Traitement action.
	 */
	private String action;
	
	
	/**
	 * The all right on this function.
	 */
	private Set<AccessRight> accessRight;
	

	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public Traitement() {
		super();
	}


	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		return result;
	}

	/**
	 * Return t with attributes of this.
	 * @param t
	 * @return Traitement
	 */
	protected Traitement clone(final Traitement t) {
		Traitement trait = t;
		trait = (Traitement) super.clone(trait);
		trait.setCode(getCode());
		trait.setRang(getRang());
		trait.setAction(getAction());
		trait.setAccessRight(getAccessRight());
		trait.setType(type);
		
		return trait; 
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (!(obj instanceof Traitement)) { return false; }
		Traitement other = (Traitement) obj;
		if (code == null) {
			if (other.getCode() != null) { return false; }
		} else if (!code.equals(other.getCode())) { return false; }
		return true;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Traitement#" + hashCode() + "[code=[" + code 
				+ "],[rang=[" + rang + "],[action=[" + action 
				+ "],  [" + super.toString() + "]]";
	}
	
	/*
	 ******************* METHODS ********************** */

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
	 * @return the rang
	 */
	public Integer getRang() {
		return rang;
	}


	/**
	 * @param rang the rang to set
	 */
	public void setRang(final Integer rang) {
		this.rang = rang;
	}


	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}


	/**
	 * @param action the action to set
	 */
	public void setAction(final String action) {
		this.action = action;
	}


	/**
	 * @return the accessRight
	 */
	public Set<AccessRight> getAccessRight() {
		return accessRight;
	}


	/**
	 * @param accessRight the accessRight to set
	 */
	public void setAccessRight(final Set<AccessRight> accessRight) {
		this.accessRight = accessRight;
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
