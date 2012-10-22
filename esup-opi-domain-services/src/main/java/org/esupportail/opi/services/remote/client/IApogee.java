package org.esupportail.opi.services.remote.client;

import java.util.List;

import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;

import fr.univ.rennes1.cri.apogee.domain.beans.GrpTypDip;
import fr.univ.rennes1.cri.apogee.domain.dto.Domaine2AnnuFormDTO;

public interface IApogee {

	public List<String> getCodesDiplomes(final String codeKeyWord) throws CommunicationApogeeException;
	
	public List<GrpTypDip> getGrpTypDip(final String bool) throws CommunicationApogeeException;
	
	public List<Domaine2AnnuFormDTO>  getDomaine2AnnuFormDTO(final GrpTypDip GrpTypDip,
			final String locale) throws CommunicationApogeeException;
}
