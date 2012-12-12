package org.esupportail.opi.web.utils.paginator;

import fj.F2;
import fj.F5;
import fj.P2;
import fj.data.Stream;
import org.primefaces.model.SortOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        final P2<Long, Stream<T>> resTuple = getData.f(first, pageSize, sortField, sortOrder, filters);
        // le nombre total d'enregistrements
        setRowCount(resTuple._1().intValue());
        // on garde les résultats
        data = resTuple._2();
        // retour en collection jaja
        return new ArrayList<T>() {{
            addAll(data.toCollection());
        }};
    }

    public List<T> getData() {
        return new ArrayList<T>(data.toCollection());
    }
}
