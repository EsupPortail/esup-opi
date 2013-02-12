package org.esupportail.opi.web.controllers.pilotage;


import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.PilotageService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.pilotage.TypeCoordonneStatistique;
import org.esupportail.opi.domain.beans.utils.CoordonneStatistique;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorSelectItem;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;

import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.model.SelectItem;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;


/**
 * @author cgomez
 */
public class ParametreStatController extends AbstractContextAwareController {
    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -7253677346101562229L;

    /**
     *
     */
    private static final String FORMULAIRE_STAT = "seeParamStatForm";

    /**
     *
     */
    private static final String LABEL = "label";

    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

	
	
	
	/*
	 ******************* PROPERTIES ******************* */
    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum;

    /**
     *
     */
    private ActionEnum addUpdateCoordonne;

    /**
     * pilotageService.
     */
    private PilotageService pilotageService;

    /**
     * selectTypeAbscisse.
     */
    private String libelleCoordonne;
	
	/*
	 ******************* PROPERTIES ORDONNE ******************* */
    /**
     * allTypeOrdonne.
     */
    private List<TypeCoordonneStatistique> allTypeOrdonne;
    /**
     * allAbscisseItems.
     */
    private List<SelectItem> allTypeOrdonneItems;

    /**
     * selectTypeOrdonne.
     */
    private String selectTypeOrdonne;


    /**
     * allOrdonneItems.
     */
    private List<CoordonneStatistique> listOrdonneCoordonne;
    /**
     * allOrdonneItems.
     */
    private String selectOrdonneCoordonne;


    /**
     * allOrdonneItems.
     */
    private List<SelectItem> allObjOrdonneItems;
    /**
     * ordonneItems.
     */
    private List<SelectItem> objOrdonneItems;
    /**
     * selectOrdonneDel.
     */
    private List<String> selectObjOrdonneDel;
    /**
     * selectOrdonneAdd.
     */
    private List<String> selectObjOrdonneAdd;
	
	
	/*
	 ******************* PROPERTIES ABSCISSE ******************* */
    /**
     * allTypeAbscisse.
     */
    private List<TypeCoordonneStatistique> allTypeAbscisse;
    /**
     * allAbscisseItems.
     */
    private List<SelectItem> allTypeAbscisseItems;
    /**
     * selectTypeAbscisse.
     */
    private String selectTypeAbscisse;


    /**
     * allOrdonneItems.
     */
    private List<CoordonneStatistique> listAbscisseCoordonne;
    /**
     * allOrdonneItems.
     */
    private String selectAbscisseCoordonne;


    /**
     * allAbscisseItems.
     */
    private List<SelectItem> allObjAbscisseItems;
    /**
     * abscisseItems.
     */
    private List<SelectItem> objAbscisseItems;
    /**
     * selectAbscisseDel.
     */
    private List<String> selectObjAbscisseDel;
    /**
     * selectAbscisseAdd.
     */
    private List<String> selectObjAbscisseAdd;

	
	/*
	 ******************* PROPERTIES CAMPAGNE ******************* */
    /**
     * allCampagneItems.
     */
    private List<SelectItem> allCampagneItems;
    /**
     * campagneItems.
     */
    private List<SelectItem> campagneItems;
    /**
     * selectCampagneDel.
     */
    private List<String> selectCampagneDel;
    /**
     * selectCampagneAdd.
     */
    private List<String> selectCampagneAdd;
	
	
	/*
	 ******************* INIT ************************* */

    /**
     * Constructors.
     */
    public ParametreStatController() {
        super();
        if (log.isDebugEnabled()) {
            log.debug("Constructor ParametreStatController");
        }
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        //
        actionEnum = new ActionEnum();
        addUpdateCoordonne = new ActionEnum();

        allTypeOrdonneItems = new ArrayList<SelectItem>();
        selectTypeOrdonne = null;

        allTypeAbscisseItems = new ArrayList<SelectItem>();
        selectTypeAbscisse = null;

        allObjOrdonneItems = null;
        objOrdonneItems = new ArrayList<SelectItem>();
        selectObjOrdonneDel = new ArrayList<String>();
        selectObjOrdonneAdd = new ArrayList<String>();

        listOrdonneCoordonne = new ArrayList<CoordonneStatistique>();
        selectOrdonneCoordonne = null;

        allObjAbscisseItems = null;
        objAbscisseItems = new ArrayList<SelectItem>();
        selectObjAbscisseDel = new ArrayList<String>();
        selectObjAbscisseAdd = new ArrayList<String>();

        listAbscisseCoordonne = new ArrayList<CoordonneStatistique>();
        selectAbscisseCoordonne = null;

        allCampagneItems = null;
        campagneItems = new ArrayList<SelectItem>();
        selectCampagneDel = new ArrayList<String>();
        selectCampagneAdd = new ArrayList<String>();
    }


    /**
     * @see org.esupportail.opi.web.controllers.AbstractContextAwareController
     *      #afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();
        Assert.notNull(this.pilotageService, "property pilotageService of class "
                + this.getClass().getName() + " can not be null");
        Assert.notNull(this.allTypeOrdonne, "property allTypeOrdonne of class "
                + this.getClass().getName() + " can not be null");
        Assert.notNull(this.allTypeAbscisse, "property allTypeAbscisse of class "
                + this.getClass().getName() + " can not be null");
        Assert.notEmpty(this.allTypeOrdonne, "property allTypeOrdonne of class "
                + this.getClass().getName() + " can not be emtpy");
        Assert.notEmpty(this.allTypeAbscisse, "property allTypeAbscisse of class "
                + this.getClass().getName() + " can not be emtpy");

    }
	/*
	 ******************* CALLBACK ********************** */

    /**
     * Callback to list of parameters.
     *
     * @return String
     */
    public String goSeeParamStat() {
        reset();
        return NavigationRulesConst.SEE_PARAM_STAT;
    }

    /**
     * Callback to list of parameters.
     *
     * @return String
     */
    public String goReturnSeeParamStat() {
        return NavigationRulesConst.SEE_PARAM_STAT;
    }


    //////////////////////////////////////////////////////////////
    // ORDONNEE
    //////////////////////////////////////////////////////////////
	/*
	 ******************* ADD, UPDATE, DEL (ORDONNEE) ********************** */

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public void addOrdonneCoordonne() {
        if (objOrdonneItems == null || objOrdonneItems.isEmpty()) {
            addErrorMessage(FORMULAIRE_STAT, "ERROR.LIST.EMPTY", "Ordonnée");
            return;
        }

        //Récupération du type de classe des objets sélectionnés
        TypeCoordonneStatistique typeCoordonne = null;
        for (TypeCoordonneStatistique type : allTypeOrdonne) {
            if (type.getTypeClass().getName().equals(getSelectTypeOrdonne())) {
                typeCoordonne = type;
                break;
            }
        }

        //Récupération des objets
        List<Object> list = new ArrayList<Object>();
        TypeCoordonneStatistique cordonne = getCoordonneOrdonne(getSelectTypeOrdonne());
        List listCoordonne = pilotageService.getAllCoordonne(cordonne.getTypeClass(), cordonne.getMethode());
        for (SelectItem items : objOrdonneItems) {
            for (Object obj : listCoordonne) {
                if (items.getValue().equals(cordonne.getRecupIdObject(obj))) {
                    list.add(obj);
                    break;
                }
            }
        }

        //Test s'il y a une autre coordonnée avec les mêmes objets
        for (CoordonneStatistique coordonneStat : listOrdonneCoordonne) {
            if (coordonneStat.getListeObject().size() == list.size()
                    && coordonneStat.getListeObject().containsAll(list)) {
                addErrorMessage(FORMULAIRE_STAT, "ERROR.FIELD.EXIST", "Coordonnée", "ordonnées");
                return;
            }
        }

        //création de la nouvelle coordonnée
        listOrdonneCoordonne.add(new CoordonneStatistique(typeCoordonne, list, libelleCoordonne));
        clearObjOrdonne();
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public void updateOrdonneCoordonne() {
        if (objOrdonneItems == null || objOrdonneItems.isEmpty()) {
            addErrorMessage(FORMULAIRE_STAT, "ERROR.LIST.EMPTY", "Ordonnée");
            return;
        }

        //Récupération des nouveaux objets
        List<Object> list = new ArrayList<Object>();
        TypeCoordonneStatistique cordonne = getCoordonneOrdonne(getSelectTypeOrdonne());
        List listCoordonne = pilotageService.getAllCoordonne(cordonne.getTypeClass(), cordonne.getMethode());
        for (SelectItem items : objOrdonneItems) {
            for (Object obj : listCoordonne) {
                if (items.getValue().equals(cordonne.getRecupIdObject(obj))) {
                    list.add(obj);
                    break;
                }
            }
        }

        //Récupération de l'objet CoordonneStatistique
        CoordonneStatistique coordonne = null;
        for (CoordonneStatistique coordonneStat : listOrdonneCoordonne) {
            if (selectOrdonneCoordonne.equals(coordonneStat.getId())) {
                coordonne = coordonneStat;
                break;
            }
        }
        if (coordonne == null) {
            log.debug("l'objet CoordonneStatistique sélectionné n'a pas été trouvé");
            return;
        }

        //Test s'il y a une autre coordonnée avec les mêmes objets
        for (CoordonneStatistique coordonneStat : listOrdonneCoordonne) {
            if (!coordonne.getId().equals(coordonneStat.getId())
                    && coordonneStat.getListeObject().size() == list.size()
                    && coordonneStat.getListeObject().containsAll(list)) {
                addErrorMessage(FORMULAIRE_STAT, "ERROR.FIELD.EXIST", "Coordonnée", "ordonnées");
                return;
            }
        }

        //Mise à jour de la coordonnée
        coordonne.setListeObject(list);
        coordonne.setLibelle(libelleCoordonne);
        coordonne.refreshId();
        actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
        clearObjOrdonne();
    }

    /**
     *
     */
    public void delOrdonneCoordonne() {
        for (CoordonneStatistique coordonne : listOrdonneCoordonne) {
            if (coordonne.getId().equals(selectOrdonneCoordonne)) {
                listOrdonneCoordonne.remove(coordonne);
                break;
            }
        }
        selectOrdonneCoordonne = null;
    }
	
	
	/*
	 ******************* METHODS ORDONNE COORDONNE ********************** */

    /**
     * @return int
     */
    private int positionSelectOrdonne() {
        int position = 0;
        for (CoordonneStatistique ordonne : listOrdonneCoordonne) {
            if (selectOrdonneCoordonne.equals(ordonne.getId())) {
                return position;
            }
            position++;
        }
        return -1;
    }

    /**
     * Mettre en première position.
     */
    public void enHautOrdonne() {
        if (listOrdonneCoordonne == null || listOrdonneCoordonne.isEmpty()) {
            return;
        }

        int position = positionSelectOrdonne();
        if (position == -1 || position == 0) {
            return;
        }

        CoordonneStatistique coordonne = listOrdonneCoordonne.get(position);
        listOrdonneCoordonne.remove(position);
        listOrdonneCoordonne.add(0, coordonne);
    }

    /**
     * monter d'une position.
     */
    public void monterOrdonne() {
        if (listOrdonneCoordonne == null || listOrdonneCoordonne.isEmpty()) {
            return;
        }

        int position = positionSelectOrdonne();
        if (position == -1 || position == 0) {
            return;
        }

        CoordonneStatistique coordonne = listOrdonneCoordonne.get(position);
        listOrdonneCoordonne.remove(position);
        listOrdonneCoordonne.add(position - 1, coordonne);
    }

    /**
     * Descendre d'une position.
     */
    public void descendreOrdonne() {
        if (listOrdonneCoordonne == null || listOrdonneCoordonne.isEmpty()) {
            return;
        }

        int position = positionSelectOrdonne();
        if (position == -1 || position >= (listOrdonneCoordonne.size() - 1)) {
            return;
        }

        CoordonneStatistique coordonne = listOrdonneCoordonne.get(position);
        listOrdonneCoordonne.remove(position);
        listOrdonneCoordonne.add(position + 1, coordonne);
    }

    /**
     * Mettre en dernière position.
     */
    public void enBasOrdonne() {
        if (listOrdonneCoordonne == null || listOrdonneCoordonne.isEmpty()) {
            return;
        }

        int position = positionSelectOrdonne();
        if (position == -1 || position >= (listOrdonneCoordonne.size() - 1)) {
            return;
        }

        CoordonneStatistique coordonne = listOrdonneCoordonne.get(position);
        listOrdonneCoordonne.remove(position);
        listOrdonneCoordonne.add(listOrdonneCoordonne.size(), coordonne);
    }

    /**
     * @return List
     */
    public List<SelectItem> getListOrdonneCoordonneItems() {
        List<SelectItem> listItem = new ArrayList<SelectItem>();
        for (CoordonneStatistique ordonne : listOrdonneCoordonne) {
            if (ordonne.getLibelle() != null && !ordonne.getLibelle().isEmpty()) {
                listItem.add(new SelectItem(ordonne.getId(), ordonne.getLibelle()));
            } else {
                listItem.add(new SelectItem(ordonne.getId()));
            }
        }
        return listItem;
    }
	
	
	/*
	 ******************* METHODS OBJECT ORDONNE ********************** */

    /**
     * Ajoute la selection dans ordonneItems.
     */
    @SuppressWarnings("unchecked")
    public void addOrdonneItems() {
        int index = -1;
        for (String idType : selectObjOrdonneAdd) {
            for (int i = 0; i < allObjOrdonneItems.size() && index == -1; i++) {
                if (idType.equals(allObjOrdonneItems.get(i).getValue())) {
                    index = i;
                }
            }
            if (index >= 0) {
                objOrdonneItems.add(allObjOrdonneItems.get(index));
                allObjOrdonneItems.remove(index);
            }
            index = -1;
        }
        Collections.sort(allObjOrdonneItems, new BeanComparator(LABEL, new NullComparator()));
        selectObjOrdonneAdd.clear();
    }

    /**
     * Supprime la selection dans ordonneItems.
     */
    @SuppressWarnings("unchecked")
    public void delOrdonneItems() {
        int index = -1;
        for (String idType : selectObjOrdonneDel) {
            for (int i = 0; i < objOrdonneItems.size() && index == -1; i++) {
                if (idType.equals(objOrdonneItems.get(i).getValue())) {
                    index = i;
                }
            }
            if (index >= 0) {
                allObjOrdonneItems.add(objOrdonneItems.get(index));
                objOrdonneItems.remove(index);
            }
            index = -1;
        }
        Collections.sort(allObjOrdonneItems, new BeanComparator(LABEL, new NullComparator()));
        selectObjOrdonneDel.clear();

    }
	
	/*
	 ******************* AUTRE METHODS ORDONNE ********************** */

    /**
     * @param name
     * @return CoordonneStatistique
     */
    private TypeCoordonneStatistique getCoordonneOrdonne(final String name) {
        for (TypeCoordonneStatistique type : allTypeOrdonne) {
            if (type.getTypeClass().getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    /**
     *
     */
    public void clearObjOrdonne(AjaxBehaviorEvent evt) {
        clearObjOrdonne();
    }

    /**
     *
     */
    public void clearObjOrdonne() {
        allObjOrdonneItems = null;
        libelleCoordonne = null;
        objOrdonneItems.clear();
    }

    /**
     * @return TypeCoordonneStatistique
     */
    public TypeCoordonneStatistique getTypeCoordonneOrdonneSelected() {
        for (TypeCoordonneStatistique typeCoordonne : allTypeOrdonne) {
            if (typeCoordonne.getTypeClass().getName().equals(getSelectTypeOrdonne())) {
                return typeCoordonne;
            }
        }
        return null;
    }


    /**
     *
     */
    public void changeSelectCoordonneOrdonne() {
        for (CoordonneStatistique coordonne : listOrdonneCoordonne) {
            if (coordonne.getId().equals(selectOrdonneCoordonne)) {
                selectTypeOrdonne = coordonne.getTypeCoordonne().getTypeClass().getName();
                break;
            }
        }
    }

    /**
     *
     */
    public void changeSelectCoordonneOrdonneUpdate() {
        CoordonneStatistique coordonneStatistique = null;
        for (CoordonneStatistique coordonne : listOrdonneCoordonne) {
            if (coordonne.getId().equals(selectOrdonneCoordonne)) {
                coordonneStatistique = coordonne;
                setSelectTypeOrdonne(coordonne.getTypeCoordonne().getTypeClass().getName());
                break;
            }
        }

        if (coordonneStatistique == null) {
            addErrorMessage(FORMULAIRE_STAT, "ERROR.LIST.NOT_SELECT_ITEM", "Ordonnée");
            return;
        }

        clearObjOrdonne();
        TypeCoordonneStatistique typeOrdonne = coordonneStatistique.getTypeCoordonne();
        for (Object obj : coordonneStatistique.getListeObject()) {
            objOrdonneItems.add(new SelectItem(typeOrdonne.getRecupIdObject(obj),
                    typeOrdonne.getRecupLibelleObject(obj)));
        }
        libelleCoordonne = coordonneStatistique.getLibelle();

        actionEnum.setWhatAction(ActionEnum.PARAM_ORDONNE);
        addUpdateCoordonne.setWhatAction(ActionEnum.UPDATE_ACTION);
    }

    /**
     * @param idObject
     * @return boolean
     */
    private boolean testExistObjOrdonneItems(final String idObject) {
        if (objOrdonneItems == null) {
            return false;
        }

        for (SelectItem item : objOrdonneItems) {
            if (((String) item.getValue()).equals(idObject)) {
                return true;
            }
        }
        return false;
    }


    //////////////////////////////////////////////////////////////
    // ABSCISSE
    //////////////////////////////////////////////////////////////
	/*
	 ******************* ADD, UPDATE, DEL (ABSCISSE) ********************** */

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public void addAbscisseCoordonne() {
        if (objAbscisseItems == null || objAbscisseItems.isEmpty()) {
            addErrorMessage(FORMULAIRE_STAT, "ERROR.LIST.EMPTY", "Abscisse");
            return;
        }

        //Récupération du type de classe des objets sélectionnés
        TypeCoordonneStatistique typeCoordonne = null;
        for (TypeCoordonneStatistique type : allTypeAbscisse) {
            if (type.getTypeClass().getName().equals(getSelectTypeAbscisse())) {
                typeCoordonne = type;
                break;
            }
        }

        //Récupération des objets
        List<Object> list = new ArrayList<Object>();
        TypeCoordonneStatistique cordonne = getCoordonneAbscisse(getSelectTypeAbscisse());
        List listCoordonne = pilotageService.getAllCoordonne(cordonne.getTypeClass(), cordonne.getMethode());
        for (SelectItem items : objAbscisseItems) {
            for (Object obj : listCoordonne) {
                if (items.getValue().equals(cordonne.getRecupIdObject(obj))) {
                    list.add(obj);
                    break;
                }
            }
        }

        //Test s'il y a une autre coordonnée avec les mêmes objets
        for (CoordonneStatistique coordonneStat : listAbscisseCoordonne) {
            if (coordonneStat.getListeObject().size() == list.size()
                    && coordonneStat.getListeObject().containsAll(list)) {
                addErrorMessage(FORMULAIRE_STAT, "ERROR.FIELD.EXIST", "Coordonnée", "abscisses");
                return;
            }
        }

        //création de la nouvelle coordonnée
        listAbscisseCoordonne.add(new CoordonneStatistique(typeCoordonne, list, libelleCoordonne));
        clearObjAbscisse();
    }

    /**
     *
     */
    @SuppressWarnings("unchecked")
    public void updateAbscisseCoordonne() {
        if (objAbscisseItems == null || objAbscisseItems.isEmpty()) {
            addErrorMessage(FORMULAIRE_STAT, "ERROR.LIST.EMPTY", "Abscisse");
            return;
        }

        //Récupération des nouveaux objets
        List<Object> list = new ArrayList<Object>();
        TypeCoordonneStatistique cordonne = getCoordonneAbscisse(getSelectTypeAbscisse());
        List listCoordonne = pilotageService.getAllCoordonne(cordonne.getTypeClass(), cordonne.getMethode());
        for (SelectItem items : objAbscisseItems) {
            for (Object obj : listCoordonne) {
                if (items.getValue().equals(cordonne.getRecupIdObject(obj))) {
                    list.add(obj);
                    break;
                }
            }
        }

        //Récupération de l'objet CoordonneStatistique
        CoordonneStatistique coordonne = null;
        for (CoordonneStatistique coordonneStat : listAbscisseCoordonne) {
            if (selectAbscisseCoordonne.equals(coordonneStat.getId())) {
                coordonne = coordonneStat;
                break;
            }
        }
        if (coordonne == null) {
            log.debug("l'objet CoordonneStatistique sélectionné n'a pas été trouvé");
            return;
        }

        //Test s'il y a une autre coordonnée avec les mêmes objets
        for (CoordonneStatistique coordonneStat : listAbscisseCoordonne) {
            if (!coordonne.getId().equals(coordonneStat.getId())
                    && coordonneStat.getListeObject().size() == list.size()
                    && coordonneStat.getListeObject().containsAll(list)) {
                addErrorMessage(FORMULAIRE_STAT, "ERROR.FIELD.EXIST", "Coordonnée", "abscisses");
                return;
            }
        }

        //Mise à jour de la coordonnée
        coordonne.setListeObject(list);
        coordonne.setLibelle(libelleCoordonne);
        coordonne.refreshId();
        actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
        clearObjAbscisse();
    }


    /**
     *
     */
    public void delAbscisseCoordonne() {
        for (CoordonneStatistique coordonne : listAbscisseCoordonne) {
            if (coordonne.getId().equals(selectAbscisseCoordonne)) {
                listAbscisseCoordonne.remove(coordonne);
                break;
            }
        }
        selectAbscisseCoordonne = null;
    }
	
	
	/*
	 ******************* METHODS ABSCISSE COORDONNE ********************** */

    /**
     * @return int
     */
    private int positionSelectAbscisse() {
        int position = 0;
        for (CoordonneStatistique abscisse : listAbscisseCoordonne) {
            if (selectAbscisseCoordonne.equals(abscisse.getId())) {
                return position;
            }
            position++;
        }
        return -1;
    }

    /**
     * Mettre en première position.
     */
    public void enHautAbscisse() {
        if (listAbscisseCoordonne == null || listAbscisseCoordonne.isEmpty()) {
            return;
        }

        int position = positionSelectAbscisse();
        if (position == -1 || position == 0) {
            return;
        }

        CoordonneStatistique coordonne = listAbscisseCoordonne.get(position);
        listAbscisseCoordonne.remove(position);
        listAbscisseCoordonne.add(0, coordonne);
    }

    /**
     * monter d'une position.
     */
    public void monterAbscisse() {
        if (listAbscisseCoordonne == null || listAbscisseCoordonne.isEmpty()) {
            return;
        }

        int position = positionSelectAbscisse();
        if (position == -1 || position == 0) {
            return;
        }

        CoordonneStatistique coordonne = listAbscisseCoordonne.get(position);
        listAbscisseCoordonne.remove(position);
        listAbscisseCoordonne.add(position - 1, coordonne);
    }

    /**
     * Descendre d'une position.
     */
    public void descendreAbscisse() {
        if (listAbscisseCoordonne == null || listAbscisseCoordonne.isEmpty()) {
            return;
        }

        int position = positionSelectAbscisse();
        if (position == -1 || position >= (listAbscisseCoordonne.size() - 1)) {
            return;
        }

        CoordonneStatistique coordonne = listAbscisseCoordonne.get(position);
        listAbscisseCoordonne.remove(position);
        listAbscisseCoordonne.add(position + 1, coordonne);
    }

    /**
     * Mettre en dernière position.
     */
    public void enBasAbscisse() {
        if (listAbscisseCoordonne == null || listAbscisseCoordonne.isEmpty()) {
            return;
        }

        int position = positionSelectAbscisse();
        if (position == -1 || position >= (listAbscisseCoordonne.size() - 1)) {
            return;
        }

        CoordonneStatistique coordonne = listAbscisseCoordonne.get(position);
        listAbscisseCoordonne.remove(position);
        listAbscisseCoordonne.add(listAbscisseCoordonne.size(), coordonne);
    }

    /**
     * @return List
     */
    public List<SelectItem> getListAbscisseCoordonneItems() {
        List<SelectItem> listItem = new ArrayList<SelectItem>();
        for (CoordonneStatistique abscisse : listAbscisseCoordonne) {
            if (abscisse.getLibelle() != null && !abscisse.getLibelle().isEmpty()) {
                listItem.add(new SelectItem(abscisse.getId(), abscisse.getLibelle()));
            } else {
                listItem.add(new SelectItem(abscisse.getId()));
            }
        }
        return listItem;
    }
	
	
	/*
	 ******************* METHODS OBJECT ABSCISSE ********************** */

    /**
     * Ajoute la selection dans allAbscisseItems.
     */
    @SuppressWarnings("unchecked")
    public void addAbscisseItems() {
        int index = -1;
        for (String idType : selectObjAbscisseAdd) {
            for (int i = 0; i < allObjAbscisseItems.size() && index == -1; i++) {
                if (idType.equals(allObjAbscisseItems.get(i).getValue())) {
                    index = i;
                }
            }
            if (index >= 0) {
                objAbscisseItems.add(allObjAbscisseItems.get(index));
                allObjAbscisseItems.remove(index);
            }
            index = -1;
        }
        Collections.sort(allObjAbscisseItems, new BeanComparator(LABEL, new NullComparator()));
        selectObjAbscisseAdd.clear();
    }

    /**
     * Supprime la selection dans allAbscisseItems.
     */
    @SuppressWarnings("unchecked")
    public void delAbscisseItems() {
        int index = -1;
        for (String idType : selectObjAbscisseDel) {
            for (int i = 0; i < objAbscisseItems.size() && index == -1; i++) {
                if (idType.equals(objAbscisseItems.get(i).getValue())) {
                    index = i;
                }
            }
            if (index >= 0) {
                allObjAbscisseItems.add(objAbscisseItems.get(index));
                objAbscisseItems.remove(index);
            }
            index = -1;
        }
        Collections.sort(allObjAbscisseItems, new BeanComparator(LABEL, new NullComparator()));
        selectObjAbscisseDel.clear();
    }
	
	
	/*
	 ******************* AUTRES METHODS ABSCISSE ********************** */

    /**
     * @param name
     * @return CoordonneStatistique
     */
    private TypeCoordonneStatistique getCoordonneAbscisse(final String name) {
        for (TypeCoordonneStatistique type : allTypeAbscisse) {
            if (type.getTypeClass().getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    /**
     *
     */
    public void clearObjAbscisse(AjaxBehaviorEvent evt) {
        clearObjAbscisse();
    }

    /**
     * Nettoyage de liste pour l'abscisse.
     */
    public void clearObjAbscisse() {
        allObjAbscisseItems = null;
        libelleCoordonne = null;
        objAbscisseItems.clear();
    }

    /**
     * @return TypeCoordonneStatistique
     */
    public TypeCoordonneStatistique getTypeCoordonneAbscisseSelected() {
        for (TypeCoordonneStatistique typeCoordonne : allTypeAbscisse) {
            if (typeCoordonne.getTypeClass().getName().equals(getSelectTypeAbscisse())) {
                return typeCoordonne;
            }
        }
        return null;
    }

    /**
     *
     */
    public void changeSelectCoordonneAbscisse() {
        for (CoordonneStatistique coordonne : listAbscisseCoordonne) {
            if (coordonne.getId().equals(selectAbscisseCoordonne)) {
                setSelectTypeAbscisse(coordonne.getTypeCoordonne().getTypeClass().getName());
                break;
            }
        }
    }

    /**
     *
     */
    public void changeSelectCoordonneAbscisseUpdate() {
        CoordonneStatistique coordonneStatistique = null;
        for (CoordonneStatistique coordonne : listAbscisseCoordonne) {
            if (coordonne.getId().equals(selectAbscisseCoordonne)) {
                coordonneStatistique = coordonne;
                setSelectTypeAbscisse(coordonne.getTypeCoordonne().getTypeClass().getName());
                break;
            }
        }

        if (coordonneStatistique == null) {
            addErrorMessage(FORMULAIRE_STAT, "ERROR.LIST.NOT_SELECT_ITEM", "Abscisse");
            return;
        }

        clearObjOrdonne();
        TypeCoordonneStatistique typeAbscisse = coordonneStatistique.getTypeCoordonne();
        for (Object obj : coordonneStatistique.getListeObject()) {
            objAbscisseItems.add(new SelectItem(typeAbscisse.getRecupIdObject(obj),
                    typeAbscisse.getRecupLibelleObject(obj)));
        }
        libelleCoordonne = coordonneStatistique.getLibelle();

        actionEnum.setWhatAction(ActionEnum.PARAM_ABSCISSE);
        addUpdateCoordonne.setWhatAction(ActionEnum.UPDATE_ACTION);
    }

    /**
     * @param idObject
     * @return boolean
     */
    private boolean testExistObjAbscisseItems(final String idObject) {
        if (objAbscisseItems == null) {
            return false;
        }

        for (SelectItem item : objAbscisseItems) {
            if (((String) item.getValue()).equals(idObject)) {
                return true;
            }
        }
        return false;
    }


    //////////////////////////////////////////////////////////////
    // CAMPAGNE
    //////////////////////////////////////////////////////////////
	/*
	 ******************* METHODS CAMPAGNE ********************** */

    /**
     * Ajoute la selection dans allAbscisseItems.
     */
    @SuppressWarnings("unchecked")
    public void addCampagneItems() {
        int index = -1;
        for (String idType : selectCampagneAdd) {
            for (int i = 0; i < allCampagneItems.size() && index == -1; i++) {
                if (idType.equals(allCampagneItems.get(i).getValue())) {
                    index = i;
                }
            }
            if (index >= 0) {
                campagneItems.add(allCampagneItems.get(index));
                allCampagneItems.remove(index);
            }
            index = -1;
        }
        Collections.sort(allCampagneItems, new BeanComparator(LABEL, new NullComparator()));
        selectCampagneAdd.clear();
    }

    /**
     * Supprime la selection dans allAbscisseItems.
     */
    @SuppressWarnings("unchecked")
    public void delCampagneItems() {
        int index = -1;
        for (String idType : selectCampagneDel) {
            for (int i = 0; i < campagneItems.size() && index == -1; i++) {
                if (idType.equals(campagneItems.get(i).getValue())) {
                    index = i;
                }
            }
            if (index >= 0) {
                allCampagneItems.add(campagneItems.get(index));
                campagneItems.remove(index);
            }
            index = -1;
        }
        Collections.sort(allCampagneItems, new BeanComparator(LABEL, new NullComparator()));
        selectCampagneDel.clear();
    }

    /**
     * @return list
     */
    public List<Campagne> getCampagnes() {
        List<Campagne> listCamp = new ArrayList<Campagne>();
        for (SelectItem campItem : campagneItems) {
            for (Campagne campagne : getParameterService().getCampagnes(null, null)) {
                String value = (String) campItem.getValue();
                if (value.equals(String.valueOf(campagne.getId()))) {
                    listCamp.add(campagne);
                }
            }
        }
        return listCamp;
    }


    //////////////////////////////////////////////////////////////
    // ACCESSORS
    //////////////////////////////////////////////////////////////
	/*
	 ******************* ACCESSORS ******************** */

    /**
     * @return the actionEnum
     */
    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    /**
     * @return addUpdateCoordonne
     */
    public ActionEnum getAddUpdateCoordonne() {
        return addUpdateCoordonne;
    }

    /**
     * @return pilotageService
     */
    public PilotageService getPilotageService() {
        return pilotageService;
    }

    /**
     * @param pilotageService
     */
    public void setPilotageService(final PilotageService pilotageService) {
        this.pilotageService = pilotageService;
    }

    /**
     * @return libelleCoordonne
     */
    public String getLibelleCoordonne() {
        return libelleCoordonne;
    }

    /**
     * @param libelleCoordonne
     */
    public void setLibelleCoordonne(final String libelleCoordonne) {
        this.libelleCoordonne = libelleCoordonne;
    }


    //////////////////////////////////////////////////////////////
    // ACCESSORS ORDONNE
    //////////////////////////////////////////////////////////////
	/*
	 ******************* ACCESSORS TYPE ORDONNE ******************** */

    /**
     * @return allTypeOrdonne
     */
    public List<TypeCoordonneStatistique> getAllTypeOrdonne() {
        return allTypeOrdonne;
    }

    /**
     * @param allTypeOrdonne
     */
    public void setAllTypeOrdonne(final List<TypeCoordonneStatistique> allTypeOrdonne) {
        this.allTypeOrdonne = allTypeOrdonne;
    }

    /**
     * @return allTypeDecision
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getAllTypeOrdonneItems() {
        if (allTypeOrdonneItems.isEmpty()) {
            if (allTypeOrdonne != null && !allTypeOrdonne.isEmpty()) {
                for (TypeCoordonneStatistique typeOrdonne : allTypeOrdonne) {
                    allTypeOrdonneItems.add(new SelectItem(typeOrdonne.getTypeClass().getName(),
                            typeOrdonne.getShortLabel()));
                }
                setSelectTypeOrdonne(allTypeOrdonne.get(0).getTypeClass().getName());
            } else {
                if (log.isDebugEnabled()) {
                    log.debug("allTypeOrdonne is null");
                }
            }
            Collections.sort(allTypeOrdonneItems, new BeanComparator(LABEL, new NullComparator()));
        }
        return allTypeOrdonneItems;
    }

    /**
     * @return selectTypeOrdonne
     */
    public String getSelectTypeOrdonne() {
        if (selectTypeOrdonne == null) {
            TypeCoordonneStatistique coordonne = allTypeOrdonne.get(0);
            if (coordonne != null) {
                selectTypeOrdonne = coordonne.getTypeClass().getName();
            }
        }
        return selectTypeOrdonne;
    }

    /**
     * @param selectTypeOrdonne
     */
    public void setSelectTypeOrdonne(final String selectTypeOrdonne) {
        this.selectTypeOrdonne = selectTypeOrdonne;
    }
	
	
	/*
	 ******************* ACCESSORS COORDONNE (ORDONNE) ******************** */

    /**
     * @return listOrdonneCoordonne
     */
    public List<CoordonneStatistique> getListOrdonneCoordonne() {
        return listOrdonneCoordonne;
    }

    /**
     * @param listOrdonneCoordonne
     */
    public void setListOrdonneCoordonne(
            final List<CoordonneStatistique> listOrdonneCoordonne) {
        this.listOrdonneCoordonne = listOrdonneCoordonne;
    }

    /**
     * @return selectOrdonneCoordonne
     */
    public String getSelectOrdonneCoordonne() {
        return selectOrdonneCoordonne;
    }

    /**
     * @param selectOrdonneCoordonne
     */
    public void setSelectOrdonneCoordonne(final String selectOrdonneCoordonne) {
        this.selectOrdonneCoordonne = selectOrdonneCoordonne;
    }
	
	
	/*
	 ******************* ACCESSORS OBJECT (ORDONNE) ******************** */

    /**
     * @return allObjOrdonneItems
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getAllObjOrdonneItems() {
        if (allObjOrdonneItems == null) {
            allObjOrdonneItems = new ArrayList<SelectItem>();
            TypeCoordonneStatistique cordonne = getCoordonneOrdonne(getSelectTypeOrdonne());
            if (cordonne != null) {
                List listOrdonne = pilotageService.getAllCoordonne(cordonne.getTypeClass(),
                        cordonne.getMethode());
                if (listOrdonne != null) {
                    for (Object ordonne : listOrdonne) {
                        if (!testExistObjOrdonneItems(cordonne.getRecupIdObject(ordonne))) {
                            allObjOrdonneItems.add(new SelectItem(
                                    cordonne.getRecupIdObject(ordonne),
                                    cordonne.getRecupLibelleObject(ordonne)));
                        }
                    }
                }
            }
            Collections.sort(allObjOrdonneItems, new ComparatorSelectItem());
        }

        return allObjOrdonneItems;
    }

    /**
     * @return objOrdonneItems
     */
    public List<SelectItem> getObjOrdonneItems() {
        return objOrdonneItems;
    }

    /**
     * @param objOrdonneItems
     */
    public void setObjOrdonneItems(final List<SelectItem> objOrdonneItems) {
        this.objOrdonneItems = objOrdonneItems;
    }

    /**
     * @return selectObjOrdonneDel
     */
    public List<String> getSelectObjOrdonneDel() {
        return selectObjOrdonneDel;
    }

    /**
     * @param selectObjOrdonneDel
     */
    public void setSelectObjOrdonneDel(final List<String> selectObjOrdonneDel) {
        this.selectObjOrdonneDel = selectObjOrdonneDel;
    }

    /**
     * @return selectObjOrdonneAdd
     */
    public List<String> getSelectObjOrdonneAdd() {
        return selectObjOrdonneAdd;
    }

    /**
     * @param selectObjOrdonneAdd
     */
    public void setSelectObjOrdonneAdd(final List<String> selectObjOrdonneAdd) {
        this.selectObjOrdonneAdd = selectObjOrdonneAdd;
    }


    //////////////////////////////////////////////////////////////
    // ACCESSORS ABSCISSE
    //////////////////////////////////////////////////////////////
	/*
	 ******************* ACCESSORS TYPE ABSCISSE ******************** */

    /**
     * @return allTypeAbscisse
     */
    public List<TypeCoordonneStatistique> getAllTypeAbscisse() {
        return allTypeAbscisse;
    }

    /**
     * @param allTypeAbscisse
     */
    public void setAllTypeAbscisse(final List<TypeCoordonneStatistique> allTypeAbscisse) {
        this.allTypeAbscisse = allTypeAbscisse;
    }

    /**
     * @return allTypeAbscisseItems
     */
    public List<SelectItem> getAllTypeAbscisseItems() {
        if (allTypeAbscisseItems.isEmpty()) {
            for (TypeCoordonneStatistique typeAbscisse : allTypeAbscisse) {
                allTypeAbscisseItems.add(new SelectItem(typeAbscisse.getTypeClass().getName(),
                        typeAbscisse.getShortLabel()));
            }
            setSelectTypeAbscisse(allTypeAbscisse.get(0).getTypeClass().getName());
            Collections.sort(allTypeAbscisseItems, new ComparatorSelectItem());
        }
        return allTypeAbscisseItems;
    }

    /**
     * @return selectTypeAbscisse
     */
    public String getSelectTypeAbscisse() {
        if (selectTypeAbscisse == null) {
            TypeCoordonneStatistique coordonne = allTypeAbscisse.get(0);
            if (coordonne != null) {
                selectTypeAbscisse = coordonne.getTypeClass().getName();
            }
        }
        return selectTypeAbscisse;
    }

    /**
     * @param selectTypeAbscisse
     */
    public void setSelectTypeAbscisse(final String selectTypeAbscisse) {
        this.selectTypeAbscisse = selectTypeAbscisse;
    }
	
	
	/*
	 ******************* ACCESSORS COORDONNE (ABSCISSE) ******************** */

    /**
     * @return listAbscisseCoordonne
     */
    public List<CoordonneStatistique> getListAbscisseCoordonne() {
        return listAbscisseCoordonne;
    }

    /**
     * @param listAbscisseCoordonne
     */
    public void setListAbscisseCoordonne(
            final List<CoordonneStatistique> listAbscisseCoordonne) {
        this.listAbscisseCoordonne = listAbscisseCoordonne;
    }

    /**
     * @return selectAbscisseCoordonne
     */
    public String getSelectAbscisseCoordonne() {
        return selectAbscisseCoordonne;
    }

    /**
     * @param selectAbscisseCoordonne
     */
    public void setSelectAbscisseCoordonne(final String selectAbscisseCoordonne) {
        this.selectAbscisseCoordonne = selectAbscisseCoordonne;
    }
	
	
	/*
	 ******************* ACCESSORS OBJECT (ABSCISSE) ******************** */

    /**
     * @return allObjAbscisseItems
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getAllObjAbscisseItems() {
        if (allObjAbscisseItems == null) {
            allObjAbscisseItems = new ArrayList<SelectItem>();
            TypeCoordonneStatistique cordonne = getCoordonneAbscisse(getSelectTypeAbscisse());
            if (cordonne != null) {
                List listAbscisse = pilotageService.getAllCoordonne(cordonne.getTypeClass(),
                        cordonne.getMethode());
                if (listAbscisse != null) {
                    for (Object abscisse : listAbscisse) {
                        if (!testExistObjAbscisseItems(cordonne.getRecupIdObject(abscisse))) {
                            allObjAbscisseItems.add(new SelectItem(
                                    cordonne.getRecupIdObject(abscisse),
                                    cordonne.getRecupLibelleObject(abscisse)));
                        }
                    }
                }
                Collections.sort(allObjAbscisseItems, new BeanComparator(LABEL, new NullComparator()));
            }
        }
        return allObjAbscisseItems;
    }

    /**
     * @return abscisseItems
     */
    public List<SelectItem> getObjAbscisseItems() {
        return objAbscisseItems;
    }

    /**
     * @param objAbscisseItems
     */
    public void setObjAbscisseItems(final List<SelectItem> objAbscisseItems) {
        this.objAbscisseItems = objAbscisseItems;
    }

    /**
     * @return selectObjAbscisseDel
     */
    public List<String> getSelectObjAbscisseDel() {
        return selectObjAbscisseDel;
    }

    /**
     * @param selectObjAbscisseDel
     */
    public void setSelectObjAbscisseDel(final List<String> selectObjAbscisseDel) {
        this.selectObjAbscisseDel = selectObjAbscisseDel;
    }

    /**
     * @return selectObjAbscisseAdd
     */
    public List<String> getSelectObjAbscisseAdd() {
        return selectObjAbscisseAdd;
    }

    /**
     * @param selectObjAbscisseAdd
     */
    public void setSelectObjAbscisseAdd(final List<String> selectObjAbscisseAdd) {
        this.selectObjAbscisseAdd = selectObjAbscisseAdd;
    }


    //////////////////////////////////////////////////////////////
    // ACCESSORS CAMPAGNE
    //////////////////////////////////////////////////////////////
	/*
	 ******************* ACCESSORS CAMPAGNE ******************** */

    /**
     * @return allCampagneItems
     */
    @SuppressWarnings("unchecked")
    public List<SelectItem> getAllCampagneItems() {
        if (allCampagneItems == null) {
            allCampagneItems = new ArrayList<SelectItem>();
            Set<Campagne> listCampagne = getParameterService().getCampagnes(null, null);
            if (listCampagne != null) {
                for (Campagne campagne : listCampagne) {
                    allCampagneItems.add(new SelectItem(String.valueOf(campagne.getId()),
                            campagne.getLibelle()));
                }
            }
            Collections.sort(allCampagneItems, new BeanComparator(LABEL, new NullComparator()));
        }
        return allCampagneItems;
    }

    /**
     * @return campagneItems
     */
    public List<SelectItem> getCampagneItems() {
        return campagneItems;
    }

    /**
     * @param campagneItems
     */
    public void setCampagneItems(final List<SelectItem> campagneItems) {
        this.campagneItems = campagneItems;
    }

    /**
     * @return selectCampagneDel
     */
    public List<String> getSelectCampagneDel() {
        return selectCampagneDel;
    }

    /**
     * @param selectCampagneDel
     */
    public void setSelectCampagneDel(final List<String> selectCampagneDel) {
        this.selectCampagneDel = selectCampagneDel;
    }

    /**
     * @return selectCampagneAdd
     */
    public List<String> getSelectCampagneAdd() {
        return selectCampagneAdd;
    }

    /**
     * @param selectCampagneAdd
     */
    public void setSelectCampagneAdd(final List<String> selectCampagneAdd) {
        this.selectCampagneAdd = selectCampagneAdd;
    }
}
