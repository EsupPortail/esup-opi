package org.esupportail.opi.dao;

import fj.P2;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.utils.primefaces.PFFilters;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IndividuDaoService {

    /**
     * Retrieve a slice of {@link Individu}
     *
     *
     *
     *
     *
     * @param pfFilters
     * @param wishCreation
     * @param typeTrtmts
     * @return
     */
    P2<Long, Array<Individu>> sliceOfInds(PFFilters pfFilters,
                                          List<TypeDecision> typesDec,
                                          Option<Boolean> validWish,
                                          Option<Boolean> treatedWish,
                                          Option<Date> wishCreation,
                                          Collection<TypeTraitement> typeTrtmts,
                                          Option<Set<TraitementCmi>> trtCmis,
                                          Set<Integer> listCodesRI);

    /**
     * Return the ids of the {@link Individu}s whom {@link IndVoeu}s are managed by {@code commission}
     *
     * @param commission The {@link Commission} managing the {@link IndVoeu}s
     * @param validate Whether the {@link IndVoeu}s are valid and in service
     * @param listeRICodes The registration schemes of the {@link Campagne} the {@link IndVoeu}s belong to
     */
    List<String> getIndsIds(final Commission commission, final Option<Boolean> validate, final Set<Integer> listeRICodes);

    /**
     * <b>Eagerly</b> (in hibernate sense) fetch an {@link Individu} from the DB by its id
     * @param id The id (i.e 'numDossierOpi') of the {@link Individu}
     * @param onlyValidWishes wether the {@link IndVoeu}s of the {@link Individu} should be filtered
     *                        with regard to the validity of their Avis(cf. {@link Avis#validation}).
     *                        A {@link Option#none()} value means no filtering.
     * @return The {@link Individu} of 'numDossierOpi' {@code id}
     */
    Individu fetchIndById(String id, Option<Boolean> onlyValidWishes);
}
