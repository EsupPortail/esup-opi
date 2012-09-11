package org.esupportail.opi.domain.beans.references.rendezvous;



import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.Individu;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;



/**
 * @author Gomez
 *
 */
public class CalendarRDV extends NormeSI {

	/**
	 * 
	 */
	private static final int NB_MS_BY_HALF_HOUR = 1800000;
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -5951002366822403795L;

	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * A logger.
	 */
	private final Logger logger = new LoggerImpl(getClass());
	/**
	 * titre.
	 */
	private String titre;
	/**
	 * liste de commissions.
	 */
	private Set<Commission> commissions;
	/**
	 * liste de vets.
	 */
	private Set<VetCalendar> vets;
	/**
	 * liste de candidats.
	 */
	private List<IndividuDate> candidats;
	/**
	 * 
	 */
	private Map<Date, Map<Individu, List<IndividuDate>>> candidatsAsMap;
	/**
	 * 
	 */
	private Map<Individu, Date> dateParCandidat;

	/**
	 * code CGE.
	 */
	private String codeCge;
	/**
	 * 
	 */
	private String msgMailConfirmation;
	/**
	 * 
	 */
	private String msgEtudiant;
	/**
	 * 
	 */
	private String msgValidation;
	/**
	 * 
	 */
	private Integer nbreMaxEtud;
	/**
	 * 
	 */
	private Integer nbJoursAvantPro;
	/**
	 * 
	 */
	private Integer nbJoursApresPro;
	/**
	 * 
	 */
	private Date dateDebutInsc;
	/**
	 * 
	 */
	private Date dateFinInsc;
	/**
	 * 
	 */
	private Boolean participeOK;
	/**
	 * 
	 */
	private Map<Date, TrancheFermee> tranchesFermees;
	/**
	 * 
	 */
	private Map<Integer, Horaire> horaires;
	/**
	 * 
	 */
	private Map<Date, JourHoraire> jourHoraires;

	/*
	 ******************* INIT ************************* */
	/**
	 * Constructeur vide pour hibernate.
	 */
	public CalendarRDV() {
		//Constructeur vide pour hibernate
	}


	/*
	 ******************* METHODS ********************** */
	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(final Object obj) {

		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final CalendarRDV other = (CalendarRDV) obj;
		if (!this.getId().equals(other.getId())) {
			return false;
		}
		return true;
	}
	/**
	 * 
	 */
	@Override
	public String toString() {
		return super.toString() + "[titre=[" + titre 
		+ "], codeCge=[" + codeCge + "], msgMailConfirmation=[" + msgMailConfirmation
		+ "], msgEtudiant=[" + msgEtudiant + "], msgValidation=[" + msgValidation
		+ "], nbreMaxEtud=[" + nbreMaxEtud + "], nbJoursAvantPro=[" + nbJoursAvantPro
		+ "], nbJoursApresPro=[" + nbJoursApresPro + "], dateDebutInsc=[" + dateDebutInsc 
		+ "], dateFinInsc=[" + dateFinInsc + "], participeOK=[" + participeOK + "]]";
	}
	/**
	 * 
	 */
	@Override
	public int hashCode() {
		return super.hashCode();
	}

	/**
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CalendarRDV clone() {
		CalendarRDV cal = new CalendarRDV();
		cal = (CalendarRDV) super.clone(cal);
		cal.setTitre(titre);
		cal.setCommissions(commissions);
		cal.setVets(vets);
		cal.setCandidats(candidats);
		cal.setCodeCge(codeCge);
		cal.setMsgMailConfirmation(msgMailConfirmation);
		cal.setMsgEtudiant(msgEtudiant);
		cal.setMsgValidation(msgValidation);
		cal.setNbreMaxEtud(nbreMaxEtud);
		cal.setNbJoursAvantPro(nbJoursAvantPro);
		cal.setNbJoursApresPro(nbJoursApresPro);
		cal.setDateDebutInsc(dateDebutInsc);
		cal.setDateFinInsc(dateFinInsc);
		cal.setParticipeOK(participeOK);
		cal.setTranchesFermees(tranchesFermees);
		cal.setHoraires(horaires);
		return cal;
	}
	/**
	 * 
	 * @param laDate
	 * @return nombre de place libre
	 */
	public int getCountNbPlaceLibre(final Date laDate)  {
		if (getCandidatsAsMap().containsKey(laDate)) {
			return nbreMaxEtud - getCandidatsAsMap().get(laDate).keySet().size();
		}
		
		return nbreMaxEtud;
	}
	
	/**
	 * @return the candidats
	 */
	public Map<Date, Map<Individu, List<IndividuDate>>> getCandidatsAsMap() {
		if (candidatsAsMap == null) {
			candidatsAsMap = new HashMap<Date, Map<Individu, List<IndividuDate>>>();
			for (IndividuDate indDate : candidats) {
				if (candidatsAsMap.containsKey(indDate.getDateRdv())) {
					if (candidatsAsMap.get(indDate.getDateRdv()).
							containsKey(indDate.getCandidat())) {
						candidatsAsMap.get(indDate.getDateRdv()).
								get(indDate.getCandidat()).add(indDate);
					} else {
						List<IndividuDate> listIndDate = new ArrayList<IndividuDate>();
						listIndDate.add(indDate);
						candidatsAsMap.get(indDate.getDateRdv()).put(indDate.getCandidat(),
								listIndDate);
					}
				} else {
					List<IndividuDate> listIndDate = new ArrayList<IndividuDate>();
					listIndDate.add(indDate);
					Map<Individu, List<IndividuDate>> mapIndivListIndDate = 
						new HashMap<Individu, List<IndividuDate>>();
					mapIndivListIndDate.put(indDate.getCandidat(), listIndDate);
					candidatsAsMap.put(indDate.getDateRdv(), 
							mapIndivListIndDate);
				}
			}
		}
		return candidatsAsMap;
	}
	
	/**
	 * @return the candidats
	 */
	public Map<Individu, Date> getDateParCandidat() {
		if (dateParCandidat == null) {
			logger.debug("Dans getDateParCandidat : " + dateParCandidat);
			dateParCandidat = new HashMap<Individu, Date>();
			for (IndividuDate indDate : candidats) {
				if (!dateParCandidat.containsKey(indDate.getCandidat())
						&& indDate.getDateRdv().after(new Date())) {
					dateParCandidat.put(indDate.getCandidat(), indDate.getDateRdv());
				}
			}
			logger.info("En fin de getDateParCandidat : " + dateParCandidat);
		}
		return dateParCandidat;
	}
	
	/**
	 * 
	 */
	public void resetMap() {
		candidatsAsMap = null;
		dateParCandidat = null;
	}
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return titre
	 */
	public String getTitre() {
		return titre;
	}
	/**
	 * 
	 * @param titre
	 */
	public void setTitre(final String titre) {
		this.titre = titre;
	}
	
	/**
	 * 
	 * @return liste de commissions
	 */
	public Set<Commission> getCommissions() {
		return commissions;
	}
	/**
	 * 
	 * @param commissions
	 */
	public void setCommissions(final Set<Commission> commissions) {
		this.commissions = commissions;
	}
	
	/**
	 * 
	 * @return vets
	 */
	public Set<VetCalendar> getVets() {
		return vets;
	}
	/**
	 * 
	 * @param vets
	 */
	public void setVets(final Set<VetCalendar> vets) {
		this.vets = vets;
	}
	
	/**
	 * @return the candidats
	 */
	public List<IndividuDate> getCandidats() {
		return candidats;
	}
	/**
	 * @param candidats the candidats to set
	 */
	public void setCandidats(final List<IndividuDate> candidats) {
		this.candidats = candidats;
	}
	
	/**
	 * 
	 * @return codeCge
	 */
	public String getCodeCge() {
		return codeCge;
	}
	/**
	 * 
	 * @param codeCge
	 */
	public void setCodeCge(final String codeCge) {
		this.codeCge = codeCge;
	}
	
	/**
	 * 
	 * @return msgMailConfirmation
	 */
	public String getMsgMailConfirmation() {
		return msgMailConfirmation;
	}
	/**
	 * 
	 * @param msgMailConfirmation
	 */
	public void setMsgMailConfirmation(final String msgMailConfirmation) {
		this.msgMailConfirmation = msgMailConfirmation;
	}
	
	/**
	 * 
	 * @return msgEtudiant
	 */
	public String getMsgEtudiant() {
		return msgEtudiant;
	}
	/**
	 * 
	 * @param msgEtudiant
	 */
	public void setMsgEtudiant(final String msgEtudiant) {
		this.msgEtudiant = msgEtudiant;
	}
	
	/**
	 * 
	 * @return msgValidation
	 */
	public String getMsgValidation() {
		return msgValidation;
	}
	/**
	 * 
	 * @param msgValidation
	 */
	public void setMsgValidation(final String msgValidation) {
		this.msgValidation = msgValidation;
	}
	
	/**
	 * 
	 * @return nbreMaxEtud
	 */
	public Integer getNbreMaxEtud() {
		return nbreMaxEtud;
	}
	/**
	 * 
	 * @param nbreMaxEtud
	 */
	public void setNbreMaxEtud(final Integer nbreMaxEtud) {
		this.nbreMaxEtud = nbreMaxEtud;
	}
	
	/**
	 * 
	 * @return nbJoursAvantPro
	 */
	public Integer getNbJoursAvantPro() {
		return nbJoursAvantPro;
	}
	/**
	 * 
	 * @param nbJoursAvantPro
	 */
	public void setNbJoursAvantPro(final Integer nbJoursAvantPro) {
		this.nbJoursAvantPro = nbJoursAvantPro;
	}
	
	/**
	 * 
	 * @return nbJoursApresPro
	 */
	public Integer getNbJoursApresPro() {
		return nbJoursApresPro;
	}
	/**
	 * 
	 * @param nbJoursApresPro
	 */
	public void setNbJoursApresPro(final Integer nbJoursApresPro) {
		this.nbJoursApresPro = nbJoursApresPro;
	}
	
	/**
	 * 
	 * @return dateDebutInsc
	 */
	public Date getDateDebutInsc() {
		return dateDebutInsc;
	}
	/**
	 * 
	 * @param dateDebutInsc
	 */
	public void setDateDebutInsc(final Date dateDebutInsc) {
		this.dateDebutInsc = dateDebutInsc;
	}
	
	/**
	 * 
	 * @return dateFinInsc
	 */
	public Date getDateFinInsc() {
		return dateFinInsc;
	}
	/**
	 * 
	 * @param dateFinInsc
	 */
	public void setDateFinInsc(final Date dateFinInsc) {
		this.dateFinInsc = dateFinInsc;
	}
	
	/**
	 * 
	 * @return tranchesFermees
	 */
	public Map<Date, TrancheFermee> getTranchesFermees() {
		return tranchesFermees;
	}
	/**
	 * 
	 * @param tranchesFermees
	 */
	public void setTranchesFermees(final Map<Date, TrancheFermee> tranchesFermees) {
		this.tranchesFermees = tranchesFermees;
	}
	
	/**
	 * 
	 * @return participeOK
	 */
	public Boolean getParticipeOK() {
		return participeOK;
	}
	/**
	 * 
	 * @param participeOK
	 */
	public void setParticipeOK(final Boolean participeOK) {
		this.participeOK = participeOK;
	}
	
	/**
	 * 
	 * @return horaires
	 */
	public Map<Integer, Horaire> getHoraires() {
		return horaires;
	}
	/**
	 * 
	 * @param horaires
	 */
	public void setHoraires(final Map<Integer, Horaire> horaires) {
		this.horaires = horaires;
	}
	/**
	 * 
	 * @return jourHoraires
	 */
	public Map<Date, JourHoraire> getJourHoraires() {
		return jourHoraires;
	}
	/**
	 * 
	 * @param jourHoraires
	 */
	public void setJourHoraires(final Map<Date, JourHoraire> jourHoraires) {
		this.jourHoraires = jourHoraires;
	}
	
	/**
	 * @param etudiant 
	 * @return la liste des dates de RDV possibles
	 * 
	 */
	public List<Date> getDatesRdv(final boolean etudiant) {
		List<Date> liste = new ArrayList<Date>();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G HH:mm:ss z");

		Calendar now = new GregorianCalendar();
		Calendar cal = new GregorianCalendar();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);	
	   	
		// Si c'est pour un etudiant on decale du nb jours parametre
		if (etudiant) {
			cal.set(Calendar.YEAR, now.get(Calendar.YEAR));
			cal.set(Calendar.MONTH, now.get(Calendar.MONTH));
			cal.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));

			// Nb jours ; on veut nbJours avantProp
			// cal.add(Calendar.DAY_OF_YEAR, getNbJoursAvantPro());

			if (cal.getTime().before(getDateDebutInsc())) {
				cal.setTime(getDateDebutInsc());
			}
		} else {
			cal.setTime(getDateDebutInsc());
		}

		logger.info("Recherche des dates dispo depuis " + sdf.format(cal.getTime()).toString());

		// Tant que le nombre de jours ouvres n'est pas atteint et 
		//	que la date de fin d'inscription n'est pas atteinte
		int nbJoursOuvres = 0;
		if (etudiant) {
			nbJoursOuvres -= getNbJoursAvantPro();
		}
		
		logger.debug("Avant while : propositions = " + nbJoursOuvres 
				+ " date = " + cal.getTimeInMillis() + " fin = " + getDateFinInsc().getTime());


		Iterator<Date> it = getTranchesFermees().keySet().iterator();
		while (it.hasNext()) {
			logger.info("Dates fermees : " + it.next());
		}

		
		boolean incrementer = false;
		while (cal.getTimeInMillis() <= getDateFinInsc().getTime()) {
			if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY 
					&& cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY) {
				// Si c'est pour un etudiant et que le nb de jours propose et OK, on quitte.
				if (etudiant && nbJoursOuvres > getNbJoursApresPro()) {
					break;
				} else if (etudiant && nbJoursOuvres == 0) {
					liste = new ArrayList<Date>();
				}

				// gestion des tranches fermees
				Date date = new Date(cal.getTimeInMillis());
				logger.info(date.toString());
				if (getTranchesFermees().containsKey(date)) {
					logger.debug("C'est un jour comprenant une tranche fermee");
					TrancheFermee tmp = getTranchesFermees().get(date);

					if (tmp.isJourFerme()) {
						logger.info("C'est un jour ferme");
					} else if (tmp.isMatin()) {
						logger.info("C'est un matin ferme");
						List<Date> dates = getCreneauxAmidi(etudiant, cal);
						incrementer = !dates.isEmpty();
						liste.addAll(dates);
					} else if (tmp.isAprem()) {
						logger.info("C'est une aprem fermee");
						List<Date> dates = getCreneauxMatin(etudiant, cal);
						incrementer = !dates.isEmpty();
						liste.addAll(dates);
					}
				} else {
					// C'est un jour ouvre ; on incremente le compteur
					List<Date> datesM = getCreneauxMatin(etudiant, cal);
					List<Date> datesA = getCreneauxAmidi(etudiant, cal);
					incrementer = !(datesM.isEmpty() && datesM.isEmpty());
					liste.addAll(datesM);
					liste.addAll(datesA);
				}
				if (incrementer) {
					nbJoursOuvres++;
				}
			}
			cal.add(Calendar.DAY_OF_YEAR, 1);
		}

		return liste;
	}
	
	/**
	 * @param etudiant 
	 * @param cal le jour e etudier
	 * @return la liste des creneaux possibles le matin
	 * 
	 */
	public List<Date> getCreneauxMatin(final boolean etudiant, final Calendar cal) {
		List<Date> liste = new ArrayList<Date>();
		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy.MM.dd G HH:mm:ss z");
		logger.debug("Cal : " + sdf1.format(cal.getTime()));

		// toutes les tranches horaires du matin
		Calendar debMatin = new GregorianCalendar();
		debMatin.setTime(cal.getTime());
		
		logger.info("Horaires : " + getHoraires());
		
		Calendar deb = new GregorianCalendar();
		if (getJourHoraires().get(cal.getTime()) != null) {
			deb.setTimeInMillis(getJourHoraires().get(cal.getTime()).
					getDateDebutMatin().getTime());
		} else {
			deb.setTimeInMillis(getHoraires().get(cal.get(Calendar.MONTH)).
					getDateDebutMatin().getTime());
		}
		debMatin.set(Calendar.HOUR_OF_DAY, deb.get(Calendar.HOUR_OF_DAY));
		debMatin.set(Calendar.MINUTE, deb.get(Calendar.MINUTE));
		debMatin.set(Calendar.SECOND, deb.get(Calendar.SECOND));
		debMatin.set(Calendar.MILLISECOND, deb.get(Calendar.MILLISECOND));

		logger.debug("Debut : " + debMatin.toString());

		Calendar finMatin = new GregorianCalendar();
		finMatin.setTime(cal.getTime());

		Calendar fin = new GregorianCalendar();
		if (getJourHoraires().get(cal.getTime()) != null) {
			fin.setTimeInMillis(getJourHoraires().get(cal.getTime()).
					getDateFinMatin().getTime());
		} else {
			fin.setTimeInMillis(getHoraires().get(cal.get(Calendar.MONTH)).
					getDateFinMatin().getTime());
		}
		finMatin.set(Calendar.HOUR_OF_DAY, fin.get(Calendar.HOUR_OF_DAY));
		finMatin.set(Calendar.MINUTE, fin.get(Calendar.MINUTE));
		finMatin.set(Calendar.SECOND, fin.get(Calendar.SECOND));
		finMatin.set(Calendar.MILLISECOND, fin.get(Calendar.MILLISECOND));

		finMatin.set(Calendar.DAY_OF_YEAR, cal.get(Calendar.DAY_OF_YEAR));
		logger.debug("Fin : " + finMatin.toString());

		for (long tranche = debMatin.getTimeInMillis(); tranche < finMatin.getTimeInMillis(); 
		tranche = tranche + NB_MS_BY_HALF_HOUR) {
			Date date = new Date(tranche);

//			SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd G HH:mm:ss z");

			if (getCandidatsAsMap().containsKey(date) 
					&& getCandidatsAsMap().get(date).keySet().size() >= getNbreMaxEtud()) {
				logger.info("Le creneau " + date.toString() 
						+ "est plein ! On ne propose pas cette date !");
				if (!etudiant) {
					liste.add(new Date(tranche));
				}
			} else {
				liste.add(new Date(tranche));
			}
		}

		return liste;
	}
	
	/**
	 * @param etudiant 
	 * @param cal le jour e etudier
	 * @return la liste des creneaux possibles l'apres midi
	 * 
	 */
	public List<Date> getCreneauxAmidi(final boolean etudiant, final Calendar cal) {
		List<Date> liste = new ArrayList<Date>();

		// toutes les tranches horaires de l'Apres midi
		Calendar debAmidi = new GregorianCalendar();
		debAmidi.setTime(cal.getTime());

		Calendar deb = new GregorianCalendar();
		if (getJourHoraires().get(cal.getTime()) != null) {
			deb.setTimeInMillis(this.getJourHoraires().get(cal.getTime()).
					getDateDebutAmidi().getTime());
		} else {
			deb.setTimeInMillis(this.getHoraires().get(cal.get(Calendar.MONTH)).
					getDateDebutAmidi().getTime());
		}
		debAmidi.set(Calendar.HOUR_OF_DAY, deb.get(Calendar.HOUR_OF_DAY));
		debAmidi.set(Calendar.MINUTE, deb.get(Calendar.MINUTE));
		debAmidi.set(Calendar.SECOND, deb.get(Calendar.SECOND));
		debAmidi.set(Calendar.MILLISECOND, deb.get(Calendar.MILLISECOND));

		Calendar finAmidi = new GregorianCalendar();
		finAmidi.setTime(cal.getTime());

		Calendar fin = new GregorianCalendar();
		if (getJourHoraires().get(cal.getTime()) != null) {
			fin.setTimeInMillis(this.getJourHoraires().get(cal.getTime()).
					getDateFinAmidi().getTime());
		} else {
			fin.setTimeInMillis(this.getHoraires().get(cal.get(Calendar.MONTH)).
					getDateFinAmidi().getTime());
		}
		finAmidi.set(Calendar.HOUR_OF_DAY, fin.get(Calendar.HOUR_OF_DAY));
		finAmidi.set(Calendar.MINUTE, fin.get(Calendar.MINUTE));
		finAmidi.set(Calendar.SECOND, fin.get(Calendar.SECOND));
		finAmidi.set(Calendar.MILLISECOND, fin.get(Calendar.MILLISECOND));

		for (long tranche = debAmidi.getTimeInMillis(); tranche < finAmidi.getTimeInMillis(); 
		tranche = tranche + NB_MS_BY_HALF_HOUR) {
			Date date = new Date(tranche);

			if (this.getCandidatsAsMap().containsKey(date) 
					&& this.getCandidatsAsMap().get(date).keySet().size() 
						>= this.getNbreMaxEtud()) {
				logger.info("Le creneau " + date.toString() 
						+ "est plein ! On ne propose pas cette date !");
				if (!etudiant) {
					liste.add(new Date(tranche));
				}
			} else {
				liste.add(new Date(tranche));
			}
		}

		return liste;
	}
}
