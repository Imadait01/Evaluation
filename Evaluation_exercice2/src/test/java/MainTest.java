import classes.*;
import service.*;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class MainTest {
    public static void main(String[] args) {
        EmployeService employeService = new EmployeService();
        ProjetService projetService = new ProjetService();
        TacheService tacheService = new TacheService();
        EmployeTacheService etService = new EmployeTacheService();


        Employe chef = new Employe("salim", "kohaich", "0675469376");
        employeService.create(chef);

        Projet p = new Projet("Gestion de stock",
                LocalDate.of(2013,1,14),
                LocalDate.of(2013,6,30),
                chef);
        projetService.create(p);

        Tache t1 = new Tache("Analyse",
                LocalDate.of(2013,2,1), LocalDate.of(2013,2,20),
                1200, p);
        Tache t2 = new Tache("Conception",
                LocalDate.of(2013,3,1), LocalDate.of(2013,3,15),
                2200, p);
        Tache t3 = new Tache("Développement",
                LocalDate.of(2013,4,1), LocalDate.of(2013,4,25),
                5000, p);
        tacheService.create(t1); tacheService.create(t2); tacheService.create(t3);


        etService.assigner(chef, t1, LocalDate.of(2013,2,10), LocalDate.of(2013,2,20));
        etService.assigner(chef, t2, LocalDate.of(2013,3,10), LocalDate.of(2013,3,15));
        etService.assigner(chef, t3, LocalDate.of(2013,4,10), LocalDate.of(2013,4,25));


        System.out.println("\n=== Projets gérés par " + chef.getNom() + " ===");
        employeService.projetsGeresParEmploye(chef.getId()).forEach(pr ->
                System.out.println(" - " + pr.getId() + " | " + pr.getNom() + " | début " + pr.getDateDebut()));

        System.out.println("\n=== Tâches planifiées pour le projet ===");
        projetService.tachesPlanifieesPourProjet(p.getId()).forEach(t ->
                System.out.println(" - " + t.getId() + " " + t.getNom() +
                        " (planifié: " + t.getDateDebut() + " -> " + t.getDateFin() + ")"));

        System.out.println("\n=== Tâches réalisées (agrégées par tâche) ===");
        System.out.printf("Projet : %d    Nom : %s    Date début : %s%n",
                p.getId(), p.getNom(), p.getDateDebut());
        System.out.println("Liste des tâches:");
        System.out.println("Num  Nom      Date Début Réelle   Date Fin Réelle");
        List<Object[]> lignes = projetService.tachesRealiseesAvecDates(p.getId());
        for (Object[] row : lignes) {
            Long id = (Long) row[0];
            String nom = (String) row[1];
            java.time.LocalDate ddr = (java.time.LocalDate) row[2];
            java.time.LocalDate dfr = (java.time.LocalDate) row[3];
            System.out.printf("%-4d %-15s %-18s %-15s%n", id, nom, ddr, dfr);
        }

        System.out.println("\n=== Tâches prix > 1000 DH ===");
        tacheService.tachesPrixSup(1000).forEach(t ->
                System.out.println(" - " + t.getNom() + " : " + t.getPrix() + " DH"));

        System.out.println("\n=== Tâches réalisées entre 2013-03-01 et 2013-04-30 ===");
        tacheService.tachesRealiseesEntre(LocalDate.of(2013,3,1), LocalDate.of(2013,4,30))
                .forEach(et -> System.out.println(" - " + et.getTache().getNom()
                        + " (" + et.getDateDebutReelle() + " -> " + et.getDateFinReelle() + ")"));

        HibernateUtil.shutdown();
    }
}
