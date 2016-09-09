package com.scmspain.mercadio.filter.filters;

import java.util.List;

public class ChainFilter implements Filter {
    private final List<Filter> filters;

    public ChainFilter(List<Filter> filters) {
        this.filters = filters;
    }

    @Override
    public String filter(String text) {
        String result = text;
        for (Filter filter : filters) {
            result = filter.filter(result);
        }

        return result;
    }
}
