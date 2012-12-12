package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.references.commission.Commission;

import java.util.List;
import java.util.Map;

/**
 * @author ylecuyer
 * Pojo servant e stocker les informations d'un individu pour
 * l'edition de la liste preparatoire des commission
 * Information recuperee e partir de IndividuPojo
 * Version "allegee" de IndividuPojo (principalement sur la partie Individu
 */
public class ListePrepaPojo {
	/*
	 ******************* PROPERTIES ******************* */

	private String date;
	
	private String debut;
	
	private Commission commission;
	
	private List<IndListePrepaPojo> listeIndPrepa;
	
	@SuppressWarnings("unchecked")
	private Map mapIndList;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public ListePrepaPojo() {
		super();
	}


	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the listeIndPrepa
	 */
	public List<IndListePrepaPojo> getListeIndPrepa() {
		return listeIndPrepa;
	}


	/**
	 * @param listeIndPrepa the listeIndPrepa to set
	 */
	public void setListeIndPrepa(final List<IndListePrepaPojo> listeIndPrepa) {
		this.listeIndPrepa = listeIndPrepa;
	}




	/**
	 * @return the mapIndList
	 */
	@SuppressWarnings("unchecked")
	public Map getMapIndList() {
		return mapIndList;
	}


	/**
	 * @param mapIndList the mapIndList to set
	 */
	@SuppressWarnings("unchecked")
	public void setMapIndList(final Map mapIndList) {
		this.mapIndList = mapIndList;
	}


	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}


	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}


	/**
	 * @return the commission
	 */
	public Commission getCommission() {
		return commission;
	}


	/**
	 * @param commission the commission to set
	 */
	public void setCommission(Commission commission) {
		this.commission = commission;
	}

	
	/**
	 * @return String
	 */
	public String getDebut() {
		return debut;
	}


	/**
	 * @param dateDebutCampagne
	 */
	public void setDebut(String debut) {
		this.debut = debut;
	}

	

	
}
