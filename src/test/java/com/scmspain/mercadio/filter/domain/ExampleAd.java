package com.scmspain.mercadio.filter.domain;

public class ExampleAd {
    private final String in;
    private final String out;
    private final String scenario;

    public ExampleAd() {
        in = "";
        out = "";
        scenario = "";
    }

    public ExampleAd(String in, String out, String scenario) {
        this.in = in;
        this.out = out;
        this.scenario = scenario;
    }

    public String getIn() {
        return in;
    }

    public String getOut() {
        return out;
    }

    public Object getScenario() {
        return scenario;
    }
}