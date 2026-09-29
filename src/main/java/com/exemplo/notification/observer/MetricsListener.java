package com.exemplo.notification.observer;

/** Contabiliza envios com sucesso e falhas. */
public class MetricsListener implements NotificationListener {

    private int sent;
    private int failed;

    @Override
    public void onEvent(NotificationEvent e) {
        if (e.type() == EventType.NOTIFICATION_SENT) sent++;
        else failed++;
    }

    public int getSent() { return sent; }
    public int getFailed() { return failed; }
}
