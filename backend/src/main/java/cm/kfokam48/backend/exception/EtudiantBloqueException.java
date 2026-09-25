package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class EtudiantBloqueException extends ApiException {
    public EtudiantBloqueException() {
        super("ETUDIANT_BLOQUE", "Trop d'erreurs, réessayez dans 2 minutes.", HttpStatus.TOO_MANY_REQUESTS);
    }
}
