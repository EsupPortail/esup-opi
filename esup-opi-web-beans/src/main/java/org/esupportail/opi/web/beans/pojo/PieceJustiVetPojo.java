/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;



import org.esupportail.opi.domain.beans.parameters.PieceJustiVet;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;


/**
 * @author cleprous
 *
 */
public class PieceJustiVetPojo {

	
	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The pieceJustiVet.
	 */
    private PieceJustiVet pieceJustiVet;
    
    

	/**
	 * The VersionEtapeDTO.
	 */
	private VersionEtapeDTO versionEtape;

	/**
	 * l'utilisateur possï¿½de tous les droits.
	 */
	private boolean allRight;
	
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Constructors.
	 */
	public PieceJustiVetPojo() {
		super();
		pieceJustiVet = new PieceJustiVet();
	}
	
	/**
	 * 
	 * Constructors.
	 * @param etape
	 * @param codEtp
	 * @param codVrsVet
	 */
	public PieceJustiVetPojo(final VersionEtapeDTO etape) {
		super();
		this.versionEtape = etape;
		this.pieceJustiVet = new PieceJustiVet(etape);
	}
	
	/**
	 * 
	 * Constructors.
	 * @param etape
	 * @param codEtp
	 * @param codVrsVet
	 * @param allRight
	 */
	public PieceJustiVetPojo(final VersionEtapeDTO etape, final boolean allRight) {
		super();
		this.versionEtape = etape;
		this.pieceJustiVet = new PieceJustiVet(etape);
		this.allRight = allRight;
	}
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result;
		if (pieceJustiVet.getVersionEtpOpi().getCodEtp() != null) { 
			result += pieceJustiVet.getVersionEtpOpi().getCodEtp().hashCode(); 
		}
		result = prime * result;
		if (pieceJustiVet.getVersionEtpOpi().getCodVrsVet() != null) {
			result += pieceJustiVet.getVersionEtpOpi().getCodVrsVet().hashCode(); 
		}
		return result;
	}
	
	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {	return true; }
		if (obj == null) { return false; }
		if (getClass() != obj.getClass()) {	return false; }
		PieceJustiVetPojo other = (PieceJustiVetPojo) obj;
		if (pieceJustiVet != null) {
			if (pieceJustiVet.getVersionEtpOpi().getCodEtp() == null) {
				if (other.pieceJustiVet.getVersionEtpOpi().getCodEtp() != null) { return false; }
			} else if (!pieceJustiVet.getVersionEtpOpi().getCodEtp().equals(
					other.pieceJustiVet.getVersionEtpOpi().getCodEtp())) {
				return false;
			}
			
			if (pieceJustiVet.getVersionEtpOpi().getCodVrsVet() == null) {
				if (other.pieceJustiVet.getVersionEtpOpi().getCodVrsVet() != null) { return false; }
			} else if (!pieceJustiVet.getVersionEtpOpi().getCodVrsVet()
					.equals(other.pieceJustiVet.getVersionEtpOpi().getCodVrsVet())) {
				return false;
			}
		} 
		return true;
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PieceJustiVetPojo#" + hashCode() + "[etape=[" + versionEtape
				+ "]]";
	}
	
	
	
	/*
	 ******************* METHODS ********************** */
	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * @return the pieceJustiVet
	 */
	public PieceJustiVet getPieceJustiVet() {
		return pieceJustiVet;
	}


	/**
	 * @param pieceJustiVet the pieceJustiVet to set
	 */
	public void setPieceJustiVet(final PieceJustiVet pieceJustiVet) {
		this.pieceJustiVet = pieceJustiVet;
	}
	/**
	 * @return the versionEtape
	 */
	public VersionEtapeDTO getVersionEtape() {
		return versionEtape;
	}
	/**
	 * @param versionEtape the versionEtape to set
	 */
	public void setVersionEtape(final VersionEtapeDTO versionEtape) {
		this.versionEtape = versionEtape;
	}
	
	/**
	 * @return allRight
	 */
	public boolean isAllRight() {
		return allRight;
	}
	
	/**
	 * @param allRight
	 */
	public void setAllRight(final boolean allRight) {
		this.allRight = allRight;
	}
}
