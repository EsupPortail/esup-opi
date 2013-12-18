package org.esupportail.opi.web.utils.paginator;

import fj.*;
import fj.data.Array;
import fj.data.Option;
import org.esupportail.opi.domain.DomainApoService;
import org.esupportail.opi.domain.DomainService;
import org.esupportail.opi.domain.ParameterService;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.parameters.TypeTraitement;
import org.esupportail.opi.domain.beans.references.commission.Commission;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Gestionnaire;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.User;
import org.esupportail.opi.web.beans.parameters.RegimeInscription;
import org.esupportail.opi.web.beans.pojo.IndRechPojo;
import org.esupportail.opi.web.controllers.SessionController;
import org.esupportail.opi.web.utils.fj.Conversions;
import org.primefaces.model.SortOrder;

import java.util.*;

import static fj.Bottom.error;
import static fj.data.Array.iterableArray;
import static fj.data.Option.*;
import static org.esupportail.opi.utils.primefaces.PFFilters.pfFilters;
import static org.esupportail.opi.web.utils.fj.Functions.applyPut;

public final class PaginationFunctions {
    private PaginationFunctions() { throw error("Unauthorized"); }

    public static F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Array<Individu>>>
    getData(final P1<SessionController> session,
            final P1<DomainService> serv,
            final P1<DomainApoService> apo,
            final P1<ParameterService> param,
            final P1<IndRechPojo> indPojo) {
        return new F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Array<Individu>>>() {
            public P2<Long, Array<Individu>> f(final Integer first, final Integer pageSize,
                                               final String sortField, final SortOrder sortOrder,
                                               final Map<String, String> filters) {
                final SessionController sessionCont = session._1();
                final DomainService domainServ = serv._1();
                final DomainApoService apoServ = apo._1();
                final ParameterService paramServ = param._1();
                final IndRechPojo indRechPojo = indPojo._1();

                // le gestionnaire courant
                User user = sessionCont.getCurrentUser();
                final Gestionnaire gest = !(user instanceof Gestionnaire) ?
                        sessionCont.getManager() : (Gestionnaire) user;

                // les filtres :
                // 1. les numdossier, nom, prenom
                fromString(indRechPojo.getNumDossierOpiRecherche())
                        .foreach(applyPut("numDossierOpi", filters));
                fromString(indRechPojo.getNomRecherche())
                        .foreach(applyPut("nomPatronymique", filters));
                fromString(indRechPojo.getPrenomRecherche())
                        .foreach(applyPut("prenom", filters));
                iif(indRechPojo.isUseVoeuFilter(), "true")
                        .foreach(applyPut("useVoeuFilter", filters));

                // 2. le ou les types de décision
                final List<TypeDecision> typesDec = indRechPojo.getTypesDec();

                // 3. les étapes (TraitementCmi) de la commission
                Integer idCom = indRechPojo.getIdCmi();
                Option<Array<Commission>> cmis = iif(idCom != null && idCom > 0, idCom)
                        .map(new F<Integer, Array<Commission>>() {
                            public Array<Commission> f(Integer idCmi) {
                                return Array.single(paramServ.getCommission(idCmi, null));
                            }})
                        .orElse(iif(indRechPojo.isUseGestCommsFilter(), new P1<Array<Commission>>() {
                            public Array<Commission> _1() {
                                return iterableArray(fromNull(apoServ.getListCommissionsByRight(gest, true))
                                        .orSome(new HashSet<Commission>()));
                            }
                        }));

                final Option<Set<TraitementCmi>> trtCmis =
                        cmis.map(new F<Array<Commission>, Array<TraitementCmi>>() {
                            public Array<TraitementCmi> f(Array<Commission> commissions) {
                                return commissions.bind(new F<Commission, Array<TraitementCmi>>() {
                                    public Array<TraitementCmi> f(Commission com) {
                                        return Array.join(fromNull(com.getTraitementCmi())
                                                .toArray()
                                                .map(Conversions.<TraitementCmi>setToArray_()));
                                    }
                                });
                            }
                        }.andThen(Conversions.<TraitementCmi>arrayToSet_()));

                // 4. les régimes d'inscription
                final Set<Integer> listCodesRI = new HashSet<>(
                        iterableArray(fromNull(indRechPojo.getListeRI())
                                .orSome(Collections.<RegimeInscription>emptySet()))
                                .map(new F<RegimeInscription, Integer>() {
                                    public Integer f(RegimeInscription ri) {
                                        return ri.getCode();
                                    }
                                }).toCollection());

                // 5. caractère 'traité' ou non du voeu
                Boolean excludeTreated = indRechPojo.getExcludeWishProcessed();
                final Option<Boolean> wishTreated = iif(excludeTreated != null && excludeTreated, false);

                // 6. caratère 'validé' ou non du voeu
                final Option<Boolean> validWish = fromNull(indRechPojo.getSelectValid());

                // 7. le type de traitement
                final Collection<TypeTraitement> typeTrtmts = indRechPojo.getTypeTraitements();

                // 8. Date de création des voeux
                final Option<Date> dateCrea = fromNull(indRechPojo.getDateCreationVoeuRecherchee());

                return domainServ.sliceOfInd(
                        pfFilters((long) first, (long) pageSize, sortField, sortOrder, filters),
                        typesDec, validWish, wishTreated, dateCrea, typeTrtmts, trtCmis, listCodesRI);
            }
        };
    }

    public static final F2<String, Individu, Boolean> findByRowKey = new F2<String, Individu, Boolean>() {
        public Boolean f(String rowKey, Individu individu) {
            return individu.getId().toString().equals(rowKey);
        }
    };

}
