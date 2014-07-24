package org.esupportail.opi.domain.dto;


import fj.F;
import fj.data.Option;
import org.esupportail.opi.domain.beans.etat.EtatIndividu;

import java.io.Serializable;
import java.util.*;

import static fj.data.Option.fromString;
import static java.lang.String.format;

public class CandidatDTO {

    private String numDossierOpi;

    private EtatCivilDTO etatCivil;

    private AdresseFixeDTO adresseFixe;

    private BaccalaureatDTO baccalaureat;


    private Collection<CursusScolDTO> cursusScols;

    private Collection<CampagneDTO> campagnes;

    private String etatCandidat;

    /**
     * The vows of individu.
     * Default value : empty
     * where="temoinEnService = y"
     */
    private Collection<CandidatVoeuDTO> voeux;

    private CandidatDTO() {}

    public static CandidatDTO empty() {
        return new CandidatDTO()
                .withDossier("")
                .withEtatCivil(EtatCivilDTO.empty())
                .withAdresseFixe(AdresseFixeDTO.empty())
                .withBaccalaureat(BaccalaureatDTO.empty())
                .withCursusScols(new ArrayList<CursusScolDTO>())
                .withVoeux(new ArrayList<CandidatVoeuDTO>())
                .withCampagnes(new ArrayList<CampagneDTO>());
    }

    public String getNumDossierOpi() {
        return numDossierOpi;
    }

    public void setNumDossierOpi(String numDossierOpi) {
        this.numDossierOpi = numDossierOpi;
    }

    public CandidatDTO withDossier(String numDossierOpi) {
        setNumDossierOpi(numDossierOpi);
        return this;
    }

    public EtatCivilDTO getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(EtatCivilDTO etatCivil) {
        this.etatCivil = etatCivil;
    }

    public CandidatDTO withEtatCivil(EtatCivilDTO etatCivil) {
        setEtatCivil(etatCivil);
        return this;
    }

    public String getEtatCandidat() {
        return etatCandidat;
    }

    public void setEtatCandidat(String etatCandidat) {
        this.etatCandidat = etatCandidat;
    }

    public CandidatDTO withEtatCandidat(String etatCandidat) {
        setEtatCandidat(etatCandidat);
        return this;
    }

    public AdresseFixeDTO getAdresseFixe() {
        return adresseFixe;
    }

    public void setAdresseFixe(AdresseFixeDTO adresseFixe) {
        this.adresseFixe = adresseFixe;
    }

    public CandidatDTO withAdresseFixe(AdresseFixeDTO adresseFixe) {
        setAdresseFixe(adresseFixe);
        return this;
    }

    public BaccalaureatDTO getBaccalaureat() {
        return baccalaureat;
    }

    public void setBaccalaureat(BaccalaureatDTO baccalaureat) {
        this.baccalaureat = baccalaureat;
    }

    public CandidatDTO withBaccalaureat(BaccalaureatDTO baccalaureat) {
        setBaccalaureat(baccalaureat);
        return this;
    }

    public Collection<CursusScolDTO> getCursusScols() {
        return cursusScols;
    }

    public void setCursusScols(Collection<CursusScolDTO> cursusScols) {
        this.cursusScols = cursusScols;
    }

    public CandidatDTO withCursusScols(ArrayList<CursusScolDTO> cursusScols) {
        setCursusScols(cursusScols);
        return this;
    }

    public Collection<CandidatVoeuDTO> getVoeux() {
        return voeux;
    }

    public void setVoeux(Collection<CandidatVoeuDTO> voeux) {
        this.voeux = voeux;
    }

    public CandidatDTO withVoeux(ArrayList<CandidatVoeuDTO> voeux) {
        setVoeux(voeux);
        return this;
    }

    public Collection<CampagneDTO> getCampagnes() {
        return campagnes;
    }

    public void setCampagnes(Collection<CampagneDTO> campagnes) {
        this.campagnes = campagnes;
    }

    public CandidatDTO withCampagnes(ArrayList<CampagneDTO> campagnes) {
        setCampagnes(campagnes);
        return this;
    }

    public static final class EtatCivilDTO implements Serializable {

        private String civilite;

        private String nomPatronymique;

        private String nomUsuel;

        private String prenom;

        private String prenomAutre;

        private Date dateNaissance;

        private String paysNaissance;

        private String deptNaissance;

        private String villeNaissance;

        private String nationalite;

        private String nne;

        private String email;

        private EtatCivilDTO() {}

        public static final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";


        public static EtatCivilDTO empty() {
            return new EtatCivilDTO();
        }

        public String getCivilite() {
            return civilite;
        }

        public void setCivilite(String civilite) {
            this.civilite = civilite;
        }

        public EtatCivilDTO withCivilite(String civilite) {
            setCivilite(civilite);
            return this;
        }

        public String getNomPatronymique() {
            return nomPatronymique;
        }

        public void setNomPatronymique(String nomPatronymique) {
            this.nomPatronymique = nomPatronymique;
        }

        public EtatCivilDTO withNomPatronymique(String nomPatronymique) {
            setNomPatronymique(nomPatronymique);
            return this;
        }

        public String getNomUsuel() {
            return nomUsuel;
        }

        public void setNomUsuel(String nomUsuel) {
            this.nomUsuel = nomUsuel;
        }

        public EtatCivilDTO withNomUsuel(String nomUsuel) {
            setNomUsuel(nomUsuel);
            return this;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public EtatCivilDTO withPrenom(String prenom) {
            setPrenom(prenom);
            return this;
        }

        public String getPrenomAutre() {
            return prenomAutre;
        }

        public void setPrenomAutre(String prenomAutre) {
            this.prenomAutre = prenomAutre;
        }

        public EtatCivilDTO withPrenomAutre(String prenomAutre) {
            setPrenomAutre(prenomAutre);
            return this;
        }

        public Date getDateNaissance() {
            return dateNaissance;
        }

        public void setDateNaissance(Date dateNaissance) {
            this.dateNaissance = dateNaissance;
        }

        public EtatCivilDTO withDateNaissance(Date dateNaissance) {
            setDateNaissance(dateNaissance);
            return this;
        }

        public String getPaysNaissance() {
            return paysNaissance;
        }

        public void setPaysNaissance(String paysNaissance) {
            this.paysNaissance = paysNaissance;
        }

        public EtatCivilDTO withPaysNaissance(String paysNaissance) {
            setPaysNaissance(paysNaissance);
            return this;
        }

        public String getDeptNaissance() {
            return deptNaissance;
        }

        public void setDeptNaissance(String deptNaissance) {
            this.deptNaissance = deptNaissance;
        }

        public EtatCivilDTO withDeptNaissance(String deptNaissance) {
            setDeptNaissance(deptNaissance);
            return this;
        }

        public String getVilleNaissance() {
            return villeNaissance;
        }

        public void setVilleNaissance(String villeNaissance) {
            this.villeNaissance = villeNaissance;
        }

        public EtatCivilDTO withVilleNaissance(String villeNaissance) {
            setVilleNaissance(villeNaissance);
            return this;
        }

        public String getNationalite() {
            return nationalite;
        }

        public void setNationalite(String nationalite) {
            this.nationalite = nationalite;
        }

        public EtatCivilDTO withNationalite(String nationalite) {
            setNationalite(nationalite);
            return this;
        }

        public String getNne() {
            return nne;
        }

        public void setNne(String nne) {
            this.nne = nne;
        }

        public EtatCivilDTO withNne(String nne) {
            setNne(nne);
            return this;
        }
    }

    public static final class AdresseFixeDTO implements Serializable {

        public static final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        private String email;

        private String telFixe;

        private String telPortable;

        private String pays;

        private String adresse;

        private String adresseCmp1;

        private String adresseCmp2;

        private String codePostal;

        private String ville;

        private String villeEtr;

        private AdresseFixeDTO() {}

        public static AdresseFixeDTO empty() {
            return new AdresseFixeDTO();
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public AdresseFixeDTO withEmail(String email) {
            setEmail(email);
            return this;
        }

        public String getTelFixe() {
            return telFixe;
        }

        public void setTelFixe(String telFixe) {
            this.telFixe = telFixe;
        }

        public AdresseFixeDTO withTelFixe(String telFixe) {
            setTelFixe(telFixe);
            return this;
        }

        public String getTelPortable() {
            return telPortable;
        }

        public void setTelPortable(String telPortable) {
            this.telPortable = telPortable;
        }

        public AdresseFixeDTO withTelPortable(String telPortable) {
            setTelPortable(telPortable);
            return this;
        }

        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }

        public AdresseFixeDTO withPays(String pays) {
            setPays(pays);
            return this;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        public AdresseFixeDTO withAdresse(String adresse) {
            setAdresse(adresse);
            return this;
        }

        public String getAdresseCmp1() {
            return adresseCmp1;
        }

        public void setAdresseCmp1(String adresseCmp1) {
            this.adresseCmp1 = adresseCmp1;
        }

        public AdresseFixeDTO withAdresseCmp1(String adresseCmp1) {
            setAdresseCmp1(adresseCmp1);
            return this;
        }

        public String getAdresseCmp2() {
            return adresseCmp2;
        }

        public void setAdresseCmp2(String adresseCmp2) {
            this.adresseCmp2 = adresseCmp2;
        }

        public AdresseFixeDTO withAdresseCmp2(String adresseCmp2) {
            setAdresseCmp2(adresseCmp2);
            return this;
        }

        public String getCodePostal() {
            return codePostal;
        }

        public void setCodePostal(String codePostal) {
            this.codePostal = codePostal;
        }

        public AdresseFixeDTO withCodePostal(String codePostal) {
            setCodePostal(codePostal);
            return this;
        }

        public String getVille() {
            return ville;
        }

        public void setVille(String ville) {
            this.ville = ville;
        }

        public AdresseFixeDTO withVille(String ville) {
            setVille(ville);
            return this;
        }

        public String getVilleEtr() {
            return villeEtr;
        }

        public void setVilleEtr(String villeEtr) {
            this.villeEtr = villeEtr;
        }

        public AdresseFixeDTO withVilleEtr(String villeEtr) {
            setVilleEtr(villeEtr);
            return this;
        }
    }

    public static final class BaccalaureatDTO implements Serializable {

        private String bac;

        private String mention;

        private String dateObtention;

        private String pays;

        private String departement;

        private String ville;

        private String etablissement;

        private String codTpe;

        private BaccalaureatDTO() {}

        public static BaccalaureatDTO empty() {
            return new BaccalaureatDTO();
        }

        public String getBac() {
            return bac;
        }

        public void setBac(String bac) {
            this.bac = bac;
        }

        public BaccalaureatDTO withBac(String bac) {
            setBac(bac);
            return this;
        }

        public String getMention() {
            return mention;
        }

        public void setMention(String mention) {
            this.mention = mention;
        }

        public BaccalaureatDTO withMention(String mention) {
            setMention(mention);
            return this;
        }

        public String getDateObtention() {
            return dateObtention;
        }

        public void setDateObtention(String dateObtention) {
            this.dateObtention = dateObtention;
        }

        public BaccalaureatDTO withDateObtention(String dateObtention) {
            setDateObtention(dateObtention);
            return this;
        }

        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }

        public BaccalaureatDTO withPays(String pays) {
            setPays(pays);
            return this;
        }

        public String getDepartement() {
            return departement;
        }

        public void setDepartement(String departement) {
            this.departement = departement;
        }

        public BaccalaureatDTO withDepartement(String departement) {
            setDepartement(departement);
            return this;
        }

        public String getVille() {
            return ville;
        }

        public void setVille(String ville) {
            this.ville = ville;
        }

        public BaccalaureatDTO withVille(String ville) {
            setVille(ville);
            return this;
        }

        public String getEtablissement() {
            return etablissement;
        }

        public void setEtablissement(String etablissement) {
            this.etablissement = etablissement;
        }

        public BaccalaureatDTO withEtablissement(String etablissement) {
            setEtablissement(etablissement);
            return this;
        }
    }
}
