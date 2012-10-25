/**
 * 
 */
package org.esupportail.opi.web.beans.pojo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.esupportail.opi.domain.beans.formation.GrpTypDip;
import org.esupportail.opi.web.beans.utils.NavigationRulesConst;
import org.esupportail.wssi.services.remote.VersionDiplomeDTO;










/**
 * @author cleprous
 *
 */
public class SearchFormationPojo implements Serializable {

	/*
	 ******************* PROPERTIES ******************* */
	
	/**
	 * The serialization id.
	 */
	private static final long serialVersionUID = 1326455060737388106L;

	
	/**
	 * The group selected.
	 */
	private GrpTypDip groupTypSelected;
	
	/**
	 * Code of key word.
	 */
	private String codKeyWordSelected;
	
	/**
	 * The version Etape to delete.
	 */
	private VersionEtapePojo versiontEtpToDelete;
	
	/**
	 * the version diplome selected by individu.
	 */
	private VersionDiplomeDTO vrsDipSelected;
	
	
	/**
	 * the version etapes selected by individu.
	 */
	private Set<VersionEtapePojo> vrsEtpSelected;
	
	/**
	 * The VersionDiplomes.
	 */
	private List<VersionDiplomeDTO> versionDiplomes;
	
	/**
	 * The VersionEtapes for the search.
	 */
	private Set<VersionEtapePojo> versionEtapes;
	
	/**
	 * The versionEtape to select for indivudu's inscription.
	 */
	private List<Object> objectToAdd;
	
	/**
	 * Has true if all VersionEtape in etapes is selected.
	 * Default value = false
	 */
	private Boolean allChecked; 
	
	
	/*
	 ******************* INIT ************************* */
	
	/**
	 * Constructors.
	 */
	public SearchFormationPojo() {
		super();
		reset();
	}
	
	/**
	 * reset all attributs.
	 */
	private void reset() {
		resetSearch();
		vrsEtpSelected = new HashSet<VersionEtapePojo>();
		
	}

	/**
	 * Reset the attributes for search formation.
	 */
	public void resetSearch() {
		codKeyWordSelected = null;
		groupTypSelected = null;
		versionDiplomes = null;
		versiontEtpToDelete = null;
		versionEtapes = null;
		vrsDipSelected = null;
		objectToAdd = new ArrayList<Object>();
		allChecked = false;
	}
	
	/*
	 ******************* METHODS ********************** */
	
	
	/**
	 * Add VET in objectToadd to vrsEtpSelected.
	 * @return callback to recap formation.
	 */
	public String addEtapeChoice() {
		if (!objectToAdd.isEmpty()) {
			for (Object o : objectToAdd) {
				vrsEtpSelected.add((VersionEtapePojo) o);
			}
		} else if (allChecked) {
			for (VersionEtapePojo v : versionEtapes) {
				if (v.getCanChoiceVet()) {
					vrsEtpSelected.add(v);
				}
			}
			//comment the 04/02/2009
			//vrsEtpSelected.addAll(versionEtapes);
		}
		
		return NavigationRulesConst.RECAP_FORMATION;
	}
	
	/**
	 * Remove a versiontEtpToDelete in list vrsEtpSelected.
	 */
	public void removeEtapeChoice() {
		vrsEtpSelected.remove(versiontEtpToDelete);
	}
	
	/*
	 ******************* ACCESSORS ******************** */
	
	/**
	 * @param groupTypSelected the groupTypSelected to set
	 */
	public void setGroupTypSelected(final GrpTypDip groupTypSelected) {
		resetSearch();
		this.groupTypSelected = groupTypSelected;
	}
	
	
	/**
	 * @return the groupTypSelected
	 */
	public GrpTypDip getGroupTypSelected() {
		return groupTypSelected;
	}



	/**
	 * @return the codKeyWordSelected
	 */
	public String getCodKeyWordSelected() {
		return codKeyWordSelected;
	}

	/**
	 * @param codKeyWordSelected the codKeyWordSelected to set
	 */
	public void setCodKeyWordSelected(final String codKeyWordSelected) {
		this.codKeyWordSelected = codKeyWordSelected;
	}

	/**
	 * @return the versionDiplomes
	 */
	public List<VersionDiplomeDTO> getVersionDiplomes() {
		return versionDiplomes;
	}

	/**
	 * @param versionDiplomes the versionDiplomes to set
	 */
	public void setVersionDiplomes(final List<VersionDiplomeDTO> versionDiplomes) {
		this.versionDiplomes = versionDiplomes;
	}

	/**
	 * @return the vrsDipSelected
	 */
	public VersionDiplomeDTO getVrsDipSelected() {
		return vrsDipSelected;
	}

	/**
	 * @param vrsDipSelected the vrsDipSelected to set
	 */
	public void setVrsDipSelected(final VersionDiplomeDTO vrsDipSelected) {
		this.vrsDipSelected = vrsDipSelected;
	}

	/**
	 * @return the versionEtapes
	 */
	public Set<VersionEtapePojo> getVersionEtapes() {
		return versionEtapes;
	}

	/**
	 * @param versionEtapes the versionEtapes to set
	 */
	public void setVersionEtapes(final Set<VersionEtapePojo> versionEtapes) {
		this.versionEtapes = versionEtapes;
	}

	/**
	 * @return the vrsEtpSelected
	 */
	public Set<VersionEtapePojo> getVrsEtpSelected() {
		return vrsEtpSelected;
	}

	/**
	 * @param vrsEtpSelected the vrsEtpSelected to set
	 */
	public void setVrsEtpSelected(final Set<VersionEtapePojo> vrsEtpSelected) {
		this.vrsEtpSelected = vrsEtpSelected;
	}

	/**
	 * @return the objectToAdd
	 */
	public List<Object> getObjectToAdd() {
		return objectToAdd;
	}

	/**
	 * @param objectToAdd the objectToAdd to set
	 */
	public void setObjectToAdd(final List<Object> objectToAdd) {
		this.objectToAdd = objectToAdd;
	}

	/**
	 * @return the allChecked
	 */
	public Boolean getAllChecked() {
		return allChecked;
	}

	/**
	 * @param allChecked the allChecked to set
	 */
	public void setAllChecked(final Boolean allChecked) {
		this.allChecked = allChecked;
	}

	/**
	 * @return the versiontEtpToDelete
	 */
	public VersionEtapePojo getVersiontEtpToDelete() {
		return versiontEtpToDelete;
	}

	/**
	 * @param versiontEtpToDelete the versiontEtpToDelete to set
	 */
	public void setVersiontEtpToDelete(final VersionEtapePojo versiontEtpToDelete) {
		this.versiontEtpToDelete = versiontEtpToDelete;
	}

}
