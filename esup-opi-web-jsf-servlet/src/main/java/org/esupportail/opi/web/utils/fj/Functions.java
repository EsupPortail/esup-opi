package org.esupportail.opi.web.utils.fj;

import fj.F;
import fj.F2;
import fj.F3;
import fj.Unit;

import java.util.Map;

import static fj.Unit.unit;

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
}
