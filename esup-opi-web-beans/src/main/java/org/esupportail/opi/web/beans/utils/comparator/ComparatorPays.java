/**
 * 
 */
package org.esupportail.opi.web.beans.utils.comparator;

import org.esupportail.wssi.services.remote.Pays;

import java.io.Serializable;
import java.util.Comparator;



/**
 * Compare le libelle d'un objet.
 * @author cleprous
 *
 */
public class ComparatorPays implements Comparator<Pays>, Serializable {

	/**
	 * The country.
	 */
	public static final String PAYS = "PAYS";
	
	/**
	 * The nationality.
	 */
	public static final String NATIONALITE = "NAT";
	
	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = -3428314437084490971L;

	
	
	/**
	 * Sort with the good property according to the type.
	 */
	private String type;
	
	/**
	 *	Constructor. 
	 */
	public ComparatorPays() {
		super();
	}

	/**
	 * 
	 * Constructors.
	 * @param type
	 */
	public ComparatorPays(final String type) {
		this.type = type;
	}
	


	/** 
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	@Override
	public int compare(final Pays o1, final Pays o2) {
		String lib1 = o1.getLibPay();
	    String lib2 = o2.getLibPay();
	    
	    if (type.equals(NATIONALITE)) {
	    	lib1 = o1.getLibNat();
	    	lib2 = o2.getLibNat();
	    } 
	    
	    if (!(lib1.equals(lib2))) {
	        return lib1.compareTo(lib2);
	    }
	    
		return 0;
	}

}
