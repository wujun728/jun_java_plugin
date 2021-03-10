/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.learn.hibernate.test.cascade.TestCascadeTypeLock.java
 * Class:			TestCascadeTypeLock
 * Date:			2012-9-27
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/
 
package cascade;

import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.ReplicationMode;
import org.hibernate.Session;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.User;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/** 
 * 	
 * @author Wujun
 * Version  1.1.0
 * @since   2012-9-27 上午10:15:59 
 */

public class TestLock {
	@Test
	public void testLock() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = (User)s1.get(User.class, 5L);
		s1.delete(user);
		
		s1.buildLockRequest(LockOptions.NONE).lock(user);
		//s1.save(user);
		s1.getTransaction().commit();
	}
	
	@Test
	public void testSave() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = new User();
		user.setName("kaka:");
		
		s1.save(user);
		s1.getTransaction().commit();
	}
	
	@Test
	public void testPersist() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = new User();
		user.setName("kaka2:");
		
		s1.persist(user);
		s1.getTransaction().commit();
	}
	
	@Test
	public void testSaveOrUpdate() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = new User();
		user.setId(100L);
		user.setName("kaka2:");
		
		s1.saveOrUpdate(user);
		s1.getTransaction().commit();
	}
	
	@Test
	public void testMerge() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = new User();
		user.setId(100L);
		user.setName("kaka2:");
		
		s1.merge(user);
		s1.getTransaction().commit();
	}
	
	@Test
	public void testReplicate() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = new User();
		user.setId(100L);
		user.setName("kaka2:");
		
		s1.replicate(user, ReplicationMode.EXCEPTION);
		s1.getTransaction().commit();
	}
	
	@Test
	public void testdeleteMerge() {
		Session s1 = HibernateUtil.getSessionFactory().getCurrentSession();
		s1.beginTransaction();
		
		User user = (User)s1.get(User.class, 21L);
		s1.delete(user);
		s1.merge(user);
		s1.getTransaction().commit();
	}
}
