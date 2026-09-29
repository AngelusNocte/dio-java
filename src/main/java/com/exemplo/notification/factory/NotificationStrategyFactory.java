package com.exemplo.notification.factory;

import com.exemplo.notification.notification.Channel;
import com.exemplo.notification.strategy.EmailStrategy;
import com.exemplo.notification.strategy.NotificationStrategy;
import com.exemplo.notification.strategy.PushStrategy;
import com.exemplo.notification.strategy.SmsStrategy;

/** Factory: centraliza a criação da Strategy correta para cada canal. */
public final class NotificationStrategyFactory {

    private NotificationStrategyFactory() { }

    public static NotificationStrategy create(Channel channel) {
        return switch (channel) {
            case EMAIL -> new EmailStrategy();
            case SMS -> new SmsStrategy();
            case PUSH -> new PushStrategy();
        };
    }
}
