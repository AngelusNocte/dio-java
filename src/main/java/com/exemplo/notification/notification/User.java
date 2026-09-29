package com.exemplo.notification.notification;

/** Destinatário da notificação, com os dados de contato de cada canal. */
public record User(String name, String email, String phone, String deviceToken) {

    public static boolean isBlank(String value) {
        return value == null || value.isBlank();
    }
}
