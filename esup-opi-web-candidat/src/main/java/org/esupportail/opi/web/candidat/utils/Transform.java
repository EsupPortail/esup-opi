package org.esupportail.opi.web.candidat.utils;

import fj.Effect;
import fj.F;
import fj.F2;
import org.esupportail.opi.domain.beans.user.Adresse;
import org.esupportail.opi.domain.beans.user.AdresseFix;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.utils.Constantes;
import org.esupportail.opi.web.candidat.beans.CandidatPojo;

import static fj.data.Option.fromNull;
import static fj.data.Option.fromString;
import static java.lang.String.format;
import static org.esupportail.opi.web.candidat.beans.CandidatPojo.*;

public final class Transform {

    public static final F<Individu, CandidatPojo> individuToCandidatPojo = new F<Individu, CandidatPojo>() {
        public CandidatPojo f(final Individu i) {
            final CandidatPojo candidatPojo = CandidatPojo.empty();
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
            return candidatPojo
                    .withDossier(i.getNumDossierOpi())
                    .withEtatCivil(etatCivil);
        }
    };

    public static final F2<CandidatPojo, Individu, Individu> candidatPojoToIndividu = new F2<CandidatPojo, Individu, Individu>() {
        public Individu f(final CandidatPojo c, final Individu individu) {
            final EtatCivil etatCivil = c.getEtatCivil();
            final AdresseFixe adresseFixe = c.getAdresseFixe();

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

            return individu;
        }
    };
}
