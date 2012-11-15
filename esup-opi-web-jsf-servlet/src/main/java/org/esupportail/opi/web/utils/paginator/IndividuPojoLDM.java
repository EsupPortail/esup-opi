package org.esupportail.opi.web.utils.paginator;

import static fj.data.Option.fromNull;
import static fj.data.Option.fromString;
import static fj.data.Option.none;
import static fj.data.Stream.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import fj.data.Seq;
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
import org.esupportail.opi.web.beans.pojo.IndividuPojo;
import org.esupportail.opi.web.controllers.SessionController;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;

import fj.F;
import fj.P2;
import fj.data.Option;
import fj.data.Stream;

public final class IndividuPojoLDM extends LazyDataModel<IndividuPojo> {

    private static final long serialVersionUID = -6707564925716449956L;
    
    private final SessionController sessionCont;
    
    private final DomainService domain;
    
    private final ParameterService paramServ;
    
    private final DomainApoService domainApo;
    
    private final IndRechPojo indRechPojo;
    
    private final Option<TypeTraitement> typeTraitmt;
    
    private final F<Stream<Individu>, Stream<IndividuPojo>> indsToIndPojos;

    private Stream<IndividuPojo> indPojos = nil();
    
    private IndividuPojoLDM(
            SessionController sessionCont,
            IndRechPojo indRechPojo,
            Option<TypeTraitement> typeTraitmt,
            F<Stream<Individu>, Stream<IndividuPojo>> indsToIndPojos) {
        this.sessionCont = sessionCont;
        this.indRechPojo = indRechPojo;
        this.typeTraitmt = typeTraitmt;
        this.indsToIndPojos = indsToIndPojos;
        domain = sessionCont.getDomainService();
        paramServ = sessionCont.getParameterService();
        domainApo = sessionCont.getDomainApoService();
    }
    
    public static IndividuPojoLDM indPojoLdm(
            SessionController sessionCont,
            IndRechPojo indRechPojo,
            Option<TypeTraitement> typeTraitmt,
            F<Stream<Individu>, Stream<IndividuPojo>> indsToIndPojos) {
        return new IndividuPojoLDM(sessionCont, indRechPojo, typeTraitmt, indsToIndPojos);
    }
    
    @Override
    public IndividuPojo getRowData(final String rowKey) {
        return indPojos.find(new F<IndividuPojo, Boolean>() {
            public Boolean f(IndividuPojo ind) {
                return ind.getIndividu().getId().equals(rowKey);
            }}).orSome((IndividuPojo) null);
    }
    
    private F<Set<TraitementCmi>, Stream<TraitementCmi>> setToStream_() {
        return new F<Set<TraitementCmi>, Stream<TraitementCmi>>() {
            public Stream<TraitementCmi> f(Set<TraitementCmi> set) {
                return iterableStream(set);
            }
        };
    }
    
    @SuppressWarnings("serial")
    @Override
    public List<IndividuPojo> load(int first, int pageSize, String sortField,
            SortOrder sortOrder, Map<String, String> filters) {
        // le gestionnaire courant
        final User user = sessionCont.getCurrentUser();
        final Gestionnaire gest = !(user instanceof Gestionnaire) ?
                sessionCont.getManager() : (Gestionnaire) user;

        // les filtres :
        // 1. les numdossier, nom, prenom
        final Option<String> numDossier = fromString(indRechPojo.getNumDossierOpiRecherche());
        final Option<String> nomPatro = fromString(indRechPojo.getNomRecherche());
        final Option<String> prenom = fromString(indRechPojo.getPrenomRecherche());
        final Stream<Option<String>> stringFilters =
                Stream.<Option<String>>single(prenom).cons(nomPatro).cons(numDossier);

        // 2. le type de décision
        final Option<TypeDecision> typeDec = fromNull(indRechPojo.getTypeDecRecherchee());
        
        // 3. les étapes (TraitementCmi) de la commission
        final Integer idCmi = indRechPojo.getIdCmi();
        final Stream<Commission> cmis = (idCmi != null) ?
                single(paramServ.getCommission(idCmi, null)) :
                    iterableStream(domainApo.getListCommissionsByRight(gest, true));
                
        final Set<TraitementCmi> trtCmis = new HashSet<TraitementCmi>(
                cmis.bind(new F<Commission, Stream<TraitementCmi>>() {
                    public Stream<TraitementCmi> f(Commission com) {
                        return join(fromNull(com.getTraitementCmi()).toStream().map(setToStream_()));
                    }
                }).toCollection());
        
        // 4. les régimes d'inscription
        final Set<Integer> listCodesRI = new HashSet<Integer>(
        iterableStream(indRechPojo.getListeRI()).map(new F<RegimeInscription, Integer>() {
            public Integer f(RegimeInscription ri) {
                return ri.getCode();
            }}).toCollection());
        
        // 5. caractère 'traité' ou non du voeu
        // TODO : parameterize the condition on wich we apply this filter
        final Option<Boolean> wishTreated = none(); //fromNull(!indRechPojo.getExcludeWishProcessed());
        
        // 6. le type de traitement
        final Option<String> codeTypeTrtmt = typeTraitmt.map(new F<TypeTraitement, String>() {
            public String f(TypeTraitement t) {
                return t.getCode();
            }});
        
        // le 2-tuple de résultat
        final P2<Long, Stream<Individu>> resTuple =
                domain.sliceOfInd(new Long(first), new Long(pageSize), sortField, sortOrder, filters,
                        stringFilters, typeDec, wishTreated, codeTypeTrtmt, trtCmis, listCodesRI);
        // le nombre total d'enregistrements
        setRowCount(resTuple._1().intValue());
        // conversion individu -> individuPojo
        indPojos = indsToIndPojos.f(resTuple._2());
        // retour en collection jaja
        return new ArrayList<IndividuPojo>() {{ addAll(indPojos.toCollection()); }};
    }

    public List<IndividuPojo> getIndPojos() {
        return new ArrayList<IndividuPojo>(indPojos.toCollection());
    }

    public void setIndPojos(List<IndividuPojo> indPojos) {
        this.indPojos = iterableStream(indPojos);
    }
}
