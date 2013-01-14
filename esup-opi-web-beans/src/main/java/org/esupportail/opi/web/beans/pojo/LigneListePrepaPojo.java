package org.esupportail.opi.web.beans.pojo;

import java.util.Date;


public class LigneListePrepaPojo {

	private String commission;
	
	private String num_Dos_OPI;
	
	private String nom_Patrony;
	
	private String prenom;
	
	private Date date_Naiss;

	private String code_clef_INE;
	
	private String adresse_1;
	
	private String adresse_2;
	
	private String adresse_3;
	
	private String cedex;
	
	private String code_Postal;
	
	private String lib_Commune;
	
	private String lib_Pays;
	
	private String telephone_fixe;
	
	private String mail;
	
	private String bac;
	
	private String dernier_Etab_Cur;
	
	private String dernier_Etab_Etb;
	
	private String dernier_Etab_Result_Ext;
	
	private String date_depot_voeu;
	
	private String type_Traitement;
	
	private String voeu_Lib_Vet;
	
	private String etat_Voeu;
	
	private String avis_Result_Lib;
	
	private String rang;
	
	private String avis_Motivation_Commentaire;
	
	private String avis_Result_Code;
	
	private String avis_Result_Code_Apogee;
	
	private String avis_temoin_validation;
	
	private String avis_date_validation;

	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public LigneListePrepaPojo() {
		super();
	}

	public LigneListePrepaPojo(final String commission, final String num_Dos_OPI, final String nom_Patrony, final String prenom) {
//			  		final Date date_Naiss, final String code_clef_INE, final String adresse_1, final String adresse_2,
//			  		final String adresse_3, final String cedex, final String code_Postal, final String lib_Commune,
//			  		final String lib_Pays, final String telephone_fixe, final String mail, final String bac, final String dernier_Etab_Cur,
//			  		final String dernier_Etab_Etb, final String dernier_Etab_Result_Ext, final String date_depot_voeu,
//			  		final String type_Traitement, final String voeu_Lib_Vet, final String etat_Voeu, final String avis_Result_Lib,
//			  		final String rang, final String avis_Motivation_Commentaire, final String avis_Result_Code,
//			  		final String avis_Result_Code_Apogee, final String avis_temoin_validation, final String avis_date_validation) {
			super();
			this.commission = commission;
			this.num_Dos_OPI = num_Dos_OPI;
			this.nom_Patrony = nom_Patrony;
			this.prenom = prenom;
//			this.date_Naiss = date_Naiss;
//			this.code_clef_INE = code_clef_INE;
//			this.adresse_1 = adresse_1;
//			this.adresse_2 = adresse_2;
//			this.adresse_3 = adresse_3;
//			this.cedex = cedex;
//			this.code_Postal = code_Postal;
//			this.lib_Commune = lib_Commune;
//			this.lib_Pays = lib_Pays;
//			this.telephone_fixe = telephone_fixe;
//			this.mail = mail;
//			this.bac = bac;
//			this.dernier_Etab_Cur = dernier_Etab_Cur;
//			this.dernier_Etab_Etb = dernier_Etab_Etb;
//			this.dernier_Etab_Result_Ext = dernier_Etab_Result_Ext;
//			this.date_depot_voeu = date_depot_voeu;
//			this.type_Traitement = type_Traitement;
//			this.voeu_Lib_Vet = voeu_Lib_Vet;
//			this.etat_Voeu = etat_Voeu;
//			this.avis_Result_Lib = avis_Result_Lib;
//			this.rang = rang;
//			this.avis_Motivation_Commentaire = avis_Motivation_Commentaire;
//			this.avis_Result_Code = avis_Result_Code;
//			this.avis_Result_Code_Apogee = avis_Result_Code_Apogee;
//			this.avis_temoin_validation = avis_temoin_validation;
//			this.avis_date_validation = avis_date_validation;
	}

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * @return the mail
	 */
	public String getMail() {
		return mail;
	}

	/**
	 * @param mail the mail to set
	 */
	public void setMail(String mail) {
		this.mail = mail;
	}

	/**
	 * @return the commission
	 */
	public String getCommission() {
		return commission;
	}

	/**
	 * @param commission the commission to set
	 */
	public void setCommission(String commission) {
		this.commission = commission;
	}

	/**
	 * @return the num_Dos_OPI
	 */
	public String getNum_Dos_OPI() {
		return num_Dos_OPI;
	}

	/**
	 * @param num_Dos_OPI the num_Dos_OPI to set
	 */
	public void setNum_Dos_OPI(String num_Dos_OPI) {
		this.num_Dos_OPI = num_Dos_OPI;
	}

	/**
	 * @return the nom_Patrony
	 */
	public String getNom_Patrony() {
		return nom_Patrony;
	}

	/**
	 * @param nom_Patrony the nom_Patrony to set
	 */
	public void setNom_Patrony(String nom_Patrony) {
		this.nom_Patrony = nom_Patrony;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the date_Naiss
	 */
	public Date getDate_Naiss() {
		return date_Naiss;
	}

	/**
	 * @param date_Naiss the date_Naiss to set
	 */
	public void setDate_Naiss(Date date_Naiss) {
		this.date_Naiss = date_Naiss;
	}

	/**
	 * @return the code_clef_INE
	 */
	public String getCode_clef_INE() {
		return code_clef_INE;
	}

	/**
	 * @param code_clef_INE the code_clef_INE to set
	 */
	public void setCode_clef_INE(String code_clef_INE) {
		this.code_clef_INE = code_clef_INE;
	}

	/**
	 * @return the adresse_1
	 */
	public String getAdresse_1() {
		return adresse_1;
	}

	/**
	 * @param adresse_1 the adresse_1 to set
	 */
	public void setAdresse_1(String adresse_1) {
		this.adresse_1 = adresse_1;
	}

	/**
	 * @return the adresse_2
	 */
	public String getAdresse_2() {
		return adresse_2;
	}

	/**
	 * @param adresse_2 the adresse_2 to set
	 */
	public void setAdresse_2(String adresse_2) {
		this.adresse_2 = adresse_2;
	}

	/**
	 * @return the adresse_3
	 */
	public String getAdresse_3() {
		return adresse_3;
	}

	/**
	 * @param adresse_3 the adresse_3 to set
	 */
	public void setAdresse_3(String adresse_3) {
		this.adresse_3 = adresse_3;
	}

	/**
	 * @return the cedex
	 */
	public String getCedex() {
		return cedex;
	}

	/**
	 * @param cedex the cedex to set
	 */
	public void setCedex(String cedex) {
		this.cedex = cedex;
	}

	/**
	 * @return the code_Postal
	 */
	public String getCode_Postal() {
		return code_Postal;
	}

	/**
	 * @param code_Postal the code_Postal to set
	 */
	public void setCode_Postal(String code_Postal) {
		this.code_Postal = code_Postal;
	}

	/**
	 * @return the lib_Commune
	 */
	public String getLib_Commune() {
		return lib_Commune;
	}

	/**
	 * @param lib_Commune the lib_Commune to set
	 */
	public void setLib_Commune(String lib_Commune) {
		this.lib_Commune = lib_Commune;
	}

	/**
	 * @return the lib_Pays
	 */
	public String getLib_Pays() {
		return lib_Pays;
	}

	/**
	 * @param lib_Pays the lib_Pays to set
	 */
	public void setLib_Pays(String lib_Pays) {
		this.lib_Pays = lib_Pays;
	}

	/**
	 * @return the telephone_fixe
	 */
	public String getTelephone_fixe() {
		return telephone_fixe;
	}

	/**
	 * @param telephone_fixe the telephone_fixe to set
	 */
	public void setTelephone_fixe(String telephone_fixe) {
		this.telephone_fixe = telephone_fixe;
	}

	/**
	 * @return the bac
	 */
	public String getBac() {
		return bac;
	}

	/**
	 * @param bac the bac to set
	 */
	public void setBac(String bac) {
		this.bac = bac;
	}

	/**
	 * @return the dernier_Etab_Cur
	 */
	public String getDernier_Etab_Cur() {
		return dernier_Etab_Cur;
	}

	/**
	 * @param dernier_Etab_Cur the dernier_Etab_Cur to set
	 */
	public void setDernier_Etab_Cur(String dernier_Etab_Cur) {
		this.dernier_Etab_Cur = dernier_Etab_Cur;
	}

	/**
	 * @return the dernier_Etab_Etb
	 */
	public String getDernier_Etab_Etb() {
		return dernier_Etab_Etb;
	}

	/**
	 * @param dernier_Etab_Etb the dernier_Etab_Etb to set
	 */
	public void setDernier_Etab_Etb(String dernier_Etab_Etb) {
		this.dernier_Etab_Etb = dernier_Etab_Etb;
	}

	/**
	 * @return the dernier_Etab_Result_Ext
	 */
	public String getDernier_Etab_Result_Ext() {
		return dernier_Etab_Result_Ext;
	}

	/**
	 * @param dernier_Etab_Result_Ext the dernier_Etab_Result_Ext to set
	 */
	public void setDernier_Etab_Result_Ext(String dernier_Etab_Result_Ext) {
		this.dernier_Etab_Result_Ext = dernier_Etab_Result_Ext;
	}

	/**
	 * @return the date_depot_voeu
	 */
	public String getDate_depot_voeu() {
		return date_depot_voeu;
	}

	/**
	 * @param date_depot_voeu the date_depot_voeu to set
	 */
	public void setDate_depot_voeu(String date_depot_voeu) {
		this.date_depot_voeu = date_depot_voeu;
	}

	/**
	 * @return the type_Traitement
	 */
	public String getType_Traitement() {
		return type_Traitement;
	}

	/**
	 * @param type_Traitement the type_Traitement to set
	 */
	public void setType_Traitement(String type_Traitement) {
		this.type_Traitement = type_Traitement;
	}

	/**
	 * @return the voeu_Lib_Vet
	 */
	public String getVoeu_Lib_Vet() {
		return voeu_Lib_Vet;
	}

	/**
	 * @param voeu_Lib_Vet the voeu_Lib_Vet to set
	 */
	public void setVoeu_Lib_Vet(String voeu_Lib_Vet) {
		this.voeu_Lib_Vet = voeu_Lib_Vet;
	}

	/**
	 * @return the etat_Voeu
	 */
	public String getEtat_Voeu() {
		return etat_Voeu;
	}

	/**
	 * @param etat_Voeu the etat_Voeu to set
	 */
	public void setEtat_Voeu(String etat_Voeu) {
		this.etat_Voeu = etat_Voeu;
	}

	/**
	 * @return the avis_Result_Lib
	 */
	public String getAvis_Result_Lib() {
		return avis_Result_Lib;
	}

	/**
	 * @param avis_Result_Lib the avis_Result_Lib to set
	 */
	public void setAvis_Result_Lib(String avis_Result_Lib) {
		this.avis_Result_Lib = avis_Result_Lib;
	}

	/**
	 * @return the rang
	 */
	public String getRang() {
		return rang;
	}

	/**
	 * @param rang the rang to set
	 */
	public void setRang(String rang) {
		this.rang = rang;
	}

	/**
	 * @return the avis_Motivation_Commentaire
	 */
	public String getAvis_Motivation_Commentaire() {
		return avis_Motivation_Commentaire;
	}

	/**
	 * @param avis_Motivation_Commentaire the avis_Motivation_Commentaire to set
	 */
	public void setAvis_Motivation_Commentaire(String avis_Motivation_Commentaire) {
		this.avis_Motivation_Commentaire = avis_Motivation_Commentaire;
	}

	/**
	 * @return the avis_Result_Code
	 */
	public String getAvis_Result_Code() {
		return avis_Result_Code;
	}

	/**
	 * @param avis_Result_Code the avis_Result_Code to set
	 */
	public void setAvis_Result_Code(String avis_Result_Code) {
		this.avis_Result_Code = avis_Result_Code;
	}

	/**
	 * @return the avis_Result_Code_Apogee
	 */
	public String getAvis_Result_Code_Apogee() {
		return avis_Result_Code_Apogee;
	}

	/**
	 * @param avis_Result_Code_Apogee the avis_Result_Code_Apogee to set
	 */
	public void setAvis_Result_Code_Apogee(String avis_Result_Code_Apogee) {
		this.avis_Result_Code_Apogee = avis_Result_Code_Apogee;
	}

	/**
	 * @return the avis_temoin_validation
	 */
	public String getAvis_temoin_validation() {
		return avis_temoin_validation;
	}

	/**
	 * @param avis_temoin_validation the avis_temoin_validation to set
	 */
	public void setAvis_temoin_validation(String avis_temoin_validation) {
		this.avis_temoin_validation = avis_temoin_validation;
	}

	/**
	 * @return the avis_date_validation
	 */
	public String getAvis_date_validation() {
		return avis_date_validation;
	}

	/**
	 * @param avis_date_validation the avis_date_validation to set
	 */
	public void setAvis_date_validation(String avis_date_validation) {
		this.avis_date_validation = avis_date_validation;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "LigneListePrepaPojo#" + hashCode()
			+ "[commission =" + commission
			+ "], [num_Dos_OPI =" + num_Dos_OPI
			+ "], [nom_Patrony =" + nom_Patrony
			+ "], [prenom =" + prenom
			+ "], [date_Naiss =" + date_Naiss
			+ "], [code_clef_INE =" + code_clef_INE
			+ "], [adresse_1 =" + adresse_1
			+ "], [adresse_2 =" + adresse_2
			+ "], [adresse_3 =" + adresse_3
			+ "], [cedex =" + cedex
			+ "], [code_Postal =" + code_Postal
			+ "], [lib_Commune =" + lib_Commune
			+ "], [lib_Pays =" + lib_Pays
			+ "], [telephone_fixe =" + telephone_fixe
			+ "], [mail =" + mail
			+ "], [bac =" + bac
			+ "], [dernier_Etab_Cur =" + dernier_Etab_Cur
			+ "], [dernier_Etab_Etb =" + dernier_Etab_Etb
			+ "], [dernier_Etab_Result_Ext =" + dernier_Etab_Result_Ext
			+ "], [date_depot_voeu =" + date_depot_voeu
			+ "], [type_Traitement =" + type_Traitement
			+ "], [voeu_Lib_Vet =" + voeu_Lib_Vet
			+ "], [etat_Voeu =" + etat_Voeu
			+ "], [avis_Result_Lib =" + avis_Result_Lib
			+ "], [rang =" + rang
			+ "], [avis_Motivation_Commentaire =" + avis_Motivation_Commentaire
			+ "], [avis_Result_Code =" + avis_Result_Code
			+ "], [avis_Result_Code_Apogee =" + avis_Result_Code_Apogee
			+ "], [avis_temoin_validation =" + avis_temoin_validation
			+ "], [avis_date_validation =" + avis_date_validation + "]";
	}
}
