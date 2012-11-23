package org.esupportail.opi.services.remote.client;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesAnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesDiplomeAnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.DomaineAnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;

public interface IApogee {

	public List<String> getCodesDiplomes(final String codeKeyWord);
	
	public List<GrpTypDip> getGrpTypDip(final String bool);

    public void updateGrpTypDip(GrpTypDip grp, List<String> codesTpdEtb);
	
	public Map<Domaine2AnnuForm, List<Cles2AnnuForm>> getDomaine2AnnuFormDTO(final GrpTypDip grpTypDip,
			final String locale);
	
	public List<ClesAnnuForm> getClesAnnuForm();
	
	public List<DomaineAnnuForm> getDomaineAnnuForm();

	public List<Cles2AnnuForm> getCles2AnnuForm(final String codCle);
	
	public List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm(final String codCle);
	
	public List<Domaine2AnnuForm> getDomaine2AnnuForm(final String codDom);

	public DomaineAnnuForm getDomaineAnnuForm(final String codDom);
	
	public <T> Serializable save(final T o);
	
	public <T> void saveOrUpdate(final T o) ;
	
	public <T> void update(final T o);
	
	public <T> void delete(final T o);
}
