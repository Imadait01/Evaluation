package service;

import classes.EmployeTache;
import classes.Tache;
import dao.IDao;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class TacheService implements IDao<Tache> {

    @Override
    public void create(Tache t) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.persist(t); tx.commit();
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public Tache update(Tache t) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.merge(t); tx.commit(); return t;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public void delete(Tache t) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.remove(s.merge(t)); tx.commit();
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public Tache findById(Long id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); Tache t = s.get(Tache.class, id); tx.commit(); return t;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public List<Tache> findAll() {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Tache> list = s.createQuery("from Tache", Tache.class).list();
            tx.commit(); return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    // --- Requêtes demandées ---

    /** Tâches dont le prix est > seuil (requête nommée) */
    public List<Tache> tachesPrixSup(double seuil) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Tache> list = s.createNamedQuery("Tache.findByPrixGreaterThan", Tache.class)
                    .setParameter("prix", seuil).list();
            tx.commit();
            return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    /** Tâches réalisées entre deux dates (on filtre sur l’intervalle réel de l’affectation) */
    public List<EmployeTache> tachesRealiseesEntre(LocalDate d1, LocalDate d2) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<EmployeTache> list = s.createQuery(
                            "from EmployeTache et " +
                                    "where et.dateDebutReelle >= :d1 and et.dateFinReelle <= :d2 " +
                                    "order by et.dateDebutReelle", EmployeTache.class)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
            tx.commit();
            return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }
}
