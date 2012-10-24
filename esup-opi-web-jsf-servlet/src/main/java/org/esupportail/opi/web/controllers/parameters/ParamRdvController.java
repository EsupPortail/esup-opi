package org.esupportail.opi.web.controllers.parameters;



import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.BusinessUtil;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.references.rendezvous.Horaire;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire;
import org.esupportail.opi.domain.beans.references.rendezvous.TrancheFermee;
import org.esupportail.opi.domain.beans.references.rendezvous.VetCalendar;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.pojo.CalendarRDVPojo;
import org.esupportail.opi.web.beans.pojo.CommissionPojo;
import org.esupportail.opi.web.beans.pojo.VetCalendarPojo;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;



/**
 * 
 * @author gomez
 *
 */
public class ParamRdvController extends AbstractContextAwareController {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -4290523903742695628L;
	/**
	 * 
	 */
	private static final String FORMULAIRE_ADD_RDV = "formAddParamRdv";
	/**
	 * 
	 */
	private static final String ERROR_FIELD_EMPTY = "ERROR.FIELD.EMPTY";
	/**
	 * 
	 */
	private static final String VALUE = "value";
	/**
	 * CGE.
	 */
	private static final String CGE = "cge";
	/**
	 * Commission.
	 */
	private static final String COMMISSION = "commission";
	/**
	 * Commission.
	 */
	private static final String VET = "vet";
	/**
	 * Tableau des choix.
	 */
	private static final String[] LESCHOIX = {VET, COMMISSION, CGE};
	/**
	 * 
	 */
	private static final int DEFAULT_HEURE_DEBUT_AM = 9;
	/**
	 * 
	 */
	private static final int DEFAULT_MINUTE_DEBUT_AM = 0;
	/**
	 * 
	 */
	private static final int DEFAULT_HEURE_FIN_AM = 12;
	/**
	 * 
	 */
	private static final int DEFAULT_MINUTE_FIN_AM = 0;
	/**
	 * 
	 */
	private static final int DEFAULT_HEURE_DEBUT_PM = 14;
	/**
	 * 
	 */
	private static final int DEFAULT_MINUTE_DEBUT_PM = 0;
	/**
	 * 
	 */
	private static final int DEFAULT_HEURE_FIN_PM = 16;
	/**
	 * 
	 */
	private static final int DEFAULT_MINUTE_FIN_PM = 30;
	/**
	 * 
	 */
	private static final int LENGTH_MSG = 500;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	/**
	 * Liste des calendriers de rendez-vous.
	 */
	private List<CalendarRDVPojo> listCalendarRdv;
	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;
	/**
	 * CalendarRdv selectionne.
	 */
	private CalendarRDV calendarRDV;
	/**
	 * Liste des codes CGE.
	 */
	private List<SelectItem> allCge;
	/**
	 * allCommItems.
	 */
	private List<SelectItem> allCommItems;
	/**
	 * CommItems.
	 */
	private List<SelectItem> commItems;
	/**
	 * allVetItems.
	 */
	private List<SelectItem> allVetItems;
	/**
	 * vetItems.
	 */
	private List<SelectItem> vetItems;
	/**
	 * liste des choix (Vet, Commission ou CGE).
	 */
	private List<SelectItem> allChoix;
	/**
	 * choix entre la liste de Commission ou une CGE.
	 */
	private String choix;
	/**
	 * Code CGE selectionne.
	 */
	private String codeCge;
	/**
	 * selectCommADI.
	 */
	private List<String> selectCommADI;
	/**
	 * selectCommDI.
	 */
	private List<String> selectCommDI;
	/**
	 * selectVetADI.
	 */
	private List<String> selectVetADI;
	/**
	 * selectVetDI.
	 */
	private List<String> selectVetDI;
	
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Controller.
	 */
	public ParamRdvController() {
		listCalendarRdv = new ArrayList<CalendarRDVPojo>();
		allCge = new ArrayList<SelectItem>();
		
		allCommItems = new ArrayList<SelectItem>();
		selectCommADI = new ArrayList<String>();
		commItems = new ArrayList<SelectItem>();
		selectCommDI = new ArrayList<String>();
		
		allVetItems = new ArrayList<SelectItem>();
		selectVetADI = new ArrayList<String>();
		vetItems = new ArrayList<SelectItem>();
		selectVetDI = new ArrayList<String>();
	}
	
	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		listCalendarRdv = new ArrayList<CalendarRDVPojo>();
		allCge.clear();
		actionEnum = null;
		calendarRDV = null;
		codeCge = null;
		
		allCommItems.clear();
		selectCommADI.clear();
		commItems.clear();
		selectCommDI.clear();
		
		allVetItems.clear();
		selectVetADI.clear();
		vetItems.clear();
		selectVetDI.clear();
	}
	
	
	/*
	 ******************* CALLBACK ********************** */
	/**
	 * Callback to calendar list.
	 * @return String 
	 */
	public String goSeeAllParamRdv() {
		reset();
		return NavigationRulesConst.SEE_PARAM_RDV;
	}
	
	/**
	 * Callback to calendar add.
	 * @return String 
	 */
	public String goAddParamRdv() {
		reset();
		getActionEnum().setWhatAction(ActionEnum.ADD_ACTION);
		return null;
	}
	
	
	/*
	 * ******************* ADD ET UPDATE ************************* */
	/**
	 * Add a Domain to the dataBase.
	 */
	public void add() {
		if (testErreurSave()) {
			return;
		}
		
		if (log.isDebugEnabled()) {
			log.debug("enterind add with calendarRDV = " + getCalendarRDV().getTitre());
		}
		
		getCalendarRDV().setCodeCge(null);
		getCalendarRDV().getVets().clear();
		getCalendarRDV().getCommissions().clear();
		
		if (isChoixCge()) {
			getCalendarRDV().setCodeCge(codeCge);
			
		} else if (isChoixCommission()) {
			for (SelectItem comm : commItems) {
				getCalendarRDV().getCommissions().add(recupCommission((String) comm.getValue()));
			}
			
		}
		
		//getListCalendarRdv().add(getCalendarRDV());
		getParameterService().addCalendarRdv(getCalendarRDV());
		
		if (isChoixVet()) {
			for (SelectItem vet : vetItems) {
				VetCalendar vetCal = new VetCalendar((String) vet.getValue(),
							Integer.parseInt(vet.getDescription()),
							getCalendarRDV(),
							getCommissionVet((String) vet.getValue(), Integer.parseInt(vet.getDescription())));
				
				getDomainService().addVetCalendar(vetCal);
				getCalendarRDV().getVets().add(vetCal);
			}
		}
		
		initCalendar();
		
		//Collections.sort(getListCalendarRdv(), new BeanComparator("titre", new NullComparator()));
		reset();
		
		addInfoMessage(null, "INFO.ENTER.SUCCESS");
		if (log.isDebugEnabled()) {
			log.debug("leaving add");
		}
	}
	/**
	 * Update a function to the dataBase.
	 */
	public void update() {
		if (testErreurUpdate()) {
			return;
		}
		
		if (log.isDebugEnabled()) {
			log.debug("enterind update with calendarRDV = " + getCalendarRDV().getTitre());
		}
		
		if (isChoixCge()) {
			getCalendarRDV().setCodeCge(codeCge);
			getCalendarRDV().getCommissions().clear();
			getCalendarRDV().getVets().clear();
			
		} else if (isChoixCommission()) {
			int index;
			List<Commission> listComm = new ArrayList<Commission>();
			
			for (Commission comm : getCalendarRDV().getCommissions()) {
				index = getExistCommItems(comm.getCode());
				if (index == -1) {
					//recuperation des commissions ne se trouvant pas dans la liste des items
					listComm.add(comm);
				} else {
					//suppression des commissions se trouvant dans la liste des items
					commItems.remove(index);
				}
			}
			
			for (Commission comm : listComm) {
				//suppression des commissions
				getCalendarRDV().getCommissions().remove(comm);
			}
			
			for (SelectItem comm : commItems) {
				//ajout des commissions
				getCalendarRDV().getCommissions().add(recupCommission((String) comm.getValue()));
			}

			getCalendarRDV().setCodeCge(null);
			getCalendarRDV().getVets().clear();
			
		} else {
			int index;
			List<VetCalendar> listVets = new ArrayList<VetCalendar>();
			
			for (VetCalendar vet : getCalendarRDV().getVets()) {
				index = getExistVetItems(vet.getCodEtp(), vet.getCodVrsVet());
				if (index == -1) {
					//recuperation des vets ne se trouvant pas dans la liste des items
					listVets.add(vet);
				} else {
					//suppression des vets se trouvant dans la liste des items
					vetItems.remove(index);
				}
			}
			
			for (VetCalendar vet : listVets) {
				//suppression des vets
				getDomainService().deleteVetCalendar(vet);
				getCalendarRDV().getVets().remove(vet);
			}
			
			

			getCalendarRDV().setCodeCge(null);
			getCalendarRDV().getCommissions().clear();
		}
		
		getParameterService().updateCalendarRdv(getCalendarRDV());
		
		if (isChoixVet()) {
			for (SelectItem vet : vetItems) {
				//ajout des vets
				VetCalendar vetCal = new VetCalendar((String) vet.getValue(),
						Integer.parseInt(vet.getDescription()),
						getCalendarRDV(),
						getCommissionVet((String) vet.getValue(), Integer.parseInt(vet.getDescription())));
				
				getDomainService().addVetCalendar(vetCal);
				getCalendarRDV().getVets().add(vetCal);
			}
		}
		
		initCalendar();
		reset();
		
		if (log.isDebugEnabled()) {
			log.debug("leaving update");
		}
	}
	/**
	 * Delete a fonction to the dataBase.
	 */
	public void delete() {
		if (log.isDebugEnabled()) {
			log.debug("enterind delete with calendarRDV = " + getCalendarRDV().getTitre());
		}
		
		getListCalendarRdv().remove(getCalendarRDV());
		getParameterService().deleteCalendarRdv(getCalendarRDV());
		reset();
		
		if (log.isDebugEnabled()) {
			log.debug("leaving delete");
		}
	}
	/**
	 * 
	 */
	private void initCalendar() {
		Calendar cal = new GregorianCalendar();
		
		cal.setTime(getCalendarRDV().getDateDebutInsc());
		int moisDebut = cal.get(Calendar.MONTH);
		cal.setTime(getCalendarRDV().getDateFinInsc());
		int moisFin = cal.get(Calendar.MONTH);
		
		//suppression des horaires
		for (Horaire ho : getCalendarRDV().getHoraires().values()) {
			if (ho.getNumMois() < moisDebut || ho.getNumMois() > moisFin) {
				getCalendarRDV().getHoraires().remove(ho);
				getDomainService().deleteHoraire(ho);
			}
		}
		
		//Ajout des horaires
		for (int i = moisDebut; i <= moisFin; i++) {
			if (getCalendarRDV().getHoraires().get(i) == null) {
				Calendar calMonth = new GregorianCalendar();
				calMonth.set(Calendar.MONTH, i);
				initDate(calMonth);
			}
		}
	}
	/**
	 * @param calendar
	 */
	private void initDate(final Calendar calendar) {
		log.info("initDate");
		Calendar cal = new GregorianCalendar();
		
		Horaire newHoraire = new Horaire();
		newHoraire.setId(Integer.parseInt(String.valueOf(calendar.get(Calendar.MONTH)) 
				+ String.valueOf(getCalendarRDV().getId())));
		newHoraire.setCalendrierRdv(getCalendarRDV());
		newHoraire.setNumMois(calendar.get(Calendar.MONTH));
		cal.set(0, 0, 0, DEFAULT_HEURE_DEBUT_AM, DEFAULT_MINUTE_DEBUT_AM, 0);
		newHoraire.setDateDebutMatin(cal.getTime());
		cal.set(0, 0, 0, DEFAULT_HEURE_FIN_AM, DEFAULT_MINUTE_FIN_AM, 0);
		newHoraire.setDateFinMatin(cal.getTime());
		cal.set(0, 0, 0, DEFAULT_HEURE_DEBUT_PM, DEFAULT_MINUTE_DEBUT_PM, 0);
		newHoraire.setDateDebutAmidi(cal.getTime());
		cal.set(0, 0, 0, DEFAULT_HEURE_FIN_PM, DEFAULT_MINUTE_FIN_PM, 0);
		newHoraire.setDateFinAmidi(cal.getTime());
		
		//sauvegarde de l'horaire
		getDomainService().addHoraire(newHoraire);
		
		//mise e jour de la map horaire
		getCalendarRDV().getHoraires().put(
				calendar.get(Calendar.MONTH), newHoraire);
	}
	
		
	/*
	 * ******************* TEST ************************* */
	/**
	 * @return boolean
	 */
	private boolean testErreurSave() {
		if (getCalendarRDV().getTitre() == null || getCalendarRDV().getTitre().equals("")) {
			addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, "Titre");
			return true;
		}
		
		if (testExistCalendarRDV()) {
			addErrorMessage(FORMULAIRE_ADD_RDV, "ERROR.FIELD.EXIST", 
					"Calendrier des rendez-vous", "Titre");
			return true;
		}
		
		return testErreurUpdate();
	}
	/**
	 * @return boolean
	 */
	private boolean testErreurUpdate() {
		if (isChoixCommission() && (commItems == null || commItems.isEmpty())) {
			addErrorMessage(FORMULAIRE_ADD_RDV, "ERROR.LIST.EMPTY", "Liste des commissions");
			return true;
		}
		
		if (isChoixVet() && (vetItems == null || vetItems.isEmpty())) {
			addErrorMessage(FORMULAIRE_ADD_RDV, "ERROR.LIST.EMPTY", "Liste des vets");
			return true;
		}
		
		if (getCalendarRDV().getMsgEtudiant() == null || getCalendarRDV().getMsgEtudiant().isEmpty()) {
			addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, "Message d'accueil de l'etudiant");
			return true;
		}
		
		if (getCalendarRDV().getMsgEtudiant().length() > LENGTH_MSG) {
			addErrorMessage(FORMULAIRE_ADD_RDV, "ERROR.FIELD.TOO_LONG", "Message d'accueil de l'etudiant", LENGTH_MSG);
			return true;
		}
		
		if (getCalendarRDV().getParticipeOK()) {
			if (getCalendarRDV().getDateDebutInsc() == null) {
				addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, "Date de debut");
				return true;
			}
			
			if (getCalendarRDV().getDateFinInsc() == null) {
				addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, "Date de fin");
				return true;
			}
			
			if (getCalendarRDV().getNbreMaxEtud() == null) {
				addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, 
						"Nombre d'etudiants par creneau (30 minutes)");
				return true;
			}
			
			if (getCalendarRDV().getNbJoursAvantPro() == null) {
				addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, 
						"Nombre de jours ouvres avant le premier rendez-vous");
				return true;
			}
			
			if (getCalendarRDV().getNbJoursApresPro() == null) {
				addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, 
						"Nombre de jours ouvres proposes e l'etudiant");
				return true;
			}
			
			if (getCalendarRDV().getMsgMailConfirmation() == null 
					|| getCalendarRDV().getMsgMailConfirmation().isEmpty()) {
				addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, "Mail de confirmation");
				return true;
			}
			
//			if (getCalendarRDV().getMsgMailConfirmation().length() > LENGTH_MSG) {
//				addErrorMessage(FORMULAIRE_ADD_RDV, "ERROR.FIELD.TOO_LONG", "Mail de confirmation",
//						LENGTH_MSG);
//				return true;
//			}
			
			if (getCalendarRDV().getMsgValidation() == null 
					|| getCalendarRDV().getMsgValidation().isEmpty()) {
				addErrorMessage(FORMULAIRE_ADD_RDV, ERROR_FIELD_EMPTY, 
						"Message de validation du rendez-vous");
				return true;
			}
			
			if (getCalendarRDV().getMsgValidation().length() > LENGTH_MSG) {
				addErrorMessage(FORMULAIRE_ADD_RDV, "ERROR.FIELD.TOO_LONG", 
						"Message de validation du rendez-vous", LENGTH_MSG);
				return true;
			}
			
		}
		
		return false;
	}
	/**
	 * @return boolean
	 */
	private boolean testExistCalendarRDV() {
		String titre = getCalendarRDV().getTitre();
		for (CalendarRDVPojo c : getListCalendarRdv()) {
			if (titre.equals(c.getCalendarRDV().getTitre())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * @param cgeCode
	 * @return boolean
	 */
	public boolean testExistCge(final String cgeCode) {
		for (CalendarRDVPojo c : getListCalendarRdv()) {
			if (cgeCode.equals(c.getCalendarRDV().getCodeCge())) {
				return true;
			}
		}
		return false;
	}
	/**
	 * @param codeCommission
	 * @return boolean
	 */
	public boolean testExistCommItems(final String codeCommission) {
		for (CalendarRDVPojo rdv : getListCalendarRdv()) {
			for (Commission comm : rdv.getCalendarRDV().getCommissions()) {
				if (comm.getCode().equals(codeCommission)) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * @param codEtp
	 * @param codVrsVet
	 * @return boolean
	 */
	public boolean testExistVetItems(final String codEtp, final int codVrsVet) {
		for (CalendarRDVPojo rdv : getListCalendarRdv()) {
			for (VetCalendar vet : rdv.getCalendarRDV().getVets()) {
				if (vet.getCodEtp().equals(codEtp)
						&& vet.getCodVrsVet() == codVrsVet) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * @return boolean
	 */
	public boolean isChoixCge() {
		return choix.equals(CGE);
	}
	/**
	 * @return boolean
	 */
	public boolean isChoixCommission() {
		return choix.equals(COMMISSION);
	}
	/**
	 * @return boolean
	 */
	public boolean isChoixVet() {
		return choix.equals(VET);
	}
	
	/**
	 * @param codeComm
	 * @return int
	 */
	private int getExistCommItems(final String codeComm) {
		for (int i = 0; i < commItems.size(); i++) {
			if (codeComm.equals(commItems.get(i).getValue())) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * @param codEtp
	 * @param codVrsVet
	 * @return int
	 */
	private int getExistVetItems(final String codEtp, final int codVrsVet) {
		for (int i = 0; i < vetItems.size(); i++) {
			if (codEtp.equals(vetItems.get(i).getValue())
					&& codVrsVet == Integer.parseInt(vetItems.get(i).getDescription())) {
				return i;
			}
		}
		return -1;
	}
	
	
	/*
	 ******************* METHODS COMMISSIONS ********************** */
	/**
	 * Ajoute la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void ajouCommItems() {
		int index = -1;
		for (String c : selectCommADI) {
			for (int i = 0; i < allCommItems.size() && index == -1; i++) {
				if (allCommItems.get(i).getValue().equals(c)) {
					index = i;
				}
			}
			if (index >= 0) {
				commItems.add(allCommItems.get(index));
				allCommItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(commItems, new BeanComparator(VALUE, new NullComparator()));
		Collections.sort(allCommItems, new BeanComparator(VALUE, new NullComparator()));
		selectCommADI.clear();
	}
	/**
	 *  Supprime la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void suppCommItems() {
		int index = -1;
		for (String c : selectCommDI) {
			for (int i = 0; i < commItems.size() && index == -1; i++) {
				if (commItems.get(i).getValue().equals(c)) {
					index = i;
				}
			}
			if (index >= 0) {
				allCommItems.add(commItems.get(index));
				commItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(commItems, new BeanComparator(VALUE, new NullComparator()));
		Collections.sort(allCommItems, new BeanComparator(VALUE, new NullComparator()));
		selectCommDI.clear();
	}
	/**
	 * @param codeComm
	 * @return Commission
	 */
	private Commission recupCommission(final String codeComm) {
		for (Commission comm : getParameterService().getCommissions(null)) {
			if (comm.getCode().equals(codeComm)) {
				return comm;
			}
		}
		return null;
	}
	
	
	/*
	 ******************* METHODS VETS ********************** */
	/**
	 * Ajoute la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void ajouVetItems() {
		int index = -1;
		for (String c : selectVetADI) {
			for (int i = 0; i < allVetItems.size() && index == -1; i++) {
				if (allVetItems.get(i).getValue().equals(c)) {
					index = i;
				}
			}
			if (index >= 0) {
				vetItems.add(allVetItems.get(index));
				allVetItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(vetItems, new BeanComparator(VALUE, new NullComparator()));
		Collections.sort(allVetItems, new BeanComparator(VALUE, new NullComparator()));
		selectVetADI.clear();
	}
	/**
	 *  Supprime la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void suppVetItems() {
		int index = -1;
		for (String c : selectVetDI) {
			for (int i = 0; i < vetItems.size() && index == -1; i++) {
				if (vetItems.get(i).getValue().equals(c)) {
					index = i;
				}
			}
			if (index >= 0) {
				allVetItems.add(vetItems.get(index));
				vetItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(vetItems, new BeanComparator(VALUE, new NullComparator()));
		Collections.sort(allVetItems, new BeanComparator(VALUE, new NullComparator()));
		selectVetDI.clear();
	}
	/**
	 * @param codEtp
	 * @param codVrsVet
	 * @return Commission
	 */
	public Commission getCommissionVet(final String codEtp, final int codVrsVet) {
		for (Commission comm : getParameterService().getCommissions(null)) {
			for (TraitementCmi traitementCmi : comm.getTraitementCmi()) {
				//TODO:toutes les vets (codEtp) ne retourne pas une commissions.
				if (traitementCmi.getVersionEtpOpi().getCodEtp().equals(codEtp)
						&& traitementCmi.getVersionEtpOpi().getCodVrsVet() == codVrsVet) {
					return comm;
				}
			}
		}
		return null;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return la liste des calendriers de rendez-vous
	 */
	@SuppressWarnings("unchecked")
	public List<CalendarRDVPojo> getListCalendarRdv() {
		if (listCalendarRdv == null || listCalendarRdv.isEmpty()) {
			for (CalendarRDV calRdv : getParameterService().getCalendarRdv()) {
				CalendarRDVPojo calendarRDVPojo = new CalendarRDVPojo(calRdv,
						new ArrayList<VetCalendarPojo>(),
						new ArrayList<CommissionPojo>());
				//VetCalendarPojo
				for (VetCalendar vet : calRdv.getVets()) {
					VetCalendarPojo vetCalendarPojo = new VetCalendarPojo(vet,
							getDomainApoService().getVersionEtape(vet.getCodEtp(),
									vet.getCodVrsVet()).getLibWebVet());
					calendarRDVPojo.getListVetCalendarPojo().add(vetCalendarPojo);
				}
				//CommissionPojo
				for(Commission comm : calRdv.getCommissions()) {
					Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
					int codeRI = gest.getProfile().getCodeRI();
					CommissionPojo commissionPojo = new CommissionPojo(comm,
							comm.getContactsCommission().get(codeRI));
					calendarRDVPojo.getListCommissionPojo().add(commissionPojo);
				}
				listCalendarRdv.add(calendarRDVPojo);
			}
			Collections.sort(listCalendarRdv, new BeanComparator("calendarRDV.titre", 
					new NullComparator()));

		}
		return listCalendarRdv;
	}
	/**
	 * @param listCalendarRdv
	 */
	public void setListCalendarRdv(final List<CalendarRDVPojo> listCalendarRdv) {
		this.listCalendarRdv = listCalendarRdv;
	}
	/**
	 * @return actionEnum
	 */
	public ActionEnum getActionEnum() {
		if (actionEnum == null) {
			actionEnum = new ActionEnum();
		}
		return actionEnum;
	}
	/**
	 * @param actionEnum
	 */
	public void setActionEnum(final ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}
	/**
	 * @return le calendrier des rendez-vous selectionne
	 */
	public CalendarRDV getCalendarRDV() {
		if (calendarRDV == null) {
			calendarRDV = new CalendarRDV();
			calendarRDV.setMsgEtudiant(getString("CALENDAR_RDV.MESSAGES.DEFAULT.ACCUEIL"));
			calendarRDV.setMsgValidation(getString("CALENDAR_RDV.MESSAGES.DEFAULT.VALID"));
			calendarRDV.setCommissions(new HashSet<Commission>());
			calendarRDV.setVets(new HashSet<VetCalendar>());
			calendarRDV.setCandidats(new ArrayList<IndividuDate>());
			calendarRDV.setHoraires(new HashMap<Integer, Horaire>());
			calendarRDV.setJourHoraires(new HashMap<Date, JourHoraire>());
			calendarRDV.setTranchesFermees(new HashMap<Date, TrancheFermee>());
			calendarRDV.setParticipeOK(true);
			calendarRDV.setDateDebutInsc(new Date());
			calendarRDV.setDateFinInsc(new Date());
			choix = CGE;
		}
		return calendarRDV;
	}
	/**
	 * @param calendarRDV
	 */
	public void setCalendarRDV(final CalendarRDV calendarRDV) {
		this.calendarRDV = calendarRDV;
		if (calendarRDV.getCodeCge() != null) {
			choix = CGE;
		} else if (calendarRDV.getCommissions() != null
				&& !calendarRDV.getCommissions().isEmpty()) {
			choix = COMMISSION;
		} else {
			choix = VET;
		}
	}
	/**
	 * @return la liste des codes CGE
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllCge() {
		if (allCge.isEmpty()) {
			List<CentreGestion> listCentreGestion = getDomainApoService().getCentreGestion();
			if (listCentreGestion != null) {
				for (CentreGestion centreGestion : listCentreGestion) {
					if (!testExistCge(centreGestion.getCodCge())) {
						allCge.add(new SelectItem(centreGestion.getCodCge()));
					}
				}
			}
			Collections.sort(allCge, new BeanComparator(VALUE, new NullComparator()));
		}
		return allCge;
	}
	/**
	 * @param allCge
	 */
	public void setAllCge(final List<SelectItem> allCge) {
		this.allCge = allCge;
	}
	/**
	 * @return codeCge
	 */
	public String getCodeCge() {
		return codeCge;
	}
	/**
	 * @param codeCge
	 */
	public void setCodeCge(final String codeCge) {
		this.codeCge = codeCge;
	}
	
	/**
	 * @return la liste de toute les commissions
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllCommItems() {
		if (allCommItems.isEmpty()) {
			Set<Commission> allCommissions = getParameterService().getCommissions(null);
			if (allCommissions != null) {
				for (Commission comm : allCommissions) {
					if (!testExistCommItems(comm.getCode())) {
						allCommItems.add(new SelectItem(comm.getCode(), 
								comm.getCode() + " (" + comm.getLibelle() + ")"));
					}
				}
			}
			Collections.sort(allCommItems, new BeanComparator(VALUE, new NullComparator()));
		}
		return allCommItems;
	}
	/**
	 * @param allCommItems
	 */
	public void setAllCommItems(final List<SelectItem> allCommItems) {
		this.allCommItems = allCommItems;
	}
	
	/**
	 * @return la liste de commissions selectionne
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getCommItems() {
		if (commItems.isEmpty()) {
			Set<Commission> listCommissions = getCalendarRDV().getCommissions();
			if (listCommissions != null) {
				for (Commission comm : listCommissions) {
					commItems.add(new SelectItem(comm.getCode(), 
							comm.getCode() + " (" + comm.getLibelle() + ")"));
				}
			}
			Collections.sort(commItems, new BeanComparator(VALUE, new NullComparator()));
		}
		return commItems;
	}
	/**
	 * @param commItems
	 */
	public void setCommItems(final List<SelectItem> commItems) {
		this.commItems = commItems;
	}
	
	/**
	 * @return allVetItems
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllVetItems() {
		if (allVetItems.isEmpty()) {
			Set<Campagne> camps = getParameterService().getCampagnes(true, 
					String.valueOf(getCurrentGest().getProfile().getCodeRI()));
			Set<VersionEtapeDTO> allVets = new HashSet<VersionEtapeDTO>();
			for (Campagne camp : camps) {
				allVets.addAll(getDomainApoService().getVersionEtapes(
					null, null, getCurrentGest().getCodeCge(), camp.getCodAnu()));
			}

			if (!allVets.isEmpty()) {
				Set<Commission> cmi = Utilitaires.getListCommissionsByRight(
						getCurrentGest(), 
						getDomainApoService(),
						getParameterService(), true);
				
				Set<VersionEtapeDTO> allVets2 = new HashSet<VersionEtapeDTO>();
					
				for (VersionEtapeDTO versionEtapeDTO : allVets) {
					if (BusinessUtil.getCmiForVetDTO(cmi, versionEtapeDTO) != null) {
						allVets2.add(versionEtapeDTO);
					}
				}
				allVets = allVets2;
				for (VersionEtapeDTO vet : allVets) {
					if (!testExistVetItems(vet.getCodEtp(),vet.getCodVrsVet())) {
						allVetItems.add(new SelectItem(vet.getCodEtp(),
								vet.getCodEtp() + "(" + vet.getLibWebVet() + ")",
								String.valueOf(vet.getCodVrsVet())));
					}
				}
			}
			Collections.sort(allVetItems, new BeanComparator(VALUE, new NullComparator()));
		}
		return allVetItems;
	}
	/**
	 * @param allVetItems
	 */
	public void setAllVetItems(final List<SelectItem> allVetItems) {
		this.allVetItems = allVetItems;
	}
	
	/**
	 * @return vetItems
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getVetItems() {
		if (vetItems.isEmpty()) {
			Set<VetCalendar> listVets = getCalendarRDV().getVets();
			if (listVets != null) {
				for (VetCalendar vet : listVets) {
					String libWebVet = getDomainApoService().getVersionEtape(vet.getCodEtp(), vet.getCodVrsVet()).getLibWebVet();
					vetItems.add(new SelectItem(vet.getCodEtp(),
							vet.getCodEtp() + "(" + libWebVet + ")",
							String.valueOf(vet.getCodVrsVet())));
				}
			}
			Collections.sort(vetItems, new BeanComparator(VALUE, new NullComparator()));
		}
		return vetItems;
	}
	/**
	 * @param vetItems
	 */
	public void setVetItems(final List<SelectItem> vetItems) {
		this.vetItems = vetItems;
	}

	/**
	 * @return selectCommADI
	 */
	public List<String> getSelectCommADI() {
		return selectCommADI;
	}
	/**
	 * @param selectCommADI
	 */
	public void setSelectCommADI(final List<String> selectCommADI) {
		this.selectCommADI = selectCommADI;
	}
	
	/**
	 * @return selectCommDI
	 */
	public List<String> getSelectCommDI() {
		return selectCommDI;
	}
	/**
	 * @param selectCommDI
	 */
	public void setSelectCommDI(final List<String> selectCommDI) {
		this.selectCommDI = selectCommDI;
	}
	
	/**
	 * @return selectVetADI
	 */
	public List<String> getSelectVetADI() {
		return selectVetADI;
	}
	/**
	 * @param selectVetADI
	 */
	public void setSelectVetADI(final List<String> selectVetADI) {
		this.selectVetADI = selectVetADI;
	}
	
	/**
	 * @return selectVetDI
	 */
	public List<String> getSelectVetDI() {
		return selectVetDI;
	}
	/**
	 * @param selectVetDI
	 */
	public void setSelectVetDI(final List<String> selectVetDI) {
		this.selectVetDI = selectVetDI;
	}
	
	/**
	 * @return allChoix
	 */
	public List<SelectItem> getAllChoix() {
		if (allChoix == null) {
			allChoix = new ArrayList<SelectItem>();
			for (String ch : LESCHOIX) {
				allChoix.add(new SelectItem(ch));
			}
		}
		return allChoix;
	}
	/**
	 * @return choix
	 */
	public String getChoix() {
		return choix;
	}
	/**
	 * @param choix
	 */
	public void setChoix(final String choix) {
		this.choix = choix;
	}
	
	
	/*
	 ******************* ACCESSORS STATIC ******************** */
	/**
	 * @return heure de d�but (AM)
	 */
	public static int getDefaultHeureDebutAM() {
		return DEFAULT_HEURE_DEBUT_AM;
	}
	/**
	 * @return minute de d�but (AM) 
	 */
	public static int getDefaultMinuteDebutAM() {
		return DEFAULT_MINUTE_DEBUT_AM;
	}
	/**
	 * @return heure de fin (AM) 
	 */
	public static int getDefaultHeureFinAM() {
		return DEFAULT_HEURE_FIN_AM;
	}
	/**
	 * @return minute de fin (AM)
	 */
	public static int getDefaultMinuteFinAM() {
		return DEFAULT_MINUTE_FIN_AM;
	}
	/**
	 * @return heure de d�but (PM)
	 */
	public static int getDefaultHeureDebutPM() {
		return DEFAULT_HEURE_DEBUT_PM;
	}
	/**
	 * @return minute de d�but (PM)
	 */
	public static int getDefaultMinuteDebutPM() {
		return DEFAULT_MINUTE_DEBUT_PM;
	}
	/**
	 * @return heure de fin (PM)
	 */
	public static int getDefaultHeureFinPM() {
		return DEFAULT_HEURE_FIN_PM;
	}
	/**
	 * @return minute de fin (PM)
	 */
	public static int getDefaultMinuteFinPM() {
		return DEFAULT_MINUTE_FIN_PM;
	}
}
