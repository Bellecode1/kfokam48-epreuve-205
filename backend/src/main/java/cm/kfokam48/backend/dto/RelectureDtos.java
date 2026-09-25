package cm.kfokam48.backend.dto;

import cm.kfokam48.backend.domain.enums.StatutRelecture;

public class RelectureDtos {

    public record RendreRelectureRequest(Integer note, String commentaire) {}

    public record ReassignerRequest(Long nouveauRelecteurId) {}

    public record RelectureDetail(Long id, Long exerciceId, String lien, String auteurNom,
                                  Integer note, String commentaire, StatutRelecture statut) {}

    public record RelectureResume(Long id, Long exerciceId, StatutRelecture statut) {}

    public record NoteRecue(Long exerciceId, Integer note, String commentaire) {}
}
