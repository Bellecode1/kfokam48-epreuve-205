package cm.kfokam48.backend;

import cm.kfokam48.backend.domain.SessionCours;
import cm.kfokam48.backend.repository.PresenceRepository;
import cm.kfokam48.backend.repository.SessionCoursRepository;
import cm.kfokam48.backend.service.PresenceService;
import cm.kfokam48.backend.dto.PresenceDtos.MarquerPresenceRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class PresenceConcurrenceTest {

    @Autowired
    private PresenceService presenceService;

    @Autowired
    private SessionCoursRepository sessionRepository;

    @Autowired
    private PresenceRepository presenceRepository;

    private SessionCours session;
    private static final Long ETUDIANT_ID = 1L;

    @BeforeEach
    void setUp() {
        sessionRepository.deleteAll();
        
        session = new SessionCours();
        session.setTitre("Session test");
        session.setCode("ABC123");
        session.setOuvertureAt(LocalDateTime.now().minusMinutes(10));
        session.setExpirationAt(LocalDateTime.now().plusMinutes(15));
        session.setCloturee(false);
        session.setPromotionId(1L);
        session = sessionRepository.save(session);
    }

    @Test
    void testConcurrence_MemeEtudiant_CodeSimultane_PasDeDoublon() throws Exception {
        // Given
        MarquerPresenceRequest request = new MarquerPresenceRequest(session.getCode(), ETUDIANT_ID);
        
        ExecutorService executor = Executors.newFixedThreadPool(2);
        
        // When - deux requêtes simultanées pour le même étudiant
        CompletableFuture<Void> future1 = CompletableFuture.runAsync(() -> {
            try {
                presenceService.marquer(request);
            } catch (Exception e) {
                // Une des deux peut échouer à cause de la contrainte unique
            }
        }, executor);
        
        CompletableFuture<Void> future2 = CompletableFuture.runAsync(() -> {
            try {
                presenceService.marquer(request);
            } catch (Exception e) {
                // Une des deux peut échouer à cause de la contrainte unique
            }
        }, executor);
        
        CompletableFuture.allOf(future1, future2).get();
        executor.shutdown();
        
        // Then - une seule présence doit exister (pas de doublon grâce à la contrainte unique)
        List<cm.kfokam48.backend.domain.Presence> presences = 
            presenceRepository.findBySessionId(session.getId());
        assertEquals(1, presences.size(), "Une seule présence doit exister, pas de doublon");
    }
}
