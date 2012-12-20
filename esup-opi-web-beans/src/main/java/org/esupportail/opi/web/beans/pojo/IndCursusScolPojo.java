/**
*CRI - Universite de Rennes1 - 57SI-CIES - 2007
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.beans.user.indcursus.CursusExt;
import org.esupportail.opi.domain.beans.user.indcursus.CursusR1;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.wssi.services.remote.Etablissement;
import org.springframework.util.StringUtils;





/**
 * @author leproust cedric
 *
 */
public class IndCursusScolPojo {

	/**
	 * A logger.
	 */
//	private final Logger log = new LoggerImpl(getClass());
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The Cursus.
	 * Default value is Cursus externe
	 */
	private IndCursusScol cursus;
	
	/**
	 * The Etablissement.
	 */
	private Etablissement etablissement;
	
	/**
	 * I18n Service.
	 */
	private I18nService i18nService;
	
	/**
	 * The code departement.
	 * Default value : ""
	 */
	private String codDep;
	
	/**
	 * The code commune.
	 * Default value : ""
	 */
	private String codCom;
	
	/**
	 * The code Pays.
	 * Default value : ""
	 */
	private String codPay;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public IndCursusScolPojo() {
		super();
		cursus = new CursusExt();
		codPay = "";
		codDep = "";
		codCom = "";
	}
	
	/**
	 * Constructor.
	 * @param cur 
	 * @param i18nService 
	 */
	public IndCursusScolPojo(final IndCursusScol cur, final I18nService i18nService) {
		super();
		cursus = cur;
		codPay = "";
		codDep = "";
		codCom = "";
		this.i18nService = i18nService; 
	}
	
	/**
	 * Constructor.
	 * Instance etablissement.
	 * @param cur 
	 * @param i18nService 
	 * @param domainApoService
	 */
	public IndCursusScolPojo(final IndCursusScol cur, final I18nService i18nService, 
			final DomainApoService domainApoService) {
		super();
		cursus = cur;
		codPay = "";
		codDep = "";
		codCom = "";
		this.i18nService = i18nService;
		if (StringUtils.hasText(cur.getCodEtablissement())) {
			etablissement = domainApoService.getEtablissement(cur.getCodEtablissement());
			cursus.setCodTypeEtab(etablissement.getCodTpe());
		}
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PojoIndCursusScol#" + hashCode() + "[cursus" + cursus + "]";
	}


	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((cursus == null) ? 0 : cursus.hashCode());
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		IndCursusScolPojo other = (IndCursusScolPojo) obj;
		if (cursus == null) {
			if (other.cursus != null) {	return false; }
		} else if (!cursus.equals(other.cursus)) { return false; }
		return true;
	}

	/*
	 ******************* METHODS ********************** */

	
	/**
	 * Return true if cursus is instance of CursusR1.
	 * @return Boolean
	 */
	public Boolean getIsCursusR1() {
		return cursus instanceof CursusR1;
	}
	
	/**
	 * Return true if cursus is instance of CursusExt.
	 * @return Boolean
	 */
	public Boolean getIsCursusExt() {
		return cursus instanceof CursusExt;
	}
	
	/**
	 * Return the result label according to the code.
	 * @return String
	 */
	public String getResultatExt() {
		if (i18nService != null) {
			if ("N".equals(cursus.getResultat())) {
				return i18nService.getString("_.BUTTON.NO");
			}
			if ("O".equals(cursus.getResultat())) {
				return i18nService.getString("_.BUTTON.YES");
			}
		}
		return cursus.getResultat();
	}
	
	/**
	 * Le libelle des cursus scolaire.
	 * @return String
	 */
	public String getLibCur() {
		if (getIsCursusR1()) {
			return ((CursusR1) cursus).getLibEtape();
		}
		
		CursusExt curE = (CursusExt) cursus;
		if (StringUtils.hasText(curE.getLibelle())) {
			return curE.getLibelle();
		}
		return curE.getLibDac();
	}
	
	/**
	 * the libEtb soit etablissement.getLibEtb soit LibEtbEtr.
	 * @return String
	 */
	public String getLibEtb() {
		if (etablissement != null) {
			return etablissement.getLibEtb();
		}
		//sinon etablissement etranger
		return ((CursusExt) cursus).getLibEtbEtr();
	}
	
	
//	/**
//	 * Add a new cursus to the list.
//	 */
//	public void addCursus() {
//		if (log.isDebugEnabled()) {
//			log.debug("entering addCursus with cursus = " + cursus);
//		}		
//		cursusList.add(cursus);
//		initCursus();
//	}
//	
//	/**
//	 * Remove Cursus in cursusList.
//	 */
//	public void removeCursus() {
//		if (log.isDebugEnabled()) {
//			log.debug("entering removeCursus with cursus = " + cursus);
//		}
//		cursusList.remove(cursus);
//		initCursus();
//	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	
	
	/**
	 * @return the cursus
	 */
	public IndCursusScol getCursus() {
		return cursus;
	}

	/**
	 * @param cursus the cursus to set
	 */
	public void setCursus(final IndCursusScol cursus) {
		this.cursus = cursus;
	}

	/**
	 * @return the etablissement
	 */
	public Etablissement getEtablissement() {
		return etablissement;
	}

	/**
	 * @param etablissement the etablissement to set
	 */
	public void setEtablissement(final Etablissement etablissement) {
		this.etablissement = etablissement;
	}

	/**
	 * @return the codDep
	 */
	public String getCodDep() {
		return codDep;
	}

	/**
	 * @param codDep the codDep to set
	 */
	public void setCodDep(final String codDep) {
		this.codDep = codDep;
	}

	/**
	 * @return the codCom
	 */
	public String getCodCom() {
		return codCom;
	}

	/**
	 * @param codCom the codCom to set
	 */
	public void setCodCom(final String codCom) {
		this.codCom = codCom;
	}

	/**
	 * @return the codPay
	 */
	public String getCodPay() {
		return codPay;
	}

	/**
	 * @param codPay the codPay to set
	 */
	public void setCodPay(final String codPay) {
		this.codPay = codPay;
	}

}
