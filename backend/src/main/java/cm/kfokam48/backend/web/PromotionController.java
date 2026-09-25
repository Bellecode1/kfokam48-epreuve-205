package cm.kfokam48.backend.web;

import cm.kfokam48.backend.dto.ReferentielDtos.*;
import cm.kfokam48.backend.service.ReferentielService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/promotions")
public class PromotionController {

    private final ReferentielService referentiel;

    public PromotionController(ReferentielService referentiel) {
        this.referentiel = referentiel;
    }

    @GetMapping
    public List<PromotionResponse> promotions() {
        return referentiel.promotions();
    }

    @GetMapping("/{id}/etudiants")
    public List<EtudiantResponse> etudiants(@PathVariable Long id) {
        return referentiel.etudiantsDe(id);
    }
}
