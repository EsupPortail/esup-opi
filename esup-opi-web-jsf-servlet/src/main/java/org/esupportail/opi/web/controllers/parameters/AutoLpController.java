/**
 * 
 */
package org.esupportail.opi.web.controllers.parameters;



import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.faces.model.SelectItem;

import org.apache.commons.beanutils.BeanComparator;
import org.apache.commons.collections.comparators.NullComparator;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.BusinessUtil;
import org.esupportail.opi.domain.beans.parameters.AutoListPrincipale;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.InscriptionAdm;
import org.esupportail.opi.domain.beans.parameters.ListeComplementaire;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.VetAutoLp;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.pojo.AutoListPrincipalePojo;
import org.esupportail.opi.web.beans.pojo.CommissionPojo;
import org.esupportail.opi.web.beans.pojo.VetAutoLpPojo;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;



/**
 * @author Gomez
 *
 */
public class AutoLpController extends AbstractContextAwareController {
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5068742639342019656L;
	
	/**
	 * 
	 */
	private static final String FORMULAIRE_ADD_AUTO_LP = "seeAutoLpForm";
	
	/**
	 * 
	 */
	private static final String ERROR_FIELD_EMPTY = "ERROR.FIELD.EMPTY";
	
	/**
	 * 
	 */
	private static final String LABEL = "label";
	
	/**
	 * 
	 */
	private static final String VALUE = "value";
	
	/**
	 * CGE.
	 */
	private static final String CGE = "cge";
	
	/**
	 * Commission.
	 */
	private static final String COMMISSION = "commission";
	
	/**
	 * Commission.
	 */
	private static final String VET = "vet";
	
	/**
	 * Tableau des choix.
	 */
	private static final String[] LESCHOIX = {VET, COMMISSION, CGE};
	
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The selected list Auto.
	 */
	private AutoListPrincipalePojo autoListPrincipale;
	
	/**
	 * The list Auto LP.
	 */
	private List<AutoListPrincipalePojo> listAutoLp;
	
	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;
	
	/**
	 * The actionListLc.
	 */
	private ActionEnum actionListLc;
	
	/**
	 * Liste des codes CGE.
	 */
	private List<SelectItem> allCge;
	
	/**
	 * allCommItems.
	 */
	private List<SelectItem> allCommItems;
	/**
	 * CommItems.
	 */
	private List<SelectItem> commItems;
	/**
	 * selectCommADI.
	 */
	private List<String> selectCommADI;
	/**
	 * selectCommDI.
	 */
	private List<String> selectCommDI;
	
	/**
	 * allVetItems.
	 */
	private List<SelectItem> allVetItems;
	/**
	 * vetItems.
	 */
	private List<SelectItem> vetItems;
	/**
	 * selectVetADI.
	 */
	private List<String> selectVetADI;
	/**
	 * selectVetDI.
	 */
	private List<String> selectVetDI;
	
	/**
	 * liste des choix (Vet, Commission ou CGE).
	 */
	private List<SelectItem> allChoix;
	
	/**
	 * choix entre la liste de Commission ou une CGE.
	 */
	private String choix;
	
	/**
	 * Code CGE selectionne.
	 */
	private String codeCge;
	
	/**
	 * allTypeDecisionIA.
	 */
	private List<SelectItem> allTypeDecisionIA;
	/**
	 * typeDecisionDeLP.
	 */
	private String typeDecisionDeLP;

	/**
	 * allTypeDecLcItems.
	 */
	private List<SelectItem> allTypeDecLcItems;
	/**
	 * TypeDecItems.
	 */
	private List<SelectItem> typeDecItems;
	/**
	 * selectTypeDecDI.
	 */
	private List<String> selectTypeDecDI;
	/**
	 * selectTypeDecADI.
	 */
	private List<String> selectTypeDecADI;
	/**
	 * typeDecisionDeLP.
	 */
	private String selectTypeDecPosition;
	
	/**
	 * see {@link InscriptionAdm}.
	 */
	private InscriptionAdm inscriptionAdm;
	
	/**
	 * see {@link InscriptionAdm}.
	 */
	private ListeComplementaire listeComplementaire;
	
	/**
	 * 
	 */
	private Set<CentreGestion> listAllCge;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public AutoLpController() {
		super();
		listAutoLp = new ArrayList<AutoListPrincipalePojo>();
		allCge = new ArrayList<SelectItem>();
		
		choix = VET;
		
		allCommItems = new ArrayList<SelectItem>();
		selectCommADI = new ArrayList<String>();
		commItems = new ArrayList<SelectItem>();
		selectCommDI = new ArrayList<String>();
		
		allVetItems = new ArrayList<SelectItem>();
		selectVetADI = new ArrayList<String>();
		vetItems = new ArrayList<SelectItem>();
		selectVetDI = new ArrayList<String>();
		
		allTypeDecisionIA = new ArrayList<SelectItem>();
		
		allTypeDecLcItems = new ArrayList<SelectItem>();
		selectTypeDecADI = new ArrayList<String>();
		typeDecItems = new ArrayList<SelectItem>();
		selectTypeDecDI = new ArrayList<String>();
		
		listAllCge = new HashSet<CentreGestion>();
	}
	
	/** 
	 * @see org.esupportail.opi.web.controllers.AbstractDomainAwareBean#reset()
	 */
	@Override
	public void reset() {
		super.reset();
		autoListPrincipale = null;
		listAutoLp.clear();
		actionEnum = new ActionEnum();
		actionListLc = new ActionEnum();
		allCge.clear();
		codeCge = null;
		
		choix = VET;
		
		allCommItems.clear();
		selectCommADI.clear();
		commItems.clear();
		selectCommDI.clear();
		
		allVetItems.clear();
		selectVetADI.clear();
		vetItems.clear();
		selectVetDI.clear();
		
		allTypeDecisionIA.clear();
		
		allTypeDecLcItems.clear();
		selectTypeDecADI.clear();
		typeDecItems.clear();
		selectTypeDecDI.clear();
		
		listAllCge.clear();
	}
	
	
	
	/*
	 ******************* CALLBACK ********************** */
	/**
	 * Callback to treatment type list.
	 * @return String 
	 */
	public String goSeeAllAutoLp() {
		reset();
		return NavigationRulesConst.MANAGED_AUTO_LP;
	}
	
	/**
	 * Callback to treatment type list.
	 * @return String 
	 */
	public String goAddAutoLp() {
		reset();
		getActionEnum().setWhatAction(ActionEnum.ADD_ACTION);
		getActionListLc().setWhatAction(ActionEnum.EMPTY_ACTION);
		return null;
	}
	
	/**
	 * Callback to treatment type list.
	 * @return String 
	 */
	public String goSeeListLc() {
		getActionListLc().setWhatAction(ActionEnum.EMPTY_ACTION);
		return null;
	}
	
	/**
	 * Callback to treatment type list.
	 * @return String 
	 */
	public String goAddListLc() {
		getActionListLc().setWhatAction(ActionEnum.ADD_ACTION);
		return null;
	}
	
	
	/*
	 * ******************* METHODE ************************* */
	/**
	 * @return boolean
	 */
	public boolean getIsOnTypeDecLp() {
		boolean test;
		
		if (getTypeDecisionDeLP() == null && getAllTypeDecisionIA().size() == 1) {
			typeDecisionDeLP = (String) getAllTypeDecisionIA().get(0).getValue();
			test = true;
		} else if (getTypeDecisionDeLP() != null && getAllTypeDecisionIA().size() == 1
				&& getTypeDecisionDeLP().equals((String) getAllTypeDecisionIA().get(0).getValue())) {
			test = true;
		} else if (getTypeDecisionDeLP() != null && getAllTypeDecisionIA().size() == 0) {
			test = true;
		} else {
			test = false;
		}
		
		return test;
	}
	
	/**
	 * @return boolean
	 */
	public boolean getIsOnTypeDecLc() {
		boolean test;
		
		if (getTypeDecItems().isEmpty() && getAllTypeDecLcItems().size() == 1) {
			getTypeDecItems().add(getAllTypeDecLcItems().get(0));
			test = true;
		} else if (!getTypeDecItems().isEmpty() && getTypeDecItems().size() == 1
				&& getAllTypeDecLcItems().size() == 1
				&& ((String) getTypeDecItems().get(0).getValue()).
					equals((String) getAllTypeDecLcItems().get(0).getValue())) {
			test = true;
		} else if (!getTypeDecItems().isEmpty() && getTypeDecItems().size() == 1
				&& getAllTypeDecLcItems().size() == 0) {
			test = true;
		} else {
			test = false;
		}
		
		return test;
	}
	
	/**
	 * @return libelle 
	 */
	public String getSelectTypeDeLpILibelle() {
		if (!getAllTypeDecisionIA().isEmpty()) {
			return getAllTypeDecisionIA().get(0).getLabel();
		} else if (autoListPrincipale != null) {
			return autoListPrincipale.getAutoLp().getTypeDecisionDeLP().getLibelle();
		}
		return "";
	}
	
	public String getSelectTypeDeLcILibelle() {
		if (!getAllTypeDecLcItems().isEmpty()) {
			return getAllTypeDecLcItems().get(0).getLabel();
		} else if (autoListPrincipale != null 
				&& autoListPrincipale.getAutoLp().getListTypeOfDecision().size() == 1) {
			return autoListPrincipale.getAutoLp().getListTypeOfDecision().get(0).getLibelle();
		}
		return "";
	}
	
	/*
	 * ******************* ADD ET UPDATE ************************* */
	/**
	 * Add a AutoListPrincipale to the dataBase.
	 */
	@SuppressWarnings("unchecked")
	public void add() {
		if (testErreurSave()) {
			return;
		}
		
		if (log.isDebugEnabled()) {
			log.debug("entering add with autoLp = " + autoListPrincipale.getAutoLp());
		}
		
		if (isChoixCge()) {
			getAutoListPrincipale().getAutoLp().setCodeCge(codeCge);
			for (CentreGestion c : getListAllCge()) {
				if (c.getCodCge().equals(codeCge)) {
					getAutoListPrincipale().setLibelleCge(c.getLibCge());
					break;
				}
			}
			
		} else if (isChoixCommission()) {
			for (SelectItem comm : commItems) {
				getAutoListPrincipale().getAutoLp().getListCommission()
					.add(recupCommission((String) comm.getValue()));
			}
		}
		
		getAutoListPrincipale().getAutoLp().setTypeDecisionDeLP((TypeDecision) 
				getParameterService().getNomenclature(Integer.parseInt(typeDecisionDeLP)));
		
		for (SelectItem typeDec : typeDecItems) {
			getAutoListPrincipale().getAutoLp().getListTypeOfDecision().add(
					(TypeDecision) getParameterService().getNomenclature(
							Integer.parseInt((String) typeDec.getValue())));
		}
		
		//save
		getAutoListPrincipale().setRight(true);
		getAutoListPrincipale().setAutoLp((AutoListPrincipale) getDomainService().add(
				getAutoListPrincipale().getAutoLp(), getCurrentGest().getLogin()));
		getListAutoLp().add(getAutoListPrincipale());
		getDomainService().addAutoListPrincipale(getAutoListPrincipale().getAutoLp());
		
		if (isChoixVet()) {
			for (SelectItem vet : vetItems) {
				VetAutoLp vetCal = new VetAutoLp(
						(String) vet.getValue(),
						Integer.parseInt(vet.getDescription()),
						getAutoListPrincipale().getAutoLp(),
						getCommissionVet((String) vet.getValue(),
								Integer.parseInt(vet.getDescription())));
				
				getDomainService().addVetAutoLp(vetCal);
				getAutoListPrincipale().getAutoLp().getListVet().add(vetCal);
				//ajout d'une vetAutoPojo
				getAutoListPrincipale().getListVetAutoLpPojo().add(
						new VetAutoLpPojo(vetCal, getBusinessCacheService().
								getVersionEtape(vetCal.getCodEtp(), 
										vetCal.getCodVrsVet()).getLibWebVet()));
			}
		}
		
		Collections.sort(getListAutoLp(), new BeanComparator("autoLp.libelle", new NullComparator()));
		reset();
		
		if (log.isDebugEnabled()) {
			log.debug("leaving add");
		}
	}
	
	/**
	 * Update a Domain to the dataBase.
	 */
	public void update() {
		if (testErreurUpdate()) {
			return;
		}
		
		if (log.isDebugEnabled()) {
			log.debug("enterind update with autoLp = " + autoListPrincipale);
		}
		
		if (isChoixCge()) {
			getAutoListPrincipale().getAutoLp().setCodeCge(codeCge);
			for (CentreGestion c : getListAllCge()) {
				if (c.getCodCge().equals(codeCge)) {
					getAutoListPrincipale().setLibelleCge(c.getLibCge());
					break;
				}
			}
			getAutoListPrincipale().getAutoLp().getListCommission().clear();
			getAutoListPrincipale().getAutoLp().getListVet().clear();
			
		} else if (isChoixCommission()) {
			int index;
			List<Commission> listComm = new ArrayList<Commission>();
			
			for (Commission comm : getAutoListPrincipale().getAutoLp().getListCommission()) {
				index = getExistCommItems(comm.getCode());
				if (index == -1) {
					//recuperation des commissions ne se trouvant pas dans la liste des items
					listComm.add(comm);
				} else {
					//suppression des commissions se trouvant dans la liste des items
					commItems.remove(index);
				}
			}
			
			for (Commission comm : listComm) {
				//suppression des commissions
				getAutoListPrincipale().getAutoLp().getListCommission().remove(comm);
			}
			
			for (SelectItem comm : commItems) {
				//ajout des commissions
				getAutoListPrincipale().getAutoLp().getListCommission()
					.add(recupCommission((String) comm.getValue()));
			}

			getAutoListPrincipale().getAutoLp().setCodeCge(null);
			getAutoListPrincipale().getAutoLp().getListVet().clear();
			
		} else {
			int index;
			List<VetAutoLpPojo> listVets = new ArrayList<VetAutoLpPojo>();
			
			for (VetAutoLpPojo vet : getAutoListPrincipale().getListVetAutoLpPojo()) {
				index = getExistVetItems(vet.getVetAutoLp().getCodEtp(),
						vet.getVetAutoLp().getCodVrsVet());
				if (index == -1) {
					//recuperation des vets ne se trouvant pas dans la liste des items
					listVets.add(vet);
				} else {
					//suppression des vets se trouvant dans la liste des items
					vetItems.remove(index);
				}
			}
			
			for (VetAutoLpPojo vet : listVets) {
				//suppression des vets
				getDomainService().deleteVetAutoLp(vet.getVetAutoLp());
				getAutoListPrincipale().getAutoLp().getListVet().remove(vet.getVetAutoLp());
				//suppression des vetAutoPojo
				getAutoListPrincipale().getListVetAutoLpPojo().remove(vet);
			}
			
			getAutoListPrincipale().getAutoLp().setCodeCge(null);
			getAutoListPrincipale().getAutoLp().getListCommission().clear();
		}
		
		//Types de decisions LP
		if (!typeDecisionDeLP.equals(getAutoListPrincipale().getAutoLp()
				.getTypeDecisionDeLP().getId())) {
			getAutoListPrincipale().getAutoLp().setTypeDecisionDeLP((TypeDecision) 
				getParameterService().getNomenclature(Integer.parseInt(typeDecisionDeLP)));
		}
		
		//Liste de Types de decisions LC
		int index;
		List<TypeDecision> listTypeDec = new ArrayList<TypeDecision>();
		
		for (TypeDecision typeDec : getAutoListPrincipale().getAutoLp().getListTypeOfDecision()) {
			index = getExistTypeDecItems(typeDec.getId());
			if (index == -1) {
				//recuperation des types de decisions ne se trouvant pas dans la liste des items
				listTypeDec.add(typeDec);
			} else {
				//suppression des types de decisions se trouvant dans la liste des items
				typeDecItems.remove(index);
			}
		}
		
		for (TypeDecision typeDec : listTypeDec) {
			//suppression des types de decisions
			getAutoListPrincipale().getAutoLp().getListTypeOfDecision().remove(typeDec);
		}
		
		for (SelectItem typeDec : typeDecItems) {
			//ajout des types de decisions
			getAutoListPrincipale().getAutoLp().getListTypeOfDecision().add(
					(TypeDecision) getParameterService().getNomenclature(
							Integer.parseInt((String) typeDec.getValue())));
		}
		
		//update
		getAutoListPrincipale().setRight(true);
		getAutoListPrincipale().setAutoLp((AutoListPrincipale) getDomainService().update(
				getAutoListPrincipale().getAutoLp(), getCurrentGest().getLogin()));
		getDomainService().updateAutoListPrincipale(getAutoListPrincipale().getAutoLp());
		
		if (isChoixVet()) {
			for (SelectItem vet : vetItems) {
				//ajout des vets
				VetAutoLp vetCal = new VetAutoLp(
						(String) vet.getValue(),
						Integer.parseInt(vet.getDescription()),
						getAutoListPrincipale().getAutoLp(),
						getCommissionVet((String) vet.getValue(),
								Integer.parseInt(vet.getDescription())));
				
				getDomainService().addVetAutoLp(vetCal);
				getAutoListPrincipale().getAutoLp().getListVet().add(vetCal);
				//ajout d'une vetAutoPojo
				getAutoListPrincipale().getListVetAutoLpPojo().add(
						new VetAutoLpPojo(vetCal, getBusinessCacheService().
								getVersionEtape(vetCal.getCodEtp(), 
										vetCal.getCodVrsVet()).getLibWebVet()));
			}
		}
		
		reset();
		
		if (log.isDebugEnabled()) {
			log.debug("leaving update");
		}
	}
	
	
	/**
	 * Delete a Nomenclature to the dataBase.
	 */
	public void delete() {
		if (log.isDebugEnabled()) {
			log.debug("enterind delete with autoLp = " + autoListPrincipale);
		}
		
		getListAutoLp().remove(getAutoListPrincipale());
		getDomainService().deleteAutoListPrincipale(getAutoListPrincipale().getAutoLp());
		reset();
		
		if (log.isDebugEnabled()) {
			log.debug("leaving delete");
		}
	}
	
	
	
	/*
	 * ******************* TEST ************************* */
	/**
	 * 
	 * @return boolean
	 */
	private boolean testErreurSave() {
		if (getAutoListPrincipale().getAutoLp().getLibelle() == null 
				|| getAutoListPrincipale().getAutoLp().getLibelle().equals("")) {
			addErrorMessage(FORMULAIRE_ADD_AUTO_LP, ERROR_FIELD_EMPTY, "Libellé");
			return true;
		}
		
		if (testExistAutoListPrincipale()) {
			addErrorMessage(FORMULAIRE_ADD_AUTO_LP, "ERROR.FIELD.EXISTE", 
					"Liste complémentaire automatique", "Libellé");
			return true;
		}
		
		return testErreurUpdate();
	}
	
	/**
	 * 
	 * @return boolean
	 */
	private boolean testErreurUpdate() {
		if (isChoixCommission() && (commItems == null || commItems.isEmpty())) {
			addErrorMessage(FORMULAIRE_ADD_AUTO_LP, "ERROR.LIST.EMPTY", "Liste des commissions");
			return true;
		}
		
		if (isChoixVet() && (vetItems == null || vetItems.isEmpty())) {
			addErrorMessage(FORMULAIRE_ADD_AUTO_LP, "ERROR.LIST.EMPTY", "Liste des vets");
			return true;
		}
		
		if (typeDecisionDeLP == null || typeDecisionDeLP.isEmpty()) {
			addErrorMessage(FORMULAIRE_ADD_AUTO_LP, "ERROR.FIELD.EMPTY", "type de decision");
			return true;
		}
		
		if (typeDecItems == null || typeDecItems.isEmpty()) {
			addErrorMessage(FORMULAIRE_ADD_AUTO_LP, "ERROR.LIST.EMPTY", "Liste des types de decision");
			return true;
		}
		
		for (SelectItem itemTypeDec : typeDecItems) {
			if (typeDecisionDeLP.equals((String) itemTypeDec.getValue())) {
				addErrorMessage(FORMULAIRE_ADD_AUTO_LP, "ERROR.TYP_DEC.EQUALS", 
						"type de decision", "Type de décisions pour la liste principale",
						"Types de décisions pour la liste complémentaire");
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	private boolean testExistAutoListPrincipale() {
		String libelle = getAutoListPrincipale().getAutoLp().getLibelle();
		for (AutoListPrincipalePojo autoLp : getListAutoLp()) {
			if (libelle.equals(autoLp.getAutoLp().getLibelle())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param cgeCode
	 * @return boolean
	 */
	public boolean testExistCge(final String cgeCode) {
		for (AutoListPrincipalePojo autoLp : getListAutoLp()) {
			if (cgeCode.equals(autoLp.getAutoLp().getCodeCge())
					&& !autoLp.getAutoLp().getId().equals(autoListPrincipale.getAutoLp().getId())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param codeCommission
	 * @return boolean
	 */
	public boolean testExistCommItems(final String codeCommission) {
		for (AutoListPrincipalePojo autoLp : getListAutoLp()) {
			for (Commission comm : autoLp.getAutoLp().getListCommission()) {
				if (comm.getCode().equals(codeCommission)) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @param codEtp
	 * @param codVrsVet 
	 * @return boolean
	 */
	public boolean testExistVetItems(final String codEtp, final int codVrsVet) {
		for (AutoListPrincipalePojo autoLp : getListAutoLp()) {
			for (VetAutoLpPojo vet : autoLp.getListVetAutoLpPojo()) {
				if (vet.getVetAutoLp().getCodEtp().equals(codEtp)
						&& vet.getVetAutoLp().getCodVrsVet() == codVrsVet) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isChoixCge() {
		return choix.equals(CGE);
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isChoixCommission() {
		return choix.equals(COMMISSION);
	}
	
	/**
	 * 
	 * @return boolean
	 */
	public boolean isChoixVet() {
		return choix.equals(VET);
	}
	
	/**
	 * 
	 * @param codeComm
	 * @return int
	 */
	private int getExistCommItems(final String codeComm) {
		for (int i = 0; i < commItems.size(); i++) {
			if (codeComm.equals(commItems.get(i).getValue())) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 
	 * @param codeEtp
	 * @param codVsrVet 
	 * @return int
	 */
	private int getExistVetItems(final String codeEtp, final int codVsrVet) {
		for (int i = 0; i < vetItems.size(); i++) {
			if (codeEtp.equals(vetItems.get(i).getValue())
					&& codVsrVet == Integer.parseInt(vetItems.get(i).getDescription())) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * 
	 * @param idType
	 * @return int
	 */
	private int getExistTypeDecItems(final int idType) {
		for (int i = 0; i < typeDecItems.size(); i++) {
			if (String.valueOf(idType).equals(typeDecItems.get(i).getValue())) {
				return i;
			}
		}
		
		return -1;
	}
	
	
	
	/*
	 ******************* METHODS COMMISSIONS ********************** */
	/**
	 * 
	 * Ajoute la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void ajouCommItems() {
		int index = -1;
		for (String c : selectCommADI) {
			for (int i = 0; i < allCommItems.size() && index == -1; i++) {
				if (allCommItems.get(i).getValue().equals(c)) {
					index = i;
				}
			}
			if (index >= 0) {
				commItems.add(allCommItems.get(index));
				allCommItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(commItems, new BeanComparator(VALUE, new NullComparator()));
		Collections.sort(allCommItems, new BeanComparator(VALUE, new NullComparator()));
		selectCommADI.clear();
	}
	
	/**
	 * 
	 *  Supprime la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void suppCommItems() {
		int index = -1;
		for (String c : selectCommDI) {
			for (int i = 0; i < commItems.size() && index == -1; i++) {
				if (commItems.get(i).getValue().equals(c)) {
					index = i;
				}
			}
			if (index >= 0) {
				allCommItems.add(commItems.get(index));
				commItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(commItems, new BeanComparator(VALUE, new NullComparator()));
		Collections.sort(allCommItems, new BeanComparator(VALUE, new NullComparator()));
		selectCommDI.clear();
	}
	
	/**
	 * 
	 * @param codeComm
	 * @return Commission
	 */
	private Commission recupCommission(final String codeComm) {
		for (Commission comm : getParameterService().getCommissions(null)) {
			if (comm.getCode().equals(codeComm)) {
				return comm;
			}
		}
		return null;
	}
	
	
	/*
	 ******************* METHODS VETS ********************** */
	/**
	 * 
	 * Ajoute la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void ajouVetItems() {
		int index = -1;
		for (String codeEtp : selectVetADI) {
			for (int i = 0; i < allVetItems.size() && index == -1; i++) {
				if (codeEtp.equals(allVetItems.get(i).getValue())) {
					index = i;
				}
			}
			if (index >= 0) {
				vetItems.add(allVetItems.get(index));
				allVetItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(vetItems, new BeanComparator(LABEL, new NullComparator()));
		Collections.sort(allVetItems, new BeanComparator(LABEL, new NullComparator()));
		selectVetADI.clear();
	}
	
	/**
	 * 
	 *  Supprime la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void suppVetItems() {
		int index = -1;
		for (String codeEtp : selectVetDI) {
			for (int i = 0; i < vetItems.size() && index == -1; i++) {
				if (codeEtp.equals(vetItems.get(i).getValue())) {
					index = i;
				}
			}
			if (index >= 0) {
				allVetItems.add(vetItems.get(index));
				vetItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(vetItems, new BeanComparator(LABEL, new NullComparator()));
		Collections.sort(allVetItems, new BeanComparator(LABEL, new NullComparator()));
		selectVetDI.clear();
	}
	
	/**
	 * 
	 * @param codEtp
	 * @param codVsrVet 
	 * @return Commission
	 */
	public Commission getCommissionVet(final String codEtp, final int codVsrVet) {
		for (Commission comm : getParameterService().getCommissions(null)) {
			for (TraitementCmi traitementCmi : comm.getTraitementCmi()) {
				if (traitementCmi.getVersionEtpOpi().getCodEtp().equals(codEtp)
						&& traitementCmi.getVersionEtpOpi().getCodVrsVet() == codVsrVet) {
					return comm;
				}
			}
		}
		return null;
	}
	
	
	
	/*
	 ******************* METHODS TYPES DE DECISIONS ********************** */
	/**
	 * 
	 * Ajoute la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void ajouTypeDecItems() {
		int index = -1;
		for (String idType : selectTypeDecADI) {
			for (int i = 0; i < allTypeDecLcItems.size() && index == -1; i++) {
				if (idType.equals(allTypeDecLcItems.get(i).getValue())) {
					index = i;
				}
			}
			if (index >= 0) {
				typeDecItems.add(allTypeDecLcItems.get(index));
				allTypeDecLcItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(allTypeDecLcItems, new BeanComparator("label", new NullComparator()));
		selectTypeDecADI.clear();
	}
	/**
	 * 
	 *  Supprime la selection dans DipsItems.
	 */
	@SuppressWarnings("unchecked")
	public void suppTypeDecItems() {
		int index = -1;
		for (String idType : selectTypeDecDI) {
			for (int i = 0; i < typeDecItems.size() && index == -1; i++) {
				if (idType.equals(typeDecItems.get(i).getValue())) {
					index = i;
				}
			}
			if (index >= 0) {
				allTypeDecLcItems.add(typeDecItems.get(index));
				typeDecItems.remove(index);
			}
			index = -1;
		}
		Collections.sort(allTypeDecLcItems, new BeanComparator("label", new NullComparator()));
		selectTypeDecDI.clear();
	}
	
	/**
	 * @return int
	 */
	private int positionSelectTypeDec() {
		int position = 0;
		for (SelectItem typeDec : typeDecItems) {
			if (selectTypeDecPosition.equals(typeDec.getValue())) {
				return position;
			}
			position++;
		}
		return -1;
	}
	
	/**
	 * Mettre en première position.
	 */
	public void enHaut() {
		if (selectTypeDecPosition == null || selectTypeDecPosition.isEmpty() 
				|| typeDecItems == null || typeDecItems.isEmpty()) {
			return;
		}
		
		int position = positionSelectTypeDec();
		if (position == -1 || position == 0) {
			return;
		}
		
		SelectItem item = typeDecItems.get(position);
		typeDecItems.remove(position);
		typeDecItems.add(0, item);
	}
	
	/**
	 * monter d'une position.
	 */
	public void monter() {
		if (selectTypeDecPosition == null || selectTypeDecPosition.isEmpty()
				|| typeDecItems == null || typeDecItems.isEmpty()) {
			return;
		}
		
		int position = positionSelectTypeDec();
		if (position == -1 || position == 0) {
			return;
		}
		
		SelectItem item = typeDecItems.get(position);
		typeDecItems.remove(position);
		typeDecItems.add(position - 1, item);
	}
	
	/**
	 * Descendre d'une position.
	 */
	public void descendre() {
		if (selectTypeDecPosition == null || selectTypeDecPosition.isEmpty()
				|| typeDecItems == null || typeDecItems.isEmpty()) {
			return;
		}
		
		int position = positionSelectTypeDec();
		if (position == -1 || position >= (typeDecItems.size() - 1)) {
			return;
		}
		
		SelectItem item = typeDecItems.get(position);
		typeDecItems.remove(position);
		typeDecItems.add(position + 1, item);
	}
	
	/**
	 * Mettre en dernière position.
	 */
	public void enBas() {
		if (selectTypeDecPosition == null || selectTypeDecPosition.isEmpty()
				|| typeDecItems == null || typeDecItems.isEmpty()) {
			return;
		}
		
		int position = positionSelectTypeDec();
		if (position == -1 || position >= (typeDecItems.size() - 1)) {
			return;
		}
		
		SelectItem item = typeDecItems.get(position);
		typeDecItems.remove(position);
		typeDecItems.add(typeDecItems.size(), item);
	}
	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return autoListPrincipale
	 */
	public AutoListPrincipalePojo getAutoListPrincipale() {
		if (autoListPrincipale == null) {
			AutoListPrincipale autoLp = new AutoListPrincipale();
			autoLp.setListCommission(new HashSet<Commission>());
			autoLp.setListVet(new HashSet<VetAutoLp>());
			autoLp.setListTypeOfDecision(new ArrayList<TypeDecision>());
			
			autoListPrincipale = new AutoListPrincipalePojo(autoLp, new ArrayList<VetAutoLpPojo>(), 
					new ArrayList<CommissionPojo>());
			
			choix = VET;
		}
		return autoListPrincipale;
	}
	
	/**
	 * 
	 * @param autoListPrincipale
	 */
	public void setAutoListPrincipale(final AutoListPrincipalePojo autoListPrincipale) {
		this.autoListPrincipale = autoListPrincipale;
		if (autoListPrincipale.getAutoLp().getCodeCge() != null) {
			codeCge = autoListPrincipale.getAutoLp().getCodeCge();
			choix = CGE;
		} else if (autoListPrincipale.getAutoLp().getListCommission() != null
				&& !autoListPrincipale.getAutoLp().getListCommission().isEmpty()) {
			choix = COMMISSION;
		} else {
			choix = VET;
		}
		typeDecisionDeLP = String.valueOf(autoListPrincipale.getAutoLp().getTypeDecisionDeLP().getId());
	}
	
	/**
	 * 
	 * @return The list autoListPrincipale
	 */
	@SuppressWarnings("unchecked")
	public List<AutoListPrincipalePojo> getListAutoLp() {
		if (listAutoLp.isEmpty()) {
			for (AutoListPrincipale autoLp : getParameterService().getAutoListPrincipale()) {
				AutoListPrincipalePojo autoLpPojo = new AutoListPrincipalePojo(autoLp, 
						new ArrayList<VetAutoLpPojo>(), new ArrayList<CommissionPojo>());
				
				if (autoLp.getCodeCge() != null) {
					for (CentreGestion c : getDomainApoService().getCentreGestion()) {
						if (c.getCodCge().equals(autoLp.getCodeCge())) {
							autoLpPojo.setLibelleCge(c.getLibCge());
							break;
						}
					}
				}
				
				for (VetAutoLp vetAutoLp : autoLp.getListVet()) {
					autoLpPojo.getListVetAutoLpPojo().add(new VetAutoLpPojo(vetAutoLp, 
							getBusinessCacheService().getVersionEtape(vetAutoLp.getCodEtp(), 
									vetAutoLp.getCodVrsVet()).getLibWebVet()));
				}
				
				for (Commission comm : autoLp.getListCommission()) {
					Gestionnaire gest = (Gestionnaire) getSessionController().getCurrentUser();
					int codeRI = gest.getProfile().getCodeRI();
					CommissionPojo commissionPojo = new CommissionPojo(comm,
							comm.getContactsCommission().get(codeRI));
					autoLpPojo.getListCommissionPojo().add(commissionPojo);
				}
				listAutoLp.add(autoLpPojo);
				
				initDroitAutoLpPojo();
			}
			Collections.sort(listAutoLp, new BeanComparator("autoLp.libelle", new NullComparator()));
		}
		return listAutoLp;
	}
	
	private void initDroitAutoLpPojo() {
		Gestionnaire g = getCurrentGest();
		for (AutoListPrincipalePojo a : listAutoLp) {
			if (a.getAutoLp().getCodeCge() != null && !a.getAutoLp().getCodeCge().isEmpty()) {
				if (g.getCodeCge() != null && !g.getCodeCge().isEmpty()) {
					if (a.getAutoLp().getCodeCge().equals(g.getCodeCge())) {
						a.setRight(true);
					} else {
						a.setRight(false);
					}
				} else if (g.getRightOnCmi() == null || g.getRightOnCmi().isEmpty()) {
					a.setRight(true);
				} else {
					a.setRight(false);
				}
			} else if (a.getAutoLp().getListCommission() != null
					&& !a.getAutoLp().getListCommission().isEmpty()) {
				if (g.getCodeCge() != null && !g.getCodeCge().isEmpty()) {
					a.setRight(true);
					for (Commission c : a.getAutoLp().getListCommission()) {
						for (TraitementCmi t : c.getTraitementCmi()) {
							if (!t.getVersionEtpOpi().getCodCge().equals(g.getCodeCge())) {
								a.setRight(false);
								break;
							}
						}
						if (!a.isRight()) {
							break;
						}
					}
				} else if (g.getRightOnCmi() != null && !g.getRightOnCmi().isEmpty()) {
					for (Commission c : a.getAutoLp().getListCommission()) {
						a.setRight(false);
						for (Commission cCmi : g.getRightOnCmi()) {
							if (c.getId().equals(cCmi.getId())) {
								a.setRight(true);
								break;
							}
						}
						if (!a.isRight()) {
							break;
						}
					}
				} else {
					a.setRight(true);
				}
			} else if (a.getAutoLp().getListVet() != null && !a.getAutoLp().getListVet().isEmpty()) {
				if (g.getCodeCge() != null && !g.getCodeCge().isEmpty()) {
					a.setRight(true);
					for (VetAutoLp v : a.getAutoLp().getListVet()) {
						VersionEtapeDTO vet = getBusinessCacheService().getVersionEtape(
							v.getCodEtp(), v.getCodVrsVet());
						TraitementCmi trt = getParameterService().getTraitementCmi(
							new VersionEtpOpi(vet), null);
						if (!g.getCodeCge().equals(trt.getVersionEtpOpi().getCodCge())) {
							a.setRight(false);
							break;
						}
					}
				} else if (g.getRightOnCmi() != null && !g.getRightOnCmi().isEmpty()) {
					for (VetAutoLp v : a.getAutoLp().getListVet()) {
						a.setRight(false);
						for (Commission cCmi : g.getRightOnCmi()) {
							for (TraitementCmi t : cCmi.getTraitementCmi()) {
								if (t.getVersionEtpOpi().getCodEtp().
										equals(v.getCodEtp())
									&& t.getVersionEtpOpi().getCodVrsVet().
										equals(v.getCodVrsVet())) {
									a.setRight(true);
									break;
								}
							}
							if (a.isRight()) {
								break;
							}
						}
						if (!a.isRight()) {
							break;
						}
					}
				} else {
					a.setRight(true);
				}
			} else {
				a.setRight(true);
			}
		}
	}
	
	/**
	 * 
	 * @param listAutoLp
	 */
	public void setListAutoLp(final List<AutoListPrincipalePojo> listAutoLp) {
		this.listAutoLp = listAutoLp;
	}
	
	/**
	 * 
	 * @return actionEnum
	 */
	public ActionEnum getActionEnum() {
		if (actionEnum == null) {
			actionEnum = new ActionEnum();
		}
		return actionEnum;
	}
	
	/**
	 * 
	 * @param actionEnum
	 */
	public void setActionEnum(final ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}
	
	/**
	 * 
	 * @return actionListLc
	 */
	public ActionEnum getActionListLc() {
		if (actionListLc == null) {
			actionListLc = new ActionEnum();
		}
		return actionListLc;
	}
	
	/**
	 * 
	 * @param actionListLc
	 */
	public void setActionListLc(final ActionEnum actionListLc) {
		this.actionListLc = actionListLc;
	}

	/**
	 * 
	 * @return la liste des codes CGE
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllCge() {
		if (allCge.isEmpty()) {
			Set<CentreGestion> listCentreGestion = getListAllCge();
			if (listCentreGestion != null) {
				for (CentreGestion centreGestion : listCentreGestion) {
					if (!testExistCge(centreGestion.getCodCge())) {
						allCge.add(new SelectItem(centreGestion.getCodCge(),
								centreGestion.getLibCge()));
					}
				}
			}
			Collections.sort(allCge, new BeanComparator(VALUE, new NullComparator()));
		}
		return allCge;
	}
	
	/**
	 * 
	 * @param allCge
	 */
	public void setAllCge(final List<SelectItem> allCge) {
		this.allCge = allCge;
	}
	
	/**
	 * 
	 * @return codeCge
	 */
	public String getCodeCge() {
		return codeCge;
	}
	
	/**
	 * 
	 * @param codeCge
	 */
	public void setCodeCge(final String codeCge) {
		this.codeCge = codeCge;
	}
	
	/**
	 * 
	 * @return la liste de toute les commissions
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllCommItems() {
		if (allCommItems.isEmpty()) {
			Set<Commission> allCommissions = Utilitaires.getListCommissionsByRight(
					getCurrentGest(), 
					getDomainApoService(),
					getParameterService(), true);
			if (allCommissions != null) {
				for (Commission comm : allCommissions) {
					if (!testExistCommItems(comm.getCode())) {
						allCommItems.add(new SelectItem(comm.getCode(), 
								comm.getCode() + " (" + comm.getLibelle() + ")"));
					}
				}
			}
			Collections.sort(allCommItems, new BeanComparator(VALUE, new NullComparator()));
		}
		return allCommItems;
	}
	
	/**
	 * 
	 * @param allCommItems
	 */
	public void setAllCommItems(final List<SelectItem> allCommItems) {
		this.allCommItems = allCommItems;
	}
	
	/**
	 * 
	 * @return la liste de commissions selectionne
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getCommItems() {
		if (commItems.isEmpty()) {
			Set<Commission> listCommissions = getAutoListPrincipale().getAutoLp().getListCommission();
			if (listCommissions != null) {
				for (Commission comm : listCommissions) {
					commItems.add(new SelectItem(comm.getCode(), 
							comm.getCode() + " (" + comm.getLibelle() + ")"));
				}
			}
			Collections.sort(commItems, new BeanComparator(VALUE, new NullComparator()));
		}
		return commItems;
	}
	
	/**
	 * 
	 * @param commItems
	 */
	public void setCommItems(final List<SelectItem> commItems) {
		this.commItems = commItems;
	}
	
	/**
	 * 
	 * @return allVetItems
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllVetItems() {
		if (allVetItems.isEmpty()) {
			Set<Campagne> camps = getParameterService().getCampagnes(true, 
					String.valueOf(getCurrentGest().getProfile().getCodeRI()));
			Set<VersionEtapeDTO> allVets = new HashSet<VersionEtapeDTO>();
			for (Campagne camp : camps) {
				allVets.addAll(getDomainApoService().getVersionEtapes(
					null, null, getCurrentGest().getCodeCge(), camp.getCodAnu()));
			}

			Set<Commission> cmi = Utilitaires.getListCommissionsByRight(
					getCurrentGest(), 
					getDomainApoService(),
					getParameterService(), true);
			
			Set<VersionEtapeDTO> allVets2 = new HashSet<VersionEtapeDTO>();
				
			for (VersionEtapeDTO versionEtapeDTO : allVets) {
				if (BusinessUtil.getCmiForVetDTO(cmi, versionEtapeDTO) != null) {
					allVets2.add(versionEtapeDTO);
				}
			}
			allVets = allVets2;
			for (VersionEtapeDTO vet : allVets) {
				if (!testExistVetItems(vet.getCodEtp(), vet.getCodVrsVet())) {
					allVetItems.add(new SelectItem(vet.getCodEtp(),
							vet.getCodEtp() + "(" + vet.getLibWebVet() + ")",
							String.valueOf(vet.getCodVrsVet())));
				}
			}
			
			Collections.sort(allVetItems, new BeanComparator(LABEL, new NullComparator()));
		}
		return allVetItems;
	}
	
	/**
	 * 
	 * @param allVetItems
	 */
	public void setAllVetItems(final List<SelectItem> allVetItems) {
		this.allVetItems = allVetItems;
	}
	
	/**
	 * 
	 * @return vetItems
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getVetItems() {
		if (vetItems.isEmpty()) {
			List<VetAutoLpPojo> listVets = getAutoListPrincipale().getListVetAutoLpPojo();
			if (listVets != null) {
				for (VetAutoLpPojo vet : listVets) {
					vetItems.add(new SelectItem(vet.getVetAutoLp().getCodEtp(),
							vet.getVetAutoLp().getCodEtp() + "(" + vet.getLibWebVet() + ")",
							String.valueOf(vet.getVetAutoLp().getCodVrsVet())));
				}
			}
			Collections.sort(vetItems, new BeanComparator(LABEL, new NullComparator()));
		}
		return vetItems;
	}
	
	/**
	 * 
	 * @param vetItems
	 */
	public void setVetItems(final List<SelectItem> vetItems) {
		this.vetItems = vetItems;
	}
	
	/**
	 * 
	 * @return selectCommADI
	 */
	public List<String> getSelectCommADI() {
		return selectCommADI;
	}
	
	/**
	 * 
	 * @param selectCommADI
	 */
	public void setSelectCommADI(final List<String> selectCommADI) {
		this.selectCommADI = selectCommADI;
	}
	
	/**
	 * 
	 * @return selectCommDI
	 */
	public List<String> getSelectCommDI() {
		return selectCommDI;
	}
	
	/**
	 * 
	 * @param selectCommDI
	 */
	public void setSelectCommDI(final List<String> selectCommDI) {
		this.selectCommDI = selectCommDI;
	}
	
	/**
	 * 
	 * @return selectVetADI
	 */
	public List<String> getSelectVetADI() {
		return selectVetADI;
	}
	
	/**
	 * 
	 * @param selectVetADI
	 */
	public void setSelectVetADI(final List<String> selectVetADI) {
		this.selectVetADI = selectVetADI;
	}
	
	/**
	 * 
	 * @return selectVetDI
	 */
	public List<String> getSelectVetDI() {
		return selectVetDI;
	}
	
	/**
	 * 
	 * @param selectVetDI
	 */
	public void setSelectVetDI(final List<String> selectVetDI) {
		this.selectVetDI = selectVetDI;
	}
	
	/**
	 * 
	 * @return allChoix
	 */
	public List<SelectItem> getAllChoix() {
		if (allChoix == null) {
			allChoix = new ArrayList<SelectItem>();
			for (String ch : LESCHOIX) {
				allChoix.add(new SelectItem(ch));
			}
		}
		return allChoix;
	}
	
	/**
	 * 
	 * @return choix
	 */
	public String getChoix() {
		return choix;
	}
	
	/**
	 * 
	 * @param choix
	 */
	public void setChoix(final String choix) {
		this.choix = choix;
	}
	
	/**
	 * 
	 * @return allTypeDecision
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllTypeDecisionIA() {
		if (allTypeDecisionIA.isEmpty()) {
			Set<TypeDecision> allTypeDec = getParameterService().getTypeDecisions(true);
			if (allTypeDec != null) {
				for (TypeDecision typeDec : allTypeDec) {
					if (typeDec.getCodeTypeConvocation().equals(inscriptionAdm.getCode())) {
						allTypeDecisionIA.add(new SelectItem(String.valueOf(typeDec.getId()), 
							typeDec.getCode() + " (" + typeDec.getLibelle() + ")"));
					}
				}
			}
			Collections.sort(allTypeDecisionIA, new BeanComparator("label", new NullComparator()));
		}
		return allTypeDecisionIA;
	}
	
	/**
	 * 
	 * @return typeDecisionDeLP
	 */
	public String getTypeDecisionDeLP() {
		return typeDecisionDeLP;
	}
	
	/**
	 * 
	 * @param typeDecisionDeLP
	 */
	public void setTypeDecisionDeLP(final String typeDecisionDeLP) {
		this.typeDecisionDeLP = typeDecisionDeLP;
	}
	
	/**
	 * 
	 * @return la liste de type de décision
	 */
	public List<SelectItem> getTypeDecItems() {
		if (typeDecItems.isEmpty()) {
			List<TypeDecision> listTypeDec = getAutoListPrincipale().getAutoLp().getListTypeOfDecision();
			if (listTypeDec != null) {
				for (TypeDecision typeDec : listTypeDec) {
					typeDecItems.add(new SelectItem(String.valueOf(typeDec.getId()), 
						typeDec.getCode() + " (" + typeDec.getLibelle() + ")"));
				}
			}
		}
		return typeDecItems;
	}
	
	/**
	 * 
	 * @param typeDecItems
	 */
	public void setTypeDecItems(final List<SelectItem> typeDecItems) {
		this.typeDecItems = typeDecItems;
	}
	
	/**
	 * 
	 * @return la liste de type de décision
	 */
	@SuppressWarnings("unchecked")
	public List<SelectItem> getAllTypeDecLcItems() {
		if (allTypeDecLcItems.isEmpty()) {
			Set<TypeDecision> allTypeDec = getParameterService().getTypeDecisions(true);
			if (allTypeDec != null) {
				for (TypeDecision typeDec : allTypeDec) {
					if (typeDec.getCodeTypeConvocation().equals(listeComplementaire.getCode())
							&& getExistTypeDecItems(typeDec.getId()) == -1) {
						allTypeDecLcItems.add(new SelectItem(String.valueOf(typeDec.getId()), 
								typeDec.getCode() + " (" + typeDec.getLibelle() + ")"));
					}
				}
			}
			Collections.sort(allTypeDecLcItems, new BeanComparator("label", new NullComparator()));
		}
		return allTypeDecLcItems;
	}
	
	/**
	 * 
	 * @param allTypeDecLcItems
	 */
	public void setAllTypeDecLcItems(final List<SelectItem> allTypeDecLcItems) {
		this.allTypeDecLcItems = allTypeDecLcItems;
	}
	
	/**
	 * 
	 * @return selectTypeDecDI
	 */
	public List<String> getSelectTypeDecDI() {
		return selectTypeDecDI;
	}
	
	/**
	 * 
	 * @param selectTypeDecDI
	 */
	public void setSelectTypeDecDI(final List<String> selectTypeDecDI) {
		this.selectTypeDecDI = selectTypeDecDI;
	}
	
	/**
	 * 
	 * @return selectTypeDecADI
	 */
	public List<String> getSelectTypeDecADI() {
		return selectTypeDecADI;
	}
	
	/**
	 * 
	 * @param selectTypeDecADI
	 */
	public void setSelectTypeDecADI(final List<String> selectTypeDecADI) {
		this.selectTypeDecADI = selectTypeDecADI;
	}
	
	/**
	 * 
	 * @return selectTypeDecPosition
	 */
	public String getSelectTypeDecPosition() {
		return selectTypeDecPosition;
	}
	
	/**
	 * 
	 * @param selectTypeDecPosition
	 */
	public void setSelectTypeDecPosition(final String selectTypeDecPosition) {
		this.selectTypeDecPosition = selectTypeDecPosition;
	}
	
	/**
	 * 
	 * @return inscriptionAdm
	 */
	public InscriptionAdm getInscriptionAdm() {
		return inscriptionAdm;
	}
	
	/**
	 * 
	 * @param inscriptionAdm
	 */
	public void setInscriptionAdm(final InscriptionAdm inscriptionAdm) {
		this.inscriptionAdm = inscriptionAdm;
	}
	
	/**
	 * 
	 * @return listeComplementaire
	 */
	public ListeComplementaire getListeComplementaire() {
		return listeComplementaire;
	}
	
	/**
	 * 
	 * @param listeComplementaire
	 */
	public void setListeComplementaire(final ListeComplementaire listeComplementaire) {
		this.listeComplementaire = listeComplementaire;
	}
	
	private Set<CentreGestion> getListAllCge() {
		if (listAllCge.isEmpty()) {
			listAllCge.addAll(Utilitaires.getListCgeByRight(
					getCurrentGest(),
					getDomainApoService()));
		}
		return listAllCge;
	}
}
