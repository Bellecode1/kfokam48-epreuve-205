package cm.kfokam48.backend.service;

import cm.kfokam48.backend.domain.Presence;
import cm.kfokam48.backend.domain.SessionCours;
import cm.kfokam48.backend.domain.TentativeCode;
import cm.kfokam48.backend.domain.enums.SourcePresence;
import cm.kfokam48.backend.dto.PresenceDtos.*;
import cm.kfokam48.backend.exception.*;
import cm.kfokam48.backend.repository.PresenceRepository;
import cm.kfokam48.backend.repository.SessionCoursRepository;
import cm.kfokam48.backend.repository.TentativeCodeRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PresenceService {

    private final PresenceRepository presences;
    private final SessionCoursRepository sessions;
    private final TentativeCodeRepository tentatives;

    public PresenceService(PresenceRepository presences, SessionCoursRepository sessions, TentativeCodeRepository tentatives) {
        this.presences = presences;
        this.sessions = sessions;
        this.tentatives = tentatives;
    }

    @Transactional
    public PresenceResponse marquer(MarquerPresenceRequest req) {
        SessionCours session = sessions.findByCode(req.code())
                .orElseThrow(CodeInconnuException::new);

        TentativeCode t = tentatives.findBySessionIdAndEtudiantId(session.getId(), req.etudiantId())
                .orElseGet(() -> {
                    TentativeCode tc = new TentativeCode();
                    tc.setSessionId(session.getId());
                    tc.setEtudiantId(req.etudiantId());
                    return tc;
                });

        if (t.getBloqueJusqua() != null && t.getBloqueJusqua().isAfter(LocalDateTime.now())) {
            throw new EtudiantBloqueException();
        }

        if (session.isCloturee()) throw new SessionClotureeException();
        if (session.getExpirationAt().isBefore(LocalDateTime.now())) throw new CodeExpireException();

        try {
            Presence p = new Presence();
            p.setSessionId(session.getId());
            p.setEtudiantId(req.etudiantId());
            p.setMarqueeAt(LocalDateTime.now());
            p.setSource(SourcePresence.ETUDIANT);
            Presence saved = presences.saveAndFlush(p);

            t.setNbEchecs(0);
            t.setBloqueJusqua(null);
            tentatives.save(t);

            return new PresenceResponse(saved.getId(), saved.getSessionId(), saved.getEtudiantId(), saved.getSource());
        } catch (DataIntegrityViolationException e) {
            throw new DejaPresentException();
        }
    }

    @Transactional
    public PresenceResponse ajouterManuelle(PresenceManuelleRequest req) {
        SessionCours session = sessions.findById(req.sessionId()).orElseThrow(SessionInconnueException::new);
        if (session.isCloturee()) throw new SessionClotureeException();
        if (presences.existsBySessionIdAndEtudiantId(session.getId(), req.etudiantId())) throw new DejaPresentException();

        Presence p = new Presence();
        p.setSessionId(session.getId());
        p.setEtudiantId(req.etudiantId());
        p.setMarqueeAt(LocalDateTime.now());
        p.setSource(SourcePresence.FORMATEUR);
        Presence saved = presences.save(p);
        return new PresenceResponse(saved.getId(), saved.getSessionId(), saved.getEtudiantId(), saved.getSource());
    }

    public List<PresenceDetail> parSession(Long sessionId) {
        if (!sessions.existsById(sessionId)) throw new SessionInconnueException();
        return presences.findBySessionId(sessionId).stream()
                .map(p -> new PresenceDetail(p.getId(), p.getEtudiantId(), p.getSource(), p.getMarqueeAt()))
                .toList();
    }
}
