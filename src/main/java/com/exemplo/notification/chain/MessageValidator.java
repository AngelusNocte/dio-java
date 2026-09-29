package com.exemplo.notification.chain;

import com.exemplo.notification.notification.Notification;
import com.exemplo.notification.notification.User;

public class MessageValidator extends ValidationHandler {

    private final int maxLength;

    public MessageValidator(int maxLength) {
        this.maxLength = maxLength;
    }

    @Override
    protected void validate(Notification n) {
        if (User.isBlank(n.message())) {
            throw new ValidationException("Mensagem não pode ser vazia");
        }
        if (n.message().length() > maxLength) {
            throw new ValidationException("Mensagem excede " + maxLength + " caracteres");
        }
    }

    @Override
    protected String successMessage() {
        return "Mensagem válida";
    }
}
