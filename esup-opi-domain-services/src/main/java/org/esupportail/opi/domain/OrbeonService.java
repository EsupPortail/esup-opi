/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;

import java.io.IOException;
import java.io.Serializable;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;

/**
 * The domain service interface.
 */
public interface OrbeonService extends Serializable {

	/**
	 * @param formName
	 * @param numDossier 
	 * @return true if the data has been created
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	boolean createResponse(final String formName, final String numDossier) 
	throws IOException, ParserConfigurationException, SAXException;

	/**
	 * @param formName
	 * @param numDossier
	 * @return true if the data has been removed
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	boolean removeResponse(final String formName, final String numDossier) 
	throws IOException, ParserConfigurationException, SAXException;

	/**
	 * @param formNameFrom
	 * @param formNameTo
	 * @param numDossier
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	boolean copyResponse(final String formNameFrom, final String formNameTo, final String numDossier)
	throws IOException, ParserConfigurationException, SAXException;
	
	/**
	 * @param formName
	 * @return true if the form has been created 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	boolean createForm(final String formName) 
	throws IOException, ParserConfigurationException, SAXException;

	/**
	 * @param code
	 * @return true if the form has been deleted 
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	boolean deleteForm(final String code) throws IOException, ParserConfigurationException, SAXException;

	/**
	 * @param url
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	boolean deleteFolder(final String formName) throws IOException, ParserConfigurationException, SAXException;
	
	/**
	 * @param formNameFrom
	 * @param formNameTo
	 * @param numDossier
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	boolean copyForm(final String formNameFrom, final String formNameTo)
	throws IOException, ParserConfigurationException, SAXException;
	
	/**
	 * @param formNameFrom
	 * @param formNameTo
	 * @param numDossier
	 * @return
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	boolean copyTemplateForm(final String formNameFrom, final String formNameTo)
	throws IOException, ParserConfigurationException, SAXException;

	/**
	 * @param indFormulaire
	 * @param sLabelRI : shortLabel of the RegimeInscription
	 * @return the tabs of bytes corresponding to the PDF file
	 * @throws IOException
	 */
	byte[] getPdf(final IndFormulaire indFormulaire, final String sLabelRI) throws IOException;

	/**
	 * @return the public url for the responses.
	 */
	String getOrbeonOpiUrl();

	/**
	 * @return the builder url for the responses.
	 */
	String getOrbeonBuilderUrl();

	@Deprecated
	//don't use 28/10/2009
	void getAuthOrbeon();

	String getSessionId();
}
