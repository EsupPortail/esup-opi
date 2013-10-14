package org.esupportail.opi.services.i18n;

import fj.P1;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.beans.etat.Etat;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.user.Individu;

import static org.esupportail.opi.domain.beans.etat.EtatNonRenseigne.EtatNonRenseigne;
import static org.esupportail.opi.domain.beans.etat.EtatIndividu.EtatComplet;

/**
 * Defines, possibly caches and presents some frequent calls to {@link I18nService}
 */
public final class I18NUtilsService {

    private final I18nService i18nService;

    private final P1<String> labelEtatNonRenseigne = new P1<String>() {
        public String _1() {
            return i18nService.getString(EtatNonRenseigne.getCodeLabel());
        }
    }.memo();

    private final P1<String> labelEtatComplet = new P1<String>() {
        public String _1() {
            return i18nService.getString(EtatComplet.getCodeLabel());
        }
    }.memo();

    private I18NUtilsService(I18nService i18nService) {
        this.i18nService = i18nService;
    }

    public static I18NUtilsService i18NUtilsService(I18nService i18nService) {
        return new I18NUtilsService(i18nService);
    }

    public String labelEtatNonRenseigne() { return labelEtatNonRenseigne._1(); }

    public String labelEtatComplet() { return labelEtatComplet._1(); }

    public String labelEtat(Etat etat) {
        return i18nService.getString(etat.getCodeLabel());
    }

    public I18nService getI18nService() { return i18nService; }
}
