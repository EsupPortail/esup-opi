package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.utils.Utilitaires;

/**
 * @author ylecuyer
 *
 */
public class AvisPojo {
	/*
	 ******************* PROPERTIES ******************* */

	private Avis avis;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public AvisPojo() {
		super();
	}
	
	/**
	 * Constructors.
	 * @param avis
	 */
	public AvisPojo(final Avis avis) {
		super();
		this.avis = avis;
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 * @return soit la motivation de l'avis, soit le commentaire
	 */
	public String getCommentaireMotiv() {
		String comm = null;
		if (avis.getMotivationAvis() != null
				&& !avis.getMotivationAvis().getLibelle().equals("")) {
			comm = avis.getMotivationAvis().getLibelle();
		}
		if (avis.getCommentaire() != null
				&& !avis.getCommentaire().equals("")) {
			comm = avis.getCommentaire();
		}
			
		return comm;
	}
	
	/**
	 * @return the short commentaire ou motivation 
	 */
	public String getShortCommentaireMotiv() {
		return Utilitaires.limitStrLength(getCommentaireMotiv(), 
				Constantes.STR_LENGTH_LIMIT);
	}
	
	/**
	 * @return true if shortComment
	 */
	public Boolean getIsShortCommentaireMotiv() {
		String comm = getCommentaireMotiv();
		if (comm != null && (comm.length() > Constantes.STR_LENGTH_LIMIT)) {
			return true;
		}
		return false;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the avis
	 */
	public Avis getAvis() {
		return avis;
	}

	/**
	 * @param avis the avis to set
	 */
	public void setAvis(final Avis avis) {
		this.avis = avis;
	}

}
