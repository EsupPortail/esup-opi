/**
 * CRI - Universite de Rennes1 - 57SI-CIES - 2007
 * http://sourcesup.cru.fr/projects/57si-cies/
 */
package org.esupportail.opi.web.tag;

import java.io.IOException;
import java.util.List;

import javax.faces.application.Application;
import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.el.MethodBinding;
import javax.faces.el.ValueBinding;
import javax.faces.webapp.UIComponentTag;

import org.apache.myfaces.component.html.ext.HtmlCommandButton;
import org.apache.myfaces.custom.htmlTag.HtmlTag;
import org.apache.myfaces.custom.updateactionlistener.UpdateActionListener;
import org.apache.myfaces.shared.el.SimpleActionMethodBinding;
import org.apache.myfaces.shared.renderkit.JSFAttr;
import org.apache.myfaces.shared.renderkit.html.HTML;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.web.jsf.tags.TagUtils;
import org.springframework.util.StringUtils;

/**
 * @author cleprous
 *
 */
public class UIMenuDynItems extends UIComponentBase {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Nombre de br apres le commandButton.
	 */
	private static final int NB_BR = 3;
	
	/**
	 * The current traitement.
	 */
	private static final String CURRENT_TRT = "managedAccess.currentTraitement";
	
	/**
	 * The value.
	 */
	private Object value;
	
	
	
	/**
	 * The styleClass.
	 */
	private String styleClass;
	
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());

	/*
	 ******************* INIT ************************* */


	/**
	 * Constructor.
	 */
	public UIMenuDynItems() {
		super();
		value = null;
		

	}

	/*
	 ******************* METHODS ********************** */


	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		return UIMenuDyn.COMPONENT_FAMILY;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(final FacesContext context) {
		Object[] values = new Object[3];
		values[0] = super.saveState(context);
		values[1] = value;
		values[2] = styleClass;
		return values;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	@Override
	public void restoreState(final FacesContext context, final Object state) {
		Object[] values = (Object[]) state;
		super.restoreState(context, values[0]);
		value = values[1];
		styleClass = (String) values[2];
	}
	
	
	/**
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void encodeBegin(final FacesContext facesContext) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entered encodeBegin for client-Id: " + getId());
		}
		
		List<NavigationMenuItem> listItem = (List<NavigationMenuItem>) getValue();
		Application a = facesContext.getApplication();
		//car UIMenuDynItems ne peut contenir comme fils que les composants crees dans makeMenu
		if (listItem != null && getChildren() == null || getChildren().isEmpty()) {
			makeMenu(listItem, a);
		}
		
		
		//encodeChildren(facesContext);
		if (logger.isDebugEnabled()) {
			logger.debug("Exited encodeBegin");
		}
	}
	

	
	/**
	 * 
	 * Prepare les differents composants qui composent le menus.
	 * @param listI
	 * @param a
	 */
	@SuppressWarnings("unchecked")
	private void makeMenu(final List<NavigationMenuItem> listI, final Application a) {
		if (logger.isDebugEnabled()) {
			logger.debug("Entered encodeMenu for client-Id: " + getId());
		}
		int cpt = 0; 
		for (NavigationMenuItem n : listI) {
			HtmlTag li = (HtmlTag) a.createComponent(HtmlTag.COMPONENT_TYPE);
			li.setValue(HTML.LI_ELEM);
			li.setParent(this);
			HtmlCommandButton command = 
				(HtmlCommandButton) a.createComponent(HtmlCommandButton.COMPONENT_TYPE);
			command.setId("menu_" + cpt);
			command.setParent(li);
			command.setImmediate(true);
			command.setStyleClass(getStyleClass());
			command.setValue(n.getLabel());
			//a true si une action est definie.
			boolean isAction = StringUtils.hasText(n.getAction());
			//si est un e EL explression
			if (isAction && UIComponentTag.isValueReference(n.getAction())) {
				MethodBinding m = a.createMethodBinding(n.getAction(), null);
				command.setAction(m);
			} else if (isAction) {
				command.setAction(new SimpleActionMethodBinding(n.getAction()));
			}
			
			UpdateActionListener listener = new UpdateActionListener();
			listener.setValue(n.getTraitement());
			//TODO ne peut mettre cette valeure en dure
			listener.setPropertyBinding(a.createValueBinding(
					TagUtils.makeELExpression(CURRENT_TRT)));
			command.addActionListener(listener);
			
			li.getChildren().add(command);
			
			HtmlTag hr = (HtmlTag) a.createComponent(HtmlTag.COMPONENT_TYPE);
			hr.setValue("hr");
			li.getChildren().add(hr);
			
			for (int i = 0; i < NB_BR; ++i) {
				HtmlTag br = (HtmlTag) a.createComponent(HtmlTag.COMPONENT_TYPE);
				br.setValue(HTML.BR_ELEM);
				li.getChildren().add(br);
			}
			
			
			getChildren().add(li);
			++cpt;
		}
		
		if (logger.isDebugEnabled()) {
			logger.debug("Exited encodeMenu for client-Id: " + getId());
		}
	}
	
	
	
	/**
	 * @see javax.faces.component.UIComponentBase#encodeChildren(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeChildren(final FacesContext context) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entered encodeChildren for client-Id: " + getId());
		}
		for (Object o : getChildren()) {
			UIComponentBase c = (UIComponentBase) o;
			c.encodeBegin(context);
			TagUtils.recursiveEncodechildren(c, context);
			
		}
		if (logger.isDebugEnabled()) {
			logger.debug("Exited encodeChildren");
		}
	}

	
	/*
	 ******************* ACCESSORS ******************** */



	/**
	 * @return Object
	 */
	public Object getValue() {
		ValueBinding vb = getValueBinding(JSFAttr.VALUE_ATTR);
		if (vb != null) {
			return vb.getValue(getFacesContext()); 
		}
		if (value != null) { return value; }
		return null;
	}
	
	/**
	 * @param v
	 */
	public void setValue(final Object v) {
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
