/**
 * 
 */
package net.oschina.j2cache.hibernate3;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertTrue;

/**
 * @author Wujun
 *
 */

public class J2CacheProviderTester {
    
    static SessionFactory sessionFactory;
    
    static {
        Configuration configuration = new Configuration();
        sessionFactory = configuration.configure().buildSessionFactory();
    }
    
    @Before
    public void testInsert() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            User user = new User();
            user.setId(1);
            user.setName("tom");
            session.save(user);
            user = (User) session.get(User.class, 1);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    @After
    public void testDelete() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        try {
            User user = (User) session.get(User.class, 1);
            assertTrue(user != null);
            session.delete(user);
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
    
    @Test
    public void testQuery() {
        Session session = sessionFactory.openSession();
        User user = (User) session.get(User.class, 1);
        assertTrue(user.getId() == 1);
        user = (User) session.get(User.class, 1);
        assertTrue(user.getName().equals("tom"));
    }
}
