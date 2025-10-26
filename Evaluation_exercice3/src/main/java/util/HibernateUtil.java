package util;

import beans.*;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.io.InputStream;
import java.util.Properties;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = build();

    private static SessionFactory build() {
        try {
            Properties p = new Properties();
            try (InputStream in = Thread.currentThread().getContextClassLoader()
                    .getResourceAsStream("application.properties")) {
                if (in != null) p.load(in);
            }
            var registry = new StandardServiceRegistryBuilder().applySettings(p).build();
            return new MetadataSources(registry)
                    .addAnnotatedClass(Personne.class)
                    .addAnnotatedClass(Homme.class)
                    .addAnnotatedClass(Femme.class)
                    .addAnnotatedClass(Mariage.class)
                    .buildMetadata().buildSessionFactory();
        } catch (Exception e) { throw new RuntimeException(e); }
    }

    public static SessionFactory getSessionFactory() { return sessionFactory; }
}