package org.esupportail.opi.batch;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;

import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.calendar.CalendarCmi;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.web.utils.Constantes;

/**
 * @author ylecuyer
 * TODO : IMPORTANT, batch à exécuter AVANT de supprimer les colonnes dans commission
 * Transfert la date de retour de dossier d'une commission dans le calendrier correspondant
 */
public class CreateMissingCalendarCmi {
	
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(CreateMissingCalendarCmi.class);

	/**
	 * Bean constructor.
	 */
	private CreateMissingCalendarCmi() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	private static void createMissingCalendarCmi() {
		DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();

			LOG.info("lancement de la procédure createMissingCalendarCmi");
			

			// on récupère toutes les commissions
			Set<Commission> commissions = parameterService.getCommissions(null);
			
			for (Commission cmi : commissions) {
				CalendarCmi calendarCmi = cmi.getCalendarCmi();
				if (calendarCmi == null) {
					// on crée le calendrier avec de valeur par défaut si il n'existe pas
					calendarCmi = new CalendarCmi();
					calendarCmi.setCode(parameterService.getPrefixCodCalCmi() + cmi.getCode());
					calendarCmi.setLibelle(parameterService.getPrefixLibCalCmi() + cmi.getLibelle());
					SimpleDateFormat formatter = new SimpleDateFormat(Constantes.DATE_FORMAT);
					ParsePosition pos = new ParsePosition(0);
					Date dateBack = formatter.parse(parameterService.getDateBackDefault(), pos);
					calendarCmi.setDatEndBackDossier(dateBack);
					calendarCmi.setCommission(cmi);
					calendarCmi = (CalendarCmi) domainService.add(calendarCmi, 
							"Batch createMissingCalendarCmi");
					parameterService.addCalendar(calendarCmi);
					cmi.setCalendarCmi(calendarCmi);
					parameterService.updateCommission(cmi);
				}
			}
			
			DatabaseUtils.commit();
			
			LOG.info("procédure createMissingCalendarCmi terminée");

		} catch (Exception e) {
			DatabaseUtils.rollback();
		} finally {
			DatabaseUtils.close();
		}
	}


	/**
	 * Print the syntax and exit.
	 */
	private static void syntax() {
		throw new IllegalArgumentException(
				"syntax: " + SetCampagneToInd.class.getSimpleName() + " <options>"
				+ "\nwhere option can be:"
				+ "\n- test-beans: test the required beans");
	}



	/**
	 * Dispatch dependaing on the arguments.
	 * @param args
	 */
	protected static void dispatch(final String[] args) {
		switch (args.length) {
		case 0:
			createMissingCalendarCmi();
			break;
		default:
			syntax();
		break;
		}
	}

	/**
	 * The main method, called by ant.
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			ApplicationService applicationService = ApplicationUtils.createApplicationService();
			LOG.info(applicationService.getName() + " v" + applicationService.getVersion());
			dispatch(args);
		} catch (Throwable t) {
			ExceptionUtils.catchException(t);
		}
	}

}
