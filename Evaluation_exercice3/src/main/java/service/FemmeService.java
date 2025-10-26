package service;

import beans.Femme;
import dao.IDao;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class FemmeService implements IDao<Femme> {

    // ---------- CRUD ----------
    @Override
    public void create(Femme f) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.persist(f);
            tx.commit();
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public Femme update(Femme f) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.merge(f);
            tx.commit();
            return f;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public void delete(Femme f) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.remove(s.merge(f));
            tx.commit();
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public Femme findById(Long id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            Femme f = s.get(Femme.class, id);
            tx.commit();
            return f;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public List<Femme> findAll() {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Femme> list = s.createQuery("from Femme", Femme.class).list();
            tx.commit();
            return list;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    // ---------- Méthodes demandées ----------

    /** Requête native nommée : nombre d’enfants d’une femme entre deux dates. */
    public long nbEnfantsEntre(Long femmeId, LocalDate d1, LocalDate d2) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            Number n = s.createNamedQuery("Mariage.nbEnfantsFemmeEntre", Number.class)
                    .setParameter("fid", femmeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .getSingleResult();
            tx.commit();
            return n == null ? 0L : n.longValue();
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    /** Requête nommée JPQL : femmes mariées au moins deux fois. */
    public List<Femme> femmesMarieesAuMoinsDeuxFois() {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createNamedQuery("Femme.marieeAuMoinsDeuxFois", Femme.class).list();
        }
    }
}