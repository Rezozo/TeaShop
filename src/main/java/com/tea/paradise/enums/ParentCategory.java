package com.tea.paradise.enums;

public enum ParentCategory {
    TEA("Чайная продукция"),
    TEA_DISHES("Посуда для чая");

    private final String value;

    ParentCategory(String value) {
        this.value = value;
    }
}
