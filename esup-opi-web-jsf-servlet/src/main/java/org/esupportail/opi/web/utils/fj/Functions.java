package org.esupportail.opi.web.utils.fj;

import fj.F;
import fj.F2;
import fj.Unit;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;

import java.util.Map;

import static fj.Unit.unit;
import static fj.function.Booleans.not;
import static org.esupportail.opi.web.utils.fj.Predicates.indAvisValidationIs;

public class Functions {

    public static <K, V> F<V, F2<K, Map<K, V>, Unit>> put_() {
        return new F<V, F2<K, Map<K, V>, Unit>>() {
            public F2<K, Map<K, V>, Unit> f(final V v) {
                return new F2<K, Map<K, V>, Unit>() {
                    public Unit f(K k, Map<K, V> map) {
                        map.put(k, v);
                        return unit();
                    }
                };
            }
        };
    }

    public static <A, B, C> F<F2<A, B, C>, C> apply2(final A a, final B b) {
        return new F<F2<A, B, C>, C>() {
            public C f(F2<A, B, C> f2) {
                return f2.f(a, b);
            }
        };
    }

    /**
     * si onlyValidate = true, on enlève les voeux non validés
     * et inversement
     * (see FunctionsTest.testRemoveNotValidatedVoeu())
     *
     * @param onlyValidate param to decide
     * @return The given {@link IndividuPojo} with its {@link IndVoeuPojo} filtered
     */
    public static F<IndividuPojo, IndividuPojo> filterVoeuxByAvisValidation(final Boolean onlyValidate) {
        return new F<IndividuPojo, IndividuPojo>() {
            public IndividuPojo f(IndividuPojo ip) {
                ip.setIndVoeuxPojo(ip.getIndVoeuxPojo().filter(indAvisValidationIs(onlyValidate)));
                return ip;
            }
        };
    }

    /**
     * Decode a {@link fj.data.Stream} of{@link org.esupportail.opi.web.beans.parameters.RegimeInscription}
     *
     * @return the resulting stream of Code
     */
    public static F<RegimeInscription, Integer> getRICode() {
        return new F<RegimeInscription, Integer>() {
            public Integer f(RegimeInscription ri) {
                return ri.getCode();
            }
        };
    }
}
