package com.exemplo.notification.observer;

/** Observer: reage a eventos publicados pelo serviço. */
public interface NotificationListener {
    void onEvent(NotificationEvent event);
}
