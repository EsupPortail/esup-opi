package org.esupportail.opi.batch;

import java.util.HashSet;
import java.util.List;

import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;

import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.user.Individu;


/**
 * @author ylecuyer
 * Ce batch permet de mettre à jour la campagne d'un individu.
 */
public class SetCampagneToInd  {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(SetCampagneToInd.class);


	/**
	 * Bean constructor.
	 */
	private SetCampagneToInd() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	private static void setCampagne() {
		DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();
		
			// récupération de tous les individus
			List<Individu> individus = domainService.getIndividusWishes(true, null);
			
			// récupération de la campagne
			Campagne camp = parameterService.getCampagneEnServ(0);
			
			// on met à jour la campagne
			for (Individu ind : individus) {
				if (ind.getCampagnes() == null) {
					ind.setCampagnes(new HashSet<Campagne>());
				}
				ind.getCampagnes().add(camp);
				domainService.updateUser(ind);
			}
			DatabaseUtils.commit();

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
			setCampagne();
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
