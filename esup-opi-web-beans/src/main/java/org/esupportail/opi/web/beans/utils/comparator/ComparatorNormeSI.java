/**
 * 
 */
package org.esupportail.opi.web.beans.utils.comparator;

import java.io.Serializable;
import java.util.Comparator;

import org.esupportail.opi.domain.beans.NormeSI;



/**
 * Compare le libelle d'un objet.
 * @author cleprous
 *
 */
public class ComparatorNormeSI implements Comparator<NormeSI>, Serializable {

	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = 5127988285099484183L;



	/**
	 *	Constructor. 
	 */
	public ComparatorNormeSI() {
		super();
	}

	

	/**
	 * @param obj1 NormeSI
	 * @param obj2 NormeSI
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(final NormeSI obj1,
								final NormeSI obj2) {
		String lib1 = obj1.getLibelle().toUpperCase();
	    String lib2 = obj2.getLibelle().toUpperCase();
	    
	    if (!(lib1.equals(lib2))) {
	        return lib1.compareTo(lib2);
	    }
	    
		return 0;
	}

}
