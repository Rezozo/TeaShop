package com.tea.paradise.enums;

public enum ProductSortType {
    CHEAP("По возрастанию цены"),
    EXPENSIVE("По убыванию цены"),
    POPULAR("По популярности"),
    MORE_RATE("По убыванию рейтинга");

    private final String value;

    ProductSortType(String value) {
        this.value = value;
    }
}
