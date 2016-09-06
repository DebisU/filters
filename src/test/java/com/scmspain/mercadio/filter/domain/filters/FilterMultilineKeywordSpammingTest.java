package com.scmspain.mercadio.filter.domain.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterMultilineKeywordSpammingTest {
    private Filter sut;

    @Before
    public void setUp() throws Exception {
        sut = new FilterKeywordMultilineSpamming();
    }

    @Test
    public void shouldDeleteAll() throws Exception {
        final String result = sut.filter(getMultilineSpam());

        Assert.assertEquals("",result);
    }

    @Test
    public void shouldNotDelete() throws Exception {
        final String result = sut.filter(getValidMultilineText());

        Assert.assertEquals(getValidMultilineText(),result);
    }

    @Test
    public void shouldRemoveLastLine() throws Exception {
        final String result = sut.filter(getTextWithOneSpamLine());

        Assert.assertEquals( "this text \n have an invalid ",result);

    }

    @Test
    public void shouldBeEmpty() throws Exception {
        final String result = sut.filter("");

        Assert.assertEquals("",result);
    }

    @Test
    public void filterTestWithTwoWords() throws Exception {
        final String result = sut.filter("word1, word2");

        Assert.assertEquals("word1, word2",result);
    }

    @Test
    public void filterWithSuffixes() throws Exception {
        final String result = sut.filter("-word");

        Assert.assertEquals("-word", result);
    }

    @Test
    public void filterNewLine() throws Exception {
        final String result = sut.filter("\n");

        Assert.assertEquals("", result);
    }

    @Test
    public void filterWordAndNumbers() throws Exception {
        final String result = sut.filter("nVidia 750");

        Assert.assertEquals("nVidia 750",result);
    }

    private static String getMultilineSpam() {
        return "Samsung \n Sony \n Acer \n iOS \n Toshiba \n Asus \n LG \n Sharp \n Panasonic "
                + "\n Mitsubishi \n Dell \n Epson \n Itachi \n BenQ \n Cannon \n Casio";
    }

    private static String getValidMultilineText() {
        return "Line 1 \n this is not spam \n this is a valid text";
    }

    private static String getTextWithOneSpamLine() {
        return "this text \n have an invalid \n line";
    }
}
