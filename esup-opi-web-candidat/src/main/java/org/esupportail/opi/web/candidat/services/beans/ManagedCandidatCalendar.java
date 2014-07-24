package org.esupportail.opi.web.candidat.services.beans;


import fj.data.Array;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.calendar.Calendar;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.services.DomainCandidatService;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.parameters.FormationContinue;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.VersionEtapePojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.candidat.beans.CandidatVoeuPojo;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ManagedCandidatCalendar {

    private ParameterService parameterService;

    private DomainCandidatService domainCandidatService;

    private I18nService i18Service;

    private ManagedCandidatCalendar(final DomainCandidatService domainCandidatService,
                                    final I18nService i18nService,
                                    final ParameterService parameterService) {
        this.domainCandidatService = domainCandidatService;
        this.i18Service = i18nService;
        this.parameterService = parameterService;
    }

    public static ManagedCandidatCalendar managedCandidatCalendar(final DomainCandidatService domainCandidatService,
                                                                  final I18nService i18nService,
                                                                  final ParameterService parameterService) {
        return new ManagedCandidatCalendar(domainCandidatService,
                i18nService,
                parameterService);
    }

    	/*
     ******************* METHODS ********************** */


    /**
     * Parcours tous les calendriers des depots des voeux.
     * Return true si il existe au moins un calendrier ouvert.
     *
     * @return Boolean
     */
    public Boolean getCalInsIsOpen(RegimeInscription ri) {
        if (ri instanceof FormationContinue) {
            return true;
        }
        List<Calendar> calIns = parameterService.getCalendars(true, Calendar.TYPE_CAL_INSCRIPTION);
        for (Calendar c : calIns) {
            CalendarIns cIns = (CalendarIns) c;
            Boolean isOpen = Utilitaires.calIsOpen(cIns);
            if (isOpen) {
                return isOpen;
            }
        }
        return false;
    }


    public Set<VersionEtapePojo> getVrsEtpPojo(
            final Map<Commission, Set<VersionEtapeDTO>> mapCmi,
            final RegimeInscription candidatRi,
            final Array<CandidatVoeuPojo> candidatVoeux) {
        Set<VersionEtapePojo> vrsEtpToDiplay = new HashSet<VersionEtapePojo>();
        for (Map.Entry<Commission, Set<VersionEtapeDTO>> cmi : mapCmi.entrySet()) {
            Set<VersionEtapeDTO> vet = cmi.getValue();
            List<CalendarIns> calList = parameterService.getCalendarIns(cmi.getKey());
            vrsEtpToDiplay.addAll(
                    initVetPojo(vet,
                            candidatVoeux,
                            calIsOpen(calList),
                            getPeriodCAlIsOpen(calList, false),
                            candidatRi.getCanAlwaysAddVows())
            );
        }

        return vrsEtpToDiplay;
    }

    /**
     * Init the VersionEtapePojo.
     *
     * @param vet
     * @param isOpen
     * @param periodCalIns
     * @param hasRightsToAddVows
     * @return Set< VersionEtapePojo>
     */
    private Set<VersionEtapePojo> initVetPojo(
            final Set<VersionEtapeDTO> vet,
            final Array<CandidatVoeuPojo> candidatVoeux,
            final boolean isOpen,
            final String periodCalIns,
            final boolean hasRightsToAddVows) {
        Set<VersionEtapePojo> vrsEtpToDiplay = new HashSet<VersionEtapePojo>();
        for (VersionEtapeDTO v : vet) {
            VersionEtapePojo vPojo = new VersionEtapePojo(v, isOpen, periodCalIns);
            for (CandidatVoeuPojo iV : candidatVoeux) {
                TraitementCmi trtCmi = parameterService.getTraitementCmi(iV.getLinkTrtCmiCamp().getTraitementCmi().getId());
                VersionEtpOpi vOpi = new VersionEtpOpi(v);
                if (trtCmi.getVersionEtpOpi().equals(vOpi)) {
                    //le candidat est deja inscrit
                    vPojo.setIsAlReadyChoice(true);
                }
            }
            vPojo.setHasRightsToAddVows(hasRightsToAddVows);
            vrsEtpToDiplay.add(vPojo);
        }
        return vrsEtpToDiplay;
    }

    /**
     * @param cIns
     * @return Boolean true if cIns is open.
     */
    private Boolean calIsOpen(final List<CalendarIns> cIns) {
        if (cIns != null) {
            for (CalendarIns cal : cIns) {
                if (Utilitaires.calIsOpen(cal)) {
                    return true;
                }
            }
        }
        return false;
    }

    private String getPeriodCAlIsOpen(final List<CalendarIns> cIns, final boolean notDisplayPeriod) {
        if (notDisplayPeriod) {
            return "";
        }
        String period = null;
        int cpt = 0;
        SimpleDateFormat s = new SimpleDateFormat(Constantes.DATE_FORMAT);
        for (CalendarIns cal : cIns) {
            // on affiche que les calendriers en service
            if (cal.getTemoinEnService()) {
                String datDbt = s.format(cal.getStartDate());
                String endDat = s.format(cal.getEndDate());
                if (cpt == 0) {
                    period = i18Service.getString(Constantes.I18N_FIELD_DAT_PERIOD, datDbt, endDat);
                } else {
                    period += ", "
                            + i18Service.getString(Constantes.I18N_FIELD_DAT_PERIOD, datDbt, endDat);
                }
                cpt++;
            }
        }
        return period;
    }
}
