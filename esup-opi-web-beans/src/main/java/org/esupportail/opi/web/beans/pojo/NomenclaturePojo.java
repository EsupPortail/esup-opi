package org.esupportail.opi.web.beans.pojo;


import org.esupportail.opi.domain.beans.parameters.Nomenclature;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.utils.Utilitaires;

import java.io.Serializable;


/**
 * @author ylecuyer
 *
 */
public class NomenclaturePojo implements Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 7036367086442177635L;
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The Nomenclature.
	 */
	private Nomenclature nomenclature;
	
	/**
	 * The regime d'inscription.
	 */
	private RegimeInscription regimeInscription;

	/**
	 * l'utilisateur possï¿½de tous les droits.
	 */
	private boolean allRight;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 * @param nomenclature
	 */
	public NomenclaturePojo(final Nomenclature nomenclature) {
		super();
		this.nomenclature = nomenclature;
	}

	/**
	 * Constructors.
	 * @param nomenclature
	 * @param regimeInscription
	 */
	public NomenclaturePojo(final Nomenclature nomenclature, final RegimeInscription regimeInscription) {
		super();
		this.nomenclature = nomenclature;
		this.regimeInscription = regimeInscription;
	}

	/**
	 * Constructors.
	 * @param nomenclature
	 * @param regimeInscription
	 * @param allRight
	 */
	public NomenclaturePojo(final Nomenclature nomenclature, final RegimeInscription regimeInscription,
			final boolean allRight) {
		super();
		this.nomenclature = nomenclature;
		this.regimeInscription = regimeInscription;
		this.allRight = allRight;
	}

	/**
	 * Constructors.
	 */
	public NomenclaturePojo() {
		super();
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 * @return String
	 */
	public String getShortLabel() {
		return Utilitaires.limitStrLength(nomenclature.getLibelle(),
                Constantes.STR_LENGTH_LIMIT);
	}

	/**
	 * @return true if ShortComment
	 */
	public Boolean getIsShortLabel() {
		String label = nomenclature.getLibelle();
		if (label != null && (label.length() > Constantes.STR_LENGTH_LIMIT)) {
			return true;
		}
		return false;
	}
	
	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the nomenclature
	 */
	public Nomenclature getNomenclature() {
		return nomenclature;
	}

	/**
	 * @param nomenclature the nomenclature to set
	 */
	public void setNomenclature(final Nomenclature nomenclature) {
		this.nomenclature = nomenclature;
	}

	/**
	 * @return the regime d'inscription
	 */
	public RegimeInscription getRegimeInscription() {
		return regimeInscription;
	}

	/**
	 * @param regimeInscription the regimeInscription to set
	 */
	public void setRegimeInscription(final RegimeInscription regimeInscription) {
		this.regimeInscription = regimeInscription;
	}
	
	/**
	 * @return allRight
	 */
	public boolean isAllRight() {
		return allRight;
	}
	
	/**
	 * @param allRight
	 */
	public void setAllRight(final boolean allRight) {
		this.allRight = allRight;
	}
}
