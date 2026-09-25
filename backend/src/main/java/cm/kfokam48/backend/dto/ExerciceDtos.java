package cm.kfokam48.backend.dto;

import cm.kfokam48.backend.domain.enums.StatutExercice;
import java.time.LocalDateTime;

public class ExerciceDtos {

    public record DeposerExerciceRequest(Long sessionId, Long etudiantId, String lien) {}

    public record RemplacerLienRequest(String lien) {}

    public record ExerciceResponse(Long id, StatutExercice statut) {}

    public record ExerciceDetail(Long id, Long sessionId, Long etudiantId, String lien,
                                 StatutExercice statut, LocalDateTime deposeAt) {}
}
