package com.scmspain.mercadio.filter.filters;

@FunctionalInterface
public interface Filter {
    String filter(String text);
}
