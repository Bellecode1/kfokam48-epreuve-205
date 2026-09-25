package cm.kfokam48.backend.dto;

public class TableauDtos {

    public record LigneTableau(Long etudiantId, String nom, long presences, long exercicesDeposes,
                               Double moyenne, long relecturesEnAttente) {}
}
