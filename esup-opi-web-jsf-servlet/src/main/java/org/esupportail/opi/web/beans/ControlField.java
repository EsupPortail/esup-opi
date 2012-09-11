/**
 * 
 */
package org.esupportail.opi.web.beans;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.springframework.util.StringUtils;

/**
 * @author cleprous
 * ControlField : controle si les champs sont remplies.
 * utilise dans le controle d etat du dossier.
 */
public class ControlField implements Serializable {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * 
	 */
	private static final long serialVersionUID = 8038681763607073565L;

	/**
	 * Start getter.
	 */
	private static final String START_ACCESSOR = "get";
	
	/**
	 * The fields by class.
	 */
	private Map<Class< ? >, List<String>> fields;
	
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public ControlField() {
		super();
	}






	/*
	 ******************* METHODS ********************** */

	/**
	 * @param o
	 * @param useFcFields if true use fieldsForSfc
	 * @return Boolean
	 */
	public Boolean control(final Object o) {
		if (log.isDebugEnabled()) {
			log.debug("entering control with class = = " + o.getClass());
		}
		
		Map<Class< ? >, List<String>> mapUse = fields;
//		if (codeRI == FormationContinue.CODE) {
//			mapUse = fieldsForSfc;
//		} else {
//			mapUse = fields;
//		}
		
		if (mapUse.get(o.getClass()) == null) { 
			return false; 
		} 
		List<String> fieldList = mapUse.get(o.getClass());
		for (String f : fieldList) {
			String methodName = START_ACCESSOR + Utilitaires.upperCaseFirstChar(f, false);
			if (log.isDebugEnabled()) {
				log.debug("look for method = = " + methodName);
			}
			try {
				Method m = o.getClass().getMethod(methodName);
				if (String.class.equals(m.getReturnType())) {
					String s = (String) m.invoke(o);  
					if (!StringUtils.hasText(s)) {
						//si vide ou null
						return false;
					}
				} else {
					Object obj = m.invoke(o);
					if (obj == null) {
						return false;
					}
					
					Boolean listEmpty = controlCollection(obj, obj.getClass());
					if (!listEmpty) { return false; }
					
				}

			} catch (SecurityException e) {
				log.error(e);
				return false;
			} catch (IllegalArgumentException e) {
				log.error(e);
				return false;
			} catch (IllegalAccessException e) {
				log.error(e);
				return false;
			} catch (InvocationTargetException e) {
				log.error(e);
				return false;
			} catch (NoSuchMethodException e) {
				log.error(e);
				throw new ConfigException("the method name " 
						+ methodName 
						+ "does not exist for the class " 
						+ o.getClass() );
			}
		}


		return true;
	}

	/**
	 * If list test if empty return false if empty.
	 * @param o
	 * @param clazz
	 * @return Boolean true if list not empty or if it's not list
	 */
	private Boolean controlCollection(final Object o, final Class< ? > clazz) {
		if (log.isDebugEnabled()) {
			log.debug("entering control collection " + o + ", " + clazz);
		}
		Boolean isNotEmpty = true;
		//test les listes hibernates
		if (clazz.equals(Set.class)) {
			Set< ? > c = (Set< ? >) o;
			if (c.isEmpty()) {
				isNotEmpty = false;
			}
		} else if (clazz.equals(Map.class)) {
			Map< ? , ? > c = (Map< ? , ? >) o;
			if (c.isEmpty()) {
				isNotEmpty = false;
			}
		} else if (clazz.equals(List.class)) {
			List< ? > c = (List< ? >) o;
			if (c.isEmpty()) {
				isNotEmpty = false;
			}
		} else if (clazz.equals(Collection.class)) {
			Collection< ? > c = (Collection< ? >) o;
			if (c.isEmpty()) {
				isNotEmpty = false;
			}
		} else {
			//si aucune de ses classes on regarde les interfaces de cl
			Class< ? >[] tabCl = clazz.getInterfaces();
			if (tabCl != null) {
				for (int i = 0; i < tabCl.length; ++i) {
					return controlCollection(o, tabCl[i]);
				}
			}
		}
		
		if (log.isDebugEnabled()) {
			log.debug("leaving control collection return " + isNotEmpty);
		}
		return isNotEmpty;
	}
	

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the fields
	 */
	public Map<Class< ? >, List<String>> getFields() {
		return fields;
	}

	/**
	 * @param fields the fields to set
	 */
	public void setFields(final Map<Class< ? >, List<String>> fields) {
		this.fields = fields;
	}










}
