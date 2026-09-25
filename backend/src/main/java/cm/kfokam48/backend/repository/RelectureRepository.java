package cm.kfokam48.backend.repository;

import cm.kfokam48.backend.domain.Relecture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RelectureRepository extends JpaRepository<Relecture, Long> {
    Optional<Relecture> findByExerciceId(Long exerciceId);
    List<Relecture> findByRelecteurId(Long relecteurId);
    long countByRelecteurIdAndStatut(Long relecteurId, cm.kfokam48.backend.domain.enums.StatutRelecture statut);
}
