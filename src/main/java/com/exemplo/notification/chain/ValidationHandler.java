package com.exemplo.notification.chain;

import com.exemplo.notification.notification.Notification;

/** Chain of Responsibility: cada elo valida uma regra e repassa ao próximo. */
public abstract class ValidationHandler {

    private ValidationHandler next;

    /** Encadeia o próximo validador e o retorna, permitindo encadeamento fluente. */
    public ValidationHandler linkWith(ValidationHandler next) {
        this.next = next;
        return next;
    }

    public final void handle(Notification notification) {
        validate(notification);
        System.out.println("  ✓ " + successMessage());
        if (next != null) {
            next.handle(notification);
        }
    }

    /** Deve lançar {@link ValidationException} se a regra for violada. */
    protected abstract void validate(Notification notification);

    protected abstract String successMessage();
}
