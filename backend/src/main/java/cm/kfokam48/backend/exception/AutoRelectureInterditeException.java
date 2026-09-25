package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class AutoRelectureInterditeException extends ApiException {
    public AutoRelectureInterditeException() {
        super("AUTO_RELECTURE_INTERDITE", "Un étudiant ne peut pas relire son propre exercice.", HttpStatus.FORBIDDEN);
    }
}
