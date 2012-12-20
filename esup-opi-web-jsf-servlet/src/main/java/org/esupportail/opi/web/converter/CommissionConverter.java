/**
 *
 */
package org.esupportail.opi.web.converter;

import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.springframework.util.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

/**
 *
 *
 */
public class CommissionConverter implements Converter {

    /**
     * The {@link ParameterService}.
     */
    private ParameterService parameterService;

    /**
     * Constructor.
     */
    public CommissionConverter() {
        super();
    }


    /**
     * @see javax.faces.convert.Converter#getAsObject(
     *javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.String)
     */
    public Object getAsObject(
            final FacesContext context,
            final UIComponent component,
            final String value)
            throws ConverterException {
        if (!StringUtils.hasText(value)) {
            return null;
        }
        return parameterService.getCommission(null, value);
    }

    /**
     * @see javax.faces.convert.Converter#getAsString(
     *javax.faces.context.FacesContext, javax.faces.component.UIComponent, java.lang.Object)
     */
    public String getAsString(
            final FacesContext context,
            final UIComponent component,
            final Object value) throws ConverterException {
        if (value == null) {
            return "";
        } else if (value instanceof String) {
            if (!StringUtils.hasText((String) value)) {
                return "";
            }
        }
        if (!(value instanceof Commission)) {
            throw new UnsupportedOperationException(
                    "object " + value + " is not a Commission.");
        }
        Commission comm = (Commission) value;
        return String.valueOf(comm.getCode());
    }


    /**
     * @param parameterService the parameterService to set
     */
    public void setParameterService(ParameterService parameterService) {
        this.parameterService = parameterService;
    }

}
