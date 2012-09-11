package org.esupportail.opi.batch;

import java.util.Set;

import org.esupportail.apogee.domain.dto.enseignement.VersionDiplomeDTO;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;

import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;


/**
 * @author cleprous
 * Ce batch permet de mettre à jour la table TRT_CMI_VET apres l'ajout de colonne COD_DIP et COD_VRS_VDI.
 * Après l'execution du batch ne pas oublier d'ajouter les contraintes not-null sur ces 2 colonnes.
 */
public class SetTrtCmi  {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(SetTrtCmi.class);


	/**
	 * Bean constructor.
	 */
	private SetTrtCmi() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	private static void setTrtCmi() {
		DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		DomainApoService domainApoService = (DomainApoService) BeanUtils.getBean("domainApoService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();
		
			// récupération de toutes les commissions
			Set<Commission> allcmi = parameterService.getCommissions(null);
			
			
			
			// on met à jour les traitment cmi
			for (Commission c : allcmi) {
				for (TraitementCmi t : c.getTraitementCmi()) {
					Campagne camp = parameterService.getCampagneEnServ(FormationInitiale.CODE);
					VersionDiplomeDTO vdiDTO = 
						domainApoService.getVersionDiplomes(
								t.getVersionEtpOpi(), camp);
					t.setCodDip(vdiDTO .getCodDip());
					t.setCodVrsDip(vdiDTO.getCodVrsVdi());
					t.setTemoinEnService(true);
					//because create the column
					TraitementCmi trt = (TraitementCmi) domainService.add(t, "BATCH setTrtCMi");
					
					TraitementCmi trt2 = 
						(TraitementCmi) domainService.update(trt, "BATCH setTrtCMi");
					parameterService.updateTraitementCmi(trt2);
				}
			}
			
			LOG.info("procédure terminée");
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
				"syntax: " + SetTrtCmi.class.getSimpleName() + " <options>"
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
			setTrtCmi();
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
