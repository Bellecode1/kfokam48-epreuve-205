package cm.kfokam48.backend;

import cm.kfokam48.backend.dto.PresenceDtos.MarquerPresenceRequest;
import cm.kfokam48.backend.dto.SessionDtos.OuvrirSessionRequest;
import cm.kfokam48.backend.repository.PresenceRepository;
import cm.kfokam48.backend.service.PresenceService;
import cm.kfokam48.backend.service.SessionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class RacePresenceTest {

    @Autowired SessionService sessions;
    @Autowired PresenceService presences;
    @Autowired PresenceRepository presenceRepo;

    @Test
    void deuxPresencesSimultaneesDoiventEtreEnregistrees() throws Exception {
        var session = sessions.ouvrir(new OuvrirSessionRequest("Race", 1L));
        long avant = presenceRepo.count();

        int n = 2;
        ExecutorService pool = Executors.newFixedThreadPool(n);
        CountDownLatch go = new CountDownLatch(1);
        AtomicInteger ok = new AtomicInteger();

        for (long etu = 1; etu <= n; etu++) {
            final long id = etu;
            pool.submit(() -> {
                try {
                    go.await();
                    presences.marquer(new MarquerPresenceRequest(session.code(), id));
                    ok.incrementAndGet();
                } catch (Exception ignored) {}
            });
        }
        go.countDown();
        pool.shutdown();
        pool.awaitTermination(10, TimeUnit.SECONDS);

        assertEquals(2, ok.get(), "Les 2 presences doivent etre enregistrees");
        assertEquals(avant + 2, presenceRepo.count());
    }
}
