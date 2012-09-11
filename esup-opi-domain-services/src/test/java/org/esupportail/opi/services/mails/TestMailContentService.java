package org.esupportail.opi.services.mails;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;

import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.services.mails.MailContentService;

/**
 * @author cleprous
 *
 */
public class TestMailContentService extends TestCase {
	/*
	 ******************* PROPERTIES ******************* */


	/**
	 * The configuration file where Spring beans are defined.
	 */
	private static final String SPRING_CONFIG_FILE = "/properties/applicationContext.xml"; 

	/**
	 * The name of the client bean.
	 */
	private static final String BEAN_NAME = "createDosIndFI";

	/**
	 * ReadRennes1.
	 */
	private MailContentService mailContentService;





	/**
	 * @return the service.
	 */
	@SuppressWarnings("unused")
	private MailContentService getService() {
		if (mailContentService == null) {
			ClassPathResource res = new ClassPathResource(SPRING_CONFIG_FILE);
			BeanFactory beanFactory = new XmlBeanFactory(res);
			mailContentService = (MailContentService) beanFactory.getBean(BEAN_NAME);
		}

		assertNotNull(mailContentService);

		return mailContentService;
	}



	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 * @param name
	 */
	public TestMailContentService(final String name) {
		super(name);
	}

	/**
	 * Constructors.
	 */
	public TestMailContentService() {
		super();
	}



	/*
	 ******************* METHODS ********************** */


	//	public void testgetBody() {
	//		MailContent m = new MailContent();
	//		m.setBody("${individu.sexe}, je suis partis en vacances et mon numéro de dossier est ${individu.numDossierOpi}"
	//				+ "et voici mes bacs ${individu.indBac.codBac}");
	//		Individu i = new Individu();
	//		i.setSexe("M");
	//		i.setNumDossierOpi("dsfdsf");
	//		i.setIndBac(new HashSet<IndBac>());
	//		IndBac iB = new IndBac();
	//		iB.setCodBac("codeBac");
	//		i.getIndBac().add(iB);
	//		iB = new IndBac();
	//		iB.setCodBac("codeBac2");
	//		i.getIndBac().add(iB);
	//		List<Object> o = new ArrayList<Object>();
	//		o.add(i);
	//		getService().setObjects(o);
	//		String realBody = getService().getBody(m);
	//		System.out.println("realBody = " + realBody);
	//		Assert.assertNotNull(realBody);
	//	}


	//	public void testRegexExpression() {
	//		Pattern p = Pattern.compile("\\$\\{(.*?)\\}", Pattern.MULTILINE );
	//		String st = "${individu.sexe}, debuttlkjlkjltfin je suis partis en vacances et mon numéro de dossier est ${individu.numDossierOpi}"
	//			+ "et voici mes bacs ${individu.indBac.codBac} tototooto et ${begin_boucle:commissionPojo} tototot${end_boucle}";
	//		Matcher m = p.matcher(st);
	//					 
	//		while( m.find( ) ) {
	//			 System.out.println( "m : " + m ) ;
	//			 System.out.println( "group : " + m.group( ) ) ;
	//			 System.out.println( "position : " + m.start( ) ) ;
	//		}
	//		
	//		
	//	
	//	}

	//	public void testDeleteELCharacter() {
	//		String s = "${indivudi.code.tab}";		
	//		System.out.println(s.replaceAll("\\$\\{|\\}", ""));
	//	
	//	}


//	public void testGetPropertiesInBoucle() {
//		String s = "${begin_boucle value=&quot;individu.campagnes&quot; var=&quot;camp&quot;}";		
//		Pattern p = Pattern.compile("var=((\")|(&quot;))(.*?)((\")|(&quot;))");
//		Matcher m = p.matcher(s);
//		m.matches();
//		//System.out.println(m.group(1));
//		while( m.find( ) ) {
//			System.out.println( "m : " + m ) ;
//			System.out.println( "group : " + m.group( ) ) ;
//			System.out.println( "group(0) : " + m.group(0) ) ;
//			System.out.println( "group(1) : " + m.group(1) ) ;
//			System.out.println( "group(1) : " + m.group(2) ) ;
//			System.out.println( "group(1) : " + m.group(3) ) ;
//			System.out.println( "group(1) : " + m.group(4) ) ;
//			System.out.println( "group(1) : " + m.group(5) ) ;
//			System.out.println( "group(1) : " + m.group(6) ) ;
//			System.out.println( "group(1) : " + m.group(7) ) ;
//			System.out.println( "position : " + m.start() ) ;
//		}
//
//
//	}

	//	public void testDeleteBoucle() {
	//		String s = "${individu.sexe}, totototototot essaie"+
	//			"La création de votre dossier de candidature à l’Université de Rennes 1 a bien été enregistrée."+
	//			"Votre numéro de dossier est le : ${individu.numDossierOpi}."+
	//			"${begin_boucle value=individu.campagnes}"+
	//				"tototo et tata"+
	//			"${end_boucle}"+
	//			"Conservez précieusement ce numéro, il vous sera nécessaire pour consulter et modifier votre dossier électronique."+
	//			"Afin que votre dossier puisse être examiné, vous devez indiquer – si vous ne l’avez pas encore fait - sur le serveur candidatures.univ-rennes1.fr, la (ou les) formation(s) pour lesquelles vous vous portez candidat. Si vous êtes indécis sur vos choix d’orientation, n’hésitez pas à consulter l’offre de formation , de l’Université."+
	//			"Si nécessaire, vous pouvez également modifier les données précédemment saisies (coordonnées, cursus de formation, cursus professionnel…) lors d’une nouvelle connexion à candidatures.univ-rennes1.fr."+
	//			"pour toute information complémentaire, vous pouvez contacter le Service de la Scolarité concerné."+
	//			"Merci de votre attention."+
	//			";";
	//		s = s.replaceAll(MailContentUtils.REGEX_EXPRESSION, "");
	//		//REGEX BOUCLE "\\$\\{.*?boucle.*?\\}"
	//		System.out.println("s = " + s);
	//		
	//		
	//		
	//	}

//	public void testGetTestmapProperties() {
//		String expression = "trt.versionEtape.libWebVet";
//		String[] tab = expression.split(Pattern.quote("."));
//		Map<String, List<String>> props = new HashMap<String, List<String>>();
//		props = createMapProperties(tab[0], tab, null, props, 0);
//		System.out.println("props = " + props);
//	}
//
//	private Map<String, List<String>> createMapProperties(String key, String[] tabExpression, List<String> properties,
//			Map<String, List<String>> mapProps, int cpt) {
//		if (cpt == tabExpression.length - 1) {
//			//fin recursive
//			mapProps.get(key).add(tabExpression[cpt]);
//			return mapProps;
//		}
//		if (mapProps.get(key) == null) {
//			mapProps.put(key, new ArrayList<String>());
//		} else {
//			mapProps.get(key).add(tabExpression[cpt]);
//		}
//		int c = cpt + 1;
//		return createMapProperties(tabExpression[c], tabExpression, properties, mapProps, c);
//	}

	
	@SuppressWarnings("unchecked")
	public void testGetMethods() {
		Method[] methods = DomainService.class.getMethods();
		for (int i = 0; i < methods.length; ++i) {
			Method m = methods[i];
			for (int j = 0; j < m.getParameterTypes().length; ++j) {
				Class c = m.getParameterTypes()[j];
				System.out.println("c" + c);
			}
			System.out.println(m.getDefaultValue());
		}	
	}

}
