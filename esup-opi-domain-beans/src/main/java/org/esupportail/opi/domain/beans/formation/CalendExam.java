/**
* CRI - Université de Rennes1 - 57SI-OPIR1 - 2008
* https://subversion.univ-rennes1.fr/repos/57SI-apo-cri-webservice
* Version de la norme de développement : 0.9.0
*/
package org.esupportail.opi.domain.beans.formation;

import java.io.Serializable;
import java.sql.Date;


/**
 * @author gmartel
 */
public class CalendExam implements Serializable {


	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serializable id. 
	 */
	private static final long serialVersionUID = 1537657389170538363L;

	/**
	 * Code Période-Epreuve-Salle-Année. 
	 */
	private Integer codPes;
	
	/**
	 * Code Etape. 
	 */
	private String codEtp;
	
	/**
	 * Code Version Etape. 
	 */
	private Integer codVrsVet;
	
	/**
	 * Code Année universitaire.
	 */
	private String codAnu;
	
	/**
	 * Code maquette de calendrier.
	 */
	private String codCln;
	
	/**
	 * Code épreuve.
	 */
	private String codEpr;
	
	/**
	 * Date de début d'affichage du calendrier.
	 */
	private Date dateDebAff;
	
	/**
	 * Date de l'épreuve.
	 */
	private Date dateDebPes;
	
	/**
	 * Date de fin d'affichage du calendrier.
	 */
	private Date dateFinAff;
	
	/**
	 * Heure de début de l'épreuve (HH).
	 */
	private Integer heurePes;
	
	/**
	 * Minutes de début de l'épreuve (MM).
	 */
	private Integer minutePes;
	
	/**
	 * Durée de l'examen (en minutes).
	 */
	private Integer dureeExam;
	
	/**
	 * Libellé du bâtiment.
	 */
	private String libBatiment;
	
	/**
	 * Libellé de l'épreuve.
	 */
	private String libEpr;
	
	/**
	 * Code période d'examens.
	 */
	private String codPxa;
	
	/**
	 * Libellé de la période d'examens.
	 */
	private String libPxa;
	
	/**
	 * Libellé de la salle.
	 */
	private String libSalle;
	
	/**
	 * Nombre d'étudiant concernés par l'épreuve.
	 */
	private Integer nbrEtuInsPes;
	
	/**
	 * Code nature de l'épreuve.
	 */
	private String codNep;
	
	/**
	 * Date de maj du calendrier.
	 */
	private Date dateMaj;
	
	/**
	 * Témoin d'existence d'un tiers temps sur l'épreuve.
	 */
	private String temTrsPes;
	
	/*
	 ******************* INIT ******************* */
	
	/**
	 * Constructor.
	 */
	public CalendExam() {
		super();
	}
	
	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CalendExam#" + hashCode() + "[codEtp=[" + codEtp + "], codVrsVet=["	+ codVrsVet.toString()
		+ "], codPes=[" + codPes.toString() + "], codAnu=[" + codAnu + "], codCln=[" + codCln
		+ "], codPxa=[" + codPxa + "], codEpr=[" + codEpr + "], dateDebAff=[" + dateDebAff
		+ "], dateFinAff=[" + dateFinAff + "], dateDebPes=[" + dateDebPes + "]]";
	}
	
	
	/** 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codAnu == null) ? 0 : codAnu.hashCode());
		result = prime * result + ((codCln == null) ? 0 : codCln.hashCode());
		result = prime * result + ((codEpr == null) ? 0 : codEpr.hashCode());
		result = prime * result + ((codEtp == null) ? 0 : codEtp.hashCode());
		result = prime * result + ((codPes == null) ? 0 : codPes.hashCode());
		result = prime * result + ((codPxa == null) ? 0 : codPxa.hashCode());
		result = prime * result
				+ ((codVrsVet == null) ? 0 : codVrsVet.hashCode());
		return result;
	}

	/** 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CalendExam other = (CalendExam) obj;
		if (codAnu == null) {
			if (other.codAnu != null)
				return false;
		} else if (!codAnu.equals(other.codAnu))
			return false;
		if (codCln == null) {
			if (other.codCln != null)
				return false;
		} else if (!codCln.equals(other.codCln))
			return false;
		if (codEpr == null) {
			if (other.codEpr != null)
				return false;
		} else if (!codEpr.equals(other.codEpr))
			return false;
		if (codEtp == null) {
			if (other.codEtp != null)
				return false;
		} else if (!codEtp.equals(other.codEtp))
			return false;
		if (codPes == null) {
			if (other.codPes != null)
				return false;
		} else if (!codPes.equals(other.codPes))
			return false;
		if (codPxa == null) {
			if (other.codPxa != null)
				return false;
		} else if (!codPxa.equals(other.codPxa))
			return false;
		if (codVrsVet == null) {
			if (other.codVrsVet != null)
				return false;
		} else if (!codVrsVet.equals(other.codVrsVet))
			return false;
		return true;
	}


	
	/*
	 ******************* ACCESSORS ******************* */
	

	/**
	 * @return the codEtp
	 */
	public String getCodEtp() {
		return codEtp;
	}

	/**
	 * @param codEtp the codEtp to set
	 */
	public void setCodEtp(final String codEtp) {
		this.codEtp = codEtp;
	}

	/**
	 * @return the codVrsVet
	 */
	public Integer getCodVrsVet() {
		return codVrsVet;
	}

	/**
	 * @param codVrsVet the codVrsVet to set
	 */
	public void setCodVrsVet(final Integer codVrsVet) {
		this.codVrsVet = codVrsVet;
	}

	/**
	 * @return the codPes
	 */
	public Integer getCodPes() {
		return codPes;
	}

	/**
	 * @param codPes the codPes to set
	 */
	public void setCodPes(final Integer codPes) {
		this.codPes = codPes;
	}

	/**
	 * @return the codAnu
	 */
	public String getCodAnu() {
		return codAnu;
	}

	/**
	 * @param codAnu the codAnu to set
	 */
	public void setCodAnu(final String codAnu) {
		this.codAnu = codAnu;
	}

	/**
	 * @return the codCln
	 */
	public String getCodCln() {
		return codCln;
	}

	/**
	 * @param codCln the codCln to set
	 */
	public void setCodCln(final String codCln) {
		this.codCln = codCln;
	}

	/**
	 * @return the codEpr
	 */
	public String getCodEpr() {
		return codEpr;
	}

	/**
	 * @param codEpr the codEpr to set
	 */
	public void setCodEpr(final String codEpr) {
		this.codEpr = codEpr;
	}

	/**
	 * @return the dateDebAff
	 */
	public Date getDateDebAff() {
		return dateDebAff;
	}

	/**
	 * @param dateDebAff the dateDebAff to set
	 */
	public void setDateDebAff(final Date dateDebAff) {
		this.dateDebAff = dateDebAff;
	}

	/**
	 * @return the dateDebPes
	 */
	public Date getDateDebPes() {
		return dateDebPes;
	}

	/**
	 * @param dateDebPes the dateDebPes to set
	 */
	public void setDateDebPes(final Date dateDebPes) {
		this.dateDebPes = dateDebPes;
	}

	/**
	 * @return the dateFinAff
	 */
	public Date getDateFinAff() {
		return dateFinAff;
	}

	/**
	 * @param dateFinAff the dateFinAff to set
	 */
	public void setDateFinAff(final Date dateFinAff) {
		this.dateFinAff = dateFinAff;
	}

	/**
	 * @return the heurePes
	 */
	public Integer getHeurePes() {
		return heurePes;
	}

	/**
	 * @param heurePes the heurePes to set
	 */
	public void setHeurePes(final Integer heurePes) {
		this.heurePes = heurePes;
	}

	/**
	 * @return the minutePes
	 */
	public Integer getMinutePes() {
		return minutePes;
	}

	/**
	 * @param minutePes the minutePes to set
	 */
	public void setMinutePes(final Integer minutePes) {
		this.minutePes = minutePes;
	}

	/**
	 * @return the dureeExam
	 */
	public Integer getDureeExam() {
		return dureeExam;
	}

	/**
	 * @param dureeExam the dureeExam to set
	 */
	public void setDureeExam(final Integer dureeExam) {
		this.dureeExam = dureeExam;
	}

	/**
	 * @return the libBatiment
	 */
	public String getLibBatiment() {
		return libBatiment;
	}

	/**
	 * @param libBatiment the libBatiment to set
	 */
	public void setLibBatiment(final String libBatiment) {
		this.libBatiment = libBatiment;
	}

	/**
	 * @return the libEpr
	 */
	public String getLibEpr() {
		return libEpr;
	}

	/**
	 * @param libEpr the libEpr to set
	 */
	public void setLibEpr(final String libEpr) {
		this.libEpr = libEpr;
	}

	/**
	 * @return the codPxa
	 */
	public String getCodPxa() {
		return codPxa;
	}

	/**
	 * @param codPxa the codPxa to set
	 */
	public void setCodPxa(String codPxa) {
		this.codPxa = codPxa;
	}

	/**
	 * @return the libPxa
	 */
	public String getLibPxa() {
		return libPxa;
	}

	/**
	 * @param libPxa the libPxa to set
	 */
	public void setLibPxa(final String libPxa) {
		this.libPxa = libPxa;
	}

	/**
	 * @return the libSalle
	 */
	public String getLibSalle() {
		return libSalle;
	}

	/**
	 * @param libSalle the libSalle to set
	 */
	public void setLibSalle(final String libSalle) {
		this.libSalle = libSalle;
	}

	/**
	 * @return the nbrEtuInsPes
	 */
	public Integer getNbrEtuInsPes() {
		return nbrEtuInsPes;
	}

	/**
	 * @param nbrEtuInsPes the nbrEtuInsPes to set
	 */
	public void setNbrEtuInsPes(final Integer nbrEtuInsPes) {
		this.nbrEtuInsPes = nbrEtuInsPes;
	}

	/**
	 * @return the codNep
	 */
	public String getCodNep() {
		return codNep;
	}

	/**
	 * @param codNep the codNep to set
	 */
	public void setCodNep(final String codNep) {
		this.codNep = codNep;
	}

	/**
	 * @return the dateMaj
	 */
	public Date getDateMaj() {
		return dateMaj;
	}

	/**
	 * @param dateMaj the dateMaj to set
	 */
	public void setDateMaj(final Date dateMaj) {
		this.dateMaj = dateMaj;
	}

	/**
	 * @return the temTrsPes
	 */
	public String getTemTrsPes() {
		return temTrsPes;
	}

	/**
	 * @param temTrsPes the temTrsPes to set
	 */
	public void setTemTrsPes(final String temTrsPes) {
		this.temTrsPes = temTrsPes;
	}

	
}
