package com.scmspain.mercadio.filter;

import com.scmspain.mercadio.filter.filters.*;
import com.scmspain.mercadio.filter.utils.CommonStringOperations;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

public class FilterService {

    private final Filter filter;

    public static FilterService harmless() throws FilterNotFoundException {
        return new FilterService();
    }

    public static FilterService withFilters(Map<FilterType, String> filtersToApply) throws FilterNotFoundException {
        return new FilterService(filtersToApply);
    }

    private FilterService() throws FilterNotFoundException {
        this(new LinkedHashMap<>());
    }

    private FilterService(Map<FilterType, String> filters) throws FilterNotFoundException {
        filter = configureFilter(filters);
    }

    private Filter configureFilter(Map<FilterType, String> filtersToApply) throws FilterNotFoundException {
        final ChainFilter filter = new ChainFilter();
        for (Map.Entry<FilterType, String> entry : filtersToApply.entrySet()) {
            final Optional<String> extraArg = Optional.of(entry.getValue());
            final FilterType filterType = entry.getKey();
            switch (filterType) {
                case SEPARATORS:
                    filter.addFilter(new FilterKeywordSpammingBySeparators(extraArg));
                    break;
                case FORBIDDEN_WORDS:
                    filter.addFilter(new FilterKeywordSpammingWithForbiddenWords(extraArg));
                    break;
                case URL:
                    filter.addFilter(new FilterUrl(extraArg));
                    break;
                case COMMON_WORDS:
                    filter.addFilter(new FilterKeywordSpammingByCommonWords());
                    break;
                case REMOVE_SPECIFIC_WORDS:
                    filter.addFilter(new FilterRemoveSpecificWords(extraArg));
                    break;
                case MULTILINE_SPAM:
                    filter.addFilter(new FilterKeywordMultilineSpamming());
                    break;
                case END_SPAM:
                    filter.addFilter(new FilterKeywordSpammingAtTheEnd());
                    break;
                default:
                    throw new FilterNotFoundException(filterType.toString());
            }
        }

        return filter;
    }

    public String filter(String text) {
        final String textToFilter = CommonStringOperations.htmlToText(text);

        return filter.filter(textToFilter);
    }
}





