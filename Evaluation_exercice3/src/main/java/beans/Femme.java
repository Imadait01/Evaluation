package beans;

import jakarta.persistence.Entity;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;

@Entity @Table(name = "femme")
@NamedQuery(
        name = "Femme.marieeAuMoinsDeuxFois",
        query = "select f from Femme f join Mariage m on m.femme = f group by f having count(m) >= 2"
)
public class Femme extends Personne {}