package org.esupportail.opi.web.controllers.parameters;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
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
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.primefaces.model.DualListModel;

import fj.F;
import fj.Ord;
import fj.Ordering;

import static fj.data.Option.fromNull;
import static fj.data.List.iterableList;


/**
 * @author gomez
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
     * Primefaces picklist model to choose {@link Commission} objects.
     */
    private DualListModel<Commission> commissions;

    /**
     * Primefaces picklist model to choose {@link VersionEtapeDTO} objects.
     */
    private DualListModel<VersionEtapeDTO> vets;
	
	
	
	/*
	 ******************* INIT ************************* */

    /**
     * Controller.
     */
    public ParamRdvController() {
        listCalendarRdv = new ArrayList<CalendarRDVPojo>();
        allCge = new ArrayList<SelectItem>();

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

    }
	
	
	/*
	 ******************* CALLBACK ********************** */

    /**
     * Callback to calendar list.
     *
     * @return String
     */
    public String goSeeAllParamRdv() {
        reset();
        return NavigationRulesConst.SEE_PARAM_RDV;
    }

    /**
     * Callback to calendar add.
     *
     * @return String
     */
    public String goAddParamRdv() {
        reset();        
        getActionEnum().setWhatAction(ActionEnum.ADD_ACTION);
        return NavigationRulesConst.SEE_PARAM_RDV;
    }
	
	private final F<Commission, F<Commission, Ordering>> commissionOrdering = new F<Commission, F<Commission, Ordering>>() {
		public F<Commission, Ordering> f(final Commission a1) {
			return new F<Commission, Ordering>() {
				public Ordering f(final Commission a2) {
					final String lib1 = a1.getCode() + "(" + a1.getLibelle() + ")";
					final String lib2 = a2.getCode() + "(" + a2.getLibelle() + ")";
					final int x = lib1.compareToIgnoreCase(lib2);
					return x < 0 ? Ordering.LT : x == 0 ? Ordering.EQ : Ordering.GT;
				}
			};
		}
	};
	
	private final F<VersionEtapeDTO, F<VersionEtapeDTO, Ordering>> vetOrdering = new F<VersionEtapeDTO, F<VersionEtapeDTO, Ordering>>() {
		public F<VersionEtapeDTO, Ordering> f(final VersionEtapeDTO a1) {
			return new F<VersionEtapeDTO, Ordering>() {
				public Ordering f(final VersionEtapeDTO a2) {
					final String lib1 = a1.getCodEtp() + "(" + a1.getLibWebVet() + ")";
					final String lib2 = a2.getCodEtp() + "(" + a2.getLibWebVet() + ")";
					final int x = lib1.compareToIgnoreCase(lib2);
					return x < 0 ? Ordering.LT : x == 0 ? Ordering.EQ : Ordering.GT;
				}
			};
		}
	};
	
	private final F<Set<Commission>, fj.data.List<Commission>> toListCommission = new F<Set<Commission>, fj.data.List<Commission>>() {
		@Override
		public fj.data.List<Commission> f(Set<Commission> a) {
			return iterableList(a);
		}
	};
	
	/*
	 * ******************* ADD ET UPDATE ************************* */

    /**
     * Add a Domain to the dataBase.
     */
    public String add() {
        if (testErreurSave()) {
            return null;
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
            for (Commission comm : commissions.getTarget()) {
                getCalendarRDV().getCommissions().add(comm);
            }

        }

        getParameterService().addCalendarRdv(getCalendarRDV());

        if (isChoixVet()) {
        	for(VersionEtapeDTO vet : vets.getTarget()) {
				VetCalendar vetCal = new VetCalendar(vet.getCodEtp(),
						vet.getCodVrsVet(), 
						getCalendarRDV(), 
						getCommissionVet(vet.getCodEtp(), vet.getCodVrsVet()));
                getDomainService().addVetCalendar(vetCal);
                getCalendarRDV().getVets().add(vetCal);
            }
        }

        initCalendar();
        addInfoMessage(null, "INFO.ENTER.SUCCESS");
        
        return goSeeAllParamRdv();
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
            final Set<Commission> currentCmis = new HashSet<Commission>(
            		getCalendarRDV().getCommissions());
            final List<Commission> newCmis = commissions.getTarget();
            
            for (Commission commission : currentCmis) {
				if (!newCmis.contains(commission)) {
					getCalendarRDV().getCommissions().remove(commission);
				}
			}
            
            for (Commission commission : newCmis) {
				if (!currentCmis.contains(commission)) {
					getCalendarRDV().getCommissions().add(commission);
				}
			}
            getCalendarRDV().setCodeCge(null);
            getCalendarRDV().getVets().clear();

        } else {
            final fj.data.List<VetCalendar> currentVets = 
            		iterableList(getCalendarRDV().getVets());
            final fj.data.List<VetCalendar> newVets = 
            		iterableList(vets.getTarget())
            		.map(new F<VersionEtapeDTO, VetCalendar>() {
				@Override
				public VetCalendar f(VersionEtapeDTO vet) {
					return new VetCalendar(vet.getCodEtp(),
							vet.getCodVrsVet(), 
							getCalendarRDV(), 
							getCommissionVet(vet.getCodEtp(), vet.getCodVrsVet()));
				}
			});

            final Collection<VetCalendar> toAdd = newVets.filter(new F<VetCalendar, Boolean>() {
				@Override
				public Boolean f(VetCalendar vet) {
					for (VetCalendar vetCalendar : currentVets) {
						if (vetCalendar.getCodEtp().equals(vet.getCodEtp()) 
								&& vetCalendar.getCodVrsVet().equals(vet.getCodVrsVet())) {
							return false;
						}
					}
					return true;
				}
			}).toCollection();
            
            final Collection<VetCalendar> toDelete = currentVets.filter(new F<VetCalendar, Boolean>() {
				@Override
				public Boolean f(VetCalendar vet) {
					for (VetCalendar vetCalendar : newVets) {
						if (vetCalendar.getCodEtp().equals(vet.getCodEtp()) 
								&& vetCalendar.getCodVrsVet().equals(vet.getCodVrsVet())) {
							return false;
						}
					}
					return true;
				}
			}).toCollection();
            
            for (VetCalendar vetCalendar : toDelete) {
                getCalendarRDV().getVets().remove(vetCalendar);
                getDomainService().deleteVetCalendar(vetCalendar);
			}

            if (isChoixVet()) {
	            for (VetCalendar vetCalendar : toAdd) {
	                getDomainService().addVetCalendar(vetCalendar);
	                getCalendarRDV().getVets().add(vetCalendar);
				}
            }
            
            getCalendarRDV().setCodeCge(null);
            getCalendarRDV().getCommissions().clear();
        }

        getParameterService().updateCalendarRdv(getCalendarRDV());

        initCalendar();
        reset();

        if (log.isDebugEnabled()) {
            log.debug("leaving update");
        }
    }

    /**
     * Delete a fonction to the dataBase.
     */
    public String delete() {
        if (log.isDebugEnabled()) {
            log.debug("enterind delete with calendarRDV = " + getCalendarRDV().getTitre());
        }

        getListCalendarRdv().remove(getCalendarRDV());
        getParameterService().deleteCalendarRdv(getCalendarRDV());
        
        if (log.isDebugEnabled()) {
            log.debug("leaving delete");
        }
        
        return goSeeAllParamRdv();
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
        if (isChoixCommission() && (commissions.getTarget().isEmpty())) {
            addErrorMessage(FORMULAIRE_ADD_RDV, "ERROR.LIST.EMPTY", "Liste des commissions");
            return true;
        }

        if (isChoixVet() && (vets.getTarget().isEmpty())) {
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
	
	/*
	 ******************* METHODS VETS ********************** */

    /**
     * @param codEtp
     * @param codVrsVet
     * @return Commission
     */
    public Commission getCommissionVet(final String codEtp, final int codVrsVet) {
        for (Commission comm : getParameterService().getCommissions(null)) {
            for (TraitementCmi traitementCmi : comm.getTraitementCmi()) {
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
                for (Commission comm : calRdv.getCommissions()) {
                    Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
                    if (gest != null) {
                        int codeRI = gest.getProfile().getCodeRI();
                        CommissionPojo commissionPojo = new CommissionPojo(comm,
                                comm.getContactsCommission().get(codeRI));
                        calendarRDVPojo.getListCommissionPojo().add(commissionPojo);
                    }
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
     * @return allVets
     */
    public List<VersionEtapeDTO> getAllVets() {    	
        Set<Campagne> camps = getParameterService().getCampagnes(true,
                String.valueOf(getCurrentGest().getProfile().getCodeRI()));
        Set<VersionEtapeDTO> allVets = new HashSet<VersionEtapeDTO>();
        for (Campagne camp : camps) {
            allVets.addAll(getDomainApoService().getVersionEtapes(
                    null, null, getCurrentGest().getCodeCge(), camp.getCodAnu()));
        }

        fj.data.List<VersionEtapeDTO> result = fj.data.List.nil();
        if (!allVets.isEmpty()) {
            Set<Commission> cmi = getDomainApoService().getListCommissionsByRight(
                    getCurrentGest(),
                    true);

            Set<VersionEtapeDTO> allVets2 = new HashSet<VersionEtapeDTO>();

            for (VersionEtapeDTO versionEtapeDTO : allVets) {
                if (BusinessUtil.getCmiForVetDTO(cmi, versionEtapeDTO) != null) {
                    allVets2.add(versionEtapeDTO);
                }
            }
            allVets = allVets2;
            
            for (VersionEtapeDTO vet : allVets) {
                if (!testExistVetItems(vet.getCodEtp(), vet.getCodVrsVet())) {
                    result = result.snoc(vet);
                }
            }
        }
        return new ArrayList<VersionEtapeDTO>(
        		result.sort(Ord.ord(vetOrdering)).toCollection());
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

	/**
	 * @return the commissions
	 */
	public DualListModel<Commission> getCommissions() {
		final List<Commission> sourceCommissions = new ArrayList<Commission>(
				fromNull(getParameterService().getCommissions(null))
						.toList().bind(toListCommission)
						.sort(Ord.ord(commissionOrdering)).toCollection());
        commissions = new DualListModel<Commission>();
        commissions.setTarget(new ArrayList<Commission>(getCalendarRDV().getCommissions()));
        commissions.setSource(new ArrayList<Commission>(sourceCommissions));
        commissions.getSource().removeAll(commissions.getTarget());
		return commissions;
	}

	/**
	 * @param commissions the commissions to set
	 */
	public void setCommissions(final DualListModel<Commission> commissions) {
		this.commissions = commissions;
	}

	/**
	 * @return the vets
	 */
	public DualListModel<VersionEtapeDTO> getVets() {
        vets = new DualListModel<VersionEtapeDTO>();
        if (!getCalendarRDV().getVets().isEmpty()) {
        	List<VersionEtapeDTO> listVetDto = new ArrayList<VersionEtapeDTO>();
        	for (VetCalendar vetCalendar : getCalendarRDV().getVets()) {
				listVetDto.add(getDomainApoService()
						.getVersionEtape(vetCalendar.getCodEtp(),
								vetCalendar.getCodVrsVet()));
			}
	        vets.setTarget(listVetDto);
        }
        vets.setSource(getAllVets());
        vets.getSource().removeAll(vets.getTarget());
		return vets;
	}

	/**
	 * @param vets the vets to set
	 */
	public void setVets(final DualListModel<VersionEtapeDTO> vets) {
		this.vets = vets;
	}
    
}
