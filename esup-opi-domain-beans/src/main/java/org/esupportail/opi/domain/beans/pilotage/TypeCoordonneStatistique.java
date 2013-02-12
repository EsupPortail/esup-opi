/**
 * 
 */
package org.esupportail.opi.domain.beans.pilotage;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.beanutils.PropertyUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * @author cleprous
 *
 */
public class TypeCoordonneStatistique {
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * Le libelle de l'objet.
	 */
	private String libelle;
	
	/**
	 * The short label.
	 */
	private String shortLabel;
	
	/**
	 * 
	 */
	private String typeClassString;
	
	/**
	 * 
	 */
	private Class< ? > typeClass;
	
	/**
	 * 
	 */
	private String methode;
	
	/**
	 * 
	 */
	private String idObjectIsInteger;
	
	/**
	 * 
	 */
	private String idObject;
	
	/**
	 * 
	 */
	private String libelleObject;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public TypeCoordonneStatistique() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "[[libelle=" + libelle + "][shortLabel=" + shortLabel + "][typeClass=" + typeClassString + "]]";
	}
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * @return typeClass
	 */
	public Class< ? > getTypeClass() {
		if (typeClass == null) {
			try {
				typeClass = Class.forName(typeClassString);
				return typeClass;
			} catch (ClassNotFoundException e) {
				log.warn("la classe " + typeClassString + " n'existe pas");
				return null;
			}
		}
		return typeClass;
	}
	
	/**
	 * @param obj
	 * @return String
	 */
	public String getRecupIdObject(final Object obj) {
		Method[] methods = this.getTypeClass().getMethods();
		Method methodToExecute = null;
		char[] charTable = idObject.toCharArray();
		charTable[0] = Character.toUpperCase(charTable[0]);
		String methodId = "get" + new String(charTable);
		
		if (log.isDebugEnabled()) {
			log.debug("Methode id : " + methodId);
		}
		
		//look for methodToExecute to args
		for (int i = 0; i < methods.length; ++i) {
			Method m = methods[i];
			if (m.getName().equals(methodId)) {
				if (log.isDebugEnabled()) {
					log.debug("methods : " + m.getName());
				}
				methodToExecute = m;
				break;
			}
		}
		if (methodToExecute == null) {
			log.warn("l'attribut " + methodId + " représentant l'identifiant n'existe pas");
			return null;
		}
		try {
			String id;
			if (idObjectIsInteger.equals("Y") || idObjectIsInteger.equals("y")
					|| idObjectIsInteger.equals("O") || idObjectIsInteger.equals("o")) {
				id = String.valueOf((Integer) methodToExecute.invoke(obj));
			} else {
				id = (String) methodToExecute.invoke(obj);
			}
			return id;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @param obj
	 * @return String
	 */
	public String getRecupLibelleObject(final Object obj) {

		try {
			Object objResult = PropertyUtils.getNestedProperty(obj,
					libelleObject);
			return objResult.toString();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (NullPointerException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		}

		return null;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
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
	 * @return shortLabel
	 */
	public String getShortLabel() {
		return shortLabel;
	}
	
	/** 
	 * @param shortLabel
	 */
	public void setShortLabel(final String shortLabel) {
		this.shortLabel = shortLabel;
	}
	
	/**
	 * @return String
	 */
	public String getTypeClassString() {
		return typeClassString;
	}
	
	/**
	 * @param typeClassString
	 */
	public void setTypeClassString(final String typeClassString) {
		this.typeClassString = typeClassString;
	}
	
	/**
	 * @return methode
	 */
	public String getMethode() {
		return methode;
	}
	/**
	 * @param methode
	 */
	public void setMethode(final String methode) {
		this.methode = methode;
	}

	/**
	 * @return idObjectIsInteger
	 */
	public String getIdObjectIsInteger() {
		return idObjectIsInteger;
	}
	/**
	 * @param idObjectIsInteger
	 */
	public void setIdObjectIsInteger(final String idObjectIsInteger) {
		this.idObjectIsInteger = idObjectIsInteger;
	}
	
	/**
	 * @return idObject
	 */
	public String getIdObject() {
		return idObject;
	}
	/**
	 * @param idObject
	 */
	public void setIdObject(final String idObject) {
		this.idObject = idObject;
	}
	
	/**
	 * @return libelleObject
	 */
	public String getLibelleObject() {
		return libelleObject;
	}
	/**
	 * @param libelleObject
	 */
	public void setLibelleObject(final String libelleObject) {
		this.libelleObject = libelleObject;
	}
}
