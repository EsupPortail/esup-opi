package org.esupportail.opi.web.utils.paginator;

import fj.F;
import fj.F2;
import fj.F5;
import fj.P2;
import fj.data.Stream;
import org.primefaces.model.SortOrder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fj.Function.apply;
import static fj.data.Option.fromNull;
import static fj.data.Stream.nil;

public final class LazyDataModel<T> extends org.primefaces.model.LazyDataModel<T> {

    private final F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Stream<T>>> getData;

    private final F2<String, T, Boolean> findByRowKey;

    private Stream<T> data = nil();

    private LazyDataModel(
            F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Stream<T>>> getData,
            F2<String, T, Boolean> findByRowKey) {
        this.getData = getData;
        this.findByRowKey = findByRowKey;
    }

    public static <TT> LazyDataModel<TT> lazyModel(
            F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Stream<TT>>> getData,
            F2<String, TT, Boolean> findByRowKey) {
        return new LazyDataModel<TT>(getData, findByRowKey);
    }

    @Override
    public T getRowData(final String rowKey) {
        return data.find(findByRowKey.f(rowKey)).orSome((T) null);
    }

    @Override
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        // le 2-tuple de résultat
        final P2<Long, Stream<T>> resTuple =
                getData.f(fromNull(first).orSome(1),
                        fromNull(pageSize).orSome(10),
                        fromNull(sortField).orSome(""),
                        fromNull(sortOrder).orSome(SortOrder.ASCENDING),
                        fromNull(filters).orSome(new HashMap<String, String>()));
        // le nombre total d'enregistrements
        setRowCount(resTuple._1().intValue());
        // on garde les résultats
        data = resTuple._2();
        // retour en collection jaja
        return new ArrayList<T>(data.toCollection());
    }

    public List<T> getData() {
        return new ArrayList<T>(data.toCollection());
    }

    public <U> LazyDataModel<U> map(final F<T, U> f) {
        final LazyDataModel<T> self = this;
        return lazyModel(
                new F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Stream<U>>>() {
                    public P2<Long, Stream<U>> f(
                            Integer first, Integer pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
                        return self.getData.f(first, pageSize, sortField, sortOrder, filters).map2(f.mapStream());
                    }},
                new F2<String, U, Boolean>() {
                    public Boolean f(String rowKey, U u) {
                        return f.f(self.getRowData(rowKey)).equals(u);
                    }
                });
    }


}
