package com.scmspain.mercadio.filter.domain.factories;

import org.junit.Assert;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class CommonStringOperationsTest {
    @Test
    public void splitParagraphTest() throws Exception {
        final String text = "arg \n arg2";
        final List<String> result = CommonStringOperations.splitParagraphs(text);

        Assert.assertEquals(Arrays.asList("arg ", " arg2"), result);
    }

    @Test
    public void textToHtmlTest() throws Exception {
        final String text = "plain text \n";
        final String result = CommonStringOperations.textToHtml(text);

        Assert.assertEquals("plain text <br /></p>",result);
    }

    @Test
    public void htmlToTextTest() throws Exception {
        final String text = "html text <br /></p>";
        final String result = CommonStringOperations.htmlToText(text);

        Assert.assertEquals("html text \n", result);
    }

    @Test
    public void initializeCommonStringOperations() throws Exception {
        CommonStringOperations sut = new CommonStringOperations();
        Assert.assertNotNull(sut);
    }
}
