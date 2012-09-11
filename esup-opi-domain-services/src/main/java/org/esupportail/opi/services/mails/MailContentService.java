/**
 * 
 */
package org.esupportail.opi.services.mails;

import java.util.List;
import java.util.Map;

/**
 * @author cleprous
 *
 */
public interface MailContentService {
	
	
	/**
	 * send the mail at the list address. 
	 * @param addresses
	 */
	void send(List<String> addresses);
	
	
	/**
	 * send the mail at the address. 
	 * @param address
	 * @param objects for dynamics mails.
	 */
	void send(String address, List<Object> objects);
	
	/**
	 * send the mail at the addresses. 
	 * @param address
	 * @param address2
	 * @param objects for dynamics mails.
	 */
	void send(String address, String address2, List<Object> objects);
	
	/**
	 * send the mail at the list address. 
	 * @param addresses
	 * @param objects for dynamics mails.
	 */
	void send(List<String> addresses, List<Object> objects);
	

	/**
	 * send the mail at the list address. 
	 * @param addresses
	 * @param o1 for dynamics mails.
	 */
	void send(List<String> addresses, Object... o1);
	
	

	/**
	 * @return String code to the mailContent.
	 */ 
	String getCode();
	
	/**
	 * the key is the attributes and the value is a key bundles.
	 * @return Map
	 */
	Map<String, String> getDefinitions();
	
	/**
	 * the default subject.
	 * @return String
	 */
	String getDefaultSubject();
	
	/**
	 * the defaultBody.
	 * @return String
	 */
	String getDefaultBody();
	

	
}
