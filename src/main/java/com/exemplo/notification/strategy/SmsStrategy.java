package com.exemplo.notification.strategy;

import com.exemplo.notification.notification.Notification;

public class SmsStrategy implements NotificationStrategy {
    @Override
    public void send(Notification n) {
        System.out.println("  Enviando SMS para " + n.recipient().phone() + ": " + n.message());
    }
}
