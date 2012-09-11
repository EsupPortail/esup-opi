/**
 * 
 */
package org.esupportail.opi.domain.beans.user.candidature;

import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.parameters.PieceJustificative;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.Individu;


/**
 * @author cleprous
 *
 */
public class MissingPiece extends NormeSI {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 2716693450288388816L;


	/*
	 ******************* PROPERTIES ******************* */

	/**
	 * The commission who managed the individu's wishes. 
	 */
	private Commission commission;
	
	/**
	 * the Individual.
	 */
	private Individu individu;
	
	/**
	 * The piece.
	 */
	private PieceJustificative piece;
	
	
	/*
	 ******************* INIT ************************* */
	
	

	/**
	 * Constructor.
	 */
	public MissingPiece() {
		super();
	}



	/**
	 * Constructors.
	 * @param commission
	 * @param individu
	 * @param piece
	 */
	public MissingPiece(final Commission commission, final Individu individu,
			final PieceJustificative piece) {
		super();
		this.commission = commission;
		this.individu = individu;
		this.piece = piece;
	}



	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MissingPiece#" + hashCode() 
			+ "],[individu.id=[" + individu.getId()  
			+ "],[piece=[" + piece + "], [" + super.toString() + "]]";
	}




	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((commission == null) ? 0 : commission.getId().hashCode());
		result = prime * result + ((individu == null) ? 0 : individu.getId().hashCode());
		result = prime * result + ((piece == null) ? 0 : piece.hashCode());
		return result;
	}








	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {
		if (this == obj) { return true; }
		if (!super.equals(obj)) { return false; }
		if (getClass() != obj.getClass()) { return false; }
		MissingPiece other = (MissingPiece) obj;
		if (commission == null) { 
			if (other.getCommission() != null) { return false; }
		} else if (!commission.getId().equals(other.getCommission().getId())) { return false; }
		if (individu == null) { 
			if (other.getIndividu() != null) { return false; }
		} else if (!individu.getId().equals(other.getIndividu().getId())) { return false; }
		if (piece == null) {
			if (other.getPiece() != null) { return false; }
		} else if (!piece.equals(other.getPiece())) { return false; }
		return true;
	}



	/*
	 ******************* METHODS ********************** */

	/*
	 ******************* ACCESSORS ******************** */





	/**
	 * @return the piece
	 */
	public PieceJustificative getPiece() {
		return piece;
	}



	/**
	 * @param piece the piece to set
	 */
	public void setPiece(final PieceJustificative piece) {
		this.piece = piece;
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
	 * @return the individu
	 */
	public Individu getIndividu() {
		return individu;
	}



	/**
	 * @param individu the individu to set
	 */
	public void setIndividu(final Individu individu) {
		this.individu = individu;
	}




	
}
