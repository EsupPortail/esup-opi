package org.esupportail.opi.web.candidat.controllers;

import fj.Equal;
import fj.F;
import fj.data.Option;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.candidat.beans.CursusScolPojo;
import org.esupportail.opi.web.candidat.utils.Transform;
import org.esupportail.wssi.services.remote.*;
import org.primefaces.context.PrimeFacesContext;
import org.primefaces.context.RequestContext;

import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fj.Function.curry;
import static fj.P.p;
import static fj.data.Array.iterableArray;
import static fj.data.Array.single;
import static fj.data.Option.fromNull;
import static fj.data.Option.fromString;
import static org.esupportail.opi.web.candidat.utils.Transform.cursusScolPojoToIndCursusScol;
import static org.esupportail.opi.web.candidat.utils.Transform.individuToCandidatPojo;

public class CursusController extends CandidatController {

    private CursusScolPojo cursusScol;

    private CursusController(final DomainService domainService,
                             final DomainApoService apoService,
                             final I18nService i18nService) {
        super(domainService, apoService, i18nService);
        cursusScol = CursusScolPojo.empty();
    }

    public static CursusController cursusController(final DomainService domainService,
                                                                final DomainApoService apoService,
                                                                final I18nService i18nService) {
        return new CursusController(domainService, apoService, i18nService);
    }

    public void addCursusScol() {
        final Individu individu = domainService.getIndividu(candidat.getDossier(), null);
        final IndCursusScol indCursusScol = p(cursusScol)
                .map(curry(cursusScolPojoToIndCursusScol).f(apoService).f(individu))._1();
        domainService.addIndCursusScol(
                domainService.add(indCursusScol, candidat.getDossier())); //TODO: replace with connected user's id

        final Individu refreshed = domainService.getIndividu(candidat.getDossier(), null);
        setCandidat(p(refreshed).map(individuToCandidatPojo.f(apoService))._1());
        RequestContext.getCurrentInstance().execute("$('#modalPostBac').modal('hide');");
        cursusScol = CursusScolPojo.empty();
    }

    public void deleteCursusScol(CursusScolPojo cursusScol) {
        final Individu individu = domainService.getIndividu(candidat.getDossier(), null);
        final IndCursusScol indCursusScol = p(cursusScol)
                .map(curry(cursusScolPojoToIndCursusScol).f(apoService).f(individu))._1();
        domainService.deleteIndCursusScol(
                domainService.update(indCursusScol, candidat.getDossier())); //TODO: replace with connected user's id

        final Individu refreshed = domainService.getIndividu(candidat.getDossier(), null);
        setCandidat(p(refreshed).map(individuToCandidatPojo.f(apoService))._1());
    }

    public CursusScolPojo getCursusScol() {
        return cursusScol;
    }

    public void setCursusScol(CursusScolPojo cursusScol) {
        this.cursusScol = cursusScol;
    }

    public List<CommuneDTO> communes(String departement) {
        return fromString(departement).map(new F<String, List<CommuneDTO>>() {
            public List<CommuneDTO> f(String s) {
                return apoService.getCommunes(s, true, null);
            }
        }).orSome(new ArrayList<CommuneDTO>());
    }

    public List<Etablissement> lycees(final String pays, final String departement, final String ville) {
        final List<Etablissement> emptyList = new ArrayList<>();
        return fromString(pays).map(new F<String, List<Etablissement>>() {
            public List<Etablissement> f(String p) {
                final Option<List<Etablissement>> etbsEt = fromNull(apoService.getLycees(null, Constantes.COD_DEP_ETR));

                final Option<List<Etablissement>> etbsFr = fromString(ville)
                        .map(new F<String, List<Etablissement>>() {
                            public List<Etablissement> f(String v) {
                                return apoService.getLycees(v, departement);
                            }
                        });
                return Option.iif(Equal.stringEqual.eq(p, Constantes.CODEFRANCE), etbsFr).orSome(etbsEt)
                        .orSome(emptyList);
            }
        }).orSome(emptyList);
    }


    /**
     * Returns all DipAutCur.
     */
    public List<DipAutCur> dipAutCurs(String pays) {
        final Option<String> optPays = fromString(pays);
        final List<DipAutCur> emptyList = new ArrayList<>();
        final F<String, List<DipAutCur>> paysToDac = new F<String, List<DipAutCur>>() {
            public List<DipAutCur> f(String p) {
                if (p.equals(Constantes.CODEFRANCE)) {
                    DipAutCur dip = new DipAutCur();
                    dip.setCodDac("");
                    dip.setLibDac("");
                    return new ArrayList<>(single(dip).append(
                            iterableArray(fromNull(apoService.getDipAutCurs())
                                    .orSome(new ArrayList<DipAutCur>()))).toCollection());
                }
                return new ArrayList<DipAutCur>() {{
                    final DipAutCur dipAutCur = apoService.getDipAutCur(apoService.getCodDacEtr());
                    if (dipAutCur != null) {
                        add(dipAutCur);
                    }
                }};
            }
        };
        return optPays.option(emptyList, paysToDac);
    }

    public List<BacOuxEqu> getBacs() {
        List<BacOuxEqu> c = fromString(candidat.getBaccalaureat().getDateObtention())
                .map(new F<String, List<BacOuxEqu>>() {
                    public List<BacOuxEqu> f(String daaObt) {
                        return apoService.getBacOuxEqus(daaObt);
                    }
                }).orSome(new ArrayList<BacOuxEqu>());

        Collections.sort(c, new ComparatorString(BacOuxEqu.class));
        return c;
    }

    public List<MentionNivBac> getMentionNivBacs() {
        return apoService.getMentionNivBacs();
    }

    public List<SelectItem> getObtentionValues() {
        final String inprogess = i18nService.getString("FIELD_LABEL.IN_PROGRESS");
        final List<SelectItem> s = new ArrayList<>();
        s.add(new SelectItem(Constantes.FLAG_YES, i18nService.getString("_.BUTTON.YES")));
        s.add(new SelectItem(Constantes.FLAG_NO, i18nService.getString("_.BUTTON.NO")));
        s.add(new SelectItem(inprogess, inprogess));
        return s;
    }
}
