/**
*CRI - Universite de Rennes1 - 57SI-OPI - 2008
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static org.esupportail.opi.domain.beans.etat.EtatVoeu.*;

public class IndVoeuPojo implements Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3401681855841611752L;

	/**
	 * The IndVoeu.
	 */
	private IndVoeu indVoeu;
	
	/**
	 * The VersionEtape.
	 */
	private VersionEtapeDTO vrsEtape;
	
	/**
	 * The vows state. 
	 */
	private EtatVoeu etat;
	
	/**
	 * Default value false.
	 */
	private Boolean calIsOpen;
	
	/**
	 * see {@link TypeTraitement}.
	 */
	private TypeTraitement typeTraitement;
	
	/**
	 * new opinion for the indVoeu.
	 */
	private Avis newAvis;
	
	/**
	 * Avis en service.
	 */
	private Avis avisEnService;
	
	/**
	 * true if the type of decision is LC.
	 */
	private Boolean isUsingLC;
	
	/**
	 * true if the type of decision is DEF.
	 */
	private Boolean isUsingDEF;
	
	/**
	 * The state of the confirmation.
	 */
	private String stateConf;
	
	/**
	 * false if the current user can confirm.
	 * depends of the date of confirmation of the commission,
	 * the state of the voeu and if the user is a manager
	 */
	private Boolean disableConfirm;
	
	/**
	 * The individuDate.
	 */
	private IndividuDate individuDate;
	
	/**
	 * calendrier de rendez-vous.
	 */
	private CalendarRDV calendrierRdv;

	private List<Avis> avisAsList;

    public IndVoeuPojo(final IndVoeu indVoeu,
                       final VersionEtapeDTO vrsEtp,
                       final EtatVoeu etat,
                       final Boolean calIsopen,
                       final TypeTraitement typeTraitement,
                       final CalendarRDV calendrierRdv) {
        this.indVoeu = indVoeu;
        newAvis = new Avis();
        this.vrsEtape = vrsEtp;
        this.calIsOpen = calIsopen;
        this.etat = etat;
        this.typeTraitement = typeTraitement;
        isUsingLC = false;
        isUsingDEF = false;
        stateConf = "";
        initAvisInUse();
        this.calendrierRdv = calendrierRdv;
    }


    /**
	 * Constructors.
     *
	 * @param indVoeu
	 * @param vrsEtp
	 * @param i18Service
	 * @param calIsopen
	 * @param typeTraitement
     *
     * @deprecated Use {@link IndVoeuPojo#IndVoeuPojo(org.esupportail.opi.domain.beans.user.candidature.IndVoeu,
     * org.esupportail.wssi.services.remote.VersionEtapeDTO, org.esupportail.opi.domain.beans.etat.EtatVoeu, Boolean, org.esupportail.opi.domain.beans.parameters.TypeTraitement, org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV)}
	 */
//    @Deprecated
//	public IndVoeuPojo(final IndVoeu indVoeu,
//                       final VersionEtapeDTO vrsEtp,
//                       final I18nService i18Service,
//                       final Boolean calIsopen,
//                       final TypeTraitement typeTraitement,
//                       final CalendarRDV calendrierRdv) {
//		this.indVoeu = indVoeu;
//		newAvis = new Avis();
//		this.vrsEtape = vrsEtp;
//		this.calIsOpen = calIsopen;
//		etat = EtatVoeu.fromString(indVoeu.getState());
//		this.typeTraitement = typeTraitement;
//		isUsingLC = false;
//		isUsingDEF = false;
//		stateConf = "";
//		initAvisInUse();
//		this.calendrierRdv = calendrierRdv;
//	}
	
	/**
	 * Constructor.
	 */
	public IndVoeuPojo() {
		calIsOpen = false;
		newAvis = new Avis();
		isUsingLC = false;
		isUsingDEF = false;
		stateConf = "";
		//initAvisInUse();
	}

	@Override
	public String toString() {
		return "IndVoeuPojo#" + hashCode() + "[indVoeu: " + indVoeu + "], [etat: " + etat + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((indVoeu == null) ? 0 : indVoeu.hashCode());
		result = prime * result
				+ ((vrsEtape == null) ? 0 : vrsEtape.hashCode());
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		IndVoeuPojo other = (IndVoeuPojo) obj;
		if (indVoeu == null) {
			if (other.indVoeu != null) { return false; }
		} else if (!indVoeu.equals(other.indVoeu)) { return false; }
		if (vrsEtape == null) {
			if (other.vrsEtape != null) { return false; }
		} else if (!vrsEtape.getCodEtp().equals(other.vrsEtape.getCodEtp()) 
				&& !vrsEtape.getCodVrsVet().equals(other.vrsEtape.getCodVrsVet())) { 
			return false; 
		}
		return true;
	}

	public Boolean getMyCalIsOpen() {

		return false;
	}

	public String getShortLibVet() {
		return Utilitaires.limitStrLength(vrsEtape.getLibWebVet(),
                Constantes.STR_LENGTH_LIMIT_SMALL);
	}
	
	/**
	 * Find and set the avis in use.
	 */
	public void initAvisInUse() {
		if (this.indVoeu != null && this.indVoeu.getAvis() != null
				&& !this.indVoeu.getAvis().isEmpty()) {
			for (Avis a : this.indVoeu.getAvis()) {
				if (a.getTemoinEnService()) { 
					this.avisEnService =  a; 
					break;
				}
			}
		}
	}

	/**
	 * @return true si l'etat est arrive et complet
	 */
	public Boolean getIsEtatArriveComplet() {
        return etat == EtatArriveComplet;
    }
	
	/**
	 * @return true si l'etat est arrive et complet
	 */
	public Boolean getIsEtatConfirme() {
        return etat == EtatConfirme;
    }
	
	/**
	 * @return true si l'etat est arrive et complet
	 */
	public Boolean getIsEtatDesiste() {
        return etat == EtatDesiste;
    }
	
	/**
	 * @return true si : 
	 * - cas primo entrant et Code CGE servant de temoin AD pour le Web primo entrant non null
	 * - cas reinscription et Code CGE servant de temoin AD pour le Web reinscription non null
	 */
	public Boolean getHasIAForVoeu() {
		String codeEtu = indVoeu.getIndividu().getCodeEtu();
        return (!StringUtils.hasText(codeEtu) && StringUtils.hasText(vrsEtape.getCodCgeMinpVet()))
                || (StringUtils.hasText(codeEtu) && StringUtils.hasText(vrsEtape.getCodCgeMinVet()));
    }
	
	/**
	 * @return true si le candidat a un numéro INE
	 */
	public Boolean getHasNNE() {
		return StringUtils.hasText(indVoeu.getIndividu().getCodeNNE());
	}

	public IndVoeu getIndVoeu() {
		return indVoeu;
	}

	public void setIndVoeu(final IndVoeu indVoeu) {
		this.indVoeu = indVoeu;
	}

	public VersionEtapeDTO getVrsEtape() {
		return vrsEtape;
	}

	public void setVrsEtape(final VersionEtapeDTO vrsEtape) {
		this.vrsEtape = vrsEtape;
	}

	public List<Avis> getAvisAsList() {
		if (this.indVoeu.getAvis() != null && !this.indVoeu.getAvis().isEmpty()) {
			this.avisAsList = new ArrayList<>(indVoeu.getAvis());
		}
		return avisAsList;
	}

	public EtatVoeu getEtat() {
		return etat;
	}

	public void setEtat(final EtatVoeu etat) {
		this.etat = etat;
	}

	public Boolean getCalIsOpen() {
		return calIsOpen;
	}

	public void setCalIsOpen(final Boolean calIsOpen) {
		this.calIsOpen = calIsOpen;
	}

	public TypeTraitement getTypeTraitement() {
		return typeTraitement;
	}

	public void setTypeTraitement(final TypeTraitement typeTraitement) {
		this.typeTraitement = typeTraitement;
	}

	public Avis getNewAvis() {
		return newAvis;
	}

	public void setNewAvis(final Avis newAvis) {
		this.newAvis = newAvis;
	}

	public Avis getAvisEnService() {
		return avisEnService;
	}

	public void setAvisEnService(final Avis avisEnService) {
		this.avisEnService = avisEnService;
	}

	public Boolean getIsUsingLC() {
		return isUsingLC;
	}

	public void setIsUsingLC(final Boolean isUsingLC) {
		this.isUsingLC = isUsingLC;
	}

	public Boolean getIsUsingDEF() {
		return isUsingDEF;
	}

	public void setIsUsingDEF(final Boolean isUsingDEF) {
		this.isUsingDEF = isUsingDEF;
	}

	public String getStateConf() {
		return stateConf;
	}

	public void setStateConf(final String stateConf) {
		this.stateConf = stateConf;
	}

	public Boolean getDisableConfirm() {
		return disableConfirm;
	}

	public void setDisableConfirm(final Boolean disableConfirm) {
		this.disableConfirm = disableConfirm;
	}

	public IndividuDate getIndividuDate() {
		return individuDate;
	}

	public void setIndividuDate(final IndividuDate individuDate) {
		this.individuDate = individuDate;
	}
	
	public CalendarRDV getCalendrierRdv() {
		return calendrierRdv;
	}

	public void setCalendrierRdv(final CalendarRDV calendrierRdv) {
		this.calendrierRdv = calendrierRdv;
	}
}

