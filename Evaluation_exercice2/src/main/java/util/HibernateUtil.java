package util;

import java.io.InputStream;
import java.util.Properties;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import classes.Employe;
import classes.Projet;
import classes.Tache;
import classes.EmployeTache;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private static SessionFactory buildSessionFactory() {
        try {
            // Charger application.properties
            Properties props = new Properties();
            try (InputStream in = Thread.currentThread()
                    .getContextClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (in != null) props.load(in);
            }

            StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                    .applySettings(props)
                    .build();

            return new MetadataSources(registry)
                    .addAnnotatedClass(Employe.class)
                    .addAnnotatedClass(Projet.class)
                    .addAnnotatedClass(Tache.class)
                    .addAnnotatedClass(EmployeTache.class)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            throw new RuntimeException("Erreur création SessionFactory", e);
        }
    }

    public static SessionFactory getSessionFactory() { return sessionFactory; }

    public static void shutdown() { if (sessionFactory != null) sessionFactory.close(); }
}
