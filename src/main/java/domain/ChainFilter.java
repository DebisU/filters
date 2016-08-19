package domain;

import domain.filters.Filter;

import java.util.ArrayList;
import java.util.List;

public class ChainFilter implements Filter {
    private final List<Filter> filters = new ArrayList<>();

    public void addFilter(Filter filter){
        filters.add(filter);
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
