package cm.kfokam48.backend.web;

import cm.kfokam48.backend.dto.RelectureDtos.*;
import cm.kfokam48.backend.service.RelectureService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/relectures")
public class RelectureController {

    private final RelectureService relectures;

    public RelectureController(RelectureService relectures) {
        this.relectures = relectures;
    }

    @GetMapping("/{id}")
    public RelectureDetail detail(@PathVariable Long id) {
        return relectures.detail(id);
    }

    @PostMapping("/{id}")
    public RelectureDetail rendre(@PathVariable Long id, @Valid @RequestBody RendreRelectureRequest req) {
        return relectures.rendre(id, req);
    }

    @PostMapping("/{id}/reassigner")
    public RelectureDetail reassigner(@PathVariable Long id, @Valid @RequestBody ReassignerRequest req) {
        return relectures.reassigner(id, req);
    }
}
