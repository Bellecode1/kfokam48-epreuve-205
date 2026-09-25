package cm.kfokam48.backend.domain;

import cm.kfokam48.backend.domain.enums.StatutRelecture;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "relecture")
public class Relecture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "exercice_id", nullable = false)
    private Long exerciceId;

    @Column(name = "relecteur_id")
    private Long relecteurId;

    private Integer note;

    @Column(length = 2000)
    private String commentaire;

    @Column(name = "rendue_at")
    private LocalDateTime rendueAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private StatutRelecture statut;

    public Relecture() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getExerciceId() { return exerciceId; }
    public void setExerciceId(Long exerciceId) { this.exerciceId = exerciceId; }
    public Long getRelecteurId() { return relecteurId; }
    public void setRelecteurId(Long relecteurId) { this.relecteurId = relecteurId; }
    public Integer getNote() { return note; }
    public void setNote(Integer note) { this.note = note; }
    public String getCommentaire() { return commentaire; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public LocalDateTime getRendueAt() { return rendueAt; }
    public void setRendueAt(LocalDateTime rendueAt) { this.rendueAt = rendueAt; }
    public StatutRelecture getStatut() { return statut; }
    public void setStatut(StatutRelecture statut) { this.statut = statut; }
}
