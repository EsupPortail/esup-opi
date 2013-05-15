/**
 * CRI - Universite de Rennes1 - 57SI-CIES - 2007
 * http://sourcesup.cru.fr/projects/57si-cies/
 */
package org.esupportail.opi.web.beans.paginator;

import java.util.ArrayList;
import java.util.List;

import org.esupportail.commons.dao.AbstractHibernateQueryPaginator;
import org.esupportail.commons.dao.HqlQueryPojo;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndRechPojo;
import org.esupportail.opi.web.controllers.SessionController;

/**
 * TODO : get rid of that class as it is not used as a paginator anymore but as a mere data container
 *
 * @author tducreux
 */
public class IndividuPaginator extends AbstractHibernateQueryPaginator<Individu> {

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
     * Valeur de la query Hql executee.
     */
    private String hqlQuery;


    /**
     * Paramters for the search.
     */
    private IndRechPojo indRechPojo = new IndRechPojo();

    /**
     * The SessionController.
     */
    private SessionController sessionController;
	

    public IndividuPaginator() {}

    @Override
    public void reset() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("begin reset() de IndividuPaginator()");
        }
        super.reset();
        this.indRechPojo = new IndRechPojo();
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
        Assert.notNull(this.sessionController,
                "property sessionController of class " + this.getClass().getName()
                        + " can not be null");
    }

    @Override
    protected String getQueryString() {
        if (LOG.isDebugEnabled()) {
            LOG.debug("entering getQueryString with query = " + getHqlQuery());
        }
        return getHqlQuery();
    }

    /**
     * @return Set< RegimeInscription>
     */
    public List<Integer> getListeRI() {
        List<Integer> l = new ArrayList<Integer>();
        for (RegimeInscription ri : indRechPojo.getListeRI()) {
            l.add(ri.getCode());
        }
        return l;
    }

    /**
     * @param l set the listRI of indRechPojo
     */
    public void setListeRI(final List<Integer> l) {
        indRechPojo.getListeRI().clear();
        for (Integer o : l) {
            indRechPojo.getListeRI().add(
                    sessionController.getRegimeIns().get(o));
        }
    }



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
     * @return the indRechPojo
     */
    public IndRechPojo getIndRechPojo() {
        return indRechPojo;
    }

    /**
     * @param indRechPojo the indRechPojo to set
     */
    public void setIndRechPojo(final IndRechPojo indRechPojo) {
        this.indRechPojo = indRechPojo;
    }

    /**
     * @return the sessionController
     */
    public SessionController getSessionController() {
        return sessionController;
    }

    /**
     * @param sessionController the sessionController to set
     */
    public void setSessionController(final SessionController sessionController) {
        this.sessionController = sessionController;
    }

    @Override
    protected HqlQueryPojo getHqlQueryPojo() {
        // TODO Auto-generated method stub
        return null;
    }

}
