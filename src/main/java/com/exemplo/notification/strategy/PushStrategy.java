package com.exemplo.notification.strategy;

import com.exemplo.notification.notification.Notification;

public class PushStrategy implements NotificationStrategy {
    @Override
    public void send(Notification n) {
        System.out.println("  Enviando push para o dispositivo " + n.recipient().deviceToken() + ": " + n.message());
    }
}
