/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.xml.parsers.ParserConfigurationException;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.dao.ParameterDaoService;
import org.esupportail.opi.domain.beans.BeanProfile;
import org.esupportail.opi.domain.beans.mails.MailContent;
import org.esupportail.opi.domain.beans.parameters.AutoListPrincipale;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.Nomenclature;
import org.esupportail.opi.domain.beans.parameters.PieceJustiVet;
import org.esupportail.opi.domain.beans.parameters.PieceJustificative;
import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.Domain;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
import org.esupportail.opi.domain.beans.parameters.situation.TypeContrat;
import org.esupportail.opi.domain.beans.parameters.situation.TypeOrganisme;
import org.esupportail.opi.domain.beans.parameters.situation.TypeSituation;
import org.esupportail.opi.domain.beans.parameters.situation.TypeStatut;
import org.esupportail.opi.domain.beans.references.NombreVoeuCge;
import org.esupportail.opi.domain.beans.references.calendar.Calendar;
import org.esupportail.opi.domain.beans.references.calendar.CalendarCmi;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.calendar.ReunionCmi;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.ContactCommission;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.Member;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Constantes;
import org.xml.sax.SAXException;



/**
 * The basic implementation of DomainService.
 * 
 * See /properties/domain/domain.xml
 */
public class ParameterServiceImpl extends AbstractDomainService implements ParameterService {


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
	 * The list of bean TypeTraitement.
	 */
	private List<TypeTraitement> typeTraitements;
	
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
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.notNull(this.daoService, "property daoService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.typeTraitements, "property typeTraitements of class " 
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#addProfile(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Profile)
	 */
	public void addProfile(final Profile profile) {
		if (log.isDebugEnabled()) {
			log.debug("entering addProfile( " + profile + " )");
		}
		daoService.addProfile(profile);

	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#updateProfile(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Profile)
	 */
	public void updateProfile(final Profile profile) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateProfile( " + profile + " )");
		}
		daoService.updateProfile(profile);

	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getProfile(java.lang.Integer, java.lang.String)
	 */
	public Profile getProfile(final Integer id, final String code) {
		if (log.isDebugEnabled()) {
			log.debug("entering getProfile( " + id + ", " + code + " )");
		}
		return daoService.getProfile(id, code);

	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getProfiles(java.lang.Boolean)
	 */
	public Set<BeanProfile> getProfiles(final Boolean temEnSve) {
		if (log.isDebugEnabled()) {
			log.debug("entering getProfiles( " + temEnSve + " )");
		}
		List<Profile> l = daoService.getProfiles(temEnSve);
		Set<BeanProfile> beanP = new HashSet<BeanProfile>();
		for (Profile p : l) {
			BeanProfile b = new BeanProfile(p);
			beanP.add(b);
		}

		return beanP;
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#deleteProfile(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Profile)
	 */
	public void deleteProfile(final Profile profile) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteProfile( " + profile + " )");
		}
		daoService.deleteProfile(profile);

	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#profileCodeIsUnique(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Profile)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#addTraitement(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Traitement)
	 */
	public void addTraitement(final Traitement traitement) {
		if (log.isDebugEnabled()) {
			log.debug("entering addTraitement( " + traitement + " )");
		}
		daoService.addTraitement(traitement);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#deleteTraitement(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Traitement)
	 */
	public void deleteTraitement(final Traitement traitement) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteTraitement( " + traitement + " )");
		}
		daoService.deleteTraitement(traitement);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getTraitement(java.lang.Integer)
	 */
	public Traitement getTraitement(final Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("entering getTraitement( " + id + " )");
		}
		return daoService.getTraitement(id);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getDomain(java.lang.Integer)
	 */
	public Domain getDomain(final Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDomain( " + id + " )");
		}
		return daoService.getDomain(id);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getFonctions(java.lang.Boolean,
	 *      java.lang.Boolean)
	 */
	public Set<Fonction> getFonctions(final Boolean temEnSve, final Boolean initAccess) {
		if (log.isDebugEnabled()) {
			log.debug("entering getFonction( " + temEnSve + ", " + initAccess
					+ " )");
		}
		Set<Fonction> fct = new HashSet<Fonction>();
		fct.addAll(daoService.getFonctions(temEnSve, initAccess));
		return fct;
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getTraitements(
	 * 		org.esupportail.opi.domain.beans.parameters.accessRight.Profile,
	 *      java.lang.String,
	 *      org.esupportail.opi.domain.beans.parameters.accessRight.Domain)
	 */
	public Set<Traitement> getTraitements(final Profile p, final String typeTraitement,
			final Domain domain) {
		if (log.isDebugEnabled()) {
			log.debug("entering getTraitements( " + p + ", " + typeTraitement
					+ ", " + domain + " )");
		}
		return new HashSet<Traitement>(daoService.getTraitements(p,
				typeTraitement, domain));
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getDomains(java.lang.Boolean,
	 *      java.lang.Boolean)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#updateTraitement(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Traitement)
	 */
	public void updateTraitement(final Traitement traitement) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateFonction( " + traitement + " )");
		}
		daoService.updateTraitement(traitement);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#traitementCodeIsUnique(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Traitement)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#functionRangIsUnique(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Fonction,
	 *      java.lang.Integer)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#domainRangIsUnique(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Domain)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#addAccessRight(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight)
	 */
	public void addAccessRight(final AccessRight accessRight) {
		if (log.isDebugEnabled()) {
			log.debug("entering addAccessRight( " + accessRight + " )");
		}
		daoService.addAccessRight(accessRight);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#updateAccessRight(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight)
	 */
	public void updateAccessRight(final AccessRight accessRight) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateAccessRight( " + accessRight + " )");
		}
		daoService.updateAccessRight(accessRight);
	}

	// ////////////////////////////////////////////////////////////
	// Profile
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.domain.ParameterService#addNomenclature(
	 * org.esupportail.opi.domain.beans.parameters.Nomenclature)
	 */
	public void addNomenclature(final Nomenclature nomenclature) {
		if (log.isDebugEnabled()) {
			log.debug("entering addNomenclature( " + nomenclature + " )");
		}
		daoService.addNomenclature(nomenclature);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#deleteNomenclature(
	 * org.esupportail.opi.domain.beans.parameters.Nomenclature)
	 */
	public void deleteNomenclature(final Nomenclature nomenclature) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteNomenclature( " + nomenclature + " )");
		}
		daoService.deleteNomenclature(nomenclature);
	}

	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#canDeleteNomclature(
	 * org.esupportail.opi.domain.beans.parameters.Nomenclature)
	 */
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
	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#getNomenclature(java.lang.Integer)
	 */
	public Nomenclature getNomenclature(final Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("entering getNomenclature( " + id + " )");
		}
		return daoService.getNomenclature(id);
	}


	/**
	 * @see org.esupportail.opi.domain.ParameterService#getPJs(java.lang.Boolean)
	 */
	public 	Set<PieceJustificative> getPJs(final Boolean temEnSve) { 
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



	/**
	 * @see org.esupportail.opi.domain.ParameterService#getPiecesJ(Set, Integer)
	 */
	public List<PieceJustificative> getPiecesJ(final Set<VersionEtpOpi> vet, final String codeRI) {
		List<PieceJustificative> pj = new ArrayList<PieceJustificative>();
		Set<PieceJustificative> pjInUse = getPJs(true); 
		for (PieceJustificative p : pjInUse) {
			// on garde les pièces du régime
			if (codeRI == null || codeRI.equals(p.getCodeRI())) {
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
	
	/* (non-Javadoc)
	 * @see org.esupportail.opi.domain.ParameterService#getPiecesJ(org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi, java.lang.Integer)
	 */
	@Override
	public List<PieceJustificative> getPiecesJ(VersionEtpOpi vetOpi, final String codeRI) {
		List<PieceJustificative> pj = new ArrayList<PieceJustificative>();
		Set<PieceJustificative> pjInUse = getPJs(true); 
		for (PieceJustificative p : pjInUse) {
			// on garde les pièces du régime
			if (codeRI == null || codeRI.equals(p.getCodeRI())) {
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getMotivationsAvis(java.lang.Boolean)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getCampagnes(
	 * java.lang.Boolean, java.lang.Integer)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#
	 * getCampagneEnServ(int)
	 */
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
				// TODO YL 22/11/2010: Ã  modifier par une rÃ©cup de la liste
				if (camp == null 
						|| (Integer.parseInt(camp.getCodAnu()) 
						< Integer.parseInt(c.getCodAnu()))) {
					camp = c;
				}
			}
		}

		return camp;
	}
	
	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#
	 * isCampagnesFIAndFCEnServ()
	 */
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
				if (camp.getCodeRI() == 0){ //FormationInitiale.CODE){
					isFI = true;
				} else if (camp.getCodeRI() == 1) { //FormationContinue.CODE){
					isFC = true;
				}
			}
			isOK = isFI && isFC;
		}

		return isOK;
	}
	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#getCampagnesAnu(java.lang.String)
	 */
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

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getTypeDecisions(java.lang.Boolean)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#updateNomenclature(
	 * org.esupportail.opi.domain.beans.parameters.Nomenclature)
	 */
	public void updateNomenclature(final Nomenclature nomenclature) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateNomenclature( " + nomenclature + " )");
		}
		daoService.updateNomenclature(nomenclature);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#nomenclatureCodeIsUnique(
	 * org.esupportail.opi.domain.beans.parameters.Nomenclature)
	 */
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#addCommission(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
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
		
		calendarCmi = (CalendarCmi) domainService.add(calendarCmi, 
				"Batch createMissingCalendarCmi");
		addCalendar(calendarCmi);
		
		commission.setCalendarCmi(calendarCmi);
		
		daoService.addCommission(commission);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#deleteCommission(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	public void deleteCommission(final Commission commission) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteCommission( " + commission + " )");
		}
		Commission c = getCommission(commission.getId(), null);
		deleteMember(new ArrayList<Member>(c.getMembers()));
		c.setMembers(null);
		deleteTraitementCmi(new ArrayList<TraitementCmi>(c.getTraitementCmi()));
		c.setTraitementCmi(null);
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
		
		daoService.deleteCommission(commission);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getCommission(java.lang.Integer, java.lang.String)
	 */
	public Commission getCommission(final Integer id, final String code) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCommission( " + id + ", " + code + " )");
		}
		return daoService.getCommission(id, code);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getCommissions(java.lang.Boolean)
	 */
	public Set<Commission> getCommissions(final Boolean temEnSve) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCommissions( " + temEnSve + " )");
		}
		return new HashSet<Commission>(daoService.getCommissions(temEnSve));
	}

	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#updateCommission(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	public void updateCommission(final Commission commission) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateCommission( " + commission + " )");
		}
		daoService.updateCommission(commission);
	}


	/**
	 * @see org.esupportail.opi.domain.ParameterService#commissionCodeIsUnique(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
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

	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#addContactCommission(
	 * org.esupportail.opi.domain.beans.references.commission.ContactCommission)
	 */
	public void addContactCommission(final ContactCommission contactCommission) {
		if (log.isDebugEnabled()) {
			log.debug("entering addContactCommission( " + contactCommission + " )");
		}
		this.daoService.addContactCommission(contactCommission);
	
	}
	

	/**
	 * @see org.esupportail.opi.domain.ParameterService#updateContactCommission(
	 * org.esupportail.opi.domain.beans.references.commission.ContactCommission)
	 */
	public void updateContactCommission(final ContactCommission contactCommission) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateContactCommission( " + contactCommission + " )");
		}
		this.daoService.updateContactCommission(contactCommission);

	}
	// ////////////////////////////////////////////////////////////
	// Member
	// ////////////////////////////////////////////////////////////
	/**
	 * @see org.esupportail.opi.domain.ParameterService#addMember(
	 * org.esupportail.opi.domain.beans.references.commission.Member)
	 */
	public void addMember(final Member member) {
		if (log.isDebugEnabled()) {
			log.debug("entering addMember( " + member + " )");
		}
		daoService.addMember(member);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#updateMember(
	 * org.esupportail.opi.domain.beans.references.commission.Member)
	 */
	public void updateMember(final Member member) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateMember( " + member + " )");
		}
		daoService.updateMember(member);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#deleteMember(java.util.List)
	 */
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

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#addTraitementCmi(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi)
	 */
	public void addTraitementCmi(final TraitementCmi traitementCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering addTraitementCmi( " + traitementCmi + " )");
		}
		daoService.addTraitementCmi(traitementCmi);
		
	}


	/** 
	 * @see org.esupportail.opi.domain.ParameterService#updateTraitementCmi(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi)
	 */
	public void updateTraitementCmi(final TraitementCmi traitementCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateTraitementCmi( " + traitementCmi + " )");
		}
		daoService.updateTraitementCmi(traitementCmi);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#deleteTraitementCmi(java.util.List)
	 */
	public void deleteTraitementCmi(final List<TraitementCmi> traitementCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteTraitementCmi( " + traitementCmi + " )");
		}
		for (TraitementCmi t : traitementCmi) {
			daoService.deleteTraitementCmi(t);
		}
	}

	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#
	 * getTraitementCmi(org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi, java.lang.Boolean)
	 */
	public TraitementCmi getTraitementCmi(
			final VersionEtpOpi versionEtpOpi,
			final Boolean initSelection) {
		if (log.isDebugEnabled()) {
			log.debug("entering getTraitementCmi( " + versionEtpOpi + "," + initSelection + " )");
		}
		return daoService.getTraitementCmi(versionEtpOpi, null, initSelection);
	}
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getTraitementCmi(java.lang.Integer)
	 */
	public TraitementCmi getTraitementCmi(final Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("entering getTraitementCmi( " + id + " )");
		}
		return daoService.getTraitementCmi(id);
	}
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#isConnectCmi(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi)
	 */
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

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#addCalendar(
	 * org.esupportail.opi.domain.beans.references.calendar.Calendar)
	 */
	public void addCalendar(final Calendar calendar) {
		if (log.isDebugEnabled()) {
			log.debug("entering addCalendar( " + calendar + " )");
		}
		daoService.addCalendar(calendar);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#updateCalendar(
	 * org.esupportail.opi.domain.beans.references.calendar.Calendar)
	 */
	public void updateCalendar(final Calendar calendar) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateCalendar( " + calendar + " )");
		}
		daoService.updateCalendar(calendar);
	}


	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getCalendars(java.lang.Boolean, java.lang.String)
	 */
	public List<Calendar> getCalendars(final Boolean temEnSve, final String typeCal) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCalendars( " + temEnSve + ", " + typeCal + " )");
		}
		return daoService.getCalendars(temEnSve, typeCal);
	}


	/** 
	 * @see org.esupportail.opi.domain.ParameterService#calendarCodeIsUnique(
	 * org.esupportail.opi.domain.beans.references.calendar.Calendar)
	 */
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

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#deleteCalendar(
	 * org.esupportail.opi.domain.beans.references.calendar.Calendar)
	 */
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
	
	
	/***
	 * @see org.esupportail.opi.domain.ParameterService#getCalendars(
	 * org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi)
	 */
	@Override
	public Set<CalendarIns> getCalendars(final VersionEtpOpi versionEtpOpi) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCalendars( " + versionEtpOpi + " )");
		}
		Set<Commission> cmiList = getCommissions(true);
		Set<CalendarIns> cIns = new HashSet<CalendarIns>();
		for (Commission cmi : cmiList) {
			for (TraitementCmi trt : cmi.getTraitementCmi()) {
				if (trt.getVersionEtpOpi().equals(versionEtpOpi)) {
					cIns.addAll(getCalendarIns(cmi));
					break;
				}
			}
		}
		
		return cIns;
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getCalendarIns(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	public List<CalendarIns> getCalendarIns(final Commission commission) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCalendarIns( " + commission + " )");
		}
		return daoService.getCalendarIns(commission);
	}


	
	// ////////////////////////////////////////////////////////////
	// FormulaireCmi
	// ////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#addFormulaireCmi(
	 * org.esupportail.opi.domain.beans.references.commission.FormulaireCmi)
	 */
	@Override
	public void addFormulaireCmi(final FormulaireCmi form) {
		if (log.isDebugEnabled()) {
			log.debug("entering addFormulaireCmi( " + form + " )");
		}
		daoService.addFormulaire(form);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#deleteFormulaireCmi(
	 * org.esupportail.opi.domain.beans.references.commission.FormulaireCmi)
	 */
	@Override
	public void deleteFormulaireCmi(final FormulaireCmi form) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteFormulaireCmi( " + form + " )");
		}
		daoService.deleteFormulaire(form);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getFormulairesCmi(Set, Integer)
	 */
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

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#addIndFormulaire(
	 * org.esupportail.opi.domain.beans.user.candidature.IndFormulaire)
	 */
	@Override
	public void addIndFormulaire(final IndFormulaire formNorme) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addIndFormulaire(formNorme);
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#deleteIndFormulaire(IndFormulaire,
	 *      String, RegimeInscription)
	 */
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

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getIndFormulaires(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
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
	
	/**
	 * @param indSelected
	 * @param voeu
	 * @return boolean
	 */
	public boolean isExitFormulaireInd(final Individu indSelected, final IndVoeu voeu) {
		return daoService.isExitFormulaireInd(indSelected, voeu);
	}
	
	/**
	 * @param vet
	 * @param ri
	 * @return boolean
	 */
	public boolean isExitFormulaireEtp(final VersionEtpOpi vet, final String codeRI) {
		return daoService.isExitFormulaireEtp(vet, codeRI);
	}
	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#isAllFormulairesCreated(Individu,
	 *      RegimeInscription)
	 */
	@Override
	public boolean isAllFormulairesCreated(final Individu indSelected,
	    final String codeRI) {
//		Integer nbFormsToCreate = daoService.nbFormulairesToCreateForIndividu(
//				indSelected.getVoeux(), ri);
		Integer nbFormsToCreate = 0;
		Integer nbFormsCreated = 0;
		for (IndVoeu indVoeu : indSelected.getVoeux()) {
			if (isExitFormulaireEtp(
			    indVoeu.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi(),
			    codeRI)) {
				nbFormsToCreate ++;
			}
			if (isExitFormulaireInd(indSelected, indVoeu)) {
				nbFormsCreated ++;
			}
		}
//		Integer nbFormsCreated = daoService.nbFormulairesCreatedByIndividu(
//				indSelected, indSelected.getVoeux());
		
		return nbFormsToCreate.equals(nbFormsCreated);
	}
	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#isAllFormulairesCreatedByTraitementsCmi(Individu,
	 *      RegimeInscription, Set)
	 */
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
	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#getIndFormulaires(
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	
	public Map<String, List<String>> getAllFormNamesMap(final Campagne camp,
	    final String sLabelRI) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndFormulaires( " + camp + " )");
		}
		Map<String, List<String>> mapFormName = new HashMap<String, List<String>>();
		for (IndFormulaire form : daoService.getIndFormulaires(camp)) {
			// PrÃ©paration de l'entrÃ©e dans la map
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

	/**
	 * @see org.esupportail.opi.domain.ParameterService#purgeIndFormulaireCamp(
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	
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



	/** 
	 * @see org.esupportail.opi.domain.ParameterService#addReunionCmi(
	 * org.esupportail.opi.domain.beans.references.calendar.ReunionCmi)
	 */
	public void addReunionCmi(final ReunionCmi reunionCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering addReunionCmi( " + reunionCmi + " )");
		}
		daoService.addReunionCmi(reunionCmi);
	}


	/** 
	 * @see org.esupportail.opi.domain.ParameterService#deleteReunionCmi(java.util.List)
	 */
	public void deleteReunionCmi(final List<ReunionCmi> reunionCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteReunionCmi( " + reunionCmi + " )");
		}
		for (ReunionCmi r : reunionCmi) {
			daoService.deleteReunionCmi(r);
		}
	}


	/** 
	 * @see org.esupportail.opi.domain.ParameterService#updateReunionCmi(
	 * org.esupportail.opi.domain.beans.references.calendar.ReunionCmi)
	 */
	public void updateReunionCmi(final ReunionCmi reunionCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateReunionCmi( " + reunionCmi + " )");
		}
		daoService.updateReunionCmi(reunionCmi);
	}


	//////////////////////////////////////////////////////////////
	// PieceJustiVet
	//////////////////////////////////////////////////////////////
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#deletePieceJustiVet(
	 * org.esupportail.opi.domain.beans.parameters.PieceJustiVet)
	 */
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
	
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getAllNombreDeVoeuByCge()
	 */
	public List<NombreVoeuCge> getAllNombreDeVoeuByCge() {
		return daoService.getAllNombreDeVoeuByCge();
	}

	
	

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#addNombreVoeuCge(
	 * org.esupportail.opi.domain.beans.references.NombreVoeuCge)
	 */
	@Override
	public void addNombreVoeuCge(final NombreVoeuCge nombreVoeuCge) {
		if (log.isDebugEnabled()) {
			log.debug("entering addNombreVoeuCge( " + nombreVoeuCge + " )");
		}
		daoService.addNombreVoeuCge(nombreVoeuCge);
	}


	/** 
	 * @see org.esupportail.opi.domain.ParameterService#deleteNombreVoeuCge(
	 * org.esupportail.opi.domain.beans.references.NombreVoeuCge)
	 */
	@Override
	public void deleteNombreVoeuCge(final NombreVoeuCge nombreVoeuCge) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteNombreVoeuCge( " + nombreVoeuCge + " )");
		}
		daoService.deleteNombreVoeuCge(nombreVoeuCge);
	}

	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#updateNombreVoeuCge(
	 * org.esupportail.opi.domain.beans.references.NombreVoeuCge)
	 */
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
	
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getMailContent(java.lang.String)
	 */
	@Override
	public MailContent getMailContent(final String code) {
		if (log.isDebugEnabled()) {
			log.debug("entering getMailContent( " + code + " )");
		}
		return daoService.getMailContent(code);
	}
	
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#addMailContent(
	 * org.esupportail.opi.domain.beans.mails.MailContent)
	 */
	@Override
	public void addMailContent(final MailContent mailContent) {
		if (log.isDebugEnabled()) {
			log.debug("entering addMailContent( " + mailContent + " )");
		}
		daoService.addMailContent(mailContent);
		
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getMailContents()
	 */
	@Override
	public List<MailContent> getMailContents() {
		if (log.isDebugEnabled()) {
			log.debug("entering getMailContents()");
		}
		return daoService.getMailContents();
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#updateMailContent(
	 * org.esupportail.opi.domain.beans.mails.MailContent)
	 */
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
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getCalendarRdv()
	 */
	public List<CalendarRDV> getCalendarRdv() {
		return daoService.getCalendarRdv();
	}
	
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#updateCalendarRdv(
	 * org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV)
	 */
	@Override
	public void updateCalendarRdv(final CalendarRDV cal) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateCalendarRdv( " + cal + " )");
		}
		daoService.updateCalendarRdv(cal);
	}
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#deleteCalendarRdv(
	 * org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV)
	 */
	@Override
	public void deleteCalendarRdv(final CalendarRDV cal) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteCalendarRdv( " + cal + " )");
		}
		daoService.deleteCalendarRdv(cal);
	}
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#addCalendarRdv(
	 * org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV)
	 */
	@Override
	public void addCalendarRdv(final CalendarRDV cal) {
		if (log.isDebugEnabled()) {
			log.debug("entering addCalendarRdv( " + cal + " )");
		}
		daoService.addCalendarRdv(cal);
	}
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#
	 * getListEtudiantsParCalendarRdvParPeriode(int, int, java.util.Date, java.util.Date)
	 */
	public int getListEtudiantsParCalendarRdvParPeriode(
			final int idCalendarRdv, final int month, final Date dateDebut, final Date dateFin) {
		return daoService.getListEtudiantsParCalendarRdvParPeriode(idCalendarRdv, month, dateDebut, dateFin);
	}
	
	/** 
	 * 
	 * @see org.esupportail.opi.domain.ParameterService#getListEtudiantsParCalendarRdvParPeriode
	 * (int, java.util.Date, java.util.Date, java.util.Date)
	 */
	public int getListEtudiantsParCalendarRdvParPeriode(
			final int idCalendarRdv, final Date dateDuJour, final Date dateDebut, final Date dateFin) {
		return daoService.getListEtudiantsParCalendarRdvParPeriode(
				idCalendarRdv, dateDuJour, dateDebut, dateFin);
	}
	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#
	 * getListEtudiantsParCalendarRdvParDemiJournee(int, int, java.util.Date, java.util.Date, java.util.Date)
	 */
	public int getListEtudiantsParCalendarRdvParDemiJournee(
		final int idCalendarRdv, final int month, final Date date, final Date dateDebut, final Date dateFin) {
		return daoService.getListEtudiantsParCalendarRdvParDemiJournee(
							idCalendarRdv, month, date, dateDebut, dateFin);
	}

	//////////////////////////////////////////////////////////////
	// misc
	//////////////////////////////////////////////////////////////

	/**
	 * @param daoService
	 *            the daoService to set
	 */
	public void setDaoService(final ParameterDaoService daoService) {
		this.daoService = daoService;
	}
	
	
	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}
	
	/**
	 * @param orbeonService the orbeonService to set
	 */
	public void setOrbeonService(final OrbeonService orbeonService) {
		this.orbeonService = orbeonService;
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getTypeTraitements()
	 */
	public List<TypeTraitement> getTypeTraitements() {
		return typeTraitements;
	}
	
	/**
	 * @param typeTraitements the typeTraitements to set
	 */
	public void setTypeTraitements(final List<TypeTraitement> typeTraitements) {
		this.typeTraitements = typeTraitements;
	}

	
	/** 
	 * @see org.esupportail.opi.domain.ParameterService#getTypeConvocations()
	 */
	public List<TypeConvocation> getTypeConvocations() {
		return typeConvocations;
	}

	/**
	 * @param typeConvocations the typeConvocations to set
	 */
	public void setTypeConvocations(final List<TypeConvocation> typeConvocations) {
		this.typeConvocations = typeConvocations;
	}

	/**
	 * @return the typeContrats
	 */
	public List<TypeContrat> getTypeContrats() {
		return typeContrats;
	}

	/**
	 * @param typeContrats the typeContrats to set
	 */
	public void setTypeContrats(final List<TypeContrat> typeContrats) {
		this.typeContrats = typeContrats;
	}

	/**
	 * @return the typeStatuts
	 */
	public List<TypeStatut> getTypeStatuts() {
		return typeStatuts;
	}

	/**
	 * @param typeStatuts the typeStatuts to set
	 */
	public void setTypeStatuts(final List<TypeStatut> typeStatuts) {
		this.typeStatuts = typeStatuts;
	}

	/**
	 * @return the typeOrganismes
	 */
	public List<TypeOrganisme> getTypeOrganismes() {
		return typeOrganismes;
	}

	/**
	 * @param typeOrganismes the typeOrganismes to set
	 */
	public void setTypeOrganismes(final List<TypeOrganisme> typeOrganismes) {
		this.typeOrganismes = typeOrganismes;
	}

	/**
	 * @return the typeSituations
	 */
	public List<TypeSituation> getTypeSituations() {
		return typeSituations;
	}

	/**
	 * @param typeSituations the typeSituations to set
	 */
	public void setTypeSituations(final List<TypeSituation> typeSituations) {
		this.typeSituations = typeSituations;
	}

	/**
	 * @see org.esupportail.opi.domain.ParameterService#getDateBackDefault()
	 */
	@Override
	public String getDateBackDefault() {
		return dateBackDefault;
	}
	
	/**
	 * @param dateBackDefault the dateBackDefault to set
	 */
	public void setDateBackDefault(final String dateBackDefault) {
		this.dateBackDefault = dateBackDefault;
	}

	/**
	 * @return the prefixCodCalCmi
	 */
	public String getPrefixCodCalCmi() {
		return prefixCodCalCmi;
	}

	/**
	 * @param prefixCodCalCmi the prefixCodCalCmi to set
	 */
	public void setPrefixCodCalCmi(final String prefixCodCalCmi) {
		this.prefixCodCalCmi = prefixCodCalCmi;
	}

	/**
	 * @return the prefixLibCalCmi
	 */
	public String getPrefixLibCalCmi() {
		return prefixLibCalCmi;
	}

	/**
	 * @param prefixLibCalCmi the prefixLibCalCmi to set
	 */
	public void setPrefixLibCalCmi(final String prefixLibCalCmi) {
		this.prefixLibCalCmi = prefixLibCalCmi;
	}


	
	
	//////////////////////////////////////////////////////////////
	// LinkTrtCmiCamp
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#
	 * addLinkTrtCmiCamp(org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp)
	 */
	@Override
	public void addLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
		if (log.isDebugEnabled()) {
			log.debug("entering addLinkTrtCmiCamp ( " + linkTrtCmiCamp + ")");
		}
		daoService.addLinkTrtCmiCamp(linkTrtCmiCamp);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#
	 * deleteLinkTrtCmiCamp(org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp)
	 */
	@Override
	public void deleteLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteLinkTrtCmiCamp ( " + linkTrtCmiCamp + ")");
		}
		daoService.deleteLinkTrtCmiCamp(linkTrtCmiCamp);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#
	 * getLinkTrtCmiCamp(org.esupportail.opi.domain.beans.references.commission.TraitementCmi, 
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	@Override
	public LinkTrtCmiCamp getLinkTrtCmiCamp(final TraitementCmi traitementCmi,
			final Campagne campagne) {
		if (log.isDebugEnabled()) {
			log.debug("entering getLinkTrtCmiCamp ( " + traitementCmi 
					+ ", " + campagne + " )");
		}
		return daoService.getLinkTrtCmiCamp(traitementCmi, campagne);
	}

	/** 
	 * @see org.esupportail.opi.domain.ParameterService#
	 * updateLinkTrtCmiCamp(org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp)
	 */
	@Override
	public void updateLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateLinkTrtCmiCamp ( " + linkTrtCmiCamp + ")");
		}
		daoService.updateLinkTrtCmiCamp(linkTrtCmiCamp);
	}
	
	/**
	 * @see org.esupportail.opi.domain.ParameterService#updateLinkTrtCmiCampTemEnServ(
	 * org.esupportail.opi.domain.beans.parameters.Campagne, boolean)
	 */
	
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
	
	/**
	 * @return The list of AutoListPrincipale
	 */
	public List<AutoListPrincipale> getAutoListPrincipale() {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		return daoService.getAutoListPrincipale();
	}
	
	/**
	 * 
	 * @see org.esupportail.opi.domain.ParameterService#
	 * getAutoListPrincipale(org.esupportail.opi.domain.beans.user.candidature.IndVoeu)
	 */
	public AutoListPrincipale getAutoListPrincipale(final IndVoeu indVoeu) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		return daoService.getAutoListPrincipale(indVoeu);
	}

	
}
