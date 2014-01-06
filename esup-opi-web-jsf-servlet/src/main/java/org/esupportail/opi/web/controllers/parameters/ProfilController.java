/**
 *
 */
package org.esupportail.opi.web.controllers.parameters;

import fj.F;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.BeanProfile;
import org.esupportail.opi.domain.beans.parameters.accessRight.*;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.BeanAccess;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.springframework.util.StringUtils;

import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import java.util.*;

import static fj.data.Array.array;
import static java.util.Arrays.asList;

/**
 * @author cleprous
 */
public class ProfilController extends AbstractContextAwareController {
   private static final long serialVersionUID = 594487224624313963L;

    /**
     * Default value to select all domains.
     */
    private static final int SELECT_ALL_DOMAIN = 0;

	/*
     ******************* PROPERTIES ******************* */

    /**
     * The profil.
     */
    private Profile profil;

    /**
     * the domain id selected.
     */
    private Integer idDomainSelected;

    private List<BeanProfile> beanProfiles = new ArrayList<>();

    /**
     * The access function for this profil.
     */
    private List<BeanAccess> accessFct;

    /**
     * The access Domain for this profil.
     */
    private List<BeanAccess> accessDomain;

    /**
     * The actionEnum.
     */
    private ActionEnum actionEnum;

    /**
     * see {@link AccessRightController}.
     */
    private AccessRightController accessRController;

    /**
     * A logger.
     */
    private final Logger log = new LoggerImpl(getClass());

	/*
	 ******************* INIT ************************* */


    /**
     * Constructors.
     */
    public ProfilController() {
        super();
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
     */
    @Override
    public void reset() {
        super.reset();
        profil = new Profile();
        accessFct = new ArrayList<>();
        accessDomain = new ArrayList<>();
        actionEnum = new ActionEnum();
        idDomainSelected = null;
    }

    /**
     * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
     */
    @Override
    public void afterPropertiesSetInternal() {
        super.afterPropertiesSetInternal();
        Assert.notNull(this.accessRController, "property accessRController of class "
                + this.getClass().getName() + " can not be null");
    }

	/*
	 ******************* CALLBACK ********************** */

    /**
     * Callback to add profil.
     *
     * @return String
     */
    public String goEnterProfil() {
        return NavigationRulesConst.ENTER_PROFIL;
    }

    /**
     * Callback to profil list.
     *
     * @return String
     */
    public String goSeeAllProfil() {
        reset();
        return NavigationRulesConst.MANAGED_PROFILS;
    }

    /**
     * Callback to see a profil.
     *
     * @return String
     */
    public String goSeeProfil() {
        return NavigationRulesConst.SEE_PROFIL;
    }

	/*
	 ******************* METHODS ********************** */


    /**
     * Add a profil to the dataBase.
     *
     * @return String
     */
    public String add() {
        if (log.isDebugEnabled()) {
            log.debug("enterind add with profil = " + profil);
        }
        if (ctrlEnter(profil)) {
            profil = getDomainService().add(profil, getCurrentGest().getLogin());
            getParameterService().addProfile(profil);

            //add the rights
            getAccessRController().add(accessDomain, accessFct, profil);
            beanProfiles.add(new BeanProfile(profil));

            reset();
            getAccessRController().reset();

            if (log.isDebugEnabled()) {
                log.debug("leaving add");
            }

            return NavigationRulesConst.MANAGED_PROFILS;
        }
        return null;

    }


    /**
     * Update a profil in the dataBase.
     *
     * @return String
     */
    public String update() {
        if (log.isDebugEnabled()) {
            log.debug("enterind update with profil = " + profil);
        }
        if (ctrlEnter(profil)) {
            profil = getDomainService().update(profil, getCurrentGest().getLogin());
            getParameterService().updateProfile(profil);

            //add the rights
            getAccessRController().update(accessDomain, accessFct, profil);
            beanProfiles = getParameterService().getProfiles(null);

            reset();

            if (log.isDebugEnabled()) {
                log.debug("leaving update");
            }
            return NavigationRulesConst.MANAGED_PROFILS;
        }

        return null;


    }

    /**
     * Delete a Profile to the dataBase.
     */
    public void delete() {
        if (log.isDebugEnabled()) {
            log.debug("enterind delete with profil = " + profil);
        }
        // réattachment à la session hibernate
        profil = getParameterService().getProfile(profil.getId(), profil.getCode());
        // effacement
        getParameterService().deleteProfile(profil);
        beanProfiles = getParameterService().getProfiles(null);
        reset();
        addInfoMessage(null, "INFO.DELETE.SUCCESS");
        if (log.isDebugEnabled()) {
            log.debug("leaving delete");
        }
    }


    /**
     * The selected domain.
     */
    public void selectDomain(final ValueChangeEvent event) {
        idDomainSelected = (Integer) event.getNewValue();
        selectDomain();
        FacesContext.getCurrentInstance().renderResponse();
    }

    /**
     * The selected domain.
     */
    public void selectDomain() {
        if (idDomainSelected != null) {
            if (idDomainSelected.equals(0)) {
                //get all access function
                Set<Fonction> fonctions = getParameterService().getFonctions(true, true);
                setAccessFct(makeAccess(new HashSet<Traitement>(fonctions)));
                ////get all access domain
                setAccessDomain(makeAccess(
                        new HashSet<Traitement>(getParameterService().getDomains(true, true))));
            } else {
                Domain d = getParameterService().getDomain(idDomainSelected);
                Set<Fonction> fonctions = d.getFonctions();
                setAccessFct(makeAccess(new HashSet<Traitement>(fonctions)));
                Set<Traitement> trait = new HashSet<>();
                trait.add(d);
                setAccessDomain(makeAccess(trait));
            }
        } else {
            setAccessFct(new ArrayList<BeanAccess>());
        }
    }

    /**
     * Build the list that contains all access for all functions.
     */
    private List<BeanAccess> makeAccess(final Set<Traitement> traitements) {
        List<BeanAccess> theAccess = new ArrayList<>();

        for (Traitement t : traitements) {
            //init proxy Hib
            getDomainService().initOneProxyHib(t, t.getAccessRight(), Set.class);
            BeanAccess beanAccess = new BeanAccess();
            beanAccess.setTraitement(t);
            Map<AccessType, Boolean> droits = new HashMap<>();
            Domain d = null;
            if (t instanceof Domain) d = (Domain) t;

            for (String codeAc : getAccessRController().getAccessTypes().keySet()) {
                AccessType ac = getAccessRController().getAccessTypes().get(codeAc);
                boolean hasTheDroit = false;
                for (AccessRight a : t.getAccessRight()) {
                    //s'il y a deja des droit en base on regarde le droit du profil
                    if (a.getProfile().getId().equals(getProfil().getId())
                            && a.getTraitement().getId().equals(t.getId())) {
                        if (a.getCodeAccessType() != null) {
                            if (a.getCodeAccessType().equals(ac.getCode())) {
                                hasTheDroit = true;
                                break;
                            }
                        }
                    }
                }
                if (d != null && ac.getCode().equals(AccessType.COD_READ)) {
                    //droit par defaut lecture
                    droits.put(ac, true);
                } else {
                    droits.put(ac, hasTheDroit);
                }
            }
            beanAccess.setTheDroits(droits);
            theAccess.add(beanAccess);
        }

        //trier
        Collections.sort(theAccess, new ComparatorString(BeanAccess.class));
        return theAccess;
    }

    /**
     * Control Profile attributes for the adding and updating.
     */
    private Boolean ctrlEnter(final Profile pro) {
        Boolean ctrlOk = true;
        if (!StringUtils.hasText(pro.getCode())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.CODE"));
            ctrlOk = false;
        } else {
            if (!getParameterService().profileCodeIsUnique(pro)) {
                ctrlOk = false;
                addErrorMessage(null, "ERROR.FIELD.EXIST",
                        getString("PROFIL"),
                        getString("FIELD_LABEL.CODE"));
            }
        }
        if (!StringUtils.hasText(pro.getLibelle())) {
            addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.LIBELLE"));
            ctrlOk = false;
        }

        if (log.isDebugEnabled()) {
            log.debug("leaving ctrlEnter return = " + ctrlOk);
        }
        return ctrlOk;
    }

    /**
     * Control if the functions of domain has rights. If not rights return false.
     */
    public Boolean ctrlDom(final BeanAccess beanAccessDom) {
        Domain dom = (Domain) beanAccessDom.getTraitement();
        //a true si au moins une fonction du domain a des droits
        boolean oneFunctionHaveRight = false;
        Set<Traitement> functions = new HashSet<>();
        functions.addAll(dom.getFonctions());
        List<BeanAccess> beanAccessFct = makeAccess(functions);
        for (BeanAccess aFct : beanAccessFct) {
            //on controle si elle a des droits
            for (AccessType aType : aFct.getTheDroits().keySet()) {
                if (aFct.getTheDroits().get(aType)) {
                    //on ne modifie pas le domain
                    oneFunctionHaveRight = true;
                    break;
                }
            }
        }
        return oneFunctionHaveRight;
    }

    public List<BeanAccess> getAllAccess() {
        Set<Traitement> allTraitement = new HashSet<Traitement>(getParameterService().getFonctions(true, true));
        Set<Traitement> tDom = new HashSet<Traitement>(getParameterService().getDomains(true, true));
        allTraitement.addAll(tDom);
        return makeAccess(allTraitement);
    }

    public List<BeanProfile> getBeanProfile() {
        if (beanProfiles.isEmpty())
            beanProfiles = getParameterService().getProfiles(null);
        return beanProfiles;
    }

    public List<BeanProfile> getBeanProfileInUse() {
        final List<BeanProfile> profiles = getParameterService().getProfiles(true);
        return getCurrentGest().getProfile().getSuperProfile() ?
                profiles :
                asList(array(profiles.toArray(new BeanProfile[profiles.size()]))
                        .filter(new F<BeanProfile, Boolean>() {
                            public Boolean f(BeanProfile b) {
                                return !b.getProfile().getSuperProfile();
                            }
                        })
                        .array(BeanProfile[].class));
    }

    public List<BeanAccess> getAccessFct() { return accessFct; }

    public void setAccessFct(final List<BeanAccess> access) {
        this.accessFct = access;
    }

    public Profile getProfil() {
        return profil;
    }

    public void setProfil(final Profile profil) {
        //Clone est utilise afin que l'utilisateur puisse modifier l'objet sans toucher au CACHE (par reference)
        //Probleme rencontre lors du modification annulee(par exemple), le cache etait tout de meme modifier
        this.profil = profil.clone();
    }

    public AccessRightController getAccessRController() {
        return accessRController;
    }

    public void setAccessRController(final AccessRightController accessRController) {
        this.accessRController = accessRController;
    }

    public int getSelectAllDomain() {
        return SELECT_ALL_DOMAIN;
    }

    public ActionEnum getActionEnum() {
        return actionEnum;
    }

    public void setActionEnum(final ActionEnum actionEnum) {
        this.actionEnum = actionEnum;
    }

    public List<BeanAccess> getAccessDomain() {
        return accessDomain;
    }

    public void setAccessDomain(final List<BeanAccess> accessDomain) {
        this.accessDomain = accessDomain;
    }

    public Integer getIdDomainSelected() {
        return idDomainSelected;
    }

    public void setIdDomainSelected(final Integer idDomainSelected) {
        this.idDomainSelected = idDomainSelected;
    }

}

