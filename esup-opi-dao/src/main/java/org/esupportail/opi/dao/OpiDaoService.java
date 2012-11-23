package org.esupportail.opi.dao;

import java.io.Serializable;
import java.util.List;

import org.esupportail.opi.domain.beans.formation.Cles2AnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesAnnuForm;
import org.esupportail.opi.domain.beans.formation.ClesDiplomeAnnuForm;
import org.esupportail.opi.domain.beans.formation.Domaine2AnnuForm;
import org.esupportail.opi.domain.beans.formation.DomaineAnnuForm;
import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.domain.beans.formation.GrpTypDipCorresp;

import fj.data.Option;

public interface OpiDaoService extends Serializable {

	//////////////////////////////////////////////////////////////
	// ClesDiplomeAnnuForm
	//////////////////////////////////////////////////////////////
	
	List<ClesDiplomeAnnuForm> getCodesDiplomes(String codeMotClef);
	
	//////////////////////////////////////////////////////////////
	// GrpTypDip
	//////////////////////////////////////////////////////////////
	
	List<GrpTypDip> getGrpTypDip(Option<String> temEnSveGrpTpd);
	
	List<GrpTypDipCorresp> getGrpTypDipCorresp();
	
	List<GrpTypDip> getGrpTypDip();

    GrpTypDip getGrpTypDip(String codTyp);
	
	//////////////////////////////////////////////////////////////
	// ClesDiplomeAnnuForm
	//////////////////////////////////////////////////////////////
	
	List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm(Option<String> codCle);
	
	List<ClesDiplomeAnnuForm> getClesDiplomeAnnuForm(List<String> listCodDip);
	
    //////////////////////////////////////////////////////////////
	// Domaine2AnnuFormm
	//////////////////////////////////////////////////////////////

	List<DomaineAnnuForm> getDomaineAnnuForm();
	
	Option<DomaineAnnuForm> getDomaineAnnuForm(Option<String> cle);
	
	List<ClesAnnuForm> getClesAnnuForm();
	
	List<Domaine2AnnuForm> getDomaine2AnnuForm(Option<String> codDom);
	
	List<Domaine2AnnuForm> getDomaine2AnnuForm(List<String> listCodDom, Option<String> codLang, Option<String> temSveDom);	
	
	//////////////////////////////////////////////////////////////
	// Cles2AnnuForm
	//////////////////////////////////////////////////////////////
	
	List<Cles2AnnuForm> getCles2AnnuFormByCodDom(Option<String> codDom, Option<String> locale);

	List<Cles2AnnuForm> getCles2AnnuForm(List<String> listCodCle, Option<String> codLang, Option<String> temEnSveCles);

	List<Cles2AnnuForm> getCles2AnnuForm(Option<String> codCle);
	
	
	//////////////////////////////////////////////////////////////
	// CRUD
	//////////////////////////////////////////////////////////////
	
	<T> Serializable save(T o);
	
	<T> void saveOrUpdate(T o);
	
	<T> void update(T o);
	
	<T> void delete(T o);
}
