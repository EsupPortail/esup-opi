package org.esupportail.opi.web.controllers.formation;



import java.text.SimpleDateFormat;
import java.util.Date;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.controllers.AbstractAccessController;
import org.esupportail.wssi.services.remote.ReadReferentiel;
import org.springframework.util.StringUtils;



/**
 * 
 * @author Gomez
 *
 */
public class RedirectionController extends AbstractAccessController {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2598499302002196321L;
	
	
	/*
	 ******************* PROPERTIES ******************* */
	/**
	 * A logger.
	 */
	private final Logger log = new LoggerImpl(getClass());
	/**
	 * remoteCriApogeeRef.
	 */
	private ReadReferentiel remoteCriApogeeRef;
	/**
	 * lien de redirection pour les anciens etudiants.
	 */
	private String urlEtudiant;
	/**
	 * lien de redirection pour les nouveaux etudiants.
	 */
	private String urlEtudiantPrimo;
	/**
	 * voeu selected.
	 */
	private IndVoeuPojo voeu;
	
	
	/*
	 ******************* INIT ************************* */
	/**
	 * Controller.
	 */
	public RedirectionController() {
		if (log.isDebugEnabled()) {
			log.debug("Dans SaisieRdvEtuController");
		}
	}
	
	
	/*
	 ******************* METHODS VALIDER/urlDeRedirection ********************** */
	/**
	 * 
	 * @return String
	 */
	public String getUrlDeRedirection() {
		//TODO a voir avec UM1 si probleme
		//logiquement si code Etu forcement etudiant il suffit donc de test si present
		//if (isEtudiant()) { comment le 30/10/2009
		if (StringUtils.hasText(voeu.getIndVoeu().getIndividu().getCodeEtu())) {
			return urlEtudiant;
		}
		return urlEtudiantPrimo;
	}

	/*
	 ******************* METHODS ********************** */
	/**
	 * 
	 * @return redirection
	 */
	public boolean isRedirection() {
		//permet la redirection e la place de la prise de rendez-vous
		if (voeu.getVrsEtape().getCodCgeMinVet() != null) {
			return true;
		}
		return false;
	}
	/**
	 * 
	 * @return boolean
	 */
	public boolean isRedirectionOuverte() {
		// Date d'aujourd'hui
		Date tmp = new Date();
		// Sous la forme '01-01-2002 00:00:00'
		SimpleDateFormat sdf = new SimpleDateFormat(Constantes.ENGLISH_DATE_FORMAT);
		String affichage = sdf.format(tmp);
		//TODO a voir avec UM1 si prolbeme
		//logiquement si code Etu forcement etudiant il suffit donc de test si present
		//if (isEtudiant()) { comment le 30/10/2009
		if (StringUtils.hasText(voeu.getIndVoeu().getIndividu().getCodeEtu())) {
			return remoteCriApogeeRef.getDateDebWeb().equals(affichage);
		}
		
		return remoteCriApogeeRef.getDateDebWebPrimoNb().equals(affichage)
			|| remoteCriApogeeRef.getDateDebWebPrimoNnb().equals(affichage);
	}
	/**
	 * 
	 * @return boolean
	 */
	//comment the 30/10/2009
//	public boolean isEtudiant() {
//		return Utilitaires.isStringValid(voeu.getIndVoeu().getIndividu().getCodeEtu(), 
//				getDomainService().getCodStudentRegex());
//	}
	
	
	
	/*
	 ******************* ACCESSORS ******************** */
	/**
	 * 
	 * @return voeu
	 */
	public IndVoeuPojo getVoeu() {
		return voeu;
	}
	/**
	 * 
	 * @param voeu
	 */
	public void setVoeu(final IndVoeuPojo voeu) {
		this.voeu = voeu;
	}
	/**
	 * 
	 * @return remoteCriApogeeRef
	 */
	public ReadReferentiel getRemoteCriApogeeRef() {
		return remoteCriApogeeRef;
	}
	/**
	 * 
	 * @param remoteCriApogeeRef
	 */
	public void setRemoteCriApogeeRef(final ReadReferentiel remoteCriApogeeRef) {
		this.remoteCriApogeeRef = remoteCriApogeeRef;
	}
	/**
	 * 
	 * @return lienEtudiant
	 */
	public String getUrlEtudiant() {
		return urlEtudiant;
	}
	/**
	 * 
	 * @param urlEtudiant
	 */
	public void setUrlEtudiant(final String urlEtudiant) {
		this.urlEtudiant = urlEtudiant;
	}
	/**
	 * 
	 * @return lienEtudiantPrimo
	 */
	public String getUrlEtudiantPrimo() {
		return urlEtudiantPrimo;
	}
	/**
	 * 
	 * @param urlEtudiantPrimo
	 */
	public void setUrlEtudiantPrimo(final String urlEtudiantPrimo) {
		this.urlEtudiantPrimo = urlEtudiantPrimo;
	}
}
