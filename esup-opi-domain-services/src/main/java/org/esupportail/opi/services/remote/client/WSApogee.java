package org.esupportail.opi.services.remote.client;

import static fj.data.IterableW.wrap;
import static org.esupportail.opi.utils.fj.Conversions.toGrpTypDip;
import static org.esupportail.opi.utils.fj.Conversions.toMapDomCles;
import static org.esupportail.opi.utils.fj.Conversions.toMapDomaine2AnnuForm;
import static org.esupportail.opi.utils.fj.Conversions.toR1GrpTypDipCorresp;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesAnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesDiplomeAnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.DomaineAnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.utils.CacheModelConst;
import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;

import com.googlecode.ehcache.annotations.Cacheable;

import fr.univ.rennes1.cri.apogee.domain.beans.ArrayOfRen1GrpTypDipCorresp;
import fr.univ.rennes1.cri.apogee.domain.beans.Ren1GrpTypDip;
import fr.univ.rennes1.cri.apogee.services.remote.ReadRennes1PortType;

public class WSApogee implements IApogee {

	private final Logger log = new LoggerImpl(getClass());	
	
	private final ReadRennes1PortType wsApo;

	private WSApogee(final ReadRennes1PortType readRennes1){
		wsApo = readRennes1;
	}

	public static final WSApogee wsApogee(final ReadRennes1PortType readRennes1) {
		return new WSApogee(readRennes1);
	}

	@Override
	@Cacheable(cacheName = CacheModelConst.RENNES1_APOGEE_MODEL)	
	public List<String> getCodesDiplomes(final String codeKeyWord) {
		try {
			return wsApo.getCodesDiplomes(codeKeyWord).getStrings();
		} catch (Exception e) {
			log.error(new CommunicationApogeeException(e));
			return new ArrayList<String>();
		}
	}

	@Override
	@Cacheable(cacheName = CacheModelConst.RENNES1_APOGEE_MODEL)	
	public List<GrpTypDip> getGrpTypDip(final String bool) {
		try {
			return wrap(wsApo.getRen1GrpTypDip(bool).getRen1GrpTypDips()).map(
					toGrpTypDip).toStandardList();
		} catch (Exception e) {
			log.error(new CommunicationApogeeException(e));
			return new ArrayList<GrpTypDip>();
		}
	}

    @Override
    public void updateGrpTypDip(GrpTypDip grp, List<String> codesTpdEtb) {
        throw new RuntimeException("Unimplemented method.");
    }

    @Override
	@Cacheable(cacheName = CacheModelConst.RENNES1_APOGEE_MODEL)
	public Map<Domaine2AnnuForm, List<Cles2AnnuForm>> getDomaine2AnnuFormDTO(final GrpTypDip grpTypDip,
			final String locale) {
		final Ren1GrpTypDip r1g =
				new Ren1GrpTypDip().withCodGrpTpd(grpTypDip.getCodGrpTpd())
				.withLibGrpTpd(grpTypDip.getLibGrpTpd())
				.withRen1GrpTypDipCorresps(
						new ArrayOfRen1GrpTypDipCorresp().withRen1GrpTypDipCorresps(
								wrap(grpTypDip.getGrpTypDipCorresps()).map(
										toR1GrpTypDipCorresp).toStandardList())); 
		try {
			return wrap(wsApo.getRen1Domaine2AnnuFormDTO(
					r1g, locale).getRen1Domaine2AnnuFormDTOs()).map(
							toMapDomaine2AnnuForm).foldLeft(toMapDomCles, new HashMap<Domaine2AnnuForm,List<Cles2AnnuForm>>());
		} catch (Exception e) {
			log.error(new CommunicationApogeeException(e));
			return new HashMap<Domaine2AnnuForm,List<Cles2AnnuForm>>();
		}
	}

	@Override
	public List<ClesAnnuForm> getClesAnnuForm() {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public List<DomaineAnnuForm> getDomaineAnnuForm() {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public List<Cles2AnnuForm> getCles2AnnuForm(String codCle) {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm(String codCle) {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public List<Domaine2AnnuForm> getDomaine2AnnuForm(String codDom) {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public DomaineAnnuForm getDomaineAnnuForm(String codDom) {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public <T> Serializable save(T o) {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public <T> void saveOrUpdate(T o) {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public <T> void update(T o) {
		throw new RuntimeException("Unimplemented method.");
	}

	@Override
	public <T> void delete(T o) {
		throw new RuntimeException("Unimplemented method.");
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
