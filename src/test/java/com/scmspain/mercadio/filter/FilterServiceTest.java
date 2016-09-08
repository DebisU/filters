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

        final String actual = sut.filter("text remains unmodified");

        Assert.assertEquals("text remains unmodified", actual);
    }

    @Test
    public void filterUseCaseExecuteTest() throws Exception {
        final Map<String, String> filters = prepareWithAllFilters();
        final FilterService sut = FilterService.withFilters(filters);

        final String actual = sut.filter("");

        Assert.assertEquals("", actual.trim());
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

        final Map<String, String> filters = prepareWithNonExistingFilter();
        final FilterService sut = FilterService.withFilters(filters);
        sut.filter("");
    }

    private Map<String, String> prepareWithNonExistingFilter() {
        final Map<String,String> filters = new HashMap<>();

        filters.put("BAD","FILTER");
        return filters;
    }
}
