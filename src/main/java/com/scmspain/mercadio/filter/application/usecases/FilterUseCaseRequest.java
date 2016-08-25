package com.scmspain.mercadio.filter.application.usecases;

import com.scmspain.mercadio.filter.domain.FilterRequest;

public class FilterUseCaseRequest {
    private FilterRequest filterRequest;

    public FilterUseCaseRequest(FilterRequest filterRequest) {
        this.filterRequest = filterRequest;
    }

    public FilterRequest getFilterRequest() {
        return filterRequest;
    }

    public void setFilterRequest(FilterRequest filterRequest) {
        this.filterRequest = filterRequest;
    }
}
