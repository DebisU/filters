package com.scmspain.mercadio.filter;


import com.scmspain.mercadio.filter.filters.ChainFilter;
import com.scmspain.mercadio.filter.filters.Filter;
import com.scmspain.mercadio.filter.utils.CommonStringOperations;

import java.util.ArrayList;
import java.util.List;

public class FilterService {

    private final Filter filter;

    private FilterService() throws FilterNotFoundException {
        this(new ArrayList<>());
    }

    private FilterService(List<Filter> filters) throws FilterNotFoundException {
        this.filter = new ChainFilter(filters);
    }

    public static FilterService harmless() throws FilterNotFoundException {
        return new FilterService();
    }

    public static FilterService withFilters(List<Filter> filtersToApply) throws FilterNotFoundException {
        return new FilterService(filtersToApply);
    }

    public String filter(String text) {
        final String textToFilter = CommonStringOperations.htmlToText(text);

        return filter.filter(textToFilter);
    }
}





