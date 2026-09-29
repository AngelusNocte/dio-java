package com.exemplo.notification.observer;

/** Registra cada evento no console. */
public class LogListener implements NotificationListener {
    @Override
    public void onEvent(NotificationEvent e) {
        String extra = e.detail() == null ? "" : " (" + e.detail() + ")";
        System.out.println("  Evento registrado: " + e.type() + extra);
    }
}
