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
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;





/**
 * @author cleprous
 *
 */
public class MotivationAvisConverter implements Converter, InitializingBean {

	/**
	 * see {@link ParameterService}.
	 */
	private ParameterService parameterService;
	
	/**
	 * Constructor.
	 */
	public MotivationAvisConverter() {
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
			for (MotivationAvis m : parameterService.getMotivationsAvis(true)) {
				if (m.getId().equals(intValue)) {
					return m;
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
		if (!(value instanceof MotivationAvis)) {
			throw new UnsupportedOperationException(
					"object " + value + " is not a Structure.");
		}
		MotivationAvis mot = (MotivationAvis) value;
		return Integer.toString(mot.getId());
	}

	/**
	 * @param parameterService the parameterService to set
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	

}
