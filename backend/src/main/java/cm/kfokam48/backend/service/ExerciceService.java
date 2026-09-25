package cm.kfokam48.backend.service;

import cm.kfokam48.backend.domain.Etudiant;
import cm.kfokam48.backend.domain.Exercice;
import cm.kfokam48.backend.domain.Presence;
import cm.kfokam48.backend.domain.Relecture;
import cm.kfokam48.backend.domain.SessionCours;
import cm.kfokam48.backend.domain.enums.StatutExercice;
import cm.kfokam48.backend.domain.enums.StatutRelecture;
import cm.kfokam48.backend.dto.ExerciceDtos.*;
import cm.kfokam48.backend.exception.*;
import cm.kfokam48.backend.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

@Service
public class ExerciceService {

    private final ExerciceRepository exercices;
    private final SessionCoursRepository sessions;
    private final PresenceRepository presences;
    private final EtudiantRepository etudiants;
    private final RelectureRepository relectures;

    public ExerciceService(ExerciceRepository exercices, SessionCoursRepository sessions,
                           PresenceRepository presences, EtudiantRepository etudiants,
                           RelectureRepository relectures) {
        this.exercices = exercices;
        this.sessions = sessions;
        this.presences = presences;
        this.etudiants = etudiants;
        this.relectures = relectures;
    }

    @Transactional
    public ExerciceResponse deposer(DeposerExerciceRequest req) {
        if (req.lien() == null || !req.lien().startsWith("http")) throw new LienInvalideException();
        SessionCours session = sessions.findById(req.sessionId()).orElseThrow(SessionInconnueException::new);
        if (session.isCloturee()) throw new SessionClotureeException();
        if (exercices.findBySessionIdAndEtudiantId(req.sessionId(), req.etudiantId()).isPresent()) {
            throw new ExerciceDejaDeposeException();
        }

        Exercice e = new Exercice();
        e.setSessionId(req.sessionId());
        e.setEtudiantId(req.etudiantId());
        e.setLien(req.lien());
        e.setDeposeAt(LocalDateTime.now());
        e.setStatut(StatutExercice.DEPOSE);
        Exercice saved = exercices.save(e);

        // assigner un relecteur
        assignerRelecteur(saved);

        return new ExerciceResponse(saved.getId(), saved.getStatut());
    }

    @Transactional
    public ExerciceDetail remplacerLien(Long id, RemplacerLienRequest req) {
        if (req.lien() == null || !req.lien().startsWith("http")) throw new LienInvalideException();
        Exercice e = exercices.findById(id).orElseThrow(ExerciceInconnuException::new);
        // interdiction si relecture commencée
        relectures.findByExerciceId(id).ifPresent(r -> {
            if (r.getStatut() == StatutRelecture.RENDUE) throw new RelectureDejaCommenceeException();
        });
        e.setLien(req.lien());
        Exercice saved = exercices.save(e);
        return toDetail(saved);
    }

    public ExerciceDetail detail(Long id) {
        Exercice e = exercices.findById(id).orElseThrow(ExerciceInconnuException::new);
        return toDetail(e);
    }

    private void assignerRelecteur(Exercice e) {
        List<Presence> presents = presences.findBySessionId(e.getSessionId());
        List<Long> candidats = presents.stream()
                .map(Presence::getEtudiantId)
                .filter(id -> !id.equals(e.getEtudiantId()))
                .toList();
        if (candidats.isEmpty()) {
            e.setStatut(StatutExercice.EN_ATTENTE_RELECTURE);
            exercices.save(e);
            return;
        }
        Long relecteurId = candidats.get(new Random().nextInt(candidats.size()));
        Relecture r = new Relecture();
        r.setExerciceId(e.getId());
        r.setRelecteurId(relecteurId);
        r.setStatut(StatutRelecture.ASSIGNEE);
        relectures.save(r);
        e.setStatut(StatutExercice.EN_ATTENTE_RELECTURE);
        exercices.save(e);
    }

    private ExerciceDetail toDetail(Exercice e) {
        return new ExerciceDetail(e.getId(), e.getSessionId(), e.getEtudiantId(), e.getLien(), e.getStatut(), e.getDeposeAt());
    }
}
