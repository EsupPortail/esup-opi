/**
 * 
 */
package org.esupportail.opi.domain.beans.references.commission;


import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.user.AdresseCommission;

/**
 * @author cleprous
 * 
 */
public class ContactCommission extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -8136257366407434780L;

	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The adress of commission.
	 */
	private AdresseCommission adresse;
	
	/**
	 * Code Regime d'inscription.
	 * Default value 0 --> formation Initiale.
	 * 1 --> Formation Continue
	 */
	private int codeRI;
	
	/**
	 * The signataire code.
	 */
	private String codSig;
	
	/**
	 * The corresponding.
	 */
	private String corresponding;
	
	/**
	 * The commission.
	 */
	private Commission commission;
	
	/**
	 * The manager's name of the commssion.
	 */
	private String managerName;

	/**
	 * The manager's phone number of the commssion.
	 */
	private String managerPhone;

	/**
	 * The manager's mail of the commssion.
	 */
	private String managerMail;
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public ContactCommission() {
		super();
	}

	
	/**
	 * @param codeRI
	 */
	public ContactCommission(final int codeRI) {
		super();
		this.codeRI = codeRI;
	}


	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the adresse
	 */
	public AdresseCommission getAdresse() {
		return adresse;
	}

	/**
	 * @param adresse the adresse to set
	 */
	public void setAdresse(final AdresseCommission adresse) {
		this.adresse = adresse;
	}

	/**
	 * @return the codeRI
	 */
	public int getCodeRI() {
		return codeRI;
	}

	/**
	 * @param codeRI the codeRI to set
	 */
	public void setCodeRI(final int codeRI) {
		this.codeRI = codeRI;
	}

	/**
	 * @return the codSig
	 */
	public String getCodSig() {
		return codSig;
	}

	/**
	 * @param codSig the codSig to set
	 */
	public void setCodSig(final String codSig) {
		this.codSig = codSig;
	}

	/**
	 * @return the corresponding
	 */
	public String getCorresponding() {
		return corresponding;
	}

	/**
	 * @param corresponding the corresponding to set
	 */
	public void setCorresponding(final String corresponding) {
		this.corresponding = corresponding;
	}

	/**
	 * @return the commission
	 */
	public Commission getCommission() {
		return commission;
	}

	/**
	 * @param commission the commission to set
	 */
	public void setCommission(final Commission commission) {
		this.commission = commission;
	}


	/**
	 * @return the managerName
	 */
	public String getManagerName() {
		return managerName;
	}


	/**
	 * @param managerName the managerName to set
	 */
	public void setManagerName(final String managerName) {
		this.managerName = managerName;
	}


	/**
	 * @return the managerPhone
	 */
	public String getManagerPhone() {
		return managerPhone;
	}


	/**
	 * @param managerPhone the managerPhone to set
	 */
	public void setManagerPhone(final String managerPhone) {
		this.managerPhone = managerPhone;
	}


	/**
	 * @return the managerMail
	 */
	public String getManagerMail() {
		return managerMail;
	}


	/**
	 * @param managerMail the managerMail to set
	 */
	public void setManagerMail(final String managerMail) {
		this.managerMail = managerMail;
	}
	
	
	
}
