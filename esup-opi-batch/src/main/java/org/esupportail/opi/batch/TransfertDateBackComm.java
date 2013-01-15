package org.esupportail.opi.batch;

import java.util.Set;

import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;

import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.calendar.CalendarCmi;
import org.esupportail.opi.domain.beans.references.commission.Commission;

/**
 * @author ylecuyer
 * TODO : IMPORTANT, batch à exécuter AVANT de supprimer les colonnes dans commission
 * Transfert la date de retour de dossier d'une commission dans le calendrier correspondant
 */
public class TransfertDateBackComm {
	
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(TransfertDateBackComm.class);

	/**
	 * PREFIX_CAL_CMI.
	 */
//	private static final String PREFIX_COD_CAL_CMI = "C";
	
	/**
	 * PREFIX_LIB_CAL_CMI.
	 */
//	private static final String PREFIX_LIB_CAL_CMI = "Calendrier de ";

	/**
	 * Bean constructor.
	 */
	private TransfertDateBackComm() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	@SuppressWarnings("unused")
	private static void transfertDateBackCommToCalendar() {
//		DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();

			LOG.info("lancement de la procédure transfertDateBackCommToCalendar");
			

			// on récupère toutes les commissions
			Set<Commission> commissions = parameterService.getCommissions(null);
			
			for (Commission cmi : commissions) {
				CalendarCmi calendarCmi = cmi.getCalendarCmi();
				// TODO ligne à décommenter pour lancer le batch
//				if (calendarCmi != null) {
//					calendarCmi.setDatEndBackDossier(cmi.getDatEndBackDossier());
//					parameterService.updateCalendar(calendarCmi);
//				} else {
//					// on crée le calendrier avec de valeur par défaut si il n'existe pas
//					calendarCmi = new CalendarCmi();
//					calendarCmi.setCode(PREFIX_COD_CAL_CMI + cmi.getCode());
//					calendarCmi.setLibelle(PREFIX_LIB_CAL_CMI + cmi.getLibelle());
//					calendarCmi.setDatEndBackDossier(cmi.getDatEndBackDossier());
//					calendarCmi.setCommission(cmi);
//					calendarCmi = (CalendarCmi) domainService.add(calendarCmi, 
//							"Batch transfertDateBackCommToCalendar");
//					parameterService.addCalendar(calendarCmi);
//					cmi.setCalendarCmi(calendarCmi);
//					parameterService.updateCommission(cmi);
//				}
			}
			
			DatabaseUtils.commit();
			
			LOG.info("procédure transfertDateBackCommToCalendar terminée");

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
			transfertDateBackCommToCalendar();
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
