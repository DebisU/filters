package domain.filters;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

import static org.mockito.Mockito.when;


@RunWith(Parameterized.class)
public class FilterTest {
    public Filter sut;

    public FilterTest(Filter filter) {
        sut = filter;
    }

    @Test
    public void testNotNullFilterExecute() throws Exception {
        final String result = sut.filter("request");
        Assert.assertNotNull(result);
    }

    @Test
    public void mockFilterTest() throws Exception {
        sut = Mockito.mock(Filter.class);

        when(sut.filter("")).thenReturn("OK");

        Assert.assertEquals(sut.filter(""),"OK");
    }

    @Parameterized.Parameters
    public static Collection<Object[]> instancesToTest() {
        return Arrays.asList(
                new Object[] {new FilterKeywordSpammingByCommonWords()},
                new Object[] {new FilterKeywordSpammingBySeparators(Optional.of("."))},
                new Object[] {new FilterKeywordSpammingWithForbiddenWords(Optional.of("tags"))},
                new Object[] {new FilterMultilineKeywordSpamming()},
                new Object[] {new FilterRemoveSpecificWords(Optional.of("search"))},
                new Object[] {new FilterKeywordSpammingAtTheEnd()},
                new Object[] {new FilterUrl()}
                );
    }
}
