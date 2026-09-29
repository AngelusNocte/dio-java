package com.exemplo.notification.observer;

import com.exemplo.notification.notification.Notification;

public record NotificationEvent(EventType type, Notification notification, String detail) { }
