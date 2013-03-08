/**
 *
 */
package org.esupportail.opi.web.converter;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;

import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;

/**
 *
 *
 */
public class VersionEtapeDTOConverter implements Converter {

    /**
     * The {@link DomainApoService}.
     */
    private DomainApoService domainApoService;
    
    /**
     * A separator.
     */
    private final String separator = "-";

    /**
     * Constructor.
     */
    public VersionEtapeDTOConverter() {
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
        String[] tab = value.split(separator);
        if(tab.length < 2) return null;
        
        return domainApoService.getVersionEtape(tab[0], Integer.valueOf(tab[1]));
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
        if (!(value instanceof VersionEtapeDTO)) {
            throw new UnsupportedOperationException(
                    "object " + value + " is not a VersionEtapeDTO.");
        }
        VersionEtapeDTO vet = (VersionEtapeDTO) value;
        return vet.getCodEtp() + separator + vet.getCodVrsVet();
    }

	/**
	 * @param domainApoService the domainApoService to set
	 */
	public void setDomainApoService(final DomainApoService domainApoService) {
		this.domainApoService = domainApoService;
	}

}
