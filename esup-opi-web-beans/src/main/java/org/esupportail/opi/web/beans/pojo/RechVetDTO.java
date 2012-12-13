package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.parameters.Campagne;

import java.util.List;

/**
 * @author root
 *
 */
public class RechVetDTO {
	
	/**
	 * the code vet
	 */
	private String codeVet;
	
	/**
	 * Liste des campagnes en service.
	 */
	private List<Campagne> campagnes;
	
	/**
	 * The etape code.
	 */
	private String codEtp;
	
	/**
	 * The centre gestion code.
	 */
	private String codCge;
	
	/**
	 * The code de l'annÃ©e universitaire.
	 */
	private String codAnu;
	
	/**
	 * The code de la commission.
	 */
	private Integer idCmi;
	
	/**
	 * The version Etape label.
	 */
	private String libWebVet;
	

	/**
	 *  the libelle of the vet
	 */
	private String libVet;
	
	/**
	 * @return the campagnes
	 */
	public List<Campagne> getCampagnes() {
		return campagnes;
	}

	/**
	 * @param campagnes
	 */
	public void setCampagnes(List<Campagne> campagnes) {
		this.campagnes = campagnes;
	}

	/**
	 * @return code etape
	 */
	public String getCodEtp() {
		return codEtp;
	}

	/**
	 * @param codEtp
	 */
	public void setCodEtp(String codEtp) {
		this.codEtp = codEtp;
	}

	/**
	 * @return code cge
	 */
	public String getCodCge() {
		return codCge;
	}

	/**
	 * @param codCge
	 */
	public void setCodCge(String codCge) {
		this.codCge = codCge;
	}

	/**
	 * @return cod Anu
	 */
	public String getCodAnu() {
		return codAnu;
	}

	/**
	 * @param codAnu
	 */
	public void setCodAnu(String codAnu) {
		this.codAnu = codAnu;
	}

	/**
	 * @return lib web vet
	 */
	public String getLibWebVet() {
		return libWebVet;
	}

	/**
	 * @param libWebVet
	 */
	public void setLibWebVet(String libWebVet) {
		this.libWebVet = libWebVet;
	}

	/**
	 * @return code vet
	 */
	public String getCodeVet() {
		return codeVet;
	}

	/**
	 * @param codeVet
	 */
	public void setCodeVet(String codeVet) {
		this.codeVet = codeVet;
	}

	/**
	 * @return lib vet
	 */
	public String getLibVet() {
		return libVet;
	}

	/**
	 * @param libVet
	 */
	public void setLibVet(String libVet) {
		this.libVet = libVet;
	}

	/**
	 * @return idCmi
	 */
	public Integer getIdCmi() {
		return idCmi;
	}

	/**
	 * @param idCmi
	 */
	public void setIdCmi(Integer idCmi) {
		this.idCmi = idCmi;
	}
	
}
