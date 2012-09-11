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
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.ContactCommission;

/**
 * @author ylecuyer
 * TODO : IMPORTANT, batch à exécuter AVANT de supprimer les colonnes dans commission
 * Rempli la table des contacts de commissions
 */
public class CreateContactCommission {
	
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(CreateContactCommission.class);


	/**
	 * Bean constructor.
	 */
	private CreateContactCommission() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	@SuppressWarnings("unused")
	private static void createContactCommission() {
		//DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();

			LOG.info("lancement de la procédure createContactCommission");
			

			// on récupère toutes les commissions
			Set<Commission> commissions = parameterService.getCommissions(null);
			
			for (Commission cmi : commissions) {
				// on crée les contacts commissions pour la FI
				
				ContactCommission contactCommission = new ContactCommission();
				// TODO ligne à décommenter pour lancer le batch
//				contactCommission.setAdresse(cmi.getAdress());
//				contactCommission.setCodeRI(FormationInitiale.CODE);
//				contactCommission.setCodSig(cmi.getCodSig());
//				contactCommission.setCorresponding(cmi.getCorresponding());
//				contactCommission.setCommission(cmi);
//				parameterService.addContactCommission(contactCommission);
			}
			
			DatabaseUtils.commit();
			
			LOG.info("procédure createContactCommission terminée");

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
			createContactCommission();
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
