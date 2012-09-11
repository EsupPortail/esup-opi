/**
* CRI - Universite de Rennes1 - 57SI-CIES - 2007
* http://sourcesup.cru.fr/projects/57si-cies/
*/
package org.esupportail.opi.web.beans.paginator;


import org.esupportail.commons.dao.AbstractHibernateQueryPaginator;
import org.esupportail.commons.dao.HqlQueryPojo;
import org.esupportail.commons.dao.HqlUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.web.beans.pojo.GestRechPojo;
import org.springframework.util.StringUtils;



/**
 * @author cleprous
 *
 */
public class GestionnairePaginator extends AbstractHibernateQueryPaginator<Gestionnaire> {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3126476039263576088L;

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * A logger.
	 */
	private static final Logger LOG = new LoggerImpl(GestionnairePaginator.class);
	
    /**
     * Valeur de la query Hql executee.
     */
    private String hqlQuery;
    
    /**
     * The values of the search.
     */
    private GestRechPojo gestRechPojo;
    
    /*
	 ******************* INIT ************************* */

    /**
	 * Constructors.
	 */
	public GestionnairePaginator() {
		super();
		initQuery();
		this.gestRechPojo = new GestRechPojo();
	}

	
	/**
	 * 
	 * @see org.esupportail.commons.web.beans.AbstractPaginator#reset()
	 */
	@Override
	public void reset() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("begin reset() de GestionnairePaginator()");
		}
		super.reset();
		this.gestRechPojo = new GestRechPojo();
	}
	
	/*
	 ******************* METHODS ********************** */

	/**
	 * @see org.esupportail.commons.dao.AbstractHibernateQueryPaginator#getQueryString()
	 */
	@Override
	protected String getQueryString() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering getQueryString with query = " + getHqlQuery());
		}
		return getHqlQuery();
	}

	/**
	 * Initialise la valeur de l'hqlQuery.
	 */
	public void initQuery() {
		this.hqlQuery = HqlUtils.selectFromWhere("g", "Gestionnaire g", 
				"g.temoinEnService = " + true + " order by g.displayName asc");
	}

	/**
	 * Initialise la valeur de l'hqlQuery pour une requete complexe.
	 */
	public void initQueryForRequete() {
		this.hqlQuery = HqlUtils.selectFromWhere("g", "Gestionnaire g", 
				"g.temoinEnService = " + true );
	}

	
	/**
	 * Filtre the gestionaire by name and prenom.
	 */
	public void filtreSearchGestionnaire() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering filtreSearchGestionnaire()");
		}
		initQueryForRequete();

		if (StringUtils.hasText(this.gestRechPojo.getNom())) {
			this.hqlQuery += " and g.nomUsuel like '%" 
				+ this.gestRechPojo.getNom().toUpperCase() + "%'";
		} 

		if (StringUtils.hasText(this.gestRechPojo.getPrenom())) {
			this.hqlQuery += " and g.prenom like '%" 
				+ this.gestRechPojo.getPrenom().toUpperCase() + "%'";
		} 
		this.hqlQuery += " order by g.nomPatronymique asc, g.prenom asc";
		
		if (LOG.isDebugEnabled()) {
			LOG.debug("end filtreSearchGestionnaire() with hqlQuery : " + this.hqlQuery);
		}

	}
	
	/**
	 * Filtre les User par type.
	 * @param idType int
	 */
//	public void filtrebyType(final int idType) {
//		//idType + " in elements (u.types.id)"
//		this.hqlQuery = HqlUtils.selectFromWhere("u", "User u", "");
//		String queryForType = HqlUtils.selectFromWhere("t", "Type t", "t.id = " + idType);
//		this.hqlQuery += HqlUtils.and("exists(" + queryForType 
//				+ " AND t in elements(u.types))", "u." + COND_TEM_SVE);
//		
//		if (LOG.isDebugEnabled()) {
//			LOG.debug("leaving filtrebyType with hqlQuery = " + this.hqlQuery);
//		}
//	}
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the hqlQuery
	 */
	public String getHqlQuery() {
		return hqlQuery;
	}

	/**
	 * @param hqlQuery the hqlQuery to set
	 */
	public void setHqlQuery(final String hqlQuery) {
		this.hqlQuery = hqlQuery;
	}

	/**
	 * @return the gestRechPojo
	 */
	public GestRechPojo getGestRechPojo() {
		return gestRechPojo;
	}

	/**
	 * @param gestRechPojo the gestRechPojo to set
	 */
	public void setGestRechPojo(final GestRechPojo gestRechPojo) {
		this.gestRechPojo = gestRechPojo;
	}


    @Override
    protected HqlQueryPojo getHqlQueryPojo() {
        // TODO Auto-generated method stub
        return null;
    }
	
}
