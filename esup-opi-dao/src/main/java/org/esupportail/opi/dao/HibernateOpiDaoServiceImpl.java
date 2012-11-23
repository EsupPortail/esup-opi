package org.esupportail.opi.dao;

import static fj.data.Option.iif;

import java.io.Serializable;
import java.util.List;

import org.esupportail.commons.dao.AbstractJdbcJndiHibernateDaoService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesAnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesDiplomeAnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.DomaineAnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.formation.GrpTypDipCorresp;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;

import fj.data.Option;

/**
 * @author llevague
 *
 */
public class HibernateOpiDaoServiceImpl extends AbstractJdbcJndiHibernateDaoService implements OpiDaoService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -2277921831554679364L;
	
	/**
	 * False value for character witness.
	 */
	private static final String FALSE = "N";

	/**
	 * True value for character witness.
	 */
	private static final String TRUE = "O";
	
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());

	//////////////////////////////////////////////////////////////
	// ClesDiplomeAnnuForm
	//////////////////////////////////////////////////////////////

	@Override
	@SuppressWarnings("unchecked")
	public List<ClesDiplomeAnnuForm> getCodesDiplomes(final String codeMotClef) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCodesDiplomes( " + codeMotClef + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(ClesDiplomeAnnuForm.class);
		criteria.add(Restrictions.eq("codCles", codeMotClef));

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm(final Option<String> codCle) {
		if (log.isDebugEnabled()) {
			log.debug("entering getClesDiplomeAnnuForm( " + codCle + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(ClesDiplomeAnnuForm.class);
		for (String cod : codCle)
			criteria.add(Restrictions.eq("codCles", cod));

		return getHibernateTemplate().findByCriteria(criteria);
	}

	//////////////////////////////////////////////////////////////
	// GrpTypDip
	//////////////////////////////////////////////////////////////

	@Override
	@SuppressWarnings("unchecked")
	public List<GrpTypDip> getGrpTypDip(final Option<String> temEnSveGrpTpd) {
		if (log.isDebugEnabled()) {
			log.debug("entering getGrpTypDip( " + temEnSveGrpTpd + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(GrpTypDip.class);
		for (String tm : temEnSveGrpTpd)
			if (tm.equals(TRUE) || tm.equals(FALSE))
				criteria.add(Restrictions.eq("temEnSveGrpTpd", tm));

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * 
	 * @return liste de diplomes correspondants
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<GrpTypDipCorresp> getGrpTypDipCorresp() {
		DetachedCriteria criteria = DetachedCriteria.forClass(GrpTypDipCorresp.class);
		criteria.addOrder(Order.asc("codTpdEtb"));
		return getHibernateTemplate().findByCriteria(criteria);
	}

    /**
     *
     * @return liste de groupes diplomes
     */
    @Override
    @SuppressWarnings("unchecked")
    public List<GrpTypDip> getGrpTypDip() {
        DetachedCriteria criteria = DetachedCriteria.forClass(GrpTypDip.class);
        return getHibernateTemplate().findByCriteria(criteria);
    }

    /**
     *
     * @return liste de groupes diplomes
     */
    @Override
    @SuppressWarnings("unchecked")
    public GrpTypDip getGrpTypDip(final String codTyp) {
        DetachedCriteria criteria = DetachedCriteria.forClass(GrpTypDip.class);
        criteria.add(Restrictions.eq("codGrpTpd", codTyp));
        return (GrpTypDip) getHibernateTemplate().findByCriteria(criteria).get(0);
    }

	//////////////////////////////////////////////////////////////
	// ClesDiplomeAnnuForm
	//////////////////////////////////////////////////////////////

	@Override
	@SuppressWarnings("unchecked")
	public List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm(
			final List<String> listCodDip) {
		if (log.isDebugEnabled()) {
			log.debug("entering getClesDiplomeAnnuForm( " + listCodDip + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(ClesDiplomeAnnuForm.class);
		if (!listCodDip.isEmpty()) {
			criteria.add(Restrictions.in("codDip", listCodDip));
		}
		return getHibernateTemplate().findByCriteria(criteria);
	}

	//////////////////////////////////////////////////////////////
	// Domaine2AnnuForm
	//////////////////////////////////////////////////////////////

	@Override
	@SuppressWarnings("unchecked")
	public List<Domaine2AnnuForm> getDomaine2AnnuForm(final List<String> listCodDom,
					final Option<String> codLang, final Option<String> temSveDom) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDomaine2AnnuFormm( " + listCodDom + ", " 
								+ codLang + ", " + temSveDom + " )");
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Domaine2AnnuForm.class);
		if (!listCodDom.isEmpty()) {
			criteria.add(Restrictions.in("codDom", listCodDom));
		}
		for (String cl : codLang)
			criteria.add(Restrictions.eq("codLang", cl));
		for (String tm : temSveDom) {
			DetachedCriteria criteria2 = criteria.createCriteria("domaineAnnuForm");
			criteria2.add(Restrictions.eq("temSveDom", tm));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	
	//////////////////////////////////////////////////////////////
	// Cles2AnnuForm
	//////////////////////////////////////////////////////////////

	@Override
	@SuppressWarnings("unchecked")
	public List<Cles2AnnuForm> getCles2AnnuForm(final List<String> listCodCle,
					final Option<String> codLang, final Option<String> temEnSveCles) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCles2AnnuForm( " + listCodCle + ", " + codLang + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Cles2AnnuForm.class);
		if (!listCodCle.isEmpty()) {
			criteria.add(Restrictions.in("id.codCles", listCodCle));
		}
		for (String cod : codLang)
			criteria.add(Restrictions.eq("id.codLang", cod));
		for (String tm : temEnSveCles) {
			DetachedCriteria criteria2 = criteria.createCriteria("clesAnnuForm");
			criteria2.add(Restrictions.eq("temSveCles", tm));
		}
		
		return getHibernateTemplate().findByCriteria(criteria);
	}

	
	@SuppressWarnings("unchecked")
	private List<Cles2AnnuForm> getCles2AnnuForm(String field, Option<String> value, Option<String> lang) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCles2AnnuForm( " + value.toString() + " )");
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Cles2AnnuForm.class);
		for (String cod : value)
			criteria.add(Restrictions.eq(field, cod));
		for (String cod : lang)
			criteria.add(Restrictions.eq("id.codLang", cod));

		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	public List<Cles2AnnuForm> getCles2AnnuForm(Option<String> codCle) {
		return getCles2AnnuForm("id.codCles", codCle, Option.<String>none());
	}

	@Override
	public List<Cles2AnnuForm> getCles2AnnuFormByCodDom(Option<String> codDom, Option<String> locale) {
		return getCles2AnnuForm("clesAnnuForm.codDom", codDom, locale);
	}
	
	/**
	 * 
	 * @return liste de cles des formations
	 */	
	@Override
	@SuppressWarnings("unchecked")
	public List<ClesAnnuForm> getClesAnnuForm() {
		DetachedCriteria criteria = DetachedCriteria.forClass(ClesAnnuForm.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	@Override
	@SuppressWarnings("unchecked")
	public List<Domaine2AnnuForm> getDomaine2AnnuForm(final Option<String> codDom) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDomaine2AnnuFormm( " + codDom + " )");
		}
		
		DetachedCriteria criteria = DetachedCriteria.forClass(Domaine2AnnuForm.class);
		for (String cd : codDom)
			criteria.add(Restrictions.eq("codDom", cd));
		
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * 
	 * @return liste de domaines de formations
	 */
	@Override
	@SuppressWarnings("unchecked")
	public List<DomaineAnnuForm> getDomaineAnnuForm() {
		DetachedCriteria criteria = DetachedCriteria.forClass(DomaineAnnuForm.class);
		return getHibernateTemplate().findByCriteria(criteria);
	}
	
	/**
	 * 
	 * @return domaines de formations
	 */
	@Override
	@SuppressWarnings("unchecked")
	public Option<DomaineAnnuForm> getDomaineAnnuForm(Option<String> codDom) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DomaineAnnuForm.class);
		for (String cod : codDom)
			criteria.add(Restrictions.eq("codDom", cod));

		List<DomaineAnnuForm> listDom = getHibernateTemplate().findByCriteria(criteria);
		return iif(!listDom.isEmpty(), listDom.get(0));
	}

	
	//////////////////////////////////////////////////////////////
	// CRUD
	//////////////////////////////////////////////////////////////
	
	@Override
	public <T> Serializable save(final T o) {
		return getHibernateTemplate().save(o);
	}

	@Override
	public <T> void saveOrUpdate(final T o) {
		getHibernateTemplate().saveOrUpdate(o);
	}

	@Override
	public <T> void update(final T o) {
		updateObject(o);
	}

	@Override
	public <T> void delete(final T o) {
		deleteObject(o);
	}
}
