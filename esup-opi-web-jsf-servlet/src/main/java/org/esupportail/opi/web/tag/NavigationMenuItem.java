/**
 * 
 */
package org.esupportail.opi.web.tag;

import org.esupportail.opi.domain.beans.parameters.accessRight.Traitement;

/**
 * @author cleprous
 *
 */
public class NavigationMenuItem extends
		org.apache.myfaces.custom.navmenu.NavigationMenuItem {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4480248280394248021L;
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The traitement OPI.
	 */
	private Traitement traitement;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public NavigationMenuItem() {
		super();
	}

	/**
	 * Constructors.
	 * @param label
	 * @param action
	 */
	public NavigationMenuItem(String label, String action) {
		super(label, action);
	}
	
	/**
	 * Constructors.
	 * @param label
	 * @param action
	 * @param trait 
	 */
	public NavigationMenuItem(String label, String action, Traitement trait) {
		super(label, action);
		traitement = trait;
	}
	

	/**
	 * Constructors.
	 * @param label
	 * @param action
	 * @param icon
	 * @param split
	 */
	public NavigationMenuItem(String label, String action, String icon,
			boolean split) {
		super(label, action, icon, split);
	}

	/**
	 * Constructors.
	 * @param value
	 * @param label
	 * @param description
	 * @param disabled
	 * @param action
	 * @param icon
	 * @param split
	 */
	public NavigationMenuItem(Object value, String label, String description,
			boolean disabled, String action, String icon, boolean split) {
		super(value, label, description, disabled, action, icon, split);
	}

	/**
	 * Constructors.
	 * @param value
	 * @param label
	 * @param description
	 * @param disabled
	 * @param rendered
	 * @param action
	 * @param icon
	 * @param split
	 */
	public NavigationMenuItem(Object value, String label, String description,
			boolean disabled, boolean rendered, String action, String icon,
			boolean split) {
		super(value, label, description, disabled, rendered, action, icon,
				split);
	}

	/**
	 * Constructors.
	 * @param value
	 * @param label
	 * @param description
	 * @param disabled
	 * @param rendered
	 * @param action
	 * @param icon
	 * @param split
	 * @param target
	 */
	public NavigationMenuItem(Object value, String label, String description,
			boolean disabled, boolean rendered, String action, String icon,
			boolean split, String target) {
		super(value, label, description, disabled, rendered, action, icon,
				split, target);
	}

	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NavigationMenuItem#" + hashCode() + "[Traitement=[" + traitement + "]]";
	}
	

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	
	/**
	 * @return the traitement
	 */
	public Traitement getTraitement() {
		return traitement;
	}

	/**
	 * @param traitement the traitement to set
	 */
	public void setTraitement(Traitement traitement) {
		this.traitement = traitement;
	}
	


}
