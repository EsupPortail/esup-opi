/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;



import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.exceptions.UserNotFoundException;
import org.esupportail.commons.services.application.Version;
import org.esupportail.commons.services.ldap.LdapUser;
import org.esupportail.commons.services.ldap.LdapUserService;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.services.urlGeneration.UrlGenerator;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.dao.DaoService;
import org.esupportail.opi.dao.IndividuDaoService;
import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.VersionManager;
import org.esupportail.opi.domain.beans.etat.EtatComplet;
import org.esupportail.opi.domain.beans.etat.EtatInComplet;
import org.esupportail.opi.domain.beans.parameters.AutoListPrincipale;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.PieceJustificative;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.VetAutoLp;
import org.esupportail.opi.domain.beans.parameters.accessRight.Profile;
import org.esupportail.opi.domain.beans.pilotage.ArchiveTask;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.references.rendezvous.Horaire;
import org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate;
import org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire;
import org.esupportail.opi.domain.beans.references.rendezvous.TrancheFermee;
import org.esupportail.opi.domain.beans.references.rendezvous.VetCalendar;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.User;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.MissingPiece;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursus;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.domain.beans.user.situation.IndSituation;
import org.esupportail.opi.utils.ldap.LdapAttributes;
import org.esupportail.opi.utils.primefaces.PFFilters;

import fj.P2;
import fj.data.Option;
import fj.data.Stream;


/**
 * The basic implementation of DomainService.
 * 
 * See /properties/domain/domain-example.xml
 */
public class DomainServiceImpl implements DomainService {

	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8200845058340254019L;

	/**
	 * {@link DaoService}.
	 */
	private DaoService daoService;

    private IndividuDaoService individuDaoSrv;

	/**
	 * The URL generator.
	 */
	private UrlGenerator urlGenerator;

	/**
	 * {@link LdapUserService}.
	 */
	private LdapUserService ldapUserService;

	/**
	 * This class contains all LDAP attributes used to OPI.
	 */
	private LdapAttributes ldapAttributes;
	
	private String codStudentRegex;
	
	private String codStudentPattern;
	
	private final Logger log = new LoggerImpl(getClass());

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(this.daoService, "property daoService of class "
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.ldapUserService,
				"property ldapUserService of class "
						+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.ldapAttributes, "property ldapAttributes of class "
				+ this.getClass().getName() + " can not be null");
	}

	// ////////////////////////////////////////////////////////////
	// OBJECT
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.domain.DomainService#initOneProxyHib(
	 * java.lang.Object, java.lang.Object, java.lang.Class)
	 */
	public void initOneProxyHib(final Object args, final Object proxy, final Class< ? > proxyClass) {
		if (log.isDebugEnabled()) {
			log.debug("entering initOneProxyHib ( " + args + ", le proxy , " + proxyClass + ")");
		}
		daoService.initOneProxyHib(args, proxy, proxyClass);

	}

	// ////////////////////////////////////////////////////////////
	// User
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.domain.DomainService#setUserInfo(
	 * org.esupportail.opi.domain.beans.user.Gestionnaire,
	 *      org.esupportail.commons.services.ldap.LdapUser)
	 */
	public Gestionnaire setUserInfo(final Gestionnaire gest,
			final LdapUser ldapUser) {
		Gestionnaire gestU = gest;

		List<String> ldapAttribute = ldapUser.getAttributes().get(
				ldapAttributes.displayNameAttribute);
		if (ldapAttribute != null) {
			gestU.setDisplayName(ldapAttribute.get(0));
		}

		ldapAttribute = ldapUser.getAttributes().get(
				ldapAttributes.prenomAttribute);
		if (ldapAttribute != null) {
			gestU.setPrenom(ldapAttribute.get(0));
		}

		ldapAttribute = ldapUser.getAttributes().get(
				ldapAttributes.nomUsuelAttribute);
		if (ldapAttribute != null) {
			gestU.setNomUsuel(ldapAttribute.get(0));
		}

		ldapAttribute = ldapUser.getAttributes().get(
				ldapAttributes.eduPersonPrincipalNameAttribute);
		if (ldapAttribute != null) {
			gestU.setLogin(ldapAttribute.get(0));
		}

		ldapAttribute = ldapUser.getAttributes().get(
				ldapAttributes.emailAttribute);
		if (ldapAttribute != null) {
			gestU.setAdressMail(ldapAttribute.get(0));
		}
		return gestU;

	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#getManager(java.lang.String)
	 */
	public Gestionnaire getManager(final String uid) {
		if (log.isDebugEnabled()) {
			log.debug("entering getManager( " + uid + " )");
		}
		Gestionnaire g = daoService.getManager(uid);
		if (g == null) {
			throw new UserNotFoundException("Aucun gestionnaire avec le login "
					+ uid + " n'existe.");
		}

		return g;
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#getUser(java.lang.String)
	 */
	public User getUser(final String currentUserId) {
		if (log.isDebugEnabled()) {
			log.debug("entering getUser(" + currentUserId + " )");
		}
		User u = null;
		u = daoService.getUser(currentUserId, Gestionnaire.class);
		
		if (u == null) {
			if (log.isDebugEnabled()) {
				log.debug("pas de gestionnaire pour l id = " + currentUserId);
			}
			//on chercher un individu
			u = daoService.getUser(currentUserId, Individu.class);
		}
		if (log.isDebugEnabled()) {
			log.debug("leaving getUser with user = " + u);
		}
		return u;
	}
	
	/**
	 * 
	 * @param date
	 * @return liste de gestionnaire
	 * @see org.esupportail.opi.domain.DomainService#getManagers
	 */
	@Deprecated 
		//( le 27/03/2009) 
	public List<Gestionnaire> getManagers(final Date date) {
		return this.daoService.getManagers(date);
	}
	
	/**
	 * @see org.esupportail.opi.domain.DomainService#getManagers()
	 */
	public List<Gestionnaire> getManagers() {
		return this.daoService.getManagers();
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#getManagersByCmi(
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	@Override
	public List<Gestionnaire> getManagersByCmi(final Commission commission) {
		if (log.isDebugEnabled()) {
			log.debug("entering getManagersByCmi");
		}
		return daoService.getManagersByCmi(commission);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#getIndividu(java.lang.String, java.util.Date)
	 */
	public Individu getIndividu(final String numDosOpi, final Date dateNaissance) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividu( " + numDosOpi + ", " + dateNaissance + " )");
		}
		return this.daoService.getIndividu(numDosOpi, dateNaissance);
	}
	
	/**
	 * @see org.esupportail.opi.domain.DomainService#getIndividuCodEtu(java.lang.String)
	 */
	@Override
	public Individu getIndividuCodEtu(final String codEtu) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividuCodEtu( " + codEtu + " )");
		}
		return this.daoService.getIndividuCodEtu(codEtu);
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#getIndividuINE(java.lang.String, java.lang.String)
	 */
	@Override
	public Individu getIndividuINE(final String codeNNE, final String cleNNE) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividuINE( " + codeNNE + ", " + cleNNE + " )");
		}
		return this.daoService.getIndividuINE(codeNNE, cleNNE);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#getIndividusWishes(
	 * java.lang.Boolean, java.lang.String)
	 */
	public List<Individu> getIndividusWishes(final Boolean withWishes, final String codeTypeTrt) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividus withWishes = " 
					+ withWishes + ", codeTypeTrt = " + codeTypeTrt);
		}

		return daoService.getIndividus(withWishes, codeTypeTrt);
	}
	
	
	/** 
	 * 
	 * @see org.esupportail.opi.domain.DomainService#getIndividusCommission
	 * (org.esupportail.opi.domain.beans.references.commission.Commission, java.lang.Boolean)
	 */
	@Override
	public Stream<Individu> getIndividusCommission(final Commission commission, final Boolean validate, final Set<Integer> listeCodesRI) {
		if (log.isDebugEnabled())
			log.debug("entering getIndividus( " + commission + ", " + validate +  " )");

		return individuDaoSrv.getIndividus(commission, validate, listeCodesRI);
	}

	/** 
	 * 
	 * @see org.esupportail.opi.domain.DomainService#getIndividusCommission
	 * (org.esupportail.opi.domain.beans.references.commission.Commission, java.lang.Boolean)
	 */
	@Override
	public List<Individu> getIndividusByCommission(final Commission commission, final Boolean validate, final Set<Integer> listeCodesRI) {
		if (log.isDebugEnabled())
			log.debug("entering getIndividusByCommission( " + commission + ", " + validate +  " )");
		return individuDaoSrv.getIndividusByCommission(commission, validate, listeCodesRI);
	}
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#getIndividusTrtCmiState(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi, java.lang.String)
	 */
	@Override
	public List<Individu> getIndividusTrtCmiState(final TraitementCmi trt, final String state) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividus trt = " 
					+ trt + ", state = " + state);
		}
		return daoService.getIndividus(trt, state);
	}
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#getIndividusTrtCmiState(
	 * org.esupportail.opi.domain.beans.references.commission.TraitementCmi, java.lang.String)
	 */
	@Override
	public List<Individu> getIndividusTrtCmi(final TraitementCmi trt) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividus trt = " 
					+ trt);
		}
		return daoService.getIndividus(trt);
	}
	
	@Override
	public P2<Long, Stream<Individu>> sliceOfInd(PFFilters pfFilters,
                                                 List<TypeDecision> typesDec,
                                                 Option<Boolean> validWish,
                                                 Option<Boolean> treatedWish,
                                                 Option<Date> wishCreation,
                                                 Option<String> codeTypeTrtmt,
                                                 Option<Set<TraitementCmi>> trtCmis,
                                                 Set<Integer> listCodesRI,
                                                 Option<List<String>> typesTrtVet) {
	    return individuDaoSrv.sliceOfInd(
                pfFilters, typesDec, treatedWish, validWish, wishCreation, codeTypeTrtmt, trtCmis, listCodesRI, typesTrtVet);
	}
	
	
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#getIndividuByMail(java.lang.String)
	 */
	public Individu getIndividuByMail(final String mail) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividu( " + mail + " )");
		}
		return this.daoService.getIndividu(mail);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#getAllIndividusEtu()
	 */
	public List<Individu> getAllIndividusEtu() {
		if (log.isDebugEnabled()) {
			log.debug("entering getAllIndividusEtu( )");
		}
		return this.daoService.getAllIndividusEtu();
		
	}
	
	public List<Individu> getIndividusByCampagne(final Campagne campagne, final Boolean temSve) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividusByCampagne( )");
		}
		return this.daoService.getIndividusByCampagne(campagne, temSve);
		
	}
	
	public List<Individu> getIndividuSearch(final String nomPatronymique, final String prenom,
			final Date dateNaissance, final String codPayNaissance, final String codDepPaysNaissance) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividuSearch( " + nomPatronymique 
					+ ", " + prenom + ", " + dateNaissance + ", " + codPayNaissance 
					+ ", " + codDepPaysNaissance + ")");
		}
		return this.daoService.getIndividuSearch(nomPatronymique, prenom,
				dateNaissance, codPayNaissance, codDepPaysNaissance);
		
	}
	

	/**
	 * @see org.esupportail.opi.domain.DomainService#canInsertIndividu(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
	public Boolean canInsertIndividu(final Individu individu) {
		final String newNomPatronymique = individu.getNomPatronymique().toUpperCase();
		final String newPrenom = individu.getPrenom().toUpperCase();
		List<Individu> indInBase = getIndividuSearch(
				newNomPatronymique, newPrenom, 
				individu.getDateNaissance(), individu.getCodPayNaissance(),
				individu.getCodDepPaysNaissance());
		if (!indInBase.isEmpty()) {
			for (Individu ind : indInBase) {
				if (ind.getVilleNaissance().equalsIgnoreCase(individu.getVilleNaissance())) {
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * @see org.esupportail.opi.domain.DomainService#updateUser(
	 * org.esupportail.opi.domain.beans.user.User)
	 */
	public void updateUser(final User user) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateUser( " + user + " )");
		}

		if (user instanceof Gestionnaire) {
			Gestionnaire g = (Gestionnaire) user;
			// on ne garde que la premiere lettre en majuscule
			
			final String newPrenom = StringUtils.capitalize(g.getPrenom().toLowerCase());
			final String newNom = StringUtils.capitalize(g.getNomUsuel().toLowerCase());
			final String disp = newPrenom + " " + newNom;
			g.setDisplayName(disp);
		}

		this.daoService.updateUser(user);
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#deleteUser(
	 * org.esupportail.opi.domain.beans.user.User)
	 */
	public void deleteUser(final User user) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteUser( " + user + " )");
		}
		this.daoService.deleteUser(user);
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#deleteUserList(java.util.List)
	 */
	public void deleteUserList(final List<User> users) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteUserList( )");
		}
		this.daoService.deleteUserList(users);
	}


	/** 
	 * @see org.esupportail.opi.domain.DomainService#updateStateIndividu(
	 * org.esupportail.opi.domain.beans.user.Individu,
	 *  org.esupportail.opi.domain.beans.user.Gestionnaire)
	 */
	public Individu updateStateIndividu(final Individu individu, final Gestionnaire manager) {
		//nouvelle ajout on test l'etat
		boolean doUpdate = false;
//		Individu  indi = getIndividu(
//				individu.getNumDossierOpi(), individu.getDateNaissance());
		Individu  indi = individu;
		// TODO : fix the following !!
		if (false) {
			if (!indi.getState().equals(EtatComplet.I18N_STATE_COMPLET)) {
				doUpdate = true;
				indi.setState(EtatComplet.I18N_STATE_COMPLET);
			}
		} else {
			if (!indi.getState().equals(EtatInComplet.I18N_STATE_INCOMPLET)) {
				doUpdate = true;
				indi.setState(EtatInComplet.I18N_STATE_INCOMPLET);
			}
		}
		//TODO: FIx this !!
//		if (doUpdate) {
//			IndividuPojo iPojo = new IndividuPojo();
//			iPojo.setIndividu(indi);
//			indi = (Individu) update(indi, 
//					Utilitaires.codUserThatIsAction(
//							manager, iPojo));
//			updateUser(indi);
//			
//		}
		return indi;
	}
	
	/**
	 * @see org.esupportail.opi.domain.DomainService#addUser(org.esupportail.opi.domain.beans.user.User)
	 */
	public void addUser(final User user) {
		if (log.isDebugEnabled()) {
			log.debug("entering addUser( " + user + " )");
		}
		if (user instanceof Gestionnaire) {
			Gestionnaire g = (Gestionnaire) user;
			// on ne garde que la premiere lettre en majuscule
			
			final String newPrenom = StringUtils.capitalize(g.getPrenom().toLowerCase());
			final String newNom = StringUtils.capitalize(g.getNomUsuel().toLowerCase());
			final String disp = newPrenom + " " + newNom;
			g.setDisplayName(disp);
		}

		this.daoService.addUser(user);
	}

	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#emailIsUnique(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
	public Boolean emailIsUnique(final Individu individu) {
		if (log.isDebugEnabled()) {
			log.debug("entering emailIsUnique( " + individu + " )");
		}

		Individu ind = daoService.getIndividu(individu.getAdressMail());
		if (ind != null
				&& !ind.getId().equals(individu.getId())) {
			return false;
		}
		return true;
	}
	
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#addIndCursus(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndCursus)
	 */
	public void addIndCursus(final IndCursus indCursus) {
		if (log.isDebugEnabled()) {
			log.debug("entering addIndCursus( " + indCursus + " )");
		}
		this.daoService.addIndCursus(indCursus);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#updateIndCursus(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndCursus)
	 */
	public void updateIndCursus(final IndCursus indCursus) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateIndCursus( " + indCursus + " )");
		}
		this.daoService.updateIndCursus(indCursus);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#deleteIndCursus(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndCursus)
	 */
	public void deleteIndCursus(final IndCursus indCursus) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteIndCursus( " + indCursus + " )");
		}
		this.daoService.deleteIndCursus(indCursus);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#addIndCursusScol(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol)
	 */
	public void addIndCursusScol(final IndCursusScol indCursusScol) {
		if (log.isDebugEnabled()) {
			log.debug("entering addIndCursusScol( " + indCursusScol + " )");
		}
		this.daoService.addIndCursusScol(indCursusScol);
		
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#updateIndCursusScol(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol)
	 */
	public void updateIndCursusScol(final IndCursusScol indCursusScol) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateIndCursusScol( " + indCursusScol + " )");
		}
		this.daoService.updateIndCursusScol(indCursusScol);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#deleteIndCursusScol(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol)
	 */
	public void deleteIndCursusScol(final IndCursusScol indCursusScol) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteIndCursusScol( " + indCursusScol + " )");
		}
		this.daoService.deleteIndCursusScol(indCursusScol);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#addIndBac(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndBac)
	 */
	public void addIndBac(final IndBac indBac) {
		if (log.isDebugEnabled()) {
			log.debug("entering addIndBac( " + indBac + " )");
		}
		this.daoService.addIndBac(indBac);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#updateIndBac(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndBac)
	 */
	public void updateIndBac(final IndBac indBac) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateIndBac( " + indBac + " )");
		}
		this.daoService.updateIndBac(indBac);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#deleteIndBac(
	 * 			org.esupportail.opi.domain.beans.user.indcursus.IndBac)
	 */
	public void deleteIndBac(final IndBac indBac) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteIndBac( " + indBac + " )");
		}
		this.daoService.deleteIndBac(indBac);		
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#addAdresse(
	 * 			org.esupportail.opi.domain.beans.user.Adresse)
	 */
	public void addAdresse(final Adresse adresse) {
		if (log.isDebugEnabled()) {
			log.debug("entering addAdresse( " + adresse + " )");
		}
		this.daoService.addAdresse(adresse);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#updateAdresse(
	 * 			org.esupportail.opi.domain.beans.user.Adresse)
	 */
	public void updateAdresse(final Adresse adresse) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateAdresse( " + adresse + " )");
		}
		this.daoService.updateAdresse(adresse);
	}

	
	/**
	 * @see org.esupportail.opi.domain.DomainService#addFirstAdmin(java.lang.String,
	 *      	org.esupportail.opi.domain.beans.parameters.accessRight.Profile)
	 */
	public void addFirstAdmin(final String firstAdministratorId, 
					final Profile admin) {
		if (log.isDebugEnabled()) {
			log.debug("entering addFirstAdmin( " + firstAdministratorId + " )");
		}

		LdapUser ldapUser = this.ldapUserService
				.getLdapUser(firstAdministratorId);
		Gestionnaire gest = new Gestionnaire();
		gest.setDateCreaEnr(new Date());
		gest.setDateDbtValidite(new Date());
		gest.setCodUserToCreate("INIT_DATA");
		gest = setUserInfo(gest, ldapUser);
		gest.setProfile(admin);

		daoService.addUser(gest);
		log.info("user '" + gest.getLogin()
				+ "' has been added to the database");

	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#gestionnaireLoginIsUnique(
	 * org.esupportail.opi.domain.beans.user.Gestionnaire)
	 */
	public Boolean gestionnaireLoginIsUnique(final Gestionnaire manager) {
		if (log.isDebugEnabled()) {
			log.debug("entering gestionnaireLoginIsUnique( " + manager + " )");
		}
		Gestionnaire g = daoService.getManager(manager.getLogin());
		if (g != null && !g.equals(manager)) {
			return false;
		} 
		
		return true;
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#hasGestionnaireRightsOnStudent(
	 * java.util.Set, java.util.Set)
	 */
	public Boolean hasGestionnaireRightsOnStudent(final Set<IndVoeu> lesVoeux,
                                                  final Set<Commission> lesCommissions) {
		if (log.isDebugEnabled()) {
			log.debug("entering hasGestionnaireRightsOnStudent( " + lesVoeux + " ---- "
			+ lesCommissions + " )");
		}
		if (lesVoeux == null ) { return false; }
		if (lesCommissions == null) { return false; }
		boolean result = false;
		for (IndVoeu indVoeu : lesVoeux) {
			for (Commission commission : lesCommissions) {
				for (TraitementCmi traitementCmi : commission.getTraitementCmi()) {
					if (traitementCmi.getVersionEtpOpi().equals(indVoeu.getLinkTrtCmiCamp()
							.getTraitementCmi().getVersionEtpOpi())) {
						return true;
					}
				}
			}
		}
		return result;
	}
	
	// ////////////////////////////////////////////////////////////
	// Voeu
	// ////////////////////////////////////////////////////////////

	
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#addIndVoeu(
	 * org.esupportail.opi.domain.beans.user.candidature.IndVoeu)
	 */
	public void addIndVoeu(final IndVoeu voeu) {
		if (log.isDebugEnabled()) {
			log.debug("entering addVoeu( " + voeu + " )");
		}
		daoService.addIndVoeu(voeu);
	}
	
	
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#deleteIndVoeu(
	 * org.esupportail.opi.domain.beans.user.candidature.IndVoeu)
	 */
	public void deleteIndVoeu(final IndVoeu voeu) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteIndVoeu( " + voeu + " )");
		}
		Individu i = (Individu) daoService.getUser(voeu.getIndividu().getId());
		i.getVoeux().remove(voeu);
		
		//daoService.deleteIndVoeu(voeu);
	}
	
	

	/** 
	 * @see org.esupportail.opi.domain.DomainService#updateIndVoeu(
	 * org.esupportail.opi.domain.beans.user.candidature.IndVoeu)
	 */
	public void updateIndVoeu(final IndVoeu voeu) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateIndVoeu( " + voeu + " )");
		}
		daoService.updateIndVoeu(voeu);
		
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#getVoeuxAcceptes()
	 */
	public List<IndVoeu> getVoeuxAcceptes() {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		return null;
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#updateIndVoeuTemEnServ(
	 * org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp, 
	 * boolean)
	 */
	
	public void updateIndVoeuTemEnServ(final LinkTrtCmiCamp link,
			final boolean temEnServ) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateIndVoeuTemEnServ( " + link + ", " + temEnServ + ")");
		}
		daoService.updateIndVoeuTemEnServ(link, temEnServ);
		
	}

	// ////////////////////////////////////////////////////////////
	// VersionManager
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.domain.DomainService#getDatabaseVersion()
	 */
	public Version getDatabaseVersion() throws ConfigException {
		VersionManager versionManager = daoService.getVersionManager();
		if (versionManager == null) {
			return null;
		}
		return new Version(versionManager.getVersion());
	}

	public void updateDatabaseVersion(final String version) {
		if (log.isDebugEnabled()) {
			log.debug("setting database version to '" + version + "'...");
		}
		VersionManager versionManager = daoService.getVersionManager();
		versionManager.setVersion(version);
		daoService.updateVersionManager(versionManager);
		if (log.isDebugEnabled()) {
			log.debug("database version set to '" + version + "'.");
		}
	}

	public void updateDatabaseVersion(final Version version) {
		updateDatabaseVersion(version.toString());
	}

	// ////////////////////////////////////////////////////////////
	// NormeSI
	// ////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.domain.DomainService#add(org.esupportail.opi.domain.beans.NormeSI,
	 *      java.lang.String)
	 */
	@Override
	public <T extends NormeSI> T add(final T norme, final String codeCurrentUser) {
		if (log.isDebugEnabled()) {
			log.debug("entering add with norme = " + norme
					+ "and codCurrentUSer  = " + codeCurrentUser);
		}
		T newNorme = norme;
		newNorme.setCodUserToCreate(codeCurrentUser);
		newNorme.setDateCreaEnr(new Date());

		return newNorme;
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#update(org.esupportail.opi.domain.beans.NormeSI,
	 *      java.lang.String)
	 */
	@Override
	public <T extends NormeSI> T update(final T norme, final String codeCurrentUser) {
		if (log.isDebugEnabled())
			log.debug("entering update with norme = " + norme
					+ "and codCurrentUSer  = " + codeCurrentUser);

		T newNorme = norme;
		newNorme.setCodUserToUpdate(codeCurrentUser);
		newNorme.setDateModifEnr(new Date());

		return newNorme;
	}

	// ////////////////////////////////////////////////////////////
	// Misc
	// ////////////////////////////////////////////////////////////

	/**
	 * @param daoService
	 *            the daoService to set
	 */
	public void setDaoService(final DaoService daoService) {
		this.daoService = daoService;
	}

    public void setIndividuDaoSrv(IndividuDaoService individuDaoSrv) {
        this.individuDaoSrv = individuDaoSrv;
    }

    /**
	 * @param ldapUserService
	 *            the ldapUserService to set
	 */
	public void setLdapUserService(final LdapUserService ldapUserService) {
		this.ldapUserService = ldapUserService;
	}

	/**
	 * @param ldapAttributes
	 *            the ldapAttributes to set
	 */
	public void setLdapAttributes(final LdapAttributes ldapAttributes) {
		this.ldapAttributes = ldapAttributes;
	}
	
	/**
	 * @param urlGenerator the urlGenerator to set
	 */
	public void setUrlGenerator(final UrlGenerator urlGenerator) {
		this.urlGenerator = urlGenerator;
	}
	
	/**
	 * @param codStudentRegex the codStudentRegex to set
	 */
	public void setCodStudentRegex(final String codStudentRegex) {
		this.codStudentRegex = codStudentRegex;
	}
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#getCodStudentRegex()
	 */
	public String getCodStudentRegex() {
		return codStudentRegex;
	}
	
	/**
	 * @return the codStudentPattern
	 */
	public String getCodStudentPattern() {
		return codStudentPattern;
	}
	
	/**
	 * @param codStudentPattern the codStudentPattern to set
	 */
	public void setCodStudentPattern(final String codStudentPattern) {
		this.codStudentPattern = codStudentPattern;
	}
	
	
	//////////////////////////////////////////////////////////////
	// Deep links
	//////////////////////////////////////////////////////////////

	/**
	 * @see org.esupportail.opi.domain.DomainService#getUrl(boolean, java.util.Map)
	 */
	public String getUrl(final boolean local, final Map<String, String> params) {
		if (local) {
			//Modif plus utilise dans esup-commons 1.3.3
			//return urlGenerator.authUrl(params);
			return urlGenerator.casUrl(params);
		}
		return urlGenerator.guestUrl(params);
	}
	

	/**
	 * @see org.esupportail.opi.domain.DomainService#getFormationUrl(boolean, String, String)
	 */
	public String getFormationUrl(final boolean local, final String etape, final String vet) {
		Map<String, String> params = new HashMap<String, String>();
		//TODO : Fix this !!
//		params.put(DeepLinkingRedirector.PAGE_PARAM, DeepLinkingRedirector.VOWS_PAGE_VALUE);
//		params.put(DeepLinkingRedirector.PARAM_CODE_ETAPE, etape);
//		params.put(DeepLinkingRedirector.PARAM_CODE_VERSION_ETAPE, vet);
		return getUrl(local, params);

	}

	//////////////////////////////////////////////////////////////
	// MissingPiece
	//////////////////////////////////////////////////////////////
	
	
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#getMissingPiece(
	 * org.esupportail.opi.domain.beans.user.Individu, 
	 * org.esupportail.opi.domain.beans.references.commission.Commission)
	 */
	public List<MissingPiece> getMissingPiece(final Individu individu, final Commission cmi) {
		if (log.isDebugEnabled()) {
			log.debug("entering getMissingPiece( " + individu + ", " + cmi + " )");
		}
		return daoService.getMissingPiece(individu, cmi);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#saveOrUpdateMissingPiece(
	 * java.util.List, java.lang.String)
	 */
	public void saveOrUpdateMissingPiece(final List<MissingPiece> listMP, final String loginGest) {
		if (log.isDebugEnabled()) {
			log.debug("entering saveOrUpdateMissingPiece( " + listMP + ", " + loginGest + " )");
		}
		for (MissingPiece mp : listMP) {
			if (mp.getId().equals(0)) {
				daoService.addMissingPiece((MissingPiece) add(mp, loginGest));
			} else {
				daoService.updateMissingPiece((MissingPiece) update(mp, loginGest));
			}
		}
	}

	
	/**
	 * @see org.esupportail.opi.domain.DomainService#deleteMissingPiece(
	 * java.util.List, org.esupportail.opi.domain.beans.parameters.PieceJustificative)
	 */
	public void deleteMissingPiece(final List<MissingPiece> missingPiece, final PieceJustificative piece) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteMissingPiece( " + missingPiece + ", " + piece + " )");
		}
		daoService.deleteMissingPiece(missingPiece, piece);
	}
	
	/**
	 * @see org.esupportail.opi.domain.DomainService#purgeMissingPieceCamp(
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	
	public void purgeMissingPieceCamp(final Campagne camp) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteMissingPiece( " + camp + " )");
		}
		daoService.purgeMissingPieceCamp(camp);
	}	

	//////////////////////////////////////////////////////////////
	// Opinion
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainService#getAvis(
	 * org.esupportail.opi.domain.beans.user.candidature.IndVoeu)
	 */
	public List<Avis> getAvis(final IndVoeu indVoeu) {
		if (log.isDebugEnabled()) {
			log.debug("entering getAvis( " + indVoeu + " )");
		}
		return daoService.getAvis(indVoeu);
	}

	public List<Avis> getAvisByEtp(final String codEtp, final Integer codVrsVet) {
		if (log.isDebugEnabled()) {
			log.debug("entering getAvis( " + codEtp + " , " + codVrsVet + " )");
		}
		return daoService.getAvisByEtp(codEtp, codVrsVet);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#addAvis(
	 * org.esupportail.opi.domain.beans.user.candidature.Avis)
	 */
	public void addAvis(final Avis avis) {
		if (log.isDebugEnabled()) {
			log.debug("entering addAvis( " + avis + " )");
		}
		daoService.addAvis(avis);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#updateAvis(
	 * org.esupportail.opi.domain.beans.user.candidature.Avis)
	 */
	public void updateAvis(final Avis avis) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateAvis( " + avis + " )");
		}
		daoService.updateAvis(avis);
	}

	//////////////////////////////////////////////////////////////
	// Nettoyage
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainService#deleteIndividusSansVoeux()
	 */
	public void deleteIndividusSansVoeux() {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		
		// On recupere tous les individus sans voeux
		List<Individu> toDel = daoService.getIndividus(false, null);
		
		// et on les supprime
		for (Individu individu : toDel) {
			log.info("Suppression de " + individu);
			daoService.deleteUser(individu);
		}
	}
	
	
	//////////////////////////////////////////////////////////////
	// RENDEZ VOUS
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainService#addIndividuDate(
	 * org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate)
	 */
	public void addIndividuDate(final IndividuDate indDate) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addIndividuDate(indDate);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#deleteIndividuDate(
	 * org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate)
	 */
	public void deleteIndividuDate(final IndividuDate indDate) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.deleteIndividuDate(indDate);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#updateIndividuDate(
	 * org.esupportail.opi.domain.beans.references.rendezvous.IndividuDate)
	 */
	public void updateIndividuDate(final IndividuDate indDate) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.updateIndividuDate(indDate);
	}
	
	public IndividuDate getIndividuDate(final IndVoeu indVoeu) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndividuDate indVoeu " + indVoeu);
		}
		return daoService.getIndividuDate(indVoeu);
	}
	
	/**
	 * @see org.esupportail.opi.domain.DomainService#
	 * deleteAllIndividuDate(java.util.Set)
	 */
	
	public void deleteAllIndividuDate(final Individu ind) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteAllIndividuDate ind " + ind);
		}
		daoService.deleteAllIndividuDate(ind);
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#purgeIndividuDateCamp(
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	
	public void purgeIndividuDateCamp(final Campagne camp) {
		if (log.isDebugEnabled()) {
			log.debug("entering purgeIndividuDateCamp ind " + camp);
		}
		daoService.purgeIndividuDateCamp(camp);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#addHoraire
	 * (org.esupportail.opi.domain.beans.references.rendezvous.Horaire)
	 */
	public void addHoraire(final Horaire ho) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addHoraire(ho);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#updateHoraire
	 * (org.esupportail.opi.domain.beans.references.rendezvous.Horaire)
	 */
	public void updateHoraire(final Horaire ho) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.updateHoraire(ho);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#deleteHoraire
	 * (org.esupportail.opi.domain.beans.references.rendezvous.Horaire)
	 */
	public void deleteHoraire(final Horaire ho) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.deleteHoraire(ho);
	}
	
	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#addTrancheFermee
	 * (org.esupportail.opi.domain.beans.references.rendezvous.TrancheFermee)
	 */
	public void addTrancheFermee(final TrancheFermee trFermee) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addTrancheFermee(trFermee);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#updateTrancheFermee
	 * (org.esupportail.opi.domain.beans.references.rendezvous.TrancheFermee)
	 */
	public void updateTrancheFermee(final TrancheFermee trFermee) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.updateTrancheFermee(trFermee);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#deleteTrancheFermee
	 * (org.esupportail.opi.domain.beans.references.rendezvous.TrancheFermee)
	 */
	public void deleteTrancheFermee(final TrancheFermee trFermee) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.deleteTrancheFermee(trFermee);
	}
	
	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#addVetCalendar
	 * (org.esupportail.opi.domain.beans.references.rendezvous.VetCalendar)
	 */
	public void addVetCalendar(final VetCalendar vetCalendar) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addVetCalendar(vetCalendar);
	}
	
	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#updateVetCalendar
	 * (org.esupportail.opi.domain.beans.references.rendezvous.VetCalendar)
	 */
	public void updateVetCalendar(final VetCalendar vetCalendar) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.updateVetCalendar(vetCalendar);
	}
	
	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#deleteVetCalendar
	 * (org.esupportail.opi.domain.beans.references.rendezvous.VetCalendar)
	 */
	public void deleteVetCalendar(final VetCalendar vetCalendar) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.deleteVetCalendar(vetCalendar);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#addJourHoraire
	 * (org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire)
	 */
	public void addJourHoraire(final JourHoraire jourHoraire) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addJourHoraire(jourHoraire);
	}
	
	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#updateJourHoraire
	 * (org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire)
	 */
	public void updateJourHoraire(final JourHoraire jourHoraire) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.updateJourHoraire(jourHoraire);
	}
	
	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#deleteJourHoraire
	 * (org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire)
	 */
	public void deleteJourHoraire(final JourHoraire jourHoraire) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.deleteJourHoraire(jourHoraire);
	}

	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#refreshJourHoraire
	 * (org.esupportail.opi.domain.beans.references.rendezvous.JourHoraire)
	 */
	public void refreshJourHoraire(final JourHoraire jourHoraire) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.refreshJourHoraire(jourHoraire);
	}
	
	/**
	 * 
	 * @see org.esupportail.opi.domain.DomainService#refreshCalendarRdv
	 * (org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV)
	 */
	public void refreshCalendarRdv(final CalendarRDV calendarRdv) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.refreshCalendarRdv(calendarRdv);
	}

	//////////////////////////////////////////////////////////////
	// ARCHIVE TASK
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainService#
	 * addArchiveTask(org.esupportail.opi.domain.beans.pilotage.ArchiveTask)
	 */
	@Override
	public void addArchiveTask(final ArchiveTask archiveTask) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addArchiveTask(archiveTask);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#getArchiveTasks()
	 */
	@Override
	public List<ArchiveTask> getArchiveTasks() {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		return daoService.getArchiveTasks();
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#getArchiveTasksToExecute()
	 */
	@Override
	public List<ArchiveTask> getArchiveTasksToExecute() {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		return daoService.getArchiveTasksToExecute();
	}
	
	/** 
	 * @see org.esupportail.opi.domain.DomainService#
	 * updateArchiveTask(org.esupportail.opi.domain.beans.pilotage.ArchiveTask)
	 */
	@Override
	public void updateArchiveTask(final ArchiveTask archiveTask) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.updateArchiveTask(archiveTask);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainService#
	 * deleteArchiveTask(org.esupportail.opi.domain.beans.pilotage.ArchiveTask)
	 */
	@Override
	public void deleteArchiveTask(final ArchiveTask archiveTask) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.deleteArchiveTask(archiveTask);
	}
	
	
	//////////////////////////////////////////////////////////////
	// AUTOMATION SUPPLEMENTARY LISTS
	//////////////////////////////////////////////////////////////

	/**
	 * @param autoLp
	 */
	public void addAutoListPrincipale(AutoListPrincipale autoLp) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addAutoListPrincipale(autoLp);
	}

	/**
	 * @param autoLp
	 */
	public void updateAutoListPrincipale(AutoListPrincipale autoLp) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.updateAutoListPrincipale(autoLp);
	}

	/**
	 * @param autoLp
	 */
	public void deleteAutoListPrincipale(AutoListPrincipale autoLp) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.deleteAutoListPrincipale(autoLp);
	}
	
	/**
	 * @param vet
	 */
	public void addVetAutoLp(VetAutoLp vet) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.addVetAutoLp(vet);
	}

	/**
	 * @param vet
	 */
	public void updateVetAutoLp(VetAutoLp vet) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.updateVetAutoLp(vet);
	}

	/**
	 * @param vet
	 */
	public void deleteVetAutoLp(VetAutoLp vet) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		daoService.deleteVetAutoLp(vet);
	}
	
	/**
	 * @see org.esupportail.opi.domain.DomainService#
	 * getRecupIndVoeuLc(org.esupportail.opi.domain.beans.parameters.AutoListPrincipale,
	 * 		org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi)
	 */
	public IndVoeu getRecupIndVoeuLc(final AutoListPrincipale autoLp,
			final VersionEtpOpi versionEtpOpi) {
		if (log.isDebugEnabled()) {
			log.debug("");
		}
		List<IndVoeu> listIndVoeu = null;
		for (TypeDecision typeDec : autoLp.getListTypeOfDecision()) {
			listIndVoeu = daoService.getRecupListIndVoeuLc(typeDec, versionEtpOpi);
			if (listIndVoeu != null && !listIndVoeu.isEmpty()) {
				break;
			}
		}
		if (listIndVoeu == null || listIndVoeu.isEmpty()) {
			return null;
		}
		
		IndVoeu indVoeu = null;
		Integer rang = null;
		for (IndVoeu voeu : listIndVoeu) {
			for (Avis avis : voeu.getAvis()) {
				if (avis.getValidation() && avis.getTemoinEnService()) {
					if ((indVoeu == null && rang == null) ||
							(indVoeu != null && avis.getRang() != null && rang == null) ||
							(indVoeu != null && avis.getRang() != null && rang != null && rang > avis.getRang())) {
						rang = avis.getRang();
						indVoeu = voeu;
					}
					break;
				}
			}
		}
		return indVoeu;
	}

	//////////////////////////////////////////////////////////////
	// IndSituation
	//////////////////////////////////////////////////////////////
	
	/**
	 * @see org.esupportail.opi.domain.DomainService#
	 * addIndSituation(org.esupportail.opi.domain.beans.user.situation.IndSituation)
	 */
	@Override
	public void addIndSituation(final IndSituation indSituation) {
		if (log.isDebugEnabled()) {
			log.debug("entering addIndSituation");
		}
		daoService.addIndSituation(indSituation);
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#
	 * deleteIndSituation(org.esupportail.opi.domain.beans.user.situation.IndSituation)
	 */
	@Override
	public void deleteIndSituation(final IndSituation indSituation) {
		if (log.isDebugEnabled()) {
			log.debug("entering deleteIndSituation");
		}
		daoService.deleteIndSituation(indSituation);
	}

	/**
	 * @see org.esupportail.opi.domain.DomainService#
	 * updateIndSituation(org.esupportail.opi.domain.beans.user.situation.IndSituation)
	 */
	@Override
	public void updateIndSituation(final IndSituation indSituation) {
		if (log.isDebugEnabled()) {
			log.debug("entering updateIndSituation");
		}
		daoService.updateIndSituation(indSituation);
	}

	@Override
	public IndSituation getIndSituation(final Individu ind) {
		if (log.isDebugEnabled()) {
			log.debug("entering getIndSituation");
		}
		return daoService.getIndSituation(ind);
	}
}
