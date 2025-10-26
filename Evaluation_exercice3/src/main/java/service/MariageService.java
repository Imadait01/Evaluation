package service;

import beans.Mariage;
import util.HibernateUtil;
import org.hibernate.Session;

import jakarta.persistence.criteria.*;
import java.time.LocalDate;

public class MariageService {
    public long nbHommesMarieAQuatreFemmesEntre(LocalDate d1, LocalDate d2) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            var cb = s.getCriteriaBuilder();
            CriteriaQuery<Long> cq = cb.createQuery(Long.class);
            Root<Mariage> m = cq.from(Mariage.class);

            // GROUP BY homme, COUNT(distinct femme) = 4 sur période
            cq.select(cb.countDistinct(m.get("homme")))
                    .where(
                            cb.lessThanOrEqualTo(m.get("dateDebut"), d2),
                            cb.or(cb.isNull(m.get("dateFin")),
                                    cb.greaterThanOrEqualTo(m.get("dateFin"), d1))
                    )
                    .groupBy(m.get("homme"))
                    .having(cb.equal(cb.countDistinct(m.get("femme")), 4L));

            return s.createQuery(cq).getSingleResult();
        }
    }
}