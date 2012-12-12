/**
* CRI - Universite de Rennes1 - 57SI-CIES - 2007
* http://sourcesup.cru.fr/projects/57si-cies/
*/
package org.esupportail.opi.web.beans.utils.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;



/**
 * @author cleprous
 *
 */
public class ComparatorInteger implements Comparator<Object>, Serializable {

	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = 1923826937233127180L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * Permet d'identifier la classe afin de recuperer le bonne attribut.
	 */
	private Class< ? > className;
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructor.
	 */
	public ComparatorInteger() {
		super();
	}
	
	/**
	 * Constructor.
	 * @param c Class
	 */
	public ComparatorInteger(final Class< ? > c) {
		super();
		className = c;
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 * @param o1 
	 * @param o2 
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(final Object o1, final Object o2) {
		int rang1 = 0;
		int rang2 = 0;
		
		if (className.equals(Traitement.class)) {
			Traitement q1 = (Traitement) o1;
			Traitement q2 = (Traitement) o2;
			rang1 = q1.getRang();
			rang2 = q2.getRang();
		}
		
		
		if (rang1 != rang2) {
			if (rang1 > rang2) {
				return 1;
			}
	        return -1;
	    }
		
		return 0;
	}

	

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the className
	 */
	public Class< ? > getClassName() {
		return className;
	}



	/**
	 * @param className the className to set
	 */
	public void setClassName(final Class< ? > className) {
		this.className = className;
	}



	
}
