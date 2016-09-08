package com.scmspain.mercadio.filter;

public class FilterNotFoundException extends Exception {
    public FilterNotFoundException(String filterToApply) {
        super(String.format("Filter '%s' not found.", filterToApply));
    }
}
