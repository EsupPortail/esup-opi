package org.esupportail.opi.web.controllers.opinions;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.faces.event.ValueChangeEvent;

import org.esupportail.commons.exceptions.ConfigException;
import org.esupportail.commons.services.smtp.SmtpService;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.etat.EtatNull;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.InscriptionAdm;
import org.esupportail.opi.domain.beans.parameters.Transfert;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.parameters.ValidationAcquis;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.paginator.IndividuPojoPaginator;
import org.esupportail.opi.web.beans.pojo.AdressePojo;
import org.esupportail.opi.web.beans.pojo.CommissionPojo;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.utils.NavigationRulesConst;
import org.esupportail.opi.web.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;

/**
 * @author tducreux
 * TypeTraitController : 
 */
public class TypeTraitController  extends AbstractContextAwareController  {


	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * the serialization id.
	 */
	private static final long serialVersionUID = 5545836516397172544L;

	/**
	 * see {@link IndividuPojoPaginator}.
	 */
	private IndividuPojoPaginator individuPaginator;

	/**
	 * Select all the type treatment if the type. 
	 */
	private String codeTypeTrtselected;

	/**
	 * type Traitement ET (Bean).
	 */
	private TypeTraitement typeTraitementVA;

	/**
	 * type Traitement ET (Bean).
	 */
	private TypeTraitement typeTraitementTR;

	/**
	 * type Traitement ET (Bean).
	 */
	private TypeTraitement typeTraitementET;

	/**
	 * see {@link InscriptionAdm}.
	 */
	private InscriptionAdm inscriptionAdm;
	
	/**
	 * Type treatment VA and TR.
	 */
	private List<TypeTraitement> typeTraitements;
	
	/**
	 * {@link SmtpService}.
	 */
	private SmtpService smtpService;
	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public TypeTraitController() {
		super();

	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		individuPaginator.reset();
		codeTypeTrtselected = null;
	}

	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractContextAwareController#afterPropertiesSetInternal()
	 */
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
		Assert.notNull(this.individuPaginator, 
				"property individuPaginator of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.smtpService, 
				"property smtpService of class " + this.getClass().getName() 
				+ " can not be null");
		Assert.notNull(this.inscriptionAdm, 
				"property inscriptionAdm of class " + this.getClass().getName() 
				+ " can not be null");
		reset();
	}

	/*
	 ******************* CALLBACK ********************** */

	/**
	 * Callback to list of student for the gestion of the type traitement.
	 * @return String 
	 */
	public String goSeeAllTypeTraitments() {
		reset();
		individuPaginator.setUseIndividuPojo(true);
		individuPaginator.filtreAllCommissionRights(
		    getDomainApoService().getListCommissionsByRight(
		        getCurrentGest(), true), true, null);
		individuPaginator.forceReload();
		return NavigationRulesConst.DISPLAY_TYPE_TRAITEMENT;
	}


	/*
	 ******************* METHODS ********************** */


	/**
	 * Filter individu paginator 
	 * form update type treatment in wish.
	 */
	public void filterPaginator() {
		//init the filtre
		individuPaginator.setUseIndividuPojo(true);
		individuPaginator.filterInMannagedCmi(
		    getDomainApoService().getListCommissionsByRight(
						getCurrentGest(),	true),	null, true);
		individuPaginator.forceReload();
	}

	/**
	 * @param i
	 * @param indVoeuPojo 
	 * @param typeT 
	 */
	private void sendMails(final IndividuPojo i, final Campagne camp,
			final Set <IndVoeuPojo> indVoeuPojo,
			final TypeTraitement typeT) {

		String htmlBody = null;	
		String htmlCoordonnees = null;
		String corresponding = null;

		String htmlDebut = getString("MAIL.CANDIDAT_VOEU.HTMLTEXT_DEBUT", 
				Utilitaires.getCivilite(getI18nService(),
						i.getIndividu().getSexe()));

		// traitement des TR eventuels

		htmlBody = "";
		Map<Commission, Set<VersionEtapeDTO>> mapCmi = 
			Utilitaires.getCmiForIndVoeux(getParameterService().getCommissions(true),
					indVoeuPojo, camp);
		Integer codeRI = camp.getCodeRI();
		for (Map.Entry<Commission, Set<VersionEtapeDTO>> cmiEntry : mapCmi.entrySet()) {
			Commission cmi = cmiEntry.getKey();
			CommissionPojo cmiPojo = new CommissionPojo(
					cmi, 
					new AdressePojo(cmi.getContactsCommission()
							.get(codeRI)
							.getAdresse(), getDomainApoService()),
					cmi.getContactsCommission().get(codeRI));
			Set<VersionEtapeDTO> vetDTO = cmiEntry.getValue();
			StringBuffer html = new StringBuffer();
			for (VersionEtapeDTO vDTO : vetDTO) {
				html.append(getString("MAIL.LIST_VET", vDTO.getLibWebVet()));

				htmlCoordonnees = Utilitaires.getAdrCmiForSendMail(getI18nService(), cmiPojo, null);
				corresponding = cmiPojo.getContactCommission().getCorresponding();
			}
			htmlBody += html.toString();
			if (typeT instanceof Transfert) {
				htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY1");
				htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY2",
						i.getCampagneEnServ(getDomainService()).getCodAnu());
				// coordonnees commission
				htmlBody += htmlCoordonnees;
				htmlBody += getString("MAIL.CANDIDAT_VOEU_TR.HTMLTEXT_BODY3");
			} else if (typeT instanceof ValidationAcquis) {
				String commDateEnd = cmi.getCalendarCmi().getCommDatEndBack();
				if (commDateEnd == null) {
					commDateEnd = "";
				}
				//TODO test si calendar null
				htmlBody += getString("MAIL.CANDIDAT_VOEU_VA.HTMLTEXT_BODY1",
						Utilitaires.convertDateToString(
								cmi.getCalendarCmi().getDatEndBackDossier(), Constantes.DATE_FORMAT),
								commDateEnd);
				// coordonnees commission
				htmlBody += htmlCoordonnees;
				htmlBody += getString("MAIL.CANDIDAT_VOEU_VA.HTMLTEXT_BODY2");
				htmlBody += htmlCoordonnees;
			}	

			// responsable scolarite
			htmlBody += corresponding;

			Utilitaires.sendEmailIndividu(
					getString("MAIL.CANDIDAT_VOEU_TR.SUBJECT"),
					htmlDebut + htmlBody, null,
					i.getIndividu(),
					smtpService, getI18nService());
		}

	} 


	/**
	 * update.
	 * Put at true the flag haveBeTraited;
	 */
	public void update() {
		Set <IndVoeuPojo> lesVA = new HashSet<IndVoeuPojo>();
		Set <IndVoeuPojo> lesTR = new HashSet<IndVoeuPojo>();
		List <IndividuPojo> lesIndividus = this.individuPaginator.getIndividuPojos();
		for (IndividuPojo i : lesIndividus) {
			Set <IndVoeuPojo> lesVoeux = i.getIndVoeuxPojo();
			lesVA.clear();
			lesTR.clear();
			Campagne camp = i.getCampagneEnServ(getDomainService());
			for (IndVoeuPojo v : lesVoeux) {
				if (!v.getIndVoeu().getCodTypeTrait().
						equals(this.typeTraitementET.getCode())) {

					// voeu non traite qui a ete modifie
					if (v.getIndVoeu().getCodTypeTrait().
							equals(this.typeTraitementVA.getCode())) {
						lesVA.add(v);
					} else if (v.getIndVoeu().getCodTypeTrait().
							equals(this.typeTraitementTR.getCode())) {
						lesTR.add(v);
						// ajout un avis favorable
						Avis avis = new Avis();
						avis.setIndVoeu(v.getIndVoeu());
						avis.setValidation(true);
						//recherche du type Decision avec le typeConvoc inscriptionAdm
						for (TypeDecision t : getParameterService().getTypeDecisions(true)) {
							if (t.getCodeTypeConvocation()
									.equals(inscriptionAdm.getCode())) {
								avis.setResult(t);
							}
						}
						if (avis.getResult() != null) {
							Avis av = (Avis) getDomainService().add(
									avis, getCurrentGest().getLogin());
							
							getDomainService().addAvis(av);
						} else {
							throw new ConfigException(
								"il n'existe pas de typeDecision amenant"
								+ " e l'inscription administrative --> avis Favorable");
						}
						//update state in stateNull
						v.getIndVoeu().setState(EtatNull.I18N_STATE);
						//----Lignes ajoutées suite problème Mysql ----------------------//
                        if (v.getIndVoeu().getAvis() == null) {
                               v.getIndVoeu().setAvis(new HashSet<Avis>());
                        }
                        v.getIndVoeu().getAvis().add(avis);
                        //----------------------------------------------------//
					}

					v.getIndVoeu().setHaveBeTraited(true);
					
				} 
				//on met tous les temps le voeu e jour
				getDomainService().updateIndVoeu(v.getIndVoeu());
			}
			if (!lesVA.isEmpty()) {
				sendMails(i, camp, lesVA, typeTraitementVA);
			}
			if (!lesTR.isEmpty()) {
				sendMails(i, camp, lesTR, typeTraitementTR);
			}
		}

		int p = this.individuPaginator.getCurrentPage();
		int s = this.individuPaginator.getPageSize();
		individuPaginator.resetNotSuper(true);
		filterPaginator();
		individuPaginator.setPageSize(s);
		individuPaginator.setCurrentPage(p);
		this.individuPaginator.forceReload();

	}


	/**
	 * The selected type treatment.
	 * @param event
	 */
	public void selectTypTrt(final ValueChangeEvent event) {
		String idTypTrt = (String) event.getNewValue();
		codeTypeTrtselected = idTypTrt;
		selectAllTypeAction();
	}


	/**
	 * Select all the type action select for the visible elements.
	 */
	public void selectAllTypeAction() {
		List <IndividuPojo> lesIndividus = this.individuPaginator.getIndividuPojos();
		for (IndividuPojo i : lesIndividus) {
			Set <IndVoeuPojo> lesVoeux = i.getIndVoeuxPojo();
			for (IndVoeuPojo v : lesVoeux) {
				v.getIndVoeu().setCodTypeTrait(codeTypeTrtselected);
			}
		}
	}

	/*
	 ******************* ACCESSORS ******************** */



	/**
	 * @param individuPaginator the individuPaginator to set
	 */
	public void setIndividuPaginator(final IndividuPojoPaginator individuPaginator) {
		this.individuPaginator = individuPaginator;
	}

	/**
	 * @return the individuPaginator
	 */
	public IndividuPojoPaginator getIndividuPaginator() {
		return individuPaginator;
	}


	/**
	 * @return the codeTypeTrtselected
	 */
	public String getCodeTypeTrtselected() {
		return codeTypeTrtselected;
	}

	/**
	 * @param codeTypeTrtselected the codeTypeTrtselected to set
	 */
	public void setCodeTypeTrtselected(final String codeTypeTrtselected) {
		this.codeTypeTrtselected = codeTypeTrtselected;
	}

	/**
	 * @param typeTraitementVA the typeTraitementVA to set
	 */
	public void setTypeTraitementVA(final TypeTraitement typeTraitementVA) {
		this.typeTraitementVA = typeTraitementVA;
	}

	/**
	 * @param typeTraitementTR the typeTraitementTR to set
	 */
	public void setTypeTraitementTR(final TypeTraitement typeTraitementTR) {
		this.typeTraitementTR = typeTraitementTR;
	}

	/**
	 * @param typeTraitemenET the typeTraitemenET to set
	 */
	public void setTypeTraitementET(final TypeTraitement typeTraitementET) {
		this.typeTraitementET = typeTraitementET;
	}
	/**
	 * @param smtpService the smtpService to set
	 */
	public void setSmtpService(final SmtpService smtpService) {
		this.smtpService = smtpService;
	}

	/**
	 * @return the typeTraitement
	 */
	public List<TypeTraitement> getTypeTraitements() {
		return typeTraitements;
	}

	/**
	 * @param typeTraitement the typeTraitement to set
	 */
	public void setTypeTraitements(final List<TypeTraitement> typeTraitement) {
		this.typeTraitements = typeTraitement;
	}

	/**
	 * @param inscriptionAdm the inscriptionAdm to set
	 */
	public void setInscriptionAdm(final InscriptionAdm inscriptionAdm) {
		this.inscriptionAdm = inscriptionAdm;
	}

}
