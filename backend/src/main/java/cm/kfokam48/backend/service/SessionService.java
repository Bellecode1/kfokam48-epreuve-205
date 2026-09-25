package cm.kfokam48.backend.service;

import cm.kfokam48.backend.domain.Formateur;
import cm.kfokam48.backend.domain.SessionCours;
import cm.kfokam48.backend.dto.SessionDtos.*;
import cm.kfokam48.backend.exception.PromotionInconnueException;
import cm.kfokam48.backend.exception.SessionDejaClotureeException;
import cm.kfokam48.backend.exception.SessionInconnueException;
import cm.kfokam48.backend.repository.FormateurRepository;
import cm.kfokam48.backend.repository.PromotionRepository;
import cm.kfokam48.backend.repository.SessionCoursRepository;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.LocalDateTime;

@Service
public class SessionService {

    private static final String ALPHABET = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    private final SessionCoursRepository sessions;
    private final PromotionRepository promotions;
    private final FormateurRepository formateurs;

    public SessionService(SessionCoursRepository sessions, PromotionRepository promotions, FormateurRepository formateurs) {
        this.sessions = sessions;
        this.promotions = promotions;
        this.formateurs = formateurs;
    }

    public SessionResponse ouvrir(OuvrirSessionRequest req) {
        if (req.titre() == null || req.titre().isBlank() || req.promotionId() == null) {
            throw new cm.kfokam48.backend.exception.ApiException("CHAMP_MANQUANT", "Champ manquant.", org.springframework.http.HttpStatus.BAD_REQUEST);
        }
        if (!promotions.existsById(req.promotionId())) {
            throw new PromotionInconnueException();
        }

        SessionCours s = new SessionCours();
        s.setTitre(req.titre());
        s.setPromotionId(req.promotionId());
        s.setCode(genererCode());
        LocalDateTime now = LocalDateTime.now();
        s.setOuvertureAt(now);
        s.setExpirationAt(now.plusMinutes(15));
        s.setCloturee(false);

        // formateur par défaut (premier disponible) — le sujet n'impose pas d'auth
        formateurs.findAll().stream().findFirst().ifPresent(f -> s.setFormateurId(f.getId()));

        SessionCours saved = sessions.save(s);
        return new SessionResponse(saved.getId(), saved.getCode(), saved.getOuvertureAt(), saved.getExpirationAt());
    }

    public SessionDetail detail(Long id) {
        SessionCours s = sessions.findById(id).orElseThrow(SessionInconnueException::new);
        return new SessionDetail(s.getId(), s.getTitre(), s.getCode(), s.getOuvertureAt(),
                s.getExpirationAt(), s.getClotureAt(), s.isCloturee(), s.getPromotionId());
    }

    public void cloturer(Long id) {
        SessionCours s = sessions.findById(id).orElseThrow(SessionInconnueException::new);
        if (s.isCloturee()) throw new SessionDejaClotureeException();
        s.setCloturee(true);
        s.setClotureAt(LocalDateTime.now());
        sessions.save(s);
    }

    private String genererCode() {
        String code;
        do {
            StringBuilder sb = new StringBuilder(6);
            for (int i = 0; i < 6; i++) sb.append(ALPHABET.charAt(RANDOM.nextInt(ALPHABET.length())));
            code = sb.toString();
        } while (sessions.existsByCode(code));
        return code;
    }
}
