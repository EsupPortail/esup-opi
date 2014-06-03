/**
 * 
 */
package org.esupportail.opi.web.beans;

import fj.data.Option;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

public class BeanTrtCmi {
	private TraitementCmi traitementCmi;
	
	private VersionEtapeDTO etape;
	
	private Option<TypeTraitement> typeTraitement;

	public BeanTrtCmi() {
		traitementCmi = new TraitementCmi();
	}

	public BeanTrtCmi(TraitementCmi traitementCmi, Option<TypeTraitement> typeTraitement) {
		this.traitementCmi = traitementCmi;
		this.typeTraitement = typeTraitement;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((traitementCmi == null) ? 0 : traitementCmi.hashCode());
		return result;
	}

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

	@Override
	public String toString() {
		return "BeanTrtCmi#" + hashCode() + "[etape=[" + etape
				+ "]]";
	}
	
	public TraitementCmi getTraitementCmi() {
		return traitementCmi;
	}

	public void setTraitementCmi(final TraitementCmi traitementCmi) {
		this.traitementCmi = traitementCmi;
	}

	public VersionEtapeDTO getEtape() {
		return etape;
	}

	public void setEtape(final VersionEtapeDTO etape) {
		this.etape = etape;
	}

	public Option<TypeTraitement> getTypeTraitement() {
		return typeTraitement;
	}

	public void setTypeTraitement(final Option<TypeTraitement> typeTraitement) {
		this.typeTraitement = typeTraitement;
	}
}
