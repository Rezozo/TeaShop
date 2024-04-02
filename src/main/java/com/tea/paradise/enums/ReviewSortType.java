package com.tea.paradise.enums;

public enum ReviewSortType {
    NEW("Сначала новые"),
    OLD("Сначала старые"),
    POSITIVE("Сначала положительные"),
    NEGATIVE("Сначала отрицательные");

    private final String value;

    ReviewSortType(String value) {
        this.value = value;
    }
}
