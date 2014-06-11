package org.esupportail.opi.web.candidat.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public final class CursusScolPojo implements Serializable {

    private static final long serialVersionUID = -257595014971709988L;

    private Integer id;

    // CUR_R1 ou CUR_EXT
    private String type = "CUR_EXT";

    @Size(min=4, max=4)
    private String annee;

    @NotNull
    private String obtention;

    private String mention;

    private Etablissement etablissement;

    private Formation formation;

    private boolean fromApogee = false;


    private CursusScolPojo() {
    }

    public static CursusScolPojo empty() {
        return new CursusScolPojo()
                .withEtablissement(Etablissement.empty())
                .withFormation(Formation.empty());
    }

    public static class Etablissement implements Serializable {

        @NotNull
        private String pays;

        @NotNull
        private String departement;

        @NotNull
        private String ville;

        @NotNull
        private String etablissement;

        private String typeEtablissement;

        private String etablissementEtr;


        private Etablissement(){}

        public static Etablissement empty() {
            return new Etablissement();
        }

        public String getPays() {
            return pays;
        }

        public void setPays(String pays) {
            this.pays = pays;
        }

        public Etablissement withPays(String pays) {
            setPays(pays);
            return this;
        }

        public String getDepartement() {
            return departement;
        }

        public void setDepartement(String departement) {
            this.departement = departement;
        }

        public Etablissement withDepartement(String departement) {
            setDepartement(departement);
            return this;
        }

        public String getVille() {
            return ville;
        }

        public void setVille(String ville) {
            this.ville = ville;
        }

        public Etablissement withVille(String ville) {
            setVille(ville);
            return this;
        }

        public String getEtablissement() {
            return etablissement;
        }

        public void setEtablissement(String etablissement) {
            this.etablissement = etablissement;
        }

        public Etablissement withEtablissement(String etablissement) {
            setEtablissement(etablissement);
            return this;
        }

        public String getTypeEtablissement() {
            return typeEtablissement;
        }

        public void setTypeEtablissement(String typeEtablissement) {
            this.typeEtablissement = typeEtablissement;
        }

        public Etablissement withTypeEtablissement(String typeEtablissement) {
            setTypeEtablissement(typeEtablissement);
            return this;
        }

        public String getEtablissementEtr() {
            return etablissementEtr;
        }

        public void setEtablissementEtr(String etablissementEtr) {
            this.etablissementEtr = etablissementEtr;
        }

        public Etablissement withEtablissementEtr(String etablissementEtr) {
            setEtablissementEtr(etablissementEtr);
            return this;
        }

    }

    public static class Formation implements Serializable {

        private String dac;

        private String libFormation;

        private String diplome;

        private String vdi;

        private String etape;

        private String vet;

        private Formation() {}

        public static Formation empty() {
            return new Formation();
        }

        public String getDac() {
            return dac;
        }

        public void setDac(String dac) {
            this.dac = dac;
        }

        public Formation withDac(String dac) {
            setDac(dac);
            return this;
        }

        public String getLibFormation() {
            return libFormation;
        }

        public void setLibFormation(String libFormation) {
            this.libFormation = libFormation;
        }

        public Formation withLibFormation(String libFormation) {
            setLibFormation(libFormation);
            return this;
        }

        public String getDiplome() {
            return diplome;
        }

        public void setDiplome(String diplome) {
            this.diplome = diplome;
        }

        public Formation withDiplome(String diplome) {
            setDiplome(diplome);
            return this;
        }

        public String getVdi() {
            return vdi;
        }

        public void setVdi(String vdi) {
            this.vdi = vdi;
        }

        public Formation withVdi(String vdi) {
            setVdi(vdi);
            return this;
        }

        public String getEtape() {
            return etape;
        }

        public void setEtape(String etape) {
            this.etape = etape;
        }

        public Formation withEtape(String etape) {
            setEtape(etape);
            return this;
        }

        public String getVet() {
            return vet;
        }

        public void setVet(String vet) {
            this.vet = vet;
        }

        public Formation withVet(String vet) {
            setVet(vet);
            return this;
        }
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CursusScolPojo withId(Integer id) {
        setId(id);
        return this;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public CursusScolPojo withType(String type) {
        setType(type);
        return this;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public CursusScolPojo withAnnee(String annee) {
        setAnnee(annee);
        return this;
    }

    public String getObtention() {
        return obtention;
    }

    public void setObtention(String obtention) {
        this.obtention = obtention;
    }

    public CursusScolPojo withObtention(String obtention) {
        setObtention(obtention);
        return this;
    }

    public String getMention() {
        return mention;
    }

    public void setMention(String mention) {
        this.mention = mention;
    }

    public CursusScolPojo withMention(String mention) {
        setMention(mention);
        return this;
    }

    public boolean isFromApogee() {
        return fromApogee;
    }

    public void setFromApogee(boolean fromApogee) {
        this.fromApogee = fromApogee;
    }

    public CursusScolPojo withFromApogee(boolean fromApogee) {
        setFromApogee(fromApogee);
        return this;
    }

    public Etablissement getEtablissement() {
        return etablissement;
    }

    public void setEtablissement(Etablissement etablissement) {
        this.etablissement = etablissement;
    }

    public CursusScolPojo withEtablissement(Etablissement etablissement) {
        setEtablissement(etablissement);
        return this;
    }

    public Formation getFormation() {
        return formation;
    }

    public void setFormation(Formation formation) {
        this.formation = formation;
    }

    public CursusScolPojo withFormation(Formation formation) {
        setFormation(formation);
        return this;
    }
}
