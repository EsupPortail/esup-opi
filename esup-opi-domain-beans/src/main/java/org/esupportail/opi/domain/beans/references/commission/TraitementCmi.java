/**
 * 
 */
package org.esupportail.opi.domain.beans.references.commission;

import java.util.HashSet;
import java.util.Set;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;




/**
 * @author cleprous
 * TraitementCmi : Etape managed by cmi and calendar, type treatment associated. 
 */
public class TraitementCmi extends NormeSI {

	
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -9011249966465780314L;
	
	/**
	 * Version Etape. 
	 */
	private VersionEtpOpi versionEtpOpi;
	
	/**
	 * The diplome code.
	 */
	private String codDip;
	
	/**
	 * The version diplome code.
	 */
	private Integer codVrsDip;
	
	/**
	 * The code TypeTraitement.
	 */
	private String codTypeTrait;
	
	/**
	 * The Commission.
	 */
	private Commission commission;
	
	/**
	 * see {@link Selection}.
	 */
	private Selection selection;
	
	/**
	 * The list of link with the capagne and the voeux.
	 */
	private Set<LinkTrtCmiCamp> linkTrtCmiCamp;
	
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public TraitementCmi() {
		super();
		linkTrtCmiCamp = new HashSet<LinkTrtCmiCamp>();
	}
	
	/**
	 * Constructors.
	 * @param codCge 
	 * @param vet
	 */
	public TraitementCmi(final String codCge, 
					final VersionEtapeDTO vet) {
		super();
		versionEtpOpi = new VersionEtpOpi(vet);
		versionEtpOpi.setCodCge(codCge);
		linkTrtCmiCamp = new HashSet<LinkTrtCmiCamp>();
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((versionEtpOpi == null) ? 0 : versionEtpOpi.hashCode());
		return result;
	}
	
	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { 
			return false;
		}
		if (!(obj instanceof TraitementCmi)) { return false; }
		TraitementCmi other = (TraitementCmi) obj;
		if (versionEtpOpi == null) {
			if (other.getVersionEtpOpi()!= null) {	return false; }
		} else if (!versionEtpOpi.equals(other.getVersionEtpOpi())) { return false; }
		return true;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		String s = "TraitementCmi#" + hashCode() 
		+ "[versionEtpOpi=[" + versionEtpOpi + "],[codTypeTrait=[" + codTypeTrait + "]";
		
		if (commission != null) {
			s += "[commission.id=[" + commission.getId() + "]";
		}
		s += ",  [" + super.toString() + "]]";
		return s;
	}
	
	/**
	 * @return the libelle
	 */
//	@Override
//	public String getLibelle() {
//		return versionEtpOpi.getCodEtp() + "-" + versionEtpOpi.getCodVrsVet();
//	}
	
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the commission
	 */
	public Commission getCommission() {
		return commission;
	}

	/**
	 * @param commission the commission to set
	 */
	public void setCommission(final Commission commission) {
		this.commission = commission;
	}

	/**
	 * @return the versionEtpOpi
	 */
	public VersionEtpOpi getVersionEtpOpi() {
		return versionEtpOpi;
	}

	/**
	 * @param versionEtpOpi the versionEtpOpi to set
	 */
	public void setVersionEtpOpi(final VersionEtpOpi versionEtpOpi) {
		this.versionEtpOpi = versionEtpOpi;
	}

	/**
	 * @return the codDip
	 */
	public String getCodDip() {
		return codDip;
	}

	/**
	 * @param codDip the codDip to set
	 */
	public void setCodDip(final String codDip) {
		this.codDip = codDip;
	}

	/**
	 * @return the codVrsDip
	 */
	public Integer getCodVrsDip() {
		return codVrsDip;
	}

	/**
	 * @param codVrsDip the codVrsDip to set
	 */
	public void setCodVrsDip(final Integer codVrsDip) {
		this.codVrsDip = codVrsDip;
	}


	/**
	 * @return the codTypeTrait
	 */
	public String getCodTypeTrait() {
		return codTypeTrait;
	}

	/**
	 * @param codTypeTrait the codTypeTrait to set
	 */
	public void setCodTypeTrait(final String codTypeTrait) {
		this.codTypeTrait = codTypeTrait;
	}


	/**
	 * @return the selection
	 */
	public Selection getSelection() {
		return selection;
	}

	/**
	 * @param selection the selection to set
	 */
	public void setSelection(final Selection selection) {
		this.selection = selection;
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
