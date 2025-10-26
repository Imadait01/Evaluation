package classes;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "employe_tache")
public class EmployeTache {

    @EmbeddedId
    private EmployeTacheId id = new EmployeTacheId();

    @ManyToOne @MapsId("employeId")
    @JoinColumn(name = "employe_id")
    private Employe employe;

    @ManyToOne @MapsId("tacheId")
    @JoinColumn(name = "tache_id")
    private Tache tache;

    private LocalDate dateDebutReelle;
    private LocalDate dateFinReelle;

    public EmployeTache() {}

    // ⚠️ Ne pas forcer les IDs ici : laisse JPA remplir via @MapsId.
    public EmployeTache(Employe e, Tache t, LocalDate ddr, LocalDate dfr) {
        setEmploye(e);
        setTache(t);
        this.dateDebutReelle = ddr;
        this.dateFinReelle = dfr;
    }

    public EmployeTacheId getId() { return id; }
    public void setId(EmployeTacheId employeTacheId) { this.id = employeTacheId; }

    public Employe getEmploye() { return employe; }
    public void setEmploye(Employe employe) {
        this.employe = employe;
        // Si l'employé a déjà un id (managed), synchroniser la clé composée
        if (employe != null && employe.getId() != null) {
            this.id.setEmployeId(employe.getId());
        }
    }

    public Tache getTache() { return tache; }
    public void setTache(Tache tache) {
        this.tache = tache;
        if (tache != null && tache.getId() != null) {
            this.id.setTacheId(tache.getId());
        }
    }

    public LocalDate getDateDebutReelle() { return dateDebutReelle; }
    public void setDateDebutReelle(LocalDate d) { this.dateDebutReelle = d; }
    public LocalDate getDateFinReelle() { return dateFinReelle; }
    public void setDateFinReelle(LocalDate d) { this.dateFinReelle = d; }

    // Sécurité : si les IDs FK sont maintenant connus, les recopier dans l'EmbeddedId
    @PrePersist
    public void syncKeyBeforePersist() {
        if (this.employe != null)  this.id.setEmployeId(this.employe.getId());
        if (this.tache != null)    this.id.setTacheId(this.tache.getId());
    }

    @Override public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof EmployeTache)) return false;
        EmployeTache that = (EmployeTache) o;
        return Objects.equals(id, that.id);
    }

    @Override public int hashCode() { return Objects.hash(id); }
}
