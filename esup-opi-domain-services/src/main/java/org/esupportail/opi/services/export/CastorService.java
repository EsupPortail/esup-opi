/**
 * 
 */
package org.esupportail.opi.services.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.Serializable;

import javax.xml.transform.stream.StreamResult;

import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.utils.exceptions.ExportException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.MarshallingException;

/**
 * @author cleprous
 *
 */
public class CastorService implements Serializable, InitializingBean {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -6766275085431294370L;
	
	/**
	 * Object to Xml.
	 */
	private Marshaller castorMarshaller;
	
	/**
	 * Xml to Object.
	 */
//	private Unmarshaller castorUnMarshaller;	

	/**
	 * the path of xml and xsl file. 
	 */
	private String xslXmlPath;


	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public CastorService() {
		super();
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(this.castorMarshaller, 
				"property castorMarshaller of class " 
				+ this.getClass().getName() + " can not be null");
	}

	/*
	 ******************* METHODS ********************** */

	/**
	 * Generate the xml in the file.
	 * @param object 
	 * @param fileName 
	 */
	public void objectToFileXml(final Object object, 
			final String fileName ) {
		FileOutputStream out = null;
		try {
			out = new FileOutputStream(xslXmlPath + fileName);
			castorMarshaller.marshal(object, new StreamResult(out));
			
		} catch (MarshallingException e) {
			throw new ExportException("problem marhsalling in getXmlIndividu ", e);
		} catch (IOException e) {
			throw new ExportException("problem marhsalling in getXmlIndividu ", e);
		}
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @param castorMarshaller the castorMarshaller to set
	 */
	public void setCastorMarshaller(final Marshaller castorMarshaller) {
		this.castorMarshaller = castorMarshaller;
	}

	/**
	 * @return the xslXmlPath
	 */
	public String getXslXmlPath() {
		return xslXmlPath;
	}
	/**
	 * @param xslXmlPath the xslXmlPath to set
	 */
	public void setXslXmlPath(final String xslXmlPath) {
		this.xslXmlPath = xslXmlPath;
	}

}
