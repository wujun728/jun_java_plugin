/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.test.Test2LevelCache.java
 * Class:			Test2LevelCache
 * Date:			2012-9-5
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/


import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.stat.Statistics;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Monkey;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/**
 * 
 * @author Wujun
 * @since 2012-9-5 上午11:36:58
 */
public class Test2LevelCache {

	/**
	 * 目的：测试查询缓存是否启用 结果：启用查询缓存需要修改3个地方： 1.<hibernate-mapping>中<cache
	 * usage="read-only"/> 2.hibernate.cfg.xml中<property
	 * name="hibernate.cache.use_query_cache">true</property>
	 * 3.query.setCacheable(true); 备注：假如使用from Monkey m where
	 * m.id=1，进行list查询，不启用query.setCacheable(true);也会加入二级缓存 但是 from Monkey m
	 * where m.name='波波'，进行list查询，不启用query.setCacheable(true);不会加入二级缓存
	 * 
	 * get会从二级缓存中查找from Monkey m where m.id=?的数据
	 * 
	 * 描述
	 */
	@SuppressWarnings("unchecked")
	@Test
	public void testQueryCache() throws Exception {
		Session session = HibernateUtil.getSessionFactory().openSession(); // 创建一个会话
		Transaction tx = null;
		tx = session.beginTransaction(); // 开始一个事务

		String queryString = "from Monkey";

		Query query = session.createQuery(queryString);
		
		List<Monkey> list = (List<Monkey>)query.list();
		for (Monkey monkey : list) {
			System.out.println(monkey.getName());
		}

		tx.commit(); // 提交事务

		session.close();
		
		System.out.println("------------------------");

		Session session2 = HibernateUtil.getSessionFactory().openSession(); // 创建一个会话
		Transaction tx2 = null;

		tx2 = session2.beginTransaction(); // 开始一个事务

		//query.setCacheable(false);//设置使用缓存
		//Monkey monkey = (Monkey) session2.createQuery("from Monkey m where m.name='TestStatelessSession0'").uniqueResult();
		Monkey monkey = (Monkey) session2.get(Monkey.class, 1L);
		System.out.println(monkey.getName());

		tx2.commit(); // 提交事务

		session2.close();

		Statistics stats = HibernateUtil.getSessionFactory().getStatistics();
		
		System.out.println(stats.toString());

	}

	/**
	 * 目的：测试get数据是否加入缓存和get是否从二级缓存中得到数据 结果：get数据会存入缓存，并且会从缓存取得数据
	 * 
	 * 描述
	 */
	@Test
	public void testGetCache() {
		Session session = HibernateUtil.getSessionFactory().openSession(); // 创建一个会话
		Transaction tx = null;
		try {
			tx = session.beginTransaction(); // 开始一个事务

			Monkey monkey = (Monkey) session.get(Monkey.class, 1L);
			System.out.println(monkey.getName());

			tx.commit(); // 提交事务

		} catch (RuntimeException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}

		System.out.println("---------------");

		Session session2 = HibernateUtil.getSessionFactory().openSession(); // 创建一个会话
		Transaction tx2 = null;
		try {
			tx2 = session2.beginTransaction(); // 开始一个事务

			// Monkey monkey = (Monkey)session2.load(Monkey.class, 1L);
			// System.out.println(monkey.getName());

			Monkey monkey2 = (Monkey) session2.get(Monkey.class, 1L);
			System.out.println(monkey2.getName());

			tx2.commit(); // 提交事务

		} catch (RuntimeException e) {
			if (tx2 != null) {
				tx2.rollback();
			}
			throw e;
		} finally {
			session2.close();
		}
	}
	
	@Test
	public void test2LCache() {
		Session s1= HibernateUtil.getSessionFactory().getCurrentSession();    
		s1.beginTransaction();    
		System.out.println("第一次查询Monkey");    
		Query q = s1.createQuery("from Monkey");    
		q.setCacheable(true);    
		q.list();    
		System.out.println("放进二级缓存");    
		s1.getTransaction().commit();    
		   
		Session s2= HibernateUtil.getSessionFactory().getCurrentSession();    
		s2.beginTransaction();    
		System.out.println("第二次查询Monkey,将不会发出sql");    
		Query q2 = s2.createQuery("from Monkey");    
		q2.list();    
		
		s2.getTransaction().commit();    
		   
		//如果配置文件打开了generate_statistics性能调解，可以得到二级缓存命中次数等数据    
		Statistics s = HibernateUtil.getSessionFactory().getStatistics();    
		System.out.println(s);    
		System.out.println("put:"+s.getSecondLevelCachePutCount());    
		System.out.println("hit:"+s.getSecondLevelCacheHitCount());    
		System.out.println("miss:"+s.getSecondLevelCacheMissCount());  
	}

	@Test
	public void test2LCache2() {
		Session s1= HibernateUtil.getSessionFactory().getCurrentSession();    
		s1.beginTransaction();    
		System.out.println("第一次查询Monkey");    
		Query q = s1.createQuery("from Monkey");    
		q.setCacheable(false);    
		q.list();    
		s1.getTransaction().commit();    
		   
		Session s2= HibernateUtil.getSessionFactory().getCurrentSession();    
		s2.beginTransaction();    
		System.out.println("第二次查询Monkey,将会发出sql");    
		Query q2 = s2.createQuery("from Monkey");    
		q2.list();   
		
		s2.getTransaction().commit();  
		
		Session s3= HibernateUtil.getSessionFactory().getCurrentSession();    
		s3.beginTransaction();    
		
		System.out.println("根据id查询Monkey,不会发出sql");
		Monkey monkey2 = (Monkey) s3.get(Monkey.class, 1L);
		System.out.println(monkey2.getName());
		
		s3.getTransaction().commit();    
		   
		//如果配置文件打开了generate_statistics性能调解，可以得到二级缓存命中次数等数据    
		Statistics s = HibernateUtil.getSessionFactory().getStatistics();    
		System.out.println(s);    
		System.out.println("put:"+s.getSecondLevelCachePutCount());    
		System.out.println("hit:"+s.getSecondLevelCacheHitCount());    
		System.out.println("miss:"+s.getSecondLevelCacheMissCount());  
	}
	
}
