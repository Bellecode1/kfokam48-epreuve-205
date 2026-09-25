package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class RelectureDejaCommenceeException extends ApiException {
    public RelectureDejaCommenceeException() {
        super("RELECTURE_DEJA_COMMENCEE", "La relecture a déjà commencé, le lien ne peut plus être modifié.", HttpStatus.FORBIDDEN);
    }
}
