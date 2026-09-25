package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class SessionClotureeException extends ApiException {
    public SessionClotureeException() {
        super("SESSION_CLOTUREE", "La session est clôturée.", HttpStatus.GONE);
    }
}
