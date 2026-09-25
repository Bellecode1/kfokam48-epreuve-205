package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class NoteInvalideException extends ApiException {
    public NoteInvalideException() {
        super("NOTE_INVALIDE", "La note doit être un entier entre 0 et 20.", HttpStatus.BAD_REQUEST);
    }
}
