/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;

import fj.F;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.dao.ParameterDaoService;
import org.esupportail.opi.domain.beans.BeanProfile;
import org.esupportail.opi.domain.beans.mails.MailContent;
import org.esupportail.opi.domain.beans.parameters.*;
import org.esupportail.opi.domain.beans.parameters.accessRight.*;
import org.esupportail.opi.domain.beans.parameters.situation.TypeContrat;
import org.esupportail.opi.domain.beans.parameters.situation.TypeOrganisme;
import org.esupportail.opi.domain.beans.parameters.situation.TypeSituation;
import org.esupportail.opi.domain.beans.parameters.situation.TypeStatut;
import org.esupportail.opi.domain.beans.references.NombreVoeuCge;
import org.esupportail.opi.domain.beans.references.calendar.Calendar;
import org.esupportail.opi.domain.beans.references.calendar.CalendarCmi;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.calendar.ReunionCmi;
import org.esupportail.opi.domain.beans.references.commission.*;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Constantes;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.*;

import static fj.data.Array.array;
import static java.util.Arrays.asList;


/**
 * The basic implementation of DomainService.
 * <p/>
 * See /properties/domain/domain.xml
 */
public class ParameterServiceImpl implements ParameterService {


    /**
     * The serialization id.
     */
    private static final long serialVersionUID = 2199606452890382789L;

    /**
     * {@link ParameterDaoService}.
     */
    private ParameterDaoService daoService;

    /**
     * see {@link DomainService}.
     */
    private DomainService domainService;

    /**
     * see {@link OrbeonService}.
     */
    private OrbeonService orbeonService;

    /**
     * The list of bean typeConvocations.
     */
    private List<TypeConvocation> typeConvocations;

    /**
     * The list of bean TypeContrat.
     */
    private List<TypeContrat> typeContrats;

    /**
     * The list of bean TypeStatut.
     */
    private List<TypeStatut> typeStatuts;

    /**
     * The list of bean TypeStatut.
     */
    private List<TypeOrganisme> typeOrganismes;

    /**
     * The list of bean TypeStatut.
     */
    private List<TypeSituation> typeSituations;

    /**
     * The default date de retour des dossiers.
     */
    private String dateBackDefault;

    /**
     * The prefix pour le code d'un calendrier.
     */
    private String prefixCodCalCmi;

    /**
     * The prefix.
     */
    private String prefixLibCalCmi;


    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

    /**
     * Bean constructor.
     */
    public ParameterServiceImpl() {
        super();
    }

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() {
        Assert.notNull(this.daoService, "property daoService of class "
                + this.getClass().getName() + " can not be null");
        Assert.notEmpty(this.typeConvocations, "property typeConvocations of class "
                + this.getClass().getName() + " can not be empty");
        Assert.notEmpty(this.typeContrats, "property typeContrats of class "
                + this.getClass().getName() + " can not be empty");
        Assert.notEmpty(this.typeStatuts, "property typeStatuts of class "
                + this.getClass().getName() + " can not be empty");
        Assert.notEmpty(this.typeOrganismes, "property typeOrganismes of class "
                + this.getClass().getName() + " can not be empty");
        Assert.notEmpty(this.typeSituations, "property typeSituations of class "
                + this.getClass().getName() + " can not be empty");
        Assert.notNull(this.domainService, "property domainService of class "
                + this.getClass().getName() + " can not be null");

    }

    // ////////////////////////////////////////////////////////////
    // Profile
    // ////////////////////////////////////////////////////////////

    @Override
    public void addProfile(final Profile profile) {
        if (log.isDebugEnabled()) {
            log.debug("entering addProfile( " + profile + " )");
        }
        daoService.addProfile(profile);

    }

    @Override
    public void updateProfile(final Profile profile) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateProfile( " + profile + " )");
        }
        daoService.updateProfile(profile);

    }

    @Override
    public Profile getProfile(final Integer id, final String code) {
        if (log.isDebugEnabled()) {
            log.debug("entering getProfile( " + id + ", " + code + " )");
        }
        return daoService.getProfile(id, code);

    }

    @Override
    public List<BeanProfile> getProfiles(final Boolean temEnSve) {
        if (log.isDebugEnabled())
            log.debug("entering getProfiles( " + temEnSve + " )");

        final List<Profile> l = daoService.getProfiles(temEnSve);

        return new ArrayList<>(array(l.toArray(new Profile[l.size()]))
                .map(new F<Profile, BeanProfile>() {
                    public BeanProfile f(Profile p) {
                        return new BeanProfile(p);
                    }
                })
                .toCollection());
    }

    @Override
    public void deleteProfile(final Profile profile) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteProfile( " + profile + " )");
        }
        daoService.deleteProfile(profile);

    }

    @Override
    public Boolean profileCodeIsUnique(final Profile profile) {
        if (log.isDebugEnabled()) {
            log.debug("entering profileCodeIsUnique( " + profile + " )");
        }
        for (BeanProfile bp : getProfiles(null)) {
            Profile p = bp.getProfile();
            if (!p.equals(profile) && p.getCode().equals(profile.getCode())) {
                return false;
            }
        }
        return true;
    }

    // ////////////////////////////////////////////////////////////
    // Traitement
    // ////////////////////////////////////////////////////////////

    @Override
    public void addTraitement(final Traitement traitement) {
        if (log.isDebugEnabled()) {
            log.debug("entering addTraitement( " + traitement + " )");
        }
        daoService.addTraitement(traitement);
    }

    @Override
    public void deleteTraitement(final Traitement traitement) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteTraitement( " + traitement + " )");
        }
        daoService.deleteTraitement(traitement);
    }

    @Override
    public Traitement getTraitement(final Integer id) {
        if (log.isDebugEnabled()) {
            log.debug("entering getTraitement( " + id + " )");
        }
        return daoService.getTraitement(id);
    }

    @Override
    public Domain getDomain(final Integer id) {
        if (log.isDebugEnabled()) {
            log.debug("entering getDomain( " + id + " )");
        }
        return daoService.getDomain(id);
    }

    @Override
    public Set<Fonction> getFonctions(final Boolean temEnSve, final Boolean initAccess) {
        if (log.isDebugEnabled()) {
            log.debug("entering getFonction( " + temEnSve + ", " + initAccess
                    + " )");
        }
        Set<Fonction> fct = new HashSet<Fonction>();
        fct.addAll(daoService.getFonctions(temEnSve, initAccess));
        return fct;
    }

    @Override
    public Set<Traitement> getTraitements(final Profile p, final String typeTraitement,
                                          final Domain domain) {
        if (log.isDebugEnabled()) {
            log.debug("entering getTraitements( " + p + ", " + typeTraitement
                    + ", " + domain + " )");
        }
        return new HashSet<Traitement>(daoService.getTraitements(p,
                typeTraitement, domain));
    }

    @Override
    public Set<Domain> getDomains(final Boolean temEnSve, final Boolean initAccess) {
        if (log.isDebugEnabled()) {
            log.debug("entering getDomain( " + temEnSve + ", " + initAccess
                    + " )");
        }
        Set<Domain> l = new TreeSet<Domain>(new Comparator<Traitement>() {
            public int compare(Traitement t1, Traitement t2) {
                int r1 = t1.getRang();
                int r2 = t2.getRang();
                return (r1 > r2) ? 1 : (r1 < r2) ? -1 : 0;
            }

        });
        l.addAll(daoService.getDomains(temEnSve, initAccess));

        return l;
    }

    @Override
    public void updateTraitement(final Traitement traitement) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateFonction( " + traitement + " )");
        }
        daoService.updateTraitement(traitement);
    }

    @Override
    public Boolean traitementCodeIsUnique(final Traitement traitement) {
        if (log.isDebugEnabled()) {
            log.debug("entering traitementCodeIsUnique( " + traitement + " )");
        }
        if (traitement instanceof Fonction) {
            for (Fonction f : getFonctions(null, false)) {
                if (!f.equals(traitement)
                        && f.getCode().equals(traitement.getCode())) {
                    return false;
                }
            }
        } else if (traitement instanceof Domain) {
            for (Domain d : getDomains(null, null)) {
                if (!d.equals(traitement)
                        && d.getRang().equals(traitement.getRang())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public Boolean functionRangIsUnique(final Fonction function, final Integer idDomain) {
        if (log.isDebugEnabled()) {
            log.debug("entering functionRangIsUnique( " + idDomain + " , "
                    + function + " )");
        }
        Domain d = getDomain(idDomain);
        for (Fonction f : d.getFonctions()) {
            if (!f.equals(function) && f.getRang().equals(function.getRang())) {
                return false;
            }
        }

        return true;
    }

    @Override
    public Boolean domainRangIsUnique(final Domain domain) {
        if (log.isDebugEnabled()) {
            log.debug("entering domainRangIsUnique( " + domain + " )");
        }
        for (Domain d : getDomains(null, null)) {
            if (!d.equals(domain) && d.getRang().equals(domain.getRang())) {
                return false;
            }
        }
        return true;
    }

    // ////////////////////////////////////////////////////////////
    // AccessRight
    // ////////////////////////////////////////////////////////////

    @Override
    public void addAccessRight(final AccessRight accessRight) {
        if (log.isDebugEnabled()) {
            log.debug("entering addAccessRight( " + accessRight + " )");
        }
        daoService.addAccessRight(accessRight);
    }

    @Override
    public void updateAccessRight(final AccessRight accessRight) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateAccessRight( " + accessRight + " )");
        }
        daoService.updateAccessRight(accessRight);
    }

    // ////////////////////////////////////////////////////////////
    // Profile
    // ////////////////////////////////////////////////////////////

    @Override
    public void addNomenclature(final Nomenclature nomenclature) {
        if (log.isDebugEnabled()) {
            log.debug("entering addNomenclature( " + nomenclature + " )");
        }
        daoService.addNomenclature(nomenclature);
    }

    @Override
    public void deleteNomenclature(final Nomenclature nomenclature) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteNomenclature( " + nomenclature + " )");
        }
        daoService.deleteNomenclature(nomenclature);
    }


    @Override
    public boolean canDeleteNomclature(final Nomenclature nom) {
        if (log.isDebugEnabled()) {
            log.debug("entering canDeleteNomclature nom = " + nom);
        }

        if (nom instanceof TypeDecision) {
            return daoService.canDeleteTypeDecision((TypeDecision) nom);
        } else if (nom instanceof MotivationAvis) {
            return daoService.canDeleteMotivation((MotivationAvis) nom);
        }
        return true;
    }

    @Override
    public Nomenclature getNomenclature(final Integer id) {
        if (log.isDebugEnabled()) {
            log.debug("entering getNomenclature( " + id + " )");
        }
        return daoService.getNomenclature(id);
    }

    @Override
    public Set<PieceJustificative> getPJs(final Boolean temEnSve) {
        if (log.isDebugEnabled()) {
            log.debug("entering getPJs( " + temEnSve + " )");
        }
        List<Nomenclature> nomList = daoService.getNomenclatures(temEnSve,
                PieceJustificative.class);
        Set<PieceJustificative> t = new HashSet<PieceJustificative>();
        for (Nomenclature n : nomList) {
            t.add((PieceJustificative) n);
        }

        return t;
    }


    @Override
    public List<PieceJustificative> getPiecesJ(final Set<VersionEtpOpi> vet, final String codeRI) {
        List<PieceJustificative> pj = new ArrayList<PieceJustificative>();
        Set<PieceJustificative> pjInUse = getPJs(true);
        for (PieceJustificative p : pjInUse) {
            // on garde les piÃÂ¨ces du rÃÂ©gime
            if (codeRI == null || codeRI.equals(String.valueOf(p.getCodeRI()))) {
                if (p.getIsForAllVet()) {
                    pj.add(p);
                } else {
                    for (PieceJustiVet pVet : p.getVersionEtapes()) {
                        if (vet.contains(pVet.getVersionEtpOpi())) {
                            pj.add(p);
                            break;
                        }
                    }

                }
            }
        }

        return pj;
    }

    @Override
    public List<PieceJustificative> getPiecesJ(VersionEtpOpi vetOpi, final String codeRI) {
        List<PieceJustificative> pj = new ArrayList<PieceJustificative>();
        Set<PieceJustificative> pjInUse = getPJs(true);
        for (PieceJustificative p : pjInUse) {
            // on garde les piÃÂ¨ces du rÃÂ©gime
            if (codeRI == null || codeRI.equals(String.valueOf(p.getCodeRI()))) {
                if (p.getIsForAllVet()) {
                    pj.add(p);
                } else {
                    for (PieceJustiVet pVet : p.getVersionEtapes()) {
                        if (vetOpi.equals(pVet.getVersionEtpOpi())) {
                            pj.add(p);
                            break;
                        }
                    }

                }
            }
        }

        return pj;
    }

    @Override
    public Set<MotivationAvis> getMotivationsAvis(final Boolean temEnSve) {
        if (log.isDebugEnabled()) {
            log.debug("entering getMotivationAvis( " + temEnSve + " )");
        }
        List<Nomenclature> nomList = daoService.getNomenclatures(temEnSve,
                MotivationAvis.class);
        Set<MotivationAvis> t = new HashSet<MotivationAvis>();
        for (Nomenclature n : nomList) {
            t.add((MotivationAvis) n);
        }

        return t;
    }

    @Override
    public Set<Campagne> getCampagnes(final Boolean temEnSve, final String codeRI) {
        if (log.isDebugEnabled()) {
            log.debug("entering getCampagnes( " + temEnSve + " )");
        }
        List<Nomenclature> nomList = daoService.getNomenclatures(temEnSve,
                Campagne.class);
        Set<Campagne> c = new HashSet<Campagne>();
        for (Nomenclature n : nomList) {
            Campagne camp = (Campagne) n;
            if (codeRI == null || camp.getCodeRI() == Integer.parseInt(codeRI)) {
                c.add(camp);
            }
        }

        return c;
    }

    @Override
    public Campagne getCampagneEnServ(final int codeRI) {
        if (log.isDebugEnabled()) {
            log.debug("entering getCampagneEnServ( " + codeRI + " )");
        }
        List<Nomenclature> nomList = daoService.getNomenclatures(true,
                Campagne.class);
        Campagne camp = null;
        for (Nomenclature n : nomList) {
            Campagne c = (Campagne) n;
            if (c.getCodeRI() == codeRI) {
                // TODO YL 22/11/2010: ÃÂÃÂ  modifier par une rÃÂÃÂ©cup de la liste
                if (camp == null
                        || (Integer.parseInt(camp.getCodAnu())
                        < Integer.parseInt(c.getCodAnu()))) {
                    camp = c;
                }
            }
        }

        return camp;
    }


    @Override
    public Boolean isCampagnesFIAndFCEnServ() {
        if (log.isDebugEnabled()) {
            log.debug("entering isCampagnesFIAndFCEnServ()");
        }
        Boolean isOK = false;
        Set<Campagne> campagnes = getCampagnes(Boolean.TRUE, null);
        if (campagnes != null && campagnes.size() > 1) {
            Boolean isFC = false;
            Boolean isFI = false;
            for (Campagne camp : campagnes) {
                //TODO : Fix this !!
                if (camp.getCodeRI() == 0) { //FormationInitiale.CODE){
                    isFI = true;
                } else if (camp.getCodeRI() == 1) { //FormationContinue.CODE){
                    isFC = true;
                }
            }
            isOK = isFI && isFC;
        }

        return isOK;
    }

    @Override
    public Set<Campagne> getCampagnesAnu(final String codAnu) {
        if (log.isDebugEnabled()) {
            log.debug("entering getCampagnesAnu( " + codAnu + " )");
        }
        List<Nomenclature> nomList = daoService.getNomenclatures(null,
                Campagne.class);
        Set<Campagne> c = new HashSet<Campagne>();
        for (Nomenclature n : nomList) {
            Campagne camp = (Campagne) n;
            if (camp.getCodAnu().equals(codAnu)) {
                c.add(camp);
            }
        }

        return c;
    }

    @Override
    public Set<TypeDecision> getTypeDecisions(final Boolean temEnSve) {
        if (log.isDebugEnabled()) {
            log.debug("entering getTypeDecisions( " + temEnSve + " )");
        }
        List<Nomenclature> nomList = daoService.getNomenclatures(temEnSve,
                TypeDecision.class);
        Set<TypeDecision> t = new HashSet<TypeDecision>();
        for (Nomenclature n : nomList) {
            t.add((TypeDecision) n);
        }

        return t;
    }

    @Override
    public void updateNomenclature(final Nomenclature nomenclature) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateNomenclature( " + nomenclature + " )");
        }
        daoService.updateNomenclature(nomenclature);
    }

    @Override
    public Boolean nomenclatureCodeIsUnique(final Nomenclature nomenclature) {
        if (log.isDebugEnabled()) {
            log.debug("entering nomenclatureCodeIsUnique( " + nomenclature
                    + " )");
        }
        //TODO revoir car n'utilise pas le cache + trop de code repete
        if (nomenclature instanceof TypeDecision) {
            Set<TypeDecision> list = getTypeDecisions(null);
            for (TypeDecision t : list) {
                if (!t.equals(nomenclature)
                        && t.getCode().equals(nomenclature.getCode())) {
                    return false;
                }
            }
        } else if (nomenclature instanceof MotivationAvis) {
            Set<MotivationAvis> list = getMotivationsAvis(null);
            for (MotivationAvis t : list) {
                if (!t.equals(nomenclature)
                        && t.getCode().equals(nomenclature.getCode())) {
                    return false;
                }
            }
        } else if (nomenclature instanceof PieceJustificative) {
            Set<PieceJustificative> list = getPJs(null);
            for (PieceJustificative t : list) {
                if (!t.equals(nomenclature)
                        && t.getCode().equals(nomenclature.getCode())) {
                    return false;
                }
            }
        }
        return true;
    }

    // ////////////////////////////////////////////////////////////
    // Commission
    // ////////////////////////////////////////////////////////////

    @Override
    public void addCommission(final Commission commission) {
        if (log.isDebugEnabled()) {
            log.debug("entering addCommission( " + commission + " )");
        }

        // on crée le calendrier de la commission par défaut
        CalendarCmi calendarCmi = new CalendarCmi();
        calendarCmi.setCode(prefixCodCalCmi + commission.getCode());
        calendarCmi.setLibelle(prefixLibCalCmi + commission.getLibelle());
        SimpleDateFormat formatter = new SimpleDateFormat(Constantes.DATE_FORMAT);
        ParsePosition pos = new ParsePosition(0);
        Date dateBack = formatter.parse(dateBackDefault, pos);
        calendarCmi.setDatEndBackDossier(dateBack);
        calendarCmi.setCommission(commission);

        calendarCmi = domainService.add(calendarCmi,
                "Batch createMissingCalendarCmi");
        addCalendar(calendarCmi);

        commission.setCalendarCmi(calendarCmi);

        daoService.addCommission(commission);
    }

    @Override
    public void deleteCommission(final Commission commission) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteCommission( " + commission + " )");
        }
        Commission c = getCommission(commission.getId(), null);

        //delete the calendar
        for (CalendarIns cal : getCalendarIns(c)) {
            cal.getCommissions().remove(c);
        }

        //delete allMissingPiece
        List<MissingPiece> mp = domainService.getMissingPiece(null, c);
        domainService.deleteMissingPiece(mp, null);

        //delete the manager rights
        for (Gestionnaire g : domainService.getManagersByCmi(c)) {
            g.getRightOnCmi().remove(c);
        }

        daoService.deleteCommission(c);
    }

    @Override
    public Commission getCommission(final Integer id, final String code) {
        if (log.isDebugEnabled()) {
            log.debug("entering getCommission( " + id + ", " + code + " )");
        }
        return daoService.getCommission(id, code);
    }

    @Override
    public Set<Commission> getCommissions(final Boolean temEnSve) {
        if (log.isDebugEnabled()) {
            log.debug("entering getCommissions( " + temEnSve + " )");
        }
        return new HashSet<>(daoService.getCommissions(temEnSve));
    }


    @Override
    public void updateCommission(final Commission commission) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateCommission( " + commission + " )");
        }
        daoService.updateCommission(commission);
    }


    @Override
    //TODO : get rid of this !
    public Boolean commissionCodeIsUnique(final Commission commission) {
        if (log.isDebugEnabled()) {
            log.debug("entering commissionCodeIsUnique( " + commission + " )");
        }

        Set<Commission> list = getCommissions(null);
        for (Commission c : list) {
            if (!c.equals(commission)
                    && c.getCode().equals(commission.getCode())) {
                return false;
            }
        }
        return true;
    }

    //////////////////////////////////////////////////////////////
    // ContactCommission
    //////////////////////////////////////////////////////////////

    @Override
    public void addContactCommission(final ContactCommission contactCommission) {
        if (log.isDebugEnabled()) {
            log.debug("entering addContactCommission( " + contactCommission + " )");
        }
        this.daoService.addContactCommission(contactCommission);

    }

    @Override
    public void updateContactCommission(final ContactCommission contactCommission) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateContactCommission( " + contactCommission + " )");
        }
        this.daoService.updateContactCommission(contactCommission);

    }
    // ////////////////////////////////////////////////////////////
    // Member
    // ////////////////////////////////////////////////////////////

    @Override
    public void addMember(final Member member) {
        if (log.isDebugEnabled()) {
            log.debug("entering addMember( " + member + " )");
        }
        daoService.addMember(member);
    }

    @Override
    public void updateMember(final Member member) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateMember( " + member + " )");
        }
        daoService.updateMember(member);
    }

    @Override
    public void deleteMember(final List<Member> member) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteMember( " + member + " )");
        }
        for (Member m : member) {
            daoService.deleteMember(m);
        }


    }

    // ////////////////////////////////////////////////////////////
    // TraitementCmi
    // ////////////////////////////////////////////////////////////

    @Override
    public void addTraitementCmi(final TraitementCmi traitementCmi) {
        if (log.isDebugEnabled()) {
            log.debug("entering addTraitementCmi( " + traitementCmi + " )");
        }
        daoService.addTraitementCmi(traitementCmi);

    }

    @Override
    public void updateTraitementCmi(final TraitementCmi traitementCmi) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateTraitementCmi( " + traitementCmi + " )");
        }
        daoService.updateTraitementCmi(traitementCmi);
    }

    @Override
    public void deleteTraitementCmi(final List<TraitementCmi> traitementCmi) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteTraitementCmi( " + traitementCmi + " )");
        }
        for (TraitementCmi t : traitementCmi) {
            daoService.deleteTraitementCmi(t);
        }
    }

    @Override
    public TraitementCmi getTraitementCmi(
            final VersionEtpOpi versionEtpOpi,
            final Boolean initSelection) {
        if (log.isDebugEnabled()) {
            log.debug("entering getTraitementCmi( " + versionEtpOpi + "," + initSelection + " )");
        }
        return daoService.getTraitementCmi(versionEtpOpi, null, initSelection);
    }

    @Override
    public TraitementCmi getTraitementCmi(final Integer id) {
        if (log.isDebugEnabled()) {
            log.debug("entering getTraitementCmi( " + id + " )");
        }
        return daoService.getTraitementCmi(id);
    }

    @Override
    public Boolean isConnectCmi(final TraitementCmi t) {
        if (log.isDebugEnabled()) {
            log.debug("entering isConnectCmi( " + t + " )");
        }
        for (Commission c : getCommissions(null)) {
            if (c.getTraitementCmi().contains(t)) {
                return true;
            }
        }
        return false;
    }

    // ////////////////////////////////////////////////////////////
    // Calendar
    // ////////////////////////////////////////////////////////////

    @Override
    public void addCalendar(final Calendar calendar) {
        if (log.isDebugEnabled()) {
            log.debug("entering addCalendar( " + calendar + " )");
        }
        daoService.addCalendar(calendar);
    }

    @Override
    public void updateCalendar(final Calendar calendar) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateCalendar( " + calendar + " )");
        }
        daoService.updateCalendar(calendar);
    }

    @Override
    public List<Calendar> getCalendars(final Boolean temEnSve, final String typeCal) {
        if (log.isDebugEnabled()) {
            log.debug("entering getCalendars( " + temEnSve + ", " + typeCal + " )");
        }
        return daoService.getCalendars(temEnSve, typeCal);
    }


    @Override
    public Boolean calendarCodeIsUnique(final Calendar calendar) {
        if (log.isDebugEnabled()) {
            log.debug("entering calendarCodeIsUnique( " + calendar
                    + " )");
        }

        if (calendar instanceof CalendarIns) {
            List<Calendar> list = getCalendars(true, Calendar.TYPE_CAL_INSCRIPTION);
            for (Calendar c : list) {
                if (!c.equals(calendar)
                        && c.getCode().equals(calendar.getCode())) {
                    return false;
                }
            }
        } else if (calendar instanceof CalendarCmi) {
            List<Calendar> list = getCalendars(true, Calendar.TYPE_CAL_COMMISSION);
            for (Calendar c : list) {
                if (!c.equals(calendar)
                        && c.getCode().equals(calendar.getCode())) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public void deleteCalendar(final Calendar calendar) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteCalendar( " + calendar + " )");
        }
        if (calendar instanceof CalendarIns) {
            CalendarIns cIns = (CalendarIns) daoService.getCalendar(calendar.getId());
            for (Commission c : cIns.getCommissions()) {
                updateCommission(c);
            }
        } else if (calendar instanceof CalendarCmi) {
            CalendarCmi cCmi = (CalendarCmi) daoService.getCalendar(calendar.getId());
            cCmi.getCommission().setCalendarCmi(null);
            updateCommission(cCmi.getCommission());
        }

        daoService.deleteCalendar(calendar);
    }


    @Override
    public Set<CalendarIns> getCalendars(final VersionEtpOpi versionEtpOpi) {
        if (log.isDebugEnabled()) {
            log.debug("entering getCalendars( " + versionEtpOpi + " )");
        }
        return daoService.getCalendars(versionEtpOpi);
    }

    @Override
    public List<CalendarIns> getCalendarIns(final Commission commission) {
        if (log.isDebugEnabled()) {
            log.debug("entering getCalendarIns( " + commission + " )");
        }
        return daoService.getCalendarIns(commission);
    }

    // ////////////////////////////////////////////////////////////
    // FormulaireCmi
    // ////////////////////////////////////////////////////////////

    @Override
    public void addFormulaireCmi(final FormulaireCmi form) {
        if (log.isDebugEnabled()) {
            log.debug("entering addFormulaireCmi( " + form + " )");
        }
        daoService.addFormulaire(form);
    }

    @Override
    public void deleteFormulaireCmi(final FormulaireCmi form) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteFormulaireCmi( " + form + " )");
        }
        daoService.deleteFormulaire(form);
    }

    @Override
    public Map<VersionEtpOpi, FormulaireCmi> getFormulairesCmi(
            final Set<TraitementCmi> traitements, final Integer codeRI) {
        if (log.isDebugEnabled()) {
            log.debug("entering getFormulairesCmi( " + traitements + " )");
        }
        Map<VersionEtpOpi, FormulaireCmi> map = new HashMap<VersionEtpOpi, FormulaireCmi>();
        Set<VersionEtpOpi> versionsEtpOpi = null;
        // Si des traitements sont passes en param, on filtre sur les vEtpOpi
        if (traitements != null && !traitements.isEmpty()) {
            versionsEtpOpi = new HashSet<VersionEtpOpi>();
            for (TraitementCmi traitementCmi : traitements) {
                versionsEtpOpi.add(traitementCmi.getVersionEtpOpi());
            }
            if (log.isDebugEnabled()) {
                log.debug("versionsEtpOpi : " + versionsEtpOpi);
            }
        }
        for (FormulaireCmi form : daoService.getFormulairesCmi(versionsEtpOpi, codeRI)) {
            map.put(form.getVersionEtpOpi(), form);
        }
        return map;
    }

    @Override
    public void addIndFormulaire(final IndFormulaire formNorme) {
        if (log.isDebugEnabled()) {
            log.debug("");
        }
        daoService.addIndFormulaire(formNorme);
    }

    @Override
    public void deleteIndFormulaire(final IndFormulaire form, final String numDoss,
                                    final String sLabelRI) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteIndFormulaire form =  " + form);
        }
        daoService.deleteIndFormulaire(form);

        // on supprime ensuite le formulaire dans la base Orbeon
        String formName = form.getVersionEtpOpi().getCodEtp() + "-"
                + form.getVersionEtpOpi().getCodVrsVet() + "-" + sLabelRI;

        try {
            orbeonService.removeResponse(formName, numDoss);
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    public final IndFormulaire findIndFormulaireById(final Integer id) {
        return daoService.findIndFormulaireById(id);
    }

    @Override
    public Map<VersionEtpOpi, IndFormulaire> getIndFormulaires(
            final Individu indSelected) {
        if (log.isDebugEnabled()) {
            log.debug("entering getIndFormulaires( " + indSelected + " )");
        }
        Map<VersionEtpOpi, IndFormulaire> map = new HashMap<VersionEtpOpi, IndFormulaire>();

        for (IndFormulaire form : daoService.getIndFormulaires(indSelected)) {
            map.put(form.getVersionEtpOpi(), form);
        }


        return map;
    }

    public boolean isExitFormulaireInd(final Individu indSelected, final VersionEtpOpi vet) {
        return daoService.isExitFormulaireInd(indSelected, vet);
    }

    @Override
    public boolean isExitFormulaireEtp(final VersionEtpOpi vet, final String codeRI) {
        return daoService.isExitFormulaireEtp(vet, codeRI);
    }

    @Override
    public boolean isAllFormulairesCreated(final Individu indSelected,
                                           final String codeRI) {
//		Integer nbFormsToCreate = daoService.nbFormulairesToCreateForIndividu(
//				indSelected.getVoeux(), ri);
        Integer nbFormsToCreate = 0;
        Integer nbFormsCreated = 0;
        for (IndVoeu indVoeu : indSelected.getVoeux()) {
            TraitementCmi trt = getTraitementCmi(indVoeu.getLinkTrtCmiCamp()
                    .getTraitementCmi().getId());
            if (isExitFormulaireEtp(trt.getVersionEtpOpi(), codeRI)) {
                nbFormsToCreate++;
            }
            if (isExitFormulaireInd(indSelected, trt.getVersionEtpOpi())) {
                nbFormsCreated++;
            }
        }
//		Integer nbFormsCreated = daoService.nbFormulairesCreatedByIndividu(
//				indSelected, indSelected.getVoeux());

        return nbFormsToCreate.equals(nbFormsCreated);
    }

    @Override
    public boolean isAllFormulairesCreatedByTraitementsCmi(
            final Individu indSelected, final Integer codeRI,
            final Set<TraitementCmi> traitementsCmi) {

        boolean allFormsCreated = true;

        Map<VersionEtpOpi, FormulaireCmi> mapForms = getFormulairesCmi(
                traitementsCmi, codeRI);
        Map<VersionEtpOpi, IndFormulaire> mapFormInd = getIndFormulaires(indSelected);

        for (Map.Entry<VersionEtpOpi, FormulaireCmi> e : mapForms.entrySet()) {
            IndFormulaire indForm = mapFormInd.get(e.getKey());
            if (indForm == null) {
                allFormsCreated = false;
                break;
            }
        }
        return allFormsCreated;
    }

    @Override
    public Map<String, List<String>> getAllFormNamesMap(final Campagne camp,
                                                        final String sLabelRI) {
        if (log.isDebugEnabled()) {
            log.debug("entering getIndFormulaires( " + camp + " )");
        }
        Map<String, List<String>> mapFormName = new HashMap<String, List<String>>();
        for (IndFormulaire form : daoService.getIndFormulaires(camp)) {
            // Préparation de l'entrée dans la map
            String numDoss = form.getIndividu().getNumDossierOpi();
            String formName = form.getVersionEtpOpi().getCodEtp() + "-"
                    + form.getVersionEtpOpi().getCodVrsVet() + "-" + sLabelRI;
            if (!mapFormName.containsKey(numDoss)) {
                mapFormName.put(numDoss, new ArrayList<String>());
            }
            mapFormName.get(numDoss).add(formName);
        }
        return mapFormName;
    }

    @Override
    public void purgeIndFormulaireCamp(final Campagne camp) {
        if (log.isDebugEnabled()) {
            log.debug("entering purgeIndFormulaireCamp( " + camp + " )");
        }
        // Purge de la table IND_FORMULAIRE
        daoService.purgeIndFormulaireCamp(camp);
    }

    // ////////////////////////////////////////////////////////////
    // ReunionCmi
    // ////////////////////////////////////////////////////////////

    @Override
    public void addReunionCmi(final ReunionCmi reunionCmi) {
        if (log.isDebugEnabled()) {
            log.debug("entering addReunionCmi( " + reunionCmi + " )");
        }
        daoService.addReunionCmi(reunionCmi);
    }

    @Override
    public void deleteReunionCmi(final List<ReunionCmi> reunionCmi) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteReunionCmi( " + reunionCmi + " )");
        }
        for (ReunionCmi r : reunionCmi) {
            daoService.deleteReunionCmi(r);
        }
    }

    @Override
    public void updateReunionCmi(final ReunionCmi reunionCmi) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateReunionCmi( " + reunionCmi + " )");
        }
        daoService.updateReunionCmi(reunionCmi);
    }

    //////////////////////////////////////////////////////////////
    // PieceJustiVet
    //////////////////////////////////////////////////////////////

    @Override
    public void deletePieceJustiVet(final PieceJustiVet pieceJustiVet) {
        daoService.deletePieceJustiVet(pieceJustiVet);
    }

    @Override
    public void deletePieceJustiVetWithFlush(final PieceJustiVet pieceJustiVet) {
        daoService.deletePieceJustiVetWithFlush(pieceJustiVet);
    }


    // ////////////////////////////////////////////////////////////
    // NombreVoeuCge
    // ////////////////////////////////////////////////////////////

    @Override
    public List<NombreVoeuCge> getAllNombreDeVoeuByCge() {
        return daoService.getAllNombreDeVoeuByCge();
    }

    @Override
    public void addNombreVoeuCge(final NombreVoeuCge nombreVoeuCge) {
        if (log.isDebugEnabled()) {
            log.debug("entering addNombreVoeuCge( " + nombreVoeuCge + " )");
        }
        daoService.addNombreVoeuCge(nombreVoeuCge);
    }

    @Override
    public void deleteNombreVoeuCge(final NombreVoeuCge nombreVoeuCge) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteNombreVoeuCge( " + nombreVoeuCge + " )");
        }
        daoService.deleteNombreVoeuCge(nombreVoeuCge);
    }

    @Override
    public void updateNombreVoeuCge(final NombreVoeuCge nombreVoeuCge) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateNombreVoeuCge( " + nombreVoeuCge + " )");
        }
        daoService.updateNombreVoeuCge(nombreVoeuCge);
    }

    //////////////////////////////////////////////////////////////
    // MailContent
    //////////////////////////////////////////////////////////////

    @Override
    public MailContent getMailContent(final String code) {
        if (log.isDebugEnabled()) {
            log.debug("entering getMailContent( " + code + " )");
        }
        return daoService.getMailContent(code);
    }

    @Override
    public void addMailContent(final MailContent mailContent) {
        if (log.isDebugEnabled()) {
            log.debug("entering addMailContent( " + mailContent + " )");
        }
        daoService.addMailContent(mailContent);

    }

    @Override
    public List<MailContent> getMailContents() {
        if (log.isDebugEnabled()) {
            log.debug("entering getMailContents()");
        }
        return daoService.getMailContents();
    }

    @Override
    public void updateMailContent(final MailContent mailContent) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateMailContent( " + mailContent + " )");
        }
        daoService.updateMailContent(mailContent);

    }

    //////////////////////////////////////////////////////////////
    // CalendarRDV
    //////////////////////////////////////////////////////////////

    @Override
    public List<CalendarRDV> getCalendarRdv() {
        return daoService.getCalendarRdv();
    }

    @Override
    public void updateCalendarRdv(final CalendarRDV cal) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateCalendarRdv( " + cal + " )");
        }
        daoService.updateCalendarRdv(cal);
    }

    @Override
    public void deleteCalendarRdv(final CalendarRDV cal) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteCalendarRdv( " + cal + " )");
        }
        daoService.deleteCalendarRdv(cal);
    }

    @Override
    public void addCalendarRdv(final CalendarRDV cal) {
        if (log.isDebugEnabled()) {
            log.debug("entering addCalendarRdv( " + cal + " )");
        }
        daoService.addCalendarRdv(cal);
    }

    @Override
    public int getListEtudiantsParCalendarRdvParPeriode(
            final int idCalendarRdv, final int month, final Date dateDebut, final Date dateFin) {
        return daoService.getListEtudiantsParCalendarRdvParPeriode(idCalendarRdv, month, dateDebut, dateFin);
    }

    @Override
    public int getListEtudiantsParCalendarRdvParPeriode(
            final int idCalendarRdv, final Date dateDuJour, final Date dateDebut, final Date dateFin) {
        return daoService.getListEtudiantsParCalendarRdvParPeriode(
                idCalendarRdv, dateDuJour, dateDebut, dateFin);
    }

    @Override
    public int getListEtudiantsParCalendarRdvParDemiJournee(
            final int idCalendarRdv, final int month, final Date date, final Date dateDebut, final Date dateFin) {
        return daoService.getListEtudiantsParCalendarRdvParDemiJournee(
                idCalendarRdv, month, date, dateDebut, dateFin);
    }

    //////////////////////////////////////////////////////////////
    // misc
    //////////////////////////////////////////////////////////////

    public void setDaoService(final ParameterDaoService daoService) {
        this.daoService = daoService;
    }

    public void setDomainService(final DomainService domainService) {
        this.domainService = domainService;
    }

    public void setOrbeonService(final OrbeonService orbeonService) {
        this.orbeonService = orbeonService;
    }

    public List<TypeConvocation> getTypeConvocations() {
        return typeConvocations;
    }

    public void setTypeConvocations(final List<TypeConvocation> typeConvocations) {
        this.typeConvocations = typeConvocations;
    }

    public List<TypeContrat> getTypeContrats() {
        return typeContrats;
    }

    public void setTypeContrats(final List<TypeContrat> typeContrats) {
        this.typeContrats = typeContrats;
    }

    public List<TypeStatut> getTypeStatuts() {
        return typeStatuts;
    }

    public void setTypeStatuts(final List<TypeStatut> typeStatuts) {
        this.typeStatuts = typeStatuts;
    }

    public List<TypeOrganisme> getTypeOrganismes() {
        return typeOrganismes;
    }

    public void setTypeOrganismes(final List<TypeOrganisme> typeOrganismes) {
        this.typeOrganismes = typeOrganismes;
    }

    public List<TypeSituation> getTypeSituations() {
        return typeSituations;
    }

    public void setTypeSituations(final List<TypeSituation> typeSituations) {
        this.typeSituations = typeSituations;
    }

    @Override
    public String getDateBackDefault() {
        return dateBackDefault;
    }

    public void setDateBackDefault(final String dateBackDefault) {
        this.dateBackDefault = dateBackDefault;
    }

    public String getPrefixCodCalCmi() {
        return prefixCodCalCmi;
    }

    public void setPrefixCodCalCmi(final String prefixCodCalCmi) {
        this.prefixCodCalCmi = prefixCodCalCmi;
    }

    public String getPrefixLibCalCmi() {
        return prefixLibCalCmi;
    }

    public void setPrefixLibCalCmi(final String prefixLibCalCmi) {
        this.prefixLibCalCmi = prefixLibCalCmi;
    }

    //////////////////////////////////////////////////////////////
    // LinkTrtCmiCamp
    //////////////////////////////////////////////////////////////

    @Override
    public void addLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
        if (log.isDebugEnabled()) {
            log.debug("entering addLinkTrtCmiCamp ( " + linkTrtCmiCamp + ")");
        }
        daoService.addLinkTrtCmiCamp(linkTrtCmiCamp);
    }

    @Override
    public void deleteLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
        if (log.isDebugEnabled()) {
            log.debug("entering deleteLinkTrtCmiCamp ( " + linkTrtCmiCamp + ")");
        }
        daoService.deleteLinkTrtCmiCamp(linkTrtCmiCamp);
    }

    @Override
    public LinkTrtCmiCamp getLinkTrtCmiCamp(final TraitementCmi traitementCmi,
                                            final Campagne campagne) {
        if (log.isDebugEnabled()) {
            log.debug("entering getLinkTrtCmiCamp ( " + traitementCmi
                    + ", " + campagne + " )");
        }
        return daoService.getLinkTrtCmiCamp(traitementCmi, campagne);
    }

    @Override
    public void updateLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateLinkTrtCmiCamp ( " + linkTrtCmiCamp + ")");
        }
        daoService.updateLinkTrtCmiCamp(linkTrtCmiCamp);
    }

    @Override
    public void updateLinkTrtCmiCampTemEnServ(final Campagne camp, final boolean temEnServ) {
        if (log.isDebugEnabled()) {
            log.debug("entering updateLinkTrtCmiCampTemEnServ( " + camp + ", " + temEnServ + " )");
        }
        daoService.updateLinkTrtCmiCampTemEnServ(camp, temEnServ);
    }

    //////////////////////////////////////////////////////////////
    // AutoListPrincipale
    //////////////////////////////////////////////////////////////

    @Override
    public List<AutoListPrincipale> getAutoListPrincipale() {
        if (log.isDebugEnabled()) {
            log.debug("");
        }
        return daoService.getAutoListPrincipale();
    }

    @Override
    public AutoListPrincipale getAutoListPrincipale(final IndVoeu indVoeu) {
        if (log.isDebugEnabled()) {
            log.debug("");
        }
        return daoService.getAutoListPrincipale(indVoeu);
    }


}
