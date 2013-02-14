/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.web.controllers.user;

import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.utils.ldap.LdapAttributes;
import org.esupportail.opi.web.beans.beanEnum.WayfEnum;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * The bean used for LDAP searches.
 */
public class LdapSearchController extends AbstractContextAwareController {

    /**
     * The serialization id.
     */
    private static final long serialVersionUID = -3419447137883661806L;


	/*
     ******************* PROPERTIES ******************* */


    /**
     * see {@link LdapUserService}.
     */
    private LdapUserService ldapUserService;

    /**
     * This class contains all LDAP attributes used to OPI.
     */
    private LdapAttributes ldapAttributes;

    /**
     * Attributes cn.
     */
    private String login;

    /**
     * Attributes nomUsuel.
     */
    private String name;

    /**
     * Attributes Prenom.
     */
    private String firstName;

    /**
     * the result of the search, as a list of LdapUser.
     */
    private List<LdapUser> ldapManagers;

    /**
     * The LdapEnum.
     */
    private WayfEnum ldapEnum;

    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());
	
	
	/*
	 ******************* INIT ************************* */


    /**
     * Bean constructor.
     */
    public LdapSearchController() {
        super();
    }


    /**
     * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();
        Assert.notNull(this.ldapUserService, "property ldapUserService of class "
                + this.getClass().getName() + " can not be null");
        Assert.notNull(this.ldapAttributes, "property ldapAttributes of class "
                + this.getClass().getName() + " can not be null");
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        ldapEnum = new WayfEnum();
        ldapManagers = new ArrayList<LdapUser>();
        login = null;
        name = null;
        firstName = null;
    }
	
	/*
	 ******************* CALLBACK ********************** */


    /**
     * Callback to go back.
     *
     * @return String
     */
    public String goBack() {
        String returnStr = null;
        if (ldapEnum.getWhereAreYouFrom().equals(WayfEnum.MANAGER_VALUE)) {
            returnStr = NavigationRulesConst.ENTER_MANAGER;
        } else if (ldapEnum.getWhereAreYouFrom().equals(WayfEnum.MEMBER_CMI_VALUE)) {
            returnStr = NavigationRulesConst.ENTER_CMI;
        }
        reset();
        return returnStr;
    }
	
	/*
	 ******************* METHODS ********************** */

    /**
     * Look for a manager in LDAP.
     */
    public void searchManager() {
        //make filter
        String filter = "&(" + ldapAttributes.filterPers + ")";
        boolean emptyAllFields = true;
        if (StringUtils.hasText(login)) {
            emptyAllFields = false;
            filter = "&(" + ldapAttributes.uidAttribute + "=*" + login + "*)";
        }
        if (StringUtils.hasText(name)) {
            emptyAllFields = false;
            filter = "&(" + ldapAttributes.nomUsuelAttribute + "=*" + name + "*)";
        }
        if (StringUtils.hasText(firstName)) {
            emptyAllFields = false;
            filter += "(" + ldapAttributes.prenomAttribute + "=*" + firstName + "*)";
        }
        if (!emptyAllFields) {
            try {
                ldapManagers = ldapUserService.getLdapUsersFromFilter(filter);
            } catch (Throwable t) {
                ldapManagers = new ArrayList<LdapUser>();
                log.warn(t);
                addErrorMessage(null, "GESTIONNAIRE.ERROR.LDAP");
            }

        } else {
            addInfoMessage(null, "GESTIONNAIRE.INFO.SEARCH_LDAP.FIELD.EMPTY");
        }

        if (log.isDebugEnabled()) {
            log.debug("filter LDAP = " + filter);
        }

    }
	
	/*
	 ******************* ACCESSORS ******************** */

    /**
     * @return the ldapManagers
     */
    public List<Gestionnaire> getManagers() {
        List<Gestionnaire> managers = new ArrayList<Gestionnaire>();
        if (ldapManagers != null) {
            for (LdapUser l : ldapManagers) {
                Gestionnaire g = new Gestionnaire();
                g = getDomainService().setUserInfo(g, l);
                managers.add(g);
            }
        }
        return managers;
    }


    /**
     * @return the login
     */
    public String getLogin() {
        return login;
    }

    /**
     * @param login the login to set
     */
    public void setLogin(final String login) {
        this.login = login;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    /**
     * @param ldapAttributes the ldapAttributes to set
     */
    public void setLdapAttributes(final LdapAttributes ldapAttributes) {
        this.ldapAttributes = ldapAttributes;
    }

    /**
     * @return the firstName
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * @param firstName the firstName to set
     */
    public void setFirstName(final String firstName) {
        this.firstName = firstName;
    }


    /**
     * @param ldapManagers the ldapManagers to set
     */
    public void setLdapManagers(final List<LdapUser> ldapManagers) {
        this.ldapManagers = ldapManagers;
    }

    /**
     * @param ldapUserService the ldapUserService to set
     */
    public void setLdapUserService(final LdapUserService ldapUserService) {
        this.ldapUserService = ldapUserService;
    }


    /**
     * @return the ldapEnum
     */
    public WayfEnum getLdapEnum() {
        return ldapEnum;
    }


    /**
     * @param ldapEnum the ldapEnum to set
     */
    public void setLdapEnum(final WayfEnum ldapEnum) {
        this.ldapEnum = ldapEnum;
    }

}
