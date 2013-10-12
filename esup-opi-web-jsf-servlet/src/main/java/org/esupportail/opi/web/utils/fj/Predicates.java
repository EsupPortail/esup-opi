package org.esupportail.opi.web.utils.fj;

import fj.Equal;
import fj.F;
import fj.F2;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;

import static fj.Equal.booleanEqual;
import static fj.Equal.optionEqual;
import static fj.Function.curry;
import static fj.data.Option.fromNull;
import static fj.data.Option.some;

public final class Predicates {

    private Predicates() { throw new UnsupportedOperationException(); }

    /**
     * Tests an {@link IndVoeuPojo}'s {@link TypeTraitement} for non-equality
     * against {@code typeTraitement}
     *
     * @return true if non-equality is verified, false otherwise
     */
    public static F<IndVoeuPojo, Boolean> typeTrtEquals(final TypeTraitement typeTraitement) {
        return new F<IndVoeuPojo, Boolean>() {
            public Boolean f(final IndVoeuPojo indVoeuPojo) {
                return indVoeuPojo.getTypeTraitement().equals(typeTraitement);
            }
        };
    }

    /**
     * Tests an {@link IndVoeuPojo#avisEnService#validation} for equality against {@code onlyValidate}
     *
     * @return true if equality is verified, false otherwise
     */
    public static F<IndVoeuPojo, Boolean> indAvisValidationIs(final Boolean onlyValidate) {
        return new F<IndVoeuPojo, Boolean>() {
            public Boolean f(final IndVoeuPojo indVoeuPojo) {
                return fromNull(onlyValidate)
                        .apply(fromNull(indVoeuPojo.getAvisEnService())
                                .map(curry(new F2<Avis, Boolean, Boolean>() {
                                    public Boolean f(Avis avis, Boolean validate) {
                                        return optionEqual(booleanEqual).eq(fromNull(avis.getValidation()), some(validate));
                                    }
                                })))
                        .orSome(false);
            }
        };
    }

    /**
     * Tests wether an {@link IndividuPojo#indVoeuxPojo} is NOT empty
     */
    public static F<IndividuPojo, Boolean> indWithVoeux() {
        return new F<IndividuPojo, Boolean>() {
            public Boolean f(final IndividuPojo ip) {
                return !ip.getIndVoeuxPojo().isEmpty();
            }
        };
    }
}
