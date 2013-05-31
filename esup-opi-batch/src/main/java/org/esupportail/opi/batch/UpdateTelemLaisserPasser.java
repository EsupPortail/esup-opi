package org.esupportail.opi.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import fj.data.Stream;
import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.OpiWebService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatConfirme;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.springframework.util.StringUtils;


/**
 * @author ylecuyer
 * Ce batch permet de relancer l'appel au webservice pour le deversement dans Apogée.
 * des candidats ayant confirmé leurs inscriptions.
 */
public class UpdateTelemLaisserPasser  {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(UpdateTelemLaisserPasser.class);


	/**
	 * Bean constructor.
	 */
	private UpdateTelemLaisserPasser() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	private static void update() {
		DomainService domainService = (DomainService) ApplicationContextHolder.getContext().getBean("domainService");
		DomainApoService domainApoService = (DomainApoService) ApplicationContextHolder.getContext().getBean("domainApoService");
		ParameterService parameterService = (ParameterService) ApplicationContextHolder.getContext().getBean("parameterService");
		OpiWebService opiWebService = (OpiWebService) ApplicationContextHolder.getContext().getBean("opiWebService");
		try {

			DatabaseUtils.open();
			DatabaseUtils.begin();
			Set<Commission> commissions = parameterService.getCommissions(true);
			List<Individu> iAlreadyAddInApo = new ArrayList<>();
			int nbIndApo = 0;
			for (Commission cmi : commissions) {
				Stream<Individu> individus = domainService.getIndividusCommission(cmi, true, null);

                // TODO : n'a pas l'air de servir à grand chose...
				List<VersionEtpOpi> vets = new ArrayList<>();
				for (TraitementCmi trt : cmi.getTraitementCmi()) {
					vets.add(trt.getVersionEtpOpi());
				}

				for (Individu i : individus) {
					if (!iAlreadyAddInApo.contains(i)) {
						List<IndVoeu> list = new ArrayList<>();
						for (IndVoeu indVoeu : i.getVoeux()) {
							if (indVoeu.getState().equals(EtatConfirme.I18N_STATE)) {
								list.add(indVoeu);
							}
						}
						if (!list.isEmpty() && opiWebService != null) {
							//on deverse tous les temps dans Apogee
							opiWebService.launchWebService(i, list);
						
							//on creer les laisser passer 
							domainApoService.addTelemLaisserPasser(list, 
									StringUtils.hasText(i.getCodeEtu()));
							
							iAlreadyAddInApo.add(i);
							nbIndApo++;
						}
					}
				}
					
			}
			
			LOG.info(nbIndApo + " individus ont été déposés dans Apogée.");
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
				"syntax: " + UpdateTelemLaisserPasser.class.getSimpleName() + " <options>"
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
			update();
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
