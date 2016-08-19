package domain.filters;

import org.junit.Assert;
import org.junit.Test;

import java.util.Optional;

public class FilterKeywordSpammingWithForbiddenWordsTest {
    public Filter sut;

    @Test
    public void shouldDeleteTheParagraphWithTheForbiddenWord() throws Exception {
        sut = new FilterKeywordSpammingWithForbiddenWords(Optional.of("Tags"));
        final String result = sut.filter(getTextWithForbiddenWords());

        Assert.assertEquals("Palabras de búsqueda ",result);
    }

    @Test
    public void shouldDeleteBothParagraphs() throws Exception {
        sut = new FilterKeywordSpammingWithForbiddenWords(Optional.of("Tags,Palabras de búsqueda"));
        final String result = sut.filter(getTextWithForbiddenWords());

        Assert.assertEquals("",result);
    }

    @Test
    public void shouldNotDeleteAnything() throws Exception {
        sut = new FilterKeywordSpammingWithForbiddenWords(null);
        final String result = sut.filter(getTextWithForbiddenWords());

        Assert.assertEquals("Palabras de búsqueda \n Tags",result);
    }

    private static String getTextWithForbiddenWords() {
        return "Palabras de búsqueda \n Tags";

    }


}
