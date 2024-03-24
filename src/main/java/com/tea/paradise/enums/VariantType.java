package com.tea.paradise.enums;

public enum VariantType {
    FIFTY_GRAMS("50 грамм"),
    HUNDRED_GRAMS("100 грамм"),
    TWO_HUNDRED_GRAMS("200 грамм"),
    FIVE_HUNDRED_GRAMS("500 грамм"),
    PACK("Упаковка");

    private final String value;

    VariantType(String value) {
        this.value = value;
    }
}
