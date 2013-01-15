/**
 * 
 */
package org.esupportail.opi.utils.ldap;

import org.esupportail.commons.utils.Assert;
import org.springframework.beans.factory.InitializingBean;

/**
 * All LDAP attributes used to OPI.
 * This class is used to domainService
 * @author cleprous
 */
public class LdapAttributes implements InitializingBean {

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The LDAP attribute that contains the uid. 
	 */
	private String uidAttribute;
	
	/**
	 * The LDAP attribute that contains the display name. 
	 */
	private String displayNameAttribute;
	
	/**
	 * The LDAP attribute that contains the mail. 
	 */
	private String emailAttribute;
	
	/**
	 * The LDAP attribute that contains the cn. 
	 */
	private String cnAttribute;
	
	/**
	 * The LDAP attribute that contains the SurName. 
	 */
	private String nomUsuelAttribute;
	
	/**
	 * The LDAP attribute that contains the firstName. 
	 */
	private String prenomAttribute;
	
	/**
	 * The LDAP attribute that contains the eduPersonPrincipalName. 
	 */
	private String eduPersonPrincipalNameAttribute;
	
	/**
	 * The LDAP filter that filters the pers. 
	 */
	private String filterPers;
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public LdapAttributes() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() throws Exception {
		
		Assert.notNull(this.uidAttribute, 
				"property uidAttribute of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.displayNameAttribute, 
				"property displayNameAttribute of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.hasText(this.emailAttribute, 
				"property emailAttribute of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.hasText(this.cnAttribute, 
				"property cnAttribute of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.hasText(this.nomUsuelAttribute, 
				"property nomUsuelAttribute of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.hasText(this.prenomAttribute, 
				"property prenomAttribute of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.hasText(this.eduPersonPrincipalNameAttribute, 
				"property eduPersonPrincipalNameAttribute of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.hasText(this.filterPers, 
				"property filterPers of class " + this.getClass().getName() 
				+ " can not be null");
	}
	

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the uidAttribute
	 */
	public String getUidAttribute() {
		return uidAttribute;
	}



	/**
	 * @param uidAttribute the uidAttribute to set
	 */
	public void setUidAttribute(final String uidAttribute) {
		this.uidAttribute = uidAttribute;
	}



	/**
	 * @return the displayNameAttribute
	 */
	public String getDisplayNameAttribute() {
		return displayNameAttribute;
	}



	/**
	 * @param displayNameAttribute the displayNameAttribute to set
	 */
	public void setDisplayNameAttribute(final String displayNameAttribute) {
		this.displayNameAttribute = displayNameAttribute;
	}



	/**
	 * @return the emailAttribute
	 */
	public String getEmailAttribute() {
		return emailAttribute;
	}



	/**
	 * @param emailAttribute the emailAttribute to set
	 */
	public void setEmailAttribute(final String emailAttribute) {
		this.emailAttribute = emailAttribute;
	}



	/**
	 * @return the cnAttribute
	 */
	public String getCnAttribute() {
		return cnAttribute;
	}



	/**
	 * @param cnAttribute the cnAttribute to set
	 */
	public void setCnAttribute(final String cnAttribute) {
		this.cnAttribute = cnAttribute;
	}



	/**
	 * @return the nomUsuelAttribute
	 */
	public String getNomUsuelAttribute() {
		return nomUsuelAttribute;
	}



	/**
	 * @param nomUsuel the nomUsuel to set
	 */
	public void setNomUsuelAttribute(final String nomUsuel) {
		this.nomUsuelAttribute = nomUsuel;
	}



	/**
	 * @return the prenomAttribute
	 */
	public String getPrenomAttribute() {
		return prenomAttribute;
	}



	/**
	 * @param prenomAttribute the prenomAttribute to set
	 */
	public void setPrenomAttribute(final String prenomAttribute) {
		this.prenomAttribute = prenomAttribute;
	}



	/**
	 * @return the eduPersonPrincipalNameAttribute
	 */
	public String getEduPersonPrincipalNameAttribute() {
		return eduPersonPrincipalNameAttribute;
	}



	/**
	 * @param eduPersonPrincipalNameAttribute the eduPersonPrincipalNameAttribute to set
	 */
	public void setEduPersonPrincipalNameAttribute(final 
			String eduPersonPrincipalNameAttribute) {
		this.eduPersonPrincipalNameAttribute = eduPersonPrincipalNameAttribute;
	}

	/**
	 * @return the filterPers
	 */
	public String getFilterPers() {
		return filterPers;
	}

	/**
	 * @param filterPers the filterPers to set
	 */
	public void setFilterPers(final String filterPers) {
		this.filterPers = filterPers;
	}

	

}
