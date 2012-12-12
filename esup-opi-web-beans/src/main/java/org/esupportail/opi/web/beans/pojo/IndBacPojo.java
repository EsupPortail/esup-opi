/**
*CRI - Universite de Rennes1 - 57SI-CIES - 2007
* ????
* Version de la norme de developpement : 0.9.0
*/
/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;


import org.esupportail.opi.domain.beans.user.indcursus.IndBac;
import org.esupportail.wssi.services.remote.*;

/**
 * @author leproust cedric
 *
 */
public class IndBacPojo {

	/**
	 * A logger.
	 */
//	private final Logger log = new LoggerImpl(getClass());
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * The Cursus.
	 */
	private IndBac indBac;
	
	/**
	 * The Pays.
	 */
	private Pays pays;
	
	/**
	 * The Pays.
	 */
	private Departement departement;
	
	/**
	 * The commune of indBac.
	 */
	private CommuneDTO commune;
	
	/**
	 * The MentionNivBac.
	 */
	private MentionNivBac mentionNivBac;
	
	/**
	 * The Etablissement.
	 */
	private Etablissement etablissement;
	
	/**
	 * The BacOuxEqu.
	 */
	private BacOuxEqu bacOuxEqu;
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructor.
	 */
	public IndBacPojo() {
		super();
		this.indBac = null;
	}
	
	/**
	 * Constructor.
	 * @param indBac 
	 */
	public IndBacPojo(final IndBac indBac) {
		super();
		this.indBac = indBac;
		
	}
	

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "IndBacPojo#" + hashCode() + "[indBac: " + indBac + "]";
	}

	/*
	 ******************* METHODS ********************** */
	
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @return the indBac
	 */
	public IndBac getIndBac() {
		return indBac;
	}

	/**
	 * @param indBac the indBac to set
	 */
	public void setIndBac(final IndBac indBac) {
		this.indBac = indBac;
	}

	/**
	 * @return the pays
	 */
	public Pays getPays() {
		return pays;
	}

	/**
	 * @param pays the pays to set
	 */
	public void setPays(final Pays pays) {
		this.pays = pays;
	}

	/**
	 * @return the departement
	 */
	public Departement getDepartement() {
		return departement;
	}

	/**
	 * @param departement the departement to set
	 */
	public void setDepartement(final Departement departement) {
		this.departement = departement;
	}

	/**
	 * @return the etablissement
	 */
	public Etablissement getEtablissement() {
		return etablissement;
	}

	/**
	 * @param etablissement the etablissement to set
	 */
	public void setEtablissement(final Etablissement etablissement) {
		this.etablissement = etablissement;
	}

	/**
	 * @return the mentionNivBac
	 */
	public MentionNivBac getMentionNivBac() {
		return mentionNivBac;
	}

	/**
	 * @param mentionNivBac the mentionNivBac to set
	 */
	public void setMentionNivBac(final MentionNivBac mentionNivBac) {
		this.mentionNivBac = mentionNivBac;
	}

	/**
	 * @return the bacOuxEqu
	 */
	public BacOuxEqu getBacOuxEqu() {
		return bacOuxEqu;
	}

	/**
	 * @param bacOuxEqu the bacOuxEqu to set
	 */
	public void setBacOuxEqu(final BacOuxEqu bacOuxEqu) {
		this.bacOuxEqu = bacOuxEqu;
	}

	/**
	 * @return the commune
	 */
	public CommuneDTO getCommune() {
		return commune;
	}

	/**
	 * @param commune the commune to set
	 */
	public void setCommune(final CommuneDTO commune) {
		this.commune = commune;
	}

	
}
