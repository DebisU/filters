package com.scmspain.mercadio.filter;

import com.scmspain.mercadio.filter.filters.Filter;
import com.scmspain.mercadio.filter.filters.FilterKeywordMultilineSpamming;
import com.scmspain.mercadio.filter.filters.FilterKeywordSpammingAtTheEnd;
import com.scmspain.mercadio.filter.filters.FilterKeywordSpammingByCommonWords;
import com.scmspain.mercadio.filter.filters.FilterKeywordSpammingBySeparators;
import com.scmspain.mercadio.filter.filters.FilterKeywordSpammingWithForbiddenWords;
import com.scmspain.mercadio.filter.filters.FilterRemoveSpecificWords;
import com.scmspain.mercadio.filter.filters.FilterUrl;

import java.util.Optional;

public class FilterFactory {

    public Filter createFilter(FilterType filterType, Optional<String> extraArg) throws FilterNotFoundException {
        Filter filter;
        switch (filterType) {
            case SEPARATORS:
                filter = new FilterKeywordSpammingBySeparators(extraArg);
                break;
            case FORBIDDEN_WORDS:
                filter = new FilterKeywordSpammingWithForbiddenWords(extraArg);
                break;
            case URL:
                filter = new FilterUrl(extraArg);
                break;
            case COMMON_WORDS:
                filter = new FilterKeywordSpammingByCommonWords();
                break;
            case REMOVE_SPECIFIC_WORDS:
                filter = new FilterRemoveSpecificWords(extraArg);
                break;
            case MULTILINE_SPAM:
                filter = new FilterKeywordMultilineSpamming();
                break;
            case END_SPAM:
                filter = new FilterKeywordSpammingAtTheEnd();
                break;
            default:
                throw new FilterNotFoundException(filterType.toString());
        }
        return filter;
    }

    public Filter createFilter(FilterType filterType) throws FilterNotFoundException {
        return createFilter(filterType, Optional.of(""));
    }
}