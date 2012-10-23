/**
 * 
 */
package org.esupportail.opi.web.controllers.references;


import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.references.NombreVoeuCge;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.primefaces.event.RowEditEvent;


/**
 * @author gomez
 *
 */
public class NbVoeuByCGEController extends AbstractContextAwareController {
	/*
	 * ******************* PROPERTIES STATIC ******************* */
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -1985806394940633629L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;
	/**
	 * Nombre de voeux pour par CGE.
	 */
	private NombreVoeuCge nbVoeuCge;
	/**
	 * liste de nombre de voeux pour par CGE.
	 */
	private List<NombreVoeuCge> listNbVoeuByCge;
	/**
	 * liste de CGE.
	 */
	private List<SelectItem> listCge;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public NbVoeuByCGEController() {
		super();
	}
	
	
	/*
	 * ******************* RESET ************************* */
	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		actionEnum = null;
		nbVoeuCge = null;
		listCge = null;
	}
	
	
	/*
	 ******************* CALLBACK ********************** */
	/**
	 * Callback to calendar list.
	 * @return String 
	 */
	public String goSeeAllNbVoeu() {
		reset();
		return NavigationRulesConst.SEE_NB_VOEUX_CGE;
	}
	/**
	 * Callback to add nbVoeuCge.
	 * @return String 
	 */
	public String goAddNbVoeu() {
		reset();
		getActionEnum().setWhatAction(ActionEnum.ADD_ACTION);
		return null;
	}
	
	
	/*
	 * ******************* ADD UPDATE DELETE ************************* */
	/**
	 * 
	 * Add a nbVoeuCge to the dataBase.
	 */
	@SuppressWarnings("unchecked")
	public void add() {
		if (log.isDebugEnabled()) {
			log.debug("enterind add with nbVoeuCge = " + getNbVoeuCge());
		}
		
		getAllNbVoeuByCge().add(getNbVoeuCge());
		getParameterService().addNombreVoeuCge(getNbVoeuCge());
		Collections.sort(getAllNbVoeuByCge(),new BeanComparator("codeCge",new NullComparator()));
		reset();
			
		addInfoMessage(null, "INFO.ENTER.SUCCESS");
		if (log.isDebugEnabled()) {
			log.debug("leaving add");
		}
	}
	/**
	 * 
	 * Update a nbVoeuCge to the dataBase.
	 */
	public void update() {
		if (log.isDebugEnabled()) {
			log.debug("enterind update with nbVoeuCge = " + getNbVoeuCge());
		}
		
		getParameterService().updateNombreVoeuCge(getNbVoeuCge());
		reset();
		
		if (log.isDebugEnabled()) {
			log.debug("leaving update");
		}
	}
	
	/**
	 * Update a row of the datatable with in-cell editor.
	 * @param event
	 */
    public void onEdit(final RowEditEvent event) {
    	getParameterService().updateNombreVoeuCge((NombreVoeuCge) event.getObject());
		reset();
    }
    
	/**
	 * 
	 * Delete a nbVoeuCge to the dataBase.
	 */
	public void delete() {
		if (log.isDebugEnabled()) {
			log.debug("enterind delete with nbVoeuCge = " + getNbVoeuCge());
		}
		
		getParameterService().deleteNombreVoeuCge(getNbVoeuCge());
		listNbVoeuByCge = null;
		reset();
		
		if (log.isDebugEnabled()) {
			log.debug("leaving delete");
		}
	}
	
	/*
	 * ******************* TEST ************************* */
	/**
	 * 
	 * @param codeCge
	 * @return boolean
	 */
	public boolean testExistCge(final String codeCge) {
		List<NombreVoeuCge> list = getAllNbVoeuByCge();
		for (NombreVoeuCge nbVCge : list) {
			if (nbVCge.getCodeCge().equals(codeCge)) {
				return true;
			}
		}
		return false;
	}
	
	
	/*
	 * ******************* GETTERS ITEMS ************************* */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getCodeCgeItems() {
		List<CentreGestion> listCentreGestion = getDomainApoService().getCentreGestion();
		if (listCge == null) {
			listCge = new ArrayList<SelectItem>();
			for (CentreGestion centreGestion : listCentreGestion) {
				if (!testExistCge(centreGestion.getCodCge())) {
					listCge.add(new SelectItem(centreGestion.getCodCge()));
				}
			}
			Collections.sort(listCge,new BeanComparator("value",new NullComparator()));
		}
		return listCge;
	}
	
	
	/*
	 * ******************* OTHERS GETTERS ************************* */
	/**
	 * 
	 * @return liste de nombre de voeux par CGE
	 */
	@SuppressWarnings("unchecked")
	public List<NombreVoeuCge> getAllNbVoeuByCge() {
		if (listNbVoeuByCge == null) {
			listNbVoeuByCge = getParameterService().getAllNombreDeVoeuByCge();
			Collections.sort(listNbVoeuByCge,new BeanComparator("codeCge",new NullComparator()));
		}
		return listNbVoeuByCge;
	}
	
	
	/*
	 * ******************* GETTERS ************************* */
	/**
	 * @return the actionEnum
	 */
	public ActionEnum getActionEnum() {
		if (actionEnum == null) {
			actionEnum = new ActionEnum();
		}
		return actionEnum;
	}
	/**
	 * 
	 * @return nbVoeuCge
	 */
	public NombreVoeuCge getNbVoeuCge() {
		if (nbVoeuCge == null) {
			nbVoeuCge = new NombreVoeuCge();
		}
		return nbVoeuCge;
	}


	/*
	 * ******************* SETTERS ************************* */
	/**
	 * @param actionEnum the actionEnum to set
	 */
	public void setActionEnum(final ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}
	/**
	 * 
	 * @param nbVoeuCge
	 */
	public void setNbVoeuCge(NombreVoeuCge nbVoeuCge) {
		this.nbVoeuCge = nbVoeuCge;
	}
}
