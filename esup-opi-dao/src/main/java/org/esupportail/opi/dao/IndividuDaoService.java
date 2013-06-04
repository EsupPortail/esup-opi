package org.esupportail.opi.dao;

import fj.P2;
import fj.data.Option;
import fj.data.Stream;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.utils.primefaces.PFFilters;

import java.util.Date;
import java.util.List;
import java.util.Set;

public interface IndividuDaoService {

    /**
     * Retrieve a slice of {@link Individu}
     *
     *
     * @param pfFilters
     * @param wishCreation
     * @return
     */
    P2<Long, Stream<Individu>> sliceOfInd(PFFilters pfFilters,
                                          List<TypeDecision> typesDec,
                                          Option<Boolean> validWish,
                                          Option<Boolean> treatedWish,
                                          Option<Date> wishCreation,
                                          Option<String> codeTypeTrtmt,
                                          Option<Set<TraitementCmi>> trtCmis,
                                          Set<Integer> listCodesRI);

    /**
     * Return the individuals managed by commission.
     *
     */
    public Stream<Individu> getIndividus(final Commission commission,
                                         final Boolean validate,
                                         final Set<Integer> listeRICodes);
}
