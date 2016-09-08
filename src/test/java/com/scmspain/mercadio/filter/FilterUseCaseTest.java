package com.scmspain.mercadio.filter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FilterUseCaseTest {

    private FilterUseCase sut;

    @Before
    public void setUp() throws Exception {
        sut = new FilterUseCase();
    }

    @Test
    public void filterUseCaseExecuteTest() throws Exception {
        final FilterRequest filterRequest = getFilterRequestWithAllFilters();
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(filterRequest);
        final FilterUseCaseResponse filterUseCaseResponse;

        filterUseCaseResponse = sut.execute(filterUseCaseRequest);

        Assert.assertEquals(filterRequest.getTextToFilter().trim(),filterUseCaseResponse.getResult().trim());
    }

    private FilterRequest getFilterRequestWithAllFilters() {
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String,String> filters = new HashMap<>();

        filters.put("commonwords","");
        filters.put("url","");
        filters.put("forbiddenwords","tags");
        filters.put("separators","");
        filters.put("removespecificwords","search");
        filters.put("multilinespam","");

        filterRequest.setFiltersToApply(filters);

        return filterRequest;
    }

    @Test (expected = FilterNotFoundException.class)
    public void filterUseCaseExecuteWithThrowExceptionTest() throws Exception {
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(getFilterRequestWithInvalidFilter());

        sut.execute(filterUseCaseRequest);
    }

    private FilterRequest getFilterRequestWithInvalidFilter() {
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String,String> filters = new HashMap<>();

        filters.put("BAD","FILTER");

        filterRequest.setFiltersToApply(filters);

        return filterRequest;
    }
}
