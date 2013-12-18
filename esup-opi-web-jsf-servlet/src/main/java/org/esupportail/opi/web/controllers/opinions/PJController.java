package org.esupportail.opi.web.controllers.opinions;

import fj.F;
import fj.P1;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.esupportail.opi.utils.primefaces.MissingPieceDataModel;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.parameters.FormationContinue;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.*;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.controllers.references.CommissionController;
import org.esupportail.opi.web.utils.paginator.LazyDataModel;
import org.esupportail.opi.web.utils.paginator.PaginationFunctions;

import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static java.util.Arrays.asList;
import static org.esupportail.opi.domain.beans.etat.EtatVoeu.EtatArriveComplet;
import static org.esupportail.opi.domain.beans.etat.EtatVoeu.EtatArriveIncomplet;
import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.AccesSelectif;
import static org.esupportail.opi.domain.beans.parameters.TypeTraitement.ValidationAcquis;
import static org.esupportail.opi.web.utils.DTOs.commissionDTO;
import static org.esupportail.opi.web.utils.fj.Conversions.individuToPojo;
import static org.esupportail.opi.web.utils.paginator.LazyDataModel.lazyModel;

public class PJController extends AbstractContextAwareController {

    /**
     * serialization.
     */
    private static final long serialVersionUID = -4483704731784087003L;


    /**
     * The current CommissionPojo.
     */
    private CommissionPojo currentCmiPojo;

    /**
     * true if all missing piece are selected.
     */
    private Boolean allChecked;

    /**
     * Select all the state idem of stateSelected.
     */
    private String stateSelected;

    /**
     * Missing piece selected.
     */
    private MissingPiece[] missingPiece;
    
    /**
     * Missing piece selected.
     */
    private MissingPieceDataModel missingPieceModel;    

    /**
     * MissPiece just save.
     */
    private Set<MissingPiece> missPieceForInd;

    /**
     * see {@link ActionEnum}.
     */
    private ActionEnum actionEnum;

    /**
     * see {@link CommissionController}.
     */
    private CommissionController commissionController;


    /**
     * see {@link SmtpService}.
     */
    private SmtpService smtpService;

    /**
     * MissingPiecePojo selected.
     */
    private MissingPiecePojo mpPojoSelected;

    private final IndRechPojo indRechPojo = new IndRechPojo() {{
        setExcludeWishProcessed(true);
        setTypeTraitements(asList(AccesSelectif, ValidationAcquis));
    }};

    private final F<IndividuPojo, MissingPiecePojo> indPojoToMPPojo =
            new F<IndividuPojo, MissingPiecePojo>() {
                public MissingPiecePojo f(IndividuPojo individuPojo) {
                    MissingPiecePojo mp = new MissingPiecePojo();
                    mp.setIndividuPojo(individuPojo);
                    mp.initCommissions(
                            getParameterService(),
                            getI18nService(),
                            getDomainService(),
                            indRechPojo.getIdCmi());
                    return mp;
                }
            };

    private final LazyDataModel<MissingPiecePojo> missingPiecePojoLDM = lazyModel(
            PaginationFunctions.getData(
                    new P1<SessionController>() {
                        public SessionController _1() {
                            return getSessionController();
                        }
                    },
                    new P1<DomainService>() {
                        public DomainService _1() {
                            return getDomainService();
                        }
                    },
                    new P1<DomainApoService>() {
                        public DomainApoService _1() {
                            return getDomainApoService();
                        }
                    },
                    new P1<ParameterService>() {
                        public ParameterService _1() {
                            return getParameterService();
                        }
                    },
                    new P1<IndRechPojo>() {
                        public IndRechPojo _1() {
                            return indRechPojo;
                        }
                    }
            ),
            PaginationFunctions.findByRowKey)
            .map(individuToPojo(
                    new P1<DomainApoService>() {
                        public DomainApoService _1() {
                            return getDomainApoService();
                        }
                    },
                    new P1<ParameterService>() {
                        public ParameterService _1() {
                            return getParameterService();
                        }
                    }
            ).andThen(indPojoToMPPojo));

    private boolean renderTable = false;

    /**
     * Constructors.
     */
    public PJController() {}

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        allChecked = false;
        stateSelected = "";
        currentCmiPojo = null;
        missingPiece = new MissingPiece[0];
        actionEnum = new ActionEnum();
        missPieceForInd = new HashSet<>();
        missingPieceModel = new MissingPieceDataModel();
    }
    
    public void resetPm() {
    	actionEnum = new ActionEnum();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();
        Assert.notNull(this.smtpService,
                "property smtpService of class "
                        + this.getClass().getName() + " can not be null");
        Assert.notNull(this.commissionController,
                "property commissionController of class " + this.getClass().getName()
                        + " can not be null");
        reset();
    }

    /**
     * Callback to see the students in a commission for the PM treatment.
     *
     * @return String
     */
    @SuppressWarnings({"serial", "synthetic-access"})
    public String goSeePM() {
        reset();
        return NavigationRulesConst.DISPLAY_PIECE_MANQUANTE_STUDENTS;
    }

    /**
     * Callback to enter the States of PM for all the chose of an student.
     *
     * @return String
     */
    public String goSeePMEtudiant() {
        return NavigationRulesConst.ENTER_PIECE_MANQUANTE_STUDENT;
    }

    /**
     * Back to student list.
     *
     * @return String
     */
    public String goBackMPStudent() {
        reset();
        return NavigationRulesConst.DISPLAY_PIECE_MANQUANTE_STUDENTS;
    }

    /**
     * search Students.
     */
    @SuppressWarnings({"serial", "synthetic-access"})
    public void searchStudents() {
    	resetPm();
    }

    /**
     * items state for the wishes.
     */
    public List<SelectItem> getStateItems() {
        List<SelectItem> list = new ArrayList<SelectItem>();
        list.add(new SelectItem("STATE.NON_ARRIVE",
                getString("STATE.NON_ARRIVE")));
        list.add(new SelectItem("STATE.ARRIVE_COMPLET",
                getString("STATE.ARRIVE_COMPLET")));
        list.add(new SelectItem("STATE.ARRIVE_INCOMPLET",
                getString("STATE.ARRIVE_INCOMPLET")));
        return list;
    }

    /**
     * Change all the state for the elements
     * Display in the paginator.
     */
    public void changeStateAll() {
        List<MissingPiecePojo> allPojo = missingPiecePojoLDM.getData();
        for (MissingPiecePojo m : allPojo) {
            this.mpPojoSelected = m;
            if (m.getCommissions() != null) {
                for (CommissionPojo c : m.getCommissions().keySet()) {
                    this.currentCmiPojo = c;
                    if (!currentCmiPojo.getStateCurrent().equals(
                            currentCmiPojo.getStateCurrentOld())) {
                        changeState(false, m.getIndividuPojo());
                    }
                }
            }
        }
        addInfoMessage(null, "INFO.PM.ALL.SUCCESS");
        reset();
    }

    /**
     * change state for one individu.
     * from enterPM;
     */
    public void changeState() {
        changeState(true, mpPojoSelected.getIndividuPojo());
    }

    /**
     * Change the state for all wishes in one commission.
     */
    private void changeState(final Boolean trtUnit, final IndividuPojo pojoIndividu) {
        currentCmiPojo.setStateCurrentOld(currentCmiPojo.getStateCurrent());
        currentCmiPojo.setState(EtatVoeu.fromString(currentCmiPojo.getStateCurrent()));

        RegimeInscription regimeIns = getRegimeIns().get(
                Utilitaires.getCodeRIIndividu(pojoIndividu.getIndividu(),
                        getDomainService()));
        if (currentCmiPojo.getState() == EtatArriveIncomplet
                && trtUnit) {
            // Arrive Incomplet
            seeMissingPiecePrincipal();
        } else if (currentCmiPojo.getState() == EtatArriveComplet) {
            // Arrive Complet
            //delete all missing pieces
            Commission c = getParameterService()
                    .getCommission(currentCmiPojo.getCommission().getId(), null);
            currentCmiPojo.setCommission(c);
            List<MissingPiece> missP =
                    getDomainService().getMissingPiece(
                            pojoIndividu.getIndividu(), currentCmiPojo.getCommission());
            if (missP != null && !missP.isEmpty()) {
                getDomainService().deleteMissingPiece(missP, null);
            }

            //send Mail if FI ou FC && not(trtUnit)
            if (regimeIns instanceof FormationContinue && trtUnit) {
                actionEnum.setWhatAction(ActionEnum.SEND_MAIL);
            } else {
                sendMail(false);
            }


        }
        //update state all wishes in cmi.
        List<IndVoeuPojo> iList = this.mpPojoSelected.getCommissions().get(currentCmiPojo);

        for (IndVoeuPojo i : iList) {
            i.getIndVoeu().setState(currentCmiPojo.getStateCurrent());
            IndVoeu iV = (IndVoeu) getDomainService().update(i.getIndVoeu(), getCurrentGest().getLogin());
            getDomainService().updateIndVoeu(iV);
        }
        if (regimeIns instanceof FormationInitiale
                && !(currentCmiPojo.getState() == EtatArriveIncomplet)) {
            reset();
        }
    }

    /**
     * see the missing pieces if state = EtatArriveIncomplet.
     */
    public void seeMissingPiecePrincipal() {
        IndividuPojo pojoIndividu = this.mpPojoSelected.getIndividuPojo();
        if (currentCmiPojo.getState() == EtatArriveIncomplet) {
            List<MissingPiece> missP =
                    getDomainService().getMissingPiece(
                            pojoIndividu.getIndividu(), currentCmiPojo.getCommission());
            if (missP != null && !missP.isEmpty()) {
            	missingPiece = new MissingPiece[missP.size()];	
            	for(int i=0; i<missP.size(); i++) {
            		missingPiece[i] = missP.get(i);
            	}
            } else {
            	missingPiece = new MissingPiece[0];
            }
        } else {
            addInfoMessage(null, "MISSING_PIECE.NOT_EMPTY_STATE", getString("STATE.ARRIVE_INCOMPLET"));
        }
    }


    /**
     * Update the state in all the wishes of the commissions.
     */
    public void saveMissingPiecePrincipal() {
        List<MissingPiece> listMPToDelete = new ArrayList<MissingPiece>();
        if (allChecked) {
            missPieceForInd = new HashSet<MissingPiece>(
                    this.mpPojoSelected.getPiecesForCmi().get(currentCmiPojo.getCommission()));
        } else {
            //TODO probleme de lazy aleatoire a voir 01042009
            for (MissingPiece mp : missingPiece) {
                missPieceForInd.add(mp);
            }
            for (MissingPiece mp
                    : this.mpPojoSelected.getPiecesForCmi().get(currentCmiPojo.getCommission())) {
                //missing piece already in dataBase
                if (!mp.getId().equals(0)) {
                    if (!missPieceForInd.contains(mp)) {
                        //if not contains --> delete missingPiece
                        listMPToDelete.add(mp);
                    }
                }
            }
        }

        if (!missPieceForInd.isEmpty()) {
            getDomainService().saveOrUpdateMissingPiece(
                    new ArrayList<MissingPiece>(missPieceForInd),
                    getCurrentGest().getLogin());
        }

        //DELETE THE MP NOT SELECTED
        if (!listMPToDelete.isEmpty()) {
            getDomainService().deleteMissingPiece(listMPToDelete, null);
        }

        actionEnum.setWhatAction(ActionEnum.SEND_MAIL);
        addInfoMessage(null, "MISSING_PIECE.SAVE_OK");

    }


    /**
     * Change all state of visibleItems for stateSelected.
     */
    public void putStateAll() {
        for (MissingPiecePojo mPojo : missingPiecePojoLDM.getData()) {
            for (CommissionPojo c : mPojo.getCommissions().keySet()) {
                c.setStateCurrent(stateSelected);
            }
        }
    }

    /**
     * Send mail when confirm.
     */
    public void sendMail() {
        sendMail(true);
    }


    /**
     * send mail.
     */
    public void sendMail(final Boolean doReset) {
        //send Mail
        IndividuPojo pojoIndividu = this.mpPojoSelected.getIndividuPojo();

        RegimeInscription regimeIns = getRegimeIns().get(
                Utilitaires.getCodeRIIndividu(pojoIndividu.getIndividu(),
                        getDomainService()));
        if (currentCmiPojo.getState() == EtatArriveIncomplet) {
            if (regimeIns.getDossArriveIncomplet() != null) {
                currentCmiPojo.setAdressePojo(new AdressePojo(currentCmiPojo.getCommission()
                        .getContactsCommission()
                        .get(regimeIns.getCode()).getAdresse(), getDomainApoService()));
                currentCmiPojo.setContactCommission(currentCmiPojo.getCommission().
                        getContactsCommission().get(regimeIns.getCode()));
                List<Object> list = new ArrayList<>();
                list.add(pojoIndividu.getIndividu());
                list.add(this.mpPojoSelected.getCommissions().get(currentCmiPojo));
                list.add(missPieceForInd);
                list.add(commissionDTO(currentCmiPojo));

                regimeIns.getDossArriveIncomplet().send(pojoIndividu.getIndividu().getAdressMail(),
                        pojoIndividu.getIndividu().getEmailAnnuaire(), list);
            }

            addInfoMessage(null, "INFO.CANDIDAT.SEND_MAIL.MISSING_PIECE");
        } else if (currentCmiPojo.getState() == EtatArriveComplet) {
            if (regimeIns.getDossArriveComplet() != null) {
                currentCmiPojo.setAdressePojo(new AdressePojo(
                        currentCmiPojo.getCommission().getContactsCommission().
                                get(regimeIns.getCode()).getAdresse(), getDomainApoService()));
                currentCmiPojo.setContactCommission(currentCmiPojo.getCommission().
                        getContactsCommission().get(regimeIns.getCode()));
                List<Object> list = new ArrayList<>();
                list.add(pojoIndividu.getIndividu());
                list.add(this.mpPojoSelected.getCommissions().get(currentCmiPojo));
                list.add(commissionDTO(currentCmiPojo));

                regimeIns.getDossArriveComplet().send(pojoIndividu.getIndividu().getAdressMail(),
                        pojoIndividu.getIndividu().getEmailAnnuaire(), list);
            }
            addInfoMessage(null, "INFO.CANDIDAT.SEND_MAIL.STATE.FULL_ARRIVE",
                    pojoIndividu.getIndividu().getNumDossierOpi());

        }
        if (doReset) {
            reset();
        }

    }

    public CommissionPojo getCurrentCmiPojo() {
        return currentCmiPojo;
    }

    public void setCurrentCmiPojo(final CommissionPojo currentCmiPojo) {
        this.currentCmiPojo = currentCmiPojo;
    }

    public Boolean getAllChecked() {
        return allChecked;
    }

    public void setAllChecked(final Boolean allChecked) {
        this.allChecked = allChecked;
    }

    public MissingPiece[] getMissingPiece() {
        return missingPiece;
    }

    public void setMissingPiece(final MissingPiece[] missingPiece) {
    	this.missingPiece = missingPiece;
    }

    public MissingPieceDataModel getMissingPieceModel() {
    	missingPieceModel =
                new MissingPieceDataModel(this.mpPojoSelected.getPiecesForCmi().get(currentCmiPojo.getCommission()));
        return missingPieceModel;
    }

    public void setSmtpService(final SmtpService smtpService) {
        this.smtpService = smtpService;
    }

    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    public Set<MissingPiece> getMissPieceForInd() {
        return missPieceForInd;
    }

    public void setMissPieceForInd(final Set<MissingPiece> missPieceForInd) {
        this.missPieceForInd = missPieceForInd;
    }

    public void setCommissionController(final CommissionController commissionController) {
        this.commissionController = commissionController;
    }

    public MissingPiecePojo getMpPojoSelected() {
        return mpPojoSelected;
    }

    public void setMpPojoSelected(final MissingPiecePojo mpPojoSelected) {
        this.mpPojoSelected = mpPojoSelected;
    }

    public String getStateSelected() {
        return stateSelected;
    }

    public void setStateSelected(final String stateSelected) {
        this.stateSelected = stateSelected;
    }

    public LazyDataModel<MissingPiecePojo> getMissingPiecePojoLDM() {
        return missingPiecePojoLDM;
    }

    public IndRechPojo getIndRechPojo() {
        return indRechPojo;
    }

    public boolean isRenderTable() {
        return renderTable;
    }

    public void setRenderTable(boolean renderTable) {
        this.renderTable = renderTable;
    }

    public void doRenderTable() { renderTable = true; }
}
