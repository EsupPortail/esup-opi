package org.esupportail.opi.batch;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

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
import org.esupportail.opi.domain.beans.user.User;


/**
 * @author brice.quillerie
 *
 */
//@Deprecated //l'action faite par cleanUp est faite dans l'ArchiveTaskBatch fait le 27/10/2009
public class CleanUp {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(CleanUp.class);
	
	// definition des proprietes avec les getters et setters associcies
//	/**
//	 * Le domainService pour supprimer les enregistrements inutiles.
//	 */
//	private DomainService domainService;
//
//	/**
//	 * see {@link ParameterService}.
//	 */
//	private ParameterService parameterService;

	/**
	 * Constructors.
	 */
	private CleanUp() { 
		throw new UnsupportedOperationException();
	}
	
//	/**
//	 * @param domainService
//	 * the domainService to set
//	 */
//	public void setDomainService(final DomainService domainService) {
//		this.domainService = domainService;
//	}
//
//	/**
//	 * @param parameterService the parameterService to set
//	 */
//	public void setParameterService(final ParameterService parameterService) {
//		this.parameterService = parameterService;
//	}
//
	

	/**************************
	 * Methode de nettoyage.
	 **************************/
	public static void nettoie() {
		DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
//		SimpleDateFormat formatter = new SimpleDateFormat(Constantes.ENGLISH_DATE_FORMAT);
//		ParsePosition pos = new ParsePosition(0);
//		Date today = formatter.parse(
//				Utilitaires.convertDateToString(new Date(), Constantes.ENGLISH_DATE_FORMAT), pos);
		
		// On regarde si on est avant une date de fin de calendrier d'inscription 
//		List<Calendar> calIns = parameterService.getCalendars(true, Calendar.TYPE_CAL_INSCRIPTION);
//		for (Calendar c : calIns) {
//			CalendarIns cIns = (CalendarIns) c;
//			if (today.before(cIns.getEndDate())) { 
//				System.out.println("Il y a au moins un calendrier ouvert : " + cIns);
//				return; 
//			}
//		}
		
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();
			LOG.info("procédure nettoie lancée");
			// Suppression des individus sans voeux
//			domainService.deleteIndividusSansVoeux();
			
			Set<Campagne> campagnes = parameterService.getCampagnesAnu("2009");
			// TODO : les campagnes doivent être archivées
			for (Campagne campagne : campagnes) {
				List<Individu> individus = domainService.getIndividusByCampagne(campagne, true);
				List<User> usersToDel = new ArrayList<User>();
				LOG.info("nombre d'individu en service dans la campagne : " + individus.size());
				for (Individu ind : individus) {
//					domainService.deleteUser(ind);
					boolean delUser = true;
					for (Campagne camp : ind.getCampagnes()) {
						if (camp.getTemoinEnService()) {
							delUser = false;
						}
					}
					if (ind.getVoeux().isEmpty() && ind.getArchVoeux().isEmpty() && delUser) {
						if (ind.getId() == 2386) {
							LOG.info("cas 2386");
						}
						ind.setCampagnes(null);
						domainService.deleteUser(ind);
						//usersToDel.add(ind);
					}
				}
				LOG.info("nombre d'individu à supprimer dans la campagne : " + usersToDel.size());
				//domainService.deleteUserList(usersToDel);
			}
			
			DatabaseUtils.commit();
			
			LOG.info("procédure nettoie terminée");
	
		} catch (Exception e) {
			DatabaseUtils.rollback();
			LOG.error("Exception dans nettoie : " + e);
		} finally {
			DatabaseUtils.close();
		}
		// Autre chose ?
	}
	
	/**************************
	 * Pour l'execution quartz 
	 **************************/
	
	/** 
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
//	@Override
//	protected void executeInternal(final JobExecutionContext ctx) throws JobExecutionException {
//		System.out.println("Dans l'execution du quartz CleanUp ... ");
//		
//		nettoie();
//	}
//
	
	
	/**************************
	 * Pour l'execution manuelle 
	 **************************/
	
	
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
			nettoie();
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
