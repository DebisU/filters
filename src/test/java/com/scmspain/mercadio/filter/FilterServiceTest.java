package com.scmspain.mercadio.filter;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FilterServiceTest {

    @Test
    public void filterServiceWithoutConfiguredFilters() throws Exception {
        final FilterService sut = FilterService.harmless();

        Assert.assertNotNull(sut);
    }

    @Test
    public void harmlessFilterServiceDoesNothing() throws Exception {
        final FilterService sut = FilterService.harmless();

        FilterRequest requestRequest = new FilterRequest("text remains unmodified");
        FilterUseCaseRequest request = new FilterUseCaseRequest(requestRequest);
        final FilterUseCaseResponse actual = sut.filter(request);

        Assert.assertEquals("text remains unmodified", actual.getResult());
    }

    @Test
    public void filterUseCaseExecuteTest() throws Exception {
        final FilterRequest filterRequest = new FilterRequest("");
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(filterRequest);
        final FilterUseCaseResponse filterUseCaseResponse;

        final Map<String, String> filters = prepareWithAllFilters();
        final FilterService sut = FilterService.withFilters(filters);
        filterUseCaseResponse = sut.filter(filterUseCaseRequest);

        Assert.assertEquals(filterRequest.getTextToFilter().trim(),filterUseCaseResponse.getResult().trim());
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

        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(new FilterRequest(""));

        final Map<String, String> filters = prepareWithNonExistingFilter();
        final FilterService sut = FilterService.withFilters(filters);
        sut.filter(filterUseCaseRequest);
    }

    private Map<String, String> prepareWithNonExistingFilter() {
        final Map<String,String> filters = new HashMap<>();

        filters.put("BAD","FILTER");
        return filters;
    }
}
