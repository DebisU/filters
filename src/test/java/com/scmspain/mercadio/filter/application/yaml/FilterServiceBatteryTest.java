package com.scmspain.mercadio.filter.application.yaml;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.scmspain.mercadio.filter.application.usecases.FilterNotFoundException;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCase;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCaseRequest;
import com.scmspain.mercadio.filter.application.usecases.FilterUseCaseResponse;
import com.scmspain.mercadio.filter.domain.ExampleAd;
import com.scmspain.mercadio.filter.domain.FilterRequest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(Parameterized.class)
public class FilterServiceBatteryTest {
    private static final int OBJECT_WIDTH = 3;
    private static final int LEVENSHTEIN_DISTANCE_THRESHOLD = 5;

    private final String text;
    private final String expected;
    private final String scenario;

    private final FilterUseCase sut;

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        final List<ExampleAd> examples = loadExamples();
        final Object[][] result = new Object[examples.size()][OBJECT_WIDTH];

        int index = 0;
        for (ExampleAd example : examples) {
            result[index][0] = example.getIn();
            result[index][1] = example.getOut();
            result[index][2] = example.getScenario();
            index++;
        }

        return Arrays.asList(result);
    }

    private static List<ExampleAd> loadExamples() {
        final ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
        final URL url = FilterServiceBatteryTest.class.getResource("/examples.yml");
        List<ExampleAd> result = new ArrayList<>();
        try {
            result = mapper.readValue(url,  new TypeReference<List<ExampleAd>>(){});
        } catch (Exception e) {
            Assert.fail("Could not open examples file");
        }

        return result;
    }

    public FilterServiceBatteryTest(final String text, final String expected, final String scenario) {
        this.text = text;
        this.expected = expected;
        this.scenario = scenario;
        this.sut = new FilterUseCase();
    }

    @Test
    public void testSort() throws FilterNotFoundException {
        final FilterRequest filterRequest = getFilterRequest(text);
        final FilterUseCaseRequest filterUseCaseRequest = new FilterUseCaseRequest(filterRequest);

        final FilterUseCaseResponse filterUseCaseResponse = sut.execute(filterUseCaseRequest);
        final String actual = filterUseCaseResponse.getResult();

        Assert.assertTrue(scenario, hasValidDistance(actual.replace(" ", ""), this.expected.replace(" ", "")));
        //Assert.assertEquals(scenario, this.expected.replace(" ",""), actual.replace(" ",""));
    }

    private boolean hasValidDistance(String current, String expected) {
        return getLevenshteinDistance(current, expected) < LEVENSHTEIN_DISTANCE_THRESHOLD;
    }

    private FilterRequest getFilterRequest(String text) {
        final FilterRequest filterRequest = new FilterRequest();
        final Map<String,String> filters = new HashMap<>();

        filters.put("forbiddenwords","tags,keywords");
        filters.put("removespecificwords","Palabras de búsqueda,search");
        filters.put("separators","");
        filters.put("url","");
        filters.put("commonwords","");
        filters.put("endspam","");
        //filters.put("multilinespam","");
        //filters.put("detectpattern","");


        filterRequest.setFiltersToApply(filters);
        filterRequest.setTextToFilter(text);
        return filterRequest;
    }

    private static int getLevenshteinDistance(String a, String b) {
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