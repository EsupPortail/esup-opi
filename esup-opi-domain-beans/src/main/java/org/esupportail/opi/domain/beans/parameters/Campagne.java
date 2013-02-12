/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;


/**
 * @author ylecuyer
 *
 */
public class Campagne extends Nomenclature {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -4488846342380474281L;

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Code de l'année universitaire de la campagne.
	 */
	private String codAnu;
	
	/**
	 * Code Regime d'inscription.
	 * Default value 0 --> formation Initiale.
	 * 1 --> Formation Continue
	 */
	private int codeRI;
	
	/**
	 * true if this campagne has been archived.
	 */
	private Boolean isArchived;
	
	/**
	 * Date de début de la campagne.
	 */
	private Date dateDebCamp;
	
	/**
	 * Date de fin de la campagne.
	 */
	private Date dateFinCamp;
	
	/**
	 * The list of link with the capagne and the voeux.
	 */
	private Set<LinkTrtCmiCamp> linkTrtCmiCamp;

	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public Campagne() {
		super();
		codeRI = 0;
		// à la création, une campagne peut ne pas être en service
		setTemoinEnService(false);
		isArchived = false;
		linkTrtCmiCamp = new HashSet<LinkTrtCmiCamp>();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Campagne#" + hashCode() + ",  [" + super.toString() + "]]";
	}
	

	/** 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Campagne clone() {
		Campagne t = new Campagne();
		t = (Campagne) super.clone(t);
		t.setCodeRI(codeRI);
		t.setIsArchived(isArchived);
		t.setDateDebCamp(dateDebCamp);
		t.setDateFinCamp(dateFinCamp);
		t.setLinkTrtCmiCamp(linkTrtCmiCamp);
		return t; 
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the codAnu
	 */
	public String getCodAnu() {
		return codAnu;
	}

	/**
	 * @param codAnu the codAnu to set
	 */
	public void setCodAnu(final String codAnu) {
		this.codAnu = codAnu;
	}

	/**
	 * @return the codeRI
	 */
	public int getCodeRI() {
		return codeRI;
	}

	/**
	 * @param codeRI the codeRI to set
	 */
	public void setCodeRI(final int codeRI) {
		this.codeRI = codeRI;
	}

	/**
	 * @return the isArchived
	 */
	public Boolean getIsArchived() {
		return isArchived;
	}

	/**
	 * @param isArchived the isArchived to set
	 */
	public void setIsArchived(final Boolean isArchived) {
		this.isArchived = isArchived;
	}

	/**
	 * @return the dateDebCamp
	 */
	public Date getDateDebCamp() {
		return dateDebCamp;
	}

	/**
	 * @param dateDebCamp the dateDebCamp to set
	 */
	public void setDateDebCamp(final Date dateDebCamp) {
		this.dateDebCamp = dateDebCamp;
	}

	/**
	 * @return the dateFinCamp
	 */
	public Date getDateFinCamp() {
		return dateFinCamp;
	}

	/**
	 * @param dateFinCamp the dateFinCamp to set
	 */
	public void setDateFinCamp(final Date dateFinCamp) {
		this.dateFinCamp = dateFinCamp;
	}

	/**
	 * @return the linkTrtCmiCamp
	 */
	public Set<LinkTrtCmiCamp> getLinkTrtCmiCamp() {
		return linkTrtCmiCamp;
	}

	/**
	 * @param linkTrtCmiCamp the linkTrtCmiCamp to set
	 */
	public void setLinkTrtCmiCamp(final Set<LinkTrtCmiCamp> linkTrtCmiCamp) {
		this.linkTrtCmiCamp = linkTrtCmiCamp;
	}

}
