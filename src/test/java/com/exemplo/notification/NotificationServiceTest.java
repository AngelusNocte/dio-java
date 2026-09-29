package com.exemplo.notification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.exemplo.notification.builder.NotificationBuilder;
import com.exemplo.notification.chain.ChannelValidator;
import com.exemplo.notification.chain.MessageValidator;
import com.exemplo.notification.chain.RecipientValidator;
import com.exemplo.notification.chain.ValidationHandler;
import com.exemplo.notification.config.AppConfig;
import com.exemplo.notification.factory.NotificationStrategyFactory;
import com.exemplo.notification.notification.Channel;
import com.exemplo.notification.notification.User;
import com.exemplo.notification.observer.EventPublisher;
import com.exemplo.notification.observer.MetricsListener;
import com.exemplo.notification.service.NotificationService;
import com.exemplo.notification.strategy.EmailStrategy;
import com.exemplo.notification.strategy.PushStrategy;
import com.exemplo.notification.strategy.SmsStrategy;
import org.junit.jupiter.api.Test;

class NotificationServiceTest {

    private NotificationService service(MetricsListener metrics) {
        ValidationHandler chain = new RecipientValidator();
        chain.linkWith(new MessageValidator(50)).linkWith(new ChannelValidator());
        EventPublisher publisher = new EventPublisher();
        publisher.subscribe(metrics);
        return new NotificationService(chain, publisher);
    }

    @Test
    void enviaComSucessoEPublicaEvento() {
        MetricsListener metrics = new MetricsListener();
        User u = new User("Ana", "ana@exemplo.com", null, null);
        boolean ok = service(metrics).send(new NotificationBuilder().to(u).via(Channel.EMAIL).message("Oi").build());

        assertTrue(ok);
        assertEquals(1, metrics.getSent());
        assertEquals(0, metrics.getFailed());
    }

    @Test
    void falhaNaValidacaoPublicaEventoDeFalha() {
        MetricsListener metrics = new MetricsListener();
        User u = new User("Ana", "ana@exemplo.com", null, null);
        boolean ok = service(metrics).send(new NotificationBuilder().to(u).via(Channel.SMS).message("Oi").build());

        assertFalse(ok);
        assertEquals(0, metrics.getSent());
        assertEquals(1, metrics.getFailed());
    }

    @Test
    void factoryRetornaEstrategiaCorreta() {
        assertInstanceOf(EmailStrategy.class, NotificationStrategyFactory.create(Channel.EMAIL));
        assertInstanceOf(SmsStrategy.class, NotificationStrategyFactory.create(Channel.SMS));
        assertInstanceOf(PushStrategy.class, NotificationStrategyFactory.create(Channel.PUSH));
    }

    @Test
    void configEhSingleton() {
        assertSame(AppConfig.getInstance(), AppConfig.getInstance());
    }
}
