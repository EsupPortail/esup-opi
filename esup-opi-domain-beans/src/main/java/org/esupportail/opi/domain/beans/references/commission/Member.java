/**
 * 
 */
package org.esupportail.opi.domain.beans.references.commission;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.Gestionnaire;

/**
 * @author cleprous
 * 
 */
public class Member extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 3454447349899240390L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The member name.
	 */
	private String nom;

	/**
	 * The member firstName.
	 */
	private String prenom;

	/**
	 * The member adressMail.
	 */
	private String adressMail;

	/**
	 * The member type.
	 */
	private String type;

	/**
	 * The member manager.
	 */
	private Gestionnaire gestionnaire;

	/**
	 * The commission's member.
	 */
	private Commission commission;

	/*
	 ******************* INIT ************************* */

	/**
	 * Constructors.
	 */
	public Member() {
		super();
		type = "";
	}

	/**
	 * Constructors.
	 * 
	 * @param nom
	 * @param prenom
	 * @param mail
	 * @param type
	 * @param gestionnaire
	 */
	public Member(final String nom, final String prenom, final String mail,
			final String type, final Gestionnaire gestionnaire) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.adressMail = mail;
		this.type = type;
		this.gestionnaire = gestionnaire;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Membre#" + hashCode() + "[nom=[" + nom + "],[prenom=[" + prenom
				+ "],[adressMail=[" + adressMail + "],[type=[" + type + "],  ["
				+ super.toString() + "]]";
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result
				+ ((adressMail == null) ? 0 : adressMail.hashCode());
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((prenom == null) ? 0 : prenom.hashCode());
		return result;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (!super.equals(obj)) {
			return false;
		}
		if (!(obj instanceof Member)) {
			return false;
		}
		Member other = (Member) obj;
		if (adressMail == null) {
			if (other.getAdressMail() != null) {
				return false;
			}
		} else if (!adressMail.equals(other.getAdressMail())) {
			return false;
		}
		if (nom == null) {
			if (other.getNom() != null) {
				return false;
			}
		} else if (!nom.equals(other.getNom())) {
			return false;
		}
		if (prenom == null) {
			if (other.getPrenom() != null) {
				return false;
			}
		} else if (!prenom.equals(other.getPrenom())) {
			return false;
		}
		return true;
	}

	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */

	/**
	 * @return the nom
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * @param nom
	 *            the nom to set
	 */
	public void setNom(String nom) {
		this.nom = nom;
	}

	/**
	 * @return the prenom
	 */
	public String getPrenom() {
		return prenom;
	}

	/**
	 * @param prenom
	 *            the prenom to set
	 */
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}

	/**
	 * @return the adressMail
	 */
	public String getAdressMail() {
		return adressMail;
	}

	/**
	 * @param adressMail
	 *            the adressMail to set
	 */
	public void setAdressMail(String adressMail) {
		this.adressMail = adressMail;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the gestionnaire
	 */
	public Gestionnaire getGestionnaire() {
		return gestionnaire;
	}

	/**
	 * @param gestionnaire
	 *            the gestionnaire to set
	 */
	public void setGestionnaire(Gestionnaire gestionnaire) {
		this.gestionnaire = gestionnaire;
	}

	/**
	 * @return the commission
	 */
	public Commission getCommission() {
		return commission;
	}

	/**
	 * @param commission
	 *            the commission to set
	 */
	public void setCommission(Commission commission) {
		this.commission = commission;
	}

}
