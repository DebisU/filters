package com.scmspain.mercadio.filter;

import com.scmspain.mercadio.filter.filters.Filter;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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
        final List<Filter> filters = prepareWithAllFilters();
        final FilterService sut = FilterService.withFilters(filters);

        final String actual = sut.filter("");

        Assert.assertEquals("", actual.trim());
    }

    private List<Filter> prepareWithAllFilters() throws FilterNotFoundException {
        final FilterFactory factory = new FilterFactory();

        return Arrays.asList(
                factory.createFilter(FilterType.COMMON_WORDS),
                factory.createFilter(FilterType.URL),
                factory.createFilter(FilterType.FORBIDDEN_WORDS, Optional.of("tags")),
                factory.createFilter(FilterType.SEPARATORS),
                factory.createFilter(FilterType.REMOVE_SPECIFIC_WORDS, Optional.of("search")),
                factory.createFilter(FilterType.MULTILINE_SPAM)
        );
    }

    @Test (expected = FilterNotFoundException.class)
    @Ignore
    public void filterUseCaseExecuteWithThrowExceptionTest() throws Exception {
        final List<Filter> filters = prepareWithNonExistingFilter();
        final FilterService sut = FilterService.withFilters(filters);
        sut.filter("");
    }

    private List<Filter> prepareWithNonExistingFilter() {
        final List<Filter> filters = new ArrayList<>();
        //filters.put("BAD","FILTER");
        return filters;
    }
}
