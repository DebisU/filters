package com.scmspain.mercadio.filter;


import com.scmspain.mercadio.filter.filters.ChainFilter;
import com.scmspain.mercadio.filter.filters.Filter;
import com.scmspain.mercadio.filter.filters.FilterKeywordMultilineSpamming;
import com.scmspain.mercadio.filter.filters.FilterKeywordSpammingAtTheEnd;
import com.scmspain.mercadio.filter.filters.FilterKeywordSpammingByCommonWords;
import com.scmspain.mercadio.filter.filters.FilterKeywordSpammingBySeparators;
import com.scmspain.mercadio.filter.filters.FilterKeywordSpammingWithForbiddenWords;
import com.scmspain.mercadio.filter.filters.FilterRemoveSpecificWords;
import com.scmspain.mercadio.filter.filters.FilterUrl;
import com.scmspain.mercadio.filter.utils.CommonStringOperations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilterService {

    private final Filter filter;

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
            switch (filterType) {
                case SEPARATORS:
                    chainFilter.addFilter(new FilterKeywordSpammingBySeparators(extraArg));
                    break;
                case FORBIDDEN_WORDS:
                    chainFilter.addFilter(new FilterKeywordSpammingWithForbiddenWords(extraArg));
                    break;
                case URL:
                    chainFilter.addFilter(new FilterUrl(extraArg));
                    break;
                case COMMON_WORDS:
                    chainFilter.addFilter(new FilterKeywordSpammingByCommonWords());
                    break;
                case REMOVE_SPECIFIC_WORDS:
                    chainFilter.addFilter(new FilterRemoveSpecificWords(extraArg));
                    break;
                case MULTILINE_SPAM:
                    chainFilter.addFilter(new FilterKeywordMultilineSpamming());
                    break;
                case END_SPAM:
                    chainFilter.addFilter(new FilterKeywordSpammingAtTheEnd());
                    break;
                default:
                    throw new FilterNotFoundException(filterType.toString());
            }
        }

        return chainFilter;
    }

    public String filter(String text) {
        final String textToFilter = CommonStringOperations.htmlToText(text);

        return filter.filter(textToFilter);
    }
}





