package com.scmspain.mercadio.filter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class FilterUseCaseRequestTest {
    @Test
    public void getFilterRequestTest() throws Exception {
        final FilterRequest filterRequest = new FilterRequest("");
        final FilterUseCaseRequest sut = Mockito.mock(FilterUseCaseRequest.class);

        when(sut.getFilterRequest()).thenReturn(filterRequest);

        Assert.assertEquals(sut.getFilterRequest(),filterRequest);
    }

    @Test
    public void setFilterRequestTest() throws Exception {
        final FilterRequest filterRequest = new FilterRequest("");
        final FilterUseCaseRequest sut = new FilterUseCaseRequest(filterRequest);

        sut.setFilterRequest(filterRequest);

        Assert.assertEquals(sut.getFilterRequest(),filterRequest);
    }
}
