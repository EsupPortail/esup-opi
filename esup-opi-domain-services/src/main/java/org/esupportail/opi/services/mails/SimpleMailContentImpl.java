/**
 * 
 */
package org.esupportail.opi.services.mails;

import static fj.data.Option.*;
import static fj.data.Stream.*;
import fj.F;
import fj.data.Option;

import java.util.ArrayList;
import java.util.List;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.mails.MailContent;
import org.esupportail.opi.utils.Constantes;
import org.springframework.util.StringUtils;

/**
 * @author cleprous
 *
 */
public class SimpleMailContentImpl extends AbstractMailContentImpl {


	/*
	 ******************* PROPERTIES ******************* */


	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -321813007559244805L;


	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());	

	/**
	 * The key bundle for default body of the mail.
	 */
	private String defaultBody;


	/**
	 * The key bundle default subject of the mail.
	 */
	private String defaultSubject;

	/**
	 * The all objects to make the mail.
	 */
	private Object[] objects;


	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public SimpleMailContentImpl() {
		super();
	}

	/**
	 * @throws Exception
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		Assert.notNull(getCode(), 
				"property code of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(defaultSubject, 
				"property defaultSubject of class " + this.getClass().getName() + " can not be null");
		Assert.notNull(defaultBody, 
				"property defaultBody of class " + this.getClass().getName() + " can not be null");
		super.afterPropertiesSet();
	}

	/*
	 ******************* METHODS ********************** */


	/**
	 * @return String the body in mailContent
	 */
	public String makeBody() {
		if (getMailContent() == null) {
			throw new ConfigException("the getMailContent() does not be null");
		}
		//si le body est vide on prend la valeure par défaut
		if (StringUtils.hasText(getMailContent().getBody())) {
			return getMailContent().getBody();
		}
		return getDefaultBody();
	}

	/**
	 * @return String the subject in mailContent
	 */
	public String makeSubject() {
		if (getMailContent() == null) {
			throw new ConfigException("the getMailContent() does not be null");
		}
		//si le body est vide on prend la valeure par défaut
		if (StringUtils.hasText(getMailContent().getSubject())) {
			return getMailContent().getSubject();
		}
		return getDefaultSubject();
	}



	/**
	 * @see org.esupportail.opi.services.mails.MailContentService#send(java.util.List)
	 */
	@Override
	public void send(final List<String> list) {
		if (log.isDebugEnabled()) {
			log.debug("entering send addresses = " + list);
		}
		//test si les adresses sont valide
		List<String> adresses = testAddressMail(list);
		if (adresses.isEmpty()) {
			throw new ConfigException("this email list is not valid : " + list);
		}
		InternetAddress[] tab = new InternetAddress[adresses.size()];
		for (int i = 0; i < adresses.size(); ++i) {
			try {
				tab[i] = new InternetAddress(adresses.get(i));
			} catch (AddressException e) {
				log.error(e);
			}
		}
		getSmtpService().sendtocc(
				tab, null, null, 
				makeSubject(), 
				makeBody(), null, null);
		setObjects(null);
	}

	/**
	 * @param addresses
	 * @param objects
	 */
	private synchronized void sendTocc(final List<String> addresses, final Object[] objects) {
		setObjects(objects);
		send(addresses);
	}

	/**
	 * @see org.esupportail.opi.services.mails.MailContentService#send(
	 * java.util.List, java.util.List)
	 */
	@Override
	public void send(final List<String> addresses, final List<Object> objects) {
		if (log.isDebugEnabled()) {
			log.debug("entering send addresses = " + addresses + "objects == " + objects);
		}
		sendTocc(addresses, objects.toArray());
	}


	/**
	 * @see org.esupportail.opi.services.mails.MailContentService#send(
	 * java.lang.String, java.util.List)
	 */
	@Override
	public void send(final String address, final List<Object> objects) {
		if (log.isDebugEnabled()) {
			log.debug("entering send");
			log.debug("entering address = " + address);
			log.debug("entering objects = " + objects);
		}
		List<String> ad = new ArrayList<String>();
		ad.add(address);
		send(ad, objects);

	}

	/**
	 * @see org.esupportail.opi.services.mails.MailContentService#send(
	 * java.lang.String, java.lang.String, java.util.List)
	 */
	@Override
	public void send(final String address, final String address2, final List<Object> objects) {
		if (log.isDebugEnabled()) {
			log.debug("entering ");
		}
		if (log.isDebugEnabled()) {
			log.debug("entering send");
			log.debug("entering address = " + address);
			log.debug("entering address2 = " + address2);
			log.debug("entering objects = " + objects);
		}
		List<String> ad = new ArrayList<String>();
		if(address != null) {
			ad.add(address);
		}
		if(address2 != null) {
			ad.add(address2);
		}
//		ad.add(address);
//		ad.add(address2);
		send(ad, objects);

	}

	/**
	 * @see org.esupportail.opi.services.mails.MailContentService#send(java.util.List, java.lang.Object[])
	 */
	@Override
	public void send(final List<String> addresses, final Object... o1) {
		if (log.isDebugEnabled()) {
			log.debug("entering send");
			log.debug("entering addresses " + addresses);
			log.debug("entering o1 " + o1);
		}
		sendTocc(addresses, o1);

	}

	/**
	 * @param addresses
	 * @return True if all email is valid.
	 */
	public List<String> testAddressMail(final List<String> addresses) {
		return new ArrayList<String>(
				somes(iterableStream(addresses).map(
						Option.<String>fromNull())).filter(
								new F<String, Boolean>() {
									public Boolean f(String s) {
										return s.matches(Constantes.MAILREGEX);
									}
								}).toCollection());
	}

	/**
	 * @see org.esupportail.opi.services.mails.MailContentService#getDefaultSubject()
	 */
	public String getDefaultSubject() {
		return getI18Service().getString(defaultSubject);
	}

	/**
	 * @see org.esupportail.opi.services.mails.MailContentService#getDefaultBody()
	 */
	public String getDefaultBody() {
		return getI18Service().getString(defaultBody);
	}

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return MailContent
	 */
	public MailContent getMailContent() {
		return getParameterService().getMailContent(getCode());
	}


	/**
	 * @param defaultBody the defaultBody to set
	 */
	public void setDefaultBody(final String defaultBody) {
		this.defaultBody = defaultBody;
	}


	/**
	 * @param defaultSubject the defaultSubject to set
	 */
	public void setDefaultSubject(final String defaultSubject) {
		this.defaultSubject = defaultSubject;
	}


	/**
	 * @return List of objects.
	 */
	public Object[] getObjects() {
		if (log.isDebugEnabled()) {
			for (Object o : objects) {
				log.debug("Liste des objets : " + o.getClass());
			}
		}
		return objects;
	}

	/** 
	 * @see org.esupportail.opi.services.mails.SimpleMailContentImpl#setObjects(java.util.List)
	 */
	public void setObjects(final Object[] objects) {
		this.objects = objects;
	}
}
