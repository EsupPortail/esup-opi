/**
 * CRI - Universite de Rennes1 - 57SI-CIES - 2007
 * http://sourcesup.cru.fr/projects/57si-cies/
 */
package org.esupportail.opi.web.beans.paginator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.esupportail.commons.dao.AbstractHibernateQueryPaginator;
import org.esupportail.commons.dao.HqlQueryPojo;
import org.esupportail.commons.dao.HqlUtils;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndRechPojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.SessionController;
import org.springframework.util.StringUtils;

import com.ibm.icu.text.SimpleDateFormat;

/**
 * @author tducreux
 *
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
	private IndRechPojo indRechPojo;
	
	/**
	 * The SessionController.
	 */
	private SessionController sessionController;
	

	

	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public IndividuPaginator() {
		super();
	}

	/**
	 * 
	 * @see org.esupportail.commons.web.beans.AbstractPaginator#reset()
	 */
	@Override
	public void reset() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("begin reset() de IndividuPaginator()");
		}
		initQueryLeft();
		super.reset();
		this.indRechPojo = new IndRechPojo();
	}
	
	/**
	 * 
	 */
	private void initListeRI() {
		// on initialise indRechPojo selon le contexte du Gestionnaire connecté 
		indRechPojo.setListeRI(new HashSet<RegimeInscription>()); 
		if (sessionController.getCurrentUser() != null 
				&& sessionController.getCurrentUser() instanceof Gestionnaire) { 
			Gestionnaire gest = (Gestionnaire) sessionController.getCurrentUser(); 
			int codeRI = gest.getProfile().getCodeRI(); 
			RegimeInscription regimeIns = sessionController.getRegimeIns().get(codeRI); 
			this.indRechPojo.getListeRI().add(regimeIns); 
			this.indRechPojo.setCanModifyRISearch(regimeIns.canModifyRISearch()); 
		} 	
	}

	/** 
	 * @see org.esupportail.commons.dao.AbstractHibernatePaginator#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.notNull(this.sessionController, 
				"property sessionController of class " + this.getClass().getName()
				+ " can not be null");
		reset();
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
	 * On recupere tous les individus meme sans voeux.
	 * Init the hqlQuery value.
	 */
	public void initQueryLeft() {
//		this.hqlQuery = HqlUtils.selectFromWhere("distinct i", 
//				"Individu i left join i.voeux as v", "i.temoinEnService = " + true);
		this.hqlQuery = HqlUtils.selectFromWhere("distinct i", 
				"Individu i left join i.voeux as v inner join i.campagnes as c", "i.temoinEnService = " + true);
	}

	/**
	 * On recupere tous les individus avec voeux.
	 * Initialise la valeur de l'hqlQuery.
	 */
	public void initQueryInner() {
//		this.hqlQuery = HqlUtils.selectFromWhere("distinct i", 
//				"Individu i inner join i.voeux as v", "i.temoinEnService = " + true);
		this.hqlQuery = HqlUtils.selectFromWhere("distinct i", 
				"Individu i inner join i.voeux as v inner join i.campagnes as c", "i.temoinEnService = " + true);
	}


	/**
	 * Filter all student from the commissions where the manager have rights.
	 * @param lesCommissions
	 * Set< Commission> : les commissions sur lesquelles l'utilisateur a des droits 
	 * @return String
	 */
	public String addFiltreAllUserCommission(final Set<Commission> lesCommissions) {
		StringBuilder hql = new StringBuilder();
		if (lesCommissions != null && !lesCommissions.isEmpty()) {
			hql.append(" AND v.linkTrtCmiCamp.traitementCmi in(SELECT trtCmi");
			hql.append(" FROM Commission c inner join c.traitementCmi as trtCmi where c.id in (");
			boolean firstCommision = true;	
			for (Commission commissionTraitee : lesCommissions) {
				if (firstCommision)	{
					hql.append(commissionTraitee.getId());
					firstCommision = false;
				} else {
					hql.append("," + commissionTraitee.getId());
				}
			}
			hql.append(")) ");
		} else {
			//TODO find the best idea
			//permet d'afficher une liste vide pour les user qui n'ont aucun droit
			//car v.versionEtpOpi ne peut pas etre null
			hql.append(" AND v.linkTrtCmiCamp = null ");
		}
		return hql.toString();

	}

	/**
	 * Filtre sur le régime d'inscription des étudiants.
	 * @return String
	 */
	public String addFiltreCodeRI() {
		StringBuilder hql = new StringBuilder();
		// Si la liste de régime n'est pas vide, on récupère les individus du ou des régimes
		// sinon, on renvoie une liste d'individus vide
		if (!getListeRI().isEmpty()) {
			hql.append(" AND c.temoinEnService = ")
				.append(true).append(" AND c.codeRI in(");
			boolean firstRI = true;	
			for (RegimeInscription ri : indRechPojo.getListeRI()) {
				if (firstRI)	{
					hql.append(ri.getCode());
					firstRI = false;
				} else {
					hql.append("," + ri.getCode());
				}
			}
			hql.append(")");
		} else {
			hql.append(" AND c = null");
		}
		return hql.toString();
	}
	

	/**
	 * Filter : all students.
	 */
	public void allStudentsFilter() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering filtreTousLesEtudiants()");
		}
		initQueryLeft();
		
		this.hqlQuery += addFiltreCodeRI();
		
		// sort by firstname and lastname
		this.hqlQuery += " order by i.nomPatronymique asc, i.prenom asc";

		if (LOG.isDebugEnabled()) {
			LOG.debug("leaving filtreAccueilGestionnaire() with hqlQuery = " + hqlQuery);
		}
	}

	/**
	 * Filtre : Recherche des etudiants (page accueil gestionnaire).
	 */
	public void filtreRechercheEtudiants() {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering filtreAccueilGestionnaire()");
		}
		initQueryLeft();
		StringBuilder hql = new StringBuilder();
		if (StringUtils.hasText(this.indRechPojo.getNumDossierOpiRecherche())) {
			hql.append(" and i.numDossierOpi like '%"); 
			hql.append(Utilitaires.transformLikeHQL(this.indRechPojo.getNumDossierOpiRecherche()));
			hql.append("%'");
		} 
		if (StringUtils.hasText(this.indRechPojo.getNomRecherche())) {
			hql.append(" and i.nomPatronymique like '%");
			hql.append(Utilitaires.transformLikeHQL(this.indRechPojo.getNomRecherche()));
			hql.append("%'");
		} 
		if (StringUtils.hasText(this.indRechPojo.getPrenomRecherche())) {
			hql.append(" and i.prenom like '%");
			hql.append(Utilitaires.transformLikeHQL(this.indRechPojo.getPrenomRecherche()));
			hql.append("%'");
		} 

		if (this.indRechPojo.getIdCmi() != null) {

			hql.append(" AND v.linkTrtCmiCamp.traitementCmi in(SELECT trtCmi");
			hql.append(" FROM Commission c inner join c.traitementCmi as trtCmi where c.id = ");
			hql.append(this.indRechPojo.getIdCmi()); 
			hql.append(")"); 
		}

		hql.append(addFiltreCodeRI());
		
		// sort by firstname and lastname

		hql.append(" order by i.nomPatronymique asc, i.prenom asc");
		
		hqlQuery += hql.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("end filtreAccueilGestionnaire() with hqlQuery : " + this.hqlQuery);
		}
	}

	/**
	 * Filtre : Recherche des etudiants pour la gestion des types de traitements.
	 * @param lesCommissions
	 * Set< Commission> : les commissions sur lesquelles l'utilisateur a des droits 
	 * @param codeTypeTreatment : exclue les voeux de ce type (if null not filter)
	 * @param filtreIndTraited : permet ou non le filtrage sur les individus ayant deja ete traite 
	 */
	public void filterInMannagedCmi(final Set<Commission> lesCommissions,
			final String codeTypeTreatment, final Boolean filtreIndTraited) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering filtreTypeTraitementEtudiants()");
		}

		initQueryInner();
		StringBuilder hql = new StringBuilder();
		if (StringUtils.hasText(this.indRechPojo.getNumDossierOpiRecherche())) {
			hql.append(" and i.numDossierOpi like '%"); 
			hql.append(Utilitaires.transformLikeHQL(
					this.indRechPojo.getNumDossierOpiRecherche()) + "%'");
		} 
		if (StringUtils.hasText(this.indRechPojo.getNomRecherche())) {
			hql.append(" and i.nomPatronymique like '%" + Utilitaires.transformLikeHQL(
					this.indRechPojo.getNomRecherche()) + "%'");
		} 
		if (StringUtils.hasText(this.indRechPojo.getPrenomRecherche())) {
			hql.append(" and i.prenom like '%" + Utilitaires.transformLikeHQL(
					this.indRechPojo.getPrenomRecherche()) + "%'");
		} 
		if (this.indRechPojo.getIdCmi() != null) {
			hql.append(" AND v.linkTrtCmiCamp.traitementCmi in(SELECT trtCmi");
			hql.append(" FROM Commission c inner join c.traitementCmi as trtCmi where c.id = ");
			hql.append(this.indRechPojo.getIdCmi() + ")"); 
		} else {
			hql.append(addFiltreAllUserCommission(lesCommissions));
		}


		if (this.indRechPojo.getDateCreationVoeuRecherchee() != null) {
			SimpleDateFormat s = new SimpleDateFormat(Constantes.ENGLISH_DATE_FORMAT);
			String lookForDate = s.format(indRechPojo.getDateCreationVoeuRecherchee());

			hql.append(" AND v.dateCreaEnr like '%" + lookForDate + "%'");

		}

		if (this.indRechPojo.getTypeDecRecherchee() != null) {
			hql.append(" AND (v in (SELECT a.indVoeu from Avis a WHERE a.result.id = ");
			hql.append(this.indRechPojo.getTypeDecRecherchee().getId());
			hql.append(" AND a.temoinEnService = true))"); 
		}

		if (this.indRechPojo.getCodeTrtCmiRecherchee() != null) {
			hql.append(" AND (v.linkTrtCmiCamp.traitementCmi in (SELECT trt " 
					+ "from TraitementCmi as trt WHERE trt.id = ");
			hql.append(this.indRechPojo.getCodeTrtCmiRecherchee());
			hql.append("))"); 
		}


		hql.append(hqlIndWishesFilter(codeTypeTreatment, filtreIndTraited));

		hql.append(addFiltreCodeRI());
		
		// sort by firstname and lastname
		hql.append(" order by i.nomPatronymique asc, i.prenom asc");
		hqlQuery += hql.toString();
		if (LOG.isDebugEnabled()) {
			LOG.debug("end filtreTypeTraitementEtudiants() with hqlQuery : " + this.hqlQuery);
		}
	}

	/**
	 * Filtre : Recherche des etudiants pour la gestion des types de traitements.
	 * Trouve tous les etudiants des commissions oe le gestionnaire e des droits
	 * @param lesCommissions
	 * Set< Commission> : les commissions sur lesquelles l'utilisateur a des droits 
	 * @param filtreIndTraited : permet ou non le filtrage sur les individus ayant deje ete traite
	 * @param codeTypeTreatment : exclue les voeux de ce type (if null not filter)
	 */
	public void filtreAllCommissionRights(final Set<Commission> lesCommissions, 
			final boolean filtreIndTraited, 
			final String codeTypeTreatment) {
		if (LOG.isDebugEnabled()) {
			LOG.debug("entering filtreAllCommissionRights(filtreIndTraited = " 
					+ filtreIndTraited 
					+ ", codeTypeTreatment= " + codeTypeTreatment);
		}

		initQueryInner();

		hqlQuery += addFiltreAllUserCommission(lesCommissions);

		hqlQuery += hqlIndWishesFilter(codeTypeTreatment, filtreIndTraited);

		hqlQuery += addFiltreCodeRI();
		
		// sort by firstname and lastname
		this.hqlQuery += " order by i.nomPatronymique asc, i.prenom asc";

		if (LOG.isDebugEnabled()) {
			LOG.debug("end filtreAllCommissionRights() with hqlQuery : " + this.hqlQuery);
		}
	}

	/**
	 * 
	 */
	/**
	 * Add the hql query for the treated individual.
	 * @param codeTypeTreatment for the indivudal with type treatment (if null not filter)
	 * @param filtreIndTraited for the treated individual
	 * @return String
	 */
	protected String hqlIndWishesFilter(final String codeTypeTreatment, final Boolean filtreIndTraited) {
		Boolean notNulCodTrt = StringUtils.hasText(codeTypeTreatment);
		if ((indRechPojo.getExcludeWishProcessed() && filtreIndTraited) 
				|| notNulCodTrt) {
			StringBuilder hql = new StringBuilder();
			hql.append(" AND exists(SELECT indV FROM IndVoeu as indV WHERE ");

			if (filtreIndTraited) {
				hql.append(" indV.haveBeTraited = " + !indRechPojo.getExcludeWishProcessed());
				if (notNulCodTrt) {
					hql.append(" AND indV.codTypeTrait != '" + codeTypeTreatment + "'");
				}
			} else if (notNulCodTrt) {
				hql.append(" indV.codTypeTrait != '" + codeTypeTreatment + "'");
			}


			hql.append(" AND indV.individu = i");
			hql.append(" AND indV in elements(i.voeux)) ");
			return hql.toString();
		}
		return "";
	}


	/**
	 * @return Set< RegimeInscription>
	 */
	public List<Integer> getListeRI() {
		if (indRechPojo.getListeRI() == null) {
			initListeRI();
		}
		List<Integer> l = new ArrayList<Integer>();
		for (RegimeInscription ri : indRechPojo.getListeRI()) {
			l.add(ri.getCode());
		}
		return l;
	}

	/**
	 * @param l
	 * set the listRI of indRechPojo
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
