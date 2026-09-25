package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class LienInvalideException extends ApiException {
    public LienInvalideException() {
        super("LIEN_INVALIDE", "Le lien fourni est invalide.", HttpStatus.BAD_REQUEST);
    }
}
