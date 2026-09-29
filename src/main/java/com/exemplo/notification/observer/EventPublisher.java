package com.exemplo.notification.observer;

import java.util.ArrayList;
import java.util.List;

/** Subject do padrão Observer. */
public class EventPublisher {

    private final List<NotificationListener> listeners = new ArrayList<>();

    public void subscribe(NotificationListener listener) {
        listeners.add(listener);
    }

    public void publish(NotificationEvent event) {
        listeners.forEach(l -> l.onEvent(event));
    }
}
