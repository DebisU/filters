package com.scmspain.mercadio.filter.domain.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterUrlTest {
    public Filter sut;

    @Before
    public void setUp() throws Exception {
        sut = new FilterUrl();
    }

    @Test
    public void shouldDeleteAllUrls() throws Exception {
        final String result = sut.filter(getTextWithUrls());

        Assert.assertEquals("",result.trim());
    }

    private static String getTextWithUrls() {
        return "www.google.es http://google.es https://google.es http://www.google.es https://www.google.es google.es";
    }

}
