/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.dao;


import java.util.Date;
import java.util.List;
import java.util.Set;

import org.esupportail.commons.dao.AbstractJdbcJndiHibernateDaoService;
import org.esupportail.commons.dao.HqlUtils;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.mails.MailContent;
import org.esupportail.opi.domain.beans.parameters.AutoListPrincipale;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.Nomenclature;
import org.esupportail.opi.domain.beans.parameters.PieceJustiVet;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight;
import org.esupportail.opi.domain.beans.parameters.accessRight.AccessType;
import org.esupportail.opi.domain.beans.parameters.accessRight.Domain;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;
import org.esupportail.opi.domain.beans.references.NombreVoeuCge;
import org.esupportail.opi.domain.beans.references.calendar.Calendar;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.calendar.ReunionCmi;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.ContactCommission;
import org.esupportail.opi.domain.beans.references.commission.FormulaireCmi;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.Member;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.references.rendezvous.Horaire;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.hibernate.FetchMode;
import org.hibernate.criterion.CriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.Junction;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.dao.support.DataAccessUtils;
import org.springframework.util.StringUtils;


/**
 * The Hiberate implementation of the DAO service.
 */
/**
 * @author ylecuyer
 *
 */
public class HibernateParamDaoServiceImpl extends AbstractJdbcJndiHibernateDaoService implements ParameterDaoService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3152554337896617315L;

	/**
	 * The name of the 'id' attribute.
	 */
	private static final String ID_ATTRIBUTE = "id";

	/**
	 * The name of the 'temoinEnService' attribute.
	 */
	private static final String IN_USE_ATTRIBUTE = "temoinEnService";

	/**
	 * 
	 */
	private static final String INDDATE_CALENDRIERRDV_ID = "indDate.calendrierRdv.id";

	/**
	 * 
	 */
	private static final String INDDATE_DATERDV = "indDate.dateRdv";

	/**
	 * 
	 */
	private static final String CALDEB = ":calDeb";

	/**
	 * 
	 */
	private static final String CALFIN = ":calFin";

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());



	/**
	 * Bean constructor.
	 */
	public HibernateParamDaoServiceImpl() {
		super();
	}




	//////////////////////////////////////////////////////////////
	// Profile
	//////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#addProfile(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Profile)
	 */
	public void addProfile(final Profile profile) {
		if (log.isDebugEnabled()) {
			log.debug("entering addProfile( " + profile + " )");
		}
		addObject(profile);

	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteProfile(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Profile)
	 */
	public void deleteProfile(final Profile profile) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteProfile( " + profile + " )");
		}
		if (profile.getAccessRight() != null) {
			for (AccessRight a : profile.getAccessRight()) {
				a.setTraitement(null);
				deleteObject(a);
			}
		}
		deleteObject(profile);

	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateProfile(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Profile)
	 */
	public void updateProfile(final Profile profile) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateProfile( " + profile + " )");
		}
		updateObject(profile);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getProfiles(java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<Profile> getProfiles(final Boolean temEnSve) {
		if (log.isDebugEnabled()) {
			log.debug("entering getProfiles( " + temEnSve + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Profile.class);
		criteria.setFetchMode("gestionnaires", FetchMode.JOIN);
		if (temEnSve != null) {
			criteria.add(Restrictions.eq(IN_USE_ATTRIBUTE, temEnSve));
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getProfile(java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Profile getProfile(final Integer id, final String code) {
		if (log.isDebugEnabled()) {
			log.debug("entering getProfile( " + id + ", " + code + " )");
		}
		if (id != null) {
			return getHibernateTemplate().get(Profile.class, id);
		}
		if (code != null) {
//			Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
//			return (Profile) s.createCriteria(Profile.class)
//			.add(Restrictions.eq("code", code))
//			.uniqueResult();
			
			DetachedCriteria criteria = DetachedCriteria.forClass(Profile.class);
			criteria.add(Restrictions.eq("code", code));
			
			return (Profile) DataAccessUtils.uniqueResult(
					getHibernateTemplate().findByCriteria(criteria));
		}
		return null;
	}





	// ////////////////////////////////////////////////////////////
	// Traitement
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#addTraitement(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Traitement)
	 */
	public void addTraitement(final Traitement traitement) {
		if (log.isDebugEnabled()) {
			log.debug("entering addTraitement( " + traitement + " )");
		}
		addObject(traitement);

	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteTraitement(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Traitement)
	 */
	public void deleteTraitement(final Traitement traitement) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteTraitement( " + traitement + " )");
		}

		deleteObject(traitement);

	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getTraitements(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Profile,
	 *      java.lang.String,
	 *     
	 *      org.esupportail.opi.domain.beans.parameters.accessRight.Domain)
	 */
	@SuppressWarnings("unchecked")
	public List<Traitement> getTraitements(final Profile p, final String typeTraitement,
			final Domain domain) {
		if (log.isDebugEnabled()) {
			log.debug("entering getTraitements( " + p + ", " + typeTraitement
					+ ", " + domain + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Traitement.class);
		if (typeTraitement != null) {
			if (typeTraitement.equals(Traitement.TYPE_DOMAIN)) {
				criteria = DetachedCriteria.forClass(Domain.class);
			} else if (typeTraitement.equals(Traitement.TYPE_FUNCTION)) {
				criteria = DetachedCriteria.forClass(Fonction.class);
				criteria.add(Restrictions.eq("domain", domain));
			} else {
				throw new ConfigException("La valeur de typeTraitment ( ="
						+ typeTraitement + "n'est pas autorise.");
			}
		}
		criteria.createCriteria("accessRight").add(
				Restrictions.eq("profile", p)).add(
						Restrictions.eq("codeAccessType", AccessType.COD_READ));

		return getHibernateTemplate().findByCriteria(criteria);

	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getFonctions(java.lang.Boolean,
	 *      java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<Fonction> getFonctions(final Boolean temEnSve, final Boolean initAccess) {
		if (log.isDebugEnabled()) {
			log.debug("entering getFonction( " + temEnSve + ", " + initAccess
					+ " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Fonction.class);
		criteria.setFetchMode("domain", FetchMode.JOIN);
		if (initAccess) {
			criteria.setFetchMode("accessRight", FetchMode.JOIN);
		}
		if (temEnSve != null) {
			criteria.add(Restrictions.eq(IN_USE_ATTRIBUTE, temEnSve));
		}
		criteria.createCriteria("domain").addOrder(Order.desc("libelle"));
		criteria.addOrder(Order.desc("libelle"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getDomains(java.lang.Boolean,
	 *      java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<Domain> getDomains(final Boolean temEnSve, final Boolean initAccess) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDomains( temEnSve  = " + temEnSve
					+ ", initAccess = " + initAccess + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Domain.class);
		if (initAccess != null) {
			if (initAccess) {
				criteria.setFetchMode("accessRight", FetchMode.JOIN);
			} else {
				criteria.setFetchMode("fonctions", FetchMode.JOIN);
			}
		}
		if (temEnSve != null) {
			criteria.add(Restrictions.eq(IN_USE_ATTRIBUTE, temEnSve));
		}
		criteria
		.setResultTransformer(CriteriaSpecification.DISTINCT_ROOT_ENTITY);
		List<Domain> l = getHibernateTemplate().findByCriteria(criteria);
		return l;
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateTraitement(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.Traitement)
	 */
	public void updateTraitement(final Traitement traitement) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateTraitement( " + traitement + " )");
		}
		updateObject(traitement);

	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getTraitement(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public Traitement getTraitement(final Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("entering getFonction( " + id + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Traitement.class);
		criteria.setFetchMode("accessRight", FetchMode.JOIN).add(
				Restrictions.eq(ID_ATTRIBUTE, id));

		List<Fonction> f = getHibernateTemplate().findByCriteria(criteria);
		return f.get(0);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getDomain(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	public Domain getDomain(final Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDomain( " + id + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Domain.class);
		criteria.setFetchMode("fonctions", FetchMode.JOIN).add(
				Restrictions.eq(ID_ATTRIBUTE, id));
		List<Domain> d = getHibernateTemplate().findByCriteria(criteria);
		if (d != null && !d.isEmpty()) {
			return d.get(0);
		}

		return null;
	}

	// ////////////////////////////////////////////////////////////
	// AccessRight
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#addAccessRight(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight)
	 */
	public void addAccessRight(final AccessRight accessRight) {
		if (log.isDebugEnabled()) {
			log.debug("entering addAccessRight( " + accessRight + " )");
		}
		addObject(accessRight);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteAccessRight(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight)
	 */
	public void deleteAccessRight(final AccessRight accessRight) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteAccessRight( " + accessRight + " )");
		}

		getProfile(accessRight.getProfile().getId(), null).getAccessRight().remove(
				accessRight);
		deleteObject(accessRight);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateAccessRight(
	 * org.esupportail.opi.domain.beans.parameters.accessRight.AccessRight)
	 */
	public void updateAccessRight(final AccessRight accessRight) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateAccessRight( " + accessRight + " )");
		}
		updateObject(accessRight);

	}

	// ////////////////////////////////////////////////////////////
	// Nomenclature
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#addNomenclature(
	 * org.esupportail.opi.domain.beans.parameters.Nomenclature)
	 */
	public void addNomenclature(final Nomenclature nomenclature) {
		if (log.isDebugEnabled()) {
			log.debug("entering addNomenclature( " + nomenclature + " )");
		}
		addObject(nomenclature);

	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteNomenclature(
	 * org.esupportail.opi.domain.beans.parameters.Nomenclature)
	 */
	public void deleteNomenclature(final Nomenclature nomenclature) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteNomenclature( " + nomenclature + " )");
		}
		deleteObject(nomenclature);

	}
	
	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#canDeleteTypeDecision(
	 * org.esupportail.opi.domain.beans.parameters.TypeDecision)
	 */
	@Override
	public boolean canDeleteTypeDecision(final TypeDecision typeD) {
		if (log.isDebugEnabled()) {
		    log.debug("entering canDeleteTypeDecision typeDecision = " + typeD);
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Avis.class);
		criteria.add(Restrictions.eq("result", typeD))
				.setProjection(Projections.rowCount());
		Long nb = (Long) getHibernateTemplate().findByCriteria(criteria).get(0);
		if (nb == null || nb.intValue() == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#canDeleteMotivation(
	 * org.esupportail.opi.domain.beans.parameters.MotivationAvis)
	 */
	public boolean canDeleteMotivation(final MotivationAvis motiv) {
		if (log.isDebugEnabled()) {
		    log.debug("entering canDeleteMotivation MotivationAvis = " + motiv);
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Avis.class);
		criteria.add(Restrictions.eq("motivationAvis", motiv))
				.setProjection(Projections.rowCount());
		Long nb = (Long) getHibernateTemplate().findByCriteria(criteria).get(0);
		if (nb == null || nb.intValue() == 0) {
			return true;
		}
		return false;
	}
	

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getNomenclature(java.lang.Integer)
	 */
	public Nomenclature getNomenclature(final Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("entering getNomenclature( " + id + " )");
		}
		return getHibernateTemplate().get(Nomenclature.class, id);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getNomenclatures(java.lang.Boolean,
	 *      java.lang.Class)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<Nomenclature> getNomenclatures(final Boolean temEnSve,
			final Class< ? > typNomenclature) {
		if (log.isDebugEnabled()) {
			log.debug("entering getNomenclatures( " + temEnSve + ", "
					+ typNomenclature + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(typNomenclature);
		if (temEnSve != null) {
			criteria.add(Restrictions.eq(IN_USE_ATTRIBUTE, temEnSve));
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateNomenclature(
	 * org.esupportail.opi.domain.beans.parameters.Nomenclature)
	 */
	public void updateNomenclature(final Nomenclature nomenclature) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateNomenclature( " + nomenclature + " )");
		}
		updateObject(nomenclature);

	}

	// ////////////////////////////////////////////////////////////
	// Commission
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#addCommission(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	public void addCommission(final Commission commission) {
		if (log.isDebugEnabled()) {
			log.debug("entering addCommission( " + commission + " )");
		}
		addObject(commission);

	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteCommission(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	public void deleteCommission(final Commission commission) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteCommission( " + commission + " )");
		}
		Commission cmi = getCommission(commission.getId(), null);

		deleteObject(cmi);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getCommission(java.lang.Integer, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public Commission getCommission(final Integer id, final String code) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCommission( " + id + ", " + code + " )");
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Commission.class);
		
		if (id != null) {
			criteria.add(Restrictions.eq("id", id));
		}
		
		if (StringUtils.hasText(code)) {
			criteria.add(Restrictions.eq("code", code));
		}
//		return (Commission) criteria.getExecutableCriteria(
//				getHibernateTemplate().getSessionFactory().getCurrentSession()).uniqueResult();
		
		return (Commission) DataAccessUtils.uniqueResult(
				getHibernateTemplate().findByCriteria(criteria));
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getCommissions(java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public List<Commission> getCommissions(final Boolean temEnSve) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCommissions( " + temEnSve + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Commission.class);
		if (temEnSve != null) {
			criteria.add(Restrictions.eq(IN_USE_ATTRIBUTE, temEnSve));
		}
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateCommission(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	public void updateCommission(final Commission commission) {
		if (log.isDebugEnabled()) {
 		    log.debug("entering updateCommission( " + commission + " )");
		}
		updateObject(commission);
	}

	//////////////////////////////////////////////////////////////
	// ContactCommission
	//////////////////////////////////////////////////////////////

	
	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#addContactCommission(
	 * org.esupportail.opi.domain.beans.references.commission.ContactCommission)
	 */
	public void addContactCommission(final ContactCommission contactCommission) {
		if (log.isDebugEnabled()) {
			log.debug("entering addContactCommission( " + contactCommission + " )");
		}
		addObject(contactCommission);
	
	}
	

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateContactCommission(
	 * org.esupportail.opi.domain.beans.references.commission.ContactCommission)
	 */
	public void updateContactCommission(final ContactCommission contactCommission) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateContactCommission( " + contactCommission + " )");
		}
		updateObject(contactCommission);

	}
	
	// ////////////////////////////////////////////////////////////
	// Member
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#addMember(
	 * org.esupportail.opi.domain.beans.references.commission.Member)
	 */
	public void addMember(final Member member) {
		if (log.isDebugEnabled()) {
			log.debug("entering addMember( " + member + " )");
		}
		addObject(member);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateMember(
	 * org.esupportail.opi.domain.beans.references.commission.Member)
	 */
	public void updateMember(final Member member) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateMember( " + member + " )");
		}
		updateObject(member);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteMember(
	 * org.esupportail.opi.domain.beans.references.commission.Member)
	 */
	public void deleteMember(final Member member) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteMember( " + member + " )");
		}
		deleteObject(member);
	}


	// ////////////////////////////////////////////////////////////
	// TraitementCmi
	// ////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#addTraitementCmi(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi)
	 */
	public void addTraitementCmi(final TraitementCmi traitementCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering addTraitementCmi( " + traitementCmi + " )");
		}
		addObject(traitementCmi);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateTraitementCmi(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi)
	 */
	public void updateTraitementCmi(final TraitementCmi traitementCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateTraitementCmi( " + traitementCmi + " )");
		}
		updateObject(traitementCmi);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteTraitementCmi(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi)
	 */
	public void deleteTraitementCmi(final TraitementCmi traitementCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteTraitementCmi( " + traitementCmi + " )");
		}
		deleteObject(traitementCmi);
	}


	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#
	 * getTraitementCmi(
	 * org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi,
	 *  java.lang.String, java.lang.Boolean)
	 */
	@SuppressWarnings("unchecked")
	public TraitementCmi getTraitementCmi(
			final VersionEtpOpi versionEtpOpi,
			final String codCge,
			final Boolean initSelection) {
		if (log.isDebugEnabled()) {
			log.debug("entering getTraitementCmi( " 
					+ versionEtpOpi + ", " + codCge
					+ ", " + initSelection + " )");
		}	
		DetachedCriteria criteria = DetachedCriteria.forClass(TraitementCmi.class);
		if (versionEtpOpi != null) {
			criteria.add(Restrictions.eq("versionEtpOpi.codEtp", versionEtpOpi.getCodEtp()))
			.add(Restrictions.eq("versionEtpOpi.codVrsVet", versionEtpOpi.getCodVrsVet()));
		}
		if (StringUtils.hasText(codCge)) {
			criteria.add(Restrictions.eq("versionEtpOpi.codCge", codCge));
		}
		if (initSelection != null && initSelection) {
			criteria.setFetchMode("selection", FetchMode.JOIN);
		}
		List<TraitementCmi> list = getHibernateTemplate().findByCriteria(criteria);
		if (list == null || list.isEmpty()) {
			return null;
		}
 		if (list.size() > 1) {
			throw new ConfigException("Il existe plusieurs traitementCmi pour la VET : " + versionEtpOpi);
		}
		return list.get(0);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getTraitementCmi(java.lang.Integer)
	 */
	public TraitementCmi getTraitementCmi(final Integer id) {
		if (log.isDebugEnabled()) {
			log.debug("entering getNomenclature( " + id + " )");
		}
		return getHibernateTemplate().get(TraitementCmi.class, id);
	}

	// ////////////////////////////////////////////////////////////
	// Calendar
	// ////////////////////////////////////////////////////////////
	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#addCalendar(
	 * org.esupportail.opi.domain.beans.references.calendar.Calendar)
	 */
	public void addCalendar(final Calendar calendar) {
		if (log.isDebugEnabled()) {
			log.debug("entering addCalendar( " + calendar + " )");
		}
		addObject(calendar);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateCalendar(
	 * org.esupportail.opi.domain.beans.references.calendar.Calendar)
	 */
	public void updateCalendar(final Calendar calendar) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateCalendar( " + calendar + " )");
		}
		updateObject(calendar);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getCalendar(java.lang.Integer)
	 */
	public Calendar getCalendar(final Integer id) {
 		return getHibernateTemplate().get(Calendar.class, id);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getCalendars(java.lang.Boolean, java.lang.String)
	 */
	@SuppressWarnings("unchecked")
	public List<Calendar> getCalendars(final Boolean temEnSve, final String typeCal) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCalendars( " + temEnSve + ", " + typeCal + " )");
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(Calendar.class);
		
		if (temEnSve != null) {
			criteria.add(Restrictions.eq(IN_USE_ATTRIBUTE, temEnSve));
		}

		if (typeCal != null) {
			criteria.add(Restrictions.eq("type", typeCal));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteCalendar(
	 * org.esupportail.opi.domain.beans.references.calendar.Calendar)
	 */
	public void deleteCalendar(final Calendar calendar) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteCalendar( " + calendar + " )");
		}
		deleteObject(calendar);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getCalendarIns(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	@SuppressWarnings("unchecked")
	public List<CalendarIns> getCalendarIns(final Commission commission) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCalendarIns( " + commission +  " )");
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(CalendarIns.class);
		criteria.createCriteria("commissions")
					.add(Restrictions.eq("id", commission.getId()));
		
		return getHibernateTemplate().findByCriteria(criteria);
		
	}
	
	//////////////////////////////////////////////////////////////
	// Formulaire
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#addFormulaire(
	 * org.esupportail.opi.domain.beans.references.commission.FormulaireCmi)
	 */
	@Override
	public void addFormulaire(final FormulaireCmi form) {
		if (log.isDebugEnabled()) {
			log.debug("entering addFormulaire( " + form + " )");
		}
		addObject(form);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteFormulaire(
	 * org.esupportail.opi.domain.beans.references.commission.FormulaireCmi)
	 */
	@Override
	public void deleteFormulaire(final FormulaireCmi form) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		deleteObject(form);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getFormulairesCmi(java.util.Set)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<FormulaireCmi> getFormulairesCmi(
			final Set<VersionEtpOpi> versionsEtpOpi, final  Integer codeRI) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(FormulaireCmi.class);
		// Si il y a des vEtpOpi, on filtre dessus.
		if (versionsEtpOpi != null) {
			// Due to a bug in hibernate
			// Wrong order of binding parameters in Restrictions.in for components
			// criteria.add(Restrictions.in("versionEtpOpi", 
			//	versionsEtpOpi.toArray(new VersionEtpOpi[versionsEtpOpi.size()])));

			Disjunction disj = Restrictions.disjunction();
			for (VersionEtpOpi versionEtpOpi : versionsEtpOpi) {
				disj.add(Restrictions.eq("versionEtpOpi", versionEtpOpi));
			}
			criteria.add(disj);

		}
		criteria.add(Restrictions.eq("codeRI", codeRI));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#addIndFormulaire(
	 * org.esupportail.opi.domain.beans.user.candidature.IndFormulaire)
	 */
	@Override
	public void addIndFormulaire(final IndFormulaire formNorme) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		addObject(formNorme);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteIndFormulaire(
	 * org.esupportail.opi.domain.beans.user.candidature.IndFormulaire)
	 */
	@Override
	public void deleteIndFormulaire(final IndFormulaire formNorme) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		deleteObject(formNorme);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getIndFormulaires(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public List<IndFormulaire> getIndFormulaires(final Individu indSelected) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		
		DetachedCriteria criteriaF = DetachedCriteria.forClass(FormulaireCmi.class, "f");
		criteriaF.setProjection(Property.forName("f.versionEtpOpi.codEtp"));
		
		DetachedCriteria criteria = DetachedCriteria.forClass(IndFormulaire.class, "i");
		criteria.add(Restrictions.eq("i.individu", indSelected))
				.add(Subqueries.propertyIn("i.versionEtpOpi.codEtp", criteriaF));

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getIndFormulaires(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
	@SuppressWarnings("unchecked")
	public List<IndFormulaire> getIndFormulaires(final Individu indSelected, final boolean allForm) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(IndFormulaire.class, "i");
		criteria.add(Restrictions.eq("i.individu", indSelected));
		
		if (!allForm) {
			DetachedCriteria criteriaF = DetachedCriteria.forClass(FormulaireCmi.class, "f");
			criteriaF.setProjection(Property.forName("f.versionEtpOpi.codEtp"));
			
			criteria.add(Subqueries.propertyIn("i.versionEtpOpi.codEtp", criteriaF));
			
		}

		return getHibernateTemplate().findByCriteria(criteria);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getIndFormulaires(
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	@SuppressWarnings("unchecked")
	public List<IndFormulaire> getIndFormulaires(final Campagne camp) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndFormulaires (" + camp + ")");
		}
		
		DetachedCriteria criteriaIn = DetachedCriteria.forClass(Individu.class, "ind");
		criteriaIn.setProjection(Property.forName("ind.id"));
		criteriaIn.createCriteria("campagnes")
				.add(Restrictions.eq("id", camp.getId()));

		DetachedCriteria criteria = DetachedCriteria.forClass(IndFormulaire.class, "i");
		criteria.add(Subqueries.propertyIn("i.individu.id", criteriaIn));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteIndFormulaires(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
	public void deleteIndFormulaires(final Individu indSelected) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteIndFormulaires ( " + indSelected + ")");
		}
		String hqlQuery = HqlUtils.deleteWhere("IndFormulaire as f", "f.individu.id=" + indSelected.getId());
		if (log.isDebugEnabled()) {
			log.debug("deleteAllIndividuDate with the hqlQuery " + hqlQuery);
		}
		executeUpdate(hqlQuery);
		
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#purgeIndFormulaireCamp(
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	public void purgeIndFormulaireCamp(final Campagne camp) {
		if (log.isDebugEnabled()) {
			log.debug("entering purgeIndFormulaireCamp( " + camp + " )");
		}
		String selectQuery = HqlUtils.selectFromWhere("distinct i.id",
				"Individu as i left join i.campagnes as c", "c.id in (" + camp.getId() + ")");
		String condition = "i.individu.id in (" + selectQuery + ")";
		String hqlQuery = HqlUtils.deleteWhere("IndFormulaire i", condition);
		executeUpdate(hqlQuery);
		
	}
	
	/**
	 * @param indSelected
	 * @param voeu
	 * @return boolean
	 */
	public boolean isExitFormulaireInd(final Individu indSelected, final IndVoeu voeu) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		VersionEtpOpi vet = voeu.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi();
		
		DetachedCriteria criteria = DetachedCriteria.forClass(IndFormulaire.class, "i");
		criteria.add(Restrictions.eq("i.individu", indSelected))
				.add(Restrictions.eq("i.versionEtpOpi.codEtp", vet.getCodEtp()))
				.add(Restrictions.eq("i.versionEtpOpi.codVrsVet", vet.getCodVrsVet()));

		
		return getHibernateTemplate().findByCriteria(criteria).size() > 0;
	}
	
	/**
	 * @param vet
	 * @param ri
	 * @return boolean
	 */
	public boolean isExitFormulaireEtp(final VersionEtpOpi vet, final String codeRI) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(FormulaireCmi.class, "f");
			criteria.add(Restrictions.eq("f.codeRI", Integer.parseInt(codeRI)))
					.add(Restrictions.eq("f.versionEtpOpi.codEtp", vet.getCodEtp()))
					.add(Restrictions.eq("f.versionEtpOpi.codVrsVet", vet.getCodVrsVet()));
			
		return getHibernateTemplate().findByCriteria(criteria).size() > 0;
	}
	
	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#nbFormulairesToCreateForIndividu(
	 *      Set, RegimeInscription)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer nbFormulairesToCreateForIndividu(
			final Set<IndVoeu> indVoeux, final String codeRI) {
		if (log.isDebugEnabled()) {
			log.debug("entering in nbFormulairestoCreateForIndividu");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(
				FormulaireCmi.class, "f");

		criteria.setProjection(Projections.rowCount());
		Disjunction orRestr = Restrictions.disjunction();

		for (IndVoeu indVoeu : indVoeux) {
			Junction andRestr = Restrictions.conjunction();

			andRestr.add(Restrictions.eq("f.versionEtpOpi.codEtp", indVoeu
					.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi()
					.getCodEtp()));
			andRestr.add(Restrictions.eq("f.versionEtpOpi.codVrsVet", indVoeu
					.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi()
					.getCodVrsVet()));
			andRestr.add(Restrictions.eq("f.codeRI", codeRI));

			orRestr.add(andRestr);
		}
		criteria.add(orRestr);

		List<Integer> list = getHibernateTemplate().findByCriteria(criteria);

		return list.get(0);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#nbFormulairesCreatedByIndividu(Individu,
	 *      Set)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Integer nbFormulairesCreatedByIndividu(final Individu indSelected,
			final Set<IndVoeu> indVoeux) {
		if (log.isDebugEnabled()) {
			log.debug("nbFormulairesCreatedByIndividu");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(
				IndFormulaire.class, "if");

		criteria.setProjection(Projections.rowCount());
		Disjunction orRestr = Restrictions.disjunction();

		for (IndVoeu indVoeu : indVoeux) {
			Junction andRestr = Restrictions.conjunction();

			andRestr.add(Restrictions.eq("if.versionEtpOpi.codEtp", indVoeu
					.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi()
					.getCodEtp()));
			andRestr.add(Restrictions.eq("if.versionEtpOpi.codVrsVet", indVoeu
					.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi()
					.getCodVrsVet()));
			orRestr.add(andRestr);
		}
		criteria.add(Restrictions.eq("if.individu", indSelected));
		criteria.add(orRestr);

		List<Integer> list = getHibernateTemplate().findByCriteria(criteria);

		return list.get(0);
	}
	
	
	//////////////////////////////////////////////////////////////
	// ReunionCmi
	//////////////////////////////////////////////////////////////


	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#addReunionCmi(
	 * org.esupportail.opi.domain.beans.references.calendar.ReunionCmi)
	 */
	public void addReunionCmi(final ReunionCmi reunionCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering addReunionCmi( " + reunionCmi + " )");
		}
		addObject(reunionCmi);
	}



	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteReunionCmi(
	 * org.esupportail.opi.domain.beans.references.calendar.ReunionCmi)
	 */
	public void deleteReunionCmi(final ReunionCmi reunionCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteReunionCmi( " + reunionCmi + " )");
		}
		deleteObject(reunionCmi);
	}


	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateReunionCmi(
	 * org.esupportail.opi.domain.beans.references.calendar.ReunionCmi)
	 */
	public void updateReunionCmi(final ReunionCmi reunionCmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateReunionCmi( " + reunionCmi + " )");
		}
		updateObject(reunionCmi);
	}

	//////////////////////////////////////////////////////////////
	// PieceJustiVet
	//////////////////////////////////////////////////////////////
	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deletePieceJustiVet(
	 * org.esupportail.opi.domain.beans.parameters.PieceJustiVet)
	 */
	@Override
	public void deletePieceJustiVet(final PieceJustiVet pieceJustiVet) {
		if (log.isDebugEnabled()) {
			log.debug("entering deletePieceJustiVet( " + pieceJustiVet + " )");
		}
		deleteObject(pieceJustiVet);

	}
	
	@Override
	public void deletePieceJustiVetWithFlush(final PieceJustiVet pieceJustiVet) {
		if (log.isDebugEnabled()) {
			log.debug("entering deletePieceJustiVet( " + pieceJustiVet + " )");
		}
		deleteObject(pieceJustiVet);
		
		//flush the instance
		getHibernateTemplate().flush();

	}

	//////////////////////////////////////////////////////////////
	// NombreVoeuCge
	//////////////////////////////////////////////////////////////


	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getAllNombreDeVoeuByCge()
	 */
	@SuppressWarnings("unchecked")
	public List<NombreVoeuCge> getAllNombreDeVoeuByCge() {
		if (log.isDebugEnabled()) {
			log.debug("entering getAllNombreDeVoeuByCge");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(NombreVoeuCge.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#addNombreVoeuCge(
	 * org.esupportail.opi.domain.beans.references.NombreVoeuCge)
	 */
	@Override
	public void addNombreVoeuCge(final NombreVoeuCge nombreVoeuCge) {
		if (log.isDebugEnabled()) {
			log.debug("entering addNombreVoeuCge( " + nombreVoeuCge + " )");
		}
		addObject(nombreVoeuCge);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteNombreVoeuCge(
	 * org.esupportail.opi.domain.beans.references.NombreVoeuCge)
	 */
	@Override
	public void deleteNombreVoeuCge(final NombreVoeuCge nombreVoeuCge) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteNombreVoeuCge( " + nombreVoeuCge + " )");
		}
		deleteObject(nombreVoeuCge);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateNombreVoeuCge(
	 * org.esupportail.opi.domain.beans.references.NombreVoeuCge)
	 */
	@Override
	public void updateNombreVoeuCge(final NombreVoeuCge nombreVoeuCge) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateNombreVoeuCge( " + nombreVoeuCge + " )");
		}
		updateObject(nombreVoeuCge);

	}


	//////////////////////////////////////////////////////////////
	// MailContent
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getMailContent(java.lang.String)
	 */
	@Override
	@SuppressWarnings("unchecked")
	public MailContent getMailContent(final String code) {
		if (log.isDebugEnabled()) {
			log.debug("entering getMailContent( " + code + " )");
		}
//		Session s = getHibernateTemplate().getSessionFactory().getCurrentSession();
//		
//		MailContent m = (MailContent) s.createCriteria(MailContent.class)
//				.add(Restrictions.eq("code", code))
//				.uniqueResult();
//		return m;
		
		DetachedCriteria criteria = DetachedCriteria.forClass(MailContent.class);
		criteria.add(Restrictions.eq("code", code));
		
		return (MailContent) DataAccessUtils.uniqueResult(
				getHibernateTemplate().findByCriteria(criteria));
	}
	

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getMailContents()
	 */
	@Override
	public List<MailContent> getMailContents() {
		if (log.isDebugEnabled()) {
			log.debug("entering getMailContents");
		}
		return getHibernateTemplate().loadAll(MailContent.class);
	}


	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#addMailContent(
	 * org.esupportail.opi.domain.beans.mails.MailContent)
	 */
	@Override
	public void addMailContent(final MailContent mailContent) {
		if (log.isDebugEnabled()) {
			log.debug("entering addMailContent( " + mailContent + " )");
		}
		addObject(mailContent);
		
	}




	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateMailContent(
	 * org.esupportail.opi.domain.beans.mails.MailContent)
	 */
	@Override
	public void updateMailContent(final MailContent mailContent) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateMailContent( " + mailContent + " )");
		}
		updateObject(mailContent);
		
	}


	//////////////////////////////////////////////////////////////
	// CalendarRDV
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getCalendarRdv()
	 */
	@SuppressWarnings("unchecked")
	public List<CalendarRDV> getCalendarRdv() {
		DetachedCriteria criteria = DetachedCriteria.forClass(CalendarRDV.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}


	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#addCalendarRdv(CalendarRDV)
	 */
	@Override
	public void addCalendarRdv(final CalendarRDV cal) {
		if (log.isDebugEnabled()) {
			log.debug("entering addCalendarRdv( " + cal + " )");
		}
		addObject(cal);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateCalendarRdv(
	 * org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV)
	 */
	@Override
	public void updateCalendarRdv(final CalendarRDV cal) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateCalendarRdv( " + cal + " )");
		}
		updateObject(cal);

	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#deleteCalendarRdv(
	 * org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV)
	 */
	@Override
	public void deleteCalendarRdv(final CalendarRDV cal) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteCalendarRdv( " + cal + " )");
		}
		deleteObject(cal);

	}


	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#getListEtudiantsParCalendarRdvParPeriode(
	 * int, int, Date, Date)
	 */
	public int getListEtudiantsParCalendarRdvParPeriode(
			final int idCalendarRdv, final int month, final Date dateDebut, final Date dateFin) {

		java.util.Calendar cal = java.util.Calendar.getInstance();

		java.util.Calendar calDeb = new java.util.GregorianCalendar();
		calDeb.setTime(dateDebut);
		calDeb.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR));
		calDeb.set(java.util.Calendar.MONTH, cal.get(java.util.Calendar.MONTH));
		calDeb.set(java.util.Calendar.DAY_OF_MONTH, cal.get(java.util.Calendar.DAY_OF_MONTH));

		java.util.Calendar calFin = java.util.Calendar.getInstance();
		calFin.setTime(dateFin);
		calFin.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR));
		calFin.set(java.util.Calendar.MONTH, cal.get(java.util.Calendar.MONTH));
		calFin.set(java.util.Calendar.DAY_OF_MONTH, cal.get(java.util.Calendar.DAY_OF_MONTH));

		String queryStr = HqlUtils.selectCountAllFromWhere(
				IndividuDate.class.getSimpleName() + HqlUtils.AS_KEYWORD + "indDate, " 
				+ Horaire.class.getSimpleName() + HqlUtils.AS_KEYWORD + "horaire",
				HqlUtils.and(
						HqlUtils.and(
								HqlUtils.equals(INDDATE_CALENDRIERRDV_ID, 
										"horaire.calendrierRdv.id"),
								HqlUtils.equals(INDDATE_CALENDRIERRDV_ID, 
										"" + idCalendarRdv),
						//HqlUtils.equals("indDate.dateRdv.month", "" + (month + 1)),
								HqlUtils.equals("horaire.numMois", "" + month)
						), HqlUtils.and(
								HqlUtils.ge(INDDATE_DATERDV, CALDEB),
								HqlUtils.lt(INDDATE_DATERDV, CALFIN)
						))
		);
		String[] params = {"calDeb", "calFin"};
		Object[] values = {calDeb.getTime(), calFin.getTime()};
		log.info("queryStr : " + queryStr);
		return DataAccessUtils.intResult(getHibernateTemplate().findByNamedParam(queryStr, params, values));
	}
	
	/** 
	 * 
	 * @see org.esupportail.opi.dao.ParameterDaoService#getListEtudiantsParCalendarRdvParPeriode 
	 * (int, java.util.Date, java.util.Date, java.util.Date) 
	 */ 
	public int getListEtudiantsParCalendarRdvParPeriode(
			final int idCalendarRdv, final Date dateDuJour, final Date dateDebut, final Date dateFin) {

		java.util.Calendar cal = java.util.Calendar.getInstance();
		
		java.util.Calendar calDuJour = new java.util.GregorianCalendar();
		calDuJour.setTime(dateDuJour);
		calDuJour.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR));
		calDuJour.set(java.util.Calendar.MONTH, cal.get(java.util.Calendar.MONTH));
		calDuJour.set(java.util.Calendar.DAY_OF_MONTH, cal.get(java.util.Calendar.DAY_OF_MONTH));
		
		java.util.Calendar calDeb = new java.util.GregorianCalendar();
		calDeb.setTime(dateDebut);
		calDeb.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR));
		calDeb.set(java.util.Calendar.MONTH, cal.get(java.util.Calendar.MONTH));
		calDeb.set(java.util.Calendar.DAY_OF_MONTH, cal.get(java.util.Calendar.DAY_OF_MONTH));

		java.util.Calendar calFin = java.util.Calendar.getInstance();
		calFin.setTime(dateFin);
		calFin.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR));
		calFin.set(java.util.Calendar.MONTH, cal.get(java.util.Calendar.MONTH));
		calFin.set(java.util.Calendar.DAY_OF_MONTH, cal.get(java.util.Calendar.DAY_OF_MONTH));

		String queryStr = HqlUtils.selectCountAllFromWhere(
				IndividuDate.class.getSimpleName() + HqlUtils.AS_KEYWORD + "indDate, " 
				+ JourHoraire.class.getSimpleName() + HqlUtils.AS_KEYWORD + "JourHoraire",
				HqlUtils.and(
						HqlUtils.and(
								HqlUtils.equals(INDDATE_CALENDRIERRDV_ID, 
										"JourHoraire.calendrierRdv.id"),
								HqlUtils.equals(INDDATE_CALENDRIERRDV_ID, 
										String.valueOf(idCalendarRdv)),
								HqlUtils.equals("JourHoraire.dateDuJour", ":calDuJour")
						), HqlUtils.and(
								HqlUtils.ge(INDDATE_DATERDV, CALDEB),
								HqlUtils.lt(INDDATE_DATERDV, CALFIN)
						))
		);
		String[] params = {"calDuJour", "calDeb", "calFin"};
		Object[] values = {calDuJour.getTime(), calDeb.getTime(), calFin.getTime()};
		log.info("queryStr : " + queryStr);
		return DataAccessUtils.intResult(getHibernateTemplate().findByNamedParam(queryStr, params, values));
	}
	
	/**
	 * @param idCalendarRdv 
	 * @param month 
	 * @param date 
	 * @param dateDebut 
	 * @param dateFin 
	 * @return le nombre d'etudiants
	 * @see org.esupportail.opi.dao.ParameterDaoService#getListEtudiantsParCalendarRdvParDemiJournee(
	 * int, int, Date, Date, Date)
	 */
	public int getListEtudiantsParCalendarRdvParDemiJournee(final int idCalendarRdv, 
			final int month, final Date date, final Date dateDebut, final Date dateFin) {

		java.util.Calendar cal = java.util.Calendar.getInstance();
		cal.setTime(date);


		log.info("Date select : " + date);
		log.info("Jour select : " + cal.get(java.util.Calendar.DAY_OF_MONTH));

		java.util.Calendar calDeb = new java.util.GregorianCalendar();
		calDeb.setTime(dateDebut);
		calDeb.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR));
		calDeb.set(java.util.Calendar.MONTH, cal.get(java.util.Calendar.MONTH));
		calDeb.set(java.util.Calendar.DAY_OF_MONTH, cal.get(java.util.Calendar.DAY_OF_MONTH));

		java.util.Calendar calFin = java.util.Calendar.getInstance();
		calFin.setTime(dateFin);
		calFin.set(java.util.Calendar.YEAR, cal.get(java.util.Calendar.YEAR));
		calFin.set(java.util.Calendar.MONTH, cal.get(java.util.Calendar.MONTH));
		calFin.set(java.util.Calendar.DAY_OF_MONTH, cal.get(java.util.Calendar.DAY_OF_MONTH));

		String queryStr = HqlUtils.selectCountAllFromWhere(
				IndividuDate.class.getSimpleName() + HqlUtils.AS_KEYWORD + "indDate, " 
				+ Horaire.class.getSimpleName() + HqlUtils.AS_KEYWORD + "horaire",
				HqlUtils.and(
						HqlUtils.and(
								HqlUtils.equals(INDDATE_CALENDRIERRDV_ID, 
								"horaire.calendrierRdv.id"),
								HqlUtils.equals(INDDATE_CALENDRIERRDV_ID, 
										"" + idCalendarRdv),
										HqlUtils.equals("horaire.numMois", 
												"" + month)
						), HqlUtils.and(
								HqlUtils.ge(INDDATE_DATERDV, CALDEB),
								HqlUtils.lt(INDDATE_DATERDV, CALFIN)
						))
		);
		String[] params = {"calDeb", "calFin"};
		Object[] values = {calDeb.getTime(), calFin.getTime()};

		return DataAccessUtils.intResult(getHibernateTemplate().findByNamedParam(queryStr, params, values));
	}


	//////////////////////////////////////////////////////////////
	// LinkTrtCmiCamp
	//////////////////////////////////////////////////////////////


	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#
	 * addLinkTrtCmiCamp(org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp)
	 */
	@Override
	public void addLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
		if (log.isDebugEnabled()) {
			log.debug("entering addLinkTrtCmiCamp( " + linkTrtCmiCamp + " )");
		}
		addObject(linkTrtCmiCamp);
		
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#
	 * deleteLinkTrtCmiCamp(org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp)
	 */
	@Override
	public void deleteLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
		if (log.isDebugEnabled()) {
			log.debug("entering addLinkTrtCmiCamp( " + linkTrtCmiCamp + " )");
		}
		deleteObject(linkTrtCmiCamp);
	}

	/**
	 * @see org.esupportail.opi.dao.ParameterDaoService#updateLinkTrtCmiCampTemEnServ(
	 * org.esupportail.opi.domain.beans.parameters.Campagne, boolean)
	 */
	public void updateLinkTrtCmiCampTemEnServ(final Campagne camp, final boolean temEnServ) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateLinkTrtCmiCampTemEnServ( " + camp + ", " + temEnServ + " )");
		}
		String condition = "l.campagne.id=" + camp.getId();
		String hqlQuery = HqlUtils.updateWhere("LinkTrtCmiCamp as l", 
				"l.temoinEnService=" + temEnServ, condition);
		if (log.isDebugEnabled()) {
			log.debug("updateIndVoeuTemEnServ with the hqlQuery " + hqlQuery);
		}
		executeUpdate(hqlQuery);
	}
	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#
	 * getLinkTrtCmiCamp(org.esupportail.opi.domain.beans.references.commission.TraitementCmi, 
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public LinkTrtCmiCamp getLinkTrtCmiCamp(final TraitementCmi traitementCmi,
			final Campagne campagne) {
		if (log.isDebugEnabled()) {
			log.debug("entering getLinkTrtCmiCamp( " 
					+ traitementCmi + ", " + campagne + " )");
		}	
		DetachedCriteria criteria = DetachedCriteria.forClass(LinkTrtCmiCamp.class);
		if (traitementCmi != null) {
			criteria.add(Restrictions.eq("traitementCmi", traitementCmi));
		}
		if (campagne != null) {
			criteria.add(Restrictions.eq("campagne", campagne));
		}
		
		List<LinkTrtCmiCamp> links = getHibernateTemplate().findByCriteria(criteria);
		
		if (!links.isEmpty()) {
			return links.get(0);
		}
		
		return null;
	}

	/** 
	 * @see org.esupportail.opi.dao.ParameterDaoService#
	 * updateLinkTrtCmiCamp(org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp)
	 */
	@Override
	public void updateLinkTrtCmiCamp(final LinkTrtCmiCamp linkTrtCmiCamp) {
		if (log.isDebugEnabled()) {
			log.debug("entering addLinkTrtCmiCamp( " + linkTrtCmiCamp + " )");
		}
		updateObject(linkTrtCmiCamp);
	}
	
	
	//////////////////////////////////////////////////////////////
	// AutoListPrincipale
	//////////////////////////////////////////////////////////////
	
	/**
	 * @return The list of AutoListPrincipale
	 */
	@SuppressWarnings("unchecked")
	public List<AutoListPrincipale> getAutoListPrincipale() {
		if (log.isDebugEnabled()) {
			log.debug("entering getAutoListPrincipale()");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(AutoListPrincipale.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * @param indVoeu
	 * @return The list of AutoListPrincipale
	 */
	@SuppressWarnings("unchecked")
	public AutoListPrincipale getAutoListPrincipale(final IndVoeu indVoeu) {
		if (log.isDebugEnabled()) {
			log.debug("entering getAutoListPrincipale( " + indVoeu + " )");
		}
		String codeCge = indVoeu.getLinkTrtCmiCamp().
							getTraitementCmi().getVersionEtpOpi().getCodCge();
		String codEtp = indVoeu.getLinkTrtCmiCamp().
							getTraitementCmi().getVersionEtpOpi().getCodEtp();
		int codVrsVet = indVoeu.getLinkTrtCmiCamp().
							getTraitementCmi().getVersionEtpOpi().getCodVrsVet();
		Commission commission = indVoeu.getLinkTrtCmiCamp().
							getTraitementCmi().getCommission();
		
		DetachedCriteria criteriaVet = DetachedCriteria.forClass(AutoListPrincipale.class, "a");
		criteriaVet.createAlias("a.listVet", "v");
		criteriaVet.add(Restrictions.eq("a.temoinEnService", true))
				.add(Restrictions.and(Restrictions.eq("v.codEtp", codEtp),
						Restrictions.eq("v.codVrsVet", codVrsVet)));
		List<AutoListPrincipale> listAuto = getHibernateTemplate().findByCriteria(criteriaVet);
		if (listAuto != null && !listAuto.isEmpty()) {
			return listAuto.get(0);
		}
		
		DetachedCriteria criteriaCommission = DetachedCriteria.forClass(AutoListPrincipale.class, "a");
		criteriaCommission.createAlias("a.listCommission", "c");
		criteriaCommission.add(Restrictions.eq("a.temoinEnService", true))
				.add(Restrictions.eq("c.id", commission.getId()));
		listAuto = getHibernateTemplate().findByCriteria(criteriaCommission);
		if (listAuto != null && !listAuto.isEmpty()) {
			return listAuto.get(0);
		}
		
		DetachedCriteria criteriaCge = DetachedCriteria.forClass(AutoListPrincipale.class);
		criteriaCge.add(Restrictions.eq("temoinEnService", true))
				.add(Restrictions.eq("codeCge", codeCge));
		listAuto = getHibernateTemplate().findByCriteria(criteriaCge);
		if (listAuto != null && !listAuto.isEmpty()) {
			return listAuto.get(0);
		}
		
		return null;
	}
}
