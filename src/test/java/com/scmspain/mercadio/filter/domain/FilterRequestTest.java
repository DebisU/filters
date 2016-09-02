package com.scmspain.mercadio.filter.domain;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class FilterRequestTest {
    private FilterRequest sut;
    private final Map<String, String> myMap = getEmptyMap();

    @Test
    public void filterRequestZeroArgumentConstructorTest() throws Exception {
        sut = new FilterRequest();

        sut.setTextToFilter("");
        sut.setFiltersToApply(myMap);

        Assert.assertEquals(sut.getTextToFilter(),"");
        Assert.assertEquals(sut.getFiltersToApply(),myMap);
    }

    @Test
    public void filterRequestTwoArgumentConstructorTest() throws Exception {
        sut = new FilterRequest("",myMap);

        Assert.assertEquals(sut.getTextToFilter(),"");
        Assert.assertEquals(sut.getFiltersToApply(),myMap);
    }

    @Test
    public void filterRequestThreeArgumentConstructorTest() throws Exception {
        sut = new FilterRequest("",myMap);

        Assert.assertEquals(sut.getTextToFilter(),"");
        Assert.assertEquals(sut.getFiltersToApply(),myMap);
    }

    private Map<String, String> getEmptyMap() {
        return new HashMap<>();
    }
}
