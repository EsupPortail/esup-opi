/**
 * 
 */
package org.esupportail.opi.utils.ldap;

import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;

import static fj.Bottom.error;
import static fj.data.Option.fromNull;

/**
 * All LDAP attributes used in OPI.
 */
public class LdapAttributes {

	/**
	 * The LDAP attribute that contains the uid. 
	 */
    public final String uidAttribute;
	
	/**
	 * The LDAP attribute that contains the display name. 
	 */
    public final String displayNameAttribute;
	
	/**
	 * The LDAP attribute that contains the mail. 
	 */
	public final String emailAttribute;
	
	/**
	 * The LDAP attribute that contains the cn. 
	 */
	public final String cnAttribute;
	
	/**
	 * The LDAP attribute that contains the SurName. 
	 */
	public final String nomUsuelAttribute;
	
	/**
	 * The LDAP attribute that contains the firstName. 
	 */
	public final String prenomAttribute;
	
	/**
	 * The LDAP attribute that contains the eduPersonPrincipalName. 
	 */
	public final String eduPersonPrincipalNameAttribute;
	
	/**
	 * The LDAP filter that filters the pers. 
	 */
	public final String filterPers;


    private LdapAttributes() {
        uidAttribute = error("unauthorized constructor").toString();
        displayNameAttribute = error("unauthorized constructor").toString();
        emailAttribute = error("unauthorized constructor").toString();
        cnAttribute = error("unauthorized constructor").toString();
        nomUsuelAttribute = error("unauthorized constructor").toString();
        prenomAttribute = error("unauthorized constructor").toString();
        eduPersonPrincipalNameAttribute = error("unauthorized constructor").toString();
        filterPers = error("unauthorized constructor").toString();
    }

    private LdapAttributes(
            String uidAttribute,
            String displayNameAttribute,
            String emailAttribute,
            String cnAttribute,
            String nomUsuelAttribute,
            String prenomAttribute,
            String eduPersonPrincipalNameAttribute,
            String filterPers) {
        this.uidAttribute = fromNull(uidAttribute).valueE("uidAttribute is null !");
        this.displayNameAttribute = fromNull(displayNameAttribute).valueE("displayNameAttribute is null !");
        this.emailAttribute = fromNull(emailAttribute).valueE("emailAttribute us null !");
        this.cnAttribute = fromNull(cnAttribute).valueE("cnAttribute is null !");
        this.nomUsuelAttribute = fromNull(nomUsuelAttribute).valueE("nomUsuelAttribute is null !");
        this.prenomAttribute = fromNull(prenomAttribute).valueE("prenomAttribute is null !");
        this.eduPersonPrincipalNameAttribute =
                fromNull(eduPersonPrincipalNameAttribute).valueE("eduPersonPrincipalName is null !");
        this.filterPers = fromNull(filterPers).valueE("filterPers is null !");
    }

    public static LdapAttributes ldapAttributes(
            String uidAttribute,
            String displayNameAttribute,
            String emailAttribute,
            String cnAttribute,
            String nomUsuelAttribute,
            String prenomAttribute,
            String eduPersonPrincipalNameAttribute,
            String filterPers) {
        return new LdapAttributes(uidAttribute, displayNameAttribute, emailAttribute, cnAttribute,
                nomUsuelAttribute, prenomAttribute, eduPersonPrincipalNameAttribute, filterPers);
    }
}
