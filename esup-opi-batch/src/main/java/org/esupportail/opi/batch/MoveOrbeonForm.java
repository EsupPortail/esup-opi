package org.esupportail.opi.batch;

import java.util.Set;

import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;

import org.esupportail.opi.domain.OrbeonService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;

/**
 * @author ylecuyer
 * TODO : à 
 */
public class MoveOrbeonForm {
	
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(MoveOrbeonForm.class);


	/**
	 * Bean constructor.
	 */
	private MoveOrbeonForm() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	private static void moveOrbeonForm() {
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		OrbeonService orbeonService = (OrbeonService) BeanUtils.getBean("orbeonService");
		
		try { 
//			DatabaseUtils.open();
//			DatabaseUtils.begin();

			LOG.info("lancement de la procédure moveOrbeonForm");
			// on récupère toutes les commissions
			Set<Commission> commissions = parameterService.getCommissions(null);
			
			for (Commission cmi : commissions) {
				Set<TraitementCmi> traitements = cmi.getTraitementCmi();
				
				for (TraitementCmi trt : traitements) {
					String formNameFrom = trt.getVersionEtpOpi().getCodEtp() + "-" 
							+ trt.getVersionEtpOpi().getCodVrsVet();
					String formNameTo = formNameFrom + "-FI";
					if (formNameFrom.equals("E20261-281")) {
						LOG.debug("cas E20261-281");
					}
					// on déplace les formulaires dans l'arborescence FI
					orbeonService.copyForm(formNameFrom, formNameTo);
					
					// on déplace les templates
					orbeonService.copyTemplateForm(formNameFrom, formNameTo);
					
					// on supprime le dossier contenant les réponses et templates
					orbeonService.deleteFolder(formNameFrom);
				}
				
			}

//			DatabaseUtils.commit();
			
			LOG.info("procédure moveOrbeonForm terminée");

		} catch (Exception e) {
			LOG.error(e);
			ExceptionUtils.catchException(e);
//			DatabaseUtils.rollback();
//		} finally {
//			DatabaseUtils.close();
		}
	}


	/**
	 * Print the syntax and exit.
	 */
	private static void syntax() {
		throw new IllegalArgumentException(
				"syntax: " + MoveOrbeonForm.class.getSimpleName());
	}



	/**
	 * Dispatch dependaing on the arguments.
	 * @param args
	 */
	protected static void dispatch(final String[] args) {
		switch (args.length) {
		case 0:
			moveOrbeonForm();
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
