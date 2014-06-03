package org.esupportail.opi.web.candidat.controllers;

import fj.Effect;
import fj.F;
import fj.data.Option;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.web.candidat.beans.CandidatPojo;
import org.esupportail.opi.web.candidat.utils.Transform;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import static fj.data.Option.fromNull;
import static fj.data.Option.fromString;
import static fj.data.Option.join;
import static org.esupportail.opi.web.candidat.utils.Transform.individuToCandidatPojo;

public abstract class CandidatController {

    protected final DomainService domainService;

    protected final DomainApoService apoService;

    protected final I18nService i18nService;

    protected CandidatPojo candidat;

    protected int currentTabIndex = 1;

    protected CandidatController(DomainService domainService, DomainApoService apoService, I18nService i18nService) {
        this.domainService = domainService;
        this.apoService = apoService;
        this.i18nService = i18nService;
    }

    public void initView() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ExternalContext externalContext = facesContext.getExternalContext();
        final Option<String> dossier = fromString(externalContext.getRequestParameterMap().get("dossier"));
        final Option<CandidatPojo> maybeCandidat = join(dossier.map(new F<String, Option<CandidatPojo>>() {
            public Option<CandidatPojo> f(String d) {
                return fromNull(domainService.getIndividu(d, null)).map(individuToCandidatPojo);
            }
        }));
        maybeCandidat.foreach(new Effect<CandidatPojo>() {
            public void e(CandidatPojo pojo) {
                candidat = pojo;
            }
        });
    }

    public void save() {
        final Individu individu = domainService.getIndividu(candidat.getDossier(), null);

        domainService.updateUser(Transform.candidatPojoToIndividu.f(candidat, individu));
    }

    public CandidatPojo getCandidat() {
        return candidat;
    }

    public void setCandidat(CandidatPojo candidat) {
        this.candidat = candidat;
    }

    public int getCurrentTabIndex() {
        return currentTabIndex;
    }

    public void setCurrentTabIndex(int currentTabIndex) {
        this.currentTabIndex = currentTabIndex;
    }
}
