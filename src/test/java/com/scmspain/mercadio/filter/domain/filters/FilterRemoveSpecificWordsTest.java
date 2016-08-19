package com.scmspain.mercadio.filter.domain.filters;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class FilterRemoveSpecificWordsTest {
    public Filter sut;


    @Test
    public void shouldDeleteLastWord() throws Exception {
        sut = new FilterRemoveSpecificWords(Optional.of("filter"));
        final String result = sut.filter(getExampleText());

        Assert.assertEquals("this is an example text to test the remove specific words",result);
    }

    @Test
    public void shouldDeleteLastTwoWords() throws Exception {
        sut = new FilterRemoveSpecificWords(Optional.of("words,filter"));
        final String result = sut.filter(getExampleText());

        Assert.assertEquals("this is an example text to test the remove specific",result);
    }

    @Test
    public void shouldNotDeleteAnything() throws Exception {
        sut = new FilterRemoveSpecificWords(null);
        final String result = sut.filter(getExampleText());

        Assert.assertEquals(getExampleText(),result);
    }

    private static String getExampleText() {
        return "this is an example text to test the remove specific words filter";
    }
}
