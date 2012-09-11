/**
 * ESUP-Portail - candidatures - 2009
 * http://subversion.cru.fr/57si-OPI
 */
/**
 * 
 */
package org.esupportail.opi.services.mails.expression;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.util.StringUtils;

import org.esupportail.opi.services.mails.MailContentUtils;

/**
 * @author cleprous
 *
 */
public abstract class Expression implements Serializable {

	/*
	 *************************** PROPERTIES ******************************** */


	/**
	 * the serailization id.
	 */
	private static final long serialVersionUID = 6071527261914426417L;



	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());



	/**
	 * Number expression in mailContent.
	 */
	private int numberExp;

	/**
	 * The el expression. ex.:${param}
	 */
	private String eLExpression;


	/**
	 * Objet in expression.
	 */
	private Object objet;
	
	/**
	 * the parent of expression.
	 */
	private Expression parent;


	/*
	 *************************** INIT ************************************** */

	/**
	 * 
	 * Constructors.
	 */
	public Expression() {
		super();
	}



	/**
	 * Constructors.
	 * @param expression
	 * @param numberExp
	 * @param parent 
	 */
	public Expression(final String expression,
			final int numberExp, final Expression parent) {
		super();
		this.eLExpression = expression;
		this.numberExp = numberExp;
		this.parent = parent;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "Expression#" + hashCode() + ",  [[numberExp=" + numberExp 
		+ "],[eLExpression=" + eLExpression + "],[parent.getExpression=";
		if (parent != null) {
			s += parent.getExpression(); 
		}
		s += " ]]";
		return s;

	}


	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
		+ ((eLExpression == null) ? 0 : eLExpression.hashCode());
		result = prime * result + numberExp;
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		Expression other = (Expression) obj;
		if (eLExpression == null) {
			if (other.eLExpression != null) { return false; }
		} else if (!eLExpression.equals(other.eLExpression)) { return false; }
		if (numberExp != other.numberExp) { return false; }
		return true;
	}

	/*
	 *************************** METHODS *********************************** */

	/**
	 * return expression in Elexpression.
	 * return param in ${param}
	 * @return String
	 */
	public String getExpression() {
		return MailContentUtils.containsExpression(eLExpression);
	}


	


	/**
	 * split the expression value.
	 * exemple : campagne.libelle return [campagne,libelle] 
	 * @return String[]
	 */
	public abstract String[] getProperties();



	/**
	 * All field name to this clazz. 
	 * @param clazz
	 * @return List
	 */
	public List<String> getAllProperties(final Class< ? > clazz) {
		if (log.isDebugEnabled()) {
			log.debug("entering getAllproperties");
			log.debug("clazz = " + clazz);
		}
		List<String> properties = new ArrayList<String>();

		for (int i = 0; i < clazz.getMethods().length; ++i) {
			Method m = clazz.getMethods()[i];
			String fieldName = null;
			if (m.getName().startsWith(MailContentUtils.START_ACCESSOR)) {
				//it's an accessor
				//get the field name.
				fieldName = m.getName().replaceFirst(MailContentUtils.START_ACCESSOR, "");
				if (StringUtils.hasText(fieldName)) {
					fieldName = Character.toLowerCase(fieldName.charAt(0)) 
								+ fieldName.substring(1);
				}

			}
			if (fieldName != null) {
				properties.add(fieldName);
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("leaving getAllproperties");
			log.debug("porpterties = " + properties);
		}
		return properties;
	}



	/**
	 *TODO modifier pour prendre en compte les sous classes.
	 * Make the map with hierarchy attributs. 
	 * @param objects 
	 * @param fields 
	 * @return Map
	 */
	public Map<String, List<String>> makePropertiesMap(
			final List<Object> objects, 
			final Map<Class< ? >, List<String>> fields) {
		//String[] s = getProperties();
		

		List<String> properties = fields.get(getObjet().getClass());
		if (properties == null || properties.isEmpty()) {
			//then all properties
			properties = getAllProperties(getObjet().getClass());
			//throw new ConfigException("no properties for this class " + o.getClass());
		}
		String nameClass = null;
		if (log.isDebugEnabled()) {
			log.debug("in makePropertiesMap");
			log.debug("parent = " + getParent());
		}
		if (getParent() != null && getParent() instanceof BoucleExpression) {
			BoucleExpression b = (BoucleExpression) getParent();
			nameClass = b.getVar();
		} else {
			nameClass = getNameClass(getObjet());
		}
		Map<String, List<String>> props = new HashMap<String, List<String>>();
		for (String p : properties) {
			String[] tab = p.split(Pattern.quote("."));
			if (tab.length == 1) {
				if (props.get(nameClass) == null) {
					props.put(nameClass, new ArrayList<String>());
				}
				props.get(nameClass).add(tab[0]);
			} else {
				String firstElt = null;
				for (int i = 0; i < tab.length; ++i) {
					if (i == 0) {
						//first element
						firstElt = tab[i];
						if (props.get(firstElt) == null) {
							if (props.get(nameClass) == null) {
								props.put(nameClass, new ArrayList<String>());
							}
							props.get(nameClass).add(firstElt);
							props.put(firstElt, new ArrayList<String>());
						}
					} else {
						props.get(firstElt).add(tab[i]);
					}
				}
			}
		}

		if (log.isDebugEnabled()) {
			log.debug("properties = " + properties);
			log.debug("props = " + props);
		}

		return props;
	}
	


	/**
	 * nameClass here is : Ex. : fr.univ.domain.bean.Individu
	 * return individu
	 * @param o
	 * @return String
	 */
	public abstract String getNameClass(final Object o);




	/*
	 *************************** ACCESSORS ********************************* */




	/**
	 * @return the numberExp
	 */
	public int getNumberExp() {
		return numberExp;
	}

	/**
	 * @param numberExp the numberExp to set
	 */
	public void setNumberExp(final int numberExp) {
		this.numberExp = numberExp;
	}

	/**
	 * @return the eLexpression
	 */
	public String getELexpression() {
		return eLExpression;
	}

	/**
	 * @param lexpression the eLexpression to set
	 */
	public void setELexpression(final String lexpression) {
		eLExpression = lexpression;
	}

	/**
	 * @return the parent
	 */
	public Expression getParent() {
		return parent;
	}



	/**
	 * @param parent the parent to set
	 */
	public void setParent(final Expression parent) {
		this.parent = parent;
	}

	/**
	 * @return the objet
	 */
	public Object getObjet() {
		return objet;
	}



	/**
	 * @param objet the objet to set
	 */
	public void setObjet(final Object objet) {
		this.objet = objet;
	}




}
