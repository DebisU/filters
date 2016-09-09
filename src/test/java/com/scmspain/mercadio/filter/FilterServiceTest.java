package com.scmspain.mercadio.filter;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

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
        final List<FilterItem> filters = prepareWithAllFilters();
        final FilterService sut = FilterService.withFilters(filters);

        final String actual = sut.filter("");

        Assert.assertEquals("", actual.trim());
    }

    private List<FilterItem> prepareWithAllFilters() {
        final List<FilterItem> filters = new ArrayList<>();

        filters.add(new FilterItem(FilterType.COMMON_WORDS, ""));
        filters.add(new FilterItem(FilterType.URL, ""));
        filters.add(new FilterItem(FilterType.FORBIDDEN_WORDS, "tags"));
        filters.add(new FilterItem(FilterType.SEPARATORS, ""));
        filters.add(new FilterItem(FilterType.REMOVE_SPECIFIC_WORDS, "search"));
        filters.add(new FilterItem(FilterType.MULTILINE_SPAM, ""));
        return filters;
    }

    @Test (expected = FilterNotFoundException.class)
    @Ignore
    public void filterUseCaseExecuteWithThrowExceptionTest() throws Exception {
        final List<FilterItem> filters = prepareWithNonExistingFilter();
        final FilterService sut = FilterService.withFilters(filters);
        sut.filter("");
    }

    private List<FilterItem> prepareWithNonExistingFilter() {
        final List<FilterItem> filters = new ArrayList<>();
        //filters.put("BAD","FILTER");
        return filters;
    }
}
