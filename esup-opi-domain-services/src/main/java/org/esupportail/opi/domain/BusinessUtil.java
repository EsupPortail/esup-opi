package org.esupportail.opi.domain;

import java.util.List;
import java.util.Set;

import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.parameters.ValidationAcquis;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author ylecuyer
 * Parcours de listes mises en cache pour renvoyer un element
 */
public class BusinessUtil {

//	private static final Logger LOGGER = new LoggerImpl(Utilitaires.class);	

	/**
	 * Constructors.
	 */
	public BusinessUtil() {
		super();
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 *  Return the TypeTraitment with the code in the list. 
	 *  otherwise return null.
	 * @param list
	 * @param code
	 * @return TypeTraitement
	 */
	public static TypeTraitement getTypeTraitement(final List<TypeTraitement> list, final String code) {
		if (list != null) {
			for (TypeTraitement t : list) {
				if (t.getCode().equals(code)) {
					return t;
				}
			}
		}
		return null;
	}
	
	/**
	 *  Return the code of VA traitement.
	 * @param list
	 * @return String
	 */
	public static String getCodeVATypeTraitement(final List<TypeTraitement> list) {
		if (list != null) {
			for (TypeTraitement t : list) {
				if (t instanceof ValidationAcquis) {
					return t.getCode();
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 *  Return the TypeConvocation with the code in the list. 
	 *  otherwise return null.
	 * @param list
	 * @param code
	 * @return TypeConvocation
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
	 * @param cmi
	 * @param vet
	 * @return Commission
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
