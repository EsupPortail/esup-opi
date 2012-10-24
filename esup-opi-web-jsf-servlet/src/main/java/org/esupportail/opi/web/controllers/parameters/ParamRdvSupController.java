package org.esupportail.opi.web.controllers.parameters;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.faces.validator.ValidatorException;

import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.myfaces.custom.schedule.model.SimpleScheduleModel;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.references.rendezvous.Horaire;
import org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire;
import org.esupportail.opi.domain.beans.references.rendezvous.TrancheFermee;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.components.ExtendedEntry;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;


/**
 * 
 * @author gomez
 *
 */
public class ParamRdvSupController extends AbstractContextAwareController {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5408800999916536521L;
	/**
	 * 
	 */
	private static final String FORMULAIRE_ADD_RDV_SUP = "formAddParamRdvSup";
	/**
	 * Constante 8.
	 */
	private static final int HUIT = 8;
	/**
	 * Constantre 13.
	 */
	private static final int TREIZE = 13;
	/**
	 * Constante 30.
	 */
	private static final int TRENTE = 30;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;
	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEntry;
	/**
	 * Schedule Model.
	 */
	private ScheduleModel scheduleModel;
	/**
	 * Calendar.
	 */
	private Calendar calendar;
	/**
	 * Month selected.
	 */
	private Date monthSelected;
	/**
	 * Change Month.
	 */
	private boolean changeMonth;
	/**
	 * Heure du Matin.
	 */
	private List<SelectItem> amHours;
	/**
	 * Heure de l'apres-midi.
	 */
	private List<SelectItem> pmHours;
	
	/**
	 * 
	 */
	private Date jourSelected;
	/**
	 * 
	 */
	private ExtendedEntry entrySelected;
	/**
	 * paramRdvController.
	 */
	private ParamRdvController paramRdvController;
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Controller.
	 */
	public ParamRdvSupController() {
		if (log.isDebugEnabled()) {
			log.debug("Constructor ParamRdvSupController");
		}
		calendar = Calendar.getInstance();
		amHours = new ArrayList<SelectItem>();
		pmHours = new ArrayList<SelectItem>();
	}
	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		actionEnum = null;
		actionEntry = null;
		scheduleModel = null;
		calendar = Calendar.getInstance();
		monthSelected = null;
		changeMonth = true;
		amHours.clear();
		pmHours.clear();
	}
	/**
	 * Listener button initAction.
	 * @param event
	 */
	public void initAction(final ActionEvent event) {
		scheduleModel = null;
		calendar = Calendar.getInstance();
		monthSelected = null;
		changeMonth = true;
		amHours.clear();
		pmHours.clear();
	}
	
	
	/*
	 ******************* METHODE scheduleModel ******************** */
	/**
	 * Action clicked on schedule day.
	 */
	public void scheduleAction() {
		if (scheduleModel.isEntrySelected()) {
			if (scheduleModel.getSelectedEntry().getId().contains("NOTHORAIRE")
					&& actionEntry.getWhatAction().equals(ActionEnum.EMPTY_ACTION)) {
				calendar.clear();
				calendar.setTime(scheduleModel.getSelectedEntry().getStartTime());
				
				entrySelected = (ExtendedEntry) scheduleModel.getSelectedEntry();
				jourSelected = calendar.getTime();
				actionEntry.setWhatAction(ActionEnum.ADD_ACTION);
				
			} else if (scheduleModel.getSelectedEntry().getId().contains("HORAIRE")
					&& actionEntry.getWhatAction().equals(ActionEnum.EMPTY_ACTION)) {
				calendar.clear();
				calendar.setTime(scheduleModel.getSelectedEntry().getStartTime());
				
				entrySelected = (ExtendedEntry) scheduleModel.getSelectedEntry();
				jourSelected = calendar.getTime();
				actionEntry.setWhatAction(ActionEnum.UPDATE_ACTION);
				
			} else if (!scheduleModel.getSelectedEntry().getId().contains("NOTHORAIRE")
					&& !scheduleModel.getSelectedEntry().getId().contains("HORAIRE")
					&& getCalendarRdv().getTranchesFermees() != null
					&& getCalendarRdv().getTranchesFermees().get(
							scheduleModel.getSelectedEntry().getStartTime()) != null) {
				
				calendar.clear();
				calendar.setTime(scheduleModel.getSelectedEntry().getStartTime());
				
				// Tranche fermee existe			
				TrancheFermee tf = getCalendarRdv().getTranchesFermees().get(calendar.getTime());
				
				// Matin� s�lectionn�e
				if (scheduleModel.getSelectedEntry().getId().contains("AM")) {
					if (tf.isJourFerme()) {
						// Jour ferm� -> Ouverture matin�
						tf.setMatin(false);
						revertEntry((ExtendedEntry) scheduleModel.getSelectedEntry(), 
								true, true);
						//mise � jour de trancheFermee
						getDomainService().updateTrancheFermee(tf);
						
					} else if (tf.isMatin()) {
						// Matin� ferm� -> Ouverture matin�
						getCalendarRdv().getTranchesFermees().remove(calendar.getTime());
						revertEntry((ExtendedEntry) scheduleModel.getSelectedEntry(), 
								true, true);
						//suppression de trancheFermee
						getDomainService().deleteTrancheFermee(tf);
						
					} else {
						// Matin� ouverte -> Fermeture matin�
						if (getParameterService().getListEtudiantsParCalendarRdvParDemiJournee(
								getCalendarRdv().getId(),
								calendar.get(Calendar.MONTH),
								calendar.getTime(),
								getDateDebutAM(),
								getDateFinAM()) > 0) {
							
							addErrorMessage(FORMULAIRE_ADD_RDV_SUP + ":scalendar", 
									"CALENDAR_RDV.MESSAGES.RDV_ON_HALF_DAY");
							return;
						} 							
						tf.setMatin(true);
						revertEntry((ExtendedEntry) scheduleModel.getSelectedEntry(), 
								false, true);
						//mise � jour de trancheFermee
						getDomainService().updateTrancheFermee(tf);
					}
				} else {
					// Apr�s midi s�lectionn�e
					if (tf.isJourFerme()) {
						// Jour ferm� -> Ouverture apr�s midi
						tf.setAprem(false);
						revertEntry((ExtendedEntry) scheduleModel.getSelectedEntry(), 
								true, false);
						//mise � jour de trancheFermee
						getDomainService().updateTrancheFermee(tf);
						
					} else if (tf.isAprem()) {
						// Apr�s midi ferm�e -> Ouverture apr�s midi
						getCalendarRdv().getTranchesFermees().remove(calendar.getTime());
						revertEntry((ExtendedEntry) scheduleModel.getSelectedEntry(), 
								true, false);
						//suppression de trancheFermee
						getDomainService().deleteTrancheFermee(tf);
						
					} else {						
						// Apr�s midi ouverte -> fermeture apr�s midi
						if (getParameterService().getListEtudiantsParCalendarRdvParDemiJournee(
								getCalendarRdv().getId(),
								calendar.get(Calendar.MONTH),
								calendar.getTime(),
								getDateDebutPM(),
								getDateFinPM()) > 0) {
							
							addErrorMessage(FORMULAIRE_ADD_RDV_SUP + ":scalendar", 
									"CALENDAR_RDV.MESSAGES.RDV_ON_HALF_DAY");
							return;
						}						
						tf.setAprem(true);
						revertEntry((ExtendedEntry) scheduleModel.getSelectedEntry(), 
								false, false);
						//mise � jour de trancheFermee
						getDomainService().updateTrancheFermee(tf);
					}					
				}		
				
			} else if (!scheduleModel.getSelectedEntry().getId().contains("NOTHORAIRE")
					&& !scheduleModel.getSelectedEntry().getId().contains("HORAIRE")) {
				
				calendar.clear();
				calendar.setTime(scheduleModel.getSelectedEntry().getStartTime());
				
				// Nouvelle tranche ferm�e
				TrancheFermee tf = new TrancheFermee();
				tf.setCalendrierRdv(getCalendarRdv());
				tf.setDateFermeture(scheduleModel.getSelectedEntry().getStartTime());
				
				//mise � jour de la nouvelle tranche
				if (scheduleModel.getSelectedEntry().getId().contains("AM")) {
					// Matin� s�lectionn�e -> fermeture matin�
					if (getParameterService().getListEtudiantsParCalendarRdvParDemiJournee(
							getCalendarRdv().getId(),
							calendar.get(Calendar.MONTH),
							calendar.getTime(),
							getDateDebutAM(),
							getDateFinAM()) > 0) {
						
						addErrorMessage(FORMULAIRE_ADD_RDV_SUP + ":scalendar", 
								"CALENDAR_RDV.MESSAGES.RDV_ON_HALF_DAY");
						return;
					}
					tf.setMatin(true);
					revertEntry((ExtendedEntry) scheduleModel.getSelectedEntry(), false, true);
					
				} else {
					// Apr�s midi s�lectionn�e -> fermeture apr�s midi
					if (getParameterService().getListEtudiantsParCalendarRdvParDemiJournee(
							getCalendarRdv().getId(),
							calendar.get(Calendar.MONTH),
							calendar.getTime(),
							getDateDebutPM(),
							getDateFinPM()) > 0) {
							
						addErrorMessage(FORMULAIRE_ADD_RDV_SUP + ":scalendar", 
								"CALENDAR_RDV.MESSAGES.RDV_ON_HALF_DAY");
						return;	
					}
					tf.setAprem(true);
					revertEntry((ExtendedEntry) scheduleModel.getSelectedEntry(), false, false);
				}
				
				//ajout de trancheFermee
				getCalendarRdv().getTranchesFermees().put(tf.getDateFermeture(), tf);
				getDomainService().addTrancheFermee(tf);
			}
			
			scheduleModel.setSelectedEntry(null);
			scheduleModel.refresh();
		}
    }
	
	/**
	 * @return the scheduleModel
	 */
	public ScheduleModel getScheduleModel() {
		if (scheduleModel == null || isChangeMonth()) {
			setChangeMonth(false);
			
			scheduleModel = new SimpleScheduleModel();
			scheduleModel.setSelectedDate(getMonthSelected());
			scheduleModel.setMode(ScheduleModel.MONTH);
			
			calendar.clear();							
			calendar.setTime(scheduleModel.getSelectedDate());
			
			for (int i = 1;	i <= calendar.getActualMaximum(Calendar.DAY_OF_MONTH);	i++) {
				if (calendar.getTime().after(getCalendarRdv().
							getDateFinInsc())) {
					break;
				}
				
				calendar.set(Calendar.DAY_OF_MONTH, i);	
			   	calendar.set(Calendar.HOUR_OF_DAY, 0);
			   	calendar.set(Calendar.MINUTE, 0);
			   	calendar.set(Calendar.SECOND, 0);
			   	calendar.set(Calendar.MILLISECOND, 0);
			   	
			   	if (calendar.getTime().before(getCalendarRdv().
			   				getDateDebutInsc())) {
					continue;					
				}
			   	
			   	if (calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY
			   			&& calendar.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
			   		JourHoraire jourHoraire = null;
			   		if (getCalendarRdv().getJourHoraires() != null
			   				&& getCalendarRdv().getJourHoraires().
			   				get(calendar.getTime()) != null) {
			   			jourHoraire = getCalendarRdv().getJourHoraires().
			   				get(calendar.getTime()); 
			   		}
			   		
			   		if (getCalendarRdv().getTranchesFermees().
			   				get(calendar.getTime()) != null) {
			   			TrancheFermee tmp = getCalendarRdv().
			   						getTranchesFermees().get(calendar.getTime());
			   			
			   			if (tmp.isMatin()) {
			   				scheduleModel.addEntry(createEntry(false, true));
			   			} else {
			   				scheduleModel.addEntry(createEntry(true, true));
			   			}
			   			
			   			if (tmp.isAprem()) {
			   				scheduleModel.addEntry(createEntry(false, false));
			   			} else {
			   				scheduleModel.addEntry(createEntry(true, false));
			   			}
			   			
			   		} else {
			   			scheduleModel.addEntry(createEntry(true, true));
			   			scheduleModel.addEntry(createEntry(true, false));
			   		}
				   	
			   		if (jourHoraire != null) {
			   			scheduleModel.addEntry(createEntryHoraire(false, 
			   					jourHoraire.getDateDebutMatin(), 
			   					jourHoraire.getDateFinMatin(), 
			   					jourHoraire.getDateDebutAmidi(), 
			   					jourHoraire.getDateFinAmidi()));
			   			
			   		} else {
			   			scheduleModel.addEntry(createEntryHoraire(true, 
			   					null, 
			   					null, 
			   					null, 
			   					null));
			   			
				   	}
			   	}			   	
			}
			scheduleModel.refresh();
		}
		return scheduleModel;
	}
	
	/**
	 * Passage de OPEN -> CLOSE et de CLOSE -> OPEN.
	 * @param entry
	 * @param open 
	 * @param am
	 */
	private void  revertEntry(final ExtendedEntry entry, final boolean open, final boolean am) {
		if (am) {
			if (open) {			
				entry.setTitle(getString("CALENDAR_RDV.TEXT.OPEN_AM"));
				entry.setColor("#b2ff88");
			} else {
				entry.setTitle(getString("CALENDAR_RDV.TEXT.CLOSE_AM"));
				entry.setColor("#ff8e8e");
			}
		} else {
			if (open) {					
				entry.setTitle(getString("CALENDAR_RDV.TEXT.OPEN_PM"));
				entry.setColor("#b2ff88");
			} else {
				entry.setTitle(getString("CALENDAR_RDV.TEXT.CLOSE_PM"));
				entry.setColor("#ff8e8e");
			}
		}
	}
	/**
	 * @param open
	 * @param am
	 * @return entry
	 */
	private ExtendedEntry createEntry(final boolean open, final boolean am) {
		ExtendedEntry entry = new ExtendedEntry();
		
	    if (am) {
	    	entry.setId("AM" + calendar.getTimeInMillis());
		    entry.setStartTime(calendar.getTime());
	    	
	    	if (open) {	    		
	    		entry.setTitle(getString("CALENDAR_RDV.TEXT.OPEN_AM"));
	    		entry.setColor("#b2ff88");
	    	} else {
	    		entry.setTitle(getString("CALENDAR_RDV.TEXT.CLOSE_AM"));
	    		entry.setColor("#ff8e8e");
	    	}
	    	
	    } else {
	    	entry.setId("PM" + calendar.getTimeInMillis());
		    entry.setStartTime(calendar.getTime());
	    	
	    	if (open) {
	    		entry.setTitle(getString("CALENDAR_RDV.TEXT.OPEN_PM"));
	    		entry.setColor("#b2ff88");
	    	} else {
	    		entry.setTitle(getString("CALENDAR_RDV.TEXT.CLOSE_PM"));
	    		entry.setColor("#ff8e8e");
	    	}
	    	
	    }
	    
	    calendar.add(Calendar.HOUR_OF_DAY, 2);
	    entry.setEndTime(calendar.getTime());
	    entry.setAllDay(true);
	    
		return entry;
	}
	/**
	 * @param notHeure
	 * @param dateDebutAM 
	 * @param dateFinAM 
	 * @param dateDebutPM 
	 * @param dateFinPM 
	 * @return entry
	 */
	private ExtendedEntry createEntryHoraire(final boolean notHeure, 
			final Date dateDebutAM, final Date dateFinAM, 
			final Date dateDebutPM, final Date dateFinPM) {
		ExtendedEntry entry = new ExtendedEntry();
		
		if (notHeure) {
			entry.setId("NOTHORAIRE" + calendar.getTimeInMillis());
			entry.setStartTime(calendar.getTime());
			
			entry.setTitle("Ajouter une horaire");
			entry.setColor("#b2ff00");
		} else {
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateDebutAM);
			int heureDebutAM = cal.get(Calendar.HOUR_OF_DAY);
			int minuteDebutAM = cal.get(Calendar.MINUTE);
			cal.setTime(dateFinAM);
			int heureFinAM = cal.get(Calendar.HOUR_OF_DAY);
			int minuteFinAM = cal.get(Calendar.MINUTE);
			cal.setTime(dateDebutPM);
			int heureDebutPM = cal.get(Calendar.HOUR_OF_DAY);
			int minuteDebutPM = cal.get(Calendar.MINUTE);
			cal.setTime(dateFinPM);
			int heureFinPM = cal.get(Calendar.HOUR_OF_DAY);
			int minuteFinPM = cal.get(Calendar.MINUTE);
			
			entry.setId("HORAIRE" + calendar.getTimeInMillis());
			entry.setStartTime(calendar.getTime());
			
			entry.setTitle(heureDebutAM + "h" + minuteDebutAM + " - "
					+ heureFinAM + "h" + minuteFinAM + " / "
					+ heureDebutPM + "h" + minuteDebutPM + " - "
					+ heureFinPM + "h" + minuteFinPM);
			entry.setColor("#b2ff00");
		}

	    calendar.add(Calendar.HOUR_OF_DAY, 2);
	    entry.setEndTime(calendar.getTime());
	    entry.setAllDay(true);
	    
		return entry;
	}

	
	/*
	 ******************* PRESMONTH et NEXTMONTH ******************** */
	/**
	 * Listener button precMonth.
	 * @param event
	 */
	public void precMonth(final ActionEvent event) {
		calendar.clear();
		calendar.setTime(scheduleModel.getSelectedDate());
		calendar.add(Calendar.MONTH, -1);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		
		if (!calendar.getTime().before(getCalendarRdv().getDateDebutInsc())) {
			log.info("Prec month" + calendar.getTime());
			setMonthSelected(calendar.getTime());
			setChangeMonth(true);
		}
	}
	/**
	 * Listener button nextMonth.
	 * @param event
	 */
	public void nextMonth(final ActionEvent event) {
		calendar.clear();
		calendar.setTime(scheduleModel.getSelectedDate());
		calendar.add(Calendar.MONTH, 1);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		
		if (!calendar.getTime().after(getCalendarRdv().getDateFinInsc())) {
			log.info("Next month " + calendar.getTime());
			setMonthSelected(calendar.getTime());
			setChangeMonth(true);
		}
	}

	
	/*
	 ******************* VALIDATOR ******************** */
	/**
	 * Validation de l'heure de d�but de matin�.
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateDebutAMHour(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) throws ValidatorException {
		Date dateSelected = scheduleModel.getSelectedDate();
		int mois = calendar.get(Calendar.MONTH);
		Date dateDebutAM = (Date) value;
		Date dateFinAM = getCalendarRdv().getHoraires().get(mois).getDateFinMatin();
		
		if (!validate(dateSelected, dateDebutAM, dateFinAM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.HOUR_DEB_AM_AFTER_HOUR_FIN_AM"));
		}
		
		if (testRdvEtudiant(mois, (Date) getAMHours().get(0).getValue(), dateDebutAM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.RDV_BEFORE_HOUR_DEB_AM"));
		}
	}
	/**
	 * Validation de l'heure de fin de matin�.
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateFinAMHour(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) throws ValidatorException {
		Date dateSelected = scheduleModel.getSelectedDate();
		int mois = calendar.get(Calendar.MONTH);
		Date dateDebutAM = getCalendarRdv().getHoraires().get(mois).getDateDebutMatin();
		Date dateFinAM = (Date) value;
		
		if (!validate(dateSelected, dateDebutAM, dateFinAM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.HOUR_FIN_AM_BEFORE_HOUR_DEB_AM"));
		}
		
		if (testRdvEtudiant(mois, dateFinAM, (Date) getAMHours().get(getAMHours().size() - 1).getValue())) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.RDV_AFTER_HOUR_FIN_AM"));
		}
	}
	/**
	 * Validation de l'heure de d�but d'apr�s midi.
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateDebutPMHour(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) throws ValidatorException {
		Date dateSelected = scheduleModel.getSelectedDate();
		int mois = calendar.get(Calendar.MONTH);
		Date dateDebutPM = (Date) value;
		Date dateFinPM = getCalendarRdv().getHoraires().get(mois).getDateFinAmidi();
		
		if (!validate(dateSelected, dateDebutPM, dateFinPM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.HOUR_DEB_PM_AFTER_HOUR_FIN_PM"));
		}
		
		if (testRdvEtudiant(mois, (Date) getPMHours().get(0).getValue(), dateDebutPM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.RDV_BEFORE_HOUR_DEB_PM"));
		}
	}
	/**
	 * Validation de l'heure de fin d'apr�s midi.
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateFinPMHour(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) throws ValidatorException {
		Date dateSelected = scheduleModel.getSelectedDate();
		int mois = calendar.get(Calendar.MONTH);
		Date dateDebutPM = getCalendarRdv().getHoraires().get(mois).getDateDebutAmidi();
		Date dateFinPM = (Date) value;
		
		if (!validate(dateSelected, dateDebutPM, dateFinPM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.HOUR_FIN_PM_BEFORE_HOUR_DEB_PM"));
		}
		
		if (testRdvEtudiant(mois, dateFinPM, (Date) getPMHours().get(getPMHours().size() - 1).getValue())) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.RDV_AFTER_HOUR_FIN_PM"));
		}
	}
	/**
	 * 
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateDebutAMHourJourHoraire(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) throws ValidatorException {
		Date dateDebutAM = (Date) value;
		Date dateFinAM;
		if (getCalendarRdv().getHoraires().get(jourSelected) != null) {
			dateFinAM = getCalendarRdv().getJourHoraires().get(jourSelected).getDateFinMatin();
		} else {
			//R�cup�ration de la date par default (date de fin AM)
			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.HOUR_OF_DAY, ParamRdvController.getDefaultHeureFinAM());
			cal.set(Calendar.MINUTE, ParamRdvController.getDefaultMinuteFinAM());
			dateFinAM = cal.getTime();
		}
		
		if (!validate(jourSelected, dateDebutAM, dateFinAM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.HOUR_DEB_AM_AFTER_HOUR_FIN_AM"));
		}
		
		if (testRdvEtudiant(jourSelected, (Date) getAMHours().get(0).getValue(), dateDebutAM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.RDV_BEFORE_HOUR_DEB_AM"));
		}
	}
	/**
	 * 
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateFinAMHourJourHoraire(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) throws ValidatorException {
		Date dateDebutAM;
		if (getCalendarRdv().getHoraires().get(jourSelected) != null) {
			dateDebutAM = getCalendarRdv().getJourHoraires().get(jourSelected).getDateDebutMatin();
		} else {
			//R�cup�ration de la date par default (date de d�but AM)
			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.HOUR_OF_DAY, ParamRdvController.getDefaultHeureDebutAM());
			cal.set(Calendar.MINUTE, ParamRdvController.getDefaultMinuteDebutAM());
			dateDebutAM = cal.getTime();
		}
		Date dateFinAM = (Date) value;
		
		if (!validate(jourSelected, dateDebutAM, dateFinAM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.HOUR_FIN_AM_BEFORE_HOUR_DEB_AM"));
		}
		
		if (testRdvEtudiant(jourSelected, dateFinAM, 
				(Date) getAMHours().get(getAMHours().size() - 1).getValue())) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.RDV_AFTER_HOUR_FIN_AM"));
		}
	}
	/**
	 * 
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateDebutPMHourJourHoraire(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) throws ValidatorException {
		Date dateDebutPM = (Date) value;
		Date dateFinPM;
		if (getCalendarRdv().getHoraires().get(jourSelected) != null) {
			dateFinPM = getCalendarRdv().getHoraires().get(jourSelected).getDateFinAmidi();
		} else {
			//R�cup�ration de la date par default (date de fin PM)
			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.HOUR_OF_DAY, ParamRdvController.getDefaultHeureFinPM());
			cal.set(Calendar.MINUTE, ParamRdvController.getDefaultMinuteFinPM());
			dateFinPM = cal.getTime();
		}
		
		if (!validate(jourSelected, dateDebutPM, dateFinPM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.HOUR_DEB_PM_AFTER_HOUR_FIN_PM"));
		}
		
		if (testRdvEtudiant(jourSelected, (Date) getPMHours().get(0).getValue(), dateDebutPM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.RDV_BEFORE_HOUR_DEB_PM"));
		}
	}
	/**
	 * 
	 * @param context
	 * @param component
	 * @param value
	 * @throws ValidatorException
	 */
	public void validateFinPMHourJourHoraire(
			final FacesContext context, 
			final UIComponent component, 
			final Object value) throws ValidatorException {
		Date dateDebutPM;
		if (getCalendarRdv().getHoraires().get(jourSelected) != null) {
			dateDebutPM = getCalendarRdv().getHoraires().get(jourSelected).getDateDebutAmidi();
		} else {
			//R�cup�ration de la date par default (date de d�but PM)
			Calendar cal = new GregorianCalendar();
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.HOUR_OF_DAY, ParamRdvController.getDefaultHeureDebutPM());
			cal.set(Calendar.MINUTE, ParamRdvController.getDefaultMinuteDebutPM());
			dateDebutPM = cal.getTime();
		}
		Date dateFinPM = (Date) value;
		
		if (!validate(jourSelected, dateDebutPM, dateFinPM)) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.HOUR_FIN_PM_BEFORE_HOUR_DEB_PM"));
		}
		
		if (testRdvEtudiant(jourSelected, dateFinPM, 
				(Date) getPMHours().get(getPMHours().size() - 1).getValue())) {
			throw new ValidatorException(
				getFacesErrorMessage("CALENDAR_RDV.MESSAGES.RDV_AFTER_HOUR_FIN_PM"));
		}
	}
	/**
	 * 
	 * @param dateSelected
	 * @param dateDebut
	 * @param dateFin
	 * @return boolean
	 */
	public boolean validate(final Date dateSelected,
			final Date dateDebut, 
			final Date dateFin) {
		calendar.clear();
		calendar.setTime(dateSelected);
		
		//heure de debut
		Calendar calHoraire = new GregorianCalendar();
		calHoraire.setTime(dateDebut);
		int heureDebut = calHoraire.get(Calendar.HOUR_OF_DAY);
		int minuteDebut = calHoraire.get(Calendar.MINUTE);
		
		//heure de fin
		calHoraire.setTime(dateFin);
		int heureFin = calHoraire.get(Calendar.HOUR_OF_DAY);
		int minuteFin = calHoraire.get(Calendar.MINUTE);
		
		//teste si l'heure n'est pas sup�rieure � l'heure de fin du matin
		if (heureDebut > heureFin || (heureDebut == heureFin && minuteDebut >= minuteFin)) {
			return false;
		}
		return true;
	}
	/**
	 * teste si un �tudiant n'a pas pris rendez-vous  du nouveau cr�neau horaire.
	 * @param mois 
	 * @param dateDebut
	 * @param dateFin
	 * @return boolean
	 */
	public boolean testRdvEtudiant(final int mois, final Date dateDebut, final Date dateFin) {
		if (getParameterService().getListEtudiantsParCalendarRdvParPeriode(
				getCalendarRdv().getId(),
				mois,
				dateDebut,
				dateFin) > 0) {
			return true;
		}
		return false;
	}
	/**
	 * teste si un �tudiant n'a pas pris rendez-vous  du nouveau cr�neau horaire.
	 * @param jour 
	 * @param dateDebut
	 * @param dateFin
	 * @return boolean
	 */
	private boolean testRdvEtudiant(final Date jour, final Date dateDebut, final Date dateFin) {
		if (getParameterService().getListEtudiantsParCalendarRdvParPeriode(
				getCalendarRdv().getId(),
				jour,
				dateDebut,
				dateFin) > 0) {
			return true;
		}
		return false;
	}
	
	
	/*
	 ******************* Methode ******************** */
	/**
	 * @return the dateDebutMatin
	 */
	public Date getDateDebutAM() {
		Date dateDebutAM;
		if (getCalendarRdv() != null && scheduleModel.getSelectedDate() != null) {
			calendar.clear();
			calendar.setTime(scheduleModel.getSelectedDate());
			dateDebutAM = getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)).getDateDebutMatin();
			return dateDebutAM;
		}
		return null;
	}
	
	/**
	 * @param dateDebutAM
	 */
	public void setDateDebutAM(final Date dateDebutAM) {
		if (getCalendarRdv() != null && scheduleModel.getSelectedDate() != null) {
			log.info("Modification de dateDebutAM");
			
			calendar.clear();
			calendar.setTime(scheduleModel.getSelectedDate());
			
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateDebutAM);
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.SECOND, 0);
			
			getCalendarRdv().getHoraires().get(
				calendar.get(Calendar.MONTH)).setDateDebutMatin(cal.getTime());
			
			//mise � jour de l'horaire
			getDomainService().updateHoraire(getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)));
		}
	}
	
	/**
	 * @return the dateFinMatin
	 */
	public Date getDateFinAM() {
		Date dateFinAM;
		if (getCalendarRdv() != null && scheduleModel.getSelectedDate() != null) {
			calendar.clear();
			calendar.setTime(scheduleModel.getSelectedDate());		
			dateFinAM = getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)).getDateFinMatin();
			return dateFinAM;
		}
		return null;
	}
	
	/**
	 * @param dateFinAM
	 */
	public void setDateFinAM(final Date dateFinAM) {
		if (getCalendarRdv() != null && scheduleModel.getSelectedDate() != null) {
			log.info("Modification de dateFinAM");
			calendar.clear();
			calendar.setTime(scheduleModel.getSelectedDate());
			
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateFinAM);
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.SECOND, 0);
			
			getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)).setDateFinMatin(cal.getTime());
			
			//mise � jour de l'horaire
			getDomainService().updateHoraire(getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)));
		}
	}
	
	/**
	 * @return the dateDebutAmidi
	 */
	public Date getDateDebutPM() {
		Date dateDebutPM;
		if (getCalendarRdv() != null && scheduleModel.getSelectedDate() != null) {
			calendar.clear();
			calendar.setTime(scheduleModel.getSelectedDate());
			dateDebutPM = getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)).getDateDebutAmidi();
			return dateDebutPM;
		}
		return null;
	}
	
	/**
	 * @param dateDebutPM
	 */
	public void setDateDebutPM(final Date dateDebutPM) {
		if (getCalendarRdv() != null && scheduleModel.getSelectedDate() != null) {
			log.info("Modification de dateDebutPM");
			calendar.clear();
			calendar.setTime(scheduleModel.getSelectedDate());
			
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateDebutPM);
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.SECOND, 0);
			
			getCalendarRdv().getHoraires().get(
				calendar.get(Calendar.MONTH)).setDateDebutAmidi(cal.getTime());
			
			//mise � jour de l'horaire
			getDomainService().updateHoraire(getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)));
		}
	}
	
	/**
	 * @return the dateFinAmidi
	 */
	public Date getDateFinPM() {
		Date dateFinPM;
		if (getCalendarRdv() != null && scheduleModel.getSelectedDate() != null) {
			calendar.clear();
			calendar.setTime(scheduleModel.getSelectedDate());
			dateFinPM = getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)).getDateFinAmidi();
			return dateFinPM;
		}
		return null;
	}
	
	/**
	 * @param dateFinPM 
	 */
	public void setDateFinPM(final Date dateFinPM) {
		if (getCalendarRdv() != null && scheduleModel.getSelectedDate() != null) {
			log.info("Modification de dateFinPM");
			calendar.clear();
			calendar.setTime(scheduleModel.getSelectedDate());
			
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateFinPM);
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.SECOND, 0);
			
			getCalendarRdv().getHoraires().get(
				calendar.get(Calendar.MONTH)).setDateFinAmidi(cal.getTime());
			
			//mise � jour de l'horaire
			getDomainService().updateHoraire(getCalendarRdv().getHoraires().get(
					calendar.get(Calendar.MONTH)));
		}
	}
	
	/**
	 * @return l'heure de d�but de la matin� du jour s�lectionn�
	 */
	public Date getDateDebutAMJourHoraire() {
		if (getCalendarRdv() != null && jourSelected != null
				&& getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
			return getCalendarRdv().getJourHoraires().get(jourSelected).getDateDebutMatin();
		}
		
		int	mois = -1;
		if (jourSelected != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(jourSelected);
			mois = cal.get(Calendar.MONTH);
		}
		
		if (getCalendarRdv() != null
				&& getCalendarRdv().getHoraires().get(mois) != null) {
			Horaire ho = getCalendarRdv().getHoraires().get(mois);
			Calendar cal = new GregorianCalendar();
			cal.setTime(ho.getDateDebutMatin());
			return getDateHeure(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		}
		
		return getDateHeure(ParamRdvController.getDefaultHeureDebutAM(), 
				ParamRdvController.getDefaultMinuteDebutAM());
	}
	
	/**
	 * @param dateDebutAM
	 */
	public void setDateDebutAMJourHoraire(final Date dateDebutAM) {
		if (getCalendarRdv() != null && jourSelected != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateDebutAM);
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.SECOND, 0);
			
			if (getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
				getCalendarRdv().getJourHoraires().get(jourSelected).setDateDebutMatin(cal.getTime());
			} else {
				JourHoraire jourHo = newJourHoraire();
				jourHo.setDateDebutMatin(cal.getTime());
				getCalendarRdv().getJourHoraires().put(jourSelected, jourHo);
			}
		}
	}
	
	/**
	 * @return l'heure de fin de la matin� du jour s�lectionn�
	 */
	public Date getDateFinAMJourHoraire() {
		if (getCalendarRdv() != null && jourSelected != null
				&& getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
			return getCalendarRdv().getJourHoraires().get(jourSelected).getDateFinMatin();
		}

		int	mois = -1;
		if (jourSelected != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(jourSelected);
			mois = cal.get(Calendar.MONTH);
		}
		
		if (getCalendarRdv() != null
				&& getCalendarRdv().getHoraires().get(mois) != null) {
			Horaire ho = getCalendarRdv().getHoraires().get(mois);
			Calendar cal = new GregorianCalendar();
			cal.setTime(ho.getDateFinMatin());
			return getDateHeure(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		}
		
		return getDateHeure(ParamRdvController.getDefaultHeureFinAM(), 
				ParamRdvController.getDefaultMinuteFinAM());
	}
	
	/**
	 * @param dateFinAM
	 */
	public void setDateFinAMJourHoraire(final Date dateFinAM) {
		if (getCalendarRdv() != null && jourSelected != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateFinAM);
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.SECOND, 0);
			
			if (getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
				getCalendarRdv().getJourHoraires().get(jourSelected).setDateFinMatin(cal.getTime());
			} else {
				JourHoraire jourHo = newJourHoraire();
				jourHo.setDateFinMatin(cal.getTime());
				getCalendarRdv().getJourHoraires().put(jourSelected, jourHo);
			}
		}
	}
	
	/**
	 * @return l'heure de d�but de l'apr�s midi du jour s�lectionn�
	 */
	public Date getDateDebutPMJourHoraire() {
		if (getCalendarRdv() != null && jourSelected != null
				&& getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
			return getCalendarRdv().getJourHoraires().get(jourSelected).getDateDebutAmidi();
		}

		int	mois = -1;
		if (jourSelected != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(jourSelected);
			mois = cal.get(Calendar.MONTH);
		}
		
		if (getCalendarRdv() != null
				&& getCalendarRdv().getHoraires().get(mois) != null) {
			Horaire ho = getCalendarRdv().getHoraires().get(mois);
			Calendar cal = new GregorianCalendar();
			cal.setTime(ho.getDateDebutAmidi());
			return getDateHeure(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		}
		
		return getDateHeure(ParamRdvController.getDefaultHeureDebutPM(), 
				ParamRdvController.getDefaultMinuteDebutPM());
	}
	
	/**
	 * @param dateDebutPM
	 */
	public void setDateDebutPMJourHoraire(final Date dateDebutPM) {
		if (getCalendarRdv() != null && jourSelected != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateDebutPM);
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.SECOND, 0);
			
			if (getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
				getCalendarRdv().getJourHoraires().get(jourSelected).setDateDebutAmidi(cal.getTime());
			} else {
				JourHoraire jourHo = newJourHoraire();
				jourHo.setDateDebutAmidi(cal.getTime());
				getCalendarRdv().getJourHoraires().put(jourSelected, jourHo);
			}
		}
	}
	
	/**
	 * @return l'heure de fin de l'apr�s midi du jour s�lectionn�
	 */
	public Date getDateFinPMJourHoraire() {
		if (getCalendarRdv() != null && jourSelected != null
				&& getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
			return getCalendarRdv().getJourHoraires().get(jourSelected).getDateFinAmidi();
		}

		int	mois = -1;
		if (jourSelected != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(jourSelected);
			mois = cal.get(Calendar.MONTH);
		}
		
		if (getCalendarRdv() != null
				&& getCalendarRdv().getHoraires().get(mois) != null) {
			Horaire ho = getCalendarRdv().getHoraires().get(mois);
			Calendar cal = new GregorianCalendar();
			cal.setTime(ho.getDateFinAmidi());
			return getDateHeure(cal.get(Calendar.HOUR_OF_DAY), cal.get(Calendar.MINUTE));
		}
		
		return getDateHeure(ParamRdvController.getDefaultHeureFinPM(), 
				ParamRdvController.getDefaultMinuteFinPM());
	}
	
	/**
	 * @param dateFinPM 
	 */
	public void setDateFinPMJourHoraire(final Date dateFinPM) {
		if (getCalendarRdv() != null && jourSelected != null) {
			Calendar cal = new GregorianCalendar();
			cal.setTime(dateFinPM);
			cal.set(Calendar.YEAR, 0);
			cal.set(Calendar.MONTH, 0);
			cal.set(Calendar.DATE, 0);
			cal.set(Calendar.SECOND, 0);
			
			if (getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
				getCalendarRdv().getJourHoraires().get(jourSelected).setDateFinAmidi(cal.getTime());
			} else {
				JourHoraire jourHo = newJourHoraire();
				jourHo.setDateFinAmidi(cal.getTime());
				getCalendarRdv().getJourHoraires().put(jourSelected, jourHo);
			}
		}
	}
	/**
	 * 
	 * @return JourHoraire
	 */
	private JourHoraire newJourHoraire() {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 0);
		cal.set(Calendar.SECOND, 0);
		
		JourHoraire jourHo = new JourHoraire(jourSelected, getCalendarRdv());
		
		cal.set(Calendar.HOUR_OF_DAY, ParamRdvController.getDefaultHeureDebutAM());
		cal.set(Calendar.MINUTE, ParamRdvController.getDefaultMinuteDebutAM());
		jourHo.setDateDebutMatin(cal.getTime());
		cal.set(Calendar.HOUR_OF_DAY, ParamRdvController.getDefaultHeureFinAM());
		cal.set(Calendar.MINUTE, ParamRdvController.getDefaultMinuteFinAM());
		jourHo.setDateFinMatin(cal.getTime());
		cal.set(Calendar.HOUR_OF_DAY, ParamRdvController.getDefaultHeureDebutPM());
		cal.set(Calendar.MINUTE, ParamRdvController.getDefaultMinuteDebutPM());
		jourHo.setDateDebutAmidi(cal.getTime());
		cal.set(Calendar.HOUR_OF_DAY, ParamRdvController.getDefaultHeureFinPM());
		cal.set(Calendar.MINUTE, ParamRdvController.getDefaultMinuteFinPM());
		jourHo.setDateFinAmidi(cal.getTime());
		
		return jourHo;
	}
	/**
	 * 
	 * @param heure
	 * @param minute
	 * @return date
	 */
	private Date getDateHeure(final int heure, final int minute) {
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.YEAR, 0);
		cal.set(Calendar.MONTH, 0);
		cal.set(Calendar.DATE, 0);
		cal.set(Calendar.HOUR_OF_DAY, heure);
		cal.set(Calendar.MINUTE, minute);
		cal.set(Calendar.SECOND, 0);
		
		return cal.getTime();
	}
	/**
	 * 
	 * @return String
	 */
	public String getJourSelectedString() {
		if (jourSelected != null) {
			SimpleDateFormat format = (SimpleDateFormat) DateFormat.getDateInstance();
			format.applyPattern("dd/mm/yyyy");
			return format.format(jourSelected);
		}
		return "";
	}
	
	
	/*
	 ******************* METHODE VAILDER HORAIRE DU JOUR ******************** */
	/**
	 * 
	 * @return String
	 */
	public String validerAjout() {
		if (getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
			log.info("Valider l'ajout de l'horaire");
			JourHoraire jourHoraire = getCalendarRdv().getJourHoraires().get(jourSelected);
			getDomainService().addJourHoraire(jourHoraire);
			
			ExtendedEntry newEntry = createEntryHoraire(
					false, 
					jourHoraire.getDateDebutMatin(), 
					jourHoraire.getDateFinMatin(), 
					jourHoraire.getDateDebutAmidi(), 
					jourHoraire.getDateFinAmidi());
			entrySelected.setId(newEntry.getId());
			entrySelected.setTitle(newEntry.getTitle());
			entrySelected.setColor(newEntry.getColor());
			getScheduleModel().refresh();
		}
		
		actionEntry.setWhatAction(ActionEnum.EMPTY_ACTION);
		return null;
	}
	/**
	 * 
	 * @return String
	 */
	public String validerModif() {
		log.info("Valider la modification de l'horaire");
		JourHoraire jourHoraire = getCalendarRdv().getJourHoraires().get(jourSelected);
		getDomainService().updateJourHoraire(jourHoraire);
		
		ExtendedEntry newEntry = createEntryHoraire(
				false, 
				jourHoraire.getDateDebutMatin(), 
				jourHoraire.getDateFinMatin(), 
				jourHoraire.getDateDebutAmidi(), 
				jourHoraire.getDateFinAmidi());
		entrySelected.setTitle(newEntry.getTitle());
		getScheduleModel().refresh();
		
		actionEntry.setWhatAction(ActionEnum.EMPTY_ACTION);
		return null;
	}
	/**
	 * 
	 * @return String
	 */
	public String annulerModif() {
		log.info("Annuler la modification de l'horaire");
		if (getCalendarRdv().getJourHoraires().get(jourSelected) != null) {
			if (actionEntry.getWhatAction().equals(ActionEnum.ADD_ACTION)) {
				getCalendarRdv().getJourHoraires().remove(jourSelected);
			} else {
				getDomainService().refreshJourHoraire(
						getCalendarRdv().getJourHoraires().get(jourSelected));
			}
		}
		actionEntry.setWhatAction(ActionEnum.EMPTY_ACTION);
		return null;
	}
	/**
	 * 
	 * @return String
	 */
	public String supprimer() {
		log.info("Supprimer l'horaire");
		JourHoraire jourHoraire = getCalendarRdv().getJourHoraires().get(jourSelected);
		getDomainService().deleteJourHoraire(jourHoraire);
		
		ExtendedEntry newEntry = createEntryHoraire(true, null, null, null, null);
		entrySelected.setId(newEntry.getId());
		entrySelected.setTitle(newEntry.getTitle());
		entrySelected.setColor(newEntry.getColor());
		getScheduleModel().refresh();
		
		actionEntry.setWhatAction(ActionEnum.EMPTY_ACTION);
		return null;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return paramRdvController
	 */
	public ParamRdvController getParamRdvController() {
		return paramRdvController;
	}
	/**
	 * 
	 * @param paramRdvController
	 */
	public void setParamRdvController(final ParamRdvController paramRdvController) {
		this.paramRdvController = paramRdvController;
	}
	/**
	 * 
	 * @return actionEnum
	 */
	public ActionEnum getActionEnum() {
		if (actionEnum == null) {
			actionEnum = new ActionEnum();
		}
		return actionEnum;
	}
	/**
	 * 
	 * @param actionEnum
	 */
	public void setActionEnum(final ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}
	/**
	 * 
	 * @return actionEntry
	 */
	public ActionEnum getActionEntry() {
		if (actionEntry == null) {
			actionEntry = new ActionEnum();
		}
		return actionEntry;
	}
	/**
	 * 
	 * @param actionEntry
	 */
	public void setActionEntry(final ActionEnum actionEntry) {
		this.actionEntry = actionEntry;
	}
	/**
	 * @param scheduleModel the scheduleModel to set
	 */
	public void setScheduleModel(final ScheduleModel scheduleModel) {
		this.scheduleModel = scheduleModel;
	}
	
	/**
	 * @return the amHours
	 */
	public List<SelectItem> getAMHours() {
		if (amHours.isEmpty()) {
			SimpleDateFormat format = (SimpleDateFormat) DateFormat.getDateInstance();
			format.applyPattern("k:mm");
			
			calendar.clear();
			calendar.set(Calendar.HOUR_OF_DAY, HUIT);
			calendar.set(Calendar.MINUTE, 0);
						
			while (format.format(calendar.getTime()).compareTo("13:00") != 0) {
				SelectItem s1 = new SelectItem(calendar.getTime(), format.format(calendar.getTime()));
				amHours.add(s1);
				calendar.add(Calendar.MINUTE, TRENTE);
			}
		}		
		return amHours;
	}
	
	/**
	 * @return the AMHour
	 */
	public List<SelectItem> getPMHours() {
		if (pmHours.isEmpty()) {
			SimpleDateFormat format = (SimpleDateFormat) DateFormat.getDateInstance();
			format.applyPattern("k:mm");
			
			calendar.clear();
			calendar.set(Calendar.HOUR_OF_DAY, TREIZE);
			calendar.set(Calendar.MINUTE, 0);
						
			while (format.format(calendar.getTime()).compareTo("19:00") != 0) {
				SelectItem s1 = new SelectItem(calendar.getTime(), format.format(calendar.getTime()));
				pmHours.add(s1);
				calendar.add(Calendar.MINUTE, TRENTE);
			}
		}		
		return pmHours;
	}
	
	/**
	 * @return the changeMonth
	 */
	public boolean isChangeMonth() {
		return changeMonth;
	}
	/**
	 * @param changeMonth the changeMonth to set
	 */
	public void setChangeMonth(final boolean changeMonth) {
		this.changeMonth = changeMonth;
	}
	
	/**
	 * @return the monthSelected
	 */
	public Date getMonthSelected() {
		if (monthSelected == null && getCalendarRdv() != null) {
			setMonthSelected(getCalendarRdv().getDateDebutInsc());
		}
		log.info("Month Selected : " + monthSelected);
		return monthSelected;
	}
	/**
	 * @param monthSelected the monthSelected to set
	 */
	public void setMonthSelected(final Date monthSelected) {
		this.monthSelected = monthSelected;
	}
	
	/**
	 * 
	 * @return calendar
	 */
	public Calendar getCalendar() {
		return calendar;
	}
	
	/**
	 * @return the calendarRdv
	 */
	public CalendarRDV getCalendarRdv() {
		return getParamRdvController().getCalendarRDV();
	}
	/**
	 * 
	 * @return jourSelected
	 */
	public Date getJourSelected() {
		return jourSelected;
	}
	/**
	 * 
	 * @param jourSelected
	 */
	public void setJourSelected(final Date jourSelected) {
		this.jourSelected = jourSelected;
	}
	
}
