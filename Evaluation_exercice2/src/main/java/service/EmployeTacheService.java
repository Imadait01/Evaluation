package service;

import classes.Employe;
import classes.EmployeTache;
import classes.EmployeTacheId;
import classes.Tache;
import dao.IDao;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class EmployeTacheService implements IDao<EmployeTache> {

    @Override
    public void create(EmployeTache et) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.persist(et); tx.commit();
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    public EmployeTache assigner(Employe e, Tache t, LocalDate debutReel, LocalDate finReelle) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            Employe managedE = s.get(Employe.class, e.getId());
            Tache managedT = s.get(Tache.class, t.getId());
            EmployeTache et = new EmployeTache();
            et.setEmploye(managedE);
            et.setTache(managedT);
            et.setDateDebutReelle(debutReel);
            et.setDateFinReelle(finReelle);
            et.setId(new EmployeTacheId(managedE.getId(), managedT.getId()));
            s.persist(et);
            tx.commit();
            return et;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public EmployeTache update(EmployeTache et) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.merge(et); tx.commit(); return et;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public void delete(EmployeTache et) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); s.remove(s.merge(et)); tx.commit();
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public EmployeTache findById(Long compositeNotUsed) { throw new UnsupportedOperationException(); }

    public EmployeTache findById(EmployeTacheId id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction(); EmployeTache et = s.get(EmployeTache.class, id); tx.commit(); return et;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }

    @Override
    public List<EmployeTache> findAll() {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<EmployeTache> list = s.createQuery("from EmployeTache", EmployeTache.class).list();
            tx.commit(); return list;
        } catch (Exception ex) { if (tx != null) tx.rollback(); throw ex; }
    }
}
