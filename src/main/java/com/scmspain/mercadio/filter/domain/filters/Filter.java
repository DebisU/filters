package com.scmspain.mercadio.filter.domain.filters;

@FunctionalInterface
public interface Filter {
    String filter(String text);
}
