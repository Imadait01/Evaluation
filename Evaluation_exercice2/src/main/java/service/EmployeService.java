package service;

import classes.*;
import dao.IDao;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class EmployeService implements IDao<Employe> {

    @Override
    public void create(Employe e) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.persist(e);
            tx.commit();
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public Employe update(Employe e) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.merge(e);
            tx.commit();
            return e;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public void delete(Employe e) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.remove(s.merge(e));
            tx.commit();
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public Employe findById(Long id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            Employe e = s.get(Employe.class, id);
            tx.commit();
            return e;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public List<Employe> findAll() {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Employe> list = s.createQuery("from Employe", Employe.class).list();
            tx.commit();
            return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    // --- Méthodes métier demandées ---

    /** Liste des tâches réalisées (dates réelles renseignées) par un employé. */
    public List<Tache> tachesRealiseesParEmploye(Long employeId) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Tache> list = s.createQuery(
                    "select et.tache from EmployeTache et " +
                            "where et.employe.id = :id and et.dateDebutReelle is not null and et.dateFinReelle is not null",
                    Tache.class
            ).setParameter("id", employeId).list();
            tx.commit();
            return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    /** Projets gérés par un employé (chef de projet). */
    public List<Projet> projetsGeresParEmploye(Long employeId) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Projet> list = s.createQuery(
                    "from Projet p where p.chefProjet.id = :id order by p.dateDebut",
                    Projet.class
            ).setParameter("id", employeId).list();
            tx.commit();
            return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }
}
