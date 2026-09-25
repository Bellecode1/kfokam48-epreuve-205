package cm.kfokam48.backend.exception;

import org.springframework.http.HttpStatus;

public class PromotionInconnueException extends ApiException {
    public PromotionInconnueException() {
        super("PROMOTION_INCONNUE", "Promotion inconnue.", HttpStatus.NOT_FOUND);
    }
}
