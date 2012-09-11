/**
 * CRI - Universite de Rennes1 - 57SI-CIES - 2007
 * http://sourcesup.cru.fr/projects/57si-cies/
 */
package org.esupportail.opi.web.beans.utils.comparator;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;

import org.esupportail.opi.web.beans.pojo.AvisPojo;




/**
 * @author cleprous
 * 
 */
public class ComparatorDate implements Comparator<Object>, Serializable {

	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = -3564847013776612709L;
	
	
	/*
	 * PROPERTIES
	 */
	
	/**
	 * Permet d'identifier la classe afin de recuperer le bonne attribut.
	 */
	private Class< ? > className;

	/*
	 * INIT
	 */
	/**
	 * Constructor.
	 */
	public ComparatorDate() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param c
	 *            Class
	 */
	public ComparatorDate(final Class< ? > c) {
		super();
		className = c;
	}

	/*
	 * METHODS
	 */

	/**
	 * @param o1
	 * @param o2
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(final Object o1, final Object o2) {

		if (className.equals(AvisPojo.class)) {
			return sortDate(
					((AvisPojo) o2).getAvis().getDateCreaEnr(),
					((AvisPojo) o1).getAvis().getDateCreaEnr());
		} 
		
		return 0;
	}

	
	
	/**
	 * Sort the given date.
	 * 
	 * @param lib1
	 * @param lib2
	 * @return int
	 */
	private int sortDate(final Date d1, final Date d2) {
		if (!d1.equals(d2)) {
			return d1.compareTo(d2);
		}
		return 0;		
	}
	

	
	
	/*
	 * ACCESSORS
	 */

	/**
	 * @return the className
	 */
	public Class< ? > getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(final Class< ? > className) {
		this.className = className;
	}

}
