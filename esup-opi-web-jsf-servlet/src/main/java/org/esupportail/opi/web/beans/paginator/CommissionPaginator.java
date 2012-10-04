/**
* CRI - Universite de Rennes1 - 57SI-CIES - 2007
* http://sourcesup.cru.fr/projects/57si-cies/
*/
package org.esupportail.opi.web.beans.paginator;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.esupportail.commons.services.paginator.ListPaginator;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.web.beans.pojo.CommisionRechPojo;
import org.esupportail.opi.web.beans.pojo.CommissionPojo;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.springframework.util.StringUtils;

/**
 * @author cleprous
 *
 */
public class CommissionPaginator extends ListPaginator<CommissionPojo> {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 2889762688005715110L;
	
	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * A logger.
	 */
//	private static final Logger LOG = new LoggerImpl(CommissionPaginator.class);
	
	/**
     * The values of the search.
     */
    private CommisionRechPojo commissionRechPojo;
    
    /**
     * The DomainService.
     */
    private ParameterService parameterService;
    
    /**
     * domainApoService.
     */
	private DomainApoService domainApoService;
	
	/**
	 * currentGest.
	 */
	private Gestionnaire currentGest;
     /*
	 ******************* INIT ************************* */

    /**
	 * Constructors.
     * @param parameterService 
	 */
	public CommissionPaginator() {
		super();
		this.commissionRechPojo = new CommisionRechPojo();
		this.currentGest = new Gestionnaire();
	}

	
	/** 
	 * @see org.esupportail.commons.web.beans.AbstractPaginator#reset()
	 */
	@Override
	public void reset() {
		// TODO Auto-generated method stub
		super.reset();
	}	
	
	
	/**
	 * Look for the commissions.
	 */
	public void lookForCommissions() {
		reset();
		forceReload();
	}
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * Access to the Commission's data.
	 */
	@Override
	protected List<CommissionPojo> getData() {
		List <Commission> lesCommissions = new ArrayList<Commission>();
		
		Set <Commission> s = Utilitaires.getListCommissionsByRight(
							currentGest, 
							this.domainApoService,
							this.parameterService, null);
		
		Integer codRI = currentGest.getProfile().getCodeRI();
		
		// must a filtre be used  ?
		if (!StringUtils.hasText(this.commissionRechPojo.getCode())
				&& !StringUtils.hasText(this.commissionRechPojo.getLibelle())) {
			lesCommissions.addAll(s);
		} else {
			// filtre
			boolean hasFiltreCode = false;
			boolean hasFiltreLibelle = false;
			if (StringUtils.hasText(this.commissionRechPojo.getCode())) {
				hasFiltreCode = true;
			}
			if (StringUtils.hasText(this.commissionRechPojo.getLibelle())) {
				hasFiltreLibelle = true;
			}
			lesCommissions = new ArrayList<Commission>();
			boolean ajouterCode = false;
			boolean ajouterLibelle = false;
			for (Commission c : s) {
				// flags update
				if (hasFiltreCode) {
					if (c.getCode().toUpperCase()
							.contains(
								this.commissionRechPojo.getCode().toUpperCase())) {
						ajouterCode = true;
					} else { ajouterCode = false; }
				}
	
				if (hasFiltreLibelle) {
					if (c.getLibelle().toUpperCase()
							.contains(
									this.commissionRechPojo.getLibelle().toUpperCase())) {
							ajouterLibelle = true;
					} else { ajouterLibelle = false; }
				}
	
				// must be added ?
				if (hasFiltreCode && hasFiltreLibelle) {
					if (ajouterCode && ajouterLibelle) {
						lesCommissions.add(c);
					}
				} else {
					if (hasFiltreCode) {
						if (ajouterCode) {
							lesCommissions.add(c);
						}
					} else {
						if (hasFiltreLibelle) {
							if (ajouterLibelle) {
								lesCommissions.add(c);
							}
						}
					}
				}
			}
		}
		// création de la liste de commissionPojo
		List<CommissionPojo> lesCommissionsPojo = new ArrayList<CommissionPojo>();
		for (Commission comm : lesCommissions) {
			List<TraitementCmi> trtCmi = new ArrayList<TraitementCmi>();
			List<TraitementCmi> trtCmiOff = new ArrayList<TraitementCmi>();
			trtCmi.addAll(comm.getTraitementCmi());
			/**
			 * Règle pour les flags :
			 * - rouge si aucun trt en service (trtCmi vide)
			 * - orange si certains trt hors service (trtCmi non vide et trtCmiOff non vide)
			 * - vert sinon (trtCmi non vide et trtCmiOff vide)
			 */
			if (comm.getCode().equals("DVAL2M1")) {
				System.out.println("cas DVAL2M1");
			}
			for (TraitementCmi trt : comm.getTraitementCmi()) {
//				boolean enServ = false;
//				for (LinkTrtCmiCamp link : trt.getLinkTrtCmiCamp()) {
//					if (link.getTemoinEnService()) {
//						enServ = true;
//					}
//				}
				if (Utilitaires.isTraitementCmiOff(trt, codRI)) {
					trtCmi.remove(trt);
					trtCmiOff.add(trt);
				}
			}
			CommissionPojo commPojo = new CommissionPojo();
			commPojo.setCommission(comm);
			commPojo.setFlagWithoutTrtActive(trtCmi.isEmpty());
			commPojo.setFlagWithSomeTrtInactive(!trtCmi.isEmpty() && !trtCmiOff.isEmpty());
			lesCommissionsPojo.add(commPojo);
		}
		Collections.sort(lesCommissionsPojo, new ComparatorString(CommissionPojo.class));
		return lesCommissionsPojo;
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the commissionRechPojo
	 */
	public CommisionRechPojo getCommissionRechPojo() {
		return commissionRechPojo;
	}

	/**
	 * @param commissionRechPojo the commissionRechPojo to set
	 */
	public void setCommissionRechPojo(final CommisionRechPojo commissionRechPojo) {
		this.commissionRechPojo = commissionRechPojo;
	}

	/**
	 * @param parameterService the parameterService to set
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	/**
	 * @param domainApoService the domainApoService to set
	 */
	public void setDomainApoService(final DomainApoService domainApoService) {
		this.domainApoService = domainApoService;
	}


	/**
	 * @return the currentGest
	 */
	public Gestionnaire getCurrentGest() {
		return currentGest;
	}


	/**
	 * @param currentGest the currentGest to set
	 */
	public void setCurrentGest(final Gestionnaire currentGest) {
		this.currentGest = currentGest;
	}


	
	
	
}
