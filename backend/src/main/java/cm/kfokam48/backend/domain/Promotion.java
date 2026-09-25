package cm.kfokam48.backend.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "promotion")
public class Promotion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String nom;

    @Column(length = 10)
    private String annee;

    public Promotion() {}

    public Promotion(String nom, String annee) {
        this.nom = nom;
        this.annee = annee;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }
    public String getAnnee() { return annee; }
    public void setAnnee(String annee) { this.annee = annee; }
}
