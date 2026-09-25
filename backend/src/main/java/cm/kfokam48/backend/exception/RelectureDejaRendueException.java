package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class RelectureDejaRendueException extends ApiException {
    public RelectureDejaRendueException() {
        super("RELECTURE_DEJA_RENDUE", "Cette relecture a déjà été rendue.", HttpStatus.CONFLICT);
    }
}
