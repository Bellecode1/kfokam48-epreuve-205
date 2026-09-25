package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class SessionDejaClotureeException extends ApiException {
    public SessionDejaClotureeException() {
        super("SESSION_DEJA_CLOTUREE", "La session est déjà clôturée.", HttpStatus.CONFLICT);
    }
}
