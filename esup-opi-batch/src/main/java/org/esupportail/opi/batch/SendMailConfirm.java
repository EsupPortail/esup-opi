package org.esupportail.opi.batch;

import fj.Effect;
import fj.F;
import fj.Unit;
import fj.control.parallel.Actor;
import fj.control.parallel.ParModule;
import fj.control.parallel.Strategy;
import fj.data.Option;
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
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.web.beans.pojo.AdressePojo;
import org.esupportail.opi.web.beans.pojo.CommissionPojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

import static fj.data.List.iterableList;
import static fj.data.Option.some;
import static fj.data.Option.somes;
import static org.esupportail.opi.domain.beans.etat.EtatVoeu.EtatConfirme;


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
	 * send the mails.
	 */
	private static void send() {
		final DomainService domainService = (DomainService) ApplicationContextHolder.getContext().getBean("domainService");
		final ParameterService parameterService = (ParameterService) ApplicationContextHolder.getContext().getBean("parameterService");
		final SmtpService smtpService = (SmtpService) ApplicationContextHolder.getContext().getBean("smtpService");
		final I18nService i18Service = (I18nService) ApplicationContextHolder.getContext().getBean("i18nService");
		final DomainApoService domainApoService = (DomainApoService) ApplicationContextHolder.getContext().getBean("domainApoService");

        final Strategy<Unit> strategy =
                Strategy.executorStrategy(Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()));
        final ParModule parModule = ParModule.parModule(strategy);

		try {
			DatabaseUtils.open();
			DatabaseUtils.begin();

            for (final Commission cmi : parameterService.getCommissions(true)) {
				final List<VersionEtpOpi> vets = new ArrayList<>();
				for (TraitementCmi trt : cmi.getTraitementCmi()) {
					vets.add(trt.getVersionEtpOpi());
				}

                final Effect<Iterable<Option<Individu>>> sendMail = new Effect<Iterable<Option<Individu>>>() {
                    public void e(Iterable<Option<Individu>> inds) {
                        for (Individu ind : somes(iterableList(inds))) {
                            Integer codeRi = Utilitaires.getCodeRIIndividu(ind, domainService);
                            //send the mail
                            Set<IndVoeu> voeuxSendMail = new HashSet<>();
                            for (IndVoeu indVoeu : ind.getVoeux()) {
                                TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
                                if (indVoeu.getState().equals(EtatConfirme.getCodeLabel())
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
                                        Utilitaires.getCivilite(i18Service, ind.getSexe()));
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

                                // send mail
                                Utilitaires.sendEmailIndividu(
                                        htmlSubject,
                                        htmlDebut + htmlBody,
                                        endBody,
                                        ind, smtpService, i18Service);

                                LOG.info(String.format("Un mail vient d'etre envoyé à %s", ind.getAdressMail()));
                            }
                        }
                    }
                };

                final Actor<Iterable<Option<Individu>>> mailSender = parModule.effect(sendMail);

                final F<String, Option<Individu>> fetchInd = new F<String, Option<Individu>>() {
                    public Option<Individu> f(String id) {
                        return domainService.fetchIndById(id, Option.<Boolean>none());
                    }
                };

                parModule.parMap(domainService.getIndsIds(cmi, some(true), null), fetchInd).to(mailSender);
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
