/**
* CRI - Universite de Rennes1 - 57SI-CIES - 2007
* http://sourcesup.cru.fr/projects/57si-cies/
*/
package org.esupportail.opi.web.tag;

import javax.faces.component.UIComponent;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.springframework.util.StringUtils;

/**
 * @author invite
 *
 */
public class MenuDynItemsTag extends UIComponentTag {
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The value.
	 */
	private String value;
	
	/**
	 * The styleClass.
	 */
	private String styleClass;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public MenuDynItemsTag() {
		super();
		value = null;

	}

	/*
	 ******************* METHODS ********************** */
	
	/**
	 * @see javax.faces.webapp.UIComponentTag#getComponentType()
	 */
	@Override
	public String getComponentType() {
		return "faces.opi.component.menuItems";
	}


	/**
	 * @see javax.faces.webapp.UIComponentTag#getRendererType()
	 */
	@Override
	public String getRendererType() {
		return null;
	}
	
	
	
	/**
	 * @see javax.faces.webapp.UIComponentTag#setProperties(javax.faces.component.UIComponent)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void setProperties(final UIComponent component) {
		super.setProperties(component);
		if (StringUtils.hasText(getValue())) {
			component.setValueBinding(JSFAttr.VALUE_ATTR, 
					getFacesContext().getApplication().createValueBinding(getValue()));
		}
		if (StringUtils.hasText(getStyleClass())) {
			component.getAttributes().put(JSFAttr.STYLE_CLASS_ATTR, getStyleClass());
		}
	}
	
	/*
	 ******************* ACCESSORS ********************** */
	
	

	/**
	 * @return Object
	 */
	public String getValue() {
		return value;
	}
	
	/**
	 * @param v
	 */
	public void setValue(final String v) {
		value = v;
		
	}

	/**
	 * @return the styleClass
	 */
	public String getStyleClass() {
		return styleClass;
	}

	/**
	 * @param styleClass the styleClass to set
	 */
	public void setStyleClass(final String styleClass) {
		this.styleClass = styleClass;
	}

	

}
