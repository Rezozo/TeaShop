package com.tea.paradise.enums;

public enum OrderTrackingStatus {
    NEW("Новый"),
    CONFIRMED("Подтвержден"),
    IN_THE_WAY("В пути"),
    COMPLETED("Доставлен"),
    CANCELED("Отменен"),
    RETURN("Возврат");

    private final String value;

    OrderTrackingStatus(String value) {
        this.value = value;
    }
}
