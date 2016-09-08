package com.scmspain.mercadio.filter;

import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.when;

public class FilterUseCaseResponseTest {

    @Test
    public void getFilterRequestTestWithMock() throws Exception {
        final FilterUseCaseResponse sut = Mockito.mock(FilterUseCaseResponse.class);

        when(sut.getResult()).thenReturn("result");

        Assert.assertEquals(sut.getResult(),"result");
    }

    @Test
    public void getFilterRequestTest() throws Exception {
        final FilterRequest filterRequest = new FilterRequest();
        final FilterUseCaseResponse sut = new FilterUseCaseResponse("");

        Assert.assertEquals(sut.getResult(),filterRequest.getTextToFilter());
    }
}
