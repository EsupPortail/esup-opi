/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.web.beans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.concurrent.ConcurrentHashMap;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.web.controllers.Resettable;
import org.esupportail.opi.web.beans.pojo.RoadMap;
import org.springframework.beans.factory.InitializingBean;



/**
 * @author cleprous
 * ManagedRoadMap : A bean to memorize the user road .
 */
public class ManagedRoadMap implements Resettable, InitializingBean, Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3399169335073320608L;


	/*
	 ******************* PROPERTIES ******************* */


	/**
	 * The roads of user.
	 */
	private Map<Integer, RoadMap> roads;
	
	
	/**
	 * The current treatement.
	 */
	private RoadMap roadSelected;


	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	/*
	 ******************* INIT ************************* */
	
	
	/**
	 * Constructor.
	 */
	public ManagedRoadMap() {
		super();
	}

	
	/** 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		reset();
	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		roads = new ConcurrentHashMap<Integer, RoadMap>();
		roadSelected = new RoadMap();
	}
	
	/*
	 ******************* CALLBACK ********************** */
	
	/**
	 * Callback use by all domain with function.
	 * @return String 
	 */
	public String goToRoadSelect() {
		if (log.isDebugEnabled()) {
			log.debug("entering goToRoadSelect with roadSelected = " + roadSelected);
		}
		Map<Integer, RoadMap> roadsTmp = new ConcurrentHashMap<Integer, RoadMap>();
		//remove roadSelected and all roads has rang > it roadSelected
		for (Entry<Integer, RoadMap> e : roads.entrySet()) {
			Integer rang = e.getKey();
			if (rang < roadSelected.getRang()) {
				roadsTmp.put(rang, e.getValue());
			} else if (rang.equals(roadSelected.getRang())) {
				roadSelected.setIsCurrentPage(true);
				roadsTmp.put(rang, roadSelected);
			}
		}
		//redirection
		String action = roadSelected.getAction();
		roadSelected = new RoadMap();
		roads = roadsTmp;
		return action;
	}
	
	
	/**
	 * Add a road in roads.
	 * to the all roads the attr isCurrentPage is to false.
	 * @param roadMap
	 */
	public void addRoad(final RoadMap roadMap) {
		for (Entry<Integer, RoadMap> e : roads.entrySet()) {
			e.getValue().setIsCurrentPage(false);
		}
		roads.put(roadMap.getRang(), roadMap);
	}
	
	/**
	 * true if roads.size() > 1.
	 * @return Boolean
	 */
	public Boolean getIsMoreTwoElt() {
		return roads.size() > 1;
	}
	
	/*
	 ******************* METHODS ********************** */
	
	/**
	 * @return List<Integer>
	 */
	public List<Integer> getRoadsKey() {
		return new ArrayList<Integer>(getRoads().keySet());
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the roadSelected
	 */
	public RoadMap getRoadSelected() {
		return roadSelected;
	}


	/**
	 * @param roadSelected the roadSelected to set
	 */
	public void setRoadSelected(final RoadMap roadSelected) {
		this.roadSelected = roadSelected;
	}
	

	/**
	 * @return the roads
	 */
	public Map<Integer, RoadMap> getRoads() {
		// To sort the roads by their rank
		return new TreeMap<Integer, RoadMap>(roads);
	}


	/**
	 * @param roads the roads to set
	 */
	public void setRoads(final Map<Integer, RoadMap> roads) {
		this.roads = new ConcurrentHashMap<Integer, RoadMap>(roads);
	}

}
