/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.web.beans;


import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.commons.web.controllers.Resettable;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.calendar.Calendar;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.pojo.VersionEtapePojo;
import org.esupportail.opi.web.utils.Utilitaires;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.beans.factory.InitializingBean;

import com.ibm.icu.text.SimpleDateFormat;





/**
 * A bean to managed to calendars for the acces of individu.
 */
public class ManagedCalendar implements Resettable, InitializingBean, Serializable {

	


	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6423819503238976662L;


	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	/**
	 * see {@link ParameterService}.
	 */
	private ParameterService parameterService;
	
	/**
	 * see {@link DomainService}.
	 */
	private DomainService domainService;
	
	/**
	 * see {@link I18nService}.
	 */
	private I18nService i18Service;
	
	/*
	 ******************* INIT ************************* */
	
	
	/**
	 * Constructor.
	 */
	public ManagedCalendar() {
		super();
	}

	
	/** 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.parameterService, "property parameterService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.domainService, "property domainService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.i18Service, "property i18Service of class " 
				+ this.getClass().getName() + " can not be null");
		reset();
	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		//TODO
	}


	
	
	/*
	 ******************* CALLBACK ********************** */
	
	
	
	/*
	 ******************* METHODS ********************** */
	
	
	/**
	 * Parcours tous les calendriers des depots des voeux.
	 * Return true si il existe au moins un calendrier ouvert.
	 * @return Boolean
	 */
	public Boolean getCalInsIsOpen() {
		List<Calendar> calIns = parameterService.getCalendars(true, Calendar.TYPE_CAL_INSCRIPTION);
		for (Calendar c : calIns) {
			CalendarIns cIns = (CalendarIns) c;
			Boolean isOpen = Utilitaires.calIsOpen(cIns);
			if (isOpen) { return isOpen; }
		}
		if (log.isDebugEnabled()) {
			log.debug("Tous les calendriers de depot de voeux sont fermes.");
		}
		return false;
	}
	
	
	/**
	 * Parcours la list et return versionEtapePojo list with full init.
	 * @param mapCmi Map< Commission, Set< VersionEtape>>
	 * @param individuPojo 
	 * @return List< VersionEtape>
	 */
	public Set<VersionEtapePojo> getVrsEtpPojo(
			final Map<Commission, Set<VersionEtapeDTO>> mapCmi,
			final IndividuPojo individuPojo) {
		Set<VersionEtapePojo> vrsEtpToDiplay = new HashSet<VersionEtapePojo>();
		for (Map.Entry<Commission, Set<VersionEtapeDTO>> cmi : mapCmi.entrySet()) {
			Set<VersionEtapeDTO> vet = cmi.getValue();
			vrsEtpToDiplay.addAll(
					initVetPojo(vet, individuPojo.getIndividu(),
							calIsOpen(parameterService.getCalendarIns(cmi.getKey())),
							getPeriodCAlIsOpen(parameterService.getCalendarIns(cmi.getKey()),
									individuPojo.getRegimeInscription()
									.getCanAlwaysAddVows()),
							individuPojo.getHasRightsToAddVows()));
		}
		
		return vrsEtpToDiplay;
	}
	
	/**
	 * Init the VersionEtapePojo.
	 * @param vet
	 * @param individu
	 * @param isOpen
	 * @param periodCalIns
	 * @param hasRightsToAddVows 
	 * @return Set< VersionEtapePojo>
	 */
	private Set<VersionEtapePojo> initVetPojo(
			final Set<VersionEtapeDTO> vet, 
			final Individu individu, 
			final boolean isOpen,
			final String periodCalIns,
			final boolean hasRightsToAddVows) {
		Set<VersionEtapePojo> vrsEtpToDiplay = new HashSet<VersionEtapePojo>();
		for (VersionEtapeDTO v : vet) {
			VersionEtapePojo vPojo = new VersionEtapePojo(v, isOpen, periodCalIns);
			for (IndVoeu iV : individu.getVoeux()) {
				TraitementCmi trtCmi = iV.getLinkTrtCmiCamp().getTraitementCmi();
				VersionEtpOpi vOpi = new VersionEtpOpi(v);
				if (trtCmi.getVersionEtpOpi().equals(vOpi)) {
					//le candidat est deja inscrit
					vPojo.setIsAlReadyChoice(true);
				}
			}
			// put the information : 
			// Is the current User connected a manager ?
			// or the regime can alwys add vows
			vPojo.setHasRightsToAddVows(hasRightsToAddVows);
			
			vrsEtpToDiplay.add(vPojo);
		}
		
		return vrsEtpToDiplay;
	}
	
	/**
	 * Parcours la list et return versionEtapePojo list with full init.
	 * @param mapCmi Map< Commission, Set< VersionEtape>>
	 * @return List< VersionEtape>
	 */
	public Set<VersionEtapePojo> getVrsEtpPojo(
			final Map<Commission, Set<VersionEtapeDTO>> mapCmi) {
		Set<VersionEtapePojo> vrsEtpToDiplay = new HashSet<VersionEtapePojo>();
		for (Map.Entry<Commission, Set<VersionEtapeDTO>> cmi : mapCmi.entrySet()) {
			Set<VersionEtapeDTO> vet = cmi.getValue();
			vrsEtpToDiplay.addAll(
					initVetPojo(vet, calIsOpen(parameterService.getCalendarIns(cmi.getKey())),
							getPeriodCAlIsOpen(parameterService.getCalendarIns(cmi.getKey()), false)));
		}
		
		return vrsEtpToDiplay;
	}
	
	/**
	 * Init the VersionEtapePojo.
	 * @param vet
	 * @param isOpen
	 * @param periodCalIns
	 * @return Set< VersionEtapePojo>
	 */
	private Set<VersionEtapePojo> initVetPojo(
			final Set<VersionEtapeDTO> vet, 
			final boolean isOpen,
			final String periodCalIns) {
		Set<VersionEtapePojo> vrsEtpToDiplay = new HashSet<VersionEtapePojo>();
		for (VersionEtapeDTO v : vet) {
			vrsEtpToDiplay.add(new VersionEtapePojo(v, isOpen, periodCalIns));
		}
		
		return vrsEtpToDiplay;
	}
	
	
	/**
	 * @param cIns
	 * @return Boolean true if cIns is open.
	 */
	private Boolean calIsOpen(final List<CalendarIns> cIns) {
		if (cIns != null) {
			for (CalendarIns cal : cIns) {
				if (Utilitaires.calIsOpen(cal)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Return the period in calendar is open.
	 * @param cIns
	 * @param notDisplayPeriod (false if display, true otherwise)
	 * @return String
	 */
	private String getPeriodCAlIsOpen(final List<CalendarIns> cIns, final boolean notDisplayPeriod) {
		if (notDisplayPeriod) {
			return "";
		}
		String period = null;
		int cpt = 0;
		SimpleDateFormat s = new SimpleDateFormat(Constantes.DATE_FORMAT);
		for (CalendarIns cal : cIns) {
			// on affiche que les calendriers en service
			if (cal.getTemoinEnService()) {
				String datDbt = s.format(cal.getStartDate());
				String endDat = s.format(cal.getEndDate());
				if (cpt == 0) {
					period = i18Service.getString(Constantes.I18N_FIELD_DAT_PERIOD, datDbt, endDat);
				} else {
					period += ", " 
						+ i18Service.getString(Constantes.I18N_FIELD_DAT_PERIOD, datDbt, endDat);
				}
				cpt++;
			}
		}
		return period;
	}
	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @param parameterService the parameterService to set
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}


	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}


	/**
	 * @param service the i18Service to set
	 */
	public void setI18Service(final I18nService service) {
		i18Service = service;
	}


	

}
