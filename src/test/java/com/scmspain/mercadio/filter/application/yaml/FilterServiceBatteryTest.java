package com.scmspain.mercadio.filter.application.yaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCase;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCaseRequest;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCaseResponse;
import com.scmspain.mercadio.filter.domain.ExampleAd;
import com.scmspain.mercadio.filter.domain.FilterRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FilterServiceBatteryTest {
    public static final int OBJECT_WIDTH = 3;
    private FilterUseCase sut;

    @BeforeMethod
    public void setUp() throws Exception {
        sut = new FilterUseCase();
    }

    @Test(dataProvider = "examples")
    public void runAllFiltersTest(final String text, final String expected, final String scenario) throws Exception {
        final FilterRequest filterRequest = getFilterRequest();
        filterRequest.setTextToFilter(text);
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(filterRequest);

        final FilterUseCaseResponse filterUseCaseResponse = sut.execute(filterUseCaseRequest);

        final boolean validDistance = getDistanceWithLevenshteinDistanceAlgorithm(filterUseCaseResponse.getResult().replace(" ",""),expected.replace(" ","")) < 5 ? true : false;

        //Assert.assertTrue(validDistance,scenario);
        Assert.assertEquals(filterUseCaseResponse.getResult().replace(" ",""),expected.replace(" ",""));
    }

    @DataProvider(name = "examples")
    public Object[][] examples() {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        final Object[][] toReturn;
        
        try {
            List<ExampleAd> tempExampleAd = mapper.readValue(getClass().getResource("/examples.yml"),  new TypeReference<List<ExampleAd>>(){});
            final int objectHeight = tempExampleAd.size();
            toReturn = new Object[objectHeight][OBJECT_WIDTH];

            int index = 0;
            for (ExampleAd item : tempExampleAd) {
                toReturn[index][0]=item.getIn();
                toReturn[index][1]=item.getOut();
                toReturn[index][2]=item.getScenario();
                index++;
            }

            return toReturn;

        } catch (IOException e) {
            Assert.fail();
        }
        return new Object[0][0];
    }

    private FilterRequest getFilterRequest() {
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String,String> filters = new HashMap<>();

        filters.put("forbiddenwords","tags,keywords");
        filters.put("removespecificwords","Palabras de búsqueda,search");
        filters.put("separators","");
        filters.put("url","");
        filters.put("commonwords","");
        //filters.put("multilinespam","");
        //filters.put("detectpattern","");


        filterRequest.setFiltersToApply(filters);

        return filterRequest;
    }

    public static int getDistanceWithLevenshteinDistanceAlgorithm(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();

        final int [] costs = new int [b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                final int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }
}