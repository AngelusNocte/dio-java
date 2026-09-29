package com.exemplo.notification.builder;

import com.exemplo.notification.notification.Channel;
import com.exemplo.notification.notification.Notification;
import com.exemplo.notification.notification.User;

/** Builder: monta a Notification passo a passo, com API fluente. */
public class NotificationBuilder {

    private User recipient;
    private Channel channel;
    private String message;

    public NotificationBuilder to(User recipient) {
        this.recipient = recipient;
        return this;
    }

    public NotificationBuilder via(Channel channel) {
        this.channel = channel;
        return this;
    }

    public NotificationBuilder message(String message) {
        this.message = message;
        return this;
    }

    public Notification build() {
        return new Notification(recipient, channel, message);
    }
}
