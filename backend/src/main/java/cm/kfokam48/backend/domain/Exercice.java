package cm.kfokam48.backend.domain;

import cm.kfokam48.backend.domain.enums.StatutExercice;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "exercice",
       uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "etudiant_id"}))
public class Exercice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "etudiant_id", nullable = false)
    private Long etudiantId;

    @Column(nullable = false, length = 500)
    private String lien;

    @Column(name = "depose_at", nullable = false)
    private LocalDateTime deposeAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private StatutExercice statut;

    public Exercice() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    public String getLien() { return lien; }
    public void setLien(String lien) { this.lien = lien; }
    public LocalDateTime getDeposeAt() { return deposeAt; }
    public void setDeposeAt(LocalDateTime deposeAt) { this.deposeAt = deposeAt; }
    public StatutExercice getStatut() { return statut; }
    public void setStatut(StatutExercice statut) { this.statut = statut; }
}
