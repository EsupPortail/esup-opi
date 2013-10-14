package org.esupportail.opi.web.controllers.user;

import org.esupportail.opi.services.i18n.I18NUtilsService;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.controllers.SessionController;

public class EtatController {

    private final SessionController session;

    private final I18NUtilsService i18NUtils;

    private EtatController(SessionController session, I18NUtilsService i18NUtils) {
        this.session = session;
        this.i18NUtils = i18NUtils;
    }

    public static EtatController etatController(SessionController session, I18NUtilsService i18NUtils) {
        return new EtatController(session, i18NUtils);
    }

    public String showEtatCurrentInd() {
        final IndividuPojo ip = session.getCurrentInd();
        return i18NUtils.labelEtat(ip.getEtat());
    }

    public String showEtatVoeu(IndVoeuPojo indVoeu) {
        return i18NUtils.labelEtat(indVoeu.getEtat());
    }
}
