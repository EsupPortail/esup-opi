/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.dao.PilotageDaoService;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.CursusExt;
import org.esupportail.opi.domain.beans.user.indcursus.CursusR1;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.domain.beans.utils.CoordonneStatistique;
import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.DipAutCur;
import org.esupportail.wssi.services.remote.Etablissement;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.util.StringUtils;


/**
 * The basic implementation of PilotageService.
 * 
 * See /properties/domain/domain.xml
 */
public class PilotageServiceImpl implements PilotageService {
	/*
	 * ******************* PROPERTIES STATIC ******************* */
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8200845058340254019L;
	
	/**
	 * Un.
	 */
	private static final int UN = 1;
	
	/**
	 * Deux.
	 */
	private static final int DEUX = 2;
	
	/**
	 * Trois.
	 */
	private static final int TROIS = 3;
	
	/**
	 * Huit.
	 */
	private static final int HUIT = 8;
	
	
	
	/*
	 * ******************* PROPERTIES ******************* */
	/**
	 * {@link PilotageDaoService}.
	 */
	private PilotageDaoService daoService;
	
	/**
	 * {@link DomainApoService}.
	 */
	private DomainApoService domainApoService;
	
	/**
	 * {@link ParameterService}.
	 */
	private ParameterService parameterService;
		
	/**
	 * {@link I18nService}.
	 */
	private I18nService i18nService;
	
	/**
	 * {@link DomainService}.
	 */
	private DomainService domainService;
	
	/**
	 * {@link DomainService}.
	 */
	private OrbeonServiceImpl orbeonService;
	
	/**
	 * {@link BusinessCacheService}.
	 */
	private BusinessCacheService businessCacheService;
	
	/**
	 * A log.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	
	/*
	 * ******************* CONSTRUCTOR ******************* */
	/**
	 * Bean constructor.
	 */
	public PilotageServiceImpl() {
		super();
	}
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(this.daoService, "property daoService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.domainApoService, "property domainApoService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.parameterService, "property parameterService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.i18nService, "property i18nService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.domainService, "property domainService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.orbeonService, "property orbeonService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.businessCacheService, "property businessCacheService of class "
				+ this.getClass().getName() + " can not be null");
	}
	
	
	/*
	 ******************* METHODS ********************** */
	// ////////////////////////////////////////////////////////////
	// RESULTAT
	// ////////////////////////////////////////////////////////////
	/**
	 * @param ordonne 
	 * @param abscisse 
	 * @param campagne 
	 * @return String
	 */
	public String getRecupValCoordonne(final CoordonneStatistique ordonne, final CoordonneStatistique abscisse,
			final Campagne campagne, final boolean isNotDoublonIndividu) {
		if (ordonne == null || ordonne.getListeObject() == null || ordonne.getListeObject().isEmpty()) {
			return "null";
		}
		
		List<DetachedCriteria> listCriteria = new ArrayList<DetachedCriteria>();
		if (abscisse == null || abscisse.getListeObject() == null || abscisse.getListeObject().isEmpty()) {
			if (ordonne.getListeObject().size() > 1) {
				for (Object o1 : ordonne.getListeObject()) {
					listCriteria.add(getCriteria(o1, campagne));
				}
				if (!listCriteria.isEmpty()) {
					return String.valueOf(daoService.getResutRequete(
							listCriteria, isNotDoublonIndividu));
				}
			} else {
				DetachedCriteria criteria = getCriteria(ordonne.getListeObject().get(0), campagne);
				
				return String.valueOf(daoService.getResutRequete(criteria));
			}
		} else if (ordonne.getListeObject().size() > 1
				|| abscisse.getListeObject().size() > 1) {
			for (Object o1 : ordonne.getListeObject()) {
				for (Object a2 : abscisse.getListeObject()) {
					listCriteria.add(getCriteria(o1, a2, campagne));
				}
			}
			if (!listCriteria.isEmpty()) {
				return String.valueOf(daoService.getResutRequete(listCriteria, isNotDoublonIndividu));
			}
		} else {
			try {
				DetachedCriteria criteria = getCriteria(ordonne.getListeObject().get(0),
						abscisse.getListeObject().get(0), campagne);
				
				return String.valueOf(daoService.getResutRequete(criteria));
			} catch (NullPointerException e) {
				e.getStackTrace();
				return "Erreur";
			}
		}
		
		return "-1";
	}
	/**
	 * @param parameters
	 * @param o1
	 * @return boolean
	 */
	private boolean testParam(final List<Class< ? >> parameters, final Object o1) {
		return parameters.contains(o1.getClass()) 
		|| parameters.contains(o1.getClass().getSuperclass());
	}
	/**
	 * @param o1
	 * @param o2
	 * @param campagne
	 * @return Integer
	 */
	private DetachedCriteria getCriteria(final Object o1, final Object a2, final Campagne campagne) {
		Method[] methods = this.getClass().getMethods();
		Method methodToExecute = null;
		//look for methodToExecute to args
		for (int i = 0; i < methods.length; ++i) {
			Method m = methods[i];
			if (m.getParameterTypes().length == TROIS) {
				List<Class< ? >> parameters = new ArrayList<Class< ? >>();
				for (Class< ? > c : m.getParameterTypes()) {
					parameters.add(c);
				}
				boolean testParam = testParam(parameters, o1) && testParam(parameters, a2);
				if (testParam && parameters.contains(campagne.getClass())
						&& !parameters.contains(Object.class)) {
					if (log.isDebugEnabled()) {
						log.debug("parameters is = " + parameters);
					}
					methodToExecute = m;
					break;
				}
			}
		}
		
		if (methodToExecute == null) {
			return null;
		}
		try {
			if (log.isDebugEnabled()) {
				log.debug("entering " + methodToExecute.getName() + " :   ["
						+ o1.getClass().getSimpleName() + " = " + o1
						+ "]   [" + a2.getClass().getSimpleName() + " = " + a2
						+ "]   [Campagne = " + campagne + "]");
			}
			DetachedCriteria criteria = (DetachedCriteria) methodToExecute.invoke(this, o1, a2, campagne);
			if (log.isDebugEnabled()) {
				log.debug("leaving " + methodToExecute.getName() + " with criteria = " + criteria);
			}
			return criteria;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} 
		
		return null;
	}
	/**
	 * @param o1
	 * @param campagne
	 * @return Integer
	 */
	private DetachedCriteria getCriteria(final Object o1, final Campagne campagne) {
		Method[] methods = this.getClass().getMethods();
		Method methodToExecute = null;
		//look for methodToExecute to args
		for (int i = 0; i < methods.length; ++i) {
			Method m = methods[i];
			if (m.getParameterTypes().length == DEUX) {
				List<Class< ? >> parameters = new ArrayList<Class< ? >>();
				for (Class< ? > c : m.getParameterTypes()) {
					parameters.add(c);
				}
				boolean testParam = testParam(parameters, o1);
				if (testParam && parameters.contains(campagne.getClass())
						&& !parameters.contains(Object.class)) {
					if (log.isDebugEnabled()) {
						log.debug("parameters is = " + parameters);
					}
					methodToExecute = m;
					break;
				}
			}
		}
		
		if (methodToExecute == null) {
			return null;
		}
		try {
			if (log.isDebugEnabled()) {
				log.debug("entering " + methodToExecute.getName() + " :   ["
						+ o1.getClass().getSimpleName() + " = " + o1
						+ "]   [Campagne = " + campagne + "]");
			}
			DetachedCriteria criteria = (DetachedCriteria) methodToExecute.invoke(this, o1, campagne);
			if (log.isDebugEnabled()) {
				log.debug("leaving " + methodToExecute.getName() + " with criteria = " + criteria);
			}
			return criteria;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	/* * ******************* TRAITEMENTCMI ********************** * */
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statTrtCmiDiplomeCriteria(final TraitementCmi n1,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statTrtCmiDiplome");
		}
		
		return statVetDiplomeCriteria(
				domainApoService.getVersionEtape(n1.getVersionEtpOpi().getCodEtp(),
						n1.getVersionEtpOpi().getCodVrsVet()), n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statTrtCmiEtatIndividuCriteria(final TraitementCmi n1,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statTrtCmiEtatIndividu");
		}
		
		return statVetEtatIndividuCriteria(
				domainApoService.getVersionEtape(n1.getVersionEtpOpi().getCodEtp(),
						n1.getVersionEtpOpi().getCodVrsVet()), n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statTrtCmiEtatVoeuCriteria(final TraitementCmi n1,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statTrtCmiEtatVoeu");
		}
		
		return statVetEtatVoeuCriteria(
				domainApoService.getVersionEtape(n1.getVersionEtpOpi().getCodEtp(),
						n1.getVersionEtpOpi().getCodVrsVet()), n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statTrtCmiMotivationCriteria(final TraitementCmi n1,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statTrtCmiMotivation");
		}
		
		return statVetMotivationCriteria(
				domainApoService.getVersionEtape(n1.getVersionEtpOpi().getCodEtp(),
						n1.getVersionEtpOpi().getCodVrsVet()), n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statTrtCmiPaysCriteria(final TraitementCmi n1, final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statTrtCmiPays");
		}
		
		return statVetPaysCriteria(
				domainApoService.getVersionEtape(n1.getVersionEtpOpi().getCodEtp(),
						n1.getVersionEtpOpi().getCodVrsVet()), n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statTrtCmiTypeConvocationCriteria(final TraitementCmi n1,
			final TypeConvocation n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statTrtCmiTypeConvocation");
		}
		
		return statVetTypeConvocationCriteria(
				domainApoService.getVersionEtape(n1.getVersionEtpOpi().getCodEtp(),
						n1.getVersionEtpOpi().getCodVrsVet()), n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statTrtCmiTypeDecisionCriteria(final TraitementCmi n1,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statTrtCmiTypeDecision");
		}
		
		return statVetTypeDecisionCriteria(
				domainApoService.getVersionEtape(n1.getVersionEtpOpi().getCodEtp(),
						n1.getVersionEtpOpi().getCodVrsVet()), n2, c);
	}
	/**
	 * @param n1
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria allIndividuTrtCmi(final TraitementCmi n1, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuTrtCmi");
		}
		
		return allIndividuVet(
				domainApoService.getVersionEtape(n1.getVersionEtpOpi().getCodEtp(),
						n1.getVersionEtpOpi().getCodVrsVet()), c);
	}
	
	
	/* * ******************* COMMISSION ********************** * */
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statCommissionDiplomeCriteria(final Commission n1,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionDiplome");
		}
		
		return daoService.statCommissionDiplomeCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statCommissionEtatIndividuCriteria(final Commission n1,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionEtatIndividu");
		}
		
		return daoService.statCommissionEtatIndividuCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statCommissionEtatVoeuCriteria(final Commission n1,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionEtatVoeu");
		}
		
		return daoService.statCommissionEtatVoeuCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statCommissionMotivationCriteria(final Commission n1,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionMotivation");
		}
		
		return daoService.statCommissionMotivationCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statCommissionPaysCriteria(final Commission n1, final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionPays");
		}
		
		return daoService.statCommissionPaysCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statCommissionTypeConvocationCriteria(final Commission n1,
			final TypeConvocation n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionTypeConvocation");
		}
		
		return daoService.statCommissionTypeConvocationCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statCommissionTypeDecisionCriteria(final Commission n1,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statCommissionTypeDecision");
		}
		
		return daoService.statCommissionTypeDecisionCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria allIndividuCommission(final Commission n1, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuCommission");
		}
		
		return daoService.allIndividuCommission(n1, c);
	}
	
	
	/* * ******************* COMPOSANTE ********************** * */
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statComposanteDiplomeCriteria(final CentreGestion n1,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteDiplome");
		}
		
		return daoService.statComposanteDiplomeCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statComposanteEtatIndividuCriteria(final CentreGestion n1,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteEtatIndividu");
		}
		
		return daoService.statComposanteEtatIndividuCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statComposanteEtatVoeuCriteria(final CentreGestion n1,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteEtatVoeu");
		}
		
		return daoService.statComposanteEtatVoeuCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statComposanteMotivationCriteria(final CentreGestion n1,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteMotivation");
		}
		
		return daoService.statComposanteMotivationCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statComposantePaysCriteria(final CentreGestion n1, final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposantePays");
		}
		
		return daoService.statComposantePaysCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statComposanteTypeConvocationCriteria(final CentreGestion n1,
			final TypeConvocation n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteTypeConvocation");
		}
		
		return daoService.statComposanteTypeConvocationCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statComposanteTypeDecisionCriteria(final CentreGestion n1,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statComposanteTypeDecision");
		}
		
		return daoService.statComposanteTypeDecisionCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria allIndividuComposante(final CentreGestion n1, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuComposante");
		}
		
		return daoService.allIndividuComposante(n1, c);
	}
	
	
	/* * ******************* MOT CLE ********************** * */
//	/**
//	 * @param listClesDip
//	 * @param n2
//	 * @param c
//	 * @return DetachedCriteria
//	 */
//	public DetachedCriteria statMotCleDiplomeCriteria(final Ren1ClesAnnuFormPojo n1,
//			final DipAutCur n2, final Campagne c) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering statMotCleDiplome");
//		}
//		
//		return daoService.statMotCleDiplomeCriteria(
//				Utilitaires.getVersionEtpOpi(n1, c.getCodAnu(), domainApoService), n2, c);
//	}
//	/**
//	 * @param listClesDip
//	 * @param n2
//	 * @param c
//	 * @return DetachedCriteria
//	 */
//	public DetachedCriteria statMotCleEtatIndividuCriteria(final Ren1ClesAnnuFormPojo n1,
//			final EtatIndividu n2, final Campagne c) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering statMotCleEtatIndividu");
//		}
//		
//		return daoService.statMotCleEtatIndividuCriteria(
//				Utilitaires.getVersionEtpOpi(n1, c.getCodAnu(), domainApoService), n2, c);
//	}
//	/**
//	 * @param listClesDip
//	 * @param n2
//	 * @param c
//	 * @return DetachedCriteria
//	 */
//	public DetachedCriteria statMotCleEtatVoeuCriteria(final Ren1ClesAnnuFormPojo n1,
//			final EtatVoeu n2, final Campagne c) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering statMotCleEtatVoeu");
//		}
//		
//		return daoService.statMotCleEtatVoeuCriteria(
//				Utilitaires.getVersionEtpOpi(n1, c.getCodAnu(), domainApoService), n2, c);
//	}
//	/**
//	 * @param listClesDip
//	 * @param n2
//	 * @param c
//	 * @return DetachedCriteria
//	 */
//	public DetachedCriteria statMotCleMotivationCriteria(final Ren1ClesAnnuFormPojo n1,
//			final MotivationAvis n2, final Campagne c) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering statMotCleMotivation");
//		}
//		
//		return daoService.statMotCleMotivationCriteria(
//				Utilitaires.getVersionEtpOpi(n1, c.getCodAnu(), domainApoService), n2, c);
//	}
//	/**
//	 * @param listClesDip
//	 * @param n2
//	 * @param c
//	 * @return DetachedCriteria
//	 */
//	public DetachedCriteria statMotClePaysCriteria(final Ren1ClesAnnuFormPojo n1,
//			final Pays n2, final Campagne c) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering statMotClePays");
//		}
//		
//		return daoService.statMotClePaysCriteria(
//				Utilitaires.getVersionEtpOpi(n1, c.getCodAnu(), domainApoService), n2, c);
//	}
//	/**
//	 * @param listClesDip
//	 * @param n2
//	 * @param c
//	 * @return DetachedCriteria
//	 */
//	public DetachedCriteria statMotCleTypeConvocationCriteria(final Ren1ClesAnnuFormPojo n1,
//			final TypeConvocation n2, final Campagne c) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering statMotCleTypeConvocation");
//		}
//		
//		return daoService.statMotCleTypeConvocationCriteria(
//				Utilitaires.getVersionEtpOpi(n1, c.getCodAnu(), domainApoService), n2, c);
//	}
//	/**
//	 * @param listClesDip
//	 * @param n2
//	 * @param c
//	 * @return DetachedCriteria
//	 */
//	public DetachedCriteria statMotCleTypeDecisionCriteria(final Ren1ClesAnnuFormPojo n1,
//			final TypeDecision n2, final Campagne c) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering statMotCleTypeDecision");
//		}
//		
//		return daoService.statMotCleTypeDecisionCriteria(
//				Utilitaires.getVersionEtpOpi(n1, c.getCodAnu(), domainApoService), n2, c);
//	}
//	/**
//	 * @param n1
//	 * @param c
//	 * @return DetachedCriteria
//	 */
//	public DetachedCriteria allIndividuMotCle(final Ren1ClesAnnuFormPojo n1, final Campagne c) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering allIndividuMotCle");
//		}
//		
//		return daoService.allIndividuMotCle(
//				Utilitaires.getVersionEtpOpi(n1, c.getCodAnu(), domainApoService), c);
//	}
	
	
	/* * ******************* REGIME INSCRIPTION ********************** * */
	/**
	 * @param codeRI
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statRegimeInscriptionDiplomeCriteria(final Integer codeRI,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionDiplome");
		}
		
		return daoService.statRegimeInscriptionDiplomeCriteria(codeRI, n2, c);
	}
	/**
	 * @param codeRI
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statRegimeInscriptionEtatIndividuCriteria(final Integer codeRI,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionEtatIndividu");
		}
		
		return daoService.statRegimeInscriptionEtatIndividuCriteria(codeRI, n2, c);
	}
	/**
	 * @param codeRI
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statRegimeInscriptionEtatVoeuCriteria(final Integer codeRI,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionEtatVoeu");
		}
		
		return daoService.statRegimeInscriptionEtatVoeuCriteria(codeRI, n2, c);
	}
	/**
	 * @param codeRI
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statRegimeInscriptionMotivationCriteria(final Integer codeRI,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionMotivation");
		}
		
		return daoService.statRegimeInscriptionMotivationCriteria(codeRI, n2, c);
	}
	/**
	 * @param codeRI
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statRegimeInscriptionPaysCriteria(final Integer codeRI,
			final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionPays");
		}
		
		return daoService.statRegimeInscriptionPaysCriteria(codeRI, n2, c);
	}
	/**
	 * @param codeRI
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statRegimeInscriptionTypeConvocationCriteria(final Integer codeRI,
			final TypeConvocation n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionTypeConvocation");
		}
		
		return daoService.statRegimeInscriptionTypeConvocationCriteria(codeRI, n2, c);
	}
	/**
	 * @param codeRI
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statRegimeInscriptionTypeDecisionCriteria(final Integer codeRI,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statRegimeInscriptionTypeDecision");
		}
		
		return daoService.statRegimeInscriptionTypeDecisionCriteria(codeRI, n2, c);
	}
	/**
	 * @param codeRI
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria allIndividuRegimeInscription(final Integer codeRI, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuRegimeInscription");
		}
		
		return daoService.allIndividuRegimeInscription(codeRI, c);
	}
	
	
	/* * ******************* VET ********************** * */
	/**
	 * @param codeRI
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statVetDiplomeCriteria(final VersionEtapeDTO n1,
			final DipAutCur n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetDiplome");
		}
		
		return daoService.statVetDiplomeCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statVetEtatIndividuCriteria(final VersionEtapeDTO n1,
			final EtatIndividu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetEtatIndividu");
		}
		
		return daoService.statVetEtatIndividuCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statVetEtatVoeuCriteria(final VersionEtapeDTO n1,
			final EtatVoeu n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetEtatVoeu");
		}
		
		return daoService.statVetEtatVoeuCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statVetMotivationCriteria(final VersionEtapeDTO n1,
			final MotivationAvis n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetMotivation");
		}
		
		return daoService.statVetMotivationCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statVetPaysCriteria(final VersionEtapeDTO n1, final Pays n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetPays");
		}
		
		return daoService.statVetPaysCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statVetTypeConvocationCriteria(final VersionEtapeDTO n1,
			final TypeConvocation n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetTypeConvocation");
		}
		
		return daoService.statVetTypeConvocationCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria statVetTypeDecisionCriteria(final VersionEtapeDTO n1,
			final TypeDecision n2, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering statVetTypeDecision");
		}
		
		return daoService.statVetTypeDecisionCriteria(n1, n2, c);
	}
	/**
	 * @param n1
	 * @param c
	 * @return DetachedCriteria
	 */
	public DetachedCriteria allIndividuVet(final VersionEtapeDTO n1, final Campagne c) {
		if (log.isDebugEnabled()) {
			log.debug("entering allIndividuVet");
		}
		
		return daoService.allIndividuVet(n1, c);
	}
	
	
	// ////////////////////////////////////////////////////////////
	// PARAMETRE
	// ////////////////////////////////////////////////////////////
	/**
	 * @param typeClass
	 * @param methode 
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getAllCoordonne(final Class< ? > typeClass, final String methode) {
		if (log.isDebugEnabled()) {
			log.debug("entering getAllCoordonne typeClass = " + typeClass.getName());
			log.debug("methode = " + methode);
		}

		String lienDomaine = "org.esupportail.opi.domain.beans";
		if (typeClass.getName().substring(0, lienDomaine.length()).equals(lienDomaine)
				&& (methode == null || methode.isEmpty())) {
			return daoService.getAllCoordonne(typeClass);
		}

		if (methode == null || methode.isEmpty()) {
			log.warn("Aucune mÃ¯Â¿Â½thode est associÃ¯Â¿Â½e au type d'objet " + typeClass.getName());
			return null; 
		}

		Method[] methods = this.getClass().getMethods();
		Method methodToExecute = null;
		//look for methodToExecute to args
		for (int i = 0; i < methods.length; ++i) {
			Method m = methods[i];
			if (m.getName().equals(methode)) {
				methodToExecute = m;
				break;
			}
		}
		if (methodToExecute == null) {
			return null;
		}
		try {
			return (List) methodToExecute.invoke(this, typeClass);
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			log.warn("la mÃ¯Â¿Â½thode " + methode + " n'existe pas");
			e.printStackTrace();
		}


		return null;
	}
	/**
	 * @param classCge
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getComposantesStat(final Class<CentreGestion> classCge) {
		if (log.isDebugEnabled()) {
			log.debug("entering getComposantesStat classCge = " + classCge.getName());
		}
		return domainApoService.getCentreGestion();
	}
	/**
	 * @param classVet
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getVetStat(final Class<VersionEtapeDTO> classVet) {
		if (log.isDebugEnabled()) {
			log.debug("entering getVetStat classVet = " + classVet.getName());
		}
		List<VersionEtapeDTO> listVet = new ArrayList<VersionEtapeDTO>();
		
		List<VersionEtapeDTO> allVets = new ArrayList<VersionEtapeDTO>(
				domainApoService.getVersionEtapes(
						null, null, null, null));
		
		if (!allVets.isEmpty()) {
			Set<Commission> cmi = new HashSet<Commission>(parameterService.getCommissions(true));
			
			for (VersionEtapeDTO versionEtapeDTO : allVets) {
				if (BusinessUtil.getCmiForVetDTO(cmi, versionEtapeDTO) != null) {
					listVet.add(versionEtapeDTO);
				}
			}
		}
		
		return listVet;
	}
	
//	/**
//	 * @param classRi
//	 * @return List
//	 */
//	@SuppressWarnings("unchecked")
//	public List getRiStat(final Class<RegimeInscription> classRi) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering getRiStat classRi = " + classRi.getName());
//		}
//		return (List<RegimeInscription>) BeanUtils.getBean("listRi");
//	}
	
	/**
	 * @param classTypeConvocation
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getTypeConvocationStat(final Class<TypeConvocation> classTypeConvocation) {
		if (log.isDebugEnabled()) {
			log.debug("entering getTypeConvocationStat classEtat = " + classTypeConvocation.getName());
		}
		return (List<TypeConvocation>) ApplicationContextHolder.getContext().getBean("listTypeConvocation");
	}
	/**
	 * @param classEtatVoeu
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<EtatVoeu> getEtatVoeuStat(final Class<EtatVoeu> classEtatVoeu) {
		if (log.isDebugEnabled()) {
			log.debug("entering getEtatStat classEtat = " + classEtatVoeu.getName());
		}
		return (List<EtatVoeu>) ApplicationContextHolder.getContext().getBean("listEtatVoeu");
	}
	/**
	 * @param classEtatIndividu
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getEtatIndividuStat(final Class<EtatIndividu> classEtatIndividu) {
		if (log.isDebugEnabled()) {
			log.debug("entering getEtatStat classEtat = " + classEtatIndividu.getName());
		}
		return (List<EtatIndividu>) ApplicationContextHolder.getContext().getBean("listEtatIndividu");
	}
	/**
	 * @param classPays
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getPaysStat(final Class<Pays> classPays) {
		if (log.isDebugEnabled()) {
			log.debug("entering getPaysStat classPays = " + classPays.getName());
		}
		return domainApoService.getPays();
	}
	/**
	 * @param classDipAutCurs
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List getDipAutCursStat(final Class<DipAutCur> classDipAutCurs) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDipAutCursStat classDipAutCurs = " + classDipAutCurs.getName());
		}
		return domainApoService.getDipAutCurs();
	}

//	/**
//	 * @param classRen1ClesAnnuFormPojo
//	 * @return List
//	 */
//	@SuppressWarnings("unchecked")
//	public List getRen1ClesAnnuFormPojo(final Class<Ren1ClesAnnuFormPojo> classRen1ClesAnnuFormPojo) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering getDipAutCursStat classDipAutCurs = "
//					+ classRen1ClesAnnuFormPojo.getName());
//		}
//		return domainApoService.getRen1ClesAnnuForm();
//	}
	
	////////////////////////////////////////////////////////////
	// EXPORT LISTES COMPLEMENTAIRES
	/////////////////////////////////////////////////////////////////
	
	
	/**
	 * 
	 */
	public Map<Integer, List<String>> prepareCsvFormulaire(final VersionEtpOpi versionEtape,
			final String sLabelRI, final List<String> champsChoisis) {
		if (!parameterService.isExitFormulaireEtp(versionEtape, sLabelRI)) {
			return null;
		}
		
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		
		List<String> listLib = new ArrayList<String>();
		for (String key : champsChoisis) {
			listLib.add(LIB_BASE.get(key));
		}
		Map<String, String> allLibXml = orbeonService.getLibelleBaseXml(versionEtape, sLabelRI);
		
		Set<String> ksAllLibXml = allLibXml.keySet();
		for (String key : ksAllLibXml) {
			listLib.add(allLibXml.get(key));
		}
		map.put(0, listLib);
		
		Integer counter = 1;
		List<Map<String, String>> allInfosEtapes = recupAllInfosParEtape(versionEtape, sLabelRI, champsChoisis);
		for (Map<String, String> mapInfos : allInfosEtapes) {
			List<String> listInfos = new ArrayList<String>();
			for (String key1 : champsChoisis) {
				listInfos.add(mapInfos.get(key1));
			}
			for (String key2 : ksAllLibXml) {
				listInfos.add(mapInfos.get(key2));
			}
			map.put(counter, listInfos);
			counter++;
		}
		
		return map;
	}
	
	/**
	 * 
	 */
	private List<Map<String, String>> recupAllInfosParEtape(final VersionEtpOpi versionEtpOpi,
			final String sLabelRI, final List<String> champsChoisis) {
		// rÃÂ©cupÃÂ©ration de tous les individus
		TraitementCmi trtCmi = parameterService.getTraitementCmi(versionEtpOpi, null);
		List<Individu> individus = new ArrayList<Individu>();
		individus.addAll(domainService.getIndividusTrtCmi(trtCmi));
		
		Map<String, Individu> mapInd = new HashMap<String, Individu>();
		for (Individu ind : individus) {
			mapInd.put(ind.getNumDossierOpi(), ind);
		}
		
		List<Map<String, String>> allInfos = new ArrayList<Map<String, String>>();
		List <Map<String, String>> lMapInfoXml = orbeonService.recupAllInfosXML(individus, versionEtpOpi, sLabelRI);
		for (Map<String, String> mapInfoXml : lMapInfoXml) {
			String numDos = mapInfoXml.get(NUM_DOS);
			Map<String, String> mapAllInfo = new HashMap<String, String>();
			Individu ind = mapInd.get(numDos);
			if (ind != null) {
				mapAllInfo.putAll(getResultInfosBase(ind, numDos, versionEtpOpi, champsChoisis));
			} else {
				mapAllInfo.putAll(getResultInfosBaseNull(numDos));
				log.warn("l'individu numÃÂ©ro de dossier " + numDos + " est null");
			}
			mapAllInfo.putAll(mapInfoXml);
			allInfos.add(mapAllInfo);
		}
		
		return allInfos;
	}
	
	/**
	 * Initialisation des libelles de la base Mysql ou Oracle.
	 */
	private Map<String, String> getResultInfosBase(final Individu ind, final String numDos,
			final VersionEtpOpi versionEtpOpi, final List<String> champsChoisis) {
		if (log.isDebugEnabled()) {
			log.debug("Individu in getResultInfosBase() : " + ind);
		}
		Map<String, String> mapInfosBase = new HashMap<String, String>();
		
		//Date du jour
		Calendar cal = new GregorianCalendar();
		int annee = cal.get(Calendar.YEAR);
		
		//Bac de l'individu
		IndBac bac = ind.getIndBac().iterator().next();
		
		//RÃÂ©cupÃÂ©ration des cursus
		IndCursusScol cursusAnnee = null;
		IndCursusScol cursusAvant = null;
		for (IndCursusScol cursus : ind.getCursusScol()) {
			try {
				if (String.valueOf(annee + 1).equals(cursus.getAnnee())) {
					cursusAnnee = cursus;
				} else if (cursusAvant == null
						|| Integer.parseInt(cursusAvant.getAnnee())
							< Integer.parseInt(cursus.getAnnee())) {
					cursusAvant = cursus;
				}
			} catch (NumberFormatException e) {
				log.warn(e);
			}
		}
		
		//Information de l'individu
		if (champsChoisis.contains("commission")) {
			mapInfosBase.put("commission", orbeonService.getConvertChaineCsv(parameterService.
				getTraitementCmi(versionEtpOpi, null).getCommission().getLibelle()));
		}
		if (champsChoisis.contains(NUM_DOS)) {
			mapInfosBase.put(NUM_DOS, numDos);
		}
		if (champsChoisis.contains("sexe")) {
			mapInfosBase.put("sexe", getSexe(ind));
		}
		if (champsChoisis.contains("nom")) {
			mapInfosBase.put("nom", orbeonService.getConvertChaineCsv(ind.getNomPatronymique()));
		}
		if (champsChoisis.contains("prenom")) {
			mapInfosBase.put("prenom", orbeonService.getConvertChaineCsv(getPrenom(ind)));
		}
		if (champsChoisis.contains("dateNaiss")) {
			mapInfosBase.put("dateNaiss", orbeonService.getConvertChaineCsv(ind
					.getDateNaissance().toString()));
		}
		if (champsChoisis.contains("mail")) {
			mapInfosBase.put("mail", orbeonService.getConvertChaineCsv(ind.getAdressMail()));
		}
		if (champsChoisis.contains("titre")) {
			mapInfosBase.put("titre", getTitre(bac, getObtentionBac(bac)));
		}
		if (champsChoisis.contains("bac")) {
			mapInfosBase.put("bac", orbeonService.getConvertChaineCsv(getBacSerie(bac)));
		}
		if (champsChoisis.contains("insAnnee")) {
			mapInfosBase.put("insAnnee", String.valueOf(cursusAnnee != null));
		}
		if (champsChoisis.contains("univAnnee")) {
			mapInfosBase.put("univAnnee", orbeonService.getConvertChaineCsv(getUnivAnnee(cursusAnnee)));
		}
		if (champsChoisis.contains("etudAnnee")) {
			mapInfosBase.put("etudAnnee", orbeonService.getConvertChaineCsv(getEtudAnnee(cursusAnnee)));
		}
		if (champsChoisis.contains("insAvant")) {
			mapInfosBase.put("insAvant", String.valueOf(cursusAvant != null));
		}
		if (champsChoisis.contains("univAvant")) {
			mapInfosBase.put("univAvant", orbeonService.getConvertChaineCsv(getUnivAvant(cursusAvant)));
		}
		if (champsChoisis.contains("etudAvant")) {
			mapInfosBase.put("etudAvant", orbeonService.getConvertChaineCsv(getEtudAvant(cursusAvant)));
		}
		if (champsChoisis.contains("resultAvant")) {
			mapInfosBase.put("resultAvant", getResultAvant(cursusAvant));
		}
		if (champsChoisis.contains("dipAvant")) {
			mapInfosBase.put("dipAvant", orbeonService.getConvertChaineCsv(getDipAvant(cursusAvant)));
		}
		
		return mapInfosBase;
	}
	
	private Map<String, String> getResultInfosBaseNull(final String numDos) {
		Map<String, String> mapInfoBaseNull = new HashMap<String, String>();
		
		mapInfoBaseNull.put("commission", null);
		mapInfoBaseNull.put(NUM_DOS, numDos);
		mapInfoBaseNull.put("sexe", null);
		mapInfoBaseNull.put("nom", null);
		mapInfoBaseNull.put("prenom", null);
		mapInfoBaseNull.put("dateNaiss", null);
		mapInfoBaseNull.put("mail", null);
		mapInfoBaseNull.put("titre", null);
		mapInfoBaseNull.put("bac", null);
		mapInfoBaseNull.put("insAnnee", null);
		mapInfoBaseNull.put("univAnnee", null);
		mapInfoBaseNull.put("etudAnnee", null);
		mapInfoBaseNull.put("insAvant", null);
		mapInfoBaseNull.put("univAvant", null);
		mapInfoBaseNull.put("etudAvant", null);
		mapInfoBaseNull.put("resultAvant", null);
		mapInfoBaseNull.put("dipAvant", null);
		
		return mapInfoBaseNull;
	}
	
	/**
	 * @param ind
	 * @return Le sexe de l'individu
	 */
	private String getSexe(final Individu ind) {
		if (ind.getSexe().equals("F")) {
			if (ind.getNomUsuel() != null && !ind.getNomUsuel().isEmpty()
					&& !ind.getNomUsuel().equals(ind.getNomPatronymique())) {
				//Qualite = 2 -> Mme
				return "Mme";
			} else {
				//Qualite = 3 -> Mle
				return "Mle";
			}
		}
		
		//Qualite = 1 -> Mr
		return "Mr";
	}
	/**
	 * @param ind
	 * @return Le ou les prenom(s) de l'individu
	 */
	private String getPrenom(final Individu ind) {
		if (ind.getPrenom() != null && ind.getPrenom2() != null
				&& !ind.getPrenom().isEmpty() && !ind.getPrenom2().isEmpty()) {
			return ind.getPrenom() + ", " + ind.getPrenom2();
		} else if (ind.getPrenom2() != null && !ind.getPrenom2().isEmpty()) {
			return ind.getPrenom2();
		}
		
		return ind.getPrenom();
	}
	/**
	 * @param bac
	 * @return La date d'obtention du Bac
	 */
	private String getObtentionBac(final IndBac bac) {
		if (bac.getDateObtention() != null && !bac.getDateObtention().isEmpty()) {
			return bac.getDateObtention();
		}
		
		return "";
	}
	/**
	 * @param bac
	 * @param dateObtention
	 * @return Le titre du Bac (obtenu, en cours d'obtention, Autres...)
	 */
	private String getTitre(final IndBac bac, final String dateObtention) {
		try {
			//Date du jour
			Calendar cal = new GregorianCalendar();
			int mois = cal.get(Calendar.MONTH) + UN;
			int annee = cal.get(Calendar.YEAR);
			
			if (!bac.getCodBac().equals("0000") && (
					annee > Integer.parseInt(dateObtention)
					|| (annee == Integer.parseInt(dateObtention) && mois >= HUIT))) {
				boolean testCodBacAT = bac.getCodBac().equals("0031")
					|| bac.getCodBac().equals("0032") || bac.getCodBac().equals("0035");
				if (testCodBacAT || bac.getCodBac().equals("0036")
						|| bac.getCodBac().equals("0037")) {
					return "Autres";
				} else if (bac.getCodBac().equals("0033") || bac.getCodBac().equals("0034")) {
					return "E.S.E.U.";
				} else if (bac.getCodBac().equals("DAEA") || bac.getCodBac().equals("DAEB")) {
					return "D.A.E.U.";
				} else {
					return "Bac obtenu";
				}
			}
		} catch (NumberFormatException e) {
			log.debug("Date d'obtention non valide");
		}
		
		//Bac en cours d'obtention (par default)
		return "Bac en cours d'obtention";
	}
	/**
	 * @param bac
	 * @return Le libellÃÂ© du Bac
	 */
	private String getBacSerie(final IndBac bac) {
		if (bac.getDateObtention() != null && !bac.getDateObtention().isEmpty()
				&& bac.getCodBac() != null && !bac.getCodBac().isEmpty()) {
			BacOuxEqu bacOuxEqu = businessCacheService.getBacOuxEqu(
					bac.getDateObtention(), bac.getCodBac());
			if (bacOuxEqu != null) {
				return bacOuxEqu.getLibBac();
			}
		}
		
		return "";
	}
	/**
	 * @param cursusAnnee
	 * @return L'annÃÂ©e du cursus de l'annÃÂ©e courante
	 */
	private String getUnivAnnee(final IndCursusScol cursusAnnee) {
		if (cursusAnnee != null) {
			return cursusAnnee.getAnnee();
		}
		
		return "";
	}
	/**
	 * @param cursusAnnee
	 * @return Le libellÃÂ© de l'ÃÂ©tablissement du cursus de l'annÃÂ©e courante
	 */
	private String getEtudAnnee(final IndCursusScol cursusAnnee) {
		if (cursusAnnee != null && cursusAnnee.getCodEtablissement() != null
				&& !cursusAnnee.getCodEtablissement().isEmpty()) {
			Etablissement etablissement = businessCacheService.
					getEtablissement(cursusAnnee.getCodEtablissement());
			if (etablissement != null) {
				return etablissement.getLibEtb();
			}
		}
		
		return "";
	}
	/**
	 * @param cursusAvant
	 * @return L'annÃÂ©e du cursus de l'annÃÂ©e prÃÂ©cÃÂ©dente
	 */
	private String getUnivAvant(final IndCursusScol cursusAvant) {
		if (cursusAvant != null) {
			return cursusAvant.getAnnee();
		}
		
		return "";
	}
	/**
	 * @param cursusAvant
	 * @return Le libellÃÂ© de l'ÃÂ©tablissement du cursus de l'annÃÂ©e prÃÂ©cÃÂ©dente
	 */
	private String getEtudAvant(final IndCursusScol cursusAvant) {
		if (cursusAvant != null && cursusAvant.getCodEtablissement() != null
				&& !cursusAvant.getCodEtablissement().isEmpty()) {
			Etablissement etablissement = businessCacheService.
					getEtablissement(cursusAvant.getCodEtablissement());
			if (etablissement != null) {
				return etablissement.getLibEtb();
			}
		}
		
		return "";
	}
	/**
	 * @param cursusAvant
	 * @return Le rÃÂ©sultat du cursus de l'annÃÂ©e prÃÂ©cÃÂ©dente
	 */
	private String getResultAvant(final IndCursusScol cursusAvant) {
		if (cursusAvant != null && "N".equals(cursusAvant.getResultat())) {
			return "AjournÃÂ©";
		} else if (cursusAvant != null && "O".equals(cursusAvant.getResultat())) {
			return "Obtenu";
		}
		
		return "";
	}
	/**
	 * @param cursusAvant
	 * @return Le libellÃÂ© du cursus de l'annÃÂ©e prÃÂ©sÃÂ©dente
	 */
	private String getDipAvant(final IndCursusScol cursusAvant) {
		if (cursusAvant != null && cursusAvant instanceof CursusR1) {
			return ((CursusR1) cursusAvant).getLibEtape();
		} else if (cursusAvant != null && cursusAvant instanceof CursusExt) {
			CursusExt curE = (CursusExt) cursusAvant;
			if (StringUtils.hasText(curE.getLibelle())) {
				return curE.getLibelle();
			} else {
				return curE.getLibDac();
			}
		}
		
		return "";
	}
	
	
	
	//////////////////////////////////////////////////////////////
	// MISC
	//////////////////////////////////////////////////////////////

	/**
	 * @param daoService the daoService to set
	 */
	public void setDaoService(final PilotageDaoService daoService) {
		this.daoService = daoService;
	}
	/**
	 * @param domainApoService
	 */
	public void setDomainApoService(final DomainApoService domainApoService) {
		this.domainApoService = domainApoService;
	}
	/**
	 * @param parameterService
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}
	/**
	 * @param i18nService
	 */
	public void setI18nService(final I18nService i18nService) {
		this.i18nService = i18nService;
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
	public void setOrbeonService(final OrbeonServiceImpl orbeonService) {
		this.orbeonService = orbeonService;
	}
	/**
	 * @param businessCacheService
	 */
	public void setBusinessCacheService(final BusinessCacheService businessCacheService) {
		this.businessCacheService = businessCacheService;
	}
	
	
	
	
}
