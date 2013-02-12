package org.esupportail.opi.batch;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;

import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;

/**
 * @author ylecuyer
 * TODO : IMPORTANT, batch à exécuter AVANT de supprimer les colonnes
 * formant VersionEtpOpi
 *
 */
public class CreateTrtCmiCamp {
	
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(CreateTrtCmiCamp.class);


	/**
	 * Bean constructor.
	 */
	private CreateTrtCmiCamp() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	private static void createTrtCmiCamp() {
		//DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();

			LOG.info("lancement de la procédure createTrtCmiCamp");
			
			// récupération de la campagne en cours
			Campagne camp = parameterService.getCampagneEnServ(1);

			// on récupère toutes les commissions
			Set<Commission> commissions = parameterService.getCommissions(null);
			
			for (Commission cmi : commissions) {
				// pour chaque commission, on traite chaque traitement cmi
				// afin de créer le linkTrtCmiCamp correspondant
				Set<TraitementCmi> traitements = cmi.getTraitementCmi();
				
				// map utilisée pour faire le lien avec les vet des voeux
				Map<VersionEtpOpi, LinkTrtCmiCamp> mapVetLinkTrtCmiCamp = 
					new HashMap<VersionEtpOpi, LinkTrtCmiCamp>();
				
				for (TraitementCmi trt : traitements) {
					if (parameterService.getLinkTrtCmiCamp(trt, camp) == null) {
						// création du linkTrtCmiCamp
						LinkTrtCmiCamp linkTrtCmiCamp = new LinkTrtCmiCamp();
						linkTrtCmiCamp.setTraitementCmi(trt);
						linkTrtCmiCamp.setCampagne(camp);
						
						// sauvegarde en base
						parameterService.addLinkTrtCmiCamp(linkTrtCmiCamp);
						
						// on ajoute le linkTrtCmiCamp à la map
						VersionEtpOpi vet = trt.getVersionEtpOpi();
						if (!mapVetLinkTrtCmiCamp.containsKey(vet)) {
							mapVetLinkTrtCmiCamp.put(vet, linkTrtCmiCamp);
						}
						if (vet.getCodEtp().equals("SL3051")) {
							LOG.info("cas SL3051");
						}
					}
				}
				
//				List<Individu> individus = domainService.getIndividus(cmi, null);
//				// pour chaque individu de la commission, on ajoute chaque linkTrtCmiCamp
//				for (Individu ind : individus) {
//					for (IndVoeu indVoeu : ind.getVoeux()) {
//						VersionEtpOpi vet = indVoeu.getVersionEtpOpi();
//						if (vet.getCodEtp().equals("SL3051")) {
//							LOG.info("cas SL3051");
//						}
//						if (mapVetLinkTrtCmiCamp.containsKey(vet)) {
//							// on ajoute le linkTrtCmiCamp puis on met à jour en base
//							indVoeu.setLinkTrtCmiCamp(mapVetLinkTrtCmiCamp.get(vet));
//							domainService.updateIndVoeu(indVoeu);
//						}
//					}
//				}
			
			}
			
			DatabaseUtils.commit();
			
			LOG.info("procédure createTrtCmiCamp terminée");

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
			createTrtCmiCamp();
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
