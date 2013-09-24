package org.esupportail.opi.batch;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import fj.data.Stream;
import org.esupportail.commons.context.ApplicationContextHolder;
import org.esupportail.commons.services.application.ApplicationService;
import org.esupportail.commons.services.application.ApplicationUtils;
import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.services.exceptionHandling.ExceptionUtils;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatConfirme;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.web.beans.pojo.AdressePojo;
import org.esupportail.opi.web.beans.pojo.CommissionPojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;


/**
 * @author cleprous
 * Ce batch permet d'envoyer un mail a tout les candidats qui ont confirme au moins une candidature.
 * Ceci a ete fait car l'envoie du mail à la confirmation a été développé après 
 * l'ouverture du module de confirmation.
 */
public class SendMailConfirm  {

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(SendMailConfirm.class);


	/**
	 * Bean constructor.
	 */
	private SendMailConfirm() {
		throw new UnsupportedOperationException();
	}

	/**
	 * send the mail. 
	 */
	private static void send() {
		DomainService domainService = (DomainService) ApplicationContextHolder.getContext().getBean("domainService");
		ParameterService parameterService = (ParameterService) ApplicationContextHolder.getContext().getBean("parameterService");
		SmtpService smtpService = (SmtpService) ApplicationContextHolder.getContext().getBean("smtpService");
		I18nService i18Service = (I18nService) ApplicationContextHolder.getContext().getBean("i18nService");
		DomainApoService domainApoService = (DomainApoService) ApplicationContextHolder.getContext().getBean("domainApoService");
		try {

			DatabaseUtils.open();
			DatabaseUtils.begin();
			Set<Commission> commissions = parameterService.getCommissions(true);
			int nbMailsSend = 0;
			for (Commission cmi : commissions) {
				Stream<Individu> individus = domainService.getIndividusCommission(cmi, true, null);
				
				List<VersionEtpOpi> vets = new ArrayList<VersionEtpOpi>();
				for (TraitementCmi trt : cmi.getTraitementCmi()) {
					vets.add(trt.getVersionEtpOpi());
				}

				for (Individu i : individus) {
					Integer codeRi = Utilitaires.getCodeRIIndividu(i, domainService);
					//send the mail
					Set<IndVoeu> voeuxSendMail = new HashSet<IndVoeu>();
					for (IndVoeu indVoeu : i.getVoeux()) {
						TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
						if (indVoeu.getState().equals(EtatConfirme.I18N_STATE) 
								&& vets.contains(trtCmi.getVersionEtpOpi())) {
							voeuxSendMail.add(indVoeu);
						}
					}
					if (!voeuxSendMail.isEmpty()) {
						String htmlDebut = "";
						String htmlBody = "";
						String htmlSubject = "";
						String endBody = "";
						
						// récupération de la commission
						CommissionPojo currentCmiPojo = new CommissionPojo(cmi, 
								new AdressePojo(cmi.getContactsCommission().get(codeRi).getAdresse()),
								cmi.getContactsCommission().get(codeRi));
						
						htmlSubject = i18Service.getString("MAIL.CANDIDAT_AVIS.CONF.SUBJECT");
						htmlDebut += i18Service.getString("MAIL.CANDIDAT_AVIS.CONF.HTMLTEXT_DEBUT", 
								Utilitaires.getCivilite(i18Service,
										i.getSexe()));
						// list of libelle voeux
						StringBuilder htmlList = new StringBuilder();
						for (IndVoeu voeu : voeuxSendMail) {
							TraitementCmi trtCmi = voeu.getLinkTrtCmiCamp().getTraitementCmi();
							VersionEtapeDTO vet = domainApoService.getVersionEtape(
									trtCmi.getVersionEtpOpi().getCodEtp(), 
									trtCmi.getVersionEtpOpi().getCodVrsVet());
							htmlList.append(i18Service.getString("MAIL.LIST_VET", vet.getLibWebVet()));
						}
						htmlBody += htmlList.toString();
						
						htmlBody += i18Service.getString("MAIL.CANDIDAT_AVIS.CONF.HTMLTEXT_BODY1");
				
						// adresse CMI
						htmlBody += Utilitaires.getAdrCmiForSendMail(i18Service, currentCmiPojo, null);
						// formule politesse signature
						htmlBody += i18Service.getString("MAIL.CANDIDAT_FORMULE_POLITESSE.HTMLTEXT");
				
//						String subj = i18Service.getString("MAIL.CANDIDAT_AVIS.CONF.SUBJECT.ERRATA");
//						String body = i18Service.getString("MAIL.CANDIDAT_AVIS.CONF.HTMLTEXT_BODY1.ERRATA");
						// send mail
						Utilitaires.sendEmailIndividu(
								htmlSubject,
								htmlDebut + htmlBody,
								endBody,
								i, smtpService, i18Service);
//						Utilitaires.sendEmailIndividu(
//								subj,
//								body,
//								endBody,
//								i, smtpService, i18Service);
						nbMailsSend ++;
					}
				}
					
			}
			
			
		
			LOG.info(nbMailsSend + " mails vient d'etre envoyés.");
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
				"syntax: " + SendMailConfirm.class.getSimpleName() + " <options>"
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
			send();
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
