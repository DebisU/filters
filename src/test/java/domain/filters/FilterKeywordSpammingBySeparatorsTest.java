package domain.filters;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Optional;

public class FilterKeywordSpammingBySeparatorsTest {
    public Filter sut;

    @Before
    public void setUp() throws Exception {
        sut = new FilterKeywordSpammingBySeparators(Optional.of(","));
    }

    @Test
    public void shouldDeleteAllWithDefaultSeparator() throws Exception {
        final String result = sut.filter(getKeyWordSpammingWithComa());

        Assert.assertEquals("",result);
    }

    @Test
    public void shouldDeleteAllWithSpecificSeparator() throws Exception {
        sut = new FilterKeywordSpammingBySeparators(Optional.of("."));
        final String result = sut.filter(getKeyWordSpammingWithPoints());

        Assert.assertEquals("",result);
    }

    @Test
    public void shouldNotDeleteAnything() throws Exception {
        sut = new FilterKeywordSpammingBySeparators(Optional.of("."));
        final String result = sut.filter(getKeyWordSpammingWithComa());

        Assert.assertEquals(getKeyWordSpammingWithComa(),result);
    }

    private static String getKeyWordSpammingWithComa() {
        return "S5 , S6 , S6 EDGE DE 32 64 Y 128, S6 EDGE PLUS 32 ,64, NOTE 3 , NOTE 4 , GALAXY TAB , GALAXY J , TAB A," +
                " TAB S2 \\nIPHONE 6 , 6S DE 16 32 Y 64 , 6SPLUS DE 16 32GB 64GB Y 128GB , 6PLUS DE 16 64 Y 128 , 5S , " +
                "4S, IPHONE 5\\nLG G4 , LG G3 , LG G2 , LG G4C LG G4 STYLUS , LG ZERO \\nHUAWEI P8 LITE , HUAWEI P8 ," +
                " HUAWEI G8 , HUAWEI G7 , P7\\nSONY XPERIA Z2 , Z3 , Z3COMPACT , Z5 , Z5COMPACT , M4 AQUA , E3 ," +
                " Z \\nBLACKBERRY Q20 , Q0 , PASSPORT,";
    }


    private static String getKeyWordSpammingWithPoints() {
        return getKeyWordSpammingWithComa().replaceAll(",",".");
    }
}
