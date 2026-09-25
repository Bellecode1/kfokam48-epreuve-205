package cm.kfokam48.backend.dto;

import java.util.List;

public class ReferentielDtos {

    public record PromotionResponse(Long id, String nom, String annee) {}

    public record EtudiantResponse(Long id, String nom, String prenom) {}
}
