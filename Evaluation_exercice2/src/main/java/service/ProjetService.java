package service;

import classes.Projet;
import classes.Tache;
import dao.IDao;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class ProjetService implements IDao<Projet> {

    @Override
    public void create(Projet p) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.persist(p); tx.commit();
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public Projet update(Projet p) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.merge(p); tx.commit(); return p;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public void delete(Projet p) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.remove(s.merge(p)); tx.commit();
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public Projet findById(Long id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); Projet p = s.get(Projet.class, id); tx.commit(); return p;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public List<Projet> findAll() {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Projet> list = s.createQuery("from Projet", Projet.class).list();
            tx.commit(); return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    /** Tâches planifiées d’un projet (dates prévues) */
    public List<Tache> tachesPlanifieesPourProjet(Long projetId) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Tache> list = s.createQuery(
                    "from Tache t where t.projet.id = :pid order by t.dateDebut",
                    Tache.class
            ).setParameter("pid", projetId).list();
            tx.commit();
            return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    /**
     * Tâches réalisées avec agrégation des dates réelles (min début, max fin) par tâche.
     * Renvoie Object[] { Long id, String nom, LocalDate debutReel, LocalDate finReelle }
     */
    public List<Object[]> tachesRealiseesAvecDates(Long projetId) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Object[]> rows = s.createQuery(
                            "select t.id, t.nom, min(et.dateDebutReelle), max(et.dateFinReelle) " +
                                    "from EmployeTache et join et.tache t " +
                                    "where t.projet.id = :pid and et.dateDebutReelle is not null and et.dateFinReelle is not null " +
                                    "group by t.id, t.nom order by min(et.dateDebutReelle)", Object[].class)
                    .setParameter("pid", projetId)
                    .list();
            tx.commit();
            return rows;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }
}
