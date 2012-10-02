/**
*CRI - Universite de Rennes1 - 57SI-OPI - 2008
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatNull;
import org.esupportail.opi.domain.beans.parameters.PieceJustificative;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Conversions;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author tducreux
 *
 */
public class MissingPiecePojo {

	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The individuPojo.
	 */
	private IndividuPojo individuPojo;
	
	/**
	 * lesCommissions.
	 */
	private Map<CommissionPojo, List<IndVoeuPojo>> commissions;

	/**
	 * All PJ by cmi.
	 */
	private Map<Commission, List<MissingPiece> > piecesForCmi;

	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public MissingPiecePojo() {
		super();
		commissions = new HashMap<CommissionPojo, List<IndVoeuPojo>>();
		piecesForCmi = new HashMap<Commission, List<MissingPiece>>();

	}
	

	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MissingPiecePojo#" + hashCode() + "[individuPojo =" + individuPojo 
					+ "], [commissions =" + commissions + "], [piecesForCmi = " + piecesForCmi 
					+ "]";
	}

	
	/*
	 ******************* METHODS ********************** */
	/**
	 * list of commissions.
	 * @return  Set<CommissionPojo> 
	 **/
	public Set<CommissionPojo> getCmiKeySet() {
		Set<CommissionPojo> cmiPojo = new TreeSet<CommissionPojo>(new ComparatorString(CommissionPojo.class));
		cmiPojo.addAll(commissions.keySet());
		return cmiPojo;
	}

	
	/**
	 * Init the list of commissions.
	 * @param parameterService 
	 * @param i18nService 
	 * @param domainService 
	 * @param idCommissionCherchee 
	 */
	public void initCommissions(
			final ParameterService parameterService,
			final I18nService i18nService,
			final DomainService domainService,
			final Integer idCommissionCherchee) {
		Map<Commission, Set<VersionEtapeDTO>> mapCmi = 
		 // TODO: remove hashset hack
			Utilitaires.getCmiForIndVoeux(new HashSet<Commission>(parameterService.getCommissions(true))
					, this.individuPojo.getIndVoeuxPojo(), 
					this.individuPojo.getCampagneEnServ(domainService));
		for (Commission cmi : mapCmi.keySet()) {
			if (idCommissionCherchee != null) {
				if (idCommissionCherchee.compareTo(cmi.getId()) == 0) {
					traitCommission(parameterService, i18nService, domainService, mapCmi, cmi);
				}
			} else {
				traitCommission(parameterService, i18nService, domainService, mapCmi, cmi);
			}
		}
	}
	/**
	 * traitement de l'init pour une commission.
	 * @param parameterService 
	 * @param i18nService 
	 * @param domainService 
	 * @param mapCmi 
	 * @param cmi 
	 */
	private void traitCommission(
			final ParameterService parameterService,
			final I18nService i18nService,
			final DomainService domainService,
			final Map<Commission, Set<VersionEtapeDTO>> mapCmi,
			final Commission cmi) {
		Set<VersionEtpOpi> vOpi = Conversions.convertVetInVetOpi(mapCmi.get(cmi));
		List<IndVoeuPojo> iList = new ArrayList<IndVoeuPojo>();
		String codeTypeTrait = null;
		for (VersionEtpOpi vet : vOpi) {
			for (IndVoeuPojo i : this.individuPojo.getIndVoeuxPojo()) {
				if (i.getIndVoeu().getLinkTrtCmiCamp()
						.getTraitementCmi().getVersionEtpOpi().equals(vet)) {
					iList.add(i);
					if (codeTypeTrait == null) {
						codeTypeTrait = i.getEtat().getCodeLabel();
					} else if (!codeTypeTrait.equals(i.getEtat().getCodeLabel())) {
						//probleme on ne prend pas l etat vide
						if (codeTypeTrait.equals(EtatNull.I18N_STATE)) {
							codeTypeTrait = i.getEtat().getCodeLabel();
						}
						
					}
				}
			}
		}
		//on ne prend pas le voeu de type TR
		if (codeTypeTrait != null && !codeTypeTrait.equals(EtatNull.I18N_STATE)) {
			this.commissions.put(new CommissionPojo(cmi, 
					iList.get(0).getEtat().getCodeLabel(),
					i18nService), iList);
		}
		
		List<MissingPiece> missP = 
			domainService.getMissingPiece(
					this.individuPojo.getIndividu(), cmi);
		
		List<PieceJustificative> listPJ = parameterService.getPiecesJ(vOpi, 
				Utilitaires.getCodeRIIndividu(this.individuPojo.getIndividu(),
						domainService).toString());
		List<MissingPiece> missPListForCmi = new ArrayList<MissingPiece>();
		for (PieceJustificative pi : listPJ) {
			Boolean inMissP = false;
			for (MissingPiece missingP : missP) {
				if (missingP.getPiece().equals(pi)) {
					inMissP = true;
					missPListForCmi.add(missingP);
					break;
				}
			}
			if (!inMissP) {
				missPListForCmi.add(new MissingPiece(
					cmi, this.individuPojo.getIndividu(), pi));
			}
		}
		
		piecesForCmi.put(cmi, missPListForCmi); 
	}
	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the individuPojo
	 */
	public IndividuPojo getIndividuPojo() {
		return individuPojo;
	}


	/**
	 * @param individuPojo the individuPojo to set
	 */
	public void setIndividuPojo(final IndividuPojo individuPojo) {
		this.individuPojo = individuPojo;
	}


	/**
	 * @return the commissions
	 */
	public Map<CommissionPojo, List<IndVoeuPojo>> getCommissions() {
		return commissions;
	}

	/**
	 * @param commissions the commissions to set
	 */
	public void setCommissions(final Map<CommissionPojo, List<IndVoeuPojo>> commissions) {
		this.commissions = commissions;
	}

	/**
	 * @return the piecesForCmi
	 */
	public Map<Commission, List<MissingPiece>> getPiecesForCmi() {
		return piecesForCmi;
	}

	/**
	 * @param piecesForCmi the piecesForCmi to set
	 */
	public void setPiecesForCmi(final Map<Commission, List<MissingPiece>> piecesForCmi) {
		this.piecesForCmi = piecesForCmi;
	}


	

	
	
}
