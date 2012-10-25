package org.esupportail.opi.services.remote.client;

import static fj.data.IterableW.wrap;
import static fj.data.Option.some;

import java.util.List;

import org.esupportail.opi.dao.DaoService;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesAnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesDiplomeAnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.formation.GrpTypDipCorresp;
import org.esupportail.wssi.services.remote.Diplome;
import org.esupportail.wssi.services.remote.ReadEnseignement;

import fj.F;

public class BDDApogee implements IApogee {
	
	/**
	 * A first-class version of {@link GrpTypDipCorresp#getCodTpdEtb()}
	 */
	final F<GrpTypDipCorresp, String> getCodTpdEtb =
		new F<GrpTypDipCorresp, String>() {
		    public String f(GrpTypDipCorresp g) {
		    	return g.getCodTpdEtb();
		    }
	};
	
	/**
	 * A first-class version of {@link Diplome#getCodDip()}
	 */
	final F<Diplome, String> getCodDip =
	    new F<Diplome, String>() {
		    public String f(Diplome dip) {
		    	return dip.getCodDip();
		    }
	};
			
	/**
	 * A first-class version of {@link ClesDiplomeAnnuForm#getCodDip()}
	 */
	final F<ClesDiplomeAnnuForm, String> getCodDipFromCle =
	    new F<ClesDiplomeAnnuForm, String>() {
		    public String f(ClesDiplomeAnnuForm cdaf) {
		    	return cdaf.getCodDip();
		    }
	};
	
	/**
	 * A first-class version of {@link ClesDiplomeAnnuForm#getCodCles()}
	 */
	final F<ClesDiplomeAnnuForm, String> getCodCle =
		new F<ClesDiplomeAnnuForm, String>() {
		    public String f(ClesDiplomeAnnuForm cdaf) {
		    	return cdaf.getCodCles();
		    }
	};

	/**
	 * A first-class version of {@link ClesAnnuForm#getCodDom()}
	 * <br />
	 * (through {@link Cles2AnnuForm#getClesAnnuForm()})
	 */
	final F<Cles2AnnuForm, String> getCodDom =
	    new F<Cles2AnnuForm, String>() {
		    public String f(Cles2AnnuForm cles2) {
		    	return cles2.getClesAnnuForm().getCodDom();
		}
	};
	
	private final DaoService dao;

	private final ReadEnseignement remoteCriApogeeEns;
	
	private BDDApogee(final ReadEnseignement readEns, final DaoService daoParam) {
		remoteCriApogeeEns = readEns;
		dao = daoParam;
	}
	
	public static final BDDApogee bddApogee(final ReadEnseignement readEns,
			final DaoService dao) {
		return new BDDApogee(readEns, dao);
	}
	
	@Override
	public List<String> getCodesDiplomes(final String codeKeyWord) {
		return wrap(dao.getCodesDiplomes(codeKeyWord)).map(
				getCodDipFromCle).toStandardList();
	}

	@Override
	public List<GrpTypDip> getGrpTypDip(final String bool) {
		return dao.getGrpTypDip(some(bool));
	}

	@Override
	public List<Domaine2AnnuForm> getDomaine2AnnuFormDTO(final GrpTypDip grpTypDip,
			final String locale) {
		final List<String> lCodDip = wrap(remoteCriApogeeEns.getDiplomes(null, 
				wrap(grpTypDip.getGrpTypDipCorresps())
				.map(getCodTpdEtb).toStandardList()))
				.map(getCodDip).toStandardList();

		List<String> listCodDom =
				wrap(dao.getCles2AnnuForm(
						wrap(dao.getClesDiplomeAnnuForm(lCodDip)).map(getCodCle).toStandardList(),
						some(locale), some("0"))).map(getCodDom).toStandardList();

		return dao.getDomaine2AnnuForm(listCodDom, some(locale), some("O"));
	}

	@Override
	public List<Cles2AnnuForm> getCles2AnnuForm(String codDom, String locale) {
		return dao.getCles2AnnuFormByCodDom(some(codDom), some(locale));
	}
	
}
