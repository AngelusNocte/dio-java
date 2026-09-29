package com.exemplo.notification.chain;

import com.exemplo.notification.notification.Notification;
import com.exemplo.notification.notification.User;

public class RecipientValidator extends ValidationHandler {

    @Override
    protected void validate(Notification n) {
        if (n.recipient() == null || User.isBlank(n.recipient().name())) {
            throw new ValidationException("Destinatário inválido");
        }
    }

    @Override
    protected String successMessage() {
        return "Destinatário válido";
    }
}
