/**
 * 
 */
package org.esupportail.opi.web.controllers.parameters;

import fj.F;
import fj.F2;
import fj.Function;
import fj.P1;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.myfaces.component.html.ext.HtmlInputText;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.commons.utils.Assert;
import org.esupportail.opi.domain.beans.parameters.*;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.beanEnum.ActionEnum;
import org.esupportail.opi.web.beans.beanEnum.WayfEnum;
import org.esupportail.opi.web.beans.parameters.FormationContinue;
import org.esupportail.opi.web.beans.parameters.FormationInitiale;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.NomenclaturePojo;
import org.esupportail.opi.web.beans.pojo.PieceJustiVetPojo;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.opi.web.beans.utils.Utilitaires;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorSelectItem;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.opi.web.controllers.AbstractContextAwareController;
import org.esupportail.opi.web.controllers.references.EtapeController;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.primefaces.model.UploadedFile;
import org.springframework.util.StringUtils;

import javax.faces.model.SelectItem;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.*;

import static fj.data.Array.iterableArray;
import static fj.data.Array.single;
import static fj.data.Option.fromString;
import static fj.data.Stream.iterableStream;
import static java.util.Arrays.asList;

public class NomenclatureController extends AbstractContextAwareController {
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 6123349004186376190L;
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The Nomenclature.
	 */
	private Nomenclature nomenclature;
	
	/**
	 *  The VET (DTO)
	 */
	private VersionEtapeDTO vetDTO;


	/**
	 * The actionEnum.
	 */
	private ActionEnum actionEnum;
	
	/**
	 * 	The first element of the list is the Nomenclature to add.
	 * The others elements are all Nomenclature in dataBase.
	 */
	private List<Nomenclature> addNomenclatures;
	
	/**
	 * From where you are from.
	 */
	private WayfEnum wayfEnum;

	/**
	 * List of Etapes attached to the PJ. 
	 */
	private Set<PieceJustiVetPojo>  allEtapes;
	
	/**
	 * List of PJs attached to the Etape. 
	 */
	private Set<NomenclaturePojo>  allPJs;
	
	/**
	 * List of PJs attached to the Etape. 
	 */
	private Set<NomenclaturePojo>  addPJs;

	/**
	 * List of Etapes attached to the PJ to by deleted. 
	 */
	private Set<PieceJustiVetPojo>  deleteEtapes;
	
	/**
	 * List of PJs attached to the etapes to by deleted. 
	 */
	private Set<NomenclaturePojo>  deletePJs;

	/**
	 * The Etape to add.
	 */
	private PieceJustiVetPojo etapeTraitee; 
	
	
	/**
	 * The current PJ.
	 */
	private NomenclaturePojo PJTraitee;

	/**
	 * The manager or the versionEtape to add to the pj.
	 */
	private Object[] objectToAdd;
	

	/**
	 * see {@link EtapeController}.
	 */
	private EtapeController etapeController;
	
	/**
	 * see {@link InscriptionAdm}.
	 */
	private InscriptionAdm inscriptionAdm;
	
	/**
	 * see {@link EtapeController}
	 * the inscription regime code given by etapeController
	 */
	private int codeRI;
	
	/**
	 * 
	 */
	private HtmlInputText code;
	
	/**
	 * 
	 */
	private UploadedFile uploadedFile;

	/**
	 * 
	 */
	private String fileName;
	
	/**
	 * return le chemin ou vont être téléchargés les documents liés aux pièces jointes.
	 */
	private String uploadPath;
	
	/**
	 * Témoin gérant l'usage ou non de la fonctionnalité de téléchargement de document.
	 */
	private boolean useUpload;

	private List<TypeDecision> typesDec;
	private List<TypeDecision> sortedTypesDec;
	private List<TypeDecision> typesDecInUse;
	private List<SelectItem> typesDecItems;
	private List<SelectItem> typesDecInUseItems;
	
	/**
	 * default value : false.
	 */
	private Boolean isFinal = false;

    private static F<TypeDecision,SelectItem> typeDecToItem = new F<TypeDecision, SelectItem>() {
        public SelectItem f(final TypeDecision t) {
            return fromString(t.getCode())
                    .apply(fromString(t.getShortLabel())
                            .map(Function.curry(new F2<String, String, SelectItem>() {
                                public SelectItem f(String label, String code) {
                                    return new SelectItem(t, code, label);
                                }
                            })))
                    .orSome(new P1<SelectItem>() {
                        public SelectItem _1() {
                            return new SelectItem(new TypeDecision(), "???");
                        }
                    });
        }
    };

    public NomenclatureController() {
		super();
		reset();
	}
	
	public void initTypesDec() {
		typesDec = new ArrayList<>(getParameterService().getTypeDecisions(null));
		sortedTypesDec = new ArrayList<>(typesDec);
		Collections.sort(sortedTypesDec, new ComparatorString(TypeDecision.class));
		typesDecInUse = new ArrayList<>(getParameterService().getTypeDecisions(true));
		typesDecItems = typeDecsToItems(typesDec);
		typesDecInUseItems = typeDecsToItems(typesDecInUse);
	}

    private List<SelectItem> typeDecsToItems(List<TypeDecision> typeDecisions) {
        return asList(single(new SelectItem(new TypeDecision(), ""))
                .append(iterableArray(typeDecisions).map(typeDecToItem))
                .array(SelectItem[].class));
    }

    @Override
	public void reset() {
		super.reset();
		nomenclature = null;
		actionEnum = new ActionEnum();
		addNomenclatures = new ArrayList<>();
		objectToAdd = new Object[0];
		allEtapes = new HashSet<>();
		allPJs = new TreeSet<>(new ComparatorString(NomenclaturePojo.class));
		addPJs = new HashSet<>();
		deleteEtapes = new HashSet<>();
		deletePJs = new HashSet<>();
		etapeTraitee = new PieceJustiVetPojo();
		wayfEnum = new WayfEnum();
	}
	
	/**
	 * RAZ sans toucher à deletePJs
	 */
	public void resetSpecial() {
		super.reset();
		nomenclature = null;
		actionEnum = new ActionEnum();
		addNomenclatures = new ArrayList<Nomenclature>();
		objectToAdd = new Object[0];
		allEtapes = new HashSet<PieceJustiVetPojo>();
		allPJs = new TreeSet<NomenclaturePojo>(new ComparatorString(NomenclaturePojo.class));
		addPJs = new HashSet<NomenclaturePojo>();
		deleteEtapes = new HashSet<PieceJustiVetPojo>();	
		etapeTraitee = new PieceJustiVetPojo();
		wayfEnum = new WayfEnum();
	}
	
	@Override
	public void afterPropertiesSetInternal() {
		super.afterPropertiesSetInternal();
		Assert.notNull(this.etapeController, "property etapeController of class " 
				+ this.getClass().getName() + " can not be null");
		Assert.notNull(this.inscriptionAdm, "property inscriptionAdm of class " 
				+ this.getClass().getName() + " can not be null");
		
	}

	/**
	 * Callback to treatment type list.
	 */
	public String goSeeAllTypTrt() {
		reset();
		return NavigationRulesConst.MANAGED_TYP_TRT;
	}
	
	/**
	 * Callback to decision type list.
	 */
	public String goSeeAllTypDecision() {
		reset();
		return NavigationRulesConst.MANAGED_TYP_DECISION;
	}
	
	/**
	 * Callback to convocation type list.
	 */
	public String goSeeAllTypConv() {
		reset();
		return NavigationRulesConst.MANAGED_TYP_CONV;
	}
	
	/**
	 * Callback to convocation type list.
	 */
	public String goSeeAllMotivAvis() {
		reset();
		return NavigationRulesConst.MANAGED_MOTIV_AVIS;
	}
	
	/**
	 * Callback to convocation type list.
	 */
	public String goSeeAllCampagnes() {
		reset();
		return NavigationRulesConst.MANAGED_CAMPAGNES;
	}
	
	/**
	 * Callback to PJ list.
	 */
	public String goSeeAllPJ() {
		reset();
		return NavigationRulesConst.MANAGED_PJ;
	}
	
	/**
	 * Callback to PJ list to affect VET.
	 */
	public String goSeeAllAffectPJ() {
		reset();
		return NavigationRulesConst.SEE_ALL_AFFECT_PJ;
	}
	
	/**
	 * Callback to searchPJForVet
	 */
	public String goSeePJforVet(){
		//reset();
		return NavigationRulesConst.SEARCH_PJ_FOR_VET;
	}
	
	/**
	 * Callback to document list.
	 */
	public String goSeeAllDocument() {
		reset();
		return NavigationRulesConst.SEE_ALL_DOCUMENT;
	}
	
	
	/**
	 * Callback to treatment type list.
	 */
	public String goAddTypDecision() {
		addNomenclatures = new ArrayList<Nomenclature>();
		addNomenclatures.add(new TypeDecision());
		addNomenclatures.addAll(getTypeDecisions());
		return NavigationRulesConst.ADD_TYP_DECISION;
	}
	
	/**
	 * Callback to motivation add.
	 */
	public void goAddMotivation() {
		nomenclature = new MotivationAvis();
		actionEnum.setWhatAction(ActionEnum.ADD_ACTION);
	}
	
	/**
	 * Callback to campagne add.
	 */
	public void goAddCampagne() {
		nomenclature = new Campagne();
		actionEnum.setWhatAction(ActionEnum.ADD_ACTION);
	}
	
	
	/**
	 * Callback to treatment type list.
	 */
	public String goAddPJ() {
		nomenclature = new PieceJustificative();
		// type of action : add
		this.actionEnum.setWhatAction(this.actionEnum.getAddAction());
		this.wayfEnum.setWhereAreYouFrom(WayfEnum.PJ_VALUE);
		// for the return
		this.wayfEnum.setWhereAreYouFrom(this.wayfEnum.getPJValue());
		return NavigationRulesConst.ENTER_PJ;
	}
	
	/**
	 * Callback to treatment type list.
	 */
	public String goAddOrChoicesPJ() {
		return NavigationRulesConst.CHOICES_PJ;
	}
	
	/**
	 * Callback to treatment type list.
	 */
	public String goUpdatePJ() {
		// type of action : Update
		this.actionEnum.setWhatAction(this.actionEnum.getUpdateAction());
		this.wayfEnum.setWhereAreYouFrom(WayfEnum.PJ_VALUE);
		// Charge VersionEtape
		this.allEtapes.clear();
		Set<VersionEtpOpi> listEtpByRight = Utilitaires.getListEtpByRight(getCurrentGest());
		PieceJustificative laPJ = (PieceJustificative) nomenclature;
		for (PieceJustiVet p : laPJ.getVersionEtapes()) {
			PieceJustiVetPojo pjv = new PieceJustiVetPojo();
			pjv.setVersionEtape(getDomainApoService().getVersionEtape(
					p.getVersionEtpOpi().getCodEtp(), p.getVersionEtpOpi().getCodVrsVet()));
			pjv.setPieceJustiVet(p);
			if (getSessionController().isAllViewPJ()) {
				pjv.setAllRight(true);
			} else {
				pjv.setAllRight(Utilitaires.isVetByRight(listEtpByRight,
						p.getVersionEtpOpi(), getCurrentGest(), getDomainApoService()));
			}
			allEtapes.add(pjv);
		}
		// for the return
		this.wayfEnum.setWhereAreYouFrom(this.wayfEnum.getPJValue());
		return NavigationRulesConst.ENTER_PJ;
	}
	
	/**
	 * Callback to read PJ.
	 */
	public String goSeeOnePJ() {
		// type of action : Read
		this.actionEnum.setWhatAction(this.actionEnum.getReadAction());
		// Charge les Etapes
		this.allEtapes.clear();
		Set<VersionEtpOpi> listEtpByRight = Utilitaires.getListEtpByRight(getCurrentGest());
		PieceJustificative laPJ = (PieceJustificative) nomenclature;
		for (PieceJustiVet p : laPJ.getVersionEtapes()) {
			PieceJustiVetPojo pjv = new PieceJustiVetPojo();
			pjv.setVersionEtape(getDomainApoService().getVersionEtape(
					p.getVersionEtpOpi().getCodEtp(), p.getVersionEtpOpi().getCodVrsVet()));
			pjv.setPieceJustiVet(p);
			if (getSessionController().isAllViewPJ()) {
				pjv.setAllRight(true);
			} else {
				pjv.setAllRight(Utilitaires.isVetByRight(listEtpByRight,
						p.getVersionEtpOpi(), getCurrentGest(), getDomainApoService()));
			}
			allEtapes.add(pjv);
		}
		// for the return
		this.wayfEnum.setWhereAreYouFrom(this.wayfEnum.getPJValue());
		return NavigationRulesConst.ENTER_PJ;
	}
	
	/**
	 * Callback to affect PJ to VET.
	 */
	public String goSeeAffectPJ() {
		// Charge les Etapes
		this.allEtapes.clear();
		PieceJustificative laPJ = (PieceJustificative) nomenclature;
		Set<VersionEtpOpi> listEtpByRight = Utilitaires.getListEtpByRight(getCurrentGest());
		for (PieceJustiVet p : laPJ.getVersionEtapes()) {
			PieceJustiVetPojo pjv = new PieceJustiVetPojo();
			pjv.setVersionEtape(getDomainApoService().getVersionEtape(
					p.getVersionEtpOpi().getCodEtp(), p.getVersionEtpOpi().getCodVrsVet()));
			pjv.setPieceJustiVet(p);
			if (getSessionController().isAllViewPJ()) {
				pjv.setAllRight(true);
			} else {
				pjv.setAllRight(Utilitaires.isVetByRight(listEtpByRight,
						p.getVersionEtpOpi(), getCurrentGest(), getDomainApoService()));
			}
			allEtapes.add(pjv);
		}
		// for the return
		this.wayfEnum.setWhereAreYouFrom(WayfEnum.AFFECT_PJ_VALUE);
		return NavigationRulesConst.AFFECT_PJ;
	}
	
	/**
	 * Callback to search version etape.
	 */
	public String goSearchEtpForPJ() {
		etapeController.reset();
		etapeController.setCodCge(getCurrentGest().getCodeCge());
		// define from where we go to search Vet
		etapeController.setWayfEnum(this.wayfEnum);
		// on initialise la liste de campagne
		etapeController.getCampagnes().addAll(getParameterService().getCampagnes(null,
		    String.valueOf(codeRI)));
		etapeController.setCodAnu(getParameterService().getCampagneEnServ(codeRI).getCodAnu());
		
		if (!getSessionController().isAllViewPJ()
				&& !StringUtils.hasText(getCurrentGest().getCodeCge())
				&& getCurrentGest().getRightOnCmi() != null
				&& !getCurrentGest().getRightOnCmi().isEmpty()) {
			etapeController.setEtapes(new ArrayList<VersionEtapeDTO>(
				Utilitaires.getListEtpDtoByRight(getCurrentGest(), getDomainApoService())));
		}
		return NavigationRulesConst.SEARCH_VET;
	}	

	public String goBackFromSearchPJ() {
		reset();
		return etapeController.goSearchVetForGestPJ();
	}

	/**
	 * Add a Nomenclature to the dataBase.
	 * @return String
	 */
	public String add() {
		if (log.isDebugEnabled()) {
			log.debug("entering add with nomenclature = " + nomenclature);
		}
		String target = null;

        if (nomenclature instanceof Campagne) {
            ((Campagne) nomenclature).setCodeRI(FormationInitiale.CODE);
        }

		if (this.wayfEnum.getWhereAreYouFrom() != this.wayfEnum.getPJValue()
				&& nomenclature == null)	{
			//Get the first element of addNomenclatures
			nomenclature = getAddNomenclatures().get(0);
		}

		if (ctrlEnter(nomenclature)) {
			addInfoMessage(null, "INFO.ENTER.SUCCESS");
			if (nomenclature instanceof TypeDecision) {
				target = NavigationRulesConst.MANAGED_TYP_DECISION;
			} else if (nomenclature instanceof PieceJustificative) {
				target = NavigationRulesConst.MANAGED_PJ;
				Set<PieceJustiVet> listP = new HashSet<PieceJustiVet>();
				for (PieceJustiVetPojo p : allEtapes) {					
					listP.add(p.getPieceJustiVet());
				}
				PieceJustificative piece = (PieceJustificative) nomenclature;
				piece.setVersionEtapes(listP);
				// it's not necessary here to use deleteEtapes (cause this is a add)
			}
			nomenclature = getDomainService().add(nomenclature, getCurrentGest().getLogin());
			getParameterService().addNomenclature(nomenclature);
			reset();
		}
		if (log.isDebugEnabled()) {
			log.debug("leaving add");
		}
		return target;
	}

	public String update() {
		if (log.isDebugEnabled()) {
			log.debug("enterind update with nomenclature = " + nomenclature);
		}
		String jsfRetour = null;
		for (NomenclaturePojo camp : getCampagnes()) {
			if (nomenclature.getId().equals(camp.getNomenclature().getId())) {
				((Campagne) nomenclature).setCodAnu(
						((Campagne) camp.getNomenclature()).getCodAnu());
			}
		}
		if (ctrlEnter(nomenclature)) {
			if (nomenclature instanceof PieceJustificative) {
				if (wayfEnum.getWhereAreYouFrom().equals(WayfEnum.PJ_VALUE)) {
					jsfRetour = NavigationRulesConst.MANAGED_PJ;
				} else if (wayfEnum.getWhereAreYouFrom().equals(WayfEnum.AFFECT_PJ_VALUE)) {
					jsfRetour = NavigationRulesConst.SEE_ALL_AFFECT_PJ;
				}
				
				Set<PieceJustiVet> listP = new HashSet<PieceJustiVet>();
				for (PieceJustiVetPojo p : allEtapes) {
					listP.add(p.getPieceJustiVet());
				}
				PieceJustificative piece = (PieceJustificative) nomenclature;
				piece.setVersionEtapes(listP);
			}
			nomenclature = getDomainService().update(
					nomenclature, getCurrentGest().getLogin());
			getParameterService().updateNomenclature(nomenclature);
			// delete the etapes deleted by the user
			for (PieceJustiVetPojo p : deleteEtapes) {
				if (p.getPieceJustiVet().getId() != 0) {
					getParameterService().deletePieceJustiVet(p.getPieceJustiVet());
				}
			}
			reset();
			addInfoMessage(null, "INFO.ENTER.SUCCESS");
		}

		if (log.isDebugEnabled()) {
			log.debug("leaving update");
		}
		return jsfRetour;
	}

	public void delete() {
		if (log.isDebugEnabled()) {
			log.debug("enterind delete with nomenclature = " + nomenclature);
		}
		if (ctrlDelete(nomenclature)) {
			if (nomenclature instanceof PieceJustificative) {
				// Delete all etapes attached
				for (PieceJustiVetPojo p : allEtapes) {
					getParameterService().deletePieceJustiVet(p.getPieceJustiVet());
				}
				getDomainService().deleteMissingPiece(null, (PieceJustificative) nomenclature);
			}
			
			// delete nomenclature
			getParameterService().deleteNomenclature(nomenclature);	
			addInfoMessage(null, "INFO.DELETE.SUCCESS");
		} else {
			addErrorMessage(null, "ERROR.NOM.CAN_NOT.DELETE");
		}
		reset();
		if (log.isDebugEnabled()) {
			log.debug("leaving delete");
		}
	}

	public String addEtapes() {
		Set<VersionEtpOpi> listEtpByRight = Utilitaires.getListEtpByRight(getCurrentGest());
		if (objectToAdd.length > 0) {
			for (Object o : objectToAdd) {
				VersionEtapeDTO v = (VersionEtapeDTO) o;
				PieceJustiVetPojo a = new PieceJustiVetPojo(v);
				if (getSessionController().isAllViewPJ()) {
					a.setAllRight(true);
				} else {
					a.setAllRight(Utilitaires.isVetByRight(listEtpByRight, 
							new VersionEtpOpi(v), getCurrentGest(), getDomainApoService()));
				}
				this.allEtapes.add(a);
				// remove to the list of etapes to by deleted (if exist)
				this.deleteEtapes.remove(a);
				
			}
		} else if (etapeController.getAllChecked()) {
			//on ajout toutes les etapes
			for (VersionEtapeDTO v : etapeController.getEtapes()) {
				PieceJustiVetPojo a = new PieceJustiVetPojo(v);
				if (getSessionController().isAllViewPJ()) {
					a.setAllRight(true);
				} else {
					a.setAllRight(Utilitaires.isVetByRight(listEtpByRight,
							new VersionEtpOpi(v), getCurrentGest(), getDomainApoService()));
				}
				this.allEtapes.add(a);
				// remove to the list of etapes to by deleted (if exist)
				this.deleteEtapes.remove(a);
				
			}
		}
		objectToAdd = new Object[0];
		this.etapeController.reset();
		String callback = null;
		if (wayfEnum.getWhereAreYouFrom().equals(WayfEnum.AFFECT_PJ_VALUE)) {
			callback = NavigationRulesConst.AFFECT_PJ;
		} else if (wayfEnum.getWhereAreYouFrom().equals(WayfEnum.PJ_VALUE)) {
			callback = NavigationRulesConst.ENTER_PJ;
		}
		return callback;
	}

	public String addPJs() {
        allPJs= new TreeSet<>(new ComparatorString(NomenclaturePojo.class));
        if (getObjectToAdd().length > 0) {
            for (Object o : objectToAdd) {
                NomenclaturePojo v = (NomenclaturePojo) o;
                if(!isInSet(v,deletePJs)){
                    this.addPJs.add(v);
                    this.allPJs.add(v);
                }else{
                    deletePJs = removePJfromSet(v, deletePJs);
                }
            }
        }
        this.etapeController.reset();
        getPiecesJToNomenclaturePojo();
        objectToAdd = new Object[0];
		return NavigationRulesConst.ENTER_VET;
	}

	public boolean isInSet(NomenclaturePojo np, Set<NomenclaturePojo> set){
		PieceJustificative pj = (PieceJustificative)np.getNomenclature();
        for (NomenclaturePojo piecePojo : set) {
            PieceJustificative pjust = (PieceJustificative) piecePojo.getNomenclature();
            if (pjust.getCode().equalsIgnoreCase(pj.getCode()))
                return true;
        }
		return false;
	}

	public Set<NomenclaturePojo> removePJfromSet(NomenclaturePojo np, Set<NomenclaturePojo> set){
		Set<NomenclaturePojo> setTemp = new HashSet<>();
		setTemp.addAll(set);
        for (NomenclaturePojo piecePojo : setTemp)
            if (np.getNomenclature().getCode().equalsIgnoreCase(piecePojo.getNomenclature().getCode()))
                set.remove(piecePojo);
		return set;
	}

	public void removeTrtEtape() {
		if (log.isDebugEnabled())
            log.debug("entering removeTrtEtape etapeTraitee" + etapeTraitee);
		// ajoute dans la liste des etapes e supprimer
		this.deleteEtapes.add(this.etapeTraitee);
		// enleve de l'objet pj
		this.allEtapes.remove(this.etapeTraitee);
	}

	public void removeTrtPJ() {
		if (log.isDebugEnabled()) {
			log.debug("entering removeTrtPJ PJTraitee" + PJTraitee);
		}
		// ajoute dans la liste des etapes e supprimer
		this.deletePJs.add(this.PJTraitee);
		// enleve de l'objet pj
		this.allPJs.remove(this.PJTraitee);
		this.addPJs.remove(this.PJTraitee);
	}

	public void updatePJs(){
        Set<PieceJustiVet> listP = new HashSet<>();
        PieceJustiVetPojo pjvp = new PieceJustiVetPojo(vetDTO);
        listP.add(pjvp.getPieceJustiVet());

        //on sauvegarde les PJs ajoutées
        for (NomenclaturePojo piecePojo : addPJs) {
            PieceJustificative pj = (PieceJustificative) piecePojo.getNomenclature();
            pj.setVersionEtapes(listP);
            getParameterService().updateNomenclature(pj);
        }

        //on supprime les PJs à supprimer
        for (NomenclaturePojo piecePojo : deletePJs) {
            PieceJustificative pj = (PieceJustificative) piecePojo.getNomenclature();
            Set<PieceJustiVet> setPiece = pj.getVersionEtapes();
            //on cherche le bon code etp pour supprimer la PieceJutsiVet correpondant à la pièce justificative et
            // à la vet
            for (PieceJustiVet pjv : setPiece) {
                VersionEtpOpi ved = pjv.getVersionEtpOpi();
                if (vetDTO.getCodEtp().equalsIgnoreCase(ved.getCodEtp()))
                    getParameterService().deletePieceJustiVetWithFlush(pjv);
            }
        }

        allPJs = new TreeSet<>(new ComparatorString(NomenclaturePojo.class));
        Set<NomenclaturePojo> affichAllPJs = getPiecesJToNomenclaturePojo();
		affichAllPJs.removeAll(deletePJs);
		deletePJs = new HashSet<>();
		allPJs.addAll(affichAllPJs);
		addInfoMessage(null, "INFO.ENTER.SUCCESS");
	}

	private Boolean ctrlEnter(final Nomenclature nom) {
		Boolean ctrlOk = true;
		if (!StringUtils.hasText(nom.getCode())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.CODE"));
			ctrlOk = false;
		} else {
			if (!getParameterService().nomenclatureCodeIsUnique(nom)) {
				ctrlOk = false;
				addErrorMessage(null, "ERROR.FIELD.NOT_UNIQUE", getString("FIELD_LABEL.CODE"));
			}
		}
		if (!(nom instanceof Campagne) && !StringUtils.hasText(nom.getLibelle())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.LIBELLE"));
			ctrlOk = false;
		}
		if (!(nom instanceof PieceJustificative) && !(nom instanceof Campagne) 
				&& !StringUtils.hasText(nom.getShortLabel())) {
			addErrorMessage(null, Constantes.I18N_EMPTY, getString("FIELD_LABEL.SHORT_LIB"));
			ctrlOk = false;
		}
		if (nom instanceof TypeDecision) {
			TypeDecision t = (TypeDecision) nom;
			if (!StringUtils.hasText(t.getCodeTypeConvocation())) {
				addErrorMessage(null, Constantes.I18N_EMPTY, 
						getString("TYP_DECISION.CONVOCATION_TYPE"));
				ctrlOk = false;
			} else {
				//on controle qu'il n'y a qu'une inscript Adm
				if (t.getCodeTypeConvocation().equals(inscriptionAdm.getCode())) {
					Set<TypeDecision> list = getParameterService().getTypeDecisions(null);
					for (TypeDecision tlist : list) {
						if (StringUtils.hasText(tlist.getCodeTypeConvocation())) {
							if (!tlist.equals(t)
									&& tlist.getCodeTypeConvocation()
									.equals(t.getCodeTypeConvocation())) {
								addErrorMessage(null, 
										"ERROR.TYP_DEC.JUST_ONE_INS_ADM",
										inscriptionAdm.getLabel());
								ctrlOk = false;
								break;
							}
						}
					}
				}
				
				
			}
		}
		
		//Ajout try par rapport au bug 26 pour exception non-catchée
		try{
			if (nom instanceof Campagne) {
				Campagne c = (Campagne) nom;
				// on controle si les dates sont saisis correctement
				// les dates ne doivent pas être nulles
				if (c.getDateDebCamp() == null) {
					addErrorMessage(null, "ERROR.CAMP.DATE_DEB_EMPTY");
					ctrlOk = false;
				}
				if (c.getDateFinCamp() == null) {
					addErrorMessage(null, "ERROR.CAMP.DATE_FIN_EMPTY");
					ctrlOk = false;
				}
				// la date de fin de campagne doit être supérieure à celle de début
				if (c.getDateDebCamp() != null && c.getDateFinCamp() != null
						&& !c.getDateFinCamp().after(c.getDateDebCamp())) {
					addErrorMessage(null, "ERROR.CAMP.DATE_FIN_SUP_DEB");
					ctrlOk = false;
				}
				
				
				// on controle que le codeAnu saisi est correct
				if (getDomainApoService().getAnneeUni(c.getCodAnu()) == null) {
					addErrorMessage(null, "ERROR.CAMP.COD_ANU_APOGEE");
					ctrlOk = false;
				}
				
				
				Set<Campagne> camps = getParameterService().getCampagnes(null, null);
				RegimeInscription rI = getRegimeIns().get(c.getCodeRI());
				for (Campagne camp : camps) {
					// on ne peut pas avoir 2 campagnes avec le même code
					// sauf si l'une est en FC et l'autre non
					if (!camp.equals(c) && camp.getCode().equals(c.getCode())
							&& camp.getCodeRI() == c.getCodeRI()) {
						if (camp.getCodeRI() != FormationContinue.CODE) {
							addErrorMessage(null, "ERROR.CAMP.JUST_ONE_YEAR",
									camp.getCodAnu());
						} else if (camp.getCodeRI() == FormationContinue.CODE) {
							addErrorMessage(null, "ERROR.CAMP.JUST_ONE_YEAR_SFC",
									camp.getCodAnu());
						}
						ctrlOk = false;
						break;
					}
				}
				for (Campagne camp : camps) {
					// on ne peut pas avoir 2 campagnes avec le même codAnu
					// sauf si l'une est en FC et l'autre non
					if (!camp.equals(c) && camp.getCodAnu().equals(c.getCodAnu())
							&& camp.getCodeRI() == c.getCodeRI()) {
						if (camp.getCodeRI() != FormationContinue.CODE) {
							addErrorMessage(null, "ERROR.CAMP.JUST_ONE_ANU",
									camp.getCode());
						} else if (camp.getCodeRI() == FormationContinue.CODE) {
							addErrorMessage(null, "ERROR.CAMP.JUST_ONE_ANU_SFC",
									camp.getCode());
						}
						ctrlOk = false;
						break;
					}
				}
				for (Campagne camp : camps) {
					// on ne peut pas avoir 2 campagnes FI en service
					if (!camp.equals(c) && !(rI instanceof FormationInitiale)
							&& camp.getTemoinEnService().equals(c.getTemoinEnService())
							&& camp.getCodeRI() == c.getCodeRI()) {
						addErrorMessage(null, "ERROR.CAMP.JUST_ONE_EN_SERV");
						ctrlOk = false;
						break;
					}
				}
			}
		
		}catch(Exception e){
			//Si le contrôle du code anu renvoi une erreur du type SOAPFaultException c'est que la campagne n'existe pas dans Apogee
			if (e instanceof javax.xml.ws.soap.SOAPFaultException) {
				addErrorMessage(null, "ERROR.CAMP.COD_ANU_APOGEE");
			}
			
			ctrlOk=false;
		}

		if (log.isDebugEnabled()) {
			log.debug("leaving ctrlAdd return = " + ctrlOk);
		}
		return ctrlOk;
	}

	private boolean ctrlDelete(final Nomenclature nom) {
		return getParameterService().canDeleteNomclature(nom);
	}
	
	public List<SelectItem> getTypeConvocationsItems() {
		List<SelectItem> s = new ArrayList<>();
		s.add(new SelectItem("", ""));
		for (TypeConvocation t : getTypeConvocations())
            s.add(new SelectItem(t.getCode(), t.getLabel()));
		Collections.sort(s, new ComparatorSelectItem());
		return s;
	}

	public List<SelectItem> getTypeDecisionItems() {
		return typesDecItems;
	}

	public List<SelectItem> getTypeDecisionInUseItems() {
		return typesDecInUseItems;
	}
	
	public List<SelectItem> getRegimeInscriptionsItems() {
		List<SelectItem> s = new ArrayList<>();
		Map<Integer, RegimeInscription> mapRI = getRegimeIns();
		for (Map.Entry<Integer, RegimeInscription> entryRI : mapRI.entrySet())
            s.add(new SelectItem(entryRI.getKey(), entryRI.getValue().getLabel()));
		Collections.sort(s, new ComparatorSelectItem());
		return s;
	}

	public List<SelectItem> getRegimeInscriptionsItemsConv() {
		List<SelectItem> s = new ArrayList<>();
		Map<Integer, RegimeInscription> mapRI = getRegimeIns();
		for (Map.Entry<Integer, RegimeInscription> entryRI : mapRI.entrySet())
            s.add(new SelectItem(entryRI.getValue(), entryRI.getValue().getLabel()));
		Collections.sort(s, new ComparatorSelectItem());
		return s;
	}

	public List<RegimeInscription> getAllRegimeInscription() {
		List<RegimeInscription> listeRI = new ArrayList<RegimeInscription>();
		for (Map.Entry<Integer, RegimeInscription> ri : getRegimeIns().entrySet()) {
			listeRI.add(ri.getValue());
		}
		return listeRI;
	}
	
	/**
	 * @return la première valeur de la liste des régimes d'inscriptions comme valeur par défaut proposée
	 * sinon 0
	 */
	public int getRIByDefault(){
		List<RegimeInscription> allRI = getAllRegimeInscription();
		if(!allRI.isEmpty()){
			RegimeInscription ri = allRI.get(0);
		
			if (ri!=null)
				return ri.getCode();
		}
		return 0;
	}

	public boolean isRightPjForAllVet() {
		if (getSessionController().isAllViewPJ()) {
			return true;
		} else if (StringUtils.hasText(getCurrentGest().getCodeCge())
					|| (getCurrentGest().getRightOnCmi() != null
							&& !getCurrentGest().getRightOnCmi().isEmpty())) {
			return false;
		}
		
		return true;
	}

	public String getRegimeInscription(){
		Map<Integer, RegimeInscription> mapRI = getRegimeIns();
		for (Map.Entry<Integer, RegimeInscription> entryRI : mapRI.entrySet()) {
			if (entryRI.getValue().getCode()==codeRI)
				return entryRI.getValue().getLabel();
		}
		
		return null;
	}

	public Boolean getIsFinal() {
		return isFinal;
	}

	public void setIsFinal(final Boolean isFinal) {
		this.isFinal = isFinal;
	}

	public Set<NomenclaturePojo> getAllPieceJustificatives() {
		Set<NomenclaturePojo> nom = new TreeSet<>(new ComparatorString(NomenclaturePojo.class));
		
		for (PieceJustificative m : getParameterService().getPJs(null))
            nom.add(new NomenclaturePojo(m, getRegimeIns().get(m.getCodeRI())));
		
		return nom;
	}

	public List<NomenclaturePojo> getPieceJustificatives() {
		Set<NomenclaturePojo> nom = new TreeSet<NomenclaturePojo>(new ComparatorString(NomenclaturePojo.class));
		Set<VersionEtpOpi> listEtpByRight = Utilitaires.getListEtpByRight(getCurrentGest());
		
		for (PieceJustificative m : getParameterService().getPJs(null)) {
			if (getSessionController().isAllViewPJ()) {
				nom.add(new NomenclaturePojo(m, getRegimeIns().get(m.getCodeRI()), true));
			} else if (StringUtils.hasText(getCurrentGest().getCodeCge())) {
				boolean allRight = true;
				boolean isCreatePj = false;
				for (PieceJustiVet pjVet : m.getVersionEtapes()) {
					if (Utilitaires.isEtpInCge(pjVet.getVersionEtpOpi().getCodEtp(),
							getCurrentGest().getCodeCge(),
							getDomainApoService())) {
						isCreatePj = true;
					} else {
						allRight = false;
					}
				}
				if (isCreatePj) {
					nom.add(new NomenclaturePojo(m, getRegimeIns().get(m.getCodeRI()), allRight));
				}
			} else if (getCurrentGest().getRightOnCmi() != null
					&& !getCurrentGest().getRightOnCmi().isEmpty()) {
				boolean allRight = true;
				boolean isCreatePj = false;
				for (PieceJustiVet pjVet : m.getVersionEtapes()) {
					if (listEtpByRight.contains(pjVet.getVersionEtpOpi())) {
						isCreatePj = true;
					} else {
						allRight = false;
					}
				}
				if (isCreatePj) {
					nom.add(new NomenclaturePojo(m, getRegimeIns().get(m.getCodeRI()), allRight));
				}
			} else {
				nom.add(new NomenclaturePojo(m, getRegimeIns().get(m.getCodeRI()), true));
			}
		}
		
		return new ArrayList<NomenclaturePojo>(nom);
	}

	public List<NomenclaturePojo> getPieceJustificativesSelected() {
		List<NomenclaturePojo> nom = getPieceJustificatives();
		
		for (NomenclaturePojo np : nom) {
			if (!np.getNomenclature().getCode().equals(getCode().getValue()))
				nom.remove(np);
		}
		
		return nom;
	}

	public List<NomenclaturePojo> getPieceJustificativesItems() {
        return new ArrayList<>(getPieceJustificatives());
	}
	
	/**
	 * @return pieces justificative d'une vet sous forme de NomenclaturePojo
	 */
	
	public Set<NomenclaturePojo> getPiecesJToNomenclaturePojo() {
		Set<NomenclaturePojo> nom =
                new TreeSet<>(new ComparatorString(NomenclaturePojo.class));

		List<PieceJustificative> pjs = getParameterService().getPiecesJ(
				new VersionEtpOpi(vetDTO), null);
		List<PieceJustificative> pjsTemp = new ArrayList<>();
		pjsTemp.addAll(pjs);

        for (PieceJustificative pj : pjsTemp)
            for (NomenclaturePojo np : deletePJs) {
                String codeTemp = np.getNomenclature().getCode();
                if (codeTemp.equalsIgnoreCase(pj.getCode()))
                    pjs.remove(pj);
            }

		for (PieceJustificative m : pjs) {
			NomenclaturePojo np = new NomenclaturePojo(m, getRegimeIns().get(
					m.getCodeRI()));

			if (!isInSet(np, allPJs))
				nom.add(np);
		}

		allPJs.addAll(nom);

		return allPJs;
	}

	public List<TypeTraitement> getTypeTrts() {
		return asList(TypeTraitement.values());
	}

	public Set<NomenclaturePojo> getMotivationsAvis() {
		Set<MotivationAvis> mo = getParameterService().getMotivationsAvis(true);
		Set<NomenclaturePojo> nom = new TreeSet<>(new ComparatorString(NomenclaturePojo.class));
		for (MotivationAvis m : mo) {
			nom.add(new NomenclaturePojo(m));
		}
		return nom;
	}

	public Set<NomenclaturePojo> getAllMotivationsAvis() {
		Set<MotivationAvis> mo = getParameterService().getMotivationsAvis(null);
		Set<NomenclaturePojo> nom = new TreeSet<>(new ComparatorString(NomenclaturePojo.class));
		for (MotivationAvis m : mo) {
			nom.add(new NomenclaturePojo(m));
		}
		return nom;
	}

	public List<NomenclaturePojo> getAllMotivationsAvisItems() {
        return new ArrayList<>(getAllMotivationsAvis());
	}
	
	public Set<NomenclaturePojo> getCampagnes() {
		Set<Campagne> ca = getParameterService().getCampagnes(null, null);
		Set<NomenclaturePojo> nom = new TreeSet<>(new ComparatorString(NomenclaturePojo.class));
		for (Campagne c : ca)
            nom.add(new NomenclaturePojo(c, getRegimeIns().get(c.getCodeRI())));
		return nom;
	}

	public List<NomenclaturePojo> getCampagnesInUse() {
        return new ArrayList<>(getCampagnes());
	}

	public List<Campagne> getCampagnesItems() {
		List<Campagne> l = new ArrayList<>(getParameterService().getCampagnes(true, null));
		Collections.sort(l, new ComparatorString(Campagne.class));
		return l;
	}

	public void ajouterFichierPJ() {        
	        // Prepare file and outputstream.
	    File file = null;
	    OutputStream output = null;
	        
	    try {
	        	
	       if (FilenameUtils.getExtension(uploadedFile.getFileName()).equalsIgnoreCase("txt") || FilenameUtils.getExtension(uploadedFile.getFileName()).equalsIgnoreCase("pdf")
	        	|| FilenameUtils.getExtension(uploadedFile.getFileName()).equalsIgnoreCase("doc") || FilenameUtils.getExtension(uploadedFile.getFileName()).equalsIgnoreCase("odt")
	        	|| FilenameUtils.getExtension(uploadedFile.getFileName()).equalsIgnoreCase("xls")){
	    	   // Create file with unique name in upload folder and write to it.
	    	   file = new File(uploadPath,uploadedFile.getFileName());
	    	   output = new FileOutputStream(file);
	    	   IOUtils.copy(uploadedFile.getInputstream(), output);
	    	   fileName = file.getName();

	    	   // Show succes message.
	    	   addInfoMessage(null,"PJ.FILE_IS_ADDED");
	            
	    	   PieceJustificative pj = (PieceJustificative) nomenclature;
	    	   pj.setNomDocument(fileName);
	    	   getParameterService().updateNomenclature(pj);
	        }else 
	        		throw new IOException();
	 
	        } catch (IOException e) {
	            // Cleanup.
	           if (file != null) file.delete();

	            // Show error message.
	           addErrorMessage(null, "PJ.FILE_ADDED_FAILED");

	            // Always log stacktraces (with a real logger).
	           e.printStackTrace();
	        } finally {
	           IOUtils.closeQuietly(output);
	        }
	}

	public void removeFile(){
		PieceJustificative pj = (PieceJustificative)nomenclature;
		pj.setNomDocument(null);
		getParameterService().updateNomenclature(pj);
		addInfoMessage(null, "PJ.REMOVED_FILE");
	}

	public List<TypeConvocation> getTypeConvocations() {
		return getParameterService().getTypeConvocations();
	}

	public List<TypeDecision> getTypeDecisions() {
		return typesDec;
	}

	public List<TypeDecision> getTypeDecisionsSorted() {
		typesDec = new ArrayList<TypeDecision>(
				getParameterService().getTypeDecisions(null));
		sortedTypesDec = new ArrayList<TypeDecision>(typesDec);
		Collections.sort(sortedTypesDec,
				new ComparatorString(TypeDecision.class));
		return sortedTypesDec;
	}

	public List<TypeDecision> getTypeDecisionsInUse() {
		return typesDecInUse;
	}

	public List<TypeDecision> getFinalTypesDecisions() {
		return new ArrayList<TypeDecision>(
				iterableStream(sortedTypesDec).filter(
						new F<TypeDecision, Boolean>() {
							public Boolean f(TypeDecision t) {
								return getIsFinal() == t.getIsFinal();
							}
						}).toCollection());
	}

	public Nomenclature getNomenclature() {
		return nomenclature;
	}

	public void setNomenclature(final Nomenclature nomenclature) {
		//Clone est utilisé afin que l'utilisateur puisse modifier l'objet sans toucher au CACHE (par référence ?)
		//Problème rencontré lors d'une modification annulée(par exemple), le cache était tout de même modifié
		if (nomenclature instanceof TypeDecision) {
			TypeDecision t = (TypeDecision) nomenclature;
			this.nomenclature = t.clone();
		} else if (nomenclature instanceof PieceJustificative) {
			PieceJustificative p = (PieceJustificative) nomenclature;
			this.nomenclature = p.clone();
		} else if (nomenclature instanceof MotivationAvis) {
			MotivationAvis m = (MotivationAvis) nomenclature;
			this.nomenclature = m.clone();
		} else if (nomenclature instanceof Campagne) {
			Campagne m = (Campagne) nomenclature;
			this.nomenclature = m.clone();
		}
	}

	public ActionEnum getActionEnum() {
		return actionEnum;
	}

	public void setActionEnum(final ActionEnum actionEnum) {
		this.actionEnum = actionEnum;
	}

	public void setAddNomenclatures(final List<Nomenclature> addNomenclatures) {
		this.addNomenclatures = addNomenclatures;
	}

	public List<Nomenclature> getAddNomenclatures() {
		return addNomenclatures;
	}

	public WayfEnum getWayfEnum() {
		return wayfEnum;
	}

	public void setWayfEnum(final WayfEnum wayfEnum) {
		this.wayfEnum = wayfEnum;
	}

	public Set<PieceJustiVetPojo> getAllEtapes() {
		return allEtapes;
	}

	public List<PieceJustiVetPojo> getAllEtapesItems() {
        return new ArrayList<>(getAllEtapes());
	}

	public void setAllEtapes(final Set<PieceJustiVetPojo> allEtapes) {
		this.allEtapes = allEtapes;
	}

	public PieceJustiVetPojo getEtapeTraitee() {
		return etapeTraitee;
	}

	public void setEtapeTraitee(final PieceJustiVetPojo etapeTraitee) {
		this.etapeTraitee = etapeTraitee;
	}

	public Object[] getObjectToAdd() {
		return objectToAdd;
	}

	public void setObjectToAdd(final Object[] objectToAdd) {
		this.objectToAdd = objectToAdd;
	}

	public void setEtapeController(final EtapeController etapeController) {
		this.etapeController = etapeController;
	}

	public Set<PieceJustiVetPojo> getDeleteEtapes() {
		return deleteEtapes;
	}

	public void setDeleteEtapes(final Set<PieceJustiVetPojo> deleteEtapes) {
		this.deleteEtapes = deleteEtapes;
	}

	public void setInscriptionAdm(final InscriptionAdm inscriptionAdm) {
		this.inscriptionAdm = inscriptionAdm;
	}

	public VersionEtapeDTO getVetDTO() {
		return vetDTO;
	}

	public void setVetDTO(VersionEtapeDTO vetDTO) {
		this.vetDTO = vetDTO;
	}

	public int getCodeRI() {
		return codeRI;
	}

	public void setCodeRI(int codeRI) {
		this.codeRI = codeRI;
	}

	public List<NomenclaturePojo> getAllPJs() {
		return new ArrayList<NomenclaturePojo>(allPJs);
	}

	public void setAllPJs(Collection<NomenclaturePojo> allPJs) {
		Set<NomenclaturePojo> set = new TreeSet<NomenclaturePojo>(
				new ComparatorString(NomenclaturePojo.class));
		set.addAll(allPJs);
		this.allPJs = set;
	}

	public HtmlInputText getCode() {
		return code;
	}

	public void setCode(HtmlInputText code) {
		this.code = code;
	}

	public void setPJTraitee(NomenclaturePojo pJTraitee) {
		PJTraitee = pJTraitee;
	}

	public NomenclaturePojo getPJTraitee() {
		return PJTraitee;
	}

	public String getUploadPath() {
		return uploadPath;
	}

	public void setUploadPath(String uploadPath) {
		this.uploadPath = uploadPath;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public boolean getUseUpload() {
		return useUpload;
	}

	public void setUseUpload(final boolean useUpload) {
		this.useUpload = useUpload;
	}

}
