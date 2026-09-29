package com.exemplo.notification.strategy;

import com.exemplo.notification.notification.Notification;

/** Strategy: cada canal implementa sua própria forma de envio (OCP). */
public interface NotificationStrategy {
    void send(Notification notification);
}
