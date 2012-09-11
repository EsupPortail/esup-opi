/**
 * 
 */
package org.esupportail.opi.web.beans.paginator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorIndLC;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author cleprous
 *
 */
public class IndividuPojoPaginator extends IndividuPaginator {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5603026181273564012L;
	
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(IndividuPojoPaginator.class);
	
	/**
	 * The individuals pojo visible.
	 */
	private List<IndividuPojo> individuPojos;

	
	/**
	 * default value false.
	 * if true individusPojo must be reload
	 */
	private Boolean forceReload;
	
	
	/**
	 * true if the type of decision is LC.
	 * Default value : false.
	 */
	private Boolean isUsingLC;

	/**
	 * true if the type of decision is DEF.
	 * Default value : false.
	 */
	private Boolean isUsingDEF;
	
	/**
	 * true if the type of decision is Preselection.
	 * Default value : false.
	 */
	private Boolean isUsingPreselect;
	
	/**
	 * true if use  individuPojos list or not release the other list pojo in forceReload.
	 * Default value false.
	 */
	private Boolean useIndividuPojo;

	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public IndividuPojoPaginator() {
		super();
	}
	
	/**
	 * 
	 * @see org.esupportail.commons.web.beans.AbstractPaginator#reset()
	 */
	@Override
	public void reset() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("begin reset() de IndividuPojoPaginator()");
		}
		super.reset();
		resetNotSuper(true);
	}
	
	
	/**
	 * Not use super.reset.
	 * @param doNewList if true do new instance for list pojo.
	 */
	public void resetNotSuper(final Boolean doNewList) {
		if (doNewList) {
			this.individuPojos = new ArrayList<IndividuPojo>();
		}
		forceReload = false;
		isUsingLC = false;
		isUsingDEF = false;
		isUsingPreselect = false;
		useIndividuPojo = false;
	}
	
	
	/** 
	 * @see org.esupportail.commons.web.beans.AbstractPaginator#forceReload()
	 */
	@Override
	public void forceReload() {
		super.forceReload();
		forceReload = true;
		if (!useIndividuPojo) {
			updateIndPojosWithWishForOneCmi();
		}
		
	}
	
	/** 
	 * @see org.esupportail.commons.dao.AbstractHibernatePaginator#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		reset();
	}
	
	/*
	 ******************* METHODS ********************** */

	/**
	 * Return the list of Individu Converted to IndividuPojo.
	 * @return List< IndividuPojo> 
	 */
	public List<IndividuPojo> getIndividuPojos() {
		if (!forceReload && !individuPojos.isEmpty()) {
			//on recharge pas
			return individuPojos;
		}
		individuPojos.clear();
		List<IndividuPojo> indPojo = 
			Utilitaires.convertIndInIndPojo(getVisibleItems(), 
					getSessionController().getParameterService(), 
					getSessionController().getI18nService(), 
					getSessionController().getBusinessCacheService(), 
					Utilitaires.getListCommissionsByRight(
							(Gestionnaire) getSessionController().getCurrentUser(), 
							getSessionController().getDomainApoService(),
							getSessionController().getParameterService(), true),
					null, getSessionController().getParameterService().getTypeTraitements(),
					getSessionController().getParameterService().getCalendarRdv(),
					null, getIndRechPojo().getExcludeWishProcessed());

		for (IndividuPojo iP : indPojo) {
			iP.initIndCursusScolPojo(
					getSessionController().getBusinessCacheService(),
					getSessionController().getI18nService());
		}
		individuPojos = indPojo;
		forceReload = false;
		return individuPojos;
	}

	/**
	 * Return the list of Individu Converted to IndividuPojo.
	 * For display just wishes to selected commission.
	 * IndCursusPojo is not initialized 
	 * @return List< IndividuPojo> 
	 */
	public List<IndividuPojo> getIndPojosWithWishForOneCmi() {
		if (!forceReload && !individuPojos.isEmpty()) {
			//on recharge pas
			return individuPojos;
		}
		// filtrage sur les etapes de la commission
		Set<Commission> cmi = new HashSet<Commission>();
		Commission comm = null;
		if (getIndRechPojo().getIdCmi() != null) {
			comm = getSessionController().getParameterService()
			.getCommission(getIndRechPojo().getIdCmi(), null);
			cmi.add(comm);
		} else {
			cmi = Utilitaires.getListCommissionsByRight(
					(Gestionnaire) getSessionController().getCurrentUser(), 
					getSessionController().getDomainApoService(),
					getSessionController().getParameterService(), true);
		}

		// filtrage sur le type de decision
		Set<TypeDecision> typeD = new HashSet<TypeDecision>();
		if (getIndRechPojo().getTypeDecRecherchee() != null) {
			typeD.add(getIndRechPojo().getTypeDecRecherchee());
		} else {
			typeD = null;
		}

		// filtrage sur une etape
		Set<VersionEtapeDTO> versionsEtp = new HashSet<VersionEtapeDTO>();
		if (getIndRechPojo().getCodeTrtCmiRecherchee() != null && comm != null) {
			TraitementCmi trtCmi = getSessionController().getParameterService().getTraitementCmi(
					getIndRechPojo().getCodeTrtCmiRecherchee());
			if (comm.getTraitementCmi().contains(trtCmi)) {
				VersionEtpOpi vetOpi = trtCmi.getVersionEtpOpi();
				VersionEtapeDTO vet = getSessionController().getBusinessCacheService().getVersionEtape(
						vetOpi.getCodEtp(), vetOpi.getCodVrsVet());
				versionsEtp.add(vet);
			} else {
				versionsEtp = null;
			}
		} else {
			versionsEtp = null;
		}
		List<IndividuPojo> indPojo = 
			Utilitaires.convertIndInIndPojo(getVisibleItems(), 
					getSessionController().getParameterService(), 
					getSessionController().getI18nService(), 
					getSessionController().getBusinessCacheService(),
					cmi, typeD, getSessionController().getParameterService().getTypeTraitements(), 
					getSessionController().getParameterService().getCalendarRdv(),
					versionsEtp, false);

		// tri sur le rang si on a choisi une vet pour les LC
		if (getIndRechPojo().getCodeTrtCmiRecherchee() != null) {
			Collections.sort(indPojo, new ComparatorIndLC());
		}
		individuPojos = indPojo;
		forceReload = false;
		return individuPojos;
	}

	/**
	 * update for the decision type.
	 */
	public void updateIndPojosWithWishForOneCmi() {
		for (IndividuPojo iPojo : getIndPojosWithWishForOneCmi()) {
			iPojo.setIsUsingLC(isUsingLC);
			iPojo.setIsUsingDEF(isUsingDEF);
			for (IndVoeuPojo voeuPojo : iPojo.getIndVoeuxPojo()) {
				Avis a = new Avis();
				voeuPojo.setNewAvis(a);
				voeuPojo.setIsUsingLC(isUsingLC);
				voeuPojo.setIsUsingDEF(isUsingDEF);
				if (isUsingPreselect) {
					// cas de la selection de la preselection
					//charge le commentaire
					TraitementCmi t = voeuPojo.getIndVoeu().getLinkTrtCmiCamp().getTraitementCmi();
					//init proxy HIB
//					sessionController.getDomainService().initOneProxyHib(
//							t, t.getSelection(), Selection.class);
					if (t.getSelection() != null) {
						voeuPojo.getNewAvis().setCommentaire(
								t.getSelection().getComment());
					}
				}
				if (isUsingPreselect) {
					// cas de la selection de la preselection
					//charge le commentaire
					TraitementCmi t = voeuPojo.getIndVoeu().getLinkTrtCmiCamp().getTraitementCmi();
					//init proxy HIB
//					sessionController.getDomainService().initOneProxyHib(
//							t, t.getSelection(), Selection.class);
					if (t.getSelection() != null) {
						voeuPojo.getNewAvis().setCommentaire(
								t.getSelection().getComment());
					}
				} 
			}
		}
	}
	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	
	
	/**
	 * @param pojo the iPojo to set
	 */
	public void setIndividuPojos(final List<IndividuPojo> pojo) {
		individuPojos = pojo;
	}

	/**
	 * @return the forceReload
	 */
	public Boolean getForceReload() {
		return forceReload;
	}

	/**
	 * @param forceReload the forceReload to set
	 */
	public void setForceReload(final Boolean forceReload) {
		this.forceReload = forceReload;
	}

	/**
	 * @return the isUsingLC
	 */
	public Boolean getIsUsingLC() {
		return isUsingLC;
	}

	/**
	 * @param isUsingLC the isUsingLC to set
	 */
	public void setIsUsingLC(final Boolean isUsingLC) {
		this.isUsingLC = isUsingLC;
	}

	/**
	 * @return the isUsingDEF
	 */
	public Boolean getIsUsingDEF() {
		return isUsingDEF;
	}

	/**
	 * @param isUsingDEF the isUsingDEF to set
	 */
	public void setIsUsingDEF(final Boolean isUsingDEF) {
		this.isUsingDEF = isUsingDEF;
	}

	/**
	 * @return the isUsingPreselect
	 */
	public Boolean getIsUsingPreselect() {
		return isUsingPreselect;
	}

	/**
	 * @param isUsingPreselect the isUsingPreselect to set
	 */
	public void setIsUsingPreselect(final Boolean isUsingPreselect) {
		this.isUsingPreselect = isUsingPreselect;
	}

	/**
	 * @return the useIndividuPojo
	 */
	public Boolean getUseIndividuPojo() {
		return useIndividuPojo;
	}

	/**
	 * @param useIndividuPojo the useIndividuPojo to set
	 */
	public void setUseIndividuPojo(final Boolean useIndividuPojo) {
		this.useIndividuPojo = useIndividuPojo;
	}

}
