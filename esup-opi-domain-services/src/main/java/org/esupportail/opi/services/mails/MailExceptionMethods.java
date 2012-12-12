/**
* ESUP-Portail - candidatures - 2009
* http://subversion.cru.fr/57si-OPI
*/
/**
 * 
 */
package org.esupportail.opi.services.mails;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.BusinessCacheService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.references.calendar.CalendarCmi;
import org.esupportail.opi.domain.beans.references.calendar.ReunionCmi;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.Selection;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;
import org.esupportail.opi.domain.beans.user.Adresse;

import static fj.data.Option.*;

/**
 * @author cleprous
 * 
 */
public class MailExceptionMethods implements Serializable, InitializingBean {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3247424040716310409L;

	/*
	 *************************** PROPERTIES ******************************** */
	/**
	 * see {@link I18nService}.
	 */
	private I18nService iService;
	
	/**
	 * see {@link DomainApoService}.
	 */
	private DomainApoService domainApoService;
	
	/**
	 * see {@link DomainService}.
	 */
	private DomainService domainService;
	
	/**
	 * see {@link BusinessCacheService}.
	 */
	private BusinessCacheService businessCacheService;
	
	/**
	 * the key is an expression in body. The value is class and the method to execute to have the good result.
	 */
	private Map<String, String> exceptions;
	
	/*
	 *************************** INIT ************************************** */
	

	/**
	 * Constructor.
	 */
	public MailExceptionMethods() {
		super();
	}
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.iService, "property i18nService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.domainApoService, "property domainApoService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.domainService, "property domainService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notEmpty(this.exceptions, "property exceptions of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.businessCacheService, "property businessCacheService of class " 
				+ this.getClass().getName() + " can not be null");
	}

	/*
	 *************************** METHODS *********************************** */

	
	/**
	 * The civility for the individu's sex.
	 * @param sexe
	 * @return String
	 */
	public String getCivilite(final String sexe) {
		String civilite = "";
		if (!StringUtils.hasText(sexe)) {
			civilite = iService.getString(Constantes.I18N_CIV_MM) 
			+ ", " + iService.getString(Constantes.I18N_CIV_MR);
		} else if (sexe.equals(Constantes.COD_SEXE_FEMININ)) {
			civilite = iService.getString(Constantes.I18N_CIV_MM);
		} else if (sexe.equals(Constantes.COD_SEXE_MASCULIN)) {
			civilite = iService.getString(Constantes.I18N_CIV_MR);
		}

		return civilite;
	}
	
	
	/**
	 * Return address of cmi in format text html for send mail.
	 * @param adrPojo
	 * @return String
	 */
	public String getFullAdrCmi(final Adresse adr, final CommuneDTO commune) {
		String ville = "";
		if (adr != null 
				&& commune != null) {
			ville = adr.getCodBdi() 
			+ " " 
			+ commune.getLibCommune();
			if (adr.getCedex() != null) {
				ville += "CEDEX " + adr.getCedex();
			}
		}
		String htmlBody2 = "";
		if (adr != null) {
			// pour ticket 41115 decomposition pour eviter les null en adr2 et adr3
			// pour um1, dans 4 tests pour éviter les lignes blanches 
			if (StringUtils.hasText(adr.getAdr1())) {
				htmlBody2 += iService.getString("MAIL.ADR_CMI.ADR_POST", 
						adr.getAdr1());
			} 
			if (StringUtils.hasText(adr.getAdr2())) {
				htmlBody2 += iService.getString("MAIL.ADR_CMI.ADR_POST", 
						adr.getAdr2());
			}
			if (StringUtils.hasText(adr.getAdr3())) {
				htmlBody2 += iService.getString("MAIL.ADR_CMI.ADR_POST", 
						adr.getAdr3());
			}
			if (StringUtils.hasText(ville)) {
				htmlBody2 += iService.getString("MAIL.ADR_CMI.ADR_POST", 
						ville);
			} 
			
			
		}

		if (adr != null) {
			String mail = "";
			String phone = "";
			String fax = "";
			if (adr.getMail() != null) {
				mail = adr.getMail();
			}
			if (adr.getPhoneNumber() != null) {
				phone = adr.getPhoneNumber();
			}
			if (adr.getFaxNumber() != null) {
				fax = adr.getFaxNumber();
			}

			htmlBody2 += iService.getString("MAIL.ADR_CMI.MAIL_TEL", 
					mail, phone, fax, ville);
		}
		return htmlBody2;
	}
	
	/**
	 * The list of reunion in calendarCmi.
	 * @param sexe
	 * @return String
	 */
	public String getReunions(final CalendarCmi calendar) {
		StringBuffer htmlBody = new StringBuffer();
		if (calendar != null && calendar.getReunions() != null) {
			// on trie par ordre chronologique les dates de réunion
			Set<ReunionCmi> reunions = new TreeSet<ReunionCmi>(
			    new Comparator<ReunionCmi>() {
                    public int compare(ReunionCmi r1, ReunionCmi r2) {
                        return (r1.getDate().toString().concat(r1.getHeure().toString())).toUpperCase().compareTo(
                            r2.getDate().toString().concat(r2.getHeure().toString()).toUpperCase());}});
			reunions.addAll(calendar.getReunions());
			for (ReunionCmi reunion : reunions) {
				// on ajoute la date de la réunion que si elle est supérieure à la date du jour
				if (reunion.getDate().after(new Date())) {
					htmlBody.append(iService.getString("MAIL.COMMISSION.CONVOC.DATE",
					    new SimpleDateFormat(Constantes.DATE_FORMAT).format(reunion.getDate()),
					    new SimpleDateFormat(Constantes.DATE_FORMAT).format(reunion.getHeure()),
						reunion.getLieu()));
				}
			}
		}
		return htmlBody.toString();
	}
	
	
	/**
	 * The libelle of the vet.
	 * @param indVoeu
	 * @return
	 */
	public String getLibelleVet(final IndVoeu indVoeu) {
		TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
		VersionEtapeDTO vDTO = domainApoService.getVersionEtape(
				trtCmi.getVersionEtpOpi().getCodEtp(), 
				trtCmi.getVersionEtpOpi().getCodVrsVet());
		return vDTO.getLibWebVet();
	}
	
	/**
	 * The motivation of the avis.
	 * @param m
	 * @return
	 */
	public String getMotivationAvis(final MotivationAvis m) {
		String htmlBody = "";
		if (m != null) {
			htmlBody += " - " + m.getLibelle(); 
		}
		return htmlBody;
	}
	
	/**
	 * The commentaire of the avis.
	 * @param comm
	 * @return
	 */
	public String getCommentaire(final String comm) {
		String htmlBody = "";
		if (StringUtils.hasText(comm)) {
			htmlBody += " (" + comm + ")"; 
		}
		return htmlBody;
	}
	
	/**
	 * @param commission
	 * @return le message rappelant la date de fin de confirmation
	 */
	public String getMsgDateFin(final Commission commission) {
		// trouve la date de cloture de la confirmation
		String dateClotureConfirmation = "";
		if (commission.getCalendarCmi() != null) {
			domainService.initOneProxyHib(commission, 
					commission.getCalendarCmi(), CalendarCmi.class);
			if (commission.getCalendarCmi().getEndDatConfRes() != null) {
				dateClotureConfirmation =
				    new SimpleDateFormat(Constantes.DATE_FORMAT).format(
				        commission.	getCalendarCmi().getEndDatConfRes());
			}
		} 
		String msgDateFin = "";
		if (StringUtils.hasText(dateClotureConfirmation)) {
			msgDateFin = iService.getString("MAIL.CANDIDAT_AVIS.FAV.DATE_FIN",
					dateClotureConfirmation);
		} else {
			msgDateFin = iService.getString("MAIL.CANDIDAT_AVIS.FAV.NOT_DATE_FIN");
		}
		return msgDateFin;

	}
	
	/**
	 * @param selection
	 * @return le lieu de la selection
	 */
	public String getSelectionPlace(final Selection selection) {
		String htmlBody = "";
		if (selection != null) {
			htmlBody += " " + selection.getPlace();
			//ne pas afficher dans le mail comment do the 26/05/2009
//			htmlBody += " " + selection.getPeriodeAdmissibilite();
//			htmlBody += " " + selection.getResultSelection();  
//			htmlBody += " " + selection.getComment();
		}
		return htmlBody;
	}
	
	/**
	 * @param codSig
	 * @return signataire
	 */
	public String getSignataire(final String codSig) {
		SignataireDTO s = null;
		String signature = "";
		if (StringUtils.hasText(codSig)) {
			s = businessCacheService.getSignataire(codSig);
			signature = s.getQuaSig() + " " + s.getNomSig();
		}
		return signature;
	}
	
	/**
	 * @param missingPiece
	 * @return le libelle de la piece manquante
	 */
	public String getMissingPiece(final MissingPiece missingPiece) {
		String htmlBody = "";
		htmlBody += missingPiece.getPiece().getLibelle() + "( <b>" 
			+ fromString(missingPiece.getLibelle()).orSome("") + "</b> )";
		return htmlBody;
	}
	
	/**
	 * @param dateEnd
	 * @return la date de retour finale de dossier
	 */
	public String getDatEndBackDossier(final Date dateEnd) {
		String htmlBody = "";
		htmlBody += new SimpleDateFormat(Constantes.DATE_FORMAT).format(dateEnd); //Utilitaires.convertDateToString(dateEnd, Constantes.DATE_FORMAT);
		return htmlBody;
	}
	
	/*
	 *************************** ACCESSORS ********************************* */
	



	/**
	 * @param service the iService to set
	 */
	public void setIService(final I18nService service) {
		iService = service;
	}
	
	/**
	 * @param domainApoService the domainApoService to set
	 */
	public void setDomainApoService(final DomainApoService domainApoService) {
		this.domainApoService = domainApoService;
	}

	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @return the exceptions
	 */
	public Map<String, String> getExceptions() {
		return exceptions;
	}

	/**
	 * @param exceptions the exceptions to set
	 */
	public void setExceptions(final Map<String, String> exceptions) {
		this.exceptions = exceptions;
	}

	public void setBusinessCacheService(final BusinessCacheService businessCacheService) {
		this.businessCacheService = businessCacheService;
	}

}
