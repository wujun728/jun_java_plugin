/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.TestType.java
 * Class:			TestType
 * Date:			2012-5-15
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/


import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Monkey;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/**
 * 
 * @author <a href="mailto:ketayao@gmail.com">ketayao</a> Version 1.1.0
 * @since 2012-5-15 上午10:38:16
 */

public class TestGet {

	/**
	 * 目的：测试get会不会使用一级缓存（session缓存）
	 * 结果：get会使用一级缓存。
	 * 描述
	 */
	@Test
	public void testGet() {
		Session session = HibernateUtil.getSessionFactory().openSession(); // 创建一个会话
		Transaction tx = null;
		try {
			tx = session.beginTransaction(); // 开始一个事务

			Monkey monkey3 = (Monkey) session.load(Monkey.class, 1L);
			System.out.println(monkey3.getName());

			Monkey monkey = (Monkey) session.get(Monkey.class, 1L);
			System.out.println(monkey.getName());

			Monkey monkey2 = (Monkey) session.get(Monkey.class, 1L);
			System.out.println(monkey2.getName());

			tx.commit(); // 提交事务

		} catch (RuntimeException e) {
			if (tx != null) {
				tx.rollback();
			}
			throw e;
		} finally {
			session.close();
		}
	}
}
