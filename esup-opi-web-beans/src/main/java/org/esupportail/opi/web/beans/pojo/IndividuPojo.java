/**
*CRI - Universite de Rennes1 - 57SI-OPI - 2008
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import fj.F;
import fj.data.Array;
import org.esupportail.commons.annotations.cache.RequestCache;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
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
import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.opi.services.i18n.I18NUtilsService;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

import java.util.*;

import static fj.data.Array.iterableArray;
import static fj.data.Array.single;
import static fj.data.Option.fromNull;
import static java.util.Arrays.asList;

public class IndividuPojo {

	private Individu individu;
	
	/**
	 * The flag to know if the individu have a Nne Code or not.
	 */
	private Boolean doNotHaveCodeNne;

	private Pays pays;

	private Pays nationalite;

	private Departement departement;

	private Array<IndVoeuPojo> indVoeuxPojo = Array.empty();

	private Boolean isManager;
	
	/**
	 *  true if the manager can update the current student in use.
	 *  Default value = false
	 */
	private Boolean isUpdaterOfThisStudent = false;

	private EtatIndividu etat;

	private RegimeInscription regimeInscription;

	private List<IndCursusScolPojo> indCursusScolPojo;

	private Date dateCreationDossier;

	private Boolean isUsingLC;

	private Boolean isUsingDEF;
	
	/**
	 * true if the individu use the search mode to create the dossier.
	 * default false
	 */
	private Boolean isUsingSearch = false;

    private String etatIndBac;

	public IndividuPojo() {
		individu = new Individu();
		doNotHaveCodeNne = false;
		isManager = false;
		etat = fromNull(individu.getState())
                .map(new F<String, EtatIndividu>() {
                    public EtatIndividu f(String s) {
                        return EtatIndividu.fromString(s);
                    }
                }).toNull();
        dateCreationDossier = null;
        isUsingLC = false;
        isUsingDEF = false;
        isUsingSearch = false;
    }

    /**
     * @deprecated Should not be used anymore
     */
	public IndividuPojo(final Individu individu,
                        final DomainApoService apoServ,
                        final I18NUtilsService i18nUtils,
                        final ParameterService parameterService,
                        final RegimeInscription ri,
                        final List<CalendarRDV> listCalendrierParam,
                        final Set<Commission> commissions) {
		this.individu = individu;
		doNotHaveCodeNne = false;
		regimeInscription = ri;
        etat = fromNull(individu.getState())
                .map(new F<String, EtatIndividu>() {
                    public EtatIndividu f(String s) {
                        return EtatIndividu.fromString(s);
                    }
                }).toNull();
		initIndVoeuPojo(apoServ, parameterService, commissions, null, listCalendrierParam, null);
		isManager = false;
		dateCreationDossier = individu.getDateCreaEnr();
		isUsingLC = false;
		isUsingDEF = false;
		isUsingSearch = false;
        etatIndBac = fromNull(individu.getIndBac())
                .filter(new F<Set<IndBac>, Boolean>() {
                    public Boolean f(Set<IndBac> indBacs) {
                        return !indBacs.isEmpty();
                    }})
                .option(i18nUtils.labelEtatNonRenseigne(),
                        new F<Set<IndBac>, String>() {
                            public String f(Set<IndBac> indBacs) {
                                return i18nUtils.labelEtatComplet();
                            }});
    }

    /**
     * @deprecated Should not be used anymore
     */
	public IndividuPojo(final Individu individu,
                        final DomainApoService apoServ,
                        final ParameterService parameterService,
                        final Set<Commission> commissions,
                        final Set<TypeDecision> typeDecisions,
                        final List<CalendarRDV> listCalendrierParam,
                        final Set<VersionEtapeDTO> versionsEtape) {
		this.individu = individu;
		doNotHaveCodeNne = false;
        etat = fromNull(individu.getState())
                .map(new F<String, EtatIndividu>() {
                    public EtatIndividu f(String s) {
                        return EtatIndividu.fromString(s);
                    }
                }).toNull();
		initIndVoeuPojo(apoServ, parameterService, commissions, typeDecisions, listCalendrierParam, versionsEtape);
		isManager = false;
		dateCreationDossier = individu.getDateCreaEnr();
		isUsingLC = false;
		isUsingDEF = false;
		isUsingSearch = false;
	}

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

	@Override
	public String toString() {
		return "IndividuPojo#" + hashCode() + "[individu =" + individu 
					+ "], [pays =" + pays + "], [departement = " + departement 
					+ "], [doNotHaveCodeNne = " + doNotHaveCodeNne + "]";
	}

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

	public IndCursusScolPojo getDerniereAnneeEtudeCursus() {
		if (this.indCursusScolPojo == null) { return null; }
		if (this.indCursusScolPojo.isEmpty()) { return null; }
		return this.indCursusScolPojo.get(0);
	}
	
	/**
	 * Initialize the list of the voeu for the student.
	 * Avec filtrage des voeux sur les commissions du gestionnaire
     *
     * TODO : on utilise une liste de commissions uniquement pour inhiber ou non le remplissage du set de IndVoeuPojo.
     * TODO : Or le trairement nécessaire pour obtenir cette liste est TRÈS couteux.
     * TODO : Il faut chercher à ne plus avoir besoin de cette liste peut-être en construisant systématiquement le set de IndVoeuPojo.
	 */
	private void initIndVoeuPojo(final DomainApoService apoServ,
                                 final ParameterService parameterService,
                                 final Set<Commission> commissions,
                                 final Set<TypeDecision> typeDecisions,
                                 final List<CalendarRDV> listCalendrierParam,
                                 final Set<VersionEtapeDTO> versionsEtp) {
		if (individu != null) {
			Set<IndVoeu> indVoeux = individu.getVoeux();
			for (final IndVoeu indVoeu : indVoeux) {
				final TraitementCmi trtCmi = indVoeu.getLinkTrtCmiCamp().getTraitementCmi();
				final boolean trtsMatch = fromNull(commissions).option(Boolean.TRUE, new F<Set<Commission>, Boolean>() {
					public Boolean f(Set<Commission> coms) {
                        return iterableArray(coms)
                                .bind(new F<Commission, Array<TraitementCmi>>() {
                                    public Array<TraitementCmi> f(final Commission commission) {
                                        return iterableArray(commission.getTraitementCmi());
                                    }
                                })
                                .exists(new F<TraitementCmi, Boolean>() {
                                    public Boolean f(final TraitementCmi traitementCmi) {
                                        return traitementCmi.equals(trtCmi);
                                    }
                                });
                    }
                });
                			
                if (trtsMatch) {
					Boolean calIsOpen = null;
                    //si il y a des commission c'est que
                    //c'est la vue gestionnaire on n'a donc pas besoin des calendriers.
                    if (commissions == null || commissions.isEmpty())
						calIsOpen = testIfCalIsOpen(indVoeu, parameterService);

					Boolean addVoeuType = false;
					if (typeDecisions != null && !typeDecisions.isEmpty()) {
						// must be filtered by typeDecision
						for (Avis a : indVoeu.getAvis())
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
                    // must be filtered by versionEtp
                    if (versionsEtp != null)
                    	addVoeuVet = iterableArray(versionsEtp).exists(new F<VersionEtapeDTO, Boolean>() {
                            @Override
                            public Boolean f(final VersionEtapeDTO v) {
                                return v.getCodEtp().equalsIgnoreCase(vet.getCodEtp())
                                        && v.getCodVrsVet().equals(vet.getCodVrsVet());
                            }
                        });

					if (addVoeuType && addVoeuVet) {
						CalendarRDV cal = Utilitaires.getRecupCalendarRdv(indVoeu, listCalendrierParam);
                        indVoeuxPojo = indVoeuxPojo
                                .append(single(new IndVoeuPojo(
                                        indVoeu,
                                        vet,
                                        EtatVoeu.fromString(indVoeu.getState()),
                                        calIsOpen,
                                        TypeTraitement.fromCode(indVoeu.getCodTypeTrait()),
                                        cal)))
                                .reverse();
					}
				}
			}
		}
	}

	public String getEtatIndBac() {
        return etatIndBac;
	}

	public Boolean getHasRightsToAddVows() {
		return isManager || regimeInscription.getCanAlwaysAddVows();
	}

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

	private Boolean testIfCalIsOpen(
			final IndVoeu i, 
			final ParameterService parameterService) {
		Set<CalendarIns> cIns = parameterService.getCalendars(
                i.getLinkTrtCmiCamp().getTraitementCmi().getVersionEtpOpi());
		Boolean calIsOpen = false;
		for (CalendarIns cI : cIns) {
			if (Utilitaires.calIsOpen(cI)) {
				calIsOpen = true;
				break;
			}
		}
		return calIsOpen;
	}

	public Boolean getHideMessageInfoForm() {
		for (IndVoeuPojo indVoeu : indVoeuxPojo)
            if (indVoeu.getEtat().getDisplayForms())
                return false;
		return true;
	}

	public Campagne getCampagneEnServ() {
		return Utilitaires.getCampagneEnServ(individu);
	}
	
	public Campagne getCampagneEnServ(final DomainService domainService) {
		return Utilitaires.getCampagneEnServ(individu, domainService);
	}

	public List<IndVoeuPojo> getVoeuxSortedByLic() {
        IndVoeuPojo[] ar = indVoeuxPojo.array(IndVoeuPojo[].class);
        Arrays.sort(ar, new Comparator<IndVoeuPojo>() {
            public int compare(IndVoeuPojo iv1, IndVoeuPojo iv2) {
                return iv1.getVrsEtape().getLicEtp().compareToIgnoreCase(iv2.getVrsEtape().getLicEtp());
            }
        });
	    return asList(ar);
	}

	public Individu getIndividu() {
		return individu;
	}

	public void setIndividu(final Individu individu) {
		this.individu = individu;
	}

	public Boolean getDoNotHaveCodeNne() {
		return doNotHaveCodeNne;
	}

	public void setDoNotHaveCodeNne(final Boolean doNotHaveCodeNne) {
		this.doNotHaveCodeNne = doNotHaveCodeNne;
	}

	public Pays getPays() {
		return pays;
	}

	public void setPays(final Pays pays) {
		this.pays = pays;
	}

	public Departement getDepartement() {
		return departement;
	}

	public void setDepartement(final Departement departement) {
		this.departement = departement;
	}

	public Array<IndVoeuPojo> getIndVoeuxPojo() {
		return indVoeuxPojo;
	}

	public List<IndVoeuPojo> getIndVoeuxPojoAsList() {
		return asList(indVoeuxPojo.array(IndVoeuPojo[].class));
	}

	public void setIndVoeuxPojo(final Array<IndVoeuPojo> indVoeuxPojo) {
		this.indVoeuxPojo = indVoeuxPojo;
	}

	public EtatIndividu getEtat() {
		return etat;
	}

	public void setEtat(final EtatIndividu etat) {
		this.etat = etat;
	}

	public Boolean getIsManager() {
		return isManager;
	}

	public void setIsManager(final Boolean isManager) {
		this.isManager = isManager;
	}

	public void setIsUpdaterOfThisStudent(final Boolean isUpdaterOfThisStudent) {
		this.isUpdaterOfThisStudent = isUpdaterOfThisStudent;
	}

	public Boolean getIsUpdaterOfThisStudent() {
		return isUpdaterOfThisStudent;
	}

	public Date getDateCreationDossier() {
		return dateCreationDossier;
	}

	public void setDateCreationDossier(final Date dateCreationDossier) {
		this.dateCreationDossier = dateCreationDossier;
	}

	public List<IndCursusScolPojo> getIndCursusScolPojo() {
		return indCursusScolPojo;
	}

	public void setIndCursusScolPojo(final List<IndCursusScolPojo> indCursusScolPojo) {
		this.indCursusScolPojo = indCursusScolPojo;
	}

	public Pays getNationalite() {
		return nationalite;
	}

	public void setNationalite(final Pays nationalite) {
		this.nationalite = nationalite;
	}

	public Boolean getIsUsingLC() {
		return isUsingLC;
	}

	public void setIsUsingLC(final Boolean isUsingLC) {
		this.isUsingLC = isUsingLC;
	}

	public Boolean getIsUsingDEF() {
		return isUsingDEF;
	}

	public void setIsUsingDEF(final Boolean isUsingDEF) {
		this.isUsingDEF = isUsingDEF;
	}

	public RegimeInscription getRegimeInscription() {
		return regimeInscription;
	}

	public void setRegimeInscription(final RegimeInscription regimeInscription) {
		this.regimeInscription = regimeInscription;
	}

	public Boolean getIsUsingSearch() {
		return isUsingSearch;
	}

	public void setIsUsingSearch(final Boolean isUsingSearch) {
		this.isUsingSearch = isUsingSearch;
	}
}

