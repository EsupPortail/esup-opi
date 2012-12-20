package org.esupportail.opi.utils.primefaces;

import org.primefaces.model.SortOrder;

import java.util.Map;

public class PFFilters {

    public final long first;

    public final long pageSize;

    public final String sortField;

    public final SortOrder sortOrder;

    public final Map<String, String> filters;

    private PFFilters(long first, long pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        this.first = first;
        this.pageSize = pageSize;
        this.sortField = sortField;
        this.sortOrder = sortOrder;
        this.filters = filters;
    }

    public static PFFilters pfFilters(
            long first, long pageSize, String sortField, SortOrder sortOrder, Map<String, String> filters) {
        return new PFFilters(first, pageSize, sortField, sortOrder, filters);
    }
}
