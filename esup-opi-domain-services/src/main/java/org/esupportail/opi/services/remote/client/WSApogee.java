package org.esupportail.opi.services.remote.client;

import static fj.data.IterableW.wrap;
import static org.esupportail.opi.utils.fj.Conversions.*;

import java.util.ArrayList;
import java.util.List;
import fj.F;
import fj.data.Stream;
import static fj.data.Stream.*;
import org.esupportail.commons.services.logging.Logger;
import org.esupportail.commons.services.logging.LoggerImpl;
import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.utils.CacheModelConst;
import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;

import com.googlecode.ehcache.annotations.Cacheable;

import fr.univ.rennes1.cri.apogee.domain.beans.ArrayOfRen1GrpTypDipCorresp;
import fr.univ.rennes1.cri.apogee.domain.beans.Ren1GrpTypDip;
import fr.univ.rennes1.cri.apogee.domain.dto.Ren1Cles2AnnuFormDTO;
import fr.univ.rennes1.cri.apogee.domain.dto.Ren1Domaine2AnnuFormDTO;
import fr.univ.rennes1.cri.apogee.services.remote.ReadRennes1PortType;

public class WSApogee implements IApogee {

	private final Logger log = new LoggerImpl(getClass());	
	
	private final ReadRennes1PortType wsApo;

	private WSApogee(final ReadRennes1PortType readRennes1){
		wsApo = readRennes1;
	}
	
	private F<Ren1GrpTypDip, Stream<Ren1Domaine2AnnuFormDTO>> grpTypDipToDomaine(final String locale) {
			return new F<Ren1GrpTypDip, Stream<Ren1Domaine2AnnuFormDTO>>() {
				public Stream<Ren1Domaine2AnnuFormDTO> f(Ren1GrpTypDip g) {
					return iterableStream(
							wsApo.getRen1Domaine2AnnuFormDTO(
									g, locale).getRen1Domaine2AnnuFormDTO());
				}
			};
	}
	
	private F<Ren1Domaine2AnnuFormDTO, Stream<Ren1Cles2AnnuFormDTO>> getR1Cles2AnnuFormDTO =
			new F<Ren1Domaine2AnnuFormDTO, Stream<Ren1Cles2AnnuFormDTO>>() {
				public Stream<Ren1Cles2AnnuFormDTO> f(Ren1Domaine2AnnuFormDTO r1d) {
					return iterableStream(r1d.getRen1Cles2AnnuFormDTO().getRen1Cles2AnnuFormDTO());
				}
			};
	
	public static final WSApogee wsApogee(final ReadRennes1PortType readRennes1) {
		return new WSApogee(readRennes1);
	}

	@Override
	@Cacheable(cacheName = CacheModelConst.RENNES1_APOGEE_MODEL)	
	public List<String> getCodesDiplomes(final String codeKeyWord) {
		try {
			return wsApo.getCodesDiplomes(codeKeyWord).getString();
		} catch (Exception e) {
			log.error(new CommunicationApogeeException(e));
			return new ArrayList<String>();
		}
	}

	@Override
	@Cacheable(cacheName = CacheModelConst.RENNES1_APOGEE_MODEL)	
	public List<GrpTypDip> getGrpTypDip(final String bool) {
		try {
			return wrap(wsApo.getRen1GrpTypDip(bool).getRen1GrpTypDip()).map(
					toGrpTypDip).toStandardList();
		} catch (Exception e) {
			log.error(new CommunicationApogeeException(e));
			return new ArrayList<GrpTypDip>();
		}
	}

	@Override
	@Cacheable(cacheName = CacheModelConst.RENNES1_APOGEE_MODEL)
	public List<Domaine2AnnuForm> getDomaine2AnnuFormDTO(final GrpTypDip grpTypDip,
			final String locale) {
		final Ren1GrpTypDip r1g =
				new Ren1GrpTypDip().withCodGrpTpd(grpTypDip.getCodGrpTpd())
				.withLibGrpTpd(grpTypDip.getLibGrpTpd())
				.withRen1GrpTypDipCorresps(
						new ArrayOfRen1GrpTypDipCorresp().withRen1GrpTypDipCorresp(
								wrap(grpTypDip.getGrpTypDipCorresps()).map(
										toR1GrpTypDipCorresp).toStandardList())); 
		try {
			return wrap(wsApo.getRen1Domaine2AnnuFormDTO(
					r1g, locale).getRen1Domaine2AnnuFormDTO()).map(
							toDomaine2AnnuForm).toStandardList();
		} catch (Exception e) {
			log.error(new CommunicationApogeeException(e));
			return new ArrayList<Domaine2AnnuForm>();
		}
	}

	@Override
	public List<Cles2AnnuForm> getCles2AnnuForm(final String codDom, final String locale) {
		return new ArrayList<Cles2AnnuForm>(
				iterableStream(getGrpTypDip(null)).map(toR1GrpTypDip).bind(
						grpTypDipToDomaine(locale)).bind(getR1Cles2AnnuFormDTO).map(
								toCles2AnnuForm(codDom)).toCollection());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
