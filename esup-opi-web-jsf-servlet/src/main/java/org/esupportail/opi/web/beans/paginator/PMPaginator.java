/**
 * CRI - Universite de Rennes1 - 57SI-CIES - 2007
 * http://sourcesup.cru.fr/projects/57si-cies/
 */
package org.esupportail.opi.web.beans.paginator;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.pojo.MissingPiecePojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;

/**
 * @author tducreux
 *
 */
public class PMPaginator extends IndividuPaginator {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3126476039263576088L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(IndividuPaginator.class);



	/**
	 * The individuals pojo visible.
	 */
	private List<MissingPiecePojo> missingPiecePojos;


	/**
	 * default value false.
	 * if true MissingPiecePojo must be reload
	 */
	private Boolean forceReload;

	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public PMPaginator() {
		super();
	}

	/**
	 * 
	 * @see org.esupportail.commons.web.beans.AbstractPaginator#reset()
	 */
	@Override
	public void reset() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("begin reset() de PMPaginator()");
		}
		super.reset();
		String hql = getHqlQuery() + hqlIndWishesFilter("TR", false);
		setHqlQuery(hql);
		this.missingPiecePojos = new ArrayList<MissingPiecePojo>();
		forceReload = false;
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
	 * @see org.esupportail.commons.web.beans.AbstractPaginator#forceReload()
	 */
	@Override
	public void forceReload() {
		forceReload = true;
		super.forceReload();
	}


	/** 
	 * @see org.esupportail.commons.dao.AbstractHibernateQueryPaginator#getQueryString()
	 */
	@Override
	protected String getQueryString() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering getQueryString with query = " 
					+ this.getHqlQuery());
		}
		return this.getHqlQuery();
	}

	/**
	 * @return the missingPiecePojos
	 */
	public List<MissingPiecePojo> getMissingPiecePojos() {
		if (!forceReload && !missingPiecePojos.isEmpty()) {
			//on recharge pas
			return this.missingPiecePojos;
		}
		this.missingPiecePojos.clear();
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
					getSessionController().getParameterService().getCalendarRdv(), null, false);
		for (IndividuPojo iP : indPojo) {
			MissingPiecePojo m = new MissingPiecePojo();
			m.setIndividuPojo(iP);
			m.initCommissions(
					getSessionController().getParameterService(),
					getSessionController().getI18nService(),
					getSessionController().getDomainService(),
					this.getIndRechPojo().getIdCmi());
			if (!m.getCommissions().isEmpty()) {
				this.missingPiecePojos.add(m);
			}
		}
		forceReload = false;
		return this.missingPiecePojos;
	}



	/*
	 ******************* ACCESSORS ******************** */


	/**
	 * @param missingPiecePojos the missingPiecePojos to set
	 */
	public void setMissingPiecePojos(final List<MissingPiecePojo> missingPiecePojos) {
		this.missingPiecePojos = missingPiecePojos;
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



}
