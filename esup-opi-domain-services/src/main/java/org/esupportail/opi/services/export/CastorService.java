/**
 * 
 */
package org.esupportail.opi.services.export;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serializable;
import java.io.StringWriter;
import java.io.Writer;

import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.utils.exceptions.ExportException;
import org.exolab.castor.xml.MarshalException;
import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.ValidationException;
import org.springframework.beans.factory.InitializingBean;

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
		
		Writer writer = new StringWriter();
		OutputStreamWriter out = null;
		
		try {
			out = new OutputStreamWriter(new FileOutputStream(xslXmlPath + fileName), "UTF-8");
			
			castorMarshaller.setWriter(writer);		
			castorMarshaller.marshal(object);
			String xmlContent = writer.toString();
			out.write(xmlContent);
			
			
		} catch (MarshalException e) {
			throw new ExportException("problem marhsalling in getXmlIndividu ", e);
		} catch (ValidationException e) {
			throw new ExportException("problem marhsalling in getXmlIndividu ", e);
		} catch (IOException e) {
			throw new ExportException("problem marhsalling in getXmlIndividu ", e);
		} finally {
			try {
				out.close();
				writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
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

//	/**
//	 * @param castorUnMarshaller the castorUnMarshaller to set
//	 */
//	public void setCastorUnMarshaller(final Unmarshaller castorUnMarshaller) {
//		this.castorUnMarshaller = castorUnMarshaller;
//	}

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
