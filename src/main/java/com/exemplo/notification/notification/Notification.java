package com.exemplo.notification.notification;

import java.time.Instant;

/** Notificação imutável. Deve ser criada via {@code NotificationBuilder}. */
public class Notification {

    private final User recipient;
    private final Channel channel;
    private final String message;
    private final Instant createdAt;

    public Notification(User recipient, Channel channel, String message) {
        this.recipient = recipient;
        this.channel = channel;
        this.message = message;
        this.createdAt = Instant.now();
    }

    public User recipient() { return recipient; }
    public Channel channel() { return channel; }
    public String message() { return message; }
    public Instant createdAt() { return createdAt; }
}
