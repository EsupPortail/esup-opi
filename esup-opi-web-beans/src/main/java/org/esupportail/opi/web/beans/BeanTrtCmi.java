/**
 * 
 */
package org.esupportail.opi.web.beans;

import org.esupportail.opi.domain.BusinessUtil;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.util.List;




/**
 * @author cleprous
 *
 */
public class BeanTrtCmi {

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The traitementCmi.
	 */
	private TraitementCmi traitementCmi;
	
	
	/**
	 * The Etape.
	 */
	private VersionEtapeDTO etape;
	
	/**
	 * see {@link TypeTraitement}.
	 */
	private TypeTraitement typeTraitement;
	
	
	/*
	 ******************* INIT ************************* */

	


	/**
	 * Constructors.
	 */
	public BeanTrtCmi() {
		super();
		traitementCmi = new TraitementCmi();
	}
	
	/**
	 * Constructors.
	 * @param traitementCmi 
	 */
	public BeanTrtCmi(final TraitementCmi traitementCmi, final List<TypeTraitement> list) {
		super();
		this.traitementCmi = traitementCmi;
		typeTraitement = BusinessUtil.getTypeTraitement(list, this.traitementCmi.getCodTypeTrait());
	}
	
	
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((traitementCmi == null) ? 0 : traitementCmi.hashCode());
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof BeanTrtCmi)) { return false; }
		BeanTrtCmi other = (BeanTrtCmi) obj;
		if (traitementCmi == null) {
			if (other.traitementCmi != null) { return false; }
		} else if (!traitementCmi.equals(other.traitementCmi)) { return false; }
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "BeanTrtCmi#" + hashCode() + "[etape=[" + etape
				+ "]]";
	}
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	

	/**
	 * @return the traitementCmi
	 */
	public TraitementCmi getTraitementCmi() {
		return traitementCmi;
	}


	/**
	 * @param traitementCmi the traitementCmi to set
	 */
	public void setTraitementCmi(final TraitementCmi traitementCmi) {
		this.traitementCmi = traitementCmi;
	}

		

	/**
	 * @return the etape
	 */
	public VersionEtapeDTO getEtape() {
		return etape;
	}

	/**
	 * @param etape the etape to set
	 */
	public void setEtape(final VersionEtapeDTO etape) {
		this.etape = etape;
	}

	

	/**
	 * @return the typeTraitement
	 */
	public TypeTraitement getTypeTraitement() {
		return typeTraitement;
	}

	/**
	 * @param typeTraitement the typeTraitement to set
	 */
	public void setTypeTraitement(final TypeTraitement typeTraitement) {
		this.typeTraitement = typeTraitement;
	}


	
}
