package org.esupportail.opi.batch;

import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.PilotageService;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.web.beans.parameters.FormationContinue;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.utils.ExportUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.BeanUtils;
import org.jfree.util.Log;

/**
 * @author cgomez
 * 
 */
public class UpdateFormulaireCompl {
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(
			UpdateFormulaireCompl.class);
	/**
	 * 
	 */
	private static final ParameterService PARAMETER_SERVICE = (ParameterService) BeanUtils
			.getBean("parameterService");

	private static final PilotageService PILOTAGE_SERVICE = (PilotageService) BeanUtils
			.getBean("pilotageService");
	
	private static final FormationContinue BEAN_FC = (FormationContinue) BeanUtils
	.getBean("formationContinue");
	
	private static final FormationInitiale BEAN_FI = (FormationInitiale) BeanUtils
	.getBean("formationInitiale");
	
	private static final int TROIS = 3;
	
	private static final int QUATRE = 4;
	
	private static final int QUARANTE = 40;
	

	/**
	 * 
	 */
	private static String lien = "/data/appli/esup-opi/";
	/**
	 * Le code CGE.
	 */
	private static String codCge;
	/**
	 * Le code étape.
	 */
	private static String codEtp;
	/**
	 * Le code version VET.
	 */
	private static int codVrsVet;
	/**
	 * Le régime d'insription (FI ou FC).
	 */
	private static String ri;
	
	/**
	 * Le fichier utilisé pour l'export.
	 */
	private static File file;
	/**
	 * 
	 */
	private static RandomAccessFile randomAccessFile;
	/**
	 * 
	 */
	private static List<String> champsChoisis;
	/**
	 * Régime d'inscription utilisé pour l'export.
	 */
	private static RegimeInscription regimeInscription;

	/**
	 * Bean constructor.
	 */
	private UpdateFormulaireCompl() {
		throw new UnsupportedOperationException();
	}

	/**
	 * 
	 */
	private static void initFile() {
		file = new File(lien + "recupOrbeon-" + codEtp + "-" + codVrsVet
				+ ".csv");

		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			randomAccessFile = new RandomAccessFile(file, "rw");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	private static void scriptUpdate() {
		LOG.info("début de la procédure");

		try {
			DatabaseUtils.open();
			DatabaseUtils.begin();
			
			champsChoisis = new ArrayList<String>();
			
			VersionEtpOpi versionEtpOpi = new VersionEtpOpi();
			versionEtpOpi.setCodCge(codCge);
			versionEtpOpi.setCodEtp(codEtp);
			versionEtpOpi.setCodVrsVet(codVrsVet);

			if (PARAMETER_SERVICE.getTraitementCmi(versionEtpOpi, null) == null) {
				Log.warn("Il y a aucun traitement pour l'étape " + codEtp + "-"
						+ codVrsVet + "  du cge " + codCge);
				return;
			}
			LOG.info("étape : " + codEtp + "-" + codVrsVet + "  ----> cge : "
					+ codCge);

			initFile();
			// On initialise la variable choisis avec tous les champs.
			champsChoisis.addAll(PilotageService.LIB_BASE.keySet());
			
			//On initialise le regimeInscription
			if (StringUtils.equalsIgnoreCase(BEAN_FC.getShortLabel(), ri)) {
				regimeInscription = BEAN_FC;
			} else {
				regimeInscription = BEAN_FI;
			}
			
						
			Map<Integer, List<String>> map = PILOTAGE_SERVICE
					.prepareCsvFormulaire(versionEtpOpi, regimeInscription, champsChoisis);

			String exportCsv = ExportUtils.makeCvs(map);
			
			// Sauvegarde dans le fichier
			randomAccessFile.writeBytes(exportCsv);
			randomAccessFile.close();

			LOG.info("procédure terminée");
			DatabaseUtils.commit();

		} catch (Exception e) {
			DatabaseUtils.rollback();
			e.printStackTrace();
		} finally {
			DatabaseUtils.close();
		}
	}

	/**
	 * Print the syntax and exit.
	 */
	private static void syntax() {
		throw new IllegalArgumentException("syntax: "
				+ UpdateFormulaireCompl.class.getSimpleName() + " <options>"
				+ "\nwhere option can be:"
				+ "\n- test-beans: test the required beans");
	}

	/**
	 * Dispatch dependaing on the arguments.
	 * 
	 * @param args
	 */
	protected static void dispatch(final String[] args) {
		switch (args.length) {
		case 0:
			codCge = "MG5";
			codEtp = "MCOPT1";
			codVrsVet = QUARANTE;
			ri = "FI";
			scriptUpdate();
			break;
		case QUATRE:
			scriptUpdate();
			break;
		default:
			syntax();
			break;
		}
	}

	/**
	 * The main method, called by ant.
	 * 
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			if (args.length == QUATRE) {
				codCge = args[0];
				codEtp = args[1];
				codVrsVet = Integer.parseInt(args[2]);
				ri = args[TROIS];
			}
			
			
			ApplicationService applicationService = ApplicationUtils
					.createApplicationService();
			LOG.info(applicationService.getName() + " v"
					+ applicationService.getVersion());
			dispatch(args);
		} catch (NumberFormatException e) {
			LOG.error("la version étape doit être au format numérique.");
		} catch (Throwable t) {
			ExceptionUtils.catchException(t);
		}
	}
}
