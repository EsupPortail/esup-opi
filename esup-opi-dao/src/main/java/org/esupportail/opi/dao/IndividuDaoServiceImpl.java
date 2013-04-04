package org.esupportail.opi.dao;

import com.mysema.query.jpa.JPQLSubQuery;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.SetPath;
import com.mysema.query.types.template.BooleanTemplate;
import fj.F;
import fj.Function;
import fj.P2;
import fj.data.Option;
import fj.data.Stream;
import org.esupportail.opi.dao.utils.PaginatorFactory;
import org.esupportail.opi.domain.beans.parameters.Campagne;
import org.esupportail.opi.domain.beans.parameters.TypeDecision;
import org.esupportail.opi.domain.beans.references.commission.LinkTrtCmiCamp;
import org.esupportail.opi.domain.beans.references.commission.TraitementCmi;
import org.esupportail.opi.domain.beans.user.Individu;
import org.esupportail.opi.domain.beans.user.candidature.Avis;
import org.esupportail.opi.domain.beans.user.candidature.IndVoeu;
import org.esupportail.opi.utils.primefaces.PFFilters;

import java.util.Date;
import java.util.Set;

import static org.esupportail.opi.utils.fj.Conversions.parseBoolean;
import static fj.P.p;
import static fj.data.List.list;
import static fj.data.Option.*;

/**
 * TODO : Les filtres peuvent être optimisés (perfs) en évitant de recourir
 * TODO : aux sous-requêtes (JPQLSubQuery et any()).
 * TODO : Cf. {@link HibernateParamDaoServiceImpl#getCalendars(org.esupportail.opi.domain.beans.user.candidature.VersionEtpOpi)}
 */
public class IndividuDaoServiceImpl implements IndividuDaoService {

    final PaginatorFactory pf;

    final PathBuilder<Individu> ind;

    final BooleanExpression indEnService;

    final EntityPath<Campagne> campBase = new EntityPathBase<Campagne>(Campagne.class, "campagne");
    final PathBuilder<Campagne> camp = new PathBuilder<Campagne>(Campagne.class, campBase.getMetadata());
    final SetPath<Campagne, PathBuilder<Campagne>> indCamps;

    final EntityPath<IndVoeu> indVoeuBase = new EntityPathBase<IndVoeu>(IndVoeu.class, "voeu");
    final PathBuilder<IndVoeu> indVoeu = new PathBuilder<IndVoeu>(IndVoeu.class, indVoeuBase.getMetadata());
    final SetPath<IndVoeu, PathBuilder<IndVoeu>> indVoeux;

    final EntityPath<Avis> avisBase = new EntityPathBase<Avis>(Avis.class, "avis");
    final PathBuilder<Avis> avis = new PathBuilder<Avis>(Avis.class, avisBase.getMetadata());
    final SetPath<Avis, PathBuilder<Avis>> indVoeuAvis;

    private IndividuDaoServiceImpl(PaginatorFactory pf) {
        this.pf = pf;
        ind = pf.indPaginator().getTPathBuilder();
        indCamps = ind.getSet("campagnes", Campagne.class);
        indVoeux = ind.getSet("voeux", IndVoeu.class);
        indVoeuAvis = indVoeu.getSet("avis", Avis.class);
        indEnService = ind.get("temoinEnService").eq(true);
    }

    public static IndividuDaoService individuDaoSrv(PaginatorFactory pf) {
        return new IndividuDaoServiceImpl(pf);
    }

    // ######## Filtres sur les campagnes de l'individu

    final BooleanExpression campEnService = camp.get("temoinEnService").eq(true);

    final F<Set<Integer>, BooleanExpression> campRiFilter = new F<Set<Integer>, BooleanExpression>() {
        public BooleanExpression f(Set<Integer> listCodesRI) {
            return camp.get("codeRI").in(listCodesRI);
        }
    };

    final F<Set<Integer>, F<BooleanExpression, BooleanExpression>> temoinAndRiFilter =
            new F<Set<Integer>, F<BooleanExpression, BooleanExpression>>() {
                public F<BooleanExpression, BooleanExpression> f(final Set<Integer> listCodesRI) {
                    return new F<BooleanExpression, BooleanExpression>() {
                        public BooleanExpression f(BooleanExpression expr) {
                            BooleanExpression subExpr = listCodesRI.size() > 0 ?
                                    campEnService.and(campRiFilter.f(listCodesRI)) :
                                    camp.isNull();
                            return expr.and(subExpr);
                        }
                    };
                }
            };

    // ######## Filtres sur les voeux de l'individu

    final F<Boolean, F<BooleanExpression, BooleanExpression>> baseVoeuFilter =
            new F<Boolean, F<BooleanExpression, BooleanExpression>>() {
                public F<BooleanExpression, BooleanExpression> f(final Boolean bool) {
                    return new F<BooleanExpression, BooleanExpression>() {
                        public BooleanExpression f(BooleanExpression expr) {
                            return bool ?
                                    expr.and(indVoeu.get("individu").eq(ind.get("id"))) :
                                    expr.and(BooleanTemplate.create("1 = 1"));

                        }
                    };
                }
            };

    final F<Set<TraitementCmi>, F<BooleanExpression, BooleanExpression>> trtCmiFilter =
            new F<Set<TraitementCmi>, F<BooleanExpression, BooleanExpression>>() {
                public F<BooleanExpression, BooleanExpression> f(final Set<TraitementCmi> trtCmis) {
                    return new F<BooleanExpression, BooleanExpression>() {
                        public BooleanExpression f(BooleanExpression subExpr) {
                            final PathBuilder<TraitementCmi> trtCmi = indVoeu
                                            .get("linkTrtCmiCamp", LinkTrtCmiCamp.class)
                                            .get("traitementCmi", TraitementCmi.class);
                            return trtCmis.size() > 0 ? subExpr.and(trtCmi.in(trtCmis)) : subExpr.and(trtCmi.isNull());
                        }
                    };
                }
            };

    final F<TypeDecision, F<BooleanExpression, BooleanExpression>> typeDecFilter =
            new F<TypeDecision, F<BooleanExpression, BooleanExpression>>() {
                public F<BooleanExpression, BooleanExpression> f(final TypeDecision typeDecision) {
                    return new F<BooleanExpression, BooleanExpression>() {
                        public BooleanExpression f(BooleanExpression subExpr) {
                            PathBuilder<TypeDecision> typeDec = avis.get("result", TypeDecision.class);
                            BooleanExpression avisEnServ = avis.get("temoinEnService").eq(true);
                            return subExpr.and(avisEnServ.and(typeDec.eq(typeDecision)));
                        }
                    };
                }
            };

    final F<Boolean, F<BooleanExpression, BooleanExpression>> notTreatedWishFilter =
            new F<Boolean, F<BooleanExpression, BooleanExpression>>() {
                public F<BooleanExpression, BooleanExpression> f(final Boolean excludeTreated) {
                    return new F<BooleanExpression, BooleanExpression>() {
                        public BooleanExpression f(BooleanExpression expr) {
                            return expr.and(indVoeu.get("haveBeTraited").ne(excludeTreated));
                        }
                    };
                }
            };


    final F<String, F<BooleanExpression, BooleanExpression>> notCodeTypeTrtmtFilter =
            new F<String, F<BooleanExpression, BooleanExpression>>() {
                public F<BooleanExpression, BooleanExpression> f(final String code) {
                    return new F<BooleanExpression, BooleanExpression>() {
                        public BooleanExpression f(BooleanExpression expr) {
                            return expr.and(indVoeu.get("codTypeTrait").ne(code));
                        }
                    };
                }
            };

    final F<Boolean, F<BooleanExpression, BooleanExpression>> validWishFilter =
            new F<Boolean, F<BooleanExpression, BooleanExpression>>() {
                public F<BooleanExpression, BooleanExpression> f(final Boolean valid) {
                    return new F<BooleanExpression, BooleanExpression>() {
                        public BooleanExpression f(BooleanExpression expr) {
                            return expr.and(avis.get("validation", Boolean.class).eq(valid));
                        }
                    };
                }
            };

    final F<Date, F<BooleanExpression, BooleanExpression>> wishCreationFilter =
            new F<Date, F<BooleanExpression, BooleanExpression>>() {
                public F<BooleanExpression, BooleanExpression> f(final Date date) {
                    return new F<BooleanExpression, BooleanExpression>() {
                        public BooleanExpression f(BooleanExpression expr) {
                            return expr.and(indVoeu.getDate("dateCreaEnr", Date.class).eq(date));
                        }
                    };
                }
            };



    @SuppressWarnings("unchecked")
    @Override
    public P2<Long, Stream<Individu>> sliceOfInd(final PFFilters pfFilters,
                                                 final Option<TypeDecision> typesDec,
                                                 final Option<Boolean> treatedWish,
                                                 final Option<Boolean> validWish,
                                                 final Option<Date> wishCreation,
                                                 final Option<String> codeTypeTrtmt,
                                                 final Option<Set<TraitementCmi>> trtCmis,
                                                 final Set<Integer> listCodesRI) {

        final BooleanExpression andExpr = BooleanTemplate.create("1 = 1");

        final F<BooleanExpression, BooleanExpression> customCampFilter =
                p(indEnService).<BooleanExpression>constant().andThen(temoinAndRiFilter.f(listCodesRI));

        final F<BooleanExpression, BooleanExpression> customVoeuFilter =
                somes(list(
                        fromNull(pfFilters.filters.remove("useVoeuFilter"))
                                .map(parseBoolean.andThen(baseVoeuFilter)),
                        trtCmis.map(trtCmiFilter),
                        typesDec.map(typeDecFilter),
                        validWish.map(validWishFilter),
                        treatedWish.map(notTreatedWishFilter),
                        codeTypeTrtmt.map(notCodeTypeTrtmtFilter),
                        wishCreation.map(wishCreationFilter)
                )).foldLeft(Function.<BooleanExpression, BooleanExpression, BooleanExpression>andThen(),
                        Function.<BooleanExpression>identity());

        final F<HibernateQuery, HibernateQuery> customFilterQuery = new F<HibernateQuery, HibernateQuery>() {
            public HibernateQuery f(HibernateQuery query) {
                return query.distinct()
                        .leftJoin(indVoeux, indVoeu)
                        .innerJoin(indCamps, camp)
                        .leftJoin(indVoeuAvis, avis)
                        .where(customCampFilter.andThen(customVoeuFilter).f(andExpr));
            }
        };

        return pf.indPaginator().lazySliceOf(
                pfFilters.first,
                pfFilters.pageSize,
                pfFilters.sortField,
                pfFilters.sortOrder,
                pfFilters.filters,
                some(customFilterQuery));
    }

}
