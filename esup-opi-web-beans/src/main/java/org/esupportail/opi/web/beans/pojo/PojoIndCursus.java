/**
*CRI - Universite de Rennes1 - 57SI-CIES - 2007
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.user.indcursus.CursusPro;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursus;
import org.esupportail.opi.domain.beans.user.indcursus.QualifNonDiplomante;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.utils.Utilitaires;





/**
 * @author leproust cedric
 *
 */
public class PojoIndCursus {

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The Cursus.
	 */
	private IndCursus cursus;
	
	/**
	 * List of CursusPro or QualifNonDiplomante.
	 */
	private List<IndCursus> cursusList;
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public PojoIndCursus() {
		super();
		cursus = null;
		cursusList = new ArrayList<IndCursus>();
	}
	
	/**
	 * Constructor.
	 * @param cur 
	 */
	public PojoIndCursus(final IndCursus cur) {
		super();
		cursus = cur;
		cursusList = new ArrayList<IndCursus>();
	}
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PojoIndCursus#" + hashCode() + "[cursus" + cursus + "]";
	}

	/**
	 * Initialize the Cursus with a new object.
	 */
	private void initCursus() {
		if (cursus instanceof CursusPro) {
			cursus = new CursusPro();
		} else if (cursus instanceof QualifNonDiplomante) {
			cursus = new QualifNonDiplomante();
		}
	}
	/*
	 ******************* METHODS ********************** */

	/**
	 * Add a new cursus to the list.
	 */
	public void addCursus() {
		if (log.isDebugEnabled()) {
			log.debug("entering addCursus with cursus = " + cursus);
		}		
		cursusList.add(cursus);
	//	Collections.sort(cursusList, new ComparatorString(IndCursus.class));
		initCursus();
	}
	
	/**
	 * Remove Cursus in cursusList.
	 */
	public void removeCursus() {
		if (log.isDebugEnabled()) {
			log.debug("entering removeCursus with cursus = " + cursus);
		}
		cursusList.remove(cursus);
		initCursus();
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */

	
	/**
	 * return a new PojoIndCursus list with the cursusList
	 * in order to be allowed to use the 'getShortComment' method in the view.
	 * Used for the view only 
	 * 
	 * @return the current cursusList in a new PojoIndCursus list
	 */
	public List<PojoIndCursus> getPojoCursusList() {
		List<PojoIndCursus> l = new ArrayList<PojoIndCursus>();
		for (IndCursus i : cursusList) {
			l.add(new PojoIndCursus(i));
		}
		return l;
	}
	
	/**
	 * @return the cursus
	 */
	public String getShortComment() {
		String comment = cursus.getComment();
		return Utilitaires.limitStrLength(comment, Constantes.STR_LENGTH_LIMIT);
	}

	/**
	 * @return true if ShortComment
	 */
	public Boolean getIsShortComment() {
		String comment = cursus.getComment();
		if (comment != null && (comment.length() > Constantes.STR_LENGTH_LIMIT)) {
			return true;
		}
		return false;
	}

	/**
	 * @return the cursus
	 */
	public IndCursus getCursus() {
		return cursus;
	}

	/**
	 * @param cursus the cursus to set
	 */
	public void setCursus(final IndCursus cursus) {
		this.cursus = cursus;
	}

	/**
	 * @return the cursusList
	 */
	public List<IndCursus> getCursusList() {
		return cursusList;
	}

	/**
	 * @param cursusList the cursusList to set
	 */
	public void setCursusList(final List<IndCursus> cursusList) {
		this.cursusList = cursusList;
	}
	


	
}
