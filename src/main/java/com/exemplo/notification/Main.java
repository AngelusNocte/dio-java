package com.exemplo.notification;

import com.exemplo.notification.builder.NotificationBuilder;
import com.exemplo.notification.chain.ChannelValidator;
import com.exemplo.notification.chain.MessageValidator;
import com.exemplo.notification.chain.RecipientValidator;
import com.exemplo.notification.chain.ValidationHandler;
import com.exemplo.notification.config.AppConfig;
import com.exemplo.notification.notification.Channel;
import com.exemplo.notification.notification.Notification;
import com.exemplo.notification.notification.User;
import com.exemplo.notification.observer.EventPublisher;
import com.exemplo.notification.observer.LogListener;
import com.exemplo.notification.observer.MetricsListener;
import com.exemplo.notification.service.NotificationService;

public class Main {

    public static void main(String[] args) {
        AppConfig config = AppConfig.getInstance();
        System.out.println("=== " + config.getAppName() + " ===\n");

        // Observer
        EventPublisher publisher = new EventPublisher();
        MetricsListener metrics = new MetricsListener();
        publisher.subscribe(new LogListener());
        publisher.subscribe(metrics);

        // Chain of Responsibility
        ValidationHandler chain = new RecipientValidator();
        chain.linkWith(new MessageValidator(config.getMaxMessageLength()))
             .linkWith(new ChannelValidator());

        NotificationService service = new NotificationService(chain, publisher);

        User joao = new User("João", "joao@exemplo.com", "+351911111111", "token-joao");
        User maria = new User("Maria", "maria@exemplo.com", "+351922222222", null);
        User carlos = new User("Carlos", "carlos@exemplo.com", null, null);

        // Builder
        Notification[] cenarios = {
            new NotificationBuilder().to(joao).via(Channel.EMAIL).message("Seu pedido foi enviado!").build(),
            new NotificationBuilder().to(maria).via(Channel.SMS).message("Seu código de acesso é 4821").build(),
            new NotificationBuilder().to(carlos).via(Channel.PUSH).message("Promoção relâmpago!").build(),
            new NotificationBuilder().to(joao).via(Channel.EMAIL).message("   ").build()
        };

        for (Notification n : cenarios) {
            System.out.println("Usuário: " + n.recipient().name());
            System.out.println("Canal: " + n.channel());
            System.out.println("Mensagem: " + n.message());
            service.send(n);
            System.out.println();
        }

        System.out.println("Resumo: " + metrics.getSent() + " enviada(s), " + metrics.getFailed() + " falha(s).");
    }
}
