package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class ExerciceInconnuException extends ApiException {
    public ExerciceInconnuException() {
        super("EXERCICE_INCONNU", "Exercice inconnu.", HttpStatus.NOT_FOUND);
    }
}
