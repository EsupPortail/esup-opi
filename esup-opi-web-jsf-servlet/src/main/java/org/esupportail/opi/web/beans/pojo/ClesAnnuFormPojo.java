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
    private ClesAnnuForm ClesAnnuForm;
    /**
     * liste de libele en fonction de la langue.
     */
    private List<Cles2AnnuForm> Cles2AnnuForm;

    // TODO: fix the following !!
    /**
     * liste de diplomes.
     */
    private List<ClesDiplomeAnnuForm> ClesDiplomeAnnuForm;

    /**
     * lien vers le domaine.
     */
    private DomaineAnnuFormPojo DomaineAnnuFormPojo;
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
    public ClesAnnuFormPojo(final ClesAnnuForm ClesAnnuForm, final String codeLang) {
        super();
        this.ClesAnnuForm = ClesAnnuForm;
        this.codeLang = codeLang;
    }
	
	
	/*
	 ******************* METHODS ********************** */

    /**
     * @return String
     */
    public String getCodCles() {
        return ClesAnnuForm.getCodCles();
    }

    /**
     * @return String
     */
    public String getLibClesFr() {
        for (Cles2AnnuForm cle : Cles2AnnuForm) {
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
        return ClesAnnuForm;
    }

    /**
     * @param ClesAnnuForm
     */
    public void setClesAnnuForm(ClesAnnuForm ClesAnnuForm) {
        this.ClesAnnuForm = ClesAnnuForm;
    }

    /**
     * @return Cles2AnnuForm
     */
    public List<Cles2AnnuForm> getCles2AnnuForm() {
        return Cles2AnnuForm;
    }

    /**
     * @param Cles2AnnuForm
     */
    public void setCles2AnnuForm(List<Cles2AnnuForm> Cles2AnnuForm) {
        this.Cles2AnnuForm = Cles2AnnuForm;
    }

    // TODO: fix the following !!

    /**
     * @return Set(ClesDiplomeAnnuForm)
     */
    public List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm() {
        return ClesDiplomeAnnuForm;
    }

//	TODO: fix the following !!	

    /**
     * @param ClesDiplomeAnnuForm Set
     */
    public void setClesDiplomeAnnuForm(
            List<ClesDiplomeAnnuForm> ClesDiplomeAnnuForm) {
        this.ClesDiplomeAnnuForm = ClesDiplomeAnnuForm;
    }

    /**
     * @return DomaineAnnuForm
     */
    public DomaineAnnuFormPojo getDomaineAnnuFormPojo() {
        return DomaineAnnuFormPojo;
    }

    /**
     * @param DomaineAnnuFormPojo
     */
    public void setDomaineAnnuFormPojo(DomaineAnnuFormPojo DomaineAnnuFormPojo) {
        this.DomaineAnnuFormPojo = DomaineAnnuFormPojo;
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
