package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class DejaPresentException extends ApiException {
    public DejaPresentException() {
        super("DEJA_PRESENT", "Vous êtes déjà marqué présent.", HttpStatus.CONFLICT);
    }
}
