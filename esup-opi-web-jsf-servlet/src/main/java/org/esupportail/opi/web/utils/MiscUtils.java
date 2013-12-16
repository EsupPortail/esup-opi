package org.esupportail.opi.web.utils;

import fj.*;
import fj.control.parallel.Strategy;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursus;
import org.esupportail.opi.domain.beans.user.indcursus.IndCursusScol;
import org.esupportail.opi.web.beans.pojo.IndCursusScolPojo;
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.beans.utils.comparator.ComparatorString;
import org.esupportail.wssi.services.remote.Etablissement;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.concurrent.Executors;

import static fj.data.Stream.iterableStream;
import static java.util.Arrays.asList;

public final class MiscUtils {
    private MiscUtils() { throw new UnsupportedOperationException(); }

    public static void initIndCursusScolPojo(IndividuPojo iP, DomainApoService apoService) {
        final ArrayList<IndCursusScolPojo> cursusList = new ArrayList<>();
		for (IndCursusScol iCur : iP.getIndividu().getCursusScol()) {
            final IndCursusScolPojo pojo = new IndCursusScolPojo(iCur);
            final Etablissement etablissement =
                    apoService.getEtablissement(iCur.getCodEtablissement());
            iCur.setCodTypeEtab(etablissement == null ? "" : etablissement.getCodTpe());
            pojo.setEtablissement(etablissement);
            cursusList.add(pojo);
        }
		Collections.sort(cursusList, new Comparator<IndCursusScolPojo>() {
            public int compare(IndCursusScolPojo c1, IndCursusScolPojo c2) {
                return c1.getCursus().getAnnee().compareToIgnoreCase(c2.getCursus().getAnnee());
            }
        });
        iP.setIndCursusScolPojo(cursusList);
    }
}
