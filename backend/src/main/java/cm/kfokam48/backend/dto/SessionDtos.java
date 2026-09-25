package cm.kfokam48.backend.dto;

import java.time.LocalDateTime;

public class SessionDtos {

    public record OuvrirSessionRequest(String titre, Long promotionId) {}

    public record SessionResponse(Long id, String code, LocalDateTime ouvertureAt, LocalDateTime expirationAt) {}

    public record SessionDetail(Long id, String titre, String code, LocalDateTime ouvertureAt,
                                LocalDateTime expirationAt, LocalDateTime clotureAt, boolean cloturee,
                                Long promotionId) {}
}
