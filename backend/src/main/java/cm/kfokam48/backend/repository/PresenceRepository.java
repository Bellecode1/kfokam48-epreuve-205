package cm.kfokam48.backend.repository;

import cm.kfokam48.backend.domain.Presence;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PresenceRepository extends JpaRepository<Presence, Long> {
    boolean existsBySessionIdAndEtudiantId(Long sessionId, Long etudiantId);
    List<Presence> findBySessionId(Long sessionId);
    long countByEtudiantId(Long etudiantId);
}
