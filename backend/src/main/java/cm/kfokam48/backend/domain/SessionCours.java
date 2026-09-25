package cm.kfokam48.backend.domain;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "session")
public class SessionCours {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 200)
    private String titre;

    @Column(nullable = false, length = 20, unique = true)
    private String code;

    @Column(name = "ouverture_at", nullable = false)
    private LocalDateTime ouvertureAt;

    @Column(name = "expiration_at", nullable = false)
    private LocalDateTime expirationAt;

    @Column(name = "cloture_at")
    private LocalDateTime clotureAt;

    @Column(nullable = false)
    private boolean cloturee = false;

    @Column(name = "promotion_id", nullable = false)
    private Long promotionId;

    @Column(name = "formateur_id")
    private Long formateurId;

    public SessionCours() {}

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitre() { return titre; }
    public void setTitre(String titre) { this.titre = titre; }
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; }
    public LocalDateTime getOuvertureAt() { return ouvertureAt; }
    public void setOuvertureAt(LocalDateTime ouvertureAt) { this.ouvertureAt = ouvertureAt; }
    public LocalDateTime getExpirationAt() { return expirationAt; }
    public void setExpirationAt(LocalDateTime expirationAt) { this.expirationAt = expirationAt; }
    public LocalDateTime getClotureAt() { return clotureAt; }
    public void setClotureAt(LocalDateTime clotureAt) { this.clotureAt = clotureAt; }
    public boolean isCloturee() { return cloturee; }
    public void setCloturee(boolean cloturee) { this.cloturee = cloturee; }
    public Long getPromotionId() { return promotionId; }
    public void setPromotionId(Long promotionId) { this.promotionId = promotionId; }
    public Long getFormateurId() { return formateurId; }
    public void setFormateurId(Long formateurId) { this.formateurId = formateurId; }
}
