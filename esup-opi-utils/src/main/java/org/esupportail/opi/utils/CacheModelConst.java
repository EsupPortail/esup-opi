package org.esupportail.opi.utils;

/**
 * Constant class.
 */
public class CacheModelConst {
	/*
	 ******************* PROPERTIES ******************* */
	

	
	
	/**
	 * The name of model for the domain access right cache.
	 * (Profile, Treatment,...)
	 */
	public static final String ACCESS_RIGHT_DEFAULT = "accessRight.default";
	
	
	/**
	 * The name of model for the user cache.
	 * (Gestionnaire, Individu)
	 */
	public static final String USER_MODEL = "user";
	
	/**
	 * The name of model for the missing piece cache.
	 */
	public static final String MISSING_PIECE_MODEL = "missingPiece";
	
	/**
	 * The name of model for the nomenclature cache.
	 * (Type TRaitement, Type decision, ...)
	 */
	public static final String NOMENCLATURE_MODEL = "nomenclature";
	
	/**
	 * The name of model for the references cache.
	 * (Commission, Calendar, ...)
	 */
	public static final String REFERENCES_MODEL = "references";
	
	/**
	 * The name of model for the NombreVoeuCge object cache.
	 */
	public static final String NB_VOEU_CGE_MODEL = "nombreVoeuCge";
	
	
	/**
	 * The name of model for the geographieApogee cache.
	 * (Pays, Departement, ...)
	 */
	public static final String GEO_APOGEE_MODEL = "geographieApogee";
	
	/**
	 * The name of model for the geographieApogee cache.
	 * (Etape, Diplome, ...)
	 */
	public static final String ENS_APOGEE_MODEL = "enseignementApogee";
	
	
	/**
	 * The name of model for the rennes1Apogee cache.
	 * (Annuaire de formation, mot clef ...)
	 */
	public static final String RENNES1_APOGEE_MODEL = "rennes1Apogee";
	
	/**
	 * The name of model to managed the calendar.
	 * (Controle sur les caledriers)
	 */
	public static final String MANAGED_CALENDAR_MODEL = "managedCalendar";
	
	/**
	 * The name of model to managed the calendar of the rendez vous.
	 */
	public static final String RENDEZ_VOUS = "rendezvous";
	
	
	/*
	 ******************* INIT ******************* */
	
	
	/**
	 * Constructors.
	 */
	private CacheModelConst() {
		super();
	}
	
}
