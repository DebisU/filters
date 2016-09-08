package com.scmspain.mercadio.filter;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FilterRequestTest {
    private FilterRequest sut;

    @Test
    public void filterRequestZeroArgumentConstructorTest() throws Exception {
        sut = new FilterRequest("");

        Assert.assertEquals(sut.getTextToFilter(),"");
    }

    @Test
    public void filterRequestTwoArgumentConstructorTest() throws Exception {
        sut = new FilterRequest("");

        Assert.assertEquals(sut.getTextToFilter(),"");
    }

    @Test
    public void filterRequestThreeArgumentConstructorTest() throws Exception {
        sut = new FilterRequest("");

        Assert.assertEquals(sut.getTextToFilter(),"");
    }

    private Map<String, String> getEmptyMap() {
        return new HashMap<>();
    }
}
