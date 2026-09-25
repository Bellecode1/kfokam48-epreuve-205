package cm.kfokam48.backend.web;

import cm.kfokam48.backend.dto.TableauDtos.LigneTableau;
import cm.kfokam48.backend.service.TableauService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tableau")
public class TableauController {

    private final TableauService tableau;

    public TableauController(TableauService tableau) {
        this.tableau = tableau;
    }

    @GetMapping
    public List<LigneTableau> tableau(@RequestParam Long promotionId) {
        return tableau.pourPromotion(promotionId);
    }
}
