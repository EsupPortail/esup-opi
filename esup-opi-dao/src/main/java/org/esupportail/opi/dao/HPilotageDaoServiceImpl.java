/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.dao;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.esupportail.commons.dao.AbstractJdbcJndiHibernateDaoService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.AdresseFix;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.CursusExt;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.DipAutCur;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.Subqueries;
import org.springframework.dao.support.DataAccessUtils;


/**
 * The Hibernate implementation of the pilotage DAO service.
 */
public class HPilotageDaoServiceImpl extends AbstractJdbcJndiHibernateDaoService implements PilotageDaoService {
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3152554337896617315L;
	
	/**
	 * i.id.
	 */
	private static final String I_ID = "i.id";

	/**
	 * ind.id.
	 */
	private static final String T_CODVRSVET = "t.versionEtpOpi.codVrsVet";

	/**
	 * ind.id.
	 */
	private static final String T_CODETP = "t.versionEtpOpi.codEtp";
	
	/**
	 * i.voeux.
	 */
	private static final String T_COMMISSION = "t.commission";
	
	/**
	 * v.linkTrtCmiCamp.
	 */
	private static final String T_VERSIONETPOPI_CODCGE = "t.versionEtpOpi.codCge";
	
	/**
	 * l.campagne.
	 */
	private static final String C_CODERI = "c.codeRI";

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	
	 // ****************** METHODS **********************

	// ////////////////////////////////////////////////////////////
	// RESULTAT
	// ////////////////////////////////////////////////////////////

	public int getResutRequete(final DetachedCriteria criteria) {
		criteria.setProjection(Projections.rowCount());
		
		return ((Number) DataAccessUtils.uniqueResult(
				getHibernateTemplate().findByCriteria(criteria))).intValue();
	}

	public int getResutRequete(final List<DetachedCriteria> listCriteria, final boolean isNotDoublonIndividu) {
		int nb = 0;
		final String indId = "ind.id";
		try {
			if (isNotDoublonIndividu) {
				//Pas de doublon sur les individus
				/*En une seule requ�te (Debut)*/
//				DetachedCriteria criteria = DetachedCriteria.forClass(Individu.class, "ind");
//				Criterion criterionFinal = null;
//				for (DetachedCriteria c : listCriteria) {
//					if (criterionFinal == null) {
//						criterionFinal = Subqueries.propertyIn(indId,
//							c.setProjection(Property.forName(I_ID)));
//					} else {
//						criterionFinal = Restrictions.or(criterionFinal,
//							Subqueries.propertyIn(indId,
//								c.setProjection(Property.forName(I_ID))));
//					}
//				}
//				criteria.add(criterionFinal);
//				criteria.setProjection(Projections.rowCount());
//				
//				nb = (Integer) DataAccessUtils.uniqueResult(
//						getHibernateTemplate().findByCriteria(criteria));
				/*En une seule requ�te (Fin)*/
				
				/*En plusieurs requ�tes*/
				List<Integer> listId = new ArrayList<Integer>();
				for (DetachedCriteria c : listCriteria) {
					for (Object o : getHibernateTemplate().findByCriteria(
						c.setProjection(Property.forName(I_ID)))) {
						listId.add((Integer) o);
					}
				}
				
				if (!listId.isEmpty()) {
					DetachedCriteria criteria = DetachedCriteria.forClass(Individu.class, "ind");
					criteria.add(Restrictions.in(indId, listId))
						.setProjection(Projections.rowCount());
					
					nb = ((Number) DataAccessUtils.uniqueResult(
						getHibernateTemplate().findByCriteria(criteria))).intValue();
				}
				/*En plusieurs requ�tes*/
			} else {
				//Somme de toutes les requ�tes.
				//Doublon sur les individus s'ils se trouvent dans le r�sultat de plusieurs requ�tes
				//(s'ils sont inscris dans plusieurs �tapes).
				if (listCriteria != null && !listCriteria.isEmpty()) {
					for (DetachedCriteria c : listCriteria) {
						nb += ((Number) DataAccessUtils.uniqueResult(
							getHibernateTemplate().findByCriteria(
								c.setProjection(Projections.rowCount())))).intValue();
					}
				}
			}
		} catch (NullPointerException e) {
			e.getStackTrace();
		}
		return nb;
	}
	
	// ******************* COMMISSION **********************

	public DetachedCriteria statCommissionDiplomeCriteria(final Commission n1,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionDiplome");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_COMMISSION, n1))
				.add(Subqueries.propertyIn(I_ID, getListIdIndDipAutCur(n2)));
	}

	public DetachedCriteria statCommissionEtatIndividuCriteria(final Commission n1,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionEtatIndividu");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_COMMISSION, n1))
				.add(Subqueries.propertyIn(I_ID, getListIdIndEtatIndividu(n2)));
	}

	public DetachedCriteria statCommissionEtatVoeuCriteria(final Commission n1,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionEtatVoeu");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_COMMISSION, n1))
				.add(Restrictions.eq("v.state", n2.getCodeLabel()));
	}

	public DetachedCriteria statCommissionMotivationCriteria(final Commission n1,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionMotivation");
		}
		
		return getCriteriaAvis(c).add(Restrictions.eq(T_COMMISSION, n1))
				.add(Restrictions.eq("m.id", n2.getId()));
	}

	public DetachedCriteria statCommissionPaysCriteria(final Commission n1, final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionPays");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_COMMISSION, n1))
				.add(Subqueries.propertyIn(I_ID, getListIdIndPays(n2)));
	}

	public DetachedCriteria statCommissionTypeConvocationCriteria(final Commission n1,
			final TypeConvocation tc, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionTypeConvocation");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.eq(T_COMMISSION, n1))
				.add(Restrictions.eq("r.codeTypeConvocation", tc.getCode()));
	}

	public DetachedCriteria statCommissionTypeDecisionCriteria(final Commission n1,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionTypeDecision");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.eq(T_COMMISSION, n1))
				.add(Restrictions.eq("r.id", n2.getId()));
	}

	public DetachedCriteria allIndividuCommission(final Commission n1, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuCommission");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_COMMISSION, n1));
	}
	
	
	// ******************* COMPOSANTE **********************

	public DetachedCriteria statComposanteDiplomeCriteria(final CentreGestion n1,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteDiplome");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_VERSIONETPOPI_CODCGE, n1.getCodCge()))
				.add(Subqueries.propertyIn(I_ID, getListIdIndDipAutCur(n2)));
	}

	public DetachedCriteria statComposanteEtatIndividuCriteria(final CentreGestion n1,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteEtatIndividu");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_VERSIONETPOPI_CODCGE, n1.getCodCge()))
				.add(Subqueries.propertyIn(I_ID, getListIdIndEtatIndividu(n2)));
	}

	public DetachedCriteria statComposanteEtatVoeuCriteria(final CentreGestion n1,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteEtatVoeu");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_VERSIONETPOPI_CODCGE, n1.getCodCge()))
				.add(Restrictions.eq("v.state", n2.getCodeLabel()));
	}

	public DetachedCriteria statComposanteMotivationCriteria(final CentreGestion n1,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteMotivation");
		}
		
		return getCriteriaAvis(c).add(Restrictions.eq(T_VERSIONETPOPI_CODCGE, n1.getCodCge()))
				.add(Restrictions.eq("m.id", n2.getId()));
	}

	public DetachedCriteria statComposantePaysCriteria(final CentreGestion n1, final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposantePays");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_VERSIONETPOPI_CODCGE, n1.getCodCge()))
				.add(Subqueries.propertyIn(I_ID, getListIdIndPays(n2)));
	}

	public DetachedCriteria statComposanteTypeConvocationCriteria(final CentreGestion n1,
			final TypeConvocation tc, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteTypeConvocation");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.eq(T_VERSIONETPOPI_CODCGE, n1.getCodCge()))
				.add(Restrictions.eq("r.codeTypeConvocation", tc.getCode()));
	}

	public DetachedCriteria statComposanteTypeDecisionCriteria(final CentreGestion n1,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteTypeDecision");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.eq(T_VERSIONETPOPI_CODCGE, n1.getCodCge()))
				.add(Restrictions.eq("r.id", n2.getId()));
	}

	public DetachedCriteria allIndividuComposante(final CentreGestion n1, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuComposante");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_VERSIONETPOPI_CODCGE, n1.getCodCge()));
	}
	
	
	// ******************* MOT CLE **********************

	public DetachedCriteria statMotCleDiplomeCriteria(final Set<VersionEtpOpi> listClesDip,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statMotCleDiplome");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.in(T_CODETP, getListCodEtp(listClesDip)))
				.add(Subqueries.propertyIn(I_ID, getListIdIndDipAutCur(n2)));
	}

	public DetachedCriteria statMotCleEtatIndividuCriteria(final Set<VersionEtpOpi> listClesDip,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statMotCleEtatIndividu");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.in(T_CODETP, getListCodEtp(listClesDip)))
				.add(Subqueries.propertyIn(I_ID, getListIdIndEtatIndividu(n2)));
	}

	public DetachedCriteria statMotCleEtatVoeuCriteria(final Set<VersionEtpOpi> listClesDip,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statMotCleEtatVoeu");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.in(T_CODETP, getListCodEtp(listClesDip)))
				.add(Restrictions.eq("v.state", n2.getCodeLabel()));
	}

	public DetachedCriteria statMotCleMotivationCriteria(final Set<VersionEtpOpi> listClesDip,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statMotCleMotivation");
		}
		
		return getCriteriaAvis(c).add(Restrictions.in(T_CODETP, getListCodEtp(listClesDip)))
				.add(Restrictions.eq("m.id", n2.getId()));
	}

	public DetachedCriteria statMotClePaysCriteria(final Set<VersionEtpOpi> listClesDip,
			final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statMotClePays");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.in(T_CODETP, getListCodEtp(listClesDip)))
				.add(Subqueries.propertyIn(I_ID, getListIdIndPays(n2)));
	}

	public DetachedCriteria statMotCleTypeConvocationCriteria(final Set<VersionEtpOpi> listClesDip,
			final TypeConvocation tc, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statMotCleTypeConvocation");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.in(T_CODETP, getListCodEtp(listClesDip)))
				.add(Restrictions.eq("r.codeTypeConvocation", tc.getCode()));
	}

	public DetachedCriteria statMotCleTypeDecisionCriteria(final Set<VersionEtpOpi> listClesDip,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statMotCleTypeDecision");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.in(T_CODETP, getListCodEtp(listClesDip)))
				.add(Restrictions.eq("r.id", n2.getId()));
	}

	public DetachedCriteria allIndividuMotCle(final Set<VersionEtpOpi> listClesDip, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuMotCle");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.in(T_CODETP, getListCodEtp(listClesDip)));
	}

	private List<String> getListCodEtp(final Set<VersionEtpOpi> listClesDip) {
		List<String> listCodEtp = new ArrayList<String>();
		for (VersionEtpOpi vet : listClesDip) {
			if (log.isDebugEnabled()) {
				log.debug("VersionEtpOpi : CodEtp = " + vet.getCodEtp());
			}
			listCodEtp.add(vet.getCodEtp());
		}
		
		return listCodEtp;
	}
	
	
	// ******************* REGIME INSCRIPTION **********************

	public DetachedCriteria statRegimeInscriptionDiplomeCriteria(final Integer codeRI,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionDiplome");
		}
		
		return getCriteriaVoeu(c).add(Subqueries.propertyIn(I_ID, getListIdIndDipAutCur(n2)))
				.add(Restrictions.eq(C_CODERI, codeRI));
	}

	public DetachedCriteria statRegimeInscriptionEtatIndividuCriteria(final Integer codeRI,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionEtatIndividu");
		}
		
		return getCriteriaVoeu(c).add(Subqueries.propertyIn(I_ID, getListIdIndEtatIndividu(n2)))
				.add(Restrictions.eq(C_CODERI, codeRI));
	}

	public DetachedCriteria statRegimeInscriptionEtatVoeuCriteria(final Integer codeRI ,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionEtatVoeu");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(C_CODERI, codeRI))
				.add(Restrictions.eq("v.state", n2.getCodeLabel()));
	}

	public DetachedCriteria statRegimeInscriptionMotivationCriteria(final Integer codeRI,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionMotivation");
		}
		
		return getCriteriaAvis(c).add(Restrictions.eq(C_CODERI, codeRI))
				.add(Restrictions.eq("m.id", n2.getId()));
	}

	public DetachedCriteria statRegimeInscriptionPaysCriteria(final Integer codeRI,
			final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionPays");
		}
		
		return getCriteriaVoeu(c).add(Subqueries.propertyIn(I_ID, getListIdIndPays(n2)))
				.add(Restrictions.eq(C_CODERI, codeRI));
	}

	public DetachedCriteria statRegimeInscriptionTypeConvocationCriteria(final Integer codeRI,
			final TypeConvocation tc, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionTypeConvocation");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.eq(C_CODERI, codeRI))
				.add(Restrictions.eq("r.codeTypeConvocation", tc.getCode()));
	}

	public DetachedCriteria statRegimeInscriptionTypeDecisionCriteria(final Integer codeRI,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionTypeDecision");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.eq(C_CODERI, codeRI))
				.add(Restrictions.eq("r.id", n2.getId()));
	}

	public DetachedCriteria allIndividuRegimeInscription(final Integer codeRI, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuRegimeInscription");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(C_CODERI, codeRI));
	}
	
	
	// ******************* VET **********************

	public DetachedCriteria statVetDiplomeCriteria(final VersionEtapeDTO n1,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetDiplome");
		}
		
		return getCriteriaVoeu(c).add(Subqueries.propertyIn(I_ID, getListIdIndDipAutCur(n2)))
				.add(Restrictions.eq(T_CODETP, n1.getCodEtp()))
				.add(Restrictions.eq(T_CODVRSVET, n1.getCodVrsVet()));
	}

	public DetachedCriteria statVetEtatIndividuCriteria(final VersionEtapeDTO n1,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetEtatIndividu");
		}
		
		return getCriteriaVoeu(c).add(Subqueries.propertyIn(I_ID, getListIdIndEtatIndividu(n2)))
				.add(Restrictions.eq(T_CODETP, n1.getCodEtp()))
				.add(Restrictions.eq(T_CODVRSVET, n1.getCodVrsVet()));
	}

	public DetachedCriteria statVetEtatVoeuCriteria(final VersionEtapeDTO n1,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetEtatVoeu");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq("v.state", n2.getCodeLabel()))
				.add(Restrictions.eq(T_CODETP, n1.getCodEtp()))
				.add(Restrictions.eq(T_CODVRSVET, n1.getCodVrsVet()));
	}

	public DetachedCriteria statVetMotivationCriteria(final VersionEtapeDTO n1,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetMotivation");
		}
		
		return getCriteriaAvis(c).add(Restrictions.eq("m.id", n2.getId()))
				.add(Restrictions.eq(T_CODETP, n1.getCodEtp()))
				.add(Restrictions.eq(T_CODVRSVET, n1.getCodVrsVet()));
	}
 
	public DetachedCriteria statVetPaysCriteria(final VersionEtapeDTO n1, final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetPays");
		}
		
		return getCriteriaVoeu(c).add(Subqueries.propertyIn(I_ID, getListIdIndPays(n2)))
				.add(Restrictions.eq(T_CODETP, n1.getCodEtp()))
				.add(Restrictions.eq(T_CODVRSVET, n1.getCodVrsVet()));

	}

	public DetachedCriteria statVetTypeConvocationCriteria(final VersionEtapeDTO n1,
			final TypeConvocation tc, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetTypeConvocation");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.eq("r.codeTypeConvocation", tc.getCode()))
				.add(Restrictions.eq(T_CODETP, n1.getCodEtp()))
				.add(Restrictions.eq(T_CODVRSVET, n1.getCodVrsVet()));
	}

	public DetachedCriteria statVetTypeDecisionCriteria(final VersionEtapeDTO n1,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetTypeDecision");
		}
		
		return getCriteriaTypeDecision(c).add(Restrictions.eq("r.id", n2.getId()))
				.add(Restrictions.eq(T_CODETP, n1.getCodEtp()))
				.add(Restrictions.eq(T_CODVRSVET, n1.getCodVrsVet()));
	}

	public DetachedCriteria allIndividuVet(final VersionEtapeDTO n1, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuVet");
		}
		
		return getCriteriaVoeu(c).add(Restrictions.eq(T_CODETP, n1.getCodEtp()))
			.add(Restrictions.eq(T_CODVRSVET, n1.getCodVrsVet()));
	}
	
	
	// ******************* AUTRE METHODE **********************

	private DetachedCriteria getCriteriaVoeu(final Campagne c) {
		DetachedCriteria criteria = DetachedCriteria.forClass(IndVoeu.class, "v");
		criteria.createAlias("v.individu", "i")
				.createAlias("v.linkTrtCmiCamp", "l")
				.createAlias("l.campagne", "c")
				.createAlias("l.traitementCmi", "t")
				.add(Restrictions.eq("l.campagne", c));
		
		return criteria;
	}

	private DetachedCriteria getCriteriaAvis(final Campagne c) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Avis.class, "a");
		criteria.createAlias("a.motivationAvis", "m")
				.createAlias("a.indVoeu", "v")
				.createAlias("v.individu", "i")
				.createAlias("v.linkTrtCmiCamp", "l")
				.createAlias("l.campagne", "c")
				.createAlias("l.traitementCmi", "t")
				.add(Restrictions.eq("l.campagne", c))
				.add(Restrictions.eq("a.temoinEnService", Boolean.TRUE));
		
		return criteria;
	}
	
	private DetachedCriteria getCriteriaTypeDecision(final Campagne c) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Avis.class, "a");
		criteria.createAlias("a.result", "r")
				.createAlias("a.indVoeu", "v")
				.createAlias("v.individu", "i")
				.createAlias("v.linkTrtCmiCamp", "l")
				.createAlias("l.campagne", "c")
				.createAlias("l.traitementCmi", "t")
				.add(Restrictions.eq("l.campagne", c))
				.add(Restrictions.eq("a.temoinEnService", Boolean.TRUE));
		return criteria;
	}
	
	private DetachedCriteria getListIdIndDipAutCur(final DipAutCur n2) {
		DetachedCriteria cIdInd = DetachedCriteria.forClass(CursusExt.class, "ce");
		cIdInd.createAlias("ce.individu", "in")
				.add(Restrictions.eq("ce.codDac", n2.getCodDac()))
				.setProjection(Property.forName("in.id"));
		return cIdInd;
	}

	private DetachedCriteria getListIdIndEtatIndividu(final EtatIndividu n2) {
		DetachedCriteria cIdVoeu = DetachedCriteria.forClass(Individu.class, "in");
		cIdVoeu.add(Restrictions.eq("in.state", n2.getCodeLabel()))
				.setProjection(Property.forName("in.id"));
		return cIdVoeu;
	}

	private DetachedCriteria getListIdIndPays(final Pays n2) {
		DetachedCriteria cIdInd = DetachedCriteria.forClass(AdresseFix.class, "ad");
		cIdInd.createAlias("ad.individu", "in")
			.add(Restrictions.eq("ad.codPays", n2.getCodPay()))
			.setProjection(Property.forName("in.id"));
		return cIdInd;
	}
	
	
	
	// ////////////////////////////////////////////////////////////
	// PARAMETRE
	// ////////////////////////////////////////////////////////////

	@SuppressWarnings("unchecked")
	public <T> List<T> getAllCoordonne(final Class<T> typeClass) {
		if (log.isDebugEnabled()) {
			log.debug("entering getAllCoordonne");
			log.debug("entering class = " + typeClass.getName());
		}

		DetachedCriteria criteria = DetachedCriteria.forClass(typeClass);

		return getHibernateTemplate().findByCriteria(criteria);
	}
}
