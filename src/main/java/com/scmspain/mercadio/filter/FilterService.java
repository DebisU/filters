package com.scmspain.mercadio.filter;


import com.scmspain.mercadio.filter.filters.ChainFilter;
import com.scmspain.mercadio.filter.filters.Filter;
import com.scmspain.mercadio.filter.utils.CommonStringOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilterService {

    private final Filter filter;
    private final FilterFactory filterFactory = new FilterFactory();

    private FilterService() throws FilterNotFoundException {
        this(new ArrayList<>());
    }

    private FilterService(List<FilterItem> filters) throws FilterNotFoundException {
        this.filter = configureFilter(filters);
    }

    public static FilterService harmless() throws FilterNotFoundException {
        return new FilterService();
    }

    public static FilterService withFilters(List<FilterItem> filtersToApply)
            throws FilterNotFoundException {
        return new FilterService(filtersToApply);
    }

    private Filter configureFilter(List<FilterItem> filtersToApply) throws FilterNotFoundException {
        final ChainFilter chainFilter = new ChainFilter();

        for (FilterItem entry : filtersToApply) {
            final Optional<String> extraArg = Optional.of(entry.getValue());
            final FilterType filterType = entry.getKey();
            final Filter filter = filterFactory.createFilter(filterType, extraArg);
            chainFilter.addFilter(filter);
        }

        return chainFilter;
    }

    public String filter(String text) {
        final String textToFilter = CommonStringOperations.htmlToText(text);

        return filter.filter(textToFilter);
    }
}





