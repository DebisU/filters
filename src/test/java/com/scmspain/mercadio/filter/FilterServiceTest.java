package com.scmspain.mercadio.filter;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FilterServiceTest {

    @Test
    public void filterUseCaseExecuteTest() throws Exception {
        final FilterRequest filterRequest = getFilterRequestWithAllFilters();
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(filterRequest);
        final FilterUseCaseResponse filterUseCaseResponse;

        final Map<String, String> filters = prepareWithAllFilters();
        final FilterService sut = new FilterService(filters);
        filterUseCaseResponse = sut.filter(filterUseCaseRequest);

        Assert.assertEquals(filterRequest.getTextToFilter().trim(),filterUseCaseResponse.getResult().trim());
    }

    private FilterRequest getFilterRequestWithAllFilters() {
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String, String> filters = prepareWithAllFilters();

        filterRequest.setFiltersToApply(filters);

        return filterRequest;
    }

    private Map<String, String> prepareWithAllFilters() {
        final Map<String,String> filters = new HashMap<>();

        filters.put("commonwords","");
        filters.put("url","");
        filters.put("forbiddenwords","tags");
        filters.put("separators","");
        filters.put("removespecificwords","search");
        filters.put("multilinespam","");
        return filters;
    }

    @Test (expected = FilterNotFoundException.class)
    public void filterUseCaseExecuteWithThrowExceptionTest() throws Exception {
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(getFilterRequestWithInvalidFilter());

        final Map<String, String> filters = prepareWithNonExistingFilter();
        final FilterService sut = new FilterService(filters);
        sut.filter(filterUseCaseRequest);
    }

    private FilterRequest getFilterRequestWithInvalidFilter() {
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String, String> filters = prepareWithNonExistingFilter();

        filterRequest.setFiltersToApply(filters);

        return filterRequest;
    }

    private Map<String, String> prepareWithNonExistingFilter() {
        final Map<String,String> filters = new HashMap<>();

        filters.put("BAD","FILTER");
        return filters;
    }
}
