/**
* CRI - Universite de Rennes1 - 57SI-CIES - 2007
* http://sourcesup.cru.fr/projects/57si-cies/
*/
package org.esupportail.opi.web.tag;

import java.io.IOException;

import javax.faces.component.UIComponentBase;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;

import org.apache.myfaces.shared.renderkit.html.HTML;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;

/**
 * @author cleprous
 *
 */
public class UIMenuDyn extends UIComponentBase {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * Le groupe de famille de ce composant.
	 */
	protected static final String COMPONENT_FAMILY = "faces.opi"; 
	
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
	public UIMenuDyn() {
		super();
	}

	/**
	 * @see javax.faces.component.UIComponent#getFamily()
	 */
	@Override
	public String getFamily() {
		return COMPONENT_FAMILY;
	}
	
	/**
	 * @see javax.faces.component.UIComponentBase#saveState(javax.faces.context.FacesContext)
	 */
	@Override
	public Object saveState(final FacesContext context) {
		Object[] values = new Object[2];
		values[0] = super.saveState(context);
		values[1] = styleClass;
		return values;
	}

	/**
	 * @see javax.faces.component.UIComponentBase#restoreState(javax.faces.context.FacesContext, java.lang.Object)
	 */
	@Override
	public void restoreState(final FacesContext context, final Object state) {
		Object[] values = (Object[]) state;
		super.restoreState(context, values[0]);
		styleClass = (String) values[1];
	}
	
	
	/**
	 * @see javax.faces.component.UIComponentBase#encodeBegin(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeBegin(final FacesContext facesContext) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entered encodeBegin for client-Id: " + getId());
		}
		
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		
		responseWriter.startElement(HTML.DIV_ELEM, this);
		responseWriter.writeAttribute(HTML.ID_ATTR, getId(), null);
		responseWriter.writeAttribute(HTML.CLASS_ATTR, getStyleClass(), null);
		responseWriter.startElement(HTML.UL_ELEM, this);
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("Exited encodeBegin");
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
			c.encodeChildren(context);
			c.encodeEnd(context);
		}
		
		
		if (logger.isDebugEnabled()) {
			logger.debug("Exited encodeChildren");
		}
	}

	/**
	 * @see javax.faces.component.UIComponentBase#encodeEnd(javax.faces.context.FacesContext)
	 */
	@Override
	public void encodeEnd(final FacesContext facesContext) throws IOException {
		if (logger.isDebugEnabled()) {
			logger.debug("Entered encodeEnd for client-Id: " + getId());
		}
		
		ResponseWriter responseWriter = facesContext.getResponseWriter();
		responseWriter.endElement(HTML.UL_ELEM);
		responseWriter.endElement(HTML.DIV_ELEM);
		if (logger.isDebugEnabled()) {
			logger.debug("Exited encodeEnd");
		}
	}
	
	
	/**
	 * @see javax.faces.component.UIComponentBase#getRendersChildren()
	 */
	@Override
	public boolean getRendersChildren() {
		return true;
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
	public void setStyleClass(String styleClass) {
		this.styleClass = styleClass;
	}
	
	
	/*
	 ******************* INIT ************************* */
	
	


}
