package cm.kfokam48.backend.dto;

import cm.kfokam48.backend.domain.enums.SourcePresence;
import java.time.LocalDateTime;

public class PresenceDtos {

    public record MarquerPresenceRequest(String code, Long etudiantId) {}

    public record PresenceManuelleRequest(Long sessionId, Long etudiantId) {}

    public record PresenceResponse(Long id, Long sessionId, Long etudiantId, SourcePresence source) {}

    public record PresenceDetail(Long id, Long etudiantId, SourcePresence source, LocalDateTime marqueeAt) {}
}
