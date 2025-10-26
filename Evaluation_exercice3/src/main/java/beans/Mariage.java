package beans;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity @Table(name = "mariage")
@NamedNativeQuery(
        name = "Mariage.nbEnfantsFemmeEntre",
        query = """
    SELECT COALESCE(SUM(m.nbr_enfant),0)
    FROM mariage m
    WHERE m.femme_id = :fid
      AND m.date_debut >= :d1 AND (m.date_fin IS NULL OR m.date_fin <= :d2)
  """,
        resultClass = Long.class   // sera renvoyé comme Number dans Hibernate 6
)
public class Mariage {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate dateDebut;
    private LocalDate dateFin;
    @Column(name="nbr_enfant")
    private int nbrEnfant;

    @ManyToOne @JoinColumn(name = "homme_id")
    private Homme homme;

    @ManyToOne @JoinColumn(name = "femme_id")
    private Femme femme;

    public Mariage() {}
    public Mariage(LocalDate d1, LocalDate d2, int n, Homme h, Femme f) {
        this.dateDebut = d1; this.dateFin = d2; this.nbrEnfant = n; this.homme = h; this.femme = f;
    }

    public Long getId() { return id; }
    public LocalDate getDateDebut() { return dateDebut; }
    public void setDateDebut(LocalDate d) { this.dateDebut = d; }
    public LocalDate getDateFin() { return dateFin; }
    public void setDateFin(LocalDate d) { this.dateFin = d; }
    public int getNbrEnfant() { return nbrEnfant; }
    public void setNbrEnfant(int n) { this.nbrEnfant = n; }
    public Homme getHomme() { return homme; }
    public void setHomme(Homme h) { this.homme = h; }
    public Femme getFemme() { return femme; }
    public void setFemme(Femme f) { this.femme = f; }
}