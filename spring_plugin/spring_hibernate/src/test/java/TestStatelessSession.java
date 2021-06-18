/**
 * <pre>
 * Copyright:		Copyright(C) 2011-2012, ketayao.com
 * Filename:		com.ketayao.test.TestStatelessSession.java
 * Class:			TestStatelessSession
 * Date:			2012-9-6
 * Author:			<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version          1.1.0
 * Description:		
 *
 * </pre>
 **/


import org.hibernate.StatelessSession;
import org.hibernate.Transaction;
import org.junit.Test;

import com.ketayao.learn.hibernate.entity.Monkey;
import com.ketayao.learn.hibernate.test.HibernateUtil;

/** 
 * 	
 * @author 	<a href="mailto:ketayao@gmail.com">ketayao</a>
 * Version  1.1.0
 * @since   2012-9-6 下午2:30:10 
 */
public class TestStatelessSession {
	@Test
	public void test() {
		StatelessSession session = HibernateUtil.getSessionFactory().openStatelessSession();
		Transaction tx = session.beginTransaction();
		
		for (int i = 0; i < 100; i++) {
			Monkey monkey = new Monkey();
			monkey.setName("TestStatelessSession" + i);
			
			session.insert(monkey);
		}
		
		tx.commit();
		session.close();
	}
}
