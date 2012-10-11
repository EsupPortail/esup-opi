/**
 * 
 */
package org.esupportail.opi.web.controllers.user;

import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.event.ValueChangeEvent;

import org.esupportail.commons.exceptions.ObjectNotFoundException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.AdresseEmployeur;
import org.esupportail.opi.domain.beans.user.AdresseFix;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.pojo.AdressePojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorPays;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.Pays;
import org.springframework.util.StringUtils;



/**
 * @author cleprous
 *
 */
public class AdressController extends AbstractContextAwareController {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -1556518431803897644L;
	
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * List of selected CommuneDTO.
	 */
	private List<CommuneDTO> selectedCommunes;
		
	/**
	 * List of CommuneDTO for the fix adress.
	 */
	private List<CommuneDTO> communesFix;
		
	/**
	 * List of CommuneDTO for the current adress.
	 */
	private List<CommuneDTO> communesCurrent;
	
	/**
	 * List of CommuneDTO for the fix adress.
	 */
	private List<CommuneDTO> communesEmpl;
	
	/**
	 * The fix address.
	 */
	private AdressePojo fixAdrPojo;
	
	/**
	 * The fix address.
	 */
	private AdressePojo emplAdrPojo;
	
	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;	
	
	/*
	 ******************* INIT ************************* */

	

	/**
	 * Constructors.
	 */
	public AdressController() {
		super();
	}
	
	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		selectedCommunes = new ArrayList<CommuneDTO>();
		communesFix = new ArrayList<CommuneDTO>();
		communesCurrent = new ArrayList<CommuneDTO>();
		communesEmpl = new ArrayList<CommuneDTO>();
		fixAdrPojo = new AdressePojo(new AdresseFix());
		emplAdrPojo = new AdressePojo(new AdresseEmployeur());
		actionEnum = new ActionEnum();
	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
	}
	
	/*
	 ******************* CALLBACK ********************** */
	
	/**
	 * Prepare the stuff for the update.
	 */
	public void initUpdate() {
	
		//not use at the moment (11/09/2008) cf ticket : 36313
//		communesCurrent = (List<CommuneDTO>) getDomainApoService()
//				.getCommunesDTO(currentAdrPojo.getAdresse().getCodBdi());
		if (fixAdrPojo.getAdresse().getCodBdi() != null) {
			communesFix = getDomainApoService()
				.getCommunesDTO(fixAdrPojo.getAdresse().getCodBdi());
		}
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/*
	 ******************* METHODS ********************** */
	
	/**
	 * The select the cities according to the postal code selected.
	 */
	public void selectCpFix() {
		String cp = fixAdrPojo.getAdresse().getCodBdi();
		if (StringUtils.hasText(cp) && Utilitaires.isCodePostalValid(cp)) {
			try {
				communesFix = getDomainApoService().getCommunesDTO(cp);
			} catch (ObjectNotFoundException e) {
				//Le code postal n'existe pas dans Apogée.
				addErrorMessage(null, Constantes.I18N_NOT_EXSIT, getString("ADRESS.COD_POST"));
			}
		} else if (fixAdrPojo.getAdresse().getCodPays().equals(Constantes.CODEFRANCE)) {
			addErrorMessage(null, "ERROR.FIELD.INVALID", getString("ADRESS.COD_POST"));
			communesFix = new ArrayList<CommuneDTO>();
		}
	}
	
	/**
	 * The select the cities according to the postal code selected.
	 */
	public void selectCpEmpl() {
		String cp = emplAdrPojo.getAdresse().getCodBdi();
		if (StringUtils.hasText(cp) && Utilitaires.isCodePostalValid(cp)) {
			try {
				communesEmpl = getDomainApoService().getCommunesDTO(cp);
			} catch (ObjectNotFoundException e) {
				//Le code postal n'existe pas dans Apogée.
				addErrorMessage(null, Constantes.I18N_NOT_EXSIT, getString("ADRESS.COD_POST"));
			}
		} else if (emplAdrPojo.getAdresse().getCodPays().equals(Constantes.CODEFRANCE)) {
			addErrorMessage(null, "ERROR.FIELD.INVALID", getString("ADRESS.COD_POST"));
			communesEmpl = new ArrayList<CommuneDTO>();
		}
	}
	
	
	/**
	 * The selected pays for address Fix.
	 * @param event
	 */
	public void selectPayAdr(final ValueChangeEvent event) {
		String codePay = (String) event.getNewValue();
		fixAdrPojo.getAdresse().setCodPays(codePay);
	}
	
	/**
	 * The selected pays for address Fix.
	 * @param event
	 */
	public void selectPayAdrEmpl(final ValueChangeEvent event) {
		String codePay = (String) event.getNewValue();
		emplAdrPojo.getAdresse().setCodPays(codePay);
	}

	/**
	 * Save the adress for this individu. 
	 * @param individu may be null
	 */
	public void addAdrFix(final Individu individu) {
		if (log.isDebugEnabled()) {
			log.debug("entering add with individu = " 
					+ individu );
		}
		
		// Add Fix Adress
		
		IndividuPojo i = new IndividuPojo();
		i.setIndividu(individu);
		
		fixAdrPojo.setAdresse(
				(Adresse) getDomainService().add(
						fixAdrPojo.getAdresse(),
						Utilitaires.codUserThatIsAction(getCurrentGest(), i)));
		
		AdresseFix adresseFix = (AdresseFix) fixAdrPojo.getAdresse();
		
		adresseFix.setIndividu(individu);
		
		add(adresseFix);

	}
	
	/**
	 * Save the adress for the commission. 
	 */
	public void addAdrComm() {
		if (log.isDebugEnabled()) {
			log.debug("entering addAdrCom()" );
		}
		
		// Add Commission Adress
		
		fixAdrPojo.setAdresse(
				(Adresse) getDomainService().add(
						fixAdrPojo.getAdresse(),
						Utilitaires.codUserThatIsAction(getCurrentGest(), null)));
		
		add(fixAdrPojo.getAdresse());
	}
	/**
	 * Save the adress for the commission. 
	 */
	public void addAdrEmployeur() {
		if (log.isDebugEnabled()) {
			log.debug("entering addAdrEmployeur()" );
		}
		
		// Add Employeur Adress
		
		emplAdrPojo.setAdresse(
				(Adresse) getDomainService().add(
						emplAdrPojo.getAdresse(),
						Utilitaires.codUserThatIsAction(getCurrentGest(), null)));
		
		add(emplAdrPojo.getAdresse());
	}
	
	
	/**
	 * Save the adresse.
	 * @param adresse
	 */
	public void add(final Adresse adresse) {
		if (!fixAdrPojo.getIsCedex()) {
			adresse.setCedex(null);
		} else if (!StringUtils.hasText(fixAdrPojo.getAdresse().getCedex())) {
			//si la valeur est vide il faut mettre un esapce sinon oracle enrgistre NULL
			adresse.setCedex("  ");
			
		}
		if (!adresse.getCodPays().equals(Constantes.CODEFRANCE)) {
			adresse.setCodBdi(null);
			adresse.setCodCommune(null);
		} else { adresse.setLibComEtr(null); }
			getDomainService().addAdresse(adresse);
		
		if (actionEnum.getWhatAction().equals(ActionEnum.UPDATE_ACTION)) {
			reset();
			actionEnum.setWhatAction(ActionEnum.READ_ACTION);
			//for init the pojo attributes
			init(adresse, false);
		}
		
	}
	
	/**
	 * Update the adress. 
	 * 
	 */
	public void update(final AdressePojo adressePojo) {
		if (log.isDebugEnabled()) {
			log.debug("entering update ");
		}

		// Update current Adress
		//not use at the moment (11/09/2008) cf ticket : 36313
		//getDomainService().updateAdresse(currentAdrPojo.getAdresse());

		// COMMENT : 22/03/10 : ctrlEnter appeler dans chaque méthode appelant l'update
		// si besoin (cas de l'adresseEmployeur)
		//if (ctrlEnter(fixAdrPojo.getAdresse(), true)) {
			// Update Fix Adress
			String codUserToUpdate = Utilitaires.
				codUserThatIsAction(getCurrentGest(), getCurrentInd());
			
			adressePojo.setAdresse(
					(Adresse) getDomainService().update(
							adressePojo.getAdresse(), codUserToUpdate));
			if (!adressePojo.getIsCedex()) {
				adressePojo.getAdresse().setCedex(null);
			} else if (!StringUtils.hasText(adressePojo.getAdresse().getCedex())) {
				//si la valeur est vide il faut mettre un esapce sinon oracle enrgistre NULL
				adressePojo.getAdresse().setCedex("  ");
				
			}
 			if (!adressePojo.getAdresse().getCodPays().equals(Constantes.CODEFRANCE)) {
 				adressePojo.getAdresse().setCodBdi(null);
 				adressePojo.getAdresse().setCodCommune(null);
			} else { adressePojo.getAdresse().setLibComEtr(null); }
 			
 			AdresseFix adresseFix = (AdresseFix) fixAdrPojo.getAdresse(); 			
 			Individu individu = adresseFix.getIndividu();
 			individu.getAdresses().put(Constantes.ADR_FIX, adressePojo.getAdresse());
 			
			getDomainService().updateAdresse(adressePojo.getAdresse());
			reset();
			actionEnum.setWhatAction(ActionEnum.READ_ACTION);
			//for init the pojo attributes
			init(adressePojo.getAdresse(), false);
		//}
	}
	
	/**
	 * Init adress.
	 * @param adresse
	 * @param initTownList if true init the list town for the address.codBdi
	 */
	public void init(final Adresse adresse, final Boolean initTownList) {
		if (adresse instanceof AdresseEmployeur) {
			// Fix Adresse
			emplAdrPojo = new AdressePojo(adresse, getDomainApoService());
			if (initTownList) { selectCpEmpl(); }
		} else {
			// Fix Adresse
			fixAdrPojo = new AdressePojo(adresse, getDomainApoService());
			if (initTownList) { selectCpFix(); }
		}
	}

	/* ### ALL CONTROL ####*/
	
	/**
	 * Control the Adress attributes.
	 * @param a : the adress to control 
	 * @param displayMessage
	 * @return Boolean
	 */
	public Boolean ctrlEnter(final Adresse a, final Boolean displayMessage) {
		Boolean ctrlOk = true;
		
		// Check adress fields
		if (!StringUtils.hasText(a.getCodPays())) {
			if (displayMessage) {
				addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.PAY"));
			}
			ctrlOk = false;
		}
		if (!StringUtils.hasText(a.getAdr1())) {
			if (displayMessage) {
				addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS"));
			}
			ctrlOk = false;
		}
		if (!StringUtils.hasText(a.getPhoneNumber())) {
			if (displayMessage) {
				addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.TEL_FIX"));
			}
			ctrlOk = false;
		}
		if (ctrlOk && a.getCodPays().equals(Constantes.CODEFRANCE)) {
			if (!StringUtils.hasText(a.getCodBdi())) {
				if (displayMessage) {
					addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.COD_POST"));
				}
				ctrlOk = false;
			}
			if (!StringUtils.hasText(a.getCodCommune())) {
				if (displayMessage) {
					addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.VIL"));
				}
				ctrlOk = false;
			}
		} else if (ctrlOk && !a.getCodPays().equals(Constantes.CODEFRANCE)) {
			if (!StringUtils.hasText(a.getLibComEtr())) {
				if (displayMessage) {
					addErrorMessage(null, Constantes.I18N_EMPTY, getString("ADRESS.VIL.ETR"));
				}
				ctrlOk = false;
			}
		}
		
		return ctrlOk;
	}
	
	
	/**
	 * Return all nationalities.
	 * @return List< PaysDTO>
	 */
	public List<Pays> getNationalite() {
		List<Pays> l = getDomainApoService().getPays();
		Collections.sort(l, new ComparatorPays(ComparatorPays.NATIONALITE));
		return l;
	}
	
	/**
	 * Return all countries.
	 * @return List< Pays>
	 */
	public List<Pays> getPays() {
		List<Pays> l = getDomainApoService().getPays();
		Collections.sort(l, new ComparatorPays(ComparatorPays.PAYS));
		return l;
	}
	
	/**
	 * Return all departements.
	 * @return List< DepartementDTO>
	 */
	public List<Departement> getDepartements() {
		return getDomainApoService().getDepartements();
	}
			

	/**
	 * Return the France country code. 
	 * @return String
	 */
	public String getCodeFrance() {
		return Constantes.CODEFRANCE;
	}
	
	/**
	 * @return the selectedCommunes
	 */
	public List<CommuneDTO> getSelectedCommunes() {
		return selectedCommunes;
	}

	/**
	 * @param selectedCommunes the selectedCommunes to set
	 */
	public void setSelectedCommunes(final List<CommuneDTO> selectedCommunes) {
		this.selectedCommunes = selectedCommunes;
	}

	/**
	 * @return the communesFix
	 */
	public List<CommuneDTO> getCommunesFix() {
		return communesFix;
	}

	/**
	 * @param communesFix the communesFix to set
	 */
	public void setCommunesFix(final List<CommuneDTO> communesFix) {
		this.communesFix = communesFix;
	}

	/**
	 * @return the communesCurrent
	 */
	public List<CommuneDTO> getCommunesCurrent() {
		return communesCurrent;
	}

	/**
	 * @param communesCurrent the communesCurrent to set
	 */
	public void setCommunesCurrent(final List<CommuneDTO> communesCurrent) {
		this.communesCurrent = communesCurrent;
	}

	/**
	 * @return the fixAdrPojo
	 */
	public AdressePojo getFixAdrPojo() {
		return fixAdrPojo;
	}

	/**
	 * @param fixAdrPojo the fixAdrPojo to set
	 */
	public void setFixAdrPojo(final AdressePojo fixAdrPojo) {
		this.fixAdrPojo = fixAdrPojo;
	}

	/**
	 * @return the actionEnum
	 */
	public ActionEnum getActionEnum() {
		return actionEnum;
	}

	/**
	 * @param actionEnum the actionEnum to set
	 */
	public void setActionEnum(final ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}

	/**
	 * @return the communesEmpl
	 */
	public List<CommuneDTO> getCommunesEmpl() {
		return communesEmpl;
	}

	/**
	 * @param communesEmpl the communesEmpl to set
	 */
	public void setCommunesEmpl(final List<CommuneDTO> communesEmpl) {
		this.communesEmpl = communesEmpl;
	}

	/**
	 * @return the emplAdrPojo
	 */
	public AdressePojo getEmplAdrPojo() {
		return emplAdrPojo;
	}

	/**
	 * @param emplAdrPojo the emplAdrPojo to set
	 */
	public void setEmplAdrPojo(final AdressePojo emplAdrPojo) {
		this.emplAdrPojo = emplAdrPojo;
	}

}
