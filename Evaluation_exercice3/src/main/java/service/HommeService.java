package service;

import beans.Femme;
import beans.Homme;
import beans.Mariage;
import dao.IDao;
import util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.time.LocalDate;
import java.util.List;

public class HommeService implements IDao<Homme> {

    @Override
    public void create(Homme h) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.persist(h);
            tx.commit();
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public Homme update(Homme h) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.merge(h);
            tx.commit();
            return h;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public void delete(Homme h) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            s.remove(s.merge(h));
            tx.commit();
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public Homme findById(Long id) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            Homme h = s.get(Homme.class, id);
            tx.commit();
            return h;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }

    @Override
    public List<Homme> findAll() {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Homme> list = s.createQuery("from Homme", Homme.class).list();
            tx.commit();
            return list;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }


    public List<Femme> epousesEntre(Long hommeId, LocalDate d1, LocalDate d2) {
        Transaction tx = null;
        try (Session s = HibernateUtil.getSessionFactory().getCurrentSession()) {
            tx = s.beginTransaction();
            List<Femme> res = s.createQuery("""
                    select distinct m.femme
                    from Mariage m
                    where m.homme.id = :hid
                      and (m.dateDebut <= :d2)
                      and (m.dateFin is null or m.dateFin >= :d1)
                    order by m.dateDebut
                    """, Femme.class)
                    .setParameter("hid", hommeId)
                    .setParameter("d1", d1)
                    .setParameter("d2", d2)
                    .list();
            tx.commit();
            return res;
        } catch (Exception e) { if (tx != null) tx.rollback(); throw e; }
    }


    public List<Mariage> mariagesDe(Long hommeId) {
        try (Session s = HibernateUtil.getSessionFactory().openSession()) {
            return s.createQuery("""
                from Mariage m
                join fetch m.femme
                where m.homme.id = :hid
                order by m.dateDebut
            """, Mariage.class).setParameter("hid", hommeId).list();
        }
    }
}