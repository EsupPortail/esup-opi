package org.esupportail.opi.web.candidat.beans;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

public final class CursusProPojo implements Serializable {

    private static final long serialVersionUID = -6925165563574492377L;

    private Integer id;

    @NotNull
    private String annee;

    private String quotite;

    @NotNull
    private String duree;

    @NotNull
    private String organisme;

    @Size(max = 2000)
    private String description;

    private CursusProPojo() {}

    public static CursusProPojo empty() {
        return new CursusProPojo();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CursusProPojo withId(Integer id) {
        setId(id);
        return this;
    }

    public String getAnnee() {
        return annee;
    }

    public void setAnnee(String annee) {
        this.annee = annee;
    }

    public CursusProPojo withAnnee(String annee) {
        setAnnee(annee);
        return this;
    }

    public String getQuotite() {
        return quotite;
    }

    public void setQuotite(String quotite) {
        this.quotite = quotite;
    }

    public CursusProPojo withQuotite(String quotite) {
        setQuotite(quotite);
        return this;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public CursusProPojo withDuree(String duree) {
        setDuree(duree);
        return this;
    }

    public String getOrganisme() {
        return organisme;
    }

    public void setOrganisme(String organisme) {
        this.organisme = organisme;
    }

    public CursusProPojo withOrganisme(String organisme) {
        setOrganisme(organisme);
        return this;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public CursusProPojo withDescription(String description) {
        setDescription(description);
        return this;
    }
}
