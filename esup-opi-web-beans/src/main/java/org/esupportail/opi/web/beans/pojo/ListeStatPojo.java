package org.esupportail.opi.web.beans.pojo;

import java.util.List;
import java.util.Map;

/**
 * @author cgomez
 */
public class ListeStatPojo {
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * date.
	 */
	private String date;
	/**
	 * titre.
	 */
	private String titre;
	/**
	 * libCamp.
	 */
	private List<String> libCamp;
	/**
	 * nbAbscisse.
	 */
	private String nbAbscisse;
	/**
	 * titreAbscisse.
	 */
	private List<String> titreAbscisse;
	/**
	 * mapStatList.
	 */
	private Map<String, List<String>> mapStatList;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public ListeStatPojo() {
		super();
	}
	
	
	/*
	 ******************* METHODS ********************** */
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return date
	 */
	public String getDate() {
		return date;
	}
	/**
	 * @param date
	 */
	public void setDate(String date) {
		this.date = date;
	}
	/**
	 * @return titre
	 */
	public String getTitre() {
		return titre;
	}
	/**
	 * @param titre
	 */
	public void setTitre(String titre) {
		this.titre = titre;
	}
	/**
	 * @return libCamp
	 */
	public List<String> getLibCamp() {
		return libCamp;
	}
	/**
	 * @param libCamp
	 */
	public void setLibCamp(List<String> libCamp) {
		this.libCamp = libCamp;
	}
	/**
	 * @return nbAbscisse
	 */
	public String getNbAbscisse() {
		return nbAbscisse;
	}
	/**
	 * @param nbAbscisse
	 */
	public void setNbAbscisse(String nbAbscisse) {
		this.nbAbscisse = nbAbscisse;
	}
	/**
	 * @return titreAbscisse
	 */
	public List<String> getTitreAbscisse() {
		return titreAbscisse;
	}
	/**
	 * @param titreAbscisse
	 */
	public void setTitreAbscisse(List<String> titreAbscisse) {
		this.titreAbscisse = titreAbscisse;
	}
	/**
	 * @return mapStatList
	 */
	public Map<String, List<String>> getMapStatList() {
		return mapStatList;
	}
	/**
	 * @param mapStatList
	 */
	public void setMapStatList(Map<String, List<String>> mapStatList) {
		this.mapStatList = mapStatList;
	}
}
