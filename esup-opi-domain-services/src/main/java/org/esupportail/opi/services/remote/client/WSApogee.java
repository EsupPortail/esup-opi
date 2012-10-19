package org.esupportail.opi.services.remote.client;

import java.util.List;

import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;

import fr.univ.rennes1.cri.apogee.domain.beans.Ren1GrpTypDip;
import fr.univ.rennes1.cri.apogee.domain.dto.Ren1Domaine2AnnuFormDTO;
import fr.univ.rennes1.cri.apogee.services.remote.ReadRennes1PortType;

public class WSApogee implements IApogee {

	private final ReadRennes1PortType wsApo;

	private WSApogee(final ReadRennes1PortType readRennes1){
		wsApo = readRennes1;
	}
	
	public static final WSApogee wsApogee(final ReadRennes1PortType readRennes1) {
		return new WSApogee(readRennes1);
	}
	
	@Override
	public List<String> getCodesDiplomes(final String codeKeyWord)
			throws CommunicationApogeeException {
		return wsApo.getCodesDiplomes(codeKeyWord).getString();
	}

	@Override
	public List<Ren1GrpTypDip> getRen1GrpTypDip(final String bool)
			throws CommunicationApogeeException {
		return wsApo.getRen1GrpTypDip("O").getRen1GrpTypDip();
	}

	@Override
	public List<Ren1Domaine2AnnuFormDTO> getRen1Domaine2AnnuFormDTO(
			final Ren1GrpTypDip ren1GrpTypDip, final String locale)
			throws CommunicationApogeeException {
		return wsApo.getRen1Domaine2AnnuFormDTO(
				ren1GrpTypDip, locale).getRen1Domaine2AnnuFormDTO();
	}

}
