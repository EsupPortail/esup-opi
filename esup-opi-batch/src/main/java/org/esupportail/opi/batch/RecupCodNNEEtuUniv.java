package org.esupportail.opi.batch;

import java.util.List;

import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;

import org.esupportail.opi.dao.DaoService;
import org.esupportail.opi.domain.beans.user.Individu;
import gouv.education.apogee.commun.client.ws.etudiantmetier.EtudiantMetierServiceInterfaceProxy;
import gouv.education.apogee.commun.servicesmetiers.EtudiantMetierServiceInterface;
import gouv.education.apogee.commun.transverse.dto.etudiant.IdentifiantsEtudiantDTO;


/**
 * @author ylecuyer
 * Ce batch permet de recuperer tous les individus ayant un codeEtu
 * et de mettre à jour leurs codeNNE
 * Reparation de l'incoherence créée lors de la création de dossier par l'ENT
 */
public class RecupCodNNEEtuUniv  {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(RecupCodNNEEtuUniv.class);


	/**
	 * Bean constructor.
	 */
	private RecupCodNNEEtuUniv() {
		throw new UnsupportedOperationException();
	}

	/**
	 * recupere tous les individus ayant un codeEtu.
	 * et met à jour leurs codeNNE
	 */
	private static void recupCodNNE() {
		DaoService daoService = (DaoService) BeanUtils.getBean("daoService");
//		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
//		Transfert transfert = (Transfert) BeanUtils.getBean("transfert");
		try {

			DatabaseUtils.open();
			DatabaseUtils.begin();
			List<Individu> individus = daoService.getAllIndividusEtu();
//			List<Individu> individus = daoService.getIndividus(null, transfert.getCode());
			LOG.debug("Nombre d'étudiants de l'université : " + individus.size());
			
			EtudiantMetierServiceInterface etudiantMetierService = 
				new EtudiantMetierServiceInterfaceProxy();
			
			int nbEtuUp = 0;
			for (Individu ind : individus) {
				if (ind.getCodeNNE() == null) {
					LOG.debug("ind num dos : " + ind.getNumDossierOpi());
					// Recherche l'etudiant dans Apogee
					IdentifiantsEtudiantDTO etudiant = etudiantMetierService.
							recupererIdentifiantsEtudiant(ind.getCodeEtu(), null, 
									null, null, null, null, null, null, null, "O");
					ind.setCodeNNE(etudiant.getNumeroINE());
					ind.setCodeClefNNE(etudiant.getCleINE());
					daoService.updateUser(ind);
					nbEtuUp++;
				}
			}
			LOG.debug("Nombre d'étudiants mis à jour : " + nbEtuUp);
			DatabaseUtils.commit();

		} catch (Exception e) {
			LOG.error(e);
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
				"syntax: " + RecupCodNNEEtuUniv.class.getSimpleName() + " <options>"
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
			recupCodNNE();
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
