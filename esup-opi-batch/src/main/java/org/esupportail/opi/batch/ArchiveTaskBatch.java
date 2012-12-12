package org.esupportail.opi.batch;

import java.io.IOException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.parsers.ParserConfigurationException;

import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.OrbeonService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.pilotage.ArchiveTask;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.situation.IndSituation;
import org.esupportail.opi.services.archives.ArchiveService;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;
import org.xml.sax.SAXException;


/**
 * @author ylecuyer
 *
 */
public class ArchiveTaskBatch extends QuartzJobBean {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(ArchiveTaskBatch.class);
	
	/**
	 * The regime d'inscription.
	 */
	private static List<RegimeInscription> regimeInscriptions;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public ArchiveTaskBatch() {
		super();
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 * Launch the archive task(s) of the day.
	 */
	private static void launchArchiveTasks() {
		DomainService domainService = (DomainService) ApplicationContextHolder.getContext().getBean("domainService");
		ParameterService parameterService = (ParameterService) ApplicationContextHolder.getContext().getBean("parameterService");
		ArchiveService archiveService = (ArchiveService) ApplicationContextHolder.getContext().getBean("archiveService");
		OrbeonService orbeonService = (OrbeonService) ApplicationContextHolder.getContext().getBean("orbeonService");

		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();
			LOG.info("procédure launchArchiveTasks lancée");
			// on récupère les taches d'archivage non exécutés
			List<ArchiveTask> archTasks = domainService.getArchiveTasksToExecute();
			
			// on récupère la ou les taches à exécuter aujourd'hui
			List<ArchiveTask> archTasksToday = new ArrayList<ArchiveTask>();
			SimpleDateFormat formatter = new SimpleDateFormat(Constantes.ENGLISH_DATE_FORMAT);
			ParsePosition pos = new ParsePosition(0);
			Date today = formatter.parse(Utilitaires.
					convertDateToString(new Date(), Constantes.ENGLISH_DATE_FORMAT), pos);
			for (ArchiveTask archT : archTasks) {
				if (archT.getDateArchive().equals(today)) {
					archTasksToday.add(archT);
				}
			}
			
			for (ArchiveTask archT : archTasksToday) {
				// pour chaque tache, on récupère la campagne à archiver et celle à activer
				Campagne campToArch = archT.getCampagneToArchive();
				Campagne campToAct = archT.getCampagneToActive();

				RegimeInscription regime = null;
				for (RegimeInscription reg : regimeInscriptions) {
					if (reg.getCode() ==  campToArch.getCodeRI()) {
						regime = reg;
					}
				}
				/** 
				 * ARCHIVAGE des individus, de leurs voeux et des traitements cmi de la campagne
				 **/
				// on récupère les individus de la campagne
				/** 1 requête **/
				List<Individu> indToArch = domainService.getIndividusByCampagne(campToArch, true);
				
				int nbOfIndArch = 0;
				int nbOfIndDel = 0;
				
				LOG.info("Début de l'archivage des individus. " 
						+ indToArch.size() + " individus à traiter");
				
				/** Purge des tables IND_FORMULAIRES, RDV_INDIVIDU_DATE et MISSING_PIECE. **/
				// Récupération de la liste des indFormulaire 
				// pour faire la liste des formName pour orbeon
				Map<String, List<String>> mapFormName = parameterService
										.getAllFormNamesMap(campToArch, regime.getShortLabel());
				
				archiveService.purgeTablesCamp(campToArch);
				
				for (Individu ind : indToArch) {
					// on supprime les formulaires
					// récupération des formulaires
					
					/** 2 requêtes (1 si il existe des voeux)**/
					if (ind.getVoeux().isEmpty() && ind.getArchVoeux().isEmpty()) {
						// s'il n'a pas de voeux, on supprime cet individu
						/** 1 requête + cascading**/
						// suppression du indSituation s'il y en a un
						IndSituation indSit = domainService.getIndSituation(ind);
						if (indSit != null) {
							domainService.deleteIndSituation(indSit);
						}
						LOG.info("Suppression de : " + ind + " terminée");
						domainService.deleteUser(ind);
						nbOfIndDel++;
					} else {
						// on archive l'individu en lui même
						/** 2 requêtes **/
						archiveService.archiveIndividu(ind);
						LOG.info("Archivage de : " + ind + " terminé");
						nbOfIndArch++;
					}
				}
				LOG.info("Archivage des individus terminé. Rapport : "
						+ indToArch.size() + " individus traités ; "
						+ nbOfIndArch + " individus archivés ; "
						+ nbOfIndDel + " individus supprimés.");
				
				// on archive les traitements cmi de la campagne
				// et on ajoute les traitementCmi pour le recrutement de la nouvelle campagne
				Set<LinkTrtCmiCamp> linksToArch = campToArch.getLinkTrtCmiCamp();
				for (LinkTrtCmiCamp link : linksToArch) {
					/** nb link requêtes **/
					archiveService.addTraitementCmiToNewCamp(link.getTraitementCmi(), campToAct);
					// et on met hors service les voeux
					// méthode de mise HS des voeux d'un individu
					/** 1 requête **/
					domainService.updateIndVoeuTemEnServ(link, false);			
				}
				// méthode de mise HS des links
				/** 1 requête **/
				parameterService.updateLinkTrtCmiCampTemEnServ(campToArch, false);
				LOG.info("Archivage des traitements cmi terminé. Rapport : "
						+ linksToArch.size() + " trtCmi traités ; ");
				
				// on met hors service la campagne à archiver
				// on active la nouvelle campagne
				campToArch.setIsArchived(true);
				campToArch.setTemoinEnService(false);
				/** 1 requête **/
				parameterService.updateNomenclature((Campagne) domainService.
						update(campToArch, "BATCH ArchiveTask"));
				
				LOG.info("Campagne archivée");

				/** 
				 * ACTIVATION des individus, de leurs voeux et des traitements cmi de la campagne
				 **/
				// on récupère les éventuels individus de la campagnes à activer
				// au cas ou elle a déjà été archivée
				List<Individu> indToAct = domainService.getIndividusByCampagne(campToAct, false);
				Set<LinkTrtCmiCamp> linksTrtCmiCamp = new HashSet<LinkTrtCmiCamp>();
				if (indToAct != null && !indToAct.isEmpty()) {
					for (Individu ind : indToAct) {
						// on le remet en service
						ind.setTemoinEnService(true);
						/** nb ind requête **/
						domainService.updateUser(ind);
					}
					// on récupère les liens entre la campagne, les traitements
					// et les voeux
					linksTrtCmiCamp.addAll(campToAct.getLinkTrtCmiCamp());
					for (LinkTrtCmiCamp link : linksTrtCmiCamp) {
						// méthode de mise en service des voeux d'un individu
						/** nb link requête **/
						domainService.updateIndVoeuTemEnServ(link, true);
					}
					LOG.info("Activation des individus terminé. Rapport : "
							+ indToAct.size() + "individus traités.");

				} else {
					LOG.info("Activation des individus terminé. Rapport : "
							+ "0 individu traité.");
				}
				
				// on réactive les éventuels traitements cmi de la campagne
				// méthode de mise en service des links
				parameterService.updateLinkTrtCmiCampTemEnServ(campToAct, true);

				LOG.info("Activation des trtCmi terminé. Rapport : "
						+ linksTrtCmiCamp.size() + " link traités ; ");

				campToAct.setTemoinEnService(true);
				campToAct.setIsArchived(false);
				parameterService.updateNomenclature((Campagne) domainService
						.update(campToAct, "BATCH ArchiveTask"));
				
				LOG.info("Campagne activée");

				// on met à jour la tache d'archivage
				archT.setIsExecuted(true);
				domainService.updateArchiveTask((ArchiveTask) domainService
						.update(archT, "BATCH ArchiveTask"));
						
							
				// Purge dans Orbeon
				for (Map.Entry<String, List<String>> entryFormName : mapFormName.entrySet()) {
					for (String formName : entryFormName.getValue()) {
						try {
							orbeonService.removeResponse(formName, entryFormName.getKey());
						} catch (SAXException e) {
							e.printStackTrace();
						} catch (ParserConfigurationException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
			DatabaseUtils.commit();
			
			LOG.info("procédure launchArchiveTasks terminée");
	
		} catch (Exception e) {
			DatabaseUtils.rollback();
			LOG.error("Exception dans launchArchiveTasks : " + e);
		} finally {
			DatabaseUtils.close();
		}
	}
	
	/** 
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(final JobExecutionContext arg0)
			throws JobExecutionException {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering executeInternal");
		}
		launchArchiveTasks();
	}

	/**
	 * Print the syntax and exit.
	 */
	private static void syntax() {
		throw new IllegalArgumentException(
				"syntax: " + ArchiveTaskBatch.class.getSimpleName() + " <options>"
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
			launchArchiveTasks();
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
//			ApplicationService applicationService = ApplicationUtils.createApplicationService();
//			LOG.info(applicationService.getName() + " v" + applicationService.getVersion());
			dispatch(args);
		} catch (Throwable t) {
			ExceptionUtils.catchException(t);
		}
	}
	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @param regimeInscriptions the regimeInscriptions to set
	 */
	@SuppressWarnings("static-access")
	public void setRegimeInscriptions(final List<RegimeInscription> regimeInscriptions) {
		this.regimeInscriptions = regimeInscriptions;
	}


}
