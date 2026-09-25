package cm.kfokam48.backend.repository;

import cm.kfokam48.backend.domain.TentativeCode;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TentativeCodeRepository extends JpaRepository<TentativeCode, Long> {
    Optional<TentativeCode> findBySessionIdAndEtudiantId(Long sessionId, Long etudiantId);
}
