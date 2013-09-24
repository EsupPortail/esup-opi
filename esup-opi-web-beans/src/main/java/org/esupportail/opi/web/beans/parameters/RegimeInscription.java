/**
 * 
 */
package org.esupportail.opi.web.beans.parameters;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.services.mails.MailContentService;
import org.esupportail.opi.web.beans.ControlField;
import org.esupportail.opi.web.beans.ManagedCalendar;
import org.springframework.beans.factory.InitializingBean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author cleprous
 *
 */
public abstract class RegimeInscription  implements Serializable, InitializingBean {

	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1041991591224846110L;
	
	/*
	 ******************* PROPERTIES ******************* */

	
	/**
	 * The code.
	 */
	private int code;
	
	/**
	 * The label.
	 */
	private String label;
	
	/**
	 * The short label.
	 */
	private String shortLabel;
	
	/**
	 * The control field.
	 */
	private ControlField controlField;	
	
	/**
	 * see {@link I18nService}.
	 */
	private I18nService i18nService;
	
	/**
	 * see {@link managedCalendar}.
	 */
	private ManagedCalendar managedCalendar;
	
	/**
	 * The mail to create the dossier.
	 */
	private MailContentService createDossier;
	
	/**
	 * The mail to confirm the inscription.
	 */
	private MailContentService confirmInscription;
	
	/**
	 * Map of mails for each type of convocation.
	 */
	private Map<TypeConvocation, Set<MailConvocation>> mailTypeConvoc;
	
	/**
	 * The mail to remember the number of inscription.
	 */
	private MailContentService rappelNumDoss;
	
	/**
	 * The mail sent when a complete file arrived.
	 */
	private MailContentService dossArriveComplet;
	
	/**
	 * The mail sent when a incomplete file arrived.
	 */
	private MailContentService dossArriveIncomplet;
	
	/**
	 * Mail for add cursus scol.
	 */
	private MailContentService mailAddCursusScol;

	/**
	 * Mail for add wishes AS.
	 */
	private MailContentService mailAddWishesAS;
	
	/**
	 * Mail for add wishes TR ou VA.
	 */
	private MailContentService mailAddWishesTRVA;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public RegimeInscription() {
		super();
	}

	
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "RegimeInscription#" + hashCode() + "[code = " + code + "], [label = " + label + "]]";

	}



	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + code;
		result = prime * result + ((label == null) ? 0 : label.hashCode());
		result = prime * result + ((shortLabel == null) ? 0 : shortLabel.hashCode());
		return result;
	}



	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		RegimeInscription other = (RegimeInscription) obj;
		if (code != other.code) {
			return false;
		} 
		if (label == null) {
			if (other.label != null) { return false; }
		} else if (!label.equals(other.label)) { return false; }
		if (shortLabel == null) {
			if (other.shortLabel != null) { return false; }
		} else if (!shortLabel.equals(other.shortLabel)) { return false; }
		return true;
	}

	

	

	
	/*
	 ******************* METHODS ********************** */

	/**
	 * Selon le rÃ©gime, le gestionnaire peut ou non modifier.
	 * le filtre sur le rÃ©gime d'inscription
	 * @return boolean
	 */
	public abstract boolean canModifyRISearch();
	
	/**
	 * Selon le rÃ©gime, l'individu peut ou non Ã©diter.
	 * les informations sur son bac
	 * @return boolean
	 */
	public abstract boolean getDisplayInfoBac();
	
	/**
	 * Selon le rÃ©gime, l'individu peut ou non ajouter.
	 * des voeux sans notion de calendrier
	 * @return boolean
	 */
	public abstract boolean getCanAlwaysAddVows();
	
	/**
	 * Selon le rÃ©gime, l'individu peut ou non Ã©diter.
	 * les informations sur sa situation
	 * @return boolean
	 */
	public abstract boolean getDisplayInfoSituation();

	/**
	 * Selon le rÃ©gime, on affiche certaines choses pour les FC.
	 * @return boolean
	 */
	public abstract boolean getDisplayInfoFC();

	/**
	 * @param individu
	 */
	public void sendCreateDos(final Individu individu) {
		List<String> addresses = new ArrayList<>();
		addresses.add(individu.getAdressMail());
		List<Object> l = new ArrayList<>();
		l.add(individu);
		createDossier.send(addresses, l);
	}
	
	/**
	 * @param type
	 * @param isAppel (can be null)
	 * @return the MailContentService corresponding to the type
	 * and the isAppel flag
	 */
	public MailContentService getMailContentServiceTypeConvoc(
			final TypeConvocation type, final Boolean isAppel) {
		Set<MailConvocation> listMailConvoc = mailTypeConvoc.get(type);
		for (MailConvocation mailConvoc : listMailConvoc) {
			if (isAppel == null && mailConvoc.getIsAppel() == null) {
				return mailConvoc.getMailContentService();
			} else if (isAppel != null && mailConvoc.getIsAppel() != null
					&& mailConvoc.getIsAppel().equals(isAppel)) {
				return mailConvoc.getMailContentService();
			}
		}
		return null;
	}
	
	/**
	 * @return Boolean
	 */
	public abstract Boolean getCalInsIsOpen();
	
	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}



	/**
	 * @param code the code to set
	 */
	protected void setCode(final int code) {
		this.code = code;
	}



	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}



	/**
	 * @param label the label to set
	 */
	public void setLabel(final String label) {
		this.label = label;
	}



	/**
	 * @return the shortLabel
	 */
	public String getShortLabel() {
		return shortLabel;
	}



	/**
	 * @param shortLabel the shortLabel to set
	 */
	public void setShortLabel(final String shortLabel) {
		this.shortLabel = shortLabel;
	}



	/**
	 * @return the controlField
	 */
	public ControlField getControlField() {
		return controlField;
	}



	/**
	 * @param controlField the controlField to set
	 */
	public void setControlField(final ControlField controlField) {
		this.controlField = controlField;
	}



	/**
	 * @return the iService
	 */
	public I18nService getI18nService() {
		return i18nService;
	}



	/**
	 * @param service the iService to set
	 */
	public void setI18nService(final I18nService service) {
		i18nService = service;
	}



	/**
	 * @return the managedCalendar
	 */
	public ManagedCalendar getManagedCalendar() {
		return managedCalendar;
	}



	/**
	 * @param managedCalendar the managedCalendar to set
	 */
	public void setManagedCalendar(final ManagedCalendar managedCalendar) {
		this.managedCalendar = managedCalendar;
	}



	/**
	 * @return the createDossier
	 */
	public MailContentService getCreateDossier() {
		return createDossier;
	}



	/**
	 * @param createDossier the createDossier to set
	 */
	public void setCreateDossier(final MailContentService createDossier) {
		this.createDossier = createDossier;
	}



	/**
	 * @return the confirmInscription
	 */
	public MailContentService getConfirmInscription() {
		return confirmInscription;
	}



	/**
	 * @param confirmInscription the confirmInscription to set
	 */
	public void setConfirmInscription(final MailContentService confirmInscription) {
		this.confirmInscription = confirmInscription;
	}



	/**
	 * @return the mailTypeConvoc
	 */
	public Map<TypeConvocation, Set<MailConvocation>> getMailTypeConvoc() {
		return mailTypeConvoc;
	}



	/**
	 * @param mailTypeConvoc the mailTypeConvoc to set
	 */
	public void setMailTypeConvoc(
			final Map<TypeConvocation, Set<MailConvocation>> mailTypeConvoc) {
		this.mailTypeConvoc = mailTypeConvoc;
	}



	/**
	 * @return the rappelNumDoss
	 */
	public MailContentService getRappelNumDoss() {
		return rappelNumDoss;
	}



	/**
	 * @param rappelNumDoss the rappelNumDoss to set
	 */
	public void setRappelNumDoss(final MailContentService rappelNumDoss) {
		this.rappelNumDoss = rappelNumDoss;
	}



	/**
	 * @return the dossArriveComplet
	 */
	public MailContentService getDossArriveComplet() {
		return dossArriveComplet;
	}



	/**
	 * @param dossArriveComplet the dossArriveComplet to set
	 */
	public void setDossArriveComplet(final MailContentService dossArriveComplet) {
		this.dossArriveComplet = dossArriveComplet;
	}



	/**
	 * @return the dossArriveIncomplet
	 */
	public MailContentService getDossArriveIncomplet() {
		return dossArriveIncomplet;
	}



	/**
	 * @param dossArriveIncomplet the dossArriveIncomplet to set
	 */
	public void setDossArriveIncomplet(final MailContentService dossArriveIncomplet) {
		this.dossArriveIncomplet = dossArriveIncomplet;
	}



	/**
	 * @return the mailAddCursusScol
	 */
	public MailContentService getMailAddCursusScol() {
		return mailAddCursusScol;
	}



	/**
	 * @param mailAddCursusScol the mailAddCursusScol to set
	 */
	public void setMailAddCursusScol(final MailContentService mailAddCursusScol) {
		this.mailAddCursusScol = mailAddCursusScol;
	}



	/**
	 * @return the mailAddWishes
	 */
	public MailContentService getMailAddWishesAS() {
		return mailAddWishesAS;
	}



	/**
	 * @param mailAddWishes the mailAddWishes to set
	 */
	public void setMailAddWishesAS(final MailContentService mailAddWishesAS) {
		this.mailAddWishesAS = mailAddWishesAS;
	}



	/**
	 * @return the mailAddWishesTRVA
	 */
	public MailContentService getMailAddWishesTRVA() {
		return mailAddWishesTRVA;
	}



	/**
	 * @param mailAddWishesTRVA the mailAddWishesTRVA to set
	 */
	public void setMailAddWishesTRVA(final MailContentService mailAddWishesTRVA) {
		this.mailAddWishesTRVA = mailAddWishesTRVA;
	}
	



	
}
