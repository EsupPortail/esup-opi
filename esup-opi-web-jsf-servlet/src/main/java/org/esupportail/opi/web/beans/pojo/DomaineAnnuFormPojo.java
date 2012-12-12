package org.esupportail.opi.web.beans.pojo;

import java.util.List;

import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.DomaineAnnuForm;

/**
 * @author ylecuyer
 *
 */
public class DomaineAnnuFormPojo {
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * ClesAnnuForm.
	 */
	private DomaineAnnuForm domaineAnnuForm;
	/**
	 * liste de Domaine2AnnuForm (un libele par langue).
	 */
	private List<Domaine2AnnuForm> Domaine2AnnuForm;
	/**
	 * codeLang.
	 */
	private String codeLang;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public DomaineAnnuFormPojo() {
		super();
	}
	/**
	 * Constructors.
	 * @param DomaineAnnuForm
	 * @param codeLang
	 */
	public DomaineAnnuFormPojo(final DomaineAnnuForm domaineAnnuForm, final String codeLang) {
		super();
		this.domaineAnnuForm = domaineAnnuForm;
		this.codeLang = codeLang;
	}
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * @return String
	 */
	public String getLibClesFr() {
		for (Domaine2AnnuForm dom : Domaine2AnnuForm) {
			if(dom.getCodLang().equalsIgnoreCase(codeLang)) {
				return dom.getLibDom();
			}
		}
		return "";
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return DomaineAnnuForm
	 */
	public DomaineAnnuForm getDomaineAnnuForm() {
		return domaineAnnuForm;
	}
	/**
	 * 
	 * @param DomaineAnnuForm
	 */
	public void setDomaineAnnuForm(DomaineAnnuForm DomaineAnnuForm) {
		this.domaineAnnuForm = DomaineAnnuForm;
	}
	/**
	 * @return Domaine2AnnuForm
	 */
	public List<Domaine2AnnuForm> getDomaine2AnnuForm() {
		return Domaine2AnnuForm;
	}
	/**
	 * @param Domaine2AnnuForm
	 */
	public void setDomaine2AnnuForm(
			List<Domaine2AnnuForm> Domaine2AnnuForm) {
		this.Domaine2AnnuForm = Domaine2AnnuForm;
	}
	/**
	 * @return codeLang
	 */
	public String getCodeLang() {
		return codeLang;
	}
	/**
	 * @param codeLang
	 */
	public void setCodeLang(String codeLang) {
		this.codeLang = codeLang;
	}
}
