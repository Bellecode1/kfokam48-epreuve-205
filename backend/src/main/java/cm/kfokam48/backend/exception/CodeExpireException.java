package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class CodeExpireException extends ApiException {
    public CodeExpireException() {
        super("CODE_EXPIRE", "Le code de présence a expiré.", HttpStatus.GONE);
    }
}
