/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.web.controllers;

import org.esupportail.opi.web.beans.utils.comparator.ComparatorSelectItem;

import javax.faces.model.SelectItem;
import javax.faces.model.SelectItemGroup;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * A bean to manage files.
 */
public class AboutController extends AbstractContextAwareController {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -522191275533736924L;

    /**
     * JUST FOR MAQUETTE AFTER TO DELETE.
     */
    private String visible;

    /**
     * Bean constructor.
     */
    public AboutController() {
        super();
        visible = "";
    }

    /**
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return getClass().getSimpleName() + "#" + hashCode();
    }

    /**
     * @return true if the current user is allowed to view the page.
     */
    public boolean isPageAuthorized() {
        return true;
    }

    /**
     * JSF callback.
     *
     * @return A String.
     */
    public String enter() {
        if (!isPageAuthorized()) {
            addUnauthorizedActionMessage();
            return null;
        }
        return "navigationAbout";
    }


    /**
     * Construit le select pour choisir la composante trié par etablissement.
     *
     * @return List< SelectItem>
     */
    public List<SelectItem> getMotClefItems() {
        List<SelectItem> listS = new ArrayList<SelectItem>();
        List<SelectItem> listGroup = new ArrayList<SelectItem>();
        SelectItem[] listS2 = new SelectItem[3];
        SelectItem s = new SelectItem("v", "Carrieres sociales");
        listS2[0] = s;
        s = new SelectItem("v", "Enseignement-Formation");
        listS2[1] = s;
        s = new SelectItem("v", "Philosophie");
        listS2[2] = s;
        listGroup.add(new SelectItemGroup("Sciences Humaines et Sociales", null, false, listS2));
        listS2 = new SelectItem[2];
        s = new SelectItem("v", "Sciences de la terre");
        listS2[0] = s;
        s = new SelectItem("v", "Philosophie");
        listS2[1] = s;
        listGroup.add(new SelectItemGroup("Sciences, Technologies, Sante", null, false, listS2));

        Collections.sort(listGroup, new ComparatorSelectItem());
        listS.addAll(listGroup);
        return listS;

    }

    /**
     * @return the visible
     */
    public String getVisible() {
        return visible;
    }

    /**
     * @param visible the visible to set
     */
    public void setVisible(final String visible) {
        this.visible = visible;
    }


}
