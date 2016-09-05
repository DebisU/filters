package com.scmspain.mercadio.filter.domain.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterKeywordSpammingAtTheEndTest {
    private Filter sut;

    @Before
    public void setUp() throws Exception {
        sut = new FilterKeywordSpammingAtTheEnd();
    }

    @Test
    public void shouldNotDeleteAnything() throws Exception {
        final String input = getTextWithoutSpamAtTheEnd();
        final String expected = getTextWithoutSpamAtTheEnd();

        testExample(input, expected);
    }

    @Test
    public void shouldDeleteAll() throws Exception {
        final String input = getTextWithSpamAtTheEnd();
        final String expected = "este anuncio tiene spam a partir de aquí.";

        testExample(input, expected);
    }

    private void testExample(String input, String expected) {
        final String result = sut.filter(input);

        Assert.assertEquals(expected,result);
    }

    private String getTextWithSpamAtTheEnd() {
        return "este anuncio tiene spam a partir de aquí. similar a mercedes, bmw, citroen, seat, "
                + "peugeot, toyota, audi, volkswagen, samsung, tab, iphone, ipad, galaxy, apple, honda, yamaha.";
    }

    private String getTextWithoutSpamAtTheEnd() {
        return "este filtro, solo borra, una serie de palabras, separadas por coma al final del texto";
    }
}
