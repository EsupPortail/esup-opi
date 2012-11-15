/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.web.controllers;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.utils.NavigationRulesConst;
import org.esupportail.opi.web.utils.Utilitaires;


/**
 * A visual bean for the welcome page.
 */
public class WelcomeController extends AbstractContextAwareController {


	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -239570715531002003L;

	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(this.getClass());
	
	/**
	 * see {@link SmtpService}.
	 */
	private SmtpService smtpService;
	
	/**
	 * The individu's mail.
	 */
	private String mail;
	
	/**
	 * The URL of the calendar candidature.
	 */
	private String lienCalendrierDeCandidature;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Bean constructor.
	 */
	public WelcomeController() {
		super();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return getClass().getSimpleName() + "#" + hashCode();
	}

	/**
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
		Assert.notNull(this.smtpService, 
				"property smtpService of class " + this.getClass().getName() + " can not be null");
		reset();
	}

	/*
	 ******************* CALLBACK ********************** */
	

	/**
	 * @return a String.
	 */
	public String goForgetNumDos() {
		return NavigationRulesConst.FORGET_NUM_DOS;
	}

	
	
	/**
	 * JSF callback go to the welcome manager.
	 * @return a String.
	 */
	public String goWelcomeManager() {
		return NavigationRulesConst.WELCOME_MANAGER;
	}

	/**
	 * Welcome for the candidate out university.
	 * @return String
	 */
	public String goWelcome() {
		return NavigationRulesConst.LOGIN_CANDIDAT;
	}
	
	/**
	 * 
	 * @return String
	 */
	public String goSeeFormation() {
		return NavigationRulesConst.SEE_FORMATION;
	}
	
	/*
	 ******************* METHODS ********************** */

	/**
	 * if the mail exist send mail with the dossier number.
	 */
	public void sendRappelNumDos() {
		Individu ind = getDomainService().getIndividuByMail(mail);
		if (ind == null) {
			addErrorMessage(null, "ERROR.FIELD.EMAIL.NOT_EXIST");
		} else if (!ind.getTemoinEnService()) {
			addErrorMessage(null, "ERROR.INDIVIDU_NOT_ACTIVE");
		} else {
			logger.info("Envoie du mail à " + ind.getNomPatronymique() + " " + ind.getPrenom()
					+ " (numero de dossier : " + ind.getNumDossierOpi() + ")");
			
			RegimeInscription regimeIns = getRegimeIns().get(Utilitaires.getCodeRIIndividu(ind,
					getDomainService()));
			if (regimeIns.getRappelNumDoss() != null) {
				List<Object> list = new ArrayList<Object>();
				list.add(ind);
				regimeIns.getRappelNumDoss().send(ind.getAdressMail(), 
						ind.getEmailAnnuaire(), list);
			}
			addInfoMessage(null, "INFO.CANDIDAT.SEND_MAIL.NUM_DOS");
//			comment added 30/11/09
//			String htmlBody  = getString("MAIL.NOT_RESPONSE");
//			htmlBody += Utilitaires.getCivilite(getI18nService(), ind.getSexe());
//			htmlBody += getString("MAIL.FORGET_NUM_DOS.HTMLTEXT_BODY", ind.getNumDossierOpi());
//			Utilitaires.sendEmailIndividu(
//					getString("MAIL.FORGET_NUM_DOS.SUBJECT"),
//					htmlBody, null, ind, smtpService, null);
		}
	}
	
	
	/*
	 ******************* ACCESSORS ********************** */
	
	/**
	 * @param smtpService the smtpService to set
	 */
	public void setSmtpService(final SmtpService smtpService) {
		this.smtpService = smtpService;
	}

	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(final String mail) {
		this.mail = mail;
	}
	
	/**
	 * 
	 * @return The URL of the calendar candidature.
	 */
	public String getLienCalendrierDeCandidature() {
		return lienCalendrierDeCandidature;
	}
	
	/**
	 * 
	 * @param lienCalendrierDeCandidature The URL of the calendar candidature.
	 */
	public void setLienCalendrierDeCandidature(final String lienCalendrierDeCandidature) {
		this.lienCalendrierDeCandidature = lienCalendrierDeCandidature;
	}

	
}
