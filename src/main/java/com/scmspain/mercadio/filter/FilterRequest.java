package com.scmspain.mercadio.filter;

public class FilterRequest {
    private String textToFilter;

    @SuppressWarnings("SameParameterValue")
    public FilterRequest(String textToFilter) {
        this.textToFilter = textToFilter;
    }

    public String getTextToFilter() {
        return textToFilter;
    }
}