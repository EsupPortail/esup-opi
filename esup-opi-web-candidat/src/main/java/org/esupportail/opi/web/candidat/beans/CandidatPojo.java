package org.esupportail.opi.web.candidat.beans;

import fj.F;
import fj.data.Option;
import org.esupportail.opi.domain.beans.user.indcursus.CursusPro;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static fj.data.Option.fromString;
import static java.lang.String.format;

public class CandidatPojo implements Serializable {

    private static final long serialVersionUID = 5680402065388394687L;

    @NotNull
    private String dossier;

    @NotNull
    private EtatCivil etatCivil;

    @NotNull
    private AdresseFixe adresseFixe;

    @NotNull
    private Baccalaureat baccalaureat;

    @NotNull
    private List<CursusScolPojo> cursusScols;

    @NotNull
    private List<CursusProPojo> cursusPros;

    @NotNull
    private List<QualifNoDipPojo> qualifs;

    @NotNull
    private List<CampagnePojo> campagnes;

    private CandidatPojo() {}

    public static CandidatPojo empty() {
        return new CandidatPojo()
                .withDossier("")
                .withEtatCivil(EtatCivil.empty())
                .withAdresseFixe(AdresseFixe.empty())
                .withBaccalaureat(Baccalaureat.empty())
                .withCursusScols(new ArrayList<CursusScolPojo>())
                .withCursusPros(new ArrayList<CursusProPojo>())
                .withQualifs(new ArrayList<QualifNoDipPojo>())
                .withCampagnes(new ArrayList<CampagnePojo>());
    }

    public String getDossier() {
        return dossier;
    }

    public void setDossier(String dossier) {
        this.dossier = dossier;
    }

    public CandidatPojo withDossier(String dossier) {
        setDossier(dossier);
        return this;
    }

    public EtatCivil getEtatCivil() {
        return etatCivil;
    }

    public void setEtatCivil(EtatCivil etatCivil) {
        this.etatCivil = etatCivil;
    }

    public CandidatPojo withEtatCivil(EtatCivil etatCivil) {
        setEtatCivil(etatCivil);
        return this;
    }

    public AdresseFixe getAdresseFixe() {
        return adresseFixe;
    }

    public void setAdresseFixe(AdresseFixe adresseFixe) {
        this.adresseFixe = adresseFixe;
    }

    public CandidatPojo withAdresseFixe(AdresseFixe adresseFixe) {
        setAdresseFixe(adresseFixe);
        return this;
    }

    public Baccalaureat getBaccalaureat() {
        return baccalaureat;
    }

    public void setBaccalaureat(Baccalaureat baccalaureat) {
        this.baccalaureat = baccalaureat;
    }

    public CandidatPojo withBaccalaureat(Baccalaureat baccalaureat) {
        setBaccalaureat(baccalaureat);
        return this;
    }

    public List<CursusScolPojo> getCursusScols() {
        return cursusScols;
    }

    public void setCursusScols(List<CursusScolPojo> cursusScols) {
        this.cursusScols = cursusScols;
    }

    public CandidatPojo withCursusScols(List<CursusScolPojo> cursusScols) {
        setCursusScols(cursusScols);
        return this;
    }

    public List<CursusProPojo> getCursusPros() {
        return cursusPros;
    }

    public void setCursusPros(List<CursusProPojo> cursusPros) {
        this.cursusPros = cursusPros;
    }

    public CandidatPojo withCursusPros(List<CursusProPojo> cursusPros) {
        setCursusPros(cursusPros);
        return this;
    }

    public List<QualifNoDipPojo> getQualifs() {
        return qualifs;
    }

    public void setQualifs(List<QualifNoDipPojo> qualifs) {
        this.qualifs = qualifs;
    }

    public CandidatPojo withQualifs(List<QualifNoDipPojo> qualifs) {
        setQualifs(qualifs);
        return this;
    }

    public List<CampagnePojo> getCampagnes() {
        return campagnes;
    }

    public void setCampagnes(List<CampagnePojo> campagnes) {
        this.campagnes = campagnes;
    }

    public CandidatPojo withCampagnes(List<CampagnePojo> campagnes) {
        setCampagnes(campagnes);
        return this;
    }

    public static final class EtatCivil implements Serializable {

        @NotNull
        private String civilite;

        @Size(min = 1, max = 30)
        private String nomPatronymique;

        @Size(max = 30)
        private String nomUsuel;

        @Size(min = 1, max = 20)
        private String prenom;

        @Size(max = 20)
        private String prenomAutre;

        @NotNull
        private Date dateNaissance;

        @NotNull
        private String paysNaissance;

        private String deptNaissance;

        @Size(min = 1, max = 30)
        private String villeNaissance;

        @NotNull
        private String nationalite;

        @Size(max = 11)
        private String nne;

        private EtatCivil() {}

        public static EtatCivil empty() {
            return new EtatCivil();
        }

        public String getCivilite() {
            return civilite;
        }

        public void setCivilite(String civilite) {
            this.civilite = civilite;
        }

        public EtatCivil withCivilite(String civilite) {
            setCivilite(civilite);
            return this;
        }

        public String getNomPatronymique() {
            return nomPatronymique;
        }

        public void setNomPatronymique(String nomPatronymique) {
            this.nomPatronymique = nomPatronymique;
        }

        public EtatCivil withNomPatronymique(String nomPatronymique) {
            setNomPatronymique(nomPatronymique);
            return this;
        }

        public String getNomUsuel() {
            return nomUsuel;
        }

        public void setNomUsuel(String nomUsuel) {
            this.nomUsuel = nomUsuel;
        }

        public EtatCivil withNomUsuel(String nomUsuel) {
            setNomUsuel(nomUsuel);
            return this;
        }

        public String getPrenom() {
            return prenom;
        }

        public void setPrenom(String prenom) {
            this.prenom = prenom;
        }

        public EtatCivil withPrenom(String prenom) {
            setPrenom(prenom);
            return this;
        }

        public String getPrenomAutre() {
            return prenomAutre;
        }

        public void setPrenomAutre(String prenomAutre) {
            this.prenomAutre = prenomAutre;
        }

        public EtatCivil withPrenomAutre(String prenomAutre) {
            setPrenomAutre(prenomAutre);
            return this;
        }

        public Date getDateNaissance() {
            return dateNaissance;
        }

        public void setDateNaissance(Date dateNaissance) {
            this.dateNaissance = dateNaissance;
        }

        public EtatCivil withDateNaissance(Date dateNaissance) {
            setDateNaissance(dateNaissance);
            return this;
        }

        public String getPaysNaissance() {
            return paysNaissance;
        }

        public void setPaysNaissance(String paysNaissance) {
            this.paysNaissance = paysNaissance;
        }

        public EtatCivil withPaysNaissance(String paysNaissance) {
            setPaysNaissance(paysNaissance);
            return this;
        }

        public String getDeptNaissance() {
            return deptNaissance;
        }

        public void setDeptNaissance(String deptNaissance) {
            this.deptNaissance = deptNaissance;
        }

        public EtatCivil withDeptNaissance(String deptNaissance) {
            setDeptNaissance(deptNaissance);
            return this;
        }

        public String getVilleNaissance() {
            return villeNaissance;
        }

        public void setVilleNaissance(String villeNaissance) {
            this.villeNaissance = villeNaissance;
        }

        public EtatCivil withVilleNaissance(String villeNaissance) {
            setVilleNaissance(villeNaissance);
            return this;
        }

        public String getNationalite() {
            return nationalite;
        }

        public void setNationalite(String nationalite) {
            this.nationalite = nationalite;
        }

        public EtatCivil withNationalite(String nationalite) {
            setNationalite(nationalite);
            return this;
        }

        public String getNne() {
            return nne;
        }

        public void setNne(String nne) {
            this.nne = nne;
        }

        public EtatCivil withNne(String nne) {
            setNne(nne);
            return this;
        }

        public String getDisplayName() {
            final String prenom =  getPrenom().toLowerCase();
            final String prenomCap = Character.toUpperCase(prenom.charAt(0)) + prenom.substring(1);
            final Option<String> withNomUsuel = fromString(getNomUsuel()).map(new F<String, String>() {
                public String f(String s) {
                    return format("%s %s", prenomCap, getNomUsuel().toUpperCase());
                }
            });
            final Option<String> withNomPat = fromString(getNomPatronymique()).map(new F<String, String>() {
                public String f(String s) {
                    return format("%s %s", prenomCap, getNomPatronymique().toUpperCase());
                }
            });
            return withNomUsuel.orElse(withNomPat).orSome("");
        }
    }

    public static final class AdresseFixe implements Serializable {

        public static final String EMAIL_PATTERN =
                "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        @NotNull
        @Pattern(regexp = EMAIL_PATTERN)
        private String email;

        @Size(min = 10, max = 15)
        private String telFixe;

        @Size(max = 10)
        private String telPortable;

        @NotNull
        private String pays;

        @Size(min = 2, max = 32)
        private String adresse;

        @Size(max = 70)
        private String adresseCmp1;

        @Size(max = 70)
        private String adresseCmp2;

        @Size(min = 5, max = 5)
        private String codePostal;

        private String ville;

        private String villeEtr;

        private AdresseFixe() {}

        public static AdresseFixe empty() {
            return new AdresseFixe();
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public AdresseFixe withEmail(String email) {
            setEmail(email);
            return this;
        }

        public String getTelFixe() {
            return telFixe;
        }

        public void setTelFixe(String telFixe) {
            this.telFixe = telFixe;
        }

        public AdresseFixe withTelFixe(String telFixe) {
            setTelFixe(telFixe);
            return this;
        }

        public String getTelPortable() {
            return telPortable;
        }

        public void setTelPortable(String telPortable) {
            this.telPortable = telPortable;
        }

        public AdresseFixe withTelPortable(String telPortable) {
            setTelPortable(telPortable);
            return this;
        }

        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }

        public AdresseFixe withPays(String pays) {
            setPays(pays);
            return this;
        }

        public String getAdresse() {
            return adresse;
        }

        public void setAdresse(String adresse) {
            this.adresse = adresse;
        }

        public AdresseFixe withAdresse(String adresse) {
            setAdresse(adresse);
            return this;
        }

        public String getAdresseCmp1() {
            return adresseCmp1;
        }

        public void setAdresseCmp1(String adresseCmp1) {
            this.adresseCmp1 = adresseCmp1;
        }

        public AdresseFixe withAdresseCmp1(String adresseCmp1) {
            setAdresseCmp1(adresseCmp1);
            return this;
        }

        public String getAdresseCmp2() {
            return adresseCmp2;
        }

        public void setAdresseCmp2(String adresseCmp2) {
            this.adresseCmp2 = adresseCmp2;
        }

        public AdresseFixe withAdresseCmp2(String adresseCmp2) {
            setAdresseCmp2(adresseCmp2);
            return this;
        }

        public String getCodePostal() {
            return codePostal;
        }

        public void setCodePostal(String codePostal) {
            this.codePostal = codePostal;
        }

        public AdresseFixe withCodePostal(String codePostal) {
            setCodePostal(codePostal);
            return this;
        }

        public String getVille() {
            return ville;
        }

        public void setVille(String ville) {
            this.ville = ville;
        }

        public AdresseFixe withVille(String ville) {
            setVille(ville);
            return this;
        }

        public String getVilleEtr() {
            return villeEtr;
        }

        public void setVilleEtr(String villeEtr) {
            this.villeEtr = villeEtr;
        }

        public AdresseFixe withVilleEtr(String villeEtr) {
            setVilleEtr(villeEtr);
            return this;
        }
    }

    public static final class Baccalaureat implements Serializable {

        @NotNull
        private String bac;

        private String mention;

        @NotNull
        @Size(min=4, max=4)
        private String dateObtention;

        @NotNull
        private String pays;

        @NotNull
        private String departement;

        @NotNull
        private String ville;

        @NotNull
        private String etablissement;

        private Baccalaureat() {}

        public static Baccalaureat empty() {
            return new Baccalaureat();
        }

        public String getBac() {
            return bac;
        }

        public void setBac(String bac) {
            this.bac = bac;
        }

        public Baccalaureat withBac(String bac) {
            setBac(bac);
            return this;
        }

        public String getMention() {
            return mention;
        }

        public void setMention(String mention) {
            this.mention = mention;
        }

        public Baccalaureat withMention(String mention) {
            setMention(mention);
            return this;
        }

        public String getDateObtention() {
            return dateObtention;
        }

        public void setDateObtention(String dateObtention) {
            this.dateObtention = dateObtention;
        }

        public Baccalaureat withDateObtention(String dateObtention) {
            setDateObtention(dateObtention);
            return this;
        }

        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }

        public Baccalaureat withPays(String pays) {
            setPays(pays);
            return this;
        }

        public String getDepartement() {
            return departement;
        }

        public void setDepartement(String departement) {
            this.departement = departement;
        }

        public Baccalaureat withDepartement(String departement) {
            setDepartement(departement);
            return this;
        }

        public String getVille() {
            return ville;
        }

        public void setVille(String ville) {
            this.ville = ville;
        }

        public Baccalaureat withVille(String ville) {
            setVille(ville);
            return this;
        }

        public String getEtablissement() {
            return etablissement;
        }

        public void setEtablissement(String etablissement) {
            this.etablissement = etablissement;
        }

        public Baccalaureat withEtablissement(String etablissement) {
            setEtablissement(etablissement);
            return this;
        }
    }

}
