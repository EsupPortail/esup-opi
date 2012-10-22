package org.esupportail.opi.services.remote.client;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.NotImplementedException;
import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;
import org.esupportail.wssi.services.remote.Diplome;
import org.esupportail.wssi.services.remote.ReadEnseignement;

import fr.univ.rennes1.cri.apogee.domain.beans.GrpTypDip;
import fr.univ.rennes1.cri.apogee.domain.beans.GrpTypDipCorresp;
import fr.univ.rennes1.cri.apogee.domain.dto.Domaine2AnnuFormDTO;

public class BDDApogee implements IApogee {

	private final ReadEnseignement remoteCriApogeeEns;
	
	private BDDApogee(final ReadEnseignement readEns) {
		remoteCriApogeeEns = readEns;
	}
	
	public static final BDDApogee bddApogee(final ReadEnseignement readEns) {
		return new BDDApogee(readEns);
	}
	
	@Override
	public List<String> getCodesDiplomes(final String codeKeyWord)
			throws CommunicationApogeeException {
		throw new NotImplementedException("TODO !");
	}

	@Override
	public List<GrpTypDip> getGrpTypDip(final String bool)
			throws CommunicationApogeeException {
		throw new NotImplementedException("TODO !");
	}

	@Override
	public List<Domaine2AnnuFormDTO> getDomaine2AnnuFormDTO(
			final GrpTypDip grpTypDip, final String locale)
			throws CommunicationApogeeException {
				if (grpTypDip != null) {
					Set<String> lCodTpdEtb = new HashSet<String>();
					for (GrpTypDipCorresp r : grpTypDip.getGrpTypDipCorrespsArray().getGrpTypDipCorrespList()) {
						lCodTpdEtb.add(r.getCodTpdEtb());
					}
					List<Diplome> d = remoteCriApogeeEns.getDiplomes(
					    null, new ArrayList<String>(lCodTpdEtb));
					List<String> lCodDip = new ArrayList<String>();
					for (Diplome dip : d) {
						lCodDip.add(dip.getCodDip());
					}
//					return TODO.getDomaine2AnnuFormDTO(lCodDip, locale, TRUE);
				}
		throw new NotImplementedException("TODO !");		
	}

}
