/**
 * 
 */
package org.esupportail.opi.domain.beans.etat;

import java.io.Serializable;

import org.esupportail.commons.services.i18n.I18nService;


/**
 * @author cleprous
 *
 */
public abstract class Etat<T> implements Serializable {

	/**
	 * the serialization id.
	 */
	private static final long serialVersionUID = -5547455038186843477L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * state label in bundles.
	 */
	private String label;
	
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public Etat() {
		super();
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
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
		Etat other = (Etat) obj;
		if (label == null) {
			if (other.label != null) { return false; }
		} else if (!label.equals(other.label)) { return false; }
		return true;
	}

	/*
	 ******************* METHODS ********************** */


	/**
	 * Return an instance of Etat by stateLabel.
	 * @param stateLabel
	 * @param i18nService
	 * @return Etat
	 */
	public static Etat instanceState(final String stateLabel, final I18nService i18nService) {
		if (stateLabel != null) {
			if (stateLabel.equals(EtatComplet.I18N_STATE_COMPLET)) {
				return new EtatComplet(i18nService);
			} else if (stateLabel.equals(EtatInComplet.I18N_STATE_INCOMPLET)) {
				return new EtatInComplet(i18nService);
			} else if (stateLabel.equals(EtatArrive.I18N_STATE)) {
				return new EtatArrive(i18nService);
			} else if (stateLabel.equals(EtatNonArrive.I18N_STATE)) {
				return new EtatNonArrive(i18nService);
			} else if (stateLabel.equals(EtatNonRenseigne.I18N_STATE_NON_RENSEIGNE)) {
				return new EtatNonRenseigne(i18nService);
			} else if (stateLabel.equals(EtatConfirme.I18N_STATE)) {
				return new EtatConfirme(i18nService);
			} else if (stateLabel.equals(EtatDesiste.I18N_STATE)) {
				return new EtatDesiste(i18nService);
			} else if (stateLabel.equals(EtatArriveComplet.I18N_STATE)) {
				return new EtatArriveComplet(i18nService);
			} else if (stateLabel.equals(EtatArriveIncomplet.I18N_STATE)) {
				return new EtatArriveIncomplet(i18nService);
			} else if (stateLabel.equals(EtatArriveIncomplet.I18N_STATE)) {
				return new EtatArriveIncomplet(i18nService);
			} else if (stateLabel.equals(EtatNull.I18N_STATE)) {
				return new EtatNull(i18nService);
			}
		}
		return null;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Etat#" + hashCode() + "[class " + this.getClass() + "], [label " + label + "]";
	}
	
	/**
	 * 
	 * @return boolean
	 */
	@Deprecated //depuis 29/05/2009
	public boolean isConfirmerEtu() {
		if (this instanceof EtatArriveComplet) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	@Deprecated //depuis 29/05/2009
	public boolean isValiderEtu() {
		if (this instanceof EtatConfirme) {
			return true;
		}
		return false;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	@Deprecated //depuis 29/05/2009
	public boolean isRefuserEtu() {
		if (this instanceof EtatDesiste) {
			return true;
		}
		return false;
	}
	
	/**
	 * THe key i18n bundle for this instance State.
	 * @return String
	 */
	public abstract String getCodeLabel();
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @param label
	 */
	protected void setLabel(final String label) {
		this.label = label;
	}
	
	/**
	 * @return String
	 */
	public String getLabel() {
		return label;
	}
}
