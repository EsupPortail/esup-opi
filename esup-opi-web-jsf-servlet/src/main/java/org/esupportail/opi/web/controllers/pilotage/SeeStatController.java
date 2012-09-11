package org.esupportail.opi.web.controllers.pilotage;


import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.context.FacesContext;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.utils.CoordonneStatistique;
import org.esupportail.opi.services.export.CastorService;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.pojo.ListeStatPojo;
import org.esupportail.opi.web.beans.utils.ExportUtils;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.PDFUtils;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;


/**
 * @author cgomez
 *
 */
public class SeeStatController extends AbstractContextAwareController {
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -9138953052909987476L;
	
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;
	
	/**
	 * {@link ParametreStatController}.
	 */
	private ParametreStatController parametreStatController;
	
	/**
	 * Service to generate Xml.
	 */
	private CastorService castorService;
	
	/**
	 * resultCamTab.
	 */
	private List<String> resultCampagne;
	
	/**
	 * resultTable.
	 */
	private List<String> resultAbscisse;
	
	/**
	 * resultTable.
	 */
	private Map<String, List<String>> result;
	
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public SeeStatController() {
		super();
		if (log.isDebugEnabled()) {
			log.debug("Constructor seeStatController");
		}
		resultCampagne = new ArrayList<String>();
		resultAbscisse = new ArrayList<String>();
		result = new HashMap<String, List<String>>();
	}
	
	/**
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		Assert.notNull(this.castorService, 
				"property castorService of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.parametreStatController, 
				"property parametreStatController of class " + this.getClass().getName() 
				+ " can not be null");
		reset();
	}
	
	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		actionEnum = new ActionEnum();
		resultCampagne.clear();
		resultAbscisse.clear();
		result.clear();
	}
	
	
	/*
	 ******************* CALLBACK ********************** */
	/**
	 * Callback to statistical result.
	 * @return String
	 */
	public String goSeeResulStat() {
		reset();
		actionEnum.setWhatAction(ActionEnum.EMPTY_ACTION);
		return NavigationRulesConst.SEE_RESULT_STAT;
	}
	
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * @return list
	 */
	private List<String> listCampagne() {
		List<String> listCampagne = new ArrayList<String>();
		listCampagne.add("");
		for (String libCampagne : resultCampagne) {
			listCampagne.add(libCampagne);
			for (int i = 0; i < parametreStatController.getListAbscisseCoordonne().size() - 1; i++) {
				listCampagne.add("");
			}
		}
		return listCampagne;
	}
	/**
	 * 
	 */
	public void makeCsvStatistique() {
		if (log.isDebugEnabled()) {
			log.debug("entering makeCsvStatistique()");
		}
		Map<Integer, List<String>> map = new HashMap<Integer, List<String>>();
		map.put(0, listCampagne());
		map.put(1, getResultAbscisse());
		Integer counter = 2;
		for (String key : result.keySet()) {
			List<String> ligne = new ArrayList<String>();
			ligne.add(key);
			ligne.addAll(result.get(key));
			map.put(counter, ligne);
			counter++;
		}
		
		ExportUtils.csvGenerate(map, "exportStatistique.csv");
	}
	/**
	 * @throws IOException 
	 * 
	 */
	public void printPDFStatistique() throws IOException {
		if (log.isDebugEnabled()) {
			log.debug("entering printPDFStatistique()");
		}
		/**
		 * Préparation de l'objet à envoyer à castor
		 */
		ListeStatPojo list = new ListeStatPojo();
		// Ajout de la date du jour
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constantes.DATE_FORMAT); 
		list.setDate(dateFormat.format(new Date()));
		list.setTitre("");
		list.setLibCamp(listCampagne());
		list.setNbAbscisse(String.valueOf(parametreStatController.getListAbscisseCoordonne().size()));
		list.setTitreAbscisse(getResultAbscisse());
		list.setMapStatList(getResult());
		
		/**
		 * Generation du PDF
		 */
		String fileNameXml = "tabStatistique.xml";
		castorService.objectToFileXml(list, fileNameXml);
		
		PDFUtils.exportPDF(fileNameXml, FacesContext.getCurrentInstance(), 
				castorService.getXslXmlPath(), "tabStatistique.pdf", Constantes.TAB_STATISTIQUE);
	}
	/**
	 * @return list
	 */
	public Set<String> getResultKey() {
		return getResult().keySet();
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the actionEnum
	 */
	public ActionEnum getActionEnum() {
		return actionEnum;
	}

	/**
	 * @param actionEnum the actionEnum to set
	 */
	public void setActionEnum(final ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}
	
	/**
	 * @return parametreStatController
	 */
	public ParametreStatController getParametreStatController() {
		return parametreStatController;
	}

	/**
	 * @param parametreStatController
	 */
	public void setParametreStatController(
			final ParametreStatController parametreStatController) {
		this.parametreStatController = parametreStatController;
	}
	
	/**
	 * @return castorService
	 */
	public CastorService getCastorService() {
		return castorService;
	}
	
	/**
	 * @param castorService
	 */
	public void setCastorService(final CastorService castorService) {
		this.castorService = castorService;
	}
	
	/**
	 * @return list
	 */
	public List<String> getResultCampagne() {
		if (resultCampagne.isEmpty()) {
			for (Campagne camp : parametreStatController.getCampagnes()) {
				resultCampagne.add(camp.getLibelle());
			}
		}
		if (log.isDebugEnabled()) {
			log.debug("leaving getResultCampagne " + resultCampagne);
		}
		return resultCampagne;
	}
	
	/**
	 * @return the size of the list listAbscisseCoordonne
	 */
	public String getTailleAbscisse() {
		return String.valueOf(parametreStatController.getListAbscisseCoordonne().size());
	}
	
	/**
	 * @return list
	 */
	public List<String> getResultAbscisse() {
		if (resultAbscisse.isEmpty()) {
			resultAbscisse.add("");
			for (int i = 0; i < getResultCampagne().size(); i++) {
				for (CoordonneStatistique abscisse 
						: parametreStatController.getListAbscisseCoordonne()) {
					if (abscisse.getLibelle() != null && !abscisse.getLibelle().isEmpty()) {
						resultAbscisse.add(abscisse.getLibelle());
					} else {
						resultAbscisse.add(abscisse.getId());
					}
				}
			}
		}
		return resultAbscisse;
	}
	
	/**
	 * @return Map
	 */
	public Map<String, List<String>> getResult() {
		if (result.isEmpty()) {
			for (CoordonneStatistique ordonne : parametreStatController.getListOrdonneCoordonne()) {
				String key;
				List<String> listResult = new ArrayList<String>();
				if (ordonne.getLibelle() != null && !ordonne.getLibelle().isEmpty()) {
					key = ordonne.getLibelle();
				} else {
					key = ordonne.getId();
				}
				for (Campagne camp : parametreStatController.getCampagnes()) {
					for (CoordonneStatistique abscisse 
							: parametreStatController.getListAbscisseCoordonne()) {
						listResult.add(parametreStatController.getPilotageService().
								getRecupValCoordonne(ordonne, abscisse, camp, true));
					}
				}
				result.put(key, listResult);
			}
		}
		return result;
	}
}
