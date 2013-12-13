package org.esupportail.opi.web.controllers.formation;


import org.apache.myfaces.custom.schedule.model.ScheduleModel;
import org.apache.myfaces.custom.schedule.model.SimpleScheduleModel;
import org.esupportail.commons.annotations.cache.RequestCache;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.components.ExtendedEntry;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuDatePojo;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import javax.faces.event.ActionEvent;
import javax.mail.internet.InternetAddress;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * @author gomez
 */
public class SaisieRdvEtuController extends AbstractAccessController {
    /**
     *
     */
    private static final long serialVersionUID = 5728381671187891309L;
    /**
     *
     */
    private static final String FORMULAIRE_SAISIE_RDV = "saisieRdvForm";
    /**
     *
     */
    private static final int NB_MS_BY_HALF_HOUR = 1800000;
    /**
     *
     */
    private static final int DOUZE = 12;
    /**
     *
     */
    private static final int ZERO = 0;
    /**
     * Constante Trente.
     */
    private static final int TRENTE = 30;
    /**
     * Format pour l'heure.
     */
    private SimpleDateFormat heureFormat = new SimpleDateFormat(Constantes.HOUR_FORMAT);
    /**
     *
     */
    private int startHour = DOUZE;
    /**
     *
     */
    private int endHour = DOUZE;

	
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
     *
     */
    private CalendarRDV calendarRdv;
    /**
     * Calendar.
     */
    private Calendar calendar;
    /**
     * Schedule Model.
     */
    private ScheduleModel weekScheduleModel;
    /**
     * Date de rendez-vous selectionnee.
     */
    private Date dateSelected;
    /**
     * autorizedDate.
     */
    private Boolean autorizedDate;
    /**
     * voeu selected.
     */
    private IndVoeuPojo voeu;
    /**
     * list des rendez vous de l'individu.
     */
    private Map<Date, List<IndividuDatePojo>> allRdvEtu;
    /**
     * see {@link SmtpService}.
     */
    private SmtpService smtpService;
	
	
	
	/*
	 ******************* INIT ************************* */

    /**
     * Controller.
     */
    public SaisieRdvEtuController() {
        if (log.isDebugEnabled()) {
            log.debug("Dans SaisieRdvEtuController");
        }
        calendar = new GregorianCalendar();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        actionEnum = null;
        dateSelected = null;
        autorizedDate = null;
        weekScheduleModel = null;
    }
	
	
	/*
	 ******************* CALLBACK ********************** */

    /**
     * Callback to rdv list.
     *
     * @return String
     */
    public String goSeeAllRdvEtu() {
        reset();
        return NavigationRulesConst.SEE_ALL_RDV_ETU;
    }

    /**
     * Callback to rdv add.
     *
     * @return String
     */
    public String goAddRdvEtu() {
        reset();
        initWeekScheduleModel();
        return NavigationRulesConst.ADD_RDV_ETU;
    }
	
	
	
	/*
	 ******************* METHODS VALIDER ********************** */

    /**
     * Valider le rendez-vous.
     *
     * @return String
     */
    public String validerRdv() {
        if (log.isDebugEnabled()) {
            log.debug("entering validerRdv");
        }

        //mise e jours de la list des rendez-vous
        getDomainService().initOneProxyHib(calendarRdv, calendarRdv.getCandidats(), IndividuDate.class);
//		getDomainService().refreshCalendarRdv(calendarRdv);

        //calcul du nombre de place libre
        int nbPlaceLibre = calendarRdv.getNbreMaxEtud();
        if (calendarRdv.getCandidatsAsMap().containsKey(dateSelected)) {
            nbPlaceLibre = nbPlaceLibre - calendarRdv.getCandidatsAsMap().
                    get(dateSelected).size();
        }

        //Teste s'il y a une place dans le crenaux selectionner.
        if (nbPlaceLibre <= 0) {
            //La derniere place vient d'etre prise.
            addErrorMessage(FORMULAIRE_SAISIE_RDV, "ERROR.FIELD.PLACE_DEJA_PRISE",
                    heureFormat.format(dateSelected));

            actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
            return null;
        }

        //Enregistrement du nouveau rendez-vous
        IndividuDate individuDate = new IndividuDate(
                getCurrentInd().getIndividu(),
                dateSelected,
                calendarRdv,
                voeu.getIndVoeu());

        getDomainService().addIndividuDate(individuDate);

        //Mise à jour du calendrier de rendez-vous
        if (calendarRdv.getCandidats() == null) {
            calendarRdv.setCandidats(new ArrayList<IndividuDate>());
        }

        calendarRdv.getCandidats().add(individuDate);
        //rafraichissement de la map
        calendarRdv.resetMap();

        //Envoie du mail de confirmation
        sendMailRendezVous();

        return NavigationRulesConst.ACCUEIL_CANDIDAT;
    }

    /**
     * Send the mail de convocation e une commission e tous les membres.
     */
    private void sendMailRendezVous() {
        //Recuperation de l'adresses mail du candidat
        String addr = getCurrentInd().getIndividu().getAdressMail();
        InternetAddress to = new InternetAddress();
        to.setAddress(addr);

        //Construction du mail
        //Edition du sujet
        String subject = getString("MAIL.RENDEZ_VOUS.SUBJECT", voeu.getVrsEtape().getLibWebVet());
        //Edition du corps du mail
        String htmlBody = getString("MAIL.NOT_RESPONSE");
        //Recuperation des differentes dates
        htmlBody += getString("MAIL.RENDEZ_VOUS.DATE",
                Utilitaires.convertDateToString(dateSelected, Constantes.DATE_FORMAT),
                Utilitaires.convertDateToString(dateSelected, Constantes.HOUR_FORMAT));
        //Recuperation du message de confirmation
        htmlBody += getString(calendarRdv.getMsgMailConfirmation());

        //Envoi du mail
        smtpService.send(to, subject, htmlBody, null);
    }
	
	
	/*
	 ******************* METHODS DE TEST ********************** */

    /**
     * @return boolean
     */
    private boolean isDateValid() {
        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, getCalendarRdv().getNbJoursAvantPro());
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, ZERO);
        cal.set(Calendar.MINUTE, ZERO);
        cal.set(Calendar.SECOND, ZERO);
        cal.set(Calendar.MILLISECOND, ZERO);

        if (recupDateInd() != null && recupDateInd().after(cal.getTime())) {
            return true;
        }

        return false;
    }

    /**
     * @return boolean
     */
    private boolean indivExistInCalendar() {
        if (getCalendarRdv() != null) {
            for (IndividuDate individuDate : getCalendarRdv().getCandidats()) {
                if (individuDate.getCandidat().equals(getCurrentInd().getIndividu())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param date
     * @return boolean
     */
    @RequestCache
    private boolean isPossedeRendezVous(final Date date) {
        return getAllRdvEtu().containsKey(date);
    }
	
	
	
	/*
	 ******************* METHODS DU CALENDRIER ***************** */

    /**
     * Action clicked on schedule day.
     */
    public void scheduleAction() {
        if (log.isDebugEnabled()) {
            log.debug("Dans scheduleAction");
        }

        if (weekScheduleModel.isEntrySelected()) {
            Date selectedDate = weekScheduleModel.getSelectedEntry().getStartTime();
            Calendar cal = new GregorianCalendar();
            cal.add(Calendar.DATE, getCalendarRdv().getNbJoursAvantPro());
            cal.set(Calendar.AM_PM, Calendar.AM);
            cal.set(Calendar.HOUR, ZERO);
            cal.set(Calendar.MINUTE, ZERO);
            cal.set(Calendar.SECOND, ZERO);
            cal.set(Calendar.MILLISECOND, ZERO);

            if (selectedDate.before(cal.getTime())) {
                //Le delai de cette date est depasse.
                addErrorMessage(FORMULAIRE_SAISIE_RDV, "ERROR.FIELD.DELAI_DEPASSE",
                        heureFormat.format(selectedDate));
            } else if (calendarRdv.getCandidatsAsMap().containsKey(selectedDate)
                    && (calendarRdv.getNbreMaxEtud() - calendarRdv.getCandidatsAsMap().
                    get(selectedDate).size()) <= 0) {
                //Il n'y a pas de place e cette date.
                addErrorMessage(FORMULAIRE_SAISIE_RDV, "ERROR.FIELD.PAS_DE_PLACE",
                        heureFormat.format(selectedDate));
            } else {
                calendar.clear();
                calendar.setTime(transformHour(selectedDate));
                dateSelected = calendar.getTime();
            }
        }
    }

    /**
     * @param date
     * @return date transformed
     */
    public Date transformHour(final Date date) {
        calendar.clear();
        calendar.setTime(date);

        if (calendar.get(Calendar.MINUTE) < TRENTE) {
            calendar.set(Calendar.MINUTE, ZERO);
        } else {
            calendar.set(Calendar.MINUTE, TRENTE);
        }
        calendar.set(Calendar.SECOND, ZERO);
        calendar.set(Calendar.MILLISECOND, ZERO);
        return calendar.getTime();
    }

    /**
     * construct the weekScheduleModel.
     */
    public void initWeekScheduleModel() {
        if (weekScheduleModel == null) {
            if (log.isDebugEnabled()) {
                log.debug("Dans initWeekScheduleModel");
            }


            if (getCalendarRdv() != null) {
                weekScheduleModel = new SimpleScheduleModel();
                Calendar now = new GregorianCalendar();
                now.setTime(new Date());
                now.add(Calendar.DATE, getCalendarRdv().getNbJoursAvantPro());
                if (now.getTime().before(calendarRdv.getDateDebutInsc())) {
                    now.setTime(calendarRdv.getDateDebutInsc());
                }
                weekScheduleModel.setSelectedDate(now.getTime());
                weekScheduleModel.setMode(ScheduleModel.WORKWEEK);

                calendar.clear();
                calendar.setTime(weekScheduleModel.getSelectedDate());

                List<Date> datesDisponibles = calendarRdv.getDatesRdv(false);
                if (!datesDisponibles.isEmpty()) {
                    int nbPlaceLibre = 0;
                    Calendar calDebut = new GregorianCalendar();
                    Calendar calFin = new GregorianCalendar();
                    for (Date date : datesDisponibles) {
                        calDebut.setTime(date);
                        calFin.setTimeInMillis(calDebut.getTimeInMillis() + NB_MS_BY_HALF_HOUR);

                        if (startHour > calDebut.get(Calendar.HOUR_OF_DAY)) {
                            startHour = calDebut.get(Calendar.HOUR_OF_DAY);
                        } else if (endHour < calFin.get(Calendar.HOUR_OF_DAY)) {
                            endHour = calFin.get(Calendar.HOUR_OF_DAY);
                        }

                        nbPlaceLibre = calendarRdv.getCountNbPlaceLibre(calDebut.getTime());

                        weekScheduleModel.addEntry(createWeekEntry(calDebut, calFin,
                                nbPlaceLibre));
                    }
                    weekScheduleModel.refresh();
                }
            } else {
                // aucun calendrier de rendez vous n'a été crée
                addInfoMessage(null, "CALENDAR_RDV.NO_CALENDAR");
            }
        }
    }

    /**
     * @param calDebut
     * @param calFin
     * @param nbPlaceLibre
     * @return l'entry pour la semaine
     */
    private ExtendedEntry createWeekEntry(final Calendar calDebut,
                                          final Calendar calFin, final int nbPlaceLibre) {
        ExtendedEntry entry = new ExtendedEntry();
        entry.setId(Long.toString(calDebut.getTimeInMillis()));
        entry.setStartTime(calDebut.getTime());
        entry.setEndTime(calFin.getTime());

        Calendar cal = new GregorianCalendar();
        cal.add(Calendar.DATE, getCalendarRdv().getNbJoursAvantPro());
        cal.set(Calendar.AM_PM, Calendar.AM);
        cal.set(Calendar.HOUR, ZERO);
        cal.set(Calendar.MINUTE, ZERO);
        cal.set(Calendar.SECOND, ZERO);
        cal.set(Calendar.MILLISECOND, ZERO);

        boolean isPossedeRendezVous = isPossedeRendezVous(calDebut.getTime());

        if (nbPlaceLibre > 1 && calDebut.getTime().after(cal.getTime())
                && !isPossedeRendezVous) {
            //plusieurs places de libres
            entry.setDescription(getString("CALENDAR_RDV.SCHEDULER.SELECT"));
            entry.setColor("#b2ff88");
            entry.setTitle(getString("CALENDAR_RDV.SCHEDULER.MANY_PLACES",
                    nbPlaceLibre, heureFormat.format(calDebut.getTime().getTime())));
        } else if (nbPlaceLibre == 1 && calDebut.getTime().after(cal.getTime())
                && !isPossedeRendezVous) {
            //1 place de libre
            entry.setDescription(getString("CALENDAR_RDV.SCHEDULER.SELECT"));
            entry.setColor("#b2ff88");
            entry.setTitle(getString("CALENDAR_RDV.SCHEDULER.ONE_PLACE",
                    heureFormat.format(calDebut.getTime())));
        } else if (nbPlaceLibre < 1 && calDebut.getTime().after(cal.getTime())
                && !isPossedeRendezVous) {
            //aucune place disponible
            entry.setDescription(getString("CALENDAR_RDV.SCHEDULER.NOT_SELECT"));
            entry.setColor("#ff8e8e");
            entry.setTitle(getString("CALENDAR_RDV.SCHEDULER.NO_PLACE",
                    heureFormat.format(calDebut.getTime())));
        } else if (isPossedeRendezVous) {
            //delai depasse
            entry.setDescription(getString("CALENDAR_RDV.SCHEDULER.ALREADY_SELECT"));
            entry.setColor("#ff8e8e");
            entry.setTitle(getString("CALENDAR_RDV.SCHEDULER.ALREADY_RDV"));
        } else {
            //delai depasse
            entry.setDescription(getString("CALENDAR_RDV.SCHEDULER.NOT_SELECT"));
            entry.setColor("#ff8e8e");
            entry.setTitle(getString("CALENDAR_RDV.SCHEDULER.OUT_TIME"));
        }

        return entry;
    }

    /**
     * Listener link before (Month/WorkWeek/Day).
     *
     * @param event
     */
    public void before(final ActionEvent event) {
        calendar.clear();
        calendar.setTime(weekScheduleModel.getSelectedDate());

        if (weekScheduleModel.getMode() == ScheduleModel.WORKWEEK) {
            calendar.add(Calendar.WEEK_OF_MONTH, -1);
            weekScheduleModel.setSelectedDate(calendar.getTime());
            weekScheduleModel.refresh();
        }
    }

    /**
     * Listener link next (Month/WorkWeek/Day).
     *
     * @param event
     */
    public void next(final ActionEvent event) {
        calendar.clear();
        calendar.setTime(weekScheduleModel.getSelectedDate());

        if (weekScheduleModel.getMode() == ScheduleModel.WORKWEEK) {
            calendar.add(Calendar.WEEK_OF_MONTH, 1);
            weekScheduleModel.setSelectedDate(calendar.getTime());
            weekScheduleModel.refresh();
        }
    }
	
	
	
	/*
	 ******************* METHODS ***************** */

    /**
     * @return Date
     */
    private Date recupDateInd() {
        if (getCalendarRdv() != null) {
            for (IndividuDate individuDate : getCalendarRdv().getCandidats()) {
                if (individuDate.getCandidat().equals(getCurrentInd().getIndividu())) {
                    return individuDate.getDateRdv();
                }
            }
        }

        return null;
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
     * @return dateSelected
     */
    public Date getDateSelected() {
        return dateSelected;
    }

    /**
     * @param dateSelected
     */
    public void setDateSelected(final Date dateSelected) {
        this.dateSelected = dateSelected;
    }

    /**
     * teste si le rendez-vous a deja ete passer pour ce cge ou pour cette commission.
     *
     * @return autorizedDate
     */
    public boolean getAutorizedDate() {
        if (autorizedDate == null) {
            autorizedDate = true;
            //teste si le candidat a deja pris un rendez-vous sur ce calendrier
            if (indivExistInCalendar()) {
                dateSelected = recupDateInd();
                //Teste si la date est valide
                //si elle est valide on oblige le candidat a prendre le rendez-vous a la meme date
                if (isDateValid()) {
                    autorizedDate = false;
                }
            }
        }
        return autorizedDate;
    }

    /**
     * @return voeu
     */
    public IndVoeuPojo getVoeu() {
        return voeu;
    }

    /**
     * @param voeu
     */
    public void setVoeu(final IndVoeuPojo voeu) {
        this.voeu = voeu;
    }

    /**
     * @return schduleModel
     */
    public ScheduleModel getWeekScheduleModel() {
        if (weekScheduleModel == null) {
            initWeekScheduleModel();
        }
        return weekScheduleModel;
    }

    /**
     * @return the startHour
     */
    public int getStartHour() {
        return startHour;
    }

    /**
     * @return the endHour
     */
    public int getEndHour() {
        return endHour;
    }

    /**
     * @return calendarRDV
     */
    public CalendarRDV getCalendarRdv() {
        if (calendarRdv == null) {
            calendarRdv = Utilitaires.getRecupCalendarRdv(voeu.getIndVoeu(), getParameterService().getCalendarRdv());
        }

        return calendarRdv;
    }

    /**
     * @param smtpService the smtpService to set
     */
    public void setSmtpService(final SmtpService smtpService) {
        this.smtpService = smtpService;
    }

    /**
     * @return liste de date.
     */
    @RequestCache
    public Map<Date, List<IndividuDatePojo>> getAllRdvEtu() {
        allRdvEtu = new HashMap<Date, List<IndividuDatePojo>>();
        //can be null when it's the first connect for an individu (in ent)
        if (getCurrentInd() != null) {
            for (CalendarRDV calRdv : getParameterService().getCalendarRdv()) {
                for (IndividuDate indivDate : calRdv.getCandidats()) {
                    if (indivDate.getCandidat().equals(getCurrentInd().getIndividu())) {
                        IndividuDatePojo indDatePojo = new IndividuDatePojo();
                        indDatePojo.setDateRdv(indivDate.getDateRdv());
                        indDatePojo.setMessageRdv(calRdv.getMsgValidation());
                        IndVoeu indVoeu = indivDate.getVoeu();
                        getDomainService().initOneProxyHib(indVoeu.getLinkTrtCmiCamp(),
                                indVoeu.getLinkTrtCmiCamp().getTraitementCmi(), TraitementCmi.class);
                        TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
                        VersionEtapeDTO vet = getDomainApoService().getVersionEtape(
                                trtCmi.getVersionEtpOpi().getCodEtp(),
                                trtCmi.getVersionEtpOpi().getCodVrsVet());
                        indDatePojo.setVoeuRdv(new IndVoeuPojo(
                                indVoeu,
                                vet,
                                EtatVoeu.fromString(indVoeu.getState()),
                                false,
                                null,
                                Utilitaires.getRecupCalendarRdv(
                                        indVoeu,
                                        getParameterService().getCalendarRdv())));
                        if (allRdvEtu.containsKey(indDatePojo.getDateRdv())) {
                            allRdvEtu.get(indDatePojo.getDateRdv()).add(indDatePojo);
                        } else {
                            List<IndividuDatePojo> listIndDatePojo = new ArrayList<>();
                            listIndDatePojo.add(indDatePojo);
                            allRdvEtu.put(indDatePojo.getDateRdv(), listIndDatePojo);
                        }
                    }
                }
            }
        }
        return allRdvEtu;
    }

    /**
     * @return Set
     */
    public Set<Date> getKeyMapAllRdvEtu() {
        return getAllRdvEtu().keySet();
    }
}
