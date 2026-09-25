package cm.kfokam48.backend.web;

import cm.kfokam48.backend.dto.PresenceDtos.PresenceDetail;
import cm.kfokam48.backend.dto.SessionDtos.*;
import cm.kfokam48.backend.service.PresenceService;
import cm.kfokam48.backend.service.SessionService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sessions")
public class SessionController {

    private final SessionService sessions;
    private final PresenceService presences;

    public SessionController(SessionService sessions, PresenceService presences) {
        this.sessions = sessions;
        this.presences = presences;
    }

    @PostMapping
    public ResponseEntity<SessionResponse> ouvrir(@Valid @RequestBody OuvrirSessionRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(sessions.ouvrir(req));
    }

    @GetMapping("/{id}")
    public SessionDetail detail(@PathVariable Long id) {
        return sessions.detail(id);
    }

    @GetMapping("/{id}/presences")
    public List<PresenceDetail> presences(@PathVariable Long id) {
        return presences.parSession(id);
    }

    @PostMapping("/{id}/cloturer")
    public ResponseEntity<Void> cloturer(@PathVariable Long id) {
        sessions.cloturer(id);
        return ResponseEntity.ok().build();
    }
}
