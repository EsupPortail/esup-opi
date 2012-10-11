/**
*CRI - Universite de Rennes1 - 57SI-CIES - 2007
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;

import org.esupportail.opi.domain.BusinessCacheService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.AdresseFix;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.wssi.services.remote.Pays;
import org.springframework.util.StringUtils;





/**
 * @author leproust cedric
 *
 */
public class AdressePojo {

	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The adresse.
	 */
	private Adresse adresse;
	
	/**
	 * The Pays.
	 */
	private Pays pays;
	
	/**
	 * The Pays.
	 */
	private CommuneDTO commune;
	
	/**
	 * True if address has a cedex.
	 * Default value : false.
	 */
	private Boolean isCedex; 
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public AdressePojo() {
		super();
	}

	/**
	 * Constructor.
	 */
	public AdressePojo(final Adresse address) {
		super();
		adresse = address.clone();
		adresse.setCodPays(Constantes.CODEFRANCE);
		isCedex = false;
	}
	
	/**
	 * Constructor.
	 */
	public AdressePojo(final Adresse address, final DomainApoService apoService) {
		super();
		adresse = address.clone();
		if (!StringUtils.hasText(address.getCodPays())) {
			adresse.setCodPays(Constantes.CODEFRANCE);
		}
		isCedex = false;
		pays = apoService.getPays(adresse.getCodPays());
		commune = apoService.getCommune(adresse.getCodCommune(), adresse.getCodBdi());
		if (adresse.getCedex() != null) {
			isCedex = true;
		}		
	}
	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (adresse != null) {
			result += adresse.hashCode();
		}
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof AdressePojo)) { return false; }
		final AdressePojo other = (AdressePojo) obj;
		if (adresse == null) {
			if (other.adresse != null) { return false; }
		} else if (!adresse.equals(other.adresse)) { return false; }
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AdressePojo#" + hashCode() + "[adresse" + adresse + "]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the pays
	 */
	public Pays getPays() {
		return pays;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(final Pays pays) {
		this.pays = pays;
	}

	/**
	 * @return the adresse
	 */
	public Adresse getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(final Adresse adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the commune
	 */
	public CommuneDTO getCommune() {
		return commune;
	}

	/**
	 * @param commune the commune to set
	 */
	public void setCommune(final CommuneDTO commune) {
		this.commune = commune;
	}

	/**
	 * @return the isCedex
	 */
	public Boolean getIsCedex() {
		return isCedex;
	}

	/**
	 * @param isCedex the isCedex to set
	 */
	public void setIsCedex(final Boolean isCedex) {
		this.isCedex = isCedex;
	}


}
