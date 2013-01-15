/**
 * 
 */
package org.esupportail.opi.domain.beans.utils;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.pilotage.TypeCoordonneStatistique;


/**
 * @author cleprous
 *
 */
public class CoordonneStatistique {
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * Id coordonnée.
	 */
	private String id;

	/**
	 * Id coordonnée.
	 */
	private String libelle;
	
	/**
	 * 
	 */
	private TypeCoordonneStatistique typeCoordonne;
	
	/**
	 * 
	 */
	private List<Object> listeObject;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 * @param typeCoordonne 
	 * @param list 
	 * @param libelle 
	 */
	@SuppressWarnings("unchecked")
	public CoordonneStatistique(final TypeCoordonneStatistique typeCoordonne, final List list,
			final String libelle) {
		super();
		if (log.isDebugEnabled()) {
			log.debug("Constructors CoordonneStatistique()");
		}
		this.typeCoordonne = typeCoordonne;
		this.listeObject = list;
		this.libelle = libelle;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[[id=" + id + "][typeCoordonne=" + typeCoordonne + "][listeObject=" + listeObject + "]]";
	}
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * @param obj
	 * @return String
	 */
	public String[] getRecupInfosObject(final Object obj) {
		String[] tabObj = new String[2];
		tabObj[0] = this.typeCoordonne.getRecupIdObject(obj);
		tabObj[1] = this.typeCoordonne.getRecupLibelleObject(obj);
		return tabObj;
	}
	
	/**
	 * @return String
	 */
	public Map<String, String> getRecupAllMapObject() {
		Map<String, String> mapObj = new HashMap<String, String>();
		for (Object obj : listeObject) {
			mapObj.put(this.typeCoordonne.getRecupIdObject(obj), 
					this.typeCoordonne.getRecupLibelleObject(obj));
		}
		return mapObj;
	}
	
	/**
	 * refresh id.
	 */
	public void refreshId() {
		id = this.typeCoordonne.getShortLabel() + " : ";
		for (int i = 0; i < listeObject.size(); i++) {
			id += typeCoordonne.getRecupLibelleObject(listeObject.get(i));
			if (i != listeObject.size() - 1) {
				id += ",";
			}
		}
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return id
	 */
	public String getId() {
		if (id == null || id.isEmpty()) {
			refreshId();
		}
		return id;
	}
	
	/**
	 * @return libelle
	 */
	public String getLibelle() {
		return libelle;
	}
	
	/**
	 * @param libelle
	 */
	public void setLibelle(final String libelle) {
		this.libelle = libelle;
	}
	
	/**
	 * @return typeCoordonne
	 */
	public TypeCoordonneStatistique getTypeCoordonne() {
		return typeCoordonne;
	}
	/**
	 * @param typeCoordonne
	 */
	public void setTypeCoordonne(final TypeCoordonneStatistique typeCoordonne) {
		this.typeCoordonne = typeCoordonne;
	}
	
	/**
	 * @return listeObject
	 */
	public List<Object> getListeObject() {
		return listeObject;
	}
	/**
	 * @param listeObject
	 */
	public void setListeObject(final List<Object> listeObject) {
		this.listeObject = listeObject;
	}
}
