/**
 * ESUP-Portail Blank Application - Copyright (c) 2006 ESUP-Portail consortium
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.domain.beans.user;



import org.esupportail.commons.utils.strings.StringUtils;

import org.esupportail.opi.domain.beans.NormeSI;

/**
 * The class that represent users.
 */
public abstract class User extends NormeSI {
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 9108580316214008120L;

	/*
	 ******************* PROPERTIES ******************* */

	
   
    
    /**
     * The prefered language.
     * Default value = "fr"
     */
    private String language;
    
    /**
     * The SurName.
     */
    private String nomPatronymique;
    
    /**
     * The name.
     */
    private String nomUsuel;
    
    /**
     * The firstName.
     */
    private String prenom;
    
    /**
     * The firstName2.
     */
    private String prenom2;
    
    /**
	 * The mail adress.
	 */
	private String adressMail;
	
    
    /*
	 ******************* INIT ************************* */
	
    
    /**
	 * Bean constructor.
	 */
	public User() {
		super();
		language = "fr";
	}

	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "User#" + hashCode() + "language=[" + language 
		+ "], nomPatronymique=[" + nomPatronymique + "], prenom=[" + prenom 
		+ "], prenom2=[" + prenom2 + "], nomUsuel=[" + nomUsuel 
		+ "], adressMail=[" + adressMail + "],    [" + super.toString() + "]]";
	}

	
	/**
	 * Return u with attributes of this.
	 * @param u
	 * @return Traitement
	 */
	protected User clone(final User u) {
		User user = u;
		user = (User) super.clone(user);
		user.setLanguage(language);
		user.setNomPatronymique(nomPatronymique);
		user.setPrenom(prenom);
		user.setPrenom2(prenom2);
		user.setNomUsuel(nomUsuel);
		user.setAdressMail(adressMail);
		
		return user; 
	}

	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the language
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @param language the language to set
	 */
	public void setLanguage(final String language) {
		this.language = StringUtils.nullIfEmpty(language);
	}

	/**
	 * @return the nomPatronymique
	 */
	public String getNomPatronymique() {
		return nomPatronymique;
	}

	/**
	 * @param nomPatronymique the nomPatronymique to set
	 */
	public void setNomPatronymique(String nomPatronymique) {
		this.nomPatronymique = nomPatronymique;
	}

	/**
	 * @return the nomUsuel
	 */
	public String getNomUsuel() {
		return nomUsuel;
	}

	/**
	 * @param nomUsuel the nomUsuel to set
	 */
	public void setNomUsuel(String nomUsuel) {
		this.nomUsuel = nomUsuel;
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
	 * @return the prenom2
	 */
	public String getPrenom2() {
		return prenom2;
	}


	/**
	 * @param prenom2 the prenom2 to set
	 */
	public void setPrenom2(String prenom2) {
		this.prenom2 = prenom2;
	}


	/**
	 * @return the adressMail
	 */
	public String getAdressMail() {
		return adressMail;
	}

	/**
	 * @param adressMail the adressMail to set
	 */
	public void setAdressMail(String adressMail) {
		this.adressMail = adressMail;
	}
    

}
