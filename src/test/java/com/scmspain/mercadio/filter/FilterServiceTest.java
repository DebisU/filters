package com.scmspain.mercadio.filter;

import org.junit.Assert;
import org.junit.Ignore;
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
        final Map<FilterType, String> filters = prepareWithAllFilters();
        final FilterService sut = FilterService.withFilters(filters);

        final String actual = sut.filter("");

        Assert.assertEquals("", actual.trim());
    }

    private Map<FilterType, String> prepareWithAllFilters() {
        final Map<FilterType,String> filters = new HashMap<>();

        filters.put(FilterType.COMMON_WORDS, "");
        filters.put(FilterType.URL, "");
        filters.put(FilterType.FORBIDDEN_WORDS, "tags");
        filters.put(FilterType.SEPARATORS, "");
        filters.put(FilterType.REMOVE_SPECIFIC_WORDS, "search");
        filters.put(FilterType.MULTILINE_SPAM, "");
        return filters;
    }

    @Test (expected = FilterNotFoundException.class)
    @Ignore
    public void filterUseCaseExecuteWithThrowExceptionTest() throws Exception {
        final Map<FilterType, String> filters = prepareWithNonExistingFilter();
        final FilterService sut = FilterService.withFilters(filters);
        sut.filter("");
    }

    private Map<FilterType, String> prepareWithNonExistingFilter() {
        final Map<FilterType,String> filters = new HashMap<>();
        //filters.put("BAD","FILTER");
        return filters;
    }
}
