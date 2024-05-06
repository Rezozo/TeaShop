package com.tea.paradise.enums;

public enum StatisticsSortType {
    ALL_TIME("Весь период"),
    YEAR("За год"),
    MONTH("За месяц"),
    WEEK("За неделю"),
    DAY("За сегодня");

    private final String value;

    StatisticsSortType(String value) {
        this.value = value;
    }
}
