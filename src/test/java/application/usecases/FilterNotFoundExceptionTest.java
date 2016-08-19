package application.usecases;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class FilterNotFoundExceptionTest {
    Exception filterException;

    @Before
    public void setUp() throws Exception {
        filterException = new FilterNotFoundException("Filter not found.");
    }

    @Test (expected =  FilterNotFoundException.class)
    public void throwExceptionTest() throws Exception {
        throw filterException;
    }

    @Test
    public void getMessageTest() throws Exception {
        final String realMessage = filterException.getMessage();
        final String expectedMessage = "Filter 'Filter not found.' not found.";

        Assert.assertEquals(expectedMessage,realMessage);
    }
}
