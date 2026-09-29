package com.exemplo.notification.strategy;

import com.exemplo.notification.notification.Notification;

public class EmailStrategy implements NotificationStrategy {
    @Override
    public void send(Notification n) {
        System.out.println("  Enviando e-mail para " + n.recipient().email() + ": " + n.message());
    }
}
