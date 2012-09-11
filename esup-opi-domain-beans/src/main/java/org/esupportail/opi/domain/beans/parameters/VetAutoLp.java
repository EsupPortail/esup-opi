package org.esupportail.opi.domain.beans.parameters;



import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.references.commission.Commission;



/**
 * 
 * @author Gomez
 *
 */
public class VetAutoLp extends NormeSI {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 8042444084174876385L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The Etape code.
	 */
	private String codEtp;
	
	/**
	 * The Version Etape code.
	 */
	private Integer codVrsVet;
	
	/**
	 * The auto main list.
	 */
	private AutoListPrincipale autoListPrincipale;
	
	/**
	 * commission.
	 */
	private Commission commission;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructeur vide pour hibernate.
	 */
	public VetAutoLp() {
		//Constructeur vide pour hibernate.
	}
	
	/**
	 * Constructors.
	 * @param codEtp
	 * @param codVrsVet
	 * @param autoListPrincipale
	 * @param commission
	 */
	public VetAutoLp(final String codEtp,
			final Integer codVrsVet,
			final AutoListPrincipale autoListPrincipale,
			final Commission commission) {
		this.codEtp = codEtp;
		this.codVrsVet = codVrsVet;
		this.autoListPrincipale = autoListPrincipale;
		this.commission = commission;
	}
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final VetAutoLp other = (VetAutoLp) obj;
		if (!this.getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the codEtp
	 */
	public String getCodEtp() {
		return codEtp;
	}
	
	/**
	 * @param codEtp the codEtp to set
	 */
	public void setCodEtp(final String codEtp) {
		this.codEtp = codEtp;
	}
	
	/**
	 * @return the codVrsVet
	 */
	public Integer getCodVrsVet() {
		return codVrsVet;
	}
	
	/**
	 * @param codVrsVet the codVrsVet to set
	 */
	public void setCodVrsVet(final Integer codVrsVet) {
		this.codVrsVet = codVrsVet;
	}
	
	/**
	 * 
	 * @return autoListPrincipale
	 */
	public AutoListPrincipale getAutoListPrincipale() {
		return autoListPrincipale;
	}
	
	/**
	 * 
	 * @param autoListPrincipale
	 */
	public void setAutoListPrincipale(AutoListPrincipale autoListPrincipale) {
		this.autoListPrincipale = autoListPrincipale;
	}

	/**
	 * 
	 * @return commission
	 */
	public Commission getCommission() {
		return commission;
	}
	
	/**
	 * 
	 * @param commission
	 */
	public void setCommission(final Commission commission) {
		this.commission = commission;
	}
}
