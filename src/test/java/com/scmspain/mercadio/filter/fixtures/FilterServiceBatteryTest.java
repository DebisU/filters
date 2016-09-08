package com.scmspain.mercadio.filter.fixtures;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.scmspain.mercadio.filter.*;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.net.URL;
import java.util.*;

@RunWith(Parameterized.class)
public class FilterServiceBatteryTest {
    private static final int OBJECT_WIDTH = 3;
    private static final int LEVENSHTEIN_DISTANCE_THRESHOLD = 5;

    private final String text;
    private final String expected;
    private final String scenario;

    private final FilterService sut;

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

    public FilterServiceBatteryTest(final String text, final String expected, final String scenario) throws FilterNotFoundException {
        this.text = text;
        this.expected = expected;
        this.scenario = scenario;
        this.sut = FilterService.withFilters(prepareFilters());
    }

    @Test
    public void testFixture() throws FilterNotFoundException {
        final String actual = sut.filter(text);

        Assert.assertTrue(scenario, hasValidDistance(actual.replace(" ", ""), this.expected.replace(" ", "")));
//        Assert.assertEquals(scenario, this.expected.replace(" ", ""),actual.replace(" ", ""));
    }

    private boolean hasValidDistance(String current, String expected) {
        return getLevenshteinDistance(current, expected) < LEVENSHTEIN_DISTANCE_THRESHOLD;
    }

    private LinkedHashMap<FilterType, String> prepareFilters() {
        final LinkedHashMap<FilterType,String> filters = new LinkedHashMap<>();

        filters.put(FilterType.FORBIDDEN_WORDS,
                  "tags"
                + ",keywords"
                + ",palabra de busqueda"
                + ",palabras de busqueda"
                + ",palabras busquedas"
                + ",oferta ganga");
        filters.put(FilterType.REMOVE_SPECIFIC_WORDS, "Palabras de búsqueda,search");
        filters.put(FilterType.SEPARATORS, "");
        filters.put(FilterType.URL, "");
        filters.put(FilterType.COMMON_WORDS, "");
        filters.put(FilterType.END_SPAM, "");
        return filters;
    }

    private static int getLevenshteinDistance(String currentDistance, String expectedDistance) {
        currentDistance = currentDistance.toLowerCase();
        expectedDistance = expectedDistance.toLowerCase();

        final int [] costs = new int [expectedDistance.length() + 1];
        for (int j = 0; j < costs.length; j++) {
            costs[j] = j;
        }
        for (int i = 1; i <= currentDistance.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= expectedDistance.length(); j++) {
                final int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]),
                        currentDistance.charAt(i - 1) == expectedDistance.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[expectedDistance.length()];
    }
}