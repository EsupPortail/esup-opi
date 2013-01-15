/**
 * 
 */
package org.esupportail.opi.domain.beans.parameters.accessRight;

import java.io.Serializable;


/**
 * @author cleprous
 *
 */
public class AccessType implements Serializable {

	
	/**
	 * Code for type access ADD.
	 */
	public static String COD_ADD = "ADD";
	
	/**
	 * Code for type access READ.
	 */
	public static String COD_READ = "READ";
	
	/**
	 * Code for type access UPDATE.
	 */
	public static String COD_UPDATE = "UPDATE";
	
	/**
	 * Code for type access DELETE.
	 */
	public static String COD_DELETE = "DELETE";
	
	/**
	 *  The serialization id.
	 */
	private static final long serialVersionUID = -6313001610122212297L;

	
	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The AccessType code.
	 */
	private String code;
	
	/**
	 * The comment.
	 */
	private String comment;
	
	/**
	 * Le libelle de l'objet.
	 */
	private String libelle;
	
	/**
	 * The rang.
	 */
	private Integer rang;
	
	/*
	 ******************* INIT ************************* */
	
	
	/**
	 * Constructors.
	 */
	public AccessType() {
		super();
	}

	
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((comment == null) ? 0 : comment.hashCode());
		result = prime * result + ((libelle == null) ? 0 : libelle.hashCode());
		result = prime * result + ((rang == null) ? 0 : rang.hashCode());
		return result;
	}




	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) { return true; }
		if (obj == null) { return false; }
		if (!(obj instanceof AccessType)) { return false; }
		AccessType other = (AccessType) obj;
		if (code == null) {
			if (other.getCode() != null) { return false; }
		} else if (!code.equals(other.code)) { return false; }
		if (comment == null) {
			if (other.getComment() != null) { return false; }
		} else if (!comment.equals(other.getComment())) { return false; }
		if (libelle == null) {
			if (other.getLibelle() != null) { return false; }
		} else if (!libelle.equals(other.getLibelle())) { return false; }
		if (rang == null) {
			if (other.getRang() != null) { return false; }
		} else if (!rang.equals(other.getRang())) { return false; }
		return true;
	}




	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccessType#" + hashCode() + "[code=[" + code + "],libelle=[" + libelle 
				+ "]comment=[" + comment + "]rang=[" + rang + "]  ]";
	}
	
	
	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	




	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}

	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}

	/**
	 * @return the rang
	 */
	public Integer getRang() {
		return rang;
	}

	/**
	 * @param rang the rang to set
	 */
	public void setRang(Integer rang) {
		this.rang = rang;
	}

	/**
	 * @return the libelle
	 */
	public String getLibelle() {
		return libelle;
	}

	/**
	 * @param libelle the libelle to set
	 */
	public void setLibelle(String libelle) {
		this.libelle = libelle;
	}
	

	

}
