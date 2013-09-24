/**
 * ESUP-Portail - candidatures - 2009
 * http://subversion.cru.fr/57si-OPI
 */
/**
 * 
 */
package org.esupportail.opi.services.mails.expression;

import java.util.regex.Pattern;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;


/**
 * @author cleprous
 * SimpleExpression : expression just contains an accessors. 
 */
public class SimpleExpression extends Expression {

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




	/*
	 *************************** INIT ************************************** */

	/**
	 * 
	 * Constructors.
	 */
	public SimpleExpression() {
		super();
	}



	/**
	 * Constructors.
	 * @param expression
	 * @param numberExp
	 * @param parent 
	 */
	public SimpleExpression(final String expression,
			final int numberExp, final Expression parent) {
		super(expression, numberExp, parent);
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "SimpleExpression#" + hashCode() + ",  [[ " + super.toString() + "]]";
		return s;

	}


	

	/*
	 *************************** METHODS *********************************** */




	
	/** 
	 * @see org.esupportail.opi.services.mails.expression.Expression#getProperties()
	 */
	public String[] getProperties() {
		String[] s = null;

		s = getExpression().split(Pattern.quote("."));
		
		if (log.isDebugEnabled()) {
			log.debug("leaving getProperties");
			for (int i = 0; i < s.length; ++i) {
				log.debug("s[i] = " + s[i]);
			}
		}
//		if (s == null) {
//			throw new ConfigException("l'expression " + getELexpression() + "ne contient d'accesseurs");
//		}
		
		return s;
	}






	
	/** 
	 * @see org.esupportail.opi.services.mails.expression.Expression#getNameClass(java.lang.Object)
	 */
	@Override
	public String getNameClass(final Object o) {
		String className = null;
		String name = null;
		if (o != null) {
			className = ((o.getClass().getName().matches(".*(_\\$\\$_javassist_).*")) ?
                o.getClass().getSuperclass() :
                    o.getClass()).getName();
			String[] st = className.split(Pattern.quote("."));
			//the last element is a name class
			name = st[st.length - 1];
			name = Character.toLowerCase(name.charAt(0)) 
						+ name.substring(1);
			
			if (log.isDebugEnabled()) {
				log.debug("name = " + name);
			}
		}
		return name;
	}
}

