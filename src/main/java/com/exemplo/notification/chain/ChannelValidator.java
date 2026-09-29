package com.exemplo.notification.chain;

import com.exemplo.notification.notification.Notification;
import com.exemplo.notification.notification.User;

/** Garante que o destinatário possui o dado de contato exigido pelo canal. */
public class ChannelValidator extends ValidationHandler {

    @Override
    protected void validate(Notification n) {
        if (n.channel() == null) {
            throw new ValidationException("Canal não informado");
        }
        User user = n.recipient();
        boolean missing = switch (n.channel()) {
            case EMAIL -> User.isBlank(user.email());
            case SMS -> User.isBlank(user.phone());
            case PUSH -> User.isBlank(user.deviceToken());
        };
        if (missing) {
            throw new ValidationException("Destinatário sem contato para o canal " + n.channel());
        }
    }

    @Override
    protected String successMessage() {
        return "Canal válido";
    }
}
