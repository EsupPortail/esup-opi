/**
 * 
 */
package org.esupportail.opi.services;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;

import junit.framework.TestCase;

import org.esupportail.commons.services.database.DatabaseUtils;
import org.esupportail.commons.utils.BeanUtils;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.OpiWebService;
import org.esupportail.opi.domain.OrbeonService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatConfirme;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.services.mails.SimpleMailContentImpl;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.xml.sax.SAXException;



/**
 * @author ylecuyer
 *
 */
public class TestUnitaireOpi extends TestCase {

	/**
	 * Constructor.
	 */
	public TestUnitaireOpi() {
		super();
	}
	
	/**
	 * Return List correct mail addresses.
	 */
	public void testTestAddressMail() {
		System.out.println("######In testTestAddressMail######");
		List<String> list = new ArrayList<String>();
		list.add("ylecuyer@gmail.com");
		list.add(null);
		list.add("ylecuyer@gmail");
		list.add("ylecuyer.com");
		list.add("ylecuyer@yahoo.fr");
		list.add("ylecuyer@yahoo.");
		
		SimpleMailContentImpl mail = new SimpleMailContentImpl(); 
		List<String> listCorrect = mail.testAddressMail(list);
		System.out.println(" testTestAddressMail() = " + listCorrect);
		assertTrue("list mail ne doit pas être vide ", !listCorrect.isEmpty());
	}
	
	/**
	 * test if the trtCmi for the etape SM1212 is connected to a commission.
	 */
	public void testIsConnectCmi() {
		System.out.println("######In testIsConnectCmi######");
		DomainApoService domainApoService = (DomainApoService) BeanUtils.getBean("domainApoService");
		ParameterService parameterService = (ParameterService) BeanUtils.getBean("parameterService");
		try { 
			DatabaseUtils.open();
			DatabaseUtils.begin();
			List<VersionEtapeDTO> etapes = domainApoService.getVersionEtapes("SM1212", null, null, "2009");
			for (VersionEtapeDTO v : etapes) {
				TraitementCmi t = new TraitementCmi("SR", v);
				System.out.println("testIsConnectCmi() -- " 
						+ parameterService.isConnectCmi(t) + " -- for TrtCmi : " + t 
						);
			}
			
		} catch (Exception e) {
			DatabaseUtils.rollback();
		} finally {
			DatabaseUtils.close();
		}
	}
	
	/**
	 * test the copy of the response formulary in orbeon.
	 */
	public void testCopyOrbeonDataResponse() {
		System.out.println("######In testCopyOrbeonData######");
		OrbeonService orbeonService = (OrbeonService) BeanUtils.getBean("orbeonService");
		
		String formNameFrom = "GL0153-281";
		String formNameTo = "GL0153-281-FI";
		String numDossier = "DWHX45W8";
		try {
			boolean trtOk = orbeonService.copyResponse(formNameFrom, formNameTo, numDossier);
			System.out.println("testCopyOrbeonDataResponse() -- résultat du traitement " + trtOk);
			assertTrue("La copie ne s'est pas bien passée", trtOk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * test the copy of the response formulary in orbeon.
	 */
	public void testCopyOrbeonDataForm() {
		System.out.println("######In testCopyOrbeonData######");
		OrbeonService orbeonService = (OrbeonService) BeanUtils.getBean("orbeonService");
		
		String formNameFrom = "GL0153-281";
		String formNameTo = "GL0153-281-FI";
		try {
			boolean trtOk = orbeonService.copyForm(formNameFrom, formNameTo);
			System.out.println("testCopyOrbeonDataResponse() -- résultat du traitement " + trtOk);
			assertTrue("La copie ne s'est pas bien passée", trtOk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * test the copy of the response formulary in orbeon.
	 */
	public void testCopyOrbeonDataFormTemplate() {
		System.out.println("######In testCopyOrbeonData######");
		OrbeonService orbeonService = (OrbeonService) BeanUtils.getBean("orbeonService");
		
		String formNameFrom = "GL0153-281";
		String formNameTo = "GL0153-281-FI";
		try {
			boolean trtOk = orbeonService.copyTemplateForm(formNameFrom, formNameTo);
			System.out.println("testCopyOrbeonDataResponse() -- résultat du traitement " + trtOk);
			assertTrue("La copie ne s'est pas bien passée", trtOk);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Return the VersionEtapes with codVdi = T2056 and codVrsVdi = 281. and date Rct = 2010.
	 */
	public void testGetVetForVdiAll() {
		System.out.println("######In testGetVetForVdiAll with codVdi = T2056 and codVrsVdi "
				+ "= 281. and date Rct = 2010######");
		DomainApoService domainApoService = (DomainApoService) BeanUtils.getBean("domainApoService");
		
		VersionDiplomeDTO v = new VersionDiplomeDTO();
		v.setCodDip("T2056");
		v.setCodVrsVdi(281);
		List<VersionEtapeDTO> e = domainApoService.getVersionEtapes(v, "2010");
		System.out.println("liste de version etape = " + e);
		assertNotNull(" VersionEtape ne doit pas être null", e);

	}

	/**
	 * Return the VersionEtapes with codVdi = T2056 and codVrsVdi = 281. and date Rct = 2010.
	 */
	public void testLaunchWebServiceOpi() {
		System.out.println("######In testLaunchWebServiceOpi with numDoss = BSGLW15H and mail yoann.lecuyer@univ-rennes1.fr");
		
		DomainService domainService = (DomainService) BeanUtils.getBean("domainService");
		OpiWebService opiWebService = (OpiWebService) BeanUtils.getBean("opiWebService");
		try {

			DatabaseUtils.open();
			DatabaseUtils.begin();
			Individu i = domainService.getIndividuByMail("yoann.lecuyer@univ-rennes1.fr");
			
			List<IndVoeu> list = new ArrayList<IndVoeu>();
			for (IndVoeu indVoeu : i.getVoeux()) {
				if (indVoeu.getState().equals(EtatConfirme.I18N_STATE)) {
					list.add(indVoeu);
				}
			}
			Boolean testOpi = false;
			if (!list.isEmpty() && opiWebService != null) {
				//on deverse tous les temps dans Apogee
				testOpi = opiWebService.launchWebService(i, list);
			}
			assertTrue("Erreur lors du lancement du webservice", testOpi);
			DatabaseUtils.commit();

		} catch (Exception e) {
			DatabaseUtils.rollback();
		} finally {
			DatabaseUtils.close();
		}
		

	}


	
	
}
