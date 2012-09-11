/**
 * http://sourcesup.cru.fr/projects/esup-opiR1
 */
package org.esupportail.opi.dao;


import java.io.Serializable;
import java.util.List;
import java.util.Set;

import org.esupportail.opi.domain.beans.etat.EtatIndividu;
import org.esupportail.opi.domain.beans.etat.EtatVoeu;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.MotivationAvis;
import org.esupportail.opi.domain.beans.parameters.TypeConvocation;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.DipAutCur;
import org.esupportail.wssi.services.remote.Pays;
import org.esupportail.wssi.services.remote.VersionEtapeDTO;
import org.hibernate.criterion.DetachedCriteria;


/**
 * The pilotage DAO service interface.
 */
public interface PilotageDaoService extends Serializable {
	/**
	 * 
	 * @param criteria
	 * @return Integer
	 */
	int getResutRequete(final DetachedCriteria criteria);
	
	/**
	 * @param listCriteria
	 * @param isNotDoublonIndividu
	 * @return Integer
	 */
	int getResutRequete(final List<DetachedCriteria> listCriteria, final boolean isNotDoublonIndividu);
	
	
	/* * ******************* COMMISSION ********************** * */
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statCommissionDiplomeCriteria(final Commission n1,
			final DipAutCur n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statCommissionEtatIndividuCriteria(final Commission n1,
			final EtatIndividu n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statCommissionEtatVoeuCriteria(final Commission n1,
			final EtatVoeu n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statCommissionMotivationCriteria(final Commission n1,
			final MotivationAvis n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statCommissionPaysCriteria(final Commission n1, final Pays n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param tc
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statCommissionTypeConvocationCriteria(final Commission n1,
			final TypeConvocation tc, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statCommissionTypeDecisionCriteria(final Commission n1,
			final TypeDecision n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria allIndividuCommission(final Commission n1, final Campagne c);
	
	
	/* * ******************* COMPOSANTE ********************** * */
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statComposanteDiplomeCriteria(final CentreGestion n1,
			final DipAutCur n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statComposanteEtatIndividuCriteria(final CentreGestion n1,
			final EtatIndividu n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statComposanteEtatVoeuCriteria(final CentreGestion n1,
			final EtatVoeu n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statComposanteMotivationCriteria(final CentreGestion n1,
			final MotivationAvis n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statComposantePaysCriteria(final CentreGestion n1, final Pays n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param tc
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statComposanteTypeConvocationCriteria(final CentreGestion n1,
			final TypeConvocation tc, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statComposanteTypeDecisionCriteria(final CentreGestion n1,
			final TypeDecision n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria allIndividuComposante(final CentreGestion n1, final Campagne c);
	
	
	/* * ******************* MOT CLE ********************** * */
	/**
	 * @param listClesDip
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statMotCleDiplomeCriteria(final Set<VersionEtpOpi> listClesDip,
			final DipAutCur n2, final Campagne c);
	
	/**
	 * @param listClesDip
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statMotCleEtatIndividuCriteria(final Set<VersionEtpOpi> listClesDip,
			final EtatIndividu n2, final Campagne c);
	
	/**
	 * @param listClesDip
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statMotCleEtatVoeuCriteria(final Set<VersionEtpOpi> listClesDip,
			final EtatVoeu n2, final Campagne c);
	
	/**
	 * @param listClesDip
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statMotCleMotivationCriteria(final Set<VersionEtpOpi> listClesDip,
			final MotivationAvis n2, final Campagne c);
	
	/**
	 * @param listClesDip
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statMotClePaysCriteria(final Set<VersionEtpOpi> listClesDip,
			final Pays n2, final Campagne c);
	
	/**
	 * @param listClesDip
	 * @param tc
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statMotCleTypeConvocationCriteria(final Set<VersionEtpOpi> listClesDip,
			final TypeConvocation tc, final Campagne c);
	
	/**
	 * @param listClesDip
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statMotCleTypeDecisionCriteria(final Set<VersionEtpOpi> listClesDip,
			final TypeDecision n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria allIndividuMotCle(final Set<VersionEtpOpi> listClesDip, final Campagne c);
	
	
	/* * ******************* REGIME INSCRIPTION ********************** * */
	/**
	 * @param codeRI : code of the RegimeInscription
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statRegimeInscriptionDiplomeCriteria(final Integer codeRI,
			final DipAutCur n2, final Campagne c);
	
	/**
	 * @param codeRI : code of the RegimeInscription
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statRegimeInscriptionEtatIndividuCriteria(final Integer codeRI,
			final EtatIndividu n2, final Campagne c);
	
	/**
	 * @param codeRI : code of the RegimeInscription
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statRegimeInscriptionEtatVoeuCriteria(final Integer codeRI,
			final EtatVoeu n2, final Campagne c);
	
	/**
	 * @param codeRI : code of the RegimeInscription
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statRegimeInscriptionMotivationCriteria(final Integer codeRI,
			final MotivationAvis n2, final Campagne c);
	
	/**
	 * @param codeRI : code of the RegimeInscription
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statRegimeInscriptionPaysCriteria(final Integer codeRI,
			final Pays n2, final Campagne c);
	
	/**
	 * @param codeRI : code of the RegimeInscription
	 * @param tc
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statRegimeInscriptionTypeConvocationCriteria(final Integer codeRI,
			final TypeConvocation tc, final Campagne c);
	
	/**
	 * @param codeRI : code of the RegimeInscription
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statRegimeInscriptionTypeDecisionCriteria(final Integer codeRI,
			final TypeDecision n2, final Campagne c);
	
	/**
	 * @param codeRI : code of the RegimeInscription
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria allIndividuRegimeInscription(final Integer codeRI, final Campagne c);
	
	
	/* * ******************* VET ********************** * */
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statVetDiplomeCriteria(final VersionEtapeDTO n1,
			final DipAutCur n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statVetEtatIndividuCriteria(final VersionEtapeDTO n1,
			final EtatIndividu n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statVetEtatVoeuCriteria(final VersionEtapeDTO n1,
			final EtatVoeu n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statVetMotivationCriteria(final VersionEtapeDTO n1,
			final MotivationAvis n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statVetPaysCriteria(final VersionEtapeDTO n1, final Pays n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param tc
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statVetTypeConvocationCriteria(final VersionEtapeDTO n1,
			final TypeConvocation tc, final Campagne c);
	
	/**
	 * @param n1
	 * @param n2
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria statVetTypeDecisionCriteria(final VersionEtapeDTO n1,
			final TypeDecision n2, final Campagne c);
	
	/**
	 * @param n1
	 * @param c
	 * @return DetachedCriteria
	 */
	DetachedCriteria allIndividuVet(final VersionEtapeDTO n1, final Campagne c);
	
	
	// ////////////////////////////////////////////////////////////
	// PARAMETRE
	// ////////////////////////////////////////////////////////////
	/**
	 * @param typeClass
	 * @return List
	 */
	@SuppressWarnings("unchecked")
	List getAllCoordonne(Class< ? > typeClass);
}
