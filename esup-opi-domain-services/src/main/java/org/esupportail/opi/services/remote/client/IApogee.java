package org.esupportail.opi.services.remote.client;

import java.util.List;

import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;

public interface IApogee {

	public List<String> getCodesDiplomes(final String codeKeyWord);
	
	public List<GrpTypDip> getGrpTypDip(final String bool);
	
	public List<Domaine2AnnuForm>  getDomaine2AnnuFormDTO(final GrpTypDip grpTypDip,
			final String locale);
	
	public List<Cles2AnnuForm> getCles2AnnuForm(String codDom, String locale);
}
