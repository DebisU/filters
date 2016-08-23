package com.scmspain.mercadio.filter.application.yaml;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.scmspain.mercadio.filter.domain.ExampleAd;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

public class examplesTest {
    private ObjectMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new ObjectMapper(new YAMLFactory());
    }

    @Test
    public void runAllExamplesTest() throws Exception {
        try {
            final List<ExampleAd> exampleAdList = Arrays.asList(
                    mapper.readValue(getClass().getResource("/examples.yml"), ExampleAd[].class)
            );
            System.out.println(ReflectionToStringBuilder.toString(exampleAdList, ToStringStyle.MULTI_LINE_STYLE));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


}
