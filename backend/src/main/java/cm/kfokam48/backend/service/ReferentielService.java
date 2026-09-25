package cm.kfokam48.backend.service;

import cm.kfokam48.backend.dto.ReferentielDtos.*;
import cm.kfokam48.backend.exception.PromotionInconnueException;
import cm.kfokam48.backend.repository.EtudiantRepository;
import cm.kfokam48.backend.repository.PromotionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReferentielService {

    private final PromotionRepository promotions;
    private final EtudiantRepository etudiants;

    public ReferentielService(PromotionRepository promotions, EtudiantRepository etudiants) {
        this.promotions = promotions;
        this.etudiants = etudiants;
    }

    public List<PromotionResponse> promotions() {
        return promotions.findAll().stream()
                .map(p -> new PromotionResponse(p.getId(), p.getNom(), p.getAnnee()))
                .toList();
    }

    public List<EtudiantResponse> etudiantsDe(Long promotionId) {
        if (!promotions.existsById(promotionId)) throw new PromotionInconnueException();
        return etudiants.findByPromotionId(promotionId).stream()
                .map(e -> new EtudiantResponse(e.getId(), e.getNom(), e.getPrenom()))
                .toList();
    }
}
