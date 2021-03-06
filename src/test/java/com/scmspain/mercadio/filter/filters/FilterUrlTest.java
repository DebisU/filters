package com.scmspain.mercadio.filter.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class FilterUrlTest {
    private Filter sut;

    @Before
    public void setUp() throws Exception {
        sut = new FilterUrl(Optional.of(""));
    }

    @Test
    public void shouldDeleteAllUrlsWithoutArg() throws Exception {
        final String result = sut.filter(getTextWithUrls());

        Assert.assertEquals("google.es",result.trim());
    }

    @Test
    public void shouldDeleteAllUrlsPrefixes() throws Exception {
        sut = new FilterUrl(Optional.of("delete"));
        final String result = sut.filter(getTextWithUrls());

        Assert.assertEquals("google.es",result.trim());
    }

    @Test
    public void shouldReplaceAllUrlsPrefixes() throws Exception {
        sut = new FilterUrl(Optional.of("replace"));
        final String result = sut.filter(getTextWithUrls());

        Assert.assertEquals("google.es google.es google.es google.es google.es google.es",result.trim());
    }

    private static String getTextWithUrls() {
        return "www.google.es http://google.es https://google.es http://www.google.es https://www.google.es google.es";
    }

}