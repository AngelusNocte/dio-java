package com.exemplo.notification.service;

import com.exemplo.notification.chain.ValidationException;
import com.exemplo.notification.chain.ValidationHandler;
import com.exemplo.notification.factory.NotificationStrategyFactory;
import com.exemplo.notification.notification.Notification;
import com.exemplo.notification.observer.EventPublisher;
import com.exemplo.notification.observer.EventType;
import com.exemplo.notification.observer.NotificationEvent;
import com.exemplo.notification.strategy.NotificationStrategy;

/**
 * Orquestra o fluxo: validar (Chain) -> escolher canal (Factory) -> enviar (Strategy) -> avisar (Observer).
 * Depende de abstrações injetadas pelo construtor (DIP).
 */
public class NotificationService {

    private final ValidationHandler validationChain;
    private final EventPublisher publisher;

    public NotificationService(ValidationHandler validationChain, EventPublisher publisher) {
        this.validationChain = validationChain;
        this.publisher = publisher;
    }

    public boolean send(Notification notification) {
        System.out.println("Validando notificação...");
        try {
            validationChain.handle(notification);

            NotificationStrategy strategy = NotificationStrategyFactory.create(notification.channel());
            strategy.send(notification);
            System.out.println("  ✓ Notificação enviada com sucesso!");

            publisher.publish(new NotificationEvent(EventType.NOTIFICATION_SENT, notification, null));
            return true;
        } catch (ValidationException e) {
            System.out.println("  ✗ Falha: " + e.getMessage());
            publisher.publish(new NotificationEvent(EventType.NOTIFICATION_FAILED, notification, e.getMessage()));
            return false;
        }
    }
}
