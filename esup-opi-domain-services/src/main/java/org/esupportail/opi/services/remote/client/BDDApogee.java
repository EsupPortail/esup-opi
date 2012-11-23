package org.esupportail.opi.services.remote.client;

import static fj.data.IterableW.wrap;
import static fj.data.Option.*;
import static fj.data.Stream.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.esupportail.opi.dao.OpiDaoService;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesAnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesDiplomeAnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.DomaineAnnuForm;
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
	
	private final OpiDaoService dao;

	private final ReadEnseignement remoteCriApogeeEns;
	
	private BDDApogee(final ReadEnseignement readEns, final OpiDaoService daoParam) {
		remoteCriApogeeEns = readEns;
		dao = daoParam;
	}
	
	public static final BDDApogee bddApogee(final ReadEnseignement readEns,
			final OpiDaoService dao) {
		return new BDDApogee(readEns, dao);
	}
	
	@Override
	public List<String> getCodesDiplomes(final String codeKeyWord) {
		return wrap(dao.getCodesDiplomes(codeKeyWord)).map(
				getCodDipFromCle).toStandardList();
	}

	@Override
	public List<GrpTypDip> getGrpTypDip(final String bool) {
		return dao.getGrpTypDip(fromNull(bool));
	}

    @Override
    public void updateGrpTypDip(final GrpTypDip grp, final List<String> codesTpdEtb) {
        GrpTypDip grpTypDip = dao.getGrpTypDip(grp.getCodGrpTpd());
        Set<GrpTypDipCorresp> existants = new HashSet<GrpTypDipCorresp>(grpTypDip.getGrpTypDipCorresps());
        List<GrpTypDipCorresp> nouveaux = new ArrayList<GrpTypDipCorresp>();

        for (String c : codesTpdEtb) {
            GrpTypDipCorresp dip = new GrpTypDipCorresp();
            dip.getGrpTpd().setCodGrpTpd(grpTypDip.getCodGrpTpd());
            dip.setCodTpdEtb(c);
            nouveaux.add(dip);
        }

        //Ajout
        for(GrpTypDipCorresp n : nouveaux) {
            if (!existants.contains(n)) {
                grpTypDip.getGrpTypDipCorresps().add(n);
            }
        }

        //Suppression
        for(GrpTypDipCorresp e : existants) {
            if (!nouveaux.contains(e)) {
                grpTypDip.getGrpTypDipCorresps().remove(e);
            }
        }
    }

    @Override
	public Map<Domaine2AnnuForm, List<Cles2AnnuForm>> getDomaine2AnnuFormDTO(final GrpTypDip grpTypDip,
			final String locale) {
		final List<String> lCodDip = wrap(remoteCriApogeeEns.getDiplomes(null, 
				wrap(grpTypDip.getGrpTypDipCorresps())
				.map(getCodTpdEtb).toStandardList()))
				.map(getCodDip).toStandardList();
		
		final List<String> lCodcles = wrap(dao.getClesDiplomeAnnuForm(lCodDip)).map(getCodCle).toStandardList();
		final List<Cles2AnnuForm> cles2AnnuForm = dao.getCles2AnnuForm(lCodcles, some(locale), some("O"));
		final List<String> lCodDom = wrap(cles2AnnuForm).map(getCodDom).toStandardList(); 
		final List<Domaine2AnnuForm> domains = dao.getDomaine2AnnuForm(lCodDom, some(locale), some("O"));
		
		Map<Domaine2AnnuForm, List<Cles2AnnuForm>> result = new HashMap<Domaine2AnnuForm, List<Cles2AnnuForm>>();
		for (final Domaine2AnnuForm d : domains) {
			result.put(d, wrap(iterableStream(cles2AnnuForm).filter(new F<Cles2AnnuForm, Boolean>() {
				@Override
				public Boolean f(final Cles2AnnuForm cle) {
					return cle.getClesAnnuForm().getCodDom().equalsIgnoreCase(d.getCodDom());
				}
			})).toStandardList());
		}
		return result;
	}
	
	//////////////////////////////////////////////////////////////
	// Custom
	//////////////////////////////////////////////////////////////

	@Override
	public List<ClesAnnuForm> getClesAnnuForm() {
		return dao.getClesAnnuForm();
	}

	@Override
	public List<DomaineAnnuForm> getDomaineAnnuForm() {
		return dao.getDomaineAnnuForm();
	}

	@Override
	public List<Cles2AnnuForm> getCles2AnnuForm(final String codCle) {
		return dao.getCles2AnnuForm(some(codCle));
	}

	@Override
	public List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm(final String codCle) {
		return dao.getClesDiplomeAnnuForm(some(codCle));
	}

	@Override
	public List<Domaine2AnnuForm> getDomaine2AnnuForm(final String codDom) {
		return dao.getDomaine2AnnuForm(some(codDom));
	}

	public DomaineAnnuForm getDomaineAnnuForm(final String codDom) {
		return dao.getDomaineAnnuForm(some(codDom)).orSome(new DomaineAnnuForm());
	}

	@Override
	public <T> Serializable save(final T o) {
		return dao.save(o);
	}

	@Override
	public <T> void saveOrUpdate(final T o) {
		dao.saveOrUpdate(o);
	}

	@Override
	public <T> void update(final T o) {
		dao.update(o);
	}

	@Override
	public <T> void delete(final T o) {
		dao.delete(o);
	}
}
