/**
 * 
 */
package org.esupportail.opi.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;





/**
 * @author ylecuyer
 *
 */
public class CampagneConverter implements Converter, InitializingBean {

	/**
	 * see {@link ParameterService}.
	 */
	private ParameterService parameterService;
	
	/**
	 * Constructor.
	 */
	public CampagneConverter() {
		super();
	}
	
	
	/** 
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.parameterService, "property parameterService of class "
				+ this.getClass().getName() + " can not be null");
		
	}
	
	
	/**
	 * @see javax.faces.convert.Converter#getAsObject(
	 * javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
	 */
	public Object getAsObject(
			final FacesContext context,
			final UIComponent component,
			final String value)
			throws ConverterException {
		if (!StringUtils.hasText(value)) {
			return null;
		}
		try {
			int intValue = Integer.valueOf(value);
			for (Campagne c : parameterService.getCampagnes(null, null)) {
				if (c.getId().equals(intValue)) {
					return c;
				}
			}
			
			return null;
		} catch (NumberFormatException e) {
			throw new UnsupportedOperationException(
					"could not convert String [" + value + "] to a Structure.", e);
		}
	}

	/**
	 *
	 * @see javax.faces.convert.Converter#getAsString(
	 * javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
	 */
	public String getAsString(
			final FacesContext context,
			final UIComponent component,
			final Object value)	throws ConverterException {
		if (value == null) {
			return "";
		} else if (value instanceof String) {
			if (!StringUtils.hasText((String) value)) {
				return "";
			}
		}
		if (!(value instanceof Campagne)) {
			throw new UnsupportedOperationException(
					"object " + value + " is not a Campagne.");
		}
		Campagne camp = (Campagne) value;
		return Integer.toString(camp.getId());
	}

	/**
	 * @param parameterService the parameterService to set
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	

}
