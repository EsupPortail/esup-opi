/**
 * 
 */
package org.esupportail.opi.domain.beans;

import java.io.Serializable;
import java.util.Date;

/**
 * @author cleprous
 *
 */
public abstract class NormeSI implements Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1L;
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * L'identifiant de l'objet gerer par hibernate.
	 * Default value : 0
	 */
	private Integer id;
	
	/**
	 * Le libelle de l'objet.
	 */
	private String libelle;
	
	/**
	 * The short label.
	 */
	private String shortLabel;
	
	/**
	 * La date de la cretion de l'enrgistrement dans la base.
	 */
	private Date dateCreaEnr;
	
	/**
	 * Le date de modif dans la base.
	 */
	private Date dateModifEnr;
	
	/**
	 * Default value = true.
	 */
	private Boolean temoinEnService;
	
	/**
	 * code de la derniere personne e avoir modifier l'objet.
	 */
	private String codUserToUpdate;
	
	/**
	 * cod de la personne qui a cree l'objet.
	 */
	private String codUserToCreate;
	
	/*
	 ******************* INIT ************************* */
	

	/**
	 * Constructor.
	 */
	public NormeSI() {
		super();
		id = 0;
		temoinEnService = true;
	}
	

	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		return 0;
	}


	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		//Proxy hib
		if (!(obj instanceof NormeSI)) { return false; }
		NormeSI other = (NormeSI) obj;
		if (!id.equals(other.id)) { return false; }
		return true;
	}
	
	
	
	/**
	 * Return n with attributes of this.
	 * @param n
	 * @return NormeSI
	 */
	protected NormeSI clone(final NormeSI n) {
		NormeSI norme = n;
		n.setId(this.id);
		n.setLibelle(libelle);
		n.setShortLabel(shortLabel);
		n.setCodUserToCreate(codUserToCreate);
		n.setCodUserToUpdate(codUserToUpdate);
		n.setDateCreaEnr(dateCreaEnr);
		n.setDateModifEnr(dateModifEnr);
		n.setTemoinEnService(temoinEnService);
		
		
		return norme; 
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NormeSI#" + hashCode() + "[id=[" + id + "], libelle=[" + libelle
		+ "], shortLabel=[" + shortLabel + "], temoinEnService=[" + temoinEnService
		+ "], dateCreaEnr=[" + dateCreaEnr + "]"
		+ ", dateModifEnr=[" + dateModifEnr + "], codUserToCreate=[" + codUserToCreate
		+ "], codUserToUpdate=[" + codUserToUpdate + "]]";
	}
	
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	








	/**
	 * @return the id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(final Integer id) {
		this.id = id;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}

	/**
	 * @return the shortLabel
	 */
	public String getShortLabel() {
		return shortLabel;
	}

	/**
	 * @param shortLabel the shortLabel to set
	 */
	public void setShortLabel(final String shortLabel) {
		this.shortLabel = shortLabel;
	}

	/**
	 * @return the dateCreaEnr
	 */
	public Date getDateCreaEnr() {
		return dateCreaEnr;
	}

	/**
	 * @param dateCreaEnr the dateCreaEnr to set
	 */
	public void setDateCreaEnr(final Date dateCreaEnr) {
		this.dateCreaEnr = dateCreaEnr;
	}

	/**
	 * @return the dateModifEnr
	 */
	public Date getDateModifEnr() {
		return dateModifEnr;
	}

	/**
	 * @param dateModifEnr the dateModifEnr to set
	 */
	public void setDateModifEnr(final Date dateModifEnr) {
		this.dateModifEnr = dateModifEnr;
	}

	/**
	 * @return the codUserToUpdate
	 */
	public String getCodUserToUpdate() {
		return codUserToUpdate;
	}

	/**
	 * @param codUserToUpdate the codUserToUpdate to set
	 */
	public void setCodUserToUpdate(final String codUserToUpdate) {
		this.codUserToUpdate = codUserToUpdate;
	}

	/**
	 * @return the codUserToCreate
	 */
	public String getCodUserToCreate() {
		return codUserToCreate;
	}

	/**
	 * @param codUserToCreate the codUserToCreate to set
	 */
	public void setCodUserToCreate(final String codUserToCreate) {
		this.codUserToCreate = codUserToCreate;
	}


	/**
	 * @return the temoinEnService
	 */
	public Boolean getTemoinEnService() {
		return temoinEnService;
	}


	/**
	 * @param temoinEnService the temoinEnService to set
	 */
	public void setTemoinEnService(final Boolean temoinEnService) {
		this.temoinEnService = temoinEnService;
	}
	

	

}
