/**
 *
 */
package org.esupportail.opi.web.converter;

import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.StringUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import java.util.List;


/**
 * @author ylecuyer
 */
public class RegimeInscriptionConverter implements Converter, InitializingBean {

    /**
     * The list of bean regimeInscription.
     */
    private List<RegimeInscription> regimeInscriptions;

    /**
     * Constructor.
     */
    public RegimeInscriptionConverter() {
        super();
    }


    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        Assert.notNull(this.regimeInscriptions, "property regimeInscriptions of class "
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
            System.out.println("value converter =  " + value);
            int intValue = Integer.valueOf(value);
            for (RegimeInscription rI : regimeInscriptions) {
                if (rI.getCode() == intValue) {
                    System.out.println("leaving converter return " + rI);
                    return rI;
                }
            }
            System.out.println("leaving converter return null");
            return null;
        } catch (NumberFormatException e) {
            throw new UnsupportedOperationException(
                    "could not convert String [" + value + "] to a Structure.", e);
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
        if (!(value instanceof RegimeInscription)) {
            throw new UnsupportedOperationException(
                    "object " + value + " is not a RegimeInscription.");
        }
        RegimeInscription rI = (RegimeInscription) value;
        return Integer.toString(rI.getCode());
    }


    /**
     * @param regimeInscriptions the regimeInscriptions to set
     */
    public void setRegimeInscriptions(final List<RegimeInscription> regimeInscriptions) {
        this.regimeInscriptions = regimeInscriptions;
    }


}
