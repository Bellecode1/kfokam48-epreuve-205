package cm.kfokam48.backend.service;

import cm.kfokam48.backend.domain.Etudiant;
import cm.kfokam48.backend.domain.Exercice;
import cm.kfokam48.backend.domain.Relecture;
import cm.kfokam48.backend.domain.enums.StatutRelecture;
import cm.kfokam48.backend.dto.TableauDtos.LigneTableau;
import cm.kfokam48.backend.exception.PromotionInconnueException;
import cm.kfokam48.backend.repository.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TableauService {

    private final PromotionRepository promotions;
    private final EtudiantRepository etudiants;
    private final PresenceRepository presences;
    private final ExerciceRepository exercices;
    private final RelectureRepository relectures;

    public TableauService(PromotionRepository promotions, EtudiantRepository etudiants,
                          PresenceRepository presences, ExerciceRepository exercices,
                          RelectureRepository relectures) {
        this.promotions = promotions;
        this.etudiants = etudiants;
        this.presences = presences;
        this.exercices = exercices;
        this.relectures = relectures;
    }

    public List<LigneTableau> pourPromotion(Long promotionId) {
        if (!promotions.existsById(promotionId)) throw new PromotionInconnueException();
        return etudiants.findByPromotionId(promotionId).stream().map(this::ligne).toList();
    }

    private LigneTableau ligne(Etudiant e) {
        long nbPresences = presences.countByEtudiantId(e.getId());
        List<Exercice> mesExercices = exercices.findAll().stream()
                .filter(ex -> ex.getEtudiantId().equals(e.getId()))
                .toList();
        long nbExercices = mesExercices.size();

        List<Integer> notes = mesExercices.stream()
                .map(ex -> relectures.findByExerciceId(ex.getId()).orElse(null))
                .filter(r -> r != null && r.getStatut() == StatutRelecture.RENDUE && r.getNote() != null)
                .map(Relecture::getNote)
                .toList();
        Double moyenne = notes.isEmpty() ? null : notes.stream().mapToInt(Integer::intValue).average().orElse(0);

        long enAttente = relectures.countByRelecteurIdAndStatut(e.getId(), StatutRelecture.ASSIGNEE);

        String nomComplet = e.getPrenom() + " " + e.getNom();
        return new LigneTableau(e.getId(), nomComplet, nbPresences, nbExercices, moyenne, enAttente);
    }
}
