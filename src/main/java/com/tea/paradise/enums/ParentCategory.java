package com.tea.paradise.enums;

public enum ParentCategory {
    NEW("Новинки"),
    TEA("Чайная продукция"),
    TEA_DISHES("Посуда для чая"),
    POPULAR("Популярные");

    private final String value;

    ParentCategory(String value) {
        this.value = value;
    }
}
