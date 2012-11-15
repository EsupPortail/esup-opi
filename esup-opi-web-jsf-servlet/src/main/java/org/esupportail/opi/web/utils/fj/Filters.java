package org.esupportail.opi.web.utils.fj;

import fj.F;
import fj.data.Stream;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.web.beans.pojo.IndRechPojo;
import org.esupportail.opi.web.utils.Utilitaires;

import java.util.Set;

import static fj.data.Stream.iterableStream;

public class Filters {

    public static final F<CalendarIns, Boolean> isOpen =
            new F<CalendarIns, Boolean>() {
                public Boolean f(CalendarIns calendarIns) {
                    return Utilitaires.calIsOpen(calendarIns);
                }
            };

    public static F<IndVoeu, Boolean> trtCmiFilter(IndRechPojo indRechPojo, final Set<Commission> commissions) {
        return new F<IndVoeu, Boolean>() {
            public Boolean f(IndVoeu indVoeu) {
                final TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
                return iterableStream(commissions).bind(
                        new F<Commission, Stream<TraitementCmi>>() {
                            public Stream<TraitementCmi> f(Commission commission) {
                                return iterableStream(commission.getTraitementCmi());
                            }}).exists(
                        new F<TraitementCmi, Boolean>() {
                            public Boolean f(TraitementCmi traitementCmi) {
                                return traitementCmi.equals(trtCmi); }});
            }
        };
    }
}
