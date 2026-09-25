package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class SessionInconnueException extends ApiException {
    public SessionInconnueException() {
        super("SESSION_INCONNUE", "Session inconnue.", HttpStatus.NOT_FOUND);
    }
}
