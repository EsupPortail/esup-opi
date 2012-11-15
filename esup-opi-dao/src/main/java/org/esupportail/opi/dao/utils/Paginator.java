package org.esupportail.opi.dao.utils;
import static fj.data.Option.*;
import static fj.Function.curry;
import static fj.P.p;
import static fj.P2.tuple;
import static fj.Unit.unit;
import static fj.data.Either.left;
import static fj.data.Either.right;
import static fj.data.List.iterableList;
import static fj.data.Stream.iterableStream;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Map;

import javax.persistence.EntityManager;

import org.hibernate.Session;
import org.primefaces.model.SortOrder;

import com.mysema.query.jpa.JPQLQuery;
import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.jpa.impl.JPAQuery;
import com.mysema.query.support.Expressions;
import com.mysema.query.types.EntityPath;
import com.mysema.query.types.Ops;
import com.mysema.query.types.Order;
import com.mysema.query.types.OrderSpecifier;
import com.mysema.query.types.PredicateOperation;
import com.mysema.query.types.path.EntityPathBase;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.util.ReflectionUtils;

import fj.F;
import fj.F2;
import fj.F3;
import fj.Function;
import fj.P1;
import fj.P2;
import fj.P3;
import fj.data.Either;
import fj.data.List;
import fj.data.Option;
import fj.data.Stream;

/**
 * Une classe utilitaire pour effectuer des requêtes paginées sur une BDD.
 * 
 * <p>
 * TODO : Se débarrasser de la dépendance primefaces {@link SortOrder} ; y substituer un
 * <code>EsupOrder</code> ?
 * </p>
 * 
 * <p>
 * Elle s'appuie sur la librairie <a href="http://www.querydsl.com/">QueryDSL</a> pour la construction des requêtes.
 * Cette classe doit être instanciée <b>anonymement</b>, c'est-à dire de la manière suivante :
 * 
 * <p><code>new Paginator() {} //à noter la présence d'accolades</code></p>
 * 
 * et <b>non</b> :
 * 
 * <p><code>new Paginator() // Erreur : instanciation anonyme obligatoire !</code></p>
 * </p>
 * 
 * @param <Q> Le type (querydsl) de requête à construire ({@link JPAQuery} ou {@link HibernateQuery})
 * @param <T> Le type d'entités retournées par les requêtes
 */
@SuppressWarnings({ "unchecked", "rawtypes" })
public class Paginator<Q extends JPQLQuery, T> {

    private final Class<T> ttype;
    
    private final Either<P1<EntityManager>, P1<Session>> dataProvider;
    
    private final EntityPathBase<T> ent;
    
    private final PathBuilder<T> tPath;
	    
    /**
     * @param mgr un 1-tuple contenant l' {@link EntityManager} JPA si utilisation conjointe à JPA (donc pour {@link Q} = {@link JPAQuery})
     * @param mgr un 1-tuple contenant la {@link Session} Hibernate si utilisation conjointe à Hibernate (donc pour {@link Q} = {@link HibernateQuery}) 
     */
    public Paginator(P1<?> mgr) {
        // TODO : untiliser un ADT DataProvider = EntityManager | Session + pattern matching
        dataProvider = (Either<P1<EntityManager>, P1<Session>>)
                ((mgr instanceof EntityManager) ? left(mgr) : right(mgr));
        ttype = (Class<T>) getTType();
        ent = new EntityPathBase<T>(ttype, "ent");
        tPath = new PathBuilder<T>(ttype, ent.getMetadata());        
    }
    
    
    // ################ reflection utilities ##############
    
    /**
     * Méthode utilitaire pour retrouver la valeur du type {@link T} utilisée à l'exécution,
     * et ce en dépit de l'<a href="http://en.wikipedia.org/wiki/Type_erasure">effacement des types</a>
     * en vigueur sur la jvm. Le procédé qu'elle emploie nécessite que la classe {@link Paginator} soit
     * instanciée anonymement
     * (cf. <a href="http://www.jquantlib.org/index.php/Using_TypeTokens_to_retrieve_generic_parameters">ici</a>
     * et <a href="http://www.artima.com/weblogs/viewpost.jsp?thread=208860">là</a>).
     * 
     * @return le {@link Type} correspondant au paramètre {@link T} 
     */
    private Type getTType() {
        final Type supertype = Paginator.this.getClass().getGenericSuperclass(); 
        // WARNING : the below cast will only work if the class is
        //instantiated anonymously !!
        if (supertype instanceof Class)
            throw new RuntimeException("Paginator class must be instantiated anonymously !!");
        final ParameterizedType superPtype = (ParameterizedType) supertype;
        return superPtype.getActualTypeArguments()[1];
    }
    
    /**
     * Une {@link F}onction qui, étant donné le nom d'un attribut de la classe de
     * type {@link T}, retourne son type.
     */
	private final F<String, Class> getType = new F<String, Class>() {
	    public Class f(String fieldName) {
	        return fromNull(ReflectionUtils.getFieldOrNull(
	                ttype, fieldName)).option(
	                        new Object().getClass(),
	                        new F<Field, Class>() {
	                            public Class f(Field field) {
	                                return field.getClass();
	                            }});
	    }};
	
	// ########## Filtering ############
	
	/**
	 * Une {@link F}onction qui étant donnée :
	 * <p>une Map<String, String> (cad une liste de 2-tuples)
	 * associant au nom d'un attribut de la classe de type {@link T}, la valeur
	 * saisie dans le champ de filtrage correspondant</p>
	 * <p>retourne : </p>
	 * <p>une liste de 3-tuples ({@link P3}) associant
	 * <ul>
	 * <li> le nom d'un attribut de la classe de type {@link T}
	 * <li> la valeur saisie dans le champ de filtrage correspondant
	 * <li> le type de cet attribut
	 * </ul></p>
	 */
	private final F<Map<String, String>, List<P3<String, String, Class>>> typedFilters =
	    new F<Map<String, String>, List<P3<String, String, Class>>>() {
	        public List<P3<String, String, Class>> f(Map<String, String> filters) {
	            return iterableList(filters.keySet()).zip(
	                iterableList(filters.values())).map(
	                    new F<P2<String, String>, P3<String, String, Class>>() {
	                        public P3<String, String, Class> f(P2<String, String> kv) {
	                            return p(kv._1(), kv._2(), getType.f(kv._1()));}});
	        }};

	/**
	 * Une fonction d'arité 2 ({@link F2}) prenant en arguments :
	 * <ul>
	 * <li> une requête querydsl ({@link Q})
	 * <li> une liste de 3-tuples 
	 * </ul>
	 * et retournant la requête {@link Q} augmentée des
	 * clauses <code>where</code> correspondant aux filtres
	 * décrits par la liste de 3-tuples.
	 * 
	 */
	private final F2<Q, List<P3<String, String, Class>>, Q> filterFunc=
	    new F2<Q, List<P3<String, String, Class>>, Q>() {
            public Q f(Q q, List<P3<String, String, Class>> filters) {
                return filters.foldLeft(
                    new F2<Q, P3<String, String, Class>, Q>() {
                        public Q f(Q cq, P3<String, String, Class> fvt) {
                            return (Q) cq.where(
                                new PredicateOperation(
                                Ops.STARTS_WITH,
                                tPath.get(fvt._1(), fvt._3()),
                                Expressions.template(String.class, "str({0})",
                                    Expressions.constant(fvt._2()))));
                            }}, q);
            }};
    
    /**
     * La composition inversée (cf. {@link F#andThen(F)}) de {@link Paginator#typedFilters}
     * et {@link Paginator#filterFunc}
     */
	private final F<Map<String,String>,F<Q,Q>> filter = typedFilters.andThen(filterFunc.flip().curry());
            
	// ########## Sorting ############	        
	
    /**
     * Convertit un {@link SortOrder} en {@link Order}
     * <br />
     * TODO : remplacer {@link SortOrder} par un <code>EsupOrder</code>
     */
    private final F<SortOrder, Order> sorder2Order = new F<SortOrder, Order>() {
        public Order f(SortOrder so) {
            return (so.equals(SortOrder.DESCENDING)) ?
            Order.DESC: Order.ASC; }
	};
	
	/**
	 * Application de la clause <code>orderBy</code> à la requête
     * <br />
     * TODO : remplacer {@link SortOrder} par un <code>EsupOrder</code>
	 */
	private final F3<Q, String, SortOrder, Q> orderBy =
	    new F3<Q, String, SortOrder, Q>() {
            public Q f(Q q, String sortField, SortOrder sortOrder) {
                return (Q) q.orderBy(new OrderSpecifier(
                    sorder2Order.f(sortOrder),
                    tPath.get(sortField, getType.f(sortField))));
            }};
	
    // ################ querying #####################
    
    /**
     * Requête de base pour la pagination (numéro de page + nombre d'éléments)
     */
    private final F2<Long, Long, Q> slice = new F2<Long, Long, Q>() {
        public Q f(Long offset, Long limit) {
            return (Q) from(ent).offset(offset).limit(limit);
        }};
    
    /**
     * Requête de base (simple select from)
     */
    private final P1<Q> full = new P1<Q>() { public Q _1() { return from(ent); }};
    
    /**
     * La requête complète : base + filtres + filtres supplémentaires + ordre
     * 
     * @param base
     * @param filters
     * @param customFilter
     * @return Une fonction retournant la requête complète
     */
	private <A> F<A, F<String, F<SortOrder, Q>>> query(F<A, Q> base, Map<String,String> filters,
	    F<Q, Q> customFilter) {
	    return base.andThen(filter.f(filters)).andThen(customFilter).andThen(curry(orderBy));
	}
	
	private Q from(final EntityPath<T>... o) {
	    return dataProvider.either(
	        new F<EntityManager, Q>() {
	            public Q f(EntityManager entMgr) {
	                return (Q) new JPAQuery(entMgr).from(o);
	            }}.mapP1(),
	        new F<Session, Q>() {
                public Q f(Session session) {
                    return (Q) new HibernateQuery(session).from(o);
                }}.mapP1())._1();
	}

	// ############################ The public API ##################

	/**
	 * Requête de pagination
	 * 
	 * @param offset Où commence-t-on ?
	 * @param limit Combien d'éléments ?
	 * @param sortField Sur quel champ trie-t-on ?
	 * @param sortOrder Dans quel ordre ?
	 * @param filters Sur quels champs filtrer ? Avec quelles valeurs ?
	 * @param optCustomfilter Un ou des filtres supplémentaires optionnels
	 * @return Un 2-tuple constitué du nombre d'éléments correspondant à la requête filtrée mais <b>non</b> paginée
	 * et de la liste des éléments retournés par la requête complète (paginée, filtrée et ordonnée)
	 */
	public P2<Long, java.util.List<T>> sliceOf(Long offset, Long limit, String sortField,
	    SortOrder sortOrder, Map<String,String> filters, Option<F<Q, Q>> optCustomfilter) {
	    final F<Q, Q> customFilter = optCustomfilter.orSome(Function.<Q>identity());
	    return p(
	            query(full.constant(), filters, customFilter).f(unit()).f(sortField).f(sortOrder).count(),
	            query(tuple(slice), filters, customFilter).f(p(offset, limit)).f(sortField).f(sortOrder).list(ent));
	}

	/**
	 * Comme {@link Paginator#sliceOf(Long, Long, String, SortOrder, Map, Option)} mais retourne
	 * les éléments dans un {@link Stream} par commodité 
	 */
	public P2<Long, Stream<T>> lazySliceOf(Long offset, Long limit,
	    String sortField, SortOrder sortOrder, Map<String,String> filters,
	    Option<F<Q, Q>> optCustomfilter) {
	    P2<Long, java.util.List<T>> t = sliceOf(
	        offset, limit, sortField, sortOrder, filters, optCustomfilter);
	    return p(t._1(), iterableStream(t._2()));
	}

	/**
	 * Utilité pour la construction de filtres supplémentaires à fournir à {@link Paginator#sliceOf(Long, Long, String, SortOrder, Map, Option)}
	 * @return un {@link PathBuilder} associé à l'entité courante (de type {@link Q})
	 */
	public PathBuilder<T> getTPathBuilder() { return tPath; }
}
