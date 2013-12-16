package org.esupportail.opi.web.utils.paginator;

import fj.F;
import fj.F2;
import fj.F5;
import fj.P2;
import fj.data.Array;
import org.primefaces.model.SortOrder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static fj.data.Option.fromNull;
import static java.util.Arrays.asList;

public final class LazyDataModel<T> extends org.primefaces.model.LazyDataModel<T> {

    private final F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Array<T>>> getData;

    private final F2<String, T, Boolean> findByRowKey;

    private Array<T> data = Array.empty();

    private LazyDataModel(
            F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Array<T>>> getData,
            F2<String, T, Boolean> findByRowKey) {
        this.getData = getData;
        this.findByRowKey = findByRowKey;
    }

    public static <TT> LazyDataModel<TT> lazyModel(
            F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Array<TT>>> getData,
            F2<String, TT, Boolean> findByRowKey) {
        return new LazyDataModel<>(getData, findByRowKey);
    }

    @Override
    public T getRowData(final String rowKey) {
        return data.find(findByRowKey.f(rowKey)).orSome((T) null);
    }

    @Override
    @SuppressWarnings("unchecked")
    public List<T> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        // le 2-tuple de résultat
        final P2<Long, Array<T>> resTuple =
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
        return asList((T[]) data.array());
    }

    @SuppressWarnings("unchecked")
    public List<T> getData() {
        return asList((T[]) data.array());
    }

    public <U> LazyDataModel<U> map(final F<T, U> f) {
        final LazyDataModel<T> self = this;
        return lazyModel(
                new F5<Integer, Integer, String, SortOrder, Map<String, String>, P2<Long, Array<U>>>() {
                    public P2<Long, Array<U>> f(
                            Integer first, Integer pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
                        return self.getData.f(first, pageSize, sortField, sortOrder, filters).map2(f.mapArray());
                    }},
                new F2<String, U, Boolean>() {
                    public Boolean f(String rowKey, U u) {
                        return f.f(self.getRowData(rowKey)).equals(u);
                    }
                });
    }
}
