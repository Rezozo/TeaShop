package com.tea.paradise.enums;

public enum OrderSortType {
    NEW("Сначала новые"),
    OLD("Сначала старые"),
    CHEAP("Дешевле"),
    EXPENSIVE("Дороже");

    private final String value;

    OrderSortType(String value) {
        this.value = value;
    }
}
