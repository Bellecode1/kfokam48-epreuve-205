package cm.kfokam48.backend.web;

import cm.kfokam48.backend.dto.PresenceDtos.*;
import cm.kfokam48.backend.service.PresenceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/presences")
public class PresenceController {

    private final PresenceService presences;

    public PresenceController(PresenceService presences) {
        this.presences = presences;
    }

    @PostMapping
    public ResponseEntity<PresenceResponse> marquer(@Valid @RequestBody MarquerPresenceRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(presences.marquer(req));
    }

    @PostMapping("/manuelle")
    public ResponseEntity<PresenceResponse> manuelle(@Valid @RequestBody PresenceManuelleRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(presences.ajouterManuelle(req));
    }
}
