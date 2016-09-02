package com.scmspain.mercadio.filter.application.usecases;

import com.scmspain.mercadio.filter.domain.ChainFilter;
import com.scmspain.mercadio.filter.domain.factories.CommonStringOperations;
import com.scmspain.mercadio.filter.domain.filters.*;

import java.util.Map;
import java.util.Optional;

public class FilterUseCase {
    public FilterUseCaseResponse execute(FilterUseCaseRequest filterUseCaseRequest) throws FilterNotFoundException {
        final Filter filter = configureFilter(filterUseCaseRequest.getFilterRequest().getFiltersToApply());
        final String textToFilter = CommonStringOperations.htmlToText(filterUseCaseRequest.getFilterRequest().getTextToFilter());

        final String result = filter.filter(textToFilter);

        return new FilterUseCaseResponse(result);
    }

    private Filter configureFilter(Map<String, String> filtersToApply) throws FilterNotFoundException {
        final ChainFilter filter = new ChainFilter();
        for (Map.Entry<String, String> entry : filtersToApply.entrySet()) {
            final Optional<String> extraArg = Optional.of(entry.getValue());
            final String filterToApply = entry.getKey().toLowerCase();
            switch (filterToApply) {
                case "separators":
                    filter.addFilter(new FilterKeywordSpammingBySeparators(extraArg));
                    break;
                case "forbiddenwords":
                    filter.addFilter(new FilterKeywordSpammingWithForbiddenWords(extraArg));
                    break;
                case "url":
                    filter.addFilter(new FilterUrl(extraArg));
                    break;
                case "commonwords":
                    filter.addFilter(new FilterKeywordSpammingByCommonWords());
                    break;
                case "removespecificwords":
                    filter.addFilter(new FilterRemoveSpecificWords(extraArg));
                    break;
                case "multilinespam":
                    filter.addFilter(new FilterKeywordMultilineSpamming());
                    break;
                case "endspam":
                    filter.addFilter(new FilterKeywordSpammingAtTheEnd());
                    break;
                case "detectpattern":
                    filter.addFilter(new FilterKeywordSpammingDetectPattern());
                    break;
                default:
                    throw new FilterNotFoundException(filterToApply);
            }
        }

        return filter;
    }
}





