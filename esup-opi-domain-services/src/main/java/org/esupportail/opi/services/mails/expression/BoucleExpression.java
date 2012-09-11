/**
 * ESUP-Portail - candidatures - 2009
 * http://subversion.cru.fr/57si-OPI
 */
/**
 * 
 */
package org.esupportail.opi.services.mails.expression;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.services.mails.MailContentUtils;
import org.springframework.util.StringUtils;

/**
 * @author cleprous
 * BoucleExpression : parcours d'une liste.
 */
public class BoucleExpression extends Expression {

	/*
	 *************************** PROPERTIES ******************************** */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3678467857366220496L;

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());




	/**
	 * contains to particular expression. 
	 */
	private String contains;

	/**
	 * expressions in the contains.
	 */
	private List<Expression> childrens;




	/*
	 *************************** INIT ************************************** */

	/**
	 * 
	 * Constructors.
	 */
	public BoucleExpression() {
		super();
		childrens = new ArrayList<Expression>();
	}



	/**
	 * Constructors.
	 * @param contains
	 * @param expression
	 * @param numberExp
	 * @param parent 
	 */
	public BoucleExpression(
			final String contains, final String expression,
			final int numberExp, final Expression parent) {
		super(expression, numberExp, parent);
		this.contains = contains;
		childrens = new ArrayList<Expression>();
	}


	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "BoucleExpression#" + hashCode() + ",  [[ " + super.toString() + "],[contains=" + contains 
		+ "],[childrens=" + childrens + "]]";
		return s;

	}

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((contains == null) ? 0 : contains.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		BoucleExpression other = (BoucleExpression) obj;
		if (contains == null) {
			if (other.contains != null) { return false; }
		} else if (!contains.equals(other.contains)) { return false; }
		return true;
	}
	

	/*
	 *************************** METHODS *********************************** */

	





	
	/** 
	 * @see org.esupportail.opi.services.mails.expression.Expression#getProperties()
	 */
	public String[] getProperties() {
		String[] s = null;
		String value = getValue();
		if (value.contains(".")) {
			//particular expression
			s = getValue().split(Pattern.quote("."));
		} else {
			//un seul element
			String[] si = {value};
			s = si;
		}
		
		if (log.isDebugEnabled()) {
			log.debug("leaving getProperties");
			for (int i = 0; i < s.length; ++i) {
				log.debug("s[i] = " + s[i]);
			}
		}
//		if (s == null) {
//			throw new ConfigException("L'attribut value  " 
//					+ value + " est vide. Il doit contenir une liste");
//		}
		return s;
	}





	/**
	 * name of properties in for expression.
	 * @return String
	 */
	private String getValue() {
		Pattern p = Pattern.compile(MailContentUtils.REGEX_VALUE_FOR);
		Matcher m = p.matcher(getELexpression());
		while (m.find()) {
			return m.group(4);
		}
		return null;
	}

	/**
	 * var attributes for boucle expression.
	 * @return String
	 */
	public String getVar() {
		if (getParent() != null && getParent() instanceof BoucleExpression) {
			BoucleExpression b = (BoucleExpression) getParent();
			return b.getVar();
		} 

		Pattern p = Pattern.compile(MailContentUtils.REGEX_VAR_FOR);
		Matcher m = p.matcher(getELexpression());
		while (m.find()) {
			return m.group(4);
		}

		return null;
	}


	
	/** 
	 * @see org.esupportail.opi.services.mails.expression.Expression#getNameClass(java.lang.Object)
	 */
	public String getNameClass(final Object o) {
		if (log.isDebugEnabled()) {
			log.debug("getNameClass with object = " + o);
		}
		String className = null;
		String name = null;
		if (o != null) {
			Collection<Object> col = MailContentUtils.useCollection(o, o.getClass());
			if (col != null) {
				for (Object ob : col) {
					className = ((ob.getClass().getName().matches(".*(_\\$\\$_javassist_).*")) ?
					    ob.getClass().getSuperclass() :
					        ob.getClass()).getName();
					break;
				}
			} else {
				className = ((o.getClass().getName().matches(".*(_\\$\\$_javassist_).*")) ?
                    o.getClass().getSuperclass() :
                        o.getClass()).getName();
			}
			if (log.isDebugEnabled()) {
				log.debug("getNameClass() -> className = " + className);
			}
			//className peut etre vide dans la cas ou o est un list vide
			if (StringUtils.hasText(className)) {
				String[] st = className.split(Pattern.quote("."));
				//the last element is a name class
				name = st[st.length - 1];
				name = Character.toLowerCase(name.charAt(0)) 
							+ name.substring(1);
				
				if (log.isDebugEnabled()) {
					log.debug("name = " + name);
				}
			}
		}
		return name;
	}




	/*
	 *************************** ACCESSORS ********************************* */


	/**
	 * @return the contains
	 */
	public String getContains() {
		return contains;
	}

	/**
	 * @param contains the contains to set
	 */
	public void setContains(final String contains) {
		this.contains = contains;
	}

	/**
	 * @return the expressions
	 */
	public List<Expression> getChildrens() {
		return childrens;
	}



	/**
	 * @param childrens the childrens to set
	 */
	public void setExpressions(final List<Expression> childrens) {
		this.childrens = childrens;
	}



}
