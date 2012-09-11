package org.esupportail.opi.batch;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.esupportail.apogee.domain.dto.enseignement.VersionEtapeDTO;
import org.esupportail.apogee.services.remote.ReadEnseignement;
import org.esupportail.apogee.services.remote.ReadReferentiel;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.BeanUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.web.utils.Constantes;
import org.esupportail.opi.web.utils.Utilitaires;


/**
 * @author brice.quillerie
 * Envoie un mail à tous les candidats qui ont confirmé leur indiquant que les inscriptions en ligne sont ouvertes.
 */
@Deprecated //27/10/2009 car pas teste
public class InscriptionOuverte extends QuartzJobBean {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(CleanUp.class);

	// definition des proprietes avec les getters et setters associcies

	/**
	 * 
	 */
	private String dateOuvertureReins;

	/**
	 * 
	 */
	private String dateOuverturePrimoNb;

	/**
	 * 
	 */
	private String dateOuverturePrimoNnb;

	/**
	 * 
	 */
	private SmtpService smtpService;
	/**
	 * 
	 */
	private I18nService i18nService;
	/**
	 * Le domainService pour supprimer les enregistrements inutiles.
	 */
	private DomainService domainService;

	/**
	 * Constructors.
	 */
	public InscriptionOuverte() {
		super();
		smtpService = (SmtpService) BeanUtils.getBean("smtpService");
		i18nService = (I18nService) BeanUtils.getBean("i18nService");

	}
	/**
	 * @param dateOuvertureReins the dateOuvertureReins to set
	 */
	public void setDateOuvertureReins(final String dateOuvertureReins) {
		this.dateOuvertureReins = dateOuvertureReins;
	}
	/**
	 * @param dateOuverturePrimoNnb the dateOuverturePrimo to set
	 */
	public void setDateOuverturePrimoNnb(final String dateOuverturePrimoNnb) {
		this.dateOuverturePrimoNnb = dateOuverturePrimoNnb;
	}
	/**
	 * @param dateOuverturePrimoNb the dateOuverturePrimo to set
	 */
	public void setDateOuverturePrimoNb(final String dateOuverturePrimoNb) {
		this.dateOuverturePrimoNb = dateOuverturePrimoNb;
	}

	/**
	 * @param domainService
	 * the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}

	/** 
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	protected void inscriptionOuverte() {
		// On recupere le WS referentiel
		ReadEnseignement remoteCriApogeeEns = (ReadEnseignement) BeanUtils.getBean("remoteCriApogeeEns");

		// Date d'aujourd'hui
		Date tmp = new Date();
		String affichage;

		boolean primoNbOpen = false;
		boolean primoNnbOpen = false;
		boolean iawebOpen = false;
		if (dateOuverturePrimoNb != null && dateOuverturePrimoNnb != null && dateOuvertureReins != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy" );
			affichage = sdf.format(tmp);

			primoNbOpen = dateOuverturePrimoNb.equals(affichage);
			primoNnbOpen = dateOuverturePrimoNnb.equals(affichage);
			iawebOpen = dateOuvertureReins.equals(affichage);
		} else {
			// On recupere le WS referentiel
			ReadReferentiel remoteCriApogeeRef = (ReadReferentiel) BeanUtils.getBean("remoteCriApogeeRef");

			// Sous la forme '01-01-2002 00:00:00'
			SimpleDateFormat sdf = new SimpleDateFormat(Constantes.ENGLISH_DATE_FORMAT);
			affichage = sdf.format(tmp);

			primoNbOpen = remoteCriApogeeRef.getDateDebWebPrimoNb().equals(affichage);
			iawebOpen = remoteCriApogeeRef.getDateDebWeb().equals(affichage);
			primoNnbOpen = remoteCriApogeeRef.getDateDebWebPrimoNnb().equals(affichage);
			System.out.println("getDateDebWeb " + remoteCriApogeeRef.getDateDebWeb());
			System.out.println("getDateDebWebPrimoNb " + remoteCriApogeeRef.getDateDebWebPrimoNb());
			System.out.println("getDateDebWebPrimoNnb " + remoteCriApogeeRef.getDateDebWebPrimoNnb());
		}

		// Rien ouvre aujourd'hui (la plupart du temps)
		if (!iawebOpen && !primoNbOpen && !primoNnbOpen) {
			System.out.println("Today (" + affichage + ") IA Web is not opened ; " 
					+ "it will be the " + dateOuvertureReins + " ... ");
			// System.out.println("Today (" + affichage + ") Primo is not opened ; " 
			//		+ "it will be the " + dateOuverturePrimo + " ... ");
			return;
		}

		// Ouverture de reins
		if (iawebOpen) {
			System.out.println("Reins is opened ... preparing to mail the admits candidates.");
		}
		// Ouverture de primo
		if (primoNbOpen || primoNnbOpen) {
			System.out.println("Primo is opened ... preparing to mail the admits candidates.");
		}

		// Recuperation des voeux ayant un Avis avec validation et acceptation
		List<IndVoeu> voeuxValides = domainService.getVoeuxAcceptes();
		if (voeuxValides == null) {
			System.out.println("Il n'y a aucun voeu valide at accepte !");
			return;
		}
		System.out.println("Nombre de voeux valides : " + voeuxValides.size());

		// Envoi de mail e chaque etudiant pour l'avertir de l'ouverture des inscriptions
		for (IndVoeu voeu : voeuxValides) {
			boolean etudiant = false;
//			boolean etudiant = Utilitaires.isStringValid(voeu.getIndividu().getCodeEtu(), 
//					domainService.getCodStudentRegex());
//			System.out.println("Voeu : " + voeu + " etudiant : " + etudiant);

			TraitementCmi trtCmi = voeu.getLinkTrtCmiCamp().getTraitementCmi();
			// On regarde si l'etape est ouverte en (re)inscription par le Web
			VersionEtapeDTO vet = remoteCriApogeeEns.getVersionEtape(
					trtCmi.getVersionEtpOpi().getCodEtp(), trtCmi.getVersionEtpOpi().getCodVrsVet());
			if (vet.getCodCgeMinVet() != null) {
				// On n'envoie pas de mail pour les rendez vous
				
				if (iawebOpen && etudiant) {
					// C'est pour une reinscription ==> Reins est ouvert
					Utilitaires.sendEmailIndividu(
							i18nService.getString("MAIL.OPEN.REINS.SUBJECT"), 
							i18nService.getString("MAIL.OPEN.REINS.BODY"), null, 
							voeu.getIndividu(),
							smtpService, i18nService);
				} else if ((primoNbOpen || primoNnbOpen) && !etudiant) {
					// C'est pour un primo nouveau bachelier ==> Primo est ouvert
					Utilitaires.sendEmailIndividu(
							i18nService.getString("MAIL.OPEN.PRIMO.SUBJECT"), 
							i18nService.getString("MAIL.OPEN.PRIMO.BODY"), null,
							voeu.getIndividu(),
							smtpService, i18nService);
				}
			} 
		}
	}

	/**************************
	 * Pour l'execution quartz 
	 **************************/

	/** 
	 * @see org.springframework.scheduling.quartz.QuartzJobBean#executeInternal(org.quartz.JobExecutionContext)
	 */
	@Override
	protected void executeInternal(final JobExecutionContext ctx) throws JobExecutionException {
		System.out.println("Dans l'execution du quartz InscriptionOuverte ... ");

		inscriptionOuverte();
	}



	/**************************
	 * Pour l'execution manuelle 
	 **************************/


	/**
	 * The main method, called by ant.
	 * @param args
	 */
	public static void main(final String[] args) {
		try {
			ApplicationService applicationService = ApplicationUtils.createApplicationService();
			LOG.info(applicationService.getName() + " v" + applicationService.getVersion());
			
			InscriptionOuverte io = new InscriptionOuverte();
			io.dispatch(args);
		} catch (Throwable t) {
			ExceptionUtils.catchException(t);
		}
	}

	/**
	 * Dispatch dependaing on the arguments.
	 * @param args
	 */
	protected void dispatch(final String[] args) {
		switch (args.length) {
		case 0:
			inscriptionOuverte();
			break;
		default:
			syntax();
		break;
		}
	}

	/**
	 * Print the syntax and exit.
	 */
	private static void syntax() {
		throw new IllegalArgumentException(
				"syntax: " + InscriptionOuverte.class.getSimpleName());
	}

}
