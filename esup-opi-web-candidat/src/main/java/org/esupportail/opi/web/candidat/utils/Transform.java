package org.esupportail.opi.web.candidat.utils;

import fj.*;
import fj.data.List;
import fj.data.Option;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.AdresseFix;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.indcursus.*;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.candidat.beans.*;
import org.esupportail.wssi.services.remote.Etablissement;

import java.util.ArrayList;

import static fj.data.Array.iterableArray;
import static fj.data.List.iterableList;
import static fj.data.Option.*;
import static fj.data.Option.somes;
import static java.lang.String.format;
import static org.esupportail.opi.web.candidat.beans.CandidatPojo.*;

public final class Transform {

    public static final F2<DomainApoService, Individu, CandidatPojo> individuToCandidatPojo = new F2<DomainApoService, Individu, CandidatPojo>() {
        public CandidatPojo f( final DomainApoService apoService, final Individu i) {
            final CandidatPojo candidatPojo = CandidatPojo.empty();

            // Etat civil
            final EtatCivil etatCivil = EtatCivil.empty()
                    .withCivilite(i.getSexe())
                    .withDateNaissance(i.getDateNaissance())
                    .withDeptNaissance(i.getCodDepPaysNaissance())
                    .withNationalite(i.getCodPayNationalite())
                    .withNne(format("%s%s", i.getCodeNNE(), i.getCodeClefNNE()))
                    .withNomPatronymique(i.getNomPatronymique())
                    .withNomUsuel(i.getNomUsuel())
                    .withPaysNaissance(i.getCodPayNaissance())
                    .withPrenom(i.getPrenom())
                    .withPrenomAutre(i.getPrenom2())
                    .withVilleNaissance(i.getVilleNaissance());

            // Adresse fixe
            fromNull(i.getAdresses().get(Constantes.ADR_FIX))
                    .foreach(new Effect<Adresse>() {
                        public void e(Adresse a) {
                            final AdresseFixe adresseFixe = AdresseFixe.empty()
                                    .withAdresse(a.getAdr1())
                                    .withAdresseCmp1(a.getAdr2())
                                    .withAdresseCmp2(a.getAdr3())
                                    .withCodePostal(a.getCodBdi())
                                    .withEmail(i.getAdressMail())
                                    .withPays(a.getCodPays())
                                    .withTelFixe(a.getPhoneNumber())
                                    .withTelPortable(i.getNumeroTelPortable())
                                    .withVille(a.getCodCommune())
                                    .withVilleEtr(a.getLibComEtr());
                            candidatPojo.setAdresseFixe(adresseFixe);
                        }
                    });

            // Baccalaureat
            final IndBac indBac = iterableArray(i.getIndBac()).get(0);
            fromNull(indBac).foreach(new Effect<IndBac>() {
                public void e(IndBac indBac) {
                    final Baccalaureat baccalaureat = Baccalaureat.empty()
                            .withBac(indBac.getCodBac())
                            .withDateObtention(indBac.getDateObtention())
                            .withDepartement(indBac.getCodDep())
                            .withEtablissement(indBac.getCodEtb())
                            .withMention(indBac.getCodMnb())
                            .withPays(indBac.getCodPay())
                            .withVille(indBac.getCodCom());
                    candidatPojo.setBaccalaureat(baccalaureat);
                }
            });

            // Cursus post bac
            final List<CursusScolPojo> cursusScols =  iterableList(i.getCursusScol())
                    .map(cursusScolToPojo.f(apoService));

            final List<IndCursus> indCursus = iterableList(i.getCursus());

            // Cursus pro
            final List<CursusProPojo> cursusPros = somes(indCursus
                    .map(new F<IndCursus, Option<CursusPro>>() {
                        public Option<CursusPro> f(IndCursus c) {
                            if (c instanceof CursusPro) {
                                return some((CursusPro) c);
                            }
                            return none();
                        }
                    })).map(cursusProToPojo);

            // Qualifications non professionalisantes
            final List<QualifNoDipPojo> qualifs = somes(indCursus
                    .map(new F<IndCursus, Option<QualifNonDiplomante>>() {
                        public Option<QualifNonDiplomante> f(IndCursus c) {
                            if (c instanceof QualifNonDiplomante) {
                                return some((QualifNonDiplomante) c);
                            }
                            return none();
                        }
                    })).map(qualifToPojo);

            // Campagnes de candidatures
            final List<CampagnePojo> campagnes = iterableList(i.getCampagnes())
                    .map(campagneToPojo);

            return candidatPojo
                    .withDossier(i.getNumDossierOpi())
                    .withEtatCivil(etatCivil)
                    .withCursusScols(new ArrayList<>(cursusScols.toCollection()))
                    .withCursusPros(new ArrayList<>(cursusPros.toCollection()))
                    .withQualifs(new ArrayList<>(qualifs.toCollection()))
                    .withCampagnes(new ArrayList<>(campagnes.toCollection()));
        }
    };

    public static final F2<DomainApoService, IndCursusScol, CursusScolPojo> cursusScolToPojo = new F2<DomainApoService, IndCursusScol, CursusScolPojo>() {
        public CursusScolPojo f(final DomainApoService apoService, IndCursusScol indCursusScol) {
            final CursusScolPojo cursusScol = CursusScolPojo.empty()
                    .withId(indCursusScol.getId())
                    .withType(indCursusScol.getType())
                    .withAnnee(indCursusScol.getAnnee())
                    .withMention(indCursusScol.getLibMention())
                    .withObtention(indCursusScol.getResultat())
                    .withFromApogee(indCursusScol.getTemoinFromApogee());

            final CursusScolPojo.Formation formation = cursusScol.getFormation();
            final CursusScolPojo.Etablissement etablissement = cursusScol.getEtablissement();

            if (indCursusScol instanceof CursusExt) {
                final CursusExt cursusExt = (CursusExt) indCursusScol;
                final String libEtab =
                        fromString(cursusExt.getCodEtablissement()).map(new F<String, String>() {
                            public String f(String codeEtab) {
                                final Etablissement etb = apoService.getEtablissement(codeEtab);
                                return fromNull(etb).map(new F<Etablissement, String>() {
                                    public String f(Etablissement etablissement) {
                                        return etablissement.getLibEtb();
                                    }
                                }).orSome("");
                            }
                        }).orSome(cursusExt.getLibEtbEtr());
                formation
                        .withDac(cursusExt.getCodDac())
                        .withLibFormation(fromString(indCursusScol.getLibelle()).orSome(cursusExt.getLibDac()));
                etablissement
                        .withEtablissement(libEtab);
                cursusScol
                        .withEtablissement(etablissement)
                        .withFormation(formation);

            }
            if (indCursusScol instanceof CursusR1) {
                final CursusR1 cursusR1 = (CursusR1) indCursusScol;
                formation
                        .withDiplome(cursusR1.getCodDiplome())
                        .withVdi(cursusR1.getCodVersionDiplome())
                        .withEtape(cursusR1.getCodEtape())
                        .withVet(cursusR1.getCodVersionEtape());
                etablissement
                        .withEtablissement(apoService.getEtablissement(cursusR1.getCodEtablissement()).getLibEtb());
                cursusScol
                        .withEtablissement(etablissement)
                        .withFormation(formation);
            }
            return cursusScol;
        }
    };

    public static final F<CursusPro, CursusProPojo> cursusProToPojo = new F<CursusPro, CursusProPojo>() {
        public CursusProPojo f(CursusPro cursusPro) {
            return CursusProPojo.empty()
                    .withId(cursusPro.getId())
                    .withAnnee(cursusPro.getAnnee())
                    .withDuree(cursusPro.getDuree())
                    .withDescription(cursusPro.getComment())
                    .withOrganisme(cursusPro.getOrganisme())
                    .withQuotite(cursusPro.getQuotite());
        }
    };

    public static final F<QualifNonDiplomante, QualifNoDipPojo> qualifToPojo = new F<QualifNonDiplomante, QualifNoDipPojo>() {
        public QualifNoDipPojo f(QualifNonDiplomante q) {
            return QualifNoDipPojo.empty()
                    .withId(q.getId())
                    .withAnnee(q.getAnnee())
                    .withDuree(q.getDuree())
                    .withDescription(q.getComment())
                    .withOrganisme(q.getOrganisme())
                    .withIntitule(q.getLibelle());
        }
    };

    public static final F<Campagne, CampagnePojo> campagneToPojo = new F<Campagne, CampagnePojo>() {
        public CampagnePojo f(Campagne campagne) {
            return CampagnePojo.empty()
                    .withCode(campagne.getCode())
                    .withCodAnu(campagne.getCodAnu())
                    .withCodeRI(campagne.getCodeRI());
        }
    };

    public static final F2<CandidatPojo, Individu, Individu> candidatPojoToIndividu = new F2<CandidatPojo, Individu, Individu>() {
        public Individu f(final CandidatPojo c, final Individu individu) {
            final EtatCivil etatCivil = c.getEtatCivil();
            final AdresseFixe adresseFixe = c.getAdresseFixe();
            final Baccalaureat baccalaureat = c.getBaccalaureat();

            // Etat civil
            individu.setSexe(etatCivil.getCivilite());
            individu.setDateNaissance(etatCivil.getDateNaissance());
            individu.setCodDepPaysNaissance(etatCivil.getDeptNaissance());
            individu.setCodPayNationalite(etatCivil.getNationalite());
            fromString(etatCivil.getNne()).foreach(new Effect<String>() {
                public void e(String nneStr) {
                    individu.setCodeNNE(nneStr.substring(0, nneStr.length() - 1));
                    individu.setCodeClefNNE(nneStr.substring(nneStr.length() - 1));
                }
            });
            individu.setNomPatronymique(etatCivil.getNomPatronymique());
            individu.setNomUsuel(etatCivil.getNomUsuel());
            individu.setCodPayNaissance(etatCivil.getPaysNaissance());
            individu.setPrenom(etatCivil.getPrenom());
            individu.setPrenom2(etatCivil.getPrenomAutre());
            individu.setVilleNaissance(etatCivil.getVilleNaissance());

            // Adresse fixe
            final Adresse adresse = fromNull(individu.getAdresses().get(Constantes.ADR_FIX))
                    .orSome(new AdresseFix());
            adresse.setAdr1(adresseFixe.getAdresse());
            adresse.setAdr2(adresseFixe.getAdresseCmp1());
            adresse.setAdr3(adresseFixe.getAdresseCmp2());
            adresse.setCodBdi(adresseFixe.getCodePostal());
            adresse.setCodPays(adresseFixe.getPays());
            adresse.setPhoneNumber(adresseFixe.getTelFixe());
            adresse.setCodCommune(adresseFixe.getVille());
            adresse.setLibComEtr(adresseFixe.getVilleEtr());
            individu.setAdressMail(adresseFixe.getEmail());
            individu.setNumeroTelPortable(adresseFixe.getTelPortable());

            individu.getAdresses().put(Constantes.ADR_FIX, adresse);

            // Baccalaureat
            final IndBac indBac = fromNull(iterableArray(individu.getIndBac()).get(0)).orSome(new IndBac());
            individu.getIndBac().remove(indBac);
            indBac.setCodBac(baccalaureat.getBac());
            indBac.setCodCom(baccalaureat.getVille());
            indBac.setCodDep(baccalaureat.getDepartement());
            indBac.setCodEtb(baccalaureat.getEtablissement());
            indBac.setCodMnb(baccalaureat.getMention());
            indBac.setCodPay(baccalaureat.getPays());
            indBac.setDateObtention(baccalaureat.getDateObtention());
            individu.getIndBac().add(indBac);

            return individu;
        }
    };

    public static final F3<DomainApoService, Individu, CursusScolPojo, IndCursusScol> cursusScolPojoToIndCursusScol =
            new F3<DomainApoService, Individu, CursusScolPojo, IndCursusScol>() {
                public IndCursusScol f(DomainApoService aposervice, Individu i, CursusScolPojo p) {
                    final CursusScolPojo.Formation formation = p.getFormation();
                    final CursusScolPojo.Etablissement etablissement = p.getEtablissement();
                    if (p.getType().equals("CUR_R1")) {
                        final CursusR1 cursusR1 = new CursusR1();
                        cursusR1.setId(p.getId());
                        cursusR1.setAnnee(p.getAnnee());
                        cursusR1.setCodEtablissement(etablissement.getEtablissement());
                        cursusR1.setCodDiplome(formation.getDiplome());
                        cursusR1.setCodEtape(formation.getEtape());
                        cursusR1.setCodVersionDiplome(formation.getVdi());
                        cursusR1.setCodVersionEtape(formation.getVet());
                        cursusR1.setTemoinFromApogee(p.isFromApogee());
                        cursusR1.setIndividu(i);
                        return cursusR1;
                    }

                    final CursusExt cursusExt = new CursusExt();
                    cursusExt.setId(p.getId());
                    cursusExt.setCodDac(formation.getDac());
                    fromNull(aposervice.getEtablissement(
                            p.getEtablissement().getEtablissement())).map(new F<Etablissement, Unit>() {
                                public Unit f(Etablissement e) {
                                    cursusExt.setCodEtablissement(e.getCodEtb());
                                    cursusExt.setCodTypeEtab(e.getCodTpe());
                                    return Unit.unit();
                                }
                            });
                    cursusExt.setLibEtbEtr(p.getEtablissement().getEtablissementEtr());
                    cursusExt.setAnnee(p.getAnnee());
                    cursusExt.setLibMention(p.getMention());
                    cursusExt.setResultat(p.getObtention());
                    cursusExt.setTemoinFromApogee(p.isFromApogee());
                    cursusExt.setIndividu(i);
                    return cursusExt;
                }
    };

    public static final F2<Individu, CursusProPojo, CursusPro> cursusProPojoToCursusPro = new F2<Individu, CursusProPojo, CursusPro>() {
        public CursusPro f(Individu i, CursusProPojo p) {
            CursusPro cursusPro = new CursusPro();
            cursusPro.setId(p.getId());
            cursusPro.setAnnee(p.getAnnee());
            cursusPro.setDuree(p.getDuree());
            cursusPro.setComment(p.getDescription());
            cursusPro.setOrganisme(p.getOrganisme());
            cursusPro.setQuotite(p.getQuotite());
            cursusPro.setIndividu(i);
            return cursusPro;
        }
    };

    public static final F2<Individu, QualifNoDipPojo, QualifNonDiplomante> pojoToQualif = new F2<Individu, QualifNoDipPojo, QualifNonDiplomante>() {
        public QualifNonDiplomante f(Individu i, QualifNoDipPojo p) {
            QualifNonDiplomante qualif = new QualifNonDiplomante();
            qualif.setId(p.getId());
            qualif.setAnnee(p.getAnnee());
            qualif.setDuree(p.getDuree());
            qualif.setComment(p.getDescription());
            qualif.setOrganisme(p.getOrganisme());
            qualif.setLibelle(p.getIntitule());
            qualif.setIndividu(i);
            return qualif;
        }
    };
}
