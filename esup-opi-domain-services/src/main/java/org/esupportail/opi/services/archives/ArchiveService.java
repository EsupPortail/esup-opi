package org.esupportail.opi.services.archives;

import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;

/**
 * @author ylecuyer
 * The archive methods.
 *
 */
public interface ArchiveService {

	/**
	 * Archive the individu and delete all the missing pieces.
	 * @param ind
	 */
	void archiveIndividu(Individu ind);
	
	/**
	 * Test if the treatement is open for the new campagne.
	 * If ok, create the corresponding linkTrtCmiCamp
	 * @param trtCmi
	 * @param camp
	 */
	void addTraitementCmiToNewCamp(TraitementCmi trtCmi, Campagne camp);
	
	/**
	 * Purge les tables IND_FORMULAIRES, RDV_INDIVIDU_DATE et MISSING_PIECE.
	 * @param camp
	 */
	void purgeTablesCamp(Campagne camp);
}
