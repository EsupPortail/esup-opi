package org.esupportail.opi.services.archives;

import static fj.data.IterableW.*;

import java.util.HashSet;
import java.util.Set;

import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import fj.F;

/**
 * @author ylecuyer
 * The archive methods implementation.
 */
public class ArchiveServiceImpl implements ArchiveService {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The domain service.
	 */
	private DomainService domainService;
	
	/**
	 * The Apogee domain service. 
	 */
	private DomainApoService domainApoService;
	
	/**
	 * Thhe parameter service.
	 */
	private ParameterService parameterService;
	
	/*
	 ******************* INIT ************************* */

	/**
	 * Bean constructor.
	 */
	public ArchiveServiceImpl() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(domainService, 
				"property domainService of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(domainApoService, 
				"property domainApoService of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(parameterService, 
				"property parameterService of class " + this.getClass().getName() 
				+ " can not be null");
	}

	
	/*
	 ******************* METHODS ********************** */

	/**
	 * @see org.esupportail.opi.services.archives.ArchiveService#archiveIndividu(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
	@Override
	public void archiveIndividu(final Individu ind) {
		// suppression des pièces manquantes
//		List<MissingPiece> listMP = new ArrayList<MissingPiece>(
//				ind.getMissingPieces());
//		ind.getMissingPieces().clear();
//
//		domainService.deleteMissingPiece(listMP, null);
		
		// on met hors service l'individu et tous ses voeux
		ind.setTemoinEnService(false);
		domainService.updateUser(ind);

	}

	/**
	 * @see org.esupportail.opi.services.archives.ArchiveService#addTraitementCmiToNewCamp(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi, 
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	@Override
	public void addTraitementCmiToNewCamp(final TraitementCmi trtCmi,
			final Campagne camp) {
		if (parameterService.getLinkTrtCmiCamp(trtCmi, camp) == null) {
			// on teste si on peut ajouter le traitement pour la nouvelle campagne
			Set<VersionEtapeDTO> etapes = new HashSet<VersionEtapeDTO>();
			// on récupère les étapes ouvertes au recrutement pour la nouvelle campagne
			etapes.addAll(domainApoService.getVersionEtapes(
					trtCmi.getVersionEtpOpi().getCodEtp(), null, 
					null, camp.getCodAnu()));
			Set<VersionEtpOpi> listeVET = new HashSet<VersionEtpOpi>();//Utilitaires.convertVetInVetOpi(etapes);
			listeVET.addAll(wrap(etapes).map(
			    new F<VersionEtapeDTO, VersionEtpOpi>() {
                    public VersionEtpOpi f(VersionEtapeDTO dto) {
                        return new VersionEtpOpi(dto);}}).toStandardList());
			if (listeVET.contains(trtCmi.getVersionEtpOpi())) {
				// le traitementCmi est ouvert au recrutement
				LinkTrtCmiCamp link = new LinkTrtCmiCamp();
				link.setTraitementCmi(trtCmi);
				link.setCampagne(camp);
				link.setTemoinEnService(true);
				// sauvegarde en base
				parameterService.addLinkTrtCmiCamp(link);
			}
		}
	}

	/**
	 * @see org.esupportail.opi.services.archives.ArchiveService#purgeTablesCamp(
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	public void purgeTablesCamp(final Campagne camp) {
		domainService.purgeMissingPieceCamp(camp);
		domainService.purgeIndividuDateCamp(camp);
		parameterService.purgeIndFormulaireCamp(camp);
	}
	
	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the domainService
	 */
	public DomainService getDomainService() {
		return domainService;
	}

	/**
	 * @param domainService the domainService to set
	 */
	public void setDomainService(final DomainService domainService) {
		this.domainService = domainService;
	}

	/**
	 * @return the domainApoService
	 */
	public DomainApoService getDomainApoService() {
		return domainApoService;
	}

	/**
	 * @param domainApoService the domainApoService to set
	 */
	public void setDomainApoService(final DomainApoService domainApoService) {
		this.domainApoService = domainApoService;
	}

	/**
	 * @return the parameterService
	 */
	public ParameterService getParameterService() {
		return parameterService;
	}

	/**
	 * @param parameterService the parameterService to set
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}

}
