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
import static org.esupportail.opi.web.utils.fj.Predicates.keepOnlyAvisWithValidationEquals;
import static org.esupportail.opi.web.utils.fj.Predicates.isTraitementNotEquals;

public class Conversions {

    private Conversions(){
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
                return new HashSet<T>(ts.toCollection());
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
            public IndividuPojo f(final Individu individu) {
                return new IndividuPojo() {{
                    setIndividu(individu);
                    setEtat((EtatIndividu) instanceState(individu.getState(), i18nServ));
                    setDateCreationDossier(individu.getDateCreaEnr());
                    initIndCursusScolPojo(apoServ, i18nServ);
                    setIndVoeuxPojo(new HashSet<IndVoeuPojo>(
                            iterableStream(individu.getVoeux()).map(
                                    indVoeuToPojo(apoServ, paramServ, i18nServ)).toCollection()));
                }};
            }
        };
    }

}
