package DAL;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Helper singleton class manages database connection
 */
public class HibernateUtil
{
    private static HibernateUtil instance = null;
    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry serviceRegistry;

    /**
     * Constructor.
     */
    private HibernateUtil()
    {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    /**
     * Builds ones the current class.
     * @return an instance of HibernateUtil class
     */
    public static HibernateUtil getInstance()
    {
        if(instance == null)
        {
            instance = new HibernateUtil();
        }
        return instance;
    }

    /**
     * Getter of session factory class. 
     * @return sessionFactory
     */
    public static SessionFactory getSessionFactory()
    {
        return sessionFactory;
    }
}
