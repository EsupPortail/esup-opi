package org.esupportail.opi.web.controllers.opinions;

import fj.F;
import fj.P1;
import fj.data.Array;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.InscriptionAdm;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.pojo.*;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.utils.MiscUtils;
import org.esupportail.opi.web.utils.paginator.LazyDataModel;
import org.esupportail.opi.web.utils.paginator.PaginationFunctions;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.*;

import static java.util.Arrays.asList;
import static org.esupportail.opi.domain.beans.etat.EtatVoeu.EtatNull;
import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.*;
import static org.esupportail.opi.web.utils.fj.Conversions.individuToPojo;
import static org.esupportail.opi.web.utils.paginator.LazyDataModel.lazyModel;

public class TypeTraitController extends AbstractContextAwareController {
    private static final long serialVersionUID = 5545836516397172544L;

    private String codeTypeTrtselected;

    private InscriptionAdm inscriptionAdm;

    private SmtpService smtpService;

    private boolean renderTable = false;

    private IndRechPojo indRechPojo = new IndRechPojo() {
        public void setExcludeWishProcessed(Boolean exclude) {
            super.setExcludeWishProcessed(exclude);
            if (exclude)
                setTypeTraitements(asList(ValidationAcquis, Transfert, EnAttente));
            else
                setTypeTraitements(Collections.<TypeTraitement>emptyList());
        }
        {
        setExcludeWishProcessed(true);
        setUseGestCommsFilter(true);
        setUseVoeuFilter(false);
    }};

    private F<IndividuPojo, IndividuPojo> initCursus = new F<IndividuPojo, IndividuPojo>() {
        public IndividuPojo f(IndividuPojo individuPojo) {
            MiscUtils.initIndCursusScolPojo(individuPojo, getDomainApoService());
            return individuPojo;
        }
    };

    private LazyDataModel<IndividuPojo> indPojoLDM = lazyModel(
            PaginationFunctions.getData(
                    new P1<SessionController>() {
                        public SessionController _1() {
                            return getSessionController();
                        }
                    },
                    new P1<DomainService>() {
                        public DomainService _1() {
                            return getDomainService();
                        }
                    },
                    new P1<DomainApoService>() {
                        public DomainApoService _1() {
                            return getDomainApoService();
                        }
                    },
                    new P1<ParameterService>() {
                        public ParameterService _1() {
                            return getParameterService();
                        }
                    },
                    new P1<IndRechPojo>() {
                        public IndRechPojo _1() {
                            return indRechPojo;
                        }
                    }),
            PaginationFunctions.findByRowKey)
            .map(individuToPojo(
                    new P1<DomainApoService>() {
                        public DomainApoService _1() {
                            return getDomainApoService();
                        }
                    },
                    new P1<ParameterService>() {
                        public ParameterService _1() {
                            return getParameterService();
                        }
                    })
                    .andThen(initCursus));

    @Override
    public void reset() {
        super.reset();
        codeTypeTrtselected = null;
    }

    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();
        Assert.notNull(this.smtpService,
                "property smtpService of class " + this.getClass().getName()
                        + " can not be null");
        Assert.notNull(this.inscriptionAdm,
                "property inscriptionAdm of class " + this.getClass().getName()
                        + " can not be null");
        reset();
    }

    public String goSeeAllTypeTraitments() {
        reset();
        return NavigationRulesConst.DISPLAY_TYPE_TRAITEMENT;
    }

    private void sendMails(final IndividuPojo i, final Campagne camp,
                           final Set<IndVoeuPojo> indVoeuPojo,
                           final TypeTraitement typeT) {

        String htmlBody = null;
        String htmlCoordonnees = null;
        String corresponding = null;

        String htmlDebut = getString("MAIL.CANDIDAT_VOEU.HTMLTEXT_DEBUT",
                Utilitaires.getCivilite(getI18nService(),
                        i.getIndividu().getSexe()));

        // traitement des TR eventuels

        htmlBody = "";
        Map<Commission, Set<VersionEtapeDTO>> mapCmi =
                Utilitaires.getCmiForIndVoeux(getParameterService().getCommissions(true),
                        Array.iterableArray(indVoeuPojo), camp);
        Integer codeRI = camp.getCodeRI();
        for (Map.Entry<Commission, Set<VersionEtapeDTO>> cmiEntry : mapCmi.entrySet()) {
            Commission cmi = cmiEntry.getKey();
            CommissionPojo cmiPojo = new CommissionPojo(
                    cmi,
                    new AdressePojo(cmi.getContactsCommission()
                            .get(codeRI)
                            .getAdresse(), getDomainApoService()),
                    cmi.getContactsCommission().get(codeRI));
            Set<VersionEtapeDTO> vetDTO = cmiEntry.getValue();
            StringBuilder html = new StringBuilder();
            for (VersionEtapeDTO vDTO : vetDTO) {
                html.append(getString("MAIL.LIST_VET", vDTO.getLibWebVet()));

                htmlCoordonnees = Utilitaires.getAdrCmiForSendMail(getI18nService(), cmiPojo, null);
                corresponding = cmiPojo.getContactCommission().getCorresponding();
            }
            htmlBody += html.toString();
            if (typeT == Transfert) {
                htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY1");
                htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY2",
                        i.getCampagneEnServ(getDomainService()).getCodAnu());
                // coordonnees commission
                htmlBody += htmlCoordonnees;
                htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY3");
            } else if (typeT == ValidationAcquis) {
                String commDateEnd = cmi.getCalendarCmi().getCommDatEndBack();
                if (commDateEnd == null) {
                    commDateEnd = "";
                }
                //TODO test si calendar null
                htmlBody += getString("MAIL.CANDIDAT_VOEU_VA.HTMLTEXT_BODY1",
                        Utilitaires.convertDateToString(
                                cmi.getCalendarCmi().getDatEndBackDossier(), Constantes.DATE_FORMAT),
                        commDateEnd);
                // coordonnees commission
                htmlBody += htmlCoordonnees;
                htmlBody += getString("MAIL.CANDIDAT_VOEU_VA.HTMLTEXT_BODY2");
                htmlBody += htmlCoordonnees;
            }

            // responsable scolarite
            htmlBody += corresponding;

            Utilitaires.sendEmailIndividu(
                    getString("MAIL.CANDIDAT_VOEU_TR.SUBJECT"),
                    htmlDebut + htmlBody, null,
                    i.getIndividu(),
                    smtpService, getI18nService());
        }

    }


    /**
     * update.
     * Put at true the flag haveBeTraited;
     */
    public void update() {
        Set<IndVoeuPojo> lesVA = new HashSet<>();
        Set<IndVoeuPojo> lesTR = new HashSet<>();
        List<IndividuPojo> lesIndividus = indPojoLDM.getData();
        for (IndividuPojo i : lesIndividus) {
            Array<IndVoeuPojo> lesVoeux = i.getIndVoeuxPojo();
            lesVA.clear();
            lesTR.clear();
            Campagne camp = i.getCampagneEnServ(getDomainService());
            for (IndVoeuPojo v : lesVoeux) {
                String codeTrt = v.getIndVoeu().getCodTypeTrait();
                if (codeTrt != null) {
                    if (!codeTrt.equals(EnAttente.code)) {
                        // voeu non traite qui a ete modifie
                        if (codeTrt.equals(ValidationAcquis.code)) {
                            lesVA.add(v);
                        } else if (codeTrt.equals(Transfert.code)) {
                            lesTR.add(v);
                            // ajout un avis favorable
                            Avis avis = new Avis();
                            avis.setIndVoeu(v.getIndVoeu());
                            avis.setValidation(true);
                            //recherche du type Decision avec le typeConvoc inscriptionAdm
                            for (TypeDecision t : getParameterService().getTypeDecisions(true)) {
                                if (t.getCodeTypeConvocation()
                                        .equals(inscriptionAdm.getCode())) {
                                    avis.setResult(t);
                                }
                            }
                            if (avis.getResult() != null) {
                                Avis av = getDomainService().add(avis, getCurrentGest().getLogin());
                                getDomainService().addAvis(av);
                            } else {
                                throw new ConfigException(
                                        "il n'existe pas de typeDecision amenant"
                                                + " l'inscription administrative --> avis Favorable");
                            }
                            //update state in stateNull
                            v.getIndVoeu().setState(EtatNull.getCodeLabel());
                            //----Lignes ajoutées suite problème Mysql ----------------------//
                            if (v.getIndVoeu().getAvis() == null) {
                                v.getIndVoeu().setAvis(new HashSet<Avis>());
                            }
                            v.getIndVoeu().getAvis().add(avis);
                            //----------------------------------------------------//
                        }

                        v.getIndVoeu().setHaveBeTraited(true);

                    }
                    //on met le voeu à jour
                    getDomainService().updateIndVoeu(v.getIndVoeu());
                }
            }
            if (!lesVA.isEmpty()) {
                sendMails(i, camp, lesVA, ValidationAcquis);
            }
            if (!lesTR.isEmpty()) {
                sendMails(i, camp, lesTR, Transfert);
            }
        }
    }


    public void selectTypTrt(final ValueChangeEvent event) {
        codeTypeTrtselected = (String) event.getNewValue();
        selectAllTypeAction();
    }


    public void selectAllTypeAction() {
        List<IndividuPojo> lesIndividus = indPojoLDM.getData();
        for (IndividuPojo i : lesIndividus) {
            Array<IndVoeuPojo> lesVoeux = i.getIndVoeuxPojo();
            for (IndVoeuPojo v : lesVoeux)
                v.getIndVoeu().setCodTypeTrait(codeTypeTrtselected);
        }
    }

    public List<TypeTraitement> getTypesTraitement() {
        return asList(ValidationAcquis, Transfert, EnAttente);
    }

    public String getCodeTypeTrtselected() {
        return codeTypeTrtselected;
    }

    public void setCodeTypeTrtselected(final String codeTypeTrtselected) {
        this.codeTypeTrtselected = codeTypeTrtselected;
    }

    public void setSmtpService(final SmtpService smtpService) {
        this.smtpService = smtpService;
    }

    public void setInscriptionAdm(final InscriptionAdm inscriptionAdm) {
        this.inscriptionAdm = inscriptionAdm;
    }

    public LazyDataModel<IndividuPojo> getIndPojoLDM() {
        return indPojoLDM;
    }

    public IndRechPojo getIndRechPojo() {
        return indRechPojo;
    }

    public boolean isRenderTable() {
        return renderTable;
    }

    public void doNotRenderTable() {
        if (!FacesContext.getCurrentInstance().isPostback())
            this.renderTable = false;
    }

    public void doRenderTable() {
        this.renderTable = true;
    }
}
