/**
 * 
 */
package org.esupportail.opi.web.beans.beanEnum;

/**
 * This class defines  where are you from.
 * Is use to manage the callback, redirection. 
 * @author cleprous
 */
public class WayfEnum {

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * Value to the manager.
	 */
	public static final String MANAGER_VALUE = "MANAGER";
	
	/**
	 * Value to search vet for the pj.
	 */
	public static final String PJ_VALUE = "PJ";	
	
	/**
	 * Value to affect vet to the pj.
	 */
	public static final String AFFECT_PJ_VALUE = "AFFECT_PJ";

	/**
	 * Value to search the commissions members.
	 */
	public static final String MEMBER_CMI_VALUE = "MEMBER_CMI";
	
	/**
	 * This means that one comes to managing calendars.
	 */
	public static final String CALENDAR_VALUE = "CALENDAR";
	
	/**
	 * This means that one comes to opinions all students.
	 */
	public static final String OPINION_ALL_VALUE = "OPINION_ALL";
	
	/**
	 * This means that one comes to opinions students.
	 */
	public static final String OPINION_VALUE = "OPINION";
	
	/**
	 * Value to the empty value.
	 */
	public static final String EMPTY_VALUE = "EMPTY";
	
	/**
	 * Contains whereAreYouFrom.
	 * Default value : empty.
	 */
	private String whereAreYouFrom;
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public WayfEnum() {
		super();
		whereAreYouFrom = EMPTY_VALUE;
	}
	

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	

	/**
	 * @return the whereAreYouFrom
	 */
	public String getWhereAreYouFrom() {
		return whereAreYouFrom;
	}

	/**
	 * @param whereAreYouFrom the whereAreYouFrom to set
	 */
	public void setWhereAreYouFrom(final String whereAreYouFrom) {
		this.whereAreYouFrom = whereAreYouFrom;
	}

	
	/*----------------------------------------
	 *  GETTERS POUR JSF
	 */
	
	/**
	 * @return the MANAGER_VALUE
	 */
	public String getManagerValue() {
		return MANAGER_VALUE;
	}
	
	
	/**
	 * @return the MEMBER_CMI_VALUE
	 */
	public String getMemberCmiValue() {
		return MEMBER_CMI_VALUE;
	}
	
	/**
	 * @return the CALENDAR_VALUE
	 */
	public String getCalendarValue() {
		return CALENDAR_VALUE;
	}
	
	/**
	 * @return the EMPTY_VALUE
	 */
	public String getEmptyValue() {
		return EMPTY_VALUE;
	}

	/**
	 * @return the PJ_VALUE
	 */
	public String getPJValue() {
		return PJ_VALUE;
	}
	
	/**
	 * @return the AFFECT_PJ_VALUE
	 */
	public String getAffectPJValue() {
		return AFFECT_PJ_VALUE;
	}
	
	/**
	 * @return the OPINION_ALL_VALUE
	 */
	public String getOpinionAllValue() {
		return OPINION_ALL_VALUE;
	}
	
	/**
	 * @return the OPINION_VALUE
	 */
	public String getOpinionValue() {
		return OPINION_VALUE;
	}
	

}
