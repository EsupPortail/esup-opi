package org.esupportail.opi.web.utils.fj;

import fj.F;
import fj.data.Stream;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.BusinessUtil;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.Etat;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.Transfert;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.util.HashSet;
import java.util.Set;

import static fj.data.Stream.iterableStream;
import static org.esupportail.opi.domain.beans.etat.Etat.instanceState;
import static org.esupportail.opi.web.beans.utils.Utilitaires.getRecupCalendarRdv;
import static org.esupportail.opi.web.utils.fj.Predicates.isTraitementNotEquals;
import static org.esupportail.opi.web.utils.fj.Predicates.keepOnlyAvisWithValidationEquals;

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

    /**
     * Transforms a {@link IndVoeu} to a {@link IndVoeuPojo} using the given services.
     */
    public static F<IndVoeu, IndVoeuPojo> indVoeuToPojo(final DomainApoService apoServ,
                                                        final ParameterService paramServ,
                                                        final I18nService i18nServ) {
        return new F<IndVoeu, IndVoeuPojo>() {
            public IndVoeuPojo f(IndVoeu indVoeu) {
                final TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
                final VersionEtapeDTO vet = apoServ.getVersionEtape(
                        trtCmi.getVersionEtpOpi().getCodEtp(),
                        trtCmi.getVersionEtpOpi().getCodVrsVet());
                final EtatVoeu etatVoeu = (EtatVoeu) Etat.instanceState(indVoeu.getState(), i18nServ);
                final boolean calIsOpen = iterableStream(
                        paramServ.getCalendars(trtCmi.getVersionEtpOpi())).exists(Filters.isOpen);
                final TypeTraitement typeTrait =
                        BusinessUtil.getTypeTraitement(paramServ.getTypeTraitements(), indVoeu.getCodTypeTrait());
                final CalendarRDV cal = getRecupCalendarRdv(indVoeu, paramServ.getCalendarRdv());

                return new IndVoeuPojo(indVoeu, vet, etatVoeu, calIsOpen, typeTrait, cal);
            }
        };
    }

    public static F<Individu, IndividuPojo> individuToPojo(final DomainApoService apoServ,
                                                           final ParameterService paramServ,
                                                           final I18nService i18nServ) {
        return new F<Individu, IndividuPojo>() {
            @Override
            public IndividuPojo f(final Individu individu) {
                return new IndividuPojo() {{
                    setIndividu(individu);
                    setEtat((EtatIndividu) instanceState(individu.getState(), i18nServ));
                    setDateCreationDossier(individu.getDateCreaEnr());
                    initIndCursusScolPojo(apoServ, i18nServ);
                    setIndVoeuxPojo(new HashSet<>(
                            iterableStream(individu.getVoeux()).map(
                                    indVoeuToPojo(apoServ, paramServ, i18nServ)).toCollection()));
                }};
            }
        };
    }

    /**
     * si onlyValidate = true, on enlève les voeux non validés
     * et inversement
     * see {@see ConversionsTest.testRemoveNotValidatedVoeu()}
     *
     * @param onlyValidate param to decide
     * @return {@link Stream} of {@link IndividuPojo} where all {@link IndVoeuPojo} avis validation matches onlyValidate param
     */
    public static F<IndividuPojo, IndividuPojo> keepOnlyVoeuWithValidatedAvisEquals(final Boolean onlyValidate) {
        return new F<IndividuPojo, IndividuPojo>() {
            @Override
            public IndividuPojo f(IndividuPojo ip) {
                Set<IndVoeuPojo> indVoeuPojoSet = new HashSet<>(iterableStream(ip.getIndVoeuxPojo())
                        .filter(keepOnlyAvisWithValidationEquals(onlyValidate))
                        .toCollection());
                ip.setIndVoeuxPojo(indVoeuPojoSet);
                return ip;
            }
        };
    }

    /**
     * Remove every {@link IndVoeuPojo} with {@link TypeTraitement} equals param from a {@link Stream} of {@link IndividuPojo}
     *
     * @param transfert the treatment to be removed from Voeu
     * @return a stream of fltered IndividuPojo
     */
    public static F<IndividuPojo, IndividuPojo> removeVoeuWithTreatmentEquals(final Transfert transfert) {
        return new F<IndividuPojo, IndividuPojo>() {
            @Override
            public IndividuPojo f(IndividuPojo ip) {
                Set<IndVoeuPojo> indVoeuPojoSet = new HashSet<>(iterableStream(ip.getIndVoeuxPojo()).filter(isTraitementNotEquals(transfert)).toCollection());
                ip.setIndVoeuxPojo(indVoeuPojoSet);
                return ip;
            }
        };
    }

    /**
     * Decode a {@link Stream} of{@link RegimeInscription}
     *
     * @return the resulting stream of Code
     */
    public static F<RegimeInscription, Integer> decodeRegimeInscription() {
        return new F<RegimeInscription, Integer>() {
            @Override
            public Integer f(RegimeInscription ri) {
                return ri.getCode();
            }
        };
    }

    /**
     * Initialize the scolar cursus of a {@link Stream} of {@link IndividuPojo} according to param
     *
     * @param initCusrsusScol boolean param to decide if should initialize the whole stream or not
     * @param apoServ         utility service
     * @param i18nServ        utility service
     * @return the resulting {@link Stream}
     */
    public static F<IndividuPojo, IndividuPojo> initCursusScol(final boolean initCusrsusScol,
                                                               final DomainApoService apoServ,
                                                               final I18nService i18nServ) {
        return new F<IndividuPojo, IndividuPojo>() {
            @Override
            public IndividuPojo f(final IndividuPojo individuPojo) {
                if (initCusrsusScol) {
                    individuPojo.initIndCursusScolPojo(apoServ, i18nServ);
                }
                return individuPojo;
            }
        };
    }
}
