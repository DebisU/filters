package domain.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterMultilineKeywordSpammingTest {
    public Filter sut;

    @Before
    public void setUp() throws Exception {
        sut = new FilterMultilineKeywordSpamming();
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

    private static String getMultilineSpam() {
        return "Samsung \n Sony \n Acer \n iOS \n Toshiba \n Asus \n LG \n Sharp \n Panasonic \n Mitsubishi \n Dell \n Epson \n Itachi \n BenQ \n Cannon \n Casio";
    }

    private static String getValidMultilineText() {
        return "Line 1 \n this is not spam \n this is a valid text";
    }

    private static String getTextWithOneSpamLine() {
        return "this text \n have an invalid \n line";
    }
}
