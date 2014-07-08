package org.esupportail.opi.web.candidat.controllers;

import fj.F;
import fj.data.Option;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorPays;
import org.esupportail.opi.web.candidat.beans.CandidatPojo;
import org.esupportail.opi.web.candidat.config.rewrite.Navigation;
import org.esupportail.opi.web.candidat.services.security.CandidatService;
import org.esupportail.opi.web.candidat.utils.Transform;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.Pays;

import javax.faces.application.NavigationHandler;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import java.util.Collections;
import java.util.List;

import static fj.data.Option.fromNull;
import static fj.data.Option.fromString;
import static fj.data.Option.join;
import static java.lang.String.format;
import static org.esupportail.opi.web.candidat.services.security.CandidatService.LoggedUser;
import static org.esupportail.opi.web.candidat.utils.Transform.individuToCandidatPojo;

public abstract class CandidatController {

    protected final DomainService domainService;

    protected final DomainApoService apoService;

    protected final I18nService i18nService;

    protected final LoggedUser loggedUser;

    protected CandidatPojo candidat;

    protected int currentTabIndex = 1;

    protected CandidatController(DomainService domainService,
                                 DomainApoService apoService,
                                 I18nService i18nService,
                                 LoggedUser loggedUser) {
        this.domainService = domainService;
        this.apoService = apoService;
        this.i18nService = i18nService;
        this.loggedUser = loggedUser;
    }

    public void initView() {
        final FacesContext facesContext = FacesContext.getCurrentInstance();
        final ExternalContext externalContext = facesContext.getExternalContext();
        final Option<String> dossier = fromString(externalContext.getRequestParameterMap().get("dossier"));
        final Option<CandidatPojo> maybeCandidat = join(dossier.map(new F<String, Option<CandidatPojo>>() {
            public Option<CandidatPojo> f(String d) {
                return fromNull(domainService.getIndividu(d, null)).map(individuToCandidatPojo.f(apoService));
            }
        }));

        if (maybeCandidat.isNone()) {
            redirect("/stylesheets/welcome.xhtml?faces-redirect=true");
        }
        candidat = maybeCandidat.some();
        if (loggedUser != null &&
                loggedUser.isCandidat() &&
                !candidat.getDossier().equalsIgnoreCase(loggedUser.getUsername())) {
            redirect(format("%s?faces-redirect=true&dossier=%s",
                    Navigation.COORDONNEES,
                    loggedUser.getUsername()));
        }
    }

    public void save() {
        final Individu individu = domainService.getIndividu(candidat.getDossier(), null);

        domainService.updateUser(Transform.candidatPojoToIndividu.f(candidat, individu));
    }

    protected void redirect(final String outcome) {
        final FacesContext context = FacesContext.getCurrentInstance();

        final NavigationHandler navHandler = context.getApplication().getNavigationHandler();

        navHandler.handleNavigation(context, null, outcome);
        context.responseComplete();
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

    public List<Pays> getPays() {
        List<Pays> l = apoService.getPays();
        Collections.sort(l, new ComparatorPays(ComparatorPays.PAYS));
        return l;
    }

    public List<Departement> getDepartements() {
        return apoService.getDepartements();
    }

    public List<Pays> getNationalites() {
        List<Pays> l = apoService.getPays();
        Collections.sort(l, new ComparatorPays(ComparatorPays.NATIONALITE));
        return l;
    }
}
