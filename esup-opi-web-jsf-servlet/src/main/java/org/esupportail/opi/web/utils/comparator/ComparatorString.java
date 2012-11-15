/**
 * CRI - Universite de Rennes1 - 57SI-CIES - 2007
 * http://sourcesup.cru.fr/projects/57si-cies/
 */
package org.esupportail.opi.web.utils.comparator;

import fr.univ.rennes1.cri.apogee.domain.beans.Ren1GrpTypDip;
import fr.univ.rennes1.cri.apogee.domain.dto.Ren1Cles2AnnuFormDTO;
import gouv.education.apogee.commun.transverse.dto.geographie.communedto.CommuneDTO;

import java.io.Serializable;
import java.util.Comparator;

import org.esupportail.apogee.domain.dto.enseignement.VersionEtapeDTO;
import org.esupportail.opi.domain.beans.NormeSI;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.Nomenclature;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.accessRight.Fonction;
import org.esupportail.opi.domain.beans.references.calendar.Calendar;
import org.esupportail.opi.domain.beans.references.calendar.ReunionCmi;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.Member;
import org.esupportail.opi.domain.beans.user.indcursus.CursusExt;
import org.esupportail.opi.domain.beans.user.indcursus.CursusR1;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursus;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.web.beans.BeanAccess;
import org.esupportail.opi.web.beans.BeanTrtCmi;
import org.esupportail.opi.web.beans.pojo.CommissionPojo;
import org.esupportail.opi.web.beans.pojo.IndCursusScolPojo;
import org.esupportail.opi.web.beans.pojo.IndListePrepaPojo;
import org.esupportail.opi.web.beans.pojo.IndVoeuPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.pojo.NomenclaturePojo;
import org.esupportail.wssi.services.remote.BacOuxEqu;
import org.esupportail.wssi.services.remote.CentreGestion;
import org.esupportail.wssi.services.remote.Departement;
import org.esupportail.wssi.services.remote.DipAutCur;
import org.esupportail.wssi.services.remote.Diplome;
import org.esupportail.wssi.services.remote.Etablissement;
import org.esupportail.wssi.services.remote.Etape;
import org.esupportail.wssi.services.remote.SecDisSis;
import org.esupportail.wssi.services.remote.SignataireDTO;
import org.springframework.util.StringUtils;




/**
 * @author cleprous
 * 
 */
public class ComparatorString implements Comparator<Object>, Serializable {

	/**
	 * The serialization id. 
	 */
	private static final long serialVersionUID = -3564847013776612709L;
	
	
	/*
	 * PROPERTIES
	 */
	
	/**
	 * Permet d'identifier la classe afin de recuperer le bonne attribut.
	 */
	private Class< ? > className;

	/*
	 * INIT
	 */
	/**
	 * Constructor.
	 */
	public ComparatorString() {
		super();
	}

	/**
	 * Constructor.
	 * 
	 * @param c
	 *            Class
	 */
	public ComparatorString(final Class< ? > c) {
		super();
		className = c;
	}

	/*
	 * METHODS
	 */

	/**
	 * @param o1
	 * @param o2
	 * @return int
	 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
	 */
	public int compare(final Object o1, final Object o2) {
		if (className.equals(BeanAccess.class)) {
			return sortBeanAccess((BeanAccess) o1, (BeanAccess) o2);
		} else if (className.equals(NormeSI.class)) {
			return sortNormeSI((NormeSI) o1, (NormeSI) o2);
		} else if (className.equals(CentreGestion.class)) {
			return sortStr(((CentreGestion) o1).getLibCge(), 
							((CentreGestion) o2).getLibCge());
		} else if (className.equals(Etape.class)) {
			return sortStr(((Etape) o1).getLibEtp(), 
							((Etape) o2).getLibEtp());
		} else if (className.equals(Departement.class)) {
			return sortStr(((Departement) o1).getLibDep(), 
							((Departement) o2).getLibDep());
		} else if (className.equals(CommuneDTO.class)) {
			return sortStr(((CommuneDTO) o1).getLibCommune(), 
							((CommuneDTO) o2).getLibCommune());
		} else if (className.equals(Diplome.class)) {
			return sortStr(((Diplome) o1).getLicDip(), 
							((Diplome) o2).getLicDip());
		} else if (className.equals(SecDisSis.class)) {
			return sortStr(((SecDisSis) o1).getLibSds(), 
							((SecDisSis) o2).getLibSds());
		} else if (className.equals(Etablissement.class)) {
			return sortStr(((Etablissement) o1).getLibOffEtb(), 
							((Etablissement) o2).getLibOffEtb());
		} else if (className.equals(DipAutCur.class)) {
			return sortStr(((DipAutCur) o1).getLibDac(), 
							((DipAutCur) o2).getLibDac());
		} else if (className.equals(
		    org.esupportail.wssi.services.remote.CommuneDTO.class)) {
			return sortStr(((org.esupportail.wssi.services.remote.CommuneDTO) o1).getLibCom(), 
							((org.esupportail.wssi.services.remote.CommuneDTO) o2).getLibCom());
		} else if (className.equals(IndCursusScolPojo.class)) {
			//tri ascendant (du + gd au + petit) ; 2002,2001,...
			return sortStr(((IndCursusScolPojo) o2).getCursus().getAnnee(), 
							((IndCursusScolPojo) o1).getCursus().getAnnee());
		} else if (className.equals(IndCursus.class)) {
			//tri ascendant (du + gd au + petit) ; 2002,2001,...
			return sortStr(((IndCursus) o2).getAnnee(), 
							((IndCursus) o1).getAnnee());
		} else if (className.equals(Ren1GrpTypDip.class)) {
			return sortStr(((Ren1GrpTypDip) o1).getLibGrpTpd(), 
					((Ren1GrpTypDip) o2).getLibGrpTpd());
		} else if (className.equals(Ren1Cles2AnnuFormDTO.class)) {
			return sortStr(((Ren1Cles2AnnuFormDTO) o1).getLibCles(), 
					((Ren1Cles2AnnuFormDTO) o2).getLibCles());
		} else if (className.equals(IndVoeuPojo.class)) {
			if (((IndVoeuPojo) o1).getVrsEtape() == null
					|| ((IndVoeuPojo) o1).getVrsEtape().getLibWebVet() == null) {
				System.out.println("ERROR BY NULL ON INDVOEU " + ((IndVoeuPojo) o1).getVrsEtape().toString());
			}
			if (((IndVoeuPojo) o2).getVrsEtape() == null
					|| ((IndVoeuPojo) o2).getVrsEtape().getLibWebVet() == null) {
				System.out.println("ERROR BY NULL ON INDVOEU" + ((IndVoeuPojo) o2).getVrsEtape().toString());
			}
			return sortStr(((IndVoeuPojo) o1).getVrsEtape().getLibWebVet(), 
					((IndVoeuPojo) o2).getVrsEtape().getLibWebVet());
		} else if (className.equals(BacOuxEqu.class)) {
			return sortStr(((BacOuxEqu) o1).getLibBac(), 
					((BacOuxEqu) o2).getLibBac());
		} else if (className.equals(Commission.class)) {
			return sortStr(((Commission) o1).getCode(), 
					((Commission) o2).getCode());
		} else if (className.equals(CommissionPojo.class)) {
			return sortStr(((CommissionPojo) o1).getCommission().getCode(), 
					((CommissionPojo) o2).getCommission().getCode());
		} else if (className.equals(NomenclaturePojo.class)) {
			return sortStr(((NomenclaturePojo) o1).getNomenclature().getLibelle(), 
					((NomenclaturePojo) o2).getNomenclature().getLibelle());
		} else if (className.equals(Nomenclature.class)) {
			return sortStr(((Nomenclature) o1).getLibelle(), 
					((Nomenclature) o2).getLibelle());
		} else if (className.equals(Calendar.class)) {
			return sortCalendar((Calendar) o1, (Calendar) o2);
		} else if (className.equals(Fonction.class)) {
			return sortStr(((Fonction) o1).getCode(), 
					((Fonction) o2).getCode());
		} else if (className.equals(IndListePrepaPojo.class)) {
			return sortStr(((IndListePrepaPojo) o1).getNom()
					.concat(((IndListePrepaPojo) o1).getPrenom())
					.concat(((IndListePrepaPojo) o1).getNumDossierOpi()), 
					((IndListePrepaPojo) o2).getNom()
					.concat(((IndListePrepaPojo) o2).getPrenom())
					.concat(((IndListePrepaPojo) o2).getNumDossierOpi()));
		} else if (className.equals(VersionEtapeDTO.class)) {
			return sortStr(((VersionEtapeDTO) o1).getLibWebVet(), 
					((VersionEtapeDTO) o2).getLibWebVet());
		} else if (className.equals(IndCursusScol.class)) {
			return sortCursusScol((IndCursusScol) o1, (IndCursusScol) o2);
		} else if (className.equals(Member.class)) {
			return sortMembers((Member) o1, 
							(Member) o2);
		} else if (className.equals(TypeDecision.class)) {
			return sortStr(((TypeDecision) o1).getLibelle()
			.concat(((TypeDecision) o1).getCode()),
			((TypeDecision) o2).getLibelle()
			.concat(((TypeDecision) o2).getCode()));

		} else if (className.equals(BeanTrtCmi.class)) {
			return sortBeanTrtCmi((BeanTrtCmi) o1, 
					(BeanTrtCmi) o2);
		} else if (className.equals(SignataireDTO.class)) {
			return sortStr(((SignataireDTO) o1).getCodSig(), 
					((SignataireDTO) o2).getCodSig());
		} else if (className.equals(IndividuPojo.class)) {
			return sortStr(((IndividuPojo) o1).getIndividu().getNomPatronymique()
				.concat(((IndividuPojo) o1).getIndividu().getPrenom()),
				((IndividuPojo) o2).getIndividu().getNomPatronymique()
				.concat(((IndividuPojo) o2).getIndividu().getPrenom()));
		}  else if (className.equals(Campagne.class)) {
			return sortCampagne((Campagne) o1, (Campagne) o2);
		} else if (className.equals(ReunionCmi.class)) {
			return sortStr(((ReunionCmi) o1).getDate().toString()
					.concat(((ReunionCmi) o1).getHeure().toString()),
					((ReunionCmi) o2).getDate().toString()
					.concat(((ReunionCmi) o2).getHeure().toString()));
		} 
		return 0;
	}

	/**
	 * Trie les membres par type de membre puis par ordre alphabetique.
	 * 
	 * @param o1
	 * @param o2
	 * @return
	 */
	private int sortMembers(final Member o1, final Member o2) {
		if (!o1.getType().equals(o2.getType())) {
			return o2.getType().compareTo(o1.getType());
		}
		if (!o1.getNom().concat(o1.getPrenom())
				.equals(o2.getNom().concat(o2.getPrenom()))) {
			return o1.getNom().concat(o1.getPrenom())
				.compareToIgnoreCase(o2.getNom().concat(o2.getPrenom()));
		}
		
		return 0;
	}
	
	/**
	 * Trie les cursus par libelle puis par code.
	 * 
	 * @param o1
	 * @param o2
	 * @return int
	 */
	private int sortCursusScol(final IndCursusScol o1, final IndCursusScol o2) {
		String lib1 = "";
		String code1 = "";
		String lib2 = "";
		String code2 = "";
		
		if (o1 instanceof CursusR1) {
			lib1 = ((CursusR1) o1).getLibEtape();
			code1 = ((CursusR1) o1).getCodEtape();
		} else {
			lib1 = ((CursusExt) o1).getLibDac();
			code1 = ((CursusExt) o1).getCodDac();	
		}

		if (o2 instanceof CursusR1) {
			lib2 = ((CursusR1) o2).getLibEtape();
			code2 = ((CursusR1) o2).getCodEtape();
		} else {
			lib2 = ((CursusExt) o2).getLibDac();
			code2 = ((CursusExt) o2).getCodDac();	
		}
		
		if (StringUtils.hasText(lib1) 
				&& StringUtils.hasText(lib2) 
				&& !lib1.equals(lib2)) {
			return lib1.compareTo(lib2);
		}
		
		if (StringUtils.hasText(code1) 
				&& StringUtils.hasText(code2) 
				&& !code1.equals(code2)) {
			return code1.compareTo(code2);
		}

		return 0;	}

	/**
	 * Trie les acces par domain puis par fonction.
	 * 
	 * @param b1
	 * @param b2
	 * @return int
	 */
	private int sortBeanAccess(final BeanAccess b1, final BeanAccess b2) {
		String lib1 = "";
		String lib2 = "";

		if (b1.getTraitement() instanceof Fonction) {
			Fonction f1 = (Fonction) b1.getTraitement();
			lib1 = f1.getDomain().getLibelle();
		} else {
			// si les modules sont egaux au tris sur les libelle fonctionnalite
			lib1 = b1.getTraitement().getLibelle();

		}

		if (b2.getTraitement() instanceof Fonction) {
			Fonction f = (Fonction) b2.getTraitement();
			lib2 = f.getDomain().getLibelle();
		} else {
			// si les modules sont egaux au tris sur les libelle fonctionnalite
			lib2 = b2.getTraitement().getLibelle();

		}

		if (!lib1.equals(lib2)) {
			return lib1.compareTo(lib2);
		}
		// on trie par libelle de fonctionnalite si les libelles des domains
		// sont egaux.
		lib1 = b1.getTraitement().getLibelle();
		lib2 = b2.getTraitement().getLibelle();
		if (!lib1.equals(lib2)) {
			return lib1.compareTo(lib2);
		}

		return 0;
	}

	

	/**
	 * Trie les objet normeSI par libelle.
	 * 
	 * @param b1
	 * @param b2
	 * @return int
	 */
	private int sortNormeSI(final NormeSI b1, final NormeSI b2) {
		String lib1 = "";
		String lib2 = "";

		lib1 = b1.getLibelle();
		lib2 = b2.getLibelle();
		if (!lib1.equals(lib2)) {
			return lib1.compareTo(lib2);
		}
		return 0;
	}
	
	
	/**
	 * Sort the given string.
	 * 
	 * @param lib1
	 * @param lib2
	 * @return int
	 */
	private int sortStr(final String lib1, final String lib2) {
		String a = lib1.toUpperCase();
		String b = lib2.toUpperCase();
		if (!a.equals(b)) {
			return a.compareTo(b);
		}
		return 0;		
	}
	
	
	/**
	 * Trie les acces par domain puis par fonction.
	 * 
	 * @param b1
	 * @param b2
	 * @return int
	 */
	private int sortCalendar(final Calendar b1, final Calendar b2) {

		if (!b1.getType().equals(b2.getType())) {
			return b1.getType().compareTo(b2.getType());
		}
		if (!b1.getCode().equals(b2.getCode())) {
			return b1.getCode().compareTo(b2.getCode());
		}

		return 0;
	}
	
	/**
	 * Trie BeanTrtCmi by label commission next by lib Vet.
	 * 
	 * @param b1
	 * @param b2
	 * @return int
	 */
	private int sortBeanTrtCmi(final BeanTrtCmi b1, final BeanTrtCmi b2) {
		Commission c1 = b1.getTraitementCmi().getCommission();
		Commission c2 = b2.getTraitementCmi().getCommission();
		if (c1 != null && c2 != null) {
			if (!c1.getLibelle()
					.equals(c2.getLibelle())) {
				return c1.getLibelle()
					.compareTo(c2.getLibelle());
			}
		}
		if (!b1.getEtape().getLibWebVet()
				.equals(b2.getEtape().getLibWebVet())) {
			return b1.getEtape().getLibWebVet()
				.compareTo(b2.getEtape().getLibWebVet());
		}

		return 0;
	}

	/**
	 * Trie Campagne by code, newt by lib.
	 * 
	 * @param c1
	 * @param c2
	 * @return
	 */
	private int sortCampagne(final Campagne c1, final Campagne c2) {
		if (!c1.getCode()
				.equals(c2.getCode())) {
			return c1.getCode()
				.compareTo(c2.getCode());
		}
		if (!c1.getLibelle()
				.equals(c2.getLibelle())) {
			return c1.getLibelle()
				.compareTo(c2.getLibelle());
		}

		return 0;
	}
	
	
	/*
	 * ACCESSORS
	 */

	/**
	 * @return the className
	 */
	public Class< ? > getClassName() {
		return className;
	}

	/**
	 * @param className
	 *            the className to set
	 */
	public void setClassName(final Class< ? > className) {
		this.className = className;
	}

}
