package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class RelectureInconnueException extends ApiException {
    public RelectureInconnueException() {
        super("RELECTURE_INCONNUE", "Relecture inconnue.", HttpStatus.NOT_FOUND);
    }
}
