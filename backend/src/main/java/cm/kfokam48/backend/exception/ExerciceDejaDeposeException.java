package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class ExerciceDejaDeposeException extends ApiException {
    public ExerciceDejaDeposeException() {
        super("EXERCICE_DEJA_DEPOSE", "Un exercice a déjà été déposé pour cette session.", HttpStatus.CONFLICT);
    }
}
