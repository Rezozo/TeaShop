package com.tea.paradise.enums;

public enum UserRole {
    USER("Пользователь"),
    ADMIN("Администратор"),
    MANAGER("Менеджер");

    private final String value;

    UserRole(String value) {
        this.value = value;
    }
}
