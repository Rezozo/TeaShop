package com.tea.paradise.enums;

public enum ProductSortType {
    CHEAP("По возрастанию цены"),
    EXPENSIVE("По убыванию цены"),
    POPULAR("По популярности"),
    MORE_RATE("По убыванию рейтинга"),
    MORE_SALES("По убыванию кол-ва продаж"),
    LESS_SALES("По возрастанию кол-ва продаж"),
    MORE_QUANTITY("По убыванию кол-ва товара"),
    LESS_QUANTITY("По возрастанию кол-ва товара");

    private final String value;

    ProductSortType(String value) {
        this.value = value;
    }
}
