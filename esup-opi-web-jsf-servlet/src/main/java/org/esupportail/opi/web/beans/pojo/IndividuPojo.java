/**
*CRI - Universite de Rennes1 - 57SI-OPI - 2008
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import static fj.data.Stream.iterableStream;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import fj.F;
import fj.data.Stream;
import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.esupportail.commons.annotations.cache.RequestCache;
import org.esupportail.commons.services.i18n.I18nService;
import org.esupportail.opi.domain.BusinessUtil;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.Etat;
import org.esupportail.opi.domain.beans.etat.EtatComplet;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatNonRenseigne;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.calendar.CalendarIns;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.references.rendezvous.CalendarRDV;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.domain.beans.user.indcursus.CursusPro;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursus;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.domain.beans.user.indcursus.QualifNonDiplomante;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.utils.Utilitaires;
import org.esupportail.opi.web.utils.comparator.ComparatorString;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.springframework.util.StringUtils;







/**
 * @author leproust cedric
 *
 */
public class IndividuPojo {

	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The individu.
	 */
	private Individu individu;
	
	/**
	 * The flag to know if the individu have a Nne Code or not.
	 */
	private Boolean doNotHaveCodeNne;
	
	/**
	 * The Pays.
	 */
	private Pays pays;
	
	/**
	 * The Pays.
	 */
	private Pays nationalite;
	
	/**
	 * The Pays.
	 */
	private Departement departement;
	
	/**
	 * The vows of individu.
	 */
	private Set<IndVoeuPojo> indVoeuxPojo;
	
	/**
	 * a true si c'est un gestionnaire.
	 * Default value false.
	 */
	private Boolean isManager;
	
	/**
	 *  true is the manager can update the current student in use.
	 *  Default value = false
	 */
	private Boolean isUpdaterOfThisStudent;
	
	/**
	 * The state of account individu.
	 */
	private EtatIndividu etat;
	
	/**
	 * see {@link RegimeInscription}.
	 */
	private RegimeInscription regimeInscription;
	
	/**
	 * see {@link I18nService}.
	 */
	private I18nService i18nService;
	
	/**
	 * List of cursus.
	 */
	private List<IndCursusScolPojo> indCursusScolPojo;
	
	/**
	 * Date de creation du dossier electronique.
	 */
	private Date dateCreationDossier;
	
	/**
	 * true if the type of decision is LC.
	 */
	private Boolean isUsingLC;
	
	/**
	 * true if the type of decision is LC.
	 */
	private Boolean isUsingDEF;
	
	/**
	 * true if the individu use the search mode to create the dossier.
	 * default false
	 */
	private Boolean isUsingSearch;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public IndividuPojo() {
		individu = new Individu();
		doNotHaveCodeNne = false;
		isManager = false;
		i18nService = null;
		etat = (EtatIndividu) Etat.instanceState(individu.getState(), null);
		dateCreationDossier = null;
		isUsingLC = false;
		isUsingDEF = false;
		isUsingSearch = false;
	}
	
	/**
	 * Constructor.
	 * @param i18Service
	 */
	public IndividuPojo(final I18nService i18Service) {
		individu = new Individu();
		doNotHaveCodeNne = false;
		isManager = false;
		i18nService = i18Service;
		etat = (EtatIndividu) Etat.instanceState(individu.getState(), i18Service);
		dateCreationDossier = null;
		isUsingLC = false;
		isUsingDEF = false;
		isUsingSearch = false;
	}
	
	
	/**
	 * Constructor.
	 * @param individu 
	 * @param apoServ 
	 * @param i18Service
	 * @param parameterService
	 * @param commissions 
	 */
	public IndividuPojo(final Individu individu, final DomainApoService apoServ,
			final I18nService i18Service, final ParameterService parameterService,
			final RegimeInscription ri,
			final List<TypeTraitement> typeTraitements,
			final List<CalendarRDV> listCalendrierParam,
			final Set<Commission> commissions) {
		this.individu = individu;
		doNotHaveCodeNne = false;
		regimeInscription = ri;
		etat = (EtatIndividu) Etat.instanceState(individu.getState(), i18Service);
		initIndVoeuPojo(apoServ, i18Service, parameterService, commissions, null, typeTraitements, listCalendrierParam, null);
		i18nService = i18Service;
		isManager = false;
		dateCreationDossier = individu.getDateCreaEnr();
		isUsingLC = false;
		isUsingDEF = false;
		isUsingSearch = false;
	}
	
	/**
	 * Constructor.
	 * @param individu 
	 * @param i18Service
	 * @param apoServ 
	 * @param parameterService
	 * @param commissions 
	 * @param typeDecisions 
	 * @param versionsEtape 
	 */
	public IndividuPojo(final Individu individu, final DomainApoService apoServ,
			final I18nService i18Service, final ParameterService parameterService,
			final Set<Commission> commissions, final Set<TypeDecision> typeDecisions,
			final List<TypeTraitement> typeTraitements, final List<CalendarRDV> listCalendrierParam,
			final Set<VersionEtapeDTO> versionsEtape) {
		this.individu = individu;
		doNotHaveCodeNne = false;
		etat = (EtatIndividu) Etat.instanceState(individu.getState(), i18Service);
		initIndVoeuPojo(apoServ, i18Service, parameterService,
				commissions, typeDecisions, typeTraitements, listCalendrierParam, versionsEtape);
		i18nService = i18Service;
		isManager = false;
		dateCreationDossier = individu.getDateCreaEnr();
		isUsingLC = false;
		isUsingDEF = false;
		isUsingSearch = false;
	}
	
	
	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (individu != null) {
			result += individu.hashCode();
		}
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof IndividuPojo)) { return false; }
		final IndividuPojo other = (IndividuPojo) obj;
		if (individu == null) {
			if (other.individu != null) { return false; }
		} else if (!individu.equals(other.individu)) { return false; }
		return true;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndividuPojo#" + hashCode() + "[individu =" + individu 
					+ "], [pays =" + pays + "], [departement = " + departement 
					+ "], [doNotHaveCodeNne = " + doNotHaveCodeNne + "]";
	}
	
	/*
	 ******************* METHODS ********************** */
	/**
	 * Are all the voeux traited ?.
	 * @return boolean
	 */
	@RequestCache
	public boolean getHasAllVoeuxTraited() {
		boolean result = true;
		if (this.indVoeuxPojo == null) { return false; }
		if (this.indVoeuxPojo.isEmpty()) { return false; }
		for (IndVoeuPojo v : indVoeuxPojo) {
			if (!v.getIndVoeu().isHaveBeTraited()) { result = false; }
		}
		return result;
	}
	
	/**
	 * Found the last IndCursusScol of the student's cursus.
	 * @return IndCursusScol or null if no cursus
	 */
	public IndCursusScolPojo getDerniereAnneeEtudeCursus() {
		if (this.indCursusScolPojo == null) { return null; }
		if (this.indCursusScolPojo.isEmpty()) { return null; }
		return this.indCursusScolPojo.get(0);
	}
	
	
	/**
	 * Initialize the list of the voeu for the student.
	 * Avec filtrage des voeux sur les commissions du gestionnaire
	 * @param apoServ 
	 * @param i18Service 
	 * @param parameterService 
	 * @param commissions
	 * @param typeDecisions 
	 * @param versionsEtp 
	 */
	private void initIndVoeuPojo(final DomainApoService apoServ,
					final I18nService i18Service,
					final ParameterService parameterService,
					final Set<Commission> commissions,
					final Set<TypeDecision> typeDecisions,
					final List<TypeTraitement> typeTraitements,
					final List<CalendarRDV> listCalendrierParam,
					final Set<VersionEtapeDTO> versionsEtp) {
		indVoeuxPojo = new TreeSet<IndVoeuPojo>(new ComparatorString(IndVoeuPojo.class));
		if (individu != null) {
			Set<IndVoeu> indVoeu = individu.getVoeux();
			for (IndVoeu i : indVoeu) {
				final TraitementCmi trtCmi = i.getLinkTrtCmiCamp().getTraitementCmi();
                final boolean trtsMatch = iterableStream(commissions).bind(
                        new F<Commission, Stream<TraitementCmi>>() {
                            public Stream<TraitementCmi> f(Commission commission) {
                                return iterableStream(commission.getTraitementCmi());
                            }}).exists(
                        new F<TraitementCmi, Boolean>() {
                            public Boolean f(TraitementCmi traitementCmi) {
                                return traitementCmi.equals(trtCmi); }});

                if (trtsMatch) {
					Boolean calIsOpen = null;
                    //si il y a des commission c'est que
                    //c'est la vue gestionnaire on n'a donc pas besoin des calendriers.
                    if (commissions == null)
						calIsOpen = testIfCalIsOpen(i, parameterService);

					TypeTraitement t = null;
					if (StringUtils.hasText(i.getCodTypeTrait()))
						t = BusinessUtil.getTypeTraitement(
								typeTraitements, 
								i.getCodTypeTrait());

					Boolean addVoeuType = false;
					if (typeDecisions != null) {
						// must be filtred by typeDecision
						for (Avis a : i.getAvis())
							if (a.getTemoinEnService())
								if (typeDecisions.contains(a.getResult())) {
									addVoeuType = true;
									break;
								}
					} else { addVoeuType = true; }

                    Boolean addVoeuVet = true;
                    final VersionEtapeDTO vet = apoServ.getVersionEtape(
                            trtCmi.getVersionEtpOpi().getCodEtp(),
                            trtCmi.getVersionEtpOpi().getCodVrsVet());
                    // must be filtred by versionEtp
                    if (versionsEtp != null)
                        addVoeuVet = versionsEtp.contains(vet);

					if (addVoeuType && addVoeuVet) {
						CalendarRDV cal = Utilitaires.getRecupCalendarRdv(i, listCalendrierParam);
						indVoeuxPojo.add(
								new IndVoeuPojo(
										i, vet, i18Service,
										calIsOpen, t, cal));
					} 
				}
			}
		}
	}
	
	/**
	 * Initialize the list of the cursus for the student.
	 * @param domainApoService
	 * @param i18Service 
	 */
	public void initIndCursusScolPojo(final DomainApoService domainApoService,
					final I18nService i18Service) {
		indCursusScolPojo = new ArrayList<IndCursusScolPojo>();
		for (IndCursusScol iCur : individu.getCursusScol()) {
			indCursusScolPojo.add(
					new IndCursusScolPojo(iCur, i18Service, domainApoService));
		}
		Collections.sort(indCursusScolPojo, new ComparatorString(IndCursusScolPojo.class));
	}
	
	
	/**
	 * State of IndBac.
	 * @return String
	 */ 
	public String getEtatIndBac() {
		if (individu.getIndBac() != null 
				&& !individu.getIndBac().isEmpty()) {
			return new EtatComplet(i18nService).getLabel();
		}
		return new EtatNonRenseigne(i18nService).getLabel();
	}
	
	/**
	 * State of IndCursusScol.
	 * @return String
	 */ 
	public String getEtatIndCursusScol() {
		if (individu.getCursusScol() != null 
				&& !individu.getCursusScol().isEmpty()) {
			return new EtatComplet(i18nService).getLabel();
		}
		return new EtatNonRenseigne(i18nService).getLabel();
	}
	
	/**
	 * State of CursusPro.
	 * @return String
	 */ 
	public String getEtatIndCursusPro() {
		if (individu.getCursus() == null 
				|| individu.getCursus().isEmpty()) {
			return new EtatNonRenseigne(i18nService).getLabel();
		}
		boolean isCursusProExist = false;
		for (IndCursus c : individu.getCursus()) {
			if (c instanceof CursusPro) {
				isCursusProExist = true;
				break;
			}
		}
		if (isCursusProExist) {
			return new EtatComplet(i18nService).getLabel();
		}
		return new EtatNonRenseigne(i18nService).getLabel();
	}
	
	
	/**
	 * State of QualifNonDiplomante.
	 * @return String
	 */ 
	public String getEtatQualifNonDiplomante() {
		if (individu.getCursus() == null 
				|| individu.getCursus().isEmpty()) {
			return new EtatNonRenseigne(i18nService).getLabel();
		}
		boolean isQualifExist = false;
		for (IndCursus c : individu.getCursus()) {
			if (c instanceof QualifNonDiplomante) {
				isQualifExist = true;
				break;
			}
		}
		if (isQualifExist) {
			return new EtatComplet(i18nService).getLabel();
		}
		return new EtatNonRenseigne(i18nService).getLabel();
	}
	
	
	/**
	 * return true if it is a manager or if the regime can add vows.
	 * @return boolean
	 */
	public Boolean getHasRightsToAddVows() {
		return isManager || regimeInscription.getCanAlwaysAddVows();
	}
	
	/**
	 * has the current User the rights to update the current student.
	 * @return boolean
	 */
	public Boolean getAsRightsToUpdate() {
		boolean isRight = false;
		if (!isManager) { 
			isRight = true; 
		} else { 
			if (indVoeuxPojo != null && !indVoeuxPojo.isEmpty()) {
				isRight = isUpdaterOfThisStudent; 
			} else {
				//si pas de voeux le gestionnaire peut tout faire
				isRight =  true;
			}
			
		}
		return isRight;
	}
	
	/**
	 * has the manager the rights to update the current student.
	 * @return boolean
	 */
	public Boolean getManagerAsRightsToUpdate() {
		boolean isRight = false;
		if (isManager) { 
			if (indVoeuxPojo != null && !indVoeuxPojo.isEmpty()) {
				isRight = isUpdaterOfThisStudent; 
			} else {
				//si pas de voeux le gestionnaire peut tout faire
				isRight =  true;
			}
			
		}
		return isRight;
	}
	
	
	/**
	 * @return Boolean
	 */
	public Boolean getCanUpdateBac() {
		if (!getAsRightsToUpdate()) {
			if (getEtatIndBac().equals(new EtatNonRenseigne(i18nService).getLabel())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * @return Boolean
	 */
	public Boolean getCanUpdateIndCursusScol() {
		if (!getAsRightsToUpdate()) {
			if (getEtatIndCursusScol().equals(new EtatNonRenseigne(i18nService).getLabel())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * @return Boolean
	 */
	public Boolean getCanUpdateIndCursusPro() {
		if (!getAsRightsToUpdate()) {
			if (getEtatIndCursusPro().equals(new EtatNonRenseigne(i18nService).getLabel())) {
				return false;
			}
		}
		return true;
	}
	/**
	 * @return Boolean
	 */
	public Boolean getCanUpdateQualifNonDiplomante() {
		if (!getAsRightsToUpdate()) {
			if (getEtatQualifNonDiplomante().equals(new EtatNonRenseigne(i18nService).getLabel())) {
				return false;
			}
		}
		return true;
	}
	
	
	/**
	 * Test the calendar of vow. if it's open return true.
	 * @param i
	 * @param parameterService
	 * @return Boolean
	 */
	private Boolean testIfCalIsOpen(
			final IndVoeu i, 
			final ParameterService parameterService) {
		Set<CalendarIns> cIns = parameterService.getCalendars(i.getLinkTrtCmiCamp()
				.getTraitementCmi().getVersionEtpOpi());
		Boolean calIsOpen = false;
		for (CalendarIns cI : cIns) {
			if (Utilitaires.calIsOpen(cI)) {
				calIsOpen = true;
				break;
			}
		}
		
		return calIsOpen;
	}
	
	/**
	 * @return true si tous les voeux sont arrive complet, false sinon
	 */
	public Boolean getHideMessageInfoForm() {
		for (IndVoeuPojo indVoeu : indVoeuxPojo) {
			if (indVoeu.getEtat().getDisplayForms()) {
				return false;
			}
		}
		return true;
	}
	
	/**
	 * @return the campagne en service.
	 */
	public Campagne getCampagneEnServ() {
		return Utilitaires.getCampagneEnServ(individu);
	}
	
	/**
	 * @param domainService
	 * @return the campagne en service.
	 */
	public Campagne getCampagneEnServ(final DomainService domainService) {
		return Utilitaires.getCampagneEnServ(individu, domainService);
	}
	
	/**
	 * Return list voeux trié par libelle court.
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	public List<IndVoeuPojo> getVoeuxSortedByLic() {
		List<IndVoeuPojo> l = new ArrayList<IndVoeuPojo>(getIndVoeuxPojo());
		Collections.sort(l, new BeanComparator("vrsEtape.licEtp", new NullComparator()));
		return l;
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the individu
	 */
	public Individu getIndividu() {
		return individu;
	}

	/**
	 * @param individu the individu to set
	 */
	public void setIndividu(final Individu individu) {
		this.individu = individu;
	}

	/**
	 * @return the doNotHaveCodeNne
	 */
	public Boolean getDoNotHaveCodeNne() {
		return doNotHaveCodeNne;
	}

	/**
	 * @param doNotHaveCodeNne the doNotHaveCodeNne to set
	 */
	public void setDoNotHaveCodeNne(final Boolean doNotHaveCodeNne) {
		this.doNotHaveCodeNne = doNotHaveCodeNne;
	}

	/**
	 * @return the pays
	 */
	public Pays getPays() {
		return pays;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(final Pays pays) {
		this.pays = pays;
	}

	/**
	 * @return the departement
	 */
	public Departement getDepartement() {
		return departement;
	}

	/**
	 * @param departement the departement to set
	 */
	public void setDepartement(final Departement departement) {
		this.departement = departement;
	}


	/**
	 * @return the indVoeuxPojo
	 */
	public Set<IndVoeuPojo> getIndVoeuxPojo() {
		return indVoeuxPojo;
	}

	public List<IndVoeuPojo> getIndVoeuxPojoAsList() {
		return new ArrayList<IndVoeuPojo>(indVoeuxPojo);
	}
	

	/**
	 * @param indVoeuxPojo the indVoeuxPojo to set
	 */
	public void setIndVoeuxPojo(final Set<IndVoeuPojo> indVoeuxPojo) {
		this.indVoeuxPojo = indVoeuxPojo;
	}


	/**
	 * @return the etat
	 */
	public EtatIndividu getEtat() {
		return etat;
	}


	/**
	 * @param etat the etat to set
	 */
	public void setEtat(final EtatIndividu etat) {
		this.etat = etat;
	}


	/**
	 * @return the isManager
	 */
	public Boolean getIsManager() {
		return isManager;
	}


	/**
	 * @param isManager the isManager to set
	 */
	public void setIsManager(final Boolean isManager) {
		this.isManager = isManager;
	}


	/**
	 * @param isUpdaterOfThisStudent the isUpdaterOfThisStudent to set
	 */
	public void setIsUpdaterOfThisStudent(final Boolean isUpdaterOfThisStudent) {
		this.isUpdaterOfThisStudent = isUpdaterOfThisStudent;
	}

	/**
	 * @return the isUpdaterOfThisStudent
	 */
	public Boolean getIsUpdaterOfThisStudent() {
		return isUpdaterOfThisStudent;
	}


	/**
	 * @return the dateCreationDossier
	 */
	public Date getDateCreationDossier() {
		return dateCreationDossier;
	}

	/**
	 * @param dateCreationDossier the dateCreationDossier to set
	 */
	public void setDateCreationDossier(final Date dateCreationDossier) {
		this.dateCreationDossier = dateCreationDossier;
	}

	/**
	 * @return the indCursusScolPojo
	 */
	public List<IndCursusScolPojo> getIndCursusScolPojo() {
		return indCursusScolPojo;
	}

	/**
	 * @param indCursusScolPojo the indCursusScolPojo to set
	 */
	public void setIndCursusScolPojo(final List<IndCursusScolPojo> indCursusScolPojo) {
		this.indCursusScolPojo = indCursusScolPojo;
	}

	/**
	 * @return the nationalite
	 */
	public Pays getNationalite() {
		return nationalite;
	}

	/**
	 * @param nationalite the nationalite to set
	 */
	public void setNationalite(final Pays nationalite) {
		this.nationalite = nationalite;
	}

	/**
	 * @return the isUsingLC
	 */
	public Boolean getIsUsingLC() {
		return isUsingLC;
	}

	/**
	 * @param isUsingLC the isUsingLC to set
	 */
	public void setIsUsingLC(final Boolean isUsingLC) {
		this.isUsingLC = isUsingLC;
	}

	/**
	 * @return the isUsingDEF
	 */
	public Boolean getIsUsingDEF() {
		return isUsingDEF;
	}

	/**
	 * @param isUsingDEF the isUsingDEF to set
	 */
	public void setIsUsingDEF(final Boolean isUsingDEF) {
		this.isUsingDEF = isUsingDEF;
	}

	/**
	 * @return RegimeInscription
	 */
	public RegimeInscription getRegimeInscription() {
		return regimeInscription;
	}

	/**
	 * @param regimeInscription
	 */
	public void setRegimeInscription(final RegimeInscription regimeInscription) {
		this.regimeInscription = regimeInscription;
	}

	/**
	 * @return the isUsingSearch
	 */
	public Boolean getIsUsingSearch() {
		return isUsingSearch;
	}

	/**
	 * @param isUsingSearch the isUsingSearch to set
	 */
	public void setIsUsingSearch(final Boolean isUsingSearch) {
		this.isUsingSearch = isUsingSearch;
	}

	

	
	
}
