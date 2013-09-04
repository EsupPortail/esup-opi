package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesAnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesDiplomeAnnuForm;

import java.util.List;

/**
 * @author ylecuyer
 */
public class ClesAnnuFormPojo {    /*
	 ******************* PROPERTIES ******************* */
    /**
     * ClesAnnuForm.
     */
    private ClesAnnuForm clesAnnuForm;
    /**
     * liste de libele en fonction de la langue.
     */
    private List<Cles2AnnuForm> cles2AnnuForm;

    // TODO: fix the following !!
    /**
     * liste de diplomes.
     */
    private List<ClesDiplomeAnnuForm> clesDiplomeAnnuForm;

    /**
     * lien vers le domaine.
     */
    private DomaineAnnuFormPojo domaineAnnuFormPojo;
    /**
     * codeLang.
     */
    private String codeLang;
	
	
	/*
	 ******************* INIT ************************* */

    /**
     * Constructors.
     */
    public ClesAnnuFormPojo() {
        super();
    }

    /**
     * Constructors.
     *
     * @param ClesAnnuForm
     * @param codeLang
     */
    public ClesAnnuFormPojo(final ClesAnnuForm clesAnnuForm, final String codeLang) {
        super();
        this.clesAnnuForm = clesAnnuForm;
        this.codeLang = codeLang;
    }
	
	
	/*
	 ******************* METHODS ********************** */

    /**
     * @return String
     */
    public String getCodCles() {
        return clesAnnuForm.getCodCles();
    }

    /**
     * @return String
     */
    public String getLibClesFr() {
        for (Cles2AnnuForm cle : cles2AnnuForm) {
            if (cle.getId().getCodLang().equalsIgnoreCase(codeLang)) {
                return cle.getLibCles();
            }
        }
        return "";
    }
	
	
	/*
	 ******************* ACCESSORS ******************** */

    /**
     * @return ClesAnnuForm
     */
    public ClesAnnuForm getClesAnnuForm() {
        return clesAnnuForm;
    }

    /**
     * @param ClesAnnuForm
     */
    public void setClesAnnuForm(ClesAnnuForm clesAnnuForm) {
        this.clesAnnuForm = clesAnnuForm;
    }

    /**
     * @return Cles2AnnuForm
     */
    public List<Cles2AnnuForm> getCles2AnnuForm() {
        return cles2AnnuForm;
    }

    /**
     * @param Cles2AnnuForm
     */
    public void setCles2AnnuForm(List<Cles2AnnuForm> cles2AnnuForm) {
        this.cles2AnnuForm = cles2AnnuForm;
    }

    // TODO: fix the following !!

    /**
     * @return Set(ClesDiplomeAnnuForm)
     */
    public List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm() {
        return clesDiplomeAnnuForm;
    }

//	TODO: fix the following !!	

    /**
     * @param ClesDiplomeAnnuForm Set
     */
    public void setClesDiplomeAnnuForm(
            List<ClesDiplomeAnnuForm> clesDiplomeAnnuForm) {
        this.clesDiplomeAnnuForm = clesDiplomeAnnuForm;
    }

    /**
     * @return DomaineAnnuForm
     */
    public DomaineAnnuFormPojo getDomaineAnnuFormPojo() {
        return domaineAnnuFormPojo;
    }

    /**
     * @param DomaineAnnuFormPojo
     */
    public void setDomaineAnnuFormPojo(DomaineAnnuFormPojo domaineAnnuFormPojo) {
        this.domaineAnnuFormPojo = domaineAnnuFormPojo;
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
