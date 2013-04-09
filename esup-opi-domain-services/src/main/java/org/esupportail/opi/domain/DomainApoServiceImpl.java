/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import fj.Equal;
import fj.F;
import fj.F2;
import fj.data.Stream;
import org.esupportail.commons.exceptions.ObjectNotFoundException;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.formation.GrpTypDipCorresp;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.AdresseFix;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.domain.beans.user.indcursus.CursusExt;
import org.esupportail.opi.domain.beans.user.indcursus.CursusR1;
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.services.remote.client.IApogee;
import org.esupportail.opi.utils.CacheModelConst;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.utils.Conversions;
import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;
import org.esupportail.wssi.services.remote.AnneeUniDTO;
import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.CritereVdiDTO;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.DipAutCur;
import org.esupportail.wssi.services.remote.Diplome;
import org.esupportail.wssi.services.remote.Etablissement;
import org.esupportail.wssi.services.remote.Etape;
import org.esupportail.wssi.services.remote.IndOpiDTO;
import org.esupportail.wssi.services.remote.InsertLaisserPasser;
import org.esupportail.wssi.services.remote.MentionNivBac;
import org.esupportail.wssi.services.remote.MethodToLookForVdi;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.ReadEnseignement;
import org.esupportail.wssi.services.remote.ReadEtudiant;
import org.esupportail.wssi.services.remote.ReadReferentiel;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.esupportail.wssi.services.remote.TelemLaisserPasserDTO;
import org.esupportail.wssi.services.remote.TelemLaisserPasserOpiDTO;
import org.esupportail.wssi.services.remote.TypDiplome;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;

import pedagogiquemetier_28022011_impl.servicesmetiers.commun.apogee.education.gouv.PedagogiqueMetierServiceInterface;
import administratifmetier_17062009_impl.servicesmetiers.commun.apogee.education.gouv.AdministratifMetierServiceInterface;

import com.googlecode.ehcache.annotations.Cacheable;

import etudiantwebserviceimpl.impl.webservices.commun.apogee.education.gouv.EtudiantMetierServiceInterface;
import geographiemetier_06062007_impl.servicesmetiers.commun.apogee.education.gouv.GeographieMetierServiceInterface;
import geographiemetier_06062007_impl.servicesmetiers.commun.apogee.education.gouv.WebBaseException;
import gouv.education.apogee.commun.transverse.dto.administratif.cursusexternedto.CursusExterneDTO;
import gouv.education.apogee.commun.transverse.dto.administratif.cursusexternesettransfertsdto.CursusExternesEtTransfertsDTO;
import gouv.education.apogee.commun.transverse.dto.administratif.insadmetpdto.InsAdmEtpDTO;
import gouv.education.apogee.commun.transverse.dto.etudiant.adressedto.AdresseDTO;
import gouv.education.apogee.commun.transverse.dto.etudiant.coordonneesdto.CoordonneesDTO;
import gouv.education.apogee.commun.transverse.dto.etudiant.identifiantsetudiantdto.IdentifiantsEtudiantDTO;
import gouv.education.apogee.commun.transverse.dto.etudiant.indbacdto.IndBacDTO;
import gouv.education.apogee.commun.transverse.dto.etudiant.infoadmetudto.InfoAdmEtuDTO;
import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;
import gouv.education.apogee.commun.transverse.dto.pedagogique.contratpedagogiqueresultatvdivetdto.ContratPedagogiqueResultatVdiVetDTO;
import gouv.education.apogee.commun.transverse.dto.pedagogique.etaperesvdivetdto.EtapeResVdiVetDTO;
import gouv.education.apogee.commun.transverse.dto.pedagogique.resultatvetdto.ResultatVetDTO;

import static fj.data.Option.fromNull;
import static fj.data.Stream.iterableStream;

/**
 * The basic implementation of DomainApoService.
 * 
 * See /properties/domain/domain.xml
 */
@SuppressWarnings("serial")
public class DomainApoServiceImpl implements DomainApoService {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3786835418133473373L;


	/**
	 * True value for character witness.
	 */
	private static final String TRUE = "O";

	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());


	/**
	 * Can read the reference Apogee.
	 */
	private ReadReferentiel remoteCriApogeeRef;

	/**
	 * Can read the education Apogee.
	 */
	private ReadEnseignement remoteCriApogeeEns;

	/**
	 * Apogee interface
	 */
	private IApogee remoteApo;
	
	/**
	 * Can read the table of AdministratifMetier in Apogee.
	 */
	private AdministratifMetierServiceInterface remoteApoRenAdminMetier;
	
	/**
	 * Can read the table of EtudiantMetier in Apogee.
	 */
	private EtudiantMetierServiceInterface remoteApoRenEtuMetier;

	/**
	 * Can read the table of PedagoqiqueMetier in Apogee.
	 */
	private PedagogiqueMetierServiceInterface remoteApoRenPedaMetier;
	
	/**
	 * Can read the table of GeographiqueMetier in Apogee.
	 */
	private GeographieMetierServiceInterface remoteApoRenGeoMetier;
	
	/**
	 * Insert the laisserPassser in Apogee.
	 */
	private InsertLaisserPasser insertLaisserPasser; 

	/**
	 * Can read the table of etudiant in Apogee.
	 */
	private ReadEtudiant remoteCriApogeeEtudiant;
	
	/**
	 * see {@link ParameterService}.
	 */
	private ParameterService parameterService;

	/**
	 * The intern school code.
	 */
	private String codEtbInt;

	/**
	 * true if the configuration specify that only terminals diplomas have to be returned.
	 */
	private boolean onlyTerminaux;

	/**
	 * true if the configuration specify that obtentionDip can be used.
	 */
	private boolean obtentionDip;
	
	/**
	 * true if the configuration specify than cusrsusExt can be used.
	 */
	private boolean isRecupCursusExt;
	/**
	 * true if the configuration specify than apogee's TEM_ACC_ANNUAIRE is set to true. 
	 */
	private String temoinRecupAnnu;
	/**
	 * 
	 * the intern code of the diploma for foreigns students.
	 */
	private String codDacEtr;
	
	
	/**
	 * Bean constructor.
	 */
	public DomainApoServiceImpl() {
		super();
	}

	/**
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(this.remoteCriApogeeRef, 
				"property remoteCriApogeeRef of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.remoteCriApogeeEns, 
				"property remoteCriApogeeEns of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.parameterService, 
				"property parameterService of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.insertLaisserPasser, 
					"property insertLaisserPasser of class " 
					+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.remoteCriApogeeEtudiant, 
				"property remoteCriApogeeEtudiant of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.remoteApoRenAdminMetier, 
				"property remoteApoRenAdminMetier of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.remoteApoRenPedaMetier, 
				"property remoteApoRenPedaMetier of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.remoteApoRenEtuMetier, 
				"property remoteApoRenEtuMetier of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.remoteApoRenGeoMetier, 
				"property remoteApoRenGeoMetier of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.obtentionDip, 
				"property obtentionDip of class " 
				+ this.getClass().getName() + " can not be null");

	}



	//////////////////////////////////////////////////////////////
	// CentreGestion
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getCentreGestion()
	 */
	@Cacheable(cacheName = CacheModelConst.ENS_APOGEE_MODEL)
	public List<CentreGestion> getCentreGestion() {
		if (log.isDebugEnabled()) {
			log.debug("entering getCentreGestion()");
		}
		try {
			List<CentreGestion> l = remoteCriApogeeRef.getCentreGestions(TRUE);
			Collections.sort(l, new Comparator<CentreGestion>(){
				@Override
				public int compare(CentreGestion c1, CentreGestion c2) {
					return c1.getLibCge().compareToIgnoreCase(c2.getLibCge());
				}
			});
			return l;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}

	}


	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getCentreGestion(java.lang.String)
	 */
	public CentreGestion getCentreGestion(final String codCge) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCentreGestion( " + codCge + " )");
		}
		try {
			return remoteCriApogeeRef.getCentreGestion(codCge);
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	//////////////////////////////////////////////////////////////
	// Etape
	//////////////////////////////////////////////////////////////

	@Deprecated
	public List<Etape> getEtapes(final String codCge) {
		if (log.isDebugEnabled()) {
			log.debug("entering getEtapes( " + codCge + " )");
		}
		try {
			List<Etape> l = remoteCriApogeeEns.getEtapes(codCge);
			//Collections.sort(l, new ComparatorString(Etape.class));
			return l;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getEtape(java.lang.String)
	 */
	/**
	 * TODO : ÃÂ  supprimer (11/01/2012)
	 */
//	public Etape getEtape(final String codeEtp) {
//		if (log.isDebugEnabled()) {
//			log.debug("entering getEtape( " + codeEtp + " )");
//		}
//		try {
//			return remoteCriApogeeEns.getEtape(codeEtp);
//		} catch (Exception e) {
//			throw new CommunicationApogeeException(e);
//		}
//	}



	//////////////////////////////////////////////////////////////
	// Version Etape
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getVersionEtapes(
	 * java.lang.String, java.lang.String, java.lang.String, java.lang.String)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.ENS_APOGEE_MODEL)
	public List<VersionEtapeDTO> getVersionEtapes(final String codEtp, final String libWebVet,
			final String codCge, final String codAnu) {
		if (log.isDebugEnabled()) {
			log.debug("getVersionEtapes( " + codEtp + ", " + libWebVet + ", " + codCge + " )");
		}
		try {
			return remoteCriApogeeEns.getVersionEtapes1(
					codEtp, libWebVet, codCge, codAnu);
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getVersionEtapes(
	 * org.esupportail.wssi.services.remote.VersionDiplomeDTO, java.lang.String)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.ENS_APOGEE_MODEL)
	public List<VersionEtapeDTO> getVersionEtapes(final VersionDiplomeDTO vrsDip, final String codAnu) {
		if (log.isDebugEnabled()) {
			log.debug("getVersionEtapes(" + vrsDip + "  )");
		}
		List<VersionEtapeDTO> list = null;
		try {

			list = remoteCriApogeeEns.getVersionEtapes3(
					vrsDip, onlyTerminaux, true, codAnu);

		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}

		return list;

	}


	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getVersionEtape(java.lang.String, java.lang.Integer)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.ENS_APOGEE_MODEL)
	public VersionEtapeDTO getVersionEtape(final String codEtp, final Integer codVrsVet) {
		if (log.isDebugEnabled()) {
			log.debug("entering getVersionEtape(" + codEtp + ", " 
					+ codVrsVet + "  )");
		}
		try {
			return remoteCriApogeeEns.getVersionEtape(codEtp, codVrsVet);
		} catch (Exception e) {
		    log.warn(e);
		    return new VersionEtapeDTO().withCodEtp(codEtp)
		        .withCodVrsVet(codVrsVet)
		        .withLibCmtVet("DOES NOT EXIST")
		        .withLibWebVet("DOES NOT EXIST")
		        .withLicEtp("DOES NOT EXIST");
			//throw new CommunicationApogeeException(e);
		}
	}

	//////////////////////////////////////////////////////////////
	// Pays
	//////////////////////////////////////////////////////////////
	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getPays()
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<Pays> getPays() {
		if (log.isDebugEnabled()) {
			log.debug("entering getPays()");
		}
		try {
			return remoteCriApogeeRef.getPays(TRUE);
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	//////////////////////////////////////////////////////////////
	// Pays
	//////////////////////////////////////////////////////////////
	
	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getPays()
	 * getPays(java.lang.String)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public Pays getPays(final String codePays) {
		if (log.isDebugEnabled()) {
			log.debug("entering getPays( " + codePays + " )");
		}
		if (StringUtils.hasText(codePays)) {
			List<Pays> pays = getPays();
			for (Pays p : pays) {
				if (codePays.equals(p.getCodPay())) {
					return p;
				}
			}
		}
		return null;
	}

	//////////////////////////////////////////////////////////////
	// Departements
	//////////////////////////////////////////////////////////////
	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDepartements()
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<Departement> getDepartements() {
		if (log.isDebugEnabled()) {
			log.debug("entering getDepartements()");
		}
		try {
			List<Departement> d = remoteCriApogeeRef.getDepartements(TRUE);
			Collections.sort(d, new Comparator<Departement>() {
			    public int compare(Departement d1, Departement d2) {
			        return d1.getCodDep().compareTo(d2.getCodDep());
			    }
			});
			return d;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	//////////////////////////////////////////////////////////////
	// Departement
	//////////////////////////////////////////////////////////////	
	/** 
	 * @see org.esupportail.opi.domain.BusinessCacheService#
	 * getDepartement(java.lang.String)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public Departement getDepartement(final String codeDep) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDepartement( " + codeDep + " )");
		}
		if (StringUtils.hasText(codeDep)) {
			List<Departement> departements = getDepartements();
			for (Departement d : departements) {
				if (codeDep.equals(d.getCodDep())) {
					return d;
				}
			}
		}
		return null;
	}
	//////////////////////////////////////////////////////////////
	// Communes
	//////////////////////////////////////////////////////////////
	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getCommunesDTO(java.lang.String)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<CommuneDTO> getCommunesDTO(final String codBdi) {
		log.info("entering getCommunes( " + codBdi + " ) request to webservices AMUE");
		try {
			List<CommuneDTO> c = new ArrayList<CommuneDTO>();

			String temoinEnService = TRUE;
			List<CommuneDTO> commune = remoteApoRenGeoMetier.recupererCommune(
					codBdi, temoinEnService, temoinEnService);
			for (CommuneDTO communeDTO : commune) {
				c.add(communeDTO);
			}
			Collections.sort(c, new Comparator<CommuneDTO>() {
				@Override
				public int compare(final CommuneDTO c1, final CommuneDTO c2) {
					return c1.getLibCommune().compareToIgnoreCase(c2.getLibCommune());
				}
			});
			return c;
		} catch (WebBaseException e) {
			//technical.data.nullretrieve.commune
			throw new ObjectNotFoundException("Ce code postal ( " + codBdi + " ) n'existe pas dans la base de donnÃÂ©es APOGEE");
		} catch (NullPointerException e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getCommunes(
	 * java.lang.String, java.lang.Boolean, java.lang.Boolean)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<org.esupportail.wssi.services.remote.CommuneDTO> 
	getCommunes(final String codDep, 
			final Boolean onlyLycee, final Boolean withEtab) {
		try {
			List<org.esupportail.wssi.services.remote.CommuneDTO> c = 
				new ArrayList<org.esupportail.wssi.services.remote.CommuneDTO>();
			if (onlyLycee != null && onlyLycee) {
				c = remoteCriApogeeRef.getCommunes(TRUE, codDep, "LY", null);
			} else if (withEtab != null && withEtab) {
				c = remoteCriApogeeRef.getCommunes(TRUE, codDep, null, withEtab);
			} else {
				c = remoteCriApogeeRef.getCommunes(TRUE, codDep, null, null);
			}
			Collections.sort(c, new Comparator<org.esupportail.wssi.services.remote.CommuneDTO>() {
				@Override
				public int compare(final org.esupportail.wssi.services.remote.CommuneDTO c1, 
									final org.esupportail.wssi.services.remote.CommuneDTO c2) {
					return c1.getLibCom().compareToIgnoreCase(c2.getLibCom());
				}
			});			
			return c;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getCommune(java.lang.String)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public org.esupportail.wssi.services.remote.CommuneDTO getCommune(final String codCom) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCommune( " + codCom + " )");
		}
		if (StringUtils.hasText(codCom)) {
			try {
			    return remoteCriApogeeRef.getCommune(codCom);
			} catch (Exception e) {
				throw new CommunicationApogeeException(e);
			}
		}
		return null;
	}


	//////////////////////////////////////////////////////////////
	// MentionNivBac
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getMentionNivBacs()
	 */
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<MentionNivBac> getMentionNivBacs() {
		if (log.isDebugEnabled()) {
			log.debug("entering getMentionNivBacs()");
		}
		try {
			return remoteCriApogeeRef.getMentionNivBacs(TRUE);
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getMentionNivBac(java.lang.String)
	 */
	public MentionNivBac getMentionNivBac(final String codMnb) {
		if (log.isDebugEnabled()) {
			log.debug("entering getMentionNivBac( " + codMnb + " )");
		}
		if (StringUtils.hasText(codMnb)) {
			List<MentionNivBac> mnbs = getMentionNivBacs();
			for (MentionNivBac mnb : mnbs) {
				if (codMnb.equals(mnb.getCodMnb())) {
					return mnb;
				}
			}
		}
		return null;
	}


	//////////////////////////////////////////////////////////////
	// Etablissement
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getLycees(java.lang.String, java.lang.String)
	 */
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<Etablissement> getLycees(final String codCom, final String codDep) {
		if (log.isDebugEnabled()) {
			log.debug("entering getLycees(" + codCom + ", " + codDep + " )");
		}
		try {
			return remoteCriApogeeRef.getEtablissements(TRUE, codCom, "LY", codDep);
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getEtablissements(java.lang.String, java.lang.String)
	 */
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<Etablissement> getEtablissements(final String codCom, final String codDep) {
		if (log.isDebugEnabled()) {
			log.debug("entering getEtablissements(" + codCom + ", " + codDep + " )");
		}
		try {
			List<Etablissement> etbs = remoteCriApogeeRef.getEtablissements(TRUE, codCom, null, codDep);
			Collections.sort(etbs, new Comparator<Etablissement>() {
				@Override
				public int compare(Etablissement e1, Etablissement e2) {
					return e1.getLibOffEtb().compareToIgnoreCase(e2.getLibOffEtb());
				}
			});
			return etbs;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getEtablissement(java.lang.String)
	 */
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public Etablissement getEtablissement(final String codEtb) {
		if (log.isDebugEnabled()) {
			log.debug("entering getEtablissements(" + codEtb + " )");
		}
		try {
			if (StringUtils.hasText(codEtb)) {
				return remoteCriApogeeRef.getEtablissement(codEtb);
			}
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
		return null;
	}


	//////////////////////////////////////////////////////////////
	//BacOuxEqu
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getBacOuxEqus(java.lang.String)
	 */
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<BacOuxEqu> getBacOuxEqus(final String daaObt) {
		if (log.isDebugEnabled()) {
			log.debug("getBacOuxEqus( " + daaObt + " )");
		}
		try {
			return remoteCriApogeeRef.getBacOuxEqus(TRUE, daaObt);
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getBacOuxEqu(java.lang.String, java.lang.String)
	 */
	public BacOuxEqu getBacOuxEqu(final String daaObt, final String codBac) {
		if (log.isDebugEnabled()) {
			log.debug("getBacOuxEqu( " + daaObt + ", " + codBac + " )");
		}
		if (StringUtils.hasText(codBac)) {
			List<BacOuxEqu> bacs = getBacOuxEqus(daaObt);
			for (BacOuxEqu bac : bacs) {
				if (codBac.equals(bac.getCodBac())) {
					return bac;
				}
			}
		}
		return null;
	}


	//////////////////////////////////////////////////////////////
	// Diplome
	//////////////////////////////////////////////////////////////
	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDiplomes(java.lang.String)
	 * @deprecated
	 */
	@Deprecated
//	@Cacheable(modelId = CacheModelConst.GEO_APOGEE_MODEL)
	public List<Diplome> getDiplomes(final String codSds) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDiplomes( " + codSds + " )");
		}
		try {
			List<Diplome> d = remoteCriApogeeEns.getDiplomes(codSds, null);
			//TODO : fix that !
			//Collections.sort(d, new ComparatorString(Diplome.class));
			return d;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/**
	 * @see org.esupportail.opi.domain.DomainApoService#getAllDiplomes()
	 * @return list of the diplome
	 */
	public List<Diplome> getAllDiplomes() {
		return remoteCriApogeeEns.getDiplomes(null, null);
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDiplome(java.lang.String, java.lang.String)
	 * @deprecated
	 */
	@Deprecated
	public Diplome getDiplome(final String codDip, final String codSds) {
		if (codDip != null) {
			for (Diplome d : getDiplomes(codSds)) {
				if (codDip.equals(d.getCodDip())) {
					return d;
				}
			}
		}
		return null;
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDipAutCurs()
	 */
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public List<DipAutCur> getDipAutCurs() {
		if (log.isDebugEnabled()) {
			log.debug("entering getDipAutCurs()");
		}
		try {
			List<DipAutCur> d = remoteCriApogeeEns.getDipAutCurs(TRUE);
			//TODO : fix that !
			Collections.sort(d, new Comparator<DipAutCur>() {
				@Override
				public int compare(final DipAutCur d1, final DipAutCur d2) {
					return d1.getLibDac().compareToIgnoreCase(d2.getLibDac());
				}
				
			});
			return d;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDipAutCur(java.lang.String)
	 */
	public DipAutCur getDipAutCur(final String codDac) {
		if (codDac != null) {
			for (DipAutCur d : getDipAutCurs()) {
				if (codDac.equals(d.getCodDac())) {
					return d;
				}
			}
		}
		return null;
	}

	/**
	 * 
	 * @return list of the typDiplome
	 */
	public List<TypDiplome> getAllTypeDiplomes() {
		if (log.isDebugEnabled()) {
			log.debug("entering getAllTypeDiplomes()");
		}
		try {	
			return remoteCriApogeeEns.getTypDiplomes("O", null);
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	//////////////////////////////////////////////////////////////
	// AnneeUni
	//////////////////////////////////////////////////////////////
	
	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getAnneeUni(
	 * java.lang.String)
	 */
	@Override
	public AnneeUniDTO getAnneeUni(final String codAnu) {
		if (log.isDebugEnabled()) {
			log.debug("getAnneeUni(" + codAnu + " )");
		}
		return remoteCriApogeeEns.anneeUniDTO1(codAnu);
	}

	//////////////////////////////////////////////////////////////
	//VersionDiplome
	//////////////////////////////////////////////////////////////


	/**
	 * @see org.esupportail.opi.domain.DomainApoService#getVersionDiplomes(
	 * java.lang.String, org.esupportail.opi.domain.beans.formation.GrpTypDip, java.lang.String)
	 */
	@Override
	public List<VersionDiplomeDTO> getVersionDiplomes(final String codeKeyWord, final GrpTypDip grpTpd,
			final String codAnu) {
		if (log.isDebugEnabled()) {
			log.debug("getVersionDiplomes(" + codeKeyWord + ", " + grpTpd + " )");
		}
		final Set<String> codDiplome = new HashSet<String>();
		codDiplome.addAll(remoteApo.getCodesDiplomes(codeKeyWord));

		final Set<String> listCodTpdEtb = new HashSet<String>();

		for (GrpTypDipCorresp corresp : grpTpd.getGrpTypDipCorresps()) {
			listCodTpdEtb.add(corresp.getCodTpdEtb());
		}
		
		CritereVdiDTO criteria = new CritereVdiDTO() {{
		    getCodeDiplome().addAll(codDiplome);
		    dateRct = codAnu;
		    getCodTpdEtb().addAll(listCodTpdEtb);
		    getMethodToExecute().add(MethodToLookForVdi.VDI_TO_LIST_COD_DIP);		    
		}};
		
		List<VersionDiplomeDTO> list = 
			remoteCriApogeeEns.getVersionDiplomes(criteria); 
		return list;
	}
	
	/**
	 * @see org.esupportail.opi.domain.DomainApoService#getVersionDiplomes(
	 * java.lang.String, java.lang.String)
	 */
	public List<VersionDiplomeDTO> getVersionDiplomes(final String codeKeyWord, final String annee) {
		if (log.isDebugEnabled()) {
			log.debug("getVersionDiplomes(" + codeKeyWord + ", " + annee + " )");
		}
		final Set<String> codDiplome = new HashSet<String>();
		codDiplome.addAll(remoteApo.getCodesDiplomes(codeKeyWord));
		
		CritereVdiDTO criteria = new CritereVdiDTO() {{
		    getCodeDiplome().addAll(codDiplome);
		    dateRct = annee;
		    getMethodToExecute().add(MethodToLookForVdi.VDI_TO_LIST_COD_DIP);
		}};
		//criteria.buildCritVdiToListVdi(codDiplome, annee, null);
		
		return remoteCriApogeeEns.getVersionDiplomes(criteria); 
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getVersionDiplomes(
	 * org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi,
	 * org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	@Override
	public VersionDiplomeDTO getVersionDiplomes(final VersionEtpOpi vetOpi,
	    final Campagne campagne) {
		if (log.isDebugEnabled()) {
			log.debug("getVersionDiplomes(" + vetOpi + " )");
		}
		try {
			return remoteCriApogeeEns.getVersionDiplome2(
					campagne.getCodAnu(),	vetOpi.getCodEtp(),
					vetOpi.getCodVrsVet());
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	//////////////////////////////////////////////////////////////
	// EtudiantMetier Apogee
	//////////////////////////////////////////////////////////////
	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getIndividuFromApogee(
	 * java.lang.String, java.lang.String, java.lang.String, 
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public Individu getIndividuFromApogee(
			final String codeNne, final String clefNne, final String codEtu,
			final String nom, final String prenom, final String dateNaiss) {
		log.info("entering getIndividuFromApogee( " 
				+ codeNne + ", " + clefNne + ", " + codEtu + " )");
		try {
			Individu individu = null;

			// Recherche l'etudiant dans Apogee
			IdentifiantsEtudiantDTO etudiant = remoteApoRenEtuMetier.recupererIdentifiantsEtudiant(
					codEtu, null, codeNne, clefNne, null, null, 
					nom, prenom, dateNaiss, temoinRecupAnnu);			
			individu = new Individu(etudiant.getNumeroINE(), etudiant.getCleINE(),
					etudiant.getCodEtu().toString());

			// Recuperation des infos de l'etudiant dans Apogee			
			InfoAdmEtuDTO infosAdmEtu = remoteApoRenEtuMetier.recupererInfosAdmEtu(
					etudiant.getCodEtu().toString());
			individu.setCodInd(etudiant.getCodInd());
			individu.setNomPatronymique(infosAdmEtu.getNomPatronymique());
			individu.setNomUsuel(infosAdmEtu.getNomUsuel());
			individu.setPrenom(infosAdmEtu.getPrenom1());
			individu.setPrenom2(infosAdmEtu.getPrenom2());
			individu.setDateNaissance(
			    infosAdmEtu.getDateNaissance().toGregorianCalendar().getTime());
			individu.setVilleNaissance(infosAdmEtu.getLibVilleNaissance());
			individu.setCodPayNaissance(infosAdmEtu.getPaysNaissance().getCodPay());
			if (infosAdmEtu.getDepartementNaissance() != null) {
				//can be null if individual was born abroad
				individu.setCodDepPaysNaissance(infosAdmEtu.getDepartementNaissance().getCodeDept());
			}

			individu.setCodPayNationalite(infosAdmEtu.getNationaliteDTO().getCodeNationalite());
			individu.setSexe(infosAdmEtu.getSexe());

			// Recuperation des infos de l'etudiant dans Apogee			
			CoordonneesDTO coordonnees = remoteApoRenEtuMetier.recupererAdressesEtudiant(
					etudiant.getCodEtu().toString(), null, temoinRecupAnnu);
			
			individu.setAdressMail(coordonnees.getEmail());
			individu.setEmailAnnuaire(coordonnees.getEmailAnnuaire());
			if (StringUtils.hasText(coordonnees.getNumTelPortable())) {
				individu.setNumeroTelPortable(coordonnees.getNumTelPortable().replaceAll("[. ]", ""));	
			}


			Map<String, Adresse> adresses = new HashMap<String, Adresse>();

			adresses.put(Constantes.ADR_FIX, getAdresseFix(coordonnees.getAdresseFixe()));

			individu.setAdresses(adresses);


			return individu;
			// TODO: fix this !!
//		} catch (WebBaseException e) {
//			log.error("WebBaseException in getIndividuFromApogee = " + e);
//			return null;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}


	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getIndBacFromApogee(
	 * java.lang.String, java.lang.String, java.lang.String)
	 */
	public List<IndBac> getIndBacFromApogee(
			final String codeNne, final String clefNne, final String codEtu) {
		log.info("entering getIndBacFromApogee( " 
				+ codeNne + ", " + clefNne + ", " + codEtu + " )");
		try {
			List<IndBac> indBacs = new ArrayList<IndBac>();

			String codeEtudiant = null;
			if (!StringUtils.hasText(codEtu)) {
				// Recherche l'etudiant dans Apogee
				IdentifiantsEtudiantDTO etudiant = remoteApoRenEtuMetier.recupererIdentifiantsEtudiant(
						null, null, codeNne, clefNne, null,
						null, null, null, null, temoinRecupAnnu);
				
				codeEtudiant = etudiant.getCodEtu().toString();
			} else {
				codeEtudiant = codEtu;
			}

			// Recuperation des infos de l'etudiant dans Apogee			
			InfoAdmEtuDTO infosAdmEtu = remoteApoRenEtuMetier.recupererInfosAdmEtu(codeEtudiant);
			
			List<IndBacDTO> indBacDtos = infosAdmEtu.getListeBacs().getItem();
			for (IndBacDTO indBacDto : indBacDtos) {
				IndBac indBac = new IndBac();

				indBac.setCodBac(indBacDto.getCodBac());
				indBac.setLibelle(indBacDto.getLibelleBac());
				indBac.setCodEtb(indBacDto.getEtbBac().getCodeEtb());
				indBac.setCodDep(indBacDto.getDepartementBac().getCodeDept());
				indBac.setCodMnb(indBacDto.getMentionBac().getCodMention());
				indBac.setCodTpe(indBacDto.getTypeEtbBac().getCodTypeEtb());
				indBac.setDateObtention(indBacDto.getAnneeObtentionBac());

				indBacs.add(indBac);
			}

			if (indBacs.isEmpty()) {
				return null;
			}
			return indBacs;
			//TODO : fix this !!
//		} catch (WebBaseException e) {
//			log.error("WebBaseException in getIndBacFromApogee = " + e);
//			return null;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}	


	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getIndCursusScolFromApogee(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
	@Override
	public List<IndCursusScol> getIndCursusScolFromApogee(final Individu individu) {
		log.info("entering getIndCursusScolFromApogee( " + individu + " )");
		try {
			List<IndCursusScol> cursusScol = null;
			//si l'individu a un code etudiant
			if (StringUtils.hasText(individu.getCodeEtu())) {
				cursusScol = new ArrayList<IndCursusScol>();
				//INITIALISATION DES CURSUS HORS RENNES1
				if (isRecupCursusExt) {
					CursusExternesEtTransfertsDTO curEtTrans = remoteApoRenAdminMetier
					.recupererCursusExterne(individu.getCodeEtu());
					if (curEtTrans != null) {
						for (CursusExterneDTO curE : curEtTrans.getListeCursusExternes().getItem()) {
							CursusExt curExt = new CursusExt(curE.getAnnee());
							if (curE.getEtablissement() != null) {
								curExt.setCodEtablissement(curE
										.getEtablissement().getCodeEtb());
							}
							if (curE.getTypeEtb() != null) {
								curExt.setCodTypeEtab(curE.getTypeEtb()
										.getCodTypeEtb());
							}
							if (curE.getTypeAutreDiplome() != null) {
								curExt.setCodDac(curE.getTypeAutreDiplome()
										.getCodeTypeDiplome());
								curExt.setLibDac(curE.getTypeAutreDiplome()
										.getLibTypeDiplome());
							}
							if (obtentionDip
									&& curE.getTemObtentionDip()
											.equalsIgnoreCase("O")) {
								curExt.setResultat(curE.getTemObtentionDip());
							} else {
								curExt.setResultat(curE.getTemObtentionNiveau());
							}
							curExt.setTemoinFromApogee(true);
							// INIT ATTRIBUTS
							cursusScol.add(curExt);
						}
					}
				}
				//INITIALISATION DES CURSUS DANS RENNES1
				List<CursusR1> cursusR1List = new ArrayList<CursusR1>();
				List<InsAdmEtpDTO> tabInsAdmEtp = remoteApoRenAdminMetier.recupererIAEtapes(
						individu.getCodeEtu(), "toutes", null, null);
				for (InsAdmEtpDTO insAdmEtp : tabInsAdmEtp) {
					CursusR1 curR1 = new CursusR1(insAdmEtp.getAnneeIAE(), 
							codEtbInt, 
							insAdmEtp.getDiplome().getCodeDiplome(), 
							insAdmEtp.getEtape().getCodeEtp(),
							insAdmEtp.getDiplome().getVersionDiplome(),
							insAdmEtp.getEtape().getVersionEtp(), 
							insAdmEtp.getEtape().getLibWebVet());

					//TODO a mettre en properties de la classe (externaliser cette valeure)
					//code Type etablissement de Rennes1.
					curR1.setCodTypeEtab("00");
					curR1.setTemoinFromApogee(true);
					//INIT ATTRIBUTS
					cursusR1List.add(curR1);
				}
				//RECUPERATION DES RESULTAT DES CURSUS DE RENNES1 
				List<ContratPedagogiqueResultatVdiVetDTO> resultatVdiVet = 
					remoteApoRenPedaMetier.recupererContratPedagogiqueResultatVdiVet(
							individu.getCodeEtu(), "toutes",
							"Apogee", null, "toutes",  "Admission");

				for (CursusR1 cursR1 : cursusR1List) {
					cursusScol.add(getCurR1AndRes(cursR1, resultatVdiVet));
				}
			}
			return cursusScol;
			//TODO: fix this !!
//		} catch (WebBaseException e) {
//			log.error("WebBaseException in getIndCursusScolFromApogee = " + e);
//			return null;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/**
	 * @see org.esupportail.opi.domain.DomainApoService#getAnneesIa(
	 * org.esupportail.opi.domain.beans.user.Individu)
	 */
	@Override
	public String[] getAnneesIa(final Individu individu) {
		log.info("entering getAnneesIa( " + individu + " )");
		try {
			List<String> anneesIA = new ArrayList<String>();
			//si l'individu a un code etudiant
			if (StringUtils.hasText(individu.getCodeEtu())) {
				anneesIA = remoteApoRenAdminMetier.recupererAnneesIa(individu.getCodeEtu(), null);
			}
			return anneesIA.toArray(new String[anneesIA.size()]);
			//TODO: fix this !!
//		} catch (WebBaseException e) {
//			log.error("WebBaseException in getIndCursusScolFromApogee = " + e);
//			return null;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/**
	 * @param cursusR1
	 * @param resultatVdiVet
	 * @return CursusR1
	 */
	private CursusR1 getCurR1AndRes(
			final CursusR1 cursusR1, 
			final List<ContratPedagogiqueResultatVdiVetDTO> resultatVdiVet) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCurR1AndRes curR1 = " 
					+ cursusR1);
		}

		CursusR1 cR1 = cursusR1;
		for (ContratPedagogiqueResultatVdiVetDTO resultat : resultatVdiVet) {
			for (EtapeResVdiVetDTO etape : resultat.getEtapes().getItem()) {
				if (cR1.getCodEtape().equals(etape.getEtape().getCodEtp())
						&& cR1.getCodVersionEtape().equals(
								String.valueOf(etape.getEtape().getCodVrsVet()))
								&& cR1.getAnnee().equals(etape.getCodAnu())) {
					if (etape.getResultatVet() != null) {

						for (ResultatVetDTO resVet : etape.getResultatVet().getItem()) {									
							if (resVet.getTypResultat() != null) {
								if (log.isDebugEnabled()) {
									log.debug("session lib = " 
											+ resVet.getSession().getLibSes());
									log.debug("LibTre = " + resVet.getTypResultat().getLibTre());
								}
								cR1.setResultat(
										resVet.getTypResultat().getLibTre());
							}
							if (resVet.getMention() != null) {
								cR1.setLibMention(
										resVet.getMention().getLibMen());
							}
						}
					} 
				}
			}
		}
		return cR1;
	}

	/**
	 * Return the Adresse object filled with the AdresseDTO.
	 * @param adrDTO
	 * @return Adresse
	 */
	private AdresseFix getAdresseFix(final AdresseDTO adrDTO) {
		AdresseFix adr = null;
		if (adrDTO.getCommune() != null) {
			adr = new AdresseFix(
					adrDTO.getLibAd1(),
					adrDTO.getLibAd2(), 
					adrDTO.getLibAd3(),
					adrDTO.getCommune().getCodeInsee(),
					adrDTO.getCommune().getCodePostal(),
					adrDTO.getPays().getCodPay());
		} else {
			adr = new AdresseFix(
					adrDTO.getLibAd1(),
					adrDTO.getLibAd2(), 
					adrDTO.getLibAd3(),
					null,
					null,
					adrDTO.getPays().getCodPay());
		}
		adr.setPhoneNumber(adrDTO.getNumTel());
		return adr;
	}

	
	@Override
	@Cacheable(cacheName = CacheModelConst.GEO_APOGEE_MODEL)
	public CommuneDTO getCommune(final String codCom, final String codBdi) {
		if (log.isDebugEnabled()) {
			log.debug("entering getCommune(" + codCom + ", " 
					+ codBdi + "  )");
		}
		if (StringUtils.hasText(codCom) 
				&& StringUtils.hasText(codBdi)) {
			//parcours la liste des VET ouvertes au recrutement
			List<CommuneDTO> co = getCommunesDTO(codBdi);
			for (CommuneDTO c : co) {
				if (c.getCodeCommune().equals(codCom)) {
					return c;
				}
			}
		}
		return null;
	}
	

	// ////////////////////////////////////////////////////////////
	// GrpTypDip
	// ////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getGrpTypDip(org.esupportail.opi.domain.beans.parameters.Campagne)
	 */
	@Override
	@Cacheable(cacheName = CacheModelConst.RENNES1_APOGEE_MODEL)
	public List<GrpTypDip> getGrpTypDip(final Campagne camp) {
		if (log.isDebugEnabled()) {
			log.debug("entering getRen1GrpTypDip()");
		}
		List<GrpTypDip> listEnd = new ArrayList<GrpTypDip>();
		Set<Commission> cmi = parameterService.getCommissions(true);
		try {
			List<GrpTypDip> l = remoteApo.getGrpTypDip(TRUE);

			//1.on recupere toutes les VET par groupes
			for (final GrpTypDip g : l) {
				List<String> codTpdEtb = new ArrayList<String>();
				for (GrpTypDipCorresp rCorresp : g.getGrpTypDipCorresps()) {
					codTpdEtb.add(rCorresp.getCodTpdEtb());
				}
				List<Diplome> dip = remoteCriApogeeEns.getDiplomes(null, codTpdEtb);
				if (dip != null && !dip.isEmpty()) {
					List<VersionEtapeDTO> vetDTO = remoteCriApogeeEns.getVersionEtapes2(dip, 
							camp.getCodAnu());
					Set<VersionEtpOpi> vetOpi = Conversions.convertVetInVetOpi(
							new HashSet<VersionEtapeDTO>(vetDTO));
					//2.on verifie que ces VET sont raccrochees e des commissions.
					for (Commission c : cmi) {
						Boolean isAddGrp = false;
						for (TraitementCmi trt : c.getTraitementCmi()) {
							if (vetOpi.contains(trt.getVersionEtpOpi())) {
								listEnd.add(g);
								isAddGrp = true;
								break;
							}
						}
						if (isAddGrp) { break; }
					}
				}
			}
			Collections.sort(listEnd, new Comparator<GrpTypDip>() {
				@Override
				public int compare(final GrpTypDip o1, final GrpTypDip o2) {
					return o1.getLibGrpTpd().compareToIgnoreCase(o2.getLibGrpTpd());
				}
			});
			return listEnd;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}	

	// ////////////////////////////////////////////////////////////
	// Domaine2AnnuFormDTO
	// ////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDomaine2AnnuForm(
	 * org.esupportail.opi.domain.beans.formation.GrpTypDip, java.lang.String)
	 */
	@Override
	public Map<Domaine2AnnuForm, List<Cles2AnnuForm>> getDomaine2AnnuForm(
			final GrpTypDip ren1GrpTypDip, final String locale) {
		if (log.isDebugEnabled()) {
			log.debug("entering getDomaine2AnnuFormDTO( " + ren1GrpTypDip + ", " + locale + " )");
		}
		try {
			return remoteApo.getDomaine2AnnuFormDTO(ren1GrpTypDip, locale);
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}
	
	/////////////////////////////////////////////////////////
	// SignataireDTO
	//////////////////////////////////////////////////////////////


//	@Override
	// TODO : À supprimer 18/01/2012
	public SignataireDTO getSignataire(final String codSig) {
		if (log.isDebugEnabled()) {
			log.debug("entering getSignataire with " + codSig);
		}
		try {
			SignataireDTO d = remoteCriApogeeRef.getSignataire(codSig);
			return d;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getSignataires()
	 */
	@Override
	public List<SignataireDTO> getSignataires() {
		if (log.isDebugEnabled()) {
			log.debug("entering getSignataires");
		}
		try {
			List<SignataireDTO> d = remoteCriApogeeRef.getSignataires(TRUE);
			return d;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}


	//////////////////////////////////////////////////////////////
	// LaisserPasserDTO
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#addTelemLaisserPasser(
	 * java.util.List, java.lang.Boolean)
	 */
	@Override
	public void addTelemLaisserPasser(final List<IndVoeu> wishes, final Boolean isReins) {
		if (log.isDebugEnabled()) {
			log.debug("entering addTelemLaisserPasser wishes = " + wishes + ", isReins = " + isReins);
		}

		List<TelemLaisserPasserDTO> list = new ArrayList<TelemLaisserPasserDTO>();
		List<TelemLaisserPasserOpiDTO> listOpi = new ArrayList<TelemLaisserPasserOpiDTO>();
		for (IndVoeu i : wishes) {
			if (isReins) {
				list.add((TelemLaisserPasserDTO) getTelemLaisserPasser(i, isReins));
			} else {
				
				listOpi.add((TelemLaisserPasserOpiDTO) getTelemLaisserPasser(i, isReins));
			}

		}
		if (isReins) {
			insertLaisserPasser.addTelemLaisserPasser(list);
			log.info("leaving addTelemLaisserPasser  : " + list.size() + "ajouter dans Apogee");
		} else {
			insertLaisserPasser.addTelemLaisserPasserOpi(listOpi);
			log.info("leaving addTelemLaisserPasserOpi  : " + listOpi.size() + "ajouter dans Apogee");
		}
	}


	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#deleteTelemLaisserPasser(
	 * java.util.List, java.lang.Boolean)
	 */
	@Override
	public void deleteTelemLaisserPasser(final List<IndVoeu> wishes, final Boolean isReins) {
		if (log.isDebugEnabled()) {
			log.debug("entering addTelemLaisserPasser wishes = " + wishes + ", isReins = " + isReins);
		}
		List<TelemLaisserPasserDTO> list = new ArrayList<TelemLaisserPasserDTO>();
		List<TelemLaisserPasserOpiDTO> listOpi = new ArrayList<TelemLaisserPasserOpiDTO>();
		for (IndVoeu i : wishes) {
			if (isReins) {
				list.add((TelemLaisserPasserDTO) getTelemLaisserPasser(i, isReins));
			} else {
				
				listOpi.add((TelemLaisserPasserOpiDTO) getTelemLaisserPasser(i, isReins));
			}

		}
		if (isReins) {
			insertLaisserPasser.deleteTelemLaisserPasser(list);
			log.info("leaving deleteTelemLaisserPasser  : " + list.size() + "supprimer dans Apogee");
		} else {
			insertLaisserPasser.deleteTelemLaisserPasserOpi(listOpi);
			log.info("leaving deleteTelemLaisserPasserOpi  : " + listOpi.size() + "supprimer dans Apogee");
		}
	}

	/**
	 * @param wish
	 * @param isReins
	 * @return TelemLaisserPasserDTO or TelemLaisserPasserOPiDTO
	 */
	private Object getTelemLaisserPasser(final IndVoeu wish, final Boolean isReins) {
	    TelemLaisserPasserDTO t = new TelemLaisserPasserDTO();
	    TelemLaisserPasserOpiDTO tOpi = new TelemLaisserPasserOpiDTO();
	    Individu ind = wish.getIndividu();
	    TraitementCmi trtCmi = wish.getLinkTrtCmiCamp().getTraitementCmi();
	    Campagne camp = wish.getLinkTrtCmiCamp().getCampagne();
	    final GregorianCalendar cal = new GregorianCalendar() {{
	        setTime(new Date());
	    }};
	    XMLGregorianCalendar xmlCal;
	    try {
	        xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
	        if (isReins) {
	            t.setCodUtiCreLpa("CONVERSION");
	            t.setDatCreLpa(xmlCal);
	            t.setCodEtp(trtCmi.getVersionEtpOpi().getCodEtp());
	            t.setCodVrsVet(trtCmi.getVersionEtpOpi().getCodVrsVet());
	            VersionDiplomeDTO vdiDTO = getVersionDiplomes(trtCmi.getVersionEtpOpi(), camp);
	            if (vdiDTO != null) {
	                t.setCodDip(vdiDTO.getCodDip());
	                t.setCodVrsVdi(vdiDTO.getCodVrsVdi());
	            }
	            t.setCodAnu(camp.getCodAnu());
	            t.setCodEtuLpa(Integer.parseInt(ind.getCodeEtu()));
	            t.setLibCmtLpa("Avis Favorable en OPI dossier n " + ind.getNumDossierOpi());
	            //manque le code Ind 
	            //meme ind pour tous les voeux
	            Integer codInd = ind.getCodInd();
	            if (codInd == null) {
	                //TODO a supprimer pour la campagne 2009-2010 car ajout dans individu du codeInd
	                IdentifiantsEtudiantDTO etudiant = remoteApoRenEtuMetier.recupererIdentifiantsEtudiant(
		                    ind.getCodeEtu(), null, null,
		                    null, null, null, null, null, null, TRUE);
	            	codInd = etudiant.getCodInd();
	                
	            }
	            t.setCodInd(codInd);
	            return t;
	        } 
	        tOpi.setCodUtiCreLpa("CONVERSION");
	        tOpi.setDatCreLpa(xmlCal);
	        tOpi.setCodEtp(trtCmi.getVersionEtpOpi().getCodEtp());
	        tOpi.setCodVrsVet(trtCmi.getVersionEtpOpi().getCodVrsVet());
	        VersionDiplomeDTO vdiDTO = getVersionDiplomes(trtCmi.getVersionEtpOpi(), camp);
	        if (vdiDTO != null) {
	            tOpi.setCodDip(vdiDTO.getCodDip());
	            tOpi.setCodVrsVdi(vdiDTO.getCodVrsVdi());
	        }
	        tOpi.setCodAnu(camp.getCodAnu());
	        tOpi.setCodOpiIntEpo(ind.getNumDossierOpi());
	        tOpi.setLibCmtLpa("Avis Favorable en OPI dossier n " + ind.getNumDossierOpi());
	        //manque le code Ind 
	        //meme ind pour tous les voeux
	        //TODO rÃÂ©cupÃÂ©rer codIndOpi
	        IndOpiDTO opiDTO = getIndOpiDTO(ind);
	        if (opiDTO != null) {
	            tOpi.setCodIndOpi(opiDTO.getCodIndOpi());
	        }
	        return tOpi;
	        
        } catch (DatatypeConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (etudiantwebserviceimpl.impl.webservices.commun.apogee.education.gouv.WebBaseException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
	    return new Object();
	}
	
	/**
	 * @param i
	 * @return IndOpiDTO
	 */
	private IndOpiDTO getIndOpiDTO(final Individu i) {
		String nom = org.esupportail.commons.utils.strings.StringUtils
						.removeUtf8Accents(i.getNomPatronymique());
		String prenom = org.esupportail.commons.utils.strings.StringUtils
							.removeUtf8Accents(i.getPrenom());
        final GregorianCalendar cal = new GregorianCalendar() {{
            setTime(i.getDateNaissance());
        }};
        XMLGregorianCalendar xmlCal = null;
        try {
            xmlCal = DatatypeFactory.newInstance().newXMLGregorianCalendar(cal);
        } catch (DatatypeConfigurationException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
		return remoteCriApogeeEtudiant.getIndOpi(
				xmlCal, "N", nom, prenom, i.getNumDossierOpi());
	}

	//////////////////////////////////////////////////////////////
	// DateWeb
	//////////////////////////////////////////////////////////////

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDateDebWebPrimoNb()
	 */
	@Override
	public String getDateDebWebPrimoNb() {
		if (log.isDebugEnabled()) {
			log.debug("entering getDateDebWebPrimoNb");
		}
		try {
			String d = remoteCriApogeeRef.getDateDebWebPrimoNb();
			return d;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDateDebWebPrimoNnb()
	 */
	@Override
	public String getDateDebWebPrimoNnb() {
		if (log.isDebugEnabled()) {
			log.debug("entering getDateDebWebPrimoNnb");
		}
		try {
			String d = remoteCriApogeeRef.getDateDebWebPrimoNnb();
			return d;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	/** 
	 * @see org.esupportail.opi.domain.DomainApoService#getDateDebWeb()
	 */
	@Override
	public String getDateDebWeb() {
		if (log.isDebugEnabled()) {
			log.debug("entering getDateDebWeb");
		}
		try {
			String d = remoteCriApogeeRef.getDateDebWeb();
			return d;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}


	
	/**
	 * @see org.esupportail.opi.domain.DomainApoService#getVariableAppli(java.lang.String)
	 */
	@Override
	public String getVariableAppli(final String var) {
		if (log.isDebugEnabled()) {
			log.debug("entering getVariableAppli");
		}
		try {
			String v = remoteCriApogeeRef.getVariableAppli(var).getParVap();
			return v;
		} catch (Exception e) {
			throw new CommunicationApogeeException(e);
		}
	}

	
	
	
	//////////////////////////////////////////////////////////////
	// Misc
	//////////////////////////////////////////////////////////////


	/**
	 * @param obtentionDip the obtentionDip to set
	 */
	public void setObtentionDip(final boolean obtentionDip) {
		this.obtentionDip = obtentionDip;
	}

	/**
	 * @param remoteCriApogeeRef the remoteCriApogeeRef to set
	 */
	public void setRemoteCriApogeeRef(final ReadReferentiel remoteCriApogeeRef) {
		this.remoteCriApogeeRef = remoteCriApogeeRef;
	}

	/**
	 * @param remoteCriApogeeEns the remoteCriApogeeEns to set
	 */
	public void setRemoteCriApogeeEns(final ReadEnseignement remoteCriApogeeEns) {
		this.remoteCriApogeeEns = remoteCriApogeeEns;
	}


	/**
	 * @param codEtbInt the codEtbInt to set
	 */
	public void setCodEtbInt(final String codEtbInt) {
		this.codEtbInt = codEtbInt;
	}

	/**
	 * @param onlyTerminaux the onlyTerminaux to set
	 */
	public void setOnlyTerminaux(final boolean onlyTerminaux) {
		this.onlyTerminaux = onlyTerminaux;
	}

	/**
	 * @return the codDacEtr
	 */
	public String getCodDacEtr() {
		return codDacEtr;
	}

	/**
	 * @param codDacEtr the codDacEtr to set
	 */
	public void setCodDacEtr(final String codDacEtr) {
		this.codDacEtr = codDacEtr;
	}
	
	/**
	 * @return ReadRennes1
	 */
	public IApogee getRemoteApo() {
		return remoteApo;
	}
	
	/**
	 * @return AdministratifMetier
	 */	
	public AdministratifMetierServiceInterface getRemoteApoRenAdminMetier() {
		return remoteApoRenAdminMetier;
	}
	
	/**
	 * @return EtudiantMetierService
	 */	
	public EtudiantMetierServiceInterface getRemoteApoRenEtuMetier() {
		return remoteApoRenEtuMetier;
	}
	
	/**
	 * @return PedagogiqueMetierService
	 */	
	public PedagogiqueMetierServiceInterface getRemoteApoRenPedaMetier() {
		return remoteApoRenPedaMetier;
	}

	/**
	 * @return GeographieMetierService
	 */	
	public GeographieMetierServiceInterface getRemoteApoRenGeoMetier() {
		return remoteApoRenGeoMetier;
	}
	
	/**
	 * @param remoteApo the remoteCriApogeeRennes1 to set
	 */
	public void setRemoteApo(final IApogee remoteApo) {
		this.remoteApo = remoteApo;
	}

	/**
	 * @param parameterService the parameterService to set
	 */
	public void setParameterService(final ParameterService parameterService) {
		this.parameterService = parameterService;
	}

	/**
	 * @param insertLaisserPasser the insertLaisserPasser to set
	 */
	public void setInsertLaisserPasser(final InsertLaisserPasser insertLaisserPasser) {
		this.insertLaisserPasser = insertLaisserPasser;
	}

	/**
	 * @param remoteCriApogeeEtudiant the remoteCriApogeeEtudiant to set
	 */
	public void setRemoteCriApogeeEtudiant(final ReadEtudiant remoteCriApogeeEtudiant) {
		this.remoteCriApogeeEtudiant = remoteCriApogeeEtudiant;
	}
	
	/**
	 * @param remoteApoRenAdminMetier the remoteApoRenAdminMetier to set
	 */
	public void setRemoteApoRenAdminMetier(final AdministratifMetierServiceInterface remoteApoRenAdminMetier) {
		this.remoteApoRenAdminMetier = remoteApoRenAdminMetier;
	}

	/**
	 * @param remoteApoRenEtuMetier the remoteApoRenEtuMetier to set
	 */
	public void setRemoteApoRenEtuMetier(final EtudiantMetierServiceInterface remoteApoRenEtuMetier) {
		this.remoteApoRenEtuMetier = remoteApoRenEtuMetier;
	}

	/**
	 * @param remoteApoRenPedaMetier the remoteApoRenEtuMetier to set
	 */
	public void setRemoteApoRenPedaMetier(final PedagogiqueMetierServiceInterface remoteApoRenPedaMetier) {
		this.remoteApoRenPedaMetier = remoteApoRenPedaMetier;
	}

	/**
	 * @param remoteApoRenGeoMetier the remoteApoRenGeoMetier to set
	 */
	public void setRemoteApoRenGeoMetier(final GeographieMetierServiceInterface remoteApoRenGeoMetier) {
		this.remoteApoRenGeoMetier = remoteApoRenGeoMetier;
	}

	/**
	 * @param isRecupCursusExt
	 */
	public void setIsRecupCursusExt(final boolean isRecupCursusExt) {
		this.isRecupCursusExt = isRecupCursusExt;
	}

	/**
	 * @param temoinRecupAnnu the temoinRecupAnnu to set
	 */
	public void setTemoinRecupAnnu(String temoinRecupAnnu) {
		this.temoinRecupAnnu = temoinRecupAnnu;
	}

    /**
     * TODO : Méthode TRÈS lente (nombreux appels hibernate)
     * TODO : À optimiser
     * TODO : Éviter d'y avoir recours
     *
     * List of the commisions managed by the gestionnaire.
     * @param gest
     * @param temEnSve
     * @return Set<Commission>
     */
    public Set<Commission> getListCommissionsByRight(
            final Gestionnaire gest,
            final Boolean temEnSve) {
        Set<Commission> lesCommissions = new HashSet<Commission>();
        if(gest != null){
            if (StringUtils.hasText(gest.getCodeCge())) {
                final Set<VersionEtpOpi> vOpi = Conversions.convertVetInVetOpi(
                        new HashSet<VersionEtapeDTO>(
                                getVersionEtapes(null, null, gest.getCodeCge(), null)));
                Set<Commission> lCom = parameterService.getCommissions(temEnSve);
                if (lCom != null)
                    for (Commission c : lCom) {
                        if (!c.getTemoinEnService()) log.info("cas d'une comm HS");
                        for (Set<TraitementCmi> trts : fromNull(c.getTraitementCmi()))
                          for (TraitementCmi trt : trts)
                            if (vOpi.contains(trt.getVersionEtpOpi())) {
                                lesCommissions.add(c);
                                break;
                            }
                    }
            } else if (gest.getRightOnCmi()!= null && !gest.getRightOnCmi().isEmpty()) {
                //si pas cge, renvoie les cmi auxquelles ils ont droit
                // TODO: change getRightOnCmi to return a list ?
                lesCommissions = gest.getRightOnCmi();
            } else {
                lesCommissions = parameterService.getCommissions(null);
            }
        } else {
            lesCommissions = parameterService.getCommissions(null);
        }
        return lesCommissions;
    }
}
