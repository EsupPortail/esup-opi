/**
 * 
 */
package org.esupportail.opi.web.beans.beanEnum;

/**
 * This class defines the current action. 
 * @author cleprous
 */
public class ActionEnum {

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Value to the add action.
	 */
	public static final String ADD_ACTION = "ADD";
	

	/**
	 * Value to the delete action.
	 */
	public static final String DELETE_ACTION = "DELETE";
	
	/**
	 * Value to the update action.
	 */
	public static final String UPDATE_ACTION = "UPDATE";
	
	/**
	 * Value to the read action.
	 */
	public static final String READ_ACTION = "READ";
	
	/**
	 * Value to the confirmation action.
	 */
	public static final String CONFIRM_ACTION = "CONFIRM";
	
	/**
	 * Value to see the commissions Items.
	 */
	public static final String SEE_SELECT_CMI = "SEE_SELECT_CMI";
	
	/**
	 * Value to the SEND_MAIL action.
	 */
	public static final String SEND_MAIL = "SEND_MAIL";

	/**
	 * Value to the LISTE_PREPA action.
	 */
	public static final String LISTE_PREPA = "LISTE_PREPA";

	/**
	 * Value to the LISTE_PM action.
	 */
	public static final String LISTE_PM = "LISTE_PM";
	
	/**
	 * Value to the PROPOSITION action.
	 */
	public static final String PROPOSITION_ACTION = "PROPOSITION_ACTION";
	
	/**
	 * Value to the select the column to export csv action.
	 */
	public static final String CHOICE_COL_EXPORT = "CHOICE_COL_EXPORT";

	/**
	 * Value to the empty action.
	 */
	public static final String EMPTY_ACTION = "EMPTY";
	
	/**
	 * Value to the ORDONNE action.
	 */
	public static final String PARAM_ORDONNE = "PARAM_ORDONNE";
	
	/**
	 * Value to the ABSCISSE action.
	 */
	public static final String PARAM_ABSCISSE = "PARAM_ABSCISSE";
	
	/**
	 * Contains what do action.
	 * Default value : empty.
	 */
	private String whatAction;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public ActionEnum() {
		super();
		whatAction = EMPTY_ACTION;
	}
	

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the whatAction
	 */
	public String getWhatAction() {
		return whatAction;
	}

	/**
	 * @param whatAction the whatAction to set
	 */
	public void setWhatAction(final String whatAction) {
		this.whatAction = whatAction;
	}

	
	/*----------------------------------------
	 *  GETTERS POUR JSF
	 */
	
	/**
	 * @return the ADD_ACTION
	 */
	public String getAddAction() {
		return ADD_ACTION;
	}
	
	/**
	 * @return the UPDATE_ACTION
	 */
	public String getUpdateAction() {
		return UPDATE_ACTION;
	}
	
	/**
	 * @return the DELETE_ACTION
	 */
	public String getDeleteAction() {
		return DELETE_ACTION;
	}
	
	/**
	 * @return the READ_ACTION
	 */
	public String getReadAction() {
		return READ_ACTION;
	}
	
	/**
	 * @return the CONFIRM_ACTION
	 */
	public String getConfirmAction() {
		return CONFIRM_ACTION;
	}
	
	/**
	 * @return the SEE_SELECT_CMI
	 */
	public String getSeeSelectCmi() {
		return SEE_SELECT_CMI;
	}
	
	/**
	 * @return the EMPTY_ACTION
	 */
	public String getEmptyAction() {
		return EMPTY_ACTION;
	}
	
	/**
	 * @return the SEND_MAIL
	 */
	public String getSendMail() {
		return SEND_MAIL;
	}


	/**
	 * @return the LISTE_PREPA
	 */
	public String getListePrepa() {
		return LISTE_PREPA;
	}

	/**
	 * @return the LISTE_PM
	 */
	public String getListePm() {
		return LISTE_PM;
	}
	
	/**
	 * @return the PROPOSITION_ACTION
	 */
	public String getPropositionAction() {
		return PROPOSITION_ACTION;
	}
	
	/**
	 * @return the CHOICE_COL_EXPORT
	 */
	public String getChoiceColExport() {
		return CHOICE_COL_EXPORT;
	}
	
	/**
	 * @return the PARAM_ORDONNE
	 */
	public String getParamOrdonne() {
		return PARAM_ORDONNE;
	}
	
	/**
	 * @return the PARAM_ABSCISSE
	 */
	public String getParamAbscisse() {
		return PARAM_ABSCISSE;
	}

}
