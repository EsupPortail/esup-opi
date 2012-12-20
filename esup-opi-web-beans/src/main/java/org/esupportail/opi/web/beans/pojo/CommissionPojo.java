/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.beans.etat.Etat;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.ContactCommission;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


/**
 * @author cleprous
 *
 */
public class CommissionPojo implements Serializable {

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3239716643949910585L;

	/**
	 * see {@link Commission}.
	 */
	private Commission commission;
	
	/**
	 * see {@link AdressePojo}.
	 */
	private AdressePojo adressePojo;
	
	/**
	 * The contactCommission for the regime d'inscription.
	 * du gestionnaire
	 */
	private ContactCommission contactCommission;
	
	/**
	 * state of voeux for the commisions.
	 * All the same state for one commision.
	 */
	private EtatVoeu state;
	
	/**
	 * The label of current state old.
	 */
	private String stateCurrentOld;
	
	/**
	 * The label of current state.
	 */
	private String stateCurrent;
	
	/**
	 * the treament of cmi.
	 */
	private Set<TraitementCmiPojo> treatmentsPojo;
	
	/**
	 * the flagWithoutTrtActive.
	 * true if the commission don't have any active treatment
	 */
	private Boolean flagWithoutTrtActive;
	
	/**
	 * the flagWithSomeTrtInactive.
	 * true if the commission have some inactive treatment
	 */
	private Boolean flagWithSomeTrtInactive;
	
	/*
	 ******************* INIT ************************* */

	
	/**
	 * Constructors.
	 */
	public CommissionPojo() {
		super();
	}
	
	
	/**
	 * Constructors.
	 * @param commission
	 * @param contactCommission
	 */
	public CommissionPojo(final Commission commission,
			final ContactCommission contactCommission) {
		super();
		this.commission = commission;
		this.contactCommission = contactCommission;
	}


	/**
	 * Constructors.
	 * @param commission
	 * @param adressePojo
	 * @param contactCommission
	 */
	public CommissionPojo(final Commission commission, final AdressePojo adressePojo,
			final ContactCommission contactCommission) {
		super();
		this.commission = commission;
		this.adressePojo = adressePojo;
		this.contactCommission = contactCommission;
	}


	/**
	 * Constructors.
	 * @param commission
	 * @param stateLabel
	 * @param i18nService
	 */
	public CommissionPojo(final Commission commission, 
			final String stateLabel,
			final I18nService i18nService) {
		super();
		this.commission = commission;
		this.adressePojo = null;
		this.state = (EtatVoeu) Etat.instanceState(stateLabel, i18nService);
		stateCurrent = stateLabel;
		stateCurrentOld = stateLabel;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommissionPojo#" + hashCode() + "[commission=[" + commission
				+ "],  [" + super.toString() + "]]";
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 * init all treatmentsPojo.
	 * @param vet
	 */
	public void initTreatmentsPojo(final Set<VersionEtapeDTO> vet) {
		treatmentsPojo = new HashSet<TraitementCmiPojo>();
		for (VersionEtapeDTO v : vet) {
			treatmentsPojo.add(new TraitementCmiPojo(v));
		}
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return String
	 */
	public String getShortLibCom() {
		return Utilitaires.limitStrLength(commission.getLibelle(),
                Constantes.STR_LENGTH_LIMIT_SMALL);
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
	public void setCommission(final Commission commission) {
		this.commission = commission;
	}

	/**
	 * @return the adressePojo
	 */
	public AdressePojo getAdressePojo() {
		return adressePojo;
	}

	/**
	 * @param adressePojo the adressePojo to set
	 */
	public void setAdressePojo(final AdressePojo adressePojo) {
		this.adressePojo = adressePojo;
	}


	/**
	 * @return the state
	 */
	public EtatVoeu getState() {
		return state;
	}


	/**
	 * @param state the state to set
	 */
	public void setState(final EtatVoeu state) {
		this.state = state;
	}
	
	
	/**
	 * @return the stateCurrentOld
	 */
	public String getStateCurrentOld() {
		return stateCurrentOld;
	}


	/**
	 * @param stateCurrentOld the stateCurrentOld to set
	 */
	public void setStateCurrentOld(final String stateCurrentOld) {
		this.stateCurrentOld = stateCurrentOld;
	}
	
	
	/**
	 * @return the stateCurrent
	 */
	public String getStateCurrent() {
		return stateCurrent;
	}


	/**
	 * @param stateCurrent the stateCurrent to set
	 */
	public void setStateCurrent(final String stateCurrent) {
		this.stateCurrent = stateCurrent;
	}


	/**
	 * @return the treatmentsPojo
	 */
	public Set<TraitementCmiPojo> getTreatmentsPojo() {
		return treatmentsPojo;
	}


	/**
	 * @param treatmentsPojo the treatmentsPojo to set
	 */
	public void setTreatmentsPojo(final Set<TraitementCmiPojo> treatmentsPojo) {
		this.treatmentsPojo = treatmentsPojo;
	}


	/**
	 * @return the contactCommission
	 */
	public ContactCommission getContactCommission() {
		return contactCommission;
	}


	/**
	 * @param contactCommission the contactCommission to set
	 */
	public void setContactCommission(final ContactCommission contactCommission) {
		this.contactCommission = contactCommission;
	}


	/**
	 * @return the flagWithoutTrtActive
	 */
	public Boolean getFlagWithoutTrtActive() {
		return flagWithoutTrtActive;
	}


	/**
	 * @param flagWithoutTrtActive the flagWithoutTrtActive to set
	 */
	public void setFlagWithoutTrtActive(final Boolean flagWithoutTrtActive) {
		this.flagWithoutTrtActive = flagWithoutTrtActive;
	}


	/**
	 * @return the flagWithSomeTrtInactive
	 */
	public Boolean getFlagWithSomeTrtInactive() {
		return flagWithSomeTrtInactive;
	}


	/**
	 * @param flagWithSomeTrtInactive the flagWithSomeTrtInactive to set
	 */
	public void setFlagWithSomeTrtInactive(final Boolean flagWithSomeTrtInactive) {
		this.flagWithSomeTrtInactive = flagWithSomeTrtInactive;
	}


}
