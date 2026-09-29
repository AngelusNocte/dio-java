package com.exemplo.notification;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.exemplo.notification.builder.NotificationBuilder;
import com.exemplo.notification.chain.ChannelValidator;
import com.exemplo.notification.chain.MessageValidator;
import com.exemplo.notification.chain.RecipientValidator;
import com.exemplo.notification.chain.ValidationException;
import com.exemplo.notification.chain.ValidationHandler;
import com.exemplo.notification.notification.Channel;
import com.exemplo.notification.notification.Notification;
import com.exemplo.notification.notification.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ValidationChainTest {

    private ValidationHandler chain;
    private final User user = new User("Ana", "ana@exemplo.com", "+351933333333", "tok");

    @BeforeEach
    void setUp() {
        chain = new RecipientValidator();
        chain.linkWith(new MessageValidator(20)).linkWith(new ChannelValidator());
    }

    private Notification n(User u, Channel c, String msg) {
        return new NotificationBuilder().to(u).via(c).message(msg).build();
    }

    @Test
    void aceitaNotificacaoValida() {
        assertDoesNotThrow(() -> chain.handle(n(user, Channel.EMAIL, "Olá!")));
    }

    @Test
    void rejeitaMensagemVazia() {
        assertThrows(ValidationException.class, () -> chain.handle(n(user, Channel.EMAIL, " ")));
    }

    @Test
    void rejeitaMensagemLonga() {
        assertThrows(ValidationException.class, () -> chain.handle(n(user, Channel.SMS, "x".repeat(21))));
    }

    @Test
    void rejeitaCanalSemContato() {
        User semPush = new User("Bia", "bia@exemplo.com", null, null);
        assertThrows(ValidationException.class, () -> chain.handle(n(semPush, Channel.PUSH, "Oi")));
    }

    @Test
    void rejeitaDestinatarioNulo() {
        assertThrows(ValidationException.class, () -> chain.handle(n(null, Channel.EMAIL, "Oi")));
    }
}
