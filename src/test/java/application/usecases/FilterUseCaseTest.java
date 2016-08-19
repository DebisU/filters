package application.usecases;

import domain.FilterRequest;
import org.junit.Assert;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.when;

public class FilterUseCaseTest {


    @Test
    public void filterUseCaseExecuteTestWithMockito() throws Exception {
        final FilterUseCase sut = Mockito.mock(FilterUseCase.class);
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(new FilterRequest());
        final FilterUseCaseResponse filterUseCaseResponse = Mockito.mock(FilterUseCaseResponse.class);

        when(sut.execute(filterUseCaseRequest)).thenReturn(filterUseCaseResponse);

        Assert.assertEquals(sut.execute(filterUseCaseRequest), filterUseCaseResponse);
    }

    @Test
    public void filterUseCaseExecuteTest() throws Exception {
        final FilterUseCase sut = new FilterUseCase();
        final FilterRequest filterRequest = getFilterRequestWithAllFilters();
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(filterRequest);
        final FilterUseCaseResponse filterUseCaseResponse;

        filterUseCaseResponse = sut.execute(filterUseCaseRequest);

        Assert.assertEquals(filterRequest.getTextToFilter().trim(),filterUseCaseResponse.getResult().trim());

    }

    @Test (expected = FilterNotFoundException.class)
    public void filterUseCaseExecuteWithThrowExceptionTest() throws Exception {
        final FilterUseCase sut = new FilterUseCase();
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(getFilterRequestWithInvalidFilter());

        sut.execute(filterUseCaseRequest);
    }

    private FilterRequest getFilterRequestWithAllFilters() {
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String,String> filters = new HashMap<>();

        filters.put("commonwords","");
        filters.put("url","");
        filters.put("forbiddenwords","tags");
        filters.put("separators","");
        filters.put("removespecificwords","search");
        filters.put("multilinespam","");

        filterRequest.setFiltersToApply(filters);

        return filterRequest;
    }

    private FilterRequest getFilterRequestWithInvalidFilter() {
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String,String> filters = new HashMap<>();

        filters.put("BAD","FILTER");

        filterRequest.setFiltersToApply(filters);

        return filterRequest;
    }
}
