package org.esupportail.opi.domain.services;

import fj.data.Option;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.dto.CandidatDTO;

import java.io.Serializable;

/**
 * The candidat domain service interface.
 */
public interface DomainCandidatService extends Serializable {

    /**
     * <b>Eagerly</b> (in hibernate sense) fetch an {@link org.esupportail.opi.domain.beans.user.Individu} from the DB by its id
     * @param id The id (i.e 'numDossierOpi') of the {@link org.esupportail.opi.domain.beans.user.Individu}
     * @param onlyValidWishes wether the {@link org.esupportail.opi.domain.beans.user.candidature.IndVoeu}s of the {@link org.esupportail.opi.domain.beans.user.Individu} should be filtered
     *                        with regard to the validity of their Avis(cf. {@link org.esupportail.opi.domain.beans.user.candidature.Avis#validation}).
     *                        A {@link fj.data.Option#none()} value means no filtering.
     * @return The {@link org.esupportail.opi.domain.beans.user.Individu} of 'numDossierOpi' {@code id}
     */
    Option<CandidatDTO> fetchIndById(String id, Option<Boolean> onlyValidWishes);
}
