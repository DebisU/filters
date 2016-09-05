package com.scmspain.mercadio.filter.domain;

import java.util.HashMap;
import java.util.Map;

public class FilterRequest {
    private String textToFilter;
    private Map<String,String> filtersToApply;

    public FilterRequest() {
        this.textToFilter = "";
        this.filtersToApply = new HashMap<>();
    }

    @SuppressWarnings("SameParameterValue")
    public FilterRequest(String textToFilter, Map<String, String> filtersToApply) {
        this.textToFilter = textToFilter;
        this.filtersToApply = filtersToApply;
    }

    public String getTextToFilter() {
        return textToFilter;
    }

    public void setTextToFilter(String textToFilter) {
        this.textToFilter = textToFilter;
    }

    public Map<String, String> getFiltersToApply() {
        return filtersToApply;
    }

    public void setFiltersToApply(Map<String, String> filtersToApply) {
        this.filtersToApply = filtersToApply;
    }
}