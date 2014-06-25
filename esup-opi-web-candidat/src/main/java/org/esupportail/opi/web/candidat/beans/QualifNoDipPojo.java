package org.esupportail.opi.web.candidat.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public final class QualifNoDipPojo implements Serializable {

    private static final long serialVersionUID = -1035741147546660682L;

    private Integer id;

    @NotNull
    private String annee;

    @NotNull
    private String duree;

    @NotNull
    private String organisme;

    @NotNull
    private String intitule;

    @Size(max = 2000)
    private String description;

    private QualifNoDipPojo() {}

    public static QualifNoDipPojo empty() {
        return new QualifNoDipPojo();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public QualifNoDipPojo withId(Integer id) {
        setId(id);
        return this;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public QualifNoDipPojo withAnnee(String annee) {
        setAnnee(annee);
        return this;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public QualifNoDipPojo withDuree(String duree) {
        setDuree(duree);
        return this;
    }

    public String getOrganisme() {
        return organisme;
    }

    public void setOrganisme(String organisme) {
        this.organisme = organisme;
    }

    public QualifNoDipPojo withOrganisme(String organisme) {
        setOrganisme(organisme);
        return this;
    }

    public String getIntitule() {
        return intitule;
    }

    public void setIntitule(String intitule) {
        this.intitule = intitule;
    }

    public QualifNoDipPojo withIntitule(String intitule) {
        setIntitule(intitule);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public QualifNoDipPojo withDescription(String description) {
        setDescription(description);
        return this;
    }
}
