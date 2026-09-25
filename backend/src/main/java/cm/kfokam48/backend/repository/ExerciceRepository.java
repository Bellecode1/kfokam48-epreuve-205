package cm.kfokam48.backend.repository;

import cm.kfokam48.backend.domain.Exercice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExerciceRepository extends JpaRepository<Exercice, Long> {
    Optional<Exercice> findBySessionIdAndEtudiantId(Long sessionId, Long etudiantId);
    List<Exercice> findBySessionId(Long sessionId);
    long countByEtudiantId(Long etudiantId);
}
