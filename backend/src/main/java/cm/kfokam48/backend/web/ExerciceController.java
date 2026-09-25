package cm.kfokam48.backend.web;

import cm.kfokam48.backend.dto.ExerciceDtos.*;
import cm.kfokam48.backend.service.ExerciceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exercices")
public class ExerciceController {

    private final ExerciceService exercices;

    public ExerciceController(ExerciceService exercices) {
        this.exercices = exercices;
    }

    @PostMapping
    public ResponseEntity<ExerciceResponse> deposer(@Valid @RequestBody DeposerExerciceRequest req) {
        return ResponseEntity.status(HttpStatus.CREATED).body(exercices.deposer(req));
    }

    @GetMapping("/{id}")
    public ExerciceDetail detail(@PathVariable Long id) {
        return exercices.detail(id);
    }

    @PutMapping("/{id}")
    public ExerciceDetail remplacer(@PathVariable Long id, @Valid @RequestBody RemplacerLienRequest req) {
        return exercices.remplacerLien(id, req);
    }
}
