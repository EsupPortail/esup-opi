package org.esupportail.opi.web.controllers.opinions;

import fj.P1;
import fj.data.Array;
import fj.data.Stream;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.*;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.pojo.*;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.controllers.user.IndividuController;
import org.esupportail.opi.web.utils.paginator.LazyDataModel;
import org.esupportail.opi.web.utils.paginator.PaginationFunctions;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.esupportail.opi.domain.beans.etat.EtatVoeu.EtatNull;
import static org.esupportail.opi.web.utils.fj.Conversions.individuToPojo;
import static org.esupportail.opi.web.utils.paginator.LazyDataModel.lazyModel;

/**
 * @author tducreux
 *         TypeTraitController :
 */
public class TypeTraitController extends AbstractContextAwareController {
    private static final long serialVersionUID = 5545836516397172544L;

    /**
     * Select all the type treatment if the type.
     */
    private String codeTypeTrtselected;

    /**
     * type Traitement ET (Bean).
     */
    private TypeTraitement typeTraitementVA;

    /**
     * type Traitement ET (Bean).
     */
    private TypeTraitement typeTraitementTR;

    /**
     * type Traitement ET (Bean).
     */
    private TypeTraitement typeTraitementET;

    private InscriptionAdm inscriptionAdm;

    /**
     * Type treatment VA and TR.
     */
    private List<TypeTraitement> typeTraitements;

    private SmtpService smtpService;

    private boolean renderTable = false;

    private IndRechPojo indRechPojo = new IndRechPojo();

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
            .map(individuToPojo(getDomainApoService(), getParameterService()));

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
            if (typeT instanceof Transfert) {
                htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY1");
                htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY2",
                        i.getCampagneEnServ(getDomainService()).getCodAnu());
                // coordonnees commission
                htmlBody += htmlCoordonnees;
                htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY3");
            } else if (typeT instanceof ValidationAcquis) {
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
                    if (!codeTrt.equals(this.typeTraitementET.getCode())) {
                        // voeu non traite qui a ete modifie
                        if (codeTrt.equals(this.typeTraitementVA.getCode())) {
                            lesVA.add(v);
                        } else if (codeTrt.equals(this.typeTraitementTR.getCode())) {
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
                    //on met tous les temps le voeu e jour
                    getDomainService().updateIndVoeu(v.getIndVoeu());
                }
            }
            if (!lesVA.isEmpty()) {
                sendMails(i, camp, lesVA, typeTraitementVA);
            }
            if (!lesTR.isEmpty()) {
                sendMails(i, camp, lesTR, typeTraitementTR);
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

    public String getCodeTypeTrtselected() {
        return codeTypeTrtselected;
    }

    public void setCodeTypeTrtselected(final String codeTypeTrtselected) {
        this.codeTypeTrtselected = codeTypeTrtselected;
    }

    public void setTypeTraitementVA(final TypeTraitement typeTraitementVA) {
        this.typeTraitementVA = typeTraitementVA;
    }

    public void setTypeTraitementTR(final TypeTraitement typeTraitementTR) {
        this.typeTraitementTR = typeTraitementTR;
    }

    public void setTypeTraitementET(final TypeTraitement typeTraitementET) {
        this.typeTraitementET = typeTraitementET;
    }

    public void setSmtpService(final SmtpService smtpService) {
        this.smtpService = smtpService;
    }

    public List<TypeTraitement> getTypeTraitements() {
        return typeTraitements;
    }

    public void setTypeTraitements(final List<TypeTraitement> typeTraitement) {
        this.typeTraitements = typeTraitement;
    }

    public void setInscriptionAdm(final InscriptionAdm inscriptionAdm) {
        this.inscriptionAdm = inscriptionAdm;
    }

    public LazyDataModel<IndividuPojo> getIndPojoLDM() {
        return indPojoLDM;
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
