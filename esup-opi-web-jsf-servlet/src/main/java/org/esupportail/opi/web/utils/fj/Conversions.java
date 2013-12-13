package org.esupportail.opi.web.utils.fj;

import fj.*;
import fj.control.parallel.Promise;
import fj.data.Array;
import fj.data.Stream;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static fj.Function.curry;
import static fj.P.p;
import static fj.data.Array.array;
import static fj.data.Array.iterableArray;
import static fj.data.Stream.iterableStream;
import static org.esupportail.opi.web.beans.utils.Utilitaires.getRecupCalendarRdv;
import static org.esupportail.opi.web.utils.fj.parallel.ParallelModule.parMod;

public class Conversions {

    private Conversions() {
        super();
    }

    public static <T> F<Set<T>, Stream<T>> setToStream_() {
        return new F<Set<T>, Stream<T>>() {
            public Stream<T> f(Set<T> set) {
                return iterableStream(set);
            }
        };
    }

    public static <T> F<Stream<T>, Set<T>> streamToSet_() {
        return new F<Stream<T>, Set<T>>() {
            public Set<T> f(Stream<T> ts) {
                return new HashSet<>(ts.toCollection());
            }
        };
    }

    public static <T> F<Set<T>, Array<T>> setToArray_() {
        return new F<Set<T>, Array<T>>() {
            @SuppressWarnings("unchecked")
            public Array<T> f(Set<T> ts) {
                return array(ts.toArray((T[]) new Object[ts.size()]));
            }
        };
    }

    public static <T> F<Array<T>, Set<T>> arrayToSet_() {
        return new F<Array<T>, Set<T>>() {
            public Set<T> f(Array<T> ts) {
                return new HashSet<>(ts.toCollection());
            }
        };
    }

    static final F3<DomainApoService, String, Integer, VersionEtapeDTO> getVet =
            new F3<DomainApoService, String, Integer, VersionEtapeDTO>() {
                public VersionEtapeDTO f(DomainApoService apo, String codeEtp, Integer codeVrsVet) {
                    return apo.getVersionEtape(codeEtp, codeVrsVet);
                }
            };

    static final F2<ParameterService, VersionEtpOpi, Set<CalendarIns>> getCals =
            new F2<ParameterService, VersionEtpOpi, Set<CalendarIns>>() {
                public Set<CalendarIns> f(ParameterService paramServ, VersionEtpOpi vet) {
                    return paramServ.getCalendars(vet);
                }
            };

    static final F<ParameterService, List<CalendarRDV>> getCalsRdv = new F<ParameterService, List<CalendarRDV>>() {
        public List<CalendarRDV> f(ParameterService paramServ) {
            return paramServ.getCalendarRdv();
        }
    };

    static final F5<IndVoeu, List<CalendarRDV>, TypeTraitement, Set<CalendarIns>, VersionEtapeDTO, IndVoeuPojo> buildIndVoeuPojo =
            new F5<IndVoeu, List<CalendarRDV>, TypeTraitement, Set<CalendarIns>, VersionEtapeDTO, IndVoeuPojo>() {
                public IndVoeuPojo f(IndVoeu indVoeu, List<CalendarRDV> calsRdv, TypeTraitement typeTrt, Set<CalendarIns> cals, VersionEtapeDTO vet) {
                    return new IndVoeuPojo(
                            indVoeu,
                            vet,
                            EtatVoeu.fromString(indVoeu.getState()),
                            iterableStream(cals).exists(Filters.isOpen),
                            typeTrt,
                            getRecupCalendarRdv(indVoeu, calsRdv));
                }
            };

    public static F<IndVoeu, IndVoeuPojo> indVoeuToPojo(final P1<DomainApoService> apoService,
                                                        final P1<ParameterService> paramService) {
        return new F<IndVoeu, IndVoeuPojo>() {
            public IndVoeuPojo f(IndVoeu indVoeu) {
                final DomainApoService apoServ = apoService._1();
                final ParameterService paramServ = paramService._1();

                final TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
                final VersionEtpOpi versionEtpOpi = trtCmi.getVersionEtpOpi();
                final String codeEtp = versionEtpOpi.getCodEtp();
                final Integer codeVrsVet = versionEtpOpi.getCodVrsVet();

                final VersionEtapeDTO vet = getVet.f(apoServ, codeEtp, codeVrsVet);
                final Set<CalendarIns> cals = getCals.f(paramServ, versionEtpOpi);
                final TypeTraitement typeTrt = TypeTraitement.fromCode(indVoeu.getCodTypeTrait());
                final List<CalendarRDV> calsRdv = getCalsRdv.f(paramServ);

                return buildIndVoeuPojo.f(indVoeu, calsRdv, typeTrt, cals, vet);
            }
        };
    }

    public static F<Individu, IndividuPojo> individuToPojo(final P1<DomainApoService> apoServ,
                                                           final P1<ParameterService> paramServ) {
        return new F<Individu, IndividuPojo>() {
            public IndividuPojo f(final Individu individu) {
                return new IndividuPojo() {{
                    setIndividu(individu);
                    setEtat(EtatIndividu.fromString(individu.getState().trim()));
                    setDateCreationDossier(individu.getDateCreaEnr());
                    setIndVoeuxPojo(iterableArray(individu.getVoeux()).map(indVoeuToPojo(apoServ, paramServ)));
                }};
            }
        };
    }

    /**
     * Transforms a {@link IndVoeu} to a {@link IndVoeuPojo} using the given services.
     */
    public static F<IndVoeu, Promise<IndVoeuPojo>> asyncIndVoeuToPojo(final DomainApoService apoServ,
                                                                      final ParameterService paramServ) {
        return new F<IndVoeu, Promise<IndVoeuPojo>>() {
            public Promise<IndVoeuPojo> f(final IndVoeu indVoeu) {
                final TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
                final VersionEtpOpi versionEtpOpi = trtCmi.getVersionEtpOpi();
                final String codeEtp = versionEtpOpi.getCodEtp();
                final Integer codeVrsVet = versionEtpOpi.getCodVrsVet();
                
                final P1<VersionEtapeDTO> vet = curry(getVet).f(apoServ).f(codeEtp).lazy().f(codeVrsVet);
                final P1<Set<CalendarIns>> cals = curry(getCals).f(paramServ).lazy().f(versionEtpOpi);
                final P1<TypeTraitement> typeTrt = p(TypeTraitement.fromCode(indVoeu.getCodTypeTrait()));
                final P1<List<CalendarRDV>> calsRdv = getCalsRdv.lazy().f(paramServ);

                return parMod.promise(vet)
                        .apply(parMod.promise(cals)
                                .apply(parMod.promise(typeTrt)
                                        .apply(parMod.promise(calsRdv).fmap(curry(buildIndVoeuPojo).f(indVoeu)))));
            }
        };
    }

    public static F<Individu, Promise<IndividuPojo>> asyncIndividuToPojo(final DomainApoService apoServ,
                                                                         final ParameterService paramServ) {
        return new F<Individu, Promise<IndividuPojo>>() {
            public Promise<IndividuPojo> f(final Individu individu) {
                return parMod.mapM(iterableStream(individu.getVoeux()), asyncIndVoeuToPojo(apoServ, paramServ))
                        .fmap(new F<Stream<IndVoeuPojo>, IndividuPojo>() {
                            public IndividuPojo f(final Stream<IndVoeuPojo> indVoeux) {
                                return new IndividuPojo() {{
                                    setIndividu(individu);
                                    setEtat(EtatIndividu.fromString(individu.getState().trim()));
                                    setDateCreationDossier(individu.getDateCreaEnr());
                                    setIndVoeuxPojo(indVoeux.toArray());
                                }};
                            }
                        });
            }
        };
    }

}
