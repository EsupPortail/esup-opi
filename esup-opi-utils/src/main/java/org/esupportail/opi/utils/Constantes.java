package org.esupportail.opi.utils;

public final class Constantes {
    private Constantes() { throw new UnsupportedOperationException(); }

    // ------- Business code ------------------------ //
	/**Code for profile Member.*/
	public static final String COD_PRO_MEMBER = "MEMBRE";
	
	// ------- Regex ------------------------ //
	/**Post code regex.*/
	public static final String CODEPOSTREGEX = "[0-9]{5}";
	/**Email regex.*/
//	public static final String MAILREGEX = "^[a-zA-Z][\\w\\.-]*[a-zA-Z0-9]@[a-zA-Z0-9][\\w\\.-]" 
//		+ "*[a-zA-Z0-9]\\.[a-zA-Z][a-zA-Z\\.]*[a-zA-Z]$";
	public static final String MAILREGEX = "[a-zA-Z0-9-_]+(?:\\.[a-zA-Z0-9-_]+)*@(?:[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?\\.)+[a-zA-Z0-9](?:[a-zA-Z0-9-]*[a-zA-Z0-9])?";
	/** */
	public static final String[] HQLREGEX = {"'"};
	
	// ------ Control nb vows ------------------------ //
	/** The error field empty key.*/
	public static final Integer DEFAULT_NB_VOEU_BY_CGE = 5;
	
	// ------ Message keys ------------------------ //
	/** The error field empty key.*/
	public static final String I18N_EMPTY = "ERROR.FIELD.EMPTY";
	
	/** The value not exsite in APOGEE.*/
	public static final String I18N_NOT_EXSIT = "ERROR.VALUE_NOT_EXIST";
	
	/** The date period field key .*/
	public static final String I18N_FIELD_DAT_PERIOD = "FIELD_LABEL.DAT_PERIOD";
	
	/** The women field key .*/
	public static final String I18N_CIV_MM = "INDIVIDU.CIVILITE.MM";
	
	/** The mister field key .*/
	public static final String I18N_CIV_MR = "INDIVIDU.CIVILITE.MR";
	
	
	// ------ Init form ------------------------ //	
	/** The code of France used to initialized on it the country list.*/
	public static final String CODEFRANCE = "100";
	
	/** The code of Departement used to selected .*/
	public static final String COD_DEP_ETR = "099";
	
	/** The code of individu's sexe (woman) .*/
	public static final String COD_SEXE_FEMININ = "F";

	/** The code of individu's sexe (man) .*/
	public static final String COD_SEXE_MASCULIN = "M";
	
	
	/** The string length limit.*/
	public static final int STR_LENGTH_LIMIT = 40;
	
	/** The string length limit for short string.*/
	public static final int STR_LENGTH_LIMIT_SMALL = 31;


	// ------ Map keys for Adress type ------------------------ //
	/** Current key for Adresse map.*/
	public static final String ADR_CURRENT = "current";
	/** Fix key for Adresse map.*/
	public static final String ADR_FIX = "fix";
	/** Fix key for Adresse map.*/
	public static final String ADR_CMI = "commission";
	
	
	// ------ Flag in Apogee ------------------------ //
	/** Flag Yes in Apogee.*/
	public static final String FLAG_YES = "O";
	/** Flag No in Apogee.*/
	public static final String FLAG_NO = "N";
	
	// ------ Date format ------------------------ //
	/** The format for the years.*/
	public static final String YEAR_FORMAT = "yyyy";
	/** The format date to display : dd/MM/yyyy.*/
	public static final String DATE_FORMAT = "dd/MM/yyyy";
	/** The short format date : ddMMyyyy.*/
	public static final String DATE_SHORT_FORMAT = "ddMMyyyy";
	/** The full format date to display.*/
	public static final String DATE_COMPLETE_FORMAT = "EEEE d MMMM yyyy";
	/** The format date to display.*/
	public static final String HOUR_FORMAT = "HH:mm";
	/** The format date and hour to display.*/
	public static final String DATE_HOUR_FORMAT = Constantes.DATE_FORMAT + " " + Constantes.HOUR_FORMAT;
	
	/** The format date to look for in dataBase. dd-MM-yyyy*/
	public static final String APOGEE_DATE_FORMAT = "dd-MM-yyyy HH:mm:ss";
	
	/** The format date to look for in dataBase.*/
	public static final String ENGLISH_DATE_FORMAT = "yyyy-MM-dd";
	
	
	// ------ CONTENT TYPE ------------------------ //
	/** for file text.*/
	public static final String HTTP_TYPE_TEXT = "application/text";
	/** for file excel.*/
	public static final String HTTP_TYPE_EXCEL = "application/vnd.ms-excel";
	/** for file pdf.*/
	public static final String HTTP_TYPE_PDF = "application/pdf";
	/** for file zip.*/
	public static final String HTTP_TYPE_ZIP = "application/x-zip-compressed";
	
	
	// ------ FILE XSL FOR EXPORT PDF ------------------------ //
	/**  Name of xsl file to print dossier individu. */
	public static final String DOSSIER_IND_XSL = "dossierIndividu.xsl";
	/**  Name of xsl file to print dossier individu FC. */
	public static final String DOSSIER_IND_FC_XSL = "dossierIndividuFC.xsl";
	/** */
	public static final String NOMINATION_XSL = "nomination.xsl";
	/** */
	public static final String LISTE_PREPA_ALPHA_XSL = "listePrepaAlpha.xsl";
	/** */
	public static final String LISTE_PREPA_ETAPE_XSL = "listePrepaEtape.xsl";
	/** */
	public static final String LISTE_PREPA_TITRE_XSL = "listePrepaTitre.xsl";
	/** */
	public static final String LISTE_VALIDATION_AVIS_XSL = "listeValidationAvis.xsl";
	/** */
	public static final String TAB_STATISTIQUE = "tabStatistique.xsl";
	/** */
	public static final String NOTIFICATION_IND_XSL = "notificationIndividu.xsl";
}
