/**
 *
 */
package org.esupportail.opi.web.controllers.formation;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.formation.GrpTypDipCorresp;
import org.esupportail.opi.services.remote.client.IApogee;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.wssi.services.remote.TypDiplome;

import javax.faces.model.SelectItem;
import java.util.*;

/**
 * @author cleprous
 */
public class ParamGrpTypDipController extends AbstractAccessController {
/*
 * ******************* PROPERTIES STATIC ******************* */
    /**
     * attribute serialVersionUID
     */
    private static final long serialVersionUID = -4581392769340545286L;
    /**
     *
     */
    protected static final String[] DEFAULT_TEMOIN_VALUES = {"O", "N"};
    /**
     *
     */
    private static final String FORMULAIRE_GROUPE = "formAddGroupe";

/*
 * ******************* PROPERTIES ******************* */
    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());
    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum;
    /**
     * The first element of the list is the function to add.
     * The others elements are all function in dataBase.
     */
    private GrpTypDip groupe;
    /**
     * map du libele en fonction du codTpdEtb.
     */
    private Map<String, String> mapCodTpdEtbLib;
    /**
     * listGrpTypDip.
     */
    private List<GrpTypDip> listGrpTypDip;
    /**
     * temEnSveItems.
     */
    private List<SelectItem> temEnSveItems;
    /**
     * dipsItems.
     */
    private List<SelectItem> dipsItems;
    /**
     * allDipsItems.
     */
    private List<SelectItem> allDipsItems;
    /**
     * selectDipsDI.
     */
    private List<String> selectDipsDI;
    /**
     * selectDipsADI.
     */
    private List<String> selectDipsADI;

    private IApogee iApogee;
	

/*
 * ******************* CONSTRUCTOR ************************* */

    /**
     * Constructors.
     */
    public ParamGrpTypDipController() {
        super();
        temEnSveItems = new ArrayList<SelectItem>();
        selectDipsDI = new ArrayList<String>();
        selectDipsADI = new ArrayList<String>();
        mapCodTpdEtbLib = new HashMap<String, String>();
    }

/*
 * ******************* RESET ************************* */

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        groupe = null;
        actionEnum = null;
        dipsItems = null;
        allDipsItems = null;
        listGrpTypDip = null;
    }

/*
 * ******************* CALLBACK ************************* */

    /**
     * Callback to group list.
     *
     * @return String
     */
    public String goSeeAllGrp() {
        reset();
        return NavigationRulesConst.DISPLAY_GRP_TYP_DIP;
    }

    /**
     * Callback to add group.
     *
     * @return String
     */
    public String goAddGrp() {
        reset();
        getActionEnum().setWhatAction(ActionEnum.ADD_ACTION);
        return null;
    }

/*
 * ******************* ADD UPDATE DELETE ************************* */

    /**
     * Add a group to the dataBase.
     */
    @SuppressWarnings("unchecked")
    public void add() {
        if (testErreurSave()) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("enterind add with groupe = " + getGroupe());
        }

        for (SelectItem dipItem : dipsItems) {
            GrpTypDipCorresp dip = new GrpTypDipCorresp();
            dip.getGrpTpd().setCodGrpTpd(getGroupe().getCodGrpTpd());
            dip.setCodTpdEtb((String) dipItem.getValue());
            getGroupe().getGrpTypDipCorresps().add(dip);
        }

        getGrpTypDip().add(groupe);
        iApogee.save(getGroupe());
        Collections.sort(getGrpTypDip(), new BeanComparator("codGrpTpd", new NullComparator()));
        reset();

        addInfoMessage(null, "INFO.ENTER.SUCCESS");
        if (log.isDebugEnabled()) {
            log.debug("leaving add");
        }
    }

    /**
     * Update a fonction to the dataBase.
     */
    public void update() {
        if (testErreurUpdate()) {
            return;
        }

        if (log.isDebugEnabled()) {
            log.debug("enterind update with groupe = " + getGroupe());
        }

//		int index;
//		List<GrpTypDipCorresp> listDip = new ArrayList<GrpTypDipCorresp>();
//
//		for (GrpTypDipCorresp dip : getGroupe().getGrpTypDipCorresps()) {
//			index = getExistDipsItems(dip.getCodTpdEtb());
//			if (index == -1) {
//				listDip.add(dip);
//			} else {
//				dipsItems.remove(index);
//			}
//		}
//
//		for (GrpTypDipCorresp dip : listDip) {
//			getGroupe().getGrpTypDipCorresps().remove(dip);
//		}
        List<String> codesEtb = new ArrayList<String>();
        for (SelectItem dipItem : dipsItems) {
            codesEtb.add(String.valueOf(dipItem.getValue()));
        }

        iApogee.updateGrpTypDip(getGroupe(), codesEtb);
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
            log.debug("enterind delete with groupe = " + getGroupe());
        }

        getGrpTypDip().remove(groupe);
        iApogee.delete(getGroupe());
        reset();

        if (log.isDebugEnabled()) {
            log.debug("leaving delete");
        }
    }
	
/*
 * ******************* TEST ************************* */

    /**
     * @return boolean
     */
    private boolean testErreurSave() {
        if (getGroupe().getCodGrpTpd() == null || getGroupe().getCodGrpTpd().equals("")) {
            addErrorMessage(FORMULAIRE_GROUPE, "ERROR.FIELD.EMPTY", "Code");
            return true;
        }

        if (testGroupe()) {
            addErrorMessage(FORMULAIRE_GROUPE, "ERROR.FIELD.EXIST", "Groupe", "Code");
            return true;
        }

        return testErreurUpdate();
    }

    /**
     * @return boolean
     */
    private boolean testErreurUpdate() {
        if (getGroupe().getLibGrpTpd() == null || getGroupe().getLibGrpTpd().equals("")) {
            addErrorMessage(FORMULAIRE_GROUPE, "ERROR.FIELD.EMPTY", "Libelle");
            return true;
        }

        if (testLibelle()) {
            addErrorMessage(FORMULAIRE_GROUPE, "ERROR.FIELD.EXIST", "Groupe", "Libelle");
            return true;
        }

        if (dipsItems.isEmpty()) {
            addErrorMessage(FORMULAIRE_GROUPE, "ERROR.LIST.EMPTY", "Dipleme");
            return true;
        }

        return false;
    }

    /**
     * @return boolean
     */
    private boolean testGroupe() {
        String groupeCode = getGroupe().getCodGrpTpd();
        for (GrpTypDip g : getGrpTypDip()) {
            if (groupeCode.equals(g.getCodGrpTpd())) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return boolean
     */
    private boolean testLibelle() {
        String libelle = getGroupe().getLibGrpTpd();
        String groupeCode = getGroupe().getCodGrpTpd();
        for (GrpTypDip g : getGrpTypDip()) {
            if ((!groupeCode.equals(g.getCodGrpTpd())) && (libelle.equals(g.getLibGrpTpd()))) {
                return true;
            }
        }
        return false;
    }

/*
 * ******************* GETTERS ITEMS ************************* */

    /**
     * @return the list of temoins
     */
    public List<SelectItem> getTemEnSveItems() {
        if (temEnSveItems.isEmpty()) {
            for (String pageSizeValue : DEFAULT_TEMOIN_VALUES) {
                temEnSveItems.add(new SelectItem(pageSizeValue));
            }
        }
        return temEnSveItems;
    }

    /**
     * @return the list of diplemes
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getDipsItems() {
        if (dipsItems == null) {
            dipsItems = new ArrayList<SelectItem>();
            Set<GrpTypDipCorresp> listDiplomes = getGroupe().getGrpTypDipCorresps();
            if (listDiplomes != null) {
                for (GrpTypDipCorresp dip : listDiplomes) {
                    dipsItems.add(new SelectItem(dip.getCodTpdEtb(),
                            getLibDiplome(dip.getCodTpdEtb()) + " (" + dip.getCodTpdEtb() + ")",
                            dip.getGrpTpd().getCodGrpTpd()));
                }
            }
            Collections.sort(dipsItems, new BeanComparator("label", new NullComparator()));
        }
        return dipsItems;
    }

    /**
     * @return the list of diplemes
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getAllDipsItems() {
        if (allDipsItems == null) {
            allDipsItems = new ArrayList<SelectItem>();
            List<TypDiplome> allDiplomes = getAllTypDiplome();
            if (allDiplomes != null) {
                for (TypDiplome dip : allDiplomes) {
                    if (getExistDipsItems(dip.getCodTpdEtb()) == -1) {
                        allDipsItems.add(new SelectItem(dip.getCodTpdEtb(),
                                dip.getLibTpd() + " (" + dip.getCodTpdEtb() + ")",
                                dip.getLibTpd()));
                    }
                }
            }
            Collections.sort(allDipsItems, new BeanComparator("label", new NullComparator()));
        }
        return allDipsItems;
    }

    /**
     * Ajoute la selection dans DipsItems.
     */
    @SuppressWarnings("unchecked")
    public void ajouDipsItems() {
        int index = -1;
        for (String c : selectDipsADI) {
            for (int i = 0; i < allDipsItems.size() && index == -1; i++) {
                if (allDipsItems.get(i).getValue().equals(c)) {
                    index = i;
                }
            }
            if (index >= 0) {
                dipsItems.add(allDipsItems.get(index));
                allDipsItems.remove(index);
            }
            index = -1;
        }
        Collections.sort(dipsItems, new BeanComparator("label", new NullComparator()));
        Collections.sort(allDipsItems, new BeanComparator("label", new NullComparator()));
        selectDipsADI.clear();
    }

    /**
     * Supprime la selection dans DipsItems.
     */
    @SuppressWarnings("unchecked")
    public void suppDipsItems() {
        int index = -1;
        for (String c : selectDipsDI) {
            for (int i = 0; i < dipsItems.size() && index == -1; i++) {
                if (dipsItems.get(i).getValue().equals(c)) {
                    index = i;
                }
            }
            if (index >= 0) {
                allDipsItems.add(dipsItems.get(index));
                dipsItems.remove(index);
            }
            index = -1;
        }
        Collections.sort(dipsItems, new BeanComparator("value", new NullComparator()));
        Collections.sort(allDipsItems, new BeanComparator("value", new NullComparator()));
        selectDipsDI.clear();
    }

/*
 * ******************* OTHERS GETTERS ************************* */

    /**
     * @return integer
     */
    private int getExistDipsItems(final String dipCodeTpdEtb) {
        for (int i = 0; i < getDipsItems().size(); i++) {
            if (dipCodeTpdEtb.equals(String.valueOf(getDipsItems().get(i).getValue()))) {
                return i;
            }
        }
        return -1;
    }

    /**
     * @param codTpdEtb
     * @return libTpd
     */
    public String getLibDiplome(final String codTpdEtb) {
        for (TypDiplome dip : getAllTypDiplome()) {
            if (dip.getCodTpdEtb().equals(codTpdEtb)) {
                return dip.getLibTpd();
            }
        }
        return "";
    }

    /**
     * @return List(TypDiplome)
     */
    public List<TypDiplome> getAllTypDiplome() {
        return getDomainApoService().getAllTypeDiplomes();
    }
	
/*
 * ******************* GETTERS ************************* */

    /**
     * @return the actionEnum
     */
    public ActionEnum getActionEnum() {
        if (actionEnum == null) {
            actionEnum = new ActionEnum();
        }
        return actionEnum;
    }

    /**
     * @return the groupe
     */
    public GrpTypDip getGroupe() {
        if (groupe == null) {
            groupe = new GrpTypDip();
            groupe.setGrpTypDipCorresps(new HashSet<GrpTypDipCorresp>());
        }
        return groupe;
    }

    /**
     * @return mapCodTpdEtbLib
     */
    public Map<String, String> getMapCodTpdEtbLib() {
        if (mapCodTpdEtbLib.isEmpty()) {
            for (TypDiplome dip : getAllTypDiplome()) {
                mapCodTpdEtbLib.put(dip.getCodTpdEtb(), dip.getLibTpd());
            }
        }
        return mapCodTpdEtbLib;
    }

    /**
     * @return List(GrpTypDip)
     */
    @SuppressWarnings("unchecked")
    public List<GrpTypDip> getGrpTypDip() {
        if (listGrpTypDip == null) {
            listGrpTypDip = iApogee.getGrpTypDip(null);
            Collections.sort(listGrpTypDip, new BeanComparator("codGrpTpd", new NullComparator()));
        }
        return listGrpTypDip;
    }

    /**
     * @return list of the String
     */
    public List<String> getSelectDipsDI() {
        return selectDipsDI;
    }

    /**
     * @return list of the String
     */
    public List<String> getSelectDipsADI() {
        return selectDipsADI;
    }

/*
 * ******************* SETTERS ************************* */

    /**
     * @param actionEnum the actionEnum to set
     */
    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    /**
     * @param groupe the groupe to set
     */
    public void setGroupe(final GrpTypDip groupe) {
        this.groupe = groupe;
    }

    /**
     * @param selectDipsDI
     */
    public void setSelectDipsDI(final List<String> selectDipsDI) {
        this.selectDipsDI = selectDipsDI;
    }

    /**
     * @param selectDipsADI
     */
    public void setSelectDipsADI(final List<String> selectDipsADI) {
        this.selectDipsADI = selectDipsADI;
    }

    /**
     * @param iApogee the iApogee to set
     */
    public void setIApogee(final IApogee iApogee) {
        this.iApogee = iApogee;
    }
}
