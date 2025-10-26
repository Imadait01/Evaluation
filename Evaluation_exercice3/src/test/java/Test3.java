

import beans.*;
import service.*;
import util.HibernateUtil;

import java.time.LocalDate;
import java.util.List;

public class Test3 {
    public static void main(String[] args) {
        var hommeSrv = new HommeService();
        var femmeSrv = new FemmeService();
        var mariageSrv = new MariageService();


        Homme h1 = new Homme(); h1.setNom("AIT"); h1.setPrenom("Imad");
        h1.setDateNaissance(LocalDate.of(1960, 1, 1)); hommeSrv.create(h1);

        Homme h2 = new Homme(); h2.setNom("michich"); h2.setPrenom("ilyas");
        h2.setDateNaissance(LocalDate.of(1965, 2, 2)); hommeSrv.create(h2);



        Femme f1 = new Femme(); f1.setNom("joe"); f1.setPrenom("RAMI"); f1.setDateNaissance(LocalDate.of(1965,3,1));
        Femme f2 = new Femme(); f2.setNom("alex");   f2.setPrenom("ALI");  f2.setDateNaissance(LocalDate.of(1970,4,2));
        Femme f3 = new Femme(); f3.setNom("caroline");   f3.setPrenom("ALAOUI"); f3.setDateNaissance(LocalDate.of(1972,5,3));
        Femme f4 = new Femme(); f4.setNom("julien"); f4.setPrenom("ALAMI"); f4.setDateNaissance(LocalDate.of(1968,6,4));
        // … f5..f10
        femmeSrv.create(f1); femmeSrv.create(f2); femmeSrv.create(f3); femmeSrv.create(f4);
        // … create f5..f10


        save(new Mariage(LocalDate.of(1989,9,3), LocalDate.of(1990,9,3), 0, h1, f4));
        save(new Mariage(LocalDate.of(1990,9,3), null, 4, h1, f1));
        save(new Mariage(LocalDate.of(1995,9,3), null, 2, h1, f2));
        save(new Mariage(LocalDate.of(2000,11,4), null, 3, h1, f3));


        System.out.println("\nListe des femmes :");
        femmeSrv.findAll().forEach(f ->
                System.out.println(" - " + f.getNom() + " " + f.getPrenom() + " (" + f.getDateNaissance() + ")"));


        Femme plusAgee = femmeSrv.findAll().stream()
                .min((a,b) -> a.getDateNaissance().compareTo(b.getDateNaissance()))
                .orElse(null);
        System.out.println("\nFemme la plus âgée : " + plusAgee.getNom() + " " + plusAgee.getPrenom());


        System.out.println("\nÉpouses de SAFI SAID entre 1990 et 2001 :");
        hommeSrv.epousesEntre(h1.getId(), LocalDate.of(1990,1,1), LocalDate.of(2001,12,31))
                .forEach(f -> System.out.println(" - " + f.getNom() + " " + f.getPrenom()));


        long nb = femmeSrv.nbEnfantsEntre(f1.getId(), LocalDate.of(1980,1,1), LocalDate.of(2020,12,31));
        System.out.println("\nEnfants de " + f1.getNom() + " " + f1.getPrenom() + " entre 1980-2020 : " + nb);


        System.out.println("\nFemmes mariées au moins deux fois :");
        femmeSrv.femmesMarieesAuMoinsDeuxFois().forEach(f ->
                System.out.println(" - " + f.getNom() + " " + f.getPrenom()));


        long nbH = mariageSrv.nbHommesMarieAQuatreFemmesEntre(LocalDate.of(1980,1,1), LocalDate.of(2030,1,1));
        System.out.println("\nHommes mariés à 4 femmes (1980-2030) : " + nbH);


        System.out.println("\nNom : ait imad");
        var mariages = hommeSrv.mariagesDe(h1.getId());
        System.out.println("Mariages En Cours :");
        int i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() == null) {
                System.out.printf("%d. Femme : %s %s   Date Début : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme().getNom(), m.getFemme().getPrenom(), m.getDateDebut(), m.getNbrEnfant());
            }
        }
        System.out.println("\nMariages échoués :");
        i = 1;
        for (Mariage m : mariages) {
            if (m.getDateFin() != null) {
                System.out.printf("%d. Femme : %s %s   Date Début : %s    Date Fin : %s    Nbr Enfants : %d%n",
                        i++, m.getFemme().getNom(), m.getFemme().getPrenom(), m.getDateDebut(), m.getDateFin(), m.getNbrEnfant());
            }
        }
    }

    private static void save(Mariage m) {
        try (var s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            var tx = s.beginTransaction();
            s.persist(m);
            tx.commit();
        }
    }
}