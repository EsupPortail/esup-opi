package org.esupportail.opi.domain;

import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.util.List;
import java.util.Set;

/**
 * @author ylecuyer
 * Parcours de listes mises en cache pour renvoyer un element
 */
public class BusinessUtil {

	/**
	 *  Return the TypeConvocation with the code in the list. 
	 *  otherwise return null.
	 */
	public static TypeConvocation getTypeConvocation(final List<TypeConvocation> list, final String code) {
		if (list != null) {
			for (TypeConvocation t : list) {
				if (t.getCode().equals(code)) {
					return t;
				}
			}
		}
		return null;
	}

	/**
	 * Renvoie la commission dont fait partie la vet.  
	 */
	public static Commission getCmiForVetDTO(final Set<Commission> cmi, 
			final VersionEtapeDTO vet) {
		for (Commission c : cmi) {
			if (c.getTraitementCmi() != null) {
				for (TraitementCmi trt : c.getTraitementCmi()) {
					if (trt.getVersionEtpOpi().getCodEtp().equals(vet.getCodEtp())
							&& trt.getVersionEtpOpi().getCodVrsVet()
							.equals(vet.getCodVrsVet())) {
						return c;
					} 
				}
			}
		}
		return null;
	}
}
