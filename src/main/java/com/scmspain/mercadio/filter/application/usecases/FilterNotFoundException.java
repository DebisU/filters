package com.scmspain.mercadio.filter.application.usecases;

public class FilterNotFoundException extends Exception {
    public FilterNotFoundException(String filterToApply) {
        super(String.format("Filter '%s' not found.", filterToApply));
    }
}
