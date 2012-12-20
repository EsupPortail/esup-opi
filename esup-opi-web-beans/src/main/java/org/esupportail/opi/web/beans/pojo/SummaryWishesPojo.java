/**
*CRI - Universite de Rennes1 - 57SI-CIES - 2007
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import org.esupportail.opi.domain.beans.parameters.PieceJustificative;
import org.esupportail.opi.domain.beans.references.commission.Commission;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;



/**
 * @author leproust cedric
 * SummaryWishesPojo : cet objet comprend les voeux du candidat par commission 
 * avec les pieces justificatives qu'il doit fournir.
 */
public class SummaryWishesPojo implements Serializable {

	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = -5101692025826113322L;

	/**
	 * A logger.
	 */
//	private final Logger log = new LoggerImpl(getClass());
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * see {@link Commission}.
	 * Used to alert the user to the deadline for return of records.
	 */
	private CommissionPojo commission;
	
	/**
	 * The individu's vows managed by this commission and the forms by wish.
	 * Default value : empty
	 */
	private Set<IndVoeuPojo> vows;

	/**
	 * The individu's vows managed by this commission and the forms by wish.
	 * Default value : empty
	 */
	private List<IndVoeuPojo> vowsAsList;
	
	/**
	 * documents to be provided with the dossier.
	 */
	private List<PieceJustificative> pieces;
	
	/**
	 * True if all vows if AccesSelectif then canDonwload document.
	 * Default value : false. 
	 */
	private Boolean canDonwload;
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public SummaryWishesPojo() {
		super();
		canDonwload = false;
		vows = new HashSet<IndVoeuPojo>();
	}
	
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "SummaryWishesPojo#" + hashCode() + "[commission: " + commission + "],[pieces: " + pieces + "]";
	}

	/*
	 ******************* METHODS ********************** */

	

	
	/*
	 ******************* ACCESSORS ******************** */
	
	
	
	
	/**
	 * @return the commission
	 */
	public CommissionPojo getCommission() {
		return commission;
	}



	/**
	 * @param commission the commission to set
	 */
	public void setCommission(final CommissionPojo commission) {
		this.commission = commission;
	}



	/**
	 * @return the vows
	 */
	public Set<IndVoeuPojo> getVows() {
		return vows;
	}

	/**
	 * @return the avis
	 */
	public List<IndVoeuPojo> getVowsAsList() {
		if (!this.vows.isEmpty()) {
			this.vowsAsList = new ArrayList<IndVoeuPojo>(vows);
		}
		return vowsAsList;
	}

	/**
	 * @param vows the vows to set
	 */
	public void setVows(final Set<IndVoeuPojo> vows) {
		this.vows = vows;
	}



	/**
	 * @return the pieces
	 */
	public List<PieceJustificative> getPieces() {
		return pieces;
	}



	/**
	 * @param pieces the pieces to set
	 */
	public void setPieces(final List<PieceJustificative> pieces) {
		this.pieces = pieces;
	}



	/**
	 * @return the canDonwload
	 */
	public Boolean getCanDonwload() {
		return canDonwload;
	}



	/**
	 * @param canDonwload the canDonwload to set
	 */
	public void setCanDonwload(final Boolean canDonwload) {
		this.canDonwload = canDonwload;
	}



	
	
	

	
}
