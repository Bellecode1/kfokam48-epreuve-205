package cm.kfokam48.backend;

import cm.kfokam48.backend.domain.Relecture;
import cm.kfokam48.backend.domain.enums.StatutRelecture;
import cm.kfokam48.backend.dto.RelectureDtos.RendreRelectureRequest;
import cm.kfokam48.backend.exception.NoteInvalideException;
import cm.kfokam48.backend.repository.EtudiantRepository;
import cm.kfokam48.backend.repository.ExerciceRepository;
import cm.kfokam48.backend.repository.RelectureRepository;
import cm.kfokam48.backend.service.RelectureService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RelectureServiceTest {

    @Mock RelectureRepository relectures;
    @Mock ExerciceRepository exercices;
    @Mock EtudiantRepository etudiants;

    @InjectMocks RelectureService service;

    @Test
    void noteHorsBornesDoitEtreRefusee() {
        Relecture r = new Relecture();
        r.setId(1L);
        r.setStatut(StatutRelecture.ASSIGNEE);
        when(relectures.findById(1L)).thenReturn(Optional.of(r));

        assertThrows(NoteInvalideException.class,
                () -> service.rendre(1L, new RendreRelectureRequest(25, "trop haut")));
        assertThrows(NoteInvalideException.class,
                () -> service.rendre(1L, new RendreRelectureRequest(-1, "negatif")));
        assertThrows(NoteInvalideException.class,
                () -> service.rendre(1L, new RendreRelectureRequest(null, "null")));
    }
}
