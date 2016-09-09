package com.scmspain.mercadio.filter;

public class FilterItem {
    private FilterType key;
    private String value;

    public FilterItem(FilterType key, String value) {
        this.key = key;
        this.value = value;
    }

    public FilterType getKey() {
        return key;
    }

    public void setKey(FilterType key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
