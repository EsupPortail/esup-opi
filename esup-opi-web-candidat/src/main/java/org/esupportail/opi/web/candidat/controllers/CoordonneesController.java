package org.esupportail.opi.web.candidat.controllers;

import fj.Effect;
import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;
import org.esupportail.commons.exceptions.ObjectNotFoundException;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorPays;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.Pays;
import org.springframework.util.StringUtils;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static fj.data.Option.fromString;
import static javax.faces.application.FacesMessage.SEVERITY_ERROR;
import static org.esupportail.opi.utils.Constantes.I18N_NOT_EXSIT;

public class CoordonneesController extends CandidatController {

    private List<CommuneDTO> communesFix;

    private CoordonneesController(final DomainService domainService,
                                  final DomainApoService apoService,
                                  final I18nService i18nService) {
        super(domainService, apoService, i18nService);
    }

    public static CoordonneesController coordonneesController(final DomainService domainService,
                                                              final DomainApoService apoService,
                                                              final I18nService i18nService) {
        return new CoordonneesController(domainService, apoService, i18nService);
    }

    @Override
    public void initView() {
        super.initView();
        computeCities();
    }

    public void resetDeptNais() {
        final String codePay = candidat.getEtatCivil().getPaysNaissance();

        //SI Pays != france  on remet à null le département.
        fromString(codePay).foreach(new Effect<String>() {
            public void e(String code) {
                if (code.equalsIgnoreCase(Constantes.CODEFRANCE)) {
                    candidat.getEtatCivil().setDeptNaissance(null);
                }
            }
        });
    }

    public void computeCities() {
        final String cp = candidat.getAdresseFixe().getCodePostal();
        if (StringUtils.hasText(cp) && Utilitaires.isCodePostalValid(cp)) {
            try {
                communesFix = apoService.getCommunesDTO(cp);
            } catch (ObjectNotFoundException e) {
                //Le code postal n'existe pas dans Apogée.
                final FacesContext fc = FacesContext.getCurrentInstance();
                fc.addMessage(":coordonneesForm:codPostInput",
                        new FacesMessage(SEVERITY_ERROR, I18N_NOT_EXSIT, I18N_NOT_EXSIT));
            }
        } else if (candidat.getAdresseFixe().getPays().equals(Constantes.CODEFRANCE)) {
            final FacesContext fc = FacesContext.getCurrentInstance();
            final String errorCode = "ERROR.FIELD.INVALID";
            fc.addMessage(":coordonneesForm:codPostInput", new FacesMessage(SEVERITY_ERROR, errorCode, errorCode));
            communesFix = new ArrayList<>();
        } else {
            communesFix = new ArrayList<>();
        }
    }

    // JSF conveniences

    public List<SelectItem> getCivilites() {
        List<SelectItem> s = new ArrayList<>();
        s.add(new SelectItem(Constantes.COD_SEXE_FEMININ, i18nService.getString(Constantes.I18N_CIV_MM)));
        s.add(new SelectItem(Constantes.COD_SEXE_MASCULIN, i18nService.getString(Constantes.I18N_CIV_MR)));
        return s;
    }


    public List<Pays> getPays() {
        List<Pays> l = apoService.getPays();
        Collections.sort(l, new ComparatorPays(ComparatorPays.PAYS));
        return l;
    }

    public List<Pays> getNationalites() {
        List<Pays> l = apoService.getPays();
        Collections.sort(l, new ComparatorPays(ComparatorPays.NATIONALITE));
        return l;
    }

    public List<Departement> getDepartements() {
        return apoService.getDepartements();
    }

    public List<CommuneDTO> getCommunesFix() {
        return communesFix;
    }
}
