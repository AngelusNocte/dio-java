package com.exemplo.notification.config;

/**
 * Singleton: configuração central da aplicação.
 * Usa o idiom "initialization-on-demand holder": thread-safe e com carga preguiçosa.
 */
public final class AppConfig {

    private final String appName = "Notification API";
    private final int maxMessageLength = 200;

    private AppConfig() { }

    private static class Holder {
        private static final AppConfig INSTANCE = new AppConfig();
    }

    public static AppConfig getInstance() {
        return Holder.INSTANCE;
    }

    public String getAppName() { return appName; }
    public int getMaxMessageLength() { return maxMessageLength; }
}
