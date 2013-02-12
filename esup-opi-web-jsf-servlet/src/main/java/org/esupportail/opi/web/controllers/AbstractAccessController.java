/**
 *
 */
package org.esupportail.opi.web.controllers;

import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.web.beans.ManagedAccess;
import org.esupportail.opi.web.beans.ManagedCalendar;
import org.esupportail.opi.web.beans.ManagedRoadMap;
import org.esupportail.opi.web.beans.pojo.RoadMap;


/**
 * @author cleprous
 */
public abstract class AbstractAccessController extends AbstractContextAwareController {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = 5711912569152313043L;

	/*
     ******************* PROPERTIES ******************* */

    /**
     * Managed the treatment access.
     */
    private ManagedAccess managedAccess;

    /**
     * Managed the road of user.
     */
    private ManagedRoadMap managedRoadMap;


    /**
     * Managed the calendar for individu.
     */
    private ManagedCalendar managedCalendar;


    /**
     * A logger.
     */
//	private final Logger log = new LoggerImpl(getClass());
	
	/*
	 ******************* INIT ************************* */

    /**
     * Constructors.
     */
    public AbstractAccessController() {
        super();
    }


    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        Assert.notNull(this.managedAccess, "property managedAccess of class "
                + this.getClass().getName() + " can not be null");
        Assert.notNull(this.managedRoadMap, "property managedRoadMap of class "
                + this.getClass().getName() + " can not be null");
        Assert.notNull(this.managedCalendar, "property managedCalendar of class "
                + this.getClass().getName() + " can not be null");

    }
	
	
	/*
	 ******************* METHODS ********************** */

    /**
     * Add the current Road in the RoadMap.
     *
     * @param action
     * @param label
     * @param title
     */
    public void addTheCurrentRoad(final String action, final String label, final String title) {
        Integer rang = managedRoadMap.getRoads().size() + 1;
        managedRoadMap.addRoad(new RoadMap(action, true, label, rang, title));
    }

    /**
     * Reinit the managedRoadMap.
     */
    public void resetRoadMap() {
        managedRoadMap.reset();
    }
	
	
	
	/*
	 ******************* ACCESSORS ******************** */


    /**
     * @return the managedAccess
     */
    public ManagedAccess getManagedAccess() {
        return managedAccess;
    }


    /**
     * @param managedAccess the managedAccess to set
     */
    public void setManagedAccess(final ManagedAccess managedAccess) {
        this.managedAccess = managedAccess;
    }

    /**
     * @param managedRoadMap the managedRoadMap to set
     */
    public void setManagedRoadMap(final ManagedRoadMap managedRoadMap) {
        this.managedRoadMap = managedRoadMap;
    }


    /**
     * @param managedCalendar the managedCalendar to set
     */
    public void setManagedCalendar(final ManagedCalendar managedCalendar) {
        this.managedCalendar = managedCalendar;
    }


    /**
     * @return the managedCalendar
     */
    public ManagedCalendar getManagedCalendar() {
        return managedCalendar;
    }

}
