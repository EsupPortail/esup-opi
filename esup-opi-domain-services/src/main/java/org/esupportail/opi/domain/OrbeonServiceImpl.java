/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndFormulaire;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.ResourceIterator;
import org.xmldb.api.base.ResourceSet;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.XPathQueryService;

import sun.misc.BASE64Encoder;



/**
 * The basic implementation of DomainService.
 * 
 * See /properties/domain/domain.xml
 */
public class OrbeonServiceImpl implements OrbeonService {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2629860301206263399L;

	/**
	 * 
	 */
	private static final String APP_NAME	= "OPI";

	/**
	 * 
	 */
	private static final String ORBEON_BUILDER_URL	= "/fr/orbeon/builder/edit/";

	/**
	 * 
	 */
	private static final String ORBEON_OPI_URL	= "/fr/OPI/";
	
	private static final String AUTHORIZATION = "Authorization";
	
	private static final String BASIC = "Basic ";

	private static final String ACCES_DB = "/exist/rest/db/";
	
	private static final String DATA_FILE_NAME = "/data.xml";
	
	private static final String ACCES_FORM_DATA = "/exist/rest/db/orbeon/builder/data/";
	/**
	 * 
	 */
	private static final String S1 = ":_";
	/**
	 * 
	 */
	private static final String S2 = "@_";
	/**
	 * 
	 */
	private static final String S3 = ";_";
	
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());

	/**
	 * 	The URL of orbeon.
	 */
	private String orbeonUrl;

	/**
	 * 	The user of orbeon.
	 */
	private String orbeonUsername;

	/**
	 * 	The password of orbeon.
	 */
	private String orbeonPassword;

	/**
	 * The xml path.
	 */
	private String xslXmlPath;
	
	/**
	 * AccÃÂ¨s ÃÂ  la base XML.
	 */
	private XPathQueryService xPathQueryService;
	
	/**
	 * URI pour l'accÃÂ¨s ÃÂ  la base XML.
	 */
	private String uri;
	

	/**
	 * Constructors.
	 */
	public OrbeonServiceImpl() {
		super();
	}
	
	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(this.uri, 
				"property uri of class " + this.getClass().getName() 
				+ " can not be null");
		initConnexionBase();
		Assert.notNull(this.xPathQueryService, 
				"property xPathQueryService of class " + this.getClass().getName() 
				+ " can not be null");
	}

	// FIXME
	/**
	 * @return the session id
	 */
	@Override
	public String getSessionId() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		return request.getSession().getId();
	}

	/**
	 *  the session id.
	 */
	@Override
	@Deprecated 
	//don't use 28/10/2009
	public void getAuthOrbeon() {
		FacesContext facesContext = FacesContext.getCurrentInstance();
		ExternalContext externalContext = facesContext.getExternalContext();
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		HttpServletResponse response = (HttpServletResponse) externalContext.getResponse();

		String credentials = new BASE64Encoder().encode(
				(getOrbeonUsername() + ":" + getOrbeonPassword()).getBytes());

		request.setAttribute(AUTHORIZATION, BASIC + credentials); 
		try {
			request.getRequestDispatcher("/orbeon").forward(request, response);
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	

	/** 
	 * @param formName 
	 * @param numDossier 
	 * @return true if the data has been created
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 * @throws IOException 
	 */
	public boolean createResponse(final String formName, final String numDossier) 
	throws IOException, ParserConfigurationException, SAXException {
		return putData(formName, numDossier, getData(formName));
	}

	/** 
	 * @see org.esupportail.opi.domain.OrbeonService#removeResponse(java.lang.String, java.lang.String)
	 */
	public boolean removeResponse(final String formName, final String numDossier) 
	throws IOException, ParserConfigurationException, SAXException {
		return deleteData(formName, numDossier);
	}

	/**
	 * @param formName 
	 * @return the data from the template
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public String getData(final String formName) throws IOException, ParserConfigurationException, SAXException {
		URL url = new URL(orbeonUrl + ACCES_DB 
				+ APP_NAME + "/" + formName + "/form/form.xhtml");
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// String credentials = new BASE64Encoder().encode("admin:".getBytes());
		// conn.setRequestProperty(AUTHORIZATION, BASIC + credentials);

		conn.setRequestMethod("GET");
		conn.setDoOutput(true);
		conn.connect();

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();

		int responseCode = conn.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			InputStream in = conn.getInputStream();

			Document doc = db.parse(in);
			NodeList nodes = doc.getElementsByTagName("form");


			if (log.isDebugEnabled()) {
				log.debug("Elements : " + nodes.getLength());
			}

			Document xmlDocPeople = db.newDocument();
			Node node = xmlDocPeople.importNode(nodes.item(0), true);

			try {
				// Set up the output transformer
				TransformerFactory transfac = TransformerFactory.newInstance();
				Transformer trans = transfac.newTransformer();
				trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
				trans.setOutputProperty(OutputKeys.INDENT, "yes");

				// Print the DOM node
				StringWriter sw = new StringWriter();
				StreamResult result = new StreamResult(sw);
				DOMSource source = new DOMSource(node);
				trans.transform(source, result);
				String xmlString = sw.toString();

				return xmlString;

			} catch (TransformerException e) {
				e.printStackTrace();
			}

		} else {
			if (log.isDebugEnabled()) {
				log.debug("HTTP connection response != HTTP_OK : " + responseCode);
			}
		} 
		return "";
	}
	
	
	/**
	 * @param formName 
	 * @param numDossier 
	 * @param data 
	 * @return if the data has been put
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 */
	public boolean putData(final String formName, final String numDossier, final String data) 
	throws IOException, ParserConfigurationException, SAXException {
		// On recupere le data.xml
		URL url = new URL(orbeonUrl + ACCES_DB + APP_NAME + "/" + formName + "/data/" 
				+ numDossier + DATA_FILE_NAME);
		if (log.isDebugEnabled()) {
			log.debug("--- url " + url + " ---");
		}
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		
		// TODO : securiser en ayant au moins un utilisateur cote orbeon
		if (!orbeonUsername.equals("")) {
			String credentials = new BASE64Encoder().encode(
					(orbeonUsername + ":" + orbeonPassword).getBytes());
			conn.setRequestProperty(AUTHORIZATION, BASIC + credentials);
		}
		conn.setRequestMethod("PUT");
		conn.setDoOutput(true);
		conn.setRequestProperty("ContentType", "text/xml");
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			writer.write(data);
		} finally {
			writer.close();
		}
		conn.connect();
		log.info("--- Server returned response code " + conn.getResponseCode() + " ---");
		if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
			return true;
		}
		return false;
	}

	/**
	 * @param formName
	 * @param numDossier
	 * @param data
	 * @return true if the data has been removed
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public boolean deleteData(final String formName, final String numDossier) 
	throws IOException, ParserConfigurationException, SAXException {
		// On recupere le data.xml
		URL url = new URL(orbeonUrl + ACCES_DB + APP_NAME + "/" + formName + "/data/" 
				+ numDossier);
		if (log.isDebugEnabled()) {
			log.debug("--- url " + url + " ---");
		}
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// TODO : securiser en ayant au moins un utilisateur cote orbeon
		if (!orbeonUsername.equals("")) {
			String credentials = new BASE64Encoder().encode(
					(orbeonUsername + ":" + orbeonPassword).getBytes());
			conn.setRequestProperty(AUTHORIZATION, BASIC + credentials);
		}
		conn.setRequestMethod("DELETE");
		conn.setDoOutput(true);
		conn.connect();
		if (log.isDebugEnabled()) {
			log.debug("--- Server returned response code " + conn.getResponseCode() + " ---");
		}
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			conn.disconnect();
			return true;
		}
		conn.disconnect();
		return false;

	}
	
	/**
	 * @see org.esupportail.opi.domain.OrbeonService#
	 * copyResponse(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean copyResponse(final String formNameFrom, final String formNameTo, final String numDossier)
	throws IOException, ParserConfigurationException, SAXException {
		URL urlFrom = new URL(orbeonUrl + ACCES_DB + APP_NAME + "/" + formNameFrom + "/data/" 
				+ numDossier + DATA_FILE_NAME);
		URL urlTo = new URL(orbeonUrl + ACCES_DB + APP_NAME + "/" + formNameTo + "/data/" 
				+ numDossier + DATA_FILE_NAME);
		URL urlDel = new URL(orbeonUrl + ACCES_DB + APP_NAME + "/" + formNameFrom + "/data/" 
				+ numDossier + "/");
		String tagName = "form";
		return copyData(urlFrom, urlTo, urlDel, tagName);
	}
	
	/**
	 * @param urlFrom
	 * @param urlTo
	 * @param urlDel
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public boolean copyData(final URL urlFrom, final URL urlTo, final URL urlDel, final String tagName) 
	throws IOException, ParserConfigurationException, SAXException {
		/**
		 * RÃÂ©cupÃÂ©ration du fichier ÃÂ  copier 
		 */
		HttpURLConnection connFrom = (HttpURLConnection) urlFrom.openConnection();

		connFrom.setRequestMethod("GET");
		connFrom.setDoOutput(true);
		connFrom.connect();

//		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//		DocumentBuilder db = dbf.newDocumentBuilder();

		String xmlStringFrom = "";
		
		int responseCode = connFrom.getResponseCode();
		if (responseCode == HttpURLConnection.HTTP_OK) {
			StringBuilder contents = new StringBuilder();
			// On lit le fichier template pour renvoyer le contenu en String
			try {
				InputStream in = connFrom.getInputStream();
				InputStreamReader ipsr = new InputStreamReader(in);

				BufferedReader input =  new BufferedReader(ipsr);
				try {
					String line = null; 
					while ((line = input.readLine()) != null) {
						contents.append(line);
						contents.append(System.getProperty("line.separator"));
					}
				} finally {
					input.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			xmlStringFrom = contents.toString();
//			InputStream in = connFrom.getInputStream();
//
//			Document doc = db.parse(in);
//			NodeList nodes = doc.getChildNodes();
////			.getElementsByTagName(tagName);
//
//			Document xmlDocPeople = db.newDocument();
//			Node node = xmlDocPeople.importNode(nodes.item(0), true);
//
//			try {
//				// Set up the output transformer
//				TransformerFactory transfac = TransformerFactory.newInstance();
//				Transformer trans = transfac.newTransformer();
//				trans.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
//				trans.setOutputProperty(OutputKeys.INDENT, "yes");
//
//				// Print the DOM node
//				StringWriter sw = new StringWriter();
//				StreamResult result = new StreamResult(sw);
//				DOMSource source = new DOMSource(node);
//				trans.transform(source, result);
//				xmlStringFrom = sw.toString();
//
//				if (log.isDebugEnabled()) {
//					log.debug("RÃÂ©cupÃÂ©ration de : " + urlFrom + " fini");
//				}
//
//			} catch (TransformerException e) {
//				e.printStackTrace();
//			}
		} else {
			if (log.isDebugEnabled()) {
				log.debug("ProblÃÂ¨me lors de la rÃÂ©cupÃÂ©ration de : " + urlFrom );
				log.debug("HTTP connection response != HTTP_OK : " + responseCode);
			}
			return false;
		} 
		if (StringUtils.hasText(xmlStringFrom)) {
			if (tagName.equals("xhtml:html")) {
				log.debug("cas xhtml " + tagName );
			}
			/**
			 * Copie vers le nouveau dossier si on a rÃÂ©cupÃÂ©rÃÂ© un fichier
			 */
			HttpURLConnection connTo = (HttpURLConnection) urlTo.openConnection();
			
			// TODO : securiser en ayant au moins un utilisateur cote orbeon
			if (!orbeonUsername.equals("")) {
				String credentials = new BASE64Encoder().encode(
						(orbeonUsername + ":" + orbeonPassword).getBytes());
				connTo.setRequestProperty(AUTHORIZATION, BASIC + credentials);
			}
			connTo.setRequestMethod("PUT");
			connTo.setDoOutput(true);
			connTo.setRequestProperty("ContentType", "text/xml");
			Writer writer = null;
			try {
				writer = new OutputStreamWriter(connTo.getOutputStream(), "UTF-8");
				writer.write(xmlStringFrom);
			} finally {
				writer.close();
			}
			connTo.connect();
			
			if (connTo.getResponseCode() != HttpURLConnection.HTTP_CREATED) {
				if (log.isDebugEnabled()) {
					log.debug("Code rÃÂ©ponse" + connTo.getResponseCode());
					log.debug("ProblÃÂ¨me lors de la copie vers : " + urlTo );
				}
				return false;
			}
			
			if (log.isDebugEnabled()) {
				log.debug("Copie vers : " + urlTo + " fini");
			}

			/**
			 * Suppression du fichier
			 */
			HttpURLConnection connDel = (HttpURLConnection) urlDel.openConnection();
	
			// TODO : securiser en ayant au moins un utilisateur cote orbeon
			if (!orbeonUsername.equals("")) {
				String credentials = new BASE64Encoder().encode(
						(orbeonUsername + ":" + orbeonPassword).getBytes());
				connDel.setRequestProperty(AUTHORIZATION, BASIC + credentials);
			}
			connDel.setRequestMethod("DELETE");
			connDel.setDoOutput(true);
			connDel.connect();
			if (connDel.getResponseCode() == HttpURLConnection.HTTP_OK) {
				if (log.isDebugEnabled()) {
					log.debug("Suppression de " + urlDel + " fait et fin de procÃÂ©dure");
				}
				return true;
			}
			if (log.isDebugEnabled()) {
				log.debug("ProblÃÂ¨me de la suppression de " + urlDel );
			}
			return false;
		}
		return true;
	}
	/**
	 * @param formName
	 * @return true if the creation is effective
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public boolean createForm(final String formName) 
	throws IOException, ParserConfigurationException, SAXException {
		if (log.isDebugEnabled()) {
			log.debug("entering createForm (" + formName + " )");
		}
		URL url = new URL(orbeonUrl + ACCES_FORM_DATA 
				+ formName + DATA_FILE_NAME);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// TODO : securiser en ayant au moins un utilisateur cote orbeon
		if (!orbeonUsername.equals("")) {
			String credentials = new BASE64Encoder().encode(
					(orbeonUsername + ":" + orbeonPassword).getBytes());
			conn.setRequestProperty(AUTHORIZATION, BASIC + credentials);
		}
		conn.setRequestMethod("PUT");
		conn.setDoOutput(true);
		conn.setRequestProperty("ContentType", "text/xml");
		Writer writer = null;
		try {
			writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
			writer.write(readBuilderTemplate().
					replaceAll("formName", formName).replaceAll("appName", APP_NAME));
		} finally {
			writer.close();
		}
		conn.connect();
		if (log.isDebugEnabled()) {
			log.debug("--- Server returned response code " + conn.getResponseCode() + " --- " 
					+ orbeonUrl + ACCES_FORM_DATA 
					+ formName + DATA_FILE_NAME);
		}
		if (conn.getResponseCode() == HttpURLConnection.HTTP_CREATED) {
			return true;
		}
		return false;

	}

	/**
	 * @param code
	 * @return if the deletion is effective
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public boolean deleteForm(final String code) throws IOException, ParserConfigurationException, SAXException {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		URL url = new URL(orbeonUrl + ACCES_FORM_DATA + code);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// TODO : securiser en ayant au moins un utilisateur cote orbeon
		if (!orbeonUsername.equals("")) {
			String credentials = new BASE64Encoder().encode(
					(orbeonUsername + ":" + orbeonPassword).getBytes());
			conn.setRequestProperty(AUTHORIZATION, BASIC + credentials);
		}
		conn.setRequestMethod("DELETE");
		conn.setDoOutput(true);
		conn.connect();
		if (log.isDebugEnabled()) {
			log.debug("--- Server returned response code " + conn.getResponseCode() + " ---");
		}
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return true;
		}
		return false;

	}

	/**
	 * @param code
	 * @return if the deletion is effective
	 * @throws IOException
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 */
	public boolean deleteFolder(final String formName) 
	throws IOException, ParserConfigurationException, SAXException {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		URL url = new URL(orbeonUrl + ACCES_DB 
				+ APP_NAME + "/" + formName);
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		// TODO : securiser en ayant au moins un utilisateur cote orbeon
		if (!orbeonUsername.equals("")) {
			String credentials = new BASE64Encoder().encode(
					(orbeonUsername + ":" + orbeonPassword).getBytes());
			conn.setRequestProperty(AUTHORIZATION, BASIC + credentials);
		}
		conn.setRequestMethod("DELETE");
		conn.setDoOutput(true);
		conn.connect();
		if (log.isDebugEnabled()) {
			log.debug("--- Server returned response code " + conn.getResponseCode() + " ---");
		}
		if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
			return true;
		}
		return false;

	}

	/**
	 * @see org.esupportail.opi.domain.OrbeonService#
	 * copyForm(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean copyForm(final String formNameFrom, final String formNameTo)
	throws IOException, ParserConfigurationException, SAXException {
		URL urlFrom = new URL(orbeonUrl + ACCES_FORM_DATA 
				+ formNameFrom + DATA_FILE_NAME);
		URL urlTo = new URL(orbeonUrl + ACCES_FORM_DATA 
				+ formNameTo + DATA_FILE_NAME);
		URL urlDel = new URL(orbeonUrl + ACCES_FORM_DATA 
				+ formNameFrom);
		String tagName = "xhtml:html";
		return copyData(urlFrom, urlTo, urlDel, tagName);
		
	}
	
	/**
	 * @see org.esupportail.opi.domain.OrbeonService#
	 * copyForm(java.lang.String, java.lang.String, java.lang.String)
	 */
	public boolean copyTemplateForm(final String formNameFrom, final String formNameTo)
	throws IOException, ParserConfigurationException, SAXException {
		URL urlFrom = new URL(orbeonUrl + ACCES_DB 
				+ APP_NAME + "/" + formNameFrom + "/form/form.xhtml");
		URL urlTo = new URL(orbeonUrl + ACCES_DB 
				+ APP_NAME + "/" + formNameTo + "/form/form.xhtml");
		URL urlDel = new URL(orbeonUrl + ACCES_DB 
				+ APP_NAME + "/" + formNameFrom + "/form");
		String tagName = "xhtml:html";
		return copyData(urlFrom, urlTo, urlDel, tagName);
		
	}
	
	/**
	 * @return the orbeon's builder template
	 */
	public String readBuilderTemplate() {
		StringBuilder contents = new StringBuilder();
		// On lit le fichier template pour renvoyer le contenu en String
		try {
			BufferedReader input =  new BufferedReader(new FileReader(xslXmlPath + "builder-template.xml"));
			try {
				String line = null; 
				while ((line = input.readLine()) != null) {
					contents.append(line);
					contents.append(System.getProperty("line.separator"));
				}
			} finally {
				input.close();
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return contents.toString();

	}

	public byte[] getPdf(final IndFormulaire indFormulaire,
	    final String sLabelRI) throws IOException {

		URL url = new URL(getOrbeonOpiUrl() 
				+ indFormulaire.getVersionEtpOpi().getCodEtp() + "-" 
				+ indFormulaire.getVersionEtpOpi().getCodVrsVet() 
				+ "-" + sLabelRI + "/pdf/" 
				+ indFormulaire.getIndividu().getNumDossierOpi());

		byte[] b = null;
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		int size = conn.getContentLength();
		InputStream in = conn.getInputStream();

		if (in != null) {
			if (size != -1) {
				int count = 0;
				int index = 0;
				b = new byte[size];

				// read in the bytes from input stream
				while ((count = in.read(b, index, size)) > 0) {
					size -= count;
					index += count;
				}
			} else {
				int count;
				ByteArrayOutputStream bo = new ByteArrayOutputStream();
				b = new byte[256];
				try {
					while ((count = in.read(b, 0, 256)) > 0) {
						bo.write(b, 0, count);
					}
					byte[] thebytes = bo.toByteArray();
					return thebytes;
				} finally {
					bo.close();
					bo = null;
				}
			}
			in.close();
		}
		return b;
	}
	
	/**
	 * Initialisation de la connexion avec la base de donnÃ¯Â¿Â½e OrbÃ¯Â¿Â½on.
	 */
	public void initConnexionBase() {
		try {
			Class< ? > cl = Class.forName("org.exist.xmldb.DatabaseImpl");
			Database database = (Database) cl.newInstance();
			DatabaseManager.registerDatabase(database);
			
			Collection root = DatabaseManager.getCollection(uri + "/db", "admin", "");
			xPathQueryService = (XPathQueryService) root.getService("XPathQueryService", "1.0");
			xPathQueryService.setProperty("indent", "yes");
		} catch (ClassNotFoundException e) {
			log.error(e.getMessage());
		} catch (InstantiationException e) {
			log.error(e.getMessage());
		} catch (IllegalAccessException e) {
			log.error(e.getMessage());
		} catch (XMLDBException e) {
			log.error(e.getMessage());
		}
	}
	
	/**
	 * Libelles de la base Orbeon.
	 */
	public Map<String, String> getLibelleBaseXml(
			final VersionEtpOpi versionEtpOpi, final String sLabelRI) {
		Map<String, String> mapLibelleXml = new HashMap<String, String>();

		StringBuffer sbRequeteLib = new StringBuffer();
		
		sbRequeteLib
				.append("let $file := document('/db/orbeon/builder/data/")
				.append(versionEtpOpi.getCodEtp()).append("-")
				.append(versionEtpOpi.getCodVrsVet()).append("-")
				.append(sLabelRI)
				.append("/data.xml')" + "return concat(");
		
		int countSection = 1;
		Map<String, List<String>> listSection = getLibelleSection(
				versionEtpOpi, sLabelRI);
		for (Map.Entry<String, List<String>> resourceMap : listSection.entrySet()) {
			int countControl = 1;
			for (String control : resourceMap.getValue()) {
				sbRequeteLib
						.append("'")
						.append(control)
						.append(S2)
						.append("',$file/*/*/*/*/resources/resource/")
						.append(resourceMap.getKey())
						.append("/label,'->',$file/*/*/*/*/resources/resource/")
						.append(control)
						.append("/label,'->',$file/*/*/*/*/form/")
						.append(resourceMap.getKey()).append("/").append(control);
				
				if (countControl == resourceMap.getValue().size()
						&& countSection == listSection.keySet().size()) {
					sbRequeteLib.append(")");
				} else {
					sbRequeteLib.append(",'").append(S1).append("',");
				}

				countControl++;
			}
			countSection++;
		}
		
		String requeteLib = sbRequeteLib.toString();
		
		if (log.isDebugEnabled()) {
			log.debug("requete libelle orbeon : " + requeteLib);
		}

		for (String ch : getResultRequeteXml(requeteLib).split(S1)) {
			String control = null;
			StringBuffer lib = new StringBuffer();
			for (String val : ch.split(S2)) {
				if (control == null) {
					control = val;
				} else {
					lib.append(val);
				}
			}
			if (log.isDebugEnabled()) {
				log.debug("Control : " + control + " --> result : " + lib);
			}
			mapLibelleXml.put(control, getConvertChaineCsv(lib.toString()));
		}

		return mapLibelleXml;
	}
	
	/**
	 * @param codEtp
	 * @param codVrsVet
	 * @param regimeInscription
	 * @return map
	 */
	public Map<String, List<String>> getLibelleSection(final VersionEtpOpi versionEtpOpi,
			final String sLabelRI) {
		
//		if (vetLibSection == null || !vetLibSection.getCodEtp().equals(versionEtpOpi.getCodEtp())
//				|| vetLibSection.getCodVrsVet() != versionEtpOpi.getCodVrsVet()) {
//			listSection.clear();
//			vetLibSection = versionEtpOpi;
		Map<String, List<String>> listSection = new HashMap<String, List<String>>();
		
		String requeteSection = "let $file := document('/db/orbeon/builder/data/"
					+ versionEtpOpi.getCodEtp() + "-" + versionEtpOpi.getCodVrsVet() +  "-"
					+ sLabelRI + "/data.xml')"
				+ "return $file/*/*/*/*/form/*/name()";
			
			try {
				ResourceSet resultSection = xPathQueryService.query(requeteSection);
				ResourceIterator iteratorSection = resultSection.getIterator();
				
				while (iteratorSection.hasMoreResources()) {
					String section = (String) iteratorSection.nextResource().getContent();
					if (log.isDebugEnabled()) {
						log.debug(section + "  :");
					}
					List<String> listControl = new ArrayList<String>();
					
					String requeteControl = "let $file := document('/db/orbeon/builder/data/"
						+ versionEtpOpi.getCodEtp() + "-" + versionEtpOpi.getCodVrsVet() +  "-"
						+ sLabelRI + "/data.xml')"
					+ "return $file/*/*/*/*/form/" + section + "/*/name()";
	
					ResourceSet resultControl = xPathQueryService.query(requeteControl);
					ResourceIterator iteratorControl = resultControl.getIterator();
					while (iteratorControl.hasMoreResources()) {
						String control = (String) iteratorControl.nextResource().getContent();
						if (log.isDebugEnabled()) {
							log.debug("  ---> " + control);
						}
						listControl.add(control);
					}
					listSection.put(section, listControl);
				}
			} catch (XMLDBException e) {
				log.error(e.getMessage());
			}
		
		return listSection;
	}
	
	/**
	 * @param requete
	 * @return Resultat de la requete
	 */
	private String getResultRequeteXml(final String requete) {
		String resultXml = "";
		
		try {
			ResourceSet result = xPathQueryService.query(requete);
			ResourceIterator iterator = result.getIterator();
			
			while (iterator.hasMoreResources()) {
				resultXml = (String) iterator.nextResource().getContent();
			}
		} catch (XMLDBException e) {
			log.error(e.getMessage());
		}
		
		resultXml = resultXml.replaceAll("[\r\n]+", "");
		
		log.debug("resultat requete XML : " + resultXml);
		
		return resultXml;
	}
	
	/**
	 * 
	 */
	public List<Map<String, String>> recupAllInfosXML(
			final List<Individu> individus, final VersionEtpOpi versionEtpOpi,
			final String sLabelRI) {

		StringBuffer sbRequeteHead = new StringBuffer();
		StringBuffer sbRequeteBody = new StringBuffer();
		boolean premiereLigne = true;
		for (Individu ind : individus) {
			sbRequeteHead.append(getHeadRequete(ind, versionEtpOpi, sLabelRI));
			if (premiereLigne) {
				sbRequeteBody.append("'numDos").append(S2)
						.append(ind.getNumDossierOpi()).append(S1).append("',")
						.append(getBodyRequete(ind, versionEtpOpi, sLabelRI));
				premiereLigne = false;
			} else {
				sbRequeteBody.append(",'").append(S3)
						.append(PilotageService.NUM_DOS).append(S2)
						.append(ind.getNumDossierOpi()).append(S1).append("',")
						.append(getBodyRequete(ind, versionEtpOpi, sLabelRI));
			}
		}

		StringBuffer sbRequete = new StringBuffer();
		sbRequete.append(sbRequeteHead).append("return concat(")
				.append(sbRequeteBody).append(")");
		if (log.isDebugEnabled()) {
			log.debug("requete infos XML : " + sbRequete.toString());
		}

		List<Map<String, String>> listInfosXml = new ArrayList<Map<String, String>>();
		String[] tabResultRequete = getResultRequeteXml(sbRequete.toString())
				.split(S3);
		for (String infoByInd : tabResultRequete) {
			Map<String, String> mapResultXml = new HashMap<String, String>();
			String[] tabInfoByInd = infoByInd.split(S1);
			for (String infoByChamp : tabInfoByInd) {
				String control = null;
				StringBuffer sbResult = new StringBuffer();
				for (String val : infoByChamp.split(S2)) {
					if (control == null) {
						control = val;
					} else {
						sbResult.append(val);
					}
				}

				String sResult = sbResult.toString();
				if (log.isDebugEnabled()) {
					log.debug("Control : " + control + " --> result : "
							+ sResult);
				}
				mapResultXml.put(control, getConvertChaineCsv(sResult));
			}
			listInfosXml.add(mapResultXml);
		}
		return listInfosXml;
	}
	
	/**
	 * @param ind
	 * @param versionEtpOpi
	 * @param ri
	 * @return
	 */
	public String getHeadRequete(final Individu ind, final VersionEtpOpi versionEtpOpi,
			final String sLabelRI) {
		return new StringBuffer().append("let $file_")
				.append(ind.getNumDossierOpi()).append(" := ")
				.append("document('/db/OPI/").append(versionEtpOpi.getCodEtp())
				.append("-").append(versionEtpOpi.getCodVrsVet()).append("-")
				.append(sLabelRI).append("/data/")
				.append(ind.getNumDossierOpi()).append("/data.xml')")
				.toString();
	}
	
	/**
	 * @param ind
	 * @param versionEtpOpi
	 * @param ri
	 * @return String
	 */
	public String getBodyRequete(final Individu ind,
			final VersionEtpOpi versionEtpOpi, final String sLabelRI) {
		StringBuffer sbRequestBody = new StringBuffer();
		boolean premiereLigne = true;
		Map<String, List<String>> listSection = getLibelleSection(
				versionEtpOpi, sLabelRI);
		for (Map.Entry<String, List<String>> ressourceMap : listSection.entrySet()) {
			List<String> ksRessource = ressourceMap.getValue();
			for (String control : ksRessource) {
				if (premiereLigne) {
					premiereLigne = false;
				} else {
					sbRequestBody.append(",'").append(S1).append("',");
				}
				sbRequestBody.append("'").append(control).append(S2)
						.append("',replace($file_")
						.append(ind.getNumDossierOpi()).append("/form/")
						.append(ressourceMap.getKey()).append("/").append(control)
						.append(", '\"', \"'\")");
			}
		}
		return sbRequestBody.toString();
	}
	
	/**
	 * @param chaine
	 * @return String
	 */
	public String getConvertChaineCsv(final String chaine) {
		if (chaine == null) {
			return null;
		}
		return chaine.replace(';', ',');
	}

	/**
	 * @return the orbeonUrl
	 */
	public String getOrbeonUrl() {
		return orbeonUrl;
	}

	/**
	 * @param orbeonUrl the orbeonUrl to set
	 */
	public void setOrbeonUrl(final String orbeonUrl) {
		this.orbeonUrl = orbeonUrl;
	}

	/**
	 * @return the editBuilderUrl
	 */
	public String getOrbeonBuilderUrl() {
		if (orbeonUrl == null || orbeonUrl.equals("")) {
			return null;
		}
		return orbeonUrl + ORBEON_BUILDER_URL;
	}

	/**
	 * @return the orbeonOpiUrl
	 */
	public String getOrbeonOpiUrl() {
		return orbeonUrl + ORBEON_OPI_URL;
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

	/**
	 * @return the orbeonUser
	 */
	public String getOrbeonUsername() {
		return orbeonUsername;
	}

	/**
	 * @return the orbeonPassword
	 */
	public String getOrbeonPassword() {
		return orbeonPassword;
	}

	/**
	 * @param orbeonUsername the orbeonUser to set
	 */
	public void setOrbeonUsername(final String orbeonUsername) {
		this.orbeonUsername = orbeonUsername;
	}

	/**
	 * @param orbeonPassword the orbeonPassword to set
	 */
	public void setOrbeonPassword(final String orbeonPassword) {
		this.orbeonPassword = orbeonPassword;
	}

	/**
	 * @return the uri
	 */
	public String getUri() {
		return uri;
	}

	/**
	 * @param uri the uri to set
	 */
	public void setUri(final String uri) {
		this.uri = uri;
	}
	
	


}
