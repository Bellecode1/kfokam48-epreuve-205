package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class CodeInconnuException extends ApiException {
    public CodeInconnuException() {
        super("CODE_INCONNU", "Code inconnu.", HttpStatus.BAD_REQUEST);
    }
}
