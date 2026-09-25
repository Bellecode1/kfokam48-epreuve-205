package cm.kfokam48.backend.domain;

import cm.kfokam48.backend.domain.enums.SourcePresence;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "presence",
       uniqueConstraints = @UniqueConstraint(columnNames = {"session_id", "etudiant_id"}))
public class Presence {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "session_id", nullable = false)
    private Long sessionId;

    @Column(name = "etudiant_id", nullable = false)
    private Long etudiantId;

    @Column(name = "marquee_at", nullable = false)
    private LocalDateTime marqueeAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 20)
    private SourcePresence source;

    public Presence() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getSessionId() { return sessionId; }
    public void setSessionId(Long sessionId) { this.sessionId = sessionId; }
    public Long getEtudiantId() { return etudiantId; }
    public void setEtudiantId(Long etudiantId) { this.etudiantId = etudiantId; }
    public LocalDateTime getMarqueeAt() { return marqueeAt; }
    public void setMarqueeAt(LocalDateTime marqueeAt) { this.marqueeAt = marqueeAt; }
    public SourcePresence getSource() { return source; }
    public void setSource(SourcePresence source) { this.source = source; }
}
