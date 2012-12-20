/**
 * 
 */
package org.esupportail.opi.web.beans.utils.comparator;

import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;

import java.io.Serializable;
import java.util.Comparator;



/**
 * 
 * @author ylecuyer
 *
 */
public class ComparatorIndLC implements Comparator<IndividuPojo>, Serializable {

	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = 5127988285099484183L;



	/**
	 *	Constructor. 
	 */
	public ComparatorIndLC() {
		super();
	}

	

	/**
	 * @param obj1 NormeSI
	 * @param obj2 NormeSI
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final IndividuPojo obj1, final IndividuPojo obj2) {
		IndVoeuPojo indVoeu1 = null;
		IndVoeuPojo indVoeu2 = null;
		if (obj1 != null) {
			for (IndVoeuPojo ind : obj1.getIndVoeuxPojo()) {
				indVoeu1 = ind;
				break;
			}
		}
		if (obj2 != null) {
			for (IndVoeuPojo ind : obj2.getIndVoeuxPojo()) {
				indVoeu2 = ind;
				break;
			}
		}
		Integer rang1 = null;
		if (indVoeu1 != null) {
			rang1 = indVoeu1.getAvisEnService().getRang();
		}
		Integer rang2 = null;
	    if (indVoeu2 != null) {
	    	rang2 = indVoeu2.getAvisEnService().getRang();
	    }
	    
	    if (rang1 != null && !(rang1.equals(rang2))) {
	        return rang1.compareTo(rang2);
	    } else if (rang1 == null && rang2 != null){
	    	return 1;
	    }
		return 0;
	}

}
