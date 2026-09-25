package cm.kfokam48.backend.web;

import cm.kfokam48.backend.dto.RelectureDtos.NoteRecue;
import cm.kfokam48.backend.dto.RelectureDtos.RelectureResume;
import cm.kfokam48.backend.service.RelectureService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/etudiants")
public class EtudiantController {

    private final RelectureService relectures;

    public EtudiantController(RelectureService relectures) {
        this.relectures = relectures;
    }

    @GetMapping("/{id}/relectures")
    public List<RelectureResume> relectures(@PathVariable Long id) {
        return relectures.parEtudiant(id);
    }

    @GetMapping("/{id}/notes")
    public List<NoteRecue> notes(@PathVariable Long id) {
        return relectures.notesRecues(id);
    }
}
