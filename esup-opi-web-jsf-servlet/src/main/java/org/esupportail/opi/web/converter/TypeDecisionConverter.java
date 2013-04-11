/**
 *
 */
package org.esupportail.opi.web.converter;

import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;


/**
 * @author ylecuyer
 */
public class TypeDecisionConverter implements Converter, InitializingBean {

    /**
     * see {@link ParameterService}.
     */
    private ParameterService parameterService;

    /**
     * Constructor.
     */
    public TypeDecisionConverter() {
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
        try {
            int intValue = Integer.valueOf(value);
            for (TypeDecision t : parameterService.getTypeDecisions(true)) {
                if (t.getId().equals(intValue)) {
                    return t;
                }
            }

            return null;
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException(
                    "could not convert String " + value + " to a Structure.", e);
        }
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
        if (!(value instanceof TypeDecision)) {
            throw new UnsupportedOperationException(
                    "object " + value + " is not a Structure.");
        }
        TypeDecision type = (TypeDecision) value;
        return Integer.toString(type.getId());
    }

    /**
     * @param parameterService the parameterService to set
     */
    public void setParameterService(final ParameterService parameterService) {
        this.parameterService = parameterService;
    }


}
