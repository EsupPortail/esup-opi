package org.esupportail.opi.services.remote.client;

import java.util.List;

import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;

import fr.univ.rennes1.cri.apogee.domain.beans.GrpTypDip;
import fr.univ.rennes1.cri.apogee.domain.dto.Domaine2AnnuFormDTO;
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
	public List<GrpTypDip> getGrpTypDip(final String bool)
			throws CommunicationApogeeException {
		return wsApo.getGrpTypDip("O").getGrpTypDipList();
	}

	@Override
	public List<Domaine2AnnuFormDTO> getDomaine2AnnuFormDTO(
			final GrpTypDip ren1GrpTypDip, final String locale)
			throws CommunicationApogeeException {
		return wsApo.getDomaine2AnnuFormDTO(
				ren1GrpTypDip, locale).getDomaine2AnnuFormDTOList();
	}

}
