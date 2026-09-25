package cm.kfokam48.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "tentative_code",
       uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "etudiant_id"}))
public class TentativeCode {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "etudiant_id", nullable = false)
    private Long etudiantId;

    @Column(name = "nb_echecs", nullable = false)
    private int nbEchecs = 0;

    @Column(name = "bloque_jusqua")
    private LocalDateTime bloqueJusqua;

    public TentativeCode() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    public int getNbEchecs() { return nbEchecs; }
    public void setNbEchecs(int nbEchecs) { this.nbEchecs = nbEchecs; }
    public LocalDateTime getBloqueJusqua() { return bloqueJusqua; }
    public void setBloqueJusqua(LocalDateTime bloqueJusqua) { this.bloqueJusqua = bloqueJusqua; }
}
