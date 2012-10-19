package org.esupportail.opi.services.remote.client;

import java.util.List;

import org.esupportail.opi.utils.exceptions.CommunicationApogeeException;

import fr.univ.rennes1.cri.apogee.domain.beans.Ren1GrpTypDip;
import fr.univ.rennes1.cri.apogee.domain.dto.Ren1Domaine2AnnuFormDTO;

public interface IApogee {

	public List<String> getCodesDiplomes(final String codeKeyWord) throws CommunicationApogeeException;
	
	public List<Ren1GrpTypDip> getRen1GrpTypDip(final String bool) throws CommunicationApogeeException;
	
	public List<Ren1Domaine2AnnuFormDTO>  getRen1Domaine2AnnuFormDTO(final Ren1GrpTypDip ren1GrpTypDip,
			final String locale) throws CommunicationApogeeException;
}
