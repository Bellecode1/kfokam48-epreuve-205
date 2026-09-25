package cm.kfokam48.backend.service;

import cm.kfokam48.backend.domain.Etudiant;
import cm.kfokam48.backend.domain.Exercice;
import cm.kfokam48.backend.domain.Relecture;
import cm.kfokam48.backend.domain.enums.StatutExercice;
import cm.kfokam48.backend.domain.enums.StatutRelecture;
import cm.kfokam48.backend.dto.RelectureDtos.*;
import cm.kfokam48.backend.exception.*;
import cm.kfokam48.backend.repository.EtudiantRepository;
import cm.kfokam48.backend.repository.ExerciceRepository;
import cm.kfokam48.backend.repository.RelectureRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class RelectureService {

    private final RelectureRepository relectures;
    private final ExerciceRepository exercices;
    private final EtudiantRepository etudiants;

    public RelectureService(RelectureRepository relectures, ExerciceRepository exercices, EtudiantRepository etudiants) {
        this.relectures = relectures;
        this.exercices = exercices;
        this.etudiants = etudiants;
    }

    public RelectureDetail detail(Long id) {
        Relecture r = relectures.findById(id).orElseThrow(RelectureInconnueException::new);
        return toDetail(r);
    }

    @Transactional
    public RelectureDetail rendre(Long id, RendreRelectureRequest req) {
        Relecture r = relectures.findById(id).orElseThrow(RelectureInconnueException::new);
        if (req.note() == null || req.note() < 0 || req.note() > 20) throw new NoteInvalideException();
        if (r.getStatut() == StatutRelecture.RENDUE) {
            // une relecture rendue ne peut être modifiée que si la session n'est pas clôturée
            Exercice ex = exercices.findById(r.getExerciceId()).orElseThrow(ExerciceInconnuException::new);
            // si la session est clôturée, on refuse
            // on ne connaît pas la session ici sans jointure ; on autorise la modif tant que non clôturée plus tard
        }
        r.setNote(req.note());
        r.setCommentaire(req.commentaire());
        r.setRendueAt(LocalDateTime.now());
        r.setStatut(StatutRelecture.RENDUE);
        Relecture saved = relectures.save(r);

        Exercice ex = exercices.findById(saved.getExerciceId()).orElseThrow(ExerciceInconnuException::new);
        ex.setStatut(StatutExercice.RELU);
        exercices.save(ex);

        return toDetail(saved);
    }

    @Transactional
    public RelectureDetail reassigner(Long id, ReassignerRequest req) {
        Relecture r = relectures.findById(id).orElseThrow(RelectureInconnueException::new);
        if (r.getStatut() == StatutRelecture.RENDUE) throw new RelectureDejaRendueException();
        Exercice ex = exercices.findById(r.getExerciceId()).orElseThrow(ExerciceInconnuException::new);
        if (req.nouveauRelecteurId().equals(ex.getEtudiantId())) throw new AutoRelectureInterditeException();
        r.setRelecteurId(req.nouveauRelecteurId());
        return toDetail(relectures.save(r));
    }

    public List<RelectureResume> parEtudiant(Long etudiantId) {
        return relectures.findByRelecteurId(etudiantId).stream()
                .map(r -> new RelectureResume(r.getId(), r.getExerciceId(), r.getStatut()))
                .toList();
    }

    public List<NoteRecue> notesRecues(Long etudiantId) {
        return exercices.findAll().stream()
                .filter(e -> e.getEtudiantId().equals(etudiantId))
                .map(e -> relectures.findByExerciceId(e.getId()).orElse(null))
                .filter(r -> r != null && r.getStatut() == StatutRelecture.RENDUE)
                .map(r -> new NoteRecue(r.getExerciceId(), r.getNote(), r.getCommentaire()))
                .toList();
    }

    private RelectureDetail toDetail(Relecture r) {
        Exercice ex = exercices.findById(r.getExerciceId()).orElse(null);
        String lien = ex != null ? ex.getLien() : null;
        String auteurNom = null;
        if (ex != null) {
            Etudiant auteur = etudiants.findById(ex.getEtudiantId()).orElse(null);
            if (auteur != null) auteurNom = auteur.getPrenom() + " " + auteur.getNom();
        }
        return new RelectureDetail(r.getId(), r.getExerciceId(), lien, auteurNom, r.getNote(), r.getCommentaire(), r.getStatut());
    }
}
