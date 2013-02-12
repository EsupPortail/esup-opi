/**
 * 
 */
package org.esupportail.opi.services.mails;

import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.springframework.util.StringUtils;

/**
 * @author cleprous
 *
 */
public final class MailContentUtils {


	/*
	 ******************* PROPERTIES ******************* */
	
	
	/**
	 * Start getter.
	 */
	public static final String START_ACCESSOR = "get";
	
	/**
	 * mots clefs pour les boucles.
	 */
	public static final String KEY_WORD_FOR_BEGIN = "begin_boucle"; 
	
	/**
	 * mots clefs pour les boucles.
	 */
	public static final String KEY_WORD_FOR_END = "end_boucle";
	
	/**
	 * regex for find expression.
	 */
	public static final String REGEX_EXPRESSION = "\\$\\{(.*?)\\}";
	
	/**
	 * Determine la value d'une boucle for. 
	 */
	public static final String REGEX_VALUE_FOR = "value=((\")|(&quot;))(.*?)((\")|(&quot;))";
	
	/**
	 * Determine la var d'une boucle for. 
	 */
	public static final String REGEX_VAR_FOR = "var=((\")|(&quot;))(.*?)((\")|(&quot;))";
	
	/**
	 * The logger.
	 */
	private static final Logger LOGGER = new LoggerImpl(MailContentUtils.class);
	
	/**
	 * regex to delete the symbol define the expression.
	 */
	private static final String REGEX_DELETE_SYMBOL = "\\$\\{|\\}";
	
	
	
	/*
	 ******************* INIT ************************* */


	/**
	 * Private constructor.
	 */
	private MailContentUtils() {
		throw new UnsupportedOperationException();
	}


	/*
	 ******************* METHODS ********************** */
	
	/**
	 * Make an Expression.
	 * EX. : ${param}
	 * @param param 
	 * @return String
	 */
	public static String makeExpression(final String param) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("entering makeELExpression(" + param + " )");
		}
		return "${" + param + "}";
	}
	
	/**
	 * Contains an Expression.
	 * EX. : for ${param} return param
	 * @param param 
	 * @return String
	 */
	public static String containsExpression(final String param) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("entering containsExpression(" + param + " )");
		}
		if (StringUtils.hasText(param)) {
			return param.replaceAll(REGEX_DELETE_SYMBOL, "");
		}
		return param;
	}
	
	
	/**
	 * If object is a collection return this collection object.
	 * Ne fonctionne pas pour les map
	 * @param o
	 * @param clazz
	 * @return Collection null if o is not a collection.
	 */
	@SuppressWarnings("unchecked")
	public static Collection<Object> useCollection(final Object o, final Class< ? > clazz) {
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("entering useCollection " + o + ", " + clazz);
		}
		//test les listes hibernates
		if (clazz.equals(Set.class)) {
			Set<Object> c = (Set<Object>) o;
			return c;
		} else if (clazz.equals(List.class)) {
			List<Object> c = (List<Object>) o;
			return c;
		} else if (clazz.equals(Collection.class)) {
			Collection<Object> c = (Collection<Object>) o;
			return c;
		} else {
			//si aucune de ses classes on regarde les interfaces de cl
			//pour le collection hibernate
			Class< ? >[] tabCl = clazz.getInterfaces();
			if (tabCl != null) {
				for (int i = 0; i < tabCl.length; ++i) {
					return useCollection(o, tabCl[i]);
				}
			}
		}
		//		else if (clazz.equals(Map.class)) {
		//			Map< ? , ? > c = (Map< ? , ? >) o;
		//			return c;
		//		}
		if (LOGGER.isDebugEnabled()) {
			LOGGER.debug("leaving useCollection return null");
		}
		return null;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

}
